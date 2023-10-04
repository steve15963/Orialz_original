from ultralytics import YOLO
from multiprocessing import freeze_support
import os
import numpy as np
import json

os.environ['KMP_DUPLICATE_LIB_OK']='True'

def run():
    freeze_support()
    
    # Load a model
    # model = YOLO("yolov8n.yaml")  # build a new model from scratch
    # model = YOLO("yolov8n.pt")  # load a pretrained model (recommended for training)
    model = YOLO("best.pt")

    while (True):
    # img = './images/image.jpg
        img = input()

        results = model.predict(source=img)

        for result in results:
            data = {}
            data["img_name"] = img.split('/')[-1]
            data["orig_shape"] = result.boxes.orig_shape
            data["cls"] = list(map(int, result.boxes.cls.tolist()))
            xywhn = result.boxes.xywhn.tolist()
            for i in range(len(xywhn)):
                xywhn[i][0] = xywhn[i][0] - (xywhn[i][2] / 2)
                xywhn[i][1] = xywhn[i][1] - (xywhn[i][3] / 2)
            data["xywhn"] = xywhn
            json_data = json.dumps(data)
            print(json_data)
            

if __name__ == '__main__':
    run()
