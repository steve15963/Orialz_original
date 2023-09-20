package com.orialz.backend.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentPostRequestDto {
    private String content;
    private Long memberId; // 나중에 빼기
}
