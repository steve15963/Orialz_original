from ultralytics import YOLO
from multiprocessing import freeze_support
import os
os.environ['KMP_DUPLICATE_LIB_OK']='True'

def run():
    freeze_support()
    
    # Load a model 
    model = YOLO("yolov8n.pt")  # load a pretrained model (recommended for training)

    # Use the model
    model.train(data="../datasets/Aquarium/data.yaml", epochs=3, batch=8)  # train the model
    print(type(model.names), len(model.names))
    print(model.names)
    
    # metrics = model.val()  # evaluate model performance on the validation set
    results = model.predict(source="../datasets/Aquarium/test/images", save=True)  # predict on an image
    # path = model.export(format="onnx")  # export the model to ONNX format
    print(results)

if __name__ == '__main__':
    run()
