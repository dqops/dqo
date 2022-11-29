import React, { useEffect, useState } from 'react';
import CommentsView from '../CommentsView';
import ColumnActionGroup from './ColumnActionGroup';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { CommentSpec } from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getColumnComments,
  updateColumnComments
} from '../../../redux/actions/column.actions';

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
  const { comments, isUpdating } = useSelector(
    (state: IRootState) => state.column
  );
  const [updatedComments, setUpdatedComments] = useState<CommentSpec[]>([]);
  const dispatch = useActionDispatch();
  const [isUpdated, setIsUpdated] = useState(false);

  useEffect(() => {
    setUpdatedComments(comments);
  }, [comments]);

  useEffect(() => {
    dispatch(
      getColumnComments(connectionName, schemaName, tableName, columnName)
    );
  }, []);

  const onUpdate = async () => {
    await dispatch(
      updateColumnComments(
        connectionName,
        schemaName,
        tableName,
        columnName,
        updatedComments
      )
    );
    await dispatch(
      getColumnComments(connectionName, schemaName, tableName, columnName)
    );
    setIsUpdated(false);
  };

  const handleChange = (value: CommentSpec[]) => {
    setUpdatedComments(value);
    setIsUpdated(true);
  };

  return (
    <div>
      <ColumnActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdated}
        isUpdating={isUpdating}
      />
      <CommentsView comments={updatedComments} onChange={handleChange} />
    </div>
  );
};

export default ColumnCommentsView;
