---
title: DQOps data quality averages rules
---
# DQOps data quality averages rules
The list of averages [data quality rules](../../dqo-concepts/definition-of-data-quality-rules.md) supported by DQOps. The source code is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/averages* folder in the DQOps distribution.


---

## between percent moving average 30 days
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average within a time window.

**Rule summary**

The between percent moving average 30 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| averages | <span class="no-wrap-code">`averages/between_percent_moving_average_30_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/averages/between_percent_moving_average_30_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/averages/between_percent_moving_average_30_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`max_percent_above`</span>|The maximum percentage (e.g., 3%) by which the current sensor readout can be above a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|*double*| ||
|<span class="no-wrap-code">`max_percent_below`</span>|The maximum percentage (e.g., 3%) by which the current sensor readout can be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|*double*| ||




**Rule definition YAML**

The rule definition YAML file *averages/between_percent_moving_average_30_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
      - field_name: max_percent_above
        display_name: max_percent_above
        help_text: "The maximum percentage (e.g., 3%) by which the current sensor readout\
          \ can be above a moving average within the time window. Set the time window\
          \ at the threshold level for all severity levels (low, medium, high) at once.\
          \ The default is a 14 time periods (days, etc.) time window, but at least 7\
          \ readouts must exist to run the calculation."
        data_type: double
        default_value: 10.0
      - field_name: max_percent_below
        display_name: max_percent_below
        help_text: "The maximum percentage (e.g., 3%) by which the current sensor readout\
          \ can be below a moving average within the time window. Set the time window\
          \ at the threshold level for all severity levels (low, medium, high) at once.\
          \ The default is a 14 time periods (days, etc.) time window, but at least 7\
          \ readouts must exist to run the calculation."
        data_type: double
        default_value: 10.0
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *averages/between_percent_moving_average_30_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/averages/between_percent_moving_average_30_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
    #  Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
    #
    #  This file is licensed under the Business Source License 1.1,
    #  which can be found in the root directory of this repository.
    #
    #  Change Date: This file will be licensed under the Apache License, Version 2.0,
    #  four (4) years from its last modification date.
    
    from datetime import datetime
    from typing import Sequence
    import scipy
    import numpy as np
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class BetweenPercentMovingAverage30DaysRuleParametersSpec:
        max_percent_above: float
        max_percent_below: float
    
    
    class HistoricDataPoint:
        timestamp_utc_epoch: int
        local_datetime_epoch: int
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readouts: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: BetweenPercentMovingAverage30DaysRuleParametersSpec
        time_period_local_epoch: int
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
        if (not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'max_percent_above') or
                not hasattr(rule_parameters.parameters, 'max_percent_below')):
            return RuleExecutionResult()
    
        filtered = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]
        if len(filtered) == 0:
            return RuleExecutionResult()
    
        filtered_mean = float(np.mean(filtered))
    
        upper_bound = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_above / 100.0)
        lower_bound = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_below / 100.0)
    
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
        expected_value = filtered_mean
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## between percent moving average 60 days
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average within a time window.

**Rule summary**

The between percent moving average 60 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| averages | <span class="no-wrap-code">`averages/between_percent_moving_average_60_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/averages/between_percent_moving_average_60_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/averages/between_percent_moving_average_60_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`max_percent_above`</span>|The maximum percentage (e.g., 3%) by which the current sensor readout can be above a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|*double*| ||
|<span class="no-wrap-code">`max_percent_below`</span>|The maximum percentage (e.g., 3%) by which the current sensor readout can be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|*double*| ||




**Rule definition YAML**

