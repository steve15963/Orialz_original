package com.orialz.backend.Member.dto;

import com.orialz.backend.Member.domain.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberInfoResponseDto {
    private Long id;
    private String nickname;
    private String email;
    private String picture;
    private Role role;
    private String provider;
    private String providerId;

    @Builder
    public MemberInfoResponseDto(Long id, String nickname, String email, String picture, Role role, String provider, String providerId) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }
}
