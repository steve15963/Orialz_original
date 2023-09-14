package com.orialz.backend.video.domain.repository;

import com.orialz.backend.video.domain.entity.Member;
import com.orialz.backend.video.domain.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


}
