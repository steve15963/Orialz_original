package com.example.split.video.controller.dto;

import com.example.split.video.controller.dto.response.TestDto;
import com.example.split.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequiredArgsConstructor
@RequestMapping("/split")
@Slf4j
public class VideoController {

    private final VideoService videoService;

    @Value("${video.path}")
    private String root;

    @GetMapping("/{text}")
    public ResponseEntity<TestDto> hello(@PathVariable String text){
        return ResponseEntity.ok(new TestDto(text+" 왔다감"));
    }

    @ResponseBody
    @PostMapping("/upload/chunk")
    public ResponseEntity<String> upload(@RequestParam("chunk") byte[] file,
                                         @RequestParam("totalChunkNum") Integer totalChunkNum,
                                         @RequestParam("fileName") String fileName,
                                         @RequestParam("chunkNum") Integer chunkNum,
                                         @RequestParam("memberId") Long memberId,
                                         @RequestParam("hashPath") String hashPath
                                         ) throws IOException, ExecutionException, InterruptedException, NoSuchAlgorithmException {
        ByteArrayResource temp = new ByteArrayResource(file);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(file.length)) {

            log.info("왔당");
            // byte[] data size each
//            for (int i = 0; i < file.length;i++) {
//                // byte[] read
//                // byte 하나하나 읽으면서 ByteArrayOutpurStream 에 저장
//                    byteArrayOutputStream.write(file[i]);
//                    byteArrayOutputStream.flush();
//            }

//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
//            MultipartFile multipartFile = new MockMultipartFile(fileName, byteArrayInputStream.readAllBytes());
//            String filePath = root + "/"+memberId+"/"+hashPath+"/";
            String filePath = root + "/" + memberId;
            File nFile = new File(filePath+fileName);

            FileUtils.writeByteArrayToFile(nFile, file);

            MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, "video/mp4",file);

            // !-- MockMultipartFile 은 원래 테스트용으로 만들어졌으나 MultipartFile 객체를 생성하기 위해 사용하기도 한다. --!
            //업로드 성공 여부 반환
            Future<Boolean> future = videoService.chunkUpload(multipartFile,fileName,chunkNum,totalChunkNum,memberId,hashPath);
        }

//        Boolean res = future.get();

//        log.info(file.getOriginalFilename() + " "+ chunkNum);

        return ResponseEntity.ok().body("hello");
    }

}
