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
import { SOURCE_ACTION } from '../types';
import { AxiosResponse } from 'axios';
import {
  CommentSpec,
  ConnectionBasicModel,
  DataStreamMappingSpec,
  RecurringScheduleSpec
} from '../../api';
import { CheckRunRecurringScheduleGroup } from "../../shared/enums/scheduling.enum";
import { CheckTypes } from "../../shared/routes";

export const getConnectionsRequest = () => ({
  type: SOURCE_ACTION.GET_CONNECTIONS
});

export const getConnectionsSuccess = (data: ConnectionBasicModel[]) => ({
  type: SOURCE_ACTION.GET_CONNECTIONS_SUCCESS,
  data
});

export const getConnectionsFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_CONNECTIONS_ERROR,
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

export const getConnectionBasicRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_CONNECTION_BASIC,
  checkType,
  activeTab,
});

export const getConnectionBasicSuccess = (checkType: CheckTypes, activeTab: string, data: ConnectionBasicModel) => ({
  type: SOURCE_ACTION.GET_CONNECTION_BASIC_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getConnectionBasicFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_CONNECTION_BASIC_ERROR,
  error
});

export const getConnectionBasic =
  (checkType: CheckTypes, activeTab: string, connectionName: string) => async (dispatch: Dispatch) => {
    dispatch(getConnectionBasicRequest(checkType, activeTab));
    try {
      const res = await ConnectionApiClient.getConnectionBasic(connectionName);
      dispatch(getConnectionBasicSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getConnectionsFailed(err));
    }
  };

export const updateConnectionBasicRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_CONNECTION_BASIC,
  checkType,
  activeTab,
});

export const updateConnectionBasicSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_CONNECTION_BASIC_SUCCESS,
  checkType,
  activeTab
});

export const updateConnectionBasicFailed = (error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_CONNECTION_BASIC_ERROR,
  error
});

export const updateConnectionBasic =
  (checkTypes: CheckTypes, activeTab: string, connectionName: string, data: ConnectionBasicModel) =>
  async (dispatch: Dispatch) => {
    dispatch(updateConnectionBasicRequest(checkTypes, activeTab));
    try {
      await ConnectionApiClient.updateConnectionBasic(connectionName, data);
      dispatch(updateConnectionBasicSuccess(checkTypes, activeTab));
    } catch (err) {
      dispatch(updateConnectionBasicFailed(err));
    }
  };

export const resetConnectionSchedulingGroup = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.RESET_CONNECTION_SCHEDULE_GROUP,
  checkType,
  activeTab,
})

export const getConnectionSchedulingGroupRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_CONNECTION_SCHEDULE_GROUP,
  checkType,
  activeTab,
});

export const getConnectionSchedulingGroupSuccess = (checkType: CheckTypes, activeTab: string, schedulingGroup: CheckRunRecurringScheduleGroup, data: RecurringScheduleSpec) => ({
  type: SOURCE_ACTION.GET_CONNECTION_SCHEDULE_GROUP_SUCCESS,
  checkType,
  activeTab,
  data,
  schedulingGroup
});

export const getConnectionSchedulingGroupFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_CONNECTION_SCHEDULE_GROUP_ERROR,
  error
});

export const getConnectionSchedulingGroup =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schedulingGroup: CheckRunRecurringScheduleGroup) => async (dispatch: Dispatch) => {
    dispatch(getConnectionSchedulingGroupRequest(checkType, activeTab));
    try {
      const res = await ConnectionApiClient.getConnectionSchedulingGroup(
        connectionName,
        schedulingGroup
      );
      dispatch(getConnectionSchedulingGroupSuccess(checkType, activeTab, schedulingGroup, res.data));
    } catch (err) {
      dispatch(getConnectionSchedulingGroupFailed(err));
    }
  };

export const updateConnectionSchedulingGroupRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_CONNECTION_SCHEDULE_GROUP,
  checkType,
  activeTab,
});

export const updateConnectionSchedulingGroupSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_CONNECTION_SCHEDULE_GROUP_SUCCESS,
  checkType,
  activeTab,
});

export const updateConnectionSchedulingGroupFailed = (error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_CONNECTION_SCHEDULE_GROUP_ERROR,
  error
});

export const updateConnectionSchedulingGroup =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schedulingGroup: CheckRunRecurringScheduleGroup, data: RecurringScheduleSpec) =>
  async (dispatch: Dispatch) => {
    dispatch(updateConnectionSchedulingGroupRequest(checkType, activeTab));
    try {
      await ConnectionApiClient.updateConnectionSchedulingGroup(connectionName, schedulingGroup, data);
      dispatch(updateConnectionSchedulingGroupSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateConnectionSchedulingGroupFailed(err));
    }
  };

