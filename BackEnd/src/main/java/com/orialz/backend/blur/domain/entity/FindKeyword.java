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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(exclude = "keyword")
public class FindKeyword {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "find_keyword_id")
	long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "keyword_id")
	Keyword keyword;

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
	public long getLabel() {
		return video.getVideoId();
	}

	public long getX() {
		return (int)minX;
	}

	public long getY() {
		return (int)minY;
	}

	public long getW() {
		return (int)(maxX - minX);
	}

	public long getH() {
		return (int)(maxY - minY);
	}
}
