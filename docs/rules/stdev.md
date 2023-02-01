# stdev
___

## **percentile moving stdev**
**Full rule name**
```
stdev/percentile_moving_stdev
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|percentile_stdev_above|Maximum percent (e.q. 3%) that the current sensor readout could be above a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|double|||
|percentile_stdev_below|Maximum percent (e.q. 3%) that the current sensorreadout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|double|||


**Example**
```yaml
--8<-- "home/rules/stdev/percentile_moving_stdev.dqrule.yaml"
```
**Rule implementation (Python)**
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

## **below percent population stdev 60 days**
**Full rule name**
```
stdev/below_percent_population_stdev_60_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|percent_population_below|Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 60 readouts must exist to run the calculation.|double|||


**Example**
```yaml
--8<-- "home/rules/stdev/below_percent_population_stdev_60_days.dqrule.yaml"
```
**Rule implementation (Python)**
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

## **below percent population stdev 60 days**
**Full rule name**
```
stdev/below_percent_population_stdev_60_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|percent_population_below|Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 60 readouts must exist to run the calculation.|double|||


**Example**
```yaml
--8<-- "home/rules/stdev/below_percent_population_stdev_60_days.dqrule.yaml"
```
**Rule implementation (Python)**
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

## **below stdev multiply 60 days**
**Full rule name**
```
stdev/below_stdev_multiply_60_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|stdev_multiplier_below|Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 60 readouts must exist to run the calculation.|double|||


**Example**
```yaml
--8<-- "home/rules/stdev/below_stdev_multiply_60_days.dqrule.yaml"
```
**Rule implementation (Python)**
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

## **below percent population stdev 60 days**
**Full rule name**
```
stdev/below_percent_population_stdev_60_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|percent_population_below|Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 60 readouts must exist to run the calculation.|double|||


**Example**
```yaml
--8<-- "home/rules/stdev/below_percent_population_stdev_60_days.dqrule.yaml"
```
**Rule implementation (Python)**
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

## **below stdev multiply 60 days**
**Full rule name**
```
stdev/below_stdev_multiply_60_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|stdev_multiplier_below|Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 60 readouts must exist to run the calculation.|double|||


**Example**
```yaml
--8<-- "home/rules/stdev/below_stdev_multiply_60_days.dqrule.yaml"
```
**Rule implementation (Python)**
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

## **below stdev multiply 30 days**
**Full rule name**
```
stdev/below_stdev_multiply_30_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|stdev_multiplier_below|Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 30 readouts must exist to run the calculation.|double|||


**Example**
```yaml
--8<-- "home/rules/stdev/below_stdev_multiply_30_days.dqrule.yaml"
```
**Rule implementation (Python)**
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

## **below stdev multiply 60 days**
**Full rule name**
```
stdev/below_stdev_multiply_60_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|stdev_multiplier_below|Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 60 readouts must exist to run the calculation.|double|||


**Example**
```yaml
--8<-- "home/rules/stdev/below_stdev_multiply_60_days.dqrule.yaml"
```
**Rule implementation (Python)**
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

## **below stdev multiply 7 days**
**Full rule name**
```
stdev/below_stdev_multiply_7_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|stdev_multiplier_below|Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|double|||


**Example**
```yaml
--8<-- "home/rules/stdev/below_stdev_multiply_7_days.dqrule.yaml"
```
**Rule implementation (Python)**
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

## **below stdev multiply 7 days**
**Full rule name**
```
stdev/below_stdev_multiply_7_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|stdev_multiplier_below|Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|double|||


**Example**
```yaml
--8<-- "home/rules/stdev/below_stdev_multiply_7_days.dqrule.yaml"
```
**Rule implementation (Python)**
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

## **below percent population stdev 30 days**
**Full rule name**
```
stdev/below_percent_population_stdev_30_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|percent_population_below|Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, Fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 30 readout must exist to run the calculation.|double|||


**Example**
```yaml
--8<-- "home/rules/stdev/below_percent_population_stdev_30_days.dqrule.yaml"
```
**Rule implementation (Python)**
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

## **below percent population stdev 30 days**
**Full rule name**
```
stdev/below_percent_population_stdev_30_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|percent_population_below|Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, Fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 30 readout must exist to run the calculation.|double|||


**Example**
```yaml
--8<-- "home/rules/stdev/below_percent_population_stdev_30_days.dqrule.yaml"
```
**Rule implementation (Python)**
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

## **percent moving stdev**
**Full rule name**
```
stdev/percent_moving_stdev
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|multiple_stdev_above|Maximum percent (e.q. 3%) that the current sensor readout could be above a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|double|||
|multiple_stdev_below|Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|double|||


**Example**
```yaml
--8<-- "home/rules/stdev/percent_moving_stdev.dqrule.yaml"
```
**Rule implementation (Python)**
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

## **below percent population stdev 7 days**
**Full rule name**
```
stdev/below_percent_population_stdev_7_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|percent_population_below|Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|double|||


**Example**
```yaml
--8<-- "home/rules/stdev/below_percent_population_stdev_7_days.dqrule.yaml"
```
**Rule implementation (Python)**
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

## **below stdev multiply 30 days**
**Full rule name**
```
stdev/below_stdev_multiply_30_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|stdev_multiplier_below|Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 30 readouts must exist to run the calculation.|double|||


**Example**
```yaml
--8<-- "home/rules/stdev/below_stdev_multiply_30_days.dqrule.yaml"
```
**Rule implementation (Python)**
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

## **below percent population stdev 7 days**
**Full rule name**
```
stdev/below_percent_population_stdev_7_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|percent_population_below|Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|double|||


**Example**
```yaml
--8<-- "home/rules/stdev/below_percent_population_stdev_7_days.dqrule.yaml"
```
**Rule implementation (Python)**
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

## **below percent population stdev 7 days**
**Full rule name**
```
stdev/below_percent_population_stdev_7_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|percent_population_below|Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|double|||


**Example**
```yaml
--8<-- "home/rules/stdev/below_percent_population_stdev_7_days.dqrule.yaml"
```
**Rule implementation (Python)**
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

## **below stdev multiply 30 days**
**Full rule name**
```
stdev/below_stdev_multiply_30_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|stdev_multiplier_below|Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 30 readouts must exist to run the calculation.|double|||


**Example**
```yaml
--8<-- "home/rules/stdev/below_stdev_multiply_30_days.dqrule.yaml"
```
**Rule implementation (Python)**
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

## **below percent population stdev 30 days**
**Full rule name**
```
stdev/below_percent_population_stdev_30_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|percent_population_below|Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, Fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 30 readout must exist to run the calculation.|double|||


**Example**
```yaml
--8<-- "home/rules/stdev/below_percent_population_stdev_30_days.dqrule.yaml"
```
**Rule implementation (Python)**
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

## **below stdev multiply 7 days**
**Full rule name**
```
stdev/below_stdev_multiply_7_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|stdev_multiplier_below|Maximum percent (e.q. 3%) that the current sensor readout could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|double|||


**Example**
```yaml
--8<-- "home/rules/stdev/below_stdev_multiply_7_days.dqrule.yaml"
```
**Rule implementation (Python)**
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
