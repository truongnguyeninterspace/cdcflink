/**
 * Copyright © 2023 Interspace Co., Ltd. All rights reserved.
 *
 * Licensed under the Interspace's License,
 * you may not use this file except in compliance with the License.
 */
package accesstrade.cdc.flink.sink.file;

import java.io.IOException;

import accesstrade.cdc.flink.model.RedoLog;
import org.apache.flink.api.connector.sink2.Sink;
import org.apache.flink.api.connector.sink2.SinkWriter;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * purpose of the class
 *
 * @author Truong
 */
public class FileSink implements Sink<RedoLog> {

    @Override
    public SinkWriter<RedoLog> createWriter(InitContext initContext) throws IOException {
        return new FileSinkWriter();
    }
}
