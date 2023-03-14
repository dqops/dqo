# Rules

In DQO, the data quality rule and the [data quality sensor](./sensors/sensors.md) form the [data quality check](./checks/index.md).

Rule is a set of conditions against which sensor readouts are verified, described by a list of thresholds.
A basic rule can simply score the most recent data quality result if the value is within the expected range. 

A standard data quality check on a table that counts the number of rows uses a simple "greater than 10" rule to 
instantly raise data quality alerts when the table has fewer than 10 rows.

## Example of comparison max rule 


``` py title="max.py"

# rule specific parameters object, contains values received from the quality check threshold configuration
class MaxRuleParametersSpec:
max_value: float


class HistoricDataPoint:
timestamp_utc: datetime
local_datetime: datetime
back_periods_index: int
sensor_readout: float


class RuleTimeWindowSettingsSpec:
prediction_time_window: int
max_periods_with_readouts: int


# rule execution parameters, contains the sensor value (actual_value) and the rule parameters
class RuleExecutionRunParameters:
actual_value: float
parameters: MaxRuleParametersSpec
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
if not hasattr(rule_parameters, 'actual_value'):
return RuleExecutionResult()

    expected_value = None
    lower_bound = None
    upper_bound = rule_parameters.parameters.max_value
    passed = rule_parameters.actual_value <= upper_bound

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
```

## Rule categories

Rules are divided into the following categories. A full description of each category and subcategory of rules is 
available at the link.

- averages:
    - [between percent moving average 30 days](../../../reference/rules/averages/#between-percent-moving-average-30-days)
    - [between percent moving average 60 days](../../../reference/rules/averages/#between-percent-moving-average-60-days)
    - [between percent moving average 7 days](../../../reference/rules/averages/#between-percent-moving-average-7-days)
    - [percent moving average](../../../reference/rules/averages/#percent-moving-average)
    - [within percent moving average 30 days](../../../reference/rules/averages/#within-percent-moving-average-30-days)
    - [within percent moving average 60 days](../../../reference/rules/averages/#within-percent-moving-average-60-days)
    - [within percent moving average 7 days](../../../reference/rules/averages/#within-percent-moving-average-7-days)
- comparison:
    - [between floats](../../../reference/rules/comparison/#between-floats)
    - [between ints](../../../reference/rules/comparison/#between-ints)
    - [equals](../../../reference/rules/comparison/#equals)
    - [max](../../../reference/rules/comparison/#max)
    - [max count](../../../reference/rules/comparison/#max-count)
    - [max days](../../../reference/rules/comparison/#max-days)
    - [max failures](../../../reference/rules/comparison/#max-failures)
    - [max percent](../../../reference/rules/comparison/#max-percent)
    - [max value](../../../reference/rules/comparison/#max-value)
    - [min](../../../reference/rules/comparison/#min)
    - [min count](../../../reference/rules/comparison/#min-count)
    - [min percent](../../../reference/rules/comparison/#min-percent)
    - [min value](../../../reference/rules/comparison/#min-value)
- stdev:
    - [below percent population stdev 30 days](../../../reference/rules/stdev/#below-percent-population-stdev-30-days)
    - [below percent population stdev 60 days](../../../reference/rules/stdev/#below-percent-population-stdev-60-days)
    - [below percent population stdev 7 days](../../../reference/rules/stdev/#below-percent-population-stdev-7-days)
    - [below stdev multiply 30 days](../../../reference/rules/stdev/#below-stdev-multiply-30-days)
    - [below stdev multiply 60 days](../../../reference/rules/stdev/#below-stdev-multiply-60-days)
    - [below stdev multiply 7 days](../../../reference/rules/stdev/#below-stdev-multiply-7-days)
    - [percent moving stdev](../../../reference/rules/stdev/#percent-moving-stdev)
    - [percentile moving stdev](../../../reference/rules/stdev/#percentile-moving-stdev)