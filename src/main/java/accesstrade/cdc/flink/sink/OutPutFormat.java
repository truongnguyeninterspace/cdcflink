/**
 * Copyright © 2023 Interspace Co., Ltd. All rights reserved.
 *
 * Licensed under the Interspace's License,
 * you may not use this file except in compliance with the License.
 */
package accesstrade.cdc.flink.sink;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * purpose of the class
 *
 * @author Truong
 */
public abstract class OutPutFormat<JsonLog> implements Serializable {

    public abstract void open()
            throws IOException, ClassNotFoundException, InvocationTargetException,
            InstantiationException, IllegalAccessException, NoSuchMethodException;

    public abstract void writeRecord(JsonLog jsonLog) throws IOException;

    public abstract void close() throws IOException;

}
