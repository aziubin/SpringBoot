package com.aziubin.spring.boot.javaPersistenceApi;

import java.util.List;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@SpringBootApplication
public class JavaPersystenceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaPersystenceApiApplication.class, args);
	}

//	@Entity
//	// org.hibernate.InstantiationException: No default constructor for entity:  : com.aziubin.spring.boot.javaPersistenceApi.JavaPersystenceApiApplication$CloudNode
//	// record CloudNode (@Id @GeneratedValue(strategy = GenerationType.AUTO) Integer id, String name) {}
//	class CloudNode {
//		@Id @GeneratedValue(strategy = GenerationType.AUTO) Integer id;
//		String name;
//		
////		public CloudNode() {
////			super();
////		};
////
////		public CloudNode(Integer id, String name) {
////			super();
////			this.id = id;
////			this.name = name;
////		}
////
////		public CloudNode(String name) {
////			super();
////			this.name = name;
////		}
//		public Integer getId() {
//			return id;
//		}
//		public void setId(Integer id) {
//			this.id = id;
//		}
//		public String getName() {
//			return name;
//		}
//		public void setName(String name) {
//			this.name = name;
//		}
//	}
	
	@RestController
	class JpaRestController {
		
		@Autowired
		NodeRepository nodeRepository;
		
		@GetMapping("/")
		public List<CloudNode> getNodes() {
			var cloudNode = new CloudNode();
			try {
				cloudNode.name = InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//nodeRepository.save(new CloudNode(1, "rfgsfgdsdf"));
			nodeRepository.save(cloudNode);
			List<CloudNode> target = new ArrayList<>();
			nodeRepository.findAll().forEach(target::add);
			return target;
		}
	}
	
}
