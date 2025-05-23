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

import { urlencodeDecoder } from '../utils';

export enum CheckTypes {
  MONITORING = 'monitoring',
  SOURCES = 'sources',
  PROFILING = 'profiling',
  PARTITIONED = 'partitioned'
}
export const ROUTES = {
  CONNECTION_DETAIL: (checkTypes: string, connection: string, tab: string) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/${urlencodeDecoder(tab)}`,
  SCHEMA_LEVEL_PAGE: (
    checkTypes: string,
    connection: string,
    schema: string,
    tab: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/${urlencodeDecoder(tab)}`,
  TABLE_LEVEL_PAGE: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string,
    tab: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(
      table
    )}/${urlencodeDecoder(tab)}`,
  TABLE_PROFILING: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(
      table
    )}/checks/advanced-profiling`,
  TABLE_PROFILING_UI_FILTER: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string,
    category: string,
    checkName: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(
      table
    )}/checks/advanced-profiling/${urlencodeDecoder(
      category
    )}/${urlencodeDecoder(checkName)}`,
  TABLE_MONITORING: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string,
    timePartitioned: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(
      table
    )}/monitoring/${urlencodeDecoder(timePartitioned)}`,
  TABLE_MONITORING_UI_FILTER: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string,
    timePartitioned: string,
    category: string,
    checkName: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(
      table
    )}/monitoring/${urlencodeDecoder(timePartitioned)}/${urlencodeDecoder(
      category
    )}/${urlencodeDecoder(checkName)}`,
  TABLE_PARTITIONED: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string,
    timePartitioned: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(
      table
    )}/partitioned/${urlencodeDecoder(timePartitioned)}`,
  TABLE_PARTITIONED_UI_FILTER: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string,
    timePartitioned: string,
    category: string,
    checkName: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(
      table
    )}/partitioned/${urlencodeDecoder(timePartitioned)}/${urlencodeDecoder(
      category
    )}/${urlencodeDecoder(checkName)}`,
  TABLE_COLUMNS: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(
      table
    )}/columns/all`,
  TABLE_INCIDENTS_NOTIFICATION: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(
      table
    )}/incidents/configuration`,
  COLUMN_LEVEL_PAGE: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string,
    column: string,
    tab: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(
      table
    )}/columns/${urlencodeDecoder(column)}/${urlencodeDecoder(tab)}`,
  COLUMN_PROFILING: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string,
    column: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(
      table
    )}/columns/${urlencodeDecoder(column)}/checks/advanced-profiling`,
  COLUMN_PROFILING_UI_FILTER: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string,
    column: string,
    category: string,
    checkName: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(
      table
    )}/columns/${urlencodeDecoder(
      column
    )}/checks/advanced-profiling/${urlencodeDecoder(
      category
    )}/${urlencodeDecoder(checkName)}`,
  COLUMN_MONITORING: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string,
    column: string,
    timePartitioned: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(
      table
    )}/columns/${urlencodeDecoder(column)}/monitoring/${urlencodeDecoder(
      timePartitioned
    )}`,
  COLUMN_MONITORING_UI_FILTER: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string,
    column: string,
    timePartitioned: string,
    category: string,
    checkName: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(
      table
    )}/columns/${urlencodeDecoder(column)}/monitoring/${urlencodeDecoder(
      timePartitioned
    )}/${urlencodeDecoder(category)}/${urlencodeDecoder(checkName)}`,
  COLUMN_PARTITIONED: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string,
    column: string,
    timePartitioned: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(
      table
    )}/columns/${urlencodeDecoder(column)}/partitioned/${urlencodeDecoder(
      timePartitioned
    )}`,
  COLUMN_PARTITIONED_UI_FILTER: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string,
    column: string,
    timePartitioned: string,
    category: string,
    checkName: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(
      table
    )}/columns/${urlencodeDecoder(column)}/partitioned/${urlencodeDecoder(
      timePartitioned
    )}/${urlencodeDecoder(category)}/${urlencodeDecoder(checkName)}`,
  SENSOR_DETAIL: (sensor: string) =>
    `/definitions/sensors/${urlencodeDecoder(sensor)}`,
  RULE_DETAIL: (rule: string) => `/definitions/rules/${urlencodeDecoder(rule)}`,
  CHECK_DETAIL: (check: string) =>
    `/definitions/checks/${urlencodeDecoder(check)}`,
  CHECK_DEFAULT_DETAIL: (defaultCheck: string) =>
    `/definitions/default_checks/${urlencodeDecoder(defaultCheck)}`,
  INCIDENT_CONNECTION: (name: string) => `/incidents/${urlencodeDecoder(name)}`,
  INCIDENT_DETAIL: (name: string, year: number, month: number, id: string) =>
    `/incidents/${urlencodeDecoder(name)}/${year}/${month}/${urlencodeDecoder(
      id
    )}`,
  USERS_LIST_DETAIL: () => '/definitions/manage-users',
  USER_DETAIL: (email: string) =>
    `/definitions/user/${urlencodeDecoder(email)}`,
  SCHEDULES_DEFAULT_DETAIL: () => '/definitions/default-schedules',
  WEBHOOKS_DEFAULT_DETAIL: () => '/definitions/default-notifications',
  SHARED_CREDENTIALS_LIST_DETAIL: () => '/definitions/shared-credentials',
  SHARED_CREDENTIALS_DETAIL: (credential: string) =>
    `/definitions/shared-credential/${urlencodeDecoder(credential)}`,
  DATA_DICTIONARY_LIST_DETAIL: () => '/definitions/data-dictionaries',
  DATA_DICTIONARY_DETAIL: (dictionary: string) =>
    `/definitions/data-dictionary/${urlencodeDecoder(dictionary)}`,
  DEFAULT_CHECKS_PATTERNS: (type: string) =>
    `/definitions/default-check-patterns/${urlencodeDecoder(type)}`,
  DEFAULT_CHECK_PATTERN_DETAIL: (type: string, pattern: string) =>
    `/definitions/default-check/${urlencodeDecoder(type)}/${urlencodeDecoder(
      pattern
    )}`,

  CONNECTION_LEVEL_VALUE: (checkTypes: string, connection: string) =>
    `/${checkTypes}/connection/${urlencodeDecoder(connection)}`,
  SCHEMA_LEVEL_VALUE: (
    checkTypes: string,
    connection: string,
    schema: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}`,
  TABLE_LEVEL_VALUE: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(table)}`,
  TABLE_PROFILING_VALUE: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(
      table
    )}/checks/advanced-profiling`,
  TABLE_MONITORING_VALUE: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string,
    timePartition?: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(table)}/${
      timePartition || 'monitoring'
    }`,
  TABLE_PARTITIONED_VALUE: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string,
    timePartition?: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(table)}/${
      timePartition || 'partitioned'
    }`,
  TABLE_COLUMNS_VALUE: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(
      table
    )}/columns/all`,
  TABLE_INCIDENTS_NOTIFICATION_VALUE: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(
      table
    )}/incidents/configuration`,
  COLUMN_PROFILING_VALUE: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string,
    column: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(
      table
    )}/columns/${urlencodeDecoder(column)}/checks/advanced-profiling`,
  COLUMN_MONITORING_VALUE: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string,
    column: string,
    timePartition?: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(
      table
    )}/columns/${urlencodeDecoder(column)}/${timePartition || 'monitoring'}`,
  COLUMN_PARTITIONED_VALUE: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string,
    column: string,
    timePartition?: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(
      table
    )}/columns/${urlencodeDecoder(column)}/${timePartition || 'partitioned'}`,
  COLUMN_LEVEL_VALUE: (
    checkTypes: string,
    connection: string,
    schema: string,
    table: string,
    column: string
  ) =>
    `/${checkTypes}/connection/${urlencodeDecoder(
      connection
    )}/schema/${urlencodeDecoder(schema)}/table/${urlencodeDecoder(
      table
    )}/columns/${urlencodeDecoder(column)}`,
  SENSOR_DETAIL_VALUE: (sensor: string) =>
    `/definitions/sensors/${urlencodeDecoder(sensor)}`,
  RULE_DETAIL_VALUE: (rule: string) =>
    `/definitions/rules/${urlencodeDecoder(rule)}`,
  CHECK_DETAIL_VALUE: (check: string) =>
    `/definitions/checks/${urlencodeDecoder(check)}`,
  CHECK_DEFAULT_DETAIL_VALUE: (defaultCheck: string) =>
    `/definitions/default_checks/${urlencodeDecoder(defaultCheck)}`,
  INCIDENT_CONNECTION_VALUE: (name: string) =>
    `/incidents/${urlencodeDecoder(name)}`,
  INCIDENT_DETAIL_VALUE: (
    name: string,
    year: number,
    month: number,
    id: string
  ) =>
    `/incidents/${urlencodeDecoder(name)}/${year}/${month}/${urlencodeDecoder(
      id
    )}`,
  USERS_LIST_DETAIL_VALUE: () => '/definitions/manage-users',
  USER_DETAIL_VALUE: (email: string) =>
    `/definitions/user/${urlencodeDecoder(email)}`,
  SCHEDULES_DEFAULT_DETAIL_VALUE: () => '/definitions/default-schedules',
  WEBHOOKS_DEFAULT_DETAIL_VALUE: () => '/definitions/default-notifications',
  SHARED_CREDENTIALS_LIST_DETAIL_VALUE: () => '/definitions/shared-credentials',
  SHARED_CREDENTIALS_DETAIL_VALUE: (credential: string) =>
    `/definitions/shared-credential/${urlencodeDecoder(credential)}`,
  DATA_DICTIONARY_LIST_VALUE: () => '/definitions/data-dictionaries',
  DATA_DICTIONARY_VALUE: (dictionary: string) =>
    `/definitions/data-dictionary/${urlencodeDecoder(dictionary)}`,
  DEFAULT_CHECKS_PATTERNS_VALUE: () => `/definitions/default-check-patterns`,
  DEFAULT_CHECK_PATTERN_VALUE: (type: string, pattern: string) =>
    `/definitions/default-check/${urlencodeDecoder(type)}/${urlencodeDecoder(
      pattern
    )}`,
  DATA_DOMAINS: () => '/data-domains',
  DATA_DOMAIN_DETAIL: (domain: string) =>
    `/data-domain/${urlencodeDecoder(domain)}`,

  PATTERNS: {
    INDEX: '/',
    CONNECTION: '/:checkTypes/connection/:connection/:tab',
    SCHEMA: '/:checkTypes/connection/:connection/schema/:schema/:tab',
    TABLE:
      '/:checkTypes/connection/:connection/schema/:schema/table/:table/:tab',
    TABLE_PROFILING:
      '/:checkTypes/connection/:connection/schema/:schema/table/:table/checks/advanced-profiling',
    TABLE_PROFILING_FILTER:
      '/:checkTypes/connection/:connection/schema/:schema/table/:table/checks/advanced-profiling/:category/:checkName',
    TABLE_MONITORING_DAILY:
      '/:checkTypes/connection/:connection/schema/:schema/table/:table/monitoring/daily',
    TABLE_MONITORING_MONTHLY:
      '/:checkTypes/connection/:connection/schema/:schema/table/:table/monitoring/monthly',
    TABLE_MONITORING_FILTER:
      '/:checkTypes/connection/:connection/schema/:schema/table/:table/monitoring/:timePartitioned/:category/:checkName',
    TABLE_PARTITIONED_DAILY:
      '/:checkTypes/connection/:connection/schema/:schema/table/:table/partitioned/daily',
    TABLE_PARTITIONED_MONTHLY:
      '/:checkTypes/connection/:connection/schema/:schema/table/:table/partitioned/monthly',
    TABLE_PARTITIONED_FILTER:
      '/:checkTypes/connection/:connection/schema/:schema/table/:table/partitioned/:timePartitioned/:category/:checkName',
    TABLE_COLUMNS:
      '/:checkTypes/connection/:connection/schema/:schema/table/:table/columns/all',
    TABLE_INCIDENTS_NOTIFICATION:
      '/:checkTypes/connection/:connection/schema/:schema/table/:table/incidents/configuration',
    COLUMN:
      '/:checkTypes/connection/:connection/schema/:schema/table/:table/columns/:column/:tab',
    COLUMN_PROFILING:
      '/:checkTypes/connection/:connection/schema/:schema/table/:table/columns/:column/checks/advanced-profiling',
    COLUMN_PROFILING_FILTER:
      '/:checkTypes/connection/:connection/schema/:schema/table/:table/columns/:column/checks/advanced-profiling/:category/:checkName',
    COLUMN_MONITORING_DAILY:
      '/:checkTypes/connection/:connection/schema/:schema/table/:table/columns/:column/monitoring/daily',
    COLUMN_MONITORING_MONTHLY:
      '/:checkTypes/connection/:connection/schema/:schema/table/:table/columns/:column/monitoring/monthly',
    COLUMN_MONITORING_FILTER:
      '/:checkTypes/connection/:connection/schema/:schema/table/:table/columns/:column/monitoring/:timePartitioned/:category/:checkName',
    COLUMN_PARTITIONED_DAILY:
      '/:checkTypes/connection/:connection/schema/:schema/table/:table/columns/:column/partitioned/daily',
    COLUMN_PARTITIONED_MONTHLY:
      '/:checkTypes/connection/:connection/schema/:schema/table/:table/columns/:column/partitioned/monthly',
    COLUMN_PARTITIONED_FILTER:
      '/:checkTypes/connection/:connection/schema/:schema/table/:table/columns/:column/partitioned/:timePartitioned/:category/:checkName',
    QUALITY_CHECKS: '/:checkTypes',
    HOME: '/home',
    DATA_QUALITY_SUMMARY: '/data-quality-summary',
    TABLES: '/tables',
    COLUMNS: '/columns',
    GLOBAL_INCIDENTS: '/global-incidents',
    DASHBOARDS: '/dashboards',
    CREATE: '/:checkTypes/create',
    DEFINITIONS: '/definitions',
    INCIDENTS: '/incidents',
    SENSOR_DETAIL: '/definitions/sensors/:sensor',
    RULE_DETAIL: '/definitions/rules/:rule',
    CHECK_DETAIL: '/definitions/checks/:check',
    CHECK_DEFAULT_DETAIL: '/definitions/default_checks/:defaultCheck',
    INCIDENT_CONNECTION: '/incidents/:connection',
    INCIDENT_DETAIL: '/incidents/:connection/:year/:month/:id',
    USERS_LIST_DETAIL: '/definitions/manage-users',
    USER_DETAIL: '/definitions/user/:email',
    SCHEDULES_DEFAULT_DETAIL: '/definitions/default-schedules',
    WEBHOOKS_DEFAULT_DETAIL: '/definitions/default-notifications',
    SHARED_CREDENTIALS_LIST_DETAIL: '/definitions/shared-credentials',
    SHARED_CREDENTIALS_DETAIL: '/definitions/shared-credential/:credential',
    DATA_DICTIONARY_LIST_DETAIL: '/definitions/data-dictionaries',
    DATA_DICTIONARY_DETAIL: `/definitions/data-dictionary/:dictionary`,
    DEFAULT_CHECKS_PATTERNS: `/definitions/default-check-patterns/:type`,
    DEFAULT_CHECK_PATTERN_DETAIL: `/definitions/default-check/:type/:pattern`,
    DATA_DOMAINS: '/data-domains',
    DATA_DOMAIN_DETAIL: '/data-domain/:domain'
  }
};
