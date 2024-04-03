///
/// Copyright © 2024 DQOps (support@dqops.com)
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
import { ColumnListModel, CommentSpec, CheckContainerModel } from '../../api';
import { CheckTypes } from "../../shared/routes";

export const getColumnBasicRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_COLUMN_BASIC,
  checkType,
  activeTab,
});

export const getColumnBasicSuccess = (checkType: CheckTypes, activeTab: string, data: ColumnListModel) => ({
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
    data: ColumnListModel
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

export const getColumnProfilingChecksModelRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_COLUMN_PROFILING_CHECKS_MODEL,
  checkType,
  activeTab,
});

export const getColumnProfilingChecksModelSuccess = (checkType: CheckTypes, activeTab: string, data: CheckContainerModel) => ({
  type: SOURCE_ACTION.GET_COLUMN_PROFILING_CHECKS_MODEL_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getColumnProfilingChecksModelFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.GET_COLUMN_PROFILING_CHECKS_MODEL_ERROR,
  checkType,
  activeTab,
  error
});

export const getColumnProfilingChecksModel =
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
      dispatch(getColumnProfilingChecksModelRequest(checkType, activeTab));
    }
    try {
      const res = await ColumnApiClient.getColumnProfilingChecksModel(
        connectionName,
        schemaName,
        tableName,
        columnName
      );
      dispatch(getColumnProfilingChecksModelSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getColumnProfilingChecksModelFailed(checkType, activeTab, err));
    }
  };

export const updateColumnProfilingChecksModelRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_LABELS,
  checkType,
  activeTab,
});

export const updateColumnProfilingChecksModelSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_LABELS_SUCCESS,
  checkType,
  activeTab,
});

export const updateColumnProfilingChecksModelFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_LABELS_ERROR,
  checkType,
  activeTab,
  error
});

export const updateColumnProfilingChecksModel =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: CheckContainerModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnProfilingChecksModelRequest(checkType, activeTab));
    try {
      await ColumnApiClient.updateColumnProfilingChecksModel(
        connectionName,
        schemaName,
        tableName,
        columnName,
        data
      );
      dispatch(updateColumnProfilingChecksModelSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateColumnProfilingChecksModelFailed(checkType, activeTab, err));
    }
  };

export const getColumnDailyMonitoringChecksRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_COLUMN_DAILY_MONITORING_CHECKS,
  checkType,
  activeTab,
});

export const getColumnDailyMonitoringChecksSuccess = (checkType: CheckTypes, activeTab: string, data: CheckContainerModel) => ({
  type: SOURCE_ACTION.GET_COLUMN_DAILY_MONITORING_CHECKS_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getColumnDailyMonitoringChecksFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.GET_COLUMN_DAILY_MONITORING_CHECKS_ERROR,
  checkType,
  activeTab,
  error
});

export const getColumnDailyMonitoringChecks =
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
      dispatch(getColumnDailyMonitoringChecksRequest(checkType, activeTab));
    }
    try {
      const res = await ColumnApiClient.getColumnMonitoringChecksModel(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'daily'
      );
      dispatch(getColumnDailyMonitoringChecksSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getColumnDailyMonitoringChecksFailed(checkType, activeTab, err));
    }
  };

export const updateColumnDailyMonitoringChecksRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_DAILY_MONITORING_CHECKS,
  checkType,
  activeTab,
});

export const updateColumnDailyMonitoringChecksSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_DAILY_MONITORING_CHECKS_SUCCESS,
  checkType,
  activeTab,
});

export const updateColumnDailyMonitoringChecksFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_DAILY_MONITORING_CHECKS_ERROR,
  checkType,
  activeTab,
  error
});

export const updateColumnDailyMonitoringChecks =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: CheckContainerModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnDailyMonitoringChecksRequest(checkType, activeTab));
    try {
      await ColumnApiClient.updateColumnMonitoringChecksModel(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'daily',
        data
      );
      dispatch(updateColumnDailyMonitoringChecksSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateColumnDailyMonitoringChecksFailed(checkType, activeTab, err));
    }
  };

export const getColumnMonthlyMonitoringChecksRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_COLUMN_MONTHLY_MONITORING_CHECKS,
  checkType,
  activeTab,
});

export const getColumnMonthlyMonitoringChecksSuccess = (checkType: CheckTypes, activeTab: string, data: CheckContainerModel) => ({
  type: SOURCE_ACTION.GET_COLUMN_MONTHLY_MONITORING_CHECKS_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getColumnMonthlyMonitoringChecksFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.GET_COLUMN_MONTHLY_MONITORING_CHECKS_ERROR,
  checkType,
  activeTab,
  error
});

export const getColumnMonthlyMonitoringChecks =
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
      dispatch(getColumnMonthlyMonitoringChecksRequest(checkType, activeTab));
    }
    try {
      const res = await ColumnApiClient.getColumnMonitoringChecksModel(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'monthly'
      );
      dispatch(getColumnMonthlyMonitoringChecksSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getColumnMonthlyMonitoringChecksFailed(checkType, activeTab, err));
    }
  };

export const updateColumnMonthlyMonitoringChecksRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_MONTHLY_MONITORING_CHECKS,
  checkType,
  activeTab,
});

export const updateColumnMonthlyMonitoringChecksSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_MONTHLY_MONITORING_CHECKS_SUCCESS,
  checkType,
  activeTab,
});

export const updateColumnMonthlyMonitoringChecksFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_MONTHLY_MONITORING_CHECKS_ERROR,
  checkType,
  activeTab,
  error
});

export const updateColumnMonthlyMonitoringChecks =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: CheckContainerModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnMonthlyMonitoringChecksRequest(checkType, activeTab));
    try {
      await ColumnApiClient.updateColumnMonitoringChecksModel(
        connectionName,
        schemaName,
        tableName,
        columnName,
        'monthly',
        data
      );
      dispatch(updateColumnMonthlyMonitoringChecksSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateColumnMonthlyMonitoringChecksFailed(checkType, activeTab, err));
    }
  };

export const getColumnDailyPartitionedChecksRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_COLUMN_DAILY_PARTITIONED_CHECKS,
  checkType,
  activeTab,
});

export const getColumnDailyPartitionedChecksSuccess = (
  checkType: CheckTypes,
  activeTab: string,
  data: CheckContainerModel
) => ({
  type: SOURCE_ACTION.GET_COLUMN_DAILY_PARTITIONED_CHECKS_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getColumnDailyPartitionedChecksFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.GET_COLUMN_DAILY_PARTITIONED_CHECKS_ERROR,
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
      const res = await ColumnApiClient.getColumnPartitionedChecksModel(
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
  type: SOURCE_ACTION.UPDATE_COLUMN_DAILY_PARTITIONED_CHECKS,
  checkType,
  activeTab,
});

export const updateColumnDailyPartitionedChecksSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_DAILY_PARTITIONED_CHECKS_SUCCESS,
  checkType,
  activeTab,
});

export const updateColumnDailyPartitionedChecksFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_DAILY_PARTITIONED_CHECKS_ERROR,
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
    data: CheckContainerModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnDailyPartitionedChecksRequest(checkType, activeTab));
    try {
      await ColumnApiClient.updateColumnPartitionedChecksModel(
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
  type: SOURCE_ACTION.GET_COLUMN_MONTHLY_PARTITIONED_CHECKS,
  checkType,
  activeTab,
});

export const getColumnMonthlyPartitionedChecksSuccess = (
  checkType: CheckTypes,
  activeTab: string,
  data: CheckContainerModel
) => ({
  type: SOURCE_ACTION.GET_COLUMN_MONTHLY_PARTITIONED_CHECKS_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getColumnMonthlyPartitionedChecksFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.GET_COLUMN_MONTHLY_PARTITIONED_CHECKS_ERROR,
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
      const res = await ColumnApiClient.getColumnPartitionedChecksModel(
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
  type: SOURCE_ACTION.UPDATE_COLUMN_MONTHLY_PARTITIONED_CHECKS,
  checkType,
  activeTab
});

export const updateColumnMonthlyPartitionedChecksSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_MONTHLY_PARTITIONED_CHECKS_SUCCESS,
  checkType,
  activeTab
});

export const updateColumnMonthlyPartitionedChecksFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_COLUMN_MONTHLY_PARTITIONED_CHECKS_ERROR,
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
    data: CheckContainerModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnMonthlyPartitionedChecksRequest(checkType, activeTab));
    try {
      await ColumnApiClient.updateColumnPartitionedChecksModel(
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

export const setUpdatedColumnBasic = (checkType: CheckTypes, activeTab: string, column?: ColumnListModel) => ({
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

export const setUpdatedChecksModel = (checkType: CheckTypes, activeTab: string, checksUI?: CheckContainerModel) => ({
  type: SOURCE_ACTION.SET_UPDATED_CHECKS_MODEL,
  checkType,
  activeTab,
  data: checksUI
});

export const setUpdatedDailyMonitoringChecks = (checkType: CheckTypes, activeTab: string, checksUI?: CheckContainerModel) => ({
  type: SOURCE_ACTION.SET_COLUMN_DAILY_MONITORING_CHECKS,
  checkType,
  activeTab,
  data: checksUI
});

export const setUpdatedMonthlyMonitoringChecks = (checkType: CheckTypes, activeTab: string, checksUI?: CheckContainerModel) => ({
  type: SOURCE_ACTION.SET_COLUMN_MONTHLY_MONITORING_CHECKS,
  checkType,
  activeTab,
  data: checksUI
});

export const setUpdatedDailyPartitionedChecks = (
  checkType: CheckTypes,
  activeTab: string,
  checksUI?: CheckContainerModel
) => ({
  type: SOURCE_ACTION.SET_COLUMN_DAILY_PARTITIONED_CHECKS,
  checkType,
  activeTab,
  data: checksUI
});

export const setUpdatedMonthlyPartitionedChecks = (
  checkType: CheckTypes,
  activeTab: string,
  checksUI?: CheckContainerModel
) => ({
  type: SOURCE_ACTION.SET_COLUMN_MONTHLY_PARTITIONED_CHECKS,
  checkType,
  activeTab,
  data: checksUI
});

export const getColumnProfilingChecksModelFilterRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_COLUMN_PROFILING_CHECKS_MODEL_FILTER,
  checkType,
  activeTab,
});

export const getColumnProfilingChecksModelFilterSuccess = (
  checkType: CheckTypes,
  activeTab: string,
  data: CheckContainerModel
) => ({
  type: SOURCE_ACTION.GET_COLUMN_PROFILING_CHECKS_MODEL_FILTER_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getColumnProfilingChecksModelFilterFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.GET_COLUMN_PROFILING_CHECKS_MODEL_FILTER_ERROR,
  checkType,
  activeTab,
  error
});

export const getColumnProfilingChecksModelFilter =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, columnName: string, category: string, checkName: string, loading = true) =>
    async (dispatch: Dispatch) => {
      if (loading) {
        dispatch(getColumnProfilingChecksModelFilterRequest(checkType, activeTab));
      }
      try {
        const res = await ColumnApiClient.getColumnProfilingChecksModelFilter(
          connectionName,
          schemaName,
          tableName,
          columnName,
          category,
          checkName
        );
        dispatch(getColumnProfilingChecksModelFilterSuccess(checkType, activeTab, res.data));
      } catch (err) {
        dispatch(getColumnProfilingChecksModelFilterFailed(checkType, activeTab, err));
      }
    };

export const getColumnMonitoringChecksModelFilterRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_COLUMN_MONITORING_CHECKS_MODEL_FILTER,
  checkType,
  activeTab,
});

