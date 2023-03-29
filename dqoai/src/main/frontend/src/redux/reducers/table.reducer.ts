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
  CommentSpec,
  TableBasicModel,
  TableModel,
  RecurringScheduleSpec,
  TableProfilingCheckCategoriesSpec,
  UICheckContainerModel,
  DataStreamMappingSpec, TablePartitioningModel
} from '../../api';
import { TABLE_ACTION } from '../types';

export interface ITableState {
  tables: TableModel[];
  loading: boolean;
  error: any;
  activeTable: string;
  tableBasic?: TableBasicModel;
  isUpdating: boolean;
  isUpdatedTableBasic?: boolean;
  schedule?: RecurringScheduleSpec;
  isUpdatedSchedule?: boolean;
  scheduleGroups?: {
    profiling?: {
      schedule?: RecurringScheduleSpec;
      updatedSchedule?: RecurringScheduleSpec;
      isUpdatedSchedule?: boolean;
    }
    recurring_daily?: {
      schedule?: RecurringScheduleSpec;
      updatedSchedule?: RecurringScheduleSpec;
      isUpdatedSchedule?: boolean;
    }
    recurring_monthly?: {
      schedule?: RecurringScheduleSpec;
      updatedSchedule?: RecurringScheduleSpec;
      isUpdatedSchedule?: boolean;
    }
    partitioned_daily?: {
      schedule?: RecurringScheduleSpec;
      updatedSchedule?: RecurringScheduleSpec;
      isUpdatedSchedule?: boolean;
    }
    partitioned_monthly?: {
      schedule?: RecurringScheduleSpec;
      updatedSchedule?: RecurringScheduleSpec;
      isUpdatedSchedule?: boolean;
    }
  }
  comments: CommentSpec[];
  isUpdatedComments?: boolean;
  labels: string[];
  isUpdatedLabels?: boolean;
  checks?: TableProfilingCheckCategoriesSpec;
  checksUI?: UICheckContainerModel;
  isUpdatedChecksUi?: boolean;
  dataStreamsMapping?: DataStreamMappingSpec;
  isUpdatedDataStreamsMapping?: boolean;
  dailyRecurring?: UICheckContainerModel;
  isUpdatedDailyRecurring?: boolean;
  monthlyRecurring?: UICheckContainerModel;
  isUpdatedMonthlyRecurring?: boolean;
  dailyPartitionedChecks?: UICheckContainerModel;
  isUpdatedDailyPartitionedChecks?: boolean;
  monthlyPartitionedChecks?: UICheckContainerModel;
  isUpdatedMonthlyPartitionedChecks?: boolean;
  checksUIFilter?: UICheckContainerModel;
  isUpdatedChecksUIFilter?: boolean;
  recurringUIFilter?: UICheckContainerModel;
  isUpdatedRecurringUIFilter?: boolean;
  partitionedChecksUIFilter?: UICheckContainerModel;
  isUpdatedPartitionedChecksUIFilter?: boolean;
  tablePartitioning?: TablePartitioningModel
  isUpdatedTablePartitioning?: boolean;
  loadingTablePartitioning: boolean;
  updatingTablePartitioning: boolean;
}

const initialState: ITableState = {
  tables: [],
  loading: false,
  error: null,
  activeTable: '',
  isUpdating: false,
  comments: [],
  labels: [],
  loadingTablePartitioning: false,
  updatingTablePartitioning: false,
};

