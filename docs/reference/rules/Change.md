# Data quality change rules
The list of change [data quality rules](../../dqo-concepts/definition-of-data-quality-rules.md) supported by DQOps. The source code is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/change* folder in the DQOps distribution.


---

## between change
Data quality rule that verifies if data quality sensor readout value changed by a value between the provided bounds.

**Rule summary**

The between change data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| change | `change/between_change` | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/change/between_change.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/change/between_change.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|`from`|Minimal accepted change with regards to the previous readout (inclusive).|double| ||
|`to`|Maximal accepted change with regards to the previous readout (inclusive).|double| ||




**Rule definition YAML**

The rule definition YAML file *change/between_change.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ```yaml
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
    apiVersion: dqo/v1
    kind: rule
    spec:
      type: python
      java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
      mode: previous_readouts
      time_window:
        prediction_time_window: 1
        min_periods_with_readouts: 1
        historic_data_point_grouping: last_n_readouts
      fields:
      - field_name: from
        display_name: from
        help_text: Minimal accepted change with regards to the previous readout (inclusive).
        data_type: double
        sample_values:
        - 10
      - field_name: to
        display_name: to
        help_text: Maximal accepted change with regards to the previous readout (inclusive).
        data_type: double
        sample_values:
        - 20
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *change/between_change* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/change/between_change.py* file in the DQOps distribution.

??? abstract "Rule source code"

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
        previous_readout = filtered[0] if len(filtered) > 0 else None
    
        if previous_readout is None:
            return RuleExecutionResult()
    
        lower_bound = previous_readout + getattr(rule_parameters.parameters, 'from')
        upper_bound = previous_readout + rule_parameters.parameters.to
    
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
        expected_value = (lower_bound + upper_bound) / 2
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## between change 1 day
Data quality rule that verifies if data quality sensor readout value changed by a value between the provided bounds compared to yesterday.

**Rule summary**

The between change 1 day data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| change | `change/between_change_1_day` | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/change/between_change_1_day.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/change/between_change_1_day.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|`from`|Minimal accepted change with regards to the previous readout (inclusive).|double| ||
|`to`|Maximal accepted change with regards to the previous readout (inclusive).|double| ||
|`exact_day`|When the exact_day parameter is unchecked (exact_day: false), rule searches for the most recent sensor readouts from the past 60 days and compares them. If the parameter is selected (exact_day: true), the rule compares only with the results from the past 1 day. If no results are found from that time, no results or errors will be generated.|boolean| ||




**Rule definition YAML**

The rule definition YAML file *change/between_change_1_day.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ```yaml
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
    apiVersion: dqo/v1
    kind: rule
    spec:
      type: python
      java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
      mode: previous_readouts
      time_window:
        prediction_time_window: 60
        min_periods_with_readouts: 1
        historic_data_point_grouping: day
      fields:
      - field_name: from
        display_name: from
        help_text: Minimal accepted change with regards to the previous readout (inclusive).
        data_type: double
        sample_values:
        - 10
      - field_name: to
        display_name: to
        help_text: Maximal accepted change with regards to the previous readout (inclusive).
        data_type: double
        sample_values:
        - 20
      - field_name: exact_day
        display_name: exact_day
        help_text: "When the exact_day parameter is unchecked (exact_day: false), rule\
          \ searches for the most recent sensor readouts from the past 60 days and compares\
          \ them. If the parameter is selected (exact_day: true), the rule compares only\
          \ with the results from the past 1 day. If no results are found from that time,\
          \ no results or errors will be generated."
        data_type: boolean
        sample_values:
        - "false"
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *change/between_change_1_day* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/change/between_change_1_day.py* file in the DQOps distribution.

??? abstract "Rule source code"

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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class BetweenChange1DayRuleParametersSpec:
        from_: float
        to: float
        exact_day: bool = False
    
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
    
        def __init__(self, passed=None, expected_value=None, lower_bound=None, upper_bound=None):
            self.passed = passed
            self.expected_value = expected_value
            self.lower_bound = lower_bound
            self.upper_bound = upper_bound
    
    
    # rule evaluation method that should be modified for each type of rule
    def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
        if not hasattr(rule_parameters, 'actual_value'):
            return RuleExecutionResult()
    
        past_readouts = rule_parameters.previous_readouts
        if rule_parameters.parameters.exact_day:
            last_readout = past_readouts[-1]
            if last_readout is None:
                return RuleExecutionResult()
    
            previous_readout = last_readout.sensor_readout
        else:
            filtered_readouts = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in past_readouts if readouts is not None]
            previous_readout = filtered_readouts[-1] if len(filtered_readouts) > 0 else None
            if previous_readout is None:
                return RuleExecutionResult()
    
        lower_bound = previous_readout + getattr(rule_parameters.parameters, 'from')
        upper_bound = previous_readout + rule_parameters.parameters.to
    
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
        expected_value = (lower_bound + upper_bound) / 2
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## between change 30 days
Data quality rule that verifies if data quality sensor readout value changed by a value between the provided bounds compared to last month.

**Rule summary**

