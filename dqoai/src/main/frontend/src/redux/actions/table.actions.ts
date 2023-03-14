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
import {
  CommentSpec,
  DataStreamMappingSpec,
  RecurringScheduleSpec,
  TableProfilingCheckCategoriesSpec,
  TableBasicModel,
  UIAllChecksModel
} from '../../api';
import { CheckRunRecurringScheduleGroup } from "../../shared/enums/scheduling.enum";

export const getTablesRequest = () => ({
  type: TABLE_ACTION.GET_TABLES
});

export const getTablesSuccess = (data: TableBasicModel[]) => ({
  type: TABLE_ACTION.GET_TABLES_SUCCESS,
  data
});

export const getTablesFailed = (error: unknown) => ({
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

export const getTableBasicSuccess = (data: TableBasicModel) => ({
  type: TABLE_ACTION.GET_TABLE_BASIC_SUCCESS,
  data
});

export const getTableBasicFailed = (error: unknown) => ({
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

export const updateTableBasicFailed = (error: unknown) => ({
  type: TABLE_ACTION.UPDATE_TABLE_BASIC_ERROR,
  error
});

export const updateTableBasic =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: TableBasicModel
  ) =>
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

export const resetTableSchedulingGroup = () => ({
  type: TABLE_ACTION.RESET_TABLE_SCHEDULE_GROUP
});

export const getTableSchedulingGroupRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_SCHEDULE_GROUP
});

export const getTableSchedulingGroupSuccess = (schedulingGroup: CheckRunRecurringScheduleGroup, data: RecurringScheduleSpec) => ({
  type: TABLE_ACTION.GET_TABLE_SCHEDULE_GROUP_SUCCESS,
  schedulingGroup,
  data
});

export const getTableSchedulingGroupFailed = (error: unknown) => ({
  type: TABLE_ACTION.GET_TABLE_SCHEDULE_GROUP_ERROR,
  error
});

export const getTableSchedulingGroup =
  (connectionName: string, schemaName: string, tableName: string, schedulingGroup: CheckRunRecurringScheduleGroup) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableSchedulingGroupRequest());
    try {
      const res = await TableApiClient.getTableSchedulingGroupOverride(
        connectionName,
        schemaName,
        tableName,
        schedulingGroup
      );
      dispatch(getTableSchedulingGroupSuccess(schedulingGroup, res.data));
    } catch (err) {
      dispatch(getTableSchedulingGroupFailed(err));
    }
  };

export const updateTableSchedulingGroupRequest = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_SCHEDULE_GROUP
});

export const updateTableSchedulingGroupSuccess = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_SCHEDULE_GROUP_SUCCESS
});

export const updateTableSchedulingGroupFailed = (error: unknown) => ({
  type: TABLE_ACTION.UPDATE_TABLE_SCHEDULE_GROUP_ERROR,
  error
});

export const updateTableSchedulingGroup =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    schedulingGroup: CheckRunRecurringScheduleGroup,
    data: RecurringScheduleSpec
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableSchedulingGroupRequest());
    try {
      await TableApiClient.updateTableSchedulingGroupOverride (
        connectionName,
        schemaName,
        tableName,
        schedulingGroup,
        data
      );
      dispatch(updateTableSchedulingGroupSuccess());
    } catch (err) {
      dispatch(updateTableSchedulingGroupFailed(err));
    }
  };

export const getTableCommentsRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_COMMENTS
});

export const getTableCommentsSuccess = (data: CommentSpec[]) => ({
  type: TABLE_ACTION.GET_TABLE_COMMENTS_SUCCESS,
  data
});

export const getTableCommentsFailed = (error: unknown) => ({
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

export const updateTableCommentsFailed = (error: unknown) => ({
  type: TABLE_ACTION.UPDATE_TABLE_COMMENTS_ERROR,
  error
});

export const updateTableComments =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: CommentSpec[]
  ) =>
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

export const getTableLabelsSuccess = (data: string[]) => ({
  type: TABLE_ACTION.GET_TABLE_LABELS_SUCCESS,
  data
});

export const getTableLabelsFailed = (error: unknown) => ({
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

export const updateTableLabelsFailed = (error: unknown) => ({
  type: TABLE_ACTION.UPDATE_TABLE_LABELS_ERROR,
  error
});

export const updateTableLabels =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: string[]
  ) =>
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
// TODO: getTableChecks -> getTableProfilingChecks. Also getTableRecurring and getTablePartitionedChecks for CheckTimePartition
export const getTableChecksRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS
});

export const getTableChecksSuccess = (data: TableProfilingCheckCategoriesSpec) => ({
  type: TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_SUCCESS,
  data
});

export const getTableChecksFailed = (error: unknown) => ({
  type: TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_ERROR,
  error
});

