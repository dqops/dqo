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

import { TableApiClient } from '../../services/apiClient';
import { SOURCE_ACTION } from '../types';
import {
  CommentSpec,
  DataGroupingConfigurationSpec,
  MonitoringScheduleSpec,
  TableProfilingCheckCategoriesSpec,
  TableListModel,
  CheckContainerModel, TablePartitioningModel
} from '../../api';
import { CheckRunMonitoringScheduleGroup } from "../../shared/enums/scheduling.enum";
import { CheckTypes } from "../../shared/routes";

export const getTableBasicRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_BASIC,
  checkType, activeTab,
});

export const getTableBasicSuccess = (checkType: CheckTypes, activeTab: string, data: TableListModel) => ({
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
    data: TableListModel
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

export const getTableSchedulingGroupSuccess = (checkType: CheckTypes, activeTab: string, schedulingGroup: CheckRunMonitoringScheduleGroup, data: MonitoringScheduleSpec) => ({
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
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, schedulingGroup: CheckRunMonitoringScheduleGroup) =>
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
    schedulingGroup: CheckRunMonitoringScheduleGroup,
    data: MonitoringScheduleSpec
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

export const getTableProfilingChecksRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_PROFILING_CHECKS,
  checkType, activeTab,
});

export const getTableProfilingChecksSuccess = (checkType: CheckTypes, activeTab: string, data: TableProfilingCheckCategoriesSpec) => ({
  type: SOURCE_ACTION.GET_TABLE_PROFILING_CHECKS_SUCCESS,
  checkType, activeTab,
  data
});

export const getTableProfilingChecksFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_PROFILING_CHECKS_ERROR,
  error
});

export const getTableProfilingChecks =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableProfilingChecksRequest(checkType, activeTab));
    try {
      const res = await TableApiClient.getTableProfilingChecks(
        connectionName,
        schemaName,
        tableName
      );
      dispatch(getTableProfilingChecksSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getTableProfilingChecksFailed(err));
    }
  };

export const getTableProfilingChecksModelRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_PROFILING_CHECKS_MODEL,
  checkType, activeTab,
});

export const getTableProfilingChecksModelSuccess = (checkType: CheckTypes, activeTab: string, data: CheckContainerModel) => ({
  type: SOURCE_ACTION.GET_TABLE_PROFILING_CHECKS_MODEL_SUCCESS,
  checkType, activeTab,
  data
});

export const getTableProfilingChecksModelFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_PROFILING_CHECKS_MODEL_ERROR,
  error
});

export const getTableProfilingChecksModel =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, loading = true) =>
  async (dispatch: Dispatch) => {
    if (loading) {
      dispatch(getTableProfilingChecksModelRequest(checkType, activeTab));
    }
    try {
      const res = await TableApiClient.getTableProfilingChecksModel(
        connectionName,
        schemaName,
        tableName
      );
      dispatch(getTableProfilingChecksModelSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getTableProfilingChecksModelFailed(err));
    }
  };

export const updateTableProfilingChecksModelRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_PROFILING_CHECKS_MODEL,
  checkType, activeTab,
});

export const updateTableProfilingChecksModelSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_PROFILING_CHECKS_MODEL_SUCCESS,
  checkType, activeTab,
});

export const updateTableProfilingChecksModelFailed = (error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_PROFILING_CHECKS_MODEL_ERROR,
  error
});

export const updateTableProfilingChecksModel =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: CheckContainerModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableProfilingChecksModelRequest(checkType, activeTab));
    try {
      await TableApiClient.updateTableProfilingChecksModel(
        connectionName,
        schemaName,
        tableName,
        data
      );
      dispatch(updateTableProfilingChecksModelSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateTableProfilingChecksModelFailed(err));
    }
  };

export const getTableDefaultDataGroupingConfigurationRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_DEFAULT_DATA_GROUPING_CONFIGURATION,
  checkType, activeTab,
});

