# comparison
___

## **between floats**
**Full rule name**
```
comparison/between_floats
```
**Description**  
Data quality rule that verifies if a data quality check readout is between from and to values.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|from|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| ||
|to|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: current_value
  fields:
  - field_name: from
    display_name: from
    help_text: Minimum accepted value for the actual_value returned by the sensor
      (inclusive).
    data_type: double
    sample_values:
    - 10.0
  - field_name: to
    display_name: to
    help_text: Maximum accepted value for the actual_value returned by the sensor
      (inclusive).
    data_type: double
    sample_values:
    - 20.5
```



**Rule implementation (Python)**
```python
from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class BetweenIntsRuleParametersSpec:
    from_: float
    to: float

    def __getattr__(self, name):
        if name == "from":
            return self.from_
        return object.__getattribute__(self, name)

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
    parameters: BetweenIntsRuleParametersSpec
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

    expected_value = None
    lower_bound = getattr(rule_parameters.parameters,"from")
    upper_bound = rule_parameters.parameters.to
    passed = lower_bound <= rule_parameters.actual_value <= upper_bound

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **between ints**
**Full rule name**
```
comparison/between_ints
```
**Description**  
Data quality rule that verifies if a data quality check readout is between begin and end values.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|from|Minimum accepted value for the actual_value returned by the sensor (inclusive).|long| ||
|to|Maximum accepted value for the actual_value returned by the sensor (inclusive).|long| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: current_value
  fields:
  - field_name: from
    display_name: from
    help_text: Minimum accepted value for the actual_value returned by the sensor
      (inclusive).
    data_type: long
    sample_values:
    - 10
  - field_name: to
    display_name: to
    help_text: Maximum accepted value for the actual_value returned by the sensor
      (inclusive).
    data_type: long
    sample_values:
    - 20
```



**Rule implementation (Python)**
```python
from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class BetweenIntsRuleParametersSpec:
    from_: int
    to: int

    def __getattr__(self, name):
        if name == "from":
            return self.from_
        return object.__getattribute__(self, name)


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
    parameters: BetweenIntsRuleParametersSpec
    time_period_local: datetime
    previous_readouts: Sequence[HistoricDataPoint]
    time_window: RuleTimeWindowSettingsSpec


