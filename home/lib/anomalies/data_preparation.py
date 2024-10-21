#  Copyright © 2021 DQOps (support@dqops.com)
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.

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
