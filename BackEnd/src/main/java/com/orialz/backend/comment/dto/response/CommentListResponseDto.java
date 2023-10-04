package com.orialz.backend.comment.dto.response;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentListResponseDto {
    private Long commentId;
    private Long videoId;
    private Long memberId;
    private String memberProfile;
    private String memberNickname;
    private String content;
    private LocalDateTime createAt;
}
