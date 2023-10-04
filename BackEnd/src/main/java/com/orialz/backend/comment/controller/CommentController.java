package com.orialz.backend.comment.controller;


import com.orialz.backend.Member.config.jwt.TokenProvider;
import com.orialz.backend.comment.domain.entity.Comment;
import com.orialz.backend.comment.dto.request.CommentPostRequestDto;
import com.orialz.backend.comment.dto.response.CommentListResponseDto;
import com.orialz.backend.comment.dto.response.CommentPostResponseDto;
import com.orialz.backend.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final TokenProvider tokenProvider;

    @GetMapping("/{videoId}")
    public ResponseEntity<List<CommentListResponseDto>> getCommentList(@PathVariable Long videoId){
        List<CommentListResponseDto> response =  commentService.getCommentList(videoId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{videoId}")
    public ResponseEntity<CommentPostResponseDto> postComment(@PathVariable Long videoId, @RequestHeader(value = "Authorization")String token, @RequestBody CommentPostRequestDto commentPostRequestDto){
        try {
            String accessToken = token.split("Bearer ")[1];
            Long memberId = tokenProvider.getMemberId(accessToken);
            CommentPostResponseDto response = commentService.postComment(videoId,memberId,commentPostRequestDto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
