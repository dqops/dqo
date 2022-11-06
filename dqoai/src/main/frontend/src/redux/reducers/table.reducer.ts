import {
  CommentSpec,
  TableBasicModel,
  TableModel,
  RecurringScheduleSpec,
  TimeSeriesConfigurationSpec,
  TableAdHocCheckCategoriesSpec,
  UIAllChecksModel,
  DimensionsConfigurationSpec
} from '../../api';
import { TABLE_ACTION } from '../types';

export interface ITableState {
  tables: TableModel[];
  loading: boolean;
  error: any;
  activeTable: string;
  tableBasic?: TableBasicModel;
  isUpdating: boolean;
  schedule?: RecurringScheduleSpec;
  timeSeries?: TimeSeriesConfigurationSpec;
  comments: CommentSpec[];
  labels: string[];
  checks?: TableAdHocCheckCategoriesSpec;
  checksUI?: UIAllChecksModel;
  dimensions?: DimensionsConfigurationSpec;
}

const initialState: ITableState = {
  tables: [],
  loading: false,
  error: null,
  activeTable: '',
  isUpdating: false,
  comments: [],
  labels: []
};

const tableReducer = (state = initialState, action: any) => {
  switch (action.type) {
    case TABLE_ACTION.GET_TABLES:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLES_SUCCESS:
      return {
        ...state,
        loading: false,
        tables: action.data,
        error: null
      };
    case TABLE_ACTION.GET_TABLES_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_BASIC:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_BASIC_SUCCESS:
      return {
        ...state,
        loading: false,
        tableBasic: action.data,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_BASIC_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.UPDATE_TABLE_BASIC:
      return {
        ...state,
        isUpdating: true
      };
    case TABLE_ACTION.UPDATE_TABLE_BASIC_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case TABLE_ACTION.UPDATE_TABLE_BASIC_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_SCHEDULE:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_SCHEDULE_SUCCESS:
      return {
        ...state,
        loading: false,
        schedule: action.data,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_SCHEDULE_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.UPDATE_TABLE_SCHEDULE:
      return {
        ...state,
        isUpdating: true
      };
    case TABLE_ACTION.UPDATE_TABLE_SCHEDULE_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case TABLE_ACTION.UPDATE_TABLE_SCHEDULE_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_TIME:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_TIME_SUCCESS:
      return {
        ...state,
        loading: false,
        timeSeries: action.data,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_TIME_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.UPDATE_TABLE_TIME:
      return {
        ...state,
        isUpdating: true
      };
    case TABLE_ACTION.UPDATE_TABLE_TIME_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case TABLE_ACTION.UPDATE_TABLE_TIME_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_COMMENTS:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_COMMENTS_SUCCESS:
      return {
        ...state,
        loading: false,
        comments: action.data,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_COMMENTS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.UPDATE_TABLE_COMMENTS:
      return {
        ...state,
        isUpdating: true
      };
    case TABLE_ACTION.UPDATE_TABLE_COMMENTS_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case TABLE_ACTION.UPDATE_TABLE_COMMENTS_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_LABELS:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_LABELS_SUCCESS:
      return {
        ...state,
        loading: false,
        labels: action.data,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_LABELS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.UPDATE_TABLE_LABELS:
      return {
        ...state,
        isUpdating: true
      };
    case TABLE_ACTION.UPDATE_TABLE_LABELS_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case TABLE_ACTION.UPDATE_TABLE_LABELS_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_SUCCESS:
      return {
        ...state,
        loading: false,
        checks: action.data,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_UI:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_UI_SUCCESS:
      return {
        ...state,
        loading: false,
        checksUI: action.data,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_DATA_QUALITY_CHECKS_UI_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.GET_TABLE_DIMENSIONS:
      return {
        ...state,
        loading: true
      };
    case TABLE_ACTION.GET_TABLE_DIMENSIONS_SUCCESS:
      return {
        ...state,
        loading: false,
        dimensions: action.data,
        error: null
      };
    case TABLE_ACTION.GET_TABLE_DIMENSIONS_ERROR:
      return {
        ...state,
        loading: false,
        error: action.error
      };
    case TABLE_ACTION.UPDATE_TABLE_DIMENSIONS:
      return {
        ...state,
        isUpdating: true
      };
    case TABLE_ACTION.UPDATE_TABLE_DIMENSIONS_SUCCESS:
      return {
        ...state,
        isUpdating: false,
        error: null
      };
    case TABLE_ACTION.UPDATE_TABLE_DIMENSIONS_ERROR:
      return {
        ...state,
        isUpdating: false,
        error: action.error
      };
    default:
      return state;
  }
};

export default tableReducer;
