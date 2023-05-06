package com.aziubin.spring.boot.javaPersistenceApiTables;

import org.springframework.data.repository.CrudRepository;

import com.aziubin.spring.boot.javaPersistenceApiTables.JavaPersistenceApiTablesApplication.EdgeDevice;

public interface EdgeRepository extends CrudRepository<EdgeDevice, Long> {
	
}

