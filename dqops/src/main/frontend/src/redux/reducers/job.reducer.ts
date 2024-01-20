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
  DqoJobHistoryEntryModelJobTypeEnum,
  DqoJobQueueInitialSnapshotModel,
  DqoUserProfileModel,
  ImportTablesQueueJobParameters
} from '../../api';
import moment from 'moment';
import { TJobDictionary, TJobList } from '../../shared/constants';

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
  bool?: boolean;
  dataGrouping: string;
  spec: DataGroupingConfigurationSpec;
  isAdvisorOpen: boolean;
  advisorObject: ImportTablesQueueJobParameters;
  advisorListener: boolean;
  advisorJobId: number;
  isCronScheduled: boolean;
  isLicenseFree: boolean;
  userProfile: DqoUserProfileModel;
  jobList : TJobList;
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
  dataGrouping: '',
  spec: {},
  isAdvisorOpen: false,
  advisorObject: {},
  advisorListener: false,
  advisorJobId: 0,
  isCronScheduled: true,
  isLicenseFree: false,
  userProfile: {},
  jobList : {}
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
        const jobList : TJobList = {};
    action.data.jobs.forEach((item: DqoJobHistoryEntryModel) => {
      
    const jobIdKey = String(item.jobId?.parentJobId?.jobId || item.jobId?.jobId || '');

    if (item.jobId?.parentJobId?.jobId === undefined && !job_dictionary_state[jobIdKey]) {
      job_dictionary_state[jobIdKey] = { ...item, childs: [] };

    } else {

      if (!job_dictionary_state[jobIdKey]) {
        job_dictionary_state[jobIdKey] = { childs: [] };
      }

      const currentState = { ...job_dictionary_state[jobIdKey] };

      job_dictionary_state[jobIdKey] = {
        ...currentState,
        childs: [...currentState.childs, item],
      };

      if (!jobList[jobIdKey]) {
        jobList[jobIdKey] = [];
      }

      jobList[jobIdKey].push(String(item?.jobId?.jobId) || '');
    }});
      console.log(jobList, job_dictionary_state)
      return {
        ...state,
        loading: false,
        job_dictionary_state,
        jobList,
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
      const not_filtered_job_dictionary_state = Object.assign({}, state.job_dictionary_state);
      const notFilteredList = Object.assign({}, state.jobList)

      const filterObject = <T extends Record<string, TJobDictionary>>(
        obj: T
      ): Record<string, TJobDictionary> => {
        const filteredObject: Record<string, TJobDictionary> =Object.assign({}, obj);
          const nowDate = moment();

          //todo : use 2 pointers technique instead of 2 separate loops
          //todo : manage deleting data from jobList if deleting from dictionary

          const typeOccurrences: Record<string, number> = {};
          const reversedKeys = Object.keys(filteredObject).reverse();
          
          for (const key of reversedKeys) {
            if (filteredObject[key]?.jobType === DqoJobHistoryEntryModelJobTypeEnum.synchronize_multiple_folders) {
              typeOccurrences[DqoJobHistoryEntryModelJobTypeEnum.synchronize_multiple_folders] = (typeOccurrences[DqoJobHistoryEntryModelJobTypeEnum.synchronize_multiple_folders] || 0) + 1;
              if (typeOccurrences[DqoJobHistoryEntryModelJobTypeEnum.synchronize_multiple_folders] > 1) {
                delete filteredObject[key];
              }
            }
          }
          for (const key in obj) {
            if (
              (nowDate.diff(obj[key].statusChangedAt, 'minutes') > 30 &&
              obj[key].status !== 'running' &&
              obj[key].status !== 'queued' &&
              obj[key].status !== 'waiting') 
            ) {
              delete filteredObject[key];
            } else {
              break;
            }
          }
        return filteredObject;
      };
      
      jobChanges?.forEach((jobChange : DqoJobChangeModel) => {
        if (!jobChange.jobId?.jobId) return;


        //new parent (list)
        if (jobChange.jobId.parentJobId?.jobId === undefined && !notFilteredList[jobChange.jobId?.jobId]) {
          notFilteredList[jobChange.jobId?.jobId] = [];
        } 
        if (jobChange.jobId.parentJobId?.jobId && !notFilteredList[jobChange.jobId.parentJobId?.jobId]) {
          notFilteredList[jobChange.jobId.parentJobId?.jobId] = [];
        } 

        //new parent (dictionary)
        else if(!not_filtered_job_dictionary_state[jobChange.jobId?.jobId] && jobChange.jobId.parentJobId?.jobId === undefined) {
          const newJobState = Object.assign({}, jobChange);
          if (jobChange.updatedModel) {
            Object.assign(newJobState, jobChange.updatedModel);
            delete newJobState.updatedModel;
          }
            not_filtered_job_dictionary_state[jobChange.jobId?.jobId] = { ...jobChange.updatedModel, childs: []};
        }

        //new child 
        else if (jobChange.jobId.parentJobId?.jobId && 
          !not_filtered_job_dictionary_state[jobChange.jobId?.parentJobId?.jobId]?.childs.find(
          (x) => x.jobId?.jobId === jobChange?.jobId?.jobId) && jobChange.jobId?.jobId) {

          not_filtered_job_dictionary_state[jobChange.jobId.parentJobId?.jobId].childs = 
          [...not_filtered_job_dictionary_state[jobChange.jobId.parentJobId?.jobId].childs, jobChange.updatedModel ?? {}]

          notFilteredList[jobChange.jobId?.parentJobId?.jobId].push(String(jobChange.jobId?.jobId))
        }

      // updated existing parent 
        if (not_filtered_job_dictionary_state[jobChange.jobId?.jobId]) {
            let newJobState = Object.assign({}, not_filtered_job_dictionary_state[jobChange.jobId?.jobId]);
            not_filtered_job_dictionary_state[jobChange.jobId?.jobId] = newJobState;
              if (jobChange.status) {
                newJobState.status = jobChange.status;
              }
              if (jobChange.statusChangedAt) {
                newJobState.statusChangedAt = jobChange.statusChangedAt;
              }
              if (jobChange.updatedModel) {
                newJobState = { ...newJobState, ...jobChange.updatedModel };
              }
        }

        //updated existing child
        else if (
          jobChange.jobId?.parentJobId?.jobId &&
          jobChange?.jobId?.jobId &&
          //todo: make Record<Record to avoid finding child with .find arr func instead index it dict[parentId][childId] 
          not_filtered_job_dictionary_state[jobChange.jobId?.parentJobId?.jobId]?.childs?.find(
            (x) => x.jobId?.jobId === jobChange?.jobId?.jobId
          )
        ) {
          let childState = not_filtered_job_dictionary_state[jobChange.jobId?.parentJobId?.jobId].childs.find(
            (x) => x.jobId?.jobId === jobChange?.jobId?.jobId ?? {}
          );
          if (childState) {
            if (jobChange.status) {
              childState.status = jobChange.status;
            }
            if (jobChange.statusChangedAt) {
              childState.statusChangedAt = jobChange.statusChangedAt;
            }
            if (jobChange.updatedModel) {
              childState = {...childState, ...jobChange.updatedModel};
            }
          }
        }
      });
      console.log(not_filtered_job_dictionary_state, notFilteredList)

      const job_dictionary_state = filterObject(not_filtered_job_dictionary_state);

      const jobList = filterObject(not_filtered_job_dictionary_state);

      return {
        ...state,
        loading: false,
        lastSequenceNumber: action.data.lastSequenceNumber,
        job_dictionary_state,
        jobList,
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
      }
    }
    default:
      return state;
  }
};

export default schemaReducer;
