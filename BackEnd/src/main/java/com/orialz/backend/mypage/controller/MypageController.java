package com.orialz.backend.mypage.controller;

import com.orialz.backend.mypage.dto.response.MypageCommentListResponseDto;
import com.orialz.backend.mypage.dto.response.MypageListResponseDto;
import com.orialz.backend.mypage.dto.response.MypageVideoListResponseDto;
import com.orialz.backend.mypage.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MypageController {
    private final MypageService mypageService;

    @GetMapping("/{memberId}") // 나중에 pathVariable 제거
    public ResponseEntity<MypageListResponseDto> getMyVideo(@PathVariable Long memberId){
        MypageListResponseDto response = mypageService.getMypage(memberId);
        return ResponseEntity.ok(response);
    }


}
