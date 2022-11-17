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

import { TableApiClient } from '../../services/apiClient';
import { TABLE_ACTION } from '../types';
import { AxiosResponse } from 'axios';
import { TableBasicModel } from '../../api';

export const getTablesRequest = () => ({
  type: TABLE_ACTION.GET_TABLES
});

export const getTablesSuccess = (data: any) => ({
  type: TABLE_ACTION.GET_TABLES_SUCCESS,
  data
});

export const getTablesFailed = (error: any) => ({
  type: TABLE_ACTION.GET_TABLES_ERROR,
  error
});

export const getAllTables =
  (connectionName: string, schemaName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTablesRequest());
    try {
      const res: AxiosResponse<TableBasicModel[]> =
        await TableApiClient.getTables(connectionName, schemaName);
      dispatch(getTablesSuccess(res.data));
    } catch (err) {
      dispatch(getTablesFailed(err));
    }
  };

export const getTableBasicRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_BASIC
});

export const getTableBasicSuccess = (data: any) => ({
  type: TABLE_ACTION.GET_TABLE_BASIC_SUCCESS,
  data
});

export const getTableBasicFailed = (error: any) => ({
  type: TABLE_ACTION.GET_TABLE_BASIC_ERROR,
  error
});

export const getTableBasic =
  (connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableBasicRequest());
    try {
      const res = await TableApiClient.getTableBasic(
        connectionName,
        schemaName,
        tableName
      );
      dispatch(getTableBasicSuccess(res.data));
    } catch (err) {
      dispatch(getTablesFailed(err));
    }
  };

export const updateTableBasicRequest = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_BASIC
});

export const updateTableBasicSuccess = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_BASIC_SUCCESS
});

export const updateTableBasicFailed = (error: any) => ({
  type: TABLE_ACTION.UPDATE_TABLE_BASIC_ERROR,
  error
});

export const updateTableBasic =
  (connectionName: string, schemaName: string, tableName: string, data: any) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableBasicRequest());
    try {
      await TableApiClient.updateTableBasic(
        connectionName,
        schemaName,
        tableName,
        data
      );
      dispatch(updateTableBasicSuccess());
    } catch (err) {
      dispatch(updateTableBasicFailed(err));
    }
  };

export const getTableScheduleRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_SCHEDULE
});

export const getTableScheduleSuccess = (data: any) => ({
  type: TABLE_ACTION.GET_TABLE_SCHEDULE_SUCCESS,
  data
});

export const getTableScheduleFailed = (error: any) => ({
  type: TABLE_ACTION.GET_TABLE_SCHEDULE_ERROR,
  error
});

export const getTableSchedule =
  (connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableScheduleRequest());
    try {
      const res = await TableApiClient.getTableScheduleOverride(
        connectionName,
        schemaName,
        tableName
      );
      dispatch(getTableScheduleSuccess(res.data));
    } catch (err) {
      dispatch(getTableScheduleFailed(err));
    }
  };

export const updateTableScheduleRequest = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_SCHEDULE
});

export const updateTableScheduleSuccess = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_SCHEDULE_SUCCESS
});

export const updateTableScheduleFailed = (error: any) => ({
  type: TABLE_ACTION.UPDATE_TABLE_SCHEDULE_ERROR,
  error
});

export const updateTableSchedule =
  (connectionName: string, schemaName: string, tableName: string, data: any) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableScheduleRequest());
    try {
      await TableApiClient.updateTableScheduleOverride(
        connectionName,
        schemaName,
        tableName,
        data
      );
      dispatch(updateTableScheduleSuccess());
    } catch (err) {
      dispatch(updateTableScheduleFailed(err));
    }
  };

export const getTableTimeRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_TIME
});

export const getTableTimeSuccess = (data: any) => ({
  type: TABLE_ACTION.GET_TABLE_TIME_SUCCESS,
  data
});

export const getTableTimeFailed = (error: any) => ({
  type: TABLE_ACTION.GET_TABLE_TIME_ERROR,
  error
});

export const getTableTime =
  (connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableTimeRequest());
    try {
      const res = await TableApiClient.getTableTimeSeries(
        connectionName,
        schemaName,
        tableName
      );
      dispatch(getTableTimeSuccess(res.data));
    } catch (err) {
      dispatch(getTableTimeFailed(err));
    }
  };

export const updateTableTimeRequest = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_TIME
});

export const updateTableTimeSuccess = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_TIME_SUCCESS
});

export const updateTableTimeFailed = (error: any) => ({
  type: TABLE_ACTION.UPDATE_TABLE_TIME_ERROR,
  error
});

export const updateTableTime =
  (connectionName: string, schemaName: string, tableName: string, data: any) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableTimeRequest());
    try {
      await TableApiClient.updateTableTimeSeries(
        connectionName,
        schemaName,
        tableName,
        data
      );
      dispatch(updateTableTimeSuccess());
    } catch (err) {
      dispatch(updateTableTimeFailed(err));
    }
  };

export const getTableCommentsRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_COMMENTS
});

export const getTableCommentsSuccess = (data: any) => ({
  type: TABLE_ACTION.GET_TABLE_COMMENTS_SUCCESS,
  data
});

export const getTableCommentsFailed = (error: any) => ({
  type: TABLE_ACTION.GET_TABLE_COMMENTS_ERROR,
  error
});

export const getTableComments =
  (connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableCommentsRequest());
    try {
      const res = await TableApiClient.getTableComments(
        connectionName,
        schemaName,
        tableName
      );
      dispatch(getTableCommentsSuccess(res.data));
    } catch (err) {
      dispatch(getTableCommentsFailed(err));
    }
  };

