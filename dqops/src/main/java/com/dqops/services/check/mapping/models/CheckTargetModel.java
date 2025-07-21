/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.services.check.mapping.models;

import com.dqops.checks.CheckTarget;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * Enumeration of possible targets for check model request result.
 */
public enum CheckTargetModel {
    @JsonProperty("table")
    @JsonPropertyDescription("The check is assigned to a table.")
    table,

    @JsonProperty("column")
    @JsonPropertyDescription("The check is assigned to a column.")
    column;

    /**
     * Creates a check target model from a backend check target.
     * @param checkTarget Check target.
     * @return Check target model.
     */
    public static CheckTargetModel fromCheckTarget(CheckTarget checkTarget) {
        if (checkTarget == null) {
            return null;
        }

        switch (checkTarget) {
            case table:
                return table;
            case column:
                return column;
            default:
                throw new IllegalArgumentException("Unknown check target: " + checkTarget);
        }
    }
}
