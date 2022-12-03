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
  TimeSeriesConfigurationSpec,
  TableAdHocCheckCategoriesSpec,
  UIAllChecksModel,
  DataStreamMappingSpec
} from '../../api';
import { TABLE_ACTION } from '../types';

export interface ITableState {
  tables: TableModel[];
  loading: boolean;
  error: any;
  activeTable: string;
  tableBasic?: TableBasicModel;
  isUpdating: boolean;
  schedule?: RecurringScheduleSpec;
  timeSeries?: TimeSeriesConfigurationSpec;
  comments: CommentSpec[];
  labels: string[];
  checks?: TableAdHocCheckCategoriesSpec;
  checksUI?: UIAllChecksModel;
  dataStreamsMapping?: DataStreamMappingSpec;
  dailyCheckpoints?: UIAllChecksModel;
  monthlyCheckpoints?: UIAllChecksModel;
  dailyPartitionedChecks?: UIAllChecksModel;
  monthlyPartitionedChecks?: UIAllChecksModel;
}

const initialState: ITableState = {
  tables: [],
  loading: false,
  error: null,
  activeTable: '',
  isUpdating: false,
  comments: [],
  labels: []
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
    case TABLE_ACTION.GET_TABLE_SCHEDULE:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_SCHEDULE_SUCCESS:
      return {
        ...state,
        loading: false,
        schedule: action.data,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_SCHEDULE_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.UPDATE_TABLE_SCHEDULE:
      return {
        ...state,
        isUpdating: true
      };
    case TABLE_ACTION.UPDATE_TABLE_SCHEDULE_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case TABLE_ACTION.UPDATE_TABLE_SCHEDULE_ERROR:
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
        error: null
      };
    case TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_UI_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_DATA_STREAMS_MAPPING:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_DATA_STREAMS_MAPPING_SUCCESS:
      return {
        ...state,
        loading: false,
        dataStreamsMapping: action.data,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_DATA_STREAMS_MAPPING_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.UPDATE_TABLE_DATA_STREAMS_MAPPING:
      return {
        ...state,
        isUpdating: true
      };
    case TABLE_ACTION.UPDATE_TABLE_DATA_STREAMS_MAPPING_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case TABLE_ACTION.UPDATE_TABLE_DATA_STREAMS_MAPPING_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_DAILY_CHECKPOINTS:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_DAILY_CHECKPOINTS_SUCCESS:
      return {
        ...state,
        loading: false,
        dailyCheckpoints: action.data,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_DAILY_CHECKPOINTS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_MONTHLY_CHECKPOINTS:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_MONTHLY_CHECKPOINTS_SUCCESS:
      return {
        ...state,
        loading: false,
        monthlyCheckpoints: action.data,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_MONTHLY_CHECKPOINTS_ERROR:
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
        error: null
      };
    case TABLE_ACTION.GET_TABLE_PARTITIONED_MONTHLY_CHECKS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.UPDATE_TABLE_DAILY_CHECKPOINTS:
      return {
        ...state,
        isUpdating: true
      };
    case TABLE_ACTION.UPDATE_TABLE_DAILY_CHECKPOINTS_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case TABLE_ACTION.UPDATE_TABLE_DAILY_CHECKPOINTS_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case TABLE_ACTION.UPDATE_TABLE_MONTHLY_CHECKPOINTS:
      return {
        ...state,
        isUpdating: true
      };
    case TABLE_ACTION.UPDATE_TABLE_MONTHLY_CHECKPOINTS_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case TABLE_ACTION.UPDATE_TABLE_MONTHLY_CHECKPOINTS_ERROR:
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
    default:
      return state;
  }
};

export default tableReducer;
