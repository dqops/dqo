import React, {useEffect, useState} from 'react';
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
import { useParams } from "react-router-dom";

const TableCommentView = () => {
  const { connection: connectionName, schema: schemaName, table: tableName }: { connection: string, schema: string, table: string } = useParams();
  const { tableBasic, comments, isUpdating, isUpdatedComments } = useSelector(
    (state: IRootState) => state.table
  );
  const dispatch = useActionDispatch();
  const [text, setText] = useState('');

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
      updateTableComments(connectionName, schemaName, tableName, [...comments, ...text ? [{
        comment: text,
        comment_by: 'user',
        date: new Date().toISOString()
      }] : []])
    );
    setText('');
    await dispatch(getTableComments(connectionName, schemaName, tableName));
  };

  const handleChange = (value: CommentSpec[]) => {
    dispatch(setUpdatedComments(value));
  };

  return (
    <div className="px-4">
      <ActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedComments}
        isUpdating={isUpdating}
      />
      <CommentsView
        text={text}
        setText={setText}
        isUpdated={isUpdatedComments}
        setIsUpdated={(value) => dispatch(setIsUpdatedComments(value))}
        comments={comments}
        onChange={handleChange}
      />
    </div>
  );
};

export default TableCommentView;
