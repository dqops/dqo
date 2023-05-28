# change
___

## **between change**
**Full rule name**
```
change/between_change
```
**Description**  
Data quality rule that verifies if data quality sensor readout value changed by a value between the provided bounds.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|from|Minimal accepted change with regards to the previous readout (inclusive).|double| ||
|to|Maximal accepted change with regards to the previous readout (inclusive).|double| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqo.ai/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: ai.dqo.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 1
    min_periods_with_readouts: 1
    historic_data_point_grouping: last_n_readouts
  fields:
  - field_name: from
    display_name: from
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ above a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
  - field_name: to
    display_name: to
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ below a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
```



**Rule implementation (Python)**
```python
#
# Copyright © 2023 DQO.ai (support@dqo.ai)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class BetweenChangeRuleParametersSpec:
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
    min_periods_with_readouts: int


# rule execution parameters, contains the sensor value (actual_value) and the rule parameters
class RuleExecutionRunParameters:
    actual_value: float
    parameters: BetweenChangeRuleParametersSpec
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
        return RuleExecutionResult(True, None, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    previous_readout = filtered[0]

    lower_bound = previous_readout + getattr(rule_parameters.parameters, 'from')
    upper_bound = previous_readout + rule_parameters.parameters.to

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = (lower_bound + upper_bound) / 2

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **between change 1 day**
**Full rule name**
```
change/between_change_1_day
```
**Description**  
Data quality rule that verifies if data quality sensor readout value changed by a value between the provided bounds compared to yesterday.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|from|Minimal accepted change with regards to the previous readout (inclusive).|double| ||
|to|Maximal accepted change with regards to the previous readout (inclusive).|double| ||
|exact|Whether to compare the actual value to the readout exactly 1 day in the past. If the flag is false, the rule searches for the newest readout, 60 days in the past, having skipped the readouts from the current day.|boolean| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqo.ai/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: ai.dqo.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 60
    min_periods_with_readouts: 1
    historic_data_point_grouping: day
  fields:
  - field_name: from
    display_name: from
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ above a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
  - field_name: to
    display_name: to
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ below a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
  - field_name: exact
    display_name: exact
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ below a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: boolean
```



**Rule implementation (Python)**
```python
#
# Copyright © 2023 DQO.ai (support@dqo.ai)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

from datetime import datetime
from typing import Sequence
import numpy as np


# rule specific parameters object, contains values received from the quality check threshold configuration
class BetweenChange1DayRuleParametersSpec:
    from_: float
    to: float
    exact: bool = False

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
    min_periods_with_readouts: int


# rule execution parameters, contains the sensor value (actual_value) and the rule parameters
class RuleExecutionRunParameters:
    actual_value: float
    parameters: BetweenChange1DayRuleParametersSpec
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
        return RuleExecutionResult(True, None, None, None)

    past_readouts = rule_parameters.previous_readouts
    if rule_parameters.parameters.exact:
        last_readout = past_readouts[-1]
        if last_readout is None:
            return RuleExecutionResult(True, None, None, None)

        previous_readout = last_readout.sensor_readout
    else:
        filtered_readouts = [readouts.sensor_readout for readouts in past_readouts if readouts is not None]
        previous_readout = filtered_readouts[-1]

    lower_bound = previous_readout + getattr(rule_parameters.parameters, 'from')
    upper_bound = previous_readout + rule_parameters.parameters.to

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = (lower_bound + upper_bound) / 2

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **between change 30 days**
**Full rule name**
```
change/between_change_30_days
```
**Description**  
Data quality rule that verifies if data quality sensor readout value changed by a value between the provided bounds compared to last month.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|from|Minimal accepted change with regards to the previous readout (inclusive).|double| ||
|to|Maximal accepted change with regards to the previous readout (inclusive).|double| ||
|exact|Whether to compare the actual value to the readout exactly 30 days in the past. If the flag is false, the rule searches for the newest readout, 60 days in the past, having skipped the readouts for the last 30 days.|boolean| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqo.ai/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: ai.dqo.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 60
    min_periods_with_readouts: 1
    historic_data_point_grouping: day
  fields:
  - field_name: from
    display_name: from
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ above a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
  - field_name: to
    display_name: to
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ below a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
  - field_name: exact
    display_name: exact
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ below a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: boolean
```



**Rule implementation (Python)**
```python
#
# Copyright © 2023 DQO.ai (support@dqo.ai)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

from datetime import datetime
from typing import Sequence
import numpy as np


# rule specific parameters object, contains values received from the quality check threshold configuration
class BetweenChange30DaysRuleParametersSpec:
    from_: float
    to: float
    exact: bool = False

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
    min_periods_with_readouts: int


# rule execution parameters, contains the sensor value (actual_value) and the rule parameters
class RuleExecutionRunParameters:
    actual_value: float
    parameters: BetweenChange30DaysRuleParametersSpec
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
        return RuleExecutionResult(True, None, None, None)

    past_readouts = rule_parameters.previous_readouts[:-29]
    if rule_parameters.parameters.exact:
        last_readout = past_readouts[-1]
        if last_readout is None:
            return RuleExecutionResult(True, None, None, None)

        previous_readout = last_readout.sensor_readout
    else:
        filtered_readouts = [readouts.sensor_readout for readouts in past_readouts if readouts is not None]
        if not filtered_readouts:
            return RuleExecutionResult(True, None, None, None)
        previous_readout = filtered_readouts[-1]

    lower_bound = previous_readout + getattr(rule_parameters.parameters, 'from')
    upper_bound = previous_readout + rule_parameters.parameters.to

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = (lower_bound + upper_bound) / 2

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **between change 7 days**
**Full rule name**
```
change/between_change_7_days
```
**Description**  
Data quality rule that verifies if data quality sensor readout value changed by a value between the provided bounds compared to last week.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|from|Minimal accepted change with regards to the previous readout (inclusive).|double| ||
|to|Maximal accepted change with regards to the previous readout (inclusive).|double| ||
|exact|Whether to compare the actual value to the readout exactly 7 days in the past. If the flag is false, the rule searches for the newest readout, 60 days in the past, having skipped the readouts for the last 7 days.|boolean| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqo.ai/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: ai.dqo.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 60
    min_periods_with_readouts: 1
    historic_data_point_grouping: day
  fields:
  - field_name: from
    display_name: from
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ above a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
  - field_name: to
    display_name: to
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ below a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
  - field_name: exact
    display_name: exact
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ below a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: boolean
```



**Rule implementation (Python)**
```python
#
# Copyright © 2023 DQO.ai (support@dqo.ai)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

from datetime import datetime
from typing import Sequence
import numpy as np


# rule specific parameters object, contains values received from the quality check threshold configuration
class BetweenChange7DaysRuleParametersSpec:
    from_: float
    to: float
    exact: bool = False

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
    min_periods_with_readouts: int


# rule execution parameters, contains the sensor value (actual_value) and the rule parameters
class RuleExecutionRunParameters:
    actual_value: float
    parameters: BetweenChange7DaysRuleParametersSpec
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
        return RuleExecutionResult(True, None, None, None)

    past_readouts = rule_parameters.previous_readouts[:-6]
    if rule_parameters.parameters.exact:
        last_readout = past_readouts[-1]
        if last_readout is None:
            return RuleExecutionResult(True, None, None, None)

        previous_readout = last_readout.sensor_readout
    else:
        filtered_readouts = [readouts.sensor_readout for readouts in past_readouts if readouts is not None]
        if not filtered_readouts:
            return RuleExecutionResult(True, None, None, None)
        previous_readout = filtered_readouts[-1]

    lower_bound = previous_readout + getattr(rule_parameters.parameters, 'from')
    upper_bound = previous_readout + rule_parameters.parameters.to

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = (lower_bound + upper_bound) / 2

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **between percent change**
**Full rule name**
```
change/between_percent_change
```
**Description**  
Data quality rule that verifies if data quality sensor readout value changed by a percent between the provided bounds.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|from_percent|Minimal accepted change relative to the previous readout (inclusive).|double| ||
|to_percent|Maximal accepted change relative to the previous readout (inclusive).|double| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqo.ai/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: ai.dqo.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 1
    min_periods_with_readouts: 1
    historic_data_point_grouping: last_n_readouts
  fields:
    - field_name: from_percent
      display_name: from_percent
      help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ above a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
      data_type: double
    - field_name: to_percent
      display_name: to_percent
      help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ below a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
      data_type: double
```



**Rule implementation (Python)**
```python
#
# Copyright © 2023 DQO.ai (support@dqo.ai)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class BetweenPercentChangeRuleParametersSpec:
    from_percent: float
    to_percent: float


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
    parameters: BetweenPercentChangeRuleParametersSpec
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
        return RuleExecutionResult(True, None, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    previous_readout = filtered[0]

    lower_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.from_percent / 100.0)
    upper_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.to_percent / 100.0)

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = (lower_bound + upper_bound) / 2

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **between percent change 1 day**
**Full rule name**
```
change/between_percent_change_1_day
```
**Description**  
Data quality rule that verifies if data quality sensor readout value changed by a percent between the provided bounds compared to yesterday.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|from_percent|Minimal accepted change relative to the previous readout (inclusive).|double| ||
|to_percent|Maximal accepted change relative to the previous readout (inclusive).|double| ||
|exact|Whether to compare the actual value to the readout exactly 1 day in the past. If the flag is false, the rule searches for the newest readout, 60 days in the past, having skipped the readouts from the current day.|boolean| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqo.ai/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: ai.dqo.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 60
    min_periods_with_readouts: 1
    historic_data_point_grouping: day
  fields:
    - field_name: from_percent
      display_name: from_percent
      help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ above a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
      data_type: double
    - field_name: to_percent
      display_name: to_percent
      help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ below a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
      data_type: double
    - field_name: exact
      display_name: exact
      help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
        \ below a moving average within the time window. Set the time window at the\
        \ threshold level for all severity levels (low, medium, high) at once. The default\
        \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
        \ exist to run the calculation.&quot;
      data_type: boolean
