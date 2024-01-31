# Data quality stdev rules
The list of stdev [data quality rules](../../dqo-concepts/definition-of-data-quality-rules.md) supported by DQOps. The source code is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/stdev* folder in the DQOps distribution.


---

## change multiply moving stdev 30 days
Data quality rule that verifies if a data quality sensor readout value
 doesn&#x27;t excessively deviate from the moving average of increments on a time window.

**Rule summary**

The change multiply moving stdev 30 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| stdev | <span class="no-wrap-code">`stdev/change_multiply_moving_stdev_30_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/change_multiply_moving_stdev_30_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/change_multiply_moving_stdev_30_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`multiply_stdev_above`</span>|How many multiples of the estimated standard deviation can the current sensor readout be above the moving average within the time window? Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 30 time periods (days, etc.) time window, but at least 10 readouts must exist to run the calculation.|*double*| ||
|<span class="no-wrap-code">`multiply_stdev_below`</span>|How many multiples of the estimated standard deviation can the current sensor readout be below the moving average within the time window? Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 30 time periods (days, etc.) time window, but at least 10 readouts must exist to run the calculation.|*double*| ||




**Rule definition YAML**

The rule definition YAML file *stdev/change_multiply_moving_stdev_30_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
      - field_name: multiply_stdev_above
        display_name: multiply_stdev_above
        help_text: "How many multiples of the estimated standard deviation the current\
          \ sensor readout could be above the moving average within the time window. Set\
          \ the time window at the threshold level for all severity levels (warning, error,\
          \ fatal) at once. The default is a 30 time periods (days, etc.) time window,\
          \ but at least 10 readouts must exist to run the calculation."
        data_type: double
      - field_name: multiply_stdev_below
        display_name: multiply_stdev_below
        help_text: "How many multiples of the estimated standard deviation the current\
          \ sensor readout could be below the moving average within the time window. Set\
          \ the time window at the threshold level for all severity levels (warning, error,\
          \ fatal) at once. The default is a 30 time periods (days, etc.) time window,\
          \ but at least 10 readouts must exist to run the calculation."
        data_type: double
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *stdev/change_multiply_moving_stdev_30_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/stdev/change_multiply_moving_stdev_30_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    class MultiplyMovingStdevRuleParametersSpec:
        multiply_stdev_above: float
        multiply_stdev_below: float
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readout: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: MultiplyMovingStdevRuleParametersSpec
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
    
        threshold_lower = differences_mean - rule_parameters.parameters.multiply_stdev_below * differences_std \
            if rule_parameters.parameters.multiply_stdev_below is not None else None
        threshold_upper = differences_mean + rule_parameters.parameters.multiply_stdev_above * differences_std \
            if rule_parameters.parameters.multiply_stdev_above is not None else None
    
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



---

## change multiply moving stdev 60 days
Data quality rule that verifies if a data quality sensor readout value
 doesn&#x27;t excessively deviate from the moving average of increments on a time window.

**Rule summary**

The change multiply moving stdev 60 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| stdev | <span class="no-wrap-code">`stdev/change_multiply_moving_stdev_60_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/change_multiply_moving_stdev_60_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/change_multiply_moving_stdev_60_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`multiply_stdev_above`</span>|How many multiples of the estimated standard deviation can the current sensor readout be above the moving average within the time window? Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 60 time periods (days, etc.) time window, but at least 20 readouts must exist to run the calculation.|*double*| ||
|<span class="no-wrap-code">`multiply_stdev_below`</span>|How many multiples of the estimated standard deviation can the current sensor readout be below the moving average within the time window? Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 60 time periods (days, etc.) time window, but at least 20 readouts must exist to run the calculation.|*double*| ||




**Rule definition YAML**

