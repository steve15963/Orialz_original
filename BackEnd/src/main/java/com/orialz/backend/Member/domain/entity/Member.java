package com.orialz.backend.Member.domain.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.orialz.backend.blur.domain.entity.SettingKeyword;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id", updatable = false)
    private Long id;

    @Column(name="nickname", nullable = false)
    private String nickname;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "provider_id", nullable = false, unique = true)
    private String providerId;

    @OneToMany(mappedBy = "member")
    private List<SettingKeyword> settingKeywordList;

    @Builder
    public Member(String nickname, String email, String picture, Role role, String provider, String providerId) {
        this.nickname = nickname;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    public Member update(String nickname, String picture) {
        this.nickname = nickname;
        this.picture = picture;
        return this;
    }

}
