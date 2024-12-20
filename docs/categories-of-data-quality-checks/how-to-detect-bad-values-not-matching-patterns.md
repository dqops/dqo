---
title: How to Detect Values not Matching Patterns? Examples
---
# How to Detect Values not Matching Patterns? Examples
Read this guide to learn how to validate column values if they match patterns, such as phone numbers, emails, or any regular expression.

The pattern match checks are configured in the `patterns` category in DQOps.

## Text patterns
Columns that store values that must adhere to a pattern are common in databases.
The most typical text values that should match a pattern are:

- Email addresses.

- Phone numbers.

- Zip codes.

- IP addresses.

- Dates stored in text fields.

- UUIDs (Universally Unique Identifiers).

We can also validate fields containing auto-generated identifiers, such as:

- Invoice numbers.

- Contract numbers.

- Tax identifiers.

DQOps has built-in data quality checks for validating typical text formats. 
Validation of non-standard text values is supported by using a [custom regular expression](../checks/column/patterns/texts-not-matching-regex-percent.md) 
or defining a [custom data quality check](../working-with-dqo/creating-custom-data-quality-checks.md).

## Built-in patterns
The DQOps data quality checks that detect the most popular patterns are listed in the table below.

| Data quality check name                                                                                         | Description                                                                                                                                                                                           | Sample texts                              |
|-----------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------|
| [*invalid_email_format_found*](../checks/column/patterns/invalid-email-format-found.md)                         | This check finds texts that are not valid emails.                                                                                                                                                     | *john.smith@example.com*                  | 
| [*text_not_matching_date_pattern_found*](../checks/column/patterns/text-not-matching-date-pattern-found.md)     | This check measures the percentage of texts that do not match a specified date format. The supported patterns are: *"YYYY-MM-DD"*, *"MM/DD/YYYY*", *"DD/MM/YYYY"*, *"YYYY/MM/DD"*, *"Month D, YYYY"*. | *2024-02-10*, *02/10/2024*                |
| [*text_not_matching_date_pattern_percent*](../checks/column/patterns/text-not-matching-date-pattern-percent.md) | This check measures the percentage of texts that do not match a specified date format.                                                                                                                | *2024-02-10*, *02/10/2024*                | 
| [*text_not_matching_name_pattern_percent*](../checks/column/patterns/text-not-matching-name-pattern-percent.md) | This check validates text values that are valid identifiers and measures the percentage of invalid values. A valid identifier is a text that contains only letters.                                   | *abcd*                                    |
| [*invalid_uuid_format_found*](../checks/column/patterns/invalid-uuid-format-found.md)                           | This check validates common UUID and GUID formats and finds any values that are not in the correct format.                                                                                            | *46f6c2f8-038f-4f36-9b87-fbf3fcd341cc*    |
| [*invalid_uuid_format_percent*](../checks/column/patterns/invalid-uuid-format-percent.md)                       | This check validates common UUID and GUID formats and measures the percentage of invalid values.                                                                                                      | *46f6c2f8-038f-4f36-9b87-fbf3fcd341cc*    |
| [*invalid_ip4_address_format_found*](../checks/column/patterns/invalid-ip4-address-format-found.md)             | This check validates the format of IP4 addresses and detects invalid values.                                                                                                                          | *123.45.67.89*                            |
| [*invalid_ip6_address_format_found*](../checks/column/patterns/invalid-ip6-address-format-found.md)             | This check validates the format of IP6 addresses and detects invalid values.                                                                                                                          | *66b2:454b:2638:9d9e:b0c1:1c3e:a39c:e83a* |                                        


## Detecting invalid emails
The [*invalid_email_format_found*](../checks/column/patterns/invalid-email-format-found.md) check detects invalid emails that do not match the typical email patterns.
The following column profile summary shows a sample email column that contains invalid emails.

![Email column profile with an invalid email](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/email-column-profile-min2.png){ loading=lazy; width="1200px" }


### Detecting invalid emails in UI
The [*invalid_email_format_found*](../checks/column/patterns/invalid-email-format-found.md)
check is located in the `patterns` category. The parameter **max_count** configures the maximum accepted number of invalid emails.
The check has found four invalid emails.

