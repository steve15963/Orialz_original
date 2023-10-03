package com.orialz.ffmpegserver.video.Schedul;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.orialz.ffmpegserver.video.Service.FindKeywordService;
import com.orialz.ffmpegserver.video.Vo.Entity.Job;
import com.orialz.ffmpegserver.video.Repository.JobRepository;
import com.orialz.ffmpegserver.video.hadoop.HadoopControl;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class scj {
	JobRepository jobRepository;
	HadoopControl hadoopControl;
	FindKeywordService findKeywordService;
	@Scheduled(fixedDelay = 1)
	public void testSC() throws InterruptedException, IOException {
		//하는 작업이 없어 여유롭다면.
		//DB에서 작업을 가져오고.
		Optional<Job> nextJob = jobRepository.findFirstByOrderByIdAsc();
		//새 작업이 있다면 시작
		if(nextJob.isPresent()){
			System.out.println("작업시작");
			Job nowJob = nextJob.get();
			ProcessBuilder processBuilder = new ProcessBuilder("pwd");
			Process start = processBuilder.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(start.getInputStream()));

			String line;
			while((line = br.readLine()) != null){
				System.out.println(line);
			}
			//hadoopControl.HDFSUploadInputTxt(nowJob);
			// hadoopControl.MapreduceRunJob(nowJob);
			///hadoopControl.HDFSDownloadOutputTxt(nowJob);
			//findKeywordService.pushfile(nowJob);
			//파일 삭제 완료 이후 해당 작업 삭제.
			jobRepository.delete(nowJob);
			System.out.println("작업끝");
		}
		else{
			System.out.println("작업 없음");
			//너무 많은 스케줄링 방지.
			Thread.sleep(1000);
		}
	}
}
