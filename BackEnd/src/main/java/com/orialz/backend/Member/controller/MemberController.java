package com.orialz.backend.Member.controller;

import com.orialz.backend.Member.config.jwt.TokenProvider;
import com.orialz.backend.Member.domain.entity.Member;
import com.orialz.backend.Member.domain.entity.MemberDetails;
import com.orialz.backend.Member.domain.repository.MemberRepository;
import com.orialz.backend.Member.dto.MemberInfoResponseDto;
import com.orialz.backend.Member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @GetMapping("/info")
    public ResponseEntity<MemberInfoResponseDto> getMemberInfo1(@RequestHeader(value = "Authorization")String token) throws Exception {
        try {
            log.info(token);
            log.info(String.valueOf(tokenProvider.getMemberId(token)));
            Long id = tokenProvider.getMemberId(token);
            MemberInfoResponseDto memberInfo = memberService.getMemberInfo(id);
            log.info(memberInfo.toString());
            return new ResponseEntity<>(memberInfo, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception("정보를 가져올 수 없습니다.");
        }

    }
}
