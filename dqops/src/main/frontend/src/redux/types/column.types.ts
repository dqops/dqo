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

export enum COLUMN_ACTION {
  GET_COLUMNS = 'COLUMN_ACTION/GET_COLUMNS',
  GET_COLUMNS_SUCCESS = 'COLUMN_ACTION/GET_COLUMNS_SUCCESS',
  GET_COLUMNS_ERROR = 'COLUMN_ACTION/GET_COLUMNS_ERROR',
  GET_COLUMN_BASIC = 'COLUMN_ACTION/GET_COLUMN_BASIC',
  GET_COLUMN_BASIC_SUCCESS = 'COLUMN_ACTION/GET_COLUMN_BASIC_SUCCESS',
  GET_COLUMN_BASIC_ERROR = 'COLUMN_ACTION/GET_COLUMN_BASIC_ERROR',
  UPDATE_COLUMN_BASIC = 'COLUMN_ACTION/UPDATE_COLUMN_BASIC',
  UPDATE_COLUMN_BASIC_SUCCESS = 'COLUMN_ACTION/UPDATE_COLUMN_BASIC_SUCCESS',
  UPDATE_COLUMN_BASIC_ERROR = 'COLUMN_ACTION/UPDATE_COLUMN_BASIC_ERROR',
  GET_COLUMN_COMMENTS = 'COLUMN_ACTION/GET_COLUMN_COMMENTS',
  GET_COLUMN_COMMENTS_SUCCESS = 'COLUMN_ACTION/GET_COLUMN_COMMENTS_SUCCESS',
  GET_COLUMN_COMMENTS_ERROR = 'COLUMN_ACTION/GET_COLUMN_COMMENTS_ERROR',
  UPDATE_COLUMN_COMMENTS = 'COLUMN_ACTION/UPDATE_COLUMN_COMMENTS',
  UPDATE_COLUMN_COMMENTS_SUCCESS = 'COLUMN_ACTION/UPDATE_COLUMN_COMMENTS_SUCCESS',
  UPDATE_COLUMN_COMMENTS_ERROR = 'COLUMN_ACTION/UPDATE_COLUMN_COMMENTS_ERROR',
  GET_COLUMN_LABELS = 'COLUMN_ACTION/GET_COLUMN_LABELS',
  GET_COLUMN_LABELS_SUCCESS = 'COLUMN_ACTION/GET_COLUMN_LABELS_SUCCESS',
  GET_COLUMN_LABELS_ERROR = 'COLUMN_ACTION/GET_COLUMN_LABELS_ERROR',
  UPDATE_COLUMN_LABELS = 'COLUMN_ACTION/UPDATE_COLUMN_LABELS',
  UPDATE_COLUMN_LABELS_SUCCESS = 'COLUMN_ACTION/UPDATE_COLUMN_LABELS_SUCCESS',
  UPDATE_COLUMN_LABELS_ERROR = 'COLUMN_ACTION/UPDATE_COLUMN_LABELS_ERROR',
  GET_COLUMN_CHECKS_UI = 'COLUMN_ACTION/GET_COLUMN_CHECKS_UI',
  GET_COLUMN_CHECKS_UI_SUCCESS = 'COLUMN_ACTION/GET_COLUMN_CHECKS_UI_SUCCESS',
  GET_COLUMN_CHECKS_UI_ERROR = 'COLUMN_ACTION/GET_COLUMN_CHECKS_UI_ERROR',
  UPDATE_COLUMN_CHECKS_UI = 'COLUMN_ACTION/UPDATE_COLUMN_CHECKS_UI',
  UPDATE_COLUMN_CHECKS_UI_SUCCESS = 'COLUMN_ACTION/UPDATE_COLUMN_CHECKS_UI_SUCCESS',
  UPDATE_COLUMN_CHECKS_UI_ERROR = 'COLUMN_ACTION/UPDATE_COLUMN_CHECKS_UI_ERROR',
  GET_COLUMN_DAILY_MONITORING = 'COLUMN_ACTION/GET_COLUMN_DAILY_MONITORING',
  GET_COLUMN_DAILY_MONITORING_SUCCESS = 'COLUMN_ACTION/GET_COLUMN_DAILY_MONITORING_SUCCESS',
  GET_COLUMN_DAILY_MONITORING_ERROR = 'COLUMN_ACTION/GET_COLUMN_DAILY_MONITORING_ERROR',
  UPDATE_COLUMN_DAILY_MONITORING = 'COLUMN_ACTION/UPDATE_COLUMN_DAILY_MONITORING',
  UPDATE_COLUMN_DAILY_MONITORING_SUCCESS = 'COLUMN_ACTION/UPDATE_COLUMN_DAILY_MONITORING_SUCCESS',
  UPDATE_COLUMN_DAILY_MONITORING_ERROR = 'COLUMN_ACTION/UPDATE_COLUMN_DAILY_MONITORING_ERROR',
  GET_COLUMN_MONTHLY_MONITORING = 'COLUMN_ACTION/GET_COLUMN_MONTHLY_MONITORING',
  GET_COLUMN_MONTHLY_MONITORING_SUCCESS = 'COLUMN_ACTION/GET_COLUMN_MONTHLY_MONITORING_SUCCESS',
  GET_COLUMN_MONTHLY_MONITORING_ERROR = 'COLUMN_ACTION/GET_COLUMN_MONTHLY_MONITORING_ERROR',
  UPDATE_COLUMN_MONTHLY_MONITORING = 'COLUMN_ACTION/UPDATE_COLUMN_MONTHLY_MONITORING',
  UPDATE_COLUMN_MONTHLY_MONITORING_SUCCESS = 'COLUMN_ACTION/UPDATE_COLUMN_MONTHLY_MONITORING_SUCCESS',
  UPDATE_COLUMN_MONTHLY_MONITORING_ERROR = 'COLUMN_ACTION/UPDATE_COLUMN_MONTHLY_MONITORING_ERROR',
  GET_COLUMN_PARTITIONED_DAILY_CHECKS = 'COLUMN_ACTION/GET_COLUMN_PARTITIONED_DAILY_CHECKS',
  GET_COLUMN_PARTITIONED_DAILY_CHECKS_SUCCESS = 'COLUMN_ACTION/GET_COLUMN_PARTITIONED_DAILY_CHECKS_SUCCESS',
  GET_COLUMN_PARTITIONED_DAILY_CHECKS_ERROR = 'COLUMN_ACTION/GET_COLUMN_PARTITIONED_DAILY_CHECKS_ERROR',
  UPDATE_COLUMN_PARTITIONED_DAILY_CHECKS = 'COLUMN_ACTION/UPDATE_COLUMN_PARTITIONED_DAILY_CHECKS',
  UPDATE_COLUMN_PARTITIONED_DAILY_CHECKS_SUCCESS = 'COLUMN_ACTION/UPDATE_COLUMN_PARTITIONED_DAILY_CHECKS_SUCCESS',
  UPDATE_COLUMN_PARTITIONED_DAILY_CHECKS_ERROR = 'COLUMN_ACTION/UPDATE_COLUMN_PARTITIONED_DAILY_CHECKS_ERROR',
  GET_COLUMN_PARTITIONED_MONTHLY_CHECKS = 'COLUMN_ACTION/GET_COLUMN_PARTITIONED_MONTHLY_CHECKS',
  GET_COLUMN_PARTITIONED_MONTHLY_CHECKS_SUCCESS = 'COLUMN_ACTION/GET_COLUMN_PARTITIONED_MONTHLY_CHECKS_SUCCESS',
  GET_COLUMN_PARTITIONED_MONTHLY_CHECKS_ERROR = 'COLUMN_ACTION/GET_COLUMN_PARTITIONED_MONTHLY_CHECKS_ERROR',
  UPDATE_COLUMN_PARTITIONED_MONTHLY_CHECKS = 'COLUMN_ACTION/UPDATE_COLUMN_PARTITIONED_MONTHLY_CHECKS',
  UPDATE_COLUMN_PARTITIONED_MONTHLY_CHECKS_SUCCESS = 'COLUMN_ACTION/UPDATE_COLUMN_PARTITIONED_MONTHLY_CHECKS_SUCCESS',
  UPDATE_COLUMN_PARTITIONED_MONTHLY_CHECKS_ERROR = 'COLUMN_ACTION/UPDATE_COLUMN_PARTITIONED_MONTHLY_CHECKS_ERROR',
  SET_UPDATED_COLUMN_BASIC = 'COLUMN_ACTION/SET_UPDATED_COLUMN_BASIC',
  SET_IS_UPDATED_COLUMN_BASIC = 'COLUMN_ACTION/SET_IS_UPDATED_COLUMN_BASIC',
  SET_UPDATED_COMMENTS = 'COLUMN_ACTION/SET_UPDATED_COMMENTS',
  SET_IS_UPDATED_COMMENTS = 'COLUMN_ACTION/SET_IS_UPDATED_COMMENTS',
  SET_UPDATED_LABELS = 'COLUMN_ACTION/SET_UPDATED_LABELS',
  SET_IS_UPDATED_LABELS = 'COLUMN_ACTION/SET_IS_UPDATED_LABELS',
  SET_UPDATED_CHECKS_UI = 'COLUMN_ACTION/SET_UPDATED_CHECKS_UI',
  SET_COLUMN_DAILY_MONITORING = 'COLUMN_ACTION/SET_COLUMN_DAILY_MONITORING',
  SET_COLUMN_MONTHLY_MONITORING = 'COLUMN_ACTION/SET_COLUMN_MONTHLY_MONITORING',
  SET_COLUMN_PARTITIONED_DAILY_CHECKS = 'COLUMN_ACTION/SET_COLUMN_PARTITIONED_DAILY_CHECKS',
  SET_COLUMN_PARTITIONED_MONTHLY_CHECKS = 'COLUMN_ACTION/SET_COLUMN_PARTITIONED_MONTHLY_CHECKS',
  GET_COLUMN_PROFILINGS_CHECKS_UI_FILTER = 'COLUMN_ACTION/GET_COLUMN_PROFILINGS_CHECKS_UI_FILTER',
  GET_COLUMN_PROFILINGS_CHECKS_UI_FILTER_SUCCESS = 'COLUMN_ACTION/GET_COLUMN_PROFILINGS_CHECKS_UI_FILTER_SUCCESS',
  GET_COLUMN_PROFILINGS_CHECKS_UI_FILTER_ERROR = 'COLUMN_ACTION/GET_COLUMN_PROFILINGS_CHECKS_UI_FILTER_ERROR',
  GET_COLUMN_MONITORING_UI_FILTER = 'COLUMN_ACTION/GET_COLUMN_MONITORING_UI_FILTER',
  GET_COLUMN_MONITORING_UI_FILTER_SUCCESS = 'COLUMN_ACTION/GET_COLUMN_MONITORING_UI_FILTER_SUCCESS',
  GET_COLUMN_MONITORING_UI_FILTER_ERROR = 'COLUMN_ACTION/GET_COLUMN_MONITORING_UI_FILTER_ERROR',
  GET_COLUMN_PARTITIONED_CHECKS_UI_FILTER = 'COLUMN_ACTION/GET_COLUMN_PARTITIONED_CHECKS_UI_FILTER',
  GET_COLUMN_PARTITIONED_CHECKS_UI_FILTER_SUCCESS = 'COLUMN_ACTION/GET_COLUMN_PARTITIONED_CHECKS_UI_FILTER_SUCCESS',
  GET_COLUMN_PARTITIONED_CHECKS_UI_FILTER_ERROR = 'COLUMN_ACTION/GET_COLUMN_PARTITIONED_CHECKS_UI_FILTER_ERROR',
  SET_UPDATED_CHECKS_UI_FILTER = 'COLUMN_ACTION/SET_UPDATED_CHECKS_UI_FILTER',
  SET_UPDATED_MONITORING_UI_FILTER = 'COLUMN_ACTION/SET_UPDATED_MONITORING_UI_FILTER',
  SET_UPDATED_PARTITIONED_CHECKS_UI_FILTER = 'COLUMN_ACTION/SET_UPDATED_PARTITIONED_CHECKS_UI_FILTER',
}
