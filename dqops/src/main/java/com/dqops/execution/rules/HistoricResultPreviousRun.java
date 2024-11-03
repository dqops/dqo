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
