package com.orialz.backend.streaming.domain.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class MemberDetails implements OAuth2User {
    private final Member member;

    private final Map<String, Object> attributes; // 소셜 로그인 통해서 받은 정보들 그대로 담아 return해주는 역할
    public MemberDetails(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return member.getNickname();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collections = new ArrayList<>();
        collections.add(() -> {
            return member.getRole().name();
        });
        return collections;
    }

}
