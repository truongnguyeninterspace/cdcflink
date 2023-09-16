/**
 * Copyright © 2023 Interspace Co., Ltd. All rights reserved.
 *
 * Licensed under the Interspace's License,
 * you may not use this file except in compliance with the License.
 */
package accesstrade.cdc.flink.filter;

import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * purpose of the class
 *
 * @author Truong
 */
public class JsonFilter extends RichFilterFunction<ObjectNode> {

    @Override
    public boolean filter(ObjectNode value) throws Exception {
        JsonNode children = value.get("value");
        if (children == null)
            return false;
        return true;
    }
}
