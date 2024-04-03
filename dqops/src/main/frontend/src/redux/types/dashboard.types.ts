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

export enum DASHBOARD_ACTION {
  GET_DASHBOARDS = 'DASHBOARD_ACTION/GET_DASHBOARDS',
  GET_DASHBOARDS_SUCCESS = 'DASHBOARD_ACTION/GET_DASHBOARDS_SUCCESS',
  GET_DASHBOARDS_ERROR = 'DASHBOARD_ACTION/GET_DASHBOARDS_ERROR',

  TOGGLE_DASHBOARD_FOLDER = 'DASHBOARD_ACTION/TOGGLE_DASHBOARD_FOLDER',
  TOGGLE_DASHBOARD_TOOLTIP = 'DASHBOARD_ACTION/TOGGLE_DASHBOARD_TOOLTIP'
}
