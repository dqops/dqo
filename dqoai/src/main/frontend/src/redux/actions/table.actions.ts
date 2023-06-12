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
import { SOURCE_ACTION } from '../types';
import {
  CommentSpec,
  DataStreamMappingSpec,
  RecurringScheduleSpec,
  TableProfilingCheckCategoriesSpec,
  TableBasicModel,
  CheckContainerModel, TablePartitioningModel
} from '../../api';
import { CheckRunRecurringScheduleGroup } from "../../shared/enums/scheduling.enum";
import { CheckTypes } from "../../shared/routes";

export const getTableBasicRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_BASIC,
  checkType, activeTab,
});

export const getTableBasicSuccess = (checkType: CheckTypes, activeTab: string, data: TableBasicModel) => ({
  type: SOURCE_ACTION.GET_TABLE_BASIC_SUCCESS,
  checkType, activeTab,
  data
});

export const getTableBasicFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_BASIC_ERROR,
  error
});

export const getTableBasic =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableBasicRequest(checkType, activeTab));
    try {
      const res = await TableApiClient.getTableBasic(
        connectionName,
        schemaName,
        tableName
      );
      dispatch(getTableBasicSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getTableBasicFailed(err));
    }
  };

export const updateTableBasicRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_BASIC,
  checkType, activeTab,
});

export const updateTableBasicSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_BASIC_SUCCESS,
  checkType, activeTab,
});

export const updateTableBasicFailed = (error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_BASIC_ERROR,
  error
});

export const updateTableBasic =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: TableBasicModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableBasicRequest(checkType, activeTab));
    try {
      await TableApiClient.updateTableBasic(
        connectionName,
        schemaName,
        tableName,
        data
      );
      dispatch(updateTableBasicSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateTableBasicFailed(err));
    }
  };

export const resetTableSchedulingGroup = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.RESET_TABLE_SCHEDULE_GROUP,
  checkType, activeTab,
});

export const getTableSchedulingGroupRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_SCHEDULE_GROUP,
  checkType, activeTab,
});

export const getTableSchedulingGroupSuccess = (checkType: CheckTypes, activeTab: string, schedulingGroup: CheckRunRecurringScheduleGroup, data: RecurringScheduleSpec) => ({
  type: SOURCE_ACTION.GET_TABLE_SCHEDULE_GROUP_SUCCESS,
  checkType, activeTab,
  schedulingGroup,
  data
});

export const getTableSchedulingGroupFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_SCHEDULE_GROUP_ERROR,
  error
});

export const getTableSchedulingGroup =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, schedulingGroup: CheckRunRecurringScheduleGroup) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableSchedulingGroupRequest(checkType, activeTab));
    try {
      const res = await TableApiClient.getTableSchedulingGroupOverride(
        connectionName,
        schemaName,
        tableName,
        schedulingGroup
      );
      dispatch(getTableSchedulingGroupSuccess(checkType, activeTab, schedulingGroup, res.data));
    } catch (err) {
      dispatch(getTableSchedulingGroupFailed(err));
    }
  };

export const updateTableSchedulingGroupRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_SCHEDULE_GROUP,
  checkType, activeTab,
});

export const updateTableSchedulingGroupSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_SCHEDULE_GROUP_SUCCESS,
  checkType, activeTab,
});

export const updateTableSchedulingGroupFailed = (error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_SCHEDULE_GROUP_ERROR,
  error
});

export const updateTableSchedulingGroup =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    schedulingGroup: CheckRunRecurringScheduleGroup,
    data: RecurringScheduleSpec
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableSchedulingGroupRequest(checkType, activeTab));
    try {
      await TableApiClient.updateTableSchedulingGroupOverride (
        connectionName,
        schemaName,
        tableName,
        schedulingGroup,
        data
      );
      dispatch(updateTableSchedulingGroupSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateTableSchedulingGroupFailed(err));
    }
  };

export const getTableCommentsRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_COMMENTS,
  checkType, activeTab,
});

export const getTableCommentsSuccess = (checkType: CheckTypes, activeTab: string, data: CommentSpec[]) => ({
  type: SOURCE_ACTION.GET_TABLE_COMMENTS_SUCCESS,
  checkType, activeTab,
  data
});