```



**Rule implementation (Python)**
```python
#
# Copyright © 2023 DQO.ai (support@dqo.ai)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class BetweenPercentChange1DayRuleParametersSpec:
    from_percent: float
    to_percent: float
    exact: bool = False


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
    parameters: BetweenPercentChange1DayRuleParametersSpec
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
        return RuleExecutionResult(True, None, None, None)

    past_readouts = rule_parameters.previous_readouts
    if rule_parameters.parameters.exact:
        last_readout = past_readouts[-1]
        if last_readout is None:
            return RuleExecutionResult(True, None, None, None)

        previous_readout = last_readout.sensor_readout
    else:
        filtered_readouts = [readouts.sensor_readout for readouts in past_readouts if readouts is not None]
        previous_readout = filtered_readouts[-1]

    lower_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.from_percent / 100.0)
    upper_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.to_percent / 100.0)

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = (lower_bound + upper_bound) / 2

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **between percent change 30 days**
**Full rule name**
```
change/between_percent_change_30_days
```
**Description**  
Data quality rule that verifies if data quality sensor readout value changed by a percent between the provided bounds compared to last month.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|from_percent|Minimal accepted change relative to the previous readout (inclusive).|double| ||
|to_percent|Maximal accepted change relative to the previous readout (inclusive).|double| ||
|exact|Whether to compare the actual value to the readout exactly 30 days in the past. If the flag is false, the rule searches for the newest readout, 60 days in the past, having skipped the readouts for the last 30 days.|boolean| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqo.ai/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: ai.dqo.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 60
    min_periods_with_readouts: 1
    historic_data_point_grouping: day
  fields:
    - field_name: from_percent
      display_name: from_percent
      help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ above a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
      data_type: double
    - field_name: to_percent
      display_name: to_percent
      help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ below a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
      data_type: double
    - field_name: exact
      display_name: exact
      help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
        \ below a moving average within the time window. Set the time window at the\
        \ threshold level for all severity levels (low, medium, high) at once. The default\
        \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
        \ exist to run the calculation.&quot;
      data_type: boolean
