package com.orialz.backend.blur.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orialz.backend.blur.domain.entity.FindKeyword;
import com.orialz.backend.blur.domain.entity.Keyword;
import com.orialz.backend.blur.domain.entity.OnKeyword;
import com.orialz.backend.blur.domain.repository.FindKeywordRepository;
import com.orialz.backend.blur.dto.response.FindKeywordGetResponse;
import com.orialz.backend.blur.dto.response.KeywordGetResponse;
import com.orialz.backend.blur.dto.response.OnKeywordGetResponse;
import com.orialz.backend.blur.service.FindKeywordService;
import com.orialz.backend.blur.service.KeywordService;
import com.orialz.backend.blur.service.OnKeywordService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/blur")
public class BlurController {

	KeywordService keywordService;
	OnKeywordService onKeywordService;
	FindKeywordService findKeywordService;


	@GetMapping("/keyword/{id}")
	public ResponseEntity<List<OnKeywordGetResponse>> getSettingKeyword(@PathVariable long id){
		return new ResponseEntity<>(onKeywordService.SettingKeywordList(id),HttpStatus.OK);
	}

	@GetMapping("/keyword")
	public ResponseEntity<List<KeywordGetResponse>> getKeyword(){
		return new ResponseEntity<>(keywordService.keywordList(),HttpStatus.OK);
	}

	@GetMapping("/keyword/{mId}/{vId}")
	public ResponseEntity<Map<Double, List<FindKeywordGetResponse>>> getFindKeyword(@PathVariable long mId,@PathVariable long vId){
		return new ResponseEntity<>(findKeywordService.findKeywordList(mId, vId),HttpStatus.OK);
	}


}
