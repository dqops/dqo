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

import moment from 'moment';
import {
  CloudSynchronizationFoldersStatusModel,
  DqoJobChangeModel,
  DqoJobHistoryEntryModel,
  DqoJobHistoryEntryModelJobTypeEnum,
  DqoJobQueueInitialSnapshotModel,
  DqoUserProfileModel,
  ImportTablesQueueJobParameters
} from '../../api';
import { TJobDictionary, TJobList } from '../../shared/constants';
import { JOB_ACTION } from '../types';
export interface IJobAllert {
  activeTab?: string;
  action?: string;
  tooltipMessage?: string;
}
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
  job_dictionary_state: Record<string, TJobDictionary>;
  isAdvisorOpen: boolean;
  advisorObject: ImportTablesQueueJobParameters;
  advisorListener: boolean;
  advisorJobId: number;
  isCronScheduled: boolean;
  isLicenseFree: boolean;
  userProfile: DqoUserProfileModel;
  jobList: TJobList;
  isErrorModalOpen: boolean;
  notificationCount: number;
  newNotification: boolean;
  job_allert: IJobAllert;
  isTabChanged: boolean;
  cleanOldJobsInterval?: moment.Moment;
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
  isAdvisorOpen: false,
  advisorObject: {},
  advisorListener: false,
  advisorJobId: 0,
  isCronScheduled: true,
  isLicenseFree: false,
  userProfile: {},
  jobList: {},
  isErrorModalOpen: false,
  notificationCount: 0,
  newNotification: false,
  job_allert: {},
  isTabChanged: false,
  cleanOldJobsInterval: moment()
};

