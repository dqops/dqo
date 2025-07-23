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