The rule definition YAML file *averages/between_percent_moving_average_60_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
      - field_name: max_percent_above
        display_name: max_percent_above
        help_text: "The maximum percentage (e.g., 3%) by which the current sensor readout\
          \ can be above a moving average within the time window. Set the time window\
          \ at the threshold level for all severity levels (low, medium, high) at once.\
          \ The default is a 14 time periods (days, etc.) time window, but at least 7\
          \ readouts must exist to run the calculation."
        data_type: double
        default_value: 10.0
      - field_name: max_percent_below
        display_name: max_percent_below
        help_text: "The maximum percentage (e.g., 3%) by which the current sensor readout\
          \ can be below a moving average within the time window. Set the time window\
          \ at the threshold level for all severity levels (low, medium, high) at once.\
          \ The default is a 14 time periods (days, etc.) time window, but at least 7\
          \ readouts must exist to run the calculation."
        data_type: double
        default_value: 10.0
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *averages/between_percent_moving_average_60_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/averages/between_percent_moving_average_60_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
    #  Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
    #
    #  This file is licensed under the Business Source License 1.1,
    #  which can be found in the root directory of this repository.
    #
    #  Change Date: This file will be licensed under the Apache License, Version 2.0,
    #  four (4) years from its last modification date.
    
    from datetime import datetime
    from typing import Sequence
    import scipy
    import numpy as np
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class BetweenPercentMovingAverage60DaysRuleParametersSpec:
        max_percent_above: float
        max_percent_below: float
    
    
    class HistoricDataPoint:
        timestamp_utc_epoch: int
        local_datetime_epoch: int
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readout: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: BetweenPercentMovingAverage60DaysRuleParametersSpec
        time_period_local_epoch: int
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
        if (not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'max_percent_above') or
                not hasattr(rule_parameters.parameters, 'max_percent_below')):
            return RuleExecutionResult()
    
        filtered = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]
        if len(filtered) == 0:
            return RuleExecutionResult()
    
        filtered_mean = float(np.mean(filtered))
    
        upper_bound = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_above / 100.0)
        lower_bound = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_below / 100.0)
    
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
        expected_value = filtered_mean
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## between percent moving average 7 days
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average within a time window.

**Rule summary**

The between percent moving average 7 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| averages | <span class="no-wrap-code">`averages/between_percent_moving_average_7_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/averages/between_percent_moving_average_7_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/averages/between_percent_moving_average_7_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`max_percent_above`</span>|The maximum percentage (e.g., 3%) by which the current sensor readout can be above a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|*double*| ||
|<span class="no-wrap-code">`max_percent_below`</span>|The maximum percentage (e.g., 3%) by which the current sensor readout can be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|*double*| ||




**Rule definition YAML**

The rule definition YAML file *averages/between_percent_moving_average_7_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
      - field_name: max_percent_above
        display_name: max_percent_above
        help_text: "The maximum percentage (e.g., 3%) by which the current sensor readout\
          \ can be above a moving average within the time window. Set the time window\
          \ at the threshold level for all severity levels (low, medium, high) at once.\
          \ The default is a 14 time periods (days, etc.) time window, but at least 7\
          \ readouts must exist to run the calculation."
        data_type: double
        default_value: 10.0
      - field_name: max_percent_below
        display_name: max_percent_below
        help_text: "The maximum percentage (e.g., 3%) by which the current sensor readout\
          \ can be below a moving average within the time window. Set the time window\
          \ at the threshold level for all severity levels (low, medium, high) at once.\
          \ The default is a 14 time periods (days, etc.) time window, but at least 7\
          \ readouts must exist to run the calculation."
        data_type: double
        default_value: 10.0
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *averages/between_percent_moving_average_7_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/averages/between_percent_moving_average_7_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
    #  Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
    #
    #  This file is licensed under the Business Source License 1.1,
    #  which can be found in the root directory of this repository.
    #
    #  Change Date: This file will be licensed under the Apache License, Version 2.0,
    #  four (4) years from its last modification date.
    
    from datetime import datetime
    from typing import Sequence
    import scipy
    import numpy as np
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class BetweenPctMovingAverage7DaysRuleParametersSpec:
        max_percent_above: float
        max_percent_below: float
    
    
    class HistoricDataPoint:
        timestamp_utc_epoch: int
        local_datetime_epoch: int
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readouts: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: BetweenPctMovingAverage7DaysRuleParametersSpec
        time_period_local_epoch: int
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
        if (not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'max_percent_above') or
                not hasattr(rule_parameters.parameters, 'max_percent_below')):
            return RuleExecutionResult()
    
        filtered = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]
        if len(filtered) == 0:
            return RuleExecutionResult()
    
        filtered_mean = float(np.mean(filtered))
    
        upper_bound = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_above / 100.0)
        lower_bound = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_below / 100.0)
    
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
        expected_value = filtered_mean
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## percent moving average
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average within a time window.

**Rule summary**

The percent moving average data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| averages | <span class="no-wrap-code">`averages/percent_moving_average`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/averages/percent_moving_average.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/averages/percent_moving_average.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`max_percent_above`</span>|The maximum percentage (e.g., 3%) by which the current sensor readout can be above a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|*double*| ||
|<span class="no-wrap-code">`max_percent_below`</span>|The maximum percentage (e.g., 3%) by which the current sensor readout can be below a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|*double*| ||




**Rule definition YAML**

The rule definition YAML file *averages/percent_moving_average.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
        prediction_time_window: 10
        min_periods_with_readouts: 5
        historic_data_point_grouping: day
      fields:
      - field_name: max_percent_above
        display_name: max_percent_above
        help_text: "The maximum percentage (e.g., 3%) by which the current sensor readout\
          \ can be above a moving average within the time window. Set the time window\
          \ at the threshold level for all severity levels (warning, error, fatal) at\
          \ once. The default is a 14 time periods (days, etc.) time window, but at least\
          \ 7 readouts must exist to run the calculation."
        data_type: double
      - field_name: max_percent_below
        display_name: max_percent_below
        help_text: "The maximum percentage (e.g., 3%) by which the current sensor readout\
          \ can be below a moving average within the time window. Set the time window\
          \ at the threshold level for all severity levels (warning, error, fatal) at\
          \ once. The default is a 14 time periods (days, etc.) time window, but at least\
          \ 7 readouts must exist to run the calculation."
        data_type: double
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *averages/percent_moving_average* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/averages/percent_moving_average.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
    #  Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
    #
    #  This file is licensed under the Business Source License 1.1,
    #  which can be found in the root directory of this repository.
    #
    #  Change Date: This file will be licensed under the Apache License, Version 2.0,
    #  four (4) years from its last modification date.
    
    from datetime import datetime
    from typing import Sequence
    import scipy
    import numpy as np
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class PercentMovingAverageRuleParametersSpec:
        max_percent_above: float
        max_percent_below: float
    
    
    class HistoricDataPoint:
        timestamp_utc_epoch: int
        local_datetime_epoch: int
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readouts: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: PercentMovingAverageRuleParametersSpec
        time_period_local_epoch: int
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
        if (not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'max_percent_above') or
                not hasattr(rule_parameters.parameters, 'max_percent_below')):
            return RuleExecutionResult()
    
        filtered = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]
        if len(filtered) == 0:
            return RuleExecutionResult()
    
        filtered_mean = float(np.mean(filtered))
    
        upper_bound = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_above / 100.0)
        lower_bound = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_below / 100.0)
    
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
        expected_value = filtered_mean
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## within percent moving average 30 days
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average within a time window.

**Rule summary**

The within percent moving average 30 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| averages | <span class="no-wrap-code">`averages/within_percent_moving_average_30_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/averages/within_percent_moving_average_30_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/averages/within_percent_moving_average_30_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`max_percent_within`</span>|The maximum percentage (e.g., 3%) by which the current sensor readout can be within a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|*double*|:material-check-bold:||




