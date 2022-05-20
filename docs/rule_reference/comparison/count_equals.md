# Count equals
Data quality rule that verifies that a data quality check reading equals a given value. A margin of error may be configured.



## Parameters
This check has two parameters that should be configured for each alert:

- `low`: 
  <br/>rule threshold for a low severity (1) alert
    - `expectedValue`: _float_
      <br/>expected value for the actual_value returned by the sensor. The sensor value should equal `expected_value +/- the error_margin`.
    - `errorMargin`: _float_
      <br/>error margin for comparison.
- `medium`: 
  <br/>rule threshold for a medium severity (2) alert
    - `expectedValue`: _float_
      <br/>expected value for the actual_value returned by the sensor. The sensor value should equal `expected_value +/- the error_margin`.
    - `errorMargin`: _float_
      <br/>error margin for comparison.
- `high`: 
  <br/>rule threshold for a high severity (3) alert
    - `expectedValue`: _float_
      <br/>expected value for the actual_value returned by the sensor. The sensor value should equal `expected_value +/- the error_margin`.
    - `errorMargin`: _float_
      <br/>error margin for comparison.

## Example
The following example shows how to implement `count_equal` rule for a check.

The assigned severity depends on sensor result (see YAML configuration below):

- `result >= 90.0`, the check is passed: valid result
- `90.0 > result >= 80.0` the severity is low (1)
- `80.0 > result >= 70.0` the severity is medium (2)
- `70.0 > result`, the severity is high (3)

```yaml hl_lines="17-27" linenums="1" 
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  target:
    schema_name: test_data
    table_name: string_dates
  time_series: null
  columns:
    dates:
      type_snapshot:
        column_type: STRING
        nullable: true
      checks:
        validity:
          date_type_percent:
            rules:
              count_equals:
                low:
                  expected_value: 90.0
                  error_margin: 5.0
                medium:
                  expected_value: 80.0
                  error_margin: 5.0
                high:
                  expected_value: 70.0
                  error_margin: 5.0
```