The rule definition YAML file *stdev/change_multiply_moving_stdev_60_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
      - field_name: multiply_stdev_above
        display_name: multiply_stdev_above
        help_text: "How many multiples of the estimated standard deviation the current\
          \ sensor readout could be above the moving average within the time window. Set\
          \ the time window at the threshold level for all severity levels (warning, error,\
          \ fatal) at once. The default is a 60 time periods (days, etc.) time window,\
          \ but at least 20 readouts must exist to run the calculation."
        data_type: double
      - field_name: multiply_stdev_below
        display_name: multiply_stdev_below
        help_text: "How many multiples of the estimated standard deviation the current\
          \ sensor readout could be below the moving average within the time window. Set\
          \ the time window at the threshold level for all severity levels (warning, error,\
          \ fatal) at once. The default is a 60 time periods (days, etc.) time window,\
          \ but at least 20 readouts must exist to run the calculation."
        data_type: double
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *stdev/change_multiply_moving_stdev_60_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/stdev/change_multiply_moving_stdev_60_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    class MultiplyMovingStdevRuleParametersSpec:
        multiply_stdev_above: float
        multiply_stdev_below: float
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readout: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: MultiplyMovingStdevRuleParametersSpec
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
    
        threshold_lower = differences_mean - rule_parameters.parameters.multiply_stdev_below * differences_std \
            if rule_parameters.parameters.multiply_stdev_below is not None else None
        threshold_upper = differences_mean + rule_parameters.parameters.multiply_stdev_above * differences_std \
            if rule_parameters.parameters.multiply_stdev_above is not None else None
    
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



---

## change multiply moving stdev 7 days
Data quality rule that verifies if a data quality sensor readout value
 doesn&#x27;t excessively deviate from the moving average of increments on a time window.

**Rule summary**

The change multiply moving stdev 7 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| stdev | <span class="no-wrap-code">`stdev/change_multiply_moving_stdev_7_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/change_multiply_moving_stdev_7_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/change_multiply_moving_stdev_7_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`multiply_stdev_above`</span>|How many multiples of the estimated standard deviation can the current sensor readout be above the moving average within the time window? Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 7 time periods (days, etc.) time window, but at least 3 readouts must exist to run the calculation.|*double*| ||
|<span class="no-wrap-code">`multiply_stdev_below`</span>|How many multiples of the estimated standard deviation can the current sensor readout be below the moving average within the time window? Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 7 time periods (days, etc.) time window, but at least 3 readouts must exist to run the calculation.|*double*| ||




**Rule definition YAML**

The rule definition YAML file *stdev/change_multiply_moving_stdev_7_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
      - field_name: multiply_stdev_above
        display_name: multiply_stdev_above
        help_text: "How many multiples of the estimated standard deviation the current\
          \ sensor readout could be above the moving average within the time window. Set\
          \ the time window at the threshold level for all severity levels (warning, error,\
          \ fatal) at once. The default is a 7 time periods (days, etc.) time window,\
          \ but at least 3 readouts must exist to run the calculation."
        data_type: double
      - field_name: multiply_stdev_below
        display_name: multiply_stdev_below
        help_text: "How many multiples of the estimated standard deviation the current\
          \ sensor readout could be below the moving average within the time window. Set\
          \ the time window at the threshold level for all severity levels (warning, error,\
          \ fatal) at once. The default is a 7 time periods (days, etc.) time window,\
          \ but at least 3 readouts must exist to run the calculation."
        data_type: double
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *stdev/change_multiply_moving_stdev_7_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/stdev/change_multiply_moving_stdev_7_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    class MultiplyMovingStdevRuleParametersSpec:
        multiply_stdev_above: float
        multiply_stdev_below: float
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readout: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: MultiplyMovingStdevRuleParametersSpec
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
    
        threshold_lower = differences_mean - rule_parameters.parameters.multiply_stdev_below * differences_std \
            if rule_parameters.parameters.multiply_stdev_below is not None else None
        threshold_upper = differences_mean + rule_parameters.parameters.multiply_stdev_above * differences_std \
            if rule_parameters.parameters.multiply_stdev_above is not None else None
    
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



---

## change multiply moving stdev within 30 days
Data quality rule that verifies if a data quality sensor readout value
 doesn&#x27;t excessively deviate from the moving average of increments on a time window.

**Rule summary**

The change multiply moving stdev within 30 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| stdev | <span class="no-wrap-code">`stdev/change_multiply_moving_stdev_within_30_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/change_multiply_moving_stdev_within_30_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/change_multiply_moving_stdev_within_30_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`multiply_stdev`</span>|How many multiples of the estimated standard deviation within the moving average the current sensor readout could be, with regards to the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 30 time periods (days, etc.) time window, but at least 10 readouts must exist to run the calculation.|*double*|:material-check-bold:||




