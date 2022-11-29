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
  UIAllChecksModel
} from '../../api';
import { COLUMN_ACTION } from '../types';

export interface IColumnState {
  columns: ColumnModel[];
  loading: boolean;
  error: any;
  activeColumn: string;
  columnBasic?: ColumnBasicModel;
  isUpdating: boolean;
  comments: CommentSpec[];
  labels: string[];
  checksUI?: UIAllChecksModel;
  dailyCheckpoints?: UIAllChecksModel;
  monthlyCheckpoints?: UIAllChecksModel;
  dailyPartitionedChecks?: UIAllChecksModel;
  monthlyPartitionedChecks?: UIAllChecksModel;
}

const initialState: IColumnState = {
  columns: [],
  loading: false,
  error: null,
  activeColumn: '',
  isUpdating: false,
  comments: [],
  labels: []
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
        error: null
      };
    case COLUMN_ACTION.GET_COLUMN_CHECKS_UI_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case COLUMN_ACTION.GET_COLUMN_DAILY_CHECKPOINTS:
      return {
        ...state,
        loading: true
      };
    case COLUMN_ACTION.GET_COLUMN_DAILY_CHECKPOINTS_SUCCESS:
      return {
        ...state,
        loading: false,
        dailyCheckpoints: action.data,
        error: null
      };
    case COLUMN_ACTION.GET_COLUMN_DAILY_CHECKPOINTS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case COLUMN_ACTION.GET_COLUMN_MONTHLY_CHECKPOINTS:
      return {
        ...state,
        loading: true
      };
    case COLUMN_ACTION.GET_COLUMN_MONTHLY_CHECKPOINTS_SUCCESS:
      return {
        ...state,
        loading: false,
        monthlyCheckpoints: action.data,
        error: null
      };
    case COLUMN_ACTION.GET_COLUMN_MONTHLY_CHECKPOINTS_ERROR:
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
        error: null
      };
    case COLUMN_ACTION.GET_COLUMN_PARTITIONED_MONTHLY_CHECKS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case COLUMN_ACTION.UPDATE_COLUMN_DAILY_CHECKPOINTS:
      return {
        ...state,
        isUpdating: true
      };
    case COLUMN_ACTION.UPDATE_COLUMN_DAILY_CHECKPOINTS_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case COLUMN_ACTION.UPDATE_COLUMN_DAILY_CHECKPOINTS_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case COLUMN_ACTION.UPDATE_COLUMN_MONTHLY_CHECKPOINTS:
      return {
        ...state,
        isUpdating: true
      };
    case COLUMN_ACTION.UPDATE_COLUMN_MONTHLY_CHECKPOINTS_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case COLUMN_ACTION.UPDATE_COLUMN_MONTHLY_CHECKPOINTS_ERROR:
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
    default:
      return state;
  }
};

export default columnReducer;
