/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.execution.checks.progress;

import ai.dqo.checks.AbstractCheckSpec;
import ai.dqo.execution.checks.TableChecksExecutionStatistics;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.sources.TableSpec;

import java.util.Collection;

/**
 * Progress event raised after all checks for a table were executed, rules evaluated and the results written which ends the check execution for that table (no more work).
 */
public class TableChecksProcessingFinishedEvent extends CheckExecutionProgressEvent {
    private final ConnectionWrapper connectionWrapper;
    private final TableSpec tableSpec;
    private final Collection<AbstractCheckSpec<?,?,?,?>> checks;
    private final TableChecksExecutionStatistics executionStatistics;

    /**
     * Creates a progress event.
     *
     * @param connectionWrapper Target connection wrapper.
     * @param tableSpec Target table.
     * @param checks    Collection of checks that were executed.
     */
    public TableChecksProcessingFinishedEvent(ConnectionWrapper connectionWrapper, TableSpec tableSpec, Collection<AbstractCheckSpec<?,?,?,?>> checks,
                                              TableChecksExecutionStatistics executionStatistics) {
        this.connectionWrapper = connectionWrapper;
        this.tableSpec = tableSpec;
        this.checks = checks;
        this.executionStatistics = executionStatistics;
    }

    /**
     * Connection wrapper.
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
    public TableSpec getTableSpec() {
        return tableSpec;
    }

    /**
     * Collection of checks that were executed.
     *
     * @return Collection of checks that were executed.
     */
    public Collection<AbstractCheckSpec<?,?,?,?>> getChecks() {
        return checks;
    }

    /**
     * Returns the check execution statistics with counts of checks, failures, results, etc.
     * @return Check execution statistics.
     */
    public TableChecksExecutionStatistics getExecutionStatistics() {
        return executionStatistics;
    }

    /**
     * Count of checks that were executed.
     * @return Count of checks.
     */
    public int getExecutedChecksCount() {
        return this.executionStatistics.getExecutedChecksCount();
    }

    /**
     * Count of sensor results that were generated (captured) by sensors.
     * @return Count of sensor results.
     */
    public int getSensorReadoutsCount() {
        return this.executionStatistics.getSensorReadoutsCount();
    }

    /**
     * Count of rules that were evaluated and passed (were valid).
     * @return Count of passed checks.
     */
    public int getPassedRules() {
        return this.executionStatistics.getPassedRulesCount();
    }

    /**
     * Count of low severity alerts (warnings) that were raised.
     * @return Warnings count.
     */
    public int getWarningsCount() {
        return this.executionStatistics.getWarningIssuesCount();
    }

    /**
     * Count of error (medium) severity alerts (default alerts) that were raised.
     * @return Error severity alerts count.
     */
    public int getErrorsCount() {
        return this.executionStatistics.getErrorIssuesCount();
    }

    /**
     * Count of fatals (high severity alerts) that were raised.
     * @return High severity alerts (fatal) count.
     */
    public int getFatalCount() {
        return this.executionStatistics.getFatalIssuesCount();
    }

    /**
     * Returns the number of sensors that failed to execute.
     * @return Number of sensors that failed to execute.
     */
    public int getSensorExecutionErrorsCount() {
        return this.executionStatistics.getSensorExecutionErrorsCount();
    }

    /**
     * Returns the number of rules that failed to evaluate (but the sensors managed to execute).
     * @return Number of rules that failed to evaluate.
     */
    public int getRuleExecutionErrorsCount() {
        return this.executionStatistics.getRuleExecutionErrorsCount();
    }
}
