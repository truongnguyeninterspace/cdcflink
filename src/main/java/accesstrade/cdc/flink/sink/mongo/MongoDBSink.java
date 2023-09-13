/**
 * Copyright © 2023 Interspace Co., Ltd. All rights reserved.
 *
 * Licensed under the Interspace's License,
 * you may not use this file except in compliance with the License.
 */
package accesstrade.cdc.flink.sink.mongo;

import java.io.IOException;
import java.io.Serializable;

import accesstrade.cdc.flink.model.RedoLog;
import org.apache.flink.api.connector.sink2.Sink;
import org.apache.flink.api.connector.sink2.SinkWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * purpose of the class
 *
 * @author Truong
 */
@Component
public class MongoDBSink implements Sink<RedoLog>, Serializable {

    @Autowired
    private MongDBSinkWriter writer;

    @Override
    public SinkWriter<RedoLog> createWriter(InitContext initContext) throws IOException {
        return writer;
    }
}
