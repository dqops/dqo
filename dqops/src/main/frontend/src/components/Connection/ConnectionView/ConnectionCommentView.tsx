import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { CommentSpec } from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getConnectionComments,
  setIsUpdatedComments,
  setUpdatedComments,
  updateConnectionComments
} from '../../../redux/actions/connection.actions';
import { getFirstLevelActiveTab, getFirstLevelState } from "../../../redux/selectors";
import { CheckTypes } from "../../../shared/routes";
import CommentsView from '../CommentsView';
import ConnectionActionGroup from './ConnectionActionGroup';
import { useDecodedParams } from '../../../utils';

const ConnectionCommentView = () => {
  const { connection, checkTypes }: { connection: string, checkTypes: CheckTypes } = useDecodedParams();
  const { isUpdating, updatedComments, isUpdatedComments }: {
    isUpdating: boolean,
    updatedComments: CommentSpec[],
    isUpdatedComments: boolean;
  } = useSelector(getFirstLevelState(checkTypes));
  const dispatch = useActionDispatch();
  const [text, setText] = useState('');
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  useEffect(() => {
    if (!updatedComments) {
      dispatch(getConnectionComments(checkTypes, firstLevelActiveTab, connection));
    }
  }, []);

  const onUpdate = async () => {
    await dispatch(
      updateConnectionComments(checkTypes, firstLevelActiveTab, connection, [...(updatedComments || []), ...text ? [{
        comment: text,
        comment_by: 'user',
        date: new Date().toISOString()
      }] : []])
    );
    await dispatch(getConnectionComments(checkTypes, firstLevelActiveTab, connection, false));
    dispatch(setIsUpdatedComments(checkTypes, firstLevelActiveTab, false));
  };

  const handleChange = (value: CommentSpec[]) => {
    dispatch(setUpdatedComments(checkTypes, firstLevelActiveTab, value));
    dispatch(setIsUpdatedComments(checkTypes, firstLevelActiveTab, true));
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
        setIsUpdated={(value) => dispatch(setIsUpdatedComments(checkTypes, firstLevelActiveTab, value))}
        comments={updatedComments || []}
        onChange={handleChange}
      />
    </div>
  );
};

export default ConnectionCommentView;
