package com.orialz.backend.mypage.controller;

import com.orialz.backend.Member.config.jwt.TokenProvider;
import com.orialz.backend.mypage.dto.response.MypageCommentListResponseDto;
import com.orialz.backend.mypage.dto.response.MypageListResponseDto;
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

    @GetMapping("/mydata")
    public ResponseEntity<MypageListResponseDto> getMyVideo(@RequestHeader(value = "Authorization")String token){
        try {
            String accessToken = token.split("Bearer ")[1];
            Long memberId = tokenProvider.getMemberId(accessToken);
            MypageListResponseDto response = mypageService.getMypage(memberId);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
