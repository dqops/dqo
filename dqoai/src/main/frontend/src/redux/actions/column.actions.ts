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
import { ColumnBasicModel, CommentSpec, UICheckContainerModel } from '../../api';

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

export const getColumnChecksUISuccess = (data: UICheckContainerModel) => ({
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
      const res = await ColumnApiClient.getColumnProfilingChecksUI(
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
    data: UICheckContainerModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnCheckUIRequest());
    try {
      await ColumnApiClient.updateColumnProfilingChecksUI(
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

export const getColumnDailyRecurringRequest = () => ({
  type: COLUMN_ACTION.GET_COLUMN_DAILY_RECURRING
});

export const getColumnDailyRecurringSuccess = (data: UICheckContainerModel) => ({
  type: COLUMN_ACTION.GET_COLUMN_DAILY_RECURRING_SUCCESS,
  data
});

export const getColumnDailyRecurringFailed = (error: unknown) => ({
  type: COLUMN_ACTION.GET_COLUMN_DAILY_RECURRING_ERROR,
  error
});

export const getColumnDailyRecurring =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(getColumnDailyRecurringRequest());
    try {
      const res = await ColumnApiClient.getColumnRecurringUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'daily'
      );
      dispatch(getColumnDailyRecurringSuccess(res.data));
    } catch (err) {
      dispatch(getColumnDailyRecurringFailed(err));
    }
  };

export const updateColumnDailyRecurringRequest = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_DAILY_RECURRING
});

export const updateColumnDailyRecurringSuccess = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_DAILY_RECURRING_SUCCESS
});

export const updateColumnDailyRecurringFailed = (error: unknown) => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_DAILY_RECURRING_ERROR,
  error
});

export const updateColumnDailyRecurring =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: UICheckContainerModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnDailyRecurringRequest());
    try {
      await ColumnApiClient.updateColumnRecurringUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'daily',
        data
      );
      dispatch(updateColumnDailyRecurringSuccess());
    } catch (err) {
      dispatch(updateColumnDailyRecurringFailed(err));
    }
  };

export const getColumnMonthlyRecurringRequest = () => ({
  type: COLUMN_ACTION.GET_COLUMN_MONTHLY_RECURRING
});

export const getColumnMonthlyRecurringSuccess = (data: UICheckContainerModel) => ({
  type: COLUMN_ACTION.GET_COLUMN_MONTHLY_RECURRING_SUCCESS,
  data
});

export const getColumnMonthlyRecurringFailed = (error: unknown) => ({
  type: COLUMN_ACTION.GET_COLUMN_MONTHLY_RECURRING_ERROR,
  error
});

export const getColumnMonthlyRecurring =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(getColumnMonthlyRecurringRequest());
    try {
      const res = await ColumnApiClient.getColumnRecurringUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'monthly'
      );
      dispatch(getColumnMonthlyRecurringSuccess(res.data));
    } catch (err) {
      dispatch(getColumnMonthlyRecurringFailed(err));
    }
  };

export const updateColumnMonthlyRecurringRequest = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_MONTHLY_RECURRING
});

export const updateColumnMonthlyRecurringSuccess = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_MONTHLY_RECURRING_SUCCESS
});

export const updateColumnMonthlyRecurringFailed = (error: unknown) => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_MONTHLY_RECURRING_ERROR,
  error
});

export const updateColumnMonthlyRecurring =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: UICheckContainerModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnMonthlyRecurringRequest());
    try {
      await ColumnApiClient.updateColumnRecurringUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'monthly',
        data
      );
      dispatch(updateColumnMonthlyRecurringSuccess());
    } catch (err) {
      dispatch(updateColumnMonthlyRecurringFailed(err));
    }
  };

export const getColumnDailyPartitionedChecksRequest = () => ({
  type: COLUMN_ACTION.GET_COLUMN_PARTITIONED_DAILY_CHECKS
});

