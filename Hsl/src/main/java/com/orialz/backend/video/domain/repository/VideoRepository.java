package com.orialz.backend.video.domain.repository;

import com.orialz.backend.video.domain.entity.Video;
import com.orialz.backend.video.dto.response.VideoListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {


//    @Query("SELECT new com.orialz.backend.video.dto.response.VideoListResponseDto FROM videos" )
//    List<VideoListResponseDto> findAllVideo
}
