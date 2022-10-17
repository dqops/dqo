import {
  CommentSpec,
  ColumnBasicModel,
  ColumnModel,
  UIAllChecksModel
} from '../../api';
import { COLUMN_ACTION } from '../types';

export interface IColumnState {
  columns: ColumnModel[];
  loading: boolean;
  error: any;
  activeColumn: string;
  columnBasic?: ColumnBasicModel;
  isUpdating: boolean;
  comments: CommentSpec[];
  labels: string[];
  checksUI?: UIAllChecksModel;
}

const initialState: IColumnState = {
  columns: [],
  loading: false,
  error: null,
  activeColumn: '',
  isUpdating: false,
  comments: [],
  labels: []
};

const columnReducer = (state = initialState, action: any) => {
  switch (action.type) {
    case COLUMN_ACTION.GET_COLUMNS:
      return {
        ...state,
        loading: true
      };
    case COLUMN_ACTION.GET_COLUMNS_SUCCESS:
      return {
        ...state,
        loading: false,
        columns: action.data,
        error: null
      };
    case COLUMN_ACTION.GET_COLUMNS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case COLUMN_ACTION.GET_COLUMN_BASIC:
      return {
        ...state,
        loading: true
      };
    case COLUMN_ACTION.GET_COLUMN_BASIC_SUCCESS:
      return {
        ...state,
        loading: false,
        columnBasic: action.data,
        error: null
      };
    case COLUMN_ACTION.GET_COLUMN_BASIC_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case COLUMN_ACTION.UPDATE_COLUMN_BASIC:
      return {
        ...state,
        isUpdating: true
      };
    case COLUMN_ACTION.UPDATE_COLUMN_BASIC_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case COLUMN_ACTION.UPDATE_COLUMN_BASIC_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case COLUMN_ACTION.GET_COLUMN_COMMENTS:
      return {
        ...state,
        loading: true
      };
    case COLUMN_ACTION.GET_COLUMN_COMMENTS_SUCCESS:
      return {
        ...state,
        loading: false,
        comments: action.data,
        error: null
      };
    case COLUMN_ACTION.GET_COLUMN_COMMENTS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case COLUMN_ACTION.UPDATE_COLUMN_COMMENTS:
      return {
        ...state,
        isUpdating: true
      };
    case COLUMN_ACTION.UPDATE_COLUMN_COMMENTS_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case COLUMN_ACTION.UPDATE_COLUMN_COMMENTS_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case COLUMN_ACTION.GET_COLUMN_LABELS:
      return {
        ...state,
        loading: true
      };
    case COLUMN_ACTION.GET_COLUMN_LABELS_SUCCESS:
      return {
        ...state,
        loading: false,
        labels: action.data,
        error: null
      };
    case COLUMN_ACTION.GET_COLUMN_LABELS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case COLUMN_ACTION.UPDATE_COLUMN_LABELS:
      return {
        ...state,
        isUpdating: true
      };
    case COLUMN_ACTION.UPDATE_COLUMN_LABELS_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case COLUMN_ACTION.UPDATE_COLUMN_LABELS_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case COLUMN_ACTION.GET_COLUMN_CHECKS_UI:
      return {
        ...state,
        loading: true
      };
    case COLUMN_ACTION.GET_COLUMN_CHECKS_UI_SUCCESS:
      return {
        ...state,
        loading: false,
        checksUI: action.data,
        error: null
      };
    case COLUMN_ACTION.GET_COLUMN_CHECKS_UI_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    default:
      return state;
  }
};

export default columnReducer;
