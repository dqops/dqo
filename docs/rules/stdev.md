#stdev
___

##<b>{{replace_chars_in_string('percentile_moving_stdev', '_', ' ')}}</b>
<b>Full rule name</b>
```
stdev/percentile_moving_stdev
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
<td>percentile_stdev_above</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be above a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

<tr>
<td>percentile_stdev_below</td>
<td>Maximum percent (e.q. 3%) that the current sensorreadout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/stdev/percentile_moving_stdev.dqrule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from datetime import datetime
from typing import Sequence
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class PercentileMovingStdevRuleParametersSpec:
    percentile_stdev_above: float
    percentile_stdev_below: float


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
    parameters: PercentileMovingStdevRuleParametersSpec
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
    filtered_std = float(scipy.stats.tstd(filtered))
    filtered_mean = float(scipy.mean(filtered))

    multiple_stdev_above = abs(float(scipy.stats.norm.ppf(rule_parameters.parameters.percentile_stdev_above/100.0 / 2, 0, 1)))
    multiple_stdev_below = abs(float(scipy.stats.norm.ppf(rule_parameters.parameters.percentile_stdev_below/100.0 / 2, 0, 1)))

    threshold_upper = filtered_mean + multiple_stdev_above * filtered_std
    threshold_lower = filtered_mean - multiple_stdev_below * filtered_std

    if multiple_stdev_above != None and multiple_stdev_below != None:
        passed = (threshold_lower <= rule_parameters.actual_value and rule_parameters.actual_value <= threshold_upper)
    elif multiple_stdev_above != None and threshold_lower == None:
        passed = (rule_parameters.actual_value <= threshold_upper)
    elif threshold_upper == None and multiple_stdev_below != None:
        passed = (threshold_lower <= rule_parameters.actual_value)


    expected_value = filtered_mean
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('below_percent_population_stdev_60_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
stdev/below_percent_population_stdev_60_days
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
<td>percent_population_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 60 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/stdev/below_percent_population_stdev_60_days.dqrule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from datetime import datetime
from typing import Sequence
import scipy
import scipy.stats

# rule specific parameters object, contains values received from the quality check threshold configuration
class BelowPercentPopulationStdev60DaysRuleParametersSpec:
    percent_population_below: float


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
    parameters: BelowPercentPopulationStdev60DaysRuleParametersSpec
    time_period_local: datetime
    previous_readouts: Sequence[HistoricDataPoint]
    time_window: RuleTimeWindowSettingsSpec


# default object that should be returned to the dqo.io engine, specifies if the rule was passed or failed,
# what is the expected value for the rule and what are the upper and lower boundaries of accepted values (optional)
class RuleExecutionResult:
    passed: bool
    expected_value: float
    lower_bound: float

    def __init__(self, passed=True, expected_value=None, lower_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_std = float(scipy.stats.tstd(filtered))
    filtered_mean = float(scipy.mean(filtered))

    threshold_lower = filtered_mean - rule_parameters.parameters.percent_population_below * filtered_std

    if rule_parameters.parameters.percent_population_below != None:
        passed = threshold_lower <= rule_parameters.actual_value
    elif threshold_lower == None:
        passed = rule_parameters.actual_value

    expected_value = filtered_mean
    lower_bound = threshold_lower
    return RuleExecutionResult(passed, expected_value, lower_bound)
```
___

##<b>{{replace_chars_in_string('below_percent_population_stdev_60_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
stdev/below_percent_population_stdev_60_days
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
<td>percent_population_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 60 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/stdev/below_percent_population_stdev_60_days.dqrule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from datetime import datetime
from typing import Sequence
import scipy
import scipy.stats

# rule specific parameters object, contains values received from the quality check threshold configuration
class BelowPercentPopulationStdev60DaysRuleParametersSpec:
    percent_population_below: float


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
    parameters: BelowPercentPopulationStdev60DaysRuleParametersSpec
    time_period_local: datetime
    previous_readouts: Sequence[HistoricDataPoint]
    time_window: RuleTimeWindowSettingsSpec


