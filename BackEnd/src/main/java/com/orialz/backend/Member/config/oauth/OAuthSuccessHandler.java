package com.orialz.backend.Member.config.oauth;

import com.orialz.backend.Member.config.jwt.TokenProvider;
import com.orialz.backend.Member.domain.entity.Member;
import com.orialz.backend.Member.domain.entity.RefreshToken;
import com.orialz.backend.Member.domain.repository.RefreshTokenRepository;
import com.orialz.backend.Member.service.MemberService;
import com.orialz.backend.Member.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
    private final MemberService memberService;

    @Value("${properties.front.path}")
    String redirectUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Member member = memberService.getMemberByEmail((String) oAuth2User.getAttributes().get("email"));

        // 리프레시 토큰 생성 -> 저장 -> 쿠키에 저장
        String refreshToken = tokenProvider.generateToken(member, REFRESH_TOKEN_DURATION);
        saveRefreshToken(member.getId(), refreshToken);
        addRefreshTokenToCookie(request, response, refreshToken);
        // 액세스 토큰 생성 -> 패스에 액세스 토큰 추가
        String accessToken = tokenProvider.generateToken(member, ACCESS_TOKEN_DURATION);
        String targetUrl = getTargetUrl(accessToken);
        // 인증 관련 설정값, 쿠키 제거
        clearAuthenticationAttributes(request, response);
        // 리다이렉트
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    /**
     * 생성된 리프레시 토큰 전달받아 데이터베이스에 저장
     */
    private void saveRefreshToken(Long memberId, String newRefreshToken){
//        log.info(refreshTokenRepository.findByMemberId(memberId).toString());

        RefreshToken refreshToken = refreshTokenRepository
                .findByMemberId(memberId)
                .map(entity -> entity.update(newRefreshToken))
                .orElse(new RefreshToken(memberId, newRefreshToken));
        refreshTokenRepository.save(refreshToken);
    }

    /**
     * 생성된 리프레시 토큰을 쿠키에 저장
     */
    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
    }

    /**
     * 인증 관련 설정값, 쿠키 제거
     */
    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    /**
     * 액세스 토큰을 패스에 추가
     */
    private String getTargetUrl(String token) {
//        return UriComponentsBuilder.fromUriString("/")
//                .queryParam("token", token)
//                .build()
//                .toUriString();
        return "https://" + redirectUrl + "/?token=" + token;
    }
}
