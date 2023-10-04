package com.orialz.ffmpegserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FfmpegServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FfmpegServerApplication.class, args);
	}

}
