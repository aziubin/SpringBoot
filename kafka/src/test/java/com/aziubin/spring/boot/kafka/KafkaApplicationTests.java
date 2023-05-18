package com.aziubin.spring.boot.kafka;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.kafka.common.Uuid;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import com.aziubin.spring.boot.kafka.domain.CustomerVisitEvent;

@SpringBootTest
class KafkaApplicationTests {
    private static final String CUSTOMER_VISIT_EVENT = "customer_visit_event";
//    private final List<String> messageList = new ArrayList<>();
    private final BlockingQueue<String> q = new ArrayBlockingQueue<>(15);

    @Autowired
    KafkaTemplate<String, String> stringkafkaTemplate;

    @KafkaListener(topics = CUSTOMER_VISIT_EVENT)
    void consume(String message) throws InterruptedException {
        q.put(message);
    }

	@Test
	void StringProducerConsumer() throws InterruptedException, ExecutionException, TimeoutException {
	    String payload = Uuid.randomUuid().toString();
	    stringkafkaTemplate.send(CUSTOMER_VISIT_EVENT, payload).get(10, TimeUnit.SECONDS);
	    String s = q.poll(10, TimeUnit.SECONDS);
	    assertNotNull(s);
	    assertEquals(payload, s);
	}

    @Autowired
    KafkaTemplate<String, CustomerVisitEvent> customerVisitEventKafkaTemplate;

    @Test
    void CustomerVisitEventProducerConsumer() throws InterruptedException, ExecutionException, TimeoutException {
        CustomerVisitEvent customerVisitEvent = CustomerVisitEvent.builder().customerId(UUID.randomUUID().toString()).dateTime(LocalDateTime.now()).build();
        customerVisitEventKafkaTemplate.send(CUSTOMER_VISIT_EVENT, customerVisitEvent).get(10, TimeUnit.SECONDS);

        String s = q.poll(10, TimeUnit.SECONDS);
        assertNotNull(s);
//        assertEquals(payload, s);
    }

}
