/**
 * Copyright © 2022 Interspace Co., Ltd. All rights reserved.
 *
 * Licensed under the Interspace's License,
 * you may not use this file except in compliance with the License.
 */
package accesstrade.cdc.flink.repositories;

import accesstrade.cdc.flink.model.SalesLog;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * purpose of the class
 *
 * @author Ngo Van Nguyen
 */
public interface SalesLogMongoDbRepository extends MongoRepository<SalesLog, String> {
    void deleteBySegNo(String segNo);
}
