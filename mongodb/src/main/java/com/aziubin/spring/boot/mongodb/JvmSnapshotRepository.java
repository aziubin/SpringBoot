package com.aziubin.spring.boot.mongodb;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface JvmSnapshotRepository extends MongoRepository<MongodbApplication.RuntimeSnapshot, String> {

    @Query(value="{pid:?0}", fields="{'name' : 1, 'pid' : 1}")
    List<MongodbApplication.RuntimeSnapshot> findAll(Long pid);

}
