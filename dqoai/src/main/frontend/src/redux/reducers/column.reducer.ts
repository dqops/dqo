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
  ColumnBasicModel,
  ColumnModel,
  UICheckContainerModel
} from '../../api';
import { COLUMN_ACTION } from '../types';

export interface IColumnState {
  columns: ColumnModel[];
  loading: boolean;
  error: any;
  activeColumn: string;
  columnBasic?: ColumnBasicModel;
  isUpdatedColumnBasic?: boolean;
  isUpdating: boolean;
  comments: CommentSpec[];
  isUpdatedComments?: boolean;
  labels: string[];
  isUpdatedLabels?: boolean;
  checksUI?: UICheckContainerModel;
  isUpdatedChecksUi?: boolean;
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
  recurringChecksUIFilter?: UICheckContainerModel;
  isUpdatedRecurringChecksUIFilter?: boolean;
  partitionedChecksUIFilter?: UICheckContainerModel;
  isUpdatedPartitionedChecksUIFilter?: boolean;
}

const initialState: IColumnState = {
  columns: [],
  loading: false,
  error: null,
  activeColumn: '',
  isUpdating: false,
  comments: [],
  labels: [],
  isUpdatedLabels: false
};

const columnReducer = (state = initialState, action: any) => {
  switch (action.type) {
    case COLUMN_ACTION.GET_COLUMNS:
      return {
        ...state,
        loading: true
      };
    case COLUMN_ACTION.GET_COLUMNS_SUCCESS:
      return {
        ...state,
        loading: false,
        columns: action.data,
        error: null
      };
    case COLUMN_ACTION.GET_COLUMNS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case COLUMN_ACTION.GET_COLUMN_BASIC:
      return {
        ...state,
        loading: true
      };
    case COLUMN_ACTION.GET_COLUMN_BASIC_SUCCESS:
      return {
        ...state,
        loading: false,
        columnBasic: action.data,
        isUpdatedColumnBasic: false,
        error: null
      };
    case COLUMN_ACTION.GET_COLUMN_BASIC_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case COLUMN_ACTION.UPDATE_COLUMN_BASIC:
      return {
        ...state,
        isUpdating: true
      };
    case COLUMN_ACTION.UPDATE_COLUMN_BASIC_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case COLUMN_ACTION.UPDATE_COLUMN_BASIC_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case COLUMN_ACTION.GET_COLUMN_COMMENTS:
      return {
        ...state,
        loading: true
      };
    case COLUMN_ACTION.GET_COLUMN_COMMENTS_SUCCESS:
      return {
        ...state,
        loading: false,
        comments: action.data,
        isUpdatedComments: false,
        error: null
      };
    case COLUMN_ACTION.GET_COLUMN_COMMENTS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case COLUMN_ACTION.UPDATE_COLUMN_COMMENTS:
      return {
        ...state,
        isUpdating: true
      };
    case COLUMN_ACTION.UPDATE_COLUMN_COMMENTS_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case COLUMN_ACTION.UPDATE_COLUMN_COMMENTS_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case COLUMN_ACTION.GET_COLUMN_LABELS:
      return {
        ...state,
        loading: true
      };
    case COLUMN_ACTION.GET_COLUMN_LABELS_SUCCESS:
      return {
        ...state,
        loading: false,
        labels: action.data,
        isUpdatedLabels: false,
        error: null
      };
    case COLUMN_ACTION.GET_COLUMN_LABELS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case COLUMN_ACTION.UPDATE_COLUMN_LABELS:
      return {
        ...state,
        isUpdating: true
      };
    case COLUMN_ACTION.UPDATE_COLUMN_LABELS_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case COLUMN_ACTION.UPDATE_COLUMN_LABELS_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case COLUMN_ACTION.GET_COLUMN_CHECKS_UI:
      return {
        ...state,
        loading: true
      };
    case COLUMN_ACTION.GET_COLUMN_CHECKS_UI_SUCCESS:
      return {
        ...state,
        loading: false,
        checksUI: action.data,
        isUpdatedChecksUi: false,
        error: null
      };
    case COLUMN_ACTION.GET_COLUMN_CHECKS_UI_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case COLUMN_ACTION.GET_COLUMN_DAILY_RECURRING:
      return {
        ...state,
        loading: true
      };
    case COLUMN_ACTION.GET_COLUMN_DAILY_RECURRING_SUCCESS:
      return {
        ...state,
        loading: false,
        dailyRecurring: action.data,
        isUpdatedDailyRecurring: false,
        error: null
      };
    case COLUMN_ACTION.GET_COLUMN_DAILY_RECURRING_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case COLUMN_ACTION.GET_COLUMN_MONTHLY_RECURRING:
      return {
        ...state,
        loading: true
      };
    case COLUMN_ACTION.GET_COLUMN_MONTHLY_RECURRING_SUCCESS:
      return {
        ...state,
        loading: false,
        monthlyRecurring: action.data,
        isUpdatedMonthlyRecurring: false,
        error: null
      };
    case COLUMN_ACTION.GET_COLUMN_MONTHLY_RECURRING_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case COLUMN_ACTION.GET_COLUMN_PARTITIONED_DAILY_CHECKS:
      return {
        ...state,
        loading: true
      };
    case COLUMN_ACTION.GET_COLUMN_PARTITIONED_DAILY_CHECKS_SUCCESS:
      return {
        ...state,
        loading: false,
        dailyPartitionedChecks: action.data,
        isUpdatedDailyPartitionedChecks: false,
        error: null
      };
    case COLUMN_ACTION.GET_COLUMN_PARTITIONED_DAILY_CHECKS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case COLUMN_ACTION.GET_COLUMN_PARTITIONED_MONTHLY_CHECKS:
      return {
        ...state,
        loading: true
      };
    case COLUMN_ACTION.GET_COLUMN_PARTITIONED_MONTHLY_CHECKS_SUCCESS:
      return {
        ...state,
        loading: false,
        monthlyPartitionedChecks: action.data,
        isUpdatedMonthlyPartitionedChecks: false,
        error: null
      };
    case COLUMN_ACTION.GET_COLUMN_PARTITIONED_MONTHLY_CHECKS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case COLUMN_ACTION.UPDATE_COLUMN_DAILY_RECURRING:
      return {
        ...state,
        isUpdating: true
      };
    case COLUMN_ACTION.UPDATE_COLUMN_DAILY_RECURRING_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case COLUMN_ACTION.UPDATE_COLUMN_DAILY_RECURRING_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case COLUMN_ACTION.UPDATE_COLUMN_MONTHLY_RECURRING:
      return {
        ...state,
        isUpdating: true
      };
    case COLUMN_ACTION.UPDATE_COLUMN_MONTHLY_RECURRING_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case COLUMN_ACTION.UPDATE_COLUMN_MONTHLY_RECURRING_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case COLUMN_ACTION.UPDATE_COLUMN_PARTITIONED_DAILY_CHECKS:
      return {
        ...state,
        isUpdating: true
      };
    case COLUMN_ACTION.UPDATE_COLUMN_PARTITIONED_DAILY_CHECKS_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case COLUMN_ACTION.UPDATE_COLUMN_PARTITIONED_DAILY_CHECKS_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case COLUMN_ACTION.UPDATE_COLUMN_PARTITIONED_MONTHLY_CHECKS:
      return {
        ...state,
        isUpdating: true
      };
    case COLUMN_ACTION.UPDATE_COLUMN_PARTITIONED_MONTHLY_CHECKS_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case COLUMN_ACTION.UPDATE_COLUMN_PARTITIONED_MONTHLY_CHECKS_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case COLUMN_ACTION.SET_UPDATED_COLUMN_BASIC:
      return {
        ...state,
        isUpdatedColumnBasic: true,
        columnBasic: action.column
      };
    case COLUMN_ACTION.SET_UPDATED_COMMENTS:
      return {
        ...state,
        isUpdatedComments: true,
        comments: action.comments
      };
    case COLUMN_ACTION.SET_IS_UPDATED_COMMENTS:
      return {
        ...state,
        isUpdatedComments: true
      };
    case COLUMN_ACTION.SET_UPDATED_LABELS:
      return {
        ...state,
        isUpdatedLabels: true,
        labels: action.labels
      };
    case COLUMN_ACTION.SET_UPDATED_CHECKS_UI:
      return {
        ...state,
        isUpdatedChecksUi: true,
        checksUI: action.checksUI
      };
    case COLUMN_ACTION.SET_COLUMN_DAILY_RECURRING:
      return {
        ...state,
        isUpdatedDailyRecurring: true,
        dailyRecurring: action.checksUI
      };
    case COLUMN_ACTION.SET_COLUMN_MONTHLY_RECURRING:
      return {
        ...state,
        isUpdatedMonthlyRecurring: true,
        monthlyRecurring: action.checksUI
      };
    case COLUMN_ACTION.SET_COLUMN_PARTITIONED_DAILY_CHECKS:
      return {
        ...state,
        isUpdatedDailyPartitionedChecks: true,
        dailyPartitionedChecks: action.checksUI
      };
    case COLUMN_ACTION.SET_COLUMN_PARTITIONED_MONTHLY_CHECKS:
      return {
        ...state,
        isUpdatedMonthlyPartitionedChecks: true,
        monthlyPartitionedChecks: action.checksUI
      };
    case COLUMN_ACTION.GET_COLUMN_PROFILINGS_CHECKS_UI_FILTER:
      return {
        ...state,
        loading: true
      };
    case COLUMN_ACTION.GET_COLUMN_PROFILINGS_CHECKS_UI_FILTER_SUCCESS:
      return {
        ...state,
        loading: false,
        checksUIFilter: action.data,
        isUpdatedChecksUIFilter: false,
        error: null
      };
    case COLUMN_ACTION.GET_COLUMN_PROFILINGS_CHECKS_UI_FILTER_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case COLUMN_ACTION.GET_COLUMN_RECURRING_UI_FILTER:
      return {
        ...state,
        loading: true
      };
    case COLUMN_ACTION.GET_COLUMN_RECURRING_UI_FILTER_SUCCESS:
      return {
        ...state,
        loading: false,
        RecurringChecksUIFilter: action.data,
        isUpdatedRecurringChecksUIFilter: false,
        error: null
      };
    case COLUMN_ACTION.GET_COLUMN_RECURRING_UI_FILTER_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case COLUMN_ACTION.GET_COLUMN_PARTITIONED_CHECKS_UI_FILTER:
      return {
        ...state,
        loading: true
      };
    case COLUMN_ACTION.GET_COLUMN_PARTITIONED_CHECKS_UI_FILTER_SUCCESS:
      return {
        ...state,
        loading: false,
        partitionedChecksUIFilter: action.data,
        isUpdatedPartitionedChecksUIFilter: false,
        error: null
      };
    case COLUMN_ACTION.GET_COLUMN_PARTITIONED_CHECKS_UI_FILTER_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case COLUMN_ACTION.SET_UPDATED_CHECKS_UI_FILTER:
      return {
        ...state,
        isUpdatedChecksUIFilter: true,
        checksUIFilter: action.data,
      };
    case COLUMN_ACTION.SET_UPDATED_RECURRING_UI_FILTER:
      return {
        ...state,
        isUpdatedRecurringChecksUIFilter: true,
        RecurringChecksUIFilter: action.data,
      };
    case COLUMN_ACTION.SET_UPDATED_PARTITIONED_CHECKS_UI_FILTER:
      return {
        ...state,
        isUpdatedPartitionedChecksUIFilter: true,
        partitionedChecksUIFilter: action.data,
      };
    default:
      return state;
  }
};

export default columnReducer;
