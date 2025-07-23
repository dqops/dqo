/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.incidents;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Severity levels of failed data quality checks that will be grouped into incidents.
 */
public enum MinimumGroupingSeverityLevel {
    @JsonProperty("warning")
    warning(1),

    @JsonProperty("error")
    error(2),

    @JsonProperty("fatal")
    fatal(3);

    private int severityLevel;

    MinimumGroupingSeverityLevel(int severityLevel) {
        this.severityLevel = severityLevel;
    }

    /**
     * Returns a numeric severity level for a named minimum severity level.
     * @return Numeric severity level.
     */
    public int getSeverityLevel() {
        return severityLevel;
    }
}
