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

export enum CheckTypes {
  RECURRING = 'recurring',
  SOURCES = 'sources',
  PROFILING = 'profiling',
  PARTITIONED = 'partitioned',
}
export const ROUTES = {
  CONNECTION_DETAIL: (checkTypes: string, connection: string, tab: string) => `/${checkTypes}/connection/${connection}/${tab}`,
  SCHEMA_LEVEL_PAGE: (checkTypes: string, connection: string, schema: string, tab: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/${tab}`,
  TABLE_LEVEL_PAGE: (checkTypes: string, connection: string, schema: string, table: string, tab: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}/${tab}`,
  TABLE_PROFILINGS: (checkTypes: string, connection: string, schema: string, table: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}/checks/advanced-profiling`,
  TABLE_PROFILINGS_UI_FILTER: (checkTypes: string, connection: string, schema: string, table: string, category: string, checkName: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}/checks/advanced-profiling/${category}/${checkName}`,
  TABLE_RECURRING: (checkTypes: string, connection: string, schema: string, table: string, timePartitioned: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}/recurring/${timePartitioned}`,
  TABLE_RECURRING_UI_FILTER: (checkTypes: string, connection: string, schema: string, table: string, timePartitioned: string, category: string, checkName: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}/recurring/${timePartitioned}/${category}/${checkName}`,
  TABLE_PARTITIONED: (checkTypes: string, connection: string, schema: string, table: string, timePartitioned: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}/partitioned/${timePartitioned}`,
  TABLE_PARTITIONED_UI_FILTER: (checkTypes: string, connection: string, schema: string, table: string, timePartitioned: string, category: string, checkName: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}/partitioned/${timePartitioned}/${category}/${checkName}`,
  TABLE_COLUMNS: (checkTypes: string, connection: string, schema: string, table: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}/columns/all`,
  COLUMN_LEVEL_PAGE: (checkTypes: string, connection: string, schema: string, table: string, column: string, tab: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}/columns/${column}/${tab}`,
  COLUMN_PROFILINGS: (checkTypes: string, connection: string, schema: string, table: string, column: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}/columns/${column}/checks/advanced-profiling`,
  COLUMN_PROFILINGS_UI_FILTER: (checkTypes: string, connection: string, schema: string, table: string, column: string, category: string, checkName: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}/columns/${column}/checks/advanced-profiling/${category}/${checkName}`,
  COLUMN_RECURRING: (checkTypes: string, connection: string, schema: string, table: string, column: string, timePartitioned: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}/columns/${column}/recurring/${timePartitioned}`,
  COLUMN_RECURRING_UI_FILTER: (checkTypes: string, connection: string, schema: string, table: string, column: string, timePartitioned: string, category: string, checkName: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}/columns/${column}/recurring/${timePartitioned}/${category}/${checkName}`,
  COLUMN_PARTITIONED: (checkTypes: string, connection: string, schema: string, table: string, column: string, timePartitioned: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}/columns/${column}/partitioned/${timePartitioned}`,
  COLUMN_PARTITIONED_UI_FILTER: (checkTypes: string, connection: string, schema: string, table: string, column: string, timePartitioned: string, category: string, checkName: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}/columns/${column}/partitioned/${timePartitioned}/${category}/${checkName}`,
  SENSOR_DETAIL: (sensor: string) => `/definitions/sensors/${sensor}`,
  RULE_DETAIL: (rule: string) => `/definitions/rules/${rule}`,
  INCIDENT_DETAIL: (name: string) => `/incidents/${name}`,

  CONNECTION_LEVEL_VALUE: (checkTypes: string, connection: string) => `/${checkTypes}/connection/${connection}`,
  SCHEMA_LEVEL_VALUE: (checkTypes: string, connection: string, schema: string) => `/${checkTypes}/connection/${connection}/schema/${schema}`,
  TABLE_LEVEL_VALUE: (checkTypes: string, connection: string, schema: string, table: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}`,
  TABLE_PROFILINGS_VALUE: (checkTypes: string, connection: string, schema: string, table: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}/checks/advanced-profiling`,
  TABLE_RECURRING_VALUE: (checkTypes: string, connection: string, schema: string, table: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}/recurring`,
  TABLE_PARTITIONED_VALUE: (checkTypes: string, connection: string, schema: string, table: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}/partitioned`,
  TABLE_COLUMNS_VALUE: (checkTypes: string, connection: string, schema: string, table: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}/columns/all`,
  COLUMN_PROFILINGS_VALUE: (checkTypes: string, connection: string, schema: string, table: string, column: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}/columns/${column}/checks/advanced-profiling`,
  COLUMN_RECURRING_VALUE: (checkTypes: string, connection: string, schema: string, table: string, column: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}/columns/${column}/recurring`,
  COLUMN_PARTITIONED_VALUE: (checkTypes: string, connection: string, schema: string, table: string, column: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}/columns/${column}/partitioned`,
  COLUMN_LEVEL_VALUE: (checkTypes: string, connection: string, schema: string, table: string, column: string) => `/${checkTypes}/connection/${connection}/schema/${schema}/table/${table}/columns/${column}`,
  SENSOR_DETAIL_VALUE: (sensor: string) => `/definitions/sensors/${sensor}`,
  RULE_DETAIL_VALUE: (rule: string) => `/definitions/rules/${rule}`,
  INCIDENT_DETAIL_VALUE: (name: string) => `/incidents/${name}`,

  PATTERNS: {
    INDEX: '/',
    CONNECTION: '/:checkTypes/connection/:connection/:tab',
    SCHEMA: '/:checkTypes/connection/:connection/schema/:schema/:tab',
    TABLE: '/:checkTypes/connection/:connection/schema/:schema/table/:table/:tab',
    TABLE_PROFILINGS: '/:checkTypes/connection/:connection/schema/:schema/table/:table/checks/advanced-profiling',
    TABLE_PROFILINGS_FILTER: '/:checkTypes/connection/:connection/schema/:schema/table/:table/checks/advanced-profiling/:category/:checkName',
    TABLE_RECURRING_DAILY: '/:checkTypes/connection/:connection/schema/:schema/table/:table/recurring/daily',
    TABLE_RECURRING_MONTHLY: '/:checkTypes/connection/:connection/schema/:schema/table/:table/recurring/monthly',
    TABLE_RECURRING_FILTER: '/:checkTypes/connection/:connection/schema/:schema/table/:table/recurring/:timePartitioned/:category/:checkName',
    TABLE_PARTITIONED_DAILY: '/:checkTypes/connection/:connection/schema/:schema/table/:table/partitioned/daily',
    TABLE_PARTITIONED_MONTHLY: '/:checkTypes/connection/:connection/schema/:schema/table/:table/partitioned/monthly',
    TABLE_PARTITIONED_FILTER: '/:checkTypes/connection/:connection/schema/:schema/table/:table/partitioned/:timePartitioned/:category/:checkName',
    TABLE_COLUMNS: '/:checkTypes/connection/:connection/schema/:schema/table/:table/columns/all',
    COLUMN: '/:checkTypes/connection/:connection/schema/:schema/table/:table/columns/:column/:tab',
    COLUMN_PROFILINGS: '/:checkTypes/connection/:connection/schema/:schema/table/:table/columns/:column/checks/advanced-profiling',
    COLUMN_PROFILINGS_FILTER: '/:checkTypes/connection/:connection/schema/:schema/table/:table/columns/:column/checks/advanced-profiling/:category/:checkName',
    COLUMN_RECURRING_DAILY: '/:checkTypes/connection/:connection/schema/:schema/table/:table/columns/:column/recurring/daily',
    COLUMN_RECURRING_MONTHLY: '/:checkTypes/connection/:connection/schema/:schema/table/:table/columns/:column/recurring/monthly',
    COLUMN_RECURRING_FILTER: '/:checkTypes/connection/:connection/schema/:schema/table/:table/columns/:column/recurring/:timePartitioned/:category/:checkName',
    COLUMN_PARTITIONED_DAILY: '/:checkTypes/connection/:connection/schema/:schema/table/:table/columns/:column/partitioned/daily',
    COLUMN_PARTITIONED_MONTHLY: '/:checkTypes/connection/:connection/schema/:schema/table/:table/columns/:column/partitioned/monthly',
    COLUMN_PARTITIONED_FILTER: '/:checkTypes/connection/:connection/schema/:schema/table/:table/columns/:column/partitioned/:timePartitioned/:category/:checkName',
    QUALITY_CHECKS: '/:checkTypes',
    HOME: '/home',
    DASHBOARDS: '/dashboards',
    CREATE: '/create',
    DEFINITIONS: '/definitions',
    INCIDENTS: '/incidents',
    SENSOR_DETAIL: '/definitions/sensors/:sensor',
    RULE_DETAIL: '/definitions/rules/:rule',
    INCIDENT_DETAIL: '/incidents/:connection',
  }
};
