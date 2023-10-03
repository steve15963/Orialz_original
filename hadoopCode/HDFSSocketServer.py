from mmdet.apis import DetInferencer
from rich.pretty import pprint
import json
import torch
import socket
from hdfs import InsecureClient
import os

# model과 checkpoint
model_name = 'faster-rcnn_r50-caffe_fpn_ms-3x_coco'
checkpoint_file = './checkpoints/faster_rcnn_r50_caffe_fpn_mstrain_3x_coco_20210526_095054-1f77628b.pth'

device = 'cpu'
# Set the device to be used for evaluation
if torch.cuda.is_available():
    device = 'cuda:0'


# Initialize the DetInferencer
print("인공지능 모델 로딩중")
inferencer = DetInferencer(model_name, checkpoint_file, device)
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

            while True:
                data = conn.recv(1024)
                if not data:
                    break
                print(f"Java 클라이언트로부터 받은 메시지: {data.decode('utf-8')}")
                with client.read(data.decode('utf-8')) as wr:
                    with open('temp.png','wb') as file:
                        file.write(wr.read())
                img = 'temp.png'

                result = inferencer(img)

                # 결과를 JSON 형태로 직렬화
                result_json = json.dumps(result)

                # JSON 데이터를 문자열로 인코딩하여 클라이언트로 전송
                conn.sendall(result_json.encode('utf-8'))

                conn.close()
                break;