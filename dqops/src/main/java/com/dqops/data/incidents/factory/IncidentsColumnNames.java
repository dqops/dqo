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
package com.dqops.data.incidents.factory;

import com.dqops.data.normalization.CommonColumnNames;

/**
 * The data quality incidents table that tracks open incidents. Incidents are grouping multiple failed data quality checks (stored in the check_results table).
 * The check results that are part of an incident could be matched to incidents by the incident_hash column.
 * The incidents are stored in the errors table is located in the *$DQO_USER_HOME/.data/incidents* folder that contains uncompressed parquet files.
 * The table is partitioned using a Hive compatible partitioning folder structure. When the *$DQO_USER_HOME* is not configured, it is the folder where DQOps was started (the DQOps user's home folder).
 *
 * The folder partitioning structure for this table is:
 * *c=[connection_name]/m=[first_day_of_month]/*, for example: *c=myconnection/m=2023-01-01/*.
 */
public final class IncidentsColumnNames {
    /**
     * The incident id (primary key), it is a UUID created from a hash of target affected by the incident (target_hash) and a first_seen_utc. This value identifies a single row.
     */
    public static final String ID_COLUMN_NAME = CommonColumnNames.ID_COLUMN_NAME;

    /**
     * The hash of the incident.
     */
    public static final String INCIDENT_HASH_COLUMN_NAME = "incident_hash";

    /**
     * The table schema.
     */
    public static final String SCHEMA_NAME_COLUMN_NAME = CommonColumnNames.SCHEMA_NAME_COLUMN_NAME;

    /**
     * The table name.
     */
    public static final String TABLE_NAME_COLUMN_NAME = CommonColumnNames.TABLE_NAME_COLUMN_NAME;

    /**
     * The table priority.
     */
    public static final String TABLE_PRIORITY_COLUMN_NAME = CommonColumnNames.TABLE_PRIORITY_COLUMN_NAME;

    /**
     * The data group name, it is a concatenated name of the data group dimension values, created from [grouping_level_1] / [grouping_level_2] / ...
     */
    public static final String DATA_GROUP_NAME_COLUMN_NAME = CommonColumnNames.DATA_GROUP_NAME_COLUMN_NAME;

    /**
     * The data quality dimension.
     */
    public static final String QUALITY_DIMENSION_COLUMN_NAME = "quality_dimension";

    /**
     * The check category.
     */
    public static final String CHECK_CATEGORY_COLUMN_NAME = "check_category";

    /**
     * The check type (profiling, checkpoint, partitioned).
     */
    public static final String CHECK_TYPE_COLUMN_NAME = "check_type";

    /**
     * The check name.
     */
    public static final String CHECK_NAME_COLUMN_NAME = "check_name";

    /**
     * The highest data quality check result severity detected as part of this incident. The values are 0, 1, 2, 3 for none, warning, error and fatal severity alerts.
     */
    public static final String HIGHEST_SEVERITY_COLUMN_NAME = "highest_severity";

    /**
     * Minimum severity of data quality issues (data quality check results) that are included in the incident. It is copied from the incident configuration at a connection or table level at the time when the incident is first seen. The values are 0, 1, 2, 3 for none, warning, error and fatal severity alerts.
     */
    public static final String MINIMUM_SEVERITY_COLUMN_NAME = "minimum_severity";

    /**
     * Stores the exact time when the incident was raised (seen) for the first time, as a UTC timestamp: first_seen.
     */
    public static final String FIRST_SEEN_COLUMN_NAME = "first_seen";

    /**
     * Stores the exact time when the incident was raised (seen) for the last time, as a UTC timestamp: last_seen.
     */
    public static final String LAST_SEEN_COLUMN_NAME = "last_seen";

    /**
     * Stores the timestamp of the end of the incident when new issues will not be appended to this incident, as a UTC timestamp: incident_until.
     */
    public static final String INCIDENT_UNTIL_COLUMN_NAME = "incident_until";

    /**
     * Stores the number of checks that failed.
     */
    public static final String FAILED_CHECKS_COUNT_COLUMN_NAME = "failed_checks_count";

    /**
     * Stores the user provided url to an external ticket management platform that is tracking this incident.
     */
    public static final String ISSUE_URL_COLUMN_NAME = "issue_url";

    /**
     * The timestamp when the row was created at.
     */
    public static final String CREATED_AT_COLUMN_NAME = CommonColumnNames.CREATED_AT_COLUMN_NAME;

    /**
     * The timestamp when the row was updated at.
     */
    public static final String UPDATED_AT_COLUMN_NAME = CommonColumnNames.UPDATED_AT_COLUMN_NAME;

    /**
     * Stores the login of the user who created the incident by running a check.
     */
    public static final String CREATED_BY_COLUMN_NAME = CommonColumnNames.CREATED_BY_COLUMN_NAME;

    /**
     * The login of the user that updated the row.
     */
    public static final String UPDATED_BY_COLUMN_NAME = CommonColumnNames.UPDATED_BY_COLUMN_NAME;

    /**
     * Stores the login of the user who resolved the incident.
     */
    public static final String RESOLVED_BY_COLUMN_NAME = "resolved_by";

    /**
     * Stores the current status of the incident. The statuses are described in the {@link IncidentStatus} enumeration.
     */
    public static final String STATUS_COLUMN_NAME = "status";
}
