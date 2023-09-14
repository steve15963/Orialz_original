package com.orialz.backend.video.controller;


import com.orialz.backend.video.dto.response.VideoListResponseDto;
import com.orialz.backend.video.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/video")
public class VideoController {

    private final VideoService videoService;

    @GetMapping()
    public ResponseEntity<List<VideoListResponseDto>> mainList(){
        List<VideoListResponseDto> videos = videoService.mainList();
        return ResponseEntity.ok(videos);
    }


}