# default object that should be returned to the dqo.io engine, specifies if the rule was passed or failed,
# what is the expected value for the rule and what are the upper and lower boundaries of accepted values (optional)
class RuleExecutionResult:
    passed: bool
    expected_value: float
    lower_bound: float

    def __init__(self, passed=True, expected_value=None, lower_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_std = float(scipy.stats.tstd(filtered))
    filtered_mean = float(scipy.mean(filtered))

    threshold_lower = filtered_mean - rule_parameters.parameters.percent_population_below * filtered_std

    if rule_parameters.parameters.percent_population_below != None:
        passed = threshold_lower <= rule_parameters.actual_value
    elif threshold_lower == None:
        passed = rule_parameters.actual_value

    expected_value = filtered_mean
    lower_bound = threshold_lower
    return RuleExecutionResult(passed, expected_value, lower_bound)
```
___

##<b>{{replace_chars_in_string('below_stdev_multiply_60_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
stdev/below_stdev_multiply_60_days
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
<td>stdev_multiplier_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 60 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/stdev/below_stdev_multiply_60_days.dqrule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from datetime import datetime
from typing import Sequence
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class BelowStdevMultiply60DaysRuleParametersSpec:
    stdev_multiplier_below: float


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
    parameters: BelowStdevMultiply60DaysRuleParametersSpec
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
        return RuleExecutionResult(True, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_std = float(scipy.stats.tstd(filtered))
    filtered_mean = float(scipy.mean(filtered))

    multiple_stdev_below = abs(float(scipy.stats.norm.ppf(rule_parameters.parameters.stdev_multiplier_below/100.0 / 2, 0, 1)))

    threshold_lower = filtered_mean - multiple_stdev_below * filtered_std

    if multiple_stdev_below != None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None:
        passed = rule_parameters.actual_value


    expected_value = filtered_mean
    lower_bound = threshold_lower
    return RuleExecutionResult(passed, expected_value, lower_bound)
```
___

##<b>{{replace_chars_in_string('below_percent_population_stdev_60_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
stdev/below_percent_population_stdev_60_days
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
<td>percent_population_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 60 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/stdev/below_percent_population_stdev_60_days.dqrule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from datetime import datetime
from typing import Sequence
import scipy
import scipy.stats

# rule specific parameters object, contains values received from the quality check threshold configuration
class BelowPercentPopulationStdev60DaysRuleParametersSpec:
    percent_population_below: float


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
    parameters: BelowPercentPopulationStdev60DaysRuleParametersSpec
    time_period_local: datetime
    previous_readouts: Sequence[HistoricDataPoint]
    time_window: RuleTimeWindowSettingsSpec


# default object that should be returned to the dqo.io engine, specifies if the rule was passed or failed,
# what is the expected value for the rule and what are the upper and lower boundaries of accepted values (optional)
class RuleExecutionResult:
    passed: bool
    expected_value: float
    lower_bound: float

    def __init__(self, passed=True, expected_value=None, lower_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_std = float(scipy.stats.tstd(filtered))
    filtered_mean = float(scipy.mean(filtered))

    threshold_lower = filtered_mean - rule_parameters.parameters.percent_population_below * filtered_std

    if rule_parameters.parameters.percent_population_below != None:
        passed = threshold_lower <= rule_parameters.actual_value
    elif threshold_lower == None:
        passed = rule_parameters.actual_value

    expected_value = filtered_mean
    lower_bound = threshold_lower
    return RuleExecutionResult(passed, expected_value, lower_bound)
```
___

##<b>{{replace_chars_in_string('below_stdev_multiply_60_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
stdev/below_stdev_multiply_60_days
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
<td>stdev_multiplier_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 60 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/stdev/below_stdev_multiply_60_days.dqrule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from datetime import datetime
from typing import Sequence
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class BelowStdevMultiply60DaysRuleParametersSpec:
    stdev_multiplier_below: float


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
    parameters: BelowStdevMultiply60DaysRuleParametersSpec
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
        return RuleExecutionResult(True, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_std = float(scipy.stats.tstd(filtered))
    filtered_mean = float(scipy.mean(filtered))

    multiple_stdev_below = abs(float(scipy.stats.norm.ppf(rule_parameters.parameters.stdev_multiplier_below/100.0 / 2, 0, 1)))

    threshold_lower = filtered_mean - multiple_stdev_below * filtered_std

    if multiple_stdev_below != None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None:
        passed = rule_parameters.actual_value


    expected_value = filtered_mean
    lower_bound = threshold_lower
    return RuleExecutionResult(passed, expected_value, lower_bound)
```
___

##<b>{{replace_chars_in_string('below_stdev_multiply_30_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
stdev/below_stdev_multiply_30_days
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
<td>stdev_multiplier_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 30 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/stdev/below_stdev_multiply_30_days.dqrule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from datetime import datetime
from typing import Sequence
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class BelowStdevMultiply30DaysRuleParametersSpec:
    stdev_multiplier_below: float


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
    parameters: BelowStdevMultiply30DaysRuleParametersSpec
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
        return RuleExecutionResult(True, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_std = float(scipy.stats.tstd(filtered))
    filtered_mean = float(scipy.mean(filtered))

    multiple_stdev_below = abs(float(scipy.stats.norm.ppf(rule_parameters.parameters.stdev_multiplier_below/100.0 / 2, 0, 1)))

    threshold_lower = filtered_mean - multiple_stdev_below * filtered_std

    if multiple_stdev_below != None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None:
        passed = rule_parameters.actual_value


    expected_value = filtered_mean
    lower_bound = threshold_lower
    return RuleExecutionResult(passed, expected_value, lower_bound)

```
___

##<b>{{replace_chars_in_string('below_stdev_multiply_60_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
stdev/below_stdev_multiply_60_days
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
<td>stdev_multiplier_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 60 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/stdev/below_stdev_multiply_60_days.dqrule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from datetime import datetime
from typing import Sequence
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class BelowStdevMultiply60DaysRuleParametersSpec:
    stdev_multiplier_below: float


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
    parameters: BelowStdevMultiply60DaysRuleParametersSpec
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
        return RuleExecutionResult(True, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_std = float(scipy.stats.tstd(filtered))
    filtered_mean = float(scipy.mean(filtered))

    multiple_stdev_below = abs(float(scipy.stats.norm.ppf(rule_parameters.parameters.stdev_multiplier_below/100.0 / 2, 0, 1)))

    threshold_lower = filtered_mean - multiple_stdev_below * filtered_std

    if multiple_stdev_below != None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None:
        passed = rule_parameters.actual_value


    expected_value = filtered_mean
    lower_bound = threshold_lower
    return RuleExecutionResult(passed, expected_value, lower_bound)
```
___

##<b>{{replace_chars_in_string('below_stdev_multiply_7_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
stdev/below_stdev_multiply_7_days
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
<td>stdev_multiplier_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/stdev/below_stdev_multiply_7_days.dqrule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from datetime import datetime
from typing import Sequence
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class BelowStdevMultiply7DaysRuleParametersSpec:
    stdev_multiplier_below: float


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
    parameters: BelowStdevMultiply7DaysRuleParametersSpec
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
        return RuleExecutionResult(True, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_std = float(scipy.stats.tstd(filtered))
    filtered_mean = float(scipy.mean(filtered))

    multiple_stdev_below = abs(float(scipy.stats.norm.ppf(rule_parameters.parameters.stdev_multiplier_below/100.0 / 2, 0, 1)))

    threshold_lower = filtered_mean - multiple_stdev_below * filtered_std

    if multiple_stdev_below != None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None:
        passed = rule_parameters.actual_value


    expected_value = filtered_mean
    lower_bound = threshold_lower
    return RuleExecutionResult(passed, expected_value, lower_bound)

```
___

##<b>{{replace_chars_in_string('below_stdev_multiply_7_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
stdev/below_stdev_multiply_7_days
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
<td>stdev_multiplier_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/stdev/below_stdev_multiply_7_days.dqrule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from datetime import datetime
from typing import Sequence
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class BelowStdevMultiply7DaysRuleParametersSpec:
    stdev_multiplier_below: float


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
    parameters: BelowStdevMultiply7DaysRuleParametersSpec
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
        return RuleExecutionResult(True, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_std = float(scipy.stats.tstd(filtered))
    filtered_mean = float(scipy.mean(filtered))

    multiple_stdev_below = abs(float(scipy.stats.norm.ppf(rule_parameters.parameters.stdev_multiplier_below/100.0 / 2, 0, 1)))

    threshold_lower = filtered_mean - multiple_stdev_below * filtered_std

    if multiple_stdev_below != None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None:
        passed = rule_parameters.actual_value


    expected_value = filtered_mean
    lower_bound = threshold_lower
    return RuleExecutionResult(passed, expected_value, lower_bound)

```
___

##<b>{{replace_chars_in_string('below_percent_population_stdev_30_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
stdev/below_percent_population_stdev_30_days
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
<td>percent_population_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, Fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 30 readout must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/stdev/below_percent_population_stdev_30_days.dqrule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from datetime import datetime
from typing import Sequence
import scipy
import scipy.stats

# rule specific parameters object, contains values received from the quality check threshold configuration
class BelowPercentPopulationStdev30DaysRuleParametersSpec:
    percent_population_below: float


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
    parameters: BelowPercentPopulationStdev30DaysRuleParametersSpec
    time_period_local: datetime
    previous_readouts: Sequence[HistoricDataPoint]
    time_window: RuleTimeWindowSettingsSpec


# default object that should be returned to the dqo.io engine, specifies if the rule was passed or failed,
# what is the expected value for the rule and what are the upper and lower boundaries of accepted values (optional)
class RuleExecutionResult:
    passed: bool
    expected_value: float
    lower_bound: float

    def __init__(self, passed=True, expected_value=None, lower_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_std = float(scipy.stats.tstd(filtered))
    filtered_mean = float(scipy.mean(filtered))

    threshold_lower = filtered_mean - rule_parameters.parameters.percent_population_below * filtered_std

    if rule_parameters.parameters.percent_population_below != None:
        passed = threshold_lower <= rule_parameters.actual_value
    elif threshold_lower == None:
        passed = rule_parameters.actual_value

    expected_value = filtered_mean
    lower_bound = threshold_lower
    return RuleExecutionResult(passed, expected_value, lower_bound)

```
___

##<b>{{replace_chars_in_string('below_percent_population_stdev_30_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
stdev/below_percent_population_stdev_30_days
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
<td>percent_population_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, Fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 30 readout must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/stdev/below_percent_population_stdev_30_days.dqrule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from datetime import datetime
from typing import Sequence
import scipy
import scipy.stats

# rule specific parameters object, contains values received from the quality check threshold configuration
class BelowPercentPopulationStdev30DaysRuleParametersSpec:
    percent_population_below: float


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
    parameters: BelowPercentPopulationStdev30DaysRuleParametersSpec
    time_period_local: datetime
    previous_readouts: Sequence[HistoricDataPoint]
    time_window: RuleTimeWindowSettingsSpec


# default object that should be returned to the dqo.io engine, specifies if the rule was passed or failed,
# what is the expected value for the rule and what are the upper and lower boundaries of accepted values (optional)
class RuleExecutionResult:
    passed: bool
    expected_value: float
    lower_bound: float

    def __init__(self, passed=True, expected_value=None, lower_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_std = float(scipy.stats.tstd(filtered))
    filtered_mean = float(scipy.mean(filtered))

    threshold_lower = filtered_mean - rule_parameters.parameters.percent_population_below * filtered_std

    if rule_parameters.parameters.percent_population_below != None:
        passed = threshold_lower <= rule_parameters.actual_value
    elif threshold_lower == None:
        passed = rule_parameters.actual_value

    expected_value = filtered_mean
    lower_bound = threshold_lower
    return RuleExecutionResult(passed, expected_value, lower_bound)

```
___

##<b>{{replace_chars_in_string('percent_moving_stdev', '_', ' ')}}</b>
<b>Full rule name</b>
```
stdev/percent_moving_stdev
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
<td>multiple_stdev_above</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be above a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

<tr>
<td>multiple_stdev_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/stdev/percent_moving_stdev.dqrule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from datetime import datetime
from typing import Sequence
import scipy
import scipy.stats

# rule specific parameters object, contains values received from the quality check threshold configuration
class PercentMovingStdRuleParametersSpec:
    multiple_stdev_above: float
    multiple_stdev_below: float


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
    parameters: PercentMovingStdRuleParametersSpec
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
    filtered_std = float(scipy.stats.tstd(filtered))
    filtered_mean = float(scipy.mean(filtered))


    threshold_upper = filtered_mean + rule_parameters.parameters.multiple_stdev_above * filtered_std

    threshold_lower = filtered_mean - rule_parameters.parameters.multiple_stdev_below * filtered_std

    if rule_parameters.parameters.multiple_stdev_above != None and rule_parameters.parameters.multiple_stdev_below != None:
        passed = (threshold_lower <= rule_parameters.actual_value and rule_parameters.actual_value <= threshold_upper)
    elif rule_parameters.parameters.multiple_stdev_above != None and threshold_lower == None:
        passed = (rule_parameters.actual_value <= threshold_upper)
    elif threshold_upper == None and rule_parameters.parameters.multiple_stdev_below != None:
        passed = (threshold_lower <= rule_parameters.actual_value)



    expected_value = filtered_mean
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

##<b>{{replace_chars_in_string('below_percent_population_stdev_7_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
stdev/below_percent_population_stdev_7_days
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
<td>percent_population_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/stdev/below_percent_population_stdev_7_days.dqrule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from datetime import datetime
from typing import Sequence
import scipy
import scipy.stats

# rule specific parameters object, contains values received from the quality check threshold configuration
class BelowPercentPopulationStdev7DaysRuleParametersSpec:
    percent_population_below: float


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
    parameters: BelowPercentPopulationStdev7DaysRuleParametersSpec
    time_period_local: datetime
    previous_readouts: Sequence[HistoricDataPoint]
    time_window: RuleTimeWindowSettingsSpec


# default object that should be returned to the dqo.io engine, specifies if the rule was passed or failed,
# what is the expected value for the rule and what are the lower boundaries of accepted values (optional)
class RuleExecutionResult:
    passed: bool
    expected_value: float
    lower_bound: float

    def __init__(self, passed=True, expected_value=None, lower_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_std = float(scipy.stats.tstd(filtered))
    filtered_mean = float(scipy.mean(filtered))

    threshold_lower = filtered_mean - rule_parameters.parameters.percent_population_below * filtered_std

    if rule_parameters.parameters.percent_population_below != None:
        passed = threshold_lower <= rule_parameters.actual_value
    elif threshold_lower == None:
        passed = rule_parameters.actual_value

    expected_value = filtered_mean
    lower_bound = threshold_lower
    return RuleExecutionResult(passed, expected_value, lower_bound)

```
___

##<b>{{replace_chars_in_string('below_stdev_multiply_30_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
stdev/below_stdev_multiply_30_days
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
<td>stdev_multiplier_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 30 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/stdev/below_stdev_multiply_30_days.dqrule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from datetime import datetime
from typing import Sequence
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class BelowStdevMultiply30DaysRuleParametersSpec:
    stdev_multiplier_below: float


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
    parameters: BelowStdevMultiply30DaysRuleParametersSpec
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
        return RuleExecutionResult(True, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_std = float(scipy.stats.tstd(filtered))
    filtered_mean = float(scipy.mean(filtered))

    multiple_stdev_below = abs(float(scipy.stats.norm.ppf(rule_parameters.parameters.stdev_multiplier_below/100.0 / 2, 0, 1)))

    threshold_lower = filtered_mean - multiple_stdev_below * filtered_std

    if multiple_stdev_below != None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None:
        passed = rule_parameters.actual_value


    expected_value = filtered_mean
    lower_bound = threshold_lower
    return RuleExecutionResult(passed, expected_value, lower_bound)

```
___

##<b>{{replace_chars_in_string('below_percent_population_stdev_7_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
stdev/below_percent_population_stdev_7_days
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
<td>percent_population_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/stdev/below_percent_population_stdev_7_days.dqrule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from datetime import datetime
from typing import Sequence
import scipy
import scipy.stats

# rule specific parameters object, contains values received from the quality check threshold configuration
class BelowPercentPopulationStdev7DaysRuleParametersSpec:
    percent_population_below: float


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
    parameters: BelowPercentPopulationStdev7DaysRuleParametersSpec
    time_period_local: datetime
    previous_readouts: Sequence[HistoricDataPoint]
    time_window: RuleTimeWindowSettingsSpec


# default object that should be returned to the dqo.io engine, specifies if the rule was passed or failed,
# what is the expected value for the rule and what are the lower boundaries of accepted values (optional)
class RuleExecutionResult:
    passed: bool
    expected_value: float
    lower_bound: float

    def __init__(self, passed=True, expected_value=None, lower_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_std = float(scipy.stats.tstd(filtered))
    filtered_mean = float(scipy.mean(filtered))

    threshold_lower = filtered_mean - rule_parameters.parameters.percent_population_below * filtered_std

    if rule_parameters.parameters.percent_population_below != None:
        passed = threshold_lower <= rule_parameters.actual_value
    elif threshold_lower == None:
        passed = rule_parameters.actual_value

    expected_value = filtered_mean
    lower_bound = threshold_lower
    return RuleExecutionResult(passed, expected_value, lower_bound)

```
___

##<b>{{replace_chars_in_string('below_percent_population_stdev_7_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
stdev/below_percent_population_stdev_7_days
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
<td>percent_population_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/stdev/below_percent_population_stdev_7_days.dqrule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from datetime import datetime
from typing import Sequence
import scipy
import scipy.stats

# rule specific parameters object, contains values received from the quality check threshold configuration
class BelowPercentPopulationStdev7DaysRuleParametersSpec:
    percent_population_below: float


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
    parameters: BelowPercentPopulationStdev7DaysRuleParametersSpec
    time_period_local: datetime
    previous_readouts: Sequence[HistoricDataPoint]
    time_window: RuleTimeWindowSettingsSpec


# default object that should be returned to the dqo.io engine, specifies if the rule was passed or failed,
# what is the expected value for the rule and what are the lower boundaries of accepted values (optional)
class RuleExecutionResult:
    passed: bool
    expected_value: float
    lower_bound: float

    def __init__(self, passed=True, expected_value=None, lower_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_std = float(scipy.stats.tstd(filtered))
    filtered_mean = float(scipy.mean(filtered))

    threshold_lower = filtered_mean - rule_parameters.parameters.percent_population_below * filtered_std

    if rule_parameters.parameters.percent_population_below != None:
        passed = threshold_lower <= rule_parameters.actual_value
    elif threshold_lower == None:
        passed = rule_parameters.actual_value

    expected_value = filtered_mean
    lower_bound = threshold_lower
    return RuleExecutionResult(passed, expected_value, lower_bound)

```
___

##<b>{{replace_chars_in_string('below_stdev_multiply_30_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
stdev/below_stdev_multiply_30_days
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
<td>stdev_multiplier_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 30 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/stdev/below_stdev_multiply_30_days.dqrule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from datetime import datetime
from typing import Sequence
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class BelowStdevMultiply30DaysRuleParametersSpec:
    stdev_multiplier_below: float


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
    parameters: BelowStdevMultiply30DaysRuleParametersSpec
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
        return RuleExecutionResult(True, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_std = float(scipy.stats.tstd(filtered))
    filtered_mean = float(scipy.mean(filtered))

    multiple_stdev_below = abs(float(scipy.stats.norm.ppf(rule_parameters.parameters.stdev_multiplier_below/100.0 / 2, 0, 1)))

    threshold_lower = filtered_mean - multiple_stdev_below * filtered_std

    if multiple_stdev_below != None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None:
        passed = rule_parameters.actual_value


    expected_value = filtered_mean
    lower_bound = threshold_lower
    return RuleExecutionResult(passed, expected_value, lower_bound)

```
___

##<b>{{replace_chars_in_string('below_percent_population_stdev_30_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
stdev/below_percent_population_stdev_30_days
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
<td>percent_population_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, Fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 30 readout must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/stdev/below_percent_population_stdev_30_days.dqrule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from datetime import datetime
from typing import Sequence
import scipy
import scipy.stats

# rule specific parameters object, contains values received from the quality check threshold configuration
class BelowPercentPopulationStdev30DaysRuleParametersSpec:
    percent_population_below: float


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
    parameters: BelowPercentPopulationStdev30DaysRuleParametersSpec
    time_period_local: datetime
    previous_readouts: Sequence[HistoricDataPoint]
    time_window: RuleTimeWindowSettingsSpec


# default object that should be returned to the dqo.io engine, specifies if the rule was passed or failed,
# what is the expected value for the rule and what are the upper and lower boundaries of accepted values (optional)
class RuleExecutionResult:
    passed: bool
    expected_value: float
    lower_bound: float

    def __init__(self, passed=True, expected_value=None, lower_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters,'actual_value'):
        return RuleExecutionResult(True, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_std = float(scipy.stats.tstd(filtered))
    filtered_mean = float(scipy.mean(filtered))

    threshold_lower = filtered_mean - rule_parameters.parameters.percent_population_below * filtered_std

    if rule_parameters.parameters.percent_population_below != None:
        passed = threshold_lower <= rule_parameters.actual_value
    elif threshold_lower == None:
        passed = rule_parameters.actual_value

    expected_value = filtered_mean
    lower_bound = threshold_lower
    return RuleExecutionResult(passed, expected_value, lower_bound)

```
___

##<b>{{replace_chars_in_string('below_stdev_multiply_7_days', '_', ' ')}}</b>
<b>Full rule name</b>
```
stdev/below_stdev_multiply_7_days
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
<td>stdev_multiplier_below</td>
<td>Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>Example</b>
```yaml
--8<-- "home/rules/stdev/below_stdev_multiply_7_days.dqrule.yaml"
```
<b>Rule implementation (Python)</b>
```python
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from datetime import datetime
from typing import Sequence
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class BelowStdevMultiply7DaysRuleParametersSpec:
    stdev_multiplier_below: float


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
    parameters: BelowStdevMultiply7DaysRuleParametersSpec
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
        return RuleExecutionResult(True, None, None)

    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_std = float(scipy.stats.tstd(filtered))
    filtered_mean = float(scipy.mean(filtered))

    multiple_stdev_below = abs(float(scipy.stats.norm.ppf(rule_parameters.parameters.stdev_multiplier_below/100.0 / 2, 0, 1)))

    threshold_lower = filtered_mean - multiple_stdev_below * filtered_std

    if multiple_stdev_below != None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None:
        passed = rule_parameters.actual_value


    expected_value = filtered_mean
    lower_bound = threshold_lower
    return RuleExecutionResult(passed, expected_value, lower_bound)

```
___
