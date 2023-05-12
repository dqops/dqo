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

import { IncidentsApi } from '../../services/apiClient';
import { INCIDENTS_ACTION } from '../types';
import { AxiosResponse } from 'axios';
import { IncidentsPerConnectionModel } from "../../api";
import { IncidentFilter } from "../reducers/incidents.reducer";

export const getConnectionsRequest = () => ({
  type: INCIDENTS_ACTION.GET_CONNECTIONS
});

export const getConnectionsSuccess = (data: Array<IncidentsPerConnectionModel>) => ({
  type: INCIDENTS_ACTION.GET_CONNECTIONS_SUCCESS,
  data
});

export const getConnectionsFailed = (error: unknown) => ({
  type: INCIDENTS_ACTION.GET_CONNECTIONS_ERROR,
  error
});

export const getConnections = () => async (dispatch: Dispatch) => {
  dispatch(getConnectionsRequest());
  try {
    const res: AxiosResponse<Array<IncidentsPerConnectionModel>> =
      await IncidentsApi.findConnectionIncidentStats();
    dispatch(getConnectionsSuccess(res.data));
  } catch (err) {
    dispatch(getConnectionsFailed(err));
  }
};

export const addFirstLevelTab = (data: any) => ({
  type: INCIDENTS_ACTION.ADD_FIRST_LEVEL_TAB,
  data,
});

export const setActiveFirstLevelTab = (data: any) => ({
  type: INCIDENTS_ACTION.SET_ACTIVE_FIRST_LEVEL_TAB,
  data
});


export const closeFirstLevelTab = (data: any) => ({
  type: INCIDENTS_ACTION.CLOSE_FIRST_LEVEL_TAB,
  data,
});

export const getIncidentsByConnectionRequest = () => ({
  type: INCIDENTS_ACTION.GET_INCIDENTS_BY_CONNECTION
});

export const getIncidentsByConnectionSuccess = (data: Array<IncidentsPerConnectionModel>) => ({
  type: INCIDENTS_ACTION.GET_INCIDENTS_BY_CONNECTION_SUCCESS,
  data
});

export const getIncidentsByConnectionFailed = (error: unknown) => ({
  type: INCIDENTS_ACTION.GET_INCIDENTS_BY_CONNECTION_ERROR,
  error
});

export const getIncidentsByConnection = ({
  connection,
  numberOfMonth = 3,
  openIncidents = true,
  acknowledgedIncidents = true,
  resolvedIncidents = true,
  mutedIncidents = false,
  page = 1,
  pageSize = 50,
  optionalFilter = '',
  sortBy = 'table',
  sortDirection = 'asc'
}: IncidentFilter) => async (dispatch: Dispatch) => {
  dispatch(getIncidentsByConnectionRequest());
  try {
    const res: AxiosResponse<Array<IncidentsPerConnectionModel>> =
      await IncidentsApi.findRecentIncidentsOnConnection(connection, numberOfMonth, openIncidents, acknowledgedIncidents, resolvedIncidents, mutedIncidents, page, pageSize, optionalFilter, sortBy, sortDirection);
    dispatch(getIncidentsByConnectionSuccess(res.data));
  } catch (err) {
    dispatch(getIncidentsByConnectionFailed(err));
  }
};

export const setIncidentsFilter = (filter: Partial<IncidentFilter>) => ({
  type: INCIDENTS_ACTION.SET_INCIDENTS_FILTER,
  data: filter,
});
