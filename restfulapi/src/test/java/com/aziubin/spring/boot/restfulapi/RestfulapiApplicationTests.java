package com.aziubin.spring.boot.restfulapi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.aziubin.spring.boot.restfulapi.RestfulapiApplication.Node;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestfulapiApplicationTests {
	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	void contextLoads() {
	}
	
	@Test
	void nodes() {
		Node[] nodes = testRestTemplate.getForObject("/nodes", Node[].class);
		assertThat(null != nodes);

		var node = new Node();
		node.setName("name 73456 397845 239487");
		nodes = testRestTemplate.postForObject("/nodes", node, Node[].class);
		assertThat(null != nodes && nodes.length > 0);
	}

}
