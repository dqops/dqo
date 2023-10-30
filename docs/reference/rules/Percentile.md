# percentile
___

## **anomaly differencing percentile moving average**
**Full rule name**
```
percentile/anomaly_differencing_percentile_moving_average
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the increments of previous values gathered
 within a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|anomaly_percent|Probability that the current sensor readout will achieve values within the mean according to the distribution of the previous values gathered within the time window. In other words, the inter-quantile range around the mean of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a time window of 90 periods (days, etc.), but at least 30 readouts must exist to run the calculation. You can change the default value by modifying prediction_time_window parameterin Definitions section.|double| ||



**Rule definition YAML**

The rule definition YAML file *percentile/anomaly_differencing_percentile_moving_average.dqorule.yaml* with the time window and rule parameters configuration is shown below.

```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 90
    min_periods_with_readouts: 30
    historic_data_point_grouping: day
  fields:
  - field_name: anomaly_percent
    display_name: anomaly_percent
    help_text: &quot;Probability that the current sensor readout will achieve values within\
      \ the mean according to the distribution of the previous values gathered within\
      \ the time window. In other words, the inter-quantile range around the mean\
      \ of the estimated normal distribution. Set the time window at the threshold\
      \ level for all severity levels (warning, error, fatal) at once. The default\
      \ is a time window of 90 periods (days, etc.), but at least 30 readouts must\
      \ exist to run the calculation. You can change the default value by modifying\
      \ prediction_time_window parameterin Definitions section.&quot;
    data_type: double
    sample_values:
    - 0.1
```



**Rule implementation (Python)**

The code sample below shows the content of the  *percentile/anomaly_differencing_percentile_moving_average.py* file. The *evaluate_rule* function at the bottom evaluates the sensor result and returns the rule evaluation result.

```python
#
# Copyright © 2023 DQOps (support@dqops.com)
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
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class AnomalyDifferencingPercentileMovingAverageRuleParametersSpec:
    anomaly_percent: float


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
    parameters: AnomalyDifferencingPercentileMovingAverageRuleParametersSpec
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

    extracted = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]

    if len(extracted) == 0:
        return RuleExecutionResult()

    filtered = np.array(extracted, dtype=float)
    differences = np.diff(filtered)
    differences_std = float(scipy.stats.tstd(differences))
    differences_mean = float(np.mean(differences))

    last_readout = float(filtered[-1])
    actual_difference = rule_parameters.actual_value - last_readout

    if differences_std == 0:
        threshold_lower = float(differences_mean)
        threshold_upper = float(differences_mean)
    else:
        # Assumption: the historical data follows normal distribution
        readout_distribution = scipy.stats.norm(loc=differences_mean, scale=differences_std)
        one_sided_tail = rule_parameters.parameters.anomaly_percent / 100.0 / 2

        threshold_lower = float(readout_distribution.ppf(one_sided_tail))
        threshold_upper = float(readout_distribution.ppf(1 - one_sided_tail))

    passed = threshold_lower <= actual_difference <= threshold_upper

    expected_value = last_readout + differences_mean
    lower_bound = last_readout + threshold_lower
    upper_bound = last_readout + threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **anomaly differencing percentile moving average 30 days**
**Full rule name**
```
percentile/anomaly_differencing_percentile_moving_average_30_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the increments of previous values gathered
 within a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|anomaly_percent|Probability that the current sensor readout will achieve values within the mean according to the distribution of the previous values gathered within the time window. In other words, the inter-quantile range around the mean of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a time window of 30 periods (days, etc.), but at least 10 readouts must exist to run the calculation.|double| ||



**Rule definition YAML**

The rule definition YAML file *percentile/anomaly_differencing_percentile_moving_average_30_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
  - field_name: anomaly_percent
    display_name: anomaly_percent
    help_text: &quot;Probability that the current sensor readout will achieve values within\
      \ the mean according to the distribution of the previous values gathered within\
      \ the time window. In other words, the inter-quantile range around the mean\
      \ of the estimated normal distribution. Set the time window at the threshold\
      \ level for all severity levels (warning, error, fatal) at once. The default\
      \ is a time window of 30 periods (days, etc.), but at least 10 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
    sample_values:
    - 0.1
```



