import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import {
  getTableLabels,
  setUpdatedLabels,
  updateTableLabels
} from '../../../redux/actions/table.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import ActionGroup from './TableActionGroup';
import LabelsView from '../LabelsView';
import { useParams } from "react-router-dom";
import { getFirstLevelActiveTab, getFirstLevelState } from "../../../redux/selectors";
import { CheckTypes } from "../../../shared/routes";

const TableLabelsView = () => {
  const { checkTypes, connection: connectionName, schema: schemaName, table: tableName }: { checkTypes: CheckTypes, connection: string, schema: string, table: string } = useParams();
  const { labels, isUpdating, isUpdatedLabels, tableBasic } = useSelector(getFirstLevelState(checkTypes));
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  useEffect(() => {
    dispatch(getTableLabels(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName));
  }, [checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName]);

  const onUpdate = async () => {
    await dispatch(
      updateTableLabels(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, labels)
    );
    await dispatch(getTableLabels(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName));
  };

  const handleChange = (value: string[]) => {
    dispatch(setUpdatedLabels(checkTypes, firstLevelActiveTab, value));
  };

  return (
    <div className="px-4">
      <ActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedLabels}
        isUpdating={isUpdating}
      />
      <LabelsView labels={labels} onChange={handleChange} />
    </div>
  );
};

export default TableLabelsView;