**Rule definition YAML**

The rule definition YAML file *stdev/change_multiply_moving_stdev_within_30_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
      - field_name: multiply_stdev
        display_name: multiply_stdev
        help_text: "How many multiples of the estimated standard deviation within the\
          \ moving average the current sensor readout could be, with regards to the time\
          \ window. Set the time window at the threshold level for all severity levels\
          \ (warning, error, fatal) at once. The default is a 30 time periods (days, etc.)\
          \ time window, but at least 10 readouts must exist to run the calculation."
        data_type: double
        required: true
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *stdev/change_multiply_moving_stdev_within_30_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/stdev/change_multiply_moving_stdev_within_30_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    class MultiplyMovingStdevWithinRuleParametersSpec:
        multiply_stdev: float
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readout: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: MultiplyMovingStdevWithinRuleParametersSpec
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
    
        stdev_span = rule_parameters.parameters.multiply_stdev * differences_std
        threshold_lower = differences_mean - stdev_span / 2
        threshold_upper = differences_mean + stdev_span / 2
    
        passed = threshold_lower <= actual_difference <= threshold_upper
    
        expected_value = last_readout + differences_mean
        lower_bound = last_readout + threshold_lower
        upper_bound = last_readout + threshold_upper
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## change multiply moving stdev within 60 days
Data quality rule that verifies if a data quality sensor readout value
 doesn&#x27;t excessively deviate from the moving average of increments on a time window.

**Rule summary**

The change multiply moving stdev within 60 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| stdev | <span class="no-wrap-code">`stdev/change_multiply_moving_stdev_within_60_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/change_multiply_moving_stdev_within_60_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/change_multiply_moving_stdev_within_60_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`multiply_stdev`</span>|How many multiples of the estimated standard deviation within the moving average the current sensor readout could be, with regards to the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 60 time periods (days, etc.) time window, but at least 20 readouts must exist to run the calculation.|*double*|:material-check-bold:||




**Rule definition YAML**

The rule definition YAML file *stdev/change_multiply_moving_stdev_within_60_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
      - field_name: multiply_stdev
        display_name: multiply_stdev
        help_text: "How many multiples of the estimated standard deviation within the\
          \ moving average the current sensor readout could be, with regards to the time\
          \ window. Set the time window at the threshold level for all severity levels\
          \ (warning, error, fatal) at once. The default is a 60 time periods (days, etc.)\
          \ time window, but at least 20 readouts must exist to run the calculation."
        data_type: double
        required: true
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *stdev/change_multiply_moving_stdev_within_60_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/stdev/change_multiply_moving_stdev_within_60_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    class MultiplyMovingStdevWithinRuleParametersSpec:
        multiply_stdev: float
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readout: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: MultiplyMovingStdevWithinRuleParametersSpec
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
    
        stdev_span = rule_parameters.parameters.multiply_stdev * differences_std
        threshold_lower = differences_mean - stdev_span / 2
        threshold_upper = differences_mean + stdev_span / 2
    
        passed = threshold_lower <= actual_difference <= threshold_upper
    
        expected_value = last_readout + differences_mean
        lower_bound = last_readout + threshold_lower
        upper_bound = last_readout + threshold_upper
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## change multiply moving stdev within 7 days
Data quality rule that verifies if a data quality sensor readout value
 doesn&#x27;t excessively deviate from the moving average of increments on a time window.

**Rule summary**

The change multiply moving stdev within 7 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| stdev | <span class="no-wrap-code">`stdev/change_multiply_moving_stdev_within_7_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/change_multiply_moving_stdev_within_7_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/change_multiply_moving_stdev_within_7_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`multiply_stdev`</span>|How many multiples of the estimated standard deviation within the moving average the current sensor readout could be, with regards to the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 7 time periods (days, etc.) time window, but at least 3 readouts must exist to run the calculation.|*double*|:material-check-bold:||




**Rule definition YAML**

