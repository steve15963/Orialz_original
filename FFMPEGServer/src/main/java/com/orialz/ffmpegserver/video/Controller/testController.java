package com.orialz.ffmpegserver.video.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/hls")
@AllArgsConstructor
@Slf4j
public class testController {
	@ResponseBody
	@GetMapping("/hello")
	public ResponseEntity<String> hello(){
		log.info("!!");
		return ResponseEntity.ok("hello");
	}
}
