### pip install requirements ###
pip install ultralytics
pip install onnx

### inference ###
ipython train_practice

### train ###
ipython train_practice

### settings ###
Read setting_location file.
Set datasets_dir, weights_dir, runs_dir in settings file.

### result ###
{
	"img_name": "pistol_000.jpg",
	"orig_shape": [168, 300],
	"cls": [2],
	"conf": [0.9141114354133606],
	"xywhn": [[0.06662493944168091, 0.030729562044143677, 0.8673242330551147, 0.9334232211112976]]
}
img_name: 파일 이름
orig_shape: 이미지의 가로, 세로 크기
cls: 클래스(라벨링)
conf: 확률
xywhn: 객체의 위치