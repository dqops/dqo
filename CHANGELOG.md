# 1.13.0

* Fixed some UT (notifications)
* Different limits for parallel file synchronization jobs.
* Parquet loading locks only during a file read, not when parsing a Parquet file.
* Incident notification filtering fixed.
* Messages related to OSS renamed.
* Table comparisons by in-memory merge.
* Use BigQuery Streaming API for merge comparison.
* Rule miner can detect numeric outliers.
* Improvements in the rule miner - detect longitude/latitude by a column name.
* Job queue is persistent, jobs are resumed after a restart.
* Incorrectly configured checks are disabled after three execution failures.




