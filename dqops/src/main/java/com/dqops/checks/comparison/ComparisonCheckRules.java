/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.checks.comparison;

import com.dqops.rules.comparison.MaxDiffPercentRule0ParametersSpec;
import com.dqops.rules.comparison.MaxDiffPercentRule1ParametersSpec;
import com.dqops.rules.comparison.MaxDiffPercentRule5ParametersSpec;

/**
 * Interface implemented by comparison checks, exposes getters and setters to the rules. Required for mapping the rule specifications to the model.
 */
public interface ComparisonCheckRules {
    /**
     * Alerting threshold configuration that raise a "WARNING" severity alerts for unsatisfied rules.
     *
     * @return Warning severity rule parameters.
     */
    MaxDiffPercentRule0ParametersSpec getWarning();

    /**
     * Sets a new warning level alerting threshold.
     * @param warning Warning alerting threshold to set.
     */
    void setWarning(MaxDiffPercentRule0ParametersSpec warning);

    /**
     * Alerting threshold configuration that raise a regular "ERROR" severity alerts for unsatisfied rules.
     *
     * @return Default "ERROR" alerting thresholds.
     */
    MaxDiffPercentRule1ParametersSpec getError();

    /**
     * Sets a new error level alerting threshold.
     * @param error Error alerting threshold to set.
     */
    void setError(MaxDiffPercentRule1ParametersSpec error);

    /**
     * Alerting threshold configuration that raise a "FATAL" severity alerts for unsatisfied rules.
     *
     * @return Fatal severity rule parameters.
     */
    MaxDiffPercentRule5ParametersSpec getFatal();

    /**
     * Sets a new fatal level alerting threshold.
     * @param fatal Fatal alerting threshold to set.
     */
    void setFatal(MaxDiffPercentRule5ParametersSpec fatal);
}
