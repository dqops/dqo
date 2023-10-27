///
/// Copyright © 2021 DQOps (support@dqops.com)
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
import { CheckTypes } from '../../shared/routes';
import {
  CheckResultsListModel,
  ErrorsListModel,
  ConnectionIncidentGroupingSpec,
  SensorReadoutsListModel,
  CheckModel,
  CheckSearchFiltersCheckTypeEnum,
  TableIncidentGroupingSpec
} from '../../api';
import { Dispatch } from 'redux';
import { AxiosResponse } from 'axios';
import {
  CheckResultApi,
  ConnectionApiClient,
  ErrorsApi,
  SensorReadoutsApi,
  TableApiClient
} from '../../services/apiClient';

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

export const setActiveFirstLevelUrl = (
  checkType: CheckTypes,
  activeTab: string,
  newUrl: string
) => ({
  type: SOURCE_ACTION.SET_ACTIVE_FIRST_LEVEL_URL,
  checkType,
  activeTab,
  data: newUrl
});

export const closeFirstLevelTab = (checkType: CheckTypes, data: any) => ({
  type: SOURCE_ACTION.CLOSE_FIRST_LEVEL_TAB,
  checkType,
  data
});

export const setCheckResults = (
  checkType: CheckTypes,
  activeTab: string,
  checkName: string,
  comparisonName: string,
  checkResults: CheckResultsListModel[]
) => ({
  type: SOURCE_ACTION.SET_CHECK_RESULTS,
  checkType,
  activeTab,
  data: {
    checkName,
    checkResults,
    comparisonName
  }
});

export const setSensorReadouts = (
  checkType: CheckTypes,
  activeTab: string,
  checkName: string,
  comparisonName: string,
  sensorReadouts: SensorReadoutsListModel[]
) => ({
  type: SOURCE_ACTION.SET_SENSOR_READOUTS,
  checkType,
  activeTab,
  data: {
    checkName,
    sensorReadouts,
    comparisonName
  }
});

export const setSensorErrors = (
  checkType: CheckTypes,
  activeTab: string,
  checkName: string,
  comparisonName: string,
  errors: ErrorsListModel[]
) => {
  return {
    type: SOURCE_ACTION.SET_SENSOR_ERRORS,
    checkType,
    activeTab,
    data: {
      checkName,
      sensorErrors: errors,
      comparisonName
    }
  };
};

export const setCheckFilters = (
  checkType: CheckTypes,
  activeTab: string,
  checkName: string,
  filters: any
) => {
  return {
    type: SOURCE_ACTION.SET_CHECK_FILTERS,
    checkType,
    activeTab,
    data: {
      checkName,
      filters
    }
  };
};

export const getConnectionIncidentGroupingRequest = (
  checkType: CheckTypes,
  activeTab: string
) => ({
  type: SOURCE_ACTION.GET_CONNECTION_INCIDENT_GROUPING,
  checkType,
  activeTab
});

export const getConnectionIncidentGroupingSuccess = (
  checkType: CheckTypes,
  activeTab: string,
  data: ConnectionIncidentGroupingSpec
) => ({
  type: SOURCE_ACTION.GET_CONNECTION_INCIDENT_GROUPING_SUCCESS,
  data,
  checkType,
  activeTab
});

export const getConnectionIncidentGroupingFailed = (
  checkType: CheckTypes,
  activeTab: string,
  error: unknown
) => ({
  type: SOURCE_ACTION.GET_CONNECTION_INCIDENT_GROUPING_ERROR,
  error,
  checkType,
  activeTab
});

export const getConnectionIncidentGrouping =
  (checkType: CheckTypes, activeTab: string, connection: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getConnectionIncidentGroupingRequest(checkType, activeTab));
    try {
      const res: AxiosResponse<ConnectionIncidentGroupingSpec> =
        await ConnectionApiClient.getConnectionIncidentGrouping(connection);
      dispatch(
        getConnectionIncidentGroupingSuccess(checkType, activeTab, res.data)
      );
    } catch (err) {
      dispatch(getConnectionIncidentGroupingFailed(checkType, activeTab, err));
    }
  };

export const setUpdateIncidentGroup = (
  checkType: CheckTypes,
  activeTab: string,
  data: ConnectionIncidentGroupingSpec
) => ({
  type: SOURCE_ACTION.SET_CONNECTION_INCIDENT_GROUPING,
  data,
  checkType,
  activeTab
});

