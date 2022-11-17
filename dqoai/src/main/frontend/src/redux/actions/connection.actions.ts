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

import { Dispatch } from 'redux';

import { ConnectionApiClient } from '../../services/apiClient';
import { CONNECTION_ACTION } from '../types';
import { AxiosResponse } from 'axios';
import { ConnectionBasicModel } from '../../api';

export const getConnectionsRequest = () => ({
  type: CONNECTION_ACTION.GET_CONNECTIONS
});

export const getConnectionsSuccess = (data: any) => ({
  type: CONNECTION_ACTION.GET_CONNECTIONS_SUCCESS,
  data
});

export const getConnectionsFailed = (error: any) => ({
  type: CONNECTION_ACTION.GET_CONNECTIONS_ERROR,
  error
});

export const getAllConnections = () => async (dispatch: Dispatch) => {
  dispatch(getConnectionsRequest());
  try {
    const res: AxiosResponse<ConnectionBasicModel[]> =
      await ConnectionApiClient.getAllConnections();
    dispatch(getConnectionsSuccess(res.data));
  } catch (err) {
    dispatch(getConnectionsFailed(err));
  }
};

export const setActiveConnection = (activeConnection: string) => ({
  type: CONNECTION_ACTION.SET_ACTIVE_CONNECTION,
  activeConnection
});

export const getConnectionBasicRequest = () => ({
  type: CONNECTION_ACTION.GET_CONNECTION_BASIC
});

export const getConnectionBasicSuccess = (data: any) => ({
  type: CONNECTION_ACTION.GET_CONNECTION_BASIC_SUCCESS,
  data
});

export const getConnectionBasicFailed = (error: any) => ({
  type: CONNECTION_ACTION.GET_CONNECTION_BASIC_ERROR,
  error
});

export const getConnectionBasic =
  (connectionName: string) => async (dispatch: Dispatch) => {
    dispatch(getConnectionBasicRequest());
    try {
      const res = await ConnectionApiClient.getConnectionBasic(connectionName);
      dispatch(getConnectionBasicSuccess(res.data));
    } catch (err) {
      dispatch(getConnectionsFailed(err));
    }
  };

export const updateConnectionBasicRequest = () => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_BASIC
});

export const updateConnectionBasicSuccess = () => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_BASIC_SUCCESS
});

export const updateConnectionBasicFailed = (error: any) => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_BASIC_ERROR,
  error
});

export const updateConnectionBasic =
  (connectionName: string, data: any) => async (dispatch: Dispatch) => {
    dispatch(updateConnectionBasicRequest());
    try {
      await ConnectionApiClient.updateConnectionBasic(connectionName, data);
      dispatch(updateConnectionBasicSuccess());
    } catch (err) {
      dispatch(updateConnectionBasicFailed(err));
    }
  };

export const getConnectionScheduleRequest = () => ({
  type: CONNECTION_ACTION.GET_CONNECTION_SCHEDULE
});

export const getConnectionScheduleSuccess = (data: any) => ({
  type: CONNECTION_ACTION.GET_CONNECTION_SCHEDULE_SUCCESS,
  data
});

export const getConnectionScheduleFailed = (error: any) => ({
  type: CONNECTION_ACTION.GET_CONNECTION_SCHEDULE_ERROR,
  error
});

export const getConnectionSchedule =
  (connectionName: string) => async (dispatch: Dispatch) => {
    dispatch(getConnectionScheduleRequest());
    try {
      const res = await ConnectionApiClient.getConnectionSchedule(
        connectionName
      );
      dispatch(getConnectionScheduleSuccess(res.data));
    } catch (err) {
      dispatch(getConnectionScheduleFailed(err));
    }
  };

export const updateConnectionScheduleRequest = () => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_SCHEDULE
});

export const updateConnectionScheduleSuccess = () => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_SCHEDULE_SUCCESS
});

export const updateConnectionScheduleFailed = (error: any) => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_SCHEDULE_ERROR,
  error
});

export const updateConnectionSchedule =
  (connectionName: string, data: any) => async (dispatch: Dispatch) => {
    dispatch(updateConnectionScheduleRequest());
    try {
      await ConnectionApiClient.updateConnectionSchedule(connectionName, data);
      dispatch(updateConnectionScheduleSuccess());
    } catch (err) {
      dispatch(updateConnectionScheduleFailed(err));
    }
  };

export const getConnectionTimeRequest = () => ({
  type: CONNECTION_ACTION.GET_CONNECTION_TIME
});

export const getConnectionTimeSuccess = (data: any) => ({
  type: CONNECTION_ACTION.GET_CONNECTION_TIME_SUCCESS,
  data
});

export const getConnectionTimeFailed = (error: any) => ({
  type: CONNECTION_ACTION.GET_CONNECTION_TIME_ERROR,
  error
});

export const getConnectionTime =
  (connectionName: string) => async (dispatch: Dispatch) => {
    dispatch(getConnectionTimeRequest());
    try {
      const res = await ConnectionApiClient.getConnectionDefaultTimeSeries(
        connectionName
      );
      dispatch(getConnectionTimeSuccess(res.data));
    } catch (err) {
      dispatch(getConnectionTimeFailed(err));
    }
  };

export const updateConnectionTimeRequest = () => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_TIME
});

export const updateConnectionTimeSuccess = () => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_TIME_SUCCESS
});

