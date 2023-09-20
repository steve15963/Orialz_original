package com.orialz.backend.mypage.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class MypageVideoListResponseDto {
    private Long videoId;
    private String title;
    private String thumbnail;
    private Long view;
}
