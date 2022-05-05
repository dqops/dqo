# Percent moving average
Data quality rule that verifies if a data quality check reading is comparable to average. 
An average is calculated based on previous results. The time window in which we define a number of previous results used to calculate average is customizable.  


## Parameters
This rule has two parameters that should be declared for each alert.

- `low`: 
  <br/>rule threshold for a low severity (1) alert
    - `max_percent_above`: _float_
    - `max_percent_below`: _float_
- `medium`: 
  <br/>rule threshold for a medium severity (2) alert
    - `max_percent_above`: _float_
    - `max_percent_below`: _float_
- `high`: 
  <br/>rule threshold for a high severity (3) alert
    - `max_percent_above`: _float_
    - `max_percent_below`: _float_

## Example
The following example shows how to implement `moving_average` rule in `regex_match_percent` sensor for a check.

The assigned severity depends on sensor result (see YAML configuration below):
Let's suppose that average `X` is calculated based on last `n` readings. If result is in defined ranges, then the following alerts are returned


- ```(1 + low.max_percent_above/100) * X > result > (1 - low.max_percent_below/100) * X```, the check is passed: valid result
- ```(1 + medium.max_percent_below/100) * X < result < (1 - low.max_percent_below/100) * X ``` and
`(1 + medium.max_percent_above/100) * X` > `result` > `(1 - medium.max_percent_above/100) * X` 
the severity is low (1)
- ```(1 + high.max_percent_below/100) * X < result < (1 - medium.max_percent_below/100) * X ``` and
```(1 + high.max_percent_above/100) *  > result > (1 - medium.max_percent_above/100) * X ```
the severity is low (2)
- ```(1 + high.max_percent_below/100) * X > result``` or ```(1 + high.max_percent_above/100) < result```
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
              moving_average:
                low:
                  max_percent_above: 5.0
                  max_percent_below: 3.0
                medium:
                  max_percent_above: 10.0
                  max_percent_below: 10.0
                high:
                  max_percent_above: 20.0
                  max_percent_below: 20.0
                  

```