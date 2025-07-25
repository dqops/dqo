---
title: DQOps data quality percentile rules
---
# DQOps data quality percentile rules
The list of percentile [data quality rules](../../dqo-concepts/definition-of-data-quality-rules.md) supported by DQOps. The source code is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/percentile* folder in the DQOps distribution.


---

## anomaly differencing percentile moving average
Data quality rule that detects anomalies in time series of data quality measures that are increasing over time, such as the row count is growing.
 The rule transforms the recent data quality sensor readouts into a *differencing* stream, converting values to a difference
 from the previous value. For the following time series of row count values: &amp;#91;100, 105, 110, 116, 126, 122&amp;#93;, the differencing stream is &amp;#91;5, 5, 6, 10, -4&amp;#93;,
 which are the row count changes since the previous day.
 The rule identifies the top X% of anomalous values, based on the distribution of the changes using a standard deviation.
 The rule uses the time window of the last 90 days, but at least 30 historical measures must be present to run the calculation.

**Rule summary**

The anomaly differencing percentile moving average data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| percentile | <span class="no-wrap-code">`percentile/anomaly_differencing_percentile_moving_average`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/anomaly_differencing_percentile_moving_average.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/anomaly_differencing_percentile_moving_average.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`anomaly_percent`</span>|The probability (in percent) that the current sensor readout (measure) is an anomaly, because the value is outside the regular range of previous readouts. The default time window of 90 time periods (days, etc.) is used, but at least 30 readouts must exist to run the calculation.|*double*|:material-check-bold:||
|<span class="no-wrap-code">`use_ai`</span>|Use an AI model to predict anomalies. WARNING: anomaly detection by AI models is not supported in a trial distribution of DQOps. Please contact DQOps support to upgrade your instance to a full DQOps instance.|*boolean*| ||




**Rule definition YAML**

The rule definition YAML file *percentile/anomaly_differencing_percentile_moving_average.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
        prediction_time_window: 90
        min_periods_with_readouts: 30
        historic_data_point_grouping: day
      fields:
      - field_name: anomaly_percent
        display_name: anomaly_percent
        help_text: "The probability (in percent) that the current sensor readout (measure)\
          \ is an anomaly, because the value is outside the regular range of previous\
          \ readouts. The default time window of 90 time periods (days, etc.) is used,\
          \ but at least 30 readouts must exist to run the calculation."
        data_type: double
        required: true
        default_value: 0.05
      - field_name: use_ai
        display_name: use_ai
        help_text: "Use an AI model to predict anomalies. WARNING: anomaly detection by\
          \ AI models is not supported in an open-source distribution of DQOps. Please\
          \ contact DQOps support to upgrade your instance to a closed-source DQOps distribution."
        data_type: boolean
        display_hint: requires_paid_version
      parameters:
        degrees_of_freedom: 5
        ai_degrees_of_freedom: 8
    ```




**Additional rule parameters**

| Parameters name | Value |
|-----------------|-------|
|*degrees_of_freedom*|5|
|*ai_degrees_of_freedom*|8|



**Rule source code**

Please expand the section below to see the Python source code for the *percentile/anomaly_differencing_percentile_moving_average* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/percentile/anomaly_differencing_percentile_moving_average.py* file in the DQOps distribution.

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
    from typing import Sequence, Dict
    import numpy as np
    import scipy
    import scipy.stats
    from lib.anomalies.data_preparation import convert_historic_data_differencing, average_forecast
    from lib.anomalies.anomaly_detection import detect_upper_bound_anomaly, detect_lower_bound_anomaly, detect_anomaly
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class AnomalyDifferencingPercentileMovingAverageRuleParametersSpec:
        anomaly_percent: float
    
    
    class HistoricDataPoint:
        timestamp_utc_epoch: int
        local_datetime_epoch: int
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readouts: int
    
    
    class AnomalyConfigurationParameters:
        degrees_of_freedom: float
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: AnomalyDifferencingPercentileMovingAverageRuleParametersSpec
        time_period_local_epoch: int
        previous_readouts: Sequence[HistoricDataPoint]
        time_window: RuleTimeWindowSettingsSpec
        configuration_parameters: AnomalyConfigurationParameters
    
    
    class RuleExecutionResult:
        """
        The default object that should be returned to the DQOps engine, specifies if the rule has passed or failed,
        what is the expected value for the rule and what are the upper and lower boundaries of accepted values (optional).
        """
    
        passed: bool
        expected_value: float
        lower_bound: float
        upper_bound: float
    
        def __init__(self, passed=None, expected_value=None, lower_bound=None, upper_bound=None):
            self.passed = passed
            self.expected_value = expected_value
            self.lower_bound = lower_bound
            self.upper_bound = upper_bound
    
    
    def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
        """
        Rule evaluation method that validates the rule result.
        :param rule_parameters: Rule parameters, containing the current value to assess and optionally
                                an array of historical measures.
        :return: Object with the decision to accept or reject the value.
        """
    
        if not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'anomaly_percent'):
            return RuleExecutionResult()
    
        extracted = [(float(readouts.sensor_readout) if hasattr(readouts, 'sensor_readout') else None) for readouts in
                     rule_parameters.previous_readouts if readouts is not None]
    
        if len(extracted) == 0:
            return RuleExecutionResult()
    
        filtered = np.array(extracted, dtype=float)
        differences = np.diff(filtered)
        differences_list = differences.tolist()
        differences_std = float(scipy.stats.tstd(differences))
        differences_median = np.median(differences)
        differences_median_float = float(differences_median)
        tail = rule_parameters.parameters.anomaly_percent / 100.0 / 2
    
        last_readout = float(filtered[-1])
        actual_difference = rule_parameters.actual_value - last_readout
    
        if float(differences_std) == 0:
            return RuleExecutionResult(None if actual_difference == differences_median_float else False,
                                       last_readout + differences_median_float, last_readout + differences_median_float,
                                       last_readout + differences_median_float)
    
        if all(difference > 0 for difference in differences_list):
            # using a 0-based calculation (scale from 0)
            anomaly_data = convert_historic_data_differencing(rule_parameters.previous_readouts,
                                                 lambda readout: (readout / differences_median_float - 1.0 if readout >= differences_median_float else
                                                                  (-1.0 / (readout / differences_median_float)) + 1.0))
            threshold_upper_multiple, threshold_lower_multiple, forecast_multiple = detect_anomaly(historic_data=anomaly_data, median=0.0,
                                                                                                   tail=tail, parameters=rule_parameters)
    
            passed = True
            if threshold_upper_multiple is not None:
                threshold_upper = (threshold_upper_multiple + 1.0) * differences_median_float
                passed = actual_difference <= threshold_upper
            else:
                threshold_upper = None
    
            if threshold_lower_multiple is not None:
                threshold_lower = differences_median_float * (-1.0 / (threshold_lower_multiple - 1.0))
                passed = passed and threshold_lower <= actual_difference
            else:
                threshold_lower = None
    
            if forecast_multiple is not None:
                if forecast_multiple >= 0:
                    forecast = (forecast_multiple + 1.0) * differences_median_float
                else:
                    forecast = differences_median_float * (-1.0 / (forecast_multiple - 1.0))
            else:
                forecast = differences_median_float
    
            if forecast is not None:
                expected_value = last_readout + forecast
            else:
                expected_value = None
    
            if threshold_lower is not None:
                lower_bound = last_readout + threshold_lower
            else:
                lower_bound = None
    
            if threshold_upper is not None:
                upper_bound = last_readout + threshold_upper
            else:
                upper_bound = None
            return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
        else:
            # using unrestricted method for both positive and negative values
            anomaly_data = convert_historic_data_differencing(rule_parameters.previous_readouts,
                                                              lambda readout: readout)
            threshold_upper_result, threshold_lower_result, forecast = detect_anomaly(historic_data=anomaly_data, median=differences_median_float,
                                                                                      tail=tail, parameters=rule_parameters)
    
            passed = True
            if threshold_upper_result is not None:
                threshold_upper = threshold_upper_result
                passed = actual_difference <= threshold_upper
            else:
                threshold_upper = None
    
            if threshold_lower_result is not None:
                threshold_lower = threshold_lower_result
                passed = passed and threshold_lower <= actual_difference
            else:
                threshold_lower = None
    
            if forecast is not None:
                expected_value = last_readout + forecast
            else:
                expected_value = None
    
            if threshold_lower is not None:
                lower_bound = last_readout + threshold_lower
            else:
                lower_bound = None
    
            if threshold_upper is not None:
                upper_bound = last_readout + threshold_upper
            else:
                upper_bound = None
            return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## anomaly differencing percentile moving average 30 days
Data quality rule that detects anomalies in time series of data quality measures that are increasing over time, such as the row count is growing.
 The rule transforms the recent data quality sensor readouts into a *differencing* stream, converting values to a difference
 from the previous value. For the following time series of row count values: &amp;#91;100, 105, 110, 116, 126, 122&amp;#93;, the differencing stream is &amp;#91;5, 5, 6, 10, -4&amp;#93;,
 which are the row count changes since the previous day.
 The rule identifies the top X% of anomalous values, based on the distribution of the changes using a standard deviation.
 The rule uses the time window of the last 30 days, but at least 10 historical measures must be present to run the calculation.

**Rule summary**

The anomaly differencing percentile moving average 30 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| percentile | <span class="no-wrap-code">`percentile/anomaly_differencing_percentile_moving_average_30_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/anomaly_differencing_percentile_moving_average_30_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/anomaly_differencing_percentile_moving_average_30_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`anomaly_percent`</span>|The probability (in percent) that the current sensor readout (measure) is an anomaly, because the value is outside the regular range of previous readouts. The default time window of 30 periods (days, etc.) is required, but at least 10 readouts must exist to run the calculation.|*double*|:material-check-bold:||
|<span class="no-wrap-code">`use_ai`</span>|Use an AI model to predict anomalies. WARNING: anomaly detection by AI models is not supported in a trial distribution of DQOps. Please contact DQOps support to upgrade your instance to a full DQOps instance.|*boolean*| ||




