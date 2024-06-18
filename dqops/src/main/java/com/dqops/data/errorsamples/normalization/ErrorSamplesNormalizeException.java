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

package com.dqops.data.errorsamples.normalization;

import com.dqops.data.statistics.normalization.StatisticsResultsNormalizationServiceImpl;
import com.dqops.utils.exceptions.DqoRuntimeException;
import tech.tablesaw.api.Table;

/**
 * Exception thrown by {@link StatisticsResultsNormalizationServiceImpl} when the error samples result returned an invalid dataset that cannot be processed.
 */
public class ErrorSamplesNormalizeException extends DqoRuntimeException {
    private final Table resultsTable;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public ErrorSamplesNormalizeException(Table resultsTable) {
        this.resultsTable = resultsTable;
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ErrorSamplesNormalizeException(Table resultsTable, String message) {
        super(message);
        this.resultsTable = resultsTable;
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A {@code null} value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.4
     */
    public ErrorSamplesNormalizeException(Table resultsTable, String message, Throwable cause) {
        super(message, cause);
        this.resultsTable = resultsTable;
    }

    /**
     * Result table returned by a collector that was invalid.
     * @return Result dataset.
     */
    public Table getResultsTable() {
        return resultsTable;
    }
}
