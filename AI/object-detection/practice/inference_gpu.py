# ### 모델 inference 방법
# 1. 모델 초기화
#     - device로 cpu를 사용하고 싶다면 `cpu`를 적어주면 되고, 만약 gpu를 사용하고 싶다면 `cuda:0`을 작성한다.
#     - `DetInferencer()`을 통해서 설정값과 모델 결정한다.
# 2. 모델 데모 inference
#     - MMDetection에서는 테스트 용으로 데모 이미지를 제공한다.(`mmdetection/demo/demo.jpg`)
#     - 이미지 파일을 변수 형태로 담고, inferencer()를 실행시켜 이미지의 사물을 탐지한다.
#     - `score_thr`는 threshold를 의미하며, 모델이 확신하는 정도가 0.3 이하는 바운딩 박스를 그리지 말라는 의미
#     - pprint()을 통해서 시각적으로 보기 좋게 출력한다.
#     - Image.open()으로 기존 사진에 인식한 사물을 그려내어 출력한 결과를 볼 수 있다.

# In[7]:


# model 파일 코랩에서 다운로드 - 변수 저장
# config_file = 'configs/faster_rcnn/faster_rcnn_r50_caffe_fpn_mstrain_3x_coco.py'
model_name = 'faster-rcnn_r50-caffe_fpn_ms-3x_coco'
checkpoint_file = './checkpoints/faster_rcnn_r50_caffe_fpn_mstrain_3x_coco_20210526_095054-1f77628b.pth'

# In[8]:


# mmdetection 디렉토리로 이동
get_ipython().run_line_magic('cd', 'mmdetection')


# In[ ]:


# 라이브러리 충돌 방지
import os
os.environ['KMP_DUPLICATE_LIB_OK'] = 'True'


# In[20]:


# 모델 초기화
from mmdet.apis import DetInferencer

# Choose to use a config
# model_name = 'faster_rcnn_r50_caffe_fpn_mstrain_3x_coco'
# Setup a checkpoint file to load
# checkpoint = './checkpoints/faster_rcnn_r50_caffe_fpn_mstrain_3x_coco_20210526_095054-1f77628b.pth'

# Set the device to be used for evaluation
device = 'cuda:0'

# Initialize the DetInferencer
inferencer = DetInferencer(model_name, checkpoint_file, device)


# In[21]:


# 모델 데모 inference
img = './demo/demo.jpg'
result = inferencer(img, out_dir='./output')


# In[22]:


# 모델 데모 inference - 결과 확인
from rich.pretty import pprint
pprint(result, max_length=4)
