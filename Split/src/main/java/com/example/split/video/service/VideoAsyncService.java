package com.example.split.video.service;

import com.example.split.video.Repository.JobRepository;
import com.example.split.video.Repository.VideoRepository;
import com.example.split.video.domain.Entity.Job;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.apache.commons.lang3.math.Fraction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Component
@RequiredArgsConstructor
@Slf4j
public class VideoAsyncService {

    private final FFmpeg ffmpeg;
    private final FFprobe ffprobe;
    private final JobRepository jobRepository;
    @Value("${video.path}")
    private String rootPath;
    private final VideoRepository videoRepository;


    public void createTextFile(String hashing,Long userId, String middlePath,String fileName) throws IOException {
        String output = "/user/hadoop/video/"+userId+"/"+hashing+"/output";
        //해당 동영상의 초당 frame 가져오기
        FFmpegProbeResult probeResult = ffprobe.probe(middlePath + "/" + fileName);
        Fraction fps = probeResult.getStreams().get(0).avg_frame_rate;
//        log.info(fps.toString());
        double rate = (double)fps.getNumerator() / fps.getDenominator();
        double time = 1 / rate;

        File timeTextFile = new File(middlePath+"/"+"frame_timeStamp.txt");
        if (!timeTextFile.exists()) {
            timeTextFile.createNewFile();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(timeTextFile));

        File imgDiretory = new File("/user/hadoop/video/"+userId+"/"+hashing+"/output");

        File[] files = imgDiretory.listFiles();

        long nFps = files.length;

        for(int i = 0; i < nFps;i++){
            String temp = String.format("%f %s/%s\n",time * (i+1) ,output,files[i].getName());
            bw.write(temp);
        }
        bw.close();
    }

    @Async
    public void asyncFunc(String videoPath, String fileName,String hashing , Long userId, Long videoId) throws IOException {
        // frame 폴더 생성
        // 해당 폴더에 frame 변환해서 넣기.
        String outputPath = videoPath + "/" + "output";
        File output = new File(outputPath); // 폴더 위치
        if (!output.exists()) { // 사용자 파일이 없으면 생성
            output.mkdirs();
        }

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(videoPath + "/" + fileName) // 입력 동영상 파일 경로
                .addOutput(outputPath + "/" + "frame%08d.png") // 썸네일 이미지 파일 경로
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder).run();

        log.info("success create frame");
        createTextFile(hashing,userId,videoPath,fileName);
        log.info("success create Text File");
        jobRepository.save(
                Job.builder()
                        .root(rootPath)
                        .member(""+userId)
                        .hash(hashing)
                        .title(fileName)
                        .video(videoRepository.findById(videoId).get())
                        .build()
        );
    }
}
