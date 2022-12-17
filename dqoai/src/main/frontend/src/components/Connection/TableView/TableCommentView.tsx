import React, { useEffect } from 'react';
import CommentsView from '../CommentsView';
import ActionGroup from './TableActionGroup';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { CommentSpec } from '../../../api';
import {
  getTableComments,
  setIsUpdatedComments,
  setUpdatedComments,
  updateTableComments
} from '../../../redux/actions/table.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';

interface ITableCommentViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
}

const TableCommentView = ({
  connectionName,
  schemaName,
  tableName
}: ITableCommentViewProps) => {
  const { tableBasic, comments, isUpdating, isUpdatedComments } = useSelector(
    (state: IRootState) => state.table
  );
  const dispatch = useActionDispatch();

  useEffect(() => {
    if (
      !comments ||
      tableBasic?.connection_name !== connectionName ||
      tableBasic?.target?.schema_name !== schemaName ||
      tableBasic?.target?.table_name !== tableName
    ) {
      dispatch(getTableComments(connectionName, schemaName, tableName));
    }
  }, [connectionName, schemaName, tableName, tableBasic]);

  const onUpdate = async () => {
    await dispatch(
      updateTableComments(connectionName, schemaName, tableName, comments)
    );
    await dispatch(getTableComments(connectionName, schemaName, tableName));
  };

  const handleChange = (value: CommentSpec[]) => {
    dispatch(setUpdatedComments(value));
  };

  return (
    <div>
      <ActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedComments}
        isUpdating={isUpdating}
      />
      <CommentsView
        isUpdated={isUpdatedComments}
        setIsUpdated={(value) => dispatch(setIsUpdatedComments(value))}
        comments={comments}
        onChange={handleChange}
      />
    </div>
  );
};

export default TableCommentView;
