package com.orialz.backend.streaming.service;


import com.orialz.backend.video.domain.entity.Member;
import com.orialz.backend.video.domain.entity.Video;
import com.orialz.backend.video.domain.entity.VideoStatus;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static java.util.concurrent.TimeUnit.SECONDS;


@Service
@RequiredArgsConstructor
@Slf4j
public class StreamingService {

    private final FFmpeg ffmpeg;
    private final FFprobe ffprobe;
    private final VideoRepository videoRepository;
    private final MemberRepository memberRepository;

    @Value("${video.path}")
    private String rootPath;
    public File videoPlay(Long videoId,String fileName) throws NoSuchAlgorithmException {
        // fileName에 ts파일 이름이 들어옴
        // 디비에서 해당 영상의 주소 가져와서 플레이
        Video nowVideo = videoRepository.findById(videoId).orElse(null);

        String path = nowVideo.getPath();
        return new File(path+"/"+fileName);
    }

    // 그냥 업로드
    public String upload(MultipartFile file){

        if (!file.isEmpty()) {

            // 파일을 저장할 path
            log.info("fileName: "+file.getOriginalFilename());
            // 해당 path 에 파일의 스트림 데이터를 저장
            try {
                File f1 = new File(rootPath+"/"+file.getOriginalFilename());
                file.transferTo(f1);// 파일 옮기기
                convertToHls(file.getOriginalFilename(),file.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return file.getOriginalFilename();
    }


    @Async
    @Transactional
    public Future<Boolean> chunkUpload(MultipartFile file, String fileName,int chunkNumber, int totalChunkNum,Long userId , String content,String title) throws IOException, NoSuchAlgorithmException {

        if (!file.isEmpty()) {
            String path = rootPath + "/" + userId; //임시 폴더 + 실제
            File output = new File(path); // 폴더 위치
            if (!output.exists()) { // 사용자 파일이 없으면 생성
                output.mkdirs();
            }

            String partFile = fileName + ".part" + chunkNumber; //임시 저장 파일 이름
            Path filePath = Paths.get(path, partFile);
            log.info("filePath: "+String.valueOf(filePath));
            Files.write(filePath, file.getBytes());

            // 마지막 조각이 전송 됐을 경우
            if (chunkNumber == totalChunkNum - 1) {
                log.info(content);
                log.info(title);
                //해당 유저 찾기
                Member nowMember = memberRepository.findById(userId).orElse(null);
                // 디비에 영상 삽입. status = 0;
                // 디비에서 가지고온 videoId , createAt
                Video video = Video.builder()
                        .content(content)
                        .member(nowMember)
                        .title(title)
                        .status(VideoStatus.WAIT)
                        .build();

                Video nowVideo = videoRepository.save(video);
                String hashing = hashingPath(nowVideo.getVideoId(), nowVideo.getCreatedAt());
                String videoPath = path + "/"+hashing;

                File hashFolder = new File(videoPath); // 폴더 위치
                if (!hashFolder.exists()) { // 사용자 파일이 없으면 생성
                    hashFolder.mkdirs();
                }

                Path outputFile = Paths.get(videoPath, fileName);
                Files.createFile(outputFile);

                // 임시 파일들을 하나로 합침
                for (int i = 0; i < totalChunkNum; i++) {
                    Path chunkFile = Paths.get(path, fileName + ".part" + i);
                    Files.write(outputFile, Files.readAllBytes(chunkFile), StandardOpenOption.APPEND);
                    // 합친 후 삭제
                    Files.delete(chunkFile);
                }

                log.info("File uploaded successfully");
                //HLS 변환
                convertToHls(videoPath,fileName);
                //HLS경로 재생 Path로 설정
                nowVideo.setPath(videoPath+"/hls");
                //썸네일 설정
                getThumbnail(videoPath+"/"+fileName,videoPath+"/"+"thumbnail.jpg");
                nowVideo.setThumbnail(videoPath+"/"+"thumbnail.jpg");
                return CompletableFuture.completedFuture(true);
            }
            else{
                log.info("Not Last");
                return CompletableFuture.completedFuture(true);
            }
        }
        else{
            log.info("File Not Exist");
            return CompletableFuture.completedFuture(false);
        }
    }


    public void convertToHls(String hashPath , String filename) throws IOException {
        String path = hashPath+"/"+filename;
        String hlsPath = hashPath+"/hls";
        File output = new File(hlsPath);
        FFmpegProbeResult probeResult = ffprobe.probe(path);
        String audioCodec = "";
        // 원본 영상의 오디오 코덱 가지고 오기
        for (FFmpegStream stream : probeResult.getStreams()) {
            System.out.println(stream.codec_type);
            if(FFmpegStream.CodecType.AUDIO.equals(stream.codec_type)){
                System.out.println("codecName: "+stream.codec_name);
                audioCodec = stream.codec_name;

            }
            
        }
        if (!output.exists()) {
            output.mkdirs();
        }

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
        log.info("success Convert HLS");
    }


    public String hashingPath(Long userId, LocalDateTime createAt) throws NoSuchAlgorithmException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        // LocalDateTime을 문자열로 포맷
        String formattedDateTime = createAt.format(formatter);

        String path = userId + "_"+formattedDateTime;
        log.info("path: "+path);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(path.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }

        String sha256Hash = hexString.toString();
//        log.info("SHA-256 Hash: " + sha256Hash);
        return sha256Hash;
    }

    public void getThumbnail(String inputPath,String outputPath){
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
        }

    }



//    @Async
//    public Future<String> asyncTest() throws InterruptedException {
//        for(int i = 0; i < 10; i++){
//            log.info("i"+i);
//            if(i == 9){
//                asyncAct();
//                return CompletableFuture.completedFuture("now");
//            }
//        }
//        return CompletableFuture.completedFuture("success");
//    }
//
//
//
//    public void asyncAct() throws InterruptedException {
//        for(int i = 0; i<10;i++) {
//            Thread.sleep(1);
//            log.info("비동기처리중 " + i);
//        }
//    }


}
