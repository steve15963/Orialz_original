package com.orialz.backend.blur.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orialz.backend.blur.dto.response.KeywordGetResponse;
import com.orialz.backend.blur.service.KeywordService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/keyword")
public class KeywordController {
	KeywordService keywordService;
	@GetMapping("/list")
	public ResponseEntity<List<KeywordGetResponse>> getKeyword(){
		return new ResponseEntity<>(keywordService.keywordList(), HttpStatus.OK);
	}
}
