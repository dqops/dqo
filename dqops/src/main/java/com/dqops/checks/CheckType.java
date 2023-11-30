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
package com.dqops.checks;

import com.dqops.utils.docs.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.parquet.Strings;

/**
 * Enumeration of data quality check types: profiling, monitoring, partitioned.
 */
public enum CheckType {
    @JsonProperty("profiling")
    profiling("profiling"),

    @JsonProperty("monitoring")
    monitoring("monitoring"),

    @JsonProperty("partitioned")
    partitioned("partitioned");

    private final String displayName;

    CheckType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns a lower case display name used for the check. The value is stored in parquet files.
     * @return Lower case display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Converts a string to an enum value.
     * @param value String value of this enum.
     * @return Enum value or null when the value is null or empty.
     */
    public static CheckType fromString(String value) {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }

        switch (value) {
            case "profiling":
            case "adhoc": // old values
                return profiling;

            case "monitoring":
            case "checkpoint": // old values
                return monitoring;

            case "partitioned":
                return partitioned;

            default:
                throw new EnumConstantNotPresentException(CheckType.class, value);
        }
    }

    public static class CheckTypeSampleFactory implements SampleValueFactory<CheckType> {
        @Override
        public CheckType createSample() {
            return monitoring;
        }
    }
}
