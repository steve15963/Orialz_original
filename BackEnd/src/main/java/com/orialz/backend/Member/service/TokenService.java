package com.orialz.backend.Member.service;

import com.orialz.backend.Member.config.jwt.TokenProvider;
import com.orialz.backend.Member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final MemberService memberService;

    /**
     * 전달받은 refreshToken으로 토큰 유효성 검사를 진행하고,
     * 유효한 토큰일 경우 refreshToken으로 memberId를 찾아
     * memberId로 Member를 찾은 후 토큰 제공자의 generateToken() 메서드 호출해서 새로운 액세스 토큰 생성
     */
    public String createNewAccessToken(String refreshToken) {
        // 토큰 유효성 검사에 실패했을 경우 예외 발생
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long memberId = refreshTokenService.findByRefreshToken(refreshToken).getMemberId();
        Member member = memberService.findById(memberId);
        return tokenProvider.generateToken(member, Duration.ofHours(2));
    }
}
