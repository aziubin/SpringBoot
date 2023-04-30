package com.aziubin.spring.boot.restfulapi;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author aziub
 * https://start.spring.io/#!type=maven-project&
 * language=java&
 * platformVersion=3.0.6&
 * packaging=jar&jvmVersion=17&
 * groupId=com.aziubin.spring.boot&
 * artifactId=restfulapi&
 * name=restfulapi&
 * description=Demo%20project%20for%20Spring%20Boot&
 * packageName=com.aziubin.spring.boot.restfulapi&
 * dependencies=devtools,lombok,web
 *
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class RestfulapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulapiApplication.class, args);
	}

//	@Controller
	@RestController
	static class SwarmController {
		@Autowired
		RestfulApiConfiguration restfulApiConfiguration;
		
		@GetMapping("/nodes")
		List getNodes(@RequestParam(required = false, defaultValue = "100") Integer max) {
			return nodesListSingleton.INSTANCE.getList().stream().limit(max).collect(Collectors.toList());
		}

		@PostMapping("/nodes")
		List<Node> setNodes(Node node) {
			nodesListSingleton.INSTANCE.add(node);
			return nodesListSingleton.INSTANCE.getList();
		}

		@GetMapping("/")
		List<Integer> getRoot() {
			return Arrays.asList(new Integer[3]);
		}
		
		@GetMapping("/version")
		String getVersion() {
			return restfulApiConfiguration.version();
		}
	}
	
	static class Node {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	static enum nodesListSingleton {
		INSTANCE;
		private final Map<Integer, Node> map;
		nodesListSingleton() {
			map = new ConcurrentHashMap<Integer, Node>();
		}
		void add(Node object) {
			map.put(new java.security.SecureRandom().nextInt(), object);
		}
		List<Node> getList() {
			return new ArrayList<Node>(map.values());
		}
	}
	
	@ConfigurationProperties
	record RestfulApiConfiguration(Integer maxElements, String version) { }

}
