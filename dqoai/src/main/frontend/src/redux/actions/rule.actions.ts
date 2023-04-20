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

import { RulesApi } from '../../services/apiClient';
import { RULE_ACTION } from '../types';
import { AxiosResponse } from 'axios';
import { RuleBasicFolderModel } from "../../api";

export const getRuleFolderTreeRequest = () => ({
  type: RULE_ACTION.GET_RULE_FOLDER_TREE
});

export const getRuleFolderTreeSuccess = (data: RuleBasicFolderModel) => ({
  type: RULE_ACTION.GET_RULE_FOLDER_TREE_SUCCESS,
  data
});

export const getRuleFolderTreeFailed = (error: unknown) => ({
  type: RULE_ACTION.GET_RULE_FOLDER_TREE_ERROR,
  error
});

export const getRuleFolderTree = () => async (dispatch: Dispatch) => {
  dispatch(getRuleFolderTreeRequest());
  try {
    const res: AxiosResponse<RuleBasicFolderModel> =
      await RulesApi.getRuleFolderTree();
    dispatch(getRuleFolderTreeSuccess(res.data));
  } catch (err) {
    dispatch(getRuleFolderTreeFailed(err));
  }
};

export const toggleRuleFolderTree = (key: string) => ({
  type: RULE_ACTION.TOGGLE_RULE_FOLDER,
  key,
});
