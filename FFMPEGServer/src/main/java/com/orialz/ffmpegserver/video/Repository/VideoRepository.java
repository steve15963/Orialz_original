package com.orialz.ffmpegserver.video.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orialz.ffmpegserver.video.Vo.Entity.Video;

public interface VideoRepository extends JpaRepository<Video,Long> {

}
