/**
 * Copyright © 2023 Interspace Co., Ltd. All rights reserved.
 *
 * Licensed under the Interspace's License,
 * you may not use this file except in compliance with the License.
 */
package accesstrade.cdc.flink;

import java.util.Properties;

import accesstrade.cdc.flink.deserialize.JSONValueDeserializationSchema;
import accesstrade.cdc.flink.map.JsonMap;
import accesstrade.cdc.flink.model.RedoLog;
import accesstrade.cdc.flink.repositories.MongoDbRepository;
import accesstrade.cdc.flink.sink.file.FileSink;
import accesstrade.cdc.flink.sink.mongo.MongoDBSink;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * purpose of the class
 *
 * @author Truong
 */
public class PipelineInit  {
    final static String inputTopic = "fix.C__DBZUSER.BANK";
    final static String jobTitle = "Stream";
    private  ApplicationContext applicationContext;
    private MongoDBSink mongoDBSink;

    private MongoDbRepository mongoClient;
    public PipelineInit(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.mongoDBSink = applicationContext.getBean(MongoDBSink.class);
    }


    public void init(){
        final StreamExecutionEnvironment streamExecutionEnvironment =
                StreamExecutionEnvironment.getExecutionEnvironment();

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:29092");
        properties.put("group.id", "test");

        KafkaSource<ObjectNode> source = KafkaSource.<ObjectNode>builder()
                .setBootstrapServers("localhost:29092").setTopics(inputTopic)
                .setGroupId("my-group1").setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new JSONValueDeserializationSchema()).build();

//        FlinkKafkaConsumer<ObjectNode> source =
//                new FlinkKafkaConsumer<>(inputTopic,
//                        new JSONValueDeserializationSchema(), properties );
//        DataStream<ObjectNode> stream = streamExecutionEnvironment.addSource(source);

        DataStream<ObjectNode> stream = streamExecutionEnvironment
                .fromSource(source, WatermarkStrategy.noWatermarks(),
                "Kafka Source");
        DataStream<RedoLog> streamRedoLog = stream.map(new JsonMap());
        FileSink sink = new FileSink();
        streamRedoLog.sinkTo(sink);
        // Execute program
        try{
            streamExecutionEnvironment.execute(jobTitle);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

}
