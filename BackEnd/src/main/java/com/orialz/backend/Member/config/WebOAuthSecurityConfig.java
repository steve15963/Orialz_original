package com.orialz.backend.Member.config;

import com.orialz.backend.Member.config.jwt.TokenAuthenticationFilter;
import com.orialz.backend.Member.config.jwt.TokenProvider;
import com.orialz.backend.Member.config.oauth.MemberOAuth2Service;
import com.orialz.backend.Member.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.orialz.backend.Member.config.oauth.OAuthSuccessHandler;
import com.orialz.backend.Member.domain.repository.RefreshTokenRepository;
import com.orialz.backend.Member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebOAuthSecurityConfig {

    private final MemberOAuth2Service memberOAuth2Service;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberService memberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 인가(접근 권한) 설정
        http.authorizeHttpRequests().antMatchers("/").permitAll(); // 어떤 사용자든 접근 가능
        http.authorizeHttpRequests().antMatchers("api/comment/**").hasRole("MEMBER"); // MEMBER 권한 가진 사람만 접근 가능

        // 사이트 위변조 요청 방지
        http.csrf().disable();

        // 로그인 설정
        http.formLogin().disable()
                .httpBasic().disable(); // httpBasic : Http basic Auth 기반으로 로그인 인증창이 뜸.

        // 헤더를 확인할 커스텀 필터 추가
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http
                .oauth2Login()
                .authorizationEndpoint().baseUri("/oauth2/authorization")// 소셜 로그인 Url
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())// 인증 요청을 쿠키에 저장하고 검색
                .and()
                .redirectionEndpoint().baseUri("/login/oauth2/code/google")// 소셜 인증 후 Redirect Url
                .and()
                .userInfoEndpoint().userService(memberOAuth2Service)// 소셜의 회원 정보를 받아와 가공처리
                .and()
                .successHandler(oAuthSuccessHandler());

        // 로그아웃 설정
        http.logout().invalidateHttpSession(true).deleteCookies("JSESSIONID");

        // 예외 처리
        http.exceptionHandling()
                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED), new AntPathRequestMatcher("/api/**"));

        return http.build();
    }

    @Bean
    public OAuthSuccessHandler oAuthSuccessHandler() {
        return new OAuthSuccessHandler(tokenProvider,
                refreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                memberService);
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }
}