**Rule definition YAML**

The rule definition YAML file *percentile/anomaly_differencing_percentile_moving_average_30_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
      - field_name: anomaly_percent
        display_name: anomaly_percent
        help_text: "The probability (in percent) that the current sensor readout (measure)\
          \ is an anomaly, because the value is outside the regular range of previous\
          \ readouts. The default time window of 30 periods (days, etc.) is required,\
          \ but at least 10 readouts must exist to run the calculation."
        data_type: double
        required: true
        default_value: 0.05
      - field_name: use_ai
        display_name: use_ai
        help_text: "Use an AI model to predict anomalies. WARNING: anomaly detection by\
          \ AI models is not supported in an open-source distribution of DQOps. Please\
          \ contact DQOps support to upgrade your instance to a closed-source DQOps distribution."
        data_type: boolean
        display_hint: requires_paid_version
      parameters:
        degrees_of_freedom: 5
        ai_degrees_of_freedom: 8
    ```




**Additional rule parameters**

| Parameters name | Value |
|-----------------|-------|
|*degrees_of_freedom*|5|
|*ai_degrees_of_freedom*|8|



**Rule source code**

Please expand the section below to see the Python source code for the *percentile/anomaly_differencing_percentile_moving_average_30_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/percentile/anomaly_differencing_percentile_moving_average_30_days.py* file in the DQOps distribution.

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
    from typing import Sequence, Dict
    import numpy as np
    import scipy
    import scipy.stats
    from lib.anomalies.data_preparation import convert_historic_data_differencing, average_forecast
    from lib.anomalies.anomaly_detection import detect_upper_bound_anomaly, detect_lower_bound_anomaly, detect_anomaly
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class AnomalyDifferencingPercentileMovingAverageRuleParametersSpec:
        anomaly_percent: float
    
    
    class HistoricDataPoint:
        timestamp_utc_epoch: int
        local_datetime_epoch: int
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readouts: int
    
    
    class AnomalyConfigurationParameters:
        degrees_of_freedom: float
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: AnomalyDifferencingPercentileMovingAverageRuleParametersSpec
        time_period_local_epoch: int
        previous_readouts: Sequence[HistoricDataPoint]
        time_window: RuleTimeWindowSettingsSpec
        configuration_parameters: AnomalyConfigurationParameters
    
    
    class RuleExecutionResult:
        """
        The default object that should be returned to the DQOps engine, specifies if the rule has passed or failed,
        what is the expected value for the rule and what are the upper and lower boundaries of accepted values (optional).
        """
    
        passed: bool
        expected_value: float
        lower_bound: float
        upper_bound: float
    
        def __init__(self, passed=None, expected_value=None, lower_bound=None, upper_bound=None):
            self.passed = passed
            self.expected_value = expected_value
            self.lower_bound = lower_bound
            self.upper_bound = upper_bound
    
    
    def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
        """
        Rule evaluation method that validates the rule result.
        :param rule_parameters: Rule parameters, containing the current value to assess and optionally
                                an array of historical measures.
        :return: Object with the decision to accept or reject the value.
        """
    
        if not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'anomaly_percent'):
            return RuleExecutionResult()
    
        extracted = [(float(readouts.sensor_readout) if hasattr(readouts, 'sensor_readout') else None) for readouts in
                     rule_parameters.previous_readouts if readouts is not None]
    
        if len(extracted) == 0:
            return RuleExecutionResult()
    
        filtered = np.array(extracted, dtype=float)
        differences = np.diff(filtered)
        differences_list = differences.tolist()
        differences_std = float(scipy.stats.tstd(differences))
        differences_median = np.median(differences)
        differences_median_float = float(differences_median)
        tail = rule_parameters.parameters.anomaly_percent / 100.0 / 2
    
        last_readout = float(filtered[-1])
        actual_difference = rule_parameters.actual_value - last_readout
    
        if float(differences_std) == 0:
            return RuleExecutionResult(None if actual_difference == differences_median_float else False,
                                       last_readout + differences_median_float, last_readout + differences_median_float,
                                       last_readout + differences_median_float)
    
        if all(difference > 0 for difference in differences_list):
            # using a 0-based calculation (scale from 0)
            anomaly_data = convert_historic_data_differencing(rule_parameters.previous_readouts,
                                                              lambda readout: (readout / differences_median_float - 1.0 if readout >= differences_median_float else
                                                                               (-1.0 / (readout / differences_median_float)) + 1.0))
            threshold_upper_multiple, threshold_lower_multiple, forecast_multiple = detect_anomaly(historic_data=anomaly_data, median=0.0,
                                                                                                   tail=tail, parameters=rule_parameters)
    
            passed = True
            if threshold_upper_multiple is not None:
                threshold_upper = (threshold_upper_multiple + 1.0) * differences_median_float
                passed = actual_difference <= threshold_upper
            else:
                threshold_upper = None
    
            if threshold_lower_multiple is not None:
                threshold_lower = differences_median_float * (-1.0 / (threshold_lower_multiple - 1.0))
                passed = passed and threshold_lower <= actual_difference
            else:
                threshold_lower = None
    
            if forecast_multiple is not None:
                if forecast_multiple >= 0:
                    forecast = (forecast_multiple + 1.0) * differences_median_float
                else:
                    forecast = differences_median_float * (-1.0 / (forecast_multiple - 1.0))
            else:
                forecast = differences_median_float
    
            if forecast is not None:
                expected_value = last_readout + forecast
            else:
                expected_value = None
    
            if threshold_lower is not None:
                lower_bound = last_readout + threshold_lower
            else:
                lower_bound = None
    
            if threshold_upper is not None:
                upper_bound = last_readout + threshold_upper
            else:
                upper_bound = None
            return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
        else:
            # using unrestricted method for both positive and negative values
            anomaly_data = convert_historic_data_differencing(rule_parameters.previous_readouts,
                                                              lambda readout: readout)
            threshold_upper_result, threshold_lower_result, forecast = detect_anomaly(historic_data=anomaly_data, median=differences_median_float,
                                                                                      tail=tail, parameters=rule_parameters)
    
            passed = True
            if threshold_upper_result is not None:
                threshold_upper = threshold_upper_result
                passed = actual_difference <= threshold_upper
            else:
                threshold_upper = None
    
            if threshold_lower_result is not None:
                threshold_lower = threshold_lower_result
                passed = passed and threshold_lower <= actual_difference
            else:
                threshold_lower = None
    
            if forecast is not None:
                expected_value = last_readout + forecast
            else:
                expected_value = None
    
            if threshold_lower is not None:
                lower_bound = last_readout + threshold_lower
            else:
                lower_bound = None
    
            if threshold_upper is not None:
                upper_bound = last_readout + threshold_upper
            else:
                upper_bound = None
            return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## anomaly partition row count
Data quality rule that detects anomalies on the row count of daily partitions.
 The rule identifies the top X% of anomalous values, based on the distribution of the changes using a standard deviation.
 The rule uses the time window of the last 90 days, but at least 30 historical measures must be present to run the calculation.

**Rule summary**

The anomaly partition row count data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| percentile | <span class="no-wrap-code">`percentile/anomaly_partition_row_count`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/anomaly_partition_row_count.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/anomaly_partition_row_count.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`anomaly_percent`</span>|The probability (in percent) that the current daily row count is an anomaly because the value is outside the regular range of previous partition volume measures. The default time window of 90 time periods (days, etc.) is used, but at least 30 readouts must exist to run the calculation.|*double*|:material-check-bold:||
|<span class="no-wrap-code">`use_ai`</span>|Use an AI model to predict anomalies. WARNING: anomaly detection by AI models is not supported in a trial distribution of DQOps. Please contact DQOps support to upgrade your instance to a full DQOps instance.|*boolean*| ||




**Rule definition YAML**

The rule definition YAML file *percentile/anomaly_partition_row_count.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
        prediction_time_window: 90
        min_periods_with_readouts: 30
        historic_data_point_grouping: day
      fields:
      - field_name: anomaly_percent
        display_name: anomaly_percent
        help_text: "The probability (in percent) that the current daily row count is an\
          \ anomaly because the value is outside the regular range of previous partition\
          \ volume measures. The default time window of 90 time periods (days, etc.) is\
          \ used, but at least 30 readouts must exist to run the calculation."
        data_type: double
        required: true
        default_value: 0.05
      - field_name: use_ai
        display_name: use_ai
        help_text: "Use an AI model to predict anomalies. WARNING: anomaly detection by\
          \ AI models is not supported in an open-source distribution of DQOps. Please\
          \ contact DQOps support to upgrade your instance to a closed-source DQOps distribution."
        data_type: boolean
        display_hint: requires_paid_version
      parameters:
        degrees_of_freedom: 5
        ai_degrees_of_freedom: 8
    ```




