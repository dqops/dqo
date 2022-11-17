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

import { SchemaModel } from '../../api';
import { SCHEMA_ACTION } from '../types';

export interface ISchemaState {
  schemas: SchemaModel[];
  loading: boolean;
  error: any;
}

const initialState: ISchemaState = {
  schemas: [],
  loading: false,
  error: null
};

const schemaReducer = (state = initialState, action: any) => {
  switch (action.type) {
    case SCHEMA_ACTION.GET_SCHEMAS:
      return {
        ...state,
        loading: true
      };
    case SCHEMA_ACTION.GET_SCHEMAS_SUCCESS:
      return {
        ...state,
        loading: false,
        schemas: action.data,
        error: null
      };
    case SCHEMA_ACTION.GET_SCHEMAS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    default:
      return state;
  }
};

export default schemaReducer;