export const getColumnMonitoringChecksModelFilterSuccess = (
  checkType: CheckTypes,
  activeTab: string,
  data: CheckContainerModel
) => ({
  type: SOURCE_ACTION.GET_COLUMN_MONITORING_CHECKS_MODEL_FILTER_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getColumnMonitoringChecksModelFilterFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.GET_COLUMN_MONITORING_CHECKS_MODEL_FILTER_ERROR,
  checkType,
  activeTab,
  error
});

export const getColumnMonitoringChecksModelFilter =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, columnName: string, timePartitioned: 'daily' | 'monthly', category: string, checkName: string, loading = true) =>
    async (dispatch: Dispatch) => {
      if (loading) {
        dispatch(getColumnMonitoringChecksModelFilterRequest(checkType, activeTab));
      }
      try {
        const res = await ColumnApiClient.getColumnMonitoringChecksModelFilter(
          connectionName,
          schemaName,
          tableName,
          columnName,
          timePartitioned,
          category,
          checkName
        );
        dispatch(getColumnMonitoringChecksModelFilterSuccess(checkType, activeTab, res.data));
      } catch (err) {
        dispatch(getColumnMonitoringChecksModelFilterFailed(checkType, activeTab, err));
      }
    };

export const getColumnPartitionedChecksModelFilterRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_COLUMN_PARTITIONED_CHECKS_MODEL_FILTER,
  checkType,
  activeTab,
});

export const getColumnPartitionedChecksModelFilterSuccess = (
  checkType: CheckTypes,
  activeTab: string,
  data: CheckContainerModel
) => ({
  type: SOURCE_ACTION.GET_COLUMN_PARTITIONED_CHECKS_MODEL_FILTER_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getColumnPartitionedChecksModelFilterFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.GET_COLUMN_PARTITIONED_CHECKS_MODEL_FILTER_ERROR,
  checkType,
  activeTab,
  error
});

export const getColumnPartitionedChecksModelFilter =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, columnName: string, timePartitioned: 'daily' | 'monthly', category: string, checkName: string, loading = true) =>
    async (dispatch: Dispatch) => {
      if (loading) {
        dispatch(getColumnPartitionedChecksModelFilterRequest(checkType, activeTab));
      }
      try {
        const res = await ColumnApiClient.getColumnPartitionedChecksModelFilter(
          connectionName,
          schemaName,
          tableName,
          columnName,
          timePartitioned,
          category,
          checkName
        );
        dispatch(getColumnPartitionedChecksModelFilterSuccess(checkType, activeTab, res.data));
      } catch (err) {
        dispatch(getColumnPartitionedChecksModelFilterFailed(checkType, activeTab, err));
      }
    };

export const setColumnUpdatedProfilingChecksModelFilter = (checkType: CheckTypes, activeTab: string, ui: CheckContainerModel) => ({
  type: SOURCE_ACTION.SET_UPDATED_PROFILING_CHECKS_MODEL_FILTER,
  checkType,
  activeTab,
  data: ui
});

export const setColumnUpdatedMonitoringChecksModelFilter = (checkType: CheckTypes, activeTab: string, ui: CheckContainerModel) => ({
  type: SOURCE_ACTION.SET_UPDATED_MONITORING_CHECKS_MODEL_FILTER,
  checkType,
  activeTab,
  data: ui
});
export const setColumnUpdatedPartitionedChecksModelFilter = (checkType: CheckTypes, activeTab: string, ui: CheckContainerModel) => ({
  type: SOURCE_ACTION.SET_UPDATED_PARTITIONED_CHECKS_MODEL_FILTER,
  checkType,
  activeTab,
  data: ui
});
