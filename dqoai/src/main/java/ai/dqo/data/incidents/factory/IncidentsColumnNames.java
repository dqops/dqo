/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.data.incidents.factory;

import ai.dqo.data.normalization.CommonColumnNames;

/**
 * Constants with the column names used in the "incidents" table.
 */
public final class IncidentsColumnNames {
    /**
     * Column name for a incident id (primary key), it is a UUID created from a hash of target affected by the incident (target_hash) and a time_period. This value identifies a single row.
     */
    public static final String ID_COLUMN_NAME = CommonColumnNames.ID_COLUMN_NAME;

    /**
     * Column name for a hash of the target.
     */
    public static final String TARGET_HASH_COLUMN_NAME = "target_hash";

    /**
     * Column name for a table schema.
     */
    public static final String SCHEMA_NAME_COLUMN_NAME = CommonColumnNames.SCHEMA_NAME_COLUMN_NAME;

    /**
     * Column name for a table name.
     */
    public static final String TABLE_NAME_COLUMN_NAME = CommonColumnNames.TABLE_NAME_COLUMN_NAME;

    /**
     * Column name for a data stream name, it is a concatenated name of the data stream created from [stream_level_1] / [stream_level_2] / ...
     */
    public static final String DATA_STREAM_NAME_COLUMN_NAME = CommonColumnNames.DATA_STREAM_NAME_COLUMN_NAME;

    /**
     * Column name for a data quality dimension.
     */
    public static final String QUALITY_DIMENSION_COLUMN_NAME = "quality_dimension";

    /**
     * Column name for a check category.
     */
    public static final String CHECK_CATEGORY_COLUMN_NAME = "check_category";

    /**
     * Column name for a check type (adhoc, checkpoint, partitioned).
     */
    public static final String CHECK_TYPE_COLUMN_NAME = "check_type";

    /**
     * Column name for a check name.
     */
    public static final String CHECK_NAME_COLUMN_NAME = "check_name";

    /**
     * Column name for a column name.
     */
    public static final String COLUMN_NAME_COLUMN_NAME = CommonColumnNames.COLUMN_NAME_COLUMN_NAME;

    /**
     * Rule severity (0, 1, 2, 3) for none, low, medium and high alerts.
     */
    public static final String HIGHEST_SEVERITY_COLUMN_NAME = "highest_severity";

    /**
     * Column name that stores the exact time when the incident was raised (seen) for the first time, as a UTC timestamp: first_seen_utc.
     */
    public static final String FIRST_SEEN_UTC_COLUMN_NAME = "first_seen_utc";

    /**
     * Column name that stores the exact time when the incident was raised (seen) for the last time, as a UTC timestamp: last_seen_utc.
     */
    public static final String LAST_SEEN_UTC_COLUMN_NAME = "last_seen_utc";

    /**
     * Column name that stores the number of checks that failed.
     */
    public static final String FAILED_CHECKS_COUNT_COLUMN_NAME = "failed_checks_count";

    /**
     * Column name that stores the user provided url to an external ticket management platform that is tracking this incident.
     */
    public static final String ISSUE_URL_COLUMN_NAME = "issue_url";

    /**
     * Column name that stores the login of the user who created the incident by running a check.
     */
    public static final String CREATED_BY_COLUMN_NAME = "created_by";

    /**
     * Column name that stores the login of the user who resolved the incident.
     */
    public static final String RESOLVED_BY_COLUMN_NAME = "resolved_by";

    /**
     * Column name that stores the current status of the incident. The statuses are described in the {@link IncidentStatus} enumeration.
     */
    public static final String STATUS_COLUMN_NAME = "status";
}
