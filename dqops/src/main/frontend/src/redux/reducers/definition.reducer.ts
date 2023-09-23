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

import {
  CheckSpecFolderBasicModel,
  CheckSpecModel,
  RuleBasicFolderModel,
  SensorBasicFolderModel
} from '../../api';
import { DEFINITION_ACTION } from '../types/definition.types';
import { Action, INestTab } from './source.reducer';

export interface IDefinitionState {
  sensorFolderTree?: SensorBasicFolderModel;
  loading?: boolean;
  error?: any;
  sensorState: Record<string, boolean>;
  tabs: INestTab[];
  activeTab?: string;
  ruleFolderTree?: RuleBasicFolderModel;
  ruleState: Record<string, boolean>;
  checksFolderTree?: CheckSpecFolderBasicModel;
  dataQualityChecksState: Record<string, boolean>;
  checkDetail?: CheckSpecModel;

  definitionFirstLevelFolder?: Array<{ category: string; isOpen: boolean }>;
  refreshChecksTreeIndicator: boolean;
  refreshSensorsTreeIndicator: boolean;
  refreshRulesTreeIndicator: boolean;
}

const initialState: IDefinitionState = {
  loading: false,
  error: null,
  sensorState: {},
  tabs: [],
  ruleState: {},
  dataQualityChecksState: {},
  definitionFirstLevelFolder: [],
  refreshChecksTreeIndicator: false,
  refreshRulesTreeIndicator: false,
  refreshSensorsTreeIndicator: false
};
console.log(initialState)
const setActiveTabState = (
  state: IDefinitionState,
  action: Action,
  data: Record<string, unknown>
) => {
  return {
    ...state,
    tabs: state.tabs.map((item) =>
      item.url === state.activeTab
        ? {
            ...item,
            state: {
              ...item.state,
              ...data
            }
          }
        : item
    )
  };
};

