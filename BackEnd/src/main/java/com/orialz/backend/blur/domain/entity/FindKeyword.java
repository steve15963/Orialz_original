package com.orialz.backend.blur.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.orialz.backend.video.domain.entity.Video;

@Entity
public class FindKeyword {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "find_keyword_id")
	long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "keyword_id")
	Keyword keyword;

	@ManyToOne
	@JoinColumn(name = "video_id")
	Video video;

	Double time;

	@Column
	double minx;
	@Column
	double miny;
	@Column
	double maxx;
	@Column
	double maxy;
	@Column
	double score;
}
