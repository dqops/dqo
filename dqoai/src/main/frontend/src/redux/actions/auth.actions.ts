import { Dispatch } from 'redux';

import * as AuthApi from '../../services/auth.api';
import { AUTH_ACTION } from '../types';

export const fetchCurrentUserRequest = () => ({
  type: AUTH_ACTION.FETCH_CURRENT_USER,
});

export const fetchCurrentUserSuccess = (data: any) => ({
  type: AUTH_ACTION.FETCH_CURRENT_USER_SUCCESS,
  data,
});

export const fetchCurrentUserFailed = () => ({
  type: AUTH_ACTION.FETCH_CURRENT_USER_ERROR,
});

export const fetchCurrentUser = () => async (dispatch: Dispatch) => {
  dispatch(fetchCurrentUserRequest());
  try {
    const res = await AuthApi.getCurrentUser();
    dispatch(fetchCurrentUserSuccess(res));
  } catch (err) {
    dispatch(fetchCurrentUserFailed());
  }
};

export const loginRequest = () => ({
  type: AUTH_ACTION.LOGIN_REQUEST,
});

export const loginSuccess = () => ({
  type: AUTH_ACTION.LOGIN_SUCCESS,
});

export const loginFailed = () => ({
  type: AUTH_ACTION.LOGIN_ERROR,
});

export const loginUser = (data: any) => async (dispatch: Dispatch) => {
  dispatch(loginRequest());
  try {
    await AuthApi.login(data);
    await dispatch(loginSuccess());
    await dispatch(fetchCurrentUser() as any);
  } catch (err) {
    dispatch(loginFailed());
  }
};
