import { SchemaModel } from '../../api';
import { SCHEMA_ACTION } from '../types';

export interface ISchemaState {
  schemas: SchemaModel[];
  loading: boolean;
  error: any;
}

const initialState: ISchemaState = {
  schemas: [],
  loading: false,
  error: null,
};

const schemaReducer = (state = initialState, action: any) => {
  switch (action.type) {
    case SCHEMA_ACTION.GET_SCHEMAS:
      return {
        ...state,
        loading: true,
      };
    case SCHEMA_ACTION.GET_SCHEMAS_SUCCESS:
      return {
        ...state,
        loading: false,
        schemas: action.data,
        error: null,
      };
    case SCHEMA_ACTION.GET_SCHEMAS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error,
      };
    default:
      return state;
  }
};

export default schemaReducer;
