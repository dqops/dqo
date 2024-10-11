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

#
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

from typing import Sequence
import numpy as np
import scipy
import scipy.stats


def detect_upper_bound_anomaly(values_above_median: list[float], degrees_of_freedom: int, tail: float):
    values_array = np.array(values_above_median, dtype=float)
    values_median = np.median(values_array)
    values_std = scipy.stats.tstd(values_array)

    if float(values_std) == 0:
        return float(values_median)
    else:
        # Assumption: the historical data follows t-student distribution
        upper_readout_distribution = scipy.stats.t(df=degrees_of_freedom, loc=values_median,
                                                   scale=values_std)
        return float(upper_readout_distribution.ppf(1 - tail))


def detect_lower_bound_anomaly(values_below_median: list[float], degrees_of_freedom: int, tail: float):
    values_array = np.array(values_below_median, dtype=float)
    values_median = np.median(values_array)
    values_std = scipy.stats.tstd(values_array)

    if float(values_std) == 0:
        return float(values_median)
    else:
        # Assumption: the historical data follows t-student distribution
        lower_readout_distribution = scipy.stats.t(df=degrees_of_freedom, loc=values_median,
                                                   scale=values_std)
        return float(lower_readout_distribution.ppf(tail))
