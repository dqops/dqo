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

export enum DATABASE_TYPE {
  BIGQUERY = 'bigquery',
  SNOWFLAKE = 'snowflake'
}

export enum TREE_LEVEL {
  DATABASE = 'database',
  SCHEMA = 'schema',
  TABLE = 'table',
  COLUMNS = 'columns',

  TABLE_INCIDENTS = 'table_incidents',
  TABLE_CHECKS = 'table_checks',
  TABLE_DAILY_CHECKS = 'table_daily_checks',
  TABLE_MONTHLY_CHECKS = 'table_monthly_checks',
  TABLE_PARTITIONED_DAILY_CHECKS = 'table_partitioned_daily_checks',
  TABLE_PARTITIONED_MONTHLY_CHECKS = 'table_partitioned_monthly_checks',
  COLUMN_CHECKS = 'column_checks',
  COLUMN_DAILY_CHECKS = 'column_daily_checks',
  COLUMN_MONTHLY_CHECKS = 'column_monthly_checks',
  COLUMN_PARTITIONED_DAILY_CHECKS = 'column_partitioned_daily_checks',
  COLUMN_PARTITIONED_MONTHLY_CHECKS = 'column_partitioned_monthly_checks',
  COLUMN = 'column',
  ROOT = 'root',
  CHECK = 'check'
}
