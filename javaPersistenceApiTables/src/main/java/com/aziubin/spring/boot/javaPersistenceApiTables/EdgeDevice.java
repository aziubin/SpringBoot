package com.aziubin.spring.boot.javaPersistenceApiTables;

import com.aziubin.spring.boot.javaPersistenceApiTables.JavaPersistenceApiTablesApplication.Location;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
class EdgeDevice {
	@Id @GeneratedValue public long id;
	public String address;

//	@ManyToOne(optional = false)
//	DeviceArchitecture deviceArchitecture;
//	//@Formula("sub_total + (sub_total * tax)")
//
	// @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	// @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	// Hibernate: select e1_0.id,e1_0.address,e1_0."location_id" from "java_persistence_api_tables_application$edge_device" e1_0
	// com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) (through reference chain: java.util.ArrayList[0]->com.aziubin.spring.boot.javaPersistenceApiTables.JavaPersistenceApiTablesApplication$EdgeDevice["location"]->com.aziubin.spring.boot.javaPersistenceApiTables.JavaPersistenceApiTablesApplication$Location$HibernateProxy$XzZbHo0k["hibernateLazyInitializer"])
	@ManyToOne(optional = false, fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	//@JsonInclude(JsonInclude.Include.NON_NULL)
	//@Transient
	public Location location;
	
	@Lob
	byte[] firmware;
//	Hibernate: create table edge_device (id bigint not null, address varchar(255), firmware oid, "location_id" bigint not null, primary key (id))
//	Hibernate: create sequence edge_device_seq start with 1 increment by 50
//	Hibernate: alter table if exists edge_device add constraint FKeklrehruueodv0c52h4l271a1 foreign key ("location_id") references "java_persistence_api_tables_application$location"
}

//public class EdgeDevice {
//
//}