The between change 30 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| change | `change/between_change_30_days` | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/change/between_change_30_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/change/between_change_30_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|`from`|Minimal accepted change with regards to the previous readout (inclusive).|double| ||
|`to`|Maximal accepted change with regards to the previous readout (inclusive).|double| ||
|`exact_day`|When the exact_day parameter is unchecked (exact_day: false), rule searches for the most recent sensor readouts from the past 60 days and compares them. If the parameter is selected (exact_day: true), the rule compares only with the results from the past 30 days. If no results are found from that time, no results or errors will be generated.|boolean| ||




**Rule definition YAML**

The rule definition YAML file *change/between_change_30_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ```yaml
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
    apiVersion: dqo/v1
    kind: rule
    spec:
      type: python
      java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
      mode: previous_readouts
      time_window:
        prediction_time_window: 60
        min_periods_with_readouts: 1
        historic_data_point_grouping: day
      fields:
      - field_name: from
        display_name: from
        help_text: Minimal accepted change with regards to the previous readout (inclusive).
        data_type: double
        sample_values:
        - 10
      - field_name: to
        display_name: to
        help_text: Maximal accepted change with regards to the previous readout (inclusive).
        data_type: double
        sample_values:
        - 20
      - field_name: exact_day
        display_name: exact_day
        help_text: "When the exact_day parameter is unchecked (exact_day: false), rule\
          \ searches for the most recent sensor readouts from the past 60 days and compares\
          \ them. If the parameter is selected (exact_day: true), the rule compares only\
          \ with the results from the past 30 days. If no results are found from that\
          \ time, no results or errors will be generated."
        data_type: boolean
        sample_values:
        - "false"
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *change/between_change_30_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/change/between_change_30_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class BetweenChange30DaysRuleParametersSpec:
        from_: float
        to: float
        exact_day: bool = False
    
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
    
        def __init__(self, passed=None, expected_value=None, lower_bound=None, upper_bound=None):
            self.passed = passed
            self.expected_value = expected_value
            self.lower_bound = lower_bound
            self.upper_bound = upper_bound
    
    
    # rule evaluation method that should be modified for each type of rule
    def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
        if not hasattr(rule_parameters, 'actual_value'):
            return RuleExecutionResult()
    
        past_readouts = rule_parameters.previous_readouts[:-29]
        if rule_parameters.parameters.exact_day:
            last_readout = past_readouts[-1]
            if last_readout is None:
                return RuleExecutionResult()
    
            previous_readout = last_readout.sensor_readout
        else:
            filtered_readouts = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in past_readouts if readouts is not None]
            previous_readout = filtered_readouts[-1] if len(filtered_readouts) > 0 else None
            if previous_readout is None:
                return RuleExecutionResult()
    
        lower_bound = previous_readout + getattr(rule_parameters.parameters, 'from')
        upper_bound = previous_readout + rule_parameters.parameters.to
    
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
        expected_value = (lower_bound + upper_bound) / 2
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## between change 7 days
Data quality rule that verifies if data quality sensor readout value changed by a value between the provided bounds compared to last week.

**Rule summary**

The between change 7 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| change | `change/between_change_7_days` | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/change/between_change_7_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/change/between_change_7_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|`from`|Minimal accepted change with regards to the previous readout (inclusive).|double| ||
|`to`|Maximal accepted change with regards to the previous readout (inclusive).|double| ||
|`exact_day`|When the exact_day parameter is unchecked (exact_day: false), rule searches for the most recent sensor readouts from the past 60 days and compares them. If the parameter is selected (exact_day: true), the rule compares only with the results from the past 7 days. If no results are found from that time, no results or errors will be generated.|boolean| ||




**Rule definition YAML**

The rule definition YAML file *change/between_change_7_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ```yaml
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
    apiVersion: dqo/v1
    kind: rule
    spec:
      type: python
      java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
      mode: previous_readouts
      time_window:
        prediction_time_window: 60
        min_periods_with_readouts: 1
        historic_data_point_grouping: day
      fields:
      - field_name: from
        display_name: from
        help_text: Minimal accepted change with regards to the previous readout (inclusive).
        data_type: double
        sample_values:
        - 10
      - field_name: to
        display_name: to
        help_text: Maximal accepted change with regards to the previous readout (inclusive).
        data_type: double
        sample_values:
        - 20
      - field_name: exact_day
        display_name: exact_day
        help_text: "When the exact_day parameter is unchecked (exact_day: false), rule\
          \ searches for the most recent sensor readouts from the past 60 days and compares\
          \ them. If the parameter is selected (exact_day: true), the rule compares only\
          \ with the results from the past 7 days. If no results are found from that time,\
          \ no results or errors will be generated."
        data_type: boolean
        sample_values:
        - "false"
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *change/between_change_7_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/change/between_change_7_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class BetweenChange7DaysRuleParametersSpec:
        from_: float
        to: float
        exact_day: bool = False
    
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
    
        def __init__(self, passed=None, expected_value=None, lower_bound=None, upper_bound=None):
            self.passed = passed
            self.expected_value = expected_value
            self.lower_bound = lower_bound
            self.upper_bound = upper_bound
    
    
    # rule evaluation method that should be modified for each type of rule
    def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
        if not hasattr(rule_parameters, 'actual_value'):
            return RuleExecutionResult()
    
        past_readouts = rule_parameters.previous_readouts[:-6]
        if rule_parameters.parameters.exact_day:
            last_readout = past_readouts[-1]
            if last_readout is None:
                return RuleExecutionResult()
    
            previous_readout = last_readout.sensor_readout
        else:
            filtered_readouts = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in past_readouts if readouts is not None]
            previous_readout = filtered_readouts[-1] if len(filtered_readouts) > 0 else None
            if previous_readout is None:
                return RuleExecutionResult()
    
        lower_bound = previous_readout + getattr(rule_parameters.parameters, 'from')
        upper_bound = previous_readout + rule_parameters.parameters.to
    
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
        expected_value = (lower_bound + upper_bound) / 2
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## between percent change
Data quality rule that verifies if data quality sensor readout value changed by a percent between the provided bounds.

