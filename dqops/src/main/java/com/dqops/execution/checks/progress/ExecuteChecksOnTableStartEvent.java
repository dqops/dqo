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
