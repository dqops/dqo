/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.errorsampling.progress;

import com.dqops.execution.errorsampling.ErrorSamplerExecutionSummary;

/**
 * Progress event raised after all error samples were collected. Returns the summary.
 */
public class ErrorSamplersExecutionFinishedEvent extends ErrorSamplerExecutionProgressEvent {
    private final ErrorSamplerExecutionSummary collectorsExecutionSummary;

    /**
     * Creates a progress event.
     *
     * @param collectorsExecutionSummary  Summary of all executed error samplers.
     */
    public ErrorSamplersExecutionFinishedEvent(ErrorSamplerExecutionSummary collectorsExecutionSummary) {
        this.collectorsExecutionSummary = collectorsExecutionSummary;
    }

    /**
     * Returns a summary of executed error samples.
     * @return Summary of executed error samples.
     */
    public ErrorSamplerExecutionSummary getCollectorsExecutionSummary() {
        return collectorsExecutionSummary;
    }
}
