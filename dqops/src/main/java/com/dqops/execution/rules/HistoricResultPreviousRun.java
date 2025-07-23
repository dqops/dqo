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

import java.time.Instant;

/**
 * A wrapper object that contains the previous sensor readout for the current time period if this check was run again and
 * we want to detect if the sensor value has not changed, to avoid running the rule again, which would generate additional alerts.
 * It stores also the sensor's execution timestamp.
 */
public final class HistoricResultPreviousRun {
    private final Double lastActualValue;
    private final Double lastExpectedValue;
    private final Instant executedAt;

    /**
     * Creates a pair of the last result and its execution timestamp.
     * @param lastActualValue The last sensor value.
     * @param lastExpectedValue The last expected value (for data comparisons).
     * @param executedAt When it was captured.
     */
    public HistoricResultPreviousRun(Double lastActualValue, Double lastExpectedValue, Instant executedAt) {
        this.lastActualValue = lastActualValue;
        this.lastExpectedValue = lastExpectedValue;
        this.executedAt = executedAt;
    }

    /**
     * Returns the last sensor value that was captured.
     * @return Last sensor value.
     */
    public Double getLastActualValue() {
        return lastActualValue;
    }

    /**
     * Returns the last expected value that was captured.
     * @return Last expected value.
     */
    public Double getLastExpectedValue() {
        return lastExpectedValue;
    }

    /**
     * Returns the timestamp when the sensor was executed for the last time.
     * @return Last execution timestamp.
     */
    public Instant getExecutedAt() {
        return executedAt;
    }
}
