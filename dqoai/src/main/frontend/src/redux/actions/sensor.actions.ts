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

import { SensorsApi } from '../../services/apiClient';
import { SENSOR_ACTION } from '../types';
import { AxiosResponse } from 'axios';
import { SensorBasicFolderModel } from "../../api";

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
