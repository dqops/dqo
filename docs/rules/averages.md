#averages
___

##<b>{{replace_chars_in_string('within_percent_moving_average_30_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
averages/within_percent_moving_average_30_days
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.
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
<td>max_percent_within</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be within a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/averages/within_percent_moving_average_30_days.dqrule.yaml"
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_mean = float(scipy.mean(filtered))

    threshold_upper = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_within / 100.0)
    threshold_lower = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_within / 100.0)

    if threshold_lower != None and threshold_upper != None:
        passed = (threshold_lower <= rule_parameters.actual_value and rule_parameters.actual_value <= threshold_upper)
    elif threshold_lower != None and threshold_upper == None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None and threshold_upper != None:
        passed = (rule_parameters.actual_value <= threshold_upper)

    expected_value = filtered_mean
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('within_percent_moving_average_7_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
averages/within_percent_moving_average_7_days
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.
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
<td>max_percent_within</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be within a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/averages/within_percent_moving_average_7_days.dqrule.yaml"
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_mean = float(scipy.mean(filtered))

    threshold_upper = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_within / 100.0)
    threshold_lower = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_within / 100.0)

    passed = (threshold_lower <= rule_parameters.actual_value and rule_parameters.actual_value <= threshold_upper)

    expected_value = filtered_mean
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('between_percent_moving_average_30_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
averages/between_percent_moving_average_30_days
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.
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
<td>max_percent_above</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be above a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

<tr>
<td>max_percent_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/averages/between_percent_moving_average_30_days.dqrule.yaml"
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_mean = float(scipy.mean(filtered))

    threshold_upper = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_above / 100.0)
    threshold_lower = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_below / 100.0)

    if threshold_lower != None and threshold_upper != None:
        passed = (threshold_lower <= rule_parameters.actual_value and rule_parameters.actual_value <= threshold_upper)
    elif threshold_lower != None and threshold_upper == None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None and threshold_upper != None:
        passed = (rule_parameters.actual_value <= threshold_upper)

    expected_value = filtered_mean
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('between_percent_moving_average_7_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
averages/between_percent_moving_average_7_days
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.
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
<td>max_percent_above</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be above a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

<tr>
<td>max_percent_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/averages/between_percent_moving_average_7_days.dqrule.yaml"
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_mean = float(scipy.mean(filtered))

    threshold_upper = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_above / 100.0)
    threshold_lower = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_below / 100.0)

    if threshold_lower != None and threshold_upper != None:
        passed = (threshold_lower <= rule_parameters.actual_value and rule_parameters.actual_value <= threshold_upper)
    elif threshold_lower != None and threshold_upper == None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None and threshold_upper != None:
        passed = (rule_parameters.actual_value <= threshold_upper)

    expected_value = filtered_mean
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('between_percent_moving_average_60_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
averages/between_percent_moving_average_60_days
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.
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
<td>max_percent_above</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be above a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

<tr>
<td>max_percent_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/averages/between_percent_moving_average_60_days.dqrule.yaml"
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_mean = float(scipy.mean(filtered))

    threshold_upper = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_above / 100.0)
    threshold_lower = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_below / 100.0)

    if threshold_lower != None and threshold_upper != None:
        passed = (threshold_lower <= rule_parameters.actual_value and rule_parameters.actual_value <= threshold_upper)
    elif threshold_lower != None and threshold_upper == None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None and threshold_upper != None:
        passed = (rule_parameters.actual_value <= threshold_upper)

    expected_value = filtered_mean
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('within_percent_moving_average_60_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
averages/within_percent_moving_average_60_days
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.
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
<td>max_percent_within</td>
<td>Maximum percent (e.q. 3%) that the current sensor reading could be within a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readings must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/averages/within_percent_moving_average_60_days.dqrule.yaml"
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_mean = float(scipy.mean(filtered))

    threshold_upper = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_within / 100.0)
    threshold_lower = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_within / 100.0)

    if threshold_lower != None and threshold_upper != None:
        passed = (threshold_lower <= rule_parameters.actual_value and rule_parameters.actual_value <= threshold_upper)
    elif threshold_lower != None and threshold_upper == None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None and threshold_upper != None:
        passed = (rule_parameters.actual_value <= threshold_upper)

    expected_value = filtered_mean
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('percent_moving_average', '_', ' ')}}</b>
<b>Full rule name</b>
```
averages/percent_moving_average
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.
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
<td>max_percent_above</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be above a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

<tr>
<td>max_percent_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/averages/percent_moving_average.dqrule.yaml"
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_mean = float(scipy.mean(filtered))

    threshold_upper = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_above / 100.0)
    threshold_lower = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_below / 100.0)

    passed = (threshold_lower <= rule_parameters.actual_value and rule_parameters.actual_value <= threshold_upper)

    expected_value = filtered_mean
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('within_percent_moving_average_7_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
averages/within_percent_moving_average_7_days
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.
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
<td>max_percent_within</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be within a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/averages/within_percent_moving_average_7_days.dqrule.yaml"
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_mean = float(scipy.mean(filtered))

    threshold_upper = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_within / 100.0)
    threshold_lower = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_within / 100.0)

    passed = (threshold_lower <= rule_parameters.actual_value and rule_parameters.actual_value <= threshold_upper)

    expected_value = filtered_mean
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('within_percent_moving_average_60_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
averages/within_percent_moving_average_60_days
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.
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
<td>max_percent_within</td>
<td>Maximum percent (e.q. 3%) that the current sensor reading could be within a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readings must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/averages/within_percent_moving_average_60_days.dqrule.yaml"
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_mean = float(scipy.mean(filtered))

    threshold_upper = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_within / 100.0)
    threshold_lower = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_within / 100.0)

    if threshold_lower != None and threshold_upper != None:
        passed = (threshold_lower <= rule_parameters.actual_value and rule_parameters.actual_value <= threshold_upper)
    elif threshold_lower != None and threshold_upper == None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None and threshold_upper != None:
        passed = (rule_parameters.actual_value <= threshold_upper)

    expected_value = filtered_mean
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('between_percent_moving_average_7_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
averages/between_percent_moving_average_7_days
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.
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
<td>max_percent_above</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be above a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

<tr>
<td>max_percent_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/averages/between_percent_moving_average_7_days.dqrule.yaml"
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_mean = float(scipy.mean(filtered))

    threshold_upper = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_above / 100.0)
    threshold_lower = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_below / 100.0)

    if threshold_lower != None and threshold_upper != None:
        passed = (threshold_lower <= rule_parameters.actual_value and rule_parameters.actual_value <= threshold_upper)
    elif threshold_lower != None and threshold_upper == None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None and threshold_upper != None:
        passed = (rule_parameters.actual_value <= threshold_upper)

    expected_value = filtered_mean
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('between_percent_moving_average_60_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
averages/between_percent_moving_average_60_days
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.
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
<td>max_percent_above</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be above a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

<tr>
<td>max_percent_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/averages/between_percent_moving_average_60_days.dqrule.yaml"
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_mean = float(scipy.mean(filtered))

    threshold_upper = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_above / 100.0)
    threshold_lower = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_below / 100.0)

    if threshold_lower != None and threshold_upper != None:
        passed = (threshold_lower <= rule_parameters.actual_value and rule_parameters.actual_value <= threshold_upper)
    elif threshold_lower != None and threshold_upper == None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None and threshold_upper != None:
        passed = (rule_parameters.actual_value <= threshold_upper)

    expected_value = filtered_mean
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('between_percent_moving_average_7_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
averages/between_percent_moving_average_7_days
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.
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
<td>max_percent_above</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be above a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

<tr>
<td>max_percent_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/averages/between_percent_moving_average_7_days.dqrule.yaml"
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_mean = float(scipy.mean(filtered))

    threshold_upper = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_above / 100.0)
    threshold_lower = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_below / 100.0)

    if threshold_lower != None and threshold_upper != None:
        passed = (threshold_lower <= rule_parameters.actual_value and rule_parameters.actual_value <= threshold_upper)
    elif threshold_lower != None and threshold_upper == None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None and threshold_upper != None:
        passed = (rule_parameters.actual_value <= threshold_upper)

    expected_value = filtered_mean
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('within_percent_moving_average_7_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
averages/within_percent_moving_average_7_days
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.
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
<td>max_percent_within</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be within a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/averages/within_percent_moving_average_7_days.dqrule.yaml"
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_mean = float(scipy.mean(filtered))

    threshold_upper = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_within / 100.0)
    threshold_lower = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_within / 100.0)

    passed = (threshold_lower <= rule_parameters.actual_value and rule_parameters.actual_value <= threshold_upper)

    expected_value = filtered_mean
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('between_percent_moving_average_30_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
averages/between_percent_moving_average_30_days
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.
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
<td>max_percent_above</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be above a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

<tr>
<td>max_percent_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/averages/between_percent_moving_average_30_days.dqrule.yaml"
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_mean = float(scipy.mean(filtered))

    threshold_upper = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_above / 100.0)
    threshold_lower = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_below / 100.0)

    if threshold_lower != None and threshold_upper != None:
        passed = (threshold_lower <= rule_parameters.actual_value and rule_parameters.actual_value <= threshold_upper)
    elif threshold_lower != None and threshold_upper == None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None and threshold_upper != None:
        passed = (rule_parameters.actual_value <= threshold_upper)

    expected_value = filtered_mean
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('within_percent_moving_average_30_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
averages/within_percent_moving_average_30_days
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.
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
<td>max_percent_within</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be within a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/averages/within_percent_moving_average_30_days.dqrule.yaml"
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_mean = float(scipy.mean(filtered))

    threshold_upper = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_within / 100.0)
    threshold_lower = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_within / 100.0)

    if threshold_lower != None and threshold_upper != None:
        passed = (threshold_lower <= rule_parameters.actual_value and rule_parameters.actual_value <= threshold_upper)
    elif threshold_lower != None and threshold_upper == None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None and threshold_upper != None:
        passed = (rule_parameters.actual_value <= threshold_upper)

    expected_value = filtered_mean
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('between_percent_moving_average_60_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
averages/between_percent_moving_average_60_days
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.
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
<td>max_percent_above</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be above a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

<tr>
<td>max_percent_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/averages/between_percent_moving_average_60_days.dqrule.yaml"
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_mean = float(scipy.mean(filtered))

    threshold_upper = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_above / 100.0)
    threshold_lower = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_below / 100.0)

    if threshold_lower != None and threshold_upper != None:
        passed = (threshold_lower <= rule_parameters.actual_value and rule_parameters.actual_value <= threshold_upper)
    elif threshold_lower != None and threshold_upper == None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None and threshold_upper != None:
        passed = (rule_parameters.actual_value <= threshold_upper)

    expected_value = filtered_mean
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('within_percent_moving_average_60_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
averages/within_percent_moving_average_60_days
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.
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
<td>max_percent_within</td>
<td>Maximum percent (e.q. 3%) that the current sensor reading could be within a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readings must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/averages/within_percent_moving_average_60_days.dqrule.yaml"
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_mean = float(scipy.mean(filtered))

    threshold_upper = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_within / 100.0)
    threshold_lower = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_within / 100.0)

    if threshold_lower != None and threshold_upper != None:
        passed = (threshold_lower <= rule_parameters.actual_value and rule_parameters.actual_value <= threshold_upper)
    elif threshold_lower != None and threshold_upper == None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None and threshold_upper != None:
        passed = (rule_parameters.actual_value <= threshold_upper)

    expected_value = filtered_mean
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('within_percent_moving_average_30_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
averages/within_percent_moving_average_30_days
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.
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
<td>max_percent_within</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be within a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/averages/within_percent_moving_average_30_days.dqrule.yaml"
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_mean = float(scipy.mean(filtered))

    threshold_upper = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_within / 100.0)
    threshold_lower = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_within / 100.0)

    if threshold_lower != None and threshold_upper != None:
        passed = (threshold_lower <= rule_parameters.actual_value and rule_parameters.actual_value <= threshold_upper)
    elif threshold_lower != None and threshold_upper == None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None and threshold_upper != None:
        passed = (rule_parameters.actual_value <= threshold_upper)

    expected_value = filtered_mean
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('between_percent_moving_average_30_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
averages/between_percent_moving_average_30_days
```
<b>Description</b>
<br/>
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.
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
<td>max_percent_above</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be above a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

<tr>
<td>max_percent_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/averages/between_percent_moving_average_30_days.dqrule.yaml"
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

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_mean = float(scipy.mean(filtered))

    threshold_upper = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_above / 100.0)
    threshold_lower = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_below / 100.0)

    if threshold_lower != None and threshold_upper != None:
        passed = (threshold_lower <= rule_parameters.actual_value and rule_parameters.actual_value <= threshold_upper)
    elif threshold_lower != None and threshold_upper == None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None and threshold_upper != None:
        passed = (rule_parameters.actual_value <= threshold_upper)

    expected_value = filtered_mean
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___
