package com.orialz.backend.comment.domain.repository;

import com.orialz.backend.comment.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByVideo_VideoId(Long videoId);
    List<Comment> findByMember_Id(Long memberId);
}