**Additional rule parameters**

| Parameters name | Value |
|-----------------|-------|
|*degrees_of_freedom*|5|
|*ai_degrees_of_freedom*|8|



**Rule source code**

Please expand the section below to see the Python source code for the *percentile/anomaly_partition_row_count* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/percentile/anomaly_partition_row_count.py* file in the DQOps distribution.

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
    import numpy as np
    import scipy
    import scipy.stats
    from lib.anomalies.data_preparation import convert_historic_data_stationary, average_forecast
    from lib.anomalies.anomaly_detection import detect_upper_bound_anomaly, detect_lower_bound_anomaly, detect_anomaly
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class AnomalyPartitionRowCountRuleParametersSpec:
        anomaly_percent: float
    
    
    class HistoricDataPoint:
        timestamp_utc_epoch: int
        local_datetime_epoch: int
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readouts: int
    
    
    class AnomalyConfigurationParameters:
        degrees_of_freedom: float
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: AnomalyPartitionRowCountRuleParametersSpec
        time_period_local_epoch: int
        previous_readouts: Sequence[HistoricDataPoint]
        time_window: RuleTimeWindowSettingsSpec
        configuration_parameters: AnomalyConfigurationParameters
    
    
    class RuleExecutionResult:
        """
        The default object that should be returned to the DQOps engine, specifies if the rule has passed or failed,
        what is the expected value for the rule and what are the upper and lower boundaries of accepted values (optional).
        """
    
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
        """
        Rule evaluation method that validates the rule result.
        :param rule_parameters: Rule parameters, containing the current value to assess and optionally
                                an array of historical measures.
        :return: Object with the decision to accept or reject the value.
        """
    
        if not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'anomaly_percent'):
            return RuleExecutionResult()
    
        extracted = [(float(readouts.sensor_readout) if hasattr(readouts, 'sensor_readout') else None) for readouts in
                     rule_parameters.previous_readouts if readouts is not None]
    
        if len(extracted) == 0:
            return RuleExecutionResult()
    
        filtered = np.array(extracted, dtype=float)
        filtered_median = np.median(filtered)
        filtered_median_float = float(filtered_median)
        filtered_std = scipy.stats.tstd(filtered)
    
        if float(filtered_std) == 0:
            return RuleExecutionResult(None if rule_parameters.actual_value == filtered_median_float else False,
                                       filtered_median_float, filtered_median_float, filtered_median_float)
    
        tail = rule_parameters.parameters.anomaly_percent / 100.0 / 2.0
    
        anomaly_data = convert_historic_data_stationary(rule_parameters.previous_readouts,
                                             lambda readout: (readout / filtered_median_float - 1.0 if readout >= filtered_median_float else
                                                              (-1.0 / (readout / filtered_median_float)) + 1.0))
        threshold_upper_multiple, threshold_lower_multiple, forecast_multiple = detect_anomaly(historic_data=anomaly_data, median=0.0,
                                                                                               tail=tail, parameters=rule_parameters)
    
        passed = True
        if threshold_upper_multiple is not None:
            threshold_upper = (threshold_upper_multiple + 1.0) * filtered_median_float
            passed = rule_parameters.actual_value <= threshold_upper
        else:
            threshold_upper = None
    
        if threshold_lower_multiple is not None:
            threshold_lower = filtered_median_float * (-1.0 / (threshold_lower_multiple - 1.0))
            passed = passed and threshold_lower <= rule_parameters.actual_value
        else:
            threshold_lower = None
    
        if forecast_multiple is not None:
            if forecast_multiple >= 0:
                forecast = (forecast_multiple + 1.0) * filtered_median_float
            else:
                forecast = filtered_median_float * (-1.0 / (forecast_multiple - 1.0))
        else:
            forecast = filtered_median_float
    
        expected_value = forecast
        lower_bound = threshold_lower
        upper_bound = threshold_upper
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## anomaly stationary count values
Data quality rule that detects anomalies in a stationary time series of counts of values.
 The rule identifies the top X% of anomalous values, based on the distribution of the changes using a standard deviation.
 The rule uses the time window of the last 90 days, but at least 30 historical measures must be present to run the calculation.

**Rule summary**

The anomaly stationary count values data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| percentile | <span class="no-wrap-code">`percentile/anomaly_stationary_count_values`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/anomaly_stationary_count_values.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/anomaly_stationary_count_values.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`anomaly_percent`</span>|The probability (in percent) that the count of values (records) is an anomaly because the value is outside the regular range of counts. The default time window of 90 time periods (days, etc.) is used, but at least 30 readouts must exist to run the calculation.|*double*|:material-check-bold:||
|<span class="no-wrap-code">`use_ai`</span>|Use an AI model to predict anomalies. WARNING: anomaly detection by AI models is not supported in a trial distribution of DQOps. Please contact DQOps support to upgrade your instance to a full DQOps instance.|*boolean*| ||




**Rule definition YAML**

