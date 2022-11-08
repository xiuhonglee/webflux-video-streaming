package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class DemoApplication {

	private final StreamingService streamingService;

	public DemoApplication(StreamingService streamingService) {
		this.streamingService = streamingService;
	}

	@RequestMapping(value = "video/{title}", produces = "video/mp4")
	public Mono<Resource> getVideo(@PathVariable String title, @RequestHeader("Range") String ranger) {
		System.out.println("range in bytes() : " + ranger);

		return streamingService.getVideo(title);
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