export const getTableCommentsFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_COMMENTS_ERROR,
  error
});

export const getTableComments =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, loading = true) =>
  async (dispatch: Dispatch) => {
    if (loading) {
      dispatch(getTableCommentsRequest(checkType, activeTab));
    }
    try {
      const res = await TableApiClient.getTableComments(
        connectionName,
        schemaName,
        tableName
      );
      dispatch(getTableCommentsSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getTableCommentsFailed(err));
    }
  };

export const updateTableCommentsRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_COMMENTS,
  checkType, activeTab,
});

export const updateTableCommentsSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_COMMENTS_SUCCESS,
  checkType, activeTab,
});

export const updateTableCommentsFailed = (error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_COMMENTS_ERROR,
  error
});

export const updateTableComments =
  (
    checkType: CheckTypes, activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: CommentSpec[]
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableCommentsRequest(checkType, activeTab));
    try {
      await TableApiClient.updateTableComments(
        connectionName,
        schemaName,
        tableName,
        data
      );
      dispatch(updateTableCommentsSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateTableCommentsFailed(err));
    }
  };

export const getTableLabelsRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_LABELS,
  checkType, activeTab,
});

export const getTableLabelsSuccess = (checkType: CheckTypes, activeTab: string, data: string[]) => ({
  type: SOURCE_ACTION.GET_TABLE_LABELS_SUCCESS,
  checkType, activeTab,
  data
});

export const getTableLabelsFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_LABELS_ERROR,
  error
});

export const getTableLabels =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, loading = true) =>
  async (dispatch: Dispatch) => {
    if (loading) {
      dispatch(getTableLabelsRequest(checkType, activeTab));
    }
    try {
      const res = await TableApiClient.getTableLabels(
        connectionName,
        schemaName,
        tableName
      );
      dispatch(getTableLabelsSuccess(checkType, activeTab,res.data || []));
    } catch (err) {
      dispatch(getTableLabelsFailed(err));
    }
  };

export const updateTableLabelsRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_LABELS,
  checkType, activeTab,
});

export const updateTableLabelsSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_LABELS_SUCCESS,
  checkType, activeTab,
});

export const updateTableLabelsFailed = (error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_LABELS_ERROR,
  error
});

export const updateTableLabels =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: string[]
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableLabelsRequest(checkType, activeTab));
    try {
      await TableApiClient.updateTableLabels(
        connectionName,
        schemaName,
        tableName,
        data
      );
      dispatch(updateTableLabelsSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateTableLabelsFailed(err));
    }
  };
// TODO: getTableChecks -> getTableProfilingChecks. Also getTableRecurring and getTablePartitionedChecks for CheckTimePartition
export const getTableChecksRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS,
  checkType, activeTab,
});

export const getTableChecksSuccess = (checkType: CheckTypes, activeTab: string, data: TableProfilingCheckCategoriesSpec) => ({
  type: SOURCE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_SUCCESS,
  checkType, activeTab,
  data
});

export const getTableChecksFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_ERROR,
  error
});

export const getTableProfilingChecks =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableChecksRequest(checkType, activeTab));
    try {
      const res = await TableApiClient.getTableProfilingChecks(
        connectionName,
        schemaName,
        tableName
      );
      dispatch(getTableChecksSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getTableChecksFailed(err));
    }
  };

export const getTableChecksUiRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_UI,
  checkType, activeTab,
});

export const getTableChecksUiSuccess = (checkType: CheckTypes, activeTab: string, data: CheckContainerModel) => ({
  type: SOURCE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_UI_SUCCESS,
  checkType, activeTab,
  data
});

export const getTableChecksUiFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_UI_ERROR,
  error
});

export const getTableProfilingChecksUI =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, loading = true) =>
  async (dispatch: Dispatch) => {
    if (loading) {
      dispatch(getTableChecksUiRequest(checkType, activeTab));
    }
    try {
      const res = await TableApiClient.getTableProfilingChecksModel(
        connectionName,
        schemaName,
        tableName
      );
      dispatch(getTableChecksUiSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getTableChecksUiFailed(err));
    }
  };

