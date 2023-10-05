package com.example.split.video.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.split.video.domain.Entity.Video;

public interface VideoRepository extends JpaRepository<Video,Long> {

}
