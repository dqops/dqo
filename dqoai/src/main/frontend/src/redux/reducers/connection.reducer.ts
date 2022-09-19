import { IConnection } from '../../shared/interfaces';
import { CONNECTION_ACTION } from '../types';

export interface IConnectionState {
  connections: IConnection[];
  loading: boolean;
  error: any;
}

const initialState: IConnectionState = {
  connections: [],
  loading: false,
  error: null,
};

const connectionReducer = (state = initialState, action: any) => {
  switch (action.type) {
    case CONNECTION_ACTION.GET_CONNECTIONS:
      return {
        ...state,
        loading: true,
      };
    case CONNECTION_ACTION.GET_CONNECTIONS_SUCCESS:
      return {
        ...state,
        loading: false,
        connections: action.data,
        error: null,
      };
    case CONNECTION_ACTION.GET_CONNECTIONS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error,
      };
    default:
      return state;
  }
};

export default connectionReducer;
