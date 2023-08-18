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

import { combineReducers } from 'redux';

import jobReducer, { IJobsState } from './job.reducer';
import dashboardReducer, { IDashboardState } from './dashboard.reducer';
import sourceReducer, { INestTab, ISourceState } from './source.reducer';
import sensorReducer, { ISensorState } from './sensor.reducer';
import ruleReducer, { IRuleState } from './rule.reducer';
import incidentsReducer, { IIncidentsState } from './incidents.reducer';
import dataQualityChecksReducer, { IdataQualityChecks } from './dataQualityChecks';
export interface IRootState {
  job: IJobsState;
  dashboard: IDashboardState;
  source: ISourceState;
  sensor: ISensorState;
  rule: IRuleState;
  dataQualityChecks: IdataQualityChecks
  incidents: IIncidentsState;
}

const rootReducer = combineReducers({
  job: jobReducer,
  dashboard: dashboardReducer,
  source: sourceReducer,
  sensor: sensorReducer,
  rule: ruleReducer,
  dataQualityChecks: dataQualityChecksReducer,
  incidents: incidentsReducer
});

export default rootReducer;
