package com.aziubin.spring.boot.javaPersistenceApiTables;

import com.aziubin.spring.boot.javaPersistenceApiTables.JavaPersistenceApiTablesApplication.Location;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
class EdgeDevice {
	@Id @GeneratedValue public long id;
    //EntityManager ent; 

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
	// any HQL (with fetch or not) query with FROM EdgeDevice will bring all locations
	public Location location;
	
//	FetchType.LAZY
//	set	LinkedHashSet<E>  (id=202)	
//	[0]	EdgeDevice  (id=205)	
//		address	"bf0a3fc3-b22a-411b-bfa6-d1303d781c9a" (id=208)	
//		firmware	null	
//		id	1	
//		location	JavaPersistenceApiTablesApplication$Location$HibernateProxy$mk3nBCDI  (id=209)	
//			$$_hibernate_interceptor	ByteBuddyInterceptor  (id=215)	
//				allowLoadOutsideTransaction	false	
//				componentIdType	null	
//				entityName	"com.aziubin.spring.boot.javaPersistenceApiTables.JavaPersistenceApiTablesApplication$Location" (id=248)	
//				getIdentifierMethod	null	
//				id	Long  (id=249)	
//				initialized	true	
//				interfaces	Class<T>[1]  (id=253)	
//				overridesEquals	false	
//				persistentClass	Class<T> (com.aziubin.spring.boot.javaPersistenceApiTables.JavaPersistenceApiTablesApplication$Location) (id=156)	
//				readOnly	false	
//				readOnlyBeforeAttachedToSession	null	
//				replacement	null	
//				session	SessionImpl  (id=261)	
//				sessionFactoryUuid	null	
//				setIdentifierMethod	null	
//				target	JavaPersistenceApiTablesApplication$Location  (id=280)	
//				unwrap	false	
//			id	0	
//			name	null	
	
//			[com.aziubin.spring.boot.javaPersistenceApiTables.EdgeDevice@10df72ae]
//			com.aziubin.spring.boot.javaPersistenceApiTables.EdgeDevice@10df72ae
//			bf0a3fc3-b22a-411b-bfa6-d1303d781c9a
//			null
//			1
//			com.aziubin.spring.boot.javaPersistenceApiTables.JavaPersistenceApiTablesApplication$Location@6971a37e
//			org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor@39ca222b
//			false
//			null
//			com.aziubin.spring.boot.javaPersistenceApiTables.JavaPersistenceApiTablesApplication$Location
//			null
//			202
//			true
//			[interface org.hibernate.proxy.HibernateProxy]
//			false
//			class com.aziubin.spring.boot.javaPersistenceApiTables.JavaPersistenceApiTablesApplication$Location
//			false
//			null
//			null
//			SessionImpl(1485768880<open>)
//			null
//			null
//			com.aziubin.spring.boot.javaPersistenceApiTables.JavaPersistenceApiTablesApplication$Location@6971a37e
//			false
//			0
//			null	

	@Lob
	byte[] firmware;
//	Hibernate: create table edge_device (id bigint not null, address varchar(255), firmware oid, "location_id" bigint not null, primary key (id))
//	Hibernate: create sequence edge_device_seq start with 1 increment by 50
//	Hibernate: alter table if exists edge_device add constraint FKeklrehruueodv0c52h4l271a1 foreign key ("location_id") references "java_persistence_api_tables_application$location"
}

//public class EdgeDevice {
//
//}