export const updateTableChecksUIRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_DATA_QUALITY_CHECKS_UI,
  checkType, activeTab,
});

export const updateTableChecksUISuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_DATA_QUALITY_CHECKS_UI_SUCCESS,
  checkType, activeTab,
});

export const updateTableChecksUIFailed = (error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_DATA_QUALITY_CHECKS_UI_ERROR,
  error
});

export const updateTableProfilingChecksUI =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: CheckContainerModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableChecksUIRequest(checkType, activeTab));
    try {
      await TableApiClient.updateTableProfilingChecksModel(
        connectionName,
        schemaName,
        tableName,
        data
      );
      dispatch(updateTableChecksUISuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateTableChecksUIFailed(err));
    }
  };

export const getTableDefaultDataStreamsMappingRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_DEFAULT_DATA_STREAMS_MAPPING,
  checkType, activeTab,
});

export const getTableDefaultDataStreamsMappingSuccess = (
  checkType: CheckTypes, activeTab: string, data: DataStreamMappingSpec
) => ({
  type: SOURCE_ACTION.GET_TABLE_DEFAULT_DATA_STREAMS_MAPPING_SUCCESS,
  checkType, activeTab,
  data
});

export const getTableDefaultDataStreamsMappingFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_DEFAULT_DATA_STREAMS_MAPPING_ERROR,
  error
});

export const getTableDefaultDataStreamMapping =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableDefaultDataStreamsMappingRequest(checkType, activeTab));
    try {
      const res = await TableApiClient.getTableDefaultDataStreamsMapping(
        connectionName,
        schemaName,
        tableName
      );
      dispatch(getTableDefaultDataStreamsMappingSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getTableDefaultDataStreamsMappingFailed(err));
    }
  };

export const updateTableDefaultDataStreamsMappingRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_DEFAULT_DATA_STREAMS_MAPPING,
  checkType, activeTab,
});

export const updateTableDefaultDataStreamsSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_DEFAULT_DATA_STREAMS_MAPPING_SUCCESS,
  checkType, activeTab,
});

export const updateTableDefaultDataStreamsMappingFailed = (error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_DEFAULT_DATA_STREAMS_MAPPING_ERROR,
  error
});

export const updateTableDefaultDataStreamMapping =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: DataStreamMappingSpec
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableDefaultDataStreamsMappingRequest(checkType, activeTab));
    try {
      await TableApiClient.updateTableDefaultDataStreamsMapping(
        connectionName,
        schemaName,
        tableName,
        data
      );
      dispatch(updateTableDefaultDataStreamsSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateTableDefaultDataStreamsMappingFailed(err));
    }
  };

export const getTableDailyRecurringRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_DAILY_RECURRING,
  checkType, activeTab,
});

export const getTableDailyRecurringSuccess = (checkType: CheckTypes, activeTab: string, data: CheckContainerModel) => ({
  type: SOURCE_ACTION.GET_TABLE_DAILY_RECURRING_SUCCESS,
  checkType, activeTab,
  data
});

export const getTableDailyRecurringFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_DAILY_RECURRING_ERROR,
  error
});

export const getTableDailyRecurring =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, loading = true) =>
  async (dispatch: Dispatch) => {
    if (loading) {
      dispatch(getTableDailyRecurringRequest(checkType, activeTab));
    }
    try {
      const res = await TableApiClient.getTableRecurringChecksModel(
        connectionName,
        schemaName,
        tableName,
        'daily'
      );
      dispatch(getTableDailyRecurringSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getTableDailyRecurringFailed(err));
    }
  };

export const updateTableDailyRecurringRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_DAILY_RECURRING,
  checkType, activeTab
});

export const updateTableDailyRecurringSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_DAILY_RECURRING_SUCCESS,
  checkType, activeTab
});

export const updateTableDailyRecurringFailed = (error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_DAILY_RECURRING_ERROR,
  error
});