export const updateTableCommentsRequest = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_COMMENTS
});

export const updateTableCommentsSuccess = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_COMMENTS_SUCCESS
});

export const updateTableCommentsFailed = (error: any) => ({
  type: TABLE_ACTION.UPDATE_TABLE_COMMENTS_ERROR,
  error
});

export const updateTableComments =
  (connectionName: string, schemaName: string, tableName: string, data: any) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableCommentsRequest());
    try {
      await TableApiClient.updateTableComments(
        connectionName,
        schemaName,
        tableName,
        data
      );
      dispatch(updateTableCommentsSuccess());
    } catch (err) {
      dispatch(updateTableCommentsFailed(err));
    }
  };

export const getTableLabelsRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_LABELS
});

export const getTableLabelsSuccess = (data: any) => ({
  type: TABLE_ACTION.GET_TABLE_LABELS_SUCCESS,
  data
});

export const getTableLabelsFailed = (error: any) => ({
  type: TABLE_ACTION.GET_TABLE_LABELS_ERROR,
  error
});

export const getTableLabels =
  (connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableLabelsRequest());
    try {
      const res = await TableApiClient.getTableLabels(
        connectionName,
        schemaName,
        tableName
      );
      dispatch(getTableLabelsSuccess(res.data));
    } catch (err) {
      dispatch(getTableLabelsFailed(err));
    }
  };

export const updateTableLabelsRequest = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_LABELS
});

export const updateTableLabelsSuccess = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_LABELS_SUCCESS
});

export const updateTableLabelsFailed = (error: any) => ({
  type: TABLE_ACTION.UPDATE_TABLE_LABELS_ERROR,
  error
});

export const updateTableLabels =
  (connectionName: string, schemaName: string, tableName: string, data: any) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableLabelsRequest());
    try {
      await TableApiClient.updateTableLabels(
        connectionName,
        schemaName,
        tableName,
        data
      );
      dispatch(updateTableLabelsSuccess());
    } catch (err) {
      dispatch(updateTableLabelsFailed(err));
    }
  };

export const getTableChecksRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS
});

export const getTableChecksSuccess = (data: any) => ({
  type: TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_SUCCESS,
  data
});

export const getTableChecksFailed = (error: any) => ({
  type: TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_ERROR,
  error
});

export const getTableChecks =
  (connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableChecksRequest());
    try {
      const res = await TableApiClient.getTableChecks(
        connectionName,
        schemaName,
        tableName
      );
      dispatch(getTableChecksSuccess(res.data));
    } catch (err) {
      dispatch(getTableChecksFailed(err));
    }
  };

export const getTableChecksUiRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_UI
});

export const getTableChecksUiSuccess = (data: any) => ({
  type: TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_UI_SUCCESS,
  data
});

export const getTableChecksUiFailed = (error: any) => ({
  type: TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_UI_ERROR,
  error
});

export const getTableChecksUI =
  (connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableChecksUiRequest());
    try {
      const res = await TableApiClient.getTableChecksUI(
        connectionName,
        schemaName,
        tableName
      );
      dispatch(getTableChecksUiSuccess(res.data));
    } catch (err) {
      dispatch(getTableChecksUiFailed(err));
    }
  };

export const updateTableChecksUIRequest = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_DATA_QUALITY_CHECKS_UI
});

export const updateTableChecksUISuccess = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_DATA_QUALITY_CHECKS_UI_SUCCESS
});

export const updateTableChecksUIFailed = (error: any) => ({
  type: TABLE_ACTION.UPDATE_TABLE_DATA_QUALITY_CHECKS_UI_ERROR,
  error
});

export const updateTableChecksUI =
  (connectionName: string, schemaName: string, tableName: string, data: any) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableChecksUIRequest());
    try {
      await TableApiClient.updateTableChecksUI(
        connectionName,
        schemaName,
        tableName,
        data
      );
      dispatch(updateTableChecksUISuccess());
    } catch (err) {
      dispatch(updateTableChecksUIFailed(err));
    }
  };

export const getTableDataStreamsMappingRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_DATA_STREAMS_MAPPING
});

export const getTableDataStreamsMappingSuccess = (data: any) => ({
  type: TABLE_ACTION.GET_TABLE_DATA_STREAMS_MAPPING_SUCCESS,
  data
});

export const getTableDataStreamsMappingFailed = (error: any) => ({
  type: TABLE_ACTION.GET_TABLE_DATA_STREAMS_MAPPING_ERROR,
  error
});

export const getTableDataStreamMapping =
  (connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableDataStreamsMappingRequest());
    try {
      const res = await TableApiClient.getTableDataStreamsMapping(
        connectionName,
        schemaName,
        tableName
      );
      dispatch(getTableDataStreamsMappingSuccess(res.data));
    } catch (err) {
      dispatch(getTableDataStreamsMappingFailed(err));
    }
  };

export const updateTableDataStreamsMappingRequest = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_DATA_STREAMS_MAPPING
});

export const updateTableDataStreamsSuccess = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_DATA_STREAMS_MAPPING_SUCCESS
});

export const updateTableDataStreamsMappingFailed = (error: any) => ({
  type: TABLE_ACTION.UPDATE_TABLE_DATA_STREAMS_MAPPING_ERROR,
  error
});

export const updateTableDataStreamMapping =
  (connectionName: string, schemaName: string, tableName: string, data: any) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableDataStreamsMappingRequest());
    try {
      await TableApiClient.updateTableDataStreamsMapping(
        connectionName,
        schemaName,
        tableName,
        data
      );
      dispatch(updateTableDataStreamsSuccess());
    } catch (err) {
      dispatch(updateTableDataStreamsMappingFailed(err));
    }
  };
