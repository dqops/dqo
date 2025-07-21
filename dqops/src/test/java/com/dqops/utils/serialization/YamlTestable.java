/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.serialization;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class YamlTestable implements DeserializationAware {
    @JsonIgnore
    public boolean wasOnDeserializedCalled;

    private String field1;
    private int int1;

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public int getInt1() {
        return int1;
    }

    public void setInt1(int int1) {
        this.int1 = int1;
    }

    /**
     * Called after the object was deserialized from JSON or YAML.
     */
    @Override
    public void onDeserialized() {
        this.wasOnDeserializedCalled = true;
    }
}
