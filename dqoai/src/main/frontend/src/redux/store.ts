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
import { persistReducer } from 'redux-persist'
import storage from 'redux-persist/lib/storage'
const transformSourceState = (state: ISourceState): ISourceState => {
  const newState = {...state};

  return {
    [CheckTypes.SOURCES]: {
      activeTab: newState[CheckTypes.SOURCES].activeTab,
      tabs: newState[CheckTypes.SOURCES].tabs.map((item: INestTab) => ({
        url: item.url,
        value: item.value,
        label: item.label,
        state: {}
      }))
    },
    [CheckTypes.PROFILING]: {
      activeTab: newState[CheckTypes.PROFILING].activeTab,
      tabs: newState[CheckTypes.PROFILING].tabs.map((item: INestTab) => ({
        url: item.url,
        value: item.value,
        label: item.label,
        state: {}
      }))
    },
    [CheckTypes.RECURRING]: {
      activeTab: newState[CheckTypes.RECURRING].activeTab,
      tabs: newState[CheckTypes.RECURRING].tabs.map((item: INestTab) => ({
        url: item.url,
        value: item.value,
        label: item.label,
        state: {}
      }))
    },
    [CheckTypes.PARTITIONED]: {
      activeTab: newState[CheckTypes.PARTITIONED].activeTab,
      tabs: newState[CheckTypes.PARTITIONED].tabs.map((item: INestTab) => ({
        url: item.url,
        value: item.value,
        label: item.label,
        state: {}
      }))
    }
  };
};

const transformSensorState = (state: ISensorState): ISensorState => {
  return {
    sensorState: {},
    tabs: state.tabs.map((item: INestTab) => ({
      url: item.url,
      value: item.value,
      label: item.label,
      state: {}
    })),
    activeTab: state.activeTab
  };
};

const transformIncidentsState = (state: IIncidentsState): IIncidentsState => {
  return {
    connections: [],
    tabs: state.tabs.map((item: INestTab) => ({
      url: item.url,
      value: item.value,
      label: item.label,
      state: {}
    })),
    activeTab: state.activeTab
  };
};

const persistConfig = {
  key: 'root',
  storage,
  whitelist: ['source', 'incidents', 'sensor'],
  stateReconciler: (state: IRootState) => {
    return {
      ...state,
      incidents: transformIncidentsState(state.incidents),
      sensor: transformSensorState(state.sensor),
      source: transformSourceState(state.source)
    }
  }
}


import rootReducer, { IRootState } from './reducers';
import { INestTab, ISourceState } from "./reducers/source.reducer";
import { CheckTypes } from "../shared/routes";
import { ISensorState } from "./reducers/sensor.reducer";
import { IIncidentsState } from "./reducers/incidents.reducer";

const middleware = compose(applyMiddleware(thunk));

const persistedReducer = persistReducer(persistConfig, rootReducer)
const store = createStore(persistedReducer, middleware)
export default store;
