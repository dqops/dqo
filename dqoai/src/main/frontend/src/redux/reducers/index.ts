///
/// Copyright © 2021 DQO.ai (support@dqo.ai)
///
/// Licensed under the Apache License, Version 2.0 (the "License");
/// you may not use this file except in compliance with the License.
/// You may obtain a copy of the License at
///
///     http://www.apache.org/licenses/LICENSE-2.0
///
/// Unless required by applicable law or agreed to in writing, software
/// distributed under the License is distributed on an "AS IS" BASIS,
/// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
/// See the License for the specific language governing permissions and
/// limitations under the License.
///

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
