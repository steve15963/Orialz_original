package com.orialz.backend.blur.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orialz.backend.Member.domain.entity.Member;
import com.orialz.backend.blur.domain.entity.OnKeyword;

public interface OnKeywordRepository extends JpaRepository<OnKeyword,Long> {
	List<OnKeyword> findAllByMemberId(long id);
}
