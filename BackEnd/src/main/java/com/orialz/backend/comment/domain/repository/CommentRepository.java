package com.orialz.backend.comment.domain.repository;

import com.orialz.backend.comment.domain.entity.Comment;
import com.orialz.backend.comment.dto.response.CommentListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByVideo_VideoId(Long videoId);
}