**Rule implementation (Python)**

The code sample below shows the content of the  *percentile/anomaly_differencing_percentile_moving_average_30_days.py* file. The *evaluate_rule* function at the bottom evaluates the sensor result and returns the rule evaluation result.

```python
#
# Copyright © 2023 DQOps (support@dqops.com)
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
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class AnomalyDifferencingPercentileMovingAverageRuleParametersSpec:
    anomaly_percent: float


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
    parameters: AnomalyDifferencingPercentileMovingAverageRuleParametersSpec
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

    extracted = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]

    if len(extracted) == 0:
        return RuleExecutionResult()

    filtered = np.array(extracted, dtype=float)
    differences = np.diff(filtered)
    differences_std = float(scipy.stats.tstd(differences))
    differences_mean = float(np.mean(differences))

    last_readout = float(filtered[-1])
    actual_difference = rule_parameters.actual_value - last_readout

    if differences_std == 0:
        threshold_lower = float(differences_mean)
        threshold_upper = float(differences_mean)
    else:
        # Assumption: the historical data follows normal distribution
        readout_distribution = scipy.stats.norm(loc=differences_mean, scale=differences_std)
        one_sided_tail = rule_parameters.parameters.anomaly_percent / 100.0 / 2

        threshold_lower = float(readout_distribution.ppf(one_sided_tail))
        threshold_upper = float(readout_distribution.ppf(1 - one_sided_tail))

    passed = threshold_lower <= actual_difference <= threshold_upper

    expected_value = last_readout + differences_mean
    lower_bound = last_readout + threshold_lower
    upper_bound = last_readout + threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **anomaly stationary percentile moving average**
**Full rule name**
```
percentile/anomaly_stationary_percentile_moving_average
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the previous values gathered within a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|anomaly_percent|Probability that the current sensor readout will achieve values within the mean according to the distribution of the previous values gathered within the time window. In other words, the inter-quantile range around the mean of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a time window of 90 periods (days, etc.), but at least 30 readouts must exist to run the calculation. You can change the default value by modifying prediction_time_window parameterin Definitions section.|double| ||



**Rule definition YAML**

The rule definition YAML file *percentile/anomaly_stationary_percentile_moving_average.dqorule.yaml* with the time window and rule parameters configuration is shown below.

```yaml
# yaml-language-server: $schema&#x3D;https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
apiVersion: dqo/v1
kind: rule
spec:
  type: python
  java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
  mode: previous_readouts
  time_window:
    prediction_time_window: 90
    min_periods_with_readouts: 30
    historic_data_point_grouping: day
  fields:
  - field_name: anomaly_percent
    display_name: anomaly_percent
    help_text: &quot;Probability that the current sensor readout will achieve values within\
      \ the mean according to the distribution of the previous values gathered within\
      \ the time window. In other words, the inter-quantile range around the mean\
      \ of the estimated normal distribution. Set the time window at the threshold\
      \ level for all severity levels (warning, error, fatal) at once. The default\
      \ is a time window of 90 periods (days, etc.), but at least 30 readouts must\
      \ exist to run the calculation. You can change the default value by modifying\
      \ prediction_time_window parameterin Definitions section.&quot;
    data_type: double
    sample_values:
    - 0.1
```



**Rule implementation (Python)**

The code sample below shows the content of the  *percentile/anomaly_stationary_percentile_moving_average.py* file. The *evaluate_rule* function at the bottom evaluates the sensor result and returns the rule evaluation result.

