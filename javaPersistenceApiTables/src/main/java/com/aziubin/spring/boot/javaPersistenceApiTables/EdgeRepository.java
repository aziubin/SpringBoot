package com.aziubin.spring.boot.javaPersistenceApiTables;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EdgeRepository extends CrudRepository<EdgeDevice, Long> {
	//@Query(value = "SELECT f FROM FeedbackApp f JOIN FETCH f.profiles p WHERE p.id = ?1 AND p.tenant.id = ?2")
	//HQL
	//@Query(value = "SELECT e FROM java_persistence_api_tables_application$edge_device e LEFT JOIN FETCH e.location WHERE e.id = ?1")
	//@Query(value = "SELECT e FROM JavaPersistenceApiTablesApplication.EdgeDevice e LEFT JOIN FETCH e.location WHERE e.id = ?1")
	//@Query(value = "SELECT e FROM EdgeDevice e LEFT JOIN FETCH e.location WHERE e.id = ?1")
	// + @Query(value = "SELECT e FROM EdgeDevice e WHERE e.id = ?1")
	@Query(value = "SELECT e FROM EdgeDevice e JOIN FETCH e.location l WHERE e.id = ?1")  // Hibernate: select e1_0.id,e1_0.address,e1_0.firmware,l1_0.id,l1_0.name from edge_device e1_0 join "java_persistence_api_tables_application$location" l1_0 on l1_0.id=e1_0."location_id" where e1_0.id=?
// todo	@Query(value = "SELECT e FROM com.aziubin.spring.boot.javaPersistenceApiTables.JavaPersistenceApiTablesApplication EdgeDevice e JOIN FETCH e.location l WHERE e.id = ?1")

	Set<EdgeDevice> getEdgeDeviceById(long id);

}
