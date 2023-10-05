package com.orialz.ffmpegserver.video.Controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orialz.ffmpegserver.video.Service.VideoService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@AllArgsConstructor
@Slf4j
public class VideoController {

	VideoService videoService;
	@GetMapping("/test/{vId}")
	public ResponseEntity<String> test(@PathVariable Long vId) throws IOException, InterruptedException, ClassNotFoundException {
		videoService.test(vId,"input.txt","input.txt","output","output.txt");
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

	@GetMapping("/uploadTest")
	public ResponseEntity<String> test2() throws IOException, InterruptedException, ClassNotFoundException {
		videoService.upload();
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
	@GetMapping("/downloadTest")
	public ResponseEntity<String> test3() throws IOException, InterruptedException, ClassNotFoundException {
		videoService.download();
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
}
