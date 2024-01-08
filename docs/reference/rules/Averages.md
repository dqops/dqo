# averages
___

## **between percent moving average 30 days**
**Full rule name**
```
averages/between_percent_moving_average_30_days
```
**Description**
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_percent_above|Maximum percent (e.q. 3%) that the current sensor readout could be above a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|double| ||
|max_percent_below|Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|double| ||



**Rule definition YAML**

The rule definition YAML file *averages/between_percent_moving_average_30_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 30
    min_periods_with_readouts: 10
    historic_data_point_grouping: day
  fields:
  - field_name: max_percent_above
    display_name: max_percent_above
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ above a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
  - field_name: max_percent_below
    display_name: max_percent_below
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ below a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
```



**Rule implementation (Python)**

The code sample below shows the content of the  *averages/between_percent_moving_average_30_days.py* file. The *evaluate_rule* function at the bottom evaluates the sensor result and returns the rule evaluation result.

```python
#
# Copyright © 2021 DQOps (support@dqops.com)
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
import scipy


# rule specific parameters object, contains values received from the quality check threshold configuration
class BetweenPercentMovingAverage30DaysRuleParametersSpec:
    max_percent_above: float
    max_percent_below: float


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
    parameters: BetweenPercentMovingAverage30DaysRuleParametersSpec
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
        return RuleExecutionResult()

    filtered = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]
    if len(filtered) == 0:
        return RuleExecutionResult()

    filtered_mean = float(scipy.mean(filtered))

    upper_bound = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_above / 100.0)
    lower_bound = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_below / 100.0)

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = filtered_mean

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **between percent moving average 60 days**
**Full rule name**
```
averages/between_percent_moving_average_60_days
```
**Description**
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_percent_above|Maximum percent (e.q. 3%) that the current sensor readout could be above a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|double| ||
|max_percent_below|Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|double| ||



**Rule definition YAML**

The rule definition YAML file *averages/between_percent_moving_average_60_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
    min_periods_with_readouts: 20
    historic_data_point_grouping: day
  fields:
  - field_name: max_percent_above
    display_name: max_percent_above
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ above a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
  - field_name: max_percent_below
    display_name: max_percent_below
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ below a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
```



**Rule implementation (Python)**

The code sample below shows the content of the  *averages/between_percent_moving_average_60_days.py* file. The *evaluate_rule* function at the bottom evaluates the sensor result and returns the rule evaluation result.

```python
#
# Copyright © 2021 DQOps (support@dqops.com)
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
import scipy


# rule specific parameters object, contains values received from the quality check threshold configuration
class BetweenPercentMovingAverage60DaysRuleParametersSpec:
    max_percent_above: float
    max_percent_below: float


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
    parameters: BetweenPercentMovingAverage60DaysRuleParametersSpec
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
        return RuleExecutionResult()

    filtered = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]
    if len(filtered) == 0:
        return RuleExecutionResult()

    filtered_mean = float(scipy.mean(filtered))

    upper_bound = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_above / 100.0)
    lower_bound = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_below / 100.0)

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = filtered_mean

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **between percent moving average 7 days**
**Full rule name**
```
averages/between_percent_moving_average_7_days
```
**Description**
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_percent_above|Maximum percent (e.q. 3%) that the current sensor readout could be above a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|double| ||
|max_percent_below|Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|double| ||



**Rule definition YAML**

The rule definition YAML file *averages/between_percent_moving_average_7_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 7
    min_periods_with_readouts: 3
    historic_data_point_grouping: day
  fields:
  - field_name: max_percent_above
    display_name: max_percent_above
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ above a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
  - field_name: max_percent_below
    display_name: max_percent_below
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ below a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
```



**Rule implementation (Python)**

The code sample below shows the content of the  *averages/between_percent_moving_average_7_days.py* file. The *evaluate_rule* function at the bottom evaluates the sensor result and returns the rule evaluation result.

```python
#
# Copyright © 2021 DQOps (support@dqops.com)
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
import scipy


# rule specific parameters object, contains values received from the quality check threshold configuration
class BetweenPctMovingAverage7DaysRuleParametersSpec:
    max_percent_above: float
    max_percent_below: float


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
    parameters: BetweenPctMovingAverage7DaysRuleParametersSpec
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
        return RuleExecutionResult()

    filtered = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]
    if len(filtered) == 0:
        return RuleExecutionResult()

    filtered_mean = float(scipy.mean(filtered))

    upper_bound = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_above / 100.0)
    lower_bound = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_below / 100.0)

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = filtered_mean

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **percent moving average**
**Full rule name**
```
averages/percent_moving_average
```
**Description**
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_percent_above|Maximum percent (e.q. 3%) that the current sensor readout could be above a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|double| ||
|max_percent_below|Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|double| ||



**Rule definition YAML**

The rule definition YAML file *averages/percent_moving_average.dqorule.yaml* with the time window and rule parameters configuration is shown below.

