import {
  CheckContainerModel,
  CheckSpecFolderBasicModel,
  QualityCategoryModel
} from '../../api';
import { DATA_QUALITY_CHECKS_ACTION } from '../types/dataQualityChecks.types';
import { Action, INestTab } from './source.reducer';

export interface IdataQualityChecks {
  checksFolderTree?: CheckSpecFolderBasicModel;
  loading: boolean;
  error: any;
  dataQualityChecksState: Record<string, boolean>;
  tabs: INestTab[];
  activeTab?: string;
}

const initialState: IdataQualityChecks = {
  loading: false,
  error: null,
  dataQualityChecksState: {},
  tabs: []
};

const setActiveTabState = (
  state: IdataQualityChecks,
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

const dataQualityChecksReducer = (state = initialState, action: any) => {
  switch (action.type) {
    case DATA_QUALITY_CHECKS_ACTION.GET_DATA_QUALITY_CHECKS_FOLDER_TREE:
      return {
        ...state,
        loading: true
      };
    case DATA_QUALITY_CHECKS_ACTION.GET_DATA_QUALITY_CHECKS_FOLDER_TREE_SUCCESS:
      return {
        ...state,
        loading: false,
        checksFolderTree: action.data,
        error: null
      };
    case DATA_QUALITY_CHECKS_ACTION.GET_DATA_QUALITY_CHECKS_FOLDER_TREE_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case DATA_QUALITY_CHECKS_ACTION.TOGGLE_DATA_QUALITY_CHECKS_FOLDER:
      return {
        ...state,
        dataQualityChecksState: {
          ...state.dataQualityChecksState,
          [action.key]: !state.dataQualityChecksState[action.key]
        }
      };
    case DATA_QUALITY_CHECKS_ACTION.UPDATE_DATA_QUALITY_CHECKS_FOLDER_TREE:
      return {
        ...state,
        checksFolderTree: action.data
      };
    default:
      return state;
    case DATA_QUALITY_CHECKS_ACTION.ADD_FIRST_LEVEL_TAB: {
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
    case DATA_QUALITY_CHECKS_ACTION.SET_ACTIVE_FIRST_LEVEL_TAB: {
      return {
        ...state,
        activeTab: action.data
      };
    }
    case DATA_QUALITY_CHECKS_ACTION.CLOSE_FIRST_LEVEL_TAB: {
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
    case DATA_QUALITY_CHECKS_ACTION.UPDATE_CHECK_DETAIL: {
      return setActiveTabState(state, action, {});
    }
    case DATA_QUALITY_CHECKS_ACTION.CREATE_CHECK_DETAIL: {
      return setActiveTabState(state, action, {});
    }
    case DATA_QUALITY_CHECKS_ACTION.DELETE_CHECK_DETAIL: {
      return setActiveTabState(state, action, {});
    }
  }
};

export default dataQualityChecksReducer;
