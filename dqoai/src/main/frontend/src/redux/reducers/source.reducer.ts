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

export interface INestTab {
  url: string;
  state: Record<string, unknown>;
  label: string;
}

export interface ISourceState {
  [CheckTypes.SOURCES]: {
    tabs: INestTab[];
    activeTab?: string;
  },
  [CheckTypes.PROFILING]: {
    tabs: INestTab[];
    activeTab?: string;
  },
  [CheckTypes.RECURRING]: {
    tabs: INestTab[];
    activeTab?: string;
  },
  [CheckTypes.PARTITIONED]: {
    tabs: INestTab[];
    activeTab?: string;
  },
}

const initialState: ISourceState = {
  sources: {
    tabs: []
  },
  profiling: {
    tabs: []
  },
  recurring: {
    tabs: []
  },
  partitioned: {
    tabs: []
  },
};

const connectionReducer = (state = initialState, action: { type: string, checkType: CheckTypes, activeTab?: string, data: any }) => {
  switch (action.type) {
    case SOURCE_ACTION.ADD_FIRST_LEVEL_TAB: {
      const existing = state[action.checkType].tabs.find((item) => item.url === action.data.url);

      if (existing) {
        return {
          ...state,
          [action.checkType]: {
            ...state[action.checkType],
            activeTab: action.data.url,
          }
        };
      }
      return {
        ...state,
        [action.checkType]: {
          ...state[action.checkType],
          activeTab: action.data.url,
          tabs: [
            ...state[action.checkType].tabs,
            action.data,
          ]
        }
      };
    }
    case SOURCE_ACTION.SET_ACTIVE_FIRST_LEVEL_TAB:
      return {
        ...state,
        [action.checkType]: {
          ...state[action.checkType],
          activeTab: action.data,
        }
      }
    case SOURCE_ACTION.GET_CONNECTION_BASIC_SUCCESS: {
      return {
        ...state,
        [action.checkType]: {
          ...state[action.checkType],
          tabs: state[action.checkType].tabs.map((item) => item.url === action.activeTab ? ({
            ...item,
            state: {
              ...item.state,
              loading: false,
              connectionBasic: action.data,
              isUpdatedConnectionBasic: false,
            }
          }) : item)
        }
      }
    }
    case SOURCE_ACTION.SET_UPDATED_CONNECTION_BASIC: {
      console.log('---------', action, state[action.checkType])
      return {
        ...state,
        [action.checkType]: {
          ...state[action.checkType],
          tabs: state[action.checkType].tabs.map((item) => item.url === action.activeTab ? ({
            ...item,
            state: {
              ...item.state,
              connectionBasic: action.data,
            }
          }) : item)
        }
      }
    }

    case SOURCE_ACTION.SET_IS_UPDATED_CONNECTION_BASIC: {
      return {
        ...state,
        [action.checkType]: {
          ...state[action.checkType],
          tabs: state[action.checkType].tabs.map((item) => item.url === action.activeTab ? ({
            ...item,
            state: {
              ...item.state,
              isUpdatedConnectionBasic: action.data
            }
          }) : item)
        }
      }
    }

    case SOURCE_ACTION.UPDATE_CONNECTION_BASIC: {
      return {
        ...state,
        [action.checkType]: {
          ...state[action.checkType],
          tabs: state[action.checkType].tabs.map((item) => item.url === action.activeTab ? ({
            ...item,
            state: {
              ...item.state,
              isUpdating: true,
            }
          }) : item)
        }
      }
    }

    case SOURCE_ACTION.UPDATE_CONNECTION_BASIC_SUCCESS: {
      return {
        ...state,
        [action.checkType]: {
          ...state[action.checkType],
          tabs: state[action.checkType].tabs.map((item) => item.url === action.activeTab ? ({
            ...item,
            state: {
              ...item.state,
              isUpdating: false,
            }
          }) : item)
        }
      }
    }

    default:
      return state;
  }
};

export default connectionReducer;
