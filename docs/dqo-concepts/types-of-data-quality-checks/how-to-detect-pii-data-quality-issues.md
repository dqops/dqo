# Detecting data quality issues with pii
The type of pii is documented here

## Detecting pii at a table level
How to detect pii on tables

## List of pii checks at a table level

## Detecting pii at a column level
How to detect pii on column

## List of pii checks at a column level
| Data quality check name | Data quality dimension | Description | Class |
|-------------------------|------------------------|-------------|-------|
|[contains_usa_phone_percent](../../checks/column/pii/contains-usa-phone-percent.md)|Validity|Column check that calculates the percentage of rows that contains USA phone number values in a column.|standard|
|[contains_usa_zipcode_percent](../../checks/column/pii/contains-usa-zipcode-percent.md)|Validity|Column check that calculates the percentage of rows that contains USA zip code values in a column.|advanced|
|[contains_email_percent](../../checks/column/pii/contains-email-percent.md)|Validity|Column check that calculates the percentage of rows that contains valid email values in a column.|standard|
|[contains_ip4_percent](../../checks/column/pii/contains-ip4-percent.md)|Validity|Column check that calculates the percentage of rows that contains valid IP4 address values in a column.|advanced|
|[contains_ip6_percent](../../checks/column/pii/contains-ip6-percent.md)|Validity|Column check that calculates the percentage of rows that contains valid IP6 address values in a column.|advanced|

## What's next
- Learn now to [run data quality checks](../running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
