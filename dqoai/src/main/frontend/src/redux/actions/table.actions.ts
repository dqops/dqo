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
import { TableBasicModel, UIAllChecksModel } from '../../api';

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
// TODO: getTableChecks -> getTableAdHocChecks. Also getTableCheckpoints and getTablePartitionedChecks for CheckTimePartition
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

export const getTableAdHocChecks =
  (connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableChecksRequest());
    try {
      const res = await TableApiClient.getTableAdHocChecks(
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

export const getTableAdHocChecksUI =
  (connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableChecksUiRequest());
    try {
      const res = await TableApiClient.getTableAdHocChecksUI(
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

export const updateTableAdHocChecksUI =
  (connectionName: string, schemaName: string, tableName: string, data: any) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableChecksUIRequest());
    try {
      await TableApiClient.updateTableAdHocChecksUI(
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

export const getTableDailyCheckpointsRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_DAILY_CHECKPOINTS
});

export const getTableDailyCheckpointsSuccess = (data: any) => ({
  type: TABLE_ACTION.GET_TABLE_DAILY_CHECKPOINTS_SUCCESS,
  data
});

export const getTableDailyCheckpointsFailed = (error: any) => ({
  type: TABLE_ACTION.GET_TABLE_DAILY_CHECKPOINTS_ERROR,
  error
});

export const getTableDailyCheckpoints =
  (connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableDailyCheckpointsRequest());
    try {
      const res = await TableApiClient.getTableCheckpointsUI(
        connectionName,
        schemaName,
        tableName,
        'daily'
      );
      dispatch(getTableDailyCheckpointsSuccess(res.data));
    } catch (err) {
      dispatch(getTableDailyCheckpointsFailed(err));
    }
  };

export const updateTableDailyCheckpointsRequest = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_DAILY_CHECKPOINTS
});

export const updateTableDailyCheckpointsSuccess = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_DAILY_CHECKPOINTS_SUCCESS
});

export const updateTableDailyCheckpointsFailed = (error: any) => ({
  type: TABLE_ACTION.UPDATE_TABLE_DAILY_CHECKPOINTS_ERROR,
  error
});

export const updateTableDailyCheckpoints =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: UIAllChecksModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableDailyCheckpointsRequest());
    try {
      await TableApiClient.updateTableCheckpointsUI(
        connectionName,
        schemaName,
        tableName,
        'daily',
        data
      );
      dispatch(updateTableDailyCheckpointsSuccess());
    } catch (err) {
      dispatch(updateTableDailyCheckpointsFailed(err));
    }
  };

export const getTableMonthlyCheckpointsRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_MONTHLY_CHECKPOINTS
});

export const getTableMonthlyCheckpointsSuccess = (data: any) => ({
  type: TABLE_ACTION.GET_TABLE_MONTHLY_CHECKPOINTS_SUCCESS,
  data
});

export const getTableMonthlyCheckpointsFailed = (error: any) => ({
  type: TABLE_ACTION.GET_TABLE_MONTHLY_CHECKPOINTS_ERROR,
  error
});

export const getTableMonthlyCheckpoints =
  (connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableMonthlyCheckpointsRequest());
    try {
      const res = await TableApiClient.getTableCheckpointsUI(
        connectionName,
        schemaName,
        tableName,
        'monthly'
      );
      dispatch(getTableMonthlyCheckpointsSuccess(res.data));
    } catch (err) {
      dispatch(getTableMonthlyCheckpointsFailed(err));
    }
  };

export const updateTableMonthlyCheckpointsRequest = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_MONTHLY_CHECKPOINTS
});

export const updateTableMonthlyCheckpointsSuccess = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_MONTHLY_CHECKPOINTS_SUCCESS
});

export const updateTableMonthlyCheckpointsFailed = (error: any) => ({
  type: TABLE_ACTION.UPDATE_TABLE_MONTHLY_CHECKPOINTS_ERROR,
  error
});

