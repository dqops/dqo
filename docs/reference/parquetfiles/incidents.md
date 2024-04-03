# DQOps incidents parquet table schema
The parquet file schema for the incidents table stored in the *[$DQO_USER_HOME](../../dqo-concepts/dqops-user-home-folder.md)/.data/incidents* folder in DQOps.

## Table description

The data quality incidents table that tracks open incidents. Incidents are grouping multiple failed data quality checks (stored in the check_results table).
 The check results that are part of an incident can be matched to incidents by the incident_hash column.
 The incidents are stored in the errors table is located in the *[$DQO_USER_HOME](../../dqo-concepts/dqops-user-home-folder.md)/.data/incidents* folder that contains uncompressed parquet files.
 The table is partitioned using a Hive compatible partitioning folder structure. When the *[$DQO_USER_HOME](../../dqo-concepts/dqops-user-home-folder.md)* is not configured, it is the folder where DQOps was started (the DQOps user&#x27;s home folder).

 The folder partitioning structure for this table is:
 *c&#x3D;[connection_name]/m&#x3D;[first_day_of_month]/*, for example: *c&#x3D;myconnection/m&#x3D;2023-01-01/*.


## Parquet table schema
The columns of this table are described below.

| Column&nbsp;name | Description | Hive&nbsp;data&nbsp;type |
|------------------|-------------|--------------------------|
 | <span class="no-wrap-code">`id`</span> | The incident id (primary key), it is a UUID created from a hash of target affected by the incident (target_hash) and a first_seen_utc. This value identifies a single row. | *STRING* |
 | <span class="no-wrap-code">`incident_hash`</span> | The hash of the incident. | *BIGINT* |
 | <span class="no-wrap-code">`schema_name`</span> | The table schema. | *STRING* |
 | <span class="no-wrap-code">`table_name`</span> | The table name. | *STRING* |
 | <span class="no-wrap-code">`table_priority`</span> | The table priority. | *INTEGER* |
 | <span class="no-wrap-code">`data_group_name`</span> | The data group name, it is a concatenated name of the data group dimension values, created from [grouping_level_1] / [grouping_level_2] / ... | *STRING* |
 | <span class="no-wrap-code">`quality_dimension`</span> | The data quality dimension. | *STRING* |
 | <span class="no-wrap-code">`check_category`</span> | The check category. | *STRING* |
 | <span class="no-wrap-code">`check_type`</span> | The check type (profiling, checkpoint, partitioned). | *STRING* |
 | <span class="no-wrap-code">`check_name`</span> | The check name. | *STRING* |
 | <span class="no-wrap-code">`highest_severity`</span> | The highest data quality check result severity detected as part of this incident. The values are 0, 1, 2, 3 for none, warning, error and fatal severity alerts. | *INTEGER* |
 | <span class="no-wrap-code">`minimum_severity`</span> | Minimum severity of data quality issues (data quality check results) that are included in the incident. It is copied from the incident configuration at a connection or table level at the time when the incident is first seen. The values are 0, 1, 2, 3 for none, warning, error and fatal severity alerts. | *INTEGER* |
 | <span class="no-wrap-code">`first_seen`</span> | Stores the exact time when the incident was raised (seen) for the first time, as a UTC timestamp: first_seen. | *TIMESTAMP* |
 | <span class="no-wrap-code">`last_seen`</span> | Stores the exact time when the incident was raised (seen) for the last time, as a UTC timestamp: last_seen. | *TIMESTAMP* |
 | <span class="no-wrap-code">`incident_until`</span> | Stores the timestamp of the end of the incident when new issues will not be appended to this incident, as a UTC timestamp: incident_until. | *TIMESTAMP* |
 | <span class="no-wrap-code">`failed_checks_count`</span> | Stores the number of checks that failed. | *INTEGER* |
 | <span class="no-wrap-code">`issue_url`</span> | Stores the user provided url to an external ticket management platform that is tracking this incident. | *STRING* |
 | <span class="no-wrap-code">`resolved_by`</span> | Stores the login of the user who resolved the incident. | *STRING* |
 | <span class="no-wrap-code">`status`</span> | Stores the current status of the incident. The statuses are described in the {@link IncidentStatus IncidentStatus} enumeration. | *STRING* |
 | <span class="no-wrap-code">`created_at`</span> | The timestamp when the row was created at. | *TIMESTAMP* |
 | <span class="no-wrap-code">`updated_at`</span> | The timestamp when the row was updated at. | *TIMESTAMP* |
 | <span class="no-wrap-code">`created_by`</span> | Stores the login of the user who created the incident by running a check. | *STRING* |
 | <span class="no-wrap-code">`updated_by`</span> | The login of the user that updated the row. | *STRING* |


## What's more
- You can find more information on how the Parquet files are partitioned in the [data quality results storage concept](../../dqo-concepts/data-storage-of-data-quality-results.md).
