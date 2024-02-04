# Data quality comparison rules
The list of comparison [data quality rules](../../dqo-concepts/definition-of-data-quality-rules.md) supported by DQOps. The source code is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison* folder in the DQOps distribution.


---

## between floats
Data quality rule that verifies if a data quality check readout is between from and to values.

**Rule summary**

The between floats data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/between_floats`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/between_floats.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/between_floats.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`from`</span>|Minimum accepted value for the actual_value returned by the sensor (inclusive).|*double*| ||
|<span class="no-wrap-code">`to`</span>|Maximum accepted value for the actual_value returned by the sensor (inclusive).|*double*| ||




**Rule definition YAML**

The rule definition YAML file *comparison/between_floats.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
      - field_name: to
        display_name: to
        help_text: Maximum accepted value for the actual_value returned by the sensor
          (inclusive).
        data_type: double
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/between_floats* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/between_floats.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class BetweenFloatsRuleParametersSpec:
        from_: float
        to: float
    
        def __getattr__(self, name):
            if name == "from":
                return self.from_ if hasattr(self, 'from_') else None
            return object.__getattribute__(self, name)
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        max_periods_with_readouts: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: BetweenFloatsRuleParametersSpec
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
    
        expected_value = None
        lower_bound = getattr(rule_parameters.parameters, "from") if hasattr(rule_parameters.parameters, 'from') else None
        upper_bound = rule_parameters.parameters.to if hasattr(rule_parameters.parameters, 'to') else None
        passed = (lower_bound if lower_bound is not None else rule_parameters.actual_value) <= rule_parameters.actual_value <= (upper_bound if upper_bound is not None else rule_parameters.actual_value)
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## between ints
Data quality rule that verifies if a data quality check readout is between begin and end values.

**Rule summary**

The between ints data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/between_ints`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/between_ints.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/between_ints.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`from`</span>|Minimum accepted value for the actual_value returned by the sensor (inclusive).|*long*| ||
|<span class="no-wrap-code">`to`</span>|Maximum accepted value for the actual_value returned by the sensor (inclusive).|*long*| ||




**Rule definition YAML**

The rule definition YAML file *comparison/between_ints.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
      - field_name: to
        display_name: to
        help_text: Maximum accepted value for the actual_value returned by the sensor
          (inclusive).
        data_type: long
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/between_ints* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/between_ints.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class BetweenIntsRuleParametersSpec:
        from_: int
        to: int
    
        def __getattr__(self, name):
            if name == "from":
                return self.from_ if hasattr(self, 'from_') else None
            return object.__getattribute__(self, name)
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
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
        if not hasattr(rule_parameters, 'actual_value'):
            return RuleExecutionResult()
    
        expected_value = None
        lower_bound = getattr(rule_parameters.parameters, "from") if hasattr(rule_parameters.parameters, 'from') else None
        upper_bound = rule_parameters.parameters.to if hasattr(rule_parameters.parameters, 'to') else None
        passed = (lower_bound if lower_bound is not None else rule_parameters.actual_value) <= rule_parameters.actual_value <= (upper_bound if upper_bound is not None else rule_parameters.actual_value)
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## between percent
Data quality rule that verifies if a data quality check percentage readout is between an accepted range of percentages.

**Rule summary**

The between percent data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/between_percent`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/between_percent.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/between_percent.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`min_percent`</span>|Minimum accepted percentage of rows passing the check (inclusive).|*double*| ||
|<span class="no-wrap-code">`max_percent`</span>|Maximum accepted percentage of rows passing the check (inclusive).|*double*| ||




**Rule definition YAML**

The rule definition YAML file *comparison/between_percent.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
    apiVersion: dqo/v1
    kind: rule
    spec:
      type: python
      java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
      mode: current_value
      fields:
      - field_name: min_percent
        display_name: min_percent
        help_text: Minimum accepted percentage of rows passing the check (inclusive).
        data_type: double
        default_value: 100.0
      - field_name: max_percent
        display_name: max_percent
        help_text: Maximum accepted percentage of rows passing the check (inclusive).
        data_type: double
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/between_percent* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/between_percent.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class BetweenPercentRuleParametersSpec:
        min_percent: float
        max_percent: float
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        max_periods_with_readouts: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: BetweenPercentRuleParametersSpec
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
    
        expected_value = None
        lower_bound = rule_parameters.parameters.min_percent if hasattr(rule_parameters.parameters, 'min_percent') else None
        upper_bound = rule_parameters.parameters.max_percent if hasattr(rule_parameters.parameters, 'max_percent') else None
        passed = (lower_bound if lower_bound is not None else
                     rule_parameters.actual_value) <= rule_parameters.actual_value <= (
                     upper_bound if upper_bound is not None else rule_parameters.actual_value)
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## count between
Data quality rule that verifies if a data quality check readout is between begin and end values, defined as min_count and max_count.

**Rule summary**

The count between data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/count_between`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/count_between.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/count_between.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`min_count`</span>|Minimum accepted count (inclusive), leave empty when the limit is not assigned.|*long*|:material-check-bold:||
|<span class="no-wrap-code">`max_count`</span>|Maximum accepted count (inclusive), leave empty when the limit is not assigned.|*long*|:material-check-bold:||




