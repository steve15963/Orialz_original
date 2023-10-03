package com.orialz.backend.blur.Config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.orialz.backend.blur.domain.entity.FindKeyword;
import com.orialz.backend.blur.domain.entity.Keyword;
import com.orialz.backend.blur.domain.entity.OnKeyword;
import com.orialz.backend.blur.dto.response.FindKeywordGetResponse;
import com.orialz.backend.blur.dto.response.KeywordGetResponse;
import com.orialz.backend.blur.dto.response.OnKeywordGetResponse;

@Configuration
public class Mapper {
	@Bean
	public ModelMapper OnKeywordToOnKeywordReponseMapper(){
		ModelMapper mp = new ModelMapper();
		mp.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		mp.createTypeMap(OnKeyword.class, OnKeywordGetResponse.class)
			.addMapping(data -> data.getKeyword().getId(),OnKeywordGetResponse::setId)
			.addMapping(data -> data.getKeyword().getKeyword(),OnKeywordGetResponse::setKeyword);
		return mp;
	}

	@Bean
	public ModelMapper KeywordToKeywordReponseMapper(){
		ModelMapper mp = new ModelMapper();
		mp.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		mp.createTypeMap(Keyword.class, KeywordGetResponse.class)
			.addMapping(Keyword::getId,KeywordGetResponse::setId)
			.addMapping(Keyword::getKeyword,KeywordGetResponse::setKeyword);
		return mp;
	}
	@Bean
	public ModelMapper FindKeywordToFindKeywordReponseMapper(){
		ModelMapper mp = new ModelMapper();
		mp.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		mp.createTypeMap(FindKeyword.class, FindKeywordGetResponse.class)
			.addMapping(FindKeyword::getLabel, FindKeywordGetResponse::setLabel)
			.addMapping(FindKeyword::getMinX,FindKeywordGetResponse::setX)
			.addMapping(FindKeyword::getMinY,FindKeywordGetResponse::setY)
			.addMapping(FindKeyword::getW,FindKeywordGetResponse::setW)
			.addMapping(FindKeyword::getH,FindKeywordGetResponse::setH);
		return mp;
	}
}