export const updateTableDailyRecurring =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: CheckContainerModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableDailyRecurringRequest(checkType, activeTab));
    try {
      await TableApiClient.updateTableRecurringChecksModel(
        connectionName,
        schemaName,
        tableName,
        'daily',
        data
      );
      dispatch(updateTableDailyRecurringSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateTableDailyRecurringFailed(err));
    }
  };

export const getTableMonthlyRecurringRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_MONTHLY_RECURRING,
  checkType, activeTab
});

export const getTableMonthlyRecurringSuccess = (checkType: CheckTypes, activeTab: string, data: CheckContainerModel) => ({
  type: SOURCE_ACTION.GET_TABLE_MONTHLY_RECURRING_SUCCESS,
  data,
  checkType, activeTab
});

export const getTableMonthlyRecurringFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_MONTHLY_RECURRING_ERROR,
  error
});

export const getTableMonthlyRecurring =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, loading = true) =>
  async (dispatch: Dispatch) => {
    if (loading) {
      dispatch(getTableMonthlyRecurringRequest(checkType, activeTab));
    }
    try {
      const res = await TableApiClient.getTableRecurringChecksModel(
        connectionName,
        schemaName,
        tableName,
        'monthly'
      );
      dispatch(getTableMonthlyRecurringSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getTableMonthlyRecurringFailed(err));
    }
  };

export const updateTableMonthlyRecurringRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_MONTHLY_RECURRING,
  checkType,
  activeTab
});

export const updateTableMonthlyRecurringSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_MONTHLY_RECURRING_SUCCESS,
  checkType,
  activeTab
});

export const updateTableMonthlyRecurringFailed = (error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_MONTHLY_RECURRING_ERROR,
  error
});

export const updateTableMonthlyRecurring =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: CheckContainerModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableMonthlyRecurringRequest(checkType, activeTab));
    try {
      await TableApiClient.updateTableRecurringChecksModel(
        connectionName,
        schemaName,
        tableName,
        'monthly',
        data
      );
      dispatch(updateTableMonthlyRecurringSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateTableMonthlyRecurringFailed(err));
    }
  };

export const getTableDailyPartitionedChecksRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_PARTITIONED_DAILY_CHECKS,
  checkType,
  activeTab
});

export const getTableDailyPartitionedChecksSuccess = (
  checkType: CheckTypes, activeTab: string, data: CheckContainerModel
) => ({
  type: SOURCE_ACTION.GET_TABLE_PARTITIONED_DAILY_CHECKS_SUCCESS,
  data,
  checkType,
  activeTab
});

export const getTableDailyPartitionedChecksFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_PARTITIONED_DAILY_CHECKS_ERROR,
  error
});

export const getTableDailyPartitionedChecks =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, loading = true) =>
  async (dispatch: Dispatch) => {
    if (loading) {
      dispatch(getTableDailyPartitionedChecksRequest(checkType, activeTab));
    }
    try {
      const res = await TableApiClient.getTablePartitionedChecksModel(
        connectionName,
        schemaName,
        tableName,
        'daily'
      );
      dispatch(getTableDailyPartitionedChecksSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getTableDailyPartitionedChecksFailed(err));
    }
  };

export const updateTableDailyPartitionedChecksRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_PARTITIONED_DAILY_CHECKS,
  checkType,
  activeTab
});

export const updateTableDailyPartitionedChecksSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_PARTITIONED_DAILY_CHECKS_SUCCESS,
  checkType,
  activeTab
});

export const updateTableDailyPartitionedChecksFailed = (error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_PARTITIONED_DAILY_CHECKS_ERROR,
  error
});

export const updateTableDailyPartitionedChecks =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: CheckContainerModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableDailyPartitionedChecksRequest(checkType, activeTab));
    try {
      await TableApiClient.updateTablePartitionedChecksModel(
        connectionName,
        schemaName,
        tableName,
        'daily',
        data
      );
      dispatch(updateTableDailyPartitionedChecksSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateTableDailyPartitionedChecksFailed(err));
    }
  };

export const getTableMonthlyPartitionedChecksRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_PARTITIONED_MONTHLY_CHECKS,
  checkType,
  activeTab
});

