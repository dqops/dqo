##IncidentsTable  
Constants with the column names used in the &quot;incidents&quot; table.  
  
**The columns of this table is described below**  
  
| Column name | Description | Data type |
|-------------|-------------|-----------|
 | id | Column name for a incident id (primary key), it is a UUID created from a hash of target affected by the incident (target_hash) and a first_seen_utc. This value identifies a single row. | text |
 | incident_hash | Column name for a hash of the incident. | long |
 | schema_name | Column name for a table schema. | text |
 | table_name | Column name for a table name. | text |
 | table_priority | Column name for a table priority. | integer |
 | data_stream_name | Column name for a data stream name, it is a concatenated name of the data stream created from [stream_level_1] / [stream_level_2] / ... | text |
 | quality_dimension | Column name for a data quality dimension. | text |
 | check_category | Column name for a check category. | text |
 | check_type | Column name for a check type (adhoc, checkpoint, partitioned). | text |
 | check_name | Column name for a check name. | text |
 | highest_severity | The highest data quality check result severity detected as part of this incident. The values are 0, 1, 2, 3 for none, warning, error and fatal severity alerts. | integer |
 | min_severity | Minimum severity of data quality issues (data quality check results) that are included in the incident. It is copied from the incident configuration at a connection or table level at the time when the incident is first seen. The values are 0, 1, 2, 3 for none, warning, error and fatal severity alerts. | integer |
 | first_seen | Column name that stores the exact time when the incident was raised (seen) for the first time, as a UTC timestamp: first_seen. | instant |
 | last_seen | Column name that stores the exact time when the incident was raised (seen) for the last time, as a UTC timestamp: last_seen. | instant |
 | incident_until | Column name that stores the timestamp of the end of the incident when new issues will not be appended to this incident, as a UTC timestamp: incident_until. | instant |
 | failed_checks_count | Column name that stores the number of checks that failed. | integer |
 | issue_url | Column name that stores the user provided url to an external ticket management platform that is tracking this incident. | text |
 | created_by | Column name that stores the login of the user who created the incident by running a check. | text |
 | resolved_by | Column name that stores the login of the user who resolved the incident. | text |
 | status | Column name that stores the current status of the incident. The statuses are described in the {@link IncidentStatus IncidentStatus} enumeration. | text |