const definitionReducer = (state = initialState, action: any) => {
  switch (action.type) {
    case DEFINITION_ACTION.GET_SENSOR_FOLDER_TREE:
      return {
        ...state,
        loading: true
      };
    case DEFINITION_ACTION.GET_SENSOR_FOLDER_TREE_SUCCESS:
      return {
        ...state,
        loading: false,
        sensorFolderTree: action.data,
        error: null
      };
    case DEFINITION_ACTION.UPDATE_SENSOR_FOLDER_TREE:
      return {
        ...state,
        sensorFolderTree: action.data
      };
    case DEFINITION_ACTION.GET_SENSOR_FOLDER_TREE_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case DEFINITION_ACTION.TOGGLE_SENSOR_FOLDER:
      return {
        ...state,
        sensorState: {
          ...state.sensorState,
          [action.key]: !state.sensorState[action.key]
        }
      };
      case DEFINITION_ACTION.OPEN_SENSOR_FOLDER:
      return {
        ...state,
        sensorState: {
          ...state.sensorState,
          [action.key]: true
        }
      };
    case DEFINITION_ACTION.ADD_FIRST_LEVEL_TAB: {
      const existing = state.tabs?.find(
        (item) => item.value === action.data.value
      );

      if (existing) {
        return {
          ...state,
          activeTab: action.data.url,
          tabs: state.tabs.map((item) =>
            item.value === action.data.value
              ? {
                  ...item,
                  ...action.data
                }
              : item
          )
        };
      }

      return {
        ...state,
        activeTab: action.data.url,
        tabs: [...(state.tabs || []), action.data]
      };
    }
    case DEFINITION_ACTION.SET_ACTIVE_FIRST_LEVEL_TAB: {
      return {
        ...state,
        activeTab: action.data
      };
    }
    case DEFINITION_ACTION.CLOSE_FIRST_LEVEL_TAB: {
      const index = state.tabs?.findIndex((item) => item.url === action.data);
      let activeTab = state.activeTab;

      if (state.activeTab === action.data) {
        if (index > 0) {
          activeTab = state.tabs[index - 1].url;
        } else if (index < state.tabs.length - 1) {
          activeTab = state.tabs[index + 1].url;
        }
      }

      return {
        ...state,
        tabs: state.tabs.filter((item) => item.url !== action.data),
        activeTab
      };
    }
    case DEFINITION_ACTION.GET_SENSOR_DETAIL: {
      return setActiveTabState(state, action, {
        loading: true
      });
    }
    case DEFINITION_ACTION.GET_SENSOR_DETAIL_SUCCESS: {
      return setActiveTabState(state, action, {
        loading: false,
        sensorDetail: action.data,
        isUpdatedSensorDetail: false
      });
    }
    case DEFINITION_ACTION.GET_SENSOR_DETAIL_FAILED: {
      return setActiveTabState(state, action, {
        loading: false
      });
    }
    case DEFINITION_ACTION.GET_RULE_DETAIL: {
      return setActiveTabState(state, action, {
        loading: true
      });
    }
    case DEFINITION_ACTION.GET_RULE_DETAIL_SUCCESS: {
      return setActiveTabState(state, action, {
        loading: false,
        ruleDetail: action.data,
        isUpdatedRuleDetail: false
      });
    }
    case DEFINITION_ACTION.GET_RULE_DETAIL_FAILED: {
      return setActiveTabState(state, action, {
        loading: false
      });
    }
    case DEFINITION_ACTION.SET_UPDATED_RULE: {
      return setActiveTabState(state, action, {
        ruleDetail: action.data,
        isUpdatedRuleDetail: true
      });
    }
    case DEFINITION_ACTION.SET_UPDATED_SENSOR: {
      return setActiveTabState(state, action, {
        sensorDetail: action.data,
        isUpdatedSensorDetail: true
      });
    }
    case DEFINITION_ACTION.CREATE_SENSOR_DETAIL_SUCCESS: {
      return setActiveTabState(state, action, {
        isUpdatedSensorDetail: false
      });
    }
    case DEFINITION_ACTION.UPDATE_SENSOR_DETAIL: {
      return setActiveTabState(state, action, {
        isUpdating: true
      });
    }
    case DEFINITION_ACTION.UPDATE_SENSOR_DETAIL_SUCCESS: {
      return setActiveTabState(state, action, {
        isUpdating: false,
        isUpdatedSensorDetail: false
      });
    }
    case DEFINITION_ACTION.UPDATE_SENSOR_DETAIL_FAILED: {
      return setActiveTabState(state, action, {
        isUpdating: false
      });
    }
    case DEFINITION_ACTION.UPDATE_RULE_DETAIL: {
      return setActiveTabState(state, action, {
        isUpdating: true
      });
    }
    case DEFINITION_ACTION.UPDATE_RULE_DETAIL_SUCCESS: {
      return setActiveTabState(state, action, {
        isUpdating: false,
        isUpdatedRuleDetail: false
      });
    }
    case DEFINITION_ACTION.UPDATE_RULE_DETAIL_FAILED: {
      return setActiveTabState(state, action, {
        isUpdating: false
      });
    }
    case DEFINITION_ACTION.GET_RULE_FOLDER_TREE:
      return {
        ...state,
        loading: true
      };
    case DEFINITION_ACTION.GET_RULE_FOLDER_TREE_SUCCESS:
      return {
        ...state,
        loading: false,
        ruleFolderTree: action.data,
        error: null
      };
    case DEFINITION_ACTION.GET_RULE_FOLDER_TREE_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case DEFINITION_ACTION.TOGGLE_RULE_FOLDER:
      return {
        ...state,
        ruleState: {
          ...state.ruleState,
          [action.key]: !state.ruleState[action.key]
        }
      };
      case DEFINITION_ACTION.OPEN_RULE_FOLDER:
        return {
          ...state,
          ruleState: {
            ...state.ruleState,
            [action.key]: true
          }
        };  
    case DEFINITION_ACTION.UPDATE_RULE_FOLDER_TREE:
      return {
        ...state,
        ruleFolderTree: action.data
      };
    case DEFINITION_ACTION.GET_DATA_QUALITY_CHECKS_FOLDER_TREE:
      return {
        ...state,
        loading: true
      };
    case DEFINITION_ACTION.GET_DATA_QUALITY_CHECKS_FOLDER_TREE_SUCCESS:
      return {
        ...state,
        loading: false,
        checksFolderTree: action.data,
        error: null
      };
    case DEFINITION_ACTION.GET_DATA_QUALITY_CHECKS_FOLDER_TREE_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case DEFINITION_ACTION.TOGGLE_DATA_QUALITY_CHECKS_FOLDER:
      return {
        ...state,
        dataQualityChecksState: {
          ...state.dataQualityChecksState,
          [action.fullPath]: !state.dataQualityChecksState[action.fullPath]
        }
      };
      case DEFINITION_ACTION.OPEN_DATA_QUALITY_CHECKS_FOLDER:
        return {
          ...state,
          dataQualityChecksState: {
            ...state.dataQualityChecksState,
            [action.fullPath]: true
          }
        };

    case DEFINITION_ACTION.UPDATE_DATA_QUALITY_CHECKS_FOLDER_TREE:
      return {
        ...state,
        checksFolderTree: action.data
      };
    //   case DEFINITION_ACTION.ADD_FIRST_LEVEL_TAB: {
    //     const existing = state.tabs?.find(
    //       (item) => item.value === action.data.value
    //     );

    //     if (existing) {
    //       return {
    //         ...state,
    //         activeTab: action.data.url,
    //         tabs: state.tabs.map((item) =>
    //           item.value === action.data.value
    //             ? {
    //                 ...item,
    //                 ...action.data
    //               }
    //             : item
    //         )
    //       };
    //     }

    //     return {
    //       ...state,
    //       activeTab: action.data.url,
    //       tabs: [...(state.tabs || []), action.data]
    //     };
    //   }
    //   case DEFINITION_ACTION.SET_ACTIVE_FIRST_LEVEL_TAB: {
    //     return {
    //       ...state,
    //       activeTab: action.data
    //     };
    //   }
    //   case DEFINITION_ACTION.CLOSE_FIRST_LEVEL_TAB: {
    //     const index = state.tabs?.findIndex((item) => item.url === action.data);
    //     let activeTab = state.activeTab;

    //     if (state.activeTab === action.data) {
    //       if (index > 0) {
    //         activeTab = state.tabs[index - 1].url;
    //       } else if (index < state.tabs.length - 1) {
    //         activeTab = state.tabs[index + 1].url;
    //       }
    //     }

    //     return {
    //       ...state,
    //       tabs: state.tabs.filter((item) => item.url !== action.data),
    //       activeTab
    //     };
    //   }
    case DEFINITION_ACTION.GET_CHECK_DETAIL_SUCCESS: {
      return setActiveTabState(state, action, {
        checkDetail: action.data,
        isUpdatedSensorDetail: false
      });
    }
    case DEFINITION_ACTION.UPDATE_CHECK_DETAIL: {
      return setActiveTabState(state, action, {});
    }
    case DEFINITION_ACTION.CREATE_CHECK_DETAIL: {
      return setActiveTabState(state, action, {});
    }
    case DEFINITION_ACTION.DELETE_CHECK_DETAIL: {
      return setActiveTabState(state, action, {});
    }
    case DEFINITION_ACTION.GET_CHECK_DETAIL: {
      return setActiveTabState(state, action, {});
    }

    case DEFINITION_ACTION.TOGGLE_FIRST_LEVEL_FOLDER: {
      return { ...state, definitionFirstLevelFolder: action.data };
    }
    case DEFINITION_ACTION.REFRESH_CHECKS_TREE_INDICATOR: {
      return { ...state, refreshChecksTreeIndicator: action.data};
    }
    case DEFINITION_ACTION.REFRESH_SENSORS_TREE_INDICATOR: {
      return { ...state, refreshSensorsTreeIndicator: action.data};
    }
    case DEFINITION_ACTION.REFRESH_RULES_TREE_INDICATOR: {
      return { ...state, refreshRulesTreeIndicator: action.data };
    }

    default:
      return state;
  }

};

export default definitionReducer;
