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