export const updateConnectionIncidentGroupingRequest = (
  checkType: CheckTypes,
  activeTab: string
) => ({
  type: SOURCE_ACTION.UPDATE_CONNECTION_INCIDENT_GROUPING,
  checkType,
  activeTab
});

export const updateConnectionIncidentGroupingSuccess = (
  checkType: CheckTypes,
  activeTab: string
) => ({
  type: SOURCE_ACTION.UPDATE_CONNECTION_INCIDENT_GROUPING_SUCCESS,
  checkType,
  activeTab
});

export const updateConnectionIncidentGroupingFailed = (
  checkType: CheckTypes,
  activeTab: string,
  error: unknown
) => ({
  type: SOURCE_ACTION.UPDATE_CONNECTION_INCIDENT_GROUPING_ERROR,
  error,
  checkType,
  activeTab
});

export const updateConnectionIncidentGrouping =
  (
    checkType: CheckTypes,
    activeTab: string,
    connection: string,
    data: ConnectionIncidentGroupingSpec
  ) =>
  async (dispatch: any) => {
    dispatch(updateConnectionIncidentGroupingRequest(checkType, activeTab));
    try {
      await ConnectionApiClient.updateConnectionIncidentGrouping(
        connection,
        data
      );
      dispatch(updateConnectionIncidentGroupingSuccess(checkType, activeTab));
      dispatch(getConnectionIncidentGrouping(checkType, activeTab, connection));
    } catch (err) {
      dispatch(
        updateConnectionIncidentGroupingFailed(checkType, activeTab, err)
      );
    }
  };

export const getCheckResultsRequest = (
  checkType: CheckTypes,
  activeTab: string
) => ({
  type: SOURCE_ACTION.GET_CHECK_RESULTS_REQUEST,
  checkType,
  activeTab
});

export const getCheckResultsSuccess = (
  checkType: CheckTypes,
  activeTab: string,
  checkName: string,
  checkResults: CheckResultsListModel[]
) => ({
  type: SOURCE_ACTION.GET_CHECK_RESULTS_SUCCESS,
  checkType,
  activeTab,
  data: {
    checkName,
    checkResults
  }
});

export const getCheckResultsError = (
  checkType: CheckTypes,
  activeTab: string
) => ({
  type: SOURCE_ACTION.GET_CHECK_RESULTS_ERROR,
  checkType,
  activeTab
});

export const getCheckResults =
  (
    checkType: CheckTypes,
    activeTab: string,
    {
      connection,
      schema,
      table,
      column,
      dataGrouping,
      checkName,
      runCheckType,
      startDate,
      endDate,
      timeScale,
      category,
      comparisonName
    }: {
      connection: string;
      schema: string;
      table: string;
      column?: string;
      dataGrouping?: string;
      startDate: string;
      endDate: string;
      timeScale?: 'daily' | 'monthly';
      checkName: string;
      runCheckType?: string;
      category?: string;
      comparisonName?: string;
    }
  ) =>
  (dispatch: any) => {
    dispatch(getCheckResultsRequest(checkType, activeTab));

    const successCallback = (
      res: AxiosResponse<CheckResultsListModel[]>
    ) => {
      const checks = [...res.data]

    if (checks && checks[0] && checks[0].checkResultEntries && comparisonName && comparisonName.length > 0) {
      checks[0].checkResultEntries = checks[0].checkResultEntries.filter(entry => entry.tableComparison === comparisonName);
    }
    
    const filteredChecks = checks.filter((item) => item.checkName === checkName)

      dispatch(
        getCheckResultsSuccess(
          checkType,
          activeTab,
          checkName,
          filteredChecks ?? []
          )
          );
          
      dispatch(
        setCheckResults(
          checkType,
          activeTab,
          checkName,
          comparisonName ?? "",
          filteredChecks ?? []
        )
      );
    };
    const errCallback = () => {
      dispatch(getCheckResultsError(checkType, activeTab));
    };
    if (JSON.stringify(startDate) !== JSON.stringify(endDate)) {
      if (column) {
        if (checkType === CheckSearchFiltersCheckTypeEnum.profiling) {
          CheckResultApi.getColumnProfilingChecksResults(
            connection,
            schema,
            table,
            column,
            dataGrouping,
            startDate,
            endDate,
            checkName,
            category,
            comparisonName
          )
            .then(successCallback)
            .catch(errCallback);
        } else if (runCheckType === CheckSearchFiltersCheckTypeEnum.monitoring) {
          CheckResultApi.getColumnMonitoringChecksResults(
            connection,
            schema,
            table,
            column,
            timeScale || 'daily',
            dataGrouping,
            startDate,
            endDate,
            checkName,
            category,
            comparisonName
          )
            .then(successCallback)
            .catch(errCallback);
        } else if (
          runCheckType === CheckSearchFiltersCheckTypeEnum.partitioned
        ) {
          CheckResultApi.getColumnPartitionedChecksResults(
            connection,
            schema,
            table,
            column,
            timeScale || 'daily',
            dataGrouping,
            startDate,
            endDate,
            checkName,
            category,
            comparisonName
          )
            .then(successCallback)
            .catch(errCallback);
        }
      } else {
        if (runCheckType === CheckSearchFiltersCheckTypeEnum.profiling) {
          CheckResultApi.getTableProfilingChecksResults(
            connection,
            schema,
            table,
            dataGrouping,
            startDate,
            endDate,
            checkName,
            category,
            comparisonName
          )
            .then(successCallback)
            .catch(errCallback);
        } else if (runCheckType === CheckSearchFiltersCheckTypeEnum.monitoring) {
          CheckResultApi.getTableMonitoringChecksResults(
            connection,
            schema,
            table,
            timeScale || 'daily',
            dataGrouping,
            startDate,
            endDate,
            checkName,
            category,
            comparisonName
          )
            .then(successCallback)
            .catch(errCallback);
        } else if (
          runCheckType === CheckSearchFiltersCheckTypeEnum.partitioned
        ) {
          CheckResultApi.getTablePartitionedChecksResults(
            connection,
            schema,
            table,
            timeScale || 'daily',
            dataGrouping,
            startDate,
            endDate,
            checkName,
            category,
            comparisonName
          )
            .then(successCallback)
            .catch(errCallback);
        }
      }
    }
  };

