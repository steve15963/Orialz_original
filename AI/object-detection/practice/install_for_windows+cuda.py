# # MMDetection 모델 학습 실습
# 

# ## 모델 설치 및 기본 세팅
# ---

# ### 라이브러리 설치
# 1. `mmcv` 설치
#     - MMDetection을 설치하기 전, mmcv 설치가 선행되어야 한다.
#     - `openmim`을 설치하여 `mim install`을 사용할 수 있도록 한다.
#     - `mim install`을 통해 `mmengine`과 `mmcv` 패키지를 설치한다.
# 2. pytorch와 cuda 호환성 체크
#     - `torch.cuda.is_available()`을 통해서 pytorch와 cuda가 서로 호환이 되는지 체크한다.
#     - `torch.cuda.current_device()`으로 torch에서 사용하는 cuda device를 알 수 있다.
#     - `torch.cuda.get_device_name(0)`으로 0번째 device의 이름을 알 수 있다.
# 3. mmdetection 파일 설치
#     - 기존 MMDetection 파일을 다운로드한 적이 있다면 깔끔하게 정리하는 차원에서 `!rmdir /s /q` 명령어를 통해 기존 파일 삭제 (Linux 기준, `!rm -rf`)
#     - `!git clone`을 통해 mmdetection 파일을 가져온다.
#     - `!cd mmdetection & pip install -e .` 명령어를 통해서 필요한 패키지를 모두 설치한다. (Linux 기준, `!cd mmdetection; pip install -e .`)
#     - `!cd ,`로 현재 위치 확인 (/content) (Linux 기준, `!pwd`)
#     - 생성된 mmdetection 디렉토리로 이동한 후에 setup.py를 통해서 install 진행하기 위해서 `!cd mmdetection & python setup.py install` 실행 (Linux 기준, `!cd mmdetection; python setup.py install`)
# 
# ※ `!`, `%` 차이
# - `!`는 일회성.
# - `%`로 실행하면 이후 코드에도 해당 명령어가 적용된 상태로 다음 명령어를 수행.
# - `!` 예. `!cd direct`하면 디렉토리가 변경되지 않은 상태로 다음 명령어를 수행.
# - `%` 예. `%cd direct`하면 디렉토리가 변경된 상태로 다음 명령어를 수행.

# In[1]:


# mmcv 설치
get_ipython().run_line_magic('pip', 'install torch==1.12.1+cu113 torchvision==0.13.1+cu113 torchaudio==0.12.1 --extra-index-url https://download.pytorch.org/whl/cu113')
get_ipython().run_line_magic('pip', 'install -U openmim')
get_ipython().system('mim install "mmengine>=0.7.0"')
get_ipython().system('mim install "mmcv>=2.0.0rc4"')


# In[2]:


# pytorch와 cuda 호환성 체크
import torch
print(torch.cuda.is_available())
print(torch.cuda.current_device())
print(torch.cuda.get_device_name(0))


# In[3]:


# mmdetection 파일 설치
get_ipython().system('rmdir /s /q mmdetection')
# !rm -rf mmdetection
get_ipython().system('git clone https://github.com/open-mmlab/mmdetection.git')

get_ipython().system('cd mmdetection & pip install -e .')
# !cd mmdetection; pip install -e .


# In[4]:


# mmdetection 파일 설치 - setup.py 실행
get_ipython().system('cd ,')
# !pwd
get_ipython().system('cd mmdetection & python setup.py install')
# !cd mmdetection; python setup.py install


# ### pretrained 모델 / config 파일 세팅
# 1. pretrained 모델 링크 주소 확인
#     - [github](https://github.com/open-mmlab/mmdetection) 페이지에서 `configs` 폴더에 다양한 인공지능 알고리즘이 존재한다.
#     - 예제에서는 `faster-rcnn`을 기준으로 진행한다.
# 2. config 파일 확인
#     - 선택한 model 좌측에 위치해 있는 `config` 파일을 선택한다.
# 3. model 파일 코랩에서 다운로드
#     - 먼저 model 파일을 저장할 폴더(checkpoints)를 생성한다.
#     - `!wget` 명령어를 통해 모델을 다운로드한다.
#     - 다운로드 완료 후에는 `config` 파일과 다운로드한 `.pth` 파일 경로를 각 변수에 저장한다.
#     - `-c`에 넣을 값이 앞서 복사한 링크 주소이고, `-O`는 어느 경로에 어떤 이름으로 파일을 저장할지 적어준 것이다.

# In[5]:


# model 파일 코랩에서 다운로드 - 모델 파일 다운로드
get_ipython().system('cd mmdetection & mkdir checkpoints &          wget -c https://download.openmmlab.com/mmdetection/v2.0/faster_rcnn/faster_rcnn_r50_caffe_fpn_mstrain_3x_coco/faster_rcnn_r50_caffe_fpn_mstrain_3x_coco_20210526_095054-1f77628b.pth              -O checkpoints/faster_rcnn_r50_caffe_fpn_mstrain_3x_coco_20210526_095054-1f77628b.pth')
# !cd mmdetection & mkdir checkpoints & \
#         mim download mmdet --config rtmdet_tiny_8xb32-300e_coco --dest ./checkpoints

# !cd mmdetection; mkdir checkpoints; \
#         wget -c https://download.openmmlab.com/mmdetection/v2.0/faster_rcnn/faster_rcnn_r50_caffe_fpn_mstrain_3x_coco/faster_rcnn_r50_caffe_fpn_mstrain_3x_coco_20210526_095054-1f77628b.pth \
#             -O checkpoints/faster_rcnn_r50_caffe_fpn_mstrain_3x_coco_20210526_095054-1f77628b.pth
