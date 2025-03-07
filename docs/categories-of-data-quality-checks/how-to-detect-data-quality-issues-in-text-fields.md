---
title: How to detect out-of-range text values? Examples
---
# How to detect out-of-range text values? Examples
Read this guide to learn how to find text values that are too short or too long, which are most likely invalid values stored in a database.

The data quality checks that detect issues with too short or too long texts are configured in the `text` category in DQOps.

## Text statistics
The statistics about text values are pretty simple. 
We can analyze the length of text values and find the shorted or longest text values. 
The statistics around text values are less sophisticated than calculating metrics for numeric values,
but observing the length of strings can still reveal many data quality issues.

### Issues with too short texts
Texts that are shorter than a reasonable minimum may indicate potential data corruption. For example, a phone number 
should not be only two digits, and an email address with just two letters is likely to be incorrect 
as it does not include the domain name.

Too short texts can be a result of:

- Someone accidentally truncated the text manually.

- A user entered incomplete values during data entry.

- The data was corrupted in transport.

- The data loading was interrupted because the platform ran out of disk space.

- A bug in the transformation logic truncated the text.

- The temporary variable or a column in a temporary table was too short, causing truncation.

To detect these types of possible data corruption, we can choose a reasonable minimum text length
that should fit the smallest valid value, such as a phone number.

Short, truncated texts can lead to issues with completeness and uniqueness. 
If additional identifiers are also truncated, it can result in duplicate data.

### Issues with too long texts
Texts that are longer than expected can be caused by other problems.

- The data is corrupted.

- Column values were concatenated together because a wrong separator was used to load the values.

- The column is used for different purposes than it was designed for. 
  Users use the phone column to enter additional comments about how to contact a person.

- The maximum column length was incorrectly estimated and unable to fit valid values, such as address lines.

Texts that are too long can also cause other problems.

- They take more storage.

- Indexes are growing too big, and queries take longer to run.

- The target column length is shorter, and valid texts will be truncated.

It is wise to find out how long the longest text should be that we plan to store in a column. 
A data quality check should monitor the data to find texts that are longer than expected.

## Text length checks
DQOps has several data quality checks for validating the length of texts. 
They are very similar to each other, but they can still detect different types of length issues.

- The [*text_min_length*](../checks/column/text/text-min-length.md) check captures the length of the shortest text and validates it using a rule parameter.
  The *actual_value* field in the data quality check results will show the length of the shortest identified text.

- The [*text_max_length*](../checks/column/text/text-max-length.md) check captures the length of the longest text and validates it using a rule parameter.
  The *actual_value* field in the data quality check results will show the length of the longest identified text.

- The [*text_mean_length*](../checks/column/text/text-mean-length.md) check calculates the average text length. 
  The mean length is validated by a rule, and it must be in the range of accepted values.

- The [*text_length_below_min_length*](../checks/column/text/text-length-below-min-length.md) check finds texts shorter than a given minimum length.

- The [*text_length_below_min_length_percent*](../checks/column/text/text-length-below-min-length-percent.md) check counts texts shorter than a given
  minimum length and calculates a percentage of a too-short text in the whole column.

- The [*text_length_above_max_length*](../checks/column/text/text-length-above-max-length.md) check finds texts longer than a given maximum length.

- The [*text_length_above_max_length_percent*](../checks/column/text/text-length-above-max-length-percent.md) check counts texts longer than a given maximum length
  and calculates a percentage of a too-short text in the whole column.

- The [*text_length_in_range_percent*](../checks/column/text/text-length-in-range-percent.md) check measures the percentage of valid texts whose length
  is within a minimum and maximum accepted length.

- The [*min_word_count*](../checks/column/text/min-word-count.md) check verifies that the minimum word count of the text column is in the range.

- The [*max_word_count*](../checks/column/text/max-word-count.md) check verifies that the maximum word count of the text is in the range.

### Profiling the text length
DQOps shows the text length statistics on the column's profile screen.
The values are:

- The **Text min length** shows the length of the shortest text in the column.
- The **Text max length** shows the length of the longest text in the column.
- The **Text mean length** shows the average text length.
- The **Text min word count** shows the minimal number of words in the column.
- The **Text max word count** shows the maximal number of words in the column.

![Data profiling a text column length in DQOps](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/data-profiling-text-column-length-in-dqops-min2.png){ loading=lazy; width="1200px" }

## Minimum text length
The [*text_min_length*](../checks/column/text/text-min-length.md) has two optional rule parameters.

- The **from** parameter configures a minimum text length bottom range.
- The **to** parameter configures a minimum text length upper range.

