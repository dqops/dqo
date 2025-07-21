/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.rules.comparison;

/**
 * Interface implemented by max_percent rule parameter classes.
 */
public interface MaxPercentRule {
    /**
     * Returns a maximum percent value for a data quality percent check.
     * @return Maximum value for a data quality check readout.
     */
     Double getMaxPercent();

    /**
     * Sets a maximum percent value that is accepted for percent checks.
     * @param maxPercent Maximum value that is accepted.
     */
    void setMaxPercent(Double maxPercent);
}
