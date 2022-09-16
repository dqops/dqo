import { Dispatch } from 'redux';

import { CONNECTION_ACTION } from '../types';
import { ConnectionApiClient } from '../../services/apiClient';

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
    const res = await ConnectionApiClient.getAllConnections();
    dispatch(getConnectionsSuccess(res.data));
  } catch (err) {
    dispatch(getConnectionsFailed(err));
  }
};
