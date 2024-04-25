# Definition of a data quality rule
Read this guide to understand what is a data quality rule in DQOps, and how it evaluates the data quality measures to detect data quality issues.

## What are the rules in DQOps?

In DQOps, the data quality rule and the [data quality sensor](definition-of-data-quality-sensors.md) form the [data quality check](definition-of-data-quality-checks/index.md).

The rule is a set of conditions against which sensor readouts are verified, described by a list of thresholds.
A basic rule can simply score the most recent data quality result if the value is above or below a particular value or
within the expected range. 

Rules evaluate sensors' results and assign them severity levels. There are 3 severity levels in DQOps: warning, error and
fatal


## Example of rule 

A standard data quality check on a table that counts the number of rows uses a simple "min_count" rule. For example when
the error severity level is set to 10 and the table has fewer than 10 rows the data quality error will be raised. 

Below is an example of a Phyton script that defines classes and methods for min_count threshold rule.

``` py title="min_count.py"
# rule specific parameters object, contains values received from the quality check threshold configuration
class MinCountRuleParametersSpec:
    min_count: int

class HistoricDataPoint:
    timestamp_utc: datetime
    local_datetime: datetime
    back_periods_index: int
    sensor_readout: float

class RuleTimeWindowSettingsSpec:
    prediction_time_window: int
    min_periods_with_readouts: int

# rule execution parameters, contains the sensor value (actual_value) and the rule parameters
class RuleExecutionRunParameters:
    actual_value: float
    parameters: MinCountRuleParametersSpec
    time_period_local: datetime
    previous_readouts: Sequence[HistoricDataPoint]
    time_window: RuleTimeWindowSettingsSpec

# default object that should be returned to the dqo.io engine, specifies if the rule was passed or failed,
# what is the expected value for the rule and what are the upper and lower boundaries of accepted values (optional)
class RuleExecutionResult:
    passed: bool
    expected_value: float
    lower_bound: float
    upper_bound: float

    def __init__(self, passed=None, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound

# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters, 'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    expected_value = rule_parameters.parameters.min_count
    lower_bound = rule_parameters.parameters.min_count
    upper_bound = None
    passed = rule_parameters.actual_value >= lower_bound

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
```

## Rule categories

Rules are divided into the following categories. A full description of each category and subcategory of rules is 
available at the link.

