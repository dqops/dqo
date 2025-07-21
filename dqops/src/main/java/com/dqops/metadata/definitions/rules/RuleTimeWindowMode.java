/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.definitions.rules;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Rule historic data mode. A rule may evaluate only the current sensor readout (current_value) or use historic values.
 */
public enum RuleTimeWindowMode {
    /**
     * Analyze only the current sensor readout. This is the default settings.
     */
    @JsonProperty("current_value")
    current_value,

    /**
     * Use the previous sensor readouts (readouts from previous time periods) in the rule evaluation, for example by calculating an average.
     */
    @JsonProperty("previous_readouts")
    previous_readouts
}
