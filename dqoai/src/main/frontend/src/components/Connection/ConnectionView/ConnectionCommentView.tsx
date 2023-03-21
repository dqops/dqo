import React, {useEffect, useState} from 'react';
import CommentsView from '../CommentsView';
import ConnectionActionGroup from './ConnectionActionGroup';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { CommentSpec } from '../../../api';
import {
  getConnectionComments,
  setIsUpdatedComments,
  setUpdatedComments,
  updateConnectionComments
} from '../../../redux/actions/connection.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { useParams } from "react-router-dom";

const ConnectionCommentView = () => {
  const { connection }: { connection: string } = useParams();
  const { isUpdating, updatedComments, isUpdatedComments } = useSelector(
    (state: IRootState) => state.connection
  );
  const dispatch = useActionDispatch();
  const [text, setText] = useState('');

  useEffect(() => {
    if (!updatedComments) {
      dispatch(getConnectionComments(connection));
    }
  }, []);

  const onUpdate = async () => {
    await dispatch(
      updateConnectionComments(connection, [...(updatedComments || []), ...text ? [{
        comment: text,
        comment_by: 'user',
        date: new Date().toISOString()
      }] : []])
    );
    await dispatch(getConnectionComments(connection));
    dispatch(setIsUpdatedComments(false));
  };

  const handleChange = (value: CommentSpec[]) => {
    dispatch(setUpdatedComments(value));
    dispatch(setIsUpdatedComments(true));
  };

  return (
    <div className="px-4">
      <ConnectionActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedComments}
        isUpdating={isUpdating}
      />
      <CommentsView
        text={text}
        setText={setText}
        isUpdated={isUpdatedComments}
        setIsUpdated={(value) => dispatch(setIsUpdatedComments(value))}
        comments={updatedComments || []}
        onChange={handleChange}
      />
    </div>
  );
};

export default ConnectionCommentView;
