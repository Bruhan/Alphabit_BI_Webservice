package com.owner.process;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@SpringBootApplication
public class OwnerWebServiceApplication {
	@Value("${spring.date.time.zone}")
	private String timeZone;

	public static void main(String[] args) {
		SpringApplication.run(OwnerWebServiceApplication.class, args);
	}
	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
	}
}
@RestController()
@RequestMapping("/api")
class PingController {
	@GetMapping("/ping")
	public ResponseEntity<?> pingTest() {
		Map<String, Object> statusOb = new HashMap<>();
		String userDirectory = Paths.get("").toAbsolutePath().toString();
		statusOb.put("ping", 1);
		statusOb.put("userDirectory", userDirectory);
		return new ResponseEntity<>(statusOb, HttpStatus.OK);
	}
}

@RestController()
@RequestMapping("/api")
class AsyncTesting {
	@GetMapping("/async")
	@Async
	public ResponseEntity<?> asyncMethodWithReturnType() {
		System.out.println("Execute method asynchronously - "
				+ Thread.currentThread().getName());
		try {
			Thread.sleep(5000);
			System.out.println("Thread Completed " + Thread.currentThread().getName());
			return new ResponseEntity<>("", HttpStatus.OK);
		} catch (InterruptedException e) {
			//
		}
		System.out.println("Thread Processing " + Thread.currentThread().getName());
		return new ResponseEntity<>("", HttpStatus.OK);
	}
}