/**
 * Copyright © 2023 Interspace Co., Ltd. All rights reserved.
 *
 * Licensed under the Interspace's License,
 * you may not use this file except in compliance with the License.
 */
package accesstrade.cdc.flink.model;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;

/**
 * purpose of the class
 *
 * @author Truong
 */
public class RedoLog {

    private JsonNode schema;
    private JsonNode payload;
    private JsonNode after;
    private JsonNode before;
    private String op;

    public RedoLog() {
    }

    public RedoLog(JsonNode schema, JsonNode payload, JsonNode after, JsonNode before,
            String op) {
        this.schema = schema;
        this.payload = payload;
        this.after = after;
        this.before = before;
        this.op = op;
    }

    public JsonNode getSchema() {
        return schema;
    }

    public void setSchema(JsonNode schema) {
        this.schema = schema;
    }

    public JsonNode getPayload() {
        return payload;
    }

    public void setPayload(JsonNode payload) {
        this.payload = payload;
    }

    public JsonNode getAfter() {
        return after;
    }

    public void setAfter(JsonNode after) {
        this.after = after;
    }

    public JsonNode getBefore() {
        return before;
    }

    public void setBefore(JsonNode before) {
        this.before = before;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }
}
