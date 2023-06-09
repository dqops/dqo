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

import { IRootState } from "../reducers";
import { CheckTypes } from "../../shared/routes";

export const getFirstLevelState = (checkType: CheckTypes) => (state: IRootState) => {
  const { tabs, activeTab = ''} = state.source[checkType || CheckTypes.SOURCES] || {};

  return tabs.find((item) => item.value === activeTab)?.state || {} as any;
};

export const getFirstLevelActiveTab = (checkType: CheckTypes) => (state: IRootState) => {
  const { activeTab = ''} = state.source[checkType || CheckTypes.SOURCES];

  return activeTab;
};

export const getFirstLevelSensorState = (state: IRootState) => {
  const { tabs, activeTab = ''} = state.sensor;

  return tabs.find((item) => item.url === activeTab)?.state || {} as any;
};

export const getFirstLevelIncidentsState = (state: IRootState) => {
  const { tabs, activeTab = ''} = state.incidents;

  return tabs.find((item) => item.url === activeTab)?.state || {} as any;
};
