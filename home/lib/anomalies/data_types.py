#  Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
#
#  This file is licensed under the Business Source License 1.1,
#  which can be found in the root directory of this repository.
#
#  Change Date: This file will be licensed under the Apache License, Version 2.0,
#  four (4) years from its last modification date.

from datetime import datetime
from typing import Sequence, List, TypedDict


# rule specific parameters object, contains values received from the quality check threshold configuration
class AnomalyRuleParametersSpec:
    anomaly_percent: float
    use_ai: bool


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
    degrees_of_freedom: int
    ai_degrees_of_freedom: int
    anderson_significance_level: float
    kolmogorov_significance_level: float


# rule execution parameters, contains the sensor value (actual_value) and the rule parameters
class RuleExecutionRunParameters:
    actual_value: float
    parameters: AnomalyRuleParametersSpec
    time_period_local_epoch: int
    previous_readouts: Sequence[HistoricDataPoint]
    time_window: RuleTimeWindowSettingsSpec
    configuration_parameters: AnomalyConfigurationParameters
    model_path: str
    data_group: str
    update_model: str


class HistoricData(TypedDict):
    time_period_epochs: List[int]
    sensor_values: List[float]
    converted_values: List[float]

