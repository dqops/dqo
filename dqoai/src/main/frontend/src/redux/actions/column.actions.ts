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
import { SOURCE_ACTION } from '../types';
import { ColumnBasicModel, CommentSpec, UICheckContainerModel } from '../../api';
import { CheckTypes } from "../../shared/routes";

export const getColumnBasicRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_COLUMN_BASIC,
  checkType,
  activeTab,
});

export const getColumnBasicSuccess = (checkType: CheckTypes, activeTab: string, data: ColumnBasicModel) => ({
  type: SOURCE_ACTION.GET_COLUMN_BASIC_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getColumnBasicFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.GET_COLUMN_BASIC_ERROR,
  checkType,
  activeTab,
  error
});

export const getColumnBasic =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(getColumnBasicRequest(checkType, activeTab));
    try {
      const res = await ColumnApiClient.getColumnBasic(
        connectionName,
        schemaName,
        tableName,
        columnName
      );
      dispatch(getColumnBasicSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getColumnBasicFailed(checkType, activeTab, err));
    }
  };

export const updateColumnBasicRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_BASIC,
  checkType,
  activeTab,
});

export const updateColumnBasicSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_BASIC_SUCCESS,
  checkType,
  activeTab,
});

export const updateColumnBasicFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_BASIC_ERROR,
  checkType,
  activeTab,
  error
});

export const updateColumnBasic =
  (
    checkType: CheckTypes, activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: ColumnBasicModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnBasicRequest(checkType, activeTab));
    try {
      await ColumnApiClient.updateColumnBasic(
        connectionName,
        schemaName,
        tableName,
        columnName,
        data
      );
      dispatch(updateColumnBasicSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateColumnBasicFailed(checkType, activeTab, err));
    }
  };

export const getColumnCommentsRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_COLUMN_COMMENTS,
  checkType,
  activeTab,
});

export const getColumnCommentsSuccess = (checkType: CheckTypes, activeTab: string, data: CommentSpec[]) => ({
  type: SOURCE_ACTION.GET_COLUMN_COMMENTS_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getColumnCommentsFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.GET_COLUMN_COMMENTS_ERROR,
  checkType,
  activeTab,
  error
});

export const getColumnComments =
  (
    checkType: CheckTypes, activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    loading = true
  ) =>
  async (dispatch: Dispatch) => {
    if (loading) {
      dispatch(getColumnCommentsRequest(checkType, activeTab));
    }
    try {
      const res = await ColumnApiClient.getColumnComments(
        connectionName,
        schemaName,
        tableName,
        columnName
      );
      dispatch(getColumnCommentsSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getColumnCommentsFailed(checkType, activeTab, err));
    }
  };

export const updateColumnCommentsRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_COMMENTS,
  checkType,
  activeTab,
});

export const updateColumnCommentsSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_COMMENTS_SUCCESS,
  checkType,
  activeTab,
});

export const updateColumnCommentsFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_COMMENTS_ERROR,
  checkType,
  activeTab,
  error
});

export const updateColumnComments =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: CommentSpec[]
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnCommentsRequest(checkType, activeTab));
    try {
      await ColumnApiClient.updateColumnComments(
        connectionName,
        schemaName,
        tableName,
        columnName,
        data
      );
      dispatch(updateColumnCommentsSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateColumnCommentsFailed(checkType, activeTab, err));
    }
  };

export const getColumnLabelsRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_COLUMN_LABELS,
  checkType,
  activeTab,
});

export const getColumnLabelsSuccess = (checkType: CheckTypes, activeTab: string, data: string[]) => ({
  type: SOURCE_ACTION.GET_COLUMN_LABELS_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getColumnLabelsFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.GET_COLUMN_LABELS_ERROR,
  checkType,
  activeTab,
  error
});

export const getColumnLabels =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    loading = true,
  ) =>
  async (dispatch: Dispatch) => {
    if (loading) {
      dispatch(getColumnLabelsRequest(checkType, activeTab));
    }
    try {
      const res = await ColumnApiClient.getColumnLabels(
        connectionName,
        schemaName,
        tableName,
        columnName
      );
      dispatch(getColumnLabelsSuccess(checkType, activeTab, res.data || []));
    } catch (err) {
      dispatch(getColumnLabelsFailed(checkType, activeTab, err));
    }
  };

