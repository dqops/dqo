import { Dispatch } from 'redux';

import * as ConnectionApi from '../../services/connection.api';
import { CONNECTION_ACTION } from '../types';

export const getConnectionsRequest = () => ({
  type: CONNECTION_ACTION.GET_CONNECTIONS,
});

export const getConnectionsSuccess = (data: any) => ({
  type: CONNECTION_ACTION.GET_CONNECTIONS_SUCCESS,
  data,
});

export const getConnectionsFailed = (error: any) => ({
  type: CONNECTION_ACTION.GET_CONNECTIONS_ERROR,
  error
});

export const getAllConnections = () => async (dispatch: Dispatch) => {
  dispatch(getConnectionsRequest());
  try {
    const res = await ConnectionApi.getAllConnections();
    dispatch(getConnectionsSuccess(res));
  } catch (err) {
    dispatch(getConnectionsFailed(err));
  }
};
