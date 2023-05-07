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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
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
		long pid;
		java.util.List<String> inputArguments;
		long uptime;
		long startTime;
		java.util.Map<String, String> systemProperties;

		@CreatedDate
	    private long createdDate;

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
	    private long createdDate;

	    @LastModifiedDate
	    private long modifiedDate;
	}

	@RestController
	static class JvmSnapshotController {
		@Autowired
		JvmSnapshotRepository jvmSnapshotRepository;
		
		@Autowired
		JvmMachineRepository jvmMachineRepository;
		
		@Autowired
		void setMapKeyDotReplacement(MappingMongoConverter mappingMongoConverter) {
		    mappingMongoConverter.setMapKeyDotReplacement("_");
		}
		
		@GetMapping("/*")
		List<RuntimeSnapshot> getJvmSnaphots() {
			//new RuntimeSnapshot(UUID.randomUUID(),runtimeMXBean.getPid(), runtimeMXBean.getInputArguments(), runtimeMXBean.getUptime(), runtimeMXBean.getStartTime(), runtimeMXBean.getSystemProperties());
//			new RuntimeSnapshot();
			RuntimeSnapshot runtimeSnapshot;
			try {
				Machine machine = Machine.builder().name(InetAddress.getLocalHost().getHostName()).build();
				jvmMachineRepository.save(machine);
				
				runtimeSnapshot = RuntimeSnapshot.builder().id(UUID.randomUUID().toString()).pid(runtimeMXBean.getPid())
						.inputArguments(runtimeMXBean.getInputArguments()).uptime(runtimeMXBean.getUptime())
						.startTime(runtimeMXBean.getStartTime())
						.systemProperties(runtimeMXBean.getSystemProperties()) // org.springframework.data.mapping.MappingException: Map key java.specification.version contains dots but no replacement was configured; Make sure map keys don't contain dots in the first place or configure an appropriate replacement
						//.stackTraces(Thread.getAllStackTraces().values().iterator().next()) // "stackTraces":[]
						//.stackTraces(Thread.getAllStackTraces().values().stream().findFirst().get()) // "stackTraces":[]
						 // org.springframework.data.mapping.MappingException: Cannot create a reference to an object with a NULL id
						.machine(machine)						
						.build();
				jvmSnapshotRepository.save(runtimeSnapshot);
				return jvmSnapshotRepository.findAll();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@GetMapping("/delete")
		List<RuntimeSnapshot> deleteJvmSnaphots() {
			jvmSnapshotRepository.deleteAll();
			return jvmSnapshotRepository.findAll();
		}
	}

}