export const updateColumnLabelsRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_LABELS,
  checkType,
  activeTab,
});

export const updateColumnLabelsSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_LABELS_SUCCESS,
  checkType,
  activeTab,
});

export const updateColumnLabelsFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_LABELS_ERROR,
  checkType,
  activeTab,
  error
});

export const updateColumnLabels =
  (
    checkType: CheckTypes, activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: string[]
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnLabelsRequest(checkType, activeTab));
    try {
      await ColumnApiClient.updateColumnLabels(
        connectionName,
        schemaName,
        tableName,
        columnName,
        data
      );
      dispatch(updateColumnLabelsSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateColumnLabelsFailed(checkType, activeTab, err));
    }
  };

export const getColumnChecksUIRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_COLUMN_CHECKS_UI,
  checkType,
  activeTab,
});

export const getColumnChecksUISuccess = (checkType: CheckTypes, activeTab: string, data: UICheckContainerModel) => ({
  type: SOURCE_ACTION.GET_COLUMN_CHECKS_UI_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getColumnChecksUIFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.GET_COLUMN_CHECKS_UI_ERROR,
  checkType,
  activeTab,
  error
});

export const getColumnChecksUi =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    loading = true,
  ) =>
  async (dispatch: Dispatch) => {
    if (loading) {
      dispatch(getColumnChecksUIRequest(checkType, activeTab));
    }
    try {
      const res = await ColumnApiClient.getColumnProfilingChecksUI(
        connectionName,
        schemaName,
        tableName,
        columnName
      );
      dispatch(getColumnChecksUISuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getColumnChecksUIFailed(checkType, activeTab, err));
    }
  };

export const updateColumnCheckUIRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_LABELS,
  checkType,
  activeTab,
});

export const updateColumnCheckUISuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_LABELS_SUCCESS,
  checkType,
  activeTab,
});

export const updateColumnCheckUIFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_LABELS_ERROR,
  checkType,
  activeTab,
  error
});

export const updateColumnCheckUI =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: UICheckContainerModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnCheckUIRequest(checkType, activeTab));
    try {
      await ColumnApiClient.updateColumnProfilingChecksUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        data
      );
      dispatch(updateColumnCheckUISuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateColumnCheckUIFailed(checkType, activeTab, err));
    }
  };

export const getColumnDailyRecurringRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_COLUMN_DAILY_RECURRING,
  checkType,
  activeTab,
});

export const getColumnDailyRecurringSuccess = (checkType: CheckTypes, activeTab: string, data: UICheckContainerModel) => ({
  type: SOURCE_ACTION.GET_COLUMN_DAILY_RECURRING_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getColumnDailyRecurringFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.GET_COLUMN_DAILY_RECURRING_ERROR,
  checkType,
  activeTab,
  error
});

export const getColumnDailyRecurring =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    loading = true
  ) =>
  async (dispatch: Dispatch) => {
    if (loading) {
      dispatch(getColumnDailyRecurringRequest(checkType, activeTab));
    }
    try {
      const res = await ColumnApiClient.getColumnRecurringChecksUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'daily'
      );
      dispatch(getColumnDailyRecurringSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getColumnDailyRecurringFailed(checkType, activeTab, err));
    }
  };

export const updateColumnDailyRecurringRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_DAILY_RECURRING,
  checkType,
  activeTab,
});

export const updateColumnDailyRecurringSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_DAILY_RECURRING_SUCCESS,
  checkType,
  activeTab,
});

export const updateColumnDailyRecurringFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_DAILY_RECURRING_ERROR,
  checkType,
  activeTab,
  error
});

export const updateColumnDailyRecurring =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: UICheckContainerModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnDailyRecurringRequest(checkType, activeTab));
    try {
      await ColumnApiClient.updateColumnRecurringChecksUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'daily',
        data
      );
      dispatch(updateColumnDailyRecurringSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateColumnDailyRecurringFailed(checkType, activeTab, err));
    }
  };

export const getColumnMonthlyRecurringRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_COLUMN_MONTHLY_RECURRING,
  checkType,
  activeTab,
});

