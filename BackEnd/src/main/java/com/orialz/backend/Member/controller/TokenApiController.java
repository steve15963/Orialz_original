package com.orialz.backend.Member.controller;

import com.orialz.backend.Member.config.jwt.TokenProvider;
import com.orialz.backend.Member.domain.entity.RefreshToken;
import com.orialz.backend.Member.domain.repository.RefreshTokenRepository;
import com.orialz.backend.Member.dto.CreateAccessTokenRequest;
import com.orialz.backend.Member.dto.CreateAccessTokenResponse;
import com.orialz.backend.Member.dto.ValidAccessTokenRequestDto;
import com.orialz.backend.Member.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TokenApiController {
    private final TokenService tokenService;
    private final TokenProvider tokenProvider;

    /**
     * 리프레시 토큰을 기반으로 새로운 액세스 토큰 만드는 메서드
     */
    @PostMapping("/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(@RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateAccessTokenResponse(newAccessToken));
    }

    @PostMapping("/valid")
    public ResponseEntity validAccessToken(@RequestBody ValidAccessTokenRequestDto request) {
        String accessToken = request.getAccessToken();
        if(tokenProvider.validToken(accessToken)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Autowired
    public RefreshTokenRepository refreshTokenRepository;

    @GetMapping("/api/test")
    public ResponseEntity<String> test(){
        Optional<RefreshToken> byMemberId = refreshTokenRepository.findByMemberId(1L);
        return new ResponseEntity(byMemberId.toString(),HttpStatus.OK);
    }
}
