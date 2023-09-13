/**
 * Copyright © 2022 Interspace Co., Ltd. All rights reserved.
 *
 * Licensed under the Interspace's License,
 * you may not use this file except in compliance with the License.
 */
package accesstrade.cdc.flink.model;

/**
 * purpose of the class
 *
 * @author Ngo Van Nguyen
 */
public enum Operation {
    CREATE("c"),
    DELETE("d"),
    UPDATE("u");

    Operation(String value) {
        this.value = value;
    }
    private String value;
}