export const getColumnMonthlyRecurringSuccess = (checkType: CheckTypes, activeTab: string, data: UICheckContainerModel) => ({
  type: SOURCE_ACTION.GET_COLUMN_MONTHLY_RECURRING_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getColumnMonthlyRecurringFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.GET_COLUMN_MONTHLY_RECURRING_ERROR,
  checkType,
  activeTab,
  error
});

export const getColumnMonthlyRecurring =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    loading = true,
  ) =>
  async (dispatch: Dispatch) => {
    if (loading) {
      dispatch(getColumnMonthlyRecurringRequest(checkType, activeTab));
    }
    try {
      const res = await ColumnApiClient.getColumnRecurringChecksUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'monthly'
      );
      dispatch(getColumnMonthlyRecurringSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getColumnMonthlyRecurringFailed(checkType, activeTab, err));
    }
  };

export const updateColumnMonthlyRecurringRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_MONTHLY_RECURRING,
  checkType,
  activeTab,
});

export const updateColumnMonthlyRecurringSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_MONTHLY_RECURRING_SUCCESS,
  checkType,
  activeTab,
});

export const updateColumnMonthlyRecurringFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_MONTHLY_RECURRING_ERROR,
  checkType,
  activeTab,
  error
});

export const updateColumnMonthlyRecurring =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: UICheckContainerModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnMonthlyRecurringRequest(checkType, activeTab));
    try {
      await ColumnApiClient.updateColumnRecurringChecksUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'monthly',
        data
      );
      dispatch(updateColumnMonthlyRecurringSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateColumnMonthlyRecurringFailed(checkType, activeTab, err));
    }
  };

export const getColumnDailyPartitionedChecksRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_COLUMN_PARTITIONED_DAILY_CHECKS,
  checkType,
  activeTab,
});

export const getColumnDailyPartitionedChecksSuccess = (
  checkType: CheckTypes,
  activeTab: string,
  data: UICheckContainerModel
) => ({
  type: SOURCE_ACTION.GET_COLUMN_PARTITIONED_DAILY_CHECKS_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getColumnDailyPartitionedChecksFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.GET_COLUMN_PARTITIONED_DAILY_CHECKS_ERROR,
  checkType,
  activeTab,
  error
});

export const getColumnDailyPartitionedChecks =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    loading = true
  ) =>
  async (dispatch: Dispatch) => {
    if (loading) {
      dispatch(getColumnDailyPartitionedChecksRequest(checkType, activeTab));
    }
    try {
      const res = await ColumnApiClient.getColumnPartitionedChecksUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'daily'
      );
      dispatch(getColumnDailyPartitionedChecksSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getColumnDailyPartitionedChecksFailed(checkType, activeTab, err));
    }
  };

export const updateColumnDailyPartitionedChecksRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_PARTITIONED_DAILY_CHECKS,
  checkType,
  activeTab,
});

export const updateColumnDailyPartitionedChecksSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_PARTITIONED_DAILY_CHECKS_SUCCESS,
  checkType,
  activeTab,
});

export const updateColumnDailyPartitionedChecksFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_PARTITIONED_DAILY_CHECKS_ERROR,
  checkType,
  activeTab,
  error
});

export const updateColumnDailyPartitionedChecks =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: UICheckContainerModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnDailyPartitionedChecksRequest(checkType, activeTab));
    try {
      await ColumnApiClient.updateColumnPartitionedChecksUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'daily',
        data
      );
      dispatch(updateColumnDailyPartitionedChecksSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateColumnDailyPartitionedChecksFailed(checkType, activeTab, err));
    }
  };

export const getColumnMonthlyPartitionedChecksRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_COLUMN_PARTITIONED_MONTHLY_CHECKS,
  checkType,
  activeTab,
});

export const getColumnMonthlyPartitionedChecksSuccess = (
  checkType: CheckTypes,
  activeTab: string,
  data: UICheckContainerModel
) => ({
  type: SOURCE_ACTION.GET_COLUMN_PARTITIONED_MONTHLY_CHECKS_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getColumnMonthlyPartitionedChecksFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.GET_COLUMN_PARTITIONED_MONTHLY_CHECKS_ERROR,
  checkType,
  activeTab,
  error
});

