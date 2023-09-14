package com.orialz.backend.Member.service;

import com.orialz.backend.Member.domain.entity.RefreshToken;
import com.orialz.backend.Member.domain.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 전달받은 리프레시 토큰으로 리프레시 토큰 객체 검색해서 전달하는 메서드
     */
    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }
}