export const getTableProfilingChecks =
  (connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableChecksRequest());
    try {
      const res = await TableApiClient.getTableProfilingChecks(
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

export const getTableChecksUiSuccess = (data: UIAllChecksModel) => ({
  type: TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_UI_SUCCESS,
  data
});

export const getTableChecksUiFailed = (error: unknown) => ({
  type: TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_UI_ERROR,
  error
});

export const getTableProfilingChecksUI =
  (connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableChecksUiRequest());
    try {
      const res = await TableApiClient.getTableProfilingChecksUI(
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

export const updateTableChecksUIFailed = (error: unknown) => ({
  type: TABLE_ACTION.UPDATE_TABLE_DATA_QUALITY_CHECKS_UI_ERROR,
  error
});

export const updateTableProfilingChecksUI =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: UIAllChecksModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableChecksUIRequest());
    try {
      await TableApiClient.updateTableProfilingChecksUI(
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

export const getTableDefaultDataStreamsMappingRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_DEFAULT_DATA_STREAMS_MAPPING
});

export const getTableDefaultDataStreamsMappingSuccess = (
  data: DataStreamMappingSpec
) => ({
  type: TABLE_ACTION.GET_TABLE_DEFAULT_DATA_STREAMS_MAPPING_SUCCESS,
  data
});

export const getTableDefaultDataStreamsMappingFailed = (error: unknown) => ({
  type: TABLE_ACTION.GET_TABLE_DEFAULT_DATA_STREAMS_MAPPING_ERROR,
  error
});

export const getTableDefaultDataStreamMapping =
  (connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableDefaultDataStreamsMappingRequest());
    try {
      const res = await TableApiClient.getTableDefaultDataStreamsMapping(
        connectionName,
        schemaName,
        tableName
      );
      dispatch(getTableDefaultDataStreamsMappingSuccess(res.data));
    } catch (err) {
      dispatch(getTableDefaultDataStreamsMappingFailed(err));
    }
  };

export const updateTableDefaultDataStreamsMappingRequest = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_DEFAULT_DATA_STREAMS_MAPPING
});

export const updateTableDefaultDataStreamsSuccess = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_DEFAULT_DATA_STREAMS_MAPPING_SUCCESS
});

export const updateTableDefaultDataStreamsMappingFailed = (error: unknown) => ({
  type: TABLE_ACTION.UPDATE_TABLE_DEFAULT_DATA_STREAMS_MAPPING_ERROR,
  error
});

export const updateTableDefaultDataStreamMapping =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: DataStreamMappingSpec
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableDefaultDataStreamsMappingRequest());
    try {
      await TableApiClient.updateTableDefaultDataStreamsMapping(
        connectionName,
        schemaName,
        tableName,
        data
      );
      dispatch(updateTableDefaultDataStreamsSuccess());
    } catch (err) {
      dispatch(updateTableDefaultDataStreamsMappingFailed(err));
    }
  };

export const getTableDailyRecurringRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_DAILY_RECURRING
});

export const getTableDailyRecurringSuccess = (data: UIAllChecksModel) => ({
  type: TABLE_ACTION.GET_TABLE_DAILY_RECURRING_SUCCESS,
  data
});

export const getTableDailyRecurringFailed = (error: unknown) => ({
  type: TABLE_ACTION.GET_TABLE_DAILY_RECURRING_ERROR,
  error
});

export const getTableDailyRecurring =
  (connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableDailyRecurringRequest());
    try {
      const res = await TableApiClient.getTableRecurringUI(
        connectionName,
        schemaName,
        tableName,
        'daily'
      );
      dispatch(getTableDailyRecurringSuccess(res.data));
    } catch (err) {
      dispatch(getTableDailyRecurringFailed(err));
    }
  };

export const updateTableDailyRecurringRequest = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_DAILY_RECURRING
});

export const updateTableDailyRecurringSuccess = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_DAILY_RECURRING_SUCCESS
});

export const updateTableDailyRecurringFailed = (error: unknown) => ({
  type: TABLE_ACTION.UPDATE_TABLE_DAILY_RECURRING_ERROR,
  error
});

export const updateTableDailyRecurring =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: UIAllChecksModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableDailyRecurringRequest());
    try {
      await TableApiClient.updateTableRecurringUI(
        connectionName,
        schemaName,
        tableName,
        'daily',
        data
      );
      dispatch(updateTableDailyRecurringSuccess());
    } catch (err) {
      dispatch(updateTableDailyRecurringFailed(err));
    }
  };

export const getTableMonthlyRecurringRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_MONTHLY_RECURRING
});

export const getTableMonthlyRecurringSuccess = (data: UIAllChecksModel) => ({
  type: TABLE_ACTION.GET_TABLE_MONTHLY_RECURRING_SUCCESS,
  data
});

