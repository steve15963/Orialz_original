package com.orialz.backend.streaming.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.orialz.backend.streaming.controller.dto.UploadResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import com.orialz.backend.streaming.domain.entity.CategoryStatus;
import com.orialz.backend.streaming.service.StreamingService;
import com.orialz.backend.streaming.service.VideoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/hls")
public class StreamingController {


    private final StreamingService streamingService;
    private final VideoService videoService;

    @Value("${video.path}")
    private String rootPath;

    // HLS 스트리밍

    @ResponseBody
    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello");
    }

    @ResponseBody
    @GetMapping("/streaming/{videoId}/{fileName}")
    public ResponseEntity<Resource> hlsPlay(@PathVariable Long videoId , @PathVariable String fileName) throws FileNotFoundException, NoSuchAlgorithmException {
        log.info("hls!!!!");
        File file = streamingService.videoPlay(videoId,fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/x-mpegURL"))
                .body(resource);
    }

    @GetMapping("/{text}")
    public ResponseEntity<String> webClient(@PathVariable String text){
        WebClient webClient =
                WebClient
                        .builder()
                        .baseUrl("http://localhost:8081")
                        .build();

        // api 요청
        Map<String, Object> response =
                webClient
                        .get()
                        .uri("/split/{text}",text)
                        .retrieve()
                        .bodyToMono(Map.class)
                        .block();

        // 결과 확인
        log.info(response.toString());
        return ResponseEntity.ok(response.toString());

    }




    // 분할 업로드
    // 유저 정보 헤더에 담기 나중에 수정
    @ResponseBody
    @PostMapping("/upload/chunk")
    public ResponseEntity<UploadResponseDto> upload(@RequestParam("chunk") MultipartFile file,
                                         @RequestParam("totalChunkNum") Integer totalChunkNum,
                                         @RequestParam("fileName") String fileName,
                                         @RequestParam("chunkNum") Integer chunkNum,
                                         @RequestParam("memberId") Long memberId,
                                         @RequestParam(name = "content", required = false) String content, 
                                          @RequestParam(name = "title", required = false) String title,
                                          @RequestParam(name = "category", required = false) CategoryStatus category
    ) throws IOException, ExecutionException, InterruptedException, NoSuchAlgorithmException {

        log.info(memberId.toString());
//        //업로드 성공 여부 반환
        Future<UploadResponseDto> future = streamingService.chunkUpload(file,fileName,chunkNum,totalChunkNum,memberId,content,title,category);
//        Boolean res = future.get();
//        Boolean res = true;
//        String res = videoService.sendFormData(file,totalChunkNum,fileName,chunkNum);
        log.info("여기다ㅏㅏㅏㅏ");
        return ResponseEntity.ok().body(future.get());
    }




    // 그냥 영상 송출
    @ResponseBody
    @GetMapping(value = "/streaming")
    public ResponseEntity<Resource> videoPlay() throws Exception{
        log.info("왔당");
        Resource resource = new FileSystemResource(rootPath+"/1/f9fd461bb47c46a5526f00c295642739df83d9064693ba3b2150fcda27f40af4/hotel.mp4");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
//                .contentLength(resource.contentLength())
                .body(resource);
    }

//    @ResponseBody
//    @GetMapping("/hash")
//    public ResponseEntity<String> hashTest() throws NoSuchAlgorithmException {;
//        long id = 1;
//        LocalDateTime date = LocalDateTime.of(2023,9,12,10,14,20);
//        log.info("date: "+date);
//        String hash = streamingService.hashingPath(id,date);
//        return ResponseEntity.ok()
//                .body(hash);
//    }


//    @ResponseBody
//    @GetMapping("/async")
//    public ResponseEntity<String> asyncTest() throws InterruptedException, ExecutionException {
//        Future<String> future = streamingService.asyncTest();
//        streamingService.asyncTest();
//         streamingService.asyncTest();
//        String res = future.get();
//
//            log.info(res);
//        return ResponseEntity.ok()
//                .body("success");
//    }
}
