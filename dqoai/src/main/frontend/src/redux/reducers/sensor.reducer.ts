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

import {
  SensorBasicFolderModel,
} from '../../api';
import { SENSOR_ACTION } from '../types';

export interface ISensorState {
  sensorFolderTree?: SensorBasicFolderModel;
  loading: boolean;
  error: any;
  sensorState: Record<string, boolean>
}

const initialState: ISensorState = {
  loading: false,
  error: null,
  sensorState: {}
};

const sensorReducer = (state = initialState, action: any) => {
  switch (action.type) {
    case SENSOR_ACTION.GET_SENSOR_FOLDER_TREE:
      return {
        ...state,
        loading: true
      };
    case SENSOR_ACTION.GET_SENSOR_FOLDER_TREE_SUCCESS:
      return {
        ...state,
        loading: false,
        sensorFolderTree: action.data,
        error: null
      };
    case SENSOR_ACTION.GET_SENSOR_FOLDER_TREE_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case SENSOR_ACTION.TOGGLE_SENSOR_FOLDER:
      return {
        ...state,
        sensorState: {
          ...state.sensorState,
          [action.key]: !state.sensorState[action.key]
        }
      };
    default:
      return state;
  }
};

export default sensorReducer;
