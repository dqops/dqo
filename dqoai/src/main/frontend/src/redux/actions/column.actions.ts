import { Dispatch } from 'redux';

import { ColumnApiClient } from '../../services/apiClient';
import { COLUMN_ACTION } from '../types';
import { AxiosResponse } from 'axios';
import { ColumnBasicModel } from '../../api';

export const getColumnsRequest = () => ({
  type: COLUMN_ACTION.GET_COLUMNS
});

export const getColumnsSuccess = (data: any) => ({
  type: COLUMN_ACTION.GET_COLUMNS_SUCCESS,
  data
});

export const getColumnsFailed = (error: any) => ({
  type: COLUMN_ACTION.GET_COLUMNS_ERROR,
  error
});

export const getAllColumns =
  (connectionName: string, schemaName: string, columnName: string) =>
  async (dispatch: Dispatch) => {
    dispatch(getColumnsRequest());
    try {
      const res: AxiosResponse<ColumnBasicModel[]> =
        await ColumnApiClient.getColumns(
          connectionName,
          schemaName,
          columnName
        );
      dispatch(getColumnsSuccess(res.data));
    } catch (err) {
      dispatch(getColumnsFailed(err));
    }
  };

export const getColumnBasicRequest = () => ({
  type: COLUMN_ACTION.GET_COLUMN_BASIC
});

export const getColumnBasicSuccess = (data: any) => ({
  type: COLUMN_ACTION.GET_COLUMN_BASIC_SUCCESS,
  data
});

export const getColumnBasicFailed = (error: any) => ({
  type: COLUMN_ACTION.GET_COLUMN_BASIC_ERROR,
  error
});

export const getColumnBasic =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(getColumnBasicRequest());
    try {
      const res = await ColumnApiClient.getColumnBasic(
        connectionName,
        schemaName,
        tableName,
        columnName
      );
      dispatch(getColumnBasicSuccess(res.data));
    } catch (err) {
      dispatch(getColumnsFailed(err));
    }
  };

export const updateColumnBasicRequest = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_BASIC
});

export const updateColumnBasicSuccess = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_BASIC_SUCCESS
});

export const updateColumnBasicFailed = (error: any) => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_BASIC_ERROR,
  error
});

export const updateColumnBasic =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: any
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnBasicRequest());
    try {
      await ColumnApiClient.updateColumnBasic(
        connectionName,
        schemaName,
        tableName,
        columnName,
        data
      );
      dispatch(updateColumnBasicSuccess());
    } catch (err) {
      dispatch(updateColumnBasicFailed(err));
    }
  };

export const getColumnCommentsRequest = () => ({
  type: COLUMN_ACTION.GET_COLUMN_COMMENTS
});

export const getColumnCommentsSuccess = (data: any) => ({
  type: COLUMN_ACTION.GET_COLUMN_COMMENTS_SUCCESS,
  data
});

export const getColumnCommentsFailed = (error: any) => ({
  type: COLUMN_ACTION.GET_COLUMN_COMMENTS_ERROR,
  error
});

export const getColumnComments =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(getColumnCommentsRequest());
    try {
      const res = await ColumnApiClient.getColumnComments(
        connectionName,
        schemaName,
        tableName,
        columnName
      );
      dispatch(getColumnCommentsSuccess(res.data));
    } catch (err) {
      dispatch(getColumnCommentsFailed(err));
    }
  };

export const updateColumnCommentsRequest = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_COMMENTS
});

export const updateColumnCommentsSuccess = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_COMMENTS_SUCCESS
});

export const updateColumnCommentsFailed = (error: any) => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_COMMENTS_ERROR,
  error
});

export const updateColumnComments =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: any
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnCommentsRequest());
    try {
      await ColumnApiClient.updateColumnComments(
        connectionName,
        schemaName,
        tableName,
        columnName,
        data
      );
      dispatch(updateColumnCommentsSuccess());
    } catch (err) {
      dispatch(updateColumnCommentsFailed(err));
    }
  };

export const getColumnLabelsRequest = () => ({
  type: COLUMN_ACTION.GET_COLUMN_LABELS
});

export const getColumnLabelsSuccess = (data: any) => ({
  type: COLUMN_ACTION.GET_COLUMN_LABELS_SUCCESS,
  data
});

export const getColumnLabelsFailed = (error: any) => ({
  type: COLUMN_ACTION.GET_COLUMN_LABELS_ERROR,
  error
});

export const getColumnLabels =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(getColumnLabelsRequest());
    try {
      const res = await ColumnApiClient.getColumnLabels(
        connectionName,
        schemaName,
        tableName,
        columnName
      );
      dispatch(getColumnLabelsSuccess(res.data));
    } catch (err) {
      dispatch(getColumnLabelsFailed(err));
    }
  };

export const updateColumnLabelsRequest = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_LABELS
});

export const updateColumnLabelsSuccess = () => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_LABELS_SUCCESS
});

export const updateColumnLabelsFailed = (error: any) => ({
  type: COLUMN_ACTION.UPDATE_COLUMN_LABELS_ERROR,
  error
});

export const updateColumnLabels =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string,
    data: any
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(updateColumnLabelsRequest());
    try {
      await ColumnApiClient.updateColumnLabels(
        connectionName,
        schemaName,
        tableName,
        columnName,
        data
      );
      dispatch(updateColumnLabelsSuccess());
    } catch (err) {
      dispatch(updateColumnLabelsFailed(err));
    }
  };

export const getColumnChecksUIRequest = () => ({
  type: COLUMN_ACTION.GET_COLUMN_CHECKS_UI
});

export const getColumnChecksUISuccess = (data: any) => ({
  type: COLUMN_ACTION.GET_COLUMN_CHECKS_UI_SUCCESS,
  data
});

export const getColumnChecksUIFailed = (error: any) => ({
  type: COLUMN_ACTION.GET_COLUMN_CHECKS_UI_ERROR,
  error
});

export const getColumnChecksUi =
  (
    connectionName: string,
    schemaName: string,
    tableName: string,
    columnName: string
  ) =>
  async (dispatch: Dispatch) => {
    dispatch(getColumnChecksUIRequest());
    try {
      const res = await ColumnApiClient.getColumnChecksUI(
        connectionName,
        schemaName,
        tableName,
        columnName
      );
      dispatch(getColumnChecksUISuccess(res.data));
    } catch (err) {
      dispatch(getColumnChecksUIFailed(err));
    }
  };
