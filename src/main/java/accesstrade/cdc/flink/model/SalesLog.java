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
public class SalesLog {

    private String SegNo;
    private JsonNode payLoad;

    public SalesLog(String segNo, JsonNode payLoad) {
        SegNo = segNo;
        this.payLoad = payLoad;
    }

    public SalesLog() {
    }

    public String getSegNo() {
        return SegNo;
    }

    public void setSegNo(String segNo) {
        SegNo = segNo;
    }

    public JsonNode getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(JsonNode payLoad) {
        this.payLoad = payLoad;
    }
}
