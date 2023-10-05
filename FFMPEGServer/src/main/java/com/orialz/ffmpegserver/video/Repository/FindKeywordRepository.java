package com.orialz.ffmpegserver.video.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orialz.ffmpegserver.video.Vo.Entity.FindKeyword;

public interface FindKeywordRepository extends JpaRepository<FindKeyword,Long> {

}
