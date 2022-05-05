# Max count
Data quality rule that verifies if a data quality check reading is lesser or equal a maximal value.



## Parameters
This checks has one parameter that should be configured for each alert:

- `low`: 
  <br/>rule threshold for a low severity (1) alert
    - `max_value`: _float_
      <br/>maximal accepted value for the `actual_value` returned by the sensor (inclusive)
- `medium`: 
  <br/>rule threshold for a medium severity (2) alert
    - `max_value`: _float_
      <br/>maximal accepted value for the `actual_value` returned by the sensor (inclusive)
- `high`: 
  <br/>rule threshold for a high severity (3) alert
    - `max_value`: _float_
      <br/>maximal accepted value for the `actual_value` returned by the sensor (inclusive)

## Example
The following example shows how to implement `max_count` rule for a check.

The assigned severity depends on sensor result (see YAML configuration below):

- `result <= 12.0`, the check is passed: valid result
- `12.0 < result <= 24.0` the severity is low (1)
- `24.0 < result <= 36.0` the severity is medium (2)
- `36.0 < result`, the severity is high (3)

```yaml hl_lines="17-24" linenums="1" 
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
              max_count:
                low:
                  max_value: 12.0
                medium:
                  max_value: 24.0
                high:
                  max_value: 36.0
```