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

import {
  RuleBasicFolderModel,
} from '../../api';
import { RULE_ACTION } from '../types';

export interface IRuleState {
  ruleFolderTree?: RuleBasicFolderModel;
  loading: boolean;
  error: any;
  ruleState: Record<string, boolean>
}

const initialState: IRuleState = {
  loading: false,
  error: null,
  ruleState: {}
};

const ruleReducer = (state = initialState, action: any) => {
  switch (action.type) {
    case RULE_ACTION.GET_RULE_FOLDER_TREE:
      return {
        ...state,
        loading: true
      };
    case RULE_ACTION.GET_RULE_FOLDER_TREE_SUCCESS:
      return {
        ...state,
        loading: false,
        ruleFolderTree: action.data,
        error: null
      };
    case RULE_ACTION.GET_RULE_FOLDER_TREE_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case RULE_ACTION.TOGGLE_RULE_FOLDER:
      return {
        ...state,
        ruleState: {
          ...state.ruleState,
          [action.key]: !state.ruleState[action.key]
        }
      };
    case RULE_ACTION.UPDATE_RULE_FOLDER_TREE:
      return {
        ...state,
        ruleFolderTree: action.data,
      }
    default:
      return state;
  }
};

export default ruleReducer;
