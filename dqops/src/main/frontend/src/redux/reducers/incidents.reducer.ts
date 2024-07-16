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

import { IncidentsPerConnectionModel } from '../../api';
import { INCIDENTS_ACTION } from '../types';
import { Action, INestTab } from './source.reducer';

export interface IncidentFilter {
  connection: string;
  numberOfMonth?: number;
  openIncidents?: boolean;
  acknowledgedIncidents?: boolean;
  resolvedIncidents?: boolean;
  mutedIncidents?: boolean;
  severity?: number;
  page?: number;
  pageSize?: number;
  optionalFilter?: string;
  dimension?: string;
  category?: string;
  sortBy?:
    | 'table'
    | 'tablePriority'
    | 'firstSeen'
    | 'lastSeen'
    | 'dataGroup'
    | 'qualityDimension'
    | 'checkName'
    | 'highestSeverity'
    | 'failedChecksCount';
  sortDirection?: 'asc' | 'desc';
}

export interface IncidentIssueFilter {
  connection: string;
  year: number;
  month: number;
  incidentId: string;
  page?: number;
  pageSize?: number;
  filter?: string;
  days?: number;
  date?: string;
  column?: string;
  check?: string;
  order?:
    | 'executedAt'
    | 'checkHash'
    | 'checkCategory'
    | 'checkName'
    | 'checkDisplayName'
    | 'checkType'
    | 'actualValue'
    | 'expectedValue'
    | 'severity'
    | 'columnName'
    | 'dataGroup'
    | 'timeGradient'
    | 'timePeriod'
    | 'qualityDimension'
    | 'sensorName';
  direction?: any;
}

export interface IncidentHistogramFilter {
  connection: string;
  year: number;
  month: number;
  incidentId: string;
  filter?: string;
  days?: number;
  date?: string;
  column?: string;
  check?: string;
}

export interface IIncidentsState {
  connections: IncidentsPerConnectionModel[];
  loading?: boolean;
  error?: any;
  tabs: INestTab[];
  activeTab?: string;
  selectedConnections?: { [key: string]: string };
}

const initialState: IIncidentsState = {
  connections: [],
  loading: false,
  error: null,
  tabs: []
};

const setActiveTabState = (
  state: IIncidentsState,
  action: Action,
  data: Record<string, unknown>
) => {
  return {
    ...state,
    tabs: state.tabs.map((item) =>
      item.url === state.activeTab
        ? {
            ...item,
            state: {
              ...item.state,
              ...data
            }
          }
        : item
    )
  };
};

const incidentsReducer = (state = initialState, action: any) => {
  switch (action.type) {
    case INCIDENTS_ACTION.GET_CONNECTIONS:
      return {
        ...state,
        loading: true
      };
    case INCIDENTS_ACTION.GET_CONNECTIONS_SUCCESS:
      return {
        ...state,
        loading: false,
        connections: action.data,
        error: null
      };
    case INCIDENTS_ACTION.GET_CONNECTIONS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };

    case INCIDENTS_ACTION.ADD_FIRST_LEVEL_TAB: {
      const existing = state.tabs?.find(
        (item) => item.value === action.data.value
      );

      if (existing) {
        return {
          ...state,
          activeTab: action.data.url,
          tabs: state.tabs.map((item) =>
            item.value === action.data.value
              ? {
                  ...item,
                  ...action.data
                }
              : item
          )
        };
      }

      return {
        ...state,
        activeTab: action.data.url,
        tabs: [...(state.tabs || []), action.data]
      };
    }
    case INCIDENTS_ACTION.SET_ACTIVE_FIRST_LEVEL_TAB: {
      return {
        ...state,
        activeTab: action.data
      };
    }
    case INCIDENTS_ACTION.CLOSE_FIRST_LEVEL_TAB: {
      const index = state.tabs?.findIndex((item) => item.url === action.data);
      let activeTab = state.activeTab;

      if (state.activeTab === action.data) {
        if (index > 0) {
          activeTab = state.tabs[index - 1].url;
        } else if (index < state.tabs.length - 1) {
          activeTab = state.tabs[index + 1].url;
        } else {
          activeTab = '';
        }
      }

      return {
        ...state,
        tabs: state.tabs.filter((item) => item.url !== action.data),
        activeTab
      };
    }
    case INCIDENTS_ACTION.GET_INCIDENTS_BY_CONNECTION: {
      return setActiveTabState(state, action, {
        loading: true
      });
    }
    case INCIDENTS_ACTION.GET_INCIDENTS_BY_CONNECTION_SUCCESS: {
      return setActiveTabState(state, action, {
        incidents: action.data,
        isEnd: action.isEnd,
        loading: false
      });
    }
    case INCIDENTS_ACTION.GET_INCIDENTS_BY_CONNECTION_ERROR: {
      return setActiveTabState(state, action, {
        loading: false
      });
    }
    case INCIDENTS_ACTION.GET_INCIDENTS_ISSUES: {
      return setActiveTabState(state, action, {
        loading: true
      });
    }
    case INCIDENTS_ACTION.GET_INCIDENTS_ISSUES_SUCCESS: {
      return setActiveTabState(state, action, {
        issues: action.data,
        loading: false,
        isEnd: false
      });
    }
    case INCIDENTS_ACTION.GET_INCIDENTS_ISSUES_ERROR: {
      return setActiveTabState(state, action, {
        loading: false
      });
    }
    case INCIDENTS_ACTION.GET_INCIDENTS_HISTOGRAMS: {
      return setActiveTabState(state, action, {
        loading: true
      });
    }
    case INCIDENTS_ACTION.SET_INCIDENTS_HISTOGRAM: {
      return setActiveTabState(state, action, {
        histograms: action.data
      });
    }
    case INCIDENTS_ACTION.GET_INCIDENTS_HISTOGRAMS_SUCCESS: {
      return setActiveTabState(state, action, {
        histograms: action.data,
        loading: false
      });
    }
    case INCIDENTS_ACTION.GET_INCIDENTS_HISTOGRAMS_ERROR: {
      return setActiveTabState(state, action, {
        loading: false
      });
    }
    case INCIDENTS_ACTION.SET_INCIDENTS_FILTER: {
      return setActiveTabState(state, action, {
        filters: action.data
      });
    }
    case INCIDENTS_ACTION.UPDATE_INCIDENT: {
      return setActiveTabState(state, action, {
        incidents: action.data
      });
    }

    case INCIDENTS_ACTION.SET_INCIDENTS_HISTOGRAM_FILTER: {
      return setActiveTabState(state, action, {
        histogramFilter: action.data
      });
    }
    case INCIDENTS_ACTION.ADD_SELECTED_CONNECTION: {
      return {
        ...state,
        selectedConnections: { ...state.selectedConnections, ...action.data }
      };
    }

    default:
      return state;
  }
};

export default incidentsReducer;