export const getTableDefaultDataGroupingConfigurationSuccess = (
  checkType: CheckTypes, activeTab: string, data: DataGroupingConfigurationSpec
) => ({
  type: SOURCE_ACTION.GET_TABLE_DEFAULT_DATA_GROUPING_CONFIGURATION_SUCCESS,
  checkType, activeTab,
  data
});

export const getTableDefaultDataGroupingConfigurationFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_DEFAULT_DATA_GROUPING_CONFIGURATION_ERROR,
  error
});

export const getTableDefaultDataGroupingConfiguration =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableDefaultDataGroupingConfigurationRequest(checkType, activeTab));
    try {
      const res = await TableApiClient.getTableDefaultGroupingConfiguration(
        connectionName,
        schemaName,
        tableName
      );
      dispatch(getTableDefaultDataGroupingConfigurationSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getTableDefaultDataGroupingConfigurationFailed(err));
    }
  };

export const updateTableDefaultDataGroupingConfigurationRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_DEFAULT_DATA_GROUPING_CONFIGURATION,
  checkType, activeTab,
});

export const updateTableDefaultDataGroupingConfigurationSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_DEFAULT_DATA_GROUPING_CONFIGURATION_SUCCESS,
  checkType, activeTab,
});

export const updateTableDefaultDataGroupingConfigurationFailed = (error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_DEFAULT_DATA_GROUPING_CONFIGURATION_ERROR,
  error
});

export const updateTableDefaultDataGroupingConfiguration =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: DataGroupingConfigurationSpec
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableDefaultDataGroupingConfigurationRequest(checkType, activeTab));
    try {
      await TableApiClient.updateTableDefaultGroupingConfiguration(
        connectionName,
        schemaName,
        tableName,
        data
      );
      dispatch(updateTableDefaultDataGroupingConfigurationSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateTableDefaultDataGroupingConfigurationFailed(err));
    }
  };

export const getTableDailyMonitoringChecksRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_DAILY_MONITORING_CHECKS,
  checkType, activeTab,
});

export const getTableDailyMonitoringChecksSuccess = (checkType: CheckTypes, activeTab: string, data: CheckContainerModel) => ({
  type: SOURCE_ACTION.GET_TABLE_DAILY_MONITORING_CHECKS_SUCCESS,
  checkType, activeTab,
  data
});

export const getTableDailyMonitoringChecksFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_DAILY_MONITORING_CHECKS_ERROR,
  error
});

export const getTableDailyMonitoringChecks =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, loading = true) =>
  async (dispatch: Dispatch) => {
    if (loading) {
      dispatch(getTableDailyMonitoringChecksRequest(checkType, activeTab));
    }
    try {
      const res = await TableApiClient.getTableMonitoringChecksModel(
        connectionName,
        schemaName,
        tableName,
        'daily'
      );
      dispatch(getTableDailyMonitoringChecksSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getTableDailyMonitoringChecksFailed(err));
    }
  };

export const updateTableDailyMonitoringChecksRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_DAILY_MONITORING_CHECKS,
  checkType, activeTab
});

export const updateTableDailyMonitoringChecksSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_DAILY_MONITORING_CHECKS_SUCCESS,
  checkType, activeTab
});

export const updateTableDailyMonitoringChecksFailed = (error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_DAILY_MONITORING_CHECKS_ERROR,
  error
});

export const updateTableDailyMonitoringChecks =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: CheckContainerModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableDailyMonitoringChecksRequest(checkType, activeTab));
    try {
      await TableApiClient.updateTableMonitoringChecksModel(
        connectionName,
        schemaName,
        tableName,
        'daily',
        data
      );
      dispatch(updateTableDailyMonitoringChecksSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateTableDailyMonitoringChecksFailed(err));
    }
  };

export const getTableMonthlyMonitoringChecksRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_MONTHLY_MONITORING_CHECKS,
  checkType, activeTab
});