**Rule summary**

The between percent change data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| change | `change/between_percent_change` | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/change/between_percent_change.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/change/between_percent_change.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|`from_percent`|Minimal accepted change relative to the previous readout (inclusive).|double| ||
|`to_percent`|Maximal accepted change relative to the previous readout (inclusive).|double| ||




**Rule definition YAML**

The rule definition YAML file *change/between_percent_change.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ```yaml
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
    apiVersion: dqo/v1
    kind: rule
    spec:
      type: python
      java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
      mode: previous_readouts
      time_window:
        prediction_time_window: 1
        min_periods_with_readouts: 1
        historic_data_point_grouping: last_n_readouts
      fields:
      - field_name: from_percent
        display_name: from_percent
        help_text: Minimal accepted change relative to the previous readout (inclusive).
        data_type: double
        sample_values:
        - 10
      - field_name: to_percent
        display_name: to_percent
        help_text: Maximal accepted change relative to the previous readout (inclusive).
        data_type: double
        sample_values:
        - 20
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *change/between_percent_change* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/change/between_percent_change.py* file in the DQOps distribution.

??? abstract "Rule source code"

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
        previous_readout = filtered[0] if len(filtered) > 0 else None
    
        if previous_readout is None:
            return RuleExecutionResult()
    
        lower_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.from_percent / 100.0)
        upper_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.to_percent / 100.0)
    
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
        expected_value = (lower_bound + upper_bound) / 2
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## between percent change 1 day
Data quality rule that verifies if data quality sensor readout value changed by a percent between the provided bounds compared to yesterday.

**Rule summary**

The between percent change 1 day data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| change | `change/between_percent_change_1_day` | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/change/between_percent_change_1_day.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/change/between_percent_change_1_day.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|`from_percent`|Minimal accepted change relative to the previous readout (inclusive).|double| ||
|`to_percent`|Maximal accepted change relative to the previous readout (inclusive).|double| ||
|`exact_day`|When the exact_day parameter is unchecked (exact_day: false), rule searches for the most recent sensor readouts from the past 60 days and compares them. If the parameter is selected (exact_day: true), the rule compares only with the results from the past 1 day. If no results are found from that time, no results or errors will be generated.|boolean| ||




**Rule definition YAML**

The rule definition YAML file *change/between_percent_change_1_day.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ```yaml
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
    apiVersion: dqo/v1
    kind: rule
    spec:
      type: python
      java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
      mode: previous_readouts
      time_window:
        prediction_time_window: 60
        min_periods_with_readouts: 1
        historic_data_point_grouping: day
      fields:
      - field_name: from_percent
        display_name: from_percent
        help_text: Minimal accepted change relative to the previous readout (inclusive).
        data_type: double
        sample_values:
        - 10
      - field_name: to_percent
        display_name: to_percent
        help_text: Maximal accepted change relative to the previous readout (inclusive).
        data_type: double
        sample_values:
        - 20
      - field_name: exact_day
        display_name: exact_day
        help_text: "When the exact_day parameter is unchecked (exact_day: false), rule\
          \ searches for the most recent sensor readouts from the past 60 days and compares\
          \ them. If the parameter is selected (exact_day: true), the rule compares only\
          \ with the results from the past 1 day. If no results are found from that time,\
          \ no results or errors will be generated."
        data_type: boolean
        sample_values:
        - "false"
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *change/between_percent_change_1_day* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/change/between_percent_change_1_day.py* file in the DQOps distribution.

??? abstract "Rule source code"

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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class BetweenPercentChange1DayRuleParametersSpec:
        from_percent: float
        to_percent: float
        exact_day: bool = False
    
    
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
    
        def __init__(self, passed=None, expected_value=None, lower_bound=None, upper_bound=None):
            self.passed = passed
            self.expected_value = expected_value
            self.lower_bound = lower_bound
            self.upper_bound = upper_bound
    
    
    # rule evaluation method that should be modified for each type of rule
    def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
        if not hasattr(rule_parameters, 'actual_value'):
            return RuleExecutionResult()
    
        past_readouts = rule_parameters.previous_readouts
        if rule_parameters.parameters.exact_day:
            last_readout = past_readouts[-1]
            if last_readout is None:
                return RuleExecutionResult()
    
            previous_readout = last_readout.sensor_readout
        else:
            filtered_readouts = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in past_readouts if readouts is not None]
            previous_readout = filtered_readouts[-1] if len(filtered_readouts) > 0 else None
            if previous_readout is None:
                return RuleExecutionResult()
    
        lower_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.from_percent / 100.0)
        upper_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.to_percent / 100.0)
    
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
        expected_value = (lower_bound + upper_bound) / 2
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## between percent change 30 days
Data quality rule that verifies if data quality sensor readout value changed by a percent between the provided bounds compared to last month.

