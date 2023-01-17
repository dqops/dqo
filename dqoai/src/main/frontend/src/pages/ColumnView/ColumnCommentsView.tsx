import React, {useEffect, useState} from 'react';
import CommentsView from '../../components/Connection/CommentsView';
import ColumnActionGroup from './ColumnActionGroup';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { CommentSpec } from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getColumnComments,
  setIsUpdatedComments,
  setUpdatedComments,
  updateColumnComments
} from '../../redux/actions/column.actions';

interface IColumnCommentsViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
  columnName: string;
}

const ColumnCommentsView = ({
  connectionName,
  schemaName,
  tableName,
  columnName
}: IColumnCommentsViewProps) => {
  const { columnBasic, comments, isUpdating, isUpdatedComments } = useSelector(
    (state: IRootState) => state.column
  );
  const dispatch = useActionDispatch();
  const [text, setText] = useState('');

  useEffect(() => {
    if (
      !comments?.length ||
      columnBasic?.connection_name !== connectionName ||
      columnBasic?.table?.schemaName !== schemaName ||
      columnBasic?.table?.tableName !== tableName ||
      columnBasic.column_name !== columnName
    ) {
      dispatch(
        getColumnComments(connectionName, schemaName, tableName, columnName)
      );
    }
  }, [connectionName, schemaName, columnName, tableName, columnBasic]);

  const onUpdate = async () => {
    await dispatch(
      updateColumnComments(
        connectionName,
        schemaName,
        tableName,
        columnName,
        [...comments, ...text ? [{
          comment: text,
          comment_by: 'user',
          date: new Date().toISOString()
        }] : []]
      )
    );
    await dispatch(
      getColumnComments(connectionName, schemaName, tableName, columnName)
    );
  };

  const handleChange = (value: CommentSpec[]) => {
    dispatch(setUpdatedComments(value));
  };

  return (
    <div>
      <ColumnActionGroup
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

export default ColumnCommentsView;
