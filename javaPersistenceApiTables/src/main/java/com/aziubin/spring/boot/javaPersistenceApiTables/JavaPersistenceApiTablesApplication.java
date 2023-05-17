package com.aziubin.spring.boot.javaPersistenceApiTables;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Set;

import org.hibernate.annotations.ManyToAny;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@SpringBootApplication
public class JavaPersistenceApiTablesApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaPersistenceApiTablesApplication.class, args);
	}
	
	@Entity
	static class DeviceArchitecture {
		@Id
		public long id;
		public String name;
	}

	@Entity
	static class Location {
		@Id @GeneratedValue public long id;
		public String name;
	}

	@RestController
	static class TablesRestController {

		@Autowired
		EdgeRepository edgeRepository;

		@GetMapping("/")
		List<EdgeDevice> getEdgeDevice() {
			ArrayList<EdgeDevice> edgeDevices = new ArrayList<>();
			// select e1_0.id,e1_0.address,e1_0."location_id" from "java_persistence_api_tables_application$edge_device" e1_0
			
			//Hibernate: select e1_0.id,e1_0.address,e1_0.firmware,e1_0."location_id" from edge_device e1_0 where e1_0.id=?
			//Hibernate: select l1_0.id,l1_0.name from "java_persistence_api_tables_application$location" l1_0 where l1_0.id=?			
			Set<EdgeDevice> set = edgeRepository.getEdgeDeviceById(1);
			
			for (EdgeDevice edgeDevice : edgeRepository.findAll()) {
				edgeDevices.add(edgeDevice);
			}
			return edgeDevices;
		}

// todo @Transactional(isolation = Isolation.READ_UNCOMMITTED)
		@GetMapping("/post")
		List<EdgeDevice> postEdgeDevice() {
			EdgeDevice edgeDevice = new EdgeDevice();
			edgeDevice.address = UUID.randomUUID().toString();
			edgeDevice.location = new Location();
			edgeDevice.location.name = UUID.randomUUID().toString();
//			Hibernate: insert into "java_persistence_api_tables_application$location" (name, id) values (?, ?)
//			Hibernate: insert into "java_persistence_api_tables_application$edge_device" (address, "location_id", id) values (?, ?, ?)
			
//			Hibernate: insert into "java_persistence_api_tables_application$location" (name, id) values (?, ?)
//			2023-05-06T18:11:31.637+03:00  WARN 88100 --- [nio-8080-exec-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 0, SQLState: 23505
//			2023-05-06T18:11:31.641+03:00 ERROR 88100 --- [nio-8080-exec-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : ERROR: duplicate key value violates unique constraint "java_persistence_api_tables_application$location_pkey"
//			  Detail: Key (id)=(0) already exists.
//			2023-05-06T18:11:31.732+03:00  INFO 88100 --- [nio-8080-exec-1] o.h.e.j.b.internal.AbstractBatchImpl     : HHH000010: On release of batch it still contained JDBC statements

			edgeRepository.save(edgeDevice);
			return getEdgeDevice();
		}
	}

}
