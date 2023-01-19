# Rules

## Severity level

Rules are functions that evaluate sensors results and assigns them severity levels: low (1), medium (2), high (3), or
information that the result is valid. 

!!! cite "Severity level"
    3-degree alert system that assigns levels to the sensor's results as low, medium or high.

## Rule configuration

Rules are split into the following categories:

- comparison:
    - [min count](../../rule_reference/comparison/min_count.md)
    - [count equals](../../rule_reference/comparison/count_equals.md)
- averages:
    - [moving average](../../rule_reference/averages/moving_average.md)
- stdev:
    - [moving_stdev](../../rule_reference/stdev/moving_stdev.md)

Basic rules compare sensor results to the thresholds (numerical values) configured directly by the user.
Configuration for such sensors is done by providing `mode: current_value` in rule configuration.


There is also `mode: previous_redaings` that enables calculating rules thresholds based on historical values. 

=== "Current value"
    ```yaml
    --8<-- "docs/dqo_concept/rules/rule_config_current.yaml"
    ```
=== "Historical values"
    ```yaml
    --8<-- "docs/dqo_concept/rules/rule_config_historical.yaml"
    ```

## How to configure a rule for check
Rules are configured under sensor name field. Below _row_count_ sensor there is a configuration that sets up the rule _min_count_. 

```yaml linenums="1" hl_lines="14-21"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  target:
    schema_name: test_data
    table_name: string_dates
  time_series:
    mode: current_time
    time_gradient: day
  checks:
    consistency:
      row_count:
        rules:
          min_count:
            low:
              min_value: 90.0
            medium:
              min_value: 80.0
            high:
              min_value: 70.0
  columns:
    dates:
      type_snapshot:
        column_type: STRING
        nullable: true
```