The rule definition YAML file *percentile/anomaly_stationary_count_values.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
        prediction_time_window: 90
        min_periods_with_readouts: 30
        historic_data_point_grouping: day
      fields:
      - field_name: anomaly_percent
        display_name: anomaly_percent
        help_text: "The probability (in percent) that the count of values (records) is\
          \ an anomaly because the value is outside the regular range of counts. The default\
          \ time window of 90 time periods (days, etc.) is used, but at least 30 readouts\
          \ must exist to run the calculation."
        data_type: double
        required: true
        default_value: 0.05
      - field_name: use_ai
        display_name: use_ai
        help_text: "Use an AI model to predict anomalies. WARNING: anomaly detection by\
          \ AI models is not supported in an open-source distribution of DQOps. Please\
          \ contact DQOps support to upgrade your instance to a closed-source DQOps distribution."
        data_type: boolean
        display_hint: requires_paid_version
      parameters:
        degrees_of_freedom: 5
        ai_degrees_of_freedom: 8
    ```




**Additional rule parameters**

| Parameters name | Value |
|-----------------|-------|
|*degrees_of_freedom*|5|
|*ai_degrees_of_freedom*|8|



**Rule source code**

Please expand the section below to see the Python source code for the *percentile/anomaly_stationary_count_values* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/percentile/anomaly_stationary_count_values.py* file in the DQOps distribution.

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
    import numpy as np
    import scipy
    import scipy.stats
    from lib.anomalies.data_preparation import convert_historic_data_stationary, average_forecast
    from lib.anomalies.anomaly_detection import detect_upper_bound_anomaly, detect_lower_bound_anomaly, detect_anomaly
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class AnomalyPartitionDistinctCountRuleParametersSpec:
        anomaly_percent: float
    
    
    class HistoricDataPoint:
        timestamp_utc_epoch: int
        local_datetime_epoch: int
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readouts: int
    
    
    class AnomalyConfigurationParameters:
        degrees_of_freedom: float
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: AnomalyPartitionDistinctCountRuleParametersSpec
        time_period_local_epoch: int
        previous_readouts: Sequence[HistoricDataPoint]
        time_window: RuleTimeWindowSettingsSpec
        configuration_parameters: AnomalyConfigurationParameters
    
    
    class RuleExecutionResult:
        """
        The default object that should be returned to the DQOps engine, specifies if the rule has passed or failed,
        what is the expected value for the rule and what are the upper and lower boundaries of accepted values (optional).
        """
    
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
        """
        Rule evaluation method that validates the rule result.
        :param rule_parameters: Rule parameters, containing the current value to assess and optionally
                                an array of historical measures.
        :return: Object with the decision to accept or reject the value.
        """
    
        if not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'anomaly_percent'):
            return RuleExecutionResult()
    
        all_extracted = [(float(readouts.sensor_readout) if hasattr(readouts, 'sensor_readout') else None) for readouts in
                         rule_parameters.previous_readouts if readouts is not None]
        extracted = [readout for readout in all_extracted if readout > 0]
    
        if len(extracted) < 30:
            return RuleExecutionResult()
    
        filtered = np.array(extracted, dtype=float)
        filtered_median = np.median(filtered)
        filtered_median_float = float(filtered_median)
        filtered_std = scipy.stats.tstd(filtered)
    
        if float(filtered_std) == 0:
            return RuleExecutionResult(None if (rule_parameters.actual_value == filtered_median_float or
                                       (rule_parameters.actual_value == 0 and 0 in all_extracted)) else False,
                                       filtered_median_float, filtered_median_float if 0 not in all_extracted else 0,
                                       filtered_median_float)
    
        tail = rule_parameters.parameters.anomaly_percent / 100.0 / 2.0
    
        anomaly_data = convert_historic_data_stationary(rule_parameters.previous_readouts,
                                             lambda readout: (readout / filtered_median_float - 1.0 if readout >= filtered_median_float else
                                                             (-1.0 / (readout / filtered_median_float)) + 1.0))
        threshold_upper_multiple, threshold_lower_multiple, forecast_multiple = detect_anomaly(historic_data=anomaly_data, median=0.0,
                                                                                               tail=tail, parameters=rule_parameters)
    
        passed = True
        if threshold_upper_multiple is not None:
            threshold_upper = (threshold_upper_multiple + 1.0) * filtered_median_float
            passed = rule_parameters.actual_value <= threshold_upper
        else:
            threshold_upper = None
    
        if threshold_lower_multiple is not None:
            threshold_lower = filtered_median_float * (-1.0 / (threshold_lower_multiple - 1.0))
            passed = passed and threshold_lower <= rule_parameters.actual_value
        else:
            threshold_lower = None
    
        if forecast_multiple is not None:
            if forecast_multiple >= 0:
                forecast = (forecast_multiple + 1.0) * filtered_median_float
            else:
                forecast = filtered_median_float * (-1.0 / (forecast_multiple - 1.0))
        else:
            forecast = filtered_median_float
    
        expected_value = forecast
        lower_bound = threshold_lower
        upper_bound = threshold_upper
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## anomaly stationary percent values
Data quality rule that detects anomalies in a stationary time series of percentage values (in the range 0..100).
 The rule identifies the top X% of anomalous values, based on the distribution of the changes using a standard deviation.
 The rule uses the time window of the last 90 days, but at least 30 historical measures must be present to run the calculation.

**Rule summary**

The anomaly stationary percent values data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| percentile | <span class="no-wrap-code">`percentile/anomaly_stationary_percent_values`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/anomaly_stationary_percent_values.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/anomaly_stationary_percent_values.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`anomaly_percent`</span>|The probability (in percent) that the current percentage value is an anomaly because the value is outside the regular range of captured percentage measures. The default time window of 90 time periods (days, etc.) is used, but at least 30 readouts must exist to run the calculation.|*double*|:material-check-bold:||
|<span class="no-wrap-code">`use_ai`</span>|Use an AI model to predict anomalies. WARNING: anomaly detection by AI models is not supported in a trial distribution of DQOps. Please contact DQOps support to upgrade your instance to a full DQOps instance.|*boolean*| ||




**Rule definition YAML**

The rule definition YAML file *percentile/anomaly_stationary_percent_values.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
        prediction_time_window: 90
        min_periods_with_readouts: 30
        historic_data_point_grouping: day
      fields:
      - field_name: anomaly_percent
        display_name: anomaly_percent
        help_text: "The probability (in percent) that the current percentage value is\
          \ an anomaly because the value is outside the regular range of captured percentage\
          \ measures. The default time window of 90 time periods (days, etc.) is used,\
          \ but at least 30 readouts must exist to run the calculation."
        data_type: double
        required: true
        default_value: 0.05
      - field_name: use_ai
        display_name: use_ai
        help_text: "Use an AI model to predict anomalies. WARNING: anomaly detection by\
          \ AI models is not supported in an open-source distribution of DQOps. Please\
          \ contact DQOps support to upgrade your instance to a closed-source DQOps distribution."
        data_type: boolean
        display_hint: requires_paid_version
      parameters:
        degrees_of_freedom: 5
        ai_degrees_of_freedom: 8
    ```




**Additional rule parameters**

| Parameters name | Value |
|-----------------|-------|
|*degrees_of_freedom*|5|
|*ai_degrees_of_freedom*|8|



**Rule source code**

Please expand the section below to see the Python source code for the *percentile/anomaly_stationary_percent_values* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/percentile/anomaly_stationary_percent_values.py* file in the DQOps distribution.

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
    import numpy as np
    import scipy
    import scipy.stats
    from lib.anomalies.data_preparation import convert_historic_data_stationary, average_forecast
    from lib.anomalies.anomaly_detection import detect_upper_bound_anomaly, detect_lower_bound_anomaly
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class AnomalyPercentageValueRuleParametersSpec:
        anomaly_percent: float
    
    
    class HistoricDataPoint:
        timestamp_utc_epoch: int
        local_datetime_epoch: int
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readouts: int
    
    
    class AnomalyConfigurationParameters:
        degrees_of_freedom: float
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: AnomalyPercentageValueRuleParametersSpec
        time_period_local_epoch: int
        previous_readouts: Sequence[HistoricDataPoint]
        time_window: RuleTimeWindowSettingsSpec
        configuration_parameters: AnomalyConfigurationParameters
    
    
    class RuleExecutionResult:
        """
        The default object that should be returned to the DQOps engine, specifies if the rule has passed or failed,
        what is the expected value for the rule and what are the upper and lower boundaries of accepted values (optional).
        """
    
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
        """
        Rule evaluation method that validates the rule result.
        :param rule_parameters: Rule parameters, containing the current value to assess and optionally
                                an array of historical measures.
        :return: Object with the decision to accept or reject the value.
        """
    
        if not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'anomaly_percent'):
            return RuleExecutionResult()
    
        all_extracted = [(float(readouts.sensor_readout) if hasattr(readouts, 'sensor_readout') else None) for readouts in
                         rule_parameters.previous_readouts if readouts is not None]
        extracted = [readout for readout in all_extracted if 0.0 < readout < 100.0]
    
        if len(extracted) < 30:
            return RuleExecutionResult()
    
        filtered = np.array(extracted, dtype=float)
        filtered_median = np.median(filtered)
        filtered_median_float = float(filtered_median)
        filtered_std = scipy.stats.tstd(filtered)
    
        if float(filtered_std) == 0.0:
            return RuleExecutionResult(None if (rule_parameters.actual_value == filtered_median_float or
                                       (rule_parameters.actual_value == 0.0 and 0.0 in all_extracted) or
                                       (rule_parameters.actual_value == 100.0 and 100.0 in all_extracted)) else False,
                                       filtered_median_float,
                                       filtered_median_float if 0.0 not in all_extracted else 0.0,
                                       filtered_median_float if 100.0 not in all_extracted else 100.0)
    
        tail = rule_parameters.parameters.anomaly_percent / 100.0 / 2.0
        passed = True
    
        if 100.0 in all_extracted:
            threshold_upper = 100.0
            forecast_upper = filtered_median_float
        else:
            anomaly_data_upper = convert_historic_data_stationary(rule_parameters.previous_readouts,
                                                                  lambda readout: 1.0 / (1.0 - readout / 100.0))
            threshold_upper_multiple, forecast_upper_multiple = detect_upper_bound_anomaly(historic_data=anomaly_data_upper,
                                                                  median=1.0 / (1.0 - filtered_median_float / 100.0),
                                                                  tail=tail, parameters=rule_parameters)
    
            if threshold_upper_multiple is not None:
                threshold_upper = 100.0 - 100.0 * (1.0 / threshold_upper_multiple)
                forecast_upper = 100.0 - 100.0 * (1.0 / forecast_upper_multiple)
                passed = rule_parameters.actual_value <= threshold_upper
            else:
                threshold_upper = None
                forecast_upper = None
    
        if 0.0 in all_extracted:
            threshold_lower = 0.0
            forecast_lower = filtered_median_float
        else:
            anomaly_data_lower = convert_historic_data_stationary(rule_parameters.previous_readouts,
                                                                  lambda readout: (-1.0 / (readout / filtered_median_float)))
            threshold_lower_multiple, forecast_lower_multiple = detect_lower_bound_anomaly(historic_data=anomaly_data_lower,
                                                                  median=-1.0,
                                                                  tail=tail, parameters=rule_parameters)
    
            if threshold_lower_multiple is not None:
                threshold_lower = filtered_median_float * (-1.0 / threshold_lower_multiple)
                forecast_lower = filtered_median_float * (-1.0 / forecast_lower_multiple)
                passed = passed and threshold_lower <= rule_parameters.actual_value
            else:
                threshold_lower = None
                forecast_lower = None
    
        expected_value = average_forecast(forecast_upper, forecast_lower)
        lower_bound = threshold_lower
        upper_bound = threshold_upper
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## anomaly stationary percentile moving average
Data quality rule that detects anomalies in time series of data quality measures that are stationary over time, such as a percentage of null values.
 Stationary measures stay within a well-known range of values.
 The rule identifies the top X% of anomalous values, based on the distribution of the changes using a standard deviation.
 The rule uses the time window of the last 90 days, but at least 30 historical measures must be present to run the calculation.

