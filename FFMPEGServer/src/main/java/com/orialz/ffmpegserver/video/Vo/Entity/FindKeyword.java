package com.orialz.ffmpegserver.video.Vo.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
public class FindKeyword {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "find_keyword_id")
	long id;

	long keyword_id;

	@ManyToOne
	@JoinColumn(name = "video_id")
	Video video;

	Double time;

	@Column
	double minX;
	@Column
	double minY;
	@Column
	double maxX;
	@Column
	double maxY;
	@Column
	double score;
}