**Rule summary**

The between percent change 30 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| change | `change/between_percent_change_30_days` | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/change/between_percent_change_30_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/change/between_percent_change_30_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|`from_percent`|Minimal accepted change relative to the previous readout (inclusive).|double| ||
|`to_percent`|Maximal accepted change relative to the previous readout (inclusive).|double| ||
|`exact_day`|When the exact_day parameter is unchecked (exact_day: false), rule searches for the most recent sensor readouts from the past 60 days and compares them. If the parameter is selected (exact_day: true), the rule compares only with the results from the past 30 days. If no results are found from that time, no results or errors will be generated.|boolean| ||




**Rule definition YAML**

The rule definition YAML file *change/between_percent_change_30_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ```yaml
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
    apiVersion: dqo/v1
    kind: rule
    spec:
      type: python
      java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
      mode: previous_readouts
      time_window:
        prediction_time_window: 60
        min_periods_with_readouts: 1
        historic_data_point_grouping: day
      fields:
      - field_name: from_percent
        display_name: from_percent
        help_text: Minimal accepted change relative to the previous readout (inclusive).
        data_type: double
        sample_values:
        - 10
      - field_name: to_percent
        display_name: to_percent
        help_text: Maximal accepted change relative to the previous readout (inclusive).
        data_type: double
        sample_values:
        - 20
      - field_name: exact_day
        display_name: exact_day
        help_text: "When the exact_day parameter is unchecked (exact_day: false), rule\
          \ searches for the most recent sensor readouts from the past 60 days and compares\
          \ them. If the parameter is selected (exact_day: true), the rule compares only\
          \ with the results from the past 30 days. If no results are found from that\
          \ time, no results or errors will be generated."
        data_type: boolean
        sample_values:
        - "false"
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *change/between_percent_change_30_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/change/between_percent_change_30_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class BetweenPercentChange30DaysRuleParametersSpec:
        from_percent: float
        to_percent: float
        exact_day: bool = False
    
    
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
    
        def __init__(self, passed=None, expected_value=None, lower_bound=None, upper_bound=None):
            self.passed = passed
            self.expected_value = expected_value
            self.lower_bound = lower_bound
            self.upper_bound = upper_bound
    
    
    # rule evaluation method that should be modified for each type of rule
    def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
        if not hasattr(rule_parameters, 'actual_value'):
            return RuleExecutionResult()
    
        past_readouts = rule_parameters.previous_readouts[:-29]
        if rule_parameters.parameters.exact_day:
            last_readout = past_readouts[-1]
            if last_readout is None:
                return RuleExecutionResult()
    
            previous_readout = last_readout.sensor_readout
        else:
            filtered_readouts = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in past_readouts if readouts is not None]
            previous_readout = filtered_readouts[-1] if len(filtered_readouts) > 0 else None
            if previous_readout is None:
                return RuleExecutionResult()
    
        lower_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.from_percent / 100.0)
        upper_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.to_percent / 100.0)
    
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
        expected_value = (lower_bound + upper_bound) / 2
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## between percent change 7 days
Data quality rule that verifies if data quality sensor readout value changed by a percent between the provided bounds compared to last week.

**Rule summary**

The between percent change 7 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| change | `change/between_percent_change_7_days` | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/change/between_percent_change_7_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/change/between_percent_change_7_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|`from_percent`|Minimal accepted change relative to the previous readout (inclusive).|double| ||
|`to_percent`|Maximal accepted change relative to the previous readout (inclusive).|double| ||
|`exact_day`|When the exact_day parameter is unchecked (exact_day: false), rule searches for the most recent sensor readouts from the past 60 days and compares them. If the parameter is selected (exact_day: true), the rule compares only with the results from the past 7 days. If no results are found from that time, no results or errors will be generated.|boolean| ||




**Rule definition YAML**

The rule definition YAML file *change/between_percent_change_7_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ```yaml
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
    apiVersion: dqo/v1
    kind: rule
    spec:
      type: python
      java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
      mode: previous_readouts
      time_window:
        prediction_time_window: 60
        min_periods_with_readouts: 1
        historic_data_point_grouping: day
      fields:
      - field_name: from_percent
        display_name: from_percent
        help_text: Minimal accepted change relative to the previous readout (inclusive).
        data_type: double
        sample_values:
        - 10
      - field_name: to_percent
        display_name: to_percent
        help_text: Maximal accepted change relative to the previous readout (inclusive).
        data_type: double
        sample_values:
        - 20
      - field_name: exact_day
        display_name: exact_day
        help_text: "When the exact_day parameter is unchecked (exact_day: false), rule\
          \ searches for the most recent sensor readouts from the past 60 days and compares\
          \ them. If the parameter is selected (exact_day: true), the rule compares only\
          \ with the results from the past 7 days. If no results are found from that time,\
          \ no results or errors will be generated."
        data_type: boolean
        sample_values:
        - "false"
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *change/between_percent_change_7_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/change/between_percent_change_7_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class BetweenPercentChange7DaysRuleParametersSpec:
        from_percent: float
        to_percent: float
        exact_day: bool = False
    
    
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
    
        def __init__(self, passed=None, expected_value=None, lower_bound=None, upper_bound=None):
            self.passed = passed
            self.expected_value = expected_value
            self.lower_bound = lower_bound
            self.upper_bound = upper_bound
    
    
    # rule evaluation method that should be modified for each type of rule
    def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
        if not hasattr(rule_parameters, 'actual_value'):
            return RuleExecutionResult()
    
        past_readouts = rule_parameters.previous_readouts[:-6]
        if rule_parameters.parameters.exact_day:
            last_readout = past_readouts[-1]
            if last_readout is None:
                return RuleExecutionResult()
    
            previous_readout = last_readout.sensor_readout
        else:
            filtered_readouts = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in past_readouts if readouts is not None]
            previous_readout = filtered_readouts[-1] if len(filtered_readouts) > 0 else None
            if previous_readout is None:
                return RuleExecutionResult()
    
        lower_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.from_percent / 100.0)
        upper_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.to_percent / 100.0)
    
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
        expected_value = (lower_bound + upper_bound) / 2
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## change difference
Data quality rule that verifies if data quality sensor readout value changed by a value within the provided bound.

