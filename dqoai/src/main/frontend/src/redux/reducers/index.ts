import { combineReducers } from 'redux';

import connectionReducer, { IConnectionState } from './connection.reducer';

export interface IRootState {
  connection: IConnectionState;
}

const rootReducer = combineReducers<IRootState>({
  connection: connectionReducer,
});

export default rootReducer;
