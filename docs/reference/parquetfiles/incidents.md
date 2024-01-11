# DQOps incidents parquet table schema
The parquet file schema for the incidents table stored in the *$DQO_USER_HOME/.data/incidents* folder in DQOps.

## Table description

The data quality incidents table that tracks open incidents. Incidents are grouping multiple failed data quality checks (stored in the check_results table).
 The check results that are part of an incident could be matched to incidents by the incident_hash column.
 The incidents are stored in the errors table is located in the *$DQO_USER_HOME/.data/incidents* folder that contains uncompressed parquet files.
 The table is partitioned using a Hive compatible partitioning folder structure. When the *$DQO_USER_HOME* is not configured, it is the folder where DQOps was started (the DQOps user&#x27;s home folder).

 The folder partitioning structure for this table is:
 *c&#x3D;[connection_name]/m&#x3D;[first_day_of_month]/*, for example: *c&#x3D;myconnection/m&#x3D;2023-01-01/*.


## Parquet table schema
The columns of this table are described below.

| Column&nbsp;name | Description | Hive&nbsp;data&nbsp;type |
|------------------|-------------|--------------------------|
 | id | The incident id (primary key), it is a UUID created from a hash of target affected by the incident (target_hash) and a first_seen_utc. This value identifies a single row. | STRING |
 | incident_hash | The hash of the incident. | BIGINT |
 | schema_name | The table schema. | STRING |
 | table_name | The table name. | STRING |
 | table_priority | The table priority. | INTEGER |
 | data_group_name | The data group name, it is a concatenated name of the data group dimension values, created from [grouping_level_1] / [grouping_level_2] / ... | STRING |
 | quality_dimension | The data quality dimension. | STRING |
 | check_category | The check category. | STRING |
 | check_type | The check type (profiling, checkpoint, partitioned). | STRING |
 | check_name | The check name. | STRING |
 | highest_severity | The highest data quality check result severity detected as part of this incident. The values are 0, 1, 2, 3 for none, warning, error and fatal severity alerts. | INTEGER |
 | minimum_severity | Minimum severity of data quality issues (data quality check results) that are included in the incident. It is copied from the incident configuration at a connection or table level at the time when the incident is first seen. The values are 0, 1, 2, 3 for none, warning, error and fatal severity alerts. | INTEGER |
 | first_seen | Stores the exact time when the incident was raised (seen) for the first time, as a UTC timestamp: first_seen. | TIMESTAMP |
 | last_seen | Stores the exact time when the incident was raised (seen) for the last time, as a UTC timestamp: last_seen. | TIMESTAMP |
 | incident_until | Stores the timestamp of the end of the incident when new issues will not be appended to this incident, as a UTC timestamp: incident_until. | TIMESTAMP |
 | failed_checks_count | Stores the number of checks that failed. | INTEGER |
 | issue_url | Stores the user provided url to an external ticket management platform that is tracking this incident. | STRING |
 | resolved_by | Stores the login of the user who resolved the incident. | STRING |
 | status | Stores the current status of the incident. The statuses are described in the {@link IncidentStatus IncidentStatus} enumeration. | STRING |
 | created_at | The timestamp when the row was created at. | TIMESTAMP |
 | updated_at | The timestamp when the row was updated at. | TIMESTAMP |
 | created_by | Stores the login of the user who created the incident by running a check. | STRING |
 | updated_by | The login of the user that updated the row. | STRING |

