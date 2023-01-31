#comparison
___

##<b>{{replace_chars_in_string('max_count', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/max_count
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>max_count</td>
<td>Maximum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('long_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/max_count.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value <= rule_parameters.parameters.max_count
    expected_value = rule_parameters.parameters.max_count
    lower_bound = rule_parameters.parameters.max_count
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('between_ints', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/between_ints
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check readout is between begin and end values.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>begin</td>
<td>Minimum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('long_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

<tr>
<td>end</td>
<td>Maximum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('long_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/between_ints.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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
class BetweenIntsRuleParametersSpec:
    begin: int
    end: int


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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    expected_value = rule_parameters.parameters.end
    lower_bound = rule_parameters.parameters.begin
    upper_bound = rule_parameters.parameters.end
    passed = (lower_bound <= rule_parameters.actual_value and rule_parameters.actual_value <= upper_bound)
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('min_count', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/min_count
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>min_count</td>
<td>Minimum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('long_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/min_count.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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
class MinCountRuleParametersSpec:
    min_count: float


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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value >= rule_parameters.parameters.min_count
    expected_value = rule_parameters.parameters.min_count
    lower_bound = rule_parameters.parameters.min_count
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('min_count', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/min_count
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>min_count</td>
<td>Minimum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('long_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/min_count.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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
class MinCountRuleParametersSpec:
    min_count: float


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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value >= rule_parameters.parameters.min_count
    expected_value = rule_parameters.parameters.min_count
    lower_bound = rule_parameters.parameters.min_count
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('min_percent', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/min_percent
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>min_percent</td>
<td>Minimum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/min_percent.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value >= rule_parameters.parameters.min_percent
    expected_value = rule_parameters.parameters.min_percent
    lower_bound = rule_parameters.parameters.min_percent
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('max_value', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/max_value
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>max_value</td>
<td>Maximum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/max_value.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value <= rule_parameters.parameters.max_value
    expected_value = rule_parameters.parameters.max_value
    lower_bound = rule_parameters.parameters.max_value
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('max_count', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/max_count
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>max_count</td>
<td>Maximum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('long_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/max_count.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value <= rule_parameters.parameters.max_count
    expected_value = rule_parameters.parameters.max_count
    lower_bound = rule_parameters.parameters.max_count
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('min_percent', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/min_percent
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>min_percent</td>
<td>Minimum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/min_percent.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value >= rule_parameters.parameters.min_percent
    expected_value = rule_parameters.parameters.min_percent
    lower_bound = rule_parameters.parameters.min_percent
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('min', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/min
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>min_value</td>
<td>Minimum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/min.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value >= rule_parameters.parameters.min_value
    expected_value = rule_parameters.parameters.min_value
    lower_bound = rule_parameters.parameters.min_value
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('max_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/max_days
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>max_days</td>
<td>Maximum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/max_days.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value <= rule_parameters.parameters.max_days
    expected_value = rule_parameters.parameters.max_days
    lower_bound = rule_parameters.parameters.max_days
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('max_percent', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/max_percent
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>max_percent</td>
<td>Maximum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/max_percent.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value <= rule_parameters.parameters.max_percent
    expected_value = rule_parameters.parameters.max_percent
    lower_bound = rule_parameters.parameters.max_percent
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('max_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/max_days
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>max_days</td>
<td>Maximum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/max_days.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value <= rule_parameters.parameters.max_days
    expected_value = rule_parameters.parameters.max_days
    lower_bound = rule_parameters.parameters.max_days
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('min_percent', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/min_percent
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>min_percent</td>
<td>Minimum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/min_percent.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value >= rule_parameters.parameters.min_percent
    expected_value = rule_parameters.parameters.min_percent
    lower_bound = rule_parameters.parameters.min_percent
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('min_percent', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/min_percent
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>min_percent</td>
<td>Minimum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/min_percent.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value >= rule_parameters.parameters.min_percent
    expected_value = rule_parameters.parameters.min_percent
    lower_bound = rule_parameters.parameters.min_percent
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('max_percent', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/max_percent
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>max_percent</td>
<td>Maximum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/max_percent.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value <= rule_parameters.parameters.max_percent
    expected_value = rule_parameters.parameters.max_percent
    lower_bound = rule_parameters.parameters.max_percent
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('min_percent', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/min_percent
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>min_percent</td>
<td>Minimum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/min_percent.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value >= rule_parameters.parameters.min_percent
    expected_value = rule_parameters.parameters.min_percent
    lower_bound = rule_parameters.parameters.min_percent
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('max_percent', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/max_percent
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>max_percent</td>
<td>Maximum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/max_percent.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value <= rule_parameters.parameters.max_percent
    expected_value = rule_parameters.parameters.max_percent
    lower_bound = rule_parameters.parameters.max_percent
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('max_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/max_days
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>max_days</td>
<td>Maximum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/max_days.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value <= rule_parameters.parameters.max_days
    expected_value = rule_parameters.parameters.max_days
    lower_bound = rule_parameters.parameters.max_days
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('max_percent', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/max_percent
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>max_percent</td>
<td>Maximum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/max_percent.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value <= rule_parameters.parameters.max_percent
    expected_value = rule_parameters.parameters.max_percent
    lower_bound = rule_parameters.parameters.max_percent
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('max_percent', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/max_percent
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>max_percent</td>
<td>Maximum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/max_percent.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value <= rule_parameters.parameters.max_percent
    expected_value = rule_parameters.parameters.max_percent
    lower_bound = rule_parameters.parameters.max_percent
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('min_percent', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/min_percent
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>min_percent</td>
<td>Minimum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/min_percent.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value >= rule_parameters.parameters.min_percent
    expected_value = rule_parameters.parameters.min_percent
    lower_bound = rule_parameters.parameters.min_percent
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('max_percent', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/max_percent
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>max_percent</td>
<td>Maximum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/max_percent.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value <= rule_parameters.parameters.max_percent
    expected_value = rule_parameters.parameters.max_percent
    lower_bound = rule_parameters.parameters.max_percent
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('max_percent', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/max_percent
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>max_percent</td>
<td>Maximum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/max_percent.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value <= rule_parameters.parameters.max_percent
    expected_value = rule_parameters.parameters.max_percent
    lower_bound = rule_parameters.parameters.max_percent
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('max_percent', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/max_percent
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>max_percent</td>
<td>Maximum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/max_percent.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value <= rule_parameters.parameters.max_percent
    expected_value = rule_parameters.parameters.max_percent
    lower_bound = rule_parameters.parameters.max_percent
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('between_floats', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/between_floats
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check readout is between begin and end values.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>begin</td>
<td>Minimum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

<tr>
<td>end</td>
<td>Maximum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/between_floats.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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
class BetweenIntsRuleParametersSpec:
    begin: float
    end: float


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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    expected_value = rule_parameters.parameters.end
    lower_bound = rule_parameters.parameters.begin
    upper_bound = rule_parameters.parameters.end
    passed = (lower_bound <= rule_parameters.actual_value and rule_parameters.actual_value <= upper_bound)
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('max_count', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/max_count
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>max_count</td>
<td>Maximum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('long_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/max_count.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value <= rule_parameters.parameters.max_count
    expected_value = rule_parameters.parameters.max_count
    lower_bound = rule_parameters.parameters.max_count
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('max', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/max
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check readsout is less or equal a maximum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>max_value</td>
<td>Maximum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/max.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value <= rule_parameters.parameters.max_value
    expected_value = rule_parameters.parameters.max_value
    lower_bound = rule_parameters.parameters.max_value
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('min_value', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/min_value
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>min_value</td>
<td>Minimum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/min_value.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value >= rule_parameters.parameters.min_value
    expected_value = rule_parameters.parameters.min_value
    lower_bound = rule_parameters.parameters.min_value
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('equals', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/equals
```
<b>Description</b>
<br/>
Data quality rule that verifies that a data quality check readout equals a given value. A margin of error may be configured.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>expected_value</td>
<td>Expected value for the actual_value returned by the sensor. The sensor value should equal expected_value +/- the error_margin.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

<tr>
<td>error_margin</td>
<td>Error margin for comparison.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/equals.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value >= rule_parameters.parameters.expected_value - rule_parameters.parameters.error_margin and \
            rule_parameters.actual_value <= rule_parameters.parameters.expected_value + rule_parameters.parameters.error_margin
    expected_value = rule_parameters.parameters.expected_value
    lower_bound = rule_parameters.parameters.expected_value - rule_parameters.parameters.error_margin
    upper_bound = rule_parameters.parameters.expected_value + rule_parameters.parameters.error_margin
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
```
___

##<b>{{replace_chars_in_string('min_percent', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/min_percent
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>min_percent</td>
<td>Minimum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/min_percent.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value >= rule_parameters.parameters.min_percent
    expected_value = rule_parameters.parameters.min_percent
    lower_bound = rule_parameters.parameters.min_percent
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('min_percent', '_', ' ')}}</b>
<b>Full rule name</b>
```
comparison/min_percent
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>min_percent</td>
<td>Minimum accepted value for the actual_value returned by the sensor (inclusive).</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/comparison/min_percent.dqorule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)
    passed = rule_parameters.actual_value >= rule_parameters.parameters.min_percent
    expected_value = rule_parameters.parameters.min_percent
    lower_bound = rule_parameters.parameters.min_percent
    upper_bound = None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___
