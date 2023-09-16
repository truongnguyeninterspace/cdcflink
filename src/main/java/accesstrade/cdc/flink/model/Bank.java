/**
 * Copyright © 2023 Interspace Co., Ltd. All rights reserved.
 *
 * Licensed under the Interspace's License,
 * you may not use this file except in compliance with the License.
 */
package accesstrade.cdc.flink.model;

import java.io.Serializable;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

/**
 * purpose of the class
 *
 * @author Truong
 */
public class Bank implements Serializable {

    @JsonProperty("BANK_ID")
    @Id
    private String bankId;
    @JsonProperty("BANK_NAME")
    private String name;
    @JsonProperty("DESCRIPTION")
    private String description;
    @JsonProperty("CREATED_ON")
    private String createOn;
    @JsonProperty("UPDATED_ON")
    private String updateOn;
    @JsonProperty("COUNTRY_CODE")
    private String countryCode;

    public Bank() {
    }

    public Bank(String bankId, String name, String description, String createOn,
            String updateOn, String countryCode) {
        this.bankId = bankId;
        this.name = name;
        this.description = description;
        this.createOn = createOn;
        this.updateOn = updateOn;
        this.countryCode = countryCode;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateOn() {
        return createOn;
    }

    public void setCreateOn(String createOn) {
        this.createOn = createOn;
    }

    public String getUpdateOn() {
        return updateOn;
    }

    public void setUpdateOn(String updateOn) {
        this.updateOn = updateOn;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
