package com.orialz.backend.streaming.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
public class VideoService {

    @Value("${video.path}")
    private String root;
    public String sendFormData(MultipartFile file,
                                     Integer totalChunkNum,
                                     String fileName,
                                     Integer chunkNum,
                               String hashPath,
                               Long memberId
    ) throws IOException {
        log.info("왔당");
        String dirPath = root + "/" + fileName; //임시 폴더 + 실제
        if (!file.isEmpty()) {
            File output = new File(dirPath); // 폴더 위치
            if (!output.exists()) { // 사용자 파일이 없으면 생성
                output.mkdirs();
            }
        }

            String path = dirPath + "/";
            String partFile = fileName + ".part" + chunkNum; //임시 저장 파일 이름
            Path filePath = Paths.get(path, partFile);
            log.info("filePath: " + String.valueOf(filePath));
            Files.write(filePath, file.getBytes());

            WebClient webClient = WebClient.builder()
                    .baseUrl("http://localhost:8081/split") // Replace with your server URL
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                    .build();



//            String response = "asdfadsf";
            // Prepare the form data
            MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
            formData.add("chunk2", file.getBytes());
            // Make the POST request using WebClient
            String response = webClient.post()
                    .uri("/upload/chunk")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData("chunk", formData)
                            .with("totalChunkNum",totalChunkNum)
                            .with("fileName",fileName)
                            .with("memberId",1L)
                            .with("chunkNum",chunkNum)
                            .with("hashPath",hashPath)
                    )
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return response;
        }
}
