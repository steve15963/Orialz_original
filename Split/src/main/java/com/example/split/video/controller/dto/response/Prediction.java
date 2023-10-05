package com.example.split.video.controller.dto.response;

import java.util.ArrayList;

public class Prediction{
	public ArrayList<Integer> labels;
	public ArrayList<Double> scores;
	public ArrayList<ArrayList<Double>> bboxes;

	public ArrayList<Integer> getLabels() {
		return labels;
	}

	public void setLabels(ArrayList<Integer> labels) {
		this.labels = labels;
	}

	public ArrayList<Double> getScores() {
		return scores;
	}

	public void setScores(ArrayList<Double> scores) {
		this.scores = scores;
	}

	public ArrayList<ArrayList<Double>> getBboxes() {
		return bboxes;
	}

	public void setBboxes(ArrayList<ArrayList<Double>> bboxes) {
		this.bboxes = bboxes;
	}
}

