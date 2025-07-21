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

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableSpec;

import java.util.Collection;

/**
 * Event raised before checks are started on a target table.
 */
public class ExecuteChecksOnTableStartEvent extends CheckExecutionProgressEvent {
    private final ConnectionWrapper connectionWrapper;
    private final TableSpec targetTable;
    private final Collection<AbstractCheckSpec<?,?,?,?>> checks;

    /**
     * Creates an event.
     *
     * @param targetTable Target table.
     * @param checks      Collection of checks.
     */
    public ExecuteChecksOnTableStartEvent(ConnectionWrapper connectionWrapper, TableSpec targetTable, Collection<AbstractCheckSpec<?,?,?,?>> checks) {
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
     * List of checks to be executed.
     *
     * @return List of checks.
     */
    public Collection<AbstractCheckSpec<?,?,?,?>> getChecks() {
        return checks;
    }
}
