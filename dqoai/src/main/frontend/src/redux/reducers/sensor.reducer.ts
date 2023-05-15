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
import { Action, INestTab } from "./source.reducer";

export interface ISensorState {
  sensorFolderTree?: SensorBasicFolderModel;
  loading: boolean;
  error: any;
  sensorState: Record<string, boolean>;
  tabs: INestTab[];
  activeTab?: string;
}

const initialState: ISensorState = {
  loading: false,
  error: null,
  sensorState: {},
  tabs: [],
};

const setActiveTabState = (state: ISensorState, action: Action, data: Record<string, unknown>) => {
  return {
    ...state,
    tabs: state.tabs.map((item) => item.url === state.activeTab ? ({
      ...item,
      state: {
        ...item.state,
        ...data
      }
    }) : item)
  }
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
    case SENSOR_ACTION.UPDATE_SENSOR_FOLDER_TREE:
      return {
        ...state,
        sensorFolderTree: action.data,
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
    case SENSOR_ACTION.ADD_FIRST_LEVEL_TAB: {
      const existing = state.tabs?.find((item) => item.value === action.data.value);

      if (existing) {
        return {
          ...state,
          activeTab: action.data.url,
          tabs: state.tabs.map((item) => item.value === action.data.value ? ({
            ...item,
            ...action.data,
          }) : item)
        };
      }

      return {
        ...state,
        activeTab: action.data.url,
        tabs: [
          ...state.tabs || [],
          action.data,
        ]
      };
    }
    case SENSOR_ACTION.SET_ACTIVE_FIRST_LEVEL_TAB: {
      return {
        ...state,
        activeTab: action.data,
      }
    }
    case SENSOR_ACTION.CLOSE_FIRST_LEVEL_TAB: {
      const index = state.tabs?.findIndex((item) => item.url === action.data);
      let activeTab = state.activeTab;

      if (state.activeTab === action.data) {
        if (index > 0) {
          activeTab = state.tabs[index-1].url;
        } else if (index < state.tabs.length - 1) {
          activeTab = state.tabs[index+1].url;
        }
      }

      return {
        ...state,
        tabs: state.tabs.filter((item) => item.url !== action.data),
        activeTab
      }
    }
    case SENSOR_ACTION.GET_SENSOR_DETAIL: {
      return setActiveTabState(state, action,{
        loading: true,
      });
    }
    case SENSOR_ACTION.GET_SENSOR_DETAIL_SUCCESS: {
      return setActiveTabState(state, action,{
        loading: false,
        sensorDetail: action.data,
        isUpdatedSensorDetail: false,
      });
    }
    case SENSOR_ACTION.GET_SENSOR_DETAIL_FAILED: {
      return setActiveTabState(state, action,{
        loading: false,
      });
    }
    case SENSOR_ACTION.GET_RULE_DETAIL: {
      return setActiveTabState(state, action,{
        loading: true,
      });
    }
    case SENSOR_ACTION.GET_RULE_DETAIL_SUCCESS: {
      return setActiveTabState(state, action,{
        loading: false,
        ruleDetail: action.data,
        isUpdatedRuleDetail: false,
      });
    }
    case SENSOR_ACTION.GET_RULE_DETAIL_FAILED: {
      return setActiveTabState(state, action,{
        loading: false,
      });
    }
    case SENSOR_ACTION.SET_UPDATED_RULE: {
      return setActiveTabState(state, action,{
        ruleDetail: action.data,
        isUpdatedRuleDetail: true,
      });
    }
    case SENSOR_ACTION.SET_UPDATED_SENSOR: {
      return setActiveTabState(state, action,{
        sensorDetail: action.data,
        isUpdatedSensorDetail: true,
      });
    }
    case SENSOR_ACTION.CREATE_SENSOR_DETAIL_SUCCESS: {
      return setActiveTabState(state, action, {
        isUpdatedSensorDetail: false
      });
    }
    case SENSOR_ACTION.UPDATE_SENSOR_DETAIL: {
      return setActiveTabState(state, action, {
        isUpdating: true
      })
    }
    case SENSOR_ACTION.UPDATE_SENSOR_DETAIL_SUCCESS: {
      return setActiveTabState(state, action, {
        isUpdating: false,
        isUpdatedSensorDetail: false
      })
    }
    case SENSOR_ACTION.UPDATE_SENSOR_DETAIL_FAILED: {
      return setActiveTabState(state, action, {
        isUpdating: false
      })
    }
    case SENSOR_ACTION.UPDATE_RULE_DETAIL: {
      return setActiveTabState(state, action, {
        isUpdating: true
      })
    }
    case SENSOR_ACTION.UPDATE_RULE_DETAIL_SUCCESS: {
      return setActiveTabState(state, action, {
        isUpdating: false,
        isUpdatedRuleDetail: false
      })
    }
    case SENSOR_ACTION.UPDATE_RULE_DETAIL_FAILED: {
      return setActiveTabState(state, action, {
        isUpdating: false
      })
    }
    default:
      return state;
  }
};

export default sensorReducer;