**Rule summary**

The anomaly stationary percentile moving average data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| percentile | <span class="no-wrap-code">`percentile/anomaly_stationary_percentile_moving_average`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/anomaly_stationary_percentile_moving_average.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/anomaly_stationary_percentile_moving_average.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`anomaly_percent`</span>|The probability (in percent) that the current sensor readout (measure) is an anomaly, because the value is outside the regular range of previous readouts. The default time window of 90 time periods (days, etc.) is used, but at least 30 readouts must exist to run the calculation.|*double*|:material-check-bold:||
|<span class="no-wrap-code">`use_ai`</span>|Use an AI model to predict anomalies. WARNING: anomaly detection by AI models is not supported in a trial distribution of DQOps. Please contact DQOps support to upgrade your instance to a full DQOps instance.|*boolean*| ||




**Rule definition YAML**

The rule definition YAML file *percentile/anomaly_stationary_percentile_moving_average.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
        prediction_time_window: 90
        min_periods_with_readouts: 30
        historic_data_point_grouping: day
      fields:
      - field_name: anomaly_percent
        display_name: anomaly_percent
        help_text: "The probability (in percent) that the current sensor readout (measure)\
          \ is an anomaly, because the value is outside the regular range of previous\
          \ readouts. The default time window of 90 time periods (days, etc.) is used,\
          \ but at least 30 readouts must exist to run the calculation."
        data_type: double
        required: true
        default_value: 0.05
      - field_name: use_ai
        display_name: use_ai
        help_text: "Use an AI model to predict anomalies. WARNING: anomaly detection by\
          \ AI models is not supported in an open-source distribution of DQOps. Please\
          \ contact DQOps support to upgrade your instance to a closed-source DQOps distribution."
        data_type: boolean
        display_hint: requires_paid_version
      parameters:
        degrees_of_freedom: 5
        ai_degrees_of_freedom: 8
    ```




**Additional rule parameters**

| Parameters name | Value |
|-----------------|-------|
|*degrees_of_freedom*|5|
|*ai_degrees_of_freedom*|8|



**Rule source code**

Please expand the section below to see the Python source code for the *percentile/anomaly_stationary_percentile_moving_average* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/percentile/anomaly_stationary_percentile_moving_average.py* file in the DQOps distribution.

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
    import numpy as np
    import scipy
    import scipy.stats
    from lib.anomalies.data_preparation import convert_historic_data_stationary, average_forecast
    from lib.anomalies.anomaly_detection import detect_upper_bound_anomaly, detect_lower_bound_anomaly, detect_anomaly
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class AnomalyStationaryPercentileMovingAverageRuleParametersSpec:
        anomaly_percent: float
    
    
    class HistoricDataPoint:
        timestamp_utc_epoch: int
        local_datetime_epoch: int
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readouts: int
    
    
    class AnomalyConfigurationParameters:
        degrees_of_freedom: float
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: AnomalyStationaryPercentileMovingAverageRuleParametersSpec
        time_period_local_epoch: int
        previous_readouts: Sequence[HistoricDataPoint]
        time_window: RuleTimeWindowSettingsSpec
        configuration_parameters: AnomalyConfigurationParameters
    
    
    class RuleExecutionResult:
        """
        The default object that should be returned to the DQOps engine, specifies if the rule has passed or failed,
        what is the expected value for the rule and what are the upper and lower boundaries of accepted values (optional).
        """
    
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
        """
        Rule evaluation method that validates the rule result.
        :param rule_parameters: Rule parameters, containing the current value to assess and optionally
                                an array of historical measures.
        :return: Object with the decision to accept or reject the value.
        """
    
        if not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'anomaly_percent'):
            return RuleExecutionResult()
    
        extracted = [(float(readouts.sensor_readout) if hasattr(readouts, 'sensor_readout') else None) for readouts in
                     rule_parameters.previous_readouts if readouts is not None]
    
        if len(extracted) == 0:
            return RuleExecutionResult()
    
        filtered = np.array(extracted, dtype=float)
        filtered_median = np.median(filtered)
        filtered_median_float = float(filtered_median)
        filtered_std = scipy.stats.tstd(filtered)
    
        if float(filtered_std) == 0:
            return RuleExecutionResult(None if rule_parameters.actual_value == filtered_median_float else False,
                                       filtered_median_float, filtered_median_float, filtered_median_float)
    
        tail = rule_parameters.parameters.anomaly_percent / 100.0 / 2.0
    
        if all(readout > 0 for readout in extracted):
            # using a 0-based calculation (scale from 0)
            anomaly_data = convert_historic_data_stationary(rule_parameters.previous_readouts,
                                                 lambda readout: (readout / filtered_median_float - 1.0 if readout >= filtered_median_float else
                                                                  (-1.0 / (readout / filtered_median_float)) + 1.0))
            threshold_upper_multiple, threshold_lower_multiple, forecast_multiple = detect_anomaly(historic_data=anomaly_data, median=0.0,
                                                                                                   tail=tail, parameters=rule_parameters)
    
            passed = True
            if threshold_upper_multiple is not None:
                threshold_upper = (threshold_upper_multiple + 1.0) * filtered_median_float
                passed = rule_parameters.actual_value <= threshold_upper
            else:
                threshold_upper = None
    
            if threshold_lower_multiple is not None:
                threshold_lower = filtered_median_float * (-1.0 / (threshold_lower_multiple - 1.0))
                passed = passed and threshold_lower <= rule_parameters.actual_value
            else:
                threshold_lower = None
    
            if forecast_multiple is not None:
                if forecast_multiple >= 0:
                    forecast = (forecast_multiple + 1.0) * filtered_median_float
                else:
                    forecast = filtered_median_float * (-1.0 / (forecast_multiple - 1.0))
            else:
                forecast = filtered_median_float
    
            expected_value = forecast
            lower_bound = threshold_lower
            upper_bound = threshold_upper
            return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
        else:
            # using unrestricted method
            anomaly_data = convert_historic_data_stationary(rule_parameters.previous_readouts, lambda readout: readout)
            threshold_upper_result, threshold_lower_result, forecast = detect_anomaly(historic_data=anomaly_data, median=filtered_median_float,
                                                                                      tail=tail, parameters=rule_parameters)
    
            passed = True
            if threshold_upper_result is not None:
                threshold_upper = threshold_upper_result
                passed = rule_parameters.actual_value <= threshold_upper
            else:
                threshold_upper = None
    
            if threshold_lower_result is not None:
                threshold_lower = threshold_lower_result
                passed = passed and threshold_lower <= rule_parameters.actual_value
            else:
                threshold_lower = None
    
            expected_value = forecast
            lower_bound = threshold_lower
            upper_bound = threshold_upper
            return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## anomaly stationary percentile moving average 30 days
Data quality rule that detects anomalies in time series of data quality measures that are stationary over time, such as a percentage of null values.
 Stationary measures stay within a well-known range of values.
 The rule identifies the top X% of anomalous values, based on the distribution of the changes using a standard deviation.
 The rule uses the time window of the last 30 days, but at least 10 historical measures must be present to run the calculation.

**Rule summary**

The anomaly stationary percentile moving average 30 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| percentile | <span class="no-wrap-code">`percentile/anomaly_stationary_percentile_moving_average_30_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/anomaly_stationary_percentile_moving_average_30_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/anomaly_stationary_percentile_moving_average_30_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`anomaly_percent`</span>|The probability (in percent) that the current sensor readout (measure) is an anomaly, because the value is outside the regular range of previous readouts. The default time window of 30 periods (days, etc.) is required, but at least 10 readouts must exist to run the calculation.|*double*|:material-check-bold:||
|<span class="no-wrap-code">`use_ai`</span>|Use an AI model to predict anomalies. WARNING: anomaly detection by AI models is not supported in a trial distribution of DQOps. Please contact DQOps support to upgrade your instance to a full DQOps instance.|*boolean*| ||