export const getColumnMonthlyPartitionedChecks =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    loading = true,
  ) =>
  async (dispatch: Dispatch) => {
    if (loading) {
      dispatch(getColumnMonthlyPartitionedChecksRequest(checkType, activeTab));
    }
    try {
      const res = await ColumnApiClient.getColumnPartitionedChecksUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'monthly'
      );
      dispatch(getColumnMonthlyPartitionedChecksSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getColumnMonthlyPartitionedChecksFailed(checkType, activeTab, err));
    }
  };

export const updateColumnMonthlyPartitionedChecksRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_PARTITIONED_MONTHLY_CHECKS,
  checkType,
  activeTab
});

export const updateColumnMonthlyPartitionedChecksSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_PARTITIONED_MONTHLY_CHECKS_SUCCESS,
  checkType,
  activeTab
});

export const updateColumnMonthlyPartitionedChecksFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_PARTITIONED_MONTHLY_CHECKS_ERROR,
  checkType,
  activeTab,
  error
});

export const updateColumnMonthlyPartitionedChecks =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: UICheckContainerModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnMonthlyPartitionedChecksRequest(checkType, activeTab));
    try {
      await ColumnApiClient.updateColumnPartitionedChecksUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'monthly',
        data
      );
      dispatch(updateColumnMonthlyPartitionedChecksSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateColumnMonthlyPartitionedChecksFailed(checkType, activeTab, err));
    }
  };

export const setUpdatedColumnBasic = (checkType: CheckTypes, activeTab: string, column?: ColumnBasicModel) => ({
  type: SOURCE_ACTION.SET_UPDATED_COLUMN_BASIC,
  checkType,
  activeTab,
  data: column
});

export const setUpdatedComments = (checkType: CheckTypes, activeTab: string, comments?: CommentSpec[]) => ({
  type: SOURCE_ACTION.SET_UPDATED_COMMENTS,
  checkType,
  activeTab,
  data: comments
});

export const setIsUpdatedComments = (checkType: CheckTypes, activeTab: string, isUpdated?: boolean) => ({
  type: SOURCE_ACTION.SET_IS_UPDATED_COMMENTS,
  checkType,
  activeTab,
  data: isUpdated
});

export const setUpdatedLabels = (checkType: CheckTypes, activeTab: string, labels?: string[]) => ({
  type: SOURCE_ACTION.SET_UPDATED_LABELS,
  checkType,
  activeTab,
  data: labels
});

export const setUpdatedChecksUi = (checkType: CheckTypes, activeTab: string, checksUI?: UICheckContainerModel) => ({
  type: SOURCE_ACTION.SET_UPDATED_CHECKS_UI,
  checkType,
  activeTab,
  data: checksUI
});

export const setUpdatedDailyRecurring = (checkType: CheckTypes, activeTab: string, checksUI?: UICheckContainerModel) => ({
  type: SOURCE_ACTION.SET_COLUMN_DAILY_RECURRING,
  checkType,
  activeTab,
  data: checksUI
});

export const setUpdatedMonthlyRecurring = (checkType: CheckTypes, activeTab: string, checksUI?: UICheckContainerModel) => ({
  type: SOURCE_ACTION.SET_COLUMN_MONTHLY_RECURRING,
  checkType,
  activeTab,
  data: checksUI
});

export const setUpdatedDailyPartitionedChecks = (
  checkType: CheckTypes,
  activeTab: string,
  checksUI?: UICheckContainerModel
) => ({
  type: SOURCE_ACTION.SET_COLUMN_PARTITIONED_DAILY_CHECKS,
  checkType,
  activeTab,
  data: checksUI
});

export const setUpdatedMonthlyPartitionedChecks = (
  checkType: CheckTypes,
  activeTab: string,
  checksUI?: UICheckContainerModel
) => ({
  type: SOURCE_ACTION.SET_COLUMN_PARTITIONED_MONTHLY_CHECKS,
  checkType,
  activeTab,
  data: checksUI
});

export const getColumnProfilingChecksUIFilterRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_COLUMN_PROFILINGS_CHECKS_UI_FILTER,
  checkType,
  activeTab,
});

export const getColumnProfilingChecksUIFilterSuccess = (
  checkType: CheckTypes,
  activeTab: string,
  data: UICheckContainerModel
) => ({
  type: SOURCE_ACTION.GET_COLUMN_PROFILINGS_CHECKS_UI_FILTER_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getColumnProfilingChecksUIFilterFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.GET_COLUMN_PROFILINGS_CHECKS_UI_FILTER_ERROR,
  checkType,
  activeTab,
  error
});