```python
#
# Copyright © 2023 DQOps (support@dqops.com)
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
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class AnomalyStationaryPercentileMovingAverageRuleParametersSpec:
    anomaly_percent: float


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
    parameters: AnomalyStationaryPercentileMovingAverageRuleParametersSpec
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

    extracted = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]

    if len(extracted) == 0:
        return RuleExecutionResult()

    filtered = np.array(extracted, dtype=float)
    filtered_std = scipy.stats.tstd(filtered)
    filtered_mean = np.mean(filtered)

    if filtered_std == 0:
        threshold_lower = float(filtered_mean)
        threshold_upper = float(filtered_mean)
    else:
        # Assumption: the historical data follows normal distribution
        readout_distribution = scipy.stats.norm(loc=filtered_mean, scale=filtered_std)
        one_sided_tail = rule_parameters.parameters.anomaly_percent / 100.0 / 2

        threshold_lower = float(readout_distribution.ppf(one_sided_tail))
        threshold_upper = float(readout_distribution.ppf(1 - one_sided_tail))

    passed = threshold_lower <= rule_parameters.actual_value <= threshold_upper

    expected_value = float(filtered_mean)
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **anomaly stationary percentile moving average 30 days**
**Full rule name**
```
percentile/anomaly_stationary_percentile_moving_average_30_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the previous values gathered within a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|anomaly_percent|Probability that the current sensor readout will achieve values within the mean according to the distribution of the previous values gathered within the time window. In other words, the inter-quantile range around the mean of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 30 time periods (days, etc.) time window, but at least 10 readouts must exist to run the calculation.|double| ||



**Rule definition YAML**

The rule definition YAML file *percentile/anomaly_stationary_percentile_moving_average_30_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
  - field_name: anomaly_percent
    display_name: anomaly_percent
    help_text: &quot;Probability that the current sensor readout will achieve values within\
      \ the mean according to the distribution of the previous values gathered within\
      \ the time window. In other words, the inter-quantile range around the mean\
      \ of the estimated normal distribution. Set the time window at the threshold\
      \ level for all severity levels (warning, error, fatal) at once. The default\
      \ is a 30 time periods (days, etc.) time window, but at least 10 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
    sample_values:
    - 0.1
```



**Rule implementation (Python)**

The code sample below shows the content of the  *percentile/anomaly_stationary_percentile_moving_average_30_days.py* file. The *evaluate_rule* function at the bottom evaluates the sensor result and returns the rule evaluation result.

```python
#
# Copyright © 2023 DQOps (support@dqops.com)
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
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class AnomalyStationaryPercentileMovingAverageRuleParametersSpec:
    anomaly_percent: float


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
    parameters: AnomalyStationaryPercentileMovingAverageRuleParametersSpec
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

    extracted = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]

    if len(extracted) == 0:
        return RuleExecutionResult()

    filtered = np.array(extracted, dtype=float)
    filtered_std = scipy.stats.tstd(filtered)
    filtered_mean = np.mean(filtered)

    if filtered_std == 0:
        threshold_lower = float(filtered_mean)
        threshold_upper = float(filtered_mean)
    else:
        # Assumption: the historical data follows normal distribution
        readout_distribution = scipy.stats.norm(loc=filtered_mean, scale=filtered_std)
        one_sided_tail = rule_parameters.parameters.anomaly_percent / 100.0 / 2

        threshold_lower = float(readout_distribution.ppf(one_sided_tail))
        threshold_upper = float(readout_distribution.ppf(1 - one_sided_tail))

    passed = threshold_lower <= rule_parameters.actual_value <= threshold_upper

    expected_value = float(filtered_mean)
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **change percentile moving 30 days**
**Full rule name**
```
percentile/change_percentile_moving_30_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the increments of previous values gathered
 within a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|percentile_above|Probability that the current sensor readout will achieve values greater than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the upper quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 30 time periods (days, etc.) time window, but at least 10 readouts must exist to run the calculation.|double| ||
|percentile_below|Probability that the current sensor readout will achieve values lesser than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the lower quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 30 time periods (days, etc.) time window, but at least 10 readouts must exist to run the calculation.|double| ||



**Rule definition YAML**

The rule definition YAML file *percentile/change_percentile_moving_30_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
  - field_name: percentile_above
    display_name: percentile_above
    help_text: &quot;Probability that the current sensor readout will achieve values greater\
      \ than it would be expected from the estimated distribution based on the previous\
      \ values gathered within the time window. In other words, the upper quantile\
      \ of the estimated normal distribution. Set the time window at the threshold\
      \ level for all severity levels (warning, error, fatal) at once. The default\
      \ is a 30 time periods (days, etc.) time window, but at least 10 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
    sample_values:
    - 5
  - field_name: percentile_below
    display_name: percentile_below
    help_text: &quot;Probability that the current sensor readout will achieve values lesser\
      \ than it would be expected from the estimated distribution based on the previous\
      \ values gathered within the time window. In other words, the lower quantile\
      \ of the estimated normal distribution. Set the time window at the threshold\
      \ level for all severity levels (warning, error, fatal) at once. The default\
      \ is a 30 time periods (days, etc.) time window, but at least 10 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
    sample_values:
    - 5
```



