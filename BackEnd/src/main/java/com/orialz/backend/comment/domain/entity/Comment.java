package com.orialz.backend.comment.domain.entity;

import com.orialz.backend.Member.domain.entity.Member;
import com.orialz.backend.common.BaseTimeEntity;
import com.orialz.backend.video.domain.entity.Video;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @NotNull
    private Member member;

    @ManyToOne
    @JoinColumn(name = "video_id")
    @NotNull
    private Video video;


    private String content;


}