**Rule summary**

The change difference data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| change | `change/change_difference` | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/change/change_difference.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/change/change_difference.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|`max_difference`|Maximum accepted absolute difference compared to previous readout (inclusive).|double| ||




**Rule definition YAML**

The rule definition YAML file *change/change_difference.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ```yaml
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
    apiVersion: dqo/v1
    kind: rule
    spec:
      type: python
      java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
      mode: previous_readouts
      time_window:
        prediction_time_window: 1
        min_periods_with_readouts: 1
        historic_data_point_grouping: last_n_readouts
      fields:
      - field_name: max_difference
        display_name: max_difference
        help_text: Maximum accepted absolute difference compared to previous readout (inclusive).
        data_type: double
        sample_values:
        - 10
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *change/change_difference* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/change/change_difference.py* file in the DQOps distribution.

??? abstract "Rule source code"

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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class WithinChangeRuleParametersSpec:
        max_difference: float
    
    
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
        previous_readout = filtered[0] if len(filtered) > 0 else None
    
        if previous_readout is None:
            return RuleExecutionResult()
    
        lower_bound = previous_readout - rule_parameters.parameters.max_difference
        upper_bound = previous_readout + rule_parameters.parameters.max_difference
    
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
        expected_value = previous_readout
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## change difference 1 day
Data quality rule that verifies if data quality sensor readout value changed by a value within the provided bound compared to yesterday.

**Rule summary**

The change difference 1 day data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| change | `change/change_difference_1_day` | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/change/change_difference_1_day.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/change/change_difference_1_day.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|`max_difference`|Maximum accepted absolute difference compared to a readout 1 day ago (inclusive).|double| ||
|`exact_day`|When the exact_day parameter is unchecked (exact_day: false), rule searches for the most recent sensor readouts from the past 60 days and compares them. If the parameter is selected (exact_day: true), the rule compares only with the results from the past 1 day. If no results are found from that time, no results or errors will be generated.|boolean| ||




**Rule definition YAML**

The rule definition YAML file *change/change_difference_1_day.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ```yaml
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
    apiVersion: dqo/v1
    kind: rule
    spec:
      type: python
      java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
      mode: previous_readouts
      time_window:
        prediction_time_window: 60
        min_periods_with_readouts: 1
        historic_data_point_grouping: day
      fields:
      - field_name: max_difference
        display_name: max_difference
        help_text: Maximum accepted absolute difference compared to a readout 1 day ago (inclusive).
        data_type: double
        sample_values:
        - 10
      - field_name: exact_day
        display_name: exact_day
        help_text: "When the exact_day parameter is unchecked (exact_day: false), rule\
          \ searches for the most recent sensor readouts from the past 60 days and compares\
          \ them. If the parameter is selected (exact_day: true), the rule compares only\
          \ with the results from the past 1 day. If no results are found from that time,\
          \ no results or errors will be generated."
        data_type: boolean
        sample_values:
        - "false"
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *change/change_difference_1_day* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/change/change_difference_1_day.py* file in the DQOps distribution.

??? abstract "Rule source code"

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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class WithinChange1DayRuleParametersSpec:
        max_difference: float
        exact_day: bool = False
    
    
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
    
        def __init__(self, passed=None, expected_value=None, lower_bound=None, upper_bound=None):
            self.passed = passed
            self.expected_value = expected_value
            self.lower_bound = lower_bound
            self.upper_bound = upper_bound
    
    
    # rule evaluation method that should be modified for each type of rule
    def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
        if not hasattr(rule_parameters, 'actual_value'):
            return RuleExecutionResult()
    
        past_readouts = rule_parameters.previous_readouts
        if rule_parameters.parameters.exact_day:
            last_readout = past_readouts[-1]
            if last_readout is None:
                return RuleExecutionResult()
    
            previous_readout = last_readout.sensor_readout
        else:
            filtered_readouts = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in past_readouts if readouts is not None]
            previous_readout = filtered_readouts[-1] if len(filtered_readouts) > 0 else None
            if previous_readout is None:
                return RuleExecutionResult()
    
        lower_bound = previous_readout - rule_parameters.parameters.max_difference
        upper_bound = previous_readout + rule_parameters.parameters.max_difference
    
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
        expected_value = previous_readout
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## change difference 30 days
Data quality rule that verifies if data quality sensor readout value changed by a value within the provided bound compared to last month.

