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

import com.dqops.data.errorsamples.snapshot.ErrorSamplesSnapshot;
import com.dqops.metadata.sources.TableSpec;

/**
 * Progress event raised before the error samples results are saved to parquet files.
 */
public class SavingErrorSamplesResultsEvent extends ErrorSamplerExecutionProgressEvent {
    private final TableSpec tableSpec;
    private final ErrorSamplesSnapshot errorSamplesSnapshot;

    /**
     * Creates a progress event.
     *
     * @param tableSpec          Target table.
     * @param errorSamplesSnapshot Error samples result (snapshot) for the given table.
     */
    public SavingErrorSamplesResultsEvent(TableSpec tableSpec, ErrorSamplesSnapshot errorSamplesSnapshot) {
        this.tableSpec = tableSpec;
        this.errorSamplesSnapshot = errorSamplesSnapshot;
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
     * Error samples snapshot for the given table.
     *
     * @return Error samples results for the given table.
     */
    public ErrorSamplesSnapshot getErrorSamplesSnapshot() {
        return errorSamplesSnapshot;
    }
}
