package com.example.split.video.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.split.video.domain.Entity.FindKeyword;

public interface FindKeywordRepository extends JpaRepository<FindKeyword,Long> {

}
