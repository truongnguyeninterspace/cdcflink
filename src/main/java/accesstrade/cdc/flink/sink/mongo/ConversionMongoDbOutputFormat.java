/**
 * Copyright © 2023 Interspace Co., Ltd. All rights reserved.
 *
 * Licensed under the Interspace's License,
 * you may not use this file except in compliance with the License.
 */
package accesstrade.cdc.flink.sink.mongo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import accesstrade.cdc.flink.CDCApplicationContext;
import accesstrade.cdc.flink.model.RedoLog;
import accesstrade.cdc.flink.model.SalesLog;
import accesstrade.cdc.flink.repositories.SalesLogMongoDbRepository;
import accesstrade.cdc.flink.sink.OutPutFormat;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

/**
 * purpose of the class
 *
 * @author Truong
 */
public class ConversionMongoDbOutputFormat extends OutPutFormat<RedoLog> {

    private SalesLogMongoDbRepository salesLogMongoDbRepository;

    @Override
    public void open()
            throws IOException, ClassNotFoundException, InvocationTargetException,
            InstantiationException, IllegalAccessException, NoSuchMethodException {
        salesLogMongoDbRepository = CDCApplicationContext.getBean(
                SalesLogMongoDbRepository.class);
    }

    @Override
    public void writeRecord(RedoLog redoLog) throws IOException {
        try {
            SalesLog salesLog;
            switch (redoLog.getOp()) {
            case "c":
            case "r":
                salesLog = new SalesLog(redoLog.getAfter().get("SEQ_NO").asText(), redoLog.getAfter());
                salesLogMongoDbRepository.insert(salesLog);
                System.out.println("Save "+ redoLog.getPayload());
                break;
            case "u":
                salesLog = new SalesLog(redoLog.getAfter().get("SEQ_NO").asText(), redoLog.getAfter());
                salesLogMongoDbRepository.save(salesLog);
                System.out.println("Update "+ redoLog.getPayload());
                break;
            case "d":
                salesLogMongoDbRepository.deleteBySegNo(redoLog.getAfter().get("SEQ_NO").asText());
                System.out.println("Delete "+ redoLog.getPayload());
                break;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {

    }
}