**Rule definition YAML**

The rule definition YAML file *comparison/count_between.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
    apiVersion: dqo/v1
    kind: rule
    spec:
      type: python
      java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
      mode: current_value
      fields:
      - field_name: min_count
        display_name: min_count
        help_text: "Minimum accepted count (inclusive), leave empty when the limit is\
          \ not assigned."
        data_type: long
        required: true
      - field_name: max_count
        display_name: max_count
        help_text: "Maximum accepted count (inclusive), leave empty when the limit is\
          \ not assigned."
        data_type: long
        required: true
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/count_between* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/count_between.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class CountBetweenRuleParametersSpec:
        min_count: int
        max_count: int
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        max_periods_with_readouts: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: CountBetweenRuleParametersSpec
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
        if not hasattr(rule_parameters, 'actual_value'):
            return RuleExecutionResult()
    
        expected_value = None
        lower_bound = rule_parameters.parameters.min_count if hasattr(rule_parameters.parameters, 'min_count') else None
        upper_bound = rule_parameters.parameters.max_count if hasattr(rule_parameters.parameters, 'max_count') else None
        passed = (lower_bound if lower_bound is not None else rule_parameters.actual_value) <= rule_parameters.actual_value <= (upper_bound if upper_bound is not None else rule_parameters.actual_value)
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## detected datatype equals
Data quality rule that verifies that a data quality check readout of a string_datatype_detect (the data type detection) matches an expected data type.
 The supported values are in the range 1..7, which are: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.

**Rule summary**

The detected datatype equals data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/detected_datatype_equals`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/detected_datatype_equals.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/detected_datatype_equals.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`expected_datatype`</span>|Expected data type code, the values for the sensor&#x27;s actual values are: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - texts, 7 - mixed data types.|*enum*| |*integers*<br/>*floats*<br/>*dates*<br/>*timestamps*<br/>*booleans*<br/>*texts*<br/>*mixed*<br/>|




**Rule definition YAML**

The rule definition YAML file *comparison/detected_datatype_equals.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
    apiVersion: dqo/v1
    kind: rule
    spec:
      type: python
      java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
      mode: current_value
      fields:
      - field_name: expected_datatype
        display_name: expected_datatype
        help_text: "Expected data type code, the values for the sensor's actual values\
          \ are: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6\
          \ - texts, 7 - mixed data types."
        data_type: enum
        allowed_values:
        - integers
        - floats
        - dates
        - timestamps
        - booleans
        - texts
        - mixed
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/detected_datatype_equals* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/detected_datatype_equals.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    from enum import IntEnum
    
    
    class DetectedDatatypeCategory(IntEnum):
        integers = 1
        floats = 2
        dates = 3
        timestamps = 4
        booleans = 5
        texts = 6
        mixed = 7
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class DetectedDatatypeEqualsRuleParametersSpec:
        expected_datatype: str
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readouts: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: DetectedDatatypeEqualsRuleParametersSpec
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
        if not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'expected_datatype'):
            return RuleExecutionResult()
    
        expected_value = getattr(DetectedDatatypeCategory, rule_parameters.parameters.expected_datatype).value
        lower_bound = expected_value
        upper_bound = expected_value
        passed = (expected_value == rule_parameters.actual_value)
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## diff percent
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.

**Rule summary**

The diff percent data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/diff_percent`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/diff_percent.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/diff_percent.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`max_diff_percent`</span>|Maximum accepted value for the percentage of difference between expected_value and actual_value returned by the sensor (inclusive).|*double*|:material-check-bold:||




**Rule definition YAML**

The rule definition YAML file *comparison/diff_percent.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
        required: true
        default_value: 0.0
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/diff_percent* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/diff_percent.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class DiffPercentRuleParametersSpec:
        max_diff_percent: float
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
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
            return RuleExecutionResult()
    
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



