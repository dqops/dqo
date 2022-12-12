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

import { ColumnApiClient } from '../../services/apiClient';
import { COLUMN_ACTION } from '../types';
import { AxiosResponse } from 'axios';
import { ColumnBasicModel, CommentSpec, UIAllChecksModel } from '../../api';

export const getColumnsRequest = () => ({
  type: COLUMN_ACTION.GET_COLUMNS
});

export const getColumnsSuccess = (data: ColumnBasicModel[]) => ({
  type: COLUMN_ACTION.GET_COLUMNS_SUCCESS,
  data
});

export const getColumnsFailed = (error: unknown) => ({
  type: COLUMN_ACTION.GET_COLUMNS_ERROR,
  error
});

export const getAllColumns =
  (connectionName: string, schemaName: string, columnName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getColumnsRequest());
    try {
      const res: AxiosResponse<ColumnBasicModel[]> =
        await ColumnApiClient.getColumns(
          connectionName,
          schemaName,
          columnName
        );
      dispatch(getColumnsSuccess(res.data));
    } catch (err) {
      dispatch(getColumnsFailed(err));
    }
  };

export const getColumnBasicRequest = () => ({
  type: COLUMN_ACTION.GET_COLUMN_BASIC
});

export const getColumnBasicSuccess = (data: ColumnBasicModel) => ({
  type: COLUMN_ACTION.GET_COLUMN_BASIC_SUCCESS,
  data
});

export const getColumnBasicFailed = (error: unknown) => ({
  type: COLUMN_ACTION.GET_COLUMN_BASIC_ERROR,
  error
});

export const getColumnBasic =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(getColumnBasicRequest());
    try {
      const res = await ColumnApiClient.getColumnBasic(
        connectionName,
        schemaName,
        tableName,
        columnName
      );
      dispatch(getColumnBasicSuccess(res.data));
    } catch (err) {
      dispatch(getColumnsFailed(err));
    }
  };

export const updateColumnBasicRequest = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_BASIC
});

export const updateColumnBasicSuccess = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_BASIC_SUCCESS
});

export const updateColumnBasicFailed = (error: unknown) => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_BASIC_ERROR,
  error
});

export const updateColumnBasic =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: ColumnBasicModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnBasicRequest());
    try {
      await ColumnApiClient.updateColumnBasic(
        connectionName,
        schemaName,
        tableName,
        columnName,
        data
      );
      dispatch(updateColumnBasicSuccess());
    } catch (err) {
      dispatch(updateColumnBasicFailed(err));
    }
  };

export const getColumnCommentsRequest = () => ({
  type: COLUMN_ACTION.GET_COLUMN_COMMENTS
});

export const getColumnCommentsSuccess = (data: CommentSpec[]) => ({
  type: COLUMN_ACTION.GET_COLUMN_COMMENTS_SUCCESS,
  data
});

export const getColumnCommentsFailed = (error: unknown) => ({
  type: COLUMN_ACTION.GET_COLUMN_COMMENTS_ERROR,
  error
});

export const getColumnComments =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(getColumnCommentsRequest());
    try {
      const res = await ColumnApiClient.getColumnComments(
        connectionName,
        schemaName,
        tableName,
        columnName
      );
      dispatch(getColumnCommentsSuccess(res.data));
    } catch (err) {
      dispatch(getColumnCommentsFailed(err));
    }
  };

export const updateColumnCommentsRequest = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_COMMENTS
});

export const updateColumnCommentsSuccess = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_COMMENTS_SUCCESS
});

export const updateColumnCommentsFailed = (error: unknown) => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_COMMENTS_ERROR,
  error
});

export const updateColumnComments =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: CommentSpec[]
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnCommentsRequest());
    try {
      await ColumnApiClient.updateColumnComments(
        connectionName,
        schemaName,
        tableName,
        columnName,
        data
      );
      dispatch(updateColumnCommentsSuccess());
    } catch (err) {
      dispatch(updateColumnCommentsFailed(err));
    }
  };

export const getColumnLabelsRequest = () => ({
  type: COLUMN_ACTION.GET_COLUMN_LABELS
});

export const getColumnLabelsSuccess = (data: string[]) => ({
  type: COLUMN_ACTION.GET_COLUMN_LABELS_SUCCESS,
  data
});

export const getColumnLabelsFailed = (error: unknown) => ({
  type: COLUMN_ACTION.GET_COLUMN_LABELS_ERROR,
  error
});

export const getColumnLabels =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(getColumnLabelsRequest());
    try {
      const res = await ColumnApiClient.getColumnLabels(
        connectionName,
        schemaName,
        tableName,
        columnName
      );
      dispatch(getColumnLabelsSuccess(res.data));
    } catch (err) {
      dispatch(getColumnLabelsFailed(err));
    }
  };