![Invalid email format data quality check in DQOps editor](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/invalid-email-data-quality-check-in-dqops-editor-min2.png){ loading=lazy; width="1200px" }

### Detecting invalid emails error sampling in UI

To assist with identifying the root cause of errors and cleaning up the data, DQOps offers error sampling for this check.
You can view representative examples of data that do not meet the specified data quality criteria by clicking on the
**Error sampling** tab in the results section.

![Invalid email format data - error sampling](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/invalid-email-data-error-sampling.png){ loading=lazy; width="1200px" }

For additional information about error sampling, please refer to [the Data Quality Error Sampling documentation](../dqo-concepts/data-quality-error-sampling.md).

### Detecting invalid emails in YAML
The configuration of the [*invalid_email_format_found*](../checks/column/patterns/invalid-email-format-found.md) check is simple.

``` { .yaml linenums="1" hl_lines="13-15" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    email:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          patterns:
            daily_invalid_email_format_found:
              error:
                max_count: 0
```


## Detecting invalid dates
The [*text_not_matching_date_pattern_found*](../checks/column/patterns/text-not-matching-date-pattern-found.md)
and [*text_not_matching_date_pattern_percent*](../checks/column/patterns/text-not-matching-date-pattern-percent.md) checks
use regular expressions to validate if text columns contain valid date strings that could be later converted to a date type.

The following summary of column profiling shows a text column that contains dates that do not match the YYYY-MM-DD format.

![Date column profile with dates as strings in an invalid format](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/text-date-column-profile2.png){ loading=lazy; width="1200px" }

### Detecting invalid dates in UI
We will use the [*text_not_matching_date_pattern_percent*](../checks/column/patterns/text-not-matching-date-pattern-percent.md)
check to detect invalid date patterns and measure the percentage of valid rows.

DQOps shows this checks when the *Show advanced checks* are enabled with a checkbox at the top of the
[data quality check editor screen](../dqo-concepts/dqops-user-interface-overview.md#check-editor). This check allows to select *date_format* parameter with different formats. 

After executing the check, we can see in the results that 45% of the rows in this column do not match the YYYY-MM-DD date pattern.

![Text in valid date format data quality check in DQOps editor](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/validating-dates-in-text-columns-dqops-data-quality-check2.png){ loading=lazy; width="1200px" }

### Detecting invalid dates error sampling in UI

We can view representative examples of data not matching YYYY-MM-DD date pattern by clicking on the
**Error sampling** tab in the results section.

![Text in valid date format - error sampling](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/validating-dates-in-text-columns-error-sampling.png){ loading=lazy; width="1200px" }}

For additional information about error sampling, please refer to [the Data Quality Error Sampling documentation](../dqo-concepts/data-quality-error-sampling.md).

### Detecting invalid dates in YAML
The configuration of the [*text_not_matching_date_pattern_percent*](../checks/column/patterns/text-not-matching-date-pattern-percent.md)
check uses an additional parameter, one of the supported date formats.

The supported date formats are:

| Date format      | Example date        |
|------------------|---------------------|
| `YYYY-MM-DD`     | *2024-02-10*        |
| `MM/DD/YYYY`     | *02/10/2024*        |
| `DD/MM/YYYY`     | *10/02/2024*        |
| `YYYY/MM/DD`     | *2024/02/10*        |
| `Month D, YYYY ` | *February 10, 2024* |

The following code sample shows a configured [*text_not_matching_date_pattern_percent*](../checks/column/patterns/text-not-matching-date-pattern-percent.md)
check in a DQOps YAML file.

``` { .yaml linenums="1" hl_lines="13-17" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    created_at:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          patterns:
            daily_text_not_matching_date_pattern_percent:
              parameters:
                date_format: YYYY-MM-DD
              error:
                max_percent: 0.0
```

## Validating custom regular expressions
If DQOps does not have a built-in pattern,
we can use the [*text_not_matching_regex_found*](../checks/column/patterns/text-not-matching-regex-found.md)
or [*texts_not_matching_regex_percent*](../checks/column/patterns/texts-not-matching-regex-percent.md)
checks with a custom regular expression pattern.

