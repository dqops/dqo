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
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.statistics.AbstractStatisticsCollectorSpec;

import java.util.Collection;

/**
 * Event raised before error samples collectors are started on a target table.
 */
public class ExecuteErrorSamplerOnTableStartEvent extends ErrorSamplerExecutionProgressEvent {
    private final ConnectionWrapper connectionWrapper;
    private final TableSpec targetTable;
    private final Collection<AbstractCheckSpec<?,?,?,?>> checks;

    /**
     * Creates an event.
     *
     * @param targetTable Target table.
     * @param checks  Collection of check specification with error samplers to execute.
     */
    public ExecuteErrorSamplerOnTableStartEvent(ConnectionWrapper connectionWrapper,
                                                TableSpec targetTable,
                                                Collection<AbstractCheckSpec<?,?,?,?>> checks) {
        this.connectionWrapper = connectionWrapper;
        this.targetTable = targetTable;
        this.checks = checks;
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
     * List of check specifications with error samplers to be executed.
     *
     * @return List of check specifications.
     */
    public Collection<AbstractCheckSpec<?,?,?,?>> getChecks() {
        return checks;
    }
}
