/**
 * Copyright © 2023 Interspace Co., Ltd. All rights reserved.
 *
 * Licensed under the Interspace's License,
 * you may not use this file except in compliance with the License.
 */
package accesstrade.cdc.flink.sink.mongo;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

import accesstrade.cdc.flink.model.Bank;
import accesstrade.cdc.flink.model.RedoLog;
import accesstrade.cdc.flink.repositories.MongoDbRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObjectCodecProvider;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.geojson.codecs.GeoJsonCodecProvider;
import org.apache.flink.api.connector.sink2.SinkWriter;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import org.bson.codecs.BsonValueCodecProvider;
import org.bson.codecs.ValueCodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;

/**
 * purpose of the class
 *
 * @author Truong
 */
@Component
public class MongDBSinkWriter implements SinkWriter<RedoLog>, Serializable,
        InitializingBean {
    MongoClient mongoClient;
    MongoDatabase database;
    MongoCollection<Document> collection;


    @Override
    public void write(RedoLog redoLog, Context context)
            throws IOException, InterruptedException {
        CodecRegistry cr = fromProviders(new ValueCodecProvider(), new BsonValueCodecProvider(), new DBObjectCodecProvider(), new GeoJsonCodecProvider());

        mongoClient = new MongoClient("localhost", 27017);

        MongoCredential credential;
        credential = MongoCredential.createCredential("root", "admin",
                "example".toCharArray());
        System.out.println("Connected to the database successfully");

        database = mongoClient.getDatabase("admin");
        System.out.println("Credentials ::" + credential);
        collection = database.getCollection("bank");
        for(String col : database.listCollectionNames()){
            System.out.println(col);
        }
        FindIterable<Document> a = collection.find();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            switch (redoLog.getOp()) {
            case "c":
                Bank bank = objectMapper.readValue(redoLog.getAfter().traverse(),
                        Bank.class);
                Document document = new Document(bank.getBankId(), redoLog.getAfter());
                collection.insertOne(document);
                break;
            case "u":
            case "d":

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void flush(boolean b) throws IOException, InterruptedException {

    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
