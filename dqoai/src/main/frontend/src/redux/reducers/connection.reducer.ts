import { ConnectionModel } from '../../api';
import { CONNECTION_ACTION } from '../types';

export interface IConnectionState {
  connections: ConnectionModel[];
  loading: boolean;
  error: any;
  activeConnection: string;
}

const initialState: IConnectionState = {
  connections: [],
  loading: false,
  error: null,
  activeConnection: ''
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
    case CONNECTION_ACTION.SET_ACTIVE_CONNECTION:
      return {
        ...state,
        activeConnection: action.activeConnection
      };
    default:
      return state;
  }
};

export default connectionReducer;
