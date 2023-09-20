package com.orialz.backend.video.controller;


import com.orialz.backend.video.dto.response.VideoListResponseDto;
import com.orialz.backend.video.dto.response.VideoViewResponseDto;
import com.orialz.backend.video.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{videoId}/view")
    public ResponseEntity<VideoViewResponseDto> upView(@PathVariable Long videoId){
        VideoViewResponseDto response = videoService.upView(videoId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<VideoListResponseDto>> searchVideo(@RequestParam String keyword){
        List<VideoListResponseDto> response = videoService.searchVideo(keyword);
        return ResponseEntity.ok(response);
    }


}
