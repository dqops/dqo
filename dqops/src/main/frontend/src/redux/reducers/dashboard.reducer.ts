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

import {
  DashboardsFolderSpec,
} from '../../api';
import { DASHBOARD_ACTION } from '../types';

export type TDashboardTootlipProps = {
  height?: number;
  label?: string;
  url?: string; 
}

export interface IDashboardState {
  dashboardFolders: DashboardsFolderSpec[];
  loading: boolean;
  error: any;
  dashboardState: Record<string, boolean>;
  dashboardTooltipState: TDashboardTootlipProps;
}

const initialState: IDashboardState = {
  dashboardFolders: [],
  loading: false,
  error: null,
  dashboardState: {},
  dashboardTooltipState: {}
};

const dashboardReducer = (state = initialState, action: any) => {
  switch (action.type) {
    case DASHBOARD_ACTION.GET_DASHBOARDS:
      return {
        ...state,
        loading: true
      };
    case DASHBOARD_ACTION.GET_DASHBOARDS_SUCCESS:
      return {
        ...state,
        loading: false,
        dashboardFolders: action.data,
        error: null
      };
    case DASHBOARD_ACTION.GET_DASHBOARDS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case DASHBOARD_ACTION.TOGGLE_DASHBOARD_FOLDER:
      return {
        ...state,
        dashboardState: {
          ...state.dashboardState,
        }
      }
    case DASHBOARD_ACTION.TOGGLE_DASHBOARD_TOOLTIP: 
      return {
        ...state,
        dashboardTooltipState: action.dashboardTooltipState
      }  
    default:
      return state;
  }
};

export default dashboardReducer;
