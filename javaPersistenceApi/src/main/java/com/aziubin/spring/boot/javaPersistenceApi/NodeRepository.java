package com.aziubin.spring.boot.javaPersistenceApi;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

//public interface NodeRepository extends CrudRepository<JavaPersystenceApiApplication.CloudNode, Integer> {
public interface NodeRepository extends CrudRepository<CloudNode, Integer> {
//public interface NodeRepository extends PagingAndSortingRepository<CloudNode, Integer> {
	
}