```



**Rule implementation (Python)**
```python
#
# Copyright © 2023 DQO.ai (support@dqo.ai)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class BetweenPercentChange30DaysRuleParametersSpec:
    from_percent: float
    to_percent: float
    exact: bool = False


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
    parameters: BetweenPercentChange30DaysRuleParametersSpec
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
        return RuleExecutionResult(True, None, None, None)

    past_readouts = rule_parameters.previous_readouts[:-29]
    if rule_parameters.parameters.exact:
        last_readout = past_readouts[-1]
        if last_readout is None:
            return RuleExecutionResult(True, None, None, None)

        previous_readout = last_readout.sensor_readout
    else:
        filtered_readouts = [readouts.sensor_readout for readouts in past_readouts if readouts is not None]
        if not filtered_readouts:
            return RuleExecutionResult(True, None, None, None)
        previous_readout = filtered_readouts[-1]

    lower_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.from_percent / 100.0)
    upper_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.to_percent / 100.0)

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = (lower_bound + upper_bound) / 2

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **between percent change 7 days**
**Full rule name**
```
change/between_percent_change_7_days
```
**Description**  
Data quality rule that verifies if data quality sensor readout value changed by a percent between the provided bounds compared to last week.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|from_percent|Minimal accepted change relative to the previous readout (inclusive).|double| ||
|to_percent|Maximal accepted change relative to the previous readout (inclusive).|double| ||
|exact|Whether to compare the actual value to the readout exactly 7 days in the past. If the flag is false, the rule searches for the newest readout, 60 days in the past, having skipped the readouts for the last 7 days.|boolean| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqo.ai/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: ai.dqo.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 60
    min_periods_with_readouts: 1
    historic_data_point_grouping: day
  fields:
    - field_name: from_percent
      display_name: from_percent
      help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ above a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
      data_type: double
    - field_name: to_percent
      display_name: to_percent
      help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ below a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
      data_type: double
    - field_name: exact
      display_name: exact
      help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
        \ below a moving average within the time window. Set the time window at the\
        \ threshold level for all severity levels (low, medium, high) at once. The default\
        \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
        \ exist to run the calculation.&quot;
      data_type: boolean
