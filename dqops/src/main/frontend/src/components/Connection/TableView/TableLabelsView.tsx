import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { setIsUpdatedLabels } from '../../../redux/actions/connection.actions';
import {
  getTableLabels,
  setUpdatedLabels,
  updateTableLabels
} from '../../../redux/actions/table.actions';
import { getFirstLevelActiveTab, getFirstLevelState } from "../../../redux/selectors";
import { CheckTypes } from "../../../shared/routes";
import LabelsView from '../LabelsView';
import ActionGroup from './TableActionGroup';
import { useDecodedParams } from '../../../utils';

const TableLabelsView = () => {
  const { checkTypes, connection: connectionName, schema: schemaName, table: tableName }: { checkTypes: CheckTypes, connection: string, schema: string, table: string } = useDecodedParams();
  const { labels, isUpdating, isUpdatedLabels } = useSelector(getFirstLevelState(checkTypes));
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  useEffect(() => {
    dispatch(getTableLabels(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName));
  }, [checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName]);

  const onUpdate = async () => {
    await dispatch(
      updateTableLabels(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, (Array.from(labels).filter(x => String(x).length !== 0) as any))
    );
    await dispatch(getTableLabels(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, false));
  };

  const handleChange = (value: string[]) => {
    dispatch(setUpdatedLabels(checkTypes, firstLevelActiveTab, value));
    dispatch(setIsUpdatedLabels(checkTypes, firstLevelActiveTab, true))
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