**Rule summary**

The change difference 30 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| change | `change/change_difference_30_days` | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/change/change_difference_30_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/change/change_difference_30_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|`max_difference`|Maximum accepted absolute difference compared to a readout 30 days ago (inclusive).|double| ||
|`exact_day`|When the exact_day parameter is unchecked (exact_day: false), rule searches for the most recent sensor readouts from the past 60 days and compares them. If the parameter is selected (exact_day: true), the rule compares only with the results from the past 30 days. If no results are found from that time, no results or errors will be generated.|boolean| ||




**Rule definition YAML**

The rule definition YAML file *change/change_difference_30_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ```yaml
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
    apiVersion: dqo/v1
    kind: rule
    spec:
      type: python
      java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
      mode: previous_readouts
      time_window:
        prediction_time_window: 60
        min_periods_with_readouts: 1
        historic_data_point_grouping: day
      fields:
      - field_name: max_difference
        display_name: max_difference
        help_text: Maximum accepted absolute difference compared to a readout 30 days ago (inclusive).
        data_type: double
        sample_values:
        - 10
      - field_name: exact_day
        display_name: exact_day
        help_text: "When the exact_day parameter is unchecked (exact_day: false), rule\
          \ searches for the most recent sensor readouts from the past 60 days and compares\
          \ them. If the parameter is selected (exact_day: true), the rule compares only\
          \ with the results from the past 30 days. If no results are found from that\
          \ time, no results or errors will be generated."
        data_type: boolean
        sample_values:
        - "false"
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *change/change_difference_30_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/change/change_difference_30_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class WithinChange30DaysRuleParametersSpec:
        max_difference: float
        exact_day: bool = False
    
    
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
    
        def __init__(self, passed=None, expected_value=None, lower_bound=None, upper_bound=None):
            self.passed = passed
            self.expected_value = expected_value
            self.lower_bound = lower_bound
            self.upper_bound = upper_bound
    
    
    # rule evaluation method that should be modified for each type of rule
    def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
        if not hasattr(rule_parameters, 'actual_value'):
            return RuleExecutionResult()
    
        past_readouts = rule_parameters.previous_readouts[:-29]
        if rule_parameters.parameters.exact_day:
            last_readout = past_readouts[-1]
            if last_readout is None:
                return RuleExecutionResult()
    
            previous_readout = last_readout.sensor_readout
        else:
            filtered_readouts = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in past_readouts if readouts is not None]
            previous_readout = filtered_readouts[-1] if len(filtered_readouts) > 0 else None
            if previous_readout is None:
                return RuleExecutionResult()
    
        lower_bound = previous_readout - rule_parameters.parameters.max_difference
        upper_bound = previous_readout + rule_parameters.parameters.max_difference
    
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
        expected_value = previous_readout
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## change difference 7 days
Data quality rule that verifies if data quality sensor readout value changed by a value within the provided bound compared to last week.

**Rule summary**

The change difference 7 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| change | `change/change_difference_7_days` | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/change/change_difference_7_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/change/change_difference_7_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|`max_difference`|Maximum accepted absolute difference compared to a readout 7 days ago (inclusive).|double| ||
|`exact_day`|When the exact_day parameter is unchecked (exact_day: false), rule searches for the most recent sensor readouts from the past 60 days and compares them. If the parameter is selected (exact_day: true), the rule compares only with the results from the past 7 days. If no results are found from that time, no results or errors will be generated.|boolean| ||




**Rule definition YAML**

The rule definition YAML file *change/change_difference_7_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ```yaml
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
    apiVersion: dqo/v1
    kind: rule
    spec:
      type: python
      java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
      mode: previous_readouts
      time_window:
        prediction_time_window: 60
        min_periods_with_readouts: 1
        historic_data_point_grouping: day
      fields:
      - field_name: max_difference
        display_name: max_difference
        help_text: Maximum accepted absolute difference compared to a readout 7 days ago (inclusive).
        data_type: double
        sample_values:
        - 10
      - field_name: exact_day
        display_name: exact_day
        help_text: "When the exact_day parameter is unchecked (exact_day: false), rule\
          \ searches for the most recent sensor readouts from the past 60 days and compares\
          \ them. If the parameter is selected (exact_day: true), the rule compares only\
          \ with the results from the past 7 days. If no results are found from that time,\
          \ no results or errors will be generated."
        data_type: boolean
        sample_values:
        - "false"
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *change/change_difference_7_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/change/change_difference_7_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class WithinChange7DaysRuleParametersSpec:
        max_difference: float
        exact_day: bool = False
    
    
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
    
        def __init__(self, passed=None, expected_value=None, lower_bound=None, upper_bound=None):
            self.passed = passed
            self.expected_value = expected_value
            self.lower_bound = lower_bound
            self.upper_bound = upper_bound
    
    
    # rule evaluation method that should be modified for each type of rule
    def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
        if not hasattr(rule_parameters, 'actual_value'):
            return RuleExecutionResult()
    
        past_readouts = rule_parameters.previous_readouts[:-6]
        if rule_parameters.parameters.exact_day:
            last_readout = past_readouts[-1]
            if last_readout is None:
                return RuleExecutionResult()
    
            previous_readout = last_readout.sensor_readout
        else:
            filtered_readouts = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in past_readouts if readouts is not None]
            previous_readout = filtered_readouts[-1] if len(filtered_readouts) > 0 else None
            if previous_readout is None:
                return RuleExecutionResult()
    
        lower_bound = previous_readout - rule_parameters.parameters.max_difference
        upper_bound = previous_readout + rule_parameters.parameters.max_difference
    
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
        expected_value = previous_readout
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## change percent
Data quality rule that verifies if data quality sensor readout value changed by a percent within the provided bound.

