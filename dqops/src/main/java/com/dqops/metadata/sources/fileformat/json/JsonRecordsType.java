/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.sources.fileformat.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum JsonRecordsType {
    @JsonProperty("auto")
    auto("auto"),

    @JsonProperty("true")
    true_value("true"),

    @JsonProperty("false")
    false_value("false");

    private final String value;

    JsonRecordsType(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

}
