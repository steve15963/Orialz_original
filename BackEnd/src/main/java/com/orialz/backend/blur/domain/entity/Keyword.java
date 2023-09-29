package com.orialz.backend.blur.domain.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Keyword {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "keyword_id")
	Long id;

	@Column
	String keyword;

	@OneToMany(mappedBy = "keyword")
	List<FindKeyword> findKeywordList;

}
