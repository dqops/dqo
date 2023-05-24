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

import { JOB_ACTION } from '../types';
import {
  CloudSynchronizationFoldersStatusModel,
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
}

const initialState: IJobsState = {
  loading: false,
  error: null,
  isOpen: false,
  isCleared: false,
  wasOpen: false,
  amounthOfElements: 0,
  isProfileOpen: false
};

const schemaReducer = (state = initialState, action: any) => {
  switch (action.type) {
    case JOB_ACTION.GET_JOBS:
      return {
        ...state,
        loading: true
      };
    case JOB_ACTION.GET_JOBS_SUCCESS:
      return {
        ...state,
        loading: false,
        jobs: action.data,
        lastSequenceNumber: action.data.lastSequenceNumber,
        error: null
      };
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
      const newJobs: DqoJobHistoryEntryModel[] = state.jobs?.jobs || [];
      const jobChanges: DqoJobChangeModel[] = action.data.jobChanges || [];

      jobChanges.map((jobChange) => {
        const existingJob = newJobs.find(
          (job) => job.jobId?.jobId === jobChange.jobId?.jobId
        );
        if (existingJob) {
          if (jobChange.status) {
            existingJob.status = jobChange.status;
          }
          if (jobChange.statusChangedAt) {
            existingJob.statusChangedAt = jobChange.statusChangedAt;
          }
          if (jobChange.updatedModel) {
            Object.assign(existingJob, jobChange.updatedModel);
          }
        } else {
          if (jobChange.updatedModel) {
            newJobs.push(jobChange.updatedModel);
          }
        }
      });

      return {
        ...state,
        loading: false,
        lastSequenceNumber: action.data.lastSequenceNumber,
        jobs: {
          ...state.jobs,
          jobs: newJobs
        },
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
    default:
      return state;
  }
};

export default schemaReducer;