export const getTableMonthlyRecurringFailed = (error: unknown) => ({
  type: TABLE_ACTION.GET_TABLE_MONTHLY_RECURRING_ERROR,
  error
});

export const getTableMonthlyRecurring =
  (connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableMonthlyRecurringRequest());
    try {
      const res = await TableApiClient.getTableRecurringUI(
        connectionName,
        schemaName,
        tableName,
        'monthly'
      );
      dispatch(getTableMonthlyRecurringSuccess(res.data));
    } catch (err) {
      dispatch(getTableMonthlyRecurringFailed(err));
    }
  };

export const updateTableMonthlyRecurringRequest = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_MONTHLY_RECURRING
});

export const updateTableMonthlyRecurringSuccess = () => ({
  type: TABLE_ACTION.UPDATE_TABLE_MONTHLY_RECURRING_SUCCESS
});

export const updateTableMonthlyRecurringFailed = (error: unknown) => ({
  type: TABLE_ACTION.UPDATE_TABLE_MONTHLY_RECURRING_ERROR,
  error
});

export const updateTableMonthlyRecurring =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: UIAllChecksModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableMonthlyRecurringRequest());
    try {
      await TableApiClient.updateTableRecurringUI(
        connectionName,
        schemaName,
        tableName,
        'monthly',
        data
      );
      dispatch(updateTableMonthlyRecurringSuccess());
    } catch (err) {
      dispatch(updateTableMonthlyRecurringFailed(err));
    }
  };

export const getTableDailyPartitionedChecksRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_PARTITIONED_DAILY_CHECKS
});

export const getTableDailyPartitionedChecksSuccess = (
  data: UIAllChecksModel
) => ({
  type: TABLE_ACTION.GET_TABLE_PARTITIONED_DAILY_CHECKS_SUCCESS,
  data
});

