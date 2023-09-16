/**
 * Copyright © 2023 Interspace Co., Ltd. All rights reserved.
 *
 * Licensed under the Interspace's License,
 * you may not use this file except in compliance with the License.
 */
package accesstrade.cdc.flink.sink.mongo;

import java.io.IOException;

import accesstrade.cdc.flink.CDCApplicationContext;
import accesstrade.cdc.flink.model.Bank;
import accesstrade.cdc.flink.model.RedoLog;
import accesstrade.cdc.flink.repositories.BankMongoDbRepository;
import accesstrade.cdc.flink.sink.OutPutFormat;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

/**
 * purpose of the class
 *
 * @author Truong
 */
public class MongoDBOutputFormat extends OutPutFormat<RedoLog> {

    private BankMongoDbRepository bankMongoDbRepository;
    @Override
    public void open() {
        bankMongoDbRepository = CDCApplicationContext.getBean(BankMongoDbRepository.class);
    }

    @Override
    public void writeRecord(RedoLog redoLog) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Bank bank;
            switch (redoLog.getOp()) {
            case "c":
                 bank = objectMapper.readValue(redoLog.getAfter().traverse(),
                        Bank.class);
                bankMongoDbRepository.insert(bank);
                System.out.println("Save "+ redoLog.getPayload());
                break;
            case "u":
                bank = objectMapper.readValue(redoLog.getAfter().traverse(),
                        Bank.class);
                 bankMongoDbRepository.save(bank);
                System.out.println("Update "+ redoLog.getPayload());
                break;
            case "d":
                bank = objectMapper.readValue(redoLog.getBefore().traverse(),
                        Bank.class);
                bankMongoDbRepository.deleteBankByBankId(bank.getBankId());
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
