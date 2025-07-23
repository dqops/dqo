#  Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
#
#  This file is licensed under the Business Source License 1.1,
#  which can be found in the root directory of this repository.
#
#  Change Date: This file will be licensed under the Apache License, Version 2.0,
#  four (4) years from its last modification date.

from typing import Sequence, Callable
from .data_types import HistoricData, HistoricDataPoint


def convert_historic_data_stationary(previous_readouts: Sequence[HistoricDataPoint], convert: Callable[[float], float]) -> HistoricData:
    data = HistoricData()
    data['time_period_epochs'] = [int(data_point.local_datetime_epoch) for data_point in previous_readouts if data_point is not None]
    data['sensor_values'] = [(float(data_point.sensor_readout) if hasattr(data_point, 'sensor_readout') else None) for data_point in
                             previous_readouts if data_point is not None]
    data['converted_values'] = [convert(sensor_value) if sensor_value is not None else None for sensor_value in data['sensor_values']]

    return data


def convert_historic_data_differencing(previous_readouts: Sequence[HistoricDataPoint], convert: Callable[[float], float]) -> HistoricData:
    data = HistoricData()
    data['time_period_epochs'] = [int(data_point.local_datetime_epoch) for index, data_point in enumerate(previous_readouts)
                                  if index > 0 and data_point is not None]
    data['sensor_values'] = [(float(data_point.sensor_readout) if hasattr(data_point, 'sensor_readout') else None) for data_point in
                             previous_readouts if data_point is not None]
    data['converted_values'] = [convert(sensor_value - data['sensor_values'][index - 1]) if sensor_value is not None else None
                                for index, sensor_value in enumerate(data['sensor_values']) if index > 0]

    return data


def average_forecast(upper_forecast: float, lower_forecast: float) -> float | None:
    if upper_forecast is None:
        return lower_forecast
    if lower_forecast is not None:
        return (upper_forecast + lower_forecast) / 2.0
    return None