### Verifying minimum text length in UI
The following screenshot shows configuring the [*text_min_length*](../checks/column/text/text-min-length.md) check 
in the [DQOps data quality check editor](../dqo-concepts/dqops-user-interface-overview.md#check-editor).

![Configuring minimum text length in range data quality check in DQOps](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/min-text-length-in-range-data-quality-check-in-dqops-min2.png){ loading=lazy; width="1200px" }

### Verifying minimum text length in YAML
The configuration of the [*text_min_length*](../checks/column/text/text-min-length.md) check in YAML is simple.

``` { .yaml linenums="1" hl_lines="13-16" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    state_name:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          text:
            daily_text_min_length:
              warning:
                from: 4
                to: 6
```

## Text length anomalies
DQOps does not provide built-in anomaly detection checks for the text length because
that would extend the list of supported data quality checks, making the platform too complex to learn.

Instead, it is effortless to customize the built-in checks by combining the sensors:

- [*column/text/text_min_length*](../reference/sensors/column/text-column-sensors.md#text-min-length)
  sensor that finds the length of the shortest text,
- [*column/text/text_max_length*](../reference/sensors/column/text-column-sensors.md#text-max-length)
  sensor that finds the length of the longest text,
- [*column/text/text_mean_length*](../reference/sensors/column/text-column-sensors.md#text-mean-length)
  sensor that calculates the average length.

And one of the anomaly or change detection rules:

- [*percentile/anomaly_stationary_percentile_moving_average*](../reference/rules/Percentile.md#anomaly-stationary-percentile-moving-average)
  that finds anomalies in a 90 days time window,
- [*change/change_percent_1_day*](../reference/rules/Change.md#change-percent-1-day)
  that detects an increase or a decrease in the captured value (such as a maximum text length).

The following screenshot shows the configuration of a custom data quality check that detects changes
to the minimum or maximum text length.

![Creating custom data quality check that detects anomalies in the maximum text length](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/creating-custom-text-maximum-length-anomaly-detection-check-dqops-min2.png){ loading=lazy; width="1200px" }

## Use cases
| **Name of the example**                                                                                        | **Description**                                                                                                                                                                                                                         |
|:---------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [A text not exceeding a maximum length](../examples/data-reasonability/text-not-exceeding-a-maximum-length.md) | This example shows how to check that the length of the text does not exceed the maximum value using [text_max_length](../checks/column/text/text-length-above-max-length-percent.md) check.                                             |

## List of text checks at a column level
| Data quality check name | Friendly name | Data quality dimension | Description | Standard check |
|-------------------------|---------------|------------------------|-------------|----------------|
|[*text_min_length*](../checks/column/text/text-min-length.md)|Verify that the minimum length of the text column is in the range|[Reasonableness](../dqo-concepts/data-quality-dimensions.md#data-reasonableness)|This check finds the length of the shortest text in a column. DQOps validates the shortest length using a range rule. DQOps raises an issue when the minimum text length is outside a range of accepted values.|:material-check-bold:|
|[*text_max_length*](../checks/column/text/text-max-length.md)|Verify that the maximum length of the text is in the range|[Reasonableness](../dqo-concepts/data-quality-dimensions.md#data-reasonableness)|This check finds the length of the longest text in a column. DQOps validates the maximum length using a range rule. DQOps raises an issue when the maximum text length is outside a range of accepted values.|:material-check-bold:|
|[*text_mean_length*](../checks/column/text/text-mean-length.md)|Verify that the mean length of the text is in the range|[Reasonableness](../dqo-concepts/data-quality-dimensions.md#data-reasonableness)|This check calculates the average text length in a column. DQOps validates the mean length using a range rule. DQOps raises an issue when the mean text length is outside a range of accepted values.|:material-check-bold:|
|[*text_length_below_min_length*](../checks/column/text/text-length-below-min-length.md)|Find text values shorter than the minimum accepted length|[Reasonableness](../dqo-concepts/data-quality-dimensions.md#data-reasonableness)|This check finds texts that are shorter than the minimum accepted text length. It counts the number of texts that are too short and raises a data quality issue when too many invalid texts are found.| |
|[*text_length_below_min_length_percent*](../checks/column/text/text-length-below-min-length-percent.md)|Measure the percentage of rows containing text values shorter than the minimum accepted length|[Reasonableness](../dqo-concepts/data-quality-dimensions.md#data-reasonableness)|This check finds texts that are shorter than the minimum accepted text length. It measures the percentage of too short texts and raises a data quality issue when too many invalid texts are found.| |
|[*text_length_above_max_length*](../checks/column/text/text-length-above-max-length.md)|Find text values longer than the maximum accepted length|[Reasonableness](../dqo-concepts/data-quality-dimensions.md#data-reasonableness)|This check finds texts that are longer than the maximum accepted text length. It counts the number of texts that are too long and raises a data quality issue when too many invalid texts are found.| |
|[*text_length_above_max_length_percent*](../checks/column/text/text-length-above-max-length-percent.md)|Measure the percentage of rows containing text values longer than the maximum accepted length|[Reasonableness](../dqo-concepts/data-quality-dimensions.md#data-reasonableness)|This check finds texts that are longer than the maximum accepted text length. It measures the percentage of texts that are too long and raises a data quality issue when too many invalid texts are found.| |
|[*text_length_in_range_percent*](../checks/column/text/text-length-in-range-percent.md)|Measure the percentage of rows containing text values in the expected range|[Reasonableness](../dqo-concepts/data-quality-dimensions.md#data-reasonableness)|This check verifies that the minimum and maximum lengths of text values are in the range of accepted values. It measures the percentage of texts with a valid length and raises a data quality issue when an insufficient number of texts have a valid length.| |
|[*min_word_count*](../checks/column/text/min-word-count.md)|Verify that the minimum word count of the text column is in the range|[Reasonableness](../dqo-concepts/data-quality-dimensions.md#data-reasonableness)|This check finds the lowest count of words in text in a column. DQOps validates the shortest length using a range rule. DQOps raises an issue when the minimum word count is outside a range of accepted values.|:material-check-bold:|
|[*max_word_count*](../checks/column/text/max-word-count.md)|Verify that the maximum word count of the text is in the range|[Reasonableness](../dqo-concepts/data-quality-dimensions.md#data-reasonableness)|This check finds the highest count of words in text in a column. DQOps validates the maximum length using a range rule. DQOps raises an issue when the maximum word count is outside a range of accepted values.|:material-check-bold:|


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/text](../checks/column/text/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../dqo-concepts/running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../dqo-concepts/configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../dqo-concepts/data-quality-dimensions.md) used by DQOps