The rule definition YAML file *stdev/change_multiply_moving_stdev_within_7_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
      - field_name: multiply_stdev
        display_name: multiply_stdev
        help_text: "How many multiples of the estimated standard deviation within the\
          \ moving average the current sensor readout could be, with regards to the time\
          \ window. Set the time window at the threshold level for all severity levels\
          \ (warning, error, fatal) at once. The default is a 7 time periods (days, etc.)\
          \ time window, but at least 3 readouts must exist to run the calculation."
        data_type: double
        required: true
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *stdev/change_multiply_moving_stdev_within_7_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/stdev/change_multiply_moving_stdev_within_7_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    class MultiplyMovingStdevWithinRuleParametersSpec:
        multiply_stdev: float
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readout: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: MultiplyMovingStdevWithinRuleParametersSpec
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
    
        stdev_span = rule_parameters.parameters.multiply_stdev * differences_std
        threshold_lower = differences_mean - stdev_span / 2
        threshold_upper = differences_mean + stdev_span / 2
    
        passed = threshold_lower <= actual_difference <= threshold_upper
    
        expected_value = last_readout + differences_mean
        lower_bound = last_readout + threshold_lower
        upper_bound = last_readout + threshold_upper
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## multiply moving stdev 30 days
Data quality rule that verifies if a data quality sensor readout value
 doesn&#x27;t excessively deviate from the moving average within a time window.

**Rule summary**

The multiply moving stdev 30 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| stdev | <span class="no-wrap-code">`stdev/multiply_moving_stdev_30_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/multiply_moving_stdev_30_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/multiply_moving_stdev_30_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`multiply_stdev_above`</span>|How many multiples of the estimated standard deviation can the current sensor readout be above the moving average within the time window? Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 30 time periods (days, etc.) time window, but at least 10 readouts must exist to run the calculation.|*double*| ||
|<span class="no-wrap-code">`multiply_stdev_below`</span>|How many multiples of the estimated standard deviation can the current sensor readout be below the moving average within the time window? Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 30 time periods (days, etc.) time window, but at least 10 readouts must exist to run the calculation.|*double*| ||




**Rule definition YAML**

The rule definition YAML file *stdev/multiply_moving_stdev_30_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
      - field_name: multiply_stdev_above
        display_name: multiply_stdev_above
        help_text: "How many multiples of the estimated standard deviation the current\
          \ sensor readout could be above the moving average within the time window. Set\
          \ the time window at the threshold level for all severity levels (warning, error,\
          \ fatal) at once. The default is a 30 time periods (days, etc.) time window,\
          \ but at least 10 readouts must exist to run the calculation."
        data_type: double
      - field_name: multiply_stdev_below
        display_name: multiply_stdev_below
        help_text: "How many multiples of the estimated standard deviation the current\
          \ sensor readout could be below the moving average within the time window. Set\
          \ the time window at the threshold level for all severity levels (warning, error,\
          \ fatal) at once. The default is a 30 time periods (days, etc.) time window,\
          \ but at least 10 readouts must exist to run the calculation."
        data_type: double
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *stdev/multiply_moving_stdev_30_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/stdev/multiply_moving_stdev_30_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    class MultiplyMovingStdevRuleParametersSpec:
        multiply_stdev_above: float
        multiply_stdev_below: float
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readout: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: MultiplyMovingStdevRuleParametersSpec
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
        filtered_std = float(scipy.stats.tstd(filtered))
        filtered_mean = float(np.mean(filtered))
    
        threshold_lower = filtered_mean - rule_parameters.parameters.multiply_stdev_below * filtered_std \
            if rule_parameters.parameters.multiply_stdev_below is not None else None
        threshold_upper = filtered_mean + rule_parameters.parameters.multiply_stdev_above * filtered_std \
            if rule_parameters.parameters.multiply_stdev_above is not None else None
    
        if threshold_lower is not None and threshold_upper is not None:
            passed = threshold_lower <= rule_parameters.actual_value <= threshold_upper
        elif threshold_upper is not None:
            passed = rule_parameters.actual_value <= threshold_upper
        elif threshold_lower is not None:
            passed = threshold_lower <= rule_parameters.actual_value
        else:
            raise ValueError("At least one threshold is required.")
    
        expected_value = filtered_mean
        lower_bound = threshold_lower
        upper_bound = threshold_upper
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## multiply moving stdev 60 days
Data quality rule that verifies if a data quality sensor readout value
 doesn&#x27;t excessively deviate from the moving average within a time window.

**Rule summary**

The multiply moving stdev 60 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| stdev | <span class="no-wrap-code">`stdev/multiply_moving_stdev_60_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/multiply_moving_stdev_60_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/multiply_moving_stdev_60_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`multiply_stdev_above`</span>|How many multiples of the estimated standard deviation can the current sensor readout be above the moving average within the time window? Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 60 time periods (days, etc.) time window, but at least 20 readouts must exist to run the calculation.|*double*| ||
|<span class="no-wrap-code">`multiply_stdev_below`</span>|How many multiples of the estimated standard deviation can the current sensor readout be below the moving average within the time window? Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 60 time periods (days, etc.) time window, but at least 20 readouts must exist to run the calculation.|*double*| ||




