package com.orialz.backend.Member.domain.repository;

import com.orialz.backend.Member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email); // email로 사용자 정보 가져옴
    Optional<Member> findById(Long id); // id로 사용자 정보 가져옴
}