**Rule definition YAML**

The rule definition YAML file *percentile/anomaly_stationary_percentile_moving_average_30_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
      - field_name: anomaly_percent
        display_name: anomaly_percent
        help_text: "The probability (in percent) that the current sensor readout (measure)\
          \ is an anomaly, because the value is outside the regular range of previous\
          \ readouts. The default time window of 30 periods (days, etc.) is required,\
          \ but at least 10 readouts must exist to run the calculation."
        data_type: double
        required: true
        default_value: 0.05
      - field_name: use_ai
        display_name: use_ai
        help_text: "Use an AI model to predict anomalies. WARNING: anomaly detection by\
          \ AI models is not supported in an open-source distribution of DQOps. Please\
          \ contact DQOps support to upgrade your instance to a closed-source DQOps distribution."
        data_type: boolean
        display_hint: requires_paid_version
      parameters:
        degrees_of_freedom: 5
        ai_degrees_of_freedom: 8
    ```




**Additional rule parameters**

| Parameters name | Value |
|-----------------|-------|
|*degrees_of_freedom*|5|
|*ai_degrees_of_freedom*|8|



**Rule source code**

Please expand the section below to see the Python source code for the *percentile/anomaly_stationary_percentile_moving_average_30_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/percentile/anomaly_stationary_percentile_moving_average_30_days.py* file in the DQOps distribution.

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
    import numpy as np
    import scipy
    import scipy.stats
    from lib.anomalies.data_preparation import convert_historic_data_stationary, average_forecast
    from lib.anomalies.anomaly_detection import detect_upper_bound_anomaly, detect_lower_bound_anomaly, detect_anomaly
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class AnomalyStationaryPercentileMovingAverageRuleParametersSpec:
        anomaly_percent: float
    
    
    class HistoricDataPoint:
        timestamp_utc_epoch: int
        local_datetime_epoch: int
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readouts: int
    
    
    class AnomalyConfigurationParameters:
        degrees_of_freedom: float
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: AnomalyStationaryPercentileMovingAverageRuleParametersSpec
        time_period_local_epoch: int
        previous_readouts: Sequence[HistoricDataPoint]
        time_window: RuleTimeWindowSettingsSpec
        configuration_parameters: AnomalyConfigurationParameters
    
    
    class RuleExecutionResult:
        """
        The default object that should be returned to the DQOps engine, specifies if the rule has passed or failed,
        what is the expected value for the rule and what are the upper and lower boundaries of accepted values (optional).
        """
    
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
        """
        Rule evaluation method that validates the rule result.
        :param rule_parameters: Rule parameters, containing the current value to assess and optionally
                                an array of historical measures.
        :return: Object with the decision to accept or reject the value.
        """
    
        if not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'anomaly_percent'):
            return RuleExecutionResult()
    
        extracted = [(float(readouts.sensor_readout) if hasattr(readouts, 'sensor_readout') else None) for readouts in
                     rule_parameters.previous_readouts if readouts is not None]
    
        if len(extracted) == 0:
            return RuleExecutionResult()
    
        filtered = np.array(extracted, dtype=float)
        filtered_median = np.median(filtered)
        filtered_median_float = float(filtered_median)
        filtered_std = scipy.stats.tstd(filtered)
    
        if float(filtered_std) == 0:
            return RuleExecutionResult(None if rule_parameters.actual_value == filtered_median_float else False,
                                       filtered_median_float, filtered_median_float, filtered_median_float)
    
        tail = rule_parameters.parameters.anomaly_percent / 100.0 / 2.0
    
        if all(readout > 0 for readout in extracted):
            # using a 0-based calculation (scale from 0)
            anomaly_data = convert_historic_data_stationary(rule_parameters.previous_readouts,
                                                            lambda readout: (readout / filtered_median_float - 1.0 if readout >= filtered_median_float else
                                                                             (-1.0 / (readout / filtered_median_float)) + 1.0))
            threshold_upper_multiple, threshold_lower_multiple, forecast_multiple = detect_anomaly(historic_data=anomaly_data, median=0.0,
                                                                                                   tail=tail, parameters=rule_parameters)
    
            passed = True
            if threshold_upper_multiple is not None:
                threshold_upper = (threshold_upper_multiple + 1.0) * filtered_median_float
                passed = rule_parameters.actual_value <= threshold_upper
            else:
                threshold_upper = None
    
            if threshold_lower_multiple is not None:
                threshold_lower = filtered_median_float * (-1.0 / (threshold_lower_multiple - 1.0))
                passed = passed and threshold_lower <= rule_parameters.actual_value
            else:
                threshold_lower = None
    
            if forecast_multiple is not None:
                if forecast_multiple >= 0:
                    forecast = (forecast_multiple + 1.0) * filtered_median_float
                else:
                    forecast = filtered_median_float * (-1.0 / (forecast_multiple - 1.0))
            else:
                forecast = filtered_median_float
    
            expected_value = forecast
            lower_bound = threshold_lower
            upper_bound = threshold_upper
            return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
        else:
            # using unrestricted method
            anomaly_data = convert_historic_data_stationary(rule_parameters.previous_readouts, lambda readout: readout)
            threshold_upper_result, threshold_lower_result, forecast = detect_anomaly(historic_data=anomaly_data, median=filtered_median_float,
                                                                                      tail=tail, parameters=rule_parameters)
    
            passed = True
            if threshold_upper_result is not None:
                threshold_upper = threshold_upper_result
                passed = rule_parameters.actual_value <= threshold_upper
            else:
                threshold_upper = None
    
            if threshold_lower_result is not None:
                threshold_lower = threshold_lower_result
                passed = passed and threshold_lower <= rule_parameters.actual_value
            else:
                threshold_lower = None
    
            expected_value = forecast
            lower_bound = threshold_lower
            upper_bound = threshold_upper
            return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## anomaly timeliness delay
Data quality rule that detects anomalies in data timeliness.
 The rule identifies the top X% of anomalous values, based on the distribution of the changes using a standard deviation.
 The rule uses the time window of the last 90 days, but at least 30 historical measures must be present to run the calculation.

**Rule summary**

The anomaly timeliness delay data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| percentile | <span class="no-wrap-code">`percentile/anomaly_timeliness_delay`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/anomaly_timeliness_delay.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/anomaly_timeliness_delay.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`anomaly_percent`</span>|The probability (in percent) that the current data delay is an anomaly because the value is outside the regular range of previous delays. The default time window of 90 time periods (days, etc.) is used, but at least 30 readouts must exist to run the calculation.|*double*|:material-check-bold:||
|<span class="no-wrap-code">`use_ai`</span>|Use an AI model to predict anomalies. WARNING: anomaly detection by AI models is not supported in a trial distribution of DQOps. Please contact DQOps support to upgrade your instance to a full DQOps instance.|*boolean*| ||




**Rule definition YAML**

The rule definition YAML file *percentile/anomaly_timeliness_delay.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
        prediction_time_window: 90
        min_periods_with_readouts: 30
        historic_data_point_grouping: day
      fields:
      - field_name: anomaly_percent
        display_name: anomaly_percent
        help_text: "The probability (in percent) that the current data delay is an anomaly\
          \ because the value is outside the regular range of previous delays. The default\
          \ time window of 90 time periods (days, etc.) is used, but at least 30 readouts\
          \ must exist to run the calculation."
        data_type: double
        required: true
        default_value: 0.5
      - field_name: use_ai
        display_name: use_ai
        help_text: "Use an AI model to predict anomalies. WARNING: anomaly detection by\
          \ AI models is not supported in an open-source distribution of DQOps. Please\
          \ contact DQOps support to upgrade your instance to a closed-source DQOps distribution."
        data_type: boolean
        display_hint: requires_paid_version
      parameters:
        degrees_of_freedom: 5
        ai_degrees_of_freedom: 8
    ```




**Additional rule parameters**

| Parameters name | Value |
|-----------------|-------|
|*degrees_of_freedom*|5|
|*ai_degrees_of_freedom*|8|



**Rule source code**