export const getCheckReadoutsRequest = (
  checkType: CheckTypes,
  activeTab: string
) => ({
  type: SOURCE_ACTION.GET_CHECK_READOUTS_REQUEST,
  checkType,
  activeTab
});

export const getCheckReadoutsSuccess = (
  checkType: CheckTypes,
  activeTab: string,
  checkName: string,
  checkResults: CheckResultsListModel[]
) => ({
  type: SOURCE_ACTION.GET_CHECK_READOUTS_SUCCESS,
  checkType,
  activeTab,
  data: {
    checkName,
    checkResults
  }
});

export const getCheckReadoutsError = (
  checkType: CheckTypes,
  activeTab: string
) => ({
  type: SOURCE_ACTION.GET_CHECK_READOUTS_ERROR,
  checkType,
  activeTab
});

export const getCheckReadouts =
  (
    checkType: CheckTypes,
    activeTab: string,
    {
      connection,
      schema,
      table,
      column,
      dataGrouping,
      startDate,
      endDate,
      checkName,
      runCheckType,
      timeScale,
      category,
      comparisonName
    }: {
      connection: string;
      schema: string;
      table: string;
      column?: string;
      dataGrouping?: string;
      check?: CheckModel;
      startDate: string;
      endDate: string;
      timeScale?: 'daily' | 'monthly';
      checkName: string;
      runCheckType?: string;
      category?: string;
      comparisonName?: string;
    }
  ) =>
  (dispatch: any) => {
    dispatch(getCheckReadoutsRequest(checkType, activeTab));

    const successCallback = (
      res: AxiosResponse<SensorReadoutsListModel[]>
    ) => {
      const sensors = [...res.data]

      if (sensors && sensors[0] && sensors[0].sensorReadoutEntries && comparisonName && comparisonName.length > 0) {
        sensors[0].sensorReadoutEntries = sensors[0].sensorReadoutEntries.filter(entry => entry.tableComparison === comparisonName);
      }
      
      const filteredSensors = sensors.filter((item) => item.checkName === checkName)

      dispatch(
        setSensorReadouts(
          checkType,
          activeTab,
          checkName,
          comparisonName ?? '',
          filteredSensors ?? []
        )
      );
    };
    const errCallback = () => {
      dispatch(getCheckReadoutsError(checkType, activeTab));
    };

    if (column) {
      if (runCheckType === CheckSearchFiltersCheckTypeEnum.profiling) {
        SensorReadoutsApi.getColumnProfilingSensorReadouts(
          connection,
          schema,
          table,
          column,
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        )
          .then(successCallback)
          .catch(errCallback);
      } else if (runCheckType === CheckSearchFiltersCheckTypeEnum.monitoring) {
        SensorReadoutsApi.getColumnMonitoringSensorReadouts(
          connection,
          schema,
          table,
          column,
          timeScale || 'daily',
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        )
          .then(successCallback)
          .catch(errCallback);
      } else if (runCheckType === CheckSearchFiltersCheckTypeEnum.partitioned) {
        SensorReadoutsApi.getColumnPartitionedSensorReadouts(
          connection,
          schema,
          table,
          column,
          timeScale || 'daily',
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        )
          .then(successCallback)
          .catch(errCallback);
      }
    } else {
      if (runCheckType === CheckSearchFiltersCheckTypeEnum.profiling) {
        SensorReadoutsApi.getTableProfilingSensorReadouts(
          connection,
          schema,
          table,
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        )
          .then(successCallback)
          .catch(errCallback);
      } else if (runCheckType === CheckSearchFiltersCheckTypeEnum.monitoring) {
        SensorReadoutsApi.getTableMonitoringSensorReadouts(
          connection,
          schema,
          table,
          timeScale || 'daily',
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        )
          .then(successCallback)
          .catch(errCallback);
      } else if (runCheckType === CheckSearchFiltersCheckTypeEnum.partitioned) {
        SensorReadoutsApi.getTablePartitionedSensorReadouts(
          connection,
          schema,
          table,
          timeScale || 'daily',
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        )
          .then(successCallback)
          .catch(errCallback);
      }
    }
  };