---

## equals
Data quality rule that verifies that a data quality check readout equals a given value. A margin of error may be configured.

**Rule summary**

The equals data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/equals`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/equals.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/equals.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`expected_value`</span>|Expected value for the actual_value returned by the sensor. The sensor value should equal expected_value +/- the error_margin.|*double*|:material-check-bold:||
|<span class="no-wrap-code">`error_margin`</span>|Error margin for comparison.|*double*| ||




**Rule definition YAML**

The rule definition YAML file *comparison/equals.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
        required: true
      - field_name: error_margin
        display_name: error_margin
        help_text: Error margin for comparison.
        data_type: double
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/equals* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/equals.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class EqualsRuleParametersSpec:
        expected_value: float
        error_margin: float
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
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
            return RuleExecutionResult()
    
        expected_value = rule_parameters.parameters.expected_value
        lower_bound = expected_value - rule_parameters.parameters.error_margin
        upper_bound = expected_value + rule_parameters.parameters.error_margin
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    ```



---

## equals 0
Data quality rule that verifies that a data quality check readout equals 0. It is used in data quality checks that have an expected value &quot;0&quot;.

**Rule summary**

The equals 0 data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/equals_0`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/equals_0.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/equals_0.py) |





**Rule definition YAML**

The rule definition YAML file *comparison/equals_0.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
    apiVersion: dqo/v1
    kind: rule
    spec:
      type: python
      java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
      mode: current_value
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/equals_0* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/equals_0.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class Equals0RuleParametersSpec:
        pass
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readouts: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: Equals0RuleParametersSpec
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
    
        expected_value = 0
        lower_bound = expected_value
        upper_bound = expected_value
        passed = rule_parameters.actual_value == expected_value
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## equals 1
Data quality rule that verifies that a data quality check readout equals 1. It is used in data quality checks that have an expected value &quot;1&quot;.

**Rule summary**

The equals 1 data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/equals_1`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/equals_1.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/equals_1.py) |





**Rule definition YAML**

The rule definition YAML file *comparison/equals_1.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
    apiVersion: dqo/v1
    kind: rule
    spec:
      type: python
      java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
      mode: current_value
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/equals_1* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/equals_1.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class Equals1RuleParametersSpec:
        pass
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readouts: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: Equals1RuleParametersSpec
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
    
        expected_value = 1
        lower_bound = expected_value
        upper_bound = expected_value
        passed = rule_parameters.actual_value == expected_value
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## equals integer
Data quality rule that verifies that a data quality check readout equals a given integer value, with an expected value preconfigured as 1.

**Rule summary**

The equals integer data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/equals_integer`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/equals_integer.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/equals_integer.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`expected_value`</span>|Expected value for the actual_value returned by the sensor. It must be an integer value. The default value is 1.|*long*|:material-check-bold:||




**Rule definition YAML**

The rule definition YAML file *comparison/equals_integer.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
          be an integer value. The default value is 1.
        data_type: long
        required: true
        default_value: 1
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/equals_integer* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/equals_integer.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class EqualsIntegerRuleParametersSpec:
        expected_value: int
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
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
            return RuleExecutionResult()
    
        expected_value = rule_parameters.parameters.expected_value
        lower_bound = expected_value
        upper_bound = expected_value
        passed = rule_parameters.actual_value == expected_value
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    ```



---

## import severity
A dummy data quality rule that always fails. It is activated on an *import_custom_result* data quality check that imports data quality results from different data quality libraries
 directly from logging tables.

**Rule summary**

The import severity data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/import_severity`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/import_severity.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/import_severity.py) |





**Rule definition YAML**

The rule definition YAML file *comparison/import_severity.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
    apiVersion: dqo/v1
    kind: rule
    spec:
      type: python
      java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
      mode: current_value
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/import_severity* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/import_severity.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class ImportSeverityRuleParametersSpec:
        pass
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readouts: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: ImportSeverityRuleParametersSpec
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
    
        return RuleExecutionResult(False)
    
    ```



---

## max
Data quality rule that verifies if a data quality check readsout is less or equal a maximum value.

**Rule summary**

