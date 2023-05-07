package com.aziubin.spring.boot.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.core.query.UpdateDefinition;

import com.mongodb.client.result.UpdateResult;

import org.springframework.data.mongodb.repository.Query;

@Component
public class CustomMongodbRepository implements TemplateMongodbRepository {

    @Autowired
    MongoTemplate mongoTemplate;
    
	// Could not create query for public abstract void com.aziubin.spring.boot.mongodb.JvmMachineRepository.updateItemQuantity(java.lang.String,float); Reason: No property 'updateItemQuantity' found for type 'Machine'
    public void updateItemQuantity(String name, float newQuantity) {
//        Query query = new Query(Criteria.where("name").is(name));
//        UpdateDefinition update = new UpdateDefinition();
//        update.set("quantity", newQuantity);
//        
//        UpdateResult result = mongoTemplate.updateFirst(query, update, GroceryItem.class);
//        
//        if(result == null)
//            System.out.println("No documents updated");
//        else
//            System.out.println(result.getModifiedCount() + " document(s) updated..");
//
    }

}
