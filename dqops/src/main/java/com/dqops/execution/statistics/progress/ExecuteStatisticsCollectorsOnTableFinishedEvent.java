/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.statistics.progress;

import com.dqops.execution.statistics.CollectorExecutionStatistics;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.statistics.AbstractStatisticsCollectorSpec;

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
