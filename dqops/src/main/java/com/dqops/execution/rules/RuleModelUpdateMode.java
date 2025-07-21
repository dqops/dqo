/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.execution.rules;

/**
 * The mode of updating outdated ML models used by a rule.
 */
public enum RuleModelUpdateMode {
    /**
     * Never update the model, even if it is outdated. Just return the results using the old model.
     * This is the default option.
     */
    never,

    /**
     * Update the model if it is outdated.
     */
    when_outdated,

    /**
     * Update the model, no matter if it is fresh, or outdated.
     */
    always
}
