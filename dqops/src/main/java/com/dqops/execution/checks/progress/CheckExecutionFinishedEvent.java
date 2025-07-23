/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.checks.progress;

import com.dqops.execution.checks.CheckExecutionSummary;

/**
 * Progress event raised after checks were executed. Returns the summary.
 */
public class CheckExecutionFinishedEvent extends CheckExecutionProgressEvent {
    private final CheckExecutionSummary checkExecutionSummary;

    /**
     * Creates a progress event.
     *
     * @param checkExecutionSummary  Summary of all executed checks.
     */
    public CheckExecutionFinishedEvent(CheckExecutionSummary checkExecutionSummary) {
        this.checkExecutionSummary = checkExecutionSummary;
    }

    /**
     * Returns a summary of executed checks.
     * @return Summary of executed checks.
     */
    public CheckExecutionSummary getCheckExecutionSummary() {
        return checkExecutionSummary;
    }
}
