/**
 * Copyright © 2023 Interspace Co., Ltd. All rights reserved.
 *
 * Licensed under the Interspace's License,
 * you may not use this file except in compliance with the License.
 */
package accesstrade.cdc.flink.sink.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import accesstrade.cdc.flink.model.Bank;
import accesstrade.cdc.flink.model.RedoLog;
import org.apache.flink.api.connector.sink2.SinkWriter;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * purpose of the class
 *
 * @author Truong
 */
public class FileSinkWriter implements SinkWriter<RedoLog> {

    @Override
    public void write(RedoLog redoLog, Context context)
            throws IOException, InterruptedException {

        File file = new File("append.txt");
        File tempFile = new File("myTempFile.txt");

        BufferedReader reader;
        BufferedWriter writer;
        ObjectMapper objectMapper = new ObjectMapper();
        Bank bank ;
        String currentLine;
        if(redoLog.getOp() == null)
            return;
        switch (redoLog.getOp()){
        case "c":
            FileWriter fr = new FileWriter(file, true);
            fr.write(redoLog.getAfter() + System.lineSeparator());
            fr.close();
            break;
        case "u":
            bank = objectMapper.readValue(redoLog.getAfter().traverse(),
                    Bank.class);
            reader= new BufferedReader(new FileReader(file));
             writer = new BufferedWriter(new FileWriter(tempFile));
            while((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if(trimmedLine.contains(bank.getBankId())){
                    writer.write(redoLog.getAfter() + System.getProperty("line.separator"));
                }else{
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
            }
            reader.close();
            writer.close();
            file.delete();
            tempFile.renameTo(file);
            break;
        case "d":
            bank = objectMapper.readValue(redoLog.getBefore().traverse(),
                    Bank.class);
            reader= new BufferedReader(new FileReader(file));
            writer = new BufferedWriter(new FileWriter(tempFile));
            while((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if(trimmedLine.contains(bank.getBankId())) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            reader.close();
            writer.close();
            file.delete();
            tempFile.renameTo(file);
            break;
        }
    }

    @Override
    public void flush(boolean b) throws IOException, InterruptedException {

    }

    @Override
    public void close() throws Exception {

    }
}
