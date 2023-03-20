# Rules

In DQO, the data quality rule and the [data quality sensor](./sensors/sensors.md) form the [data quality check](./checks/index.md).

Rule is a set of conditions against which sensor readouts are verified, described by a list of thresholds.
A basic rule can simply score the most recent data quality result if the value is above or below particular value or
within the expected range. 

Rules evaluate sensors results and assigns them severity levels. There are 3 severity levels in DQO: warning, error and
fatal


## Example of rule 

A standard data quality check on a table that counts the number of rows uses a simple "min_count" rule. For example when
the error severity level is set to 10 and the table has fewer than 10 rows the data quality error will be raised. 

Below is an example of Phyton script that defines classes and methods for min_count threshold rule.

``` py title="min_count.py"

# Class that specifies the minimum count parameter for the rule. 
class MinCountRuleParametersSpec:
    min_count: float

# Class that represents a historical data point from the sensor.
class HistoricDataPoint:
    timestamp_utc: datetime
    local_datetime: datetime
    back_periods_index: int
    sensor_readout: float

# Class that specifies the time window settings for the rule.
class RuleTimeWindowSettingsSpec:
    prediction_time_window: int
    min_periods_with_readouts: int


# Class Rthat specifies the parameters for running the rule. 
class RuleExecutionRunParameters:
    actual_value: float
    parameters: MinCountRuleParametersSpec
    time_period_local: datetime
    previous_readouts: Sequence[HistoricDataPoint]
    time_window: RuleTimeWindowSettingsSpec


# Class that specifies the result of running the rule.
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


# A method that evaluates the rule based on the parameters specified in the RuleExecutionRunParameters class.
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters, 'actual_value'):
        return RuleExecutionResult()

    expected_value = None
    lower_bound = rule_parameters.parameters.min_count
    upper_bound = None
    passed = rule_parameters.actual_value >= lower_bound

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