# default object that should be returned to the dqo.io engine, specifies if the rule was passed or failed,
# what is the expected value for the rule and what are the upper and lower boundaries of accepted values (optional)
class RuleExecutionResult:
    passed: bool
    expected_value: int
    lower_bound: int
    upper_bound: int

    def __init__(self, passed=None, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    expected_value = None
    lower_bound = getattr(rule_parameters.parameters, "from")
    upper_bound = rule_parameters.parameters.to
    passed = lower_bound <= rule_parameters.actual_value <= upper_bound

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **datatype equals**
**Full rule name**
```
comparison/datatype_equals
```
**Description**  
Data quality rule that verifies that a data quality check readout of a string_datatype_detect (the data type detection) matches an expected data type.
 The supported values are in the range 1..7, which are: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|expected_datatype|Expected data type code, the data type codes are: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.|integer| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: current_value
  fields:
  - field_name: expected_datatype
    display_name: expected_datatype
    help_text: &quot;Expected data type code, the data type codes are: 1 - integers, 2\
      \ - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed\
      \ data types.&quot;
    data_type: integer
    sample_values:
    - 1
```



**Rule implementation (Python)**
```python
from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class EqualsRuleParametersSpec:
    expected_datatype: int


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
    parameters: EqualsRuleParametersSpec
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

    expected_value = rule_parameters.parameters.expected_datatype
    lower_bound = expected_value
    upper_bound = expected_value
    passed = (expected_value == rule_parameters.actual_value)

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
```
___

## **diff percent**
**Full rule name**
```
comparison/diff_percent
```
**Description**  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_diff_percent|Maximum accepted value for the percentage of difference between expected_value and actual_value returned by the sensor (inclusive).|double| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: current_value
  fields:
  - field_name: max_diff_percent
    display_name: max_diff_percent
    help_text: Maximum accepted value for the percentage of difference between expected_value
      and actual_value returned by the sensor (inclusive).
    data_type: double
```



**Rule implementation (Python)**
```python
from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class DiffPercentRuleParametersSpec:
    max_diff_percent: float


class HistoricDataPoint:
    timestamp_utc: datetime
    local_datetime: datetime
    back_periods_index: int
    sensor_readout: float


class RuleTimeWindowSettingsSpec:
    prediction_time_window: int
    min_periods_with_readouts: int


# rule execution parameters, contains the sensor value (expected_value, actual_value) and the rule parameters
class RuleExecutionRunParameters:
    expected_value: float
    actual_value: float
    parameters: DiffPercentRuleParametersSpec
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
    has_expected_value = hasattr(rule_parameters, 'expected_value')
    has_actual_value = hasattr(rule_parameters, 'actual_value')
    if not has_expected_value and not has_actual_value:
        return RuleExecutionResult(True, None, None, None)

    if not has_expected_value:
        return RuleExecutionResult(False, None, None, None)

    expected_value = rule_parameters.expected_value
    lower_bound = rule_parameters.expected_value - (rule_parameters.parameters.max_diff_percent/100 * rule_parameters.expected_value)
    upper_bound = rule_parameters.expected_value + (rule_parameters.parameters.max_diff_percent/100 * rule_parameters.expected_value)
    if has_actual_value:
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    else:
        passed = False

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
```
___

## **equals**
**Full rule name**
```
comparison/equals
```
**Description**  
Data quality rule that verifies that a data quality check readout equals a given value. A margin of error may be configured.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|expected_value|Expected value for the actual_value returned by the sensor. The sensor value should equal expected_value +/- the error_margin.|double| ||
|error_margin|Error margin for comparison.|double| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: current_value
  fields:
  - field_name: expected_value
    display_name: expected_value
    help_text: Expected value for the actual_value returned by the sensor. The sensor
      value should equal expected_value +/- the error_margin.
    data_type: double
    sample_values:
    - 10.0
  - field_name: error_margin
    display_name: error_margin
    help_text: Error margin for comparison.
    data_type: double
    sample_values:
    - 0.01
```



**Rule implementation (Python)**
```python
from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class EqualsRuleParametersSpec:
    expected_value: float
    error_margin: float


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
    parameters: EqualsRuleParametersSpec
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

    expected_value = rule_parameters.parameters.expected_value
    lower_bound = expected_value - rule_parameters.parameters.error_margin
    upper_bound = expected_value + rule_parameters.parameters.error_margin
    passed = lower_bound <= rule_parameters.actual_value <= upper_bound

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
```
___

## **equals integer**
**Full rule name**
```
comparison/equals_integer
```
**Description**  
Data quality rule that verifies that a data quality check readout equals a given integer value, with an expected value preconfigured as 1.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|expected_value|Expected value for the actual_value returned by the sensor. It must be an integer value.|long| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: current_value
  fields:
  - field_name: expected_value
    display_name: expected_value
    help_text: Expected value for the actual_value returned by the sensor. It must
      be an integer value.
    data_type: long
    sample_values:
    - 1
```



**Rule implementation (Python)**
```python
from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class EqualsIntegerRuleParametersSpec:
    expected_value: int


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
    parameters: EqualsIntegerRuleParametersSpec
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

    expected_value = rule_parameters.parameters.expected_value
    lower_bound = expected_value
    upper_bound = expected_value
    passed = rule_parameters.actual_value == expected_value

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
```
___

## **max**
**Full rule name**
```
comparison/max
```
**Description**  
Data quality rule that verifies if a data quality check readsout is less or equal a maximum value.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_value|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: current_value
  fields:
  - field_name: max_value
    display_name: max_value
    help_text: Maximum accepted value for the actual_value returned by the sensor
      (inclusive).
    data_type: double
    sample_values:
    - 1.5
```



**Rule implementation (Python)**
```python
from datetime import datetime
from typing import Sequence


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

    def __init__(self, passed=None, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters, 'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    expected_value = rule_parameters.parameters.max_value
    lower_bound = None
    upper_bound = rule_parameters.parameters.max_value
    passed = rule_parameters.actual_value <= upper_bound

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **max count**
**Full rule name**
```
comparison/max_count
```
**Description**  
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_count|Maximum accepted value for the actual_value returned by the sensor (inclusive).|long| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: current_value
  fields:
  - field_name: max_count
    display_name: max_count
    help_text: Maximum accepted value for the actual_value returned by the sensor
      (inclusive).
    data_type: long
```



**Rule implementation (Python)**
```python
from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class MaxCountRuleParametersSpec:
    max_count: int


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
    parameters: MaxCountRuleParametersSpec
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
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    expected_value = rule_parameters.parameters.max_count
    lower_bound = None
    upper_bound = rule_parameters.parameters.max_count
    passed = rule_parameters.actual_value <= upper_bound

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **max days**
**Full rule name**
```
comparison/max_days
```
**Description**  
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_days|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: current_value
  fields:
  - field_name: max_days
    display_name: max_days
    help_text: Maximum accepted value for the actual_value returned by the sensor
      (inclusive).
    data_type: double
```



**Rule implementation (Python)**
```python
from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class MaxValueRuleParametersSpec:
    max_days: float


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
    parameters: MaxValueRuleParametersSpec
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
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    expected_value = rule_parameters.parameters.max_days
    lower_bound = None
    upper_bound = rule_parameters.parameters.max_days
    passed = rule_parameters.actual_value <= upper_bound

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **max failures**
**Full rule name**
```
comparison/max_failures
```
**Description**  
Data quality rule that verifies if the number of executive failures (the sensor returned 0) is below the max_failures. The default maximum failures is 0 failures (the first failure is reported).

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_failures|Maximum number of consecutive days with check failures. A check is failed when a sensor query fails due to a connection error, missing or corrupted table.|long| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 60
    min_periods_with_readouts: 0
    historic_data_point_grouping: last_n_readouts
  fields:
  - field_name: max_failures
    display_name: max_failures
    help_text: &quot;Maximum number of consecutive days with check failures. A check is\
      \ failed when a sensor query fails due to a connection error, missing or corrupted\
      \ table.&quot;
    data_type: long
```



**Rule implementation (Python)**
```python
from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class MaxFailuresRuleParametersSpec:
    max_failures: int


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
    parameters: MaxFailuresRuleParametersSpec
    time_period_local: datetime
    previous_readouts: Sequence[HistoricDataPoint]
    time_window: RuleTimeWindowSettingsSpec


# default object that should be returned to the dqo.io engine, specifies if the rule was passed or failed,
# what is the expected value for the rule and what are the upper and lower boundaries of accepted values (optional)
class RuleExecutionResult:
    passed: bool
    new_actual_value: float
    expected_value: float
    lower_bound: float
    upper_bound: float

    def __init__(self, passed=None, new_actual_value=None, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.new_actual_value = new_actual_value
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters, 'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    if not hasattr(rule_parameters, 'previous_readouts'):
        return RuleExecutionResult(True, None, None, None)

    filtered = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered.append(rule_parameters.actual_value)

    filtered.reverse()

    recent_failures = 0
    for i in filtered:
        if i > 0:
            recent_failures += 1
        else:
            break

    expected_value = 0
    lower_bound = None
    upper_bound = rule_parameters.parameters.max_failures
    passed = recent_failures <= rule_parameters.parameters.max_failures

    return RuleExecutionResult(passed, recent_failures, expected_value, lower_bound, upper_bound)

```
___

## **max missing**
**Full rule name**
```
comparison/max_missing
```
**Description**  
Data quality rule that verifies the results of the data quality checks that count the number of values
 present in a column, comparing it to a list of expected values. The rule compares the count of expected values (received as expected_value)
 to the count of values found in the column (as the actual_value). The rule fails when the difference is higher than
 the expected max_missing, which is the maximum difference between the expected_value (the count of values in the expected_values list)
 and the actual number of values found in the column that match the list.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_missing|The maximum number of values from the expected_values list that were not found in the column (inclusive).|long| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: current_value
  fields:
  - field_name: max_missing
    display_name: max_missing
    help_text: The maximum number of values from the expected_values list that were
      not found in the column (inclusive).
    data_type: long
    sample_values:
    - 1
```



**Rule implementation (Python)**
```python
from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class MaxMissingRuleParametersSpec:
    max_missing: int


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
    expected_value: float
    parameters: MaxMissingRuleParametersSpec
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
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    expected_value = rule_parameters.expected_value
    if rule_parameters.expected_value < rule_parameters.parameters.max_missing:
        lower_bound = 0
    else:
        lower_bound = rule_parameters.expected_value - rule_parameters.parameters.max_missing
    upper_bound = None
    passed = rule_parameters.actual_value >= lower_bound

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **max percent**
**Full rule name**
```
comparison/max_percent
```
**Description**  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_percent|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: current_value
  fields:
  - field_name: max_percent
    display_name: max_percent
    help_text: Maximum accepted value for the actual_value returned by the sensor
      (inclusive).
    data_type: double
```



**Rule implementation (Python)**
```python
from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class MaxPercentRuleParametersSpec:
    max_percent: float


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
    parameters: MaxPercentRuleParametersSpec
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

    expected_value = rule_parameters.parameters.max_percent
    lower_bound = None
    upper_bound = rule_parameters.parameters.max_percent
    passed = rule_parameters.actual_value <= upper_bound

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **max value**
**Full rule name**
```
comparison/max_value
```
**Description**  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_value|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: current_value
  fields:
  - field_name: max_value
    display_name: max_value
    help_text: Maximum accepted value for the actual_value returned by the sensor
      (inclusive).
    data_type: double
    sample_values:
    - 1.5
```



**Rule implementation (Python)**
```python
from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class MaxValueRuleParametersSpec:
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
    parameters: MaxValueRuleParametersSpec
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

    expected_value = rule_parameters.parameters.max_value
    lower_bound = None
    upper_bound = rule_parameters.parameters.max_value
    passed = rule_parameters.actual_value <= upper_bound

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **min**
**Full rule name**
```
comparison/min
```
**Description**  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|min_value|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: current_value
  fields:
  - field_name: min_value
    display_name: min_value
    help_text: Minimum accepted value for the actual_value returned by the sensor
      (inclusive).
    data_type: double
    sample_values:
    - 1.5
```



**Rule implementation (Python)**
```python
from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class MinRuleParametersSpec:
    min_value: float


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
    parameters: MinRuleParametersSpec
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

    expected_value = rule_parameters.parameters.min_value
    lower_bound = rule_parameters.parameters.min_value
    upper_bound = None
    passed = rule_parameters.actual_value >= lower_bound

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **min count**
**Full rule name**
```
comparison/min_count
```
**Description**  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|min_count|Minimum accepted value for the actual_value returned by the sensor (inclusive).|long| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: current_value
  fields:
  - field_name: min_count
    display_name: min_count
    help_text: Minimum accepted value for the actual_value returned by the sensor
      (inclusive).
    data_type: long
    sample_values:
    - 5
```



**Rule implementation (Python)**
```python
from datetime import datetime
from typing import Sequence


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
___

## **min percent**
**Full rule name**
```
comparison/min_percent
```
**Description**  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|min_percent|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: current_value
  fields:
  - field_name: min_percent
    display_name: min_percent
    help_text: Minimum accepted value for the actual_value returned by the sensor
      (inclusive).
    data_type: double
```



**Rule implementation (Python)**
```python
from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class MinPercentRuleParametersSpec:
    min_percent: float


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
    parameters: MinPercentRuleParametersSpec
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

    expected_value = rule_parameters.parameters.min_percent
    lower_bound = rule_parameters.parameters.min_percent
    upper_bound = None
    passed = rule_parameters.actual_value >= lower_bound

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **min value**
**Full rule name**
```
comparison/min_value
```
**Description**  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|min_value|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: current_value
  fields:
  - field_name: min_value
    display_name: min_value
    help_text: Minimum accepted value for the actual_value returned by the sensor
      (inclusive).
    data_type: double
    sample_values:
    - 1.5
```



**Rule implementation (Python)**
```python
from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class MinValueRuleParametersSpec:
    min_value: float


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
    parameters: MinValueRuleParametersSpec
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

    expected_value = rule_parameters.parameters.min_value
    lower_bound = rule_parameters.parameters.min_value
    upper_bound = None
    passed = rule_parameters.actual_value >= lower_bound

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **value changed**
**Full rule name**
```
comparison/value_changed
```
**Description**  
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.



**Example**
```yaml
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  mode: previous_readouts
  time_window:
    prediction_time_window: 60
    min_periods_with_readouts: 0
    historic_data_point_grouping: last_n_readouts
```



**Rule implementation (Python)**
```python
from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration

# class ValueChangedRuleParametersSpec:
#     value_changed: int


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
#     parameters: ValueChangedRuleParametersSpec
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

    if not hasattr(rule_parameters, 'previous_readouts'):
        return RuleExecutionResult(True, None, None, None)

    filtered = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]

    expected_value = filtered[-1] if len(filtered) > 0 else None
    lower_bound = expected_value
    upper_bound = expected_value
    passed = len(filtered) == 0 or (filtered[-1] == rule_parameters.actual_value)

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
```
___
