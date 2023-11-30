# Checks/column/pii

**This is a list of pii column checks in DQOps and a brief description of what they do.**





## **pii**  
Checks for the presence of sensitive or personally identifiable information (PII) in a column such as email, phone, zip code, IP4 and IP6 addresses.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_contains_usa_phone_percent](./column/pii/contains-usa-phone-percent/#profile-contains-usa-phone-percent)|profiling|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage.|
|[daily_contains_usa_phone_percent](./column/pii/contains-usa-phone-percent/#daily-contains-usa-phone-percent)|monitoring|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_contains_usa_phone_percent](./column/pii/contains-usa-phone-percent/#monthly-contains-usa-phone-percent)|monitoring|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_usa_phone_percent](./column/pii/contains-usa-phone-percent/#daily-partition-contains-usa-phone-percent)|partitioned|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_usa_phone_percent](./column/pii/contains-usa-phone-percent/#monthly-partition-contains-usa-phone-percent)|partitioned|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_contains_usa_zipcode_percent](./column/pii/contains-usa-zipcode-percent/#profile-contains-usa-zipcode-percent)|profiling|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage.|
|[daily_contains_usa_zipcode_percent](./column/pii/contains-usa-zipcode-percent/#daily-contains-usa-zipcode-percent)|monitoring|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_contains_usa_zipcode_percent](./column/pii/contains-usa-zipcode-percent/#monthly-contains-usa-zipcode-percent)|monitoring|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_usa_zipcode_percent](./column/pii/contains-usa-zipcode-percent/#daily-partition-contains-usa-zipcode-percent)|partitioned|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_usa_zipcode_percent](./column/pii/contains-usa-zipcode-percent/#monthly-partition-contains-usa-zipcode-percent)|partitioned|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_contains_email_percent](./column/pii/contains-email-percent/#profile-contains-email-percent)|profiling|Verifies that the percentage of rows that contains valid emails in a column does not exceed the minimum accepted percentage.|
|[daily_contains_email_percent](./column/pii/contains-email-percent/#daily-contains-email-percent)|monitoring|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_contains_email_percent](./column/pii/contains-email-percent/#monthly-contains-email-percent)|monitoring|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_email_percent](./column/pii/contains-email-percent/#daily-partition-contains-email-percent)|partitioned|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_email_percent](./column/pii/contains-email-percent/#monthly-partition-contains-email-percent)|partitioned|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_contains_ip4_percent](./column/pii/contains-ip4-percent/#profile-contains-ip4-percent)|profiling|Verifies that the percentage of rows that contains valid IP4 address values in a column does not fall below the minimum accepted percentage.|
|[daily_contains_ip4_percent](./column/pii/contains-ip4-percent/#daily-contains-ip4-percent)|monitoring|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_contains_ip4_percent](./column/pii/contains-ip4-percent/#monthly-contains-ip4-percent)|monitoring|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_ip4_percent](./column/pii/contains-ip4-percent/#daily-partition-contains-ip4-percent)|partitioned|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_ip4_percent](./column/pii/contains-ip4-percent/#monthly-partition-contains-ip4-percent)|partitioned|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_contains_ip6_percent](./column/pii/contains-ip6-percent/#profile-contains-ip6-percent)|profiling|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage.|
|[daily_contains_ip6_percent](./column/pii/contains-ip6-percent/#daily-contains-ip6-percent)|monitoring|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_contains_ip6_percent](./column/pii/contains-ip6-percent/#monthly-contains-ip6-percent)|monitoring|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_ip6_percent](./column/pii/contains-ip6-percent/#daily-partition-contains-ip6-percent)|partitioned|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_ip6_percent](./column/pii/contains-ip6-percent/#monthly-partition-contains-ip6-percent)|partitioned|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