```



**Rule implementation (Python)**
```python
#
# Copyright © 2023 DQO.ai (support@dqo.ai)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class BetweenPercentChange7DaysRuleParametersSpec:
    from_percent: float
    to_percent: float
    exact: bool = False


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
    parameters: BetweenPercentChange7DaysRuleParametersSpec
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
        return RuleExecutionResult(True, None, None, None)

    past_readouts = rule_parameters.previous_readouts[:-6]
    if rule_parameters.parameters.exact:
        last_readout = past_readouts[-1]
        if last_readout is None:
            return RuleExecutionResult(True, None, None, None)

        previous_readout = last_readout.sensor_readout
    else:
        filtered_readouts = [readouts.sensor_readout for readouts in past_readouts if readouts is not None]
        if not filtered_readouts:
            return RuleExecutionResult(True, None, None, None)
        previous_readout = filtered_readouts[-1]

    lower_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.from_percent / 100.0)
    upper_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.to_percent / 100.0)

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = (lower_bound + upper_bound) / 2

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **within change**
**Full rule name**
```
change/within_change
```
**Description**  
Data quality rule that verifies if data quality sensor readout value changed by a value within the provided bound.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_within|Maximal accepted absolute change with regards to the previous readout (inclusive).|double| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqo.ai/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: ai.dqo.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 1
    min_periods_with_readouts: 1
    historic_data_point_grouping: last_n_readouts
  fields:
    - field_name: max_within
      display_name: max_within
      help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ above a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
      data_type: double
