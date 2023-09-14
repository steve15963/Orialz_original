package com.orialz.backend.streaming.controller;

import com.orialz.backend.streaming.service.StreamingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StreamingController {


    private final StreamingService streamingService;

    @Value("${video.path}")
    private String rootPath;

    // HLS 스트리밍
    @ResponseBody
    @GetMapping("/streaming/hls/{videoId}/{fileName}")
    public ResponseEntity<Resource> hlsPlay(@PathVariable Long videoId , @PathVariable String fileName) throws FileNotFoundException, NoSuchAlgorithmException {
        log.info("hls!!!!");
        File file = streamingService.videoPlay(videoId,fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/x-mpegURL"))
                .body(resource);
    }


    // 분할 업로드
    @ResponseBody
    @PostMapping("/upload/chunk")
    public ResponseEntity<Boolean> upload(@RequestParam("chunk") MultipartFile file,
                                         @RequestParam("totalChunkNum") Integer totalChunkNum,
                                         @RequestParam("fileName") String fileName,
                                         @RequestParam("chunkNum") Integer chunkNum,
                                         @RequestParam(name = "content", required = false) String content,
                                          @RequestParam(name = "content", required = false) String title
    ) throws IOException, ExecutionException, InterruptedException, NoSuchAlgorithmException {

//        log.info("con: "+file);
        //올린 영상 제목 출력
        Future<Boolean> future = streamingService.chunkUpload(file,fileName,chunkNum,totalChunkNum,1L,content,title);
        Future<Boolean> future1 = streamingService.chunkUpload(file,fileName,chunkNum,totalChunkNum,2L,content,title);
        Future<Boolean> future2 = streamingService.chunkUpload(file,fileName,chunkNum,totalChunkNum,3L,content,title);
        Boolean res = future.get();
        return ResponseEntity.ok().body(res);
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

    @ResponseBody
    @GetMapping("/hash")
    public ResponseEntity<String> hashTest() throws NoSuchAlgorithmException {;
        long id = 1;
        LocalDateTime date = LocalDateTime.of(2023,9,12,10,14,20);
        log.info("date: "+date);
        String hash = streamingService.hashingPath(id,date);
        return ResponseEntity.ok()
                .body(hash);
    }


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
