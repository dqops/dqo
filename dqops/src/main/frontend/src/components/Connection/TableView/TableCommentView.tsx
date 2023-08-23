import React, {useEffect, useState} from 'react';
import CommentsView from '../CommentsView';
import ActionGroup from './TableActionGroup';
import { useSelector } from 'react-redux';
import { CommentSpec } from '../../../api';
import {
  getTableComments,
  setIsUpdatedComments,
  setUpdatedComments,
  updateTableComments
} from '../../../redux/actions/table.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { useParams } from "react-router-dom";
import { getFirstLevelActiveTab, getFirstLevelState } from "../../../redux/selectors";
import { CheckTypes } from "../../../shared/routes";

const TableCommentView = () => {
  const { checkTypes, connection: connectionName, schema: schemaName, table: tableName }: { checkTypes: CheckTypes, connection: string, schema: string, table: string } = useParams();
  const { updatedComments, isUpdating, isUpdatedComments } = useSelector(getFirstLevelState(checkTypes));
  const dispatch = useActionDispatch();
  const [text, setText] = useState('');
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  useEffect(() => {
    dispatch(getTableComments(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName));
  }, [checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName]);

  const onUpdate = async () => {
    await dispatch(
      updateTableComments(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, [...updatedComments, ...text ? [{
        comment: text,
        comment_by: 'user',
        date: Number(new Date().toISOString())
      }] : []])
    );
    setText('');
    await dispatch(getTableComments(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, false));
    dispatch(setIsUpdatedComments(checkTypes, firstLevelActiveTab, false));
  };

  const handleChange = (value: CommentSpec[]) => {
    dispatch(setUpdatedComments(checkTypes, firstLevelActiveTab, value));
    dispatch(setIsUpdatedComments(checkTypes, firstLevelActiveTab, true));
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
        setIsUpdated={(value) => dispatch(setIsUpdatedComments(checkTypes, firstLevelActiveTab, value))}
        comments={updatedComments}
        onChange={handleChange}
      />
    </div>
  );
};

export default TableCommentView;
