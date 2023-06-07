



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

import { SOURCE_ACTION } from '../types';
import { CheckTypes } from "../../shared/routes";
import {
  CheckResultsDetailedDataModel,
  ErrorsDetailedDataModel,
  ConnectionIncidentGroupingSpec,
  SensorReadoutsDetailedDataModel, TableIncidentGroupingSpec
} from "../../api";
import { Dispatch } from "redux";
import { AxiosResponse } from "axios";
import { ConnectionApiClient, TableApiClient } from "../../services/apiClient";

export const addFirstLevelTab = (checkType: CheckTypes, data: any) => ({
  type: SOURCE_ACTION.ADD_FIRST_LEVEL_TAB,
  checkType,
  data
});

export const setActiveFirstLevelTab = (checkType: CheckTypes, data: any) => ({
  type: SOURCE_ACTION.SET_ACTIVE_FIRST_LEVEL_TAB,
  checkType,
  data
});

export const closeFirstLevelTab = (checkType: CheckTypes, data: any) => ({
  type: SOURCE_ACTION.CLOSE_FIRST_LEVEL_TAB,
  checkType,
  data,
});

export const setCheckResults = (checkType: CheckTypes, activeTab: string, checkName: string, checkResults: CheckResultsDetailedDataModel[]) => ({
  type: SOURCE_ACTION.SET_CHECK_RESULTS,
  checkType,
  activeTab,
  data: {
    checkName,
    checkResults
  }
});

export const setSensorReadouts = (checkType: CheckTypes, activeTab: string, checkName: string, sensorReadouts: SensorReadoutsDetailedDataModel[]) => ({
  type: SOURCE_ACTION.SET_SENSOR_READOUTS,
  checkType,
  activeTab,
  data: {
    checkName,
    sensorReadouts
  }
});

export const setSensorErrors = (checkType: CheckTypes, activeTab: string, checkName: string, errors: ErrorsDetailedDataModel[]) => {
  return ({
    type: SOURCE_ACTION.SET_SENSOR_ERRORS,
    checkType,
    activeTab,
    data: {
      checkName,
      sensorErrors: errors
    }
  });
}

export const setCheckFilters = (checkType: CheckTypes, activeTab: string, checkName: string, filters: any) => {
  return ({
    type: SOURCE_ACTION.SET_CHECK_FILTERS,
    checkType,
    activeTab,
    data: {
      checkName,
      filters,
    }
  });
}

export const getConnectionIncidentGroupingRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_CONNECTION_INCIDENT_GROUPING,
  checkType,
  activeTab
});

export const getConnectionIncidentGroupingSuccess = (checkType: CheckTypes, activeTab: string, data: ConnectionIncidentGroupingSpec) => ({
  type: SOURCE_ACTION.GET_CONNECTION_INCIDENT_GROUPING_SUCCESS,
  data,
  checkType,
  activeTab
});

export const getConnectionIncidentGroupingFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.GET_CONNECTION_INCIDENT_GROUPING_ERROR,
  error,
  checkType,
  activeTab
});

export const getConnectionIncidentGrouping = (checkType: CheckTypes, activeTab: string, connection: string) => async (dispatch: Dispatch) => {
  dispatch(getConnectionIncidentGroupingRequest(checkType, activeTab));
  try {
    const res: AxiosResponse<ConnectionIncidentGroupingSpec> =
      await ConnectionApiClient.getConnectionIncidentGrouping(connection);
    dispatch(getConnectionIncidentGroupingSuccess(checkType, activeTab, res.data));
  } catch (err) {
    dispatch(getConnectionIncidentGroupingFailed(checkType, activeTab, err));
  }
};

export const setUpdateIncidentGroup = (checkType: CheckTypes, activeTab: string, data: ConnectionIncidentGroupingSpec) => ({
  type: SOURCE_ACTION.SET_CONNECTION_INCIDENT_GROUPING,
  data,
  checkType,
  activeTab
});

export const updateConnectionIncidentGroupingRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_CONNECTION_INCIDENT_GROUPING,
  checkType,
  activeTab
});

export const updateConnectionIncidentGroupingSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_CONNECTION_INCIDENT_GROUPING_SUCCESS,
  checkType,
  activeTab
});

export const updateConnectionIncidentGroupingFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_CONNECTION_INCIDENT_GROUPING_ERROR,
  error,
  checkType,
  activeTab
});

export const updateConnectionIncidentGrouping = (checkType: CheckTypes, activeTab: string, connection: string, data: ConnectionIncidentGroupingSpec) => async (dispatch: any) => {
  dispatch(updateConnectionIncidentGroupingRequest(checkType, activeTab));
  try {
    await ConnectionApiClient.updateConnectionIncidentGrouping(connection, data);
    dispatch(updateConnectionIncidentGroupingSuccess(checkType, activeTab));
    dispatch(getConnectionIncidentGrouping(checkType, activeTab, connection));
  } catch (err) {
    dispatch(updateConnectionIncidentGroupingFailed(checkType, activeTab, err));
  }
};


export const getTableIncidentGroupingRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.GET_TABLE_INCIDENT_GROUPING,
  checkType,
  activeTab
});

export const getTableIncidentGroupingSuccess = (checkType: CheckTypes, activeTab: string, data: ConnectionIncidentGroupingSpec) => ({
  type: SOURCE_ACTION.GET_TABLE_INCIDENT_GROUPING_SUCCESS,
  data,
  checkType,
  activeTab
});

export const getTableIncidentGroupingFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.GET_TABLE_INCIDENT_GROUPING_ERROR,
  error,
  checkType,
  activeTab
});

export const getTableIncidentGrouping = (checkType: CheckTypes, activeTab: string, connection: string, schema: string, table: string) => async (dispatch: Dispatch) => {
  dispatch(getTableIncidentGroupingRequest(checkType, activeTab));
  try {
    const res: AxiosResponse<ConnectionIncidentGroupingSpec> =
      await TableApiClient.getTableIncidentGrouping(connection, schema, table);
    dispatch(getTableIncidentGroupingSuccess(checkType, activeTab, res.data));
  } catch (err) {
    dispatch(getTableIncidentGroupingFailed(checkType, activeTab, err));
  }
};

export const updateTableIncidentGroupingRequest = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_INCIDENT_GROUPING,
  checkType,
  activeTab
});

export const updateTableIncidentGroupingSuccess = (checkType: CheckTypes, activeTab: string) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_INCIDENT_GROUPING_SUCCESS,
  checkType,
  activeTab
});

export const updateTableIncidentGroupingFailed = (checkType: CheckTypes, activeTab: string, error: unknown) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_INCIDENT_GROUPING_ERROR,
  error,
  checkType,
  activeTab
});

export const updateTableIncidentGrouping = (checkType: CheckTypes, activeTab: string, connection: string, schema: string, table: string, data: TableIncidentGroupingSpec) => async (dispatch: any) => {
  dispatch(updateTableIncidentGroupingRequest(checkType, activeTab));
  try {
    await TableApiClient.updateTableIncidentGrouping(connection, schema, table, data);
    dispatch(updateTableIncidentGroupingSuccess(checkType, activeTab));
    dispatch(getTableIncidentGrouping(checkType, activeTab, connection, schema, table));
  } catch (err) {
    dispatch(updateTableIncidentGroupingFailed(checkType, activeTab, err));
  }
};
