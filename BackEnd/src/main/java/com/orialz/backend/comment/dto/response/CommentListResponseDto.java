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
    private String memberProfile;
    private String memberNickname;
    private String content;
}
