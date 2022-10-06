import { ConnectionBasicModel, ConnectionModel } from '../../api';
import { CONNECTION_ACTION } from '../types';

export interface IConnectionState {
  connections: ConnectionModel[];
  loading: boolean;
  error: any;
  activeConnection: string;
  connectionBasic?: ConnectionBasicModel;
  isUpdating: boolean;
}

const initialState: IConnectionState = {
  connections: [],
  loading: false,
  error: null,
  activeConnection: '',
  isUpdating: false
};

const connectionReducer = (state = initialState, action: any) => {
  switch (action.type) {
    case CONNECTION_ACTION.GET_CONNECTIONS:
      return {
        ...state,
        loading: true
      };
    case CONNECTION_ACTION.GET_CONNECTIONS_SUCCESS:
      return {
        ...state,
        loading: false,
        connections: action.data,
        error: null
      };
    case CONNECTION_ACTION.GET_CONNECTIONS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case CONNECTION_ACTION.GET_CONNECTION_BASIC:
      return {
        ...state,
        loading: true
      };
    case CONNECTION_ACTION.GET_CONNECTION_BASIC_SUCCESS:
      return {
        ...state,
        loading: false,
        connectionBasic: action.data,
        error: null
      };
    case CONNECTION_ACTION.GET_CONNECTION_BASIC_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case CONNECTION_ACTION.SET_ACTIVE_CONNECTION:
      return {
        ...state,
        activeConnection: action.activeConnection
      };
    case CONNECTION_ACTION.UPDATE_CONNECTION_BASIC:
      return {
        ...state,
        isUpdating: true
      };
    case CONNECTION_ACTION.UPDATE_CONNECTION_BASIC_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case CONNECTION_ACTION.UPDATE_CONNECTION_BASIC_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    default:
      return state;
  }
};

export default connectionReducer;
