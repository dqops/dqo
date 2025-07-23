/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks;

import com.dqops.utils.docs.generators.SampleValueFactory;
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
