package com.orialz.backend.streaming.domain.repository;

import com.orialz.backend.streaming.domain.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {


//    @Query("SELECT new com.orialz.backend.video.dto.response.VideoListResponseDto FROM videos" )
//    List<VideoListResponseDto> findAllVideo
}
