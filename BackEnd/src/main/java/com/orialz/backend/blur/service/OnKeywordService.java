package com.orialz.backend.blur.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.orialz.backend.Member.domain.entity.Member;
import com.orialz.backend.Member.domain.repository.MemberRepository;
import com.orialz.backend.blur.Config.Mapper;
import com.orialz.backend.blur.domain.entity.Keyword;
import com.orialz.backend.blur.domain.entity.OnKeyword;
import com.orialz.backend.blur.domain.repository.KeywordRepository;
import com.orialz.backend.blur.domain.repository.OnKeywordRepository;
import com.orialz.backend.blur.dto.response.OnKeywordGetResponse;
import com.orialz.backend.comment.domain.entity.Comment;
import com.orialz.backend.comment.dto.response.CommentListResponseDto;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@AllArgsConstructor
public class OnKeywordService {

	MemberRepository memberRepository;
	KeywordRepository keywordRepository;
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

	public boolean ChangeOnKeyword(long mId,long kId){
		Optional<Member> member = memberRepository.findById(mId);
		if(member.isEmpty())
			return false;

		Optional<Keyword> keyword = keywordRepository.findById(kId);
		//존재하는 키워드의 경우
		if(keyword.isPresent()){
			Optional<OnKeyword> onKeyword = onKeywordRepository.findByMemberAndKeyword(member.get(),keyword.get());
			//내가 설정한 키워드가 있다면.
			//취소 요청
			if(onKeyword.isPresent()){
				onKeywordRepository.delete(onKeyword.get());
			}
			//없다면
			//신청 요청
			else{
				onKeywordRepository.save(
					OnKeyword.builder()
						.keyword(keyword.get())
						.member(member.get())
						.build()
				);
			}
			return true;
		}
		//존재하지 않는 키워드의 경우
		else{
			return false;
		}
	}

}