**Rule implementation (Python)**

The code sample below shows the content of the  *percentile/change_percentile_moving_30_days.py* file. The *evaluate_rule* function at the bottom evaluates the sensor result and returns the rule evaluation result.

```python
#
# Copyright © 2023 DQOps (support@dqops.com)
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
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class PercentileMovingRuleParametersSpec:
    percentile_above: float
    percentile_below: float


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
    parameters: PercentileMovingRuleParametersSpec
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

    extracted = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]

    if len(extracted) == 0:
        return RuleExecutionResult()

    filtered = np.array(extracted, dtype=float)
    differences = np.diff(filtered)
    differences_std = float(scipy.stats.tstd(differences))
    differences_mean = float(np.mean(differences))

    last_readout = float(filtered[-1])
    actual_difference = rule_parameters.actual_value - last_readout

    if differences_std == 0:
        threshold_lower = float(differences_mean) if rule_parameters.parameters.percentile_below is not None else None
        threshold_upper = float(differences_mean) if rule_parameters.parameters.percentile_above is not None else None
    else:
        # Assumption: the change rate in historical data follows normal distribution
        readout_distribution = scipy.stats.norm(loc=differences_mean, scale=differences_std)

        threshold_lower = float(readout_distribution.ppf(rule_parameters.parameters.percentile_below / 100.0)) \
            if rule_parameters.parameters.percentile_below is not None else None
        threshold_upper = float(readout_distribution.ppf(1 - (rule_parameters.parameters.percentile_above / 100.0))) \
            if rule_parameters.parameters.percentile_above is not None else None

    if threshold_lower is not None and threshold_upper is not None:
        passed = threshold_lower <= actual_difference <= threshold_upper
    elif threshold_upper is not None:
        passed = actual_difference <= threshold_upper
    elif threshold_lower is not None:
        passed = threshold_lower <= actual_difference
    else:
        raise ValueError("At least one threshold is required.")

    expected_value = last_readout + differences_mean
    lower_bound = last_readout + threshold_lower if threshold_lower is not None else None
    upper_bound = last_readout + threshold_upper if threshold_upper is not None else None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
```
___

## **change percentile moving 60 days**
**Full rule name**
```
percentile/change_percentile_moving_60_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the increments of previous values gathered
 within a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|percentile_above|Probability that the current sensor readout will achieve values greater than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the upper quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 60 time periods (days, etc.) time window, but at least 20 readouts must exist to run the calculation.|double| ||
|percentile_below|Probability that the current sensor readout will achieve values lesser than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the lower quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 60 time periods (days, etc.) time window, but at least 20 readouts must exist to run the calculation.|double| ||



**Rule definition YAML**

The rule definition YAML file *percentile/change_percentile_moving_60_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
  - field_name: percentile_above
    display_name: percentile_above
    help_text: &quot;Probability that the current sensor readout will achieve values greater\
      \ than it would be expected from the estimated distribution based on the previous\
      \ values gathered within the time window. In other words, the upper quantile\
      \ of the estimated normal distribution. Set the time window at the threshold\
      \ level for all severity levels (warning, error, fatal) at once. The default\
      \ is a 60 time periods (days, etc.) time window, but at least 20 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
    sample_values:
    - 5
  - field_name: percentile_below
    display_name: percentile_below
    help_text: &quot;Probability that the current sensor readout will achieve values lesser\
      \ than it would be expected from the estimated distribution based on the previous\
      \ values gathered within the time window. In other words, the lower quantile\
      \ of the estimated normal distribution. Set the time window at the threshold\
      \ level for all severity levels (warning, error, fatal) at once. The default\
      \ is a 60 time periods (days, etc.) time window, but at least 20 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
    sample_values:
    - 5
```



