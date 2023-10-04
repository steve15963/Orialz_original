package com.orialz.backend.streaming.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.orialz.backend.streaming.domain.entity.CategoryStatus;
import com.orialz.backend.streaming.domain.entity.Member;
import com.orialz.backend.streaming.domain.entity.Video;
import com.orialz.backend.streaming.domain.entity.VideoStatus;
import com.orialz.backend.streaming.domain.repository.MemberRepository;
import com.orialz.backend.streaming.domain.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;


@Service
@RequiredArgsConstructor
@Slf4j
public class StreamingService {

    private final FFmpeg ffmpeg;
    private final FFprobe ffprobe;
    private final VideoRepository videoRepository;
    private final MemberRepository memberRepository;
    private final StreamingAsyncService streaming;
    private final VideoService videoService;
//    private final AmazonS3Client amazonS3Client;

    @Value("${s3.bucket}")
    private String bucket;

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
                streaming.convertToHls(file.getOriginalFilename(),file.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return file.getOriginalFilename();
    }


    @Transactional
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
            String hashing = "";
            // 마지막 조각이 전송 됐을 경우
            if (chunkNumber == totalChunkNum - 1) {
                log.info(content);
                log.info(title);
                //해당 유저 찾기
                Member nowMember = memberRepository.findById(userId).orElse(null);
                Video video = Video.builder()
                        .content(content)
                        .member(nowMember)
                        .title(title)
                        .status(VideoStatus.WAIT)
                        .category(category)
                        .build();

                Video nowVideo = videoRepository.save(video);
                // 디비에 영상 삽입. status = 0;
                // 디비에서 가지고온 videoId , createAt
                hashing = hashingPath(nowVideo.getVideoId(), nowVideo.getCreatedAt()); // 4L 오류 발생해보기
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
                File fullFile = new File(videoPath + "/"+fileName);

                log.info("File uploaded successfully");

//                putS3(fullFile,fileName);
                //Frame 분할
                streaming.splitFrame(videoPath,fileName);
                //hdfs 전송용 text파일 생성
                streaming.createTextFile(hashing,userId,videoPath,fileName);
                //HLS 변환
                streaming.convertToHls(videoPath,fileName);
                //HLS경로 재생 Path로 설정
                nowVideo.setPath(videoPath+"/hls");
                //썸네일 설정
                streaming.getThumbnail(videoPath+"/"+fileName,videoPath+"/"+"thumbnail.jpg");
                nowVideo.setThumbnail("/thumb/"+userId+"/"+hashing+"/"+"thumbnail.jpg");

//                videoService.sendFormData(file,totalChunkNum,fileName,chunkNumber,hashing,userId);
                return CompletableFuture.completedFuture(true);
            }
            else{
//                videoService.sendFormData(file,totalChunkNum,fileName,chunkNumber,hashing,userId);
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


//    private String putS3(File uploadFile, String fileName) {
//
//        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
//        return amazonS3Client.getUrl(bucket, fileName).toString();
//    }

    public void asyncAct() throws InterruptedException {
        for(int i = 0; i<10;i++) {
            Thread.sleep(1);
            log.info("비동기처리중 " + i);
        }
    }


}
