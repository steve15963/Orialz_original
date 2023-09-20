package com.orialz.backend.video.domain.entity;

import com.orialz.backend.Member.domain.entity.Member;
import com.orialz.backend.common.BaseTimeEntity;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@DynamicInsert
@DynamicUpdate
public class Video extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long videoId;


    @ManyToOne
    @JoinColumn(name = "member_id")
    @NotNull
    private Member member;

    @ColumnDefault("0")
    private Long view;

    private String title;
    private String content;
    private String path;
    private String thumbnail;

    @Enumerated(EnumType.STRING) //Enum값 문자열로 저장
    private VideoStatus status;

    @Enumerated(EnumType.STRING)
    private CategoryStatus category;



}