```



**Rule implementation (Python)**
```python
#
# Copyright © 2023 DQO.ai (support@dqo.ai)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class WithinChangeRuleParametersSpec:
    max_within: float


class HistoricDataPoint:
    timestamp_utc: datetime
    local_datetime: datetime
    back_periods_index: int
    sensor_readout: float


class RuleTimeWindowSettingsSpec:
    prediction_time_window: int
    min_periods_with_readout: int


# rule execution parameters, contains the sensor value (actual_value) and the rule parameters
class RuleExecutionRunParameters:
    actual_value: float
    parameters: WithinChangeRuleParametersSpec
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
        return RuleExecutionResult(True, None, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    previous_readout = filtered[0]

    lower_bound = previous_readout - rule_parameters.parameters.max_within
    upper_bound = previous_readout + rule_parameters.parameters.max_within

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = previous_readout

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **within change 1 day**
**Full rule name**
```
change/within_change_1_day
```
**Description**  
Data quality rule that verifies if data quality sensor readout value changed by a value within the provided bound compared to yesterday.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_within|Maximal accepted absolute change with regards to the previous readout (inclusive).|double| ||
|exact|Whether to compare the actual value to the readout exactly 1 day in the past. If the flag is false, the rule searches for the newest readout, 60 days in the past, having skipped the readouts from the current day.|boolean| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqo.ai/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: ai.dqo.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 60
    min_periods_with_readouts: 1
    historic_data_point_grouping: day
  fields:
    - field_name: max_within
      display_name: max_within
      help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ above a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
      data_type: double
    - field_name: exact
      display_name: exact
      help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
        \ below a moving average within the time window. Set the time window at the\
        \ threshold level for all severity levels (low, medium, high) at once. The default\
        \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
        \ exist to run the calculation.&quot;
      data_type: boolean
```



**Rule implementation (Python)**
```python
#
# Copyright © 2023 DQO.ai (support@dqo.ai)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class WithinChange1DayRuleParametersSpec:
    max_within: float
    exact: bool = False


class HistoricDataPoint:
    timestamp_utc: datetime
    local_datetime: datetime
    back_periods_index: int
    sensor_readout: float


class RuleTimeWindowSettingsSpec:
    prediction_time_window: int
    min_periods_with_readout: int


# rule execution parameters, contains the sensor value (actual_value) and the rule parameters
class RuleExecutionRunParameters:
    actual_value: float
    parameters: WithinChange1DayRuleParametersSpec
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
        return RuleExecutionResult(True, None, None, None)

    past_readouts = rule_parameters.previous_readouts
    if rule_parameters.parameters.exact:
        last_readout = past_readouts[-1]
        if last_readout is None:
            return RuleExecutionResult(True, None, None, None)

        previous_readout = last_readout.sensor_readout
    else:
        filtered_readouts = [readouts.sensor_readout for readouts in past_readouts if readouts is not None]
        previous_readout = filtered_readouts[-1]

    lower_bound = previous_readout - rule_parameters.parameters.max_within
    upper_bound = previous_readout + rule_parameters.parameters.max_within

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = previous_readout

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **within change 30 days**
**Full rule name**
```
change/within_change_30_days
```
**Description**  
Data quality rule that verifies if data quality sensor readout value changed by a value within the provided bound compared to last month.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_within|Maximal accepted absolute change with regards to the previous readout (inclusive).|double| ||
|exact|Whether to compare the actual value to the readout exactly 30 days in the past. If the flag is false, the rule searches for the newest readout, 60 days in the past, having skipped the readouts for the last 30 days.|boolean| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqo.ai/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: ai.dqo.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 60
    min_periods_with_readouts: 1
    historic_data_point_grouping: day
  fields:
    - field_name: max_within
      display_name: max_within
      help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ above a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
      data_type: double
    - field_name: exact
      display_name: exact
      help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
        \ below a moving average within the time window. Set the time window at the\
        \ threshold level for all severity levels (low, medium, high) at once. The default\
        \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
        \ exist to run the calculation.&quot;
      data_type: boolean
```



**Rule implementation (Python)**
```python
#
# Copyright © 2023 DQO.ai (support@dqo.ai)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class WithinChange30DaysRuleParametersSpec:
    max_within: float
    exact: bool = False


class HistoricDataPoint:
    timestamp_utc: datetime
    local_datetime: datetime
    back_periods_index: int
    sensor_readout: float


class RuleTimeWindowSettingsSpec:
    prediction_time_window: int
    min_periods_with_readout: int


# rule execution parameters, contains the sensor value (actual_value) and the rule parameters
class RuleExecutionRunParameters:
    actual_value: float
    parameters: WithinChange30DaysRuleParametersSpec
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
        return RuleExecutionResult(True, None, None, None)

    past_readouts = rule_parameters.previous_readouts[:-29]
    if rule_parameters.parameters.exact:
        last_readout = past_readouts[-1]
        if last_readout is None:
            return RuleExecutionResult(True, None, None, None)

        previous_readout = last_readout.sensor_readout
    else:
        filtered_readouts = [readouts.sensor_readout for readouts in past_readouts if readouts is not None]
        if not filtered_readouts:
            return RuleExecutionResult(True, None, None, None)
        previous_readout = filtered_readouts[-1]

    lower_bound = previous_readout - rule_parameters.parameters.max_within
    upper_bound = previous_readout + rule_parameters.parameters.max_within

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = previous_readout

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **within change 7 days**
**Full rule name**
```
change/within_change_7_days
```
**Description**  
Data quality rule that verifies if data quality sensor readout value changed by a value within the provided bound compared to last week.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_within|Maximal accepted absolute change with regards to the previous readout (inclusive).|double| ||
|exact|Whether to compare the actual value to the readout exactly 7 days in the past. If the flag is false, the rule searches for the newest readout, 60 days in the past, having skipped the readouts for the last 7 days.|boolean| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqo.ai/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: ai.dqo.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 60
    min_periods_with_readouts: 1
    historic_data_point_grouping: day
  fields:
    - field_name: max_within
      display_name: max_within
      help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ above a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
      data_type: double
    - field_name: exact
      display_name: exact
      help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
        \ below a moving average within the time window. Set the time window at the\
        \ threshold level for all severity levels (low, medium, high) at once. The default\
        \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
        \ exist to run the calculation.&quot;
      data_type: boolean
```



**Rule implementation (Python)**
```python
#
# Copyright © 2023 DQO.ai (support@dqo.ai)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class WithinChange7DaysRuleParametersSpec:
    max_within: float
    exact: bool = False


class HistoricDataPoint:
    timestamp_utc: datetime
    local_datetime: datetime
    back_periods_index: int
    sensor_readout: float


class RuleTimeWindowSettingsSpec:
    prediction_time_window: int
    min_periods_with_readout: int


# rule execution parameters, contains the sensor value (actual_value) and the rule parameters
class RuleExecutionRunParameters:
    actual_value: float
    parameters: WithinChange7DaysRuleParametersSpec
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
        return RuleExecutionResult(True, None, None, None)

    past_readouts = rule_parameters.previous_readouts[:-6]
    if rule_parameters.parameters.exact:
        last_readout = past_readouts[-1]
        if last_readout is None:
            return RuleExecutionResult(True, None, None, None)

        previous_readout = last_readout.sensor_readout
    else:
        filtered_readouts = [readouts.sensor_readout for readouts in past_readouts if readouts is not None]
        if not filtered_readouts:
            return RuleExecutionResult(True, None, None, None)
        previous_readout = filtered_readouts[-1]

    lower_bound = previous_readout - rule_parameters.parameters.max_within
    upper_bound = previous_readout + rule_parameters.parameters.max_within

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = previous_readout

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **within percent change**
**Full rule name**
```
change/within_percent_change
```
**Description**  
Data quality rule that verifies if data quality sensor readout value changed by a percent within the provided bound.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_percent_within|Absolute value of the maximal accepted change relative to the previous readout (inclusive).|double| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqo.ai/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: ai.dqo.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 1
    min_periods_with_readouts: 1
    historic_data_point_grouping: last_n_readouts
  fields:
  - field_name: max_percent_within
    display_name: max_percent_within
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ within a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
```



**Rule implementation (Python)**
```python
#
# Copyright © 2023 DQO.ai (support@dqo.ai)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class WithinPercentChangeRuleParametersSpec:
    max_percent_within: float


class HistoricDataPoint:
    timestamp_utc: datetime
    local_datetime: datetime
    back_periods_index: int
    sensor_readout: float


class RuleTimeWindowSettingsSpec:
    prediction_time_window: int
    min_periods_with_readout: int


# rule execution parameters, contains the sensor value (actual_value) and the rule parameters
class RuleExecutionRunParameters:
    actual_value: float
    parameters: WithinPercentChangeRuleParametersSpec
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
        return RuleExecutionResult(True, None, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    previous_readout = filtered[0]

    lower_bound = previous_readout - abs(previous_readout) * (rule_parameters.parameters.max_percent_within / 100.0)
    upper_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.max_percent_within / 100.0)

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = previous_readout

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **within percent change 1 day**
**Full rule name**
```
change/within_percent_change_1_day
```
**Description**  
Data quality rule that verifies if data quality sensor readout value changed by a percent within the provided bound compared to yesterday.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_percent_within|Absolute value of the maximal accepted change relative to the previous readout (inclusive).|double| ||
|exact|Whether to compare the actual value to the readout exactly 1 day in the past. If the flag is false, the rule searches for the newest readout, 60 days in the past, having skipped the readouts from the current day.|boolean| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqo.ai/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: ai.dqo.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 60
    min_periods_with_readouts: 1
    historic_data_point_grouping: day
  fields:
  - field_name: max_percent_within
    display_name: max_percent_within
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ within a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
  - field_name: exact
    display_name: exact
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ below a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: boolean
```



**Rule implementation (Python)**
```python
#
# Copyright © 2023 DQO.ai (support@dqo.ai)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class WithinPercentChange1DayRuleParametersSpec:
    max_percent_within: float
    exact: bool = False


class HistoricDataPoint:
    timestamp_utc: datetime
    local_datetime: datetime
    back_periods_index: int
    sensor_readout: float


class RuleTimeWindowSettingsSpec:
    prediction_time_window: int
    min_periods_with_readout: int


# rule execution parameters, contains the sensor value (actual_value) and the rule parameters
class RuleExecutionRunParameters:
    actual_value: float
    parameters: WithinPercentChange1DayRuleParametersSpec
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
        return RuleExecutionResult(True, None, None, None)

    past_readouts = rule_parameters.previous_readouts
    if rule_parameters.parameters.exact:
        last_readout = past_readouts[-1]
        if last_readout is None:
            return RuleExecutionResult(True, None, None, None)

        previous_readout = last_readout.sensor_readout
    else:
        filtered_readouts = [readouts.sensor_readout for readouts in past_readouts if readouts is not None]
        previous_readout = filtered_readouts[-1]

    lower_bound = previous_readout - abs(previous_readout) * (rule_parameters.parameters.max_percent_within / 100.0)
    upper_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.max_percent_within / 100.0)

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = previous_readout

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **within percent change 30 days**
**Full rule name**
```
change/within_percent_change_30_days
```
**Description**  
Data quality rule that verifies if data quality sensor readout value changed by a percent within the provided bound compared to last month.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_percent_within|Absolute value of the maximal accepted change relative to the previous readout (inclusive).|double| ||
|exact|Whether to compare the actual value to the readout exactly 7 days in the past. If the flag is false, the rule searches for the newest readout, 60 days in the past, having skipped the readouts for the last 7 days.|boolean| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqo.ai/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: ai.dqo.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 60
    min_periods_with_readouts: 1
    historic_data_point_grouping: day
  fields:
  - field_name: max_percent_within
    display_name: max_percent_within
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ within a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
  - field_name: exact
    display_name: exact
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ below a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: boolean
```



**Rule implementation (Python)**
```python
#
# Copyright © 2023 DQO.ai (support@dqo.ai)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class WithinPercentChange30DaysRuleParametersSpec:
    max_percent_within: float
    exact: bool = False


