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
import { CheckRunRecurringScheduleGroup } from "../../shared/enums/scheduling.enum";

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

export type BasicAction = {
  type: string;
  checkType: CheckTypes;
  activeTab: string,
};

export type Action = BasicAction & {
  data?: any;
  schedulingGroup?: CheckRunRecurringScheduleGroup;
  error?: any;
};

const setActiveTabState = (state: ISourceState, action: Action, data: Record<string, unknown>) => {
  return {
    ...state,
    [action.checkType]: {
      ...state[action.checkType],
      tabs: state[action.checkType].tabs.map((item) => item.url === action.activeTab ? ({
        ...item,
        state: {
          ...item.state,
          ...data
        }
      }) : item)
    }
  }
};

const connectionReducer = (state = initialState, action: Action) => {
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
      return setActiveTabState(state, action, {
        loading: false,
        connectionBasic: action.data,
        isUpdatedConnectionBasic: false,
      });
    }
    case SOURCE_ACTION.SET_UPDATED_CONNECTION_BASIC: {
      return setActiveTabState(state, action, {
        connectionBasic: action.data,
      });
    }

    case SOURCE_ACTION.SET_IS_UPDATED_CONNECTION_BASIC: {
      return setActiveTabState(state, action, {
        isUpdatedConnectionBasic: action.data
      });
    }

    case SOURCE_ACTION.UPDATE_CONNECTION_BASIC: {
      return setActiveTabState(state, action, {
        isUpdating: true,
      });
    }

    case SOURCE_ACTION.UPDATE_CONNECTION_BASIC_SUCCESS: {
      return setActiveTabState(state, action, {
        isUpdating: false,
      });
    }

    case SOURCE_ACTION.CLOSE_FIRST_LEVEL_TAB: {
      const index = state[action.checkType].tabs.findIndex((item) => item.url === action.data);
      let activeTab = state[action.checkType].activeTab;

      if (state[action.checkType].activeTab === action.data) {
        if (index > 0) {
          activeTab = state[action.checkType].tabs[index-1].url;
        } else if (index < state[action.checkType].tabs.length - 1) {
          activeTab = state[action.checkType].tabs[index+1].url;
        }
      }

      return {
        ...state,
        [action.checkType]: {
          ...state[action.checkType],
          tabs: state[action.checkType].tabs.filter((item) => item.url !== action.data),
          activeTab
        }
      }
    }
    case SOURCE_ACTION.GET_CONNECTION_SCHEDULE_GROUP: {
      return setActiveTabState(state, action, {
        loading: true,
      });
    }

    case SOURCE_ACTION.GET_CONNECTION_SCHEDULE_GROUP_SUCCESS: {
      const firstState = state[action.checkType].tabs.find((item) => item.url === action.activeTab)?.state || {};

      return setActiveTabState(state, action, {
        loading: false,
        scheduleGroups: {
          ...firstState?.scheduleGroups || {},
          [action.schedulingGroup || '']: {
            schedule: action.data,
            updatedSchedule: action.data,
            isUpdatedSchedule: false
          }
        },
      });
    }

    case SOURCE_ACTION.GET_CONNECTIONS:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_CONNECTIONS_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        connections: action.data,
        error: null
      });
    case SOURCE_ACTION.GET_CONNECTION_BASIC:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.RESET_CONNECTION_SCHEDULE_GROUP:
      return setActiveTabState(state, action, {
        scheduleGroups: undefined
      });
    case SOURCE_ACTION.UPDATE_CONNECTION_SCHEDULE_GROUP:
      return setActiveTabState(state, action, {
        isUpdating: true
      });
    case SOURCE_ACTION.UPDATE_CONNECTION_SCHEDULE_GROUP_SUCCESS:
      return setActiveTabState(state, action,{
        isUpdating: false,
        error: null
      });
    case SOURCE_ACTION.GET_CONNECTION_COMMENTS:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_CONNECTION_COMMENTS_SUCCESS:
      return setActiveTabState(state, action,{
        loading: false,
        comments: action.data,
        updatedComments: action.data,
        error: null
      });
    case SOURCE_ACTION.UPDATE_CONNECTION_COMMENTS:
      return setActiveTabState(state, action, {
        isUpdating: true
      });
    case SOURCE_ACTION.UPDATE_CONNECTION_COMMENTS_SUCCESS:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: null
      });
    case SOURCE_ACTION.GET_CONNECTION_LABELS:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_CONNECTION_LABELS_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        labels: action.data,
        isUpdatedLabels: false,
        error: null
      });
    case SOURCE_ACTION.UPDATE_CONNECTION_LABELS:
      return setActiveTabState(state, action, {
        isUpdating: true
      });
    case SOURCE_ACTION.UPDATE_CONNECTION_LABELS_SUCCESS:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: null
      });
    // case SOURCE_ACTION.UPDATE_CONNECTION_LABELS_ERROR:
    //   return {
    //     ...state,
    //     isUpdating: false,
    //     error: action.error
    //   };
    case SOURCE_ACTION.GET_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING_SUCCESS:
      return setActiveTabState(state, action,{
        loading: false,
        updatedDataStreamsMapping: action.data,
        error: null
      });
    case SOURCE_ACTION.UPDATE_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING:
      return setActiveTabState(state, action, {
        isUpdating: true
      });
    case SOURCE_ACTION.UPDATE_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING_SUCCESS:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: null
      });
    case SOURCE_ACTION.SET_UPDATED_SCHEDULE_GROUP: {
      const firstState = state[action.checkType].tabs.find((item) => item.url === action.activeTab)?.state || {};

      const actionSchedulingGroup = action.data.schedulingGroup;
      const stateScheduleGroups = firstState.scheduleGroups || {};
      return setActiveTabState(state, action, {
        scheduleGroups: {
          ...stateScheduleGroups,
          [actionSchedulingGroup]: {
            ...(stateScheduleGroups ? stateScheduleGroups[actionSchedulingGroup as keyof typeof stateScheduleGroups] : {}),
            updatedSchedule: action.data.schedule
          }
        }
      });
    }

    case SOURCE_ACTION.SET_IS_UPDATED_SCHEDULE_GROUP: {
      const firstState = state[action.checkType].tabs.find((item) => item.url === action.activeTab)?.state || {};

      const actionSchedulingGroup = action.data.schedulingGroup;
      const stateScheduleGroups = firstState.scheduleGroups ?? {};
      return setActiveTabState(state, action, {
        scheduleGroups: {
          ...stateScheduleGroups,
          [actionSchedulingGroup]: {
            ...(stateScheduleGroups? stateScheduleGroups[actionSchedulingGroup as keyof typeof stateScheduleGroups] : {}),
            isUpdatedSchedule: action.data.isUpdated
          }
        }
      });
    }
    case SOURCE_ACTION.SET_UPDATED_COMMENTS:
      return setActiveTabState(state, action, {
        updatedComments: action.data
      });
    case SOURCE_ACTION.SET_IS_UPDATED_COMMENTS:
      return setActiveTabState(state, action, {
        isUpdatedComments: action.data
      });
    case SOURCE_ACTION.SET_UPDATED_LABELS:
      return setActiveTabState(state, action, {
        labels: action.data
      });
    case SOURCE_ACTION.SET_IS_UPDATED_LABELS:
      return setActiveTabState(state, action, {
        isUpdatedLabels: action.data
      });
    case SOURCE_ACTION.SET_UPDATED_DATA_STREAMS:
      return setActiveTabState(state, action, {
        updatedDataStreamsMapping: action.data
      });
    case SOURCE_ACTION.SET_IS_UPDATED_DATA_STREAMS:
      return setActiveTabState(state, action, {
        ...state,
        isUpdatedDataStreamsMapping: action.data
      });

