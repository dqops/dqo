///
/// Copyright © 2021 DQOps (support@dqops.com)
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

import { ChecksApi, RulesApi, SensorsApi } from '../../services/apiClient';
import { DEFINITION_ACTION } from '../types/definition.types';
import { AxiosResponse } from 'axios';
import {
  CheckSpecFolderBasicModel,
  CheckSpecModel,
  DataGroupingConfigurationSpec,
  RuleBasicFolderModel,
  RuleModel,
  SensorBasicFolderModel,
  SensorModel
} from '../../api';
import { JOB_ACTION } from '../types';

export const getSensorFolderTreeRequest = () => ({
  type: DEFINITION_ACTION.GET_SENSOR_FOLDER_TREE
});

export const getSensorFolderTreeSuccess = (data: SensorBasicFolderModel) => ({
  type: DEFINITION_ACTION.GET_SENSOR_FOLDER_TREE_SUCCESS,
  data
});

export const getSensorFolderTreeFailed = (error: unknown) => ({
  type: DEFINITION_ACTION.GET_SENSOR_FOLDER_TREE_ERROR,
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

export const updateSensorFolderTree = (data: SensorBasicFolderModel) => ({
  type: DEFINITION_ACTION.UPDATE_SENSOR_FOLDER_TREE,
  data
});

export const toggleSensorFolderTree = (key: string) => ({
  type: DEFINITION_ACTION.TOGGLE_SENSOR_FOLDER,
  key
});

export const addFirstLevelTab = (data: any) => ({
  type: DEFINITION_ACTION.ADD_FIRST_LEVEL_TAB,
  data
});

export const setActiveFirstLevelTab = (data: any) => ({
  type: DEFINITION_ACTION.SET_ACTIVE_FIRST_LEVEL_TAB,
  data
});

export const closeFirstLevelTab = (data: any) => ({
  type: DEFINITION_ACTION.CLOSE_FIRST_LEVEL_TAB,
  data
});

export const getSensorRequest = () => ({
  type: DEFINITION_ACTION.GET_SENSOR_DETAIL
});

export const getSensorSuccess = (data: SensorModel) => ({
  type: DEFINITION_ACTION.GET_SENSOR_DETAIL_SUCCESS,
  data
});

export const getSensorFailed = (error: unknown) => ({
  type: DEFINITION_ACTION.GET_SENSOR_DETAIL_FAILED,
  error
});

export const getSensor = (sensorName: string) => async (dispatch: Dispatch) => {
  dispatch(getSensorRequest());
  try {
    const res: AxiosResponse<SensorModel> = await SensorsApi.getSensor(
      sensorName
    );
    dispatch(getSensorSuccess(res.data));
  } catch (err) {
    dispatch(getSensorFailed(err));
  }
};
export const getRuleRequest = () => ({
  type: DEFINITION_ACTION.GET_RULE_DETAIL
});

export const getRuleSuccess = (data: RuleModel) => ({
  type: DEFINITION_ACTION.GET_RULE_DETAIL_SUCCESS,
  data
});

export const getRuleFailed = (error: unknown) => ({
  type: DEFINITION_ACTION.GET_RULE_DETAIL_FAILED,
  error
});

export const getRule = (ruleName: string) => async (dispatch: Dispatch) => {
  dispatch(getRuleRequest());
  try {
    const res: AxiosResponse<RuleModel> = await RulesApi.getRule(ruleName);
    dispatch(getRuleSuccess(res.data));
  } catch (err) {
    dispatch(getRuleFailed(err));
  }
};

export const setUpdatedRule = (rule: RuleModel) => ({
  type: DEFINITION_ACTION.SET_UPDATED_RULE,
  data: rule
});

export const setUpdatedSensor = (rule: SensorModel) => ({
  type: DEFINITION_ACTION.SET_UPDATED_SENSOR,
  data: rule
});

export const updateSensorRequest = () => ({
  type: DEFINITION_ACTION.UPDATE_SENSOR_DETAIL
});

export const updateSensorSuccess = (data: SensorModel) => ({
  type: DEFINITION_ACTION.UPDATE_SENSOR_DETAIL_SUCCESS,
  data
});

export const updateSensorFailed = (error: unknown) => ({
  type: DEFINITION_ACTION.UPDATE_SENSOR_DETAIL_FAILED,
  error
});

export const updateSensor =
  (sensorName: string, body: SensorModel) => async (dispatch: Dispatch) => {
    dispatch(updateSensorRequest());
    try {
      const res: AxiosResponse<SensorModel> = await SensorsApi.updateSensor(
        sensorName,
        body
      );
      dispatch(updateSensorSuccess(res.data));
    } catch (err) {
      dispatch(updateSensorFailed(err));
    }
  };

export const createSensorRequest = () => ({
  type: DEFINITION_ACTION.CREATE_SENSOR_DETAIL
});

export const createSensorSuccess = (data: SensorModel) => ({
  type: DEFINITION_ACTION.CREATE_SENSOR_DETAIL_SUCCESS,
  data
});

export const createSensorFailed = (error: unknown) => ({
  type: DEFINITION_ACTION.CREATE_SENSOR_DETAIL_FAILED,
  error
});

export const createSensor =
  (sensorName: string, body: SensorModel) => async (dispatch: any) => {
    dispatch(createSensorRequest());
    try {
      const res: AxiosResponse<SensorModel> = await SensorsApi.createSensor(
        sensorName,
        body
      );
      dispatch(createSensorSuccess(res.data));
      dispatch(getSensorFolderTree());
    } catch (err) {
      dispatch(createSensorFailed(err));
    }
  };

export const updateRuleRequest = () => ({
  type: DEFINITION_ACTION.UPDATE_RULE_DETAIL
});

export const updateRuleSuccess = (data: SensorModel) => ({
  type: DEFINITION_ACTION.UPDATE_RULE_DETAIL_SUCCESS,
  data
});

export const updateRuleFailed = (error: unknown) => ({
  type: DEFINITION_ACTION.UPDATE_RULE_DETAIL_FAILED,
  error
});

export const updateRule =
  (ruleName: string, body: RuleModel) => async (dispatch: Dispatch) => {
    dispatch(updateRuleRequest());
    try {
      const res: AxiosResponse<SensorModel> = await RulesApi.updateRule(
        ruleName,
        body
      );
      dispatch(updateRuleSuccess(res.data));
    } catch (err) {
      dispatch(updateRuleFailed(err));
    }
  };

export const createRuleRequest = () => ({
  type: DEFINITION_ACTION.CREATE_RULE_DETAIL
});

export const createRuleSuccess = (data: SensorModel) => ({
  type: DEFINITION_ACTION.CREATE_RULE_DETAIL_SUCCESS,
  data
});

export const createRuleFailed = (error: unknown) => ({
  type: DEFINITION_ACTION.CREATE_RULE_DETAIL_FAILED,
  error
});

export const createRule =
  (ruleName: string, body: RuleModel) => async (dispatch: Dispatch) => {
    dispatch(createRuleRequest());
    try {
      const res: AxiosResponse<SensorModel> = await RulesApi.createRule(
        ruleName,
        body
      );
      dispatch(createRuleSuccess(res.data));
    } catch (err) {
      dispatch(createRuleFailed(err));
    }
  };

export const getdataQualityChecksFolderTreeRequest = () => ({
  type: DEFINITION_ACTION.GET_DATA_QUALITY_CHECKS_FOLDER_TREE
});

export const getdataQualityChecksFolderTreeSuccess = (
  data: CheckSpecFolderBasicModel
) => ({
  type: DEFINITION_ACTION.GET_DATA_QUALITY_CHECKS_FOLDER_TREE_SUCCESS,
  data
});

export const getdataQualityChecksFolderTreeFailed = (error: unknown) => ({
  type: DEFINITION_ACTION.GET_DATA_QUALITY_CHECKS_FOLDER_TREE_ERROR,
  error
});

export const getdataQualityChecksFolderTree =
  () => async (dispatch: Dispatch) => {
    dispatch(getdataQualityChecksFolderTreeRequest());
    try {
      const res: AxiosResponse<CheckSpecFolderBasicModel> =
        await ChecksApi.getCheckFolderTree();
      dispatch(getdataQualityChecksFolderTreeSuccess(res.data));
    } catch (err) {
      dispatch(getdataQualityChecksFolderTreeFailed(err));
    }
  };

export const toggledataQualityChecksFolderTree = (key: string) => ({
  type: DEFINITION_ACTION.TOGGLE_DATA_QUALITY_CHECKS_FOLDER,
  key
});

export const updatedataQualityChecksFolderTree = (
  data: CheckSpecFolderBasicModel
) => ({
  type: DEFINITION_ACTION.UPDATE_DATA_QUALITY_CHECKS_FOLDER_TREE,
  data
});

export const createCheckRequest = () => ({
  type: DEFINITION_ACTION.CREATE_CHECK_DETAIL
});

export const updateCheckRequest = () => ({
  type: DEFINITION_ACTION.UPDATE_CHECK_DETAIL
});

export const deleteCheckRequest = () => ({
  type: DEFINITION_ACTION.DELETE_CHECK_DETAIL
});

// export const addFirstLevelTab = (data: any) => ({
//   type: DEFINITION_ACTION.ADD_FIRST_LEVEL_TAB,
//   data
// });

// export const setActiveFirstLevelTab = (data: any) => ({
//   type: DEFINITION_ACTION.SET_ACTIVE_FIRST_LEVEL_TAB,
//   data
// });

// export const closeFirstLevelTab = (data: any) => ({
//   type: DEFINITION_ACTION.CLOSE_FIRST_LEVEL_TAB,
//   data
// });

export const getCheckRequest = () => ({
  type: DEFINITION_ACTION.GET_CHECK_DETAIL
});

export const getCheckSuccess = (data: CheckSpecModel) => ({
  type: DEFINITION_ACTION.GET_CHECK_DETAIL_SUCCESS,
  data
});

export const getCheck = (checkName: string) => async (dispatch: Dispatch) => {
  dispatch(getCheckRequest());
  try {
    const res: AxiosResponse<CheckSpecModel> = await ChecksApi.getCheck(
      checkName
    );
    dispatch(getCheckSuccess(res.data));
  } catch (err) {
    // dispatch(getSensorFailed(err));
  }
};

export const createCheck =
  (fullCheckName: string, body: CheckSpecModel) =>
  async (dispatch: Dispatch) => {
    dispatch(createCheckRequest());
    try {
      const checkName = fullCheckName.split('/');
      const newObject = Object.assign(
        {},
        { ...body, check_name: checkName[checkName.length - 1], custom: true }
      );
      await ChecksApi.createCheck(fullCheckName, newObject);
    } catch (err) {
      console.error(err);
    }
  };

export const updateCheck =
  (fullCheckName: string, body: CheckSpecModel) =>
  async (dispatch: Dispatch) => {
    dispatch(updateCheckRequest());
    try {
      const checkName = fullCheckName.split('/');
      const newObject = Object.assign(
        {},
        { ...body, check_name: checkName[checkName.length - 1], custom: true }
      );
      await ChecksApi.updateCheck(fullCheckName, newObject);
    } catch (err) {
      console.error(err);
    }
  };

export const deleteCheck =
  (fullCheckName: string) => async (dispatch: Dispatch) => {
    dispatch(deleteCheckRequest());
    try {
      await ChecksApi.deleteCheck(fullCheckName);
    } catch (err) {
      console.error(err);
    }
  };

export const getRuleFolderTreeRequest = () => ({
  type: DEFINITION_ACTION.GET_RULE_FOLDER_TREE
});

export const getRuleFolderTreeSuccess = (data: RuleBasicFolderModel) => ({
  type: DEFINITION_ACTION.GET_RULE_FOLDER_TREE_SUCCESS,
  data
});

export const getRuleFolderTreeFailed = (error: unknown) => ({
  type: DEFINITION_ACTION.GET_RULE_FOLDER_TREE_ERROR,
  error
});

export const getRuleFolderTree = () => async (dispatch: Dispatch) => {
  dispatch(getRuleFolderTreeRequest());
  try {
    const res: AxiosResponse<RuleBasicFolderModel> =
      await RulesApi.getRuleFolderTree();
    dispatch(getRuleFolderTreeSuccess(res.data));
  } catch (err) {
    dispatch(getRuleFolderTreeFailed(err));
  }
};

export const toggleRuleFolderTree = (key: string) => ({
  type: DEFINITION_ACTION.TOGGLE_RULE_FOLDER,
  key
});

export const updateRuleFolderTree = (data: RuleBasicFolderModel) => ({
  type: DEFINITION_ACTION.UPDATE_RULE_FOLDER_TREE,
  data
});
export const setCreatedDataStream = (
  bool: boolean,
  dataGrouping: string,
  spec: DataGroupingConfigurationSpec
) => ({
  type: JOB_ACTION.SET_CREATED_DATA_STREAM,
  bool,
  dataGrouping,
  spec
});