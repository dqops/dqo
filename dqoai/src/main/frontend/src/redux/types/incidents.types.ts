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

export enum INCIDENTS_ACTION {
  GET_CONNECTIONS = 'INCIDENTS_ACTION/GET_CONNECTIONS',
  GET_CONNECTIONS_SUCCESS = 'INCIDENTS_ACTION/GET_CONNECTIONS_SUCCESS',
  GET_CONNECTIONS_ERROR = 'INCIDENTS_ACTION/GET_CONNECTIONS_ERROR',

  ADD_FIRST_LEVEL_TAB = 'INCIDENTS_ACTION/ADD_FIRST_LEVEL_TAB',
  SET_ACTIVE_FIRST_LEVEL_TAB = 'INCIDENTS_ACTION/SET_ACTIVE_FIRST_LEVEL_TAB',
  CLOSE_FIRST_LEVEL_TAB = 'INCIDENTS_ACTION/CLOSE_FIRST_LEVEL_TAB',

  GET_INCIDENTS_BY_CONNECTION = 'INCIDENTS_ACTION/GET_INCIDENTS_BY_CONNECTION',
  GET_INCIDENTS_BY_CONNECTION_SUCCESS = 'INCIDENTS_ACTION/GET_INCIDENTS_BY_CONNECTION_SUCCESS',
  GET_INCIDENTS_BY_CONNECTION_ERROR = 'INCIDENTS_ACTION/GET_INCIDENTS_BY_CONNECTION_ERROR',

  SET_INCIDENTS_FILTER = 'INCIDENTS_ACTION/SET_INCIDENTS_FILTER',
  UPDATE_INCIDENT = 'INCIDENTS_ACTION/UPDATE_INCIDENT',
}
