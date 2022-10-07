import { combineReducers } from 'redux';

import connectionReducer, { IConnectionState } from './connection.reducer';
import schemaReducer, { ISchemaState } from './schema.reducer';
import tableReducer, { ITableState } from './table.reducer';

export interface IRootState {
  connection: IConnectionState;
  schema: ISchemaState;
  table: ITableState;
}

const rootReducer = combineReducers<IRootState>({
  connection: connectionReducer,
  schema: schemaReducer,
  table: tableReducer
});

export default rootReducer;
