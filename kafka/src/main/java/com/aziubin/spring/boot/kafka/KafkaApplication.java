package com.aziubin.spring.boot.kafka;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import domain.CustomerVisitEvent;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class KafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args);
	}

	//@Controller
	@RestController
	static class RestfulController {
	    private static final String CUSTOMER_VISIT_EVENT = "customer_visit_event";

        Logger logger = LoggerFactory.getLogger(RestfulController.class);

	    @Autowired
	    KafkaTemplate<String, CustomerVisitEvent> customerVisitEventKafkaTemplate;

	    @Autowired
	    KafkaTemplate<String, String> stringkafkaTemplate;

	    @KafkaListener(topics = CUSTOMER_VISIT_EVENT, groupId = "group_id")
	    public void consumeGroupIdA(String message) throws IOException {
	        logger.info(String.format("Consumed group_id a; %s", message));
	    }

	    @KafkaListener(topics = CUSTOMER_VISIT_EVENT, groupId = "group_id")
	    public void consumeGroupIdB(String message) throws IOException {
	        logger.info(String.format("Consumed group_id b; %s", message));
	    }

	    @KafkaListener(topics = CUSTOMER_VISIT_EVENT, groupId = "group_id")
	    public void consumeGroupIdC(String message) throws IOException {
	        logger.info(String.format("Consumed group_id c; %s", message));
	    }

	    @KafkaListener(topics = CUSTOMER_VISIT_EVENT, groupId = "group_id2")
	    public void consume2(String message, org.apache.kafka.clients.consumer.ConsumerRecord sdf) throws IOException {
	        logger.info(String.format("Consumed group_id2; %s", message));
	    }

	    @KafkaListener(topics = CUSTOMER_VISIT_EVENT, groupId = "group_id3")
	    public void consume3(String message, org.apache.kafka.clients.consumer.ConsumerRecord sdf) throws IOException {
	        logger.info(String.format("Consumed group_id3; %s", message));
	    }

	    @GetMapping("/")
	    //@ResponseStatus(value = HttpStatus.OK)
	    void get() {

	        stringkafkaTemplate.send(CUSTOMER_VISIT_EVENT, UUID.randomUUID().toString()).whenComplete((t, ex) -> {
	            if (null == ex) {
	                logger.info(t.toString());
	            } else {
                   logger.error("Producer " + CUSTOMER_VISIT_EVENT, ex);
	            }
	        });
	    }

	    @GetMapping("/customer_visit_event")
	    void getCustomerVisitEvent() {
	        CustomerVisitEvent customerVisitEvent = CustomerVisitEvent.builder().customerId(UUID.randomUUID().toString()).dateTime(LocalDateTime.now()).build();
	        customerVisitEventKafkaTemplate.send(CUSTOMER_VISIT_EVENT, customerVisitEvent);
	    }
	}
	
}
