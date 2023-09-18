package com.orialz.backend.video.service;

import com.orialz.backend.video.domain.entity.Video;
import com.orialz.backend.video.domain.repository.VideoRepository;
import com.orialz.backend.video.dto.response.VideoListResponseDto;
import com.orialz.backend.video.dto.response.VideoViewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
                      .uploader(video.getMember().getNickname())
                      .view(video.getView())
                      .build();
              resVideos.add(temp);
          }
          return resVideos;
    }

    @Transactional
    public VideoViewResponseDto upView (Long videoId){
        //비디오 가져오기
        Video nowVideo = videoRepository.findById(videoId).orElse(null);
        nowVideo.setView(nowVideo.getView()+1);
        VideoViewResponseDto response = VideoViewResponseDto.builder()
                .view(nowVideo.getView())
                .build();
        return response;
    }

}
