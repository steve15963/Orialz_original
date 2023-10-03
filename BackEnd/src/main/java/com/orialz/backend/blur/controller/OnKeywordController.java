package com.orialz.backend.blur.controller;

import java.net.http.HttpResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orialz.backend.blur.service.OnKeywordService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/onkeyword")
public class OnKeywordController {
	OnKeywordService onKeywordService;
	@GetMapping("/change/{mId}/{kId}")
	public ResponseEntity<HttpResponse> ChangeOnKeywordController(@PathVariable long mId, @PathVariable Long kId){
		if(onKeywordService.ChangeOnKeyword(mId,kId)){
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
