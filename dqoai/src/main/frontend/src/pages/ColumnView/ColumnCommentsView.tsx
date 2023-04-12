import React, {useEffect, useState} from 'react';
import CommentsView from '../../components/Connection/CommentsView';
import ColumnActionGroup from './ColumnActionGroup';
import { useSelector } from 'react-redux';
import { CommentSpec } from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getColumnComments,
  setIsUpdatedComments,
  setUpdatedComments,
  updateColumnComments
} from '../../redux/actions/column.actions';
import { CheckTypes } from "../../shared/routes";
import { useParams } from "react-router-dom";
import { getFirstLevelActiveTab, getFirstLevelState } from "../../redux/selectors";

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
  const { checkTypes }: { checkTypes: CheckTypes } = useParams();
  const { columnBasic, updatedComments, isUpdating, isUpdatedComments } = useSelector(getFirstLevelState(checkTypes));
  const dispatch = useActionDispatch();
  const [text, setText] = useState('');
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  useEffect(() => {
    if (
      !updatedComments?.length ||
      columnBasic?.connection_name !== connectionName ||
      columnBasic?.table?.schema_name !== schemaName ||
      columnBasic?.table?.table_name !== tableName ||
      columnBasic.column_name !== columnName
    ) {
      dispatch(
        getColumnComments(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, columnName)
      );
    }
  }, [connectionName, schemaName, columnName, tableName, columnBasic]);

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
      getColumnComments(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, columnName)
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
