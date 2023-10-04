package com.orialz.ffmpegserver.video.Service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.orialz.ffmpegserver.video.Repository.JobRepository;
import com.orialz.ffmpegserver.video.Repository.VideoRepository;
import com.orialz.ffmpegserver.video.Vo.Entity.Job;
import com.orialz.ffmpegserver.video.Vo.Entity.Video;
import com.orialz.ffmpegserver.video.hadoop.HadoopControl;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VideoService {
	JobRepository jobRepository;
	VideoRepository videoRepository;
	HadoopControl hadoopControl;
	public void test(Long vId,String li, String ri, String ro, String lo) throws IOException, InterruptedException, ClassNotFoundException {
		Optional<Video> byId = videoRepository.findById(vId);
		byId.ifPresent(video -> jobRepository.save(Job.builder()
			.video(video)
			.localInputPath(li)
			.remoteInputPath(ri)
			.remoteOutputPath(ro)
			.localOutputPath(lo)
			.build()
		));
	}
	public void addJob(Long vId) throws IOException, InterruptedException, ClassNotFoundException {
		Optional<Video> byId = videoRepository.findById(vId);
		byId.ifPresent(video -> jobRepository.save(Job.builder()
			.video(video)
			.localInputPath("" + vId + "/AiInput.txt")
			.remoteInputPath("/user/hadoop/" + vId + "/AiInput.txt")
			.remoteOutputPath("/user/hadoop/" + vId + "/json/part-r-00000")
			.localOutputPath("" + vId +"output.txt")
			.build()
		));
	}

	public void upload() throws IOException {
		String uploadUrl = hadoopControl.HDFSUploadInputTxt(
			Job.builder()
				.localInputPath("inputTest.txt")
				.remoteInputPath("/user/hadoop/inputTest.txt")
				.build()
		);
	}
	public void download() throws IOException {
		boolean b = hadoopControl.HDFSDownloadOutputTxt(
			Job.builder()
				.localOutputPath("download.txt")
				.remoteOutputPath("/user/hadoop/inputTest.txt")
				.build()
		);
	}
}
