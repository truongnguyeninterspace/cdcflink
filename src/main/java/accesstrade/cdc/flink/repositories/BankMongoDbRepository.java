/**
 * Copyright © 2022 Interspace Co., Ltd. All rights reserved.
 *
 * Licensed under the Interspace's License,
 * you may not use this file except in compliance with the License.
 */
package accesstrade.cdc.flink.repositories;

import accesstrade.cdc.flink.model.Bank;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * purpose of the class
 *
 * @author Ngo Van Nguyen
 */
@Repository
public interface BankMongoDbRepository extends MongoRepository<Bank, String> {
    void deleteBankByBankId(String id);
}
