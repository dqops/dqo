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
import {
  CommentSpec,
  ConnectionBasicModel,
  DataStreamMappingSpec,
  RecurringScheduleSpec
} from '../../api';
import { CheckRunRecurringScheduleGroup } from "../../shared/enums/scheduling.enum";

export const getConnectionsRequest = () => ({
  type: CONNECTION_ACTION.GET_CONNECTIONS
});

export const getConnectionsSuccess = (data: ConnectionBasicModel[]) => ({
  type: CONNECTION_ACTION.GET_CONNECTIONS_SUCCESS,
  data
});

export const getConnectionsFailed = (error: unknown) => ({
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

export const getConnectionBasicSuccess = (data: ConnectionBasicModel) => ({
  type: CONNECTION_ACTION.GET_CONNECTION_BASIC_SUCCESS,
  data
});

export const getConnectionBasicFailed = (error: unknown) => ({
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

export const updateConnectionBasicFailed = (error: unknown) => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_BASIC_ERROR,
  error
});

export const updateConnectionBasic =
  (connectionName: string, data: ConnectionBasicModel) =>
  async (dispatch: Dispatch) => {
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

export const getConnectionScheduleSuccess = (data: RecurringScheduleSpec) => ({
  type: CONNECTION_ACTION.GET_CONNECTION_SCHEDULE_SUCCESS,
  data
});

export const getConnectionScheduleFailed = (error: unknown) => ({
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

export const updateConnectionScheduleFailed = (error: unknown) => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_SCHEDULE_ERROR,
  error
});

export const updateConnectionSchedule =
  (connectionName: string, data: RecurringScheduleSpec) =>
  async (dispatch: Dispatch) => {
    dispatch(updateConnectionScheduleRequest());
    try {
      await ConnectionApiClient.updateConnectionSchedule(connectionName, data);
      dispatch(updateConnectionScheduleSuccess());
    } catch (err) {
      dispatch(updateConnectionScheduleFailed(err));
    }
  };

export const resetConnectionSchedulingGroup = () => ({
  type: CONNECTION_ACTION.RESET_CONNECTION_SCHEDULE_GROUP
})

export const getConnectionSchedulingGroupRequest = () => ({
  type: CONNECTION_ACTION.GET_CONNECTION_SCHEDULE_GROUP
});

export const getConnectionSchedulingGroupSuccess = (schedulingGroup: CheckRunRecurringScheduleGroup, data: RecurringScheduleSpec) => ({
  type: CONNECTION_ACTION.GET_CONNECTION_SCHEDULE_GROUP_SUCCESS,
  data,
  schedulingGroup
});

export const getConnectionSchedulingGroupFailed = (error: unknown) => ({
  type: CONNECTION_ACTION.GET_CONNECTION_SCHEDULE_GROUP_ERROR,
  error
});

export const getConnectionSchedulingGroup =
  (connectionName: string, schedulingGroup: CheckRunRecurringScheduleGroup) => async (dispatch: Dispatch) => {
    dispatch(getConnectionSchedulingGroupRequest());
    try {
      const res = await ConnectionApiClient.getConnectionSchedulingGroup(
        connectionName,
        schedulingGroup
      );
      dispatch(getConnectionSchedulingGroupSuccess(schedulingGroup, res.data));
    } catch (err) {
      dispatch(getConnectionSchedulingGroupFailed(err));
    }
  };

export const updateConnectionSchedulingGroupRequest = () => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_SCHEDULE_GROUP
});

export const updateConnectionSchedulingGroupSuccess = () => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_SCHEDULE_GROUP_SUCCESS
});

export const updateConnectionSchedulingGroupFailed = (error: unknown) => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_SCHEDULE_GROUP_ERROR,
  error
});

export const updateConnectionSchedulingGroup =
  (connectionName: string, schedulingGroup: CheckRunRecurringScheduleGroup, data: RecurringScheduleSpec) =>
  async (dispatch: Dispatch) => {
    dispatch(updateConnectionSchedulingGroupRequest());
    try {
      await ConnectionApiClient.updateConnectionSchedulingGroup(connectionName, schedulingGroup, data);
      dispatch(updateConnectionSchedulingGroupSuccess());
    } catch (err) {
      dispatch(updateConnectionSchedulingGroupFailed(err));
    }
  };

export const getConnectionCommentsRequest = () => ({
  type: CONNECTION_ACTION.GET_CONNECTION_COMMENTS
});

