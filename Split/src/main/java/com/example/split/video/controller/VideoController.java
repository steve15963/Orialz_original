package com.example.split.video.controller;

import com.example.split.video.controller.dto.response.TestDto;
import com.example.split.video.domain.CategoryStatus;
import com.example.split.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequiredArgsConstructor
@RequestMapping("/split")
@Slf4j
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/{text}")
    public ResponseEntity<TestDto> hello(@PathVariable String text){
        return ResponseEntity.ok(new TestDto(text+" 왔다감"));
    }

    @ResponseBody
    @PostMapping("/upload/chunk")
    public ResponseEntity<Boolean> upload(@RequestParam("chunk") MultipartFile file,
                                          @RequestParam("totalChunkNum") Integer totalChunkNum,
                                          @RequestParam("fileName") String fileName,
                                          @RequestParam("chunkNum") Integer chunkNum,
                                          @RequestParam(name = "content", required = false) String content,
                                          @RequestParam(name = "title", required = false) String title,
                                          @RequestParam CategoryStatus category
    ) throws IOException, ExecutionException, InterruptedException, NoSuchAlgorithmException {
//        //업로드 성공 여부 반환
        Future<Boolean> future = videoService.chunkUpload(file,fileName,chunkNum,totalChunkNum,1L,content,title,category);
//        Boolean res = future.get();
//        Boolean res = true;
//        String res = videoService.sendFormData(file,totalChunkNum,fileName,chunkNum);

        return ResponseEntity.ok().body(future.get());
    }

}