export const updateConnectionTimeFailed = (error: any) => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_TIME_ERROR,
  error
});

export const updateConnectionTime =
  (connectionName: string, data: any) => async (dispatch: Dispatch) => {
    dispatch(updateConnectionTimeRequest());
    try {
      await ConnectionApiClient.updateConnectionDefaultTimeSeries(
        connectionName,
        data
      );
      dispatch(updateConnectionTimeSuccess());
    } catch (err) {
      dispatch(updateConnectionTimeFailed(err));
    }
  };

export const getConnectionCommentsRequest = () => ({
  type: CONNECTION_ACTION.GET_CONNECTION_COMMENTS
});

export const getConnectionCommentsSuccess = (data: any) => ({
  type: CONNECTION_ACTION.GET_CONNECTION_COMMENTS_SUCCESS,
  data
});

export const getConnectionCommentsFailed = (error: any) => ({
  type: CONNECTION_ACTION.GET_CONNECTION_COMMENTS_ERROR,
  error
});

export const getConnectionComments =
  (connectionName: string) => async (dispatch: Dispatch) => {
    dispatch(getConnectionCommentsRequest());
    try {
      const res = await ConnectionApiClient.getConnectionComments(
        connectionName
      );
      dispatch(getConnectionCommentsSuccess(res.data));
    } catch (err) {
      dispatch(getConnectionCommentsFailed(err));
    }
  };

export const updateConnectionCommentsRequest = () => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_COMMENTS
});

export const updateConnectionCommentsSuccess = () => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_COMMENTS_SUCCESS
});

export const updateConnectionCommentsFailed = (error: any) => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_COMMENTS_ERROR,
  error
});

export const updateConnectionComments =
  (connectionName: string, data: any) => async (dispatch: Dispatch) => {
    dispatch(updateConnectionCommentsRequest());
    try {
      await ConnectionApiClient.updateConnectionComments(connectionName, data);
      dispatch(updateConnectionCommentsSuccess());
    } catch (err) {
      dispatch(updateConnectionCommentsFailed(err));
    }
  };

export const getConnectionLabelsRequest = () => ({
  type: CONNECTION_ACTION.GET_CONNECTION_LABELS
});

export const getConnectionLabelsSuccess = (data: any) => ({
  type: CONNECTION_ACTION.GET_CONNECTION_LABELS_SUCCESS,
  data
});

export const getConnectionLabelsFailed = (error: any) => ({
  type: CONNECTION_ACTION.GET_CONNECTION_LABELS_ERROR,
  error
});

export const getConnectionLabels =
  (connectionName: string) => async (dispatch: Dispatch) => {
    dispatch(getConnectionLabelsRequest());
    try {
      const res = await ConnectionApiClient.getConnectionLabels(connectionName);
      dispatch(getConnectionLabelsSuccess(res.data));
    } catch (err) {
      dispatch(getConnectionLabelsFailed(err));
    }
  };

export const updateConnectionLabelsRequest = () => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_LABELS
});

export const updateConnectionLabelsSuccess = () => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_LABELS_SUCCESS
});

export const updateConnectionLabelsFailed = (error: any) => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_LABELS_ERROR,
  error
});

export const updateConnectionLabels =
  (connectionName: string, data: any) => async (dispatch: Dispatch) => {
    dispatch(updateConnectionLabelsRequest());
    try {
      await ConnectionApiClient.updateConnectionLabels(connectionName, data);
      dispatch(updateConnectionLabelsSuccess());
    } catch (err) {
      dispatch(updateConnectionLabelsFailed(err));
    }
  };

export const getConnectionDefaultDataStreamsMappingRequest = () => ({
  type: CONNECTION_ACTION.GET_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING
});

export const getConnectionDefaultDataStreamsMappingSuccess = (data: any) => ({
  type: CONNECTION_ACTION.GET_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING_SUCCESS,
  data
});

export const getConnectionDefaultDataStreamsMappingFailed = (error: any) => ({
  type: CONNECTION_ACTION.GET_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING_ERROR,
  error
});

export const getConnectionDefaultDataStreamsMapping =
  (connectionName: string) => async (dispatch: Dispatch) => {
    dispatch(getConnectionDefaultDataStreamsMappingRequest());
    try {
      const res = await ConnectionApiClient.getConnectionDefaultDataStreamsMapping(
        connectionName
      );
      dispatch(getConnectionDefaultDataStreamsMappingSuccess(res.data));
    } catch (err) {
      dispatch(getConnectionDefaultDataStreamsMappingFailed(err));
    }
  };

export const updateConnectionDefaultDataStreamsMappingRequest = () => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING
});

export const updateConnectionDefaultDataStreamsMappingSuccess = () => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING_SUCCESS
});

export const updateConnectionDefaultDataStreamsMappingFailed = (error: any) => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING_ERROR,
  error
});

export const updateConnectionDefaultDataStreamsMapping =
  (connectionName: string, data: any) => async (dispatch: Dispatch) => {
    dispatch(updateConnectionDefaultDataStreamsMappingRequest());
    try {
      await ConnectionApiClient.updateConnectionDefaultDataStreamsMapping(
        connectionName,
        data
      );
      dispatch(updateConnectionDefaultDataStreamsMappingSuccess());
    } catch (err) {
      dispatch(updateConnectionDefaultDataStreamsMappingFailed(err));
    }
  };
