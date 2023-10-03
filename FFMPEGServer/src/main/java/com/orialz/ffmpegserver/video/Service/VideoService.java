package com.orialz.ffmpegserver.video.Service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.orialz.ffmpegserver.video.Repository.JobRepository;
import com.orialz.ffmpegserver.video.Repository.VideoRepository;
import com.orialz.ffmpegserver.video.Vo.Entity.Job;
import com.orialz.ffmpegserver.video.Vo.Entity.Video;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VideoService {
	JobRepository jobRepository;
	VideoRepository videoRepository;
	public void test(Long vId) throws IOException, InterruptedException, ClassNotFoundException {
		Optional<Video> byId = videoRepository.findById(vId);
		byId.ifPresent(video -> jobRepository.save(Job.builder()
			.video(video)
			.localInputPath("/user/hadoop/" + vId + "/AiInput.txt")
			.remoteInputPath("/user/hadoop/" + vId + "/AiInput.txt")
			.remoteOutputPath("/user/hadoop/" + vId + "/json")
			.localOutputPath("output.json")
			.build()
		));
	}


}
