from ultralytics import YOLO
from multiprocessing import freeze_support
import os
os.environ['KMP_DUPLICATE_LIB_OK']='True'

def run():
    freeze_support()
    
    # Load a model
    # model = YOLO("yolov8n.yaml")  # build a new model from scratch
    # model = YOLO("yolov8n.pt")  # load a pretrained model (recommended for training)
    model = YOLO("best.pt")

    # Use the model
    # model.train(data="coco128.yaml", epochs=3, batch=8)  # train the model
    # metrics = model.val()  # evaluate model performance on the validation set
    results = model("./images/fish002.jpg")  # predict on an image
    # path = model.export(format="onnx")  # export the model to ONNX format
    print(results)

if __name__ == '__main__':
    run()
