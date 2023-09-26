package com.orialz.backend.Member.service;

import com.orialz.backend.Member.domain.entity.Member;
import com.orialz.backend.Member.domain.repository.MemberRepository;
import com.orialz.backend.Member.dto.AddMemberRequest;
import com.orialz.backend.Member.dto.MemberInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 로그인할 때 사용자 정보 가져오는 클래스
 */
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 정보 추가 메서드
     */
    public Long save(AddMemberRequest dto) {
        return memberRepository.save(Member.builder()
                .email(dto.getEmail())
                .build()).getId();
    }

    /**
     * email 입력받아 Member를 return 해주는 기능
     * @author 이지원
     * @since 09.07
     */
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected email"));
    }

    /**
     * memberId로 Member를 return
     */
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected member"));
    }

    public MemberInfoResponseDto getMemberInfo(String email) throws Exception {
        Member member = memberRepository.findMemberByEmail(email);

        MemberInfoResponseDto memberInfoResponseDto = MemberInfoResponseDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .picture(member.getPicture())
                .role(member.getRole())
                .provider(member.getProvider())
                .providerId(member.getProviderId())
                .build();
        return memberInfoResponseDto;
    }
}
