import { Dispatch } from 'redux';

import { ChecksApi } from '../../services/apiClient';
import { DATA_QUALITY_CHECKS_ACTION } from '../types';
import { AxiosResponse } from 'axios';
import { CheckSpecFolderBasicModel, CheckSpecModel } from '../../api';

export const getdataQualityChecksFolderTreeRequest = () => ({
  type: DATA_QUALITY_CHECKS_ACTION.GET_DATA_QUALITY_CHECKS_FOLDER_TREE
});

export const getdataQualityChecksFolderTreeSuccess = (
  data: CheckSpecFolderBasicModel
) => ({
  type: DATA_QUALITY_CHECKS_ACTION.GET_DATA_QUALITY_CHECKS_FOLDER_TREE_SUCCESS,
  data
});

export const getdataQualityChecksFolderTreeFailed = (error: unknown) => ({
  type: DATA_QUALITY_CHECKS_ACTION.GET_DATA_QUALITY_CHECKS_FOLDER_TREE_ERROR,
  error
});

export const getdataQualityChecksFolderTree =
  () => async (dispatch: Dispatch) => {
    dispatch(getdataQualityChecksFolderTreeRequest());
    try {
      const res: AxiosResponse<CheckSpecFolderBasicModel> =
        await ChecksApi.getCheckFolderTree();
      dispatch(getdataQualityChecksFolderTreeSuccess(res.data));
    } catch (err) {
      dispatch(getdataQualityChecksFolderTreeFailed(err));
    }
  };

export const toggledataQualityChecksFolderTree = (key: string) => ({
  type: DATA_QUALITY_CHECKS_ACTION.TOGGLE_DATA_QUALITY_CHECKS_FOLDER,
  key
});

export const updatedataQualityChecksFolderTree = (
  data: CheckSpecFolderBasicModel
) => ({
  type: DATA_QUALITY_CHECKS_ACTION.UPDATE_DATA_QUALITY_CHECKS_FOLDER_TREE,
  data
});

export const createCheckRequest = () => ({
  type: DATA_QUALITY_CHECKS_ACTION.CREATE_CHECK_DETAIL
});

export const updateCheckRequest = () => ({
  type: DATA_QUALITY_CHECKS_ACTION.UPDATE_CHECK_DETAIL
});

export const deleteCheckRequest = () => ({
  type: DATA_QUALITY_CHECKS_ACTION.DELETE_CHECK_DETAIL
});

export const addFirstLevelTab = (data: any) => ({
  type: DATA_QUALITY_CHECKS_ACTION.ADD_FIRST_LEVEL_TAB,
  data
});

export const setActiveFirstLevelTab = (data: any) => ({
  type: DATA_QUALITY_CHECKS_ACTION.SET_ACTIVE_FIRST_LEVEL_TAB,
  data
});

export const closeFirstLevelTab = (data: any) => ({
  type: DATA_QUALITY_CHECKS_ACTION.CLOSE_FIRST_LEVEL_TAB,
  data
});

export const createCheck =
  (fullCheckName: string, body: CheckSpecModel) =>
  async (dispatch: Dispatch) => {
    dispatch(createCheckRequest());
    try {
      const checkName = fullCheckName.split('/');
      const newObject = Object.assign(
        {},
        { ...body, check_name: checkName[checkName.length - 1], custom: true }
      );
      await ChecksApi.createCheck(fullCheckName, newObject);
    } catch (err) {
      console.error(err);
    }
  };

export const updateCheck =
  (fullCheckName: string, body: CheckSpecModel) =>
  async (dispatch: Dispatch) => {
    dispatch(updateCheckRequest());
    try {
      const checkName = fullCheckName.split('/');
      const newObject = Object.assign(
        {},
        { ...body, check_name: checkName[checkName.length - 1], custom: true }
      );
      await ChecksApi.updateCheck(fullCheckName, newObject);
    } catch (err) {
      console.error(err);
    }
  };

export const deleteCheck =
  (fullCheckName: string) => async (dispatch: Dispatch) => {
    dispatch(deleteCheckRequest());
    try {
      await ChecksApi.deleteCheck(fullCheckName);
    } catch (err) {
      console.error(err);
    }
  };