export const getCheckErrorsRequest = (
  checkType: CheckTypes,
  activeTab: string
) => ({
  type: SOURCE_ACTION.GET_CHECK_ERROR_REQUEST,
  checkType,
  activeTab
});

export const getCheckErrorsSuccess = (
  checkType: CheckTypes,
  activeTab: string,
  checkName: string,
  checkResults: CheckResultsListModel[]
) => ({
  type: SOURCE_ACTION.GET_CHECK_ERROR_SUCCESS,
  checkType,
  activeTab,
  data: {
    checkName,
    checkResults
  }
});

export const getCheckErrorsError = (
  checkType: CheckTypes,
  activeTab: string
) => ({
  type: SOURCE_ACTION.GET_CHECK_ERROR_ERROR,
  checkType,
  activeTab
});

export const getCheckErrors =
  (
    checkType: CheckTypes,
    activeTab: string,
    {
      connection,
      schema,
      table,
      column,
      dataGrouping,
      startDate,
      endDate,
      checkName,
      runCheckType,
      timeScale,
      category,
      comparisonName
    }: {
      connection: string;
      schema: string;
      table: string;
      column?: string;
      dataGrouping?: string;
      startDate: string;
      endDate: string;
      timeScale?: 'daily' | 'monthly';
      checkName: string;
      runCheckType?: string;
      category?: string;
      comparisonName?: string;
    }
  ) =>
  (dispatch: any) => {
    dispatch(getCheckErrorsRequest(checkType, activeTab));

    const successCallback = (res: AxiosResponse<ErrorsListModel[]>) => {
      const errors = [...res.data]

      if (errors && errors[0] && errors[0].errorEntries && comparisonName && comparisonName.length > 0) {
        errors[0].errorEntries = errors[0].errorEntries.filter((item) => item.tableComparison === comparisonName)
      }

      const filteredErrors = errors.filter((item) => item.checkName === checkName)

      dispatch(
        setSensorErrors(
          checkType,
          activeTab,
          checkName,
          comparisonName ?? '',
          filteredErrors ?? []
        )
      );
    };
    const errCallback = () => {
      dispatch(getCheckErrorsError(checkType, activeTab));
    };

    if (column) {
      if (runCheckType === CheckSearchFiltersCheckTypeEnum.profiling) {
        ErrorsApi.getColumnProfilingErrors(
          connection,
          schema,
          table,
          column,
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        )
          .then(successCallback)
          .catch(errCallback);
      } else if (runCheckType === CheckSearchFiltersCheckTypeEnum.monitoring) {
        ErrorsApi.getColumnMonitoringErrors(
          connection,
          schema,
          table,
          column,
          timeScale || 'daily',
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        )
          .then(successCallback)
          .catch(errCallback);
      } else if (runCheckType === CheckSearchFiltersCheckTypeEnum.partitioned) {
        ErrorsApi.getColumnPartitionedErrors(
          connection,
          schema,
          table,
          column,
          timeScale || 'daily',
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        )
          .then(successCallback)
          .catch(errCallback);
      }
    } else {
      if (runCheckType === CheckSearchFiltersCheckTypeEnum.profiling) {
        ErrorsApi.getTableProfilingErrors(
          connection,
          schema,
          table,
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        )
          .then(successCallback)
          .catch(errCallback);
      } else if (runCheckType === CheckSearchFiltersCheckTypeEnum.monitoring) {
        ErrorsApi.getTableMonitoringErrors(
          connection,
          schema,
          table,
          timeScale || 'daily',
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        )
          .then(successCallback)
          .catch(errCallback);
      } else if (runCheckType === CheckSearchFiltersCheckTypeEnum.partitioned) {
        ErrorsApi.getTablePartitionedErrors(
          connection,
          schema,
          table,
          timeScale || 'daily',
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        )
          .then(successCallback)
          .catch(errCallback);
      }
    }
  };