export const getTableMonthlyPartitionedChecksSuccess = (
  checkType: CheckTypes, activeTab: string, data: CheckContainerModel
) => ({
  type: SOURCE_ACTION.GET_TABLE_PARTITIONED_MONTHLY_CHECKS_SUCCESS,
  data,
  checkType,
  activeTab
});

export const getTableMonthlyPartitionedChecksFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_PARTITIONED_MONTHLY_CHECKS_ERROR,
  error
});

export const getTableMonthlyPartitionedChecks =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, loading = true) =>
  async (dispatch: Dispatch) => {
    if (loading) {
      dispatch(getTableMonthlyPartitionedChecksRequest(checkType, activeTab));
    }
    try {
      const res = await TableApiClient.getTablePartitionedChecksModel(
        connectionName,
        schemaName,
        tableName,
        'monthly'
      );
      dispatch(getTableMonthlyPartitionedChecksSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getTableMonthlyPartitionedChecksFailed(err));
    }
  };

export const updateTableMonthlyPartitionedChecksRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_PARTITIONED_MONTHLY_CHECKS,
  checkType,
  activeTab
});

export const updateTableMonthlyPartitionedChecksSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_PARTITIONED_MONTHLY_CHECKS_SUCCESS,
  checkType,
  activeTab
});

export const updateTableMonthlyPartitionedChecksFailed = (error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_PARTITIONED_MONTHLY_CHECKS_ERROR,
  error
});

export const updateTableMonthlyPartitionedChecks =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: CheckContainerModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableMonthlyPartitionedChecksRequest(checkType, activeTab));
    try {
      await TableApiClient.updateTablePartitionedChecksModel(
        connectionName,
        schemaName,
        tableName,
        'monthly',
        data
      );
      dispatch(updateTableMonthlyPartitionedChecksSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateTableMonthlyPartitionedChecksFailed(err));
    }
  };

export const setUpdatedTableBasic = (checkType: CheckTypes, activeTab: string, table?: TableBasicModel) => ({
  type: SOURCE_ACTION.SET_UPDATED_TABLE_BASIC,
  data: table,
  checkType,
  activeTab
});

export const setUpdatedSchedule = (checkType: CheckTypes, activeTab: string, schedule?: RecurringScheduleSpec) => ({
  type: SOURCE_ACTION.SET_UPDATED_SCHEDULE,
  data: schedule,
  checkType,
  activeTab
});
export const setUpdatedSchedulingGroup = (checkType: CheckTypes, activeTab: string, schedulingGroup: CheckRunRecurringScheduleGroup, schedule?: RecurringScheduleSpec) => ({
  type: SOURCE_ACTION.SET_UPDATED_SCHEDULE_GROUP,
  data: {
    schedulingGroup,
    schedule,
  },
  checkType,
  activeTab
});
export const setIsUpdatedSchedulingGroup = (checkType: CheckTypes, activeTab: string, schedulingGroup: CheckRunRecurringScheduleGroup, isUpdated: boolean) => ({
  type: SOURCE_ACTION.SET_IS_UPDATED_SCHEDULE_GROUP,
  data: {
    isUpdated,
    schedulingGroup,
  },
  checkType,
  activeTab
});

export const setUpdatedComments = (checkType: CheckTypes, activeTab: string, comments?: CommentSpec[]) => ({
  type: SOURCE_ACTION.SET_UPDATED_COMMENTS,
  data: comments,
  checkType,
  activeTab
});

export const setIsUpdatedComments = (checkType: CheckTypes, activeTab: string, isUpdated?: boolean) => ({
  type: SOURCE_ACTION.SET_IS_UPDATED_COMMENTS,
  data: isUpdated,
  checkType,
  activeTab
});

export const setUpdatedLabels = (checkType: CheckTypes, activeTab: string, labels?: string[]) => ({
  type: SOURCE_ACTION.SET_UPDATED_LABELS,
  data: labels,
  checkType,
  activeTab
});

export const setUpdatedChecksUi = (checkType: CheckTypes, activeTab: string, checksUI?: CheckContainerModel) => ({
  type: SOURCE_ACTION.SET_UPDATED_CHECKS_UI,
  data: checksUI,
  checkType,
  activeTab
});