The max data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/max`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/max.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/max.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`max_value`</span>|Maximum accepted value for the actual_value returned by the sensor (inclusive).|*double*|:material-check-bold:||




**Rule definition YAML**

The rule definition YAML file *comparison/max.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
        required: true
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/max* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/max.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class MaxRuleParametersSpec:
        max_value: float
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
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
            return RuleExecutionResult()
    
        expected_value = rule_parameters.parameters.max_value
        lower_bound = None
        upper_bound = rule_parameters.parameters.max_value
        passed = rule_parameters.actual_value <= upper_bound
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## max count
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.

**Rule summary**

The max count data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/max_count`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/max_count.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/max_count.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`max_count`</span>|Maximum accepted value for the actual_value returned by the sensor (inclusive).|*long*|:material-check-bold:||




**Rule definition YAML**

The rule definition YAML file *comparison/max_count.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
        required: true
        default_value: 0
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/max_count* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/max_count.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class MaxCountRuleParametersSpec:
        max_count: int
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
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
            return RuleExecutionResult()
    
        expected_value = rule_parameters.parameters.max_count
        lower_bound = None
        upper_bound = rule_parameters.parameters.max_count
        passed = rule_parameters.actual_value <= upper_bound
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## max days
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.

**Rule summary**

The max days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/max_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/max_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/max_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`max_days`</span>|Maximum accepted value for the actual_value returned by the sensor (inclusive).|*double*|:material-check-bold:||




**Rule definition YAML**

The rule definition YAML file *comparison/max_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
        required: true
        default_value: 1.0
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/max_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/max_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class MaxValueRuleParametersSpec:
        max_days: float
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
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
            return RuleExecutionResult()
    
        expected_value = rule_parameters.parameters.max_days
        lower_bound = None
        upper_bound = rule_parameters.parameters.max_days
        passed = rule_parameters.actual_value <= upper_bound
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## max failures
Data quality rule that verifies if the number of executive failures (the sensor returned 0) is below the max_failures. The default maximum failures is 0 failures (the first failure is reported).

**Rule summary**

The max failures data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/max_failures`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/max_failures.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/max_failures.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`max_failures`</span>|Maximum number of consecutive days with check failures. A check is failed when a sensor query fails due to a connection error, missing or corrupted table.|*long*|:material-check-bold:||




**Rule definition YAML**

The rule definition YAML file *comparison/max_failures.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
        min_periods_with_readouts: 0
        historic_data_point_grouping: last_n_readouts
      fields:
      - field_name: max_failures
        display_name: max_failures
        help_text: "Maximum number of consecutive days with check failures. A check is\
          \ failed when a sensor query fails due to a connection error, missing or corrupted\
          \ table."
        data_type: long
        required: true
        default_value: 0
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/max_failures* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/max_failures.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class MaxFailuresRuleParametersSpec:
        max_failures: int
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
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



---

## max missing
Data quality rule that verifies the results of the data quality checks that count the number of values
 present in a column, comparing it to a list of expected values. The rule compares the count of expected values (received as expected_value)
 to the count of values found in the column (as the actual_value). The rule fails when the difference is higher than
 the expected max_missing, which is the maximum difference between the expected_value (the count of values in the expected_values list)
 and the actual number of values found in the column that match the list.

**Rule summary**

The max missing data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/max_missing`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/max_missing.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/max_missing.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`max_missing`</span>|The maximum number of values from the expected_values list that were not found in the column (inclusive).|*long*|:material-check-bold:||




**Rule definition YAML**

The rule definition YAML file *comparison/max_missing.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
        required: true
        default_value: 0
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/max_missing* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/max_missing.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class MaxMissingRuleParametersSpec:
        max_missing: int
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
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



---

## max percent
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.

**Rule summary**

The max percent data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/max_percent`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/max_percent.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/max_percent.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`max_percent`</span>|Maximum accepted value for the actual_value returned by the sensor (inclusive).|*double*|:material-check-bold:||




**Rule definition YAML**

The rule definition YAML file *comparison/max_percent.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
        required: true
        default_value: 0.0
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/max_percent* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/max_percent.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class MaxPercentRuleParametersSpec:
        max_percent: float
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
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
            return RuleExecutionResult()
    
        expected_value = rule_parameters.parameters.max_percent
        lower_bound = None
        upper_bound = rule_parameters.parameters.max_percent
        passed = rule_parameters.actual_value <= upper_bound
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## max value
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.

**Rule summary**

The max value data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/max_value`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/max_value.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/max_value.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`max_value`</span>|Maximum accepted value for the actual_value returned by the sensor (inclusive).|*double*|:material-check-bold:||




**Rule definition YAML**