export const getTableMonthlyMonitoringChecksSuccess = (checkType: CheckTypes, activeTab: string, data: CheckContainerModel) => ({
  type: SOURCE_ACTION.GET_TABLE_MONTHLY_MONITORING_CHECKS_SUCCESS,
  data,
  checkType, activeTab
});

export const getTableMonthlyMonitoringChecksFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_MONTHLY_MONITORING_CHECKS_ERROR,
  error
});

export const getTableMonthlyMonitoringChecks =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, loading = true) =>
  async (dispatch: Dispatch) => {
    if (loading) {
      dispatch(getTableMonthlyMonitoringChecksRequest(checkType, activeTab));
    }
    try {
      const res = await TableApiClient.getTableMonitoringChecksModel(
        connectionName,
        schemaName,
        tableName,
        'monthly'
      );
      dispatch(getTableMonthlyMonitoringChecksSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getTableMonthlyMonitoringChecksFailed(err));
    }
  };

export const updateTableMonthlyMonitoringChecksRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_MONTHLY_MONITORING_CHECKS,
  checkType,
  activeTab
});

export const updateTableMonthlyMonitoringChecksSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_MONTHLY_MONITORING_CHECKS_SUCCESS,
  checkType,
  activeTab
});

export const updateTableMonthlyMonitoringChecksFailed = (error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_MONTHLY_MONITORING_CHECKS_ERROR,
  error
});

export const updateTableMonthlyMonitoringChecks =
  (
    checkType: CheckTypes,
    activeTab: string,
    connectionName: string,
    schemaName: string,
    tableName: string,
    data: CheckContainerModel
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateTableMonthlyMonitoringChecksRequest(checkType, activeTab));
    try {
      await TableApiClient.updateTableMonitoringChecksModel(
        connectionName,
        schemaName,
        tableName,
        'monthly',
        data
      );
      dispatch(updateTableMonthlyMonitoringChecksSuccess(checkType, activeTab));
    } catch (err) {
      dispatch(updateTableMonthlyMonitoringChecksFailed(err));
    }
  };

export const getTableDailyPartitionedChecksRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_DAILY_PARTITIONED_CHECKS,
  checkType,
  activeTab
});

export const getTableDailyPartitionedChecksSuccess = (
  checkType: CheckTypes, activeTab: string, data: CheckContainerModel
) => ({
  type: SOURCE_ACTION.GET_TABLE_DAILY_PARTITIONED_CHECKS_SUCCESS,
  data,
  checkType,
  activeTab
});

export const getTableDailyPartitionedChecksFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_DAILY_PARTITIONED_CHECKS_ERROR,
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
  type: SOURCE_ACTION.UPDATE_TABLE_DAILY_PARTITIONED_CHECKS,
  checkType,
  activeTab
});

export const updateTableDailyPartitionedChecksSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_DAILY_PARTITIONED_CHECKS_SUCCESS,
  checkType,
  activeTab
});

export const updateTableDailyPartitionedChecksFailed = (error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_DAILY_PARTITIONED_CHECKS_ERROR,
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
  type: SOURCE_ACTION.GET_TABLE_MONTHLY_PARTITIONED_CHECKS,
  checkType,
  activeTab
});

export const getTableMonthlyPartitionedChecksSuccess = (
  checkType: CheckTypes, activeTab: string, data: CheckContainerModel
) => ({
  type: SOURCE_ACTION.GET_TABLE_MONTHLY_PARTITIONED_CHECKS_SUCCESS,
  data,
  checkType,
  activeTab
});

export const getTableMonthlyPartitionedChecksFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_MONTHLY_PARTITIONED_CHECKS_ERROR,
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
  type: SOURCE_ACTION.UPDATE_TABLE_MONTHLY_PARTITIONED_CHECKS,
  checkType,
  activeTab
});

export const updateTableMonthlyPartitionedChecksSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_MONTHLY_PARTITIONED_CHECKS_SUCCESS,
  checkType,
  activeTab
});

