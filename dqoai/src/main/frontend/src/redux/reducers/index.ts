import { combineReducers } from 'redux';

import authReducer, { IAuthState } from './auth.reducer';

export interface IRootState {
  auth: IAuthState;
}

const rootReducer = combineReducers<IRootState>({
  auth: authReducer,
});

export default rootReducer;