export const getConnectionCommentsSuccess = (data: CommentSpec[]) => ({
  type: CONNECTION_ACTION.GET_CONNECTION_COMMENTS_SUCCESS,
  data
});

export const getConnectionCommentsFailed = (error: unknown) => ({
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

export const updateConnectionCommentsFailed = (error: unknown) => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_COMMENTS_ERROR,
  error
});

export const updateConnectionComments =
  (connectionName: string, data: CommentSpec[]) =>
  async (dispatch: Dispatch) => {
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

export const getConnectionLabelsSuccess = (data: string[]) => ({
  type: CONNECTION_ACTION.GET_CONNECTION_LABELS_SUCCESS,
  data
});

export const getConnectionLabelsFailed = (error: unknown) => ({
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

export const updateConnectionLabelsFailed = (error: unknown) => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_LABELS_ERROR,
  error
});

export const updateConnectionLabels =
  (connectionName: string, data: string[]) => async (dispatch: Dispatch) => {
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

export const getConnectionDefaultDataStreamsMappingSuccess = (
  data: DataStreamMappingSpec
) => ({
  type: CONNECTION_ACTION.GET_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING_SUCCESS,
  data
});

export const getConnectionDefaultDataStreamsMappingFailed = (
  error: unknown
) => ({
  type: CONNECTION_ACTION.GET_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING_ERROR,
  error
});

export const getConnectionDefaultDataStreamsMapping =
  (connectionName: string) => async (dispatch: Dispatch) => {
    dispatch(getConnectionDefaultDataStreamsMappingRequest());
    try {
      const res =
        await ConnectionApiClient.getConnectionDefaultDataStreamsMapping(
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

export const updateConnectionDefaultDataStreamsMappingFailed = (
  error: unknown
) => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING_ERROR,
  error
});

export const updateConnectionDefaultDataStreamsMapping =
  (connectionName: string, data: DataStreamMappingSpec) =>
  async (dispatch: Dispatch) => {
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

export const setConnectionBasic = (
  connectionBasic?: ConnectionBasicModel
) => ({
  type: CONNECTION_ACTION.SET_UPDATED_CONNECTION_BASIC,
  connectionBasic
});

export const setIsUpdatedConnectionBasic = (isUpdated: boolean) => ({
  type: CONNECTION_ACTION.SET_IS_UPDATED_CONNECTION_BASIC,
  isUpdated
});

export const setUpdatedSchedule = (schedule?: RecurringScheduleSpec) => ({
  type: CONNECTION_ACTION.SET_UPDATED_SCHEDULE,
  schedule
});

export const setIsUpdatedSchedule = (isUpdated: boolean) => ({
  type: CONNECTION_ACTION.SET_IS_UPDATED_SCHEDULE,
  isUpdated
});

export const setUpdatedSchedulingGroup = (schedulingGroup: CheckRunRecurringScheduleGroup, schedule?: RecurringScheduleSpec) => ({
  type: CONNECTION_ACTION.SET_UPDATED_SCHEDULE_GROUP,
  schedule,
  schedulingGroup
});

export const setIsUpdatedSchedulingGroup = (schedulingGroup: CheckRunRecurringScheduleGroup, isUpdated: boolean) => ({
  type: CONNECTION_ACTION.SET_IS_UPDATED_SCHEDULE_GROUP,
  isUpdated,
  schedulingGroup
});

export const setUpdatedComments = (comments?: CommentSpec[]) => ({
  type: CONNECTION_ACTION.SET_UPDATED_COMMENTS,
  comments
});

export const setIsUpdatedComments = (isUpdated: boolean) => ({
  type: CONNECTION_ACTION.SET_IS_UPDATED_COMMENTS,
  isUpdated
});

export const setLabels = (labels?: string[]) => ({
  type: CONNECTION_ACTION.SET_UPDATED_LABELS,
  labels
});

export const setIsUpdatedLabels = (isUpdated: boolean) => ({
  type: CONNECTION_ACTION.SET_IS_UPDATED_LABELS,
  isUpdated
});

export const setUpdatedDataStreamsMapping = (
  dataStreamsMapping?: DataStreamMappingSpec
) => ({
  type: CONNECTION_ACTION.SET_UPDATED_DATA_STREAMS,
  dataStreamsMapping
});

export const setIsUpdatedDataStreamsMapping = (isUpdated: boolean) => ({
  type: CONNECTION_ACTION.SET_IS_UPDATED_DATA_STREAMS,
  isUpdated
});