const tableReducer = (state = initialState, action: any) => {
  switch (action.type) {
    case TABLE_ACTION.GET_TABLES:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLES_SUCCESS:
      return {
        ...state,
        loading: false,
        tables: action.data,
        error: null
      };
    case TABLE_ACTION.GET_TABLES_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_BASIC:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_BASIC_SUCCESS:
      return {
        ...state,
        loading: false,
        tableBasic: action.data,
        isUpdatedTableBasic: false,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_BASIC_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.UPDATE_TABLE_BASIC:
      return {
        ...state,
        isUpdating: true
      };
    case TABLE_ACTION.UPDATE_TABLE_BASIC_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case TABLE_ACTION.UPDATE_TABLE_BASIC_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case TABLE_ACTION.RESET_TABLE_SCHEDULE_GROUP:
      return {
        ...state,
        scheduleGroups: undefined
      }
    case TABLE_ACTION.GET_TABLE_SCHEDULE_GROUP:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_SCHEDULE_GROUP_SUCCESS:
      return {
        ...state,
        loading: false,
        scheduleGroups: {
          ...state.scheduleGroups,
          [action.schedulingGroup]: {
            schedule: action.data,
            updatedSchedule: action.data,
            isUpdatedSchedule: false
          }
        },
        error: null
      };
    case TABLE_ACTION.GET_TABLE_SCHEDULE_GROUP_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.UPDATE_TABLE_SCHEDULE_GROUP:
      return {
        ...state,
        isUpdating: true
      };
    case TABLE_ACTION.UPDATE_TABLE_SCHEDULE_GROUP_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case TABLE_ACTION.UPDATE_TABLE_SCHEDULE_GROUP_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_COMMENTS:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_COMMENTS_SUCCESS:
      return {
        ...state,
        loading: false,
        comments: action.data,
        isUpdatedComments: false,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_COMMENTS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.UPDATE_TABLE_COMMENTS:
      return {
        ...state,
        isUpdating: true
      };
    case TABLE_ACTION.UPDATE_TABLE_COMMENTS_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case TABLE_ACTION.UPDATE_TABLE_COMMENTS_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_LABELS:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_LABELS_SUCCESS:
      return {
        ...state,
        loading: false,
        labels: action.data,
        isUpdatedLabels: false,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_LABELS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.UPDATE_TABLE_LABELS:
      return {
        ...state,
        isUpdating: true
      };
    case TABLE_ACTION.UPDATE_TABLE_LABELS_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case TABLE_ACTION.UPDATE_TABLE_LABELS_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_SUCCESS:
      return {
        ...state,
        loading: false,
        checks: action.data,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_UI:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_UI_SUCCESS:
      return {
        ...state,
        loading: false,
        checksUI: action.data,
        isUpdatedChecksUi: false,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_UI_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_DEFAULT_DATA_STREAMS_MAPPING:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_DEFAULT_DATA_STREAMS_MAPPING_SUCCESS:
      return {
        ...state,
        loading: false,
        dataStreamsMapping: action.data,
        isUpdatedDataStreamsMapping: false,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_DEFAULT_DATA_STREAMS_MAPPING_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.UPDATE_TABLE_DEFAULT_DATA_STREAMS_MAPPING:
      return {
        ...state,
        isUpdating: true
      };
    case TABLE_ACTION.UPDATE_TABLE_DEFAULT_DATA_STREAMS_MAPPING_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case TABLE_ACTION.UPDATE_TABLE_DEFAULT_DATA_STREAMS_MAPPING_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_DAILY_RECURRING:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_DAILY_RECURRING_SUCCESS:
      return {
        ...state,
        loading: false,
        dailyRecurring: action.data,
        isUpdatedDailyRecurring: false,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_DAILY_RECURRING_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_MONTHLY_RECURRING:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_MONTHLY_RECURRING_SUCCESS:
      return {
        ...state,
        loading: false,
        monthlyRecurring: action.data,
        isUpdatedMonthlyRecurring: false,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_MONTHLY_RECURRING_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_PARTITIONED_DAILY_CHECKS:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_PARTITIONED_DAILY_CHECKS_SUCCESS:
      return {
        ...state,
        loading: false,
        dailyPartitionedChecks: action.data,
        isUpdatedDailyPartitionedChecks: false,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_PARTITIONED_DAILY_CHECKS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_PARTITIONED_MONTHLY_CHECKS:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_PARTITIONED_MONTHLY_CHECKS_SUCCESS:
      return {
        ...state,
        loading: false,
        monthlyPartitionedChecks: action.data,
        isUpdatedMonthlyPartitionedChecks: false,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_PARTITIONED_MONTHLY_CHECKS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.UPDATE_TABLE_DAILY_RECURRING:
      return {
        ...state,
        isUpdating: true
      };
    case TABLE_ACTION.UPDATE_TABLE_DAILY_RECURRING_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case TABLE_ACTION.UPDATE_TABLE_DAILY_RECURRING_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case TABLE_ACTION.UPDATE_TABLE_MONTHLY_RECURRING:
      return {
        ...state,
        isUpdating: true
      };
    case TABLE_ACTION.UPDATE_TABLE_MONTHLY_RECURRING_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case TABLE_ACTION.UPDATE_TABLE_MONTHLY_RECURRING_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case TABLE_ACTION.UPDATE_TABLE_PARTITIONED_DAILY_CHECKS:
      return {
        ...state,
        isUpdating: true
      };
    case TABLE_ACTION.UPDATE_TABLE_PARTITIONED_DAILY_CHECKS_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case TABLE_ACTION.UPDATE_TABLE_PARTITIONED_DAILY_CHECKS_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case TABLE_ACTION.UPDATE_TABLE_PARTITIONED_MONTHLY_CHECKS:
      return {
        ...state,
        isUpdating: true
      };
    case TABLE_ACTION.UPDATE_TABLE_PARTITIONED_MONTHLY_CHECKS_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case TABLE_ACTION.UPDATE_TABLE_PARTITIONED_MONTHLY_CHECKS_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_PROFILINGS_CHECKS_UI_FILTER:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_PROFILINGS_CHECKS_UI_FILTER_SUCCESS:
      return {
        ...state,
        loading: false,
        checksUIFilter: action.data,
        isUpdatedChecksUIFilter: false,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_PROFILINGS_CHECKS_UI_FILTER_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_RECURRING_UI_FILTER:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_RECURRING_UI_FILTER_SUCCESS:
      return {
        ...state,
        loading: false,
        RecurringUIFilter: action.data,
        isUpdatedRecurringUIFilter: false,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_RECURRING_UI_FILTER_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_PARTITIONED_CHECKS_UI_FILTER:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_PARTITIONED_CHECKS_UI_FILTER_SUCCESS:
      return {
        ...state,
        loading: false,
        partitionedChecksUIFilter: action.data,
        isUpdatedPartitionedChecksUIFilter: false,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_PARTITIONED_CHECKS_UI_FILTER_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.SET_UPDATED_TABLE_BASIC:
      return {
        ...state,
        isUpdatedTableBasic: true,
        tableBasic: action.table
      };
    case TABLE_ACTION.SET_UPDATED_SCHEDULE:
      return {
        ...state,
        isUpdatedSchedule: true,
        schedule: action.schedule
      };
    case TABLE_ACTION.SET_UPDATED_SCHEDULE_GROUP: {
      const actionSchedulingGroup = action.schedulingGroup;
      const stateScheduleGroups = state.scheduleGroups ?? {};
      return {
        ...state,
        scheduleGroups: {
          ...stateScheduleGroups,
          [actionSchedulingGroup]: {
            ...(stateScheduleGroups?.[actionSchedulingGroup as keyof typeof stateScheduleGroups] || {}),
            updatedSchedule: action.schedule
          }
        }
      };
    }
    case TABLE_ACTION.SET_IS_UPDATED_SCHEDULE_GROUP: {
      const actionSchedulingGroup = action.schedulingGroup;
      const stateScheduleGroups = state.scheduleGroups ?? {};
      return {
        ...state,
        scheduleGroups: {
          ...stateScheduleGroups,
          [actionSchedulingGroup]: {
            ...(stateScheduleGroups?.[actionSchedulingGroup as keyof typeof stateScheduleGroups] || {}),
            isUpdatedSchedule: action.isUpdated
          }
        }
      };
    }
    case TABLE_ACTION.SET_UPDATED_COMMENTS:
      return {
        ...state,
        isUpdatedComments: true,
        comments: action.comments
      };
    case TABLE_ACTION.SET_IS_UPDATED_COMMENTS:
      return {
        ...state,
        isUpdatedComments: true
      };
    case TABLE_ACTION.SET_UPDATED_LABELS:
      return {
        ...state,
        isUpdatedLabels: true,
        labels: action.labels
      };
    case TABLE_ACTION.SET_UPDATED_CHECKS_UI:
      return {
        ...state,
        isUpdatedChecksUi: true,
        checksUI: action.checksUI
      };
    case TABLE_ACTION.SET_TABLE_DAILY_RECURRING:
      return {
        ...state,
        isUpdatedDailyRecurring: true,
        dailyRecurring: action.checksUI
      };
    case TABLE_ACTION.SET_TABLE_MONTHLY_RECURRING:
      return {
        ...state,
        isUpdatedMonthlyRecurring: true,
        monthlyRecurring: action.checksUI
      };
    case TABLE_ACTION.SET_TABLE_PARTITIONED_DAILY_CHECKS:
      return {
        ...state,
        isUpdatedDailyPartitionedChecks: true,
        dailyPartitionedChecks: action.checksUI
      };
    case TABLE_ACTION.SET_TABLE_PARTITIONED_MONTHLY_CHECKS:
      return {
        ...state,
        isUpdatedMonthlyPartitionedChecks: true,
        monthlyPartitionedChecks: action.checksUI
      };
    case TABLE_ACTION.SET_TABLE_DEFAULT_DATA_STREAMS_MAPPING:
      return {
        ...state,
        isUpdatedDataStreamsMapping: true,
        dataStreamsMapping: action.dataStreamsMapping
      };
    case TABLE_ACTION.SET_UPDATED_CHECKS_UI_FILTER:
      return {
        ...state,
        isUpdatedChecksUIFilter: true,
        checksUIFilter: action.data
      }
    case TABLE_ACTION.SET_UPDATED_RECURRING_UI_FILTER:
      return {
        ...state,
        isUpdatedRecurringUIFilter: true,
        RecurringUIFilter: action.data
      }
    case TABLE_ACTION.SET_UPDATED_PARTITIONED_CHECKS_UI_FILTER:
      return {
        ...state,
        isUpdatedPartitionedChecksUIFilter: true,
        partitionedChecksUIFilter: action.data
      }
    case TABLE_ACTION.GET_TABLE_TIME_STAMPS:
      return {
        ...state,
        loading: true
      }
    case TABLE_ACTION.GET_TABLE_TIME_STAMPS_SUCCESS:
      return {
        ...state,
        loading: false,
        isUpdatedTablePartitioning: false,
        tablePartitioning: action.data
      }
    case TABLE_ACTION.GET_TABLE_TIME_STAMPS_ERROR:
      return {
        ...state,
        loading: false,
      }

    case TABLE_ACTION.UPDATE_TABLE_TIME_STAMPS:
      return {
        ...state,
        updatingTablePartitioning: true
      }
    case TABLE_ACTION.UPDATE_TABLE_TIME_STAMPS_SUCCESS:
      return {
        ...state,
        updatingTablePartitioning: false,
      }
    case TABLE_ACTION.UPDATE_TABLE_TIME_STAMPS_ERROR:
      return {
        ...state,
        updatingTablePartitioning: false,
      }
    case TABLE_ACTION.SET_UPDATED_TABLE_TIME_STAMPS:
      return {
        ...state,
        tablePartitioning: action.data,
        isUpdatedTablePartitioning: true,
      }
    default:
      return state;
  }
};

export default tableReducer;
