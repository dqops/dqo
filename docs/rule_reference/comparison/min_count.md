# Min count
Data quality rule that verifies if a data quality check reading is greater or equal a minimum value.



## Parameters
This rule has one parameter that should be configured for each alert :

- `low`: 
  <br/>rule threshold for a low severity (1) alert
    - `min_value`: _float_
      <br/>minimum accepted value for the `actual_value` returned by the sensor (inclusive)
- `medium`: 
  <br/>rule threshold for a medium severity (2) alert
    - `min_value`: _float_
      <br/>minimum accepted value for the `actual_value` returned by the sensor (inclusive)
- `high`: 
  <br/>rule threshold for a high severity (3) alert
    - `min_value`: _float_
  <br/>minimum accepted value for the `actual_value` returned by the sensor (inclusive)

## Example
The following example shows how to implement `min_count` rule for a check.

The assigned severity depends on sensor result (see YAML configuration below):

- `result >= 90.0`, the check is passed: valid result
- `90.0 > result >= 80.0` the severity is low (1)
- `80.0 > result >= 70.0` the severity is medium (2)
- `70.0 > result`, the severity is high (3)

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
              min_count:
                low:
                  min_value: 90.0
                medium:
                  min_value: 80.0
                high:
                  min_value: 70.0

```