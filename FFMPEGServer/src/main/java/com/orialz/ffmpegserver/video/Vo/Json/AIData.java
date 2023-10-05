package com.orialz.ffmpegserver.video.Vo.Json;

import java.util.ArrayList;

public class AIData {
	public ArrayList<Prediction> predictions;
	public ArrayList<Object> visualization;

	public ArrayList<Prediction> getPredictions() {
		return predictions;
	}

	public void setPredictions(ArrayList<Prediction> predictions) {
		this.predictions = predictions;
	}

	public ArrayList<Object> getVisualization() {
		return visualization;
	}

	public void setVisualization(ArrayList<Object> visualization) {
		this.visualization = visualization;
	}
}