**Rule implementation (Python)**

The code sample below shows the content of the  *percentile/change_percentile_moving_60_days.py* file. The *evaluate_rule* function at the bottom evaluates the sensor result and returns the rule evaluation result.

```python
#
# Copyright © 2023 DQOps (support@dqops.com)
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
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class PercentileMovingRuleParametersSpec:
    percentile_above: float
    percentile_below: float


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
    parameters: PercentileMovingRuleParametersSpec
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

    extracted = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]

    if len(extracted) == 0:
        return RuleExecutionResult()

    filtered = np.array(extracted, dtype=float)
    differences = np.diff(filtered)
    differences_std = float(scipy.stats.tstd(differences))
    differences_mean = float(np.mean(differences))

    last_readout = float(filtered[-1])
    actual_difference = rule_parameters.actual_value - last_readout

    if differences_std == 0:
        threshold_lower = float(differences_mean) if rule_parameters.parameters.percentile_below is not None else None
        threshold_upper = float(differences_mean) if rule_parameters.parameters.percentile_above is not None else None
    else:
        # Assumption: the change rate in historical data follows normal distribution
        readout_distribution = scipy.stats.norm(loc=differences_mean, scale=differences_std)

        threshold_lower = float(readout_distribution.ppf(rule_parameters.parameters.percentile_below / 100.0)) \
            if rule_parameters.parameters.percentile_below is not None else None
        threshold_upper = float(readout_distribution.ppf(1 - (rule_parameters.parameters.percentile_above / 100.0))) \
            if rule_parameters.parameters.percentile_above is not None else None

    if threshold_lower is not None and threshold_upper is not None:
        passed = threshold_lower <= actual_difference <= threshold_upper
    elif threshold_upper is not None:
        passed = actual_difference <= threshold_upper
    elif threshold_lower is not None:
        passed = threshold_lower <= actual_difference
    else:
        raise ValueError("At least one threshold is required.")

    expected_value = last_readout + differences_mean
    lower_bound = last_readout + threshold_lower if threshold_lower is not None else None
    upper_bound = last_readout + threshold_upper if threshold_upper is not None else None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
```
___

## **change percentile moving 7 days**
**Full rule name**
```
percentile/change_percentile_moving_7_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the increments of previous values gathered
 within a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|percentile_above|Probability that the current sensor readout will achieve values greater than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the upper quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 7 time periods (days, etc.) time window, but at least 3 readouts must exist to run the calculation.|double| ||
|percentile_below|Probability that the current sensor readout will achieve values lesser than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the lower quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 7 time periods (days, etc.) time window, but at least 3 readouts must exist to run the calculation.|double| ||



**Rule definition YAML**

The rule definition YAML file *percentile/change_percentile_moving_7_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
  - field_name: percentile_above
    display_name: percentile_above
    help_text: &quot;Probability that the current sensor readout will achieve values greater\
      \ than it would be expected from the estimated distribution based on the previous\
      \ values gathered within the time window. In other words, the upper quantile\
      \ of the estimated normal distribution. Set the time window at the threshold\
      \ level for all severity levels (warning, error, fatal) at once. The default\
      \ is a 7 time periods (days, etc.) time window, but at least 3 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
    sample_values:
    - 5
  - field_name: percentile_below
    display_name: percentile_below
    help_text: &quot;Probability that the current sensor readout will achieve values lesser\
      \ than it would be expected from the estimated distribution based on the previous\
      \ values gathered within the time window. In other words, the lower quantile\
      \ of the estimated normal distribution. Set the time window at the threshold\
      \ level for all severity levels (warning, error, fatal) at once. The default\
      \ is a 7 time periods (days, etc.) time window, but at least 3 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
    sample_values:
    - 5
```



**Rule implementation (Python)**

The code sample below shows the content of the  *percentile/change_percentile_moving_7_days.py* file. The *evaluate_rule* function at the bottom evaluates the sensor result and returns the rule evaluation result.

