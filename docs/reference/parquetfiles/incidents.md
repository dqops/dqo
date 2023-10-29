## incidents  
The data quality incidents table that tracks open incidents. Incidents are grouping multiple failed data quality checks (stored in the check_results table).
 The check results that are part of an incident could be matched to incidents by the incident_hash column.
 The incidents are stored in the errors table is located in the $DQO_USER_HOME/.data/incidents folder that contains uncompressed parquet files.
 The table is partitioned using a Hive compatible partitioning folder structure. When the $DQO_USER_HOME is not configured, it is the folder where DQOps was started (the DQOps user&#x27;s home folder).

 The folder partitioning structure for this table is:
 c&#x3D;[connection_name]/m&#x3D;[first_day_of_month]/, for example: c&#x3D;myconnection/m&#x3D;2023-01-01/.  
  
**The columns of this table is described below**  
  
| Column name | Description | Data type |
|-------------|-------------|-----------|
 | id | The incident id (primary key), it is a UUID created from a hash of target affected by the incident (target_hash) and a first_seen_utc. This value identifies a single row. | text |
 | incident_hash | The hash of the incident. | long |
 | schema_name | The table schema. | text |
 | table_name | The table name. | text |
 | table_priority | The table priority. | integer |
 | data_group_name | The data group name, it is a concatenated name of the data group dimension values, created from [grouping_level_1] / [grouping_level_2] / ... | text |
 | quality_dimension | The data quality dimension. | text |
 | check_category | The check category. | text |
 | check_type | The check type (profiling, checkpoint, partitioned). | text |
 | check_name | The check name. | text |
 | highest_severity | The highest data quality check result severity detected as part of this incident. The values are 0, 1, 2, 3 for none, warning, error and fatal severity alerts. | integer |
 | minimum_severity | Minimum severity of data quality issues (data quality check results) that are included in the incident. It is copied from the incident configuration at a connection or table level at the time when the incident is first seen. The values are 0, 1, 2, 3 for none, warning, error and fatal severity alerts. | integer |
 | first_seen | Stores the exact time when the incident was raised (seen) for the first time, as a UTC timestamp: first_seen. | instant |
 | last_seen | Stores the exact time when the incident was raised (seen) for the last time, as a UTC timestamp: last_seen. | instant |
 | incident_until | Stores the timestamp of the end of the incident when new issues will not be appended to this incident, as a UTC timestamp: incident_until. | instant |
 | failed_checks_count | Stores the number of checks that failed. | integer |
 | issue_url | Stores the user provided url to an external ticket management platform that is tracking this incident. | text |
 | resolved_by | Stores the login of the user who resolved the incident. | text |
 | status | Stores the current status of the incident. The statuses are described in the {@link IncidentStatus IncidentStatus} enumeration. | text |
 | created_at | The timestamp when the row was created at. | instant |
 | updated_at | The timestamp when the row was updated at. | instant |
 | created_by | Stores the login of the user who created the incident by running a check. | text |
 | updated_by | The login of the user that updated the row. | text |
