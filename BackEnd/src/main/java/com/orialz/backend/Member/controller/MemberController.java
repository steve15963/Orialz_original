package com.orialz.backend.Member.controller;

import com.orialz.backend.Member.domain.entity.Member;
import com.orialz.backend.Member.domain.entity.MemberDetails;
import com.orialz.backend.Member.domain.repository.MemberRepository;
import com.orialz.backend.Member.dto.MemberInfoResponseDto;
import com.orialz.backend.Member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/info")
    public ResponseEntity<MemberInfoResponseDto> getMemberInfo(@AuthenticationPrincipal Object memberDetails) throws Exception{
        if(memberDetails instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User member = (org.springframework.security.core.userdetails.User) memberDetails;
            String email = member.getUsername();
            MemberInfoResponseDto memberInfo = memberService.getMemberInfo(email);
            
            return new ResponseEntity<>(memberInfo, HttpStatus.OK);
        } else {
            throw new Exception("정보를 가져올 수 없습니다.");
        }
    }

}
