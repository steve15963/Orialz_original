package com.orialz.backend.comment.dto.response;


import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentListResponseDto {
    private Long commentId;
    private Long videoId;
    private Long memberId;
    private String memberNickname;
    private String memberProfile;
    private String content;
}