The rule definition YAML file *comparison/max_value.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
        required: true
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/max_value* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/max_value.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class MaxValueRuleParametersSpec:
        max_value: float
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
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
            return RuleExecutionResult()
    
        expected_value = rule_parameters.parameters.max_value
        lower_bound = None
        upper_bound = rule_parameters.parameters.max_value
        passed = rule_parameters.actual_value <= upper_bound
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## min
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.

**Rule summary**

The min data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/min`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/min.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/min.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`min_value`</span>|Minimum accepted value for the actual_value returned by the sensor (inclusive).|*double*|:material-check-bold:||




**Rule definition YAML**

The rule definition YAML file *comparison/min.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
        required: true
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/min* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/min.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class MinRuleParametersSpec:
        min_value: float
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
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
            return RuleExecutionResult()
    
        expected_value = rule_parameters.parameters.min_value
        lower_bound = rule_parameters.parameters.min_value
        upper_bound = None
        passed = rule_parameters.actual_value >= lower_bound
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## min count
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.

**Rule summary**

The min count data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/min_count`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/min_count.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/min_count.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`min_count`</span>|Minimum accepted value for the actual_value returned by the sensor (inclusive).|*long*|:material-check-bold:||




**Rule definition YAML**

The rule definition YAML file *comparison/min_count.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
        required: true
        default_value: 1
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/min_count* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/min_count.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class MinCountRuleParametersSpec:
        min_count: int
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
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
            return RuleExecutionResult()
    
        expected_value = rule_parameters.parameters.min_count
        lower_bound = rule_parameters.parameters.min_count
        upper_bound = None
        passed = rule_parameters.actual_value >= lower_bound
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## min percent
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.

**Rule summary**

The min percent data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/min_percent`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/min_percent.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/min_percent.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`min_percent`</span>|Minimum accepted value for the actual_value returned by the sensor (inclusive).|*double*|:material-check-bold:||




**Rule definition YAML**

The rule definition YAML file *comparison/min_percent.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
        required: true
        default_value: 100.0
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/min_percent* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/min_percent.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class MinPercentRuleParametersSpec:
        min_percent: float
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
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
            return RuleExecutionResult()
    
        expected_value = rule_parameters.parameters.min_percent
        lower_bound = rule_parameters.parameters.min_percent
        upper_bound = None
        passed = rule_parameters.actual_value >= lower_bound
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## min value
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.

**Rule summary**

The min value data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/min_value`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/min_value.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/min_value.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`min_value`</span>|Minimum accepted value for the actual_value returned by the sensor (inclusive).|*double*|:material-check-bold:||




**Rule definition YAML**

The rule definition YAML file *comparison/min_value.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
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
        required: true
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/min_value* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/min_value.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class MinValueRuleParametersSpec:
        min_value: float
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
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
            return RuleExecutionResult()
    
        expected_value = rule_parameters.parameters.min_value
        lower_bound = rule_parameters.parameters.min_value
        upper_bound = None
        passed = rule_parameters.actual_value >= lower_bound
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## pass
A dummy data quality rule that always passes.

**Rule summary**

The pass data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/pass`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/pass.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/pass.py) |





**Rule definition YAML**

The rule definition YAML file *comparison/pass.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json
    apiVersion: dqo/v1
    kind: rule
    spec:
      type: python
      java_class_name: com.dqops.execution.rules.runners.python.PythonRuleRunner
      mode: current_value
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/pass* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/pass.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class PassRuleParametersSpec:
        pass
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readouts: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: PassRuleParametersSpec
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
    
        return RuleExecutionResult(True)
    
    ```



---

## value changed
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.

**Rule summary**

The value changed data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| comparison | <span class="no-wrap-code">`comparison/value_changed`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/value_changed.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/comparison/value_changed.py) |





**Rule definition YAML**

The rule definition YAML file *comparison/value_changed.dqorule.yaml* with the time window and rule parameters configuration is shown below.

??? abstract "Please expand to see the content of the .dqorule.yaml file"

    ``` { .yaml linenums="1" }
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






**Rule source code**

Please expand the section below to see the Python source code for the *comparison/value_changed* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/comparison/value_changed.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
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
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    
    class ValueChangedRuleParametersSpec:
        pass
    
    
    class HistoricDataPoint:
        timestamp_utc: datetime
        local_datetime: datetime
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        max_periods_with_readouts: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: ValueChangedRuleParametersSpec
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




## What's next
- Learn how the [data quality rules](../../dqo-concepts/definition-of-data-quality-rules.md) are defined in DQOps and what how to create custom rules
- Understand how DQOps [runs data quality checks](../../dqo-concepts/architecture/data-quality-check-execution-flow.md), calling rules
