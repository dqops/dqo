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

import { Dispatch } from 'redux';

import { SchemaApiClient } from '../../services/apiClient';
import { SCHEMA_ACTION } from '../types';
import { AxiosResponse } from 'axios';
import { SchemaModel } from '../../api';

export const getSchemasRequest = () => ({
  type: SCHEMA_ACTION.GET_SCHEMAS
});

export const getSchemasSuccess = (data: SchemaModel[]) => ({
  type: SCHEMA_ACTION.GET_SCHEMAS_SUCCESS,
  data
});

export const getSchemasFailed = (error: unknown) => ({
  type: SCHEMA_ACTION.GET_SCHEMAS_ERROR,
  error
});

export const getSchemasByConnection =
  (connectionName: string) => async (dispatch: Dispatch) => {
    dispatch(getSchemasRequest());
    try {
      const res: AxiosResponse<SchemaModel[]> =
        await SchemaApiClient.getSchemas(connectionName);
      dispatch(getSchemasSuccess(res.data));
    } catch (err) {
      dispatch(getSchemasFailed(err));
    }
  };
