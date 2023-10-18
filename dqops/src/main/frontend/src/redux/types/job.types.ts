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

export enum JOB_ACTION {
  GET_JOBS = 'JOB_ACTION/GET_JOBS',
  GET_JOBS_SUCCESS = 'JOB_ACTION/GET_JOBS_SUCCESS',
  GET_JOBS_ERROR = 'JOB_ACTION/GET_JOBS_ERROR',
  GET_JOBS_CHANGES = 'JOB_ACTION/GET_JOBS_CHANGES',
  GET_JOBS_CHANGES_SUCCESS = 'JOB_ACTION/GET_JOBS_CHANGES_SUCCESS',
  GET_JOBS_CHANGES_ERROR = 'JOB_ACTION/GET_JOBS_CHANGES_ERROR',
  TOGGLE_MENU = 'JOB_ACTION/TOGGLE_MENU',
  CLEAR_JOBS = 'JOB_ACTION/CLEAR_JOBS',
  REDUCE_COUNTER = 'JOB_ACTION/REDUCE_COUNTER',
  NOTIFICATION_NUMBER = 'JOB_ACTION/NOTIFICATION_NUMBER',
  TOGGLE_PROFILE = 'JOB_ACTION/TOGGLE_PROFILE',
  TOGGLE_SETTINGS = 'JOB_ACTION/TOGGLE_SETTINGS',
  SET_CREATED_DATA_STREAM = 'JOB_ACTION/SET_CREATED_DATA_STREAM',
  SET_CURRENT_JOB_ID = 'JOB_ACTION/SET_CURRENT_JOB_ID',
  TOGGLE_ADVISOR = 'JOB_ACTION/TOGGLE_ADVISOR',
  SET_ADVISOR_OBJECT = 'JOB_ACTION/SET_ADVISOR_OBJECT',
  SET_ADVISOR_JOBID = 'JOB_ACTION/SET_ADVISOR_JOBID',
  SET_CRON_SCHEDULER = 'JOB/ACTION/SET_CRON_SCHEDULER',
  SET_IS_LICENSE_FREE = 'JOB_ACTION/SET_IS_LICENSE_FREE',
  SET_USER_PROFILE = 'JOB_ACTION/SET_USER_PROFILE'
}