export const updateTableMonthlyPartitionedChecksFailed = (error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_MONTHLY_PARTITIONED_CHECKS_ERROR,
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

export const setUpdatedTableBasic = (checkType: CheckTypes, activeTab: string, table?: TableListModel) => ({
  type: SOURCE_ACTION.SET_UPDATED_TABLE_BASIC,
  data: table,
  checkType,
  activeTab
});

export const setUpdatedSchedule = (checkType: CheckTypes, activeTab: string, schedule?: MonitoringScheduleSpec) => ({
  type: SOURCE_ACTION.SET_UPDATED_SCHEDULE,
  data: schedule,
  checkType,
  activeTab
});
export const setUpdatedSchedulingGroup = (checkType: CheckTypes, activeTab: string, schedulingGroup: CheckRunMonitoringScheduleGroup, schedule?: MonitoringScheduleSpec) => ({
  type: SOURCE_ACTION.SET_UPDATED_SCHEDULE_GROUP,
  data: {
    schedulingGroup,
    schedule,
  },
  checkType,
  activeTab
});
export const setIsUpdatedSchedulingGroup = (checkType: CheckTypes, activeTab: string, schedulingGroup: CheckRunMonitoringScheduleGroup, isUpdated: boolean) => ({
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

export const setUpdatedChecksModel = (checkType: CheckTypes, activeTab: string, checksUI?: CheckContainerModel) => ({
  type: SOURCE_ACTION.SET_UPDATED_CHECKS_MODEL,
  data: checksUI,
  checkType,
  activeTab
});

export const setUpdatedDailyMonitoringChecks = (checkType: CheckTypes, activeTab: string, checksUI?: CheckContainerModel) => ({
  type: SOURCE_ACTION.SET_TABLE_DAILY_MONITORING_CHECKS,
  data: checksUI,
  checkType,
  activeTab
});

export const setUpdatedMonthlyMonitoringChecks = (checkType: CheckTypes, activeTab: string, checksUI?: CheckContainerModel) => ({
  type: SOURCE_ACTION.SET_TABLE_MONTHLY_MONITORING_CHECKS,
  data: checksUI,
  checkType,
  activeTab
});

export const setUpdatedDailyPartitionedChecks = (
  checkType: CheckTypes, activeTab: string, checksUI?: CheckContainerModel
) => ({
  type: SOURCE_ACTION.SET_TABLE_DAILY_PARTITIONED_CHECKS,
  data: checksUI,
  checkType,
  activeTab
});

export const setUpdatedMonthlyPartitionedChecks = (
  checkType: CheckTypes, activeTab: string, checksUI?: CheckContainerModel
) => ({
  type: SOURCE_ACTION.SET_TABLE_MONTHLY_PARTITIONED_CHECKS,
  data: checksUI,
  checkType,
  activeTab
});

export const setUpdatedTableDataGroupingConfiguration = (
  checkType: CheckTypes, activeTab: string, dataGroupConfiguration?: DataGroupingConfigurationSpec
) => ({
  type: SOURCE_ACTION.SET_TABLE_DEFAULT_DATA_GROUPING_CONFIGURATION,
  data: dataGroupConfiguration,
  checkType,
  activeTab,
});

export const getTableProfilingChecksModelFilterRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_PROFILING_CHECKS_MODEL_FILTER,
  checkType,
  activeTab
});

export const getTableProfilingChecksModelFilterSuccess = (
  checkType: CheckTypes, activeTab: string, data: CheckContainerModel
) => ({
  type: SOURCE_ACTION.GET_TABLE_PROFILING_CHECKS_MODEL_FILTER_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getTableProfilingChecksModelFilterFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_PROFILING_CHECKS_MODEL_FILTER_ERROR,
  error
});

export const getTableProfilingChecksModelFilter =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, category: string, checkName: string, loading = true) =>
    async (dispatch: Dispatch) => {
      if (loading) {
        dispatch(getTableProfilingChecksModelFilterRequest(checkType, activeTab));
      }
      try {
        const res = await TableApiClient.getTableProfilingChecksModelFilter(
          connectionName,
          schemaName,
          tableName,
          category,
          checkName
        );
        dispatch(getTableProfilingChecksModelFilterSuccess(checkType, activeTab, res.data));
      } catch (err) {
        dispatch(getTableProfilingChecksModelFilterFailed(err));
      }
    };

export const getTableMonitoringChecksModelFilterRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_MONITORING_CHECKS_MODEL_FILTER,
  checkType,
  activeTab
});

export const getTableMonitoringChecksModelFilterSuccess = (
  checkType: CheckTypes, activeTab: string, data: CheckContainerModel
) => ({
  type: SOURCE_ACTION.GET_TABLE_MONITORING_CHECKS_MODEL_FILTER_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getTableMonitoringChecksModelFilterFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_MONITORING_CHECKS_MODEL_FILTER_ERROR,
  error
});

export const getTableMonitoringChecksModelFilter =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, timePartitioned: 'daily' | 'monthly', category: string, checkName: string, loading = true) =>
    async (dispatch: Dispatch) => {
      if (loading) {
        dispatch(getTableMonitoringChecksModelFilterRequest(checkType, activeTab));
      }
      try {
        const res = await TableApiClient.getTableMonitoringChecksModelFilter(
          connectionName,
          schemaName,
          tableName,
          timePartitioned,
          category,
          checkName
        );
        dispatch(getTableMonitoringChecksModelFilterSuccess(checkType, activeTab, res.data));
      } catch (err) {
        dispatch(getTableMonitoringChecksModelFilterFailed(err));
      }
    };

export const getTablePartitionedChecksModelFilterRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_PARTITIONED_CHECKS_MODEL_FILTER,
  checkType,
  activeTab
});

export const getTablePartitionedChecksModelFilterSuccess = (
  checkType: CheckTypes, activeTab: string, data: CheckContainerModel
) => ({
  type: SOURCE_ACTION.GET_TABLE_PARTITIONED_CHECKS_MODEL_FILTER_SUCCESS,
  checkType,
  activeTab,
  data
});

export const getTablePartitionedChecksModelFilterFailed = (error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_PARTITIONED_CHECKS_MODEL_FILTER_ERROR,
  error
});

export const getTablePartitionedChecksModelFilter =
  (checkType: CheckTypes, activeTab: string, connectionName: string, schemaName: string, tableName: string, timePartitioned: 'daily' | 'monthly', category: string, checkName: string, loading = true) =>
    async (dispatch: Dispatch) => {
      if (loading) {
        dispatch(getTablePartitionedChecksModelFilterRequest(checkType, activeTab));
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
        dispatch(getTablePartitionedChecksModelFilterSuccess(checkType, activeTab, res.data));
      } catch (err) {
        dispatch(getTablePartitionedChecksModelFilterFailed(err));
      }
    };

export const setTableUpdatedProfilingChecksModelFilter = (checkType: CheckTypes, activeTab: string, ui: CheckContainerModel) => ({
  type: SOURCE_ACTION.SET_UPDATED_PROFILING_CHECKS_MODEL_FILTER,
  checkType,
  activeTab,
  data: ui
});

export const setTableUpdatedMonitoringChecksModelFilter = (checkType: CheckTypes, activeTab: string, ui: CheckContainerModel) => ({
  type: SOURCE_ACTION.SET_UPDATED_MONITORING_CHECKS_MODEL_FILTER,
  checkType,
  activeTab,
  data: ui
});
export const setTableUpdatedPartitionedChecksModelFilter = (checkType: CheckTypes, activeTab: string, ui: CheckContainerModel) => ({
  type: SOURCE_ACTION.SET_UPDATED_PARTITIONED_CHECKS_MODEL_FILTER,
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