import { AUTH_ACTION } from '../types';

export interface IAuthState {
  user: any;
  loading: boolean;
}

const initialState: IAuthState = {
  user: null,
  loading: false,
};

const authReducer = (state = initialState, action: any) => {
  switch (action.type) {
    case AUTH_ACTION.FETCH_CURRENT_USER:
      return {
        ...state,
        loading: true,
      };
    case AUTH_ACTION.FETCH_CURRENT_USER_SUCCESS:
      return {
        ...state,
        loading: false,
        user: action.data,
      };
    case AUTH_ACTION.FETCH_CURRENT_USER_ERROR:
      return {
        ...state,
        loading: false,
      };
    default:
      return state;
  }
};

export default authReducer;
