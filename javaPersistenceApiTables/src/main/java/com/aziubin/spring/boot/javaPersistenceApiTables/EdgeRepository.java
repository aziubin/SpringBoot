package com.aziubin.spring.boot.javaPersistenceApiTables;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EdgeRepository extends CrudRepository<EdgeDevice, Long> {
	//@Query(value = "SELECT f FROM FeedbackApp f JOIN FETCH f.profiles p WHERE p.id = ?1 AND p.tenant.id = ?2")
	//HQL
	//@Query(value = "SELECT e FROM java_persistence_api_tables_application$edge_device e LEFT JOIN FETCH e.location WHERE e.id = ?1")
	//@Query(value = "SELECT e FROM JavaPersistenceApiTablesApplication.EdgeDevice e LEFT JOIN FETCH e.location WHERE e.id = ?1")
	@Query(value = "SELECT e FROM EdgeDevice e LEFT JOIN FETCH e.location WHERE e.id = ?1")
    Set<EdgeDevice> getFeedbackAppsByProfileId(long id);

}
