# Checks overview

co to jest, 
jakie są rodzaje
jak wygląda w yamlu i po uruchomieniu, 
jak wygladaja wyniki 



## Severity level   dodać przy checkach

Rules are functions that evaluate sensors results and assigns them severity levels. There are 3 severity levels in DQO:
warning, error and fatal

- **Warning**. A warning level alerting threshold raises warnings for less important data quality issues. Warnings are
  not treated as data quality issues. Data quality checks that did not pass the warning alerting rule, but did pass the
  error and fatal alerting rules are still counted as passed data quality checks and do not reduce the
  [data quality KPIs](/data-quality-kpis/data-quality-kpis.md) score. Warnings should be used to identify potential data
  quality issues that should be monitored.

  For example, a percentage of data
  quality check monitoring null value may raise a warning when the percentage of rows with a null value exceeds 1% of all
  rows.


- **Error**. Error is the default alerting level, comparable to the "error" level in logging libraries. Data quality
  checks that failed to pass the rule evaluation at the "error" severity level are considered failed data quality checks.
  Errors reduce the value of [data quality KPIs](/data-quality-kpis/data-quality-kpis.md) score.

  For example, a percentage of data quality check monitoring null value may raise an error when the percentage of rows
  with a null value exceeds 5% of all rows.


- **Fatal**. Fatal is the highest alerting threshold that should only be used to identify severe data quality issues.
  These issues should result in stopping the data pipelines before the issue spreads throughout the system. Fatal data
  quality issues are treated as failed data quality checks and reduce the [data quality KPIs](/data-quality-kpis/data-quality-kpis.md)
  score. The fatal threshold should be used with caution. It is mainly useful when the data pipeline can trigger the data
  quality check assessment and wait for the result. If any data quality check raises a fatal data quality issue, the data
  pipeline should be stopped.

  For example, a percentage of data quality check monitoring null value may raise a fatal alert
  when the percentage of rows with a null value exceeds 30% of all rows.