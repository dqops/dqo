



///
/// Copyright © 2021 DQO.ai (support@dqo.ai)
///
/// Licensed under the Apache License, Version 2.0 (the "License");
/// you may not use this file except in compliance with the License.
/// You may obtain a copy of the License at
///
///     http://www.apache.org/licenses/LICENSE-2.0
///
/// Unless required by applicable law or agreed to in writing, software
/// distributed under the License is distributed on an "AS IS" BASIS,
/// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
/// See the License for the specific language governing permissions and
/// limitations under the License.
///

import { SOURCE_ACTION } from '../types';
import { CheckTypes } from "../../shared/routes";
import { CheckResultsDetailedDataModel, ErrorsDetailedDataModel, SensorReadoutsDetailedDataModel } from "../../api";

export const addFirstLevelTab = (checkType: CheckTypes, data: any) => ({
  type: SOURCE_ACTION.ADD_FIRST_LEVEL_TAB,
  checkType,
  data
});

export const setActiveFirstLevelTab = (checkType: CheckTypes, data: any) => ({
  type: SOURCE_ACTION.SET_ACTIVE_FIRST_LEVEL_TAB,
  checkType,
  data
});

export const closeFirstLevelTab = (checkType: CheckTypes, data: any) => ({
  type: SOURCE_ACTION.CLOSE_FIRST_LEVEL_TAB,
  checkType,
  data,
});

export const setCheckResults = (checkType: CheckTypes, checkName: string, checkResults: CheckResultsDetailedDataModel[]) => ({
  type: SOURCE_ACTION.SET_CHECK_RESULTS,
  checkType,
  data: {
    checkName,
    checkResults
  }
});

export const setSensorReadouts = (checkType: CheckTypes, checkName: string, sensorReadouts: SensorReadoutsDetailedDataModel[]) => ({
  type: SOURCE_ACTION.SET_SENSOR_READOUTS,
  checkType,
  data: {
    checkName,
    sensorReadouts
  }
});

export const setSensorErrors = (checkType: CheckTypes, checkName: string, errors: ErrorsDetailedDataModel[]) => {
  return ({
    type: SOURCE_ACTION.SET_SENSOR_ERRORS,
    checkType,
    data: {
      checkName,
      sensorErrors: errors
    }
  });
}

export const setCheckFilters = (checkType: CheckTypes, checkName: string, filters: any) => {
  return ({
    type: SOURCE_ACTION.SET_CHECK_FILTERS,
    checkType,
    data: {
      checkName,
      filters,
    }
  });
}