// Table reducer here
    case SOURCE_ACTION.GET_TABLE_BASIC:
      return setActiveTabState(state, action,{
        loading: true
      });
    case SOURCE_ACTION.GET_TABLE_BASIC_SUCCESS:
      return setActiveTabState(state, action,{
        loading: false,
        tableBasic: action.data,
        isUpdatedTableBasic: false,
        error: null
      });
    case SOURCE_ACTION.GET_TABLE_BASIC_ERROR:
      return setActiveTabState(state, action,{
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.UPDATE_TABLE_BASIC:
      return setActiveTabState(state, action,{
        isUpdating: true
      });
    case SOURCE_ACTION.UPDATE_TABLE_BASIC_SUCCESS:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: null
      });
    case SOURCE_ACTION.UPDATE_TABLE_BASIC_ERROR:
      return setActiveTabState(state, action,{
        isUpdating: false,
        error: action.error
      });
    case SOURCE_ACTION.RESET_TABLE_SCHEDULE_GROUP:
      return setActiveTabState(state, action,{
        scheduleGroups: undefined
      });
    case SOURCE_ACTION.GET_TABLE_SCHEDULE_GROUP:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_TABLE_SCHEDULE_GROUP_SUCCESS: {
      const firstState = state[action.checkType].tabs.find((item) => item.url === action.activeTab)?.state || {};

      return setActiveTabState(state, action,{
        loading: false,
        scheduleGroups: {
          ...firstState?.scheduleGroups || {},
          [action.schedulingGroup || '']: {
            schedule: action.data,
            updatedSchedule: action.data,
            isUpdatedSchedule: false
          }
        },
        error: null
      });
    }
    case SOURCE_ACTION.GET_TABLE_SCHEDULE_GROUP_ERROR:
      return setActiveTabState(state, action,{
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.UPDATE_TABLE_SCHEDULE_GROUP:
      return setActiveTabState(state, action, {
        isUpdating: true
      });
    case SOURCE_ACTION.UPDATE_TABLE_SCHEDULE_GROUP_SUCCESS:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: null
      });
    case SOURCE_ACTION.UPDATE_TABLE_SCHEDULE_GROUP_ERROR:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: action.error
      });
    case SOURCE_ACTION.GET_TABLE_COMMENTS:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_TABLE_COMMENTS_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        comments: action.data,
        isUpdatedComments: false,
        error: null
      });
    case SOURCE_ACTION.GET_TABLE_COMMENTS_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.UPDATE_TABLE_COMMENTS:
      return setActiveTabState(state, action, {
        isUpdating: true
      });
    case SOURCE_ACTION.UPDATE_TABLE_COMMENTS_SUCCESS:
      return setActiveTabState(state, action,{
        isUpdating: false,
        error: null
      });
    case SOURCE_ACTION.UPDATE_TABLE_COMMENTS_ERROR:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: action.error
      });
    case SOURCE_ACTION.GET_TABLE_LABELS:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_TABLE_LABELS_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        labels: action.data,
        isUpdatedLabels: false,
        error: null
      });
    case SOURCE_ACTION.GET_TABLE_LABELS_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.UPDATE_TABLE_LABELS:
      return setActiveTabState(state, action, {
        isUpdating: true
      });
    case SOURCE_ACTION.UPDATE_TABLE_LABELS_SUCCESS:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: null
      });
    case SOURCE_ACTION.UPDATE_TABLE_LABELS_ERROR:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: action.error
      });
    case SOURCE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        checks: action.data,
        error: null
      });
    case SOURCE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_UI:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_UI_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        checksUI: action.data,
        isUpdatedChecksUi: false,
        error: null
      });
    case SOURCE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_UI_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.GET_TABLE_DEFAULT_DATA_STREAMS_MAPPING:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_TABLE_DEFAULT_DATA_STREAMS_MAPPING_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        dataStreamsMapping: action.data,
        isUpdatedDataStreamsMapping: false,
        error: null
      });
    case SOURCE_ACTION.GET_TABLE_DEFAULT_DATA_STREAMS_MAPPING_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.UPDATE_TABLE_DEFAULT_DATA_STREAMS_MAPPING:
      return setActiveTabState(state, action, {
        isUpdating: true
      });
    case SOURCE_ACTION.UPDATE_TABLE_DEFAULT_DATA_STREAMS_MAPPING_SUCCESS:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: null
      });
    case SOURCE_ACTION.UPDATE_TABLE_DEFAULT_DATA_STREAMS_MAPPING_ERROR:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: action.error
      });
    case SOURCE_ACTION.GET_TABLE_DAILY_RECURRING:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_TABLE_DAILY_RECURRING_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        dailyRecurring: action.data,
        isUpdatedDailyRecurring: false,
        error: null
      });
    case SOURCE_ACTION.GET_TABLE_DAILY_RECURRING_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.GET_TABLE_MONTHLY_RECURRING:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_TABLE_MONTHLY_RECURRING_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        monthlyRecurring: action.data,
        isUpdatedMonthlyRecurring: false,
        error: null
      });
    case SOURCE_ACTION.GET_TABLE_MONTHLY_RECURRING_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.GET_TABLE_PARTITIONED_DAILY_CHECKS:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_TABLE_PARTITIONED_DAILY_CHECKS_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        dailyPartitionedChecks: action.data,
        isUpdatedDailyPartitionedChecks: false,
        error: null
      });
    case SOURCE_ACTION.GET_TABLE_PARTITIONED_DAILY_CHECKS_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.GET_TABLE_PARTITIONED_MONTHLY_CHECKS:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_TABLE_PARTITIONED_MONTHLY_CHECKS_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        monthlyPartitionedChecks: action.data,
        isUpdatedMonthlyPartitionedChecks: false,
        error: null
      });
    case SOURCE_ACTION.GET_TABLE_PARTITIONED_MONTHLY_CHECKS_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.UPDATE_TABLE_DAILY_RECURRING:
      return setActiveTabState(state, action, {
        isUpdating: true
      });
    case SOURCE_ACTION.UPDATE_TABLE_DAILY_RECURRING_SUCCESS:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: null
      });
    case SOURCE_ACTION.UPDATE_TABLE_DAILY_RECURRING_ERROR:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: action.error
      });
    case SOURCE_ACTION.UPDATE_TABLE_MONTHLY_RECURRING:
      return setActiveTabState(state, action, {
        isUpdating: true
      });
    case SOURCE_ACTION.UPDATE_TABLE_MONTHLY_RECURRING_SUCCESS:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: null
      });
    case SOURCE_ACTION.UPDATE_TABLE_MONTHLY_RECURRING_ERROR:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: action.error
      });
    case SOURCE_ACTION.UPDATE_TABLE_PARTITIONED_DAILY_CHECKS:
      return setActiveTabState(state, action, {
        isUpdating: true
      });
    case SOURCE_ACTION.UPDATE_TABLE_PARTITIONED_DAILY_CHECKS_SUCCESS:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: null
      });
    case SOURCE_ACTION.UPDATE_TABLE_PARTITIONED_DAILY_CHECKS_ERROR:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: action.error
      });
    case SOURCE_ACTION.UPDATE_TABLE_PARTITIONED_MONTHLY_CHECKS:
      return setActiveTabState(state, action, {
        isUpdating: true
      });
    case SOURCE_ACTION.UPDATE_TABLE_PARTITIONED_MONTHLY_CHECKS_SUCCESS:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: null
      });
    case SOURCE_ACTION.UPDATE_TABLE_PARTITIONED_MONTHLY_CHECKS_ERROR:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: action.error
      });
    case SOURCE_ACTION.GET_TABLE_PROFILINGS_CHECKS_UI_FILTER:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_TABLE_PROFILINGS_CHECKS_UI_FILTER_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        checksUIFilter: action.data,
        isUpdatedChecksUIFilter: false,
        error: null
      });
    case SOURCE_ACTION.GET_TABLE_PROFILINGS_CHECKS_UI_FILTER_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.GET_TABLE_RECURRING_UI_FILTER:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_TABLE_RECURRING_UI_FILTER_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        RecurringUIFilter: action.data,
        isUpdatedRecurringUIFilter: false,
        error: null
      });
    case SOURCE_ACTION.GET_TABLE_RECURRING_UI_FILTER_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.GET_TABLE_PARTITIONED_CHECKS_UI_FILTER:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_TABLE_PARTITIONED_CHECKS_UI_FILTER_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        partitionedChecksUIFilter: action.data,
        isUpdatedPartitionedChecksUIFilter: false,
        error: null
      });
    case SOURCE_ACTION.GET_TABLE_PARTITIONED_CHECKS_UI_FILTER_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.SET_UPDATED_TABLE_BASIC:
      return setActiveTabState(state, action, {
        isUpdatedTableBasic: true,
        tableBasic: action.data
      });
    case SOURCE_ACTION.SET_UPDATED_SCHEDULE:
      return setActiveTabState(state, action, {
        isUpdatedSchedule: true,
        schedule: action.data
      });
    case SOURCE_ACTION.SET_UPDATED_CHECKS_UI:
      return setActiveTabState(state, action, {
        ...state,
        isUpdatedChecksUi: true,
        checksUI: action.data
      });
    case SOURCE_ACTION.SET_TABLE_DAILY_RECURRING:
      return setActiveTabState(state, action, {
        isUpdatedDailyRecurring: true,
        dailyRecurring: action.data
      });
    case SOURCE_ACTION.SET_TABLE_MONTHLY_RECURRING:
      return setActiveTabState(state, action, {
        isUpdatedMonthlyRecurring: true,
        monthlyRecurring: action.data
      });
    case SOURCE_ACTION.SET_TABLE_PARTITIONED_DAILY_CHECKS:
      return setActiveTabState(state, action, {
        isUpdatedDailyPartitionedChecks: true,
        dailyPartitionedChecks: action.data
      });
    case SOURCE_ACTION.SET_TABLE_PARTITIONED_MONTHLY_CHECKS:
      return setActiveTabState(state, action, {
        isUpdatedMonthlyPartitionedChecks: true,
        monthlyPartitionedChecks: action.data
      });
    case SOURCE_ACTION.SET_TABLE_DEFAULT_DATA_STREAMS_MAPPING:
      return setActiveTabState(state, action, {
        isUpdatedDataStreamsMapping: true,
        dataStreamsMapping: action.data
      });
    case SOURCE_ACTION.SET_UPDATED_CHECKS_UI_FILTER:
      return setActiveTabState(state, action, {
        isUpdatedChecksUIFilter: true,
        checksUIFilter: action.data
      });
    case SOURCE_ACTION.SET_UPDATED_RECURRING_UI_FILTER:
      return setActiveTabState(state, action, {
        isUpdatedRecurringUIFilter: true,
        RecurringUIFilter: action.data
      });
    case SOURCE_ACTION.SET_UPDATED_PARTITIONED_CHECKS_UI_FILTER:
      return setActiveTabState(state, action, {
        isUpdatedPartitionedChecksUIFilter: true,
        partitionedChecksUIFilter: action.data
      });
    case SOURCE_ACTION.GET_TABLE_TIME_STAMPS:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_TABLE_TIME_STAMPS_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        isUpdatedTablePartitioning: false,
        tablePartitioning: action.data
      });
    case SOURCE_ACTION.GET_TABLE_TIME_STAMPS_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
      });

    case SOURCE_ACTION.UPDATE_TABLE_TIME_STAMPS:
      return setActiveTabState(state, action, {
        updatingTablePartitioning: true
      });
    case SOURCE_ACTION.UPDATE_TABLE_TIME_STAMPS_SUCCESS:
      return setActiveTabState(state, action, {
        updatingTablePartitioning: false,
      });
    case SOURCE_ACTION.UPDATE_TABLE_TIME_STAMPS_ERROR:
      return setActiveTabState(state, action, {
        updatingTablePartitioning: false,
      });
    case SOURCE_ACTION.SET_UPDATED_TABLE_TIME_STAMPS:
      return setActiveTabState(state, action, {
        tablePartitioning: action.data,
        isUpdatedTablePartitioning: true,
      });

    // Columns action
    case SOURCE_ACTION.GET_COLUMN_BASIC:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_COLUMN_BASIC_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        columnBasic: action.data,
        isUpdatedColumnBasic: false,
        error: null
      });
    case SOURCE_ACTION.GET_COLUMN_BASIC_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.UPDATE_COLUMN_BASIC:
      return setActiveTabState(state, action, {
        isUpdating: true
      });
    case SOURCE_ACTION.UPDATE_COLUMN_BASIC_SUCCESS:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: null
      });
    case SOURCE_ACTION.UPDATE_COLUMN_BASIC_ERROR:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: action.error
      });
    case SOURCE_ACTION.GET_COLUMN_COMMENTS:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_COLUMN_COMMENTS_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        comments: action.data,
        isUpdatedComments: false,
        error: null
      });
    case SOURCE_ACTION.GET_COLUMN_COMMENTS_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.UPDATE_COLUMN_COMMENTS:
      return setActiveTabState(state, action, {
        isUpdating: true
      });
    case SOURCE_ACTION.UPDATE_COLUMN_COMMENTS_SUCCESS:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: null
      });
    case SOURCE_ACTION.UPDATE_COLUMN_COMMENTS_ERROR:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: action.error
      });
    case SOURCE_ACTION.GET_COLUMN_LABELS:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_COLUMN_LABELS_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        labels: action.data,
        isUpdatedLabels: false,
        error: null
      });
    case SOURCE_ACTION.GET_COLUMN_LABELS_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.UPDATE_COLUMN_LABELS:
      return setActiveTabState(state, action, {
        isUpdating: true
      });
    case SOURCE_ACTION.UPDATE_COLUMN_LABELS_SUCCESS:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: null
      });
    case SOURCE_ACTION.UPDATE_COLUMN_LABELS_ERROR:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: action.error
      });
    case SOURCE_ACTION.GET_COLUMN_CHECKS_UI:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_COLUMN_CHECKS_UI_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        checksUI: action.data,
        isUpdatedChecksUi: false,
        error: null
      });
    case SOURCE_ACTION.GET_COLUMN_CHECKS_UI_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.GET_COLUMN_DAILY_RECURRING:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_COLUMN_DAILY_RECURRING_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        dailyRecurring: action.data,
        isUpdatedDailyRecurring: false,
        error: null
      });
    case SOURCE_ACTION.GET_COLUMN_DAILY_RECURRING_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.GET_COLUMN_MONTHLY_RECURRING:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_COLUMN_MONTHLY_RECURRING_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        monthlyRecurring: action.data,
        isUpdatedMonthlyRecurring: false,
        error: null
      });
    case SOURCE_ACTION.GET_COLUMN_MONTHLY_RECURRING_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.GET_COLUMN_PARTITIONED_DAILY_CHECKS:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_COLUMN_PARTITIONED_DAILY_CHECKS_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        dailyPartitionedChecks: action.data,
        isUpdatedDailyPartitionedChecks: false,
        error: null
      });
    case SOURCE_ACTION.GET_COLUMN_PARTITIONED_DAILY_CHECKS_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.GET_COLUMN_PARTITIONED_MONTHLY_CHECKS:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_COLUMN_PARTITIONED_MONTHLY_CHECKS_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        monthlyPartitionedChecks: action.data,
        isUpdatedMonthlyPartitionedChecks: false,
        error: null
      });
    case SOURCE_ACTION.GET_COLUMN_PARTITIONED_MONTHLY_CHECKS_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.UPDATE_COLUMN_DAILY_RECURRING:
      return setActiveTabState(state, action, {
        isUpdating: true
      });
    case SOURCE_ACTION.UPDATE_COLUMN_DAILY_RECURRING_SUCCESS:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: null
      });
    case SOURCE_ACTION.UPDATE_COLUMN_DAILY_RECURRING_ERROR:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: action.error
      });
    case SOURCE_ACTION.UPDATE_COLUMN_MONTHLY_RECURRING:
      return setActiveTabState(state, action, {
        isUpdating: true
      });
    case SOURCE_ACTION.UPDATE_COLUMN_MONTHLY_RECURRING_SUCCESS:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: null
      });
    case SOURCE_ACTION.UPDATE_COLUMN_MONTHLY_RECURRING_ERROR:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: action.error
      });
    case SOURCE_ACTION.UPDATE_COLUMN_PARTITIONED_DAILY_CHECKS:
      return setActiveTabState(state, action, {
        isUpdating: true
      });
    case SOURCE_ACTION.UPDATE_COLUMN_PARTITIONED_DAILY_CHECKS_SUCCESS:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: null
      });
    case SOURCE_ACTION.UPDATE_COLUMN_PARTITIONED_DAILY_CHECKS_ERROR:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: action.error
      });
    case SOURCE_ACTION.UPDATE_COLUMN_PARTITIONED_MONTHLY_CHECKS:
      return setActiveTabState(state, action, {
        isUpdating: true
      });
    case SOURCE_ACTION.UPDATE_COLUMN_PARTITIONED_MONTHLY_CHECKS_SUCCESS:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: null
      });
    case SOURCE_ACTION.UPDATE_COLUMN_PARTITIONED_MONTHLY_CHECKS_ERROR:
      return setActiveTabState(state, action, {
        isUpdating: false,
        error: action.error
      });
    case SOURCE_ACTION.SET_UPDATED_COLUMN_BASIC:
      return setActiveTabState(state, action, {
        isUpdatedColumnBasic: true,
        columnBasic: action.data
      });
    case SOURCE_ACTION.SET_COLUMN_DAILY_RECURRING:
      return setActiveTabState(state, action, {
        isUpdatedDailyRecurring: true,
        dailyRecurring: action.data
      });
    case SOURCE_ACTION.SET_COLUMN_MONTHLY_RECURRING:
      return setActiveTabState(state, action, {
        isUpdatedMonthlyRecurring: true,
        monthlyRecurring: action.data
      });
    case SOURCE_ACTION.SET_COLUMN_PARTITIONED_DAILY_CHECKS:
      return setActiveTabState(state, action, {
        isUpdatedDailyPartitionedChecks: true,
        dailyPartitionedChecks: action.data
      });
    case SOURCE_ACTION.SET_COLUMN_PARTITIONED_MONTHLY_CHECKS:
      return setActiveTabState(state, action, {
        isUpdatedMonthlyPartitionedChecks: true,
        monthlyPartitionedChecks: action.data
      });
    case SOURCE_ACTION.GET_COLUMN_PROFILINGS_CHECKS_UI_FILTER:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_COLUMN_PROFILINGS_CHECKS_UI_FILTER_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        checksUIFilter: action.data,
        isUpdatedChecksUIFilter: false,
        error: null
      });
    case SOURCE_ACTION.GET_COLUMN_PROFILINGS_CHECKS_UI_FILTER_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.GET_COLUMN_RECURRING_UI_FILTER:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_COLUMN_RECURRING_UI_FILTER_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        RecurringUIFilter: action.data,
        isUpdatedRecurringUIFilter: false,
        error: null
      });
    case SOURCE_ACTION.GET_COLUMN_RECURRING_UI_FILTER_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
        error: action.error
      });
    case SOURCE_ACTION.GET_COLUMN_PARTITIONED_CHECKS_UI_FILTER:
      return setActiveTabState(state, action, {
        loading: true
      });
    case SOURCE_ACTION.GET_COLUMN_PARTITIONED_CHECKS_UI_FILTER_SUCCESS:
      return setActiveTabState(state, action, {
        loading: false,
        partitionedChecksUIFilter: action.data,
        isUpdatedPartitionedChecksUIFilter: false,
        error: null
      });
    case SOURCE_ACTION.GET_COLUMN_PARTITIONED_CHECKS_UI_FILTER_ERROR:
      return setActiveTabState(state, action, {
        loading: false,
        error: action.error
      });
    default:
      return state;
  }
};

export default connectionReducer;
