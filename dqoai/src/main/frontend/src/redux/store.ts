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

import { createStore, applyMiddleware, compose } from 'redux';
import thunk from 'redux-thunk';
import storage from 'redux-persist/lib/storage'
import {
  persistReducer,
} from 'redux-persist'

const persistConfig = {
  key: 'root',
  storage: storage,
}
import rootReducer from './reducers';

const middleware = compose(applyMiddleware(thunk));

const persistedReducer = persistReducer(persistConfig, rootReducer)
const store = createStore(persistedReducer, middleware)
export default store;
