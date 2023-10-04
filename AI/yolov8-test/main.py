from ultralytics import YOLO
from multiprocessing import freeze_support
import os
import numpy as np
import json
import socket
from hdfs import InsecureClient

os.environ['KMP_DUPLICATE_LIB_OK']='True'

def run():
    freeze_support()

    # Load a model
    # model = YOLO("yolov8n.yaml")  # build a new model from scratch
    # model = YOLO("yolov8n.pt")  # load a pretrained model (recommended for training)
    print("인공지능 모델 로딩중")
    model = YOLO("best.pt")
    print("인공지능 모델 로딩 완료")

    print("HDFS Client 모듈 로딩")
    client = InsecureClient("http://cluster1:9870")
    print("HDFS Client 모듈 로딩 끝")

    host = '0.0.0.0'
    port = 8080
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.bind((host, port))
        s.listen()
        print(f"Python 서버가 {port} 포트에서 대기 중입니다...")
        while True:
            conn, addr = s.accept()
            with conn:
                print(f"{addr}에서 연결됨")

                url = conn.recv(1024)
                if not url:
                    break
                print(f"Java 클라이언트로부터 받은 메시지: {url.decode('utf-8')}")
                with client.read(url.decode('utf-8')) as wr:
                    with open('temp.png','wb') as file:
                        file.write(wr.read())
                results = model.predict(source='temp.png')

                for result in results:
                    data = {}
                    data["img_name"] = url.decode('utf-8').split('/')[-1]
                    data["orig_shape"] = result.boxes.orig_shape
                    data["cls"] = list(map(int, result.boxes.cls.tolist()))
                    data["conf"] = result.boxes.conf.tolist()
                    xywhn = result.boxes.xywhn.tolist()
                    for i in range(len(xywhn)):
                        xywhn[i][0] = xywhn[i][0] - (xywhn[i][2] / 2)
                        xywhn[i][1] = xywhn[i][1] - (xywhn[i][3] / 2)
                    data["xywhn"] = xywhn
                    json_data = json.dumps(data)
                    conn.sendall(json_data.encode('utf-8'))
                    conn.close()
                    break;

if __name__ == '__main__':
    run()
