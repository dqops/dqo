/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.execution.errorsampling;

/**
 * Object that stores a count of error samplings performed by retrieving samples of invalid values.
 */
public class ErrorSamplerExecutionStatistics {
    private int errorSamplersExecutedCount = 0;
    private int errorSamplersFailedCount = 0;
    private int collectedErrorSamplesCount = 0;
    private int analyzedColumnsCount = 0;
    private int analyzedColumnSuccessfullyCount = 0;
    private Throwable firstException;

    /**
     * Returns the number of error samplers that were executed.
     * @return The count of error samplers executed.
     */
    public int getErrorSamplersExecutedCount() {
        return errorSamplersExecutedCount;
    }

    /**
     * Increments the count of executed error samplers.
     * @param increment The increment to add to the current count.
     */
    public void incrementErrorSamplersExecutedCount(int increment) {
        this.errorSamplersExecutedCount += increment;
    }

    /**
     * Returns the number of error samplers that failed to execute due to some errors.
     * @return The count of failed error samplers.
     */
    public int getErrorSamplersFailedCount() {
        return errorSamplersFailedCount;
    }

    /**
     * Increments the count of failed error samplers and reports the first exception.
     * @param exception The exception that was thrown. The first exception is stored.
     */
    public void incrementErrorSamplersFailedCount(Throwable exception) {
        this.errorSamplersFailedCount++;
        if (this.firstException == null) {
            this.firstException = exception;
        }
    }

    /**
     * Returns the number of results (error samples) that were captured by the error samplers.
     * @return The count of captured error sampling results.
     */
    public int getCollectedErrorSamplesCount() {
        return collectedErrorSamplesCount;
    }

    /**
     * Increments the count of collected error samples (values).
     * @param increment The increment to add to the current count.
     */
    public void incrementCollectedErrorSamplesCount(int increment) {
        this.collectedErrorSamplesCount += increment;
    }

    /**
     * Returns the count of analyzed columns (even if the error sampler failed or there were no error samplers).
     * @return The count of columns that were analyzed.
     */
    public int getAnalyzedColumnsCount() {
        return analyzedColumnsCount;
    }

    /**
     * Increments the counts of columns that were analyzed.
     * @param increment The increment to add to the current count.
     */
    public void incrementAnalyzedColumnsCount(int increment) {
        this.analyzedColumnsCount += increment;
    }

    /**
     * Returns the count of analyzed columns that captured any error samples successfully.
     * @return The count of columns that were error sampled successfully.
     */
    public int getAnalyzedColumnSuccessfullyCount() {
        return analyzedColumnSuccessfullyCount;
    }

    /**
     * Increments the counts of columns that were error sampled successfully.
     * @param increment The increment to add to the current count.
     */
    public void incrementAnalyzedColumnSuccessfullyCount(int increment) {
        this.analyzedColumnSuccessfullyCount += increment;
    }

    /**
     * Returns the first exception that was thrown.
     * @return The first exception that was thrown and reported as a failure.
     */
    public Throwable getFirstException() {
        return firstException;
    }
}