export const getTableIncidentGroupingRequest = (
  checkType: CheckTypes,
  activeTab: string
) => ({
  type: SOURCE_ACTION.GET_TABLE_INCIDENT_GROUPING,
  checkType,
  activeTab
});

export const getTableIncidentGroupingSuccess = (
  checkType: CheckTypes,
  activeTab: string,
  data: ConnectionIncidentGroupingSpec
) => ({
  type: SOURCE_ACTION.GET_TABLE_INCIDENT_GROUPING_SUCCESS,
  data,
  checkType,
  activeTab
});

export const getTableIncidentGroupingFailed = (
  checkType: CheckTypes,
  activeTab: string,
  error: unknown
) => ({
  type: SOURCE_ACTION.GET_TABLE_INCIDENT_GROUPING_ERROR,
  error,
  checkType,
  activeTab
});

export const getTableIncidentGrouping =
  (
    checkType: CheckTypes,
    activeTab: string,
    connection: string,
    schema: string,
    table: string
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(getTableIncidentGroupingRequest(checkType, activeTab));
    try {
      const res: AxiosResponse<ConnectionIncidentGroupingSpec> =
        await TableApiClient.getTableIncidentGrouping(
          connection,
          schema,
          table
        );
      dispatch(getTableIncidentGroupingSuccess(checkType, activeTab, res.data));
    } catch (err) {
      dispatch(getTableIncidentGroupingFailed(checkType, activeTab, err));
    }
  };

export const updateTableIncidentGroupingRequest = (
  checkType: CheckTypes,
  activeTab: string
) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_INCIDENT_GROUPING,
  checkType,
  activeTab
});

export const updateTableIncidentGroupingSuccess = (
  checkType: CheckTypes,
  activeTab: string
) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_INCIDENT_GROUPING_SUCCESS,
  checkType,
  activeTab
});

export const updateTableIncidentGroupingFailed = (
  checkType: CheckTypes,
  activeTab: string,
  error: unknown
) => ({
  type: SOURCE_ACTION.UPDATE_TABLE_INCIDENT_GROUPING_ERROR,
  error,
  checkType,
  activeTab
});

export const updateTableIncidentGrouping =
  (
    checkType: CheckTypes,
    activeTab: string,
    connection: string,
    schema: string,
    table: string,
    data: TableIncidentGroupingSpec
  ) =>
  async (dispatch: any) => {
    dispatch(updateTableIncidentGroupingRequest(checkType, activeTab));
    try {
      await TableApiClient.updateTableIncidentGrouping(
        connection,
        schema,
        table,
        data
      );
      dispatch(updateTableIncidentGroupingSuccess(checkType, activeTab));
      dispatch(
        getTableIncidentGrouping(
          checkType,
          activeTab,
          connection,
          schema,
          table
        )
      );
    } catch (err) {
      dispatch(updateTableIncidentGroupingFailed(checkType, activeTab, err));
    }
  };

export const setCurrentJobId = (
  checkType: CheckTypes,
  activeTab: string,
  jobId: number
) => ({
  type: SOURCE_ACTION.SET_CURRENT_JOB_ID,
  checkType,
  activeTab,
  data: jobId
});

export const toggleCheck = (
  checkType: CheckTypes,
  activeTab: string,
  checkName: string
) => ({
  type: SOURCE_ACTION.TOGGLE_CHECK,
  checkType,
  activeTab,
  data: checkName
});

export const closeCheck = (
  checkType: CheckTypes,
  activeTab: string,
  checkName: string
) => ({
  type: SOURCE_ACTION.CLOSE_CHECK,
  checkType,
  activeTab,
  data: checkName
});
