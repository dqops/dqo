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

import com.dqops.data.errors.snapshot.ErrorsSnapshot;
import com.dqops.metadata.sources.TableSpec;

/**
 * Progress event raised before the errors captured during the data quality check evaluation are saved.
 */
public class SavingErrorsEvent extends CheckExecutionProgressEvent {
    private final TableSpec tableSpec;
    private final ErrorsSnapshot errorsSnapshot;

    /**
     * Creates a progress event.
     *
     * @param tableSpec      Target table.
     * @param errorsSnapshot Errors captured for the data quality check evaluation for the given table.
     */
    public SavingErrorsEvent(TableSpec tableSpec, ErrorsSnapshot errorsSnapshot) {
        this.tableSpec = tableSpec;
        this.errorsSnapshot = errorsSnapshot;
    }

    /**
     * Target table specification.
     *
     * @return Target table.
     */
    public TableSpec getTableSpec() {
        return tableSpec;
    }

    /**
     * Check evaluation errors for the given table.
     *
     * @return Errors for the given table.
     */
    public ErrorsSnapshot getErrors() {
        return errorsSnapshot;
    }
}