export const updateColumnLabelsRequest = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_LABELS
});

export const updateColumnLabelsSuccess = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_LABELS_SUCCESS
});

export const updateColumnLabelsFailed = (error: unknown) => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_LABELS_ERROR,
  error
});

export const updateColumnLabels =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: string[]
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnLabelsRequest());
    try {
      await ColumnApiClient.updateColumnLabels(
        connectionName,
        schemaName,
        tableName,
        columnName,
        data
      );
      dispatch(updateColumnLabelsSuccess());
    } catch (err) {
      dispatch(updateColumnLabelsFailed(err));
    }
  };

export const getColumnChecksUIRequest = () => ({
  type: COLUMN_ACTION.GET_COLUMN_CHECKS_UI
});

export const getColumnChecksUISuccess = (data: UIAllChecksModel) => ({
  type: COLUMN_ACTION.GET_COLUMN_CHECKS_UI_SUCCESS,
  data
});

export const getColumnChecksUIFailed = (error: unknown) => ({
  type: COLUMN_ACTION.GET_COLUMN_CHECKS_UI_ERROR,
  error
});

export const getColumnChecksUi =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(getColumnChecksUIRequest());
    try {
      const res = await ColumnApiClient.getColumnAdHocChecksUI(
        connectionName,
        schemaName,
        tableName,
        columnName
      );
      dispatch(getColumnChecksUISuccess(res.data));
    } catch (err) {
      dispatch(getColumnChecksUIFailed(err));
    }
  };

export const updateColumnCheckUIRequest = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_LABELS
});

export const updateColumnCheckUISuccess = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_LABELS_SUCCESS
});

export const updateColumnCheckUIFailed = (error: unknown) => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_LABELS_ERROR,
  error
});

export const updateColumnCheckUI =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: UIAllChecksModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnCheckUIRequest());
    try {
      await ColumnApiClient.updateColumnAdHocChecksUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        data
      );
      dispatch(updateColumnCheckUISuccess());
    } catch (err) {
      dispatch(updateColumnCheckUIFailed(err));
    }
  };

export const getColumnDailyCheckpointsRequest = () => ({
  type: COLUMN_ACTION.GET_COLUMN_DAILY_CHECKPOINTS
});

export const getColumnDailyCheckpointsSuccess = (data: UIAllChecksModel) => ({
  type: COLUMN_ACTION.GET_COLUMN_DAILY_CHECKPOINTS_SUCCESS,
  data
});

export const getColumnDailyCheckpointsFailed = (error: unknown) => ({
  type: COLUMN_ACTION.GET_COLUMN_DAILY_CHECKPOINTS_ERROR,
  error
});

export const getColumnDailyCheckpoints =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(getColumnDailyCheckpointsRequest());
    try {
      const res = await ColumnApiClient.getColumnCheckpointsUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'daily'
      );
      dispatch(getColumnDailyCheckpointsSuccess(res.data));
    } catch (err) {
      dispatch(getColumnDailyCheckpointsFailed(err));
    }
  };

export const updateColumnDailyCheckpointsRequest = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_DAILY_CHECKPOINTS
});

export const updateColumnDailyCheckpointsSuccess = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_DAILY_CHECKPOINTS_SUCCESS
});

export const updateColumnDailyCheckpointsFailed = (error: unknown) => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_DAILY_CHECKPOINTS_ERROR,
  error
});

export const updateColumnDailyCheckpoints =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: UIAllChecksModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnDailyCheckpointsRequest());
    try {
      await ColumnApiClient.updateColumnCheckpointsUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'daily',
        data
      );
      dispatch(updateColumnDailyCheckpointsSuccess());
    } catch (err) {
      dispatch(updateColumnDailyCheckpointsFailed(err));
    }
  };

export const getColumnMonthlyCheckpointsRequest = () => ({
  type: COLUMN_ACTION.GET_COLUMN_MONTHLY_CHECKPOINTS
});

export const getColumnMonthlyCheckpointsSuccess = (data: UIAllChecksModel) => ({
  type: COLUMN_ACTION.GET_COLUMN_MONTHLY_CHECKPOINTS_SUCCESS,
  data
});

export const getColumnMonthlyCheckpointsFailed = (error: unknown) => ({
  type: COLUMN_ACTION.GET_COLUMN_MONTHLY_CHECKPOINTS_ERROR,
  error
});

export const getColumnMonthlyCheckpoints =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(getColumnMonthlyCheckpointsRequest());
    try {
      const res = await ColumnApiClient.getColumnCheckpointsUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'monthly'
      );
      dispatch(getColumnMonthlyCheckpointsSuccess(res.data));
    } catch (err) {
      dispatch(getColumnMonthlyCheckpointsFailed(err));
    }
  };

