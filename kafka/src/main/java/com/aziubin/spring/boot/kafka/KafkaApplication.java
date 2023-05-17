package com.aziubin.spring.boot.kafka;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import domain.CustomerVisitEvent;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class KafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args);
	}

	@RestController
	static class RestfulController {
	    private static final String CUSTOMER_VISIT_EVENT = "customer_visit_event";

        Logger logger = LoggerFactory.getLogger(RestfulController.class);

	    @Autowired
	    KafkaTemplate<String, CustomerVisitEvent> customerVisitEventKafkaTemplate;

	    @Autowired
	    KafkaTemplate<String, String> stringkafkaTemplate;

	    @KafkaListener(topics = CUSTOMER_VISIT_EVENT, groupId = "group_id")
	    public void consume(String message) throws IOException {
	        logger.info(String.format("#### -&gt; Consumed message -&gt; %s", message));
	    }

	    @GetMapping("/")
	    void get() {

	        stringkafkaTemplate.send(CUSTOMER_VISIT_EVENT, UUID.randomUUID().toString());
	    }

	    @GetMapping("/customer_visit_event")
	    void getCustomerVisitEvent() {
	        CustomerVisitEvent customerVisitEvent = CustomerVisitEvent.builder().customerId(UUID.randomUUID().toString()).dateTime(LocalDateTime.now()).build();
	        customerVisitEventKafkaTemplate.send(CUSTOMER_VISIT_EVENT, customerVisitEvent);
	    }
	}
	
}
