package com.orialz.backend.blur.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.orialz.backend.Member.domain.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class OnKeyword {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "on_keyword_id")
	Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "keyword_id")
	Keyword keyword;

	@ManyToOne
	@JoinColumn(name = "member_id")
	Member member;

}