class HistoricDataPoint:
    timestamp_utc: datetime
    local_datetime: datetime
    back_periods_index: int
    sensor_readout: float


class RuleTimeWindowSettingsSpec:
    prediction_time_window: int
    min_periods_with_readout: int


# rule execution parameters, contains the sensor value (actual_value) and the rule parameters
class RuleExecutionRunParameters:
    actual_value: float
    parameters: WithinPercentChange30DaysRuleParametersSpec
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
        return RuleExecutionResult(True, None, None, None)

    past_readouts = rule_parameters.previous_readouts[:-29]
    if rule_parameters.parameters.exact:
        last_readout = past_readouts[-1]
        if last_readout is None:
            return RuleExecutionResult(True, None, None, None)

        previous_readout = last_readout.sensor_readout
    else:
        filtered_readouts = [readouts.sensor_readout for readouts in past_readouts if readouts is not None]
        if not filtered_readouts:
            return RuleExecutionResult(True, None, None, None)
        previous_readout = filtered_readouts[-1]

    lower_bound = previous_readout - abs(previous_readout) * (rule_parameters.parameters.max_percent_within / 100.0)
    upper_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.max_percent_within / 100.0)

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = previous_readout

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **within percent change 7 days**
**Full rule name**
```
change/within_percent_change_7_days
```
**Description**  
Data quality rule that verifies if data quality sensor readout value changed by a percent within the provided bound compared to last week.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_percent_within|Absolute value of the maximal accepted change relative to the previous readout (inclusive).|double| ||
|exact|Whether to compare the actual value to the readout exactly 7 days in the past. If the flag is false, the rule searches for the newest readout, 60 days in the past, having skipped the readouts for the last 7 days.|boolean| ||