```python
#
# Copyright © 2023 DQOps (support@dqops.com)
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
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class PercentileMovingRuleParametersSpec:
    percentile_above: float
    percentile_below: float


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
    parameters: PercentileMovingRuleParametersSpec
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

    extracted = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]

    if len(extracted) == 0:
        return RuleExecutionResult()

    filtered = np.array(extracted, dtype=float)
    differences = np.diff(filtered)
    differences_std = float(scipy.stats.tstd(differences))
    differences_mean = float(np.mean(differences))

    last_readout = float(filtered[-1])
    actual_difference = rule_parameters.actual_value - last_readout

    if differences_std == 0:
        threshold_lower = float(differences_mean) if rule_parameters.parameters.percentile_below is not None else None
        threshold_upper = float(differences_mean) if rule_parameters.parameters.percentile_above is not None else None
    else:
        # Assumption: the change rate in historical data follows normal distribution
        readout_distribution = scipy.stats.norm(loc=differences_mean, scale=differences_std)

        threshold_lower = float(readout_distribution.ppf(rule_parameters.parameters.percentile_below / 100.0)) \
            if rule_parameters.parameters.percentile_below is not None else None
        threshold_upper = float(readout_distribution.ppf(1 - (rule_parameters.parameters.percentile_above / 100.0))) \
            if rule_parameters.parameters.percentile_above is not None else None

    if threshold_lower is not None and threshold_upper is not None:
        passed = threshold_lower <= actual_difference <= threshold_upper
    elif threshold_upper is not None:
        passed = actual_difference <= threshold_upper
    elif threshold_lower is not None:
        passed = threshold_lower <= actual_difference
    else:
        raise ValueError("At least one threshold is required.")

    expected_value = last_readout + differences_mean
    lower_bound = last_readout + threshold_lower if threshold_lower is not None else None
    upper_bound = last_readout + threshold_upper if threshold_upper is not None else None
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
```
___

## **percentile moving 30 days**
**Full rule name**
```
percentile/percentile_moving_30_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the previous values gathered within a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|percentile_above|Probability that the current sensor readout will achieve values greater than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the upper quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 30 time periods (days, etc.) time window, but at least 10 readouts must exist to run the calculation.|double| ||
|percentile_below|Probability that the current sensor readout will achieve values lesser than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the lower quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 30 time periods (days, etc.) time window, but at least 10 readouts must exist to run the calculation.|double| ||



**Rule definition YAML**

The rule definition YAML file *percentile/percentile_moving_30_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
  - field_name: percentile_above
    display_name: percentile_above
    help_text: &quot;Probability that the current sensor readout will achieve values greater\
      \ than it would be expected from the estimated distribution based on the previous\
      \ values gathered within the time window. In other words, the upper quantile\
      \ of the estimated normal distribution. Set the time window at the threshold\
      \ level for all severity levels (warning, error, fatal) at once. The default\
      \ is a 30 time periods (days, etc.) time window, but at least 10 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
    sample_values:
    - 5
  - field_name: percentile_below
    display_name: percentile_below
    help_text: &quot;Probability that the current sensor readout will achieve values lesser\
      \ than it would be expected from the estimated distribution based on the previous\
      \ values gathered within the time window. In other words, the lower quantile\
      \ of the estimated normal distribution. Set the time window at the threshold\
      \ level for all severity levels (warning, error, fatal) at once. The default\
      \ is a 30 time periods (days, etc.) time window, but at least 10 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
    sample_values:
    - 5
```



**Rule implementation (Python)**

The code sample below shows the content of the  *percentile/percentile_moving_30_days.py* file. The *evaluate_rule* function at the bottom evaluates the sensor result and returns the rule evaluation result.