export const updateColumnMonthlyCheckpointsRequest = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_MONTHLY_CHECKPOINTS
});

export const updateColumnMonthlyCheckpointsSuccess = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_MONTHLY_CHECKPOINTS_SUCCESS
});

export const updateColumnMonthlyCheckpointsFailed = (error: unknown) => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_MONTHLY_CHECKPOINTS_ERROR,
  error
});

export const updateColumnMonthlyCheckpoints =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: UIAllChecksModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnMonthlyCheckpointsRequest());
    try {
      await ColumnApiClient.updateColumnCheckpointsUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'monthly',
        data
      );
      dispatch(updateColumnMonthlyCheckpointsSuccess());
    } catch (err) {
      dispatch(updateColumnMonthlyCheckpointsFailed(err));
    }
  };

export const getColumnDailyPartitionedChecksRequest = () => ({
  type: COLUMN_ACTION.GET_COLUMN_PARTITIONED_DAILY_CHECKS
});

export const getColumnDailyPartitionedChecksSuccess = (
  data: UIAllChecksModel
) => ({
  type: COLUMN_ACTION.GET_COLUMN_PARTITIONED_DAILY_CHECKS_SUCCESS,
  data
});

export const getColumnDailyPartitionedChecksFailed = (error: unknown) => ({
  type: COLUMN_ACTION.GET_COLUMN_PARTITIONED_DAILY_CHECKS_ERROR,
  error
});

export const getColumnDailyPartitionedChecks =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(getColumnDailyPartitionedChecksRequest());
    try {
      const res = await ColumnApiClient.getColumnPartitionedChecksUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'daily'
      );
      dispatch(getColumnDailyPartitionedChecksSuccess(res.data));
    } catch (err) {
      dispatch(getColumnDailyPartitionedChecksFailed(err));
    }
  };

export const updateColumnDailyPartitionedChecksRequest = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_PARTITIONED_DAILY_CHECKS
});

export const updateColumnDailyPartitionedChecksSuccess = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_PARTITIONED_DAILY_CHECKS_SUCCESS
});

export const updateColumnDailyPartitionedChecksFailed = (error: unknown) => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_PARTITIONED_DAILY_CHECKS_ERROR,
  error
});

export const updateColumnDailyPartitionedChecks =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: UIAllChecksModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnDailyPartitionedChecksRequest());
    try {
      await ColumnApiClient.updateColumnPartitionedChecksUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'daily',
        data
      );
      dispatch(updateColumnDailyPartitionedChecksSuccess());
    } catch (err) {
      dispatch(updateColumnDailyPartitionedChecksFailed(err));
    }
  };

export const getColumnMonthlyPartitionedChecksRequest = () => ({
  type: COLUMN_ACTION.GET_COLUMN_PARTITIONED_MONTHLY_CHECKS
});

export const getColumnMonthlyPartitionedChecksSuccess = (
  data: UIAllChecksModel
) => ({
  type: COLUMN_ACTION.GET_COLUMN_PARTITIONED_MONTHLY_CHECKS_SUCCESS,
  data
});

export const getColumnMonthlyPartitionedChecksFailed = (error: unknown) => ({
  type: COLUMN_ACTION.GET_COLUMN_PARTITIONED_MONTHLY_CHECKS_ERROR,
  error
});

export const getColumnMonthlyPartitionedChecks =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(getColumnMonthlyPartitionedChecksRequest());
    try {
      const res = await ColumnApiClient.getColumnPartitionedChecksUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'monthly'
      );
      dispatch(getColumnMonthlyPartitionedChecksSuccess(res.data));
    } catch (err) {
      dispatch(getColumnMonthlyPartitionedChecksFailed(err));
    }
  };

export const updateColumnMonthlyPartitionedChecksRequest = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_PARTITIONED_MONTHLY_CHECKS
});

export const updateColumnMonthlyPartitionedChecksSuccess = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_PARTITIONED_MONTHLY_CHECKS_SUCCESS
});

export const updateColumnMonthlyPartitionedChecksFailed = (error: unknown) => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_PARTITIONED_MONTHLY_CHECKS_ERROR,
  error
});

export const updateColumnMonthlyPartitionedChecks =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: UIAllChecksModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnMonthlyPartitionedChecksRequest());
    try {
      await ColumnApiClient.updateColumnPartitionedChecksUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'monthly',
        data
      );
      dispatch(updateColumnMonthlyPartitionedChecksSuccess());
    } catch (err) {
      dispatch(updateColumnMonthlyPartitionedChecksFailed(err));
    }
  };