const schemaReducer = (state = initialState, action: any) => {
  switch (action.type) {
    case JOB_ACTION.GET_JOBS:
      return {
        ...state,
        loading: true
      };
    case JOB_ACTION.GET_JOBS_SUCCESS: {
      const job_dictionary_state: Record<string, TJobDictionary> = {};
      const jobList: TJobList = {};
      const nowDate = moment();

      let synchronizeMultipleFoldersCounter = 0;
      let notificationCount = 0;
      action.data.jobs.forEach((item: DqoJobHistoryEntryModel) => {
        const jobIdKey = String(
          item.jobId?.parentJobId?.jobId || item.jobId?.jobId || ''
        );
        if (
          nowDate.diff(item.statusChangedAt, 'minutes') <= 30 ||
          ['running', 'queued', 'waiting'].includes(item.status as string)
        ) {
          if (
            item.jobType ===
            DqoJobHistoryEntryModelJobTypeEnum.synchronize_multiple_folders
          ) {
            synchronizeMultipleFoldersCounter++;
            if (synchronizeMultipleFoldersCounter > 1) return;
          }
          if (
            item.jobId?.parentJobId?.jobId === undefined &&
            !job_dictionary_state[jobIdKey]
          ) {
            job_dictionary_state[jobIdKey] = { ...item, childs: [] };
          } else {
            if (!job_dictionary_state[jobIdKey]) {
              job_dictionary_state[jobIdKey] = { childs: [] };
            }
            if (
              !(
                job_dictionary_state[jobIdKey].childs.length > 10 &&
                ['finished', 'canceled', 'failed'].includes(
                  job_dictionary_state[jobIdKey].status as string
                )
              )
            ) {
              const currentState = { ...job_dictionary_state[jobIdKey] };

              job_dictionary_state[jobIdKey] = {
                ...currentState,
                childs: [...currentState.childs, item]
              };

              if (!jobList[jobIdKey]) {
                jobList[jobIdKey] = [];
              }
              notificationCount++;
              jobList[jobIdKey].push(String(item?.jobId?.jobId) || '');
            }
          }
        }
      });

      return {
        ...state,
        loading: false,
        job_dictionary_state,
        jobList,
        notificationCount,
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
      const job_dictionary_state = {
        ...state.job_dictionary_state
      };
      const jobList = { ...state.jobList };
      let notificationCount = state.notificationCount;

      // filtering out finished jobs
      const filterFinishedJobs = (parentId: number) => {
        const finishedJobs = job_dictionary_state[parentId].childs?.filter(
          (x) =>
            x.status === 'finished' ||
            x.status === 'cancelled' ||
            x.status === 'failed'
        );
        if (finishedJobs?.length > 10) {
          delete job_dictionary_state[finishedJobs?.[0].jobId?.jobId ?? ''];

          jobList[parentId] = jobList[parentId].filter(
            (x) => x !== (finishedJobs?.[0].jobId?.jobId ?? '')
          );
          notificationCount--;
          return;
        }
      };

      jobChanges.forEach((jobChange: DqoJobChangeModel) => {
        if (!jobChange.jobId?.jobId) return;

        const parentId = jobChange.jobId.parentJobId?.jobId;
        const jobId = jobChange.jobId.jobId;

        // New parent (list)
        if (!parentId && !jobList[jobId]) {
          jobList[jobId] = [];
        } else if (parentId && !jobList[parentId]) {
          jobList[parentId] = [];
        }
        // Updated existing parent
        if (job_dictionary_state[jobId]) {
          let newJobState = { ...job_dictionary_state[jobId] };
          if (jobChange.status) newJobState.status = jobChange.status;
          if (jobChange.statusChangedAt)
            newJobState.statusChangedAt = jobChange.statusChangedAt;
          if (jobChange.updatedModel)
            newJobState = { ...newJobState, ...jobChange.updatedModel };
          job_dictionary_state[jobId] = newJobState;
        } else {
          // New parent (dictionary)
          if (!parentId && !job_dictionary_state[jobId]) {
            const newJobState = { ...jobChange.updatedModel, childs: [] };
            job_dictionary_state[jobId] = newJobState;
            notificationCount++;
          }
        }
        // Updated existing child
        if (parentId && jobId && job_dictionary_state[jobId]) {
          const childState = {
            ...job_dictionary_state[jobId],
            status: jobChange.status || job_dictionary_state[jobId].status,
            statusChangedAt:
              jobChange.statusChangedAt ||
              job_dictionary_state[jobId].statusChangedAt,
            ...(jobChange.updatedModel || {})
          };
          const childIndex = job_dictionary_state[parentId].childs.findIndex(
            (child) => child.jobId?.jobId === jobId
          );

          filterFinishedJobs(parentId);

          job_dictionary_state[parentId].childs[childIndex] = childState;
          job_dictionary_state[jobId] = childState;
        } else {
          // New child
          if (parentId && jobId && !job_dictionary_state[jobId]) {
            notificationCount++;
            const childState = jobChange.updatedModel ?? {};
            job_dictionary_state[parentId].childs.push(childState);

            filterFinishedJobs(parentId);

            jobList[parentId].push(String(jobId));
            job_dictionary_state[jobId] = { ...childState } as any;
          }
        }
      });

      const isNewNotification =
        Object.keys(state.job_dictionary_state).length !==
        Object.keys(job_dictionary_state).length;

      const nowDate = moment();

      const lastRefreshExceeded =
        nowDate.diff(state.cleanOldJobsInterval, 'minutes') > 10;

      if (lastRefreshExceeded) {
        Object.keys(job_dictionary_state).forEach((key) => {
          if (
            job_dictionary_state[key]?.statusChangedAt && 
            nowDate.diff(job_dictionary_state[key].statusChangedAt, 'minutes') >
            30
          ) {
            if (jobList[key]) {
              jobList[key].forEach((childId) => {
                delete job_dictionary_state[childId];
              });
              delete jobList[key];
            }
            notificationCount--;
            delete job_dictionary_state[key];
          }
        });
      }

      return {
        ...state,
        loading: false,
        lastSequenceNumber: action.data.lastSequenceNumber,
        newNotification: isNewNotification ? true : state.newNotification,
        job_dictionary_state,
        cleanOldJobsInterval: lastRefreshExceeded
          ? nowDate
          : state.cleanOldJobsInterval,
        jobList,
        notificationCount,
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
        dataGrouping: action.dataGrouping,
        spec: action.spec
      };
    }
    case JOB_ACTION.TOGGLE_ADVISOR:
      return {
        ...state,
        isAdvisorOpen: action.isOpen
      };
    case JOB_ACTION.SET_ADVISOR_OBJECT:
      return {
        ...state,
        advisorObject: action.obj
      };
    case JOB_ACTION.SET_ADVISOR_JOBID:
      return {
        ...state,
        advisorJobId: action.num
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
    case JOB_ACTION.SET_USER_PROFILE: {
      return {
        ...state,
        userProfile: action.userProfile
      };
    }
    case JOB_ACTION.SET_ERRORS: {
      const error = { ...action.error };
      const job_dictionary_state = { ...state.job_dictionary_state };
      const jobList = { ...state.jobList };

      jobList[String('-' + error.date)] = [];
      job_dictionary_state[String('-' + error.date)] = error;

      return {
        ...state,
        jobList,
        job_dictionary_state
      };
    }
    case JOB_ACTION.SET_IS_ERROR_MODAL_OPEN: {
      return {
        ...state,
        isErrorModalOpen: action.isErrorModalOpen
      };
    }
    case JOB_ACTION.SET_NOTIFICATION_COUNT: {
      return {
        ...state,
        notificationCount: action.notificationCount
      };
    }
    case JOB_ACTION.SET_NEW_NOTIFIACTION: {
      return {
        ...state,
        newNotification: action.newNotification
      };
    }
    case JOB_ACTION.SET_JOB_ALLERT: {
      return {
        ...state,
        job_allert: action.job_allert
      };
    }
    case JOB_ACTION.SET_IS_TAB_CHANGED: {
      return {
        ...state,
        isTabChanged: action.isTabChanged
      };
    }
    default:
      return state;
  }
};

export default schemaReducer;
