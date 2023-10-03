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
package com.dqops.services.check.mapping.models;

/**
 * Constants with error messages on a check that will be shown in the check editor when the table is not configured correctly to run the check.
 */
public final class CheckConfigurationRequirementsError {
    /**
     * Configuration of the event timestamp column that identifies the timestamp of the event (transaction, click, operation, etc.) is missing and must be configured at the table level.
     */
    public static final String MISSING_EVENT_TIMESTAMP_COLUMN = "Configuration of the event timestamp column that identifies the timestamp of the event (transaction, click, operation, etc.) is missing and must be configured at the table level.";

    /**
     * Configuration of the ingestion timestamp column that identifies the timestamp when the row was loaded is missing and must be configured at the table level.
     */
    public static final String MISSING_INGESTION_TIMESTAMP_COLUMN = "Configuration of the ingestion timestamp column that identifies the timestamp when the row was loaded is missing and must be configured at the table level.";

    /**
     * Configuration of both the event timestamp column that identifies the timestamp of the event (transaction, click, operation, etc.) and the ingestion timestamp column that identifies the timestamp when the row was loaded are missing and must be configured at the table level.
     */
    public static final String MISSING_EVENT_AND_INGESTION_COLUMNS = "Configuration of both the event timestamp column that identifies the timestamp of the event (transaction, click, operation, etc.) and the ingestion timestamp column that identifies the timestamp when the row was loaded are missing and must be configured at the table level.";

    /**
     * Configuration of column that is used to partition the table by a time period (day, month, etc.) is missing and must be configured at the table level.
     */
    public static final String MISSING_PARTITION_BY_COLUMN = "Configuration of column that is used to partition the table by a time period (day, month, etc.) is missing and must be configured at the table level.";

    /**
     * The result truncation for profiling checks configured for the table is too coarse. Please change the "Profiling checks result scale" on the table's configuration to collect at one result per day, per hour or all results. The current time period is too coarse and the time series (anomaly detection) data quality checks cannot detect changes when the results are truncated to store only one result per week or per month
     */
    public static final String PROFILING_CHECKS_RESULT_TRUNCATION_TOO_COARSE = "The result truncation for profiling checks configured for the table is too coarse. Please change the \"Profiling checks result scale\" on the table's configuration to collect at one result per day, per hour or all results. The current time period is too coarse and the time series (anomaly detection) data quality checks cannot detect changes when the results are truncated to store only one result per week or per month";
}
