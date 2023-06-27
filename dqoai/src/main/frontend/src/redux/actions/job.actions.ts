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

import { Dispatch } from 'redux';

import { JobApiClient } from '../../services/apiClient';
import { JOB_ACTION } from '../types';
import { AxiosResponse } from 'axios';
import {
  DqoJobQueueIncrementalSnapshotModel,
  DqoJobQueueInitialSnapshotModel
} from '../../api';
import { JOB_CHANGES_RETRY_INTERVAL } from '../../shared/config';
import { CheckTypes } from '../../shared/routes';

export const getJobsRequest = () => ({
  type: JOB_ACTION.GET_JOBS
});

export const getJobsSuccess = (data: DqoJobQueueInitialSnapshotModel) => ({
  type: JOB_ACTION.GET_JOBS_SUCCESS,
  data
});

export const getJobsFailed = (error: unknown) => ({
  type: JOB_ACTION.GET_JOBS_ERROR,
  error
});

export const getAllJobs = () => async (dispatch: Dispatch) => {
  dispatch(getJobsRequest());
  try {
    const res: AxiosResponse<DqoJobQueueInitialSnapshotModel> =
      await JobApiClient.getAllJobs();
    dispatch(getJobsSuccess(res.data));
  } catch (err) {
    dispatch(getJobsFailed(err));
  }
};

export const getJobsChangesRequest = () => ({
  type: JOB_ACTION.GET_JOBS_CHANGES
});

export const clearJobs = (isCleared: boolean) => ({
  type: JOB_ACTION.CLEAR_JOBS,
  isCleared
});

export const getJobsChangesSuccess = (
  data: DqoJobQueueIncrementalSnapshotModel
) => ({
  type: JOB_ACTION.GET_JOBS_CHANGES_SUCCESS,
  data
});

export const getJobsChangesFailed = (error: unknown) => ({
  type: JOB_ACTION.GET_JOBS_CHANGES_ERROR,
  error
});

let timerId: any = 0;

export const getJobsChanges =
  (sequenceNumber: number) => async (dispatch: Dispatch) => {
    if (timerId) {
      clearTimeout(timerId);
    }
    dispatch(getJobsChangesRequest());
    JobApiClient.getJobChangesSince(sequenceNumber)
      .then((res: AxiosResponse<DqoJobQueueIncrementalSnapshotModel>) => {
        dispatch(getJobsChangesSuccess(res.data));
      })
      .catch((err) => {
        dispatch(getJobsChangesFailed(err));
        timerId = setTimeout(() => {
          dispatch(getJobsChanges(sequenceNumber) as any);
          return;
        }, JOB_CHANGES_RETRY_INTERVAL);
      });
  };

export const toggleMenu = (isOpen: boolean) => ({
  type: JOB_ACTION.TOGGLE_MENU,
  isOpen
});
export const reduceCounter = (wasOpen: boolean, amountOfElems?: number) => ({
  type: JOB_ACTION.REDUCE_COUNTER,
  wasOpen,
  amountOfElems
});
export const toggleProfile = (isProfileOpen: boolean) => ({
  type: JOB_ACTION.TOGGLE_PROFILE,
  isProfileOpen
});
export const toggleSettings = (areSettingsOpen: boolean) => ({
  type: JOB_ACTION.TOGGLE_SETTINGS,
  areSettingsOpen
});

export const toggleAdvisor = (isOpen: boolean) => ({
  type: JOB_ACTION.TOGGLE_ADVISOR,
  isOpen
});