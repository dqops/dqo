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

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.execution.errorsampling.ErrorSamplerExecutionStatistics;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableSpec;

import java.util.Collection;

/**
 * Event raised after error sampler are started on a target table.
 */
public class ExecuteErrorSamplerOnTableFinishedEvent extends ErrorSamplerExecutionProgressEvent {
    private final ConnectionWrapper connectionWrapper;
    private final TableSpec targetTable;
    private final Collection<AbstractCheckSpec<?,?,?,?>> checks;
    private final ErrorSamplerExecutionStatistics executionStatistics;

    /**
     * Creates an event.
     *
     * @param connectionWrapper Connection wrapper to identity the target data source.
     * @param targetTable Target table.
     * @param checks  Collection of check specifications with error samples that were executed.
     * @param executionStatistics Execution statistics - the number of error samples that finished successfully.
     */
    public ExecuteErrorSamplerOnTableFinishedEvent(ConnectionWrapper connectionWrapper,
                                                   TableSpec targetTable,
                                                   Collection<AbstractCheckSpec<?,?,?,?>>  checks,
                                                   ErrorSamplerExecutionStatistics executionStatistics) {
        this.connectionWrapper = connectionWrapper;
        this.targetTable = targetTable;
        this.checks = checks;
        this.executionStatistics = executionStatistics;
    }

    /**
     * Returns the connection wrapper for the analyzed data source.
     * @return Connection wrapper of the target database.
     */
    public ConnectionWrapper getConnectionWrapper() {
        return connectionWrapper;
    }

    /**
     * Target table.
     *
     * @return Target table.
     */
    public TableSpec getTargetTable() {
        return targetTable;
    }

    /**
     * List of checks that were executed.
     *
     * @return List of checks executed.
     */
    public Collection<AbstractCheckSpec<?,?,?,?>>  getChecks() {
        return checks;
    }

    /**
     * Returns the error sampler execution statistics (counts of executed error samples, etc).
     * @return The statistics of executing error samples on a table.
     */
    public ErrorSamplerExecutionStatistics getExecutionStatistics() {
        return executionStatistics;
    }
}
