import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { CommentSpec } from '../../api';
import CommentsView from '../../components/Connection/CommentsView';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getColumnComments,
  setIsUpdatedComments,
  setUpdatedComments,
  updateColumnComments
} from '../../redux/actions/column.actions';
import { getFirstLevelActiveTab, getFirstLevelState } from "../../redux/selectors";
import { CheckTypes } from "../../shared/routes";
import ColumnActionGroup from './ColumnActionGroup';
import { useDecodedParams } from '../../utils';

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
  const { checkTypes }: { checkTypes: CheckTypes } = useDecodedParams();
  const { columnBasic, updatedComments, isUpdating, isUpdatedComments } = useSelector(getFirstLevelState(checkTypes));
  const dispatch = useActionDispatch();
  const [text, setText] = useState('');
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  useEffect(() => {
    dispatch(
      getColumnComments(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, columnName)
    );
  }, [checkTypes, firstLevelActiveTab, connectionName, schemaName, columnName, tableName]);

  const onUpdate = async () => {
    await dispatch(
      updateColumnComments(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        columnName,
        [...updatedComments, ...text ? [{
          comment: text,
          comment_by: 'user',
          date: new Date().toISOString()
        }] : []]
      )
    );
    await dispatch(
      getColumnComments(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, columnName, false)
    );
    dispatch(setIsUpdatedComments(checkTypes, firstLevelActiveTab, false));
  };

  const handleChange = (value: CommentSpec[]) => {
    dispatch(setUpdatedComments(checkTypes, firstLevelActiveTab, value));
    dispatch(setIsUpdatedComments(checkTypes, firstLevelActiveTab, true));
  };

  return (
    <div className="px-4">
      <ColumnActionGroup
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

export default ColumnCommentsView;
