package com.orialz.backend.video.service;

import com.orialz.backend.video.domain.entity.Video;
import com.orialz.backend.video.domain.repository.VideoRepository;
import com.orialz.backend.video.dto.response.VideoListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    public List<VideoListResponseDto> mainList(){
          List<Video> videos = videoRepository.findAll();
        List<VideoListResponseDto> resVideos = new ArrayList<>();
          for(Video video: videos){
              VideoListResponseDto temp = VideoListResponseDto.builder()
                      .id(video.getVideoId())
                      .date(video.getCreatedAt()) //~시간 전으로 수정 가능
                      .thumbnail(video.getThumbnail())
                      .title(video.getTitle())
                      .uploader(video.getMember().getNickName())
                      .view(video.getView())
                      .build();
              resVideos.add(temp);
          }
          return resVideos;
    }

}
