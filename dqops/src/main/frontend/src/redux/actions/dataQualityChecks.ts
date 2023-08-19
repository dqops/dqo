import { Dispatch } from 'redux';

import { SettingsApi } from '../../services/apiClient';
import {  DATA_QUALITY_CHECKS_ACTION } from '../types';
import { AxiosResponse } from 'axios';
import { CheckContainerModel } from '../../api';

export const getdataQualityChecksFolderTreeRequest = () => ({
  type: DATA_QUALITY_CHECKS_ACTION.GET_DATA_QUALITY_CHECKS_FOLDER_TREE
});

export const getdataQualityChecksFolderTreeSuccess = (data: CheckContainerModel) => ({
  type: DATA_QUALITY_CHECKS_ACTION.GET_DATA_QUALITY_CHECKS_FOLDER_TREE_SUCCESS,
  data
});

export const getdataQualityChecksFolderTreeFailed = (error: unknown) => ({
  type: DATA_QUALITY_CHECKS_ACTION.GET_DATA_QUALITY_CHECKS_FOLDER_TREE_ERROR,
  error
});

export const getdataQualityChecksFolderTree = () => async (dispatch: Dispatch) => {
  dispatch(getdataQualityChecksFolderTreeRequest());
  try {
    const res: AxiosResponse<CheckContainerModel> =
      await SettingsApi.getDefaultProfilingTableChecks()
    dispatch(getdataQualityChecksFolderTreeSuccess(res.data));
    console.log(res.data)
  } catch (err) {
    dispatch(getdataQualityChecksFolderTreeFailed(err));
  }
};

export const toggledataQualityChecksFolderTree = (key: string) => ({
  type: DATA_QUALITY_CHECKS_ACTION.TOGGLE_DATA_QUALITY_CHECKS_FOLDER,
  key
});

export const updatedataQualityChecksFolderTree = (data: CheckContainerModel) => ({
  type: DATA_QUALITY_CHECKS_ACTION.UPDATE_DATA_QUALITY_CHECKS_FOLDER_TREE,
  data
});