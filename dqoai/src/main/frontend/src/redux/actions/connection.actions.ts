import { Dispatch } from 'redux';

import { ConnectionApiClient } from '../../services/apiClient';
import { CONNECTION_ACTION } from '../types';
import { AxiosResponse } from 'axios';
import { ConnectionBasicModel } from '../../api';

export const getConnectionsRequest = () => ({
  type: CONNECTION_ACTION.GET_CONNECTIONS
});

export const getConnectionsSuccess = (data: any) => ({
  type: CONNECTION_ACTION.GET_CONNECTIONS_SUCCESS,
  data
});

export const getConnectionsFailed = (error: any) => ({
  type: CONNECTION_ACTION.GET_CONNECTIONS_ERROR,
  error
});

export const getAllConnections = () => async (dispatch: Dispatch) => {
  dispatch(getConnectionsRequest());
  try {
    const res: AxiosResponse<ConnectionBasicModel[]> =
      await ConnectionApiClient.getAllConnections();
    dispatch(getConnectionsSuccess(res.data));
  } catch (err) {
    dispatch(getConnectionsFailed(err));
  }
};

export const setActiveConnection = (activeConnection: string) => ({
  type: CONNECTION_ACTION.SET_ACTIVE_CONNECTION,
  activeConnection
});

export const getConnectionBasicRequest = () => ({
  type: CONNECTION_ACTION.GET_CONNECTION_BASIC
});

export const getConnectionBasicSuccess = (data: any) => ({
  type: CONNECTION_ACTION.GET_CONNECTION_BASIC_SUCCESS,
  data
});

export const getConnectionBasicFailed = (error: any) => ({
  type: CONNECTION_ACTION.GET_CONNECTION_BASIC_ERROR,
  error
});

export const getConnectionBasic =
  (connectionName: string) => async (dispatch: Dispatch) => {
    dispatch(getConnectionBasicRequest());
    try {
      const res = await ConnectionApiClient.getConnectionBasic(connectionName);
      dispatch(getConnectionBasicSuccess(res.data));
    } catch (err) {
      dispatch(getConnectionsFailed(err));
    }
  };

export const updateConnectionBasicRequest = () => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_BASIC
});

export const updateConnectionBasicSuccess = () => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_BASIC_SUCCESS
});

export const updateConnectionBasicFailed = (error: any) => ({
  type: CONNECTION_ACTION.UPDATE_CONNECTION_BASIC_ERROR,
  error
});

export const updateConnectionBasic =
  (connectionName: string, data: any) => async (dispatch: Dispatch) => {
    dispatch(updateConnectionBasicRequest());
    try {
      await ConnectionApiClient.updateConnectionBasic(connectionName, data);
      dispatch(updateConnectionBasicSuccess());
    } catch (err) {
      dispatch(updateConnectionBasicFailed(err));
    }
  };
