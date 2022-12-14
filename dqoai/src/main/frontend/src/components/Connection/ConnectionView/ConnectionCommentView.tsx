import React, { useEffect, useState } from 'react';
import CommentsView from '../CommentsView';
import ConnectionActionGroup from './ConnectionActionGroup';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { CommentSpec } from '../../../api';
import {
  getConnectionComments,
  updateConnectionComments
} from '../../../redux/actions/connection.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';

interface IConnectionCommentViewProps {
  connectionName: string;
}

const ConnectionCommentView = ({
  connectionName
}: IConnectionCommentViewProps) => {
  const { comments, isUpdating } = useSelector(
    (state: IRootState) => state.connection
  );
  const [updatedComments, setUpdatedComments] = useState<CommentSpec[]>([]);
  const dispatch = useActionDispatch();
  const [isUpdated, setIsUpdated] = useState(false);

  useEffect(() => {
    setUpdatedComments(comments);
  }, [comments]);

  useEffect(() => {
    dispatch(getConnectionComments(connectionName));
  }, []);

  const onUpdate = async () => {
    await dispatch(updateConnectionComments(connectionName, updatedComments));
    await dispatch(getConnectionComments(connectionName));
    setIsUpdated(false);
  };

  const handleChange = (value: CommentSpec[]) => {
    setUpdatedComments(value);
    setIsUpdated(true);
  };

  return (
    <div>
      <ConnectionActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdated}
        isUpdating={isUpdating}
      />
      <CommentsView
        isUpdated={isUpdated}
        setIsUpdated={setIsUpdated}
        comments={updatedComments}
        onChange={handleChange}
      />
    </div>
  );
};

export default ConnectionCommentView;