**Rule definition YAML**

The rule definition YAML file *stdev/multiply_moving_stdev_60_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
      - field_name: multiply_stdev_above
        display_name: multiply_stdev_above
        help_text: "How many multiples of the estimated standard deviation the current\
          \ sensor readout could be above the moving average within the time window. Set\
          \ the time window at the threshold level for all severity levels (warning, error,\
          \ fatal) at once. The default is a 60 time periods (days, etc.) time window,\
          \ but at least 20 readouts must exist to run the calculation."
        data_type: double
      - field_name: multiply_stdev_below
        display_name: multiply_stdev_below
        help_text: "How many multiples of the estimated standard deviation the current\
          \ sensor readout could be below the moving average within the time window. Set\
          \ the time window at the threshold level for all severity levels (warning, error,\
          \ fatal) at once. The default is a 60 time periods (days, etc.) time window,\
          \ but at least 20 readouts must exist to run the calculation."
        data_type: double
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *stdev/multiply_moving_stdev_60_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/stdev/multiply_moving_stdev_60_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    class MultiplyMovingStdevRuleParametersSpec:
        multiply_stdev_above: float
        multiply_stdev_below: float
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readout: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: MultiplyMovingStdevRuleParametersSpec
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
        filtered_std = float(scipy.stats.tstd(filtered))
        filtered_mean = float(np.mean(filtered))
    
        threshold_lower = filtered_mean - rule_parameters.parameters.multiply_stdev_below * filtered_std \
            if rule_parameters.parameters.multiply_stdev_below is not None else None
        threshold_upper = filtered_mean + rule_parameters.parameters.multiply_stdev_above * filtered_std \
            if rule_parameters.parameters.multiply_stdev_above is not None else None
    
        if threshold_lower is not None and threshold_upper is not None:
            passed = threshold_lower <= rule_parameters.actual_value <= threshold_upper
        elif threshold_upper is not None:
            passed = rule_parameters.actual_value <= threshold_upper
        elif threshold_lower is not None:
            passed = threshold_lower <= rule_parameters.actual_value
        else:
            raise ValueError("At least one threshold is required.")
    
        expected_value = filtered_mean
        lower_bound = threshold_lower
        upper_bound = threshold_upper
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## multiply moving stdev 7 days
Data quality rule that verifies if a data quality sensor readout value
 doesn&#x27;t excessively deviate from the moving average within a time window.

**Rule summary**

