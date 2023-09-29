package com.orialz.backend.blur.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orialz.backend.blur.domain.entity.Keyword;

public interface KeywordRepository extends JpaRepository<Keyword,Long> {
}
