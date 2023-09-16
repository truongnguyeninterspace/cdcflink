/**
 * Copyright © 2023 Interspace Co., Ltd. All rights reserved.
 *
 * Licensed under the Interspace's License,
 * you may not use this file except in compliance with the License.
 */
package accesstrade.cdc.flink.sink;

import java.io.Serializable;

import accesstrade.cdc.flink.CDCApplicationContext;
import accesstrade.cdc.flink.model.JsonLog;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

/**
 * purpose of the class
 *
 * @author Truong
 */
public class OutputSink extends RichSinkFunction<JsonLog> implements Serializable {
    private OutPutFormat<JsonLog> outputFormat;

    @Override
    public void invoke(JsonLog value, Context context) throws Exception {
        outputFormat.writeRecord(value);
    }
    @Override
    public void open(Configuration parameters) throws Exception {
        outputFormat = CDCApplicationContext.getBean(OutPutFormat.class);
        outputFormat.open();
    }
    @Override
    public void close() throws Exception {
        super.close();
    }
}
