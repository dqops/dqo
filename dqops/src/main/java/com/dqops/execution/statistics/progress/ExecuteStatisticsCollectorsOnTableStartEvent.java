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

import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.statistics.AbstractStatisticsCollectorSpec;

import java.util.Collection;

/**
 * Event raised before statistics collectors are started on a target table.
 */
public class ExecuteStatisticsCollectorsOnTableStartEvent extends StatisticsCollectorsExecutionProgressEvent {
    private final ConnectionWrapper connectionWrapper;
    private final TableSpec targetTable;
    private final Collection<AbstractStatisticsCollectorSpec<?>> collectors;

    /**
     * Creates an event.
     *
     * @param targetTable Target table.
     * @param collectors  Collection of statistics collectors to execute.
     */
    public ExecuteStatisticsCollectorsOnTableStartEvent(ConnectionWrapper connectionWrapper,
                                                        TableSpec targetTable,
                                                        Collection<AbstractStatisticsCollectorSpec<?>> collectors) {
        this.connectionWrapper = connectionWrapper;
        this.targetTable = targetTable;
        this.collectors = collectors;
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
    public TableSpec getTargetTable() {
        return targetTable;
    }

    /**
     * List of statistics collectors to be executed.
     *
     * @return List of statistics collectors.
     */
    public Collection<AbstractStatisticsCollectorSpec<?>> getCollectors() {
        return collectors;
    }
}
