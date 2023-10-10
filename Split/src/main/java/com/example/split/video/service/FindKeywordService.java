package com.example.split.video.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.springframework.stereotype.Service;

import com.example.split.video.Repository.FindKeywordRepository;
import com.example.split.video.Repository.JobRepository;
import com.example.split.video.controller.dto.response.AIData;
import com.example.split.video.controller.dto.response.Prediction;
import com.example.split.video.controller.dto.response.YOLOv8;
import com.example.split.video.domain.Entity.FindKeyword;
import com.example.split.video.domain.Entity.Job;
import com.example.split.video.domain.Entity.Video;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FindKeywordService {
	private static final ObjectMapper om = new ObjectMapper();
	private JobRepository jobRepository;
	private FindKeywordRepository findKeywordRepository;
	public void pushfile(Job job) throws IOException {
		String localRootPath = job.getRoot();
		String member = job.getMember();
		String hash = job.getHash();
		Video video = job.getVideo();

		BufferedReader br =new BufferedReader( new FileReader(localRootPath + "/" + member +"/" + hash + "/json/json.txt"));
		String line;
		while((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			String time_string = st.nextToken();
			double time = Double.parseDouble(time_string);
			String json = line.substring(time_string.length() + 1);
			System.out.println("time : " + time);
			System.out.println("json : " + json);
			pushJson(time,json,video);
		}
	}
	public void pushJson(double key, String json,Video video) throws JsonProcessingException {
		//AIData aiData = om.readValue(json, AIData.class);
		//Prediction prediction = aiData.getPredictions().get(0);
		//ArrayList<Integer> labels = prediction.getLabels();
		//ArrayList<ArrayList<Double>> bboxes = prediction.getBboxes();
		//ArrayList<Double> scores = prediction.getScores();
		//int size = labels.size();
		YOLOv8 yolOv8 = om.readValue(json, YOLOv8.class);
		ArrayList<ArrayList<Double>> xywhn = yolOv8.getXywhn();
		ArrayList<Integer> cls = yolOv8.getCls();
		ArrayList<Double> conf = yolOv8.getConf();
		int size = cls.size();
		for(int i = 0; i < size; i++){
			//Integer label = labels.get(i);
			//ArrayList<Double> position = bboxes.get(i);
			//Double score = scores.get(i);
			//video_keywords_id, video_id, times, minx, miny, maxx, maxy, score
			//findKeywordRepository.save(
				//FindKeyword.builder()
					//.keyword_id(label)
					//.video(video)
					//.time(key)
					//.minX(position.get(0).intValue())
					//.minY(position.get(1).intValue())
					//.maxX(position.get(2).intValue())
					//.maxY(position.get(3).intValue())
					//.score(score)
					//.build()
			//);
			ArrayList<Double> position = xywhn.get(i);
			findKeywordRepository.save(
				FindKeyword.builder()
					.keyword_id(cls.get(i))
					.video(video)
					.time(key)
					.minX(position.get(0))
					.minY(position.get(1))
					.W(position.get(2))
					.H(position.get(3))
					//.maxX(position.get(2))
					//.maxY(position.get(3))
					.score(1)
					.build()
			);

		}

	}
}
