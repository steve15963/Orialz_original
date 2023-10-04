package com.orialz.backend.blur.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.orialz.backend.blur.Config.Mapper;
import com.orialz.backend.blur.domain.entity.Keyword;
import com.orialz.backend.blur.domain.repository.KeywordRepository;
import com.orialz.backend.blur.dto.response.KeywordGetResponse;
import com.orialz.backend.blur.dto.response.OnKeywordGetResponse;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@AllArgsConstructor
public class KeywordService {
	KeywordRepository keywordRepository;
	private final Mapper mapper;
	public List<KeywordGetResponse> keywordList (){
		ModelMapper mp = mapper.KeywordToKeywordReponseMapper();
		List<KeywordGetResponse> result = keywordRepository.findAll()
			.stream()
			.map(entityData -> mp.map(
				entityData,
				KeywordGetResponse.class
			))
			.collect(Collectors.toList());
		return result;
	}


}
