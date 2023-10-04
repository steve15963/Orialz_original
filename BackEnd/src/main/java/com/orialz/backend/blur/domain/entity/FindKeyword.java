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
	public String getLabel() {
		return keyword.getKeyword();
	}

	public double getX() {
		return minX;
	}

	public double getY() {
		return minY;
	}

	public double getW() {
		return maxX - minX;
	}

	public double getH() {
		return maxY - minY;
	}
}