**Example**
```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqo.ai/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: ai.dqo.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 60
    min_periods_with_readouts: 1
    historic_data_point_grouping: day
  fields:
  - field_name: max_percent_within
    display_name: max_percent_within
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ within a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
  - field_name: exact
    display_name: exact
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ below a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: boolean
```



**Rule implementation (Python)**
```python
#
# Copyright © 2023 DQO.ai (support@dqo.ai)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class WithinPercentChange7DaysRuleParametersSpec:
    max_percent_within: float
    exact: bool = False


class HistoricDataPoint:
    timestamp_utc: datetime
    local_datetime: datetime
    back_periods_index: int
    sensor_readout: float


class RuleTimeWindowSettingsSpec:
    prediction_time_window: int
    min_periods_with_readout: int


# rule execution parameters, contains the sensor value (actual_value) and the rule parameters
class RuleExecutionRunParameters:
    actual_value: float
    parameters: WithinPercentChange7DaysRuleParametersSpec
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
        return RuleExecutionResult(True, None, None, None)

    past_readouts = rule_parameters.previous_readouts[:-6]
    if rule_parameters.parameters.exact:
        last_readout = past_readouts[-1]
        if last_readout is None:
            return RuleExecutionResult(True, None, None, None)

        previous_readout = last_readout.sensor_readout
    else:
        filtered_readouts = [readouts.sensor_readout for readouts in past_readouts if readouts is not None]
        if not filtered_readouts:
            return RuleExecutionResult(True, None, None, None)
        previous_readout = filtered_readouts[-1]

    lower_bound = previous_readout - abs(previous_readout) * (rule_parameters.parameters.max_percent_within / 100.0)
    upper_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.max_percent_within / 100.0)

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = previous_readout

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___
