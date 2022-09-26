import { Dispatch } from 'redux';

import { SchemaApiClient } from '../../services/apiClient';
import { SCHEMA_ACTION } from '../types';
import {AxiosResponse} from 'axios';
import {SchemaModel} from '../../api';

export const getSchemasRequest = () => ({
  type: SCHEMA_ACTION.GET_SCHEMAS
});

export const getSchemasSuccess = (data: any) => ({
  type: SCHEMA_ACTION.GET_SCHEMAS_SUCCESS,
  data
});

export const getSchemasFailed = (error: any) => ({
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
