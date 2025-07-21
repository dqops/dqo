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