**Rule definition YAML**

The rule definition YAML file *averages/within_percent_moving_average_30_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
      - field_name: max_percent_within
        display_name: max_percent_within
        help_text: "The maximum percentage (e.g., 3%) by which the current sensor readout\
          \ can be within a moving average within the time window. Set the time window\
          \ at the threshold level for all severity levels (low, medium, high) at once.\
          \ The default is a 14 time periods (days, etc.) time window, but at least 7\
          \ readouts must exist to run the calculation."
        data_type: double
        required: true
        default_value: 10.0
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *averages/within_percent_moving_average_30_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/averages/within_percent_moving_average_30_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
    #  Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
    #
    #  This file is licensed under the Business Source License 1.1,
    #  which can be found in the root directory of this repository.
    #
    #  Change Date: This file will be licensed under the Apache License, Version 2.0,
    #  four (4) years from its last modification date.
    
    from datetime import datetime
    from typing import Sequence
    import scipy
    import numpy as np
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class WithinPercentMovingAverage30DaysRuleParametersSpec:
        max_percent_within: float
    
    
    class HistoricDataPoint:
        timestamp_utc_epoch: int
        local_datetime_epoch: int
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readouts: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: WithinPercentMovingAverage30DaysRuleParametersSpec
        time_period_local_epoch: int
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
        if not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'max_percent_within'):
            return RuleExecutionResult()
    
        filtered = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]
        if len(filtered) == 0:
            return RuleExecutionResult()
    
        filtered_mean = float(np.mean(filtered))
    
        upper_bound = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_within / 100.0)
        lower_bound = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_within / 100.0)
    
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
        expected_value = filtered_mean
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## within percent moving average 60 days
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average within a time window.

**Rule summary**

