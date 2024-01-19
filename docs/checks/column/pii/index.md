# column level pii data quality checks

This is a list of pii column data quality checks supported by DQOps and a brief description of what data quality issued they detect.





## **pii**
Checks for the presence of sensitive or personally identifiable information (PII) in a column such as email, phone, zip code, IP4 and IP6 addresses.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_contains_usa_phone_percent`</span>](./contains-usa-phone-percent.md#profile-contains-usa-phone-percent)|profiling|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_contains_usa_phone_percent`</span>](./contains-usa-phone-percent.md#daily-contains-usa-phone-percent)|monitoring|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_contains_usa_phone_percent`</span>](./contains-usa-phone-percent.md#monthly-contains-usa-phone-percent)|monitoring|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_contains_usa_phone_percent`</span>](./contains-usa-phone-percent.md#daily-partition-contains-usa-phone-percent)|partitioned|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_contains_usa_phone_percent`</span>](./contains-usa-phone-percent.md#monthly-partition-contains-usa-phone-percent)|partitioned|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_contains_email_percent`</span>](./contains-email-percent.md#profile-contains-email-percent)|profiling|Verifies that the percentage of rows that contains valid emails in a column does not exceed the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_contains_email_percent`</span>](./contains-email-percent.md#daily-contains-email-percent)|monitoring|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_contains_email_percent`</span>](./contains-email-percent.md#monthly-contains-email-percent)|monitoring|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_contains_email_percent`</span>](./contains-email-percent.md#daily-partition-contains-email-percent)|partitioned|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_contains_email_percent`</span>](./contains-email-percent.md#monthly-partition-contains-email-percent)|partitioned|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_contains_usa_zipcode_percent`</span>](./contains-usa-zipcode-percent.md#profile-contains-usa-zipcode-percent)|profiling|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_contains_usa_zipcode_percent`</span>](./contains-usa-zipcode-percent.md#daily-contains-usa-zipcode-percent)|monitoring|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_contains_usa_zipcode_percent`</span>](./contains-usa-zipcode-percent.md#monthly-contains-usa-zipcode-percent)|monitoring|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_contains_usa_zipcode_percent`</span>](./contains-usa-zipcode-percent.md#daily-partition-contains-usa-zipcode-percent)|partitioned|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_contains_usa_zipcode_percent`</span>](./contains-usa-zipcode-percent.md#monthly-partition-contains-usa-zipcode-percent)|partitioned|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_contains_ip4_percent`</span>](./contains-ip4-percent.md#profile-contains-ip4-percent)|profiling|Verifies that the percentage of rows that contains valid IP4 address values in a column does not fall below the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_contains_ip4_percent`</span>](./contains-ip4-percent.md#daily-contains-ip4-percent)|monitoring|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_contains_ip4_percent`</span>](./contains-ip4-percent.md#monthly-contains-ip4-percent)|monitoring|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_contains_ip4_percent`</span>](./contains-ip4-percent.md#daily-partition-contains-ip4-percent)|partitioned|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_contains_ip4_percent`</span>](./contains-ip4-percent.md#monthly-partition-contains-ip4-percent)|partitioned|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_contains_ip6_percent`</span>](./contains-ip6-percent.md#profile-contains-ip6-percent)|profiling|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_contains_ip6_percent`</span>](./contains-ip6-percent.md#daily-contains-ip6-percent)|monitoring|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_contains_ip6_percent`</span>](./contains-ip6-percent.md#monthly-contains-ip6-percent)|monitoring|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_contains_ip6_percent`</span>](./contains-ip6-percent.md#daily-partition-contains-ip6-percent)|partitioned|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_contains_ip6_percent`</span>](./contains-ip6-percent.md#monthly-partition-contains-ip6-percent)|partitioned|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|







