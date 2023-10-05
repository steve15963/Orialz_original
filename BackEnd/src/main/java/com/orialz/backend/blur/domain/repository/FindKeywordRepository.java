package com.orialz.backend.blur.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.orialz.backend.blur.domain.entity.FindKeyword;

public interface FindKeywordRepository extends JpaRepository<FindKeyword,Long> {
	@Query("SELECT f "
		+ "FROM FindKeyword as f "
		+ "WHERE f.video.videoId = :vId "
		+ "AND f.score >= 0.15"
		+ "AND f.keyword IN ("
		+ "SELECT o.keyword "
		+ "FROM OnKeyword as o "
		+ "WHERE o.member.id = :mId "
		+ "ORDER BY f.time asc "
		+ ")"
	)
	List<FindKeyword> findAllBymIdAndvId(@Param("mId") long mId,   @Param("vId") long vId);
}
