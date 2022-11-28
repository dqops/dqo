import React, { useEffect, useState } from 'react';
import CommentsView from '../CommentsView';
import ActionGroup from './ActionGroup';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { CommentSpec } from '../../../api';
import {
  getTableComments,
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
  const { comments, isUpdating } = useSelector(
    (state: IRootState) => state.table
  );
  const [updatedComments, setUpdatedComments] = useState<CommentSpec[]>([]);
  const dispatch = useActionDispatch();
  const [isUpdated, setIsUpdated] = useState(false);

  useEffect(() => {
    setUpdatedComments(comments);
  }, [comments]);

  useEffect(() => {
    dispatch(getTableComments(connectionName, schemaName, tableName));
  }, []);

  const onUpdate = async () => {
    await dispatch(
      updateTableComments(
        connectionName,
        schemaName,
        tableName,
        updatedComments
      )
    );
    await dispatch(getTableComments(connectionName, schemaName, tableName));
    setIsUpdated(false);
  };

  const handleChange = (value: CommentSpec[]) => {
    setUpdatedComments(value);
    setIsUpdated(true);
  };

  return (
    <div>
      <ActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdated}
        isUpdating={isUpdating}
      />
      <CommentsView comments={updatedComments} onChange={handleChange} />
    </div>
  );
};

export default TableCommentView;
