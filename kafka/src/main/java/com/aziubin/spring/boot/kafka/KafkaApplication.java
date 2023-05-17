package com.aziubin.spring.boot.kafka;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class KafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args);
	}

	@RestController
	static class RestfulController {
	    @Autowired
	    KafkaTemplate<String, String> kafkaTemplate;

	    @GetMapping("/")
	    void get() {
	        kafkaTemplate.send("customer_visit_event", UUID.randomUUID().toString());
	    }

	    @GetMapping("/customer_visit_event")
	    void getCustomerVisitEvent() {
	        kafkaTemplate.send("customer_visit_event", UUID.randomUUID().toString());
	    }
	}
	
}
