package com.orialz.backend.video.domain.entity;

import com.orialz.backend.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name="categories")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long memberId;

    @Enumerated
    private CategoryStatus categoryStatus;

}
