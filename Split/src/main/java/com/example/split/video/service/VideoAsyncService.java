package com.example.split.video.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.apache.commons.lang3.math.Fraction;
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


    @Async
    public Future<Boolean> createTextFile(String hashing,Long userId, String middlePath,String fileName) throws IOException {
        String output = "/user/hadoop/"+userId+"/"+hashing+"/output";
        //해당 동영상의 초당 frame 가져오기
        FFmpegProbeResult probeResult = ffprobe.probe(middlePath + "/" + fileName);
        Fraction fps = probeResult.getStreams().get(0).avg_frame_rate;
//        log.info(fps.toString());
        double rate = (double)fps.getNumerator() / fps.getDenominator();
        double time = 1 / rate;
        long nFps = probeResult.getStreams().get(0).nb_frames;
        File timeTextFile = new File(middlePath+"/"+"frame_timeStamp.txt");
        if (!timeTextFile.exists()) {
            timeTextFile.createNewFile();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(timeTextFile));

        for(int i = 0; i <= nFps;i++){
            String temp = String.format("%f %s/frame%08d.png\n",time * i ,output,i);
            bw.write(temp);
        }
        bw.close();
        return CompletableFuture.completedFuture(true);
    }

    @Async
    public Future<Boolean> splitFrame(String hashPath, String fileName) throws IOException {
        // frame 폴더 생성
        // 해당 폴더에 frame 변환해서 넣기.
        String outputPath = hashPath + "/" + "output";
        File output = new File(outputPath); // 폴더 위치
        if (!output.exists()) { // 사용자 파일이 없으면 생성
            output.mkdirs();
        }

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(hashPath + "/" + fileName) // 입력 동영상 파일 경로
                .addOutput(outputPath + "/" + "frame%08d.png") // 썸네일 이미지 파일 경로
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder).run();

        log.info("success create frame");
        return CompletableFuture.completedFuture(true);
    }
}
