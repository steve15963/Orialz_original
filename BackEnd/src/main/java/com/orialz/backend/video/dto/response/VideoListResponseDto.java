package com.orialz.backend.video.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoListResponseDto {
    Long id;
    String thumbnail;
    String title;
    String content;
    String uploader;
    Long view;
    LocalDateTime date;
}
