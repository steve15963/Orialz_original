package com.orialz.backend.mypage.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageCommentListResponseDto {
    private Long videoId;
    private Long commentId;
    private String thumbnail;
    private String content;
    private LocalDateTime createAt;
}