Please expand the section below to see the Python source code for the *percentile/anomaly_timeliness_delay* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/percentile/anomaly_timeliness_delay.py* file in the DQOps distribution.

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
    import numpy as np
    import scipy
    import scipy.stats
    from lib.anomalies.data_preparation import convert_historic_data_stationary, average_forecast
    from lib.anomalies.anomaly_detection import detect_upper_bound_anomaly, detect_lower_bound_anomaly
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class AnomalyTimelinessDelayRuleParametersSpec:
        anomaly_percent: float
    
    
    class HistoricDataPoint:
        timestamp_utc_epoch: int
        local_datetime_epoch: int
        back_periods_index: int
        sensor_readout: float
        expected_value: float
    
    
    class RuleTimeWindowSettingsSpec:
        prediction_time_window: int
        min_periods_with_readouts: int
    
    
    class AnomalyConfigurationParameters:
        degrees_of_freedom: float
    
    
    # rule execution parameters, contains the sensor value (actual_value) and the rule parameters
    class RuleExecutionRunParameters:
        actual_value: float
        parameters: AnomalyTimelinessDelayRuleParametersSpec
        time_period_local_epoch: int
        previous_readouts: Sequence[HistoricDataPoint]
        time_window: RuleTimeWindowSettingsSpec
        configuration_parameters: AnomalyConfigurationParameters
    
    
    class RuleExecutionResult:
        """
        The default object that should be returned to the DQOps engine, specifies if the rule has passed or failed,
        what is the expected value for the rule and what are the upper and lower boundaries of accepted values (optional).
        """
    
        passed: bool
        expected_value: float
        lower_bound: float
        upper_bound: float
    
        def __init__(self, passed=None, expected_value=None, lower_bound=None, upper_bound=None):
            self.passed = passed
            self.expected_value = expected_value
            self.lower_bound = lower_bound
            self.upper_bound = upper_bound
    
    
    def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
        """
        Rule evaluation method that validates the rule result.
        :param rule_parameters: Rule parameters, containing the current value to assess and optionally
                                an array of historical measures.
        :return: Object with the decision to accept or reject the value.
        """
    
        if not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'anomaly_percent'):
            return RuleExecutionResult()
    
        all_extracted = [(float(readouts.sensor_readout) if hasattr(readouts, 'sensor_readout') else None) for readouts in
                         rule_parameters.previous_readouts if readouts is not None]
        extracted = [readout for readout in all_extracted if readout > 0]
    
        if len(extracted) < 30:
            return RuleExecutionResult()
    
        filtered = np.array(extracted, dtype=float)
        filtered_median = np.median(filtered)
        filtered_median_float = float(filtered_median)
        filtered_std = scipy.stats.tstd(filtered)
    
        if float(filtered_std) == 0:
            return RuleExecutionResult(None if rule_parameters.actual_value == filtered_median_float else False,
                                       filtered_median_float, 0.0, filtered_median_float)
    
        tail = rule_parameters.parameters.anomaly_percent / 100.0
    
        anomaly_data = convert_historic_data_stationary(rule_parameters.previous_readouts, lambda readout: readout)
        threshold_upper_multiple, forecast_upper_multiple = detect_upper_bound_anomaly(historic_data=anomaly_data, median=filtered_median_float,
                                                              tail=tail, parameters=rule_parameters)
    
        passed = True
        if threshold_upper_multiple is not None:
            threshold_upper = threshold_upper_multiple
            forecast_upper = forecast_upper_multiple
            passed = rule_parameters.actual_value <= threshold_upper
        else:
            threshold_upper = None
    
        expected_value = forecast_upper
        lower_bound = 0.0  # always, our target is to have a delay of 0.0 days
        upper_bound = threshold_upper
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
    
    ```



---

## change percentile moving 30 days
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the increments of previous values gathered
 within a time window.

**Rule summary**

The change percentile moving 30 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| percentile | <span class="no-wrap-code">`percentile/change_percentile_moving_30_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/change_percentile_moving_30_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/change_percentile_moving_30_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`percentile_above`</span>|Probability that the current sensor readout will achieve values greater than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the upper quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 30 time periods (days, etc.) time window, but at least 10 readouts must exist to run the calculation.|*double*| ||
|<span class="no-wrap-code">`percentile_below`</span>|Probability that the current sensor readout will achieve values lesser than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the lower quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 30 time periods (days, etc.) time window, but at least 10 readouts must exist to run the calculation.|*double*| ||




**Rule definition YAML**

The rule definition YAML file *percentile/change_percentile_moving_30_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
      - field_name: percentile_above
        display_name: percentile_above
        help_text: "Probability that the current sensor readout will achieve values greater\
          \ than it would be expected from the estimated distribution based on the previous\
          \ values gathered within the time window. In other words, the upper quantile\
          \ of the estimated normal distribution. Set the time window at the threshold\
          \ level for all severity levels (warning, error, fatal) at once. The default\
          \ is a 30 time periods (days, etc.) time window, but at least 10 readouts must\
          \ exist to run the calculation."
        data_type: double
      - field_name: percentile_below
        display_name: percentile_below
        help_text: "Probability that the current sensor readout will achieve values lesser\
          \ than it would be expected from the estimated distribution based on the previous\
          \ values gathered within the time window. In other words, the lower quantile\
          \ of the estimated normal distribution. Set the time window at the threshold\
          \ level for all severity levels (warning, error, fatal) at once. The default\
          \ is a 30 time periods (days, etc.) time window, but at least 10 readouts must\
          \ exist to run the calculation."
        data_type: double
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *percentile/change_percentile_moving_30_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/percentile/change_percentile_moving_30_days.py* file in the DQOps distribution.

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
    import numpy as np
    import scipy
    import scipy.stats
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class PercentileMovingRuleParametersSpec:
        percentile_above: float
        percentile_below: float
    
    
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
        parameters: PercentileMovingRuleParametersSpec
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
        if (not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'percentile_below')
                or not hasattr(rule_parameters.parameters, 'percentile_above')):
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



---

## change percentile moving 60 days
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the increments of previous values gathered
 within a time window.

**Rule summary**

The change percentile moving 60 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| percentile | <span class="no-wrap-code">`percentile/change_percentile_moving_60_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/change_percentile_moving_60_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/change_percentile_moving_60_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`percentile_above`</span>|Probability that the current sensor readout will achieve values greater than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the upper quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 60 time periods (days, etc.) time window, but at least 20 readouts must exist to run the calculation.|*double*| ||
|<span class="no-wrap-code">`percentile_below`</span>|Probability that the current sensor readout will achieve values lesser than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the lower quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 60 time periods (days, etc.) time window, but at least 20 readouts must exist to run the calculation.|*double*| ||




**Rule definition YAML**

The rule definition YAML file *percentile/change_percentile_moving_60_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
      - field_name: percentile_above
        display_name: percentile_above
        help_text: "Probability that the current sensor readout will achieve values greater\
          \ than it would be expected from the estimated distribution based on the previous\
          \ values gathered within the time window. In other words, the upper quantile\
          \ of the estimated normal distribution. Set the time window at the threshold\
          \ level for all severity levels (warning, error, fatal) at once. The default\
          \ is a 60 time periods (days, etc.) time window, but at least 20 readouts must\
          \ exist to run the calculation."
        data_type: double
      - field_name: percentile_below
        display_name: percentile_below
        help_text: "Probability that the current sensor readout will achieve values lesser\
          \ than it would be expected from the estimated distribution based on the previous\
          \ values gathered within the time window. In other words, the lower quantile\
          \ of the estimated normal distribution. Set the time window at the threshold\
          \ level for all severity levels (warning, error, fatal) at once. The default\
          \ is a 60 time periods (days, etc.) time window, but at least 20 readouts must\
          \ exist to run the calculation."
        data_type: double
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *percentile/change_percentile_moving_60_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/percentile/change_percentile_moving_60_days.py* file in the DQOps distribution.

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
    import numpy as np
    import scipy
    import scipy.stats
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class PercentileMovingRuleParametersSpec:
        percentile_above: float
        percentile_below: float
    
    
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
        parameters: PercentileMovingRuleParametersSpec
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
        if (not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'percentile_below')
                or not hasattr(rule_parameters.parameters, 'percentile_above')):
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



---

## change percentile moving 7 days
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the increments of previous values gathered
 within a time window.

**Rule summary**

The change percentile moving 7 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| percentile | <span class="no-wrap-code">`percentile/change_percentile_moving_7_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/change_percentile_moving_7_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/change_percentile_moving_7_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`percentile_above`</span>|Probability that the current sensor readout will achieve values greater than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the upper quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 7 time periods (days, etc.) time window, but at least 3 readouts must exist to run the calculation.|*double*| ||
|<span class="no-wrap-code">`percentile_below`</span>|Probability that the current sensor readout will achieve values lesser than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the lower quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 7 time periods (days, etc.) time window, but at least 3 readouts must exist to run the calculation.|*double*| ||




**Rule definition YAML**

