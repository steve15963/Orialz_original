package com.orialz.backend.streaming.service;


import com.orialz.backend.video.domain.repository.MemberRepository;
import com.orialz.backend.video.domain.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.concurrent.TimeUnit.SECONDS;

@Component
@Slf4j
@RequiredArgsConstructor
public class StreamingAsyncService {
    private final FFmpeg ffmpeg;
    private final FFprobe ffprobe;
    @Async
    public void splitFrame(String hashPath,String fileName) throws IOException {
        // frame 폴더 생성
        // 해당 폴더에 frame 변환해서 넣기.
        String outputPath = hashPath + "/" + "output";
        File output = new File(outputPath); // 폴더 위치
        if (!output.exists()) { // 사용자 파일이 없으면 생성
            output.mkdirs();
        }
        try {
            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(hashPath+"/"+fileName) // 입력 동영상 파일 경로
                    .addOutput(outputPath +"/" +"frame%08d.png") // 썸네일 이미지 파일 경로
                    .setVideoFilter("fps=60")
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(new FFmpeg(), new FFprobe());
            executor.createJob(builder).run();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        log.info("success create frame");

    }

    @Async
    public void convertToHls(String hashPath , String filename) throws IOException {
        String path = hashPath+"/"+filename;
        String hlsPath = hashPath+"/hls";
        File output = new File(hlsPath);
        FFmpegProbeResult probeResult = ffprobe.probe(path);
        String audioCodec = "";
        // 원본 영상의 오디오 코덱 가지고 오기
        try{
            for (FFmpegStream stream : probeResult.getStreams()) {
                System.out.println(stream.codec_type);
                if(FFmpegStream.CodecType.AUDIO.equals(stream.codec_type)){
                    System.out.println("codecName: "+stream.codec_name);
                    audioCodec = stream.codec_name;

                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("no Audio Codec");
            throw e;
        }

        if (!output.exists()) {
            output.mkdirs();
        }

        try{
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(path) // 입력 소스
                .overrideOutputFiles(true)
                .addOutput(output.getAbsolutePath() + "/output.m3u8") // 출력 위치
                .setAudioCodec(audioCodec)
                .setFormat("hls")
                .disableSubtitle()
                .addExtraArgs("-hls_time", "5") // 5초
                .addExtraArgs("-hls_list_size", "0")
                .addExtraArgs("-hls_segment_filename", output.getAbsolutePath() + "/output_%08d.ts") // 청크 파일 이름
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder).run();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        log.info("success Convert HLS");
    }

    @Async
    public void getThumbnail(String inputPath,String outputPath) throws IOException {
        try {
            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(inputPath) // 입력 동영상 파일 경로
                    .addOutput(outputPath) // 썸네일 이미지 파일 경로
                    .setFrames(1) // 썸네일로 추출할 프레임 수 (1개)
                    .setVideoCodec("mjpeg") // 썸네일 이미지의 코덱 설정 (JPEG)
                    .setVideoFrameRate(1) // 초당 프레임 수 (1프레임)
                    .setStartOffset(1l,SECONDS)
                    .setVideoResolution(640, 360) // 썸네일 이미지의 해상도 설정
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(new FFmpeg(), new FFprobe());
            executor.createJob(builder).run();
            log.info("success create thumbnail");
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

    }
}
