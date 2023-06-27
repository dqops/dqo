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
package ai.dqo.execution.statistics.progress;

import ai.dqo.execution.statistics.CollectorExecutionStatistics;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.statistics.AbstractStatisticsCollectorSpec;

import java.util.Collection;

/**
 * Event raised after statistics collectors are started on a target table.
 */
public class ExecuteStatisticsCollectorsOnTableFinishedEvent extends StatisticsCollectorsExecutionProgressEvent {
    private final ConnectionWrapper connectionWrapper;
    private final TableSpec targetTable;
    private final Collection<AbstractStatisticsCollectorSpec<?>> collectors;
    private final CollectorExecutionStatistics executionStatistics;

    /**
     * Creates an event.
     *
     * @param connectionWrapper Connection wrapper to identity the target data source.
     * @param targetTable Target table.
     * @param collectors  Collection of statistics collectors that were executed.
     * @param executionStatistics Execution statistics - the number of collectors that finished successfully.
     */
    public ExecuteStatisticsCollectorsOnTableFinishedEvent(ConnectionWrapper connectionWrapper,
                                                           TableSpec targetTable,
                                                           Collection<AbstractStatisticsCollectorSpec<?>> collectors,
                                                           CollectorExecutionStatistics executionStatistics) {
        this.connectionWrapper = connectionWrapper;
        this.targetTable = targetTable;
        this.collectors = collectors;
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
     * List of statistics collectors that were executed.
     *
     * @return List of statistics collectors executed.
     */
    public Collection<AbstractStatisticsCollectorSpec<?>> getCollectors() {
        return collectors;
    }

    /**
     * Returns the collector statistics execution statistics (counts of executed collectors, etc).
     * @return The statistics of executing collectors (basic profilers) on a table.
     */
    public CollectorExecutionStatistics getExecutionStatistics() {
        return executionStatistics;
    }
}
