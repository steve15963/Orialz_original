package com.orialz.backend.streaming.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadResponseDto {
    private Long videoId;
    private LocalDateTime createAt;
    private String hash;
}
