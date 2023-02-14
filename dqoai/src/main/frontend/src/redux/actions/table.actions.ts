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
  TableAdHocCheckCategoriesSpec,
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
// TODO: getTableChecks -> getTableAdHocChecks. Also getTableCheckpoints and getTablePartitionedChecks for CheckTimePartition
export const getTableChecksRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS
});

export const getTableChecksSuccess = (data: TableAdHocCheckCategoriesSpec) => ({
  type: TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_SUCCESS,
  data
});

export const getTableChecksFailed = (error: unknown) => ({
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

export const getTableChecksUiSuccess = (data: UIAllChecksModel) => ({
  type: TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_UI_SUCCESS,
  data
});

export const getTableChecksUiFailed = (error: unknown) => ({
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

export const updateTableChecksUIFailed = (error: unknown) => ({
  type: TABLE_ACTION.UPDATE_TABLE_DATA_QUALITY_CHECKS_UI_ERROR,
  error
});

export const updateTableAdHocChecksUI =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: UIAllChecksModel
  ) =>
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

export const getTableDailyCheckpointsRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_DAILY_CHECKPOINTS
});

export const getTableDailyCheckpointsSuccess = (data: UIAllChecksModel) => ({
  type: TABLE_ACTION.GET_TABLE_DAILY_CHECKPOINTS_SUCCESS,
  data
});

export const getTableDailyCheckpointsFailed = (error: unknown) => ({
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

export const updateTableDailyCheckpointsFailed = (error: unknown) => ({
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

export const getTableMonthlyCheckpointsSuccess = (data: UIAllChecksModel) => ({
  type: TABLE_ACTION.GET_TABLE_MONTHLY_CHECKPOINTS_SUCCESS,
  data
});

export const getTableMonthlyCheckpointsFailed = (error: unknown) => ({
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

export const updateTableMonthlyCheckpointsFailed = (error: unknown) => ({
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

export const setUpdatedDailyCheckPoints = (checksUI?: UIAllChecksModel) => ({
  type: TABLE_ACTION.SET_TABLE_DAILY_CHECKPOINTS,
  checksUI
});

export const setUpdatedMonthlyCheckPoints = (checksUI?: UIAllChecksModel) => ({
  type: TABLE_ACTION.SET_TABLE_MONTHLY_CHECKPOINTS,
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

export const getTableAdHockChecksUIFilterRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_ADHOCS_CHECKS_UI_FILTER
});

export const getTableAdHockChecksUIFilterSuccess = (
  data: UIAllChecksModel
) => ({
  type: TABLE_ACTION.GET_TABLE_ADHOCS_CHECKS_UI_FILTER_SUCCESS,
  data
});

export const getTableAdHockChecksUIFilterFailed = (error: unknown) => ({
  type: TABLE_ACTION.GET_TABLE_ADHOCS_CHECKS_UI_FILTER_ERROR,
  error
});

export const getTableAdHockChecksUIFilter =
  (connectionName: string, schemaName: string, tableName: string, category: string, checkName: string) =>
    async (dispatch: Dispatch) => {
      dispatch(getTableAdHockChecksUIFilterRequest());
      try {
        const res = await TableApiClient.getTableAdHocChecksUIFilter(
          connectionName,
          schemaName,
          tableName,
          category,
          checkName
        );
        dispatch(getTableAdHockChecksUIFilterSuccess(res.data));
      } catch (err) {
        dispatch(getTableAdHockChecksUIFilterFailed(err));
      }
    };

export const getTableCheckpointsUIFilterRequest = () => ({
  type: TABLE_ACTION.GET_TABLE_CHECKPOINTS_UI_FILTER
});

export const getTableCheckpointsUIFilterSuccess = (
  data: UIAllChecksModel
) => ({
  type: TABLE_ACTION.GET_TABLE_CHECKPOINTS_UI_FILTER_SUCCESS,
  data
});

export const getTableCheckpointsUIFilterFailed = (error: unknown) => ({
  type: TABLE_ACTION.GET_TABLE_CHECKPOINTS_UI_FILTER_ERROR,
  error
});

export const getTableCheckpointsUIFilter =
  (connectionName: string, schemaName: string, tableName: string, timePartitioned: 'daily' | 'monthly', category: string, checkName: string) =>
    async (dispatch: Dispatch) => {
      dispatch(getTableCheckpointsUIFilterRequest());
      try {
        const res = await TableApiClient.getTableCheckpointsUIFilter(
          connectionName,
          schemaName,
          tableName,
          timePartitioned,
          category,
          checkName
        );
        dispatch(getTableCheckpointsUIFilterSuccess(res.data));
      } catch (err) {
        dispatch(getTableCheckpointsUIFilterFailed(err));
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