- [averages](../reference/rules/Averages.md):
    - [between percent moving average 7 days](../reference/rules/Averages.md#between-percent-moving-average-7-days)
    - [between percent moving average 30 days](../reference/rules/Averages.md#between-percent-moving-average-30-days)
    - [between percent moving average 60 days](../reference/rules/Averages.md#between-percent-moving-average-60-days)
    - [percent moving average](../reference/rules/Averages.md#percent-moving-average)
    - [within percent moving average 7 days](../reference/rules/Averages.md#within-percent-moving-average-7-days)
    - [within percent moving average 30 days](../reference/rules/Averages.md#within-percent-moving-average-30-days)
    - [within percent moving average 60 days](../reference/rules/Averages.md#within-percent-moving-average-60-days)
- [change since the last readout or a reference date](../reference/rules/Change.md):
    - [between change](../reference/rules/Change.md#between-change)
    - [between change 1 day](../reference/rules/Change.md#between-change-1-day)
    - [between change 7 days](../reference/rules/Change.md#between-change-7-days)
    - [between change 30 days](../reference/rules/Change.md#between-change-30-days)
    - [between percent change](../reference/rules/Change.md#between-percent-change)
    - [between percent change 1 day](../reference/rules/Change.md#between-percent-change-1-day)
    - [between percent change 7 days](../reference/rules/Change.md#between-percent-change-7-days)
    - [between percent change 30 days](../reference/rules/Change.md#between-percent-change-30-days)
    - [change difference](../reference/rules/Change.md#change-difference)
    - [change difference 1 day](../reference/rules/Change.md#change-difference-1-day)
    - [change difference 7 days](../reference/rules/Change.md#change-difference-7-days)
    - [change difference 30 days](../reference/rules/Change.md#change-difference-30-days)
    - [change percent](../reference/rules/Change.md#change-percent)
    - [change percent 1 day](../reference/rules/Change.md#change-percent-1-day)
    - [change percent 7 days](../reference/rules/Change.md#change-percent-7-days)
    - [change percent 30 days](../reference/rules/Change.md#change-percent-30-days)
- [compare the actual value (sensor readout) to an expected value](../reference/rules/Comparison.md):
    - [between floats](../reference/rules/Comparison.md#between-floats)
    - [between ints](../reference/rules/Comparison.md#between-ints)
    - [count between](../reference/rules/Comparison.md#count-between)
    - [detected datatype equals](../reference/rules/Comparison.md#detected-datatype-equals)
    - [diff percent](../reference/rules/Comparison.md#diff-percent)
    - [equals](../reference/rules/Comparison.md#equals)
    - [equals 0](../reference/rules/Comparison.md#equals-0)
    - [equals 1](../reference/rules/Comparison.md#equals-1)
    - [equals integer](../reference/rules/Comparison.md#equals-integer)
    - [max](../reference/rules/Comparison.md#max)
    - [max count](../reference/rules/Comparison.md#max-count)
    - [max days](../reference/rules/Comparison.md#max-days)
    - [max failures](../reference/rules/Comparison.md#max-failures)
    - [max percent](../reference/rules/Comparison.md#max-percent)
    - [max value](../reference/rules/Comparison.md#max-value)
    - [min](../reference/rules/Comparison.md#min)
    - [min count](../reference/rules/Comparison.md#min-count)
    - [min percent](../reference/rules/Comparison.md#min-percent)
    - [min value](../reference/rules/Comparison.md#min-value)
    - [value changed](../reference/rules/Comparison.md#value-changed)
- [percentile](../reference/rules/Percentile.md):
    - [anomaly differencing percentile moving average](../reference/rules/Percentile.md#anomaly-differencing-percentile-moving-average)
    - [anomaly differencing percentile moving average 30 days](../reference/rules/Percentile.md#anomaly-differencing-percentile-moving-average-30-days)
    - [anomaly stationary percentile moving average](../reference/rules/Percentile.md#anomaly-stationary-percentile-moving-average)
    - [anomaly stationary percentile moving average 30 days](../reference/rules/Percentile.md#anomaly-stationary-percentile-moving-average-30-days)
    - [change percentile moving 7 days](../reference/rules/Percentile.md#change-percentile-moving-7-days)
    - [change percentile moving 30 days](../reference/rules/Percentile.md#change-percentile-moving-30-days)
    - [change percentile moving 60 days](../reference/rules/Percentile.md#change-percentile-moving-60-days)
    - [percentile moving 7 days](../reference/rules/Percentile.md#percentile-moving-7-days)
    - [percentile moving 30 days](../reference/rules/Percentile.md#percentile-moving-30-days)
    - [percentile moving 60 days](../reference/rules/Percentile.md#percentile-moving-60-days)
- [standard deviation](../reference/rules/Stdev.md):
    - [change multiply moving stddev population 7 days](../reference/rules/Stdev.md#change-multiply-moving-stdev-7-days)
    - [change multiply moving stddev population 30 days](../reference/rules/Stdev.md#change-multiply-moving-stdev-30-days)
    - [change multiply moving stddev population 60 days](../reference/rules/Stdev.md#change-multiply-moving-stdev-60-days)
    - [change multiply moving stddev within 7 days](../reference/rules/Stdev.md#change-multiply-moving-stdev-within-7-days)
    - [change multiply moving stddev within 30 days](../reference/rules/Stdev.md#change-multiply-moving-stdev-within-30-days)
    - [change multiply moving stddev within 60 days](../reference/rules/Stdev.md#change-multiply-moving-stdev-within-60-days)
    - [multiply moving stddev 7 days](../reference/rules/Stdev.md#multiply-moving-stdev-7-days)
    - [multiply moving stddev 30 days](../reference/rules/Stdev.md#multiply-moving-stdev-30-days)
    - [multiply moving stddev 60 days](../reference/rules/Stdev.md#multiply-moving-stdev-60-days)
    - [multiply moving stddev within 7 days](../reference/rules/Stdev.md#multiply-moving-stdev-within-7-days)
    - [multiply moving stddev within 30 days](../reference/rules/Stdev.md#multiply-moving-stdev-within-30-days)
    - [multiply moving stddev within 60 days](../reference/rules/Stdev.md#multiply-moving-stdev-within-60-days)


## Configure rules in UI
You can easily configure rules in DQOps using the **Configuration** section of the user interface.

### **Rule specification screen**
Below is an example of screen with the rule definition for
the [percent moving average](../reference/rules/Averages.md#percent-moving-average) rule.

This screen is responsible for editing the specification files for a custom data quality rule
stored in the [*$DQO_USER_HOME/checks/\*\*/\*.dqocheck.yaml*](../reference/yaml/RuleDefinitionYaml.md) files.

![Rule definition configuration](https://dqops.com/docs/images/concepts/rule-definition-configuration.png){ loading=lazy; width="1200px" }

### **Python code editor**
The Python source code of the data quality rule is defined on the *Python code* tab.

![Rule Python code configuration](https://dqops.com/docs/images/concepts/rule-python-code-configuration.png){ loading=lazy; width="1200px" }


## What's next
- Learn how DQOps [executes data quality sensors and rules](architecture/data-quality-check-execution-flow.md).
- Review the schema of a [check_results](../reference/parquetfiles/check_results.md) Parquet table where DQOps stores
  the data quality check results after validating the measure (sensor readout) captured by a [data quality sensor](definition-of-data-quality-sensors.md).
