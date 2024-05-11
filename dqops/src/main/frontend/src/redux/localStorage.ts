import { CheckTypes } from '../shared/routes';
import { IRootState } from './reducers';
import { IDefinitionState } from './reducers/definition.reducer';
import { IIncidentsState } from './reducers/incidents.reducer';
import { INestTab, ISourceState } from './reducers/source.reducer';

const transformSourceState = (state: ISourceState): ISourceState => {
  const newState = { ...state };

  return {
    [CheckTypes.SOURCES]: {
      activeTab: newState[CheckTypes.SOURCES]?.activeTab,
      tabs: newState[CheckTypes.SOURCES]?.tabs?.map((item: INestTab) => ({
        url: item.url,
        value: item.value,
        label: item.label,
        state: {}
      })) ?? []
    },
    [CheckTypes.PROFILING]: {
      activeTab: newState[CheckTypes.PROFILING]?.activeTab,
      tabs: newState[CheckTypes.PROFILING]?.tabs?.map((item: INestTab) => ({
        url: item.url,
        value: item.value,
        label: item.label,
        state: {}
      })) ?? []
    },
    [CheckTypes.MONITORING]: {
      activeTab: newState[CheckTypes.MONITORING]?.activeTab,
      tabs: newState[CheckTypes.MONITORING]?.tabs?.map((item: INestTab) => ({
        url: item.url,
        value: item.value,
        label: item.label,
        state: {}
      })) ?? []
    },
    [CheckTypes.PARTITIONED]: {
      activeTab: newState[CheckTypes.PARTITIONED]?.activeTab,
      tabs: newState[CheckTypes.PARTITIONED]?.tabs?.map((item: INestTab) => ({
        url: item.url,
        value: item.value,
        label: item.label,
        state: {}
      })) ?? []
    },
    ['home'] : {
      activeTab: '/home'
    }
  };
};

const transformSensorState = (state: IDefinitionState): IDefinitionState => {
  return {
    sensorState: {},
    ruleState: {},
    dataQualityChecksState: {},
    tabs: state?.tabs?.map((item: INestTab, index: number) => ({
      url: item.url,
      value: item.value,
      label: item.label,
      state: state?.tabs[index]?.state
    })) ?? [],
    activeTab: state?.activeTab
  };
};

const transformIncidentsState = (state: IIncidentsState): IIncidentsState => {
  return {
    connections: [],
    tabs: state?.tabs?.map((item: INestTab) => ({
      url: item.url,
      value: item.value,
      label: item.label,
      state: {}
    })) ?? [],
    activeTab: state?.activeTab
  };
};

export const loadState = () => {
  try {
    const serializedState = localStorage.getItem('root');
    if (serializedState === null) return undefined;

    return JSON.parse(serializedState);
  } catch (err) {
    return undefined;
  }
};

export const saveState = (state: IRootState) => {
  try {
    const newState = {
      incidents: transformIncidentsState(state.incidents),
      definition: transformSensorState(state.definition),
      source: transformSourceState(state.source)
    };
    const serializedState = JSON.stringify(newState);

    localStorage.setItem('root', serializedState);
  } catch (err) {
    console.error(err);
  }
};