```python
#
# Copyright © 2023 DQOps (support@dqops.com)
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
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class PercentileMovingRuleParametersSpec:
    percentile_above: float
    percentile_below: float


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
    parameters: PercentileMovingRuleParametersSpec
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

    extracted = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]

    if len(extracted) == 0:
        return RuleExecutionResult()

    filtered = np.array(extracted, dtype=float)
    filtered_std = scipy.stats.tstd(filtered)
    filtered_mean = np.mean(filtered)

    if filtered_std == 0:
        threshold_lower = float(filtered_mean) if rule_parameters.parameters.percentile_below is not None else None
        threshold_upper = float(filtered_mean) if rule_parameters.parameters.percentile_above is not None else None
    else:
        # Assumption: the historical data follows normal distribution
        readout_distribution = scipy.stats.norm(loc=filtered_mean, scale=filtered_std)

        threshold_lower = float(readout_distribution.ppf(rule_parameters.parameters.percentile_below / 100.0)) \
            if rule_parameters.parameters.percentile_below is not None else None
        threshold_upper = float(readout_distribution.ppf(1 - (rule_parameters.parameters.percentile_above / 100.0))) \
            if rule_parameters.parameters.percentile_above is not None else None

    if threshold_lower is not None and threshold_upper is not None:
        passed = threshold_lower <= rule_parameters.actual_value <= threshold_upper
    elif threshold_upper is not None:
        passed = rule_parameters.actual_value <= threshold_upper
    elif threshold_lower is not None:
        passed = threshold_lower <= rule_parameters.actual_value
    else:
        raise ValueError("At least one threshold is required.")

    expected_value = float(filtered_mean)
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **percentile moving 60 days**
**Full rule name**
```
percentile/percentile_moving_60_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the previous values gathered within a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|percentile_above|Probability that the current sensor readout will achieve values greater than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the upper quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 60 time periods (days, etc.) time window, but at least 20 readouts must exist to run the calculation.|double| ||
|percentile_below|Probability that the current sensor readout will achieve values lesser than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the lower quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 60 time periods (days, etc.) time window, but at least 20 readouts must exist to run the calculation.|double| ||



**Rule definition YAML**

The rule definition YAML file *percentile/percentile_moving_60_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
  - field_name: percentile_above
    display_name: percentile_above
    help_text: &quot;Probability that the current sensor readout will achieve values greater\
      \ than it would be expected from the estimated distribution based on the previous\
      \ values gathered within the time window. In other words, the upper quantile\
      \ of the estimated normal distribution. Set the time window at the threshold\
      \ level for all severity levels (warning, error, fatal) at once. The default\
      \ is a 60 time periods (days, etc.) time window, but at least 20 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
    sample_values:
    - 5
  - field_name: percentile_below
    display_name: percentile_below
    help_text: &quot;Probability that the current sensor readout will achieve values lesser\
      \ than it would be expected from the estimated distribution based on the previous\
      \ values gathered within the time window. In other words, the lower quantile\
      \ of the estimated normal distribution. Set the time window at the threshold\
      \ level for all severity levels (warning, error, fatal) at once. The default\
      \ is a 60 time periods (days, etc.) time window, but at least 20 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
    sample_values:
    - 5
```



**Rule implementation (Python)**

The code sample below shows the content of the  *percentile/percentile_moving_60_days.py* file. The *evaluate_rule* function at the bottom evaluates the sensor result and returns the rule evaluation result.

```python
#
# Copyright © 2023 DQOps (support@dqops.com)
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
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class PercentileMovingRuleParametersSpec:
    percentile_above: float
    percentile_below: float


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
    parameters: PercentileMovingRuleParametersSpec
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

    extracted = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]

    if len(extracted) == 0:
        return RuleExecutionResult()

    filtered = np.array(extracted, dtype=float)
    filtered_std = scipy.stats.tstd(filtered)
    filtered_mean = np.mean(filtered)

    if filtered_std == 0:
        threshold_lower = float(filtered_mean) if rule_parameters.parameters.percentile_below is not None else None
        threshold_upper = float(filtered_mean) if rule_parameters.parameters.percentile_above is not None else None
    else:
        # Assumption: the historical data follows normal distribution
        readout_distribution = scipy.stats.norm(loc=filtered_mean, scale=filtered_std)

        threshold_lower = float(readout_distribution.ppf(rule_parameters.parameters.percentile_below / 100.0)) \
            if rule_parameters.parameters.percentile_below is not None else None
        threshold_upper = float(readout_distribution.ppf(1 - (rule_parameters.parameters.percentile_above / 100.0))) \
            if rule_parameters.parameters.percentile_above is not None else None

    if threshold_lower is not None and threshold_upper is not None:
        passed = threshold_lower <= rule_parameters.actual_value <= threshold_upper
    elif threshold_upper is not None:
        passed = rule_parameters.actual_value <= threshold_upper
    elif threshold_lower is not None:
        passed = threshold_lower <= rule_parameters.actual_value
    else:
        raise ValueError("At least one threshold is required.")

    expected_value = float(filtered_mean)
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___

