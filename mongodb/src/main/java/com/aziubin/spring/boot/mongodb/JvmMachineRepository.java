package com.aziubin.spring.boot.mongodb;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface JvmMachineRepository extends MongoRepository<MongodbApplication.Machine, String> {

    @Query("{name:'?0'}")
    MongodbApplication.Machine findItemByName(String name);

}