export const updateTableMonthlyCheckpoints =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: UIAllChecksModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableMonthlyCheckpointsRequest());
    try {
      await TableApiClient.updateTableCheckpointsUI(
        connectionName,
        schemaName,
        tableName,
        'monthly',
        data
      );
      dispatch(updateTableMonthlyCheckpointsSuccess());
    } catch (err) {
      dispatch(updateTableMonthlyCheckpointsFailed(err));
    }
  };

export const getTableDailyPartitionedChecksRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_PARTITIONED_DAILY_CHECKS
});

export const getTableDailyPartitionedChecksSuccess = (data: any) => ({
  type: TABLE_ACTION.GET_TABLE_PARTITIONED_DAILY_CHECKS_SUCCESS,
  data
});

export const getTableDailyPartitionedChecksFailed = (error: any) => ({
  type: TABLE_ACTION.GET_TABLE_PARTITIONED_DAILY_CHECKS_ERROR,
  error
});

export const getTableDailyPartitionedChecks =
  (connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableDailyPartitionedChecksRequest());
    try {
      const res = await TableApiClient.getTablePartitionedChecksUI(
        connectionName,
        schemaName,
        tableName,
        'daily'
      );
      dispatch(getTableDailyPartitionedChecksSuccess(res.data));
    } catch (err) {
      dispatch(getTableDailyPartitionedChecksFailed(err));
    }
  };

export const updateTableDailyPartitionedChecksRequest = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_PARTITIONED_DAILY_CHECKS
});

export const updateTableDailyPartitionedChecksSuccess = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_PARTITIONED_DAILY_CHECKS_SUCCESS
});

export const updateTableDailyPartitionedChecksFailed = (error: any) => ({
  type: TABLE_ACTION.UPDATE_TABLE_PARTITIONED_DAILY_CHECKS_ERROR,
  error
});

export const updateTableDailyPartitionedChecks =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: UIAllChecksModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableDailyPartitionedChecksRequest());
    try {
      await TableApiClient.updateTablePartitionedChecksUI(
        connectionName,
        schemaName,
        tableName,
        'daily',
        data
      );
      dispatch(updateTableDailyPartitionedChecksSuccess());
    } catch (err) {
      dispatch(updateTableDailyPartitionedChecksFailed(err));
    }
  };

export const getTableMonthlyPartitionedChecksRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_PARTITIONED_MONTHLY_CHECKS
});

export const getTableMonthlyPartitionedChecksSuccess = (data: any) => ({
  type: TABLE_ACTION.GET_TABLE_PARTITIONED_MONTHLY_CHECKS_SUCCESS,
  data
});

export const getTableMonthlyPartitionedChecksFailed = (error: any) => ({
  type: TABLE_ACTION.GET_TABLE_PARTITIONED_MONTHLY_CHECKS_ERROR,
  error
});

export const getTableMonthlyPartitionedChecks =
  (connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableMonthlyPartitionedChecksRequest());
    try {
      const res = await TableApiClient.getTablePartitionedChecksUI(
        connectionName,
        schemaName,
        tableName,
        'monthly'
      );
      dispatch(getTableMonthlyPartitionedChecksSuccess(res.data));
    } catch (err) {
      dispatch(getTableMonthlyPartitionedChecksFailed(err));
    }
  };

export const updateTableMonthlyPartitionedChecksRequest = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_PARTITIONED_MONTHLY_CHECKS
});

export const updateTableMonthlyPartitionedChecksSuccess = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_PARTITIONED_MONTHLY_CHECKS_SUCCESS
});

export const updateTableMonthlyPartitionedChecksFailed = (error: any) => ({
  type: TABLE_ACTION.UPDATE_TABLE_PARTITIONED_MONTHLY_CHECKS_ERROR,
  error
});

export const updateTableMonthlyPartitionedChecks =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: UIAllChecksModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableMonthlyPartitionedChecksRequest());
    try {
      await TableApiClient.updateTablePartitionedChecksUI(
        connectionName,
        schemaName,
        tableName,
        'monthly',
        data
      );
      dispatch(updateTableMonthlyPartitionedChecksSuccess());
    } catch (err) {
      dispatch(updateTableMonthlyPartitionedChecksFailed(err));
    }
  };
