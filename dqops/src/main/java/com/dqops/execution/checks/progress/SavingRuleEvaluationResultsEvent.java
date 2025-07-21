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

import com.dqops.data.checkresults.snapshot.CheckResultsSnapshot;
import com.dqops.metadata.sources.TableSpec;

/**
 * Progress event raised before rule evaluation results are saved.
 */
public class SavingRuleEvaluationResultsEvent extends CheckExecutionProgressEvent {
    private final TableSpec tableSpec;
    private final CheckResultsSnapshot checkResultsSnapshot;

    /**
     * Creates a progress event.
     *
     * @param tableSpec                     Target table.
     * @param checkResultsSnapshot           Rule evaluation results (snapshot).
     */
    public SavingRuleEvaluationResultsEvent(TableSpec tableSpec, CheckResultsSnapshot checkResultsSnapshot) {
        this.tableSpec = tableSpec;
        this.checkResultsSnapshot = checkResultsSnapshot;
    }

    /**
     * Returns a target table spec.
     *
     * @return Target table.
     */
    public TableSpec getTableSpec() {
        return tableSpec;
    }

    /**
     * Rule evaluation results that will be saved.
     *
     * @return Rule evaluation results.
     */
    public CheckResultsSnapshot getRuleResultsSnapshot() {
        return checkResultsSnapshot;
    }
}
