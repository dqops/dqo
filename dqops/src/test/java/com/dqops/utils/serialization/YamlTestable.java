/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
