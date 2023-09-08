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

package com.dqops.execution.statistics;

/**
 * Object that stores a count of profiled columns, profilers (collectors) executed, profilers that failed.
 */
public class CollectorExecutionStatistics {
    private int collectorsExecutedCount = 0;
    private int collectorsFailedCount = 0;
    private int collectorsResultsCount = 0;
    private int profiledColumnsCount = 0;
    private int profiledColumnSuccessfullyCount = 0;
    private Throwable firstException;

    /**
     * Returns the number of collectors that were executed.
     * @return The count of collectors executed.
     */
    public int getCollectorsExecutedCount() {
        return collectorsExecutedCount;
    }

    /**
     * Increments the count of executed collectors.
     * @param increment The increment to add to the current count.
     */
    public void incrementCollectorsExecutedCount(int increment) {
        this.collectorsExecutedCount += increment;
    }

    /**
     * Returns the number of collectors that failed to execute due to some errors.
     * @return The count of failed collectors.
     */
    public int getCollectorsFailedCount() {
        return collectorsFailedCount;
    }

    /**
     * Increments the count of failed collectors and reports the first exception.
     * @param exception The exception that was thrown. The first exception is stored.
     */
    public void incrementCollectorsFailedCount(Throwable exception) {
        this.collectorsFailedCount++;
        if (this.firstException == null) {
            this.firstException = exception;
        }
    }

    /**
     * Returns the number of results that were captured by the statistics collectors.
     * @return The count of captured statistics results.
     */
    public int getCollectorsResultsCount() {
        return collectorsResultsCount;
    }

    /**
     * Increments the count of collected statistics results.
     * @param increment The increment to add to the current count.
     */
    public void incrementCollectorsResultsCount(int increment) {
        this.collectorsResultsCount += increment;
    }

    /**
     * Returns the count of profiled columns (even if the profiler failed).
     * @return The count of columns that were profiled.
     */
    public int getProfiledColumnsCount() {
        return profiledColumnsCount;
    }

    /**
     * Increments the counts of columns that were profiled.
     * @param increment The increment to add to the current count.
     */
    public void incrementProfiledColumnsCount(int increment) {
        this.profiledColumnsCount += increment;
    }

    /**
     * Returns the count of profiled columns that captured any statistics metrics successfully.
     * @return The count of columns that were profiled successfully.
     */
    public int getProfiledColumnSuccessfullyCount() {
        return profiledColumnSuccessfullyCount;
    }

    /**
     * Increments the counts of columns that were profiled successfully.
     * @param increment The increment to add to the current count.
     */
    public void incrementProfiledColumnSuccessfullyCount(int increment) {
        this.profiledColumnSuccessfullyCount += increment;
    }

    /**
     * Returns the first exception that was thrown.
     * @return The first exception that was thrown and reported as a failure.
     */
    public Throwable getFirstException() {
        return firstException;
    }
}
