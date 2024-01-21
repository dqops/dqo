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

import axios from 'axios';

import {
  ConnectionsApiFactory,
  Configuration,
  SchemasApiFactory,
  TablesApiFactory,
  ColumnsApiFactory,
  JobsApiFactory,
  DataSourcesApiFactory,
  DataGroupingConfigurationsApiFactory,
  CheckResultsOverviewApiFactory,
  DashboardsApiFactory,
  TimezonesApiFactory,
  CheckResultsApiFactory,
  SensorReadoutsApiFactory,
  ErrorsApiFactory,
  SensorsApiFactory,
  RulesApiFactory,
  IncidentsApiFactory,
  LogShippingApiFactory,
  EnvironmentApiFactory,
  ChecksApiFactory,
  TableComparisonsApiFactory,
  TableComparisonResultsApiFactory,
  DefaultsApiFactory,
  UsersApiFactory,
  SharedCredentialsApiFactory
} from '../api';

export const ConnectionApiClient = ConnectionsApiFactory(
  new Configuration(),
  '',
  axios
);

export const SchemaApiClient = SchemasApiFactory(
  new Configuration(),
  '',
  axios
);

export const TableApiClient = TablesApiFactory(new Configuration(), '', axios);

export const ColumnApiClient = ColumnsApiFactory(
  new Configuration(),
  '',
  axios
);

export const JobApiClient = JobsApiFactory(new Configuration(), '', axios);

export const DataSourcesApi = DataSourcesApiFactory(
  new Configuration(),
  '',
  axios
);

export const DataGroupingConfigurationsApi =
  DataGroupingConfigurationsApiFactory(new Configuration(), '', axios);

export const CheckResultApi = CheckResultsApiFactory(
  new Configuration(),
  '',
  axios
);

export const CheckResultOverviewApi = CheckResultsOverviewApiFactory(
  new Configuration(),
  '',
  axios
);

export const DashboardsApi = DashboardsApiFactory(
  new Configuration(),
  '',
  axios
);

export const TimezonesApi = TimezonesApiFactory(new Configuration(), '', axios);

export const SensorReadoutsApi = SensorReadoutsApiFactory(
  new Configuration(),
  '',
  axios
);

export const ErrorsApi = ErrorsApiFactory(new Configuration(), '', axios);

export const SensorsApi = SensorsApiFactory(new Configuration(), '', axios);

export const RulesApi = RulesApiFactory(new Configuration(), '', axios);

export const IncidentsApi = IncidentsApiFactory(new Configuration(), '', axios);

export const EnviromentApiClient = EnvironmentApiFactory(
  new Configuration(),
  '',
  axios
);

export const LogErrorsApi = LogShippingApiFactory(
  new Configuration(),
  '',
  axios
);

export const ChecksApi = ChecksApiFactory(new Configuration(), '', axios);

export const TableComparisonsApi = TableComparisonsApiFactory(
  new Configuration(),
  '',
  axios
);

export const TableComparisonResultsApi = TableComparisonResultsApiFactory(
  new Configuration(),
  '',
  axios
);

export const SettingsApi = DefaultsApiFactory(new Configuration(), '', axios);

export const UsersApi = UsersApiFactory(new Configuration(), '', axios);

export const SharedCredentialsApi = SharedCredentialsApiFactory(new Configuration(), '', axios);
