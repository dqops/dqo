# CRON expression format
This page shows the format of CRON expressions used by DQOps for scheduling data quality checks. DQOps CRON format is compatible with Linux CRON.

## Overview
A cron expression is a string of fve numbers separated by white space that represents when the job should be executed.

Each value may be any of the allowed values below, along with various combinations of the special characters

| *              | *            | *                   | *                       | *                            |
|:---------------|:-------------|:--------------------|:------------------------|:-----------------------------|
| minutes (0-59) | hours (0-23) | day of month (1-31) | month (1–12 or JAN–DEC) | day of week (1–7 or SUN–SAT) |


**Supported special characters:**

- asterisks (*) Selects all values within a field. For example, * in the minute field means "every minute".
- comma (,) This is used to specify additional values. For example, "MON,WED,FRI" in the day-of-week field means "the days Monday, Wednesday, and Friday".
- slash (/) This is used to specify increments. For example, "0/15" in the seconds field means "the seconds 0, 15, 30, and 45". And "5/15" in the seconds field means "the seconds 5, 20, 35, and 50".
- hyphen(-) This Used to specify ranges. For instance, "10-12" in the hour field means "the hours 10, 11 and 12".

Cron expression usage examples:

`* * * * *`     Every minute

`0 * * * *`     Every hour

`0 0 * * *`     Every day at 12:00 AM

`0 0 * * SUN`   At 12:00 AM, only on Sunday

`0 22 * * 1-5`  Every weekday (Mon-Fri) at 10:00 PM


## What's next
- Learn how to configure [scheduling data quality checks](index.md) from the DQOps user interface.
- Learn how to configure [CRON schedules for running data quality checks](configuring-schedules-by-modifying-yaml-file.md) in YAML files.
