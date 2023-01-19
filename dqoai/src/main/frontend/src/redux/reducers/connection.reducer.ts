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
  ConnectionBasicModel,
  ConnectionModel,
  DataStreamMappingSpec,
  RecurringScheduleSpec,
  TimeSeriesConfigurationSpec
} from '../../api';
import { CONNECTION_ACTION } from '../types';

export interface IConnectionState {
  connections: ConnectionModel[];
  loading: boolean;
  error: any;
  activeConnection: string;
  connectionBasic?: ConnectionBasicModel;
  isUpdating: boolean;
  schedule?: RecurringScheduleSpec;
  timeSeries?: TimeSeriesConfigurationSpec;
  comments: CommentSpec[];
  defaultDataStreams?: DataStreamMappingSpec;
  labels: string[];
  updatedConnectionBasic?: ConnectionBasicModel;
  isUpdatedConnectionBasic?: boolean;
  updatedSchedule?: RecurringScheduleSpec;
  isUpdatedSchedule?: boolean;
  updatedComments?: CommentSpec[];
  isUpdatedComments?: boolean;
  isUpdatedLabels?: boolean;
  updatedDataStreamsMapping?: DataStreamMappingSpec;
  isUpdatedDataStreamsMapping?: boolean;
}

const initialState: IConnectionState = {
  connections: [],
  loading: false,
  error: null,
  activeConnection: '',
  isUpdating: false,
  comments: [],
  labels: []
};

const connectionReducer = (state = initialState, action: any) => {
  switch (action.type) {
    case CONNECTION_ACTION.GET_CONNECTIONS:
      return {
        ...state,
        loading: true
      };
    case CONNECTION_ACTION.GET_CONNECTIONS_SUCCESS:
      return {
        ...state,
        loading: false,
        connections: action.data,
        error: null
      };
    case CONNECTION_ACTION.GET_CONNECTIONS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case CONNECTION_ACTION.GET_CONNECTION_BASIC:
      return {
        ...state,
        loading: true
      };
    case CONNECTION_ACTION.GET_CONNECTION_BASIC_SUCCESS:
      return {
        ...state,
        loading: false,
        connectionBasic: action.data,
        updatedConnectionBasic: action.data,
        error: null
      };
    case CONNECTION_ACTION.GET_CONNECTION_BASIC_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case CONNECTION_ACTION.SET_ACTIVE_CONNECTION:
      return {
        ...state,
        activeConnection: action.activeConnection
      };
    case CONNECTION_ACTION.UPDATE_CONNECTION_BASIC:
      return {
        ...state,
        isUpdating: true
      };
    case CONNECTION_ACTION.UPDATE_CONNECTION_BASIC_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case CONNECTION_ACTION.UPDATE_CONNECTION_BASIC_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case CONNECTION_ACTION.GET_CONNECTION_SCHEDULE:
      return {
        ...state,
        loading: true
      };
    case CONNECTION_ACTION.GET_CONNECTION_SCHEDULE_SUCCESS:
      return {
        ...state,
        loading: false,
        schedule: action.data,
        updatedSchedule: action.data,
        error: null
      };
    case CONNECTION_ACTION.GET_CONNECTION_SCHEDULE_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case CONNECTION_ACTION.UPDATE_CONNECTION_SCHEDULE:
      return {
        ...state,
        isUpdating: true
      };
    case CONNECTION_ACTION.UPDATE_CONNECTION_SCHEDULE_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case CONNECTION_ACTION.UPDATE_CONNECTION_SCHEDULE_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case CONNECTION_ACTION.GET_CONNECTION_COMMENTS:
      return {
        ...state,
        loading: true
      };
    case CONNECTION_ACTION.GET_CONNECTION_COMMENTS_SUCCESS:
      return {
        ...state,
        loading: false,
        comments: action.data,
        updatedComments: action.data,
        error: null
      };
    case CONNECTION_ACTION.GET_CONNECTION_COMMENTS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case CONNECTION_ACTION.UPDATE_CONNECTION_COMMENTS:
      return {
        ...state,
        isUpdating: true
      };
    case CONNECTION_ACTION.UPDATE_CONNECTION_COMMENTS_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case CONNECTION_ACTION.UPDATE_CONNECTION_COMMENTS_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case CONNECTION_ACTION.GET_CONNECTION_LABELS:
      return {
        ...state,
        loading: true
      };
    case CONNECTION_ACTION.GET_CONNECTION_LABELS_SUCCESS:
      return {
        ...state,
        loading: false,
        labels: action.data,
        isUpdatedLabels: false,
        error: null
      };
    case CONNECTION_ACTION.GET_CONNECTION_LABELS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case CONNECTION_ACTION.UPDATE_CONNECTION_LABELS:
      return {
        ...state,
        isUpdating: true
      };
    case CONNECTION_ACTION.UPDATE_CONNECTION_LABELS_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case CONNECTION_ACTION.UPDATE_CONNECTION_LABELS_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case CONNECTION_ACTION.GET_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING:
      return {
        ...state,
        loading: true
      };
    case CONNECTION_ACTION.GET_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING_SUCCESS:
      return {
        ...state,
        loading: false,
        defaultDataStreams: action.data,
        error: null
      };
    case CONNECTION_ACTION.GET_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case CONNECTION_ACTION.UPDATE_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING:
      return {
        ...state,
        isUpdating: true
      };
    case CONNECTION_ACTION.UPDATE_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case CONNECTION_ACTION.UPDATE_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case CONNECTION_ACTION.SET_UPDATED_CONNECTION_BASIC:
      return {
        ...state,
        updatedConnectionBasic: action.connectionBasic
      };
    case CONNECTION_ACTION.SET_IS_UPDATED_CONNECTION_BASIC:
      return {
        ...state,
        isUpdatedConnectionBasic: action.isUpdated
      };
    case CONNECTION_ACTION.SET_UPDATED_SCHEDULE:
      return {
        ...state,
        updatedSchedule: action.schedule
      };
    case CONNECTION_ACTION.SET_IS_UPDATED_SCHEDULE:
      return {
        ...state,
        isUpdatedSchedule: action.isUpdated
      };
    case CONNECTION_ACTION.SET_UPDATED_COMMENTS:
      return {
        ...state,
        updatedComments: action.comments
      };
    case CONNECTION_ACTION.SET_IS_UPDATED_COMMENTS:
      return {
        ...state,
        isUpdatedComments: action.isUpdated
      };
    case CONNECTION_ACTION.SET_UPDATED_LABELS:
      return {
        ...state,
        labels: action.labels
      };
    case CONNECTION_ACTION.SET_IS_UPDATED_LABELS:
      return {
        ...state,
        isUpdatedLabels: action.isUpdated
      };
    case CONNECTION_ACTION.SET_UPDATED_DATA_STREAMS:
      return {
        ...state,
        updatedDataStreamsMapping: action.dataStreamsMapping
      };
    case CONNECTION_ACTION.SET_IS_UPDATED_DATA_STREAMS:
      return {
        ...state,
        isUpdatedDataStreamsMapping: action.isUpdated
      };
    default:
      return state;
  }
};

export default connectionReducer;
