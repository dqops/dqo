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

import { JOB_ACTION } from '../types';
import {
  CloudSynchronizationFoldersStatusModel,
  DataGroupingConfigurationSpec,
  DqoJobChangeModel,
  DqoJobHistoryEntryModel,
  DqoJobQueueInitialSnapshotModel
} from '../../api';

export interface IJobsState {
  jobs?: DqoJobQueueInitialSnapshotModel;
  loading: boolean;
  error: any;
  lastSequenceNumber?: number;
  isOpen: boolean;
  folderSynchronizationStatus?: CloudSynchronizationFoldersStatusModel;
  isCleared: boolean;
  wasOpen?: boolean;
  amounthOfElems?: number;
  amounthOfElements: number;
  isProfileOpen: boolean;
  areSettingsOpen: boolean;
  job_dictionary_state: Record<string, DqoJobHistoryEntryModel>;
  bool?: boolean;
  dataStreamName: string;
  spec: DataGroupingConfigurationSpec;
  isAdvisorOpen: boolean;
  isCronScheduled: boolean;
  isLicenseFree: boolean;
}

const initialState: IJobsState = {
  loading: false,
  error: null,
  isOpen: false,
  isCleared: false,
  wasOpen: false,
  amounthOfElements: 0,
  isProfileOpen: false,
  areSettingsOpen: false,
  job_dictionary_state: {},
  bool: false,
  dataStreamName: '',
  spec: {},
  isAdvisorOpen: false,
  isCronScheduled: true,
  isLicenseFree: false
};

const schemaReducer = (state = initialState, action: any) => {
  switch (action.type) {
    case JOB_ACTION.GET_JOBS:
      return {
        ...state,
        loading: true
      };
    case JOB_ACTION.GET_JOBS_SUCCESS: {
      const job_dictionary_state: Record<string, DqoJobHistoryEntryModel> = {};
      action.data.jobs.forEach((item: DqoJobHistoryEntryModel) => {
        job_dictionary_state[item.jobId?.jobId || ''] = item;
      });
      return {
        ...state,
        loading: false,
        job_dictionary_state,
        lastSequenceNumber: action.data.lastSequenceNumber,
        error: null
      };
    }
    case JOB_ACTION.GET_JOBS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case JOB_ACTION.CLEAR_JOBS:
      return {
        ...state
      };
    case JOB_ACTION.GET_JOBS_CHANGES:
      return {
        ...state,
        loading: true
      };
    case JOB_ACTION.GET_JOBS_CHANGES_SUCCESS: {
      const jobChanges: DqoJobChangeModel[] = action.data.jobChanges || [];
      const job_dictionary_state = Object.assign(
        {},
        state.job_dictionary_state
      );

      jobChanges.forEach((jobChange) => {
        if (!jobChange.jobId?.jobId) return;
        if (job_dictionary_state[jobChange.jobId?.jobId]) {
          if (jobChange.status) {
            job_dictionary_state[jobChange.jobId?.jobId].status =
              jobChange.status;
          }
          if (jobChange.statusChangedAt) {
            job_dictionary_state[jobChange.jobId?.jobId].statusChangedAt =
              jobChange.statusChangedAt;
          }
          if (jobChange.updatedModel) {
            Object.assign(
              job_dictionary_state[jobChange.jobId?.jobId],
              jobChange.updatedModel
            );
          }
        } else {
          job_dictionary_state[jobChange.jobId.jobId] = jobChange;
        }
      });

      return {
        ...state,
        loading: false,
        lastSequenceNumber: action.data.lastSequenceNumber,
        job_dictionary_state,
        folderSynchronizationStatus: action.data.folderSynchronizationStatus,
        error: null
      };
    }
    case JOB_ACTION.GET_JOBS_CHANGES_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case JOB_ACTION.TOGGLE_MENU:
      return {
        ...state,
        isOpen: action.isOpen
      };
    case JOB_ACTION.REDUCE_COUNTER:
      return {
        ...state,
        wasOpen: action.wasOpen
      };
    case JOB_ACTION.NOTIFICATION_NUMBER:
      return {
        ...state,
        wasOpen: action.wasOpen
      };
    case JOB_ACTION.TOGGLE_PROFILE:
      return {
        ...state,
        isProfileOpen: action.isProfileOpen
      };
    case JOB_ACTION.TOGGLE_SETTINGS:
      return {
        ...state,
        areSettingsOpen: action.areSettingsOpen
      };
    case JOB_ACTION.SET_CREATED_DATA_STREAM: {
      return {
        ...state,
        bool: action.bool,
        dataStreamName: action.dataStreamName,
        spec: action.spec
      };
    }
    case JOB_ACTION.TOGGLE_ADVISOR:
      return {
        ...state,
        isAdvisorOpen: action.isOpen
      };
    case JOB_ACTION.SET_CRON_SCHEDULER: {
      return {
        ...state,
        isCronScheduled: action.isCronScheduled
      };
    }
    case JOB_ACTION.SET_IS_LICENSE_FREE: {
      return {
        ...state,
        isLicenseFree: action.isLicenseFree
      };
    }
    default:
      return state;
  }
};

export default schemaReducer;