**Rule summary**

The change percent data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| change | `change/change_percent` | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/change/change_percent.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/change/change_percent.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|`max_percent`|Percentage of maximum accepted change compared to previous readout (inclusive).|double| ||




**Rule definition YAML**

The rule definition YAML file *change/change_percent.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ```yaml
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
    apiVersion: dqo/v1
    kind: rule
    spec:
      type: python
      java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
      mode: previous_readouts
      time_window:
        prediction_time_window: 1
        min_periods_with_readouts: 1
        historic_data_point_grouping: last_n_readouts
      fields:
      - field_name: max_percent
        display_name: max_percent
        help_text: Percentage of maximum accepted change compared to previous readout
          (inclusive).
        data_type: double
        sample_values:
        - 5
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *change/change_percent* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/change/change_percent.py* file in the DQOps distribution.

??? abstract "Rule source code"

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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class WithinPercentChangeRuleParametersSpec:
        max_percent: float
    
    
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
        previous_readout = filtered[0] if len(filtered) > 0 else None
    
        if previous_readout is None:
            return RuleExecutionResult()
    
        lower_bound = previous_readout - abs(previous_readout) * (rule_parameters.parameters.max_percent / 100.0)
        upper_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.max_percent / 100.0)
    
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
        expected_value = previous_readout
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## change percent 1 day
Data quality rule that verifies if data quality sensor readout value changed by a percent within the provided bound compared to yesterday.

**Rule summary**

The change percent 1 day data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| change | `change/change_percent_1_day` | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/change/change_percent_1_day.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/change/change_percent_1_day.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|`max_percent`|Percentage of maximum accepted change compared to a readout 1 day ago (inclusive).|double| ||
|`exact_day`|When the exact_day parameter is unchecked (exact_day: false), the rule search for the most recent sensor readouts from the past 60 days and compares them. If the parameter is selected (exact_day: true), the rule compares only with the results from the past 1 day. If no results are found from that time, no results or errors will be generated.|boolean| ||




**Rule definition YAML**

The rule definition YAML file *change/change_percent_1_day.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ```yaml
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
    apiVersion: dqo/v1
    kind: rule
    spec:
      type: python
      java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
      mode: previous_readouts
      time_window:
        prediction_time_window: 60
        min_periods_with_readouts: 1
        historic_data_point_grouping: day
      fields:
      - field_name: max_percent
        display_name: max_percent
        help_text: Percentage of maximum accepted change compared to a readout 1 day ago (inclusive).
        data_type: double
        sample_values:
        - 5
      - field_name: exact_day
        display_name: exact_day
        help_text: "When the exact_day parameter is unchecked (exact_day: false), the\
          \ rule search for the most recent sensor readouts from the past 60 days and\
          \ compares them. If the parameter is selected (exact_day: true), the rule compares\
          \ only with the results from the past 1 day. If no results are found from that\
          \ time, no results or errors will be generated."
        data_type: boolean
        sample_values:
        - "false"
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *change/change_percent_1_day* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/change/change_percent_1_day.py* file in the DQOps distribution.

??? abstract "Rule source code"

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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class WithinPercentChange1DayRuleParametersSpec:
        max_percent: float
        exact_day: bool = False
    
    
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
    
        def __init__(self, passed=None, expected_value=None, lower_bound=None, upper_bound=None):
            self.passed = passed
            self.expected_value = expected_value
            self.lower_bound = lower_bound
            self.upper_bound = upper_bound
    
    
    # rule evaluation method that should be modified for each type of rule
    def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
        if not hasattr(rule_parameters, 'actual_value'):
            return RuleExecutionResult()
    
        past_readouts = rule_parameters.previous_readouts
        if rule_parameters.parameters.exact_day:
            last_readout = past_readouts[-1]
            if last_readout is None:
                return RuleExecutionResult()
    
            previous_readout = last_readout.sensor_readout
        else:
            filtered_readouts = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in past_readouts if readouts is not None]
            previous_readout = filtered_readouts[-1] if len(filtered_readouts) > 0 else None
            if previous_readout is None:
                return RuleExecutionResult()
    
        lower_bound = previous_readout - abs(previous_readout) * (rule_parameters.parameters.max_percent / 100.0)
        upper_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.max_percent / 100.0)
    
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
        expected_value = previous_readout
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## change percent 30 days
Data quality rule that verifies if data quality sensor readout value changed by a percent within the provided bound compared to last month.

**Rule summary**

The change percent 30 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| change | `change/change_percent_30_days` | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/change/change_percent_30_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/change/change_percent_30_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|`max_percent`|Percentage of maximum accepted change compared to a readout 30 days ago (inclusive).|double| ||
|`exact_day`|When the exact_day parameter is unchecked (exact_day: false), rule searches for the most recent sensor readouts from the past 60 days and compares them. If the parameter is selected (exact_day: true), the rule compares only with the results from the past 30 days. If no results are found from that time, no results or errors will be generated.|boolean| ||




