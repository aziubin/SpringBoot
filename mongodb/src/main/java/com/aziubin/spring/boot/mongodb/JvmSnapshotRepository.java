package com.aziubin.spring.boot.mongodb;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface JvmSnapshotRepository extends MongoRepository<MongodbApplication.RuntimeSnapshot, String> {

}
