package com.aziubin.spring.boot.mongodb;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.0.6&packaging=jar&jvmVersion=17&groupId=com.aziubin.spring.boot&artifactId=mongodb&name=mongodb&description=Demo%20project%20for%20Spring%20Boot%20mongodb&packageName=com.aziubin.spring.boot.mongodb&dependencies=devtools,lombok,web,data-mongodb
 *
 */
@SpringBootApplication
@EnableMongoAuditing
public class MongodbApplication {
	static final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
	Logger logger = LoggerFactory.getLogger(MongodbApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MongodbApplication.class, args);
	}
	
	@Document
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	@Builder
	static class RuntimeSnapshot {
		//@Id UUID uuid; // org.springframework.dao.InvalidDataAccessApiUsageException: Cannot autogenerate id of type java.util.UUID for entity of type com.aziubin.spring.boot.mongodb.MongodbApplication$RuntimeSnapshot
		@Id String id;
		
		@Indexed(unique = true)
		long pid;
		java.util.List<String> inputArguments;
		long uptime;
		long startTime;
		java.util.Map<String, String> systemProperties;

		@CreatedDate
	    //private long createdDate;  // createdDate 0
		private Long createdDate;  // not included 

	    @LastModifiedDate
	    private long modifiedDate;
	    
	    //@DBRef
	    // org.springframework.data.mapping.MappingException: No id property found on class class [Ljava.lang.StackTraceElement; at org.springframework.data.mongodb.core.convert.MappingMongoConverter.createDBRef
	    //Map<Thread, StackTraceElement[]> stackTraces;
	    // Cannot use a complex object as a key value org.springframework.data.mapping.MappingException: Cannot use a complex object as a key value
	    // Can't find a codec for CodecCacheKey{clazz=class java.lang.StackTraceElement, types=null}.
	    //Collection<StackTraceElement[]> stackTraces;
	    //StackTraceElement[] stackTraces;

	    @DBRef
	    //@CascadeSave
//	    @DocumentReference
	    Machine machine;
	}
	
	@Document
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	@Builder
	static class Machine {
		@Id String id;
		String name;

		@CreatedDate
	    private Long createdDate;

	    @LastModifiedDate
	    private Long modifiedDate;
	}

	@RestController
	static class JvmSnapshotController {
		Logger logger = LoggerFactory.getLogger(JvmSnapshotController.class);

		Machine machine = null;
		
		@Autowired
		JvmSnapshotRepository jvmSnapshotRepository;
		
		@Autowired
		JvmMachineRepository jvmMachineRepository;

		@Autowired
		TemplateMongodbRepository templateMongodbREpository;		
		
		@Autowired
		void setMapKeyDotReplacement(MappingMongoConverter mappingMongoConverter) {
		    mappingMongoConverter.setMapKeyDotReplacement("_");
		}
		
	    @Bean
	    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
	        return new MongoTransactionManager(dbFactory);
	    }

		@Transactional // ignored
		@GetMapping("/*")
		List<RuntimeSnapshot> getJvmSnaphots() throws Exception {
			//new RuntimeSnapshot(UUID.randomUUID(),runtimeMXBean.getPid(), runtimeMXBean.getInputArguments(), runtimeMXBean.getUptime(), runtimeMXBean.getStartTime(), runtimeMXBean.getSystemProperties());
//			new RuntimeSnapshot();
			RuntimeSnapshot runtimeSnapshot;
			try {
				
				// todo upsert
				if (null == machine) {
			        Example<Machine> example = Example.of(Machine.builder().name(InetAddress.getLocalHost().getHostName()).build());
			        long start = System.nanoTime();
					List<Machine> machines = jvmMachineRepository.findAll(example);
					// endpoint that was really bothering me with its 700 - 1000ms processing time required. Other endpoints also had respectively rather high fluctuating processing times anywhere between 100 to 400ms
			        logger.info("findAll {} ", System.nanoTime() - start / 1000. / 1000. / 1000.);  // 1.3  

					if (0 != machines.size()) {
						machine = machines.get(0);
					} else {
						machine = Machine.builder().name(InetAddress.getLocalHost().getHostName()).build();
						jvmMachineRepository.save(machine);
					}
				}

				runtimeSnapshot = RuntimeSnapshot.builder().id(UUID.randomUUID().toString()).pid(runtimeMXBean.getPid())
						.inputArguments(runtimeMXBean.getInputArguments()).uptime(runtimeMXBean.getUptime())
						.startTime(runtimeMXBean.getStartTime())
						.systemProperties(runtimeMXBean.getSystemProperties()) // org.springframework.data.mapping.MappingException: Map key java.specification.version contains dots but no replacement was configured; Make sure map keys don't contain dots in the first place or configure an appropriate replacement
						//.stackTraces(Thread.getAllStackTraces().values().iterator().next()) // "stackTraces":[]
						//.stackTraces(Thread.getAllStackTraces().values().stream().findFirst().get()) // "stackTraces":[]
						 // org.springframework.data.mapping.MappingException: Cannot create a reference to an object with a NULL id
						.machine(machine)						
						.build();
		        long start = System.nanoTime();
//		        if (start > 0) {
//		        	throw new Exception("Rollbvack transaction"); // (-) created new _class "com.aziubin.spring.boot.mongodb.MongodbApplication$Machine"
//		        }
				jvmSnapshotRepository.save(runtimeSnapshot);
		        logger.info("save {} ", System.nanoTime() - start / 1000. / 1000. / 1000.);
				return jvmSnapshotRepository.findAll(runtimeMXBean.getPid());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@GetMapping("/delete")
		List<RuntimeSnapshot> deleteJvmSnaphots() {
			jvmSnapshotRepository.deleteAll();
			//jvmSnapshotRepository.deleteById(null);
			return jvmSnapshotRepository.findAll();
		}
	}

}
