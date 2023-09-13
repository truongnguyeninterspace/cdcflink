/**
 * Copyright © 2022 Interspace Co., Ltd. All rights reserved.
 *
 * Licensed under the Interspace's License,
 * you may not use this file except in compliance with the License.
 */
package accesstrade.cdc.flink.repositories;

import java.io.Serializable;

import accesstrade.cdc.flink.model.Bank;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Repository;

/**
 * purpose of the class
 *
 * @author Ngo Van Nguyen
 */
public class MongoDbRepository implements  Serializable{
    MongoClient mongoClient;
    MongoDatabase database;
    MongoCollection<Document> collection;

    public MongoDbRepository() {
        mongoClient = new MongoClient("localhost", 27017);

        MongoCredential credential;
        credential = MongoCredential.createCredential("root", "local",
                "example".toCharArray());
        System.out.println("Connected to the database successfully");

        database = mongoClient.getDatabase("local");
        System.out.println("Credentials ::" + credential);
        collection = database.getCollection("bank");
    }
    public void  save(Bank bank){
        collection.insertOne(new Document(bank.getBankId(), bank));
    }
}
