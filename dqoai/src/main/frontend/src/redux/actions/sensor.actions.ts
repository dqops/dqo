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

import { Dispatch } from 'redux';

import { RulesApi, SensorsApi } from '../../services/apiClient';
import { SENSOR_ACTION } from '../types';
import { AxiosResponse } from 'axios';
import { RuleModel, SensorBasicFolderModel, SensorModel } from "../../api";

export const getSensorFolderTreeRequest = () => ({
  type: SENSOR_ACTION.GET_SENSOR_FOLDER_TREE
});

export const getSensorFolderTreeSuccess = (data: SensorBasicFolderModel) => ({
  type: SENSOR_ACTION.GET_SENSOR_FOLDER_TREE_SUCCESS,
  data
});

export const getSensorFolderTreeFailed = (error: unknown) => ({
  type: SENSOR_ACTION.GET_SENSOR_FOLDER_TREE_ERROR,
  error
});

export const getSensorFolderTree = () => async (dispatch: Dispatch) => {
  dispatch(getSensorFolderTreeRequest());
  try {
    const res: AxiosResponse<SensorBasicFolderModel> =
      await SensorsApi.getSensorFolderTree();
    dispatch(getSensorFolderTreeSuccess(res.data));
  } catch (err) {
    dispatch(getSensorFolderTreeFailed(err));
  }
};

export const toggleSensorFolderTree = (key: string) => ({
  type: SENSOR_ACTION.TOGGLE_SENSOR_FOLDER,
  key,
});

export const addFirstLevelTab = (data: any) => ({
  type: SENSOR_ACTION.ADD_FIRST_LEVEL_TAB,
  data,
});

export const setActiveFirstLevelTab = (data: any) => ({
  type: SENSOR_ACTION.SET_ACTIVE_FIRST_LEVEL_TAB,
  data
});


export const closeFirstLevelTab = (data: any) => ({
  type: SENSOR_ACTION.CLOSE_FIRST_LEVEL_TAB,
  data,
});

export const getSensorRequest = () => ({
  type: SENSOR_ACTION.GET_SENSOR_DETAIL
});

export const getSensorSuccess = (data: SensorModel) => ({
  type: SENSOR_ACTION.GET_SENSOR_DETAIL_SUCCESS,
  data
});

export const getSensorFailed = (error: unknown) => ({
  type: SENSOR_ACTION.GET_SENSOR_DETAIL_FAILED,
  error
});

export const getSensor = (sensorName: string) => async (dispatch: Dispatch) => {
  dispatch(getSensorRequest());
  try {
    const res: AxiosResponse<SensorModel> =
      await SensorsApi.getSensor(sensorName);
    dispatch(getSensorSuccess(res.data));
  } catch (err) {
    dispatch(getSensorFailed(err));
  }
};
export const getRuleRequest = () => ({
  type: SENSOR_ACTION.GET_RULE_DETAIL
});

export const getRuleSuccess = (data: RuleModel) => ({
  type: SENSOR_ACTION.GET_RULE_DETAIL_SUCCESS,
  data
});

export const getRuleFailed = (error: unknown) => ({
  type: SENSOR_ACTION.GET_RULE_DETAIL_FAILED,
  error
});

export const getRule = (ruleName: string) => async (dispatch: Dispatch) => {
  dispatch(getRuleRequest());
  try {
    const res: AxiosResponse<RuleModel> =
      await RulesApi.getRule(ruleName);
    dispatch(getRuleSuccess(res.data));
  } catch (err) {
    dispatch(getRuleFailed(err));
  }
};

export const setUpdatedRule = (rule: RuleModel) => ({
  type: SENSOR_ACTION.SET_UPDATED_RULE,
  data: rule
});
