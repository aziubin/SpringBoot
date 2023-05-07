package com.aziubin.spring.boot.mongodb;

public interface TemplateMongodbRepository {
    void updateItemQuantity(String name, float newQuantity);

}

