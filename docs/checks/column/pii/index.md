# column level pii data quality checks

This is a list of pii column data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## column-level pii checks
Checks for the presence of sensitive or personally identifiable information (PII) in a column such as an email, phone, zip code, IP4, and IP6 addresses.

### [contains usa phone percent](./contains-usa-phone-percent.md)
This check detects USA phone numbers inside text columns. It measures the percentage of columns containing a phone number and raises a data quality issue when too many rows contain phone numbers.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_contains_usa_phone_percent`</span>](./contains-usa-phone-percent.md#profile-contains-usa-phone-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects USA phone numbers in text columns. Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_contains_usa_phone_percent`</span>](./contains-usa-phone-percent.md#daily-contains-usa-phone-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects USA phone numbers in text columns. Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_contains_usa_phone_percent`</span>](./contains-usa-phone-percent.md#monthly-contains-usa-phone-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects USA phone numbers in text columns. Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_contains_usa_phone_percent`</span>](./contains-usa-phone-percent.md#daily-partition-contains-usa-phone-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects USA phone numbers in text columns. Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_contains_usa_phone_percent`</span>](./contains-usa-phone-percent.md#monthly-partition-contains-usa-phone-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects USA phone numbers in text columns. Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|



### [contains email percent](./contains-email-percent.md)
This check detects emails inside text columns. It measures the percentage of columns containing an email and raises a data quality issue when too many rows contain emails.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_contains_email_percent`</span>](./contains-email-percent.md#profile-contains-email-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects emails in text columns. Verifies that the percentage of rows that contains valid emails in a column does not exceed the minimum accepted percentage.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_contains_email_percent`</span>](./contains-email-percent.md#daily-contains-email-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects emails in text columns. Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_contains_email_percent`</span>](./contains-email-percent.md#monthly-contains-email-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects emails in text columns. Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_contains_email_percent`</span>](./contains-email-percent.md#daily-partition-contains-email-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects emails in text columns. Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_contains_email_percent`</span>](./contains-email-percent.md#monthly-partition-contains-email-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects emails in text columns. Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|



### [contains usa zipcode percent](./contains-usa-zipcode-percent.md)
This check detects USA zip code inside text columns. It measures the percentage of columns containing a zip code and raises a data quality issue when too many rows contain zip codes.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_contains_usa_zipcode_percent`</span>](./contains-usa-zipcode-percent.md#profile-contains-usa-zipcode-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects USA zip codes in text columns. Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage.| |
|[<span class="no-wrap-code">`daily_contains_usa_zipcode_percent`</span>](./contains-usa-zipcode-percent.md#daily-contains-usa-zipcode-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects USA zip codes in text columns. Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_contains_usa_zipcode_percent`</span>](./contains-usa-zipcode-percent.md#monthly-contains-usa-zipcode-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects USA zip codes in text columns. Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_contains_usa_zipcode_percent`</span>](./contains-usa-zipcode-percent.md#daily-partition-contains-usa-zipcode-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects USA zip codes in text columns. Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_contains_usa_zipcode_percent`</span>](./contains-usa-zipcode-percent.md#monthly-partition-contains-usa-zipcode-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects USA zip codes in text columns. Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.| |



### [contains ip4 percent](./contains-ip4-percent.md)
This check detects IP4 addresses inside text columns. It measures the percentage of columns containing an IP4 address and raises a data quality issue when too many rows contain IP4 addresses.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_contains_ip4_percent`</span>](./contains-ip4-percent.md#profile-contains-ip4-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects IP4 addresses in text columns. Verifies that the percentage of rows that contains valid IP4 address values in a column does not fall below the minimum accepted percentage.| |
|[<span class="no-wrap-code">`daily_contains_ip4_percent`</span>](./contains-ip4-percent.md#daily-contains-ip4-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects IP4 addresses in text columns. Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_contains_ip4_percent`</span>](./contains-ip4-percent.md#monthly-contains-ip4-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects IP4 addresses in text columns. Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_contains_ip4_percent`</span>](./contains-ip4-percent.md#daily-partition-contains-ip4-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects IP4 addresses in text columns. Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_contains_ip4_percent`</span>](./contains-ip4-percent.md#monthly-partition-contains-ip4-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects IP4 addresses in text columns. Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.| |



### [contains ip6 percent](./contains-ip6-percent.md)
This check detects IP6 addresses inside text columns. It measures the percentage of columns containing an IP6 address and raises a data quality issue when too many rows contain IP6 addresses.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_contains_ip6_percent`</span>](./contains-ip6-percent.md#profile-contains-ip6-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects IP6 addresses in text columns. Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage.| |
|[<span class="no-wrap-code">`daily_contains_ip6_percent`</span>](./contains-ip6-percent.md#daily-contains-ip6-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects IP6 addresses in text columns. Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_contains_ip6_percent`</span>](./contains-ip6-percent.md#monthly-contains-ip6-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects IP6 addresses in text columns. Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_contains_ip6_percent`</span>](./contains-ip6-percent.md#daily-partition-contains-ip6-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects IP6 addresses in text columns. Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_contains_ip6_percent`</span>](./contains-ip6-percent.md#monthly-partition-contains-ip6-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects IP6 addresses in text columns. Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.| |