!!! tip "Creating custom pattern data quality checks"

    If multiple columns should be validated using the same regular expression, 
    a better approach would be to define a custom data quality check. 
    
    Please follow the manual for [creating custom data quality checks](../working-with-dqo/creating-custom-data-quality-checks.md)
    that show how to validate a DUNS number with a custom check.

### Validating regular expressions in UI
The [*texts_not_matching_regex_percent*](../checks/column/patterns/texts-not-matching-regex-percent.md)
check is configured by setting the regular expression pattern and a maximum accepted percentage of invalid values.

We will validate the text column that should contain dates in YYYY-MM-DDD format but using a custom regular expression.
The regular expression will be `^[0-9]{4}-[0-9]{2}-[0-9]{2}$`.

![Validate date text with a custom regular expression using DQOps check editor](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/regex-match-data-quality-check-validating-date-string-in-dqops-min2.png){ loading=lazy; width="1200px" }

We can view representative examples of data not matching the regular expression by clicking on the
**Error sampling** tab in the results section.

### Validating regular expressions in YAML
The configuration of the [*texts_not_matching_regex_percent*](../checks/column/patterns/texts-not-matching-regex-percent.md)
check in YAML is simple. 

The regular expression can be wrapped in double quotes to avoid issues when the YAML file is parsed by DQOps.

``` { .yaml linenums="1" hl_lines="13-17" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    created_at:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          patterns:
            daily_texts_not_matching_regex_percent:
              parameters:
                regex: "^[0-9]{4}-[0-9]{2}-[0-9]{2}$"
              error:
                max_percent: 0.0
```


## Use cases
| **Name of the example**                                                                                                 | **Description**                                                                                                                                                                                                                                                         |
|:------------------------------------------------------------------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Percentage of texts not matching a date pattern](../examples/data-validity/percentage-of-texts-matching-date-regex.md) | This example shows how to detect that the percentage of texts not matching the date format regex in a column does not exceed a set threshold using [text_not_matching_date_pattern_percent](../checks/column/patterns/text-not-matching-date-pattern-percent.md) check. |
| [Detect invalid emails](../examples/data-validity/detect-invalid-emails.md)                                             | This example shows how to detect that the number of invalid emails in a column does not exceed the maximum accepted count using [invalid_email_format_found](../checks/column/patterns/invalid-email-format-found.md) check.                                            |
| [Percentage of invalid UUID](../examples/data-validity/percentage-of-valid-uuid.md)                                     | This example shows how to detect that th percentage of valid UUID values in a column does not fall below a set threshold using [invalid_uuid_format_percent](../checks/column/patterns/invalid-uuid-format-percent.md) check.                                           |
| [Detect invalid IP4 address](../examples/data-validity/detect-invalid-ip4-addresses.md)                                 | This example shows how to detect that the number of invalid IP4 address in a column does not exceed a set threshold using [invalid_ip4_address_format_found](../checks/column/patterns/invalid-ip4-address-format-found.md) check.                                      |