**Rule definition YAML**

The rule definition YAML file *change/change_percent_30_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ```yaml
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
    apiVersion: dqo/v1
    kind: rule
    spec:
      type: python
      java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
      mode: previous_readouts
      time_window:
        prediction_time_window: 60
        min_periods_with_readouts: 1
        historic_data_point_grouping: day
      fields:
      - field_name: max_percent
        display_name: max_percent
        help_text: Percentage of maximum accepted change compared to a readout 30 days ago (inclusive).
        data_type: double
        sample_values:
        - 5
      - field_name: exact_day
        display_name: exact_day
        help_text: "When the exact_day parameter is unchecked (exact_day: false), rule\
          \ searches for the most recent sensor readouts from the past 60 days and compares\
          \ them. If the parameter is selected (exact_day: true), the rule compares only\
          \ with the results from the past 30 days. If no results are found from that\
          \ time, no results or errors will be generated."
        data_type: boolean
        sample_values:
        - "false"
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *change/change_percent_30_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/change/change_percent_30_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class WithinPercentChange30DaysRuleParametersSpec:
        max_percent: float
        exact_day: bool = False
    
    
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
    
        def __init__(self, passed=None, expected_value=None, lower_bound=None, upper_bound=None):
            self.passed = passed
            self.expected_value = expected_value
            self.lower_bound = lower_bound
            self.upper_bound = upper_bound
    
    
    # rule evaluation method that should be modified for each type of rule
    def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
        if not hasattr(rule_parameters, 'actual_value'):
            return RuleExecutionResult()
    
        past_readouts = rule_parameters.previous_readouts[:-29]
        if rule_parameters.parameters.exact_day:
            last_readout = past_readouts[-1]
            if last_readout is None:
                return RuleExecutionResult()
    
            previous_readout = last_readout.sensor_readout
        else:
            filtered_readouts = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in past_readouts if readouts is not None]
            previous_readout = filtered_readouts[-1] if len(filtered_readouts) > 0 else None
            if previous_readout is None:
                return RuleExecutionResult()
    
        lower_bound = previous_readout - abs(previous_readout) * (rule_parameters.parameters.max_percent / 100.0)
        upper_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.max_percent / 100.0)
    
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
        expected_value = previous_readout
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## change percent 7 days
Data quality rule that verifies if data quality sensor readout value changed by a percent within the provided bound compared to last week.

**Rule summary**

The change percent 7 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| change | `change/change_percent_7_days` | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/change/change_percent_7_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/change/change_percent_7_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|`max_percent`|Percentage of maximum accepted change compared to a readout 7 days ago (inclusive).|double| ||
|`exact_day`|When the exact_day parameter is unchecked (exact_day: false), rule searches for the most recent sensor readouts from the past 60 days and compares them. If the parameter is selected (exact_day: true), the rule compares only with the results from the past 7 days. If no results are found from that time, no results or errors will be generated.|boolean| ||




**Rule definition YAML**

The rule definition YAML file *change/change_percent_7_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ```yaml
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
    apiVersion: dqo/v1
    kind: rule
    spec:
      type: python
      java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
      mode: previous_readouts
      time_window:
        prediction_time_window: 60
        min_periods_with_readouts: 1
        historic_data_point_grouping: day
      fields:
      - field_name: max_percent
        display_name: max_percent
        help_text: Percentage of maximum accepted change compared to a readout 7 days ago (inclusive).
        data_type: double
        sample_values:
        - 5
      - field_name: exact_day
        display_name: exact_day
        help_text: "When the exact_day parameter is unchecked (exact_day: false), rule\
          \ searches for the most recent sensor readouts from the past 60 days and compares\
          \ them. If the parameter is selected (exact_day: true), the rule compares only\
          \ with the results from the past 7 days. If no results are found from that time,\
          \ no results or errors will be generated."
        data_type: boolean
        sample_values:
        - "false"
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *change/change_percent_7_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/change/change_percent_7_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class WithinPercentChange7DaysRuleParametersSpec:
        max_percent: float
        exact_day: bool = False
    
    
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
    
        def __init__(self, passed=None, expected_value=None, lower_bound=None, upper_bound=None):
            self.passed = passed
            self.expected_value = expected_value
            self.lower_bound = lower_bound
            self.upper_bound = upper_bound
    
    
    # rule evaluation method that should be modified for each type of rule
    def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
        if not hasattr(rule_parameters, 'actual_value'):
            return RuleExecutionResult()
    
        past_readouts = rule_parameters.previous_readouts[:-6]
        if rule_parameters.parameters.exact_day:
            last_readout = past_readouts[-1]
            if last_readout is None:
                return RuleExecutionResult()
    
            previous_readout = last_readout.sensor_readout
        else:
            filtered_readouts = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in past_readouts if readouts is not None]
            previous_readout = filtered_readouts[-1] if len(filtered_readouts) > 0 else None
            if previous_readout is None:
                return RuleExecutionResult()
    
        lower_bound = previous_readout - abs(previous_readout) * (rule_parameters.parameters.max_percent / 100.0)
        upper_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.max_percent / 100.0)
    
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
        expected_value = previous_readout
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



