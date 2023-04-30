package com.aziubin.spring.boot.javaPersistenceApi;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
// org.hibernate.InstantiationException: No default constructor for entity:  : com.aziubin.spring.boot.javaPersistenceApi.JavaPersystenceApiApplication$CloudNode
// record CloudNode (@Id @GeneratedValue(strategy = GenerationType.AUTO) Integer id, String name) {}
class CloudNode {
	@Id @GeneratedValue(strategy = GenerationType.AUTO) Integer id;
	String name;
	
//	public CloudNode() {
//		super();
//	};
//
//	public CloudNode(Integer id, String name) {
//		super();
//		this.id = id;
//		this.name = name;
//	}
//
//	public CloudNode(String name) {
//		super();
//		this.name = name;
//	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
