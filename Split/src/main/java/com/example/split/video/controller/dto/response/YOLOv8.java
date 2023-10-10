package com.example.split.video.controller.dto.response;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class YOLOv8 {
	public String img_name;
	public ArrayList<Integer> orig_shape;
	public ArrayList<Integer> cls;
	public ArrayList<Double> conf;
	public ArrayList<ArrayList<Double>> xywhn;
}