export const getConnectionCommentsRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_CONNECTION_COMMENTS,
  checkType,
  activeTab,
});

export const getConnectionCommentsSuccess = (checkType: CheckTypes, activeTab: string, data: CommentSpec[]) => ({
  type: SOURCE_ACTION.GET_CONNECTION_COMMENTS_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getConnectionCommentsFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_CONNECTION_COMMENTS_ERROR,
  error
});

export const getConnectionComments =
  (checkType: CheckTypes, activeTab: string, connectionName: string, loading = true) => async (dispatch: Dispatch) => {
    if(loading) {
      dispatch(getConnectionCommentsRequest(checkType, activeTab));
    }
    try {
      const res = await ConnectionApiClient.getConnectionComments(
        connectionName
      );
      dispatch(getConnectionCommentsSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getConnectionCommentsFailed(err));
    }
  };

export const updateConnectionCommentsRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_CONNECTION_COMMENTS,
  checkType,
  activeTab
});

export const updateConnectionCommentsSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_CONNECTION_COMMENTS_SUCCESS,
  checkType,
  activeTab,
});

export const updateConnectionCommentsFailed = (error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_CONNECTION_COMMENTS_ERROR,
  error
});

export const updateConnectionComments =
  (checkType: CheckTypes, activeTab: string, connectionName: string, data: CommentSpec[]) =>
  async (dispatch: Dispatch) => {
    dispatch(updateConnectionCommentsRequest(checkType, activeTab));
    try {
      await ConnectionApiClient.updateConnectionComments(connectionName, data);
      dispatch(updateConnectionCommentsSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateConnectionCommentsFailed(err));
    }
  };

export const getConnectionLabelsRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_CONNECTION_LABELS,
  checkType,
  activeTab,
});

export const getConnectionLabelsSuccess = (checkType: CheckTypes, activeTab: string, data: string[]) => ({
  type: SOURCE_ACTION.GET_CONNECTION_LABELS_SUCCESS,
  data,
  checkType,
  activeTab,
});

export const getConnectionLabelsFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_CONNECTION_LABELS_ERROR,
  error
});

export const getConnectionLabels =
  (checkType: CheckTypes, activeTab: string, connectionName: string, loading = true) => async (dispatch: Dispatch) => {
    if (loading) {
      dispatch(getConnectionLabelsRequest(checkType, activeTab));
    }
    try {
      const res = await ConnectionApiClient.getConnectionLabels(connectionName);
      dispatch(getConnectionLabelsSuccess(checkType, activeTab, res.data || []));
    } catch (err) {
      dispatch(getConnectionLabelsFailed(err));
    }
  };

export const updateConnectionLabelsRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_CONNECTION_LABELS,
  checkType,
  activeTab
});

export const updateConnectionLabelsSuccess = (checkType: CheckTypes, activeTab: string, ) => ({
  type: SOURCE_ACTION.UPDATE_CONNECTION_LABELS_SUCCESS,
  checkType,
  activeTab
});

export const updateConnectionLabelsFailed = (error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_CONNECTION_LABELS_ERROR,
  error,
});

export const updateConnectionLabels =
  (checkType: CheckTypes, activeTab: string, connectionName: string, data: string[]) => async (dispatch: Dispatch) => {
    dispatch(updateConnectionLabelsRequest(checkType, activeTab));
    try {
      await ConnectionApiClient.updateConnectionLabels(connectionName, data);
      dispatch(updateConnectionLabelsSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateConnectionLabelsFailed(err));
    }
  };

export const getConnectionDefaultDataStreamsMappingRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING,
  checkType,
  activeTab
});

export const getConnectionDefaultDataStreamsMappingSuccess = (
  checkType: CheckTypes,
  activeTab: string,
  data: DataStreamMappingSpec
) => ({
  type: SOURCE_ACTION.GET_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING_SUCCESS,
  data,
  checkType,
  activeTab
});

export const getConnectionDefaultDataStreamsMappingFailed = (
  error: unknown
) => ({
  type: SOURCE_ACTION.GET_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING_ERROR,
  error
});