The rule definition YAML file *percentile/change_percentile_moving_7_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
      - field_name: percentile_above
        display_name: percentile_above
        help_text: "Probability that the current sensor readout will achieve values greater\
          \ than it would be expected from the estimated distribution based on the previous\
          \ values gathered within the time window. In other words, the upper quantile\
          \ of the estimated normal distribution. Set the time window at the threshold\
          \ level for all severity levels (warning, error, fatal) at once. The default\
          \ is a 7 time periods (days, etc.) time window, but at least 3 readouts must\
          \ exist to run the calculation."
        data_type: double
      - field_name: percentile_below
        display_name: percentile_below
        help_text: "Probability that the current sensor readout will achieve values lesser\
          \ than it would be expected from the estimated distribution based on the previous\
          \ values gathered within the time window. In other words, the lower quantile\
          \ of the estimated normal distribution. Set the time window at the threshold\
          \ level for all severity levels (warning, error, fatal) at once. The default\
          \ is a 7 time periods (days, etc.) time window, but at least 3 readouts must\
          \ exist to run the calculation."
        data_type: double
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *percentile/change_percentile_moving_7_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/percentile/change_percentile_moving_7_days.py* file in the DQOps distribution.

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
    import numpy as np
    import scipy
    import scipy.stats
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class PercentileMovingRuleParametersSpec:
        percentile_above: float
        percentile_below: float
    
    
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
        parameters: PercentileMovingRuleParametersSpec
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
        if (not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'percentile_below')
                or not hasattr(rule_parameters.parameters, 'percentile_above')):
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



---

## percentile moving 30 days
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the previous values gathered within a time window.

**Rule summary**

The percentile moving 30 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| percentile | <span class="no-wrap-code">`percentile/percentile_moving_30_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/percentile_moving_30_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/percentile_moving_30_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`percentile_above`</span>|Probability that the current sensor readout will achieve values greater than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the upper quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 30 time periods (days, etc.) time window, but at least 10 readouts must exist to run the calculation.|*double*| ||
|<span class="no-wrap-code">`percentile_below`</span>|Probability that the current sensor readout will achieve values lesser than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the lower quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 30 time periods (days, etc.) time window, but at least 10 readouts must exist to run the calculation.|*double*| ||




**Rule definition YAML**

The rule definition YAML file *percentile/percentile_moving_30_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
      - field_name: percentile_above
        display_name: percentile_above
        help_text: "Probability that the current sensor readout will achieve values greater\
          \ than it would be expected from the estimated distribution based on the previous\
          \ values gathered within the time window. In other words, the upper quantile\
          \ of the estimated normal distribution. Set the time window at the threshold\
          \ level for all severity levels (warning, error, fatal) at once. The default\
          \ is a 30 time periods (days, etc.) time window, but at least 10 readouts must\
          \ exist to run the calculation."
        data_type: double
      - field_name: percentile_below
        display_name: percentile_below
        help_text: "Probability that the current sensor readout will achieve values lesser\
          \ than it would be expected from the estimated distribution based on the previous\
          \ values gathered within the time window. In other words, the lower quantile\
          \ of the estimated normal distribution. Set the time window at the threshold\
          \ level for all severity levels (warning, error, fatal) at once. The default\
          \ is a 30 time periods (days, etc.) time window, but at least 10 readouts must\
          \ exist to run the calculation."
        data_type: double
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *percentile/percentile_moving_30_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/percentile/percentile_moving_30_days.py* file in the DQOps distribution.

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
    import numpy as np
    import scipy
    import scipy.stats
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class PercentileMovingRuleParametersSpec:
        percentile_above: float
        percentile_below: float
    
    
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
        parameters: PercentileMovingRuleParametersSpec
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
        if (not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'percentile_below')
                or not hasattr(rule_parameters.parameters, 'percentile_above')):
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



---

## percentile moving 60 days
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the previous values gathered within a time window.

**Rule summary**

The percentile moving 60 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| percentile | <span class="no-wrap-code">`percentile/percentile_moving_60_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/percentile_moving_60_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/percentile_moving_60_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`percentile_above`</span>|Probability that the current sensor readout will achieve values greater than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the upper quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 60 time periods (days, etc.) time window, but at least 20 readouts must exist to run the calculation.|*double*| ||
|<span class="no-wrap-code">`percentile_below`</span>|Probability that the current sensor readout will achieve values lesser than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the lower quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 60 time periods (days, etc.) time window, but at least 20 readouts must exist to run the calculation.|*double*| ||




**Rule definition YAML**

The rule definition YAML file *percentile/percentile_moving_60_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
      - field_name: percentile_above
        display_name: percentile_above
        help_text: "Probability that the current sensor readout will achieve values greater\
          \ than it would be expected from the estimated distribution based on the previous\
          \ values gathered within the time window. In other words, the upper quantile\
          \ of the estimated normal distribution. Set the time window at the threshold\
          \ level for all severity levels (warning, error, fatal) at once. The default\
          \ is a 60 time periods (days, etc.) time window, but at least 20 readouts must\
          \ exist to run the calculation."
        data_type: double
      - field_name: percentile_below
        display_name: percentile_below
        help_text: "Probability that the current sensor readout will achieve values lesser\
          \ than it would be expected from the estimated distribution based on the previous\
          \ values gathered within the time window. In other words, the lower quantile\
          \ of the estimated normal distribution. Set the time window at the threshold\
          \ level for all severity levels (warning, error, fatal) at once. The default\
          \ is a 60 time periods (days, etc.) time window, but at least 20 readouts must\
          \ exist to run the calculation."
        data_type: double
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *percentile/percentile_moving_60_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/percentile/percentile_moving_60_days.py* file in the DQOps distribution.

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
    import numpy as np
    import scipy
    import scipy.stats
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class PercentileMovingRuleParametersSpec:
        percentile_above: float
        percentile_below: float
    
    
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
        parameters: PercentileMovingRuleParametersSpec
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
        if (not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'percentile_below')
                or not hasattr(rule_parameters.parameters, 'percentile_above')):
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



---

## percentile moving 7 days
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the previous values gathered within a time window.

**Rule summary**

The percentile moving 7 days data quality rule is described below.

| Category | Full rule name | Rule specification source code | Python source code |
| ---------|----------------|--------------------|--------------------|
| percentile | <span class="no-wrap-code">`percentile/percentile_moving_7_days`</span> | [Rule configuration](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/percentile_moving_7_days.dqorule.yaml) | [Python code](https://github.com/dqops/dqo/blob/develop/home/rules/percentile/percentile_moving_7_days.py) |


**Rule parameters**

The parameters passed to the rule are shown below.

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`percentile_above`</span>|Probability that the current sensor readout will achieve values greater than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the upper quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 7 time periods (days, etc.) time window, but at least 3 readouts must exist to run the calculation.|*double*| ||
|<span class="no-wrap-code">`percentile_below`</span>|Probability that the current sensor readout will achieve values lesser than it would be expected from the estimated distribution based on the previous values gathered within the time window. In other words, the lower quantile of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 7 time periods (days, etc.) time window, but at least 3 readouts must exist to run the calculation.|*double*| ||




**Rule definition YAML**

The rule definition YAML file *percentile/percentile_moving_7_days.dqorule.yaml* with the time window and rule parameters configuration is shown below.

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
      - field_name: percentile_above
        display_name: percentile_above
        help_text: "Probability that the current sensor readout will achieve values greater\
          \ than it would be expected from the estimated distribution based on the previous\
          \ values gathered within the time window. In other words, the upper quantile\
          \ of the estimated normal distribution. Set the time window at the threshold\
          \ level for all severity levels (warning, error, fatal) at once. The default\
          \ is a 7 time periods (days, etc.) time window, but at least 3 readouts must\
          \ exist to run the calculation."
        data_type: double
      - field_name: percentile_below
        display_name: percentile_below
        help_text: "Probability that the current sensor readout will achieve values lesser\
          \ than it would be expected from the estimated distribution based on the previous\
          \ values gathered within the time window. In other words, the lower quantile\
          \ of the estimated normal distribution. Set the time window at the threshold\
          \ level for all severity levels (warning, error, fatal) at once. The default\
          \ is a 7 time periods (days, etc.) time window, but at least 3 readouts must\
          \ exist to run the calculation."
        data_type: double
    ```






**Rule source code**

Please expand the section below to see the Python source code for the *percentile/percentile_moving_7_days* rule.
The file is found in the *[$DQO_HOME](../../dqo-concepts/architecture/dqops-architecture.md#dqops-home)/rules/percentile/percentile_moving_7_days.py* file in the DQOps distribution.

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
    import numpy as np
    import scipy
    import scipy.stats
    
    
    # rule specific parameters object, contains values received from the quality check threshold configuration
    class PercentileMovingRuleParametersSpec:
        percentile_above: float
        percentile_below: float
    
    
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
        parameters: PercentileMovingRuleParametersSpec
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
        if (not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'percentile_below')
                or not hasattr(rule_parameters.parameters, 'percentile_above')):
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




## What's next
- Learn how the [data quality rules](../../dqo-concepts/definition-of-data-quality-rules.md) are defined in DQOps and what how to create custom rules
- Understand how DQOps [runs data quality checks](../../dqo-concepts/architecture/data-quality-check-execution-flow.md), calling rules
