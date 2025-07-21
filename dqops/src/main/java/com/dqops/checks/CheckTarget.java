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

/**
 * Enumeration of targets where the check is applied. It is one of "table" or "column".
 */
public enum CheckTarget {
    @JsonProperty("table")
    table,  // lowercase name to make swagger work

    @JsonProperty("column")
    column; // lowercase name to make swagger work

    public static class CheckTargetSampleFactory implements SampleValueFactory<CheckTarget> {
        @Override
        public CheckTarget createSample() {
            return column;
        }
    }
}
