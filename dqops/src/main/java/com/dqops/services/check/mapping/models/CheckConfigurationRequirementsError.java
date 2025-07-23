/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