export const getColumnDailyPartitionedChecksSuccess = (
  data: UICheckContainerModel
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
    data: UICheckContainerModel
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
  data: UICheckContainerModel
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
    data: UICheckContainerModel
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

export const setUpdatedColumnBasic = (column?: ColumnBasicModel) => ({
  type: COLUMN_ACTION.SET_UPDATED_COLUMN_BASIC,
  column
});

export const setUpdatedComments = (comments?: CommentSpec[]) => ({
  type: COLUMN_ACTION.SET_UPDATED_COMMENTS,
  comments
});

export const setIsUpdatedComments = (isUpdated?: boolean) => ({
  type: COLUMN_ACTION.SET_IS_UPDATED_COMMENTS,
  isUpdated
});

export const setUpdatedLabels = (labels?: string[]) => ({
  type: COLUMN_ACTION.SET_UPDATED_LABELS,
  labels
});

export const setUpdatedChecksUi = (checksUI?: UICheckContainerModel) => ({
  type: COLUMN_ACTION.SET_UPDATED_CHECKS_UI,
  checksUI
});

export const setUpdatedDailyRecurring = (checksUI?: UICheckContainerModel) => ({
  type: COLUMN_ACTION.SET_COLUMN_DAILY_RECURRING,
  checksUI
});

export const setUpdatedMonthlyRecurring = (checksUI?: UICheckContainerModel) => ({
  type: COLUMN_ACTION.SET_COLUMN_MONTHLY_RECURRING,
  checksUI
});

export const setUpdatedDailyPartitionedChecks = (
  checksUI?: UICheckContainerModel
) => ({
  type: COLUMN_ACTION.SET_COLUMN_PARTITIONED_DAILY_CHECKS,
  checksUI
});

export const setUpdatedMonthlyPartitionedChecks = (
  checksUI?: UICheckContainerModel
) => ({
  type: COLUMN_ACTION.SET_COLUMN_PARTITIONED_MONTHLY_CHECKS,
  checksUI
});

export const getColumnProfilingChecksUIFilterRequest = () => ({
  type: COLUMN_ACTION.GET_COLUMN_PROFILINGS_CHECKS_UI_FILTER
});

export const getColumnProfilingChecksUIFilterSuccess = (
  data: UICheckContainerModel
) => ({
  type: COLUMN_ACTION.GET_COLUMN_PROFILINGS_CHECKS_UI_FILTER_SUCCESS,
  data
});

export const getColumnProfilingChecksUIFilterFailed = (error: unknown) => ({
  type: COLUMN_ACTION.GET_COLUMN_PROFILINGS_CHECKS_UI_FILTER_ERROR,
  error
});

export const getColumnProfilingChecksUIFilter =
  (connectionName: string, schemaName: string, tableName: string, columnName: string, category: string, checkName: string) =>
    async (dispatch: Dispatch) => {
      dispatch(getColumnProfilingChecksUIFilterRequest());
      try {
        const res = await ColumnApiClient.getColumnProfilingChecksUIFilter(
          connectionName,
          schemaName,
          tableName,
          columnName,
          category,
          checkName
        );
        dispatch(getColumnProfilingChecksUIFilterSuccess(res.data));
      } catch (err) {
        dispatch(getColumnProfilingChecksUIFilterFailed(err));
      }
    };

export const getColumnRecurringUIFilterRequest = () => ({
  type: COLUMN_ACTION.GET_COLUMN_RECURRING_UI_FILTER
});

export const getColumnRecurringUIFilterSuccess = (
  data: UICheckContainerModel
) => ({
  type: COLUMN_ACTION.GET_COLUMN_RECURRING_UI_FILTER_SUCCESS,
  data
});

export const getColumnRecurringUIFilterFailed = (error: unknown) => ({
  type: COLUMN_ACTION.GET_COLUMN_RECURRING_UI_FILTER_ERROR,
  error
});

export const getColumnRecurringUIFilter =
  (connectionName: string, schemaName: string, tableName: string, columnName: string, timePartitioned: 'daily' | 'monthly', category: string, checkName: string) =>
    async (dispatch: Dispatch) => {
      dispatch(getColumnRecurringUIFilterRequest());
      try {
        const res = await ColumnApiClient.getColumnRecurringUIFilter(
          connectionName,
          schemaName,
          tableName,
          columnName,
          timePartitioned,
          category,
          checkName
        );
        dispatch(getColumnRecurringUIFilterSuccess(res.data));
      } catch (err) {
        dispatch(getColumnRecurringUIFilterFailed(err));
      }
    };

export const getColumnPartitionedChecksUIFilterRequest = () => ({
  type: COLUMN_ACTION.GET_COLUMN_PARTITIONED_CHECKS_UI_FILTER
});

export const getColumnPartitionedChecksUIFilterSuccess = (
  data: UICheckContainerModel
) => ({
  type: COLUMN_ACTION.GET_COLUMN_PARTITIONED_CHECKS_UI_FILTER_SUCCESS,
  data
});

export const getColumnPartitionedChecksUIFilterFailed = (error: unknown) => ({
  type: COLUMN_ACTION.GET_COLUMN_PARTITIONED_CHECKS_UI_FILTER_ERROR,
  error
});

export const getColumnPartitionedChecksUIFilter =
  (connectionName: string, schemaName: string, tableName: string, columnName: string, timePartitioned: 'daily' | 'monthly', category: string, checkName: string) =>
    async (dispatch: Dispatch) => {
      dispatch(getColumnPartitionedChecksUIFilterRequest());
      try {
        const res = await ColumnApiClient.getColumnPartitionedChecksUIFilter(
          connectionName,
          schemaName,
          tableName,
          columnName,
          timePartitioned,
          category,
          checkName
        );
        dispatch(getColumnPartitionedChecksUIFilterSuccess(res.data));
      } catch (err) {
        dispatch(getColumnPartitionedChecksUIFilterFailed(err));
      }
    };

export const setColumnUpdatedCheckUiFilter = (ui: UICheckContainerModel) => ({
  type: COLUMN_ACTION.SET_UPDATED_CHECKS_UI_FILTER,
  data: ui
});

export const setColumnUpdatedRecurringUIFilter = (ui: UICheckContainerModel) => ({
  type: COLUMN_ACTION.SET_UPDATED_RECURRING_UI_FILTER,
  data: ui
});
export const setColumnUpdatedPartitionedChecksUiFilter = (ui: UICheckContainerModel) => ({
  type: COLUMN_ACTION.SET_UPDATED_PARTITIONED_CHECKS_UI_FILTER,
  data: ui
});
