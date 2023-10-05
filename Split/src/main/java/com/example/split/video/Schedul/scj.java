package com.example.split.video.Schedul;

import java.io.IOException;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.split.video.Repository.JobRepository;
import com.example.split.video.domain.Entity.Job;
import com.example.split.video.Repository.HadoopRepository;
import com.example.split.video.service.FindKeywordService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class scj {
	JobRepository jobRepository;
	HadoopRepository hadoopRepository;
	FindKeywordService findKeywordService;
	@Scheduled(fixedDelay = 1)
	public void testSC() throws InterruptedException, IOException {
		//하는 작업이 없어 여유롭다면.
		//DB에서 작업을 가져오고.
		Optional<Job> nextJob = jobRepository.findFirstByOrderByIdAsc();
		//새 작업이 있다면 시작
		if(nextJob.isPresent()){
			log.info("작업 시작.");

			Job nowJob = nextJob.get();

			log.info("HDFS 파일 업로드 시작.");
			String uploadUrl = hadoopRepository.HDFSUpload(nowJob);

			//반환값에 따라서 실패
			if(hadoopRepository.MapreduceRunJob(nowJob)){
				log.info("Hadoop 실행 실패");
				return;
			}
			hadoopRepository.HDFSDownload(nowJob);
			findKeywordService.pushfile(nowJob);
			//파일 삭제 완료 이후 해당 작업 삭제.
			jobRepository.delete(nowJob);
			log.info("작업 끝");
		}
		else{
			System.out.println("작업 없음");
			//너무 많은 스케줄링 방지.
			Thread.sleep(1000);
		}
	}
}
