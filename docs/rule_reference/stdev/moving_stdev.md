# Moving stdev
Data quality rule that verifies if a data quality check reading is comparable to stdev range.
A stdev is calculated based on previous results. The time window in which we define a number of previous results used to calculate stdev is customizable.


## Parameters
This rule has two parameters that should be declared for each alert.

- `low`:
  <br/>rule threshold for a low severity (1) alert
    - `multiple_stdev_above`: _float_
    - `multiple_stdev_below`: _float_
- `medium`:
  <br/>rule threshold for a medium severity (2) alert
    - `multiple_stdev_above`: _float_
    - `multiple_stdev_below`: _float_
- `high`:
  <br/>rule threshold for a high severity (3) alert
    - `multiple_stdev_above`: _float_
    - `multiple_stdev_below`: _float_

## Example
The following example shows how to implement `moving_stdev` rule in `regex_match_percent` sensor for a check.

The assigned severity depends on sensor result (see YAML configuration below):
Let's suppose that average `X` and stdev `Y` are calculated based on last `n` readings. If result is in defined ranges, then the following alerts are returned


- ```X + low.multipe_stdev_above * Y > result > X - low.multiple_stdev_below * Y ```, the check is passed: valid result
- ```X + medium.multiple_stdev_below * Y < result < X - low.multiple_stdev_below * Y ``` and
  ```X + medium.multipe_stdev_above * Y > result > X - low.multipe_stdev_above * Y```
  the severity is low (1)
- ```X + high.multiple_stdev_below * Y < result < X - medium.multiple_stdev_below * Y ``` and
  ```X + high.multiple_stdev_above * Y > result > X - medium.multiple_stdev_above * Y ```
  the severity is low (2)
- ```X + high.multiple_stdev_below * Y > result``` or ```X + high.multiple_stdev_above * Y < result```
  the severity is high (3)

As you can see, time series specification is defined. It's necessary to indicate which column in your table is timestamp. You can do it defining fields `timestamp_column`, `time_gradient`, and `mode` in `time_series` section.
```yaml hl_lines="17-36" linenums="1" 
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  target:
    schema_name: test_data
    table_name: string_dates
  time_series: 
    mode: timestamp_column
    time_gradient: day
    timestamp_column: name_of_timestamp_column
  columns:
    name_of_timestamp_column:
      type_snapshot:
        column_type: DATE
        nullable: true
    emails:
      type_snapshot:
        column_type: STRING
        nullable: true
      checks:
        validity:
          regex_match_percent:
            parameters:
              named_regex: email
            rules:
              moving_stdev:
                low:
                  multiple_stdev_above: 1.0
                  multiple_stdev_below: 1.0
                medium:
                  multiple_stdev_above: 2.0
                  multiple_stdev_below: 2.0
                high:
                  multiple_stdev_above: 3.0
                  multiple_stdev_below: 3.0
                  

```