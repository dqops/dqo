import { combineReducers } from 'redux';

import connectionReducer, { IConnectionState } from './connection.reducer';
import schemaReducer, { ISchemaState } from './schema.reducer';
import tableReducer, { ITableState } from './table.reducer';
import columnReducer, { IColumnState } from './column.reducer';

export interface IRootState {
  connection: IConnectionState;
  schema: ISchemaState;
  table: ITableState;
  column: IColumnState;
}

const rootReducer = combineReducers<IRootState>({
  connection: connectionReducer,
  schema: schemaReducer,
  table: tableReducer,
  column: columnReducer
});

export default rootReducer;