```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 10
    min_periods_with_readouts: 5
    historic_data_point_grouping: day
  fields:
  - field_name: max_percent_above
    display_name: max_percent_above
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ above a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (warning, error, fatal) at once. The\
      \ default is a 14 time periods (days, etc.) time window, but at least 7 readouts\
      \ must exist to run the calculation.&quot;
    data_type: double
    sample_values:
    - 10.0
  - field_name: max_percent_below
    display_name: max_percent_below
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor readout could be\
      \ below a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (warning, error, fatal) at once. The\
      \ default is a 14 time periods (days, etc.) time window, but at least 7 readouts\
      \ must exist to run the calculation.&quot;
    data_type: double
    sample_values:
    - 10.0
```



**Rule implementation (Python)**

The code sample below shows the content of the  *averages/percent_moving_average.py* file. The *evaluate_rule* function at the bottom evaluates the sensor result and returns the rule evaluation result.

```python
#
# Copyright © 2021 DQOps (support@dqops.com)
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
import scipy


# rule specific parameters object, contains values received from the quality check threshold configuration
class PercentMovingAverageRuleParametersSpec:
    max_percent_above: float
    max_percent_below: float


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
    parameters: PercentMovingAverageRuleParametersSpec
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
        return RuleExecutionResult()

    filtered = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]
    if len(filtered) == 0:
        return RuleExecutionResult()

    filtered_mean = float(scipy.mean(filtered))

    upper_bound = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_above / 100.0)
    lower_bound = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_below / 100.0)

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = filtered_mean

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **within percent moving average 30 days**
**Full rule name**
```
averages/within_percent_moving_average_30_days
```
**Description**
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_percent_within|Maximum percent (e.q. 3%) that the current sensor readout could be within a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|double| ||



**Rule definition YAML**

The rule definition YAML file *averages/within_percent_moving_average_30_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 30
    min_periods_with_readouts: 10
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
```



**Rule implementation (Python)**

The code sample below shows the content of the  *averages/within_percent_moving_average_30_days.py* file. The *evaluate_rule* function at the bottom evaluates the sensor result and returns the rule evaluation result.

```python
#
# Copyright © 2021 DQOps (support@dqops.com)
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
import scipy


# rule specific parameters object, contains values received from the quality check threshold configuration
class WithinPercentMovingAverage30DaysRuleParametersSpec:
    max_percent_within: float


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
    parameters: WithinPercentMovingAverage30DaysRuleParametersSpec
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
        return RuleExecutionResult()

    filtered = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]
    if len(filtered) == 0:
        return RuleExecutionResult()

    filtered_mean = float(scipy.mean(filtered))

    upper_bound = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_within / 100.0)
    lower_bound = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_within / 100.0)

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = filtered_mean

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **within percent moving average 60 days**
**Full rule name**
```
averages/within_percent_moving_average_60_days
```
**Description**
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_percent_within|Maximum percent (e.q. 3%) that the current sensor reading could be within a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readings must exist to run the calculation.|double| ||



**Rule definition YAML**

The rule definition YAML file *averages/within_percent_moving_average_60_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
    min_periods_with_readouts: 20
    historic_data_point_grouping: day
  fields:
  - field_name: max_percent_within
    display_name: max_percent_within
    help_text: &quot;Maximum percent (e.q. 3%) that the current sensor reading could be\
      \ within a moving average within the time window. Set the time window at the\
      \ threshold level for all severity levels (low, medium, high) at once. The default\
      \ is a 14 time periods (days, etc.) time window, but at least 7 readings must\
      \ exist to run the calculation.&quot;
    data_type: double
```



**Rule implementation (Python)**

The code sample below shows the content of the  *averages/within_percent_moving_average_60_days.py* file. The *evaluate_rule* function at the bottom evaluates the sensor result and returns the rule evaluation result.

```python
#
# Copyright © 2021 DQOps (support@dqops.com)
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
import scipy


# rule specific parameters object, contains values received from the quality check threshold configuration
class WithinPercentMovingAverage60DaysRuleParametersSpec:
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
    parameters: WithinPercentMovingAverage60DaysRuleParametersSpec
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
        return RuleExecutionResult()

    filtered = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]
    if len(filtered) == 0:
        return RuleExecutionResult()

    filtered_mean = float(scipy.mean(filtered))

    upper_bound = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_within / 100.0)
    lower_bound = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_within / 100.0)

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = filtered_mean

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **within percent moving average 7 days**
**Full rule name**
```
averages/within_percent_moving_average_7_days
```
**Description**
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|max_percent_within|Maximum percent (e.q. 3%) that the current sensor readout could be within a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|double| ||



**Rule definition YAML**

The rule definition YAML file *averages/within_percent_moving_average_7_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 7
    min_periods_with_readouts: 3
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
```



**Rule implementation (Python)**

The code sample below shows the content of the  *averages/within_percent_moving_average_7_days.py* file. The *evaluate_rule* function at the bottom evaluates the sensor result and returns the rule evaluation result.

```python
#
# Copyright © 2021 DQOps (support@dqops.com)
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
import scipy


# rule specific parameters object, contains values received from the quality check threshold configuration
class WithinPercentMovingAverage7DaysRuleParametersSpec:
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
    parameters: WithinPercentMovingAverage7DaysRuleParametersSpec
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
        return RuleExecutionResult()

    filtered = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]
    if len(filtered) == 0:
        return RuleExecutionResult()

    filtered_mean = float(scipy.mean(filtered))

    upper_bound = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_within / 100.0)
    lower_bound = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_within / 100.0)

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = filtered_mean

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___
