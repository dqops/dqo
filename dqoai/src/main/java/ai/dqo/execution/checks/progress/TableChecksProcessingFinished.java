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
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.sources.TableSpec;

import java.util.Collection;

/**
 * Progress event raised after all checks for a table were executed, rules evaluated and the results written which ends the check execution for that table (no more work).
 */
public class TableChecksProcessingFinished extends CheckExecutionProgressEvent {
    private final ConnectionWrapper connectionWrapper;
    private final TableSpec tableSpec;
    private final Collection<AbstractCheckSpec<?,?,?,?>> checks;
    private final int checksCount;
    private final int sensorResultsCount;
    private final int passedRules;
    private final int warningsCount;
    private final int errorsCount;
    private final int fatalCount;

    /**
     * Creates a progress event.
     *
     * @param connectionWrapper Target connection wrapper.
     * @param tableSpec Target table.
     * @param checks    Collection of checks that were executed.
     */
    public TableChecksProcessingFinished(ConnectionWrapper connectionWrapper, TableSpec tableSpec, Collection<AbstractCheckSpec<?,?,?,?>> checks,
                                         int checksCount, int sensorResultsCount, int passedRules,
                                         int warningsCount, int errorsCount, int fatalCount) {
        this.connectionWrapper = connectionWrapper;
        this.tableSpec = tableSpec;
        this.checks = checks;
        this.checksCount = checksCount;
        this.sensorResultsCount = sensorResultsCount;
        this.passedRules = passedRules;
        this.warningsCount = warningsCount;
        this.errorsCount = errorsCount;
        this.fatalCount = fatalCount;
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
     * Count of checks that were executed.
     * @return Count of checks.
     */
    public int getChecksCount() {
        return checksCount;
    }

    /**
     * Count of sensor results that were generated (captured) by sensors.
     * @return Count of sensor results.
     */
    public int getSensorResultsCount() {
        return sensorResultsCount;
    }

    /**
     * Count of rules that were evaluated and passed (were valid).
     * @return Count of passed checks.
     */
    public int getPassedRules() {
        return passedRules;
    }

    /**
     * Count of low severity alerts (warnings) that were raised.
     * @return Warnings count.
     */
    public int getWarningsCount() {
        return warningsCount;
    }

    /**
     * Count of error (medium) severity alerts (default alerts) that were raised.
     * @return Error severity alerts count.
     */
    public int getErrorsCount() {
        return errorsCount;
    }

    /**
     * Count of fatals (high severity alerts) that were raised.
     * @return High severity alerts (fatal) count.
     */
    public int getFatalCount() {
        return fatalCount;
    }
}