The within percent moving average 60 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| averages | <span class="no-wrap-code">`averages/within_percent_moving_average_60_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/averages/within_percent_moving_average_60_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/averages/within_percent_moving_average_60_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`max_percent_within`</span>|The maximum percentage (e.q., 3%) by which the current sensor readout can be within a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a time window of 14 periods (days, etc.), but there must be at least 7 readouts to run the calculation.|*double*|:material-check-bold:||




**Rule definition YAML**

The rule definition YAML file *averages/within_percent_moving_average_60_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
      - field_name: max_percent_within
        display_name: max_percent_within
        help_text: "The maximum percentage (e.q., 3%) by which the current sensor readout\
          \ can be within a moving average within the time window. Set the time window\
          \ at the threshold level for all severity levels (low, medium, high) at once.\
          \ The default is a time window of 14 periods (days, etc.), but there must be\
          \ at least 7 readouts to run the calculation."
        data_type: double
        required: true
        default_value: 10.0
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *averages/within_percent_moving_average_60_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/averages/within_percent_moving_average_60_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
    #  Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
    #
    #  This file is licensed under the Business Source License 1.1,
    #  which can be found in the root directory of this repository.
    #
    #  Change Date: This file will be licensed under the Apache License, Version 2.0,
    #  four (4) years from its last modification date.
    
    from datetime import datetime
    from typing import Sequence
    import scipy
    import numpy as np
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class WithinPercentMovingAverage60DaysRuleParametersSpec:
        max_percent_within: float
    
    
    class HistoricDataPoint:
        timestamp_utc_epoch: int
        local_datetime_epoch: int
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readout: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: WithinPercentMovingAverage60DaysRuleParametersSpec
        time_period_local_epoch: int
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
        if not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'max_percent_within'):
            return RuleExecutionResult()
    
        filtered = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]
        if len(filtered) == 0:
            return RuleExecutionResult()
    
        filtered_mean = float(np.mean(filtered))
    
        upper_bound = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_within / 100.0)
        lower_bound = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_within / 100.0)
    
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
        expected_value = filtered_mean
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## within percent moving average 7 days
Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average within a time window.

**Rule summary**

The within percent moving average 7 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| averages | <span class="no-wrap-code">`averages/within_percent_moving_average_7_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/averages/within_percent_moving_average_7_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/averages/within_percent_moving_average_7_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`max_percent_within`</span>|The maximum percentage (e.g., 3%) by which the current sensor readout can be within a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.|*double*|:material-check-bold:||




**Rule definition YAML**

The rule definition YAML file *averages/within_percent_moving_average_7_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
      - field_name: max_percent_within
        display_name: max_percent_within
        help_text: "The maximum percentage (e.g., 3%) by which the current sensor readout\
          \ can be within a moving average within the time window. Set the time window\
          \ at the threshold level for all severity levels (low, medium, high) at once.\
          \ The default is a 14 time periods (days, etc.) time window, but at least 7\
          \ readouts must exist to run the calculation."
        data_type: double
        required: true
        default_value: 10.0
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *averages/within_percent_moving_average_7_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/averages/within_percent_moving_average_7_days.py* file in the DQOps distribution.

??? abstract "Rule source code"

    ``` { .python linenums="1" }
    #  Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
    #
    #  This file is licensed under the Business Source License 1.1,
    #  which can be found in the root directory of this repository.
    #
    #  Change Date: This file will be licensed under the Apache License, Version 2.0,
    #  four (4) years from its last modification date.
    
    from datetime import datetime
    from typing import Sequence
    import scipy
    import numpy as np
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class WithinPercentMovingAverage7DaysRuleParametersSpec:
        max_percent_within: float
    
    
    class HistoricDataPoint:
        timestamp_utc_epoch: int
        local_datetime_epoch: int
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readout: int
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: WithinPercentMovingAverage7DaysRuleParametersSpec
        time_period_local_epoch: int
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
        if not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'max_percent_within'):
            return RuleExecutionResult()
    
        filtered = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]
        if len(filtered) == 0:
            return RuleExecutionResult()
    
        filtered_mean = float(np.mean(filtered))
    
        upper_bound = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_within / 100.0)
        lower_bound = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_within / 100.0)
    
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
        expected_value = filtered_mean
    
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```




## What's next
- Learn how the [data quality rules](../../dqo-concepts/definition-of-data-quality-rules.md) are defined in DQOps and what how to create custom rules
- Understand how DQOps [runs data quality checks](../../dqo-concepts/architecture/data-quality-check-execution-flow.md), calling rules
