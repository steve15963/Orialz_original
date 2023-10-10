package com.example.split.video.domain.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
	double W;
	@Column
	double H;
	@Column
	double score;
}
