package com.orialz.backend.streaming.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import java.util.Map;

@Service
@Slf4j
public class VideoService {
    public String sendFormData(MultipartFile file,
                                     Integer totalChunkNum,
                                     String fileName,
                                     Integer chunkNum) throws IOException {
        log.info("왔당");
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8081/split") // Replace with your server URL
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .build();

        // Prepare the form data
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("chunk", new ByteArrayResource(file.getBytes()));
        formData.add("totalChunkNum", totalChunkNum);
        formData.add("fileName", fileName);
        formData.add("chunkNum", chunkNum);

        // Make the POST request using WebClient
        String response = webClient.post()
                .uri("/upload/chunk")
                .syncBody(formData)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return response;
    }
}
