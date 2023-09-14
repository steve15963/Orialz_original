package com.orialz.backend.Member.config.jwt;

import com.orialz.backend.Member.domain.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

/**
 * 토큰 생성하고 올바른 토큰인지 유효성 검사하고 토큰에서 필요한 정보 가져오는 클래스
 */
@Service
@RequiredArgsConstructor
public class TokenProvider {

    private final JwtProperties jwtProperties;

    public String generateToken(Member member, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), member);
    }
    /**
     * JWT 생성 메서드
     */
    private String makeToken(Date expiry, Member member) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 typ : JWT
                .setIssuer(jwtProperties.getIssuer()) // 내용 iss : 이슈 발급자
                .setIssuedAt(now) // 내용 iat : 현재 시간
                .setExpiration(expiry) // 내용 exp : expiry 멤버 변수값
                .setSubject(member.getEmail()) // 내용 sub : 멤버 이메일
                .claim("id", member.getId()) // 클레임 id : 멤버 ID
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()) // 토큰 만들 때 비밀값과 함께 HS256 방식으로 암호화
                .compact();
    }

    /**
     * 토큰 유효성 검정 메서드
     */
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey()) // 비밀값으로 복호화
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) { // 복호화 과정에서 에러가 나면 유효하지 않은 토큰
            return false;
        }
    }

    /**
     * 토큰을 받아 인증 정보를 담은 Authentication을 반환하는 메서드
     * 클레임 정보를 반환받아 사용자 이메일인 issuer와 토큰 기반으로 인증 정보 생성
     */
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_MEMBER"));
        // 스프링 시큐리티에서 제공하는 객체인 User 클래스 import
        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(
                claims.getSubject(), "", authorities), token, authorities);
    }

    /**
     * 비밀값으로 토큰을 복호화한 뒤 클레임 가져오는 메서드
     */
    private Claims getClaims(String token) {
        return Jwts.parser() // 클레임 조회
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token) // 비밀값으로 토큰 복호화
                .getBody();
    }

    /**
     * 토큰 기반으로 사용자 ID 가져오는 메서드
     */
    public Long getMemberId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }
}