export const getColumnProfilingChecksUIFilter =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, columnName: string, category: string, checkName: string, loading = true) =>
    async (dispatch: Dispatch) => {
      if (loading) {
        dispatch(getColumnProfilingChecksUIFilterRequest(checkType, activeTab));
      }
      try {
        const res = await ColumnApiClient.getColumnProfilingChecksUIFilter(
          connectionName,
          schemaName,
          tableName,
          columnName,
          category,
          checkName
        );
        dispatch(getColumnProfilingChecksUIFilterSuccess(checkType, activeTab, res.data));
      } catch (err) {
        dispatch(getColumnProfilingChecksUIFilterFailed(checkType, activeTab, err));
      }
    };

export const getColumnRecurringChecksUIFilterRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_COLUMN_RECURRING_UI_FILTER,
  checkType,
  activeTab,
});

export const getColumnRecurringChecksUIFilterSuccess = (
  checkType: CheckTypes,
  activeTab: string,
  data: UICheckContainerModel
) => ({
  type: SOURCE_ACTION.GET_COLUMN_RECURRING_UI_FILTER_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getColumnRecurringChecksUIFilterFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.GET_COLUMN_RECURRING_UI_FILTER_ERROR,
  checkType,
  activeTab,
  error
});

export const getColumnRecurringChecksUIFilter =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, columnName: string, timePartitioned: 'daily' | 'monthly', category: string, checkName: string, loading = true) =>
    async (dispatch: Dispatch) => {
      if (loading) {
        dispatch(getColumnRecurringChecksUIFilterRequest(checkType, activeTab));
      }
      try {
        const res = await ColumnApiClient.getColumnRecurringChecksUIFilter(
          connectionName,
          schemaName,
          tableName,
          columnName,
          timePartitioned,
          category,
          checkName
        );
        dispatch(getColumnRecurringChecksUIFilterSuccess(checkType, activeTab, res.data));
      } catch (err) {
        dispatch(getColumnRecurringChecksUIFilterFailed(checkType, activeTab, err));
      }
    };

export const getColumnPartitionedChecksUIFilterRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_COLUMN_PARTITIONED_CHECKS_UI_FILTER,
  checkType,
  activeTab,
});

export const getColumnPartitionedChecksUIFilterSuccess = (
  checkType: CheckTypes,
  activeTab: string,
  data: UICheckContainerModel
) => ({
  type: SOURCE_ACTION.GET_COLUMN_PARTITIONED_CHECKS_UI_FILTER_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getColumnPartitionedChecksUIFilterFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.GET_COLUMN_PARTITIONED_CHECKS_UI_FILTER_ERROR,
  checkType,
  activeTab,
  error
});

export const getColumnPartitionedChecksUIFilter =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, columnName: string, timePartitioned: 'daily' | 'monthly', category: string, checkName: string, loading = true) =>
    async (dispatch: Dispatch) => {
      if (loading) {
        dispatch(getColumnPartitionedChecksUIFilterRequest(checkType, activeTab));
      }
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
        dispatch(getColumnPartitionedChecksUIFilterSuccess(checkType, activeTab, res.data));
      } catch (err) {
        dispatch(getColumnPartitionedChecksUIFilterFailed(checkType, activeTab, err));
      }
    };

export const setColumnUpdatedCheckUiFilter = (checkType: CheckTypes, activeTab: string, ui: UICheckContainerModel) => ({
  type: SOURCE_ACTION.SET_UPDATED_CHECKS_UI_FILTER,
  checkType,
  activeTab,
  data: ui
});

export const setColumnUpdatedRecurringChecksUIFilter = (checkType: CheckTypes, activeTab: string, ui: UICheckContainerModel) => ({
  type: SOURCE_ACTION.SET_UPDATED_RECURRING_UI_FILTER,
  checkType,
  activeTab,
  data: ui
});
export const setColumnUpdatedPartitionedChecksUiFilter = (checkType: CheckTypes, activeTab: string, ui: UICheckContainerModel) => ({
  type: SOURCE_ACTION.SET_UPDATED_PARTITIONED_CHECKS_UI_FILTER,
  checkType,
  activeTab,
  data: ui
});
