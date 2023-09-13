package com.orialz.backend.video.domain.entity;


import com.orialz.backend.common.BaseTimeEntity;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "members")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long memberId;

    @OneToMany(mappedBy = "member")
    @NotNull
    private List<Video> videos;
    private String email;
    private String nickName;
}