The multiply moving stdev 7 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| stdev | <span class="no-wrap-code">`stdev/multiply_moving_stdev_7_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/multiply_moving_stdev_7_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/multiply_moving_stdev_7_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`multiply_stdev_above`</span>|How many multiples of the estimated standard deviation can the current sensor readout be above the moving average within the time window? Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 7 time periods (days, etc.) time window, but at least 3 readouts must exist to run the calculation.|*double*| ||
|<span class="no-wrap-code">`multiply_stdev_below`</span>|How many multiples of the estimated standard deviation can the current sensor readout be below the moving average within the time window? Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 7 time periods (days, etc.) time window, but at least 3 readouts must exist to run the calculation.|*double*| ||




**Rule definition YAML**

The rule definition YAML file *stdev/multiply_moving_stdev_7_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
      - field_name: multiply_stdev_above
        display_name: multiply_stdev_above
        help_text: "How many multiples of the estimated standard deviation the current\
          \ sensor readout could be above the moving average within the time window. Set\
          \ the time window at the threshold level for all severity levels (warning, error,\
          \ fatal) at once. The default is a 7 time periods (days, etc.) time window,\
          \ but at least 3 readouts must exist to run the calculation."
        data_type: double
      - field_name: multiply_stdev_below
        display_name: multiply_stdev_below
        help_text: "How many multiples of the estimated standard deviation the current\
          \ sensor readout could be below the moving average within the time window. Set\
          \ the time window at the threshold level for all severity levels (warning, error,\
          \ fatal) at once. The default is a 7 time periods (days, etc.) time window,\
          \ but at least 3 readouts must exist to run the calculation."
        data_type: double
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *stdev/multiply_moving_stdev_7_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/stdev/multiply_moving_stdev_7_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    class MultiplyMovingStdevRuleParametersSpec:
        multiply_stdev_above: float
        multiply_stdev_below: float
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readout: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: MultiplyMovingStdevRuleParametersSpec
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
        filtered_std = float(scipy.stats.tstd(filtered))
        filtered_mean = float(np.mean(filtered))
    
        threshold_lower = filtered_mean - rule_parameters.parameters.multiply_stdev_below * filtered_std \
            if rule_parameters.parameters.multiply_stdev_below is not None else None
        threshold_upper = filtered_mean + rule_parameters.parameters.multiply_stdev_above * filtered_std \
            if rule_parameters.parameters.multiply_stdev_above is not None else None
    
        if threshold_lower is not None and threshold_upper is not None:
            passed = threshold_lower <= rule_parameters.actual_value <= threshold_upper
        elif threshold_upper is not None:
            passed = rule_parameters.actual_value <= threshold_upper
        elif threshold_lower is not None:
            passed = threshold_lower <= rule_parameters.actual_value
        else:
            raise ValueError("At least one threshold is required.")
    
        expected_value = filtered_mean
        lower_bound = threshold_lower
        upper_bound = threshold_upper
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## multiply moving stdev within 30 days
Data quality rule that verifies if a data quality sensor readout value
 doesn&#x27;t excessively deviate from the moving average within a time window.

**Rule summary**

The multiply moving stdev within 30 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| stdev | <span class="no-wrap-code">`stdev/multiply_moving_stdev_within_30_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/multiply_moving_stdev_within_30_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/multiply_moving_stdev_within_30_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`multiply_stdev`</span>|How many multiples of the estimated standard deviation within the moving average the current sensor readout could be, with regards to the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 30 time periods (days, etc.) time window, but at least 10 readouts must exist to run the calculation.|*double*|:material-check-bold:||




**Rule definition YAML**

The rule definition YAML file *stdev/multiply_moving_stdev_within_30_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
      - field_name: multiply_stdev
        display_name: multiply_stdev
        help_text: "How many multiples of the estimated standard deviation within the\
          \ moving average the current sensor readout could be, with regards to the time\
          \ window. Set the time window at the threshold level for all severity levels\
          \ (warning, error, fatal) at once. The default is a 30 time periods (days, etc.)\
          \ time window, but at least 10 readouts must exist to run the calculation."
        data_type: double
        required: true
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *stdev/multiply_moving_stdev_within_30_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/stdev/multiply_moving_stdev_within_30_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    class MultiplyMovingStdevWithinRuleParametersSpec:
        multiply_stdev: float
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readout: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: MultiplyMovingStdevWithinRuleParametersSpec
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
        filtered_std = float(scipy.stats.tstd(filtered))
        filtered_mean = float(np.mean(filtered))
    
        stdev_span = rule_parameters.parameters.multiply_stdev * filtered_std
        threshold_lower = filtered_mean - stdev_span / 2
        threshold_upper = filtered_mean + stdev_span / 2
    
        passed = threshold_lower <= rule_parameters.actual_value <= threshold_upper
    
        expected_value = filtered_mean
        lower_bound = threshold_lower
        upper_bound = threshold_upper
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## multiply moving stdev within 60 days
Data quality rule that verifies if a data quality sensor readout value
 doesn&#x27;t excessively deviate from the moving average within a time window.