## **percentile moving 7 days**
**Full rule name**
```
percentile/percentile_moving_7_days
```
**Description**  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the previous values gathered within a time window.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|percentile_above|Probability that the current sensor readout will achieve values greater than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the upper quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 7 time periods (days, etc.) time window, but at least 3 readouts must exist to run the calculation.|double| ||
|percentile_below|Probability that the current sensor readout will achieve values lesser than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the lower quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 7 time periods (days, etc.) time window, but at least 3 readouts must exist to run the calculation.|double| ||



**Rule definition YAML**

The rule definition YAML file *percentile/percentile_moving_7_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
  - field_name: percentile_above
    display_name: percentile_above
    help_text: &quot;Probability that the current sensor readout will achieve values greater\
      \ than it would be expected from the estimated distribution based on the previous\
      \ values gathered within the time window. In other words, the upper quantile\
      \ of the estimated normal distribution. Set the time window at the threshold\
      \ level for all severity levels (warning, error, fatal) at once. The default\
      \ is a 7 time periods (days, etc.) time window, but at least 3 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
    sample_values:
    - 5
  - field_name: percentile_below
    display_name: percentile_below
    help_text: &quot;Probability that the current sensor readout will achieve values lesser\
      \ than it would be expected from the estimated distribution based on the previous\
      \ values gathered within the time window. In other words, the lower quantile\
      \ of the estimated normal distribution. Set the time window at the threshold\
      \ level for all severity levels (warning, error, fatal) at once. The default\
      \ is a 7 time periods (days, etc.) time window, but at least 3 readouts must\
      \ exist to run the calculation.&quot;
    data_type: double
    sample_values:
    - 5
```



**Rule implementation (Python)**

The code sample below shows the content of the  *percentile/percentile_moving_7_days.py* file. The *evaluate_rule* function at the bottom evaluates the sensor result and returns the rule evaluation result.

```python
#
# Copyright © 2023 DQOps (support@dqops.com)
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
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class PercentileMovingRuleParametersSpec:
    percentile_above: float
    percentile_below: float


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
    parameters: PercentileMovingRuleParametersSpec
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

    extracted = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]

    if len(extracted) == 0:
        return RuleExecutionResult()

    filtered = np.array(extracted, dtype=float)
    filtered_std = scipy.stats.tstd(filtered)
    filtered_mean = np.mean(filtered)

    if filtered_std == 0:
        threshold_lower = float(filtered_mean) if rule_parameters.parameters.percentile_below is not None else None
        threshold_upper = float(filtered_mean) if rule_parameters.parameters.percentile_above is not None else None
    else:
        # Assumption: the historical data follows normal distribution
        readout_distribution = scipy.stats.norm(loc=filtered_mean, scale=filtered_std)

        threshold_lower = float(readout_distribution.ppf(rule_parameters.parameters.percentile_below / 100.0)) \
            if rule_parameters.parameters.percentile_below is not None else None
        threshold_upper = float(readout_distribution.ppf(1 - (rule_parameters.parameters.percentile_above / 100.0))) \
            if rule_parameters.parameters.percentile_above is not None else None

    if threshold_lower is not None and threshold_upper is not None:
        passed = threshold_lower <= rule_parameters.actual_value <= threshold_upper
    elif threshold_upper is not None:
        passed = rule_parameters.actual_value <= threshold_upper
    elif threshold_lower is not None:
        passed = threshold_lower <= rule_parameters.actual_value
    else:
        raise ValueError("At least one threshold is required.")

    expected_value = float(filtered_mean)
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

```
___
