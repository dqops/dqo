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

import axios from 'axios';

import {
  ConnectionsApiFactory,
  Configuration,
  SchemasApiFactory,
  TablesApiFactory,
  ColumnsApiFactory,
  JobsApiFactory,
  SourceSchemasApiFactory,
  SourceTablesApiFactory,
  DataStreamsApiFactory, CheckResultsOverviewApiFactory, DashboardsApiFactory
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

export const SourceSchemasApi = SourceSchemasApiFactory(
  new Configuration(),
  '',
  axios
);

export const SourceTableApi = SourceTablesApiFactory(
  new Configuration(),
  '',
  axios
);

export const DataStreamsApi = DataStreamsApiFactory(
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
