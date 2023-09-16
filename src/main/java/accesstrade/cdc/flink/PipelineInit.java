/**
 * Copyright © 2023 Interspace Co., Ltd. All rights reserved.
 *
 * Licensed under the Interspace's License,
 * you may not use this file except in compliance with the License.
 */
package accesstrade.cdc.flink;

import java.util.Properties;

import accesstrade.cdc.flink.deserialize.JSONValueDeserializationSchema;
import accesstrade.cdc.flink.filter.JsonFilter;
import accesstrade.cdc.flink.map.JsonMap;
import accesstrade.cdc.flink.model.JsonLog;
import accesstrade.cdc.flink.sink.OutputSink;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * purpose of the class
 *
 * @author Truong
 */
@Component
public class PipelineInit  {

    @Autowired
    private Environment environment;

    public void init(){
        final StreamExecutionEnvironment streamExecutionEnvironment =
                StreamExecutionEnvironment.getExecutionEnvironment();

        String kafkaServer = environment.getProperty("flink.kafka.bootstrap.servers");
        String groupId = environment.getProperty("flink.kafka.group.id");
        String topic = environment.getProperty("flink.kafka.topic");
        String jobName = environment.getProperty("flink.title");

//        Properties properties = new Properties();
//        properties.put("bootstrap.servers", "localhost:29092");
//        properties.put("group.id", "test");

        KafkaSource<ObjectNode> source = KafkaSource.<ObjectNode>builder()
                .setBootstrapServers(kafkaServer).setTopics(topic)
                .setGroupId(groupId).setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new JSONValueDeserializationSchema()).build();

//        FlinkKafkaConsumer<ObjectNode> source =
//                new FlinkKafkaConsumer<>(inputTopic,
//                        new JSONValueDeserializationSchema(), properties );
//        DataStream<ObjectNode> stream = streamExecutionEnvironment.addSource(source);

        DataStream<ObjectNode> stream = streamExecutionEnvironment
                .fromSource(source, WatermarkStrategy.noWatermarks(),
                "Kafka Source");
        DataStream<ObjectNode> filteredStream = stream.filter(new JsonFilter());
        DataStream<JsonLog> streamLog = filteredStream.map(new JsonMap());
        streamLog.addSink(new OutputSink());
        // Execute program
        try{
            streamExecutionEnvironment.execute(jobName);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

}
