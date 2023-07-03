import { INestTab, ISourceState } from "./reducers/source.reducer";
import { CheckTypes } from "../shared/routes";
import { ISensorState } from "./reducers/sensor.reducer";
import { IIncidentsState } from "./reducers/incidents.reducer";
import { IRootState } from "./reducers";

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
      sensor: transformSensorState(state.sensor),
      source: transformSourceState(state.source)
    }
    const serializedState = JSON.stringify(newState);

    localStorage.setItem('root', serializedState)
  } catch (err) {
    // Ignore write error
  }
}