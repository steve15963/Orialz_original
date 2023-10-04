package com.example.split.video.service;


import com.example.split.video.domain.CategoryStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.apache.commons.lang3.math.Fraction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoService {
    private final FFmpeg ffmpeg;
    private final FFprobe ffprobe;
    @Value("${video.path}")
    private String rootPath;
    public Future<Boolean> chunkUpload(MultipartFile file, String fileName,int chunkNumber, int totalChunkNum,Long userId , String content,String title, CategoryStatus category) throws IOException, NoSuchAlgorithmException {

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
                String videoPath = path + "/" + fileName;

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
                File fullFile = new File(videoPath + "/"+fileName);

                log.info("File uploaded successfully");

//                putS3(fullFile,fileName);
                //Frame 분할
                splitFrame(videoPath,fileName);
                //hdfs 전송용 text파일 생성
                createTextFile(userId,videoPath,fileName);
                //HLS 변환
//                convertToHls(videoPath,fileName);
                //HLS경로 재생 Path로 설정
//                nowVideo.setPath(videoPath+"/hls");
                //썸네일 설정
//                streaming.getThumbnail(videoPath+"/"+fileName,videoPath+"/"+"thumbnail.jpg");
//                nowVideo.setThumbnail("/thumb/"+userId+"/"+hashing+"/"+"thumbnail.jpg");
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
        return sha256Hash;
    }

    public void createTextFile(Long userId, String middlePath,String fileName) throws IOException {

        String output = "/user/hadoop/"+userId+"/"+middlePath+"/output";
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
    }

    public void splitFrame(String hashPath,String fileName) throws IOException {
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
    }
}
