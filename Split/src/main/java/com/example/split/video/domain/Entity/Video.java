package com.example.split.video.domain.Entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.example.split.Entity.BaseTimeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@DynamicInsert
@DynamicUpdate
public class Video extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long videoId;


	long member_id;

	@ColumnDefault("0")
	private Long view;

	private String title;
	private String content;
	private String path;
	private String thumbnail;

	@Enumerated(EnumType.STRING) //Enum값 문자열로 저장
	private VideoStatus status;

	String category;

	@OneToOne
	Job job;
}
