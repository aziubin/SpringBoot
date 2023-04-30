package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
	
//    @LocalServerPort
//    int randomServerPort;
    
//	@Autowired
//	WebTestClient webTestClient;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	void contextLoads() {
	}

	@Test
	void temperature() {
//		webTestClient.get().uri("/temperature").exchange().expectStatus().isOk().expectBody(Integer.class).isEqualTo(15);
		Integer body = restTemplate.getForObject("/temperature", Integer.class);
		assertThat(body).isEqualTo(1);
	}

	@Test
	void humidity() {
		Integer body = restTemplate.getForObject("/humidity", Integer.class);
		assertThat(body).isEqualTo(8);
	}

	@Test
	void version() {
		String body = restTemplate.getForObject("/version", String.class);
		assertThat(body).isNotEmpty();
	}

}