## List of patterns checks at a column level
| Data quality check name | Friendly name | Data quality dimension | Description | Standard check |
|-------------------------|---------------|------------------------|-------------|----------------|
|[*text_not_matching_regex_found*](../checks/column/patterns/text-not-matching-regex-found.md)|Maximum count of rows containing texts values not matching regex|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|This check validates text values using a pattern defined as a regular expression. It counts the number of invalid values and raises a data quality issue when the number exceeds a threshold.|:material-check-bold:|
|[*texts_not_matching_regex_percent*](../checks/column/patterns/texts-not-matching-regex-percent.md)|Maximum percent of rows containing texts values not matching regex|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|This check validates text values using a pattern defined as a regular expression. It measures the percentage of invalid values and raises a data quality issue when the rate is above a threshold.|:material-check-bold:|
|[*invalid_email_format_found*](../checks/column/patterns/invalid-email-format-found.md)|Maximum count of rows containing emails in invalid format|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|This check detects invalid email addresses in text columns using a regular expression. It counts the number of invalid emails and raises a data quality issue when the number is above a threshold.|:material-check-bold:|
|[*invalid_email_format_percent*](../checks/column/patterns/invalid-email-format-percent.md)|Minimum percent of rows containing emails in invalid format|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|This check detects invalid email addresses in text columns using a regular expression. It calculated the percentage of invalid emails and raises a data quality issue when the percentage is above a threshold.| |
|[*text_not_matching_date_pattern_found*](../checks/column/patterns/text-not-matching-date-pattern-found.md)|Maximum count of rows containing texts not matching an expected date pattern|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|This check detects dates in the wrong format inside text columns using a regular expression. It counts the number of incorrectly formatted dates and raises a data quality issue when the number exceeds a threshold.| |
|[*text_not_matching_date_pattern_percent*](../checks/column/patterns/text-not-matching-date-pattern-percent.md)|Maximum percentage of rows containing texts not matching an expected date pattern|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|This check validates the date format of dates stored in text columns. It measures the percentage of incorrectly formatted dates and raises a data quality issue when the rate is above a threshold.| |
|[*text_not_matching_name_pattern_percent*](../checks/column/patterns/text-not-matching-name-pattern-percent.md)|Maximum percentage of rows not containing texts that are names (e.g. numeric)|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|This check verifies if values stored in a text column contain only letters and are usable as literal identifiers. It measures the percentage of invalid literal identifiers and raises a data quality issue when the rate is above a threshold.| |
|[*invalid_uuid_format_found*](../checks/column/patterns/invalid-uuid-format-found.md)|Maximum count of rows containing invalid UUID values|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|This check detects invalid UUID identifiers in text columns using a regular expression. It counts the number of invalid UUIDs and raises a data quality issue when the number is above a threshold.| |
|[*invalid_uuid_format_percent*](../checks/column/patterns/invalid-uuid-format-percent.md)|Maximum percentage of rows containing invalid UUID values|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|This check validates the format of UUID values in text columns. It measures the percentage of invalid UUIDs and raises a data quality issue when the rate is above a threshold.| |
|[*invalid_ip4_address_format_found*](../checks/column/patterns/invalid-ip4-address-format-found.md)|Maximum count of rows containing invalid IP4 address values|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|This check detects invalid IP4 internet addresses in text columns using a regular expression. It counts the number of invalid addresses and raises a data quality issue when the number is above a threshold.| |
|[*invalid_ip6_address_format_found*](../checks/column/patterns/invalid-ip6-address-format-found.md)|Maximum count of rows containing invalid IP6 address values|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|This check detects invalid IP6 internet addresses in text columns using a regular expression. It counts the number of invalid addresses and raises a data quality issue when the number is above a threshold.| |
|[*invalid_usa_phone_format_found*](../checks/column/patterns/invalid-usa-phone-format-found.md)|Maximum count of rows containing invalid USA phone number values|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|This check validates the format of USA phone numbers inside text columns. It counts the number of invalid phone number and raises a data quality issue when too many rows contain phone numbers.|:material-check-bold:|
|[*invalid_usa_zipcode_format_found*](../checks/column/patterns/invalid-usa-zipcode-format-found.md)|Maximum count of rows containing invalid USA zip code values|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|This check validates the format of a USA zip code inside text columns. It counts the number of invalid zip code and raises a data quality issue when the rate is below a threshold.| |
|[*invalid_usa_phone_format_percent*](../checks/column/patterns/invalid-usa-phone-format-percent.md)|Maximum percentage of rows containing invalid USA phone number values|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|This check validates the format of USA phone numbers inside text columns. It measures the percentage of columns containing invalid phone numbers and raises a data quality issue when the rate is above a threshold.|:material-check-bold:|
|[*invalid_usa_zipcode_format_percent*](../checks/column/patterns/invalid-usa-zipcode-format-percent.md)|Maximum percentage of rows containing invalid USA zip code values|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|This check validates the format of a USA zip code inside text columns. It measures the percentage of columns containing invalid zip codes and raises a data quality issue when the rate is above a threshold.| |


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/patterns](../checks/column/patterns/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../dqo-concepts/running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../dqo-concepts/configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../dqo-concepts/data-quality-dimensions.md) used by DQOps