export const getConnectionDefaultDataStreamsMapping =
  (checkType: CheckTypes, activeTab: string, connectionName: string, loading = true) => async (dispatch: Dispatch) => {
    if (loading) {
      dispatch(getConnectionDefaultDataStreamsMappingRequest(checkType, activeTab));
    }
    try {
      const res =
        await ConnectionApiClient.getConnectionDefaultDataStreamsMapping(
          connectionName
        );
      dispatch(getConnectionDefaultDataStreamsMappingSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getConnectionDefaultDataStreamsMappingFailed(err));
    }
  };

export const updateConnectionDefaultDataStreamsMappingRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING,
  checkType,
  activeTab
});

export const updateConnectionDefaultDataStreamsMappingSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING_SUCCESS,
  checkType,
  activeTab
});

export const updateConnectionDefaultDataStreamsMappingFailed = (
  error: unknown
) => ({
  type: SOURCE_ACTION.UPDATE_CONNECTION_DEFAULT_DATA_STREAMS_MAPPING_ERROR,
  error
});

export const updateConnectionDefaultDataStreamsMapping =
  (checkType: CheckTypes, activeTab: string, connectionName: string, data: DataStreamMappingSpec) =>
  async (dispatch: Dispatch) => {
    dispatch(updateConnectionDefaultDataStreamsMappingRequest(checkType, activeTab));
    try {
      await ConnectionApiClient.updateConnectionDefaultDataStreamsMapping(
        connectionName,
        data
      );
      dispatch(updateConnectionDefaultDataStreamsMappingSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateConnectionDefaultDataStreamsMappingFailed(err));
    }
  };

export const setConnectionBasic = (
  checkType: CheckTypes,
  activeTab: string,
  connectionBasic?: ConnectionBasicModel
) => ({
  type: SOURCE_ACTION.SET_UPDATED_CONNECTION_BASIC,
  checkType,
  activeTab,
  data: connectionBasic
});

export const setIsUpdatedConnectionBasic = (checkType: CheckTypes, activeTab: string, isUpdated: boolean) => ({
  type: SOURCE_ACTION.SET_IS_UPDATED_CONNECTION_BASIC,
  checkType,
  activeTab,
  data: isUpdated
});

export const setUpdatedSchedulingGroup = (checkType: CheckTypes, activeTab: string, schedulingGroup: CheckRunRecurringScheduleGroup, schedule?: RecurringScheduleSpec) => ({
  type: SOURCE_ACTION.SET_UPDATED_SCHEDULE_GROUP,
  checkType,
  activeTab,
  data: {
    schedule,
    schedulingGroup,
  }
});

export const setIsUpdatedSchedulingGroup = (checkType: CheckTypes, activeTab: string, schedulingGroup: CheckRunRecurringScheduleGroup, isUpdated: boolean) => ({
  type: SOURCE_ACTION.SET_IS_UPDATED_SCHEDULE_GROUP,
  checkType,
  activeTab,
  data: {
    isUpdated,
    schedulingGroup,
  }
});

export const setUpdatedComments = (checkType: CheckTypes, activeTab: string, comments?: CommentSpec[]) => ({
  type: SOURCE_ACTION.SET_UPDATED_COMMENTS,
  checkType,
  activeTab,
  data: comments,
});

export const setIsUpdatedComments = (checkType: CheckTypes, activeTab: string, isUpdated: boolean) => ({
  type: SOURCE_ACTION.SET_IS_UPDATED_COMMENTS,
  data: isUpdated,
  checkType,
  activeTab
});

export const setLabels = (checkType: CheckTypes, activeTab: string, labels?: string[]) => ({
  type: SOURCE_ACTION.SET_UPDATED_LABELS,
  data: labels,
  checkType,
  activeTab
});

export const setIsUpdatedLabels = (checkType: CheckTypes, activeTab: string, isUpdated: boolean) => ({
  type: SOURCE_ACTION.SET_IS_UPDATED_LABELS,
  data: isUpdated,
  checkType,
  activeTab
});

export const setUpdatedDataStreamsMapping = (
  checkType: CheckTypes,
  activeTab: string,
  dataStreamsMapping?: DataStreamMappingSpec
) => ({
  type: SOURCE_ACTION.SET_UPDATED_DATA_STREAMS,
  data: dataStreamsMapping,
  checkType,
  activeTab
});

export const setIsUpdatedDataStreamsMapping = (checkType: CheckTypes, activeTab: string, isUpdated: boolean) => ({
  type: SOURCE_ACTION.SET_IS_UPDATED_DATA_STREAMS,
  data: isUpdated,
  checkType,
  activeTab
});
