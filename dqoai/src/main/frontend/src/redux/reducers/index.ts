import { combineReducers } from 'redux';

import connectionReducer, { IConnectionState } from './connection.reducer';
import schemaReducer, { ISchemaState } from './schema.reducer';

export interface IRootState {
  connection: IConnectionState;
  schema: ISchemaState;
}

const rootReducer = combineReducers<IRootState>({
  connection: connectionReducer,
  schema: schemaReducer,
});

export default rootReducer;
