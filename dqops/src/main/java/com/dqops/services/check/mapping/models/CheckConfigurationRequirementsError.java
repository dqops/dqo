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
     * For this check to work, specify the event_timestamp column name at the table level to indicate when the event (transaction, click, operation, etc.) occurred.
     */
    public static final String MISSING_EVENT_TIMESTAMP_COLUMN = "For this check to work, specify the event_timestamp column name at the table level to indicate when the event (transaction, click, operation, etc.) occurred.";

    /**
     * For this check to work, specify the ingestion_timestamp column name at the table level to indicate when the row was loaded.
     */
    public static final String MISSING_INGESTION_TIMESTAMP_COLUMN = "For this check to work, specify the ingestion_timestamp column name at the table level to indicate when the row was loaded.";

    /**
     * For this check to work, specify the event_timestamp and ingestion_timestamp column names at the table level to indicate when the event (transaction, click, operation, etc.) occurred and when the row was loaded, respectively.
     */
    public static final String MISSING_EVENT_AND_INGESTION_COLUMNS = "For this check to work, specify the event_timestamp and ingestion_timestamp column names at the table level to indicate when the event (transaction, click, operation, etc.) occurred and when the row was loaded, respectively.";

    /**
     * For this check to work, configure the date or datetime column at the table level that will be used for date partitioning the table.
     */
    public static final String MISSING_PARTITION_BY_COLUMN = "For this check to work, configure the date or datetime column at the table level that will be used for date partitioning the table.";

    /**
     * The result truncation for profiling checks configured for the table is too coarse. Please change the "Profiling checks result scale" on the table's configuration to collect at one result per day, per hour or all results. The current time period is too coarse and the time series (anomaly detection) data quality checks cannot detect changes when the results are truncated to store only one result per week or per month
     */
    public static final String PROFILING_CHECKS_RESULT_TRUNCATION_TOO_COARSE = "The result truncation for profiling checks configured for the table is too coarse. Please change the \"Profiling checks result scale\" on the table's configuration to collect at one result per day, per hour or all results. The current time period is too coarse and the time series (anomaly detection) data quality checks cannot detect changes when the results are truncated to store only one result per week or per month";
}
