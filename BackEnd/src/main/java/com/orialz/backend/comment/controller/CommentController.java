package com.orialz.backend.comment.controller;


import com.orialz.backend.comment.domain.entity.Comment;
import com.orialz.backend.comment.dto.request.CommentPostRequestDto;
import com.orialz.backend.comment.dto.response.CommentListResponseDto;
import com.orialz.backend.comment.dto.response.CommentPostResponseDto;
import com.orialz.backend.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{videoId}")
    public ResponseEntity<List<CommentListResponseDto>> getCommentList(@PathVariable Long videoId){
        List<CommentListResponseDto> response =  commentService.getCommentList(videoId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{videoId}")
    public ResponseEntity<CommentPostResponseDto> postComment(@PathVariable Long videoId, @RequestBody CommentPostRequestDto commentPostRequestDto){
        CommentPostResponseDto response = commentService.postComment(videoId,commentPostRequestDto);
        return ResponseEntity.ok(response);
    }
}