**Rule summary**

The multiply moving stdev within 60 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| stdev | <span class="no-wrap-code">`stdev/multiply_moving_stdev_within_60_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/multiply_moving_stdev_within_60_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/multiply_moving_stdev_within_60_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`multiply_stdev`</span>|How many multiples of the estimated standard deviation within the moving average the current sensor readout could be, with regards to the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 60 time periods (days, etc.) time window, but at least 20 readouts must exist to run the calculation.|*double*|:material-check-bold:||




**Rule definition YAML**

The rule definition YAML file *stdev/multiply_moving_stdev_within_60_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
      - field_name: multiply_stdev
        display_name: multiply_stdev
        help_text: "How many multiples of the estimated standard deviation within the\
          \ moving average the current sensor readout could be, with regards to the time\
          \ window. Set the time window at the threshold level for all severity levels\
          \ (warning, error, fatal) at once. The default is a 60 time periods (days, etc.)\
          \ time window, but at least 20 readouts must exist to run the calculation."
        data_type: double
        required: true
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *stdev/multiply_moving_stdev_within_60_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/stdev/multiply_moving_stdev_within_60_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    class MultiplyMovingStdevWithinRuleParametersSpec:
        multiply_stdev: float
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readout: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: MultiplyMovingStdevWithinRuleParametersSpec
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
        filtered_std = float(scipy.stats.tstd(filtered))
        filtered_mean = float(np.mean(filtered))
    
        stdev_span = rule_parameters.parameters.multiply_stdev * filtered_std
        threshold_lower = filtered_mean - stdev_span / 2
        threshold_upper = filtered_mean + stdev_span / 2
    
        passed = threshold_lower <= rule_parameters.actual_value <= threshold_upper
    
        expected_value = filtered_mean
        lower_bound = threshold_lower
        upper_bound = threshold_upper
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## multiply moving stdev within 7 days
Data quality rule that verifies if a data quality sensor readout value
 doesn&#x27;t excessively deviate from the moving average within a time window.

**Rule summary**

The multiply moving stdev within 7 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| stdev | <span class="no-wrap-code">`stdev/multiply_moving_stdev_within_7_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/multiply_moving_stdev_within_7_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/stdev/multiply_moving_stdev_within_7_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`multiply_stdev`</span>|How many multiples of the estimated standard deviation within the moving average the current sensor readout could be, with regards to the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 7 time periods (days, etc.) time window, but at least 3 readouts must exist to run the calculation.|*double*|:material-check-bold:||




**Rule definition YAML**

The rule definition YAML file *stdev/multiply_moving_stdev_within_7_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
      - field_name: multiply_stdev
        display_name: multiply_stdev
        help_text: "How many multiples of the estimated standard deviation within the\
          \ moving average the current sensor readout could be, with regards to the time\
          \ window. Set the time window at the threshold level for all severity levels\
          \ (warning, error, fatal) at once. The default is a 7 time periods (days, etc.)\
          \ time window, but at least 3 readouts must exist to run the calculation."
        data_type: double
        required: true
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *stdev/multiply_moving_stdev_within_7_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/stdev/multiply_moving_stdev_within_7_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    class MultiplyMovingStdevWithinRuleParametersSpec:
        multiply_stdev: float
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readout: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: MultiplyMovingStdevWithinRuleParametersSpec
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
        filtered_std = float(scipy.stats.tstd(filtered))
        filtered_mean = float(np.mean(filtered))
    
        stdev_span = rule_parameters.parameters.multiply_stdev * filtered_std
        threshold_lower = filtered_mean - stdev_span / 2
        threshold_upper = filtered_mean + stdev_span / 2
    
        passed = threshold_lower <= rule_parameters.actual_value <= threshold_upper
    
        expected_value = filtered_mean
        lower_bound = threshold_lower
        upper_bound = threshold_upper
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```




## What's next
- Learn how the [data quality rules](../../dqo-concepts/definition-of-data-quality-rules.md) are defined in DQOps and what how to create custom rules
- Understand how DQOps [runs data quality checks](../../dqo-concepts/architecture/data-quality-check-execution-flow.md), calling rules
