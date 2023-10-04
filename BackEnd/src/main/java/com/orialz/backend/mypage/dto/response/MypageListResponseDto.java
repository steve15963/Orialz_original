package com.orialz.backend.mypage.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageListResponseDto {
    private Integer commentNum;
    private Integer videoNum;
    private List<MypageVideoListResponseDto> videoList;
    private List<MypageCommentListResponseDto> commentList;
}