export const getTableDailyPartitionedChecksFailed = (error: unknown) => ({
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

export const updateTableDailyPartitionedChecksFailed = (error: unknown) => ({
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

export const getTableMonthlyPartitionedChecksSuccess = (
  data: UIAllChecksModel
) => ({
  type: TABLE_ACTION.GET_TABLE_PARTITIONED_MONTHLY_CHECKS_SUCCESS,
  data
});

export const getTableMonthlyPartitionedChecksFailed = (error: unknown) => ({
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

export const updateTableMonthlyPartitionedChecksFailed = (error: unknown) => ({
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

export const setUpdatedTableBasic = (table?: TableBasicModel) => ({
  type: TABLE_ACTION.SET_UPDATED_TABLE_BASIC,
  table
});

export const setUpdatedSchedule = (schedule?: RecurringScheduleSpec) => ({
  type: TABLE_ACTION.SET_UPDATED_SCHEDULE,
  schedule
});
export const setUpdatedSchedulingGroup = (schedulingGroup: CheckRunRecurringScheduleGroup, schedule?: RecurringScheduleSpec) => ({
  type: TABLE_ACTION.SET_UPDATED_SCHEDULE_GROUP,
  schedulingGroup,
  schedule
});
export const setIsUpdatedSchedulingGroup = (schedulingGroup: CheckRunRecurringScheduleGroup, isUpdated: boolean) => ({
  type: TABLE_ACTION.SET_IS_UPDATED_SCHEDULE_GROUP,
  isUpdated,
  schedulingGroup
});

export const setUpdatedComments = (comments?: CommentSpec[]) => ({
  type: TABLE_ACTION.SET_UPDATED_COMMENTS,
  comments
});

export const setIsUpdatedComments = (isUpdated?: boolean) => ({
  type: TABLE_ACTION.SET_IS_UPDATED_COMMENTS,
  isUpdated
});

export const setUpdatedLabels = (labels?: string[]) => ({
  type: TABLE_ACTION.SET_UPDATED_LABELS,
  labels
});

export const setUpdatedChecksUi = (checksUI?: UIAllChecksModel) => ({
  type: TABLE_ACTION.SET_UPDATED_CHECKS_UI,
  checksUI
});

export const setUpdatedDailyRecurring = (checksUI?: UIAllChecksModel) => ({
  type: TABLE_ACTION.SET_TABLE_DAILY_RECURRING,
  checksUI
});

export const setUpdatedMonthlyRecurring = (checksUI?: UIAllChecksModel) => ({
  type: TABLE_ACTION.SET_TABLE_MONTHLY_RECURRING,
  checksUI
});

export const setUpdatedDailyPartitionedChecks = (
  checksUI?: UIAllChecksModel
) => ({
  type: TABLE_ACTION.SET_TABLE_PARTITIONED_DAILY_CHECKS,
  checksUI
});

export const setUpdatedMonthlyPartitionedChecks = (
  checksUI?: UIAllChecksModel
) => ({
  type: TABLE_ACTION.SET_TABLE_PARTITIONED_MONTHLY_CHECKS,
  checksUI
});

export const setUpdatedTableDataStreamsMapping = (
  dataStreamsMapping?: DataStreamMappingSpec
) => ({
  type: TABLE_ACTION.SET_TABLE_DEFAULT_DATA_STREAMS_MAPPING,
  dataStreamsMapping
});

export const getTableProfilingChecksUIFilterRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_PROFILINGS_CHECKS_UI_FILTER
});

export const getTableProfilingChecksUIFilterSuccess = (
  data: UIAllChecksModel
) => ({
  type: TABLE_ACTION.GET_TABLE_PROFILINGS_CHECKS_UI_FILTER_SUCCESS,
  data
});

export const getTableProfilingChecksUIFilterFailed = (error: unknown) => ({
  type: TABLE_ACTION.GET_TABLE_PROFILINGS_CHECKS_UI_FILTER_ERROR,
  error
});

export const getTableProfilingChecksUIFilter =
  (connectionName: string, schemaName: string, tableName: string, category: string, checkName: string) =>
    async (dispatch: Dispatch) => {
      dispatch(getTableProfilingChecksUIFilterRequest());
      try {
        const res = await TableApiClient.getTableProfilingChecksUIFilter(
          connectionName,
          schemaName,
          tableName,
          category,
          checkName
        );
        dispatch(getTableProfilingChecksUIFilterSuccess(res.data));
      } catch (err) {
        dispatch(getTableProfilingChecksUIFilterFailed(err));
      }
    };

export const getTableRecurringUIFilterRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_RECURRING_UI_FILTER
});

export const getTableRecurringUIFilterSuccess = (
  data: UIAllChecksModel
) => ({
  type: TABLE_ACTION.GET_TABLE_RECURRING_UI_FILTER_SUCCESS,
  data
});

export const getTableRecurringUIFilterFailed = (error: unknown) => ({
  type: TABLE_ACTION.GET_TABLE_RECURRING_UI_FILTER_ERROR,
  error
});

export const getTableRecurringUIFilter =
  (connectionName: string, schemaName: string, tableName: string, timePartitioned: 'daily' | 'monthly', category: string, checkName: string) =>
    async (dispatch: Dispatch) => {
      dispatch(getTableRecurringUIFilterRequest());
      try {
        const res = await TableApiClient.getTableRecurringUIFilter(
          connectionName,
          schemaName,
          tableName,
          timePartitioned,
          category,
          checkName
        );
        dispatch(getTableRecurringUIFilterSuccess(res.data));
      } catch (err) {
        dispatch(getTableRecurringUIFilterFailed(err));
      }
    };

export const getTablePartitionedChecksUIFilterRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_PARTITIONED_CHECKS_UI_FILTER
});

export const getTablePartitionedChecksUIFilterSuccess = (
  data: UIAllChecksModel
) => ({
  type: TABLE_ACTION.GET_TABLE_PARTITIONED_CHECKS_UI_FILTER_SUCCESS,
  data
});

export const getTablePartitionedChecksUIFilterFailed = (error: unknown) => ({
  type: TABLE_ACTION.GET_TABLE_PARTITIONED_CHECKS_UI_FILTER_ERROR,
  error
});

export const getTablePartitionedChecksUIFilter =
  (connectionName: string, schemaName: string, tableName: string, timePartitioned: 'daily' | 'monthly', category: string, checkName: string) =>
    async (dispatch: Dispatch) => {
      dispatch(getTablePartitionedChecksUIFilterRequest());
      try {
        const res = await TableApiClient.getTablePartitionedChecksUIFilter(
          connectionName,
          schemaName,
          tableName,
          timePartitioned,
          category,
          checkName
        );
        dispatch(getTablePartitionedChecksUIFilterSuccess(res.data));
      } catch (err) {
        dispatch(getTablePartitionedChecksUIFilterFailed(err));
      }
    };

export const setTableUpdatedCheckUiFilter = (ui: UIAllChecksModel) => ({
  type: TABLE_ACTION.SET_UPDATED_CHECKS_UI_FILTER,
  data: ui
});

export const setTableUpdatedRecurringUIFilter = (ui: UIAllChecksModel) => ({
  type: TABLE_ACTION.SET_UPDATED_RECURRING_UI_FILTER,
  data: ui
});
export const setTableUpdatedPartitionedChecksUiFilter = (ui: UIAllChecksModel) => ({
  type: TABLE_ACTION.SET_UPDATED_PARTITIONED_CHECKS_UI_FILTER,
  data: ui
});