export const setUpdatedDailyRecurring = (checkType: CheckTypes, activeTab: string, checksUI?: CheckContainerModel) => ({
  type: SOURCE_ACTION.SET_TABLE_DAILY_RECURRING,
  data: checksUI,
  checkType,
  activeTab
});

export const setUpdatedMonthlyRecurring = (checkType: CheckTypes, activeTab: string, checksUI?: CheckContainerModel) => ({
  type: SOURCE_ACTION.SET_TABLE_MONTHLY_RECURRING,
  data: checksUI,
  checkType,
  activeTab
});

export const setUpdatedDailyPartitionedChecks = (
  checkType: CheckTypes, activeTab: string, checksUI?: CheckContainerModel
) => ({
  type: SOURCE_ACTION.SET_TABLE_PARTITIONED_DAILY_CHECKS,
  data: checksUI,
  checkType,
  activeTab
});

export const setUpdatedMonthlyPartitionedChecks = (
  checkType: CheckTypes, activeTab: string, checksUI?: CheckContainerModel
) => ({
  type: SOURCE_ACTION.SET_TABLE_PARTITIONED_MONTHLY_CHECKS,
  data: checksUI,
  checkType,
  activeTab
});

export const setUpdatedTableDataStreamsMapping = (
  checkType: CheckTypes, activeTab: string, dataStreamsMapping?: DataStreamMappingSpec
) => ({
  type: SOURCE_ACTION.SET_TABLE_DEFAULT_DATA_STREAMS_MAPPING,
  data: dataStreamsMapping,
  checkType,
  activeTab,
});

export const getTableProfilingChecksUIFilterRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_PROFILINGS_CHECKS_UI_FILTER,
  checkType,
  activeTab
});

export const getTableProfilingChecksUIFilterSuccess = (
  checkType: CheckTypes, activeTab: string, data: CheckContainerModel
) => ({
  type: SOURCE_ACTION.GET_TABLE_PROFILINGS_CHECKS_UI_FILTER_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getTableProfilingChecksUIFilterFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_PROFILINGS_CHECKS_UI_FILTER_ERROR,
  error
});

export const getTableProfilingChecksUIFilter =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, category: string, checkName: string, loading = true) =>
    async (dispatch: Dispatch) => {
      if (loading) {
        dispatch(getTableProfilingChecksUIFilterRequest(checkType, activeTab));
      }
      try {
        const res = await TableApiClient.getTableProfilingChecksModelFilter(
          connectionName,
          schemaName,
          tableName,
          category,
          checkName
        );
        dispatch(getTableProfilingChecksUIFilterSuccess(checkType, activeTab, res.data));
      } catch (err) {
        dispatch(getTableProfilingChecksUIFilterFailed(err));
      }
    };

export const getTableRecurringChecksUIFilterRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_RECURRING_UI_FILTER,
  checkType,
  activeTab
});

export const getTableRecurringChecksUIFilterSuccess = (
  checkType: CheckTypes, activeTab: string, data: CheckContainerModel
) => ({
  type: SOURCE_ACTION.GET_TABLE_RECURRING_UI_FILTER_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getTableRecurringChecksUIFilterFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_RECURRING_UI_FILTER_ERROR,
  error
});

export const getTableRecurringChecksUIFilter =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, timePartitioned: 'daily' | 'monthly', category: string, checkName: string, loading = true) =>
    async (dispatch: Dispatch) => {
      if (loading) {
        dispatch(getTableRecurringChecksUIFilterRequest(checkType, activeTab));
      }
      try {
        const res = await TableApiClient.getTableRecurringChecksModelFilter(
          connectionName,
          schemaName,
          tableName,
          timePartitioned,
          category,
          checkName
        );
        dispatch(getTableRecurringChecksUIFilterSuccess(checkType, activeTab, res.data));
      } catch (err) {
        dispatch(getTableRecurringChecksUIFilterFailed(err));
      }
    };

export const getTablePartitionedChecksUIFilterRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_PARTITIONED_CHECKS_UI_FILTER,
  checkType,
  activeTab
});

