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
