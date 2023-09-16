/**
 * Copyright © 2023 Interspace Co., Ltd. All rights reserved.
 *
 * Licensed under the Interspace's License,
 * you may not use this file except in compliance with the License.
 */
package accesstrade.cdc.flink.map;

import accesstrade.cdc.flink.model.JsonLog;
import accesstrade.cdc.flink.model.RedoLog;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * purpose of the class
 *
 * @author Truong
 */
public class JsonMap extends RichMapFunction<ObjectNode, JsonLog> {

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
    }

    @Override
    public void close() throws Exception {
        super.close();
    }

    @Override
    public RedoLog map(ObjectNode objectNode) throws Exception {
        JsonNode children = objectNode.get("value");
        if(children == null)
            return new RedoLog();
        JsonNode schema = children.get("schema");
        JsonNode payload = children.get("payload");
        JsonNode after = payload.get("after");
        JsonNode before = payload.get("before");
        String operation = payload.get("op").asText();
        RedoLog redoLog = new RedoLog(schema, payload, after, before,
                operation);
        return redoLog;
    }
}
