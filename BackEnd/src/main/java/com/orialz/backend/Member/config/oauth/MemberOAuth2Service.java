package com.orialz.backend.Member.config.oauth;

import com.orialz.backend.Member.domain.entity.Member;
import com.orialz.backend.Member.domain.entity.MemberDetails;
import com.orialz.backend.Member.domain.entity.Role;
import com.orialz.backend.Member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberOAuth2Service extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes : {}", oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");
        String email = oAuth2User.getAttribute("email");

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        Member member;

        if(optionalMember.isEmpty()) { // 멤버가 없으면 멤버 생성
            member = Member.builder()
                    .email(email)
                    .nickname(oAuth2User.getAttribute("name"))
                    .provider(provider)
                    .providerId(providerId)
                    .picture(oAuth2User.getAttribute("picture"))
                    .role(Role.MEMBER)
                    .build();
        } else { // 멤버가 있으면 업데이트
            member = optionalMember
                    .map(entity -> entity.update(oAuth2User.getAttribute("name"), oAuth2User.getAttribute("picture")))
                    .orElse(Member.builder().build());
        }
        memberRepository.save(member);

        return new MemberDetails(member, oAuth2User.getAttributes());
    }
}
