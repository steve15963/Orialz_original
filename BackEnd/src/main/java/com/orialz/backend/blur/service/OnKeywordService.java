package com.orialz.backend.blur.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.orialz.backend.blur.Config.Mapper;
import com.orialz.backend.blur.domain.entity.OnKeyword;
import com.orialz.backend.blur.domain.repository.OnKeywordRepository;
import com.orialz.backend.blur.dto.response.OnKeywordGetResponse;
import com.orialz.backend.comment.domain.entity.Comment;
import com.orialz.backend.comment.dto.response.CommentListResponseDto;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@AllArgsConstructor
public class OnKeywordService {

	OnKeywordRepository onKeywordRepository;
	private final Mapper mapper;

	public List<OnKeywordGetResponse> SettingKeywordList(long id){
		ModelMapper mp = mapper.OnKeywordToOnKeywordReponseMapper();
		List<OnKeywordGetResponse> result = onKeywordRepository.findAllByMemberId(id)
			.stream()
			.map(entityData -> mp.map(
				entityData,
				OnKeywordGetResponse.class
			))
			.collect(Collectors.toList());
		return result;
	}
}