export const getTablePartitionedChecksUIFilterSuccess = (
  checkType: CheckTypes, activeTab: string, data: CheckContainerModel
) => ({
  type: SOURCE_ACTION.GET_TABLE_PARTITIONED_CHECKS_UI_FILTER_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getTablePartitionedChecksUIFilterFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_PARTITIONED_CHECKS_UI_FILTER_ERROR,
  error
});

export const getTablePartitionedChecksUIFilter =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, timePartitioned: 'daily' | 'monthly', category: string, checkName: string, loading = true) =>
    async (dispatch: Dispatch) => {
      if (loading) {
        dispatch(getTablePartitionedChecksUIFilterRequest(checkType, activeTab));
      }
      try {
        const res = await TableApiClient.getTablePartitionedChecksModelFilter(
          connectionName,
          schemaName,
          tableName,
          timePartitioned,
          category,
          checkName
        );
        dispatch(getTablePartitionedChecksUIFilterSuccess(checkType, activeTab, res.data));
      } catch (err) {
        dispatch(getTablePartitionedChecksUIFilterFailed(err));
      }
    };

export const setTableUpdatedCheckUiFilter = (checkType: CheckTypes, activeTab: string, ui: CheckContainerModel) => ({
  type: SOURCE_ACTION.SET_UPDATED_CHECKS_UI_FILTER,
  checkType,
  activeTab,
  data: ui
});

export const setTableUpdatedRecurringChecksUIFilter = (checkType: CheckTypes, activeTab: string, ui: CheckContainerModel) => ({
  type: SOURCE_ACTION.SET_UPDATED_RECURRING_UI_FILTER,
  checkType,
  activeTab,
  data: ui
});
export const setTableUpdatedPartitionedChecksUiFilter = (checkType: CheckTypes, activeTab: string, ui: CheckContainerModel) => ({
  type: SOURCE_ACTION.SET_UPDATED_PARTITIONED_CHECKS_UI_FILTER,
  checkType,
  activeTab,
  data: ui
});

export const getTableTimestampsRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_TIME_STAMPS,
  checkType,
  activeTab
});

export const getTableTimestampsSuccess = (checkType: CheckTypes, activeTab: string, data: TablePartitioningModel) => ({
  type: SOURCE_ACTION.GET_TABLE_TIME_STAMPS_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getTableTimestampsFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_TIME_STAMPS_ERROR,
  error
});

export const getTableTimestamps =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string) =>
    async (dispatch: Dispatch) => {
      dispatch(getTableTimestampsRequest(checkType, activeTab));
      try {
        const res = await TableApiClient.getTablePartitioning(
          connectionName,
          schemaName,
          tableName
        );
        dispatch(getTableTimestampsSuccess(checkType, activeTab, res.data));
      } catch (err) {
        dispatch(getTableTimestampsFailed(err));
      }
    };

export const updateTableTimestampsRequest = (checkType: CheckTypes, activeTab: string, ) => ({
  type: SOURCE_ACTION.GET_TABLE_TIME_STAMPS,
  checkType,
  activeTab
});

export const updateTableTimestampsSuccess = (checkType: CheckTypes, activeTab: string, data: TablePartitioningModel) => ({
  type: SOURCE_ACTION.GET_TABLE_TIME_STAMPS_SUCCESS,
  checkType,
  activeTab,
  data
});

export const updateTableTimestampsFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_TIME_STAMPS_ERROR,
  error
});

export const updateTableTimestamps =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, data: TablePartitioningModel) =>
    async (dispatch: Dispatch) => {
      dispatch(updateTableTimestampsRequest(checkType, activeTab));
      try {
        const res = await TableApiClient.updateTablePartitioning(
          connectionName,
          schemaName,
          tableName,
          data
        );
        dispatch(updateTableTimestampsSuccess(checkType, activeTab, res.data));
      } catch (err) {
        dispatch(updateTableTimestampsFailed(err));
      }
    };

export const setUpdatedTablePartitioning = (checkType: CheckTypes, activeTab: string, data: TablePartitioningModel) => ({
  type: SOURCE_ACTION.SET_UPDATED_TABLE_TIME_STAMPS,
  checkType,
  activeTab,
  data
});