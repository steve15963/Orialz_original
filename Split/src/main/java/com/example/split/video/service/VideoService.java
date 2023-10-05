package com.example.split.video.service;

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

import org.apache.commons.lang3.math.Fraction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

import com.example.split.video.Repository.JobRepository;
import com.example.split.video.Repository.VideoRepository;
import com.example.split.video.domain.Entity.Job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoService {
    private final FFmpeg ffmpeg;
    private final FFprobe ffprobe;

    private final VideoAsycService videoAsycService;
    private final JobRepository jobRepository;
    private final VideoRepository videoRepository;

    @Value("${video.path}")
    private String rootPath;
    public Future<Boolean> chunkUpload(MultipartFile file, String fileName,int chunkNumber, int totalChunkNum,Long userId , long videoId, LocalDateTime createAt, String hashing) throws IOException, NoSuchAlgorithmException {

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
                //해당 유저 찾기
//                String videoPath = path + "/" + fileName.split(".")[0];
                String videoPath = path + "/" + hashing;
                File videoFolder = new File(videoPath); // 폴더 위치
                if (!videoFolder.exists()) { // 사용자 파일이 없으면 생성
                    videoFolder.mkdirs();
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
                videoAsycService.splitFrame(videoPath,fileName);
                //hdfs 전송용 text파일 생성
                videoAsycService.createTextFile(hashing,userId,videoPath,fileName);

                jobRepository.save(
                    Job.builder()
                        .root(rootPath)
                        .member(""+userId)
                        .hash(hashing)
                        .video(videoRepository.findById(videoId).get())
                        .build()
                );

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


}
