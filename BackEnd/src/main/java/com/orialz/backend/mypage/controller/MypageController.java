package com.orialz.backend.mypage.controller;

import com.orialz.backend.Member.config.jwt.TokenProvider;
import com.orialz.backend.mypage.dto.response.MypageCommentListResponseDto;
import com.orialz.backend.mypage.dto.response.MypageVideoListResponseDto;
import com.orialz.backend.mypage.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MypageController {
    private final MypageService mypageService;
    private final TokenProvider tokenProvider;

    @GetMapping("/video")
    public ResponseEntity<List<MypageVideoListResponseDto>> getMyVideo(@RequestHeader(value = "Authorization")String token){
        try {
            String accessToken = token.split("Bearer ")[1];
            Long memberId = tokenProvider.getMemberId(accessToken);
            List<MypageVideoListResponseDto> response = mypageService.getMyVideo(memberId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/comment")
    public ResponseEntity<List<MypageCommentListResponseDto>> getMyComment(@RequestHeader(value = "Authorization")String token){
        try {
            String accessToken = token.split("Bearer ")[1];
            Long memberId = tokenProvider.getMemberId(accessToken);
            List<MypageCommentListResponseDto> response = mypageService.getMyComment(memberId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
