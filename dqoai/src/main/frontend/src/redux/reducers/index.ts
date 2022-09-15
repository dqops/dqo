import { combineReducers } from 'redux';

import authReducer, { IAuthState } from './auth.reducer';
import connectionReducer, { IConnectionState } from './connection.reducer';

export interface IRootState {
  auth: IAuthState;
  connection: IConnectionState
}

const rootReducer = combineReducers<IRootState>({
  auth: authReducer,
  connection: connectionReducer
});

export default rootReducer;
