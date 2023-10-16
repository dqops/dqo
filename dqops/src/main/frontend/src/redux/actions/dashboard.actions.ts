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

import { Dispatch } from 'redux';

import { DashboardsApi } from '../../services/apiClient';
import { DASHBOARD_ACTION } from '../types';
import { AxiosResponse } from 'axios';
import { DashboardsFolderSpec } from "../../api";
import { TDashboardTootlipProps } from '../reducers/dashboard.reducer';

export const getDashboardsRequest = () => ({
  type: DASHBOARD_ACTION.GET_DASHBOARDS
});

export const getDashboardsSuccess = (data: DashboardsFolderSpec[]) => ({
  type: DASHBOARD_ACTION.GET_DASHBOARDS_SUCCESS,
  data
});

export const getDashboardsFailed = (error: unknown) => ({
  type: DASHBOARD_ACTION.GET_DASHBOARDS_ERROR,
  error
});

export const getAllDashboards = () => async (dispatch: Dispatch) => {
  dispatch(getDashboardsRequest());
  try {
    const res: AxiosResponse<DashboardsFolderSpec[]> =
      await DashboardsApi.getAllDashboards();
    dispatch(getDashboardsSuccess(res.data));
  } catch (err) {
    dispatch(getDashboardsFailed(err));
  }
};

export const getDashboardTooltipState = (dashboardTooltipState: TDashboardTootlipProps) => ({
  type: DASHBOARD_ACTION.TOGGLE_DASHBOARD_TOOLTIP,
  dashboardTooltipState
})

