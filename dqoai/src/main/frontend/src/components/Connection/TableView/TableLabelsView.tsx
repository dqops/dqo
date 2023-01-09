import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import {
  getTableLabels,
  setUpdatedLabels,
  updateTableLabels
} from '../../../redux/actions/table.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import ActionGroup from './TableActionGroup';
import LabelsView from '../LabelsView';
import { useParams } from "react-router-dom";

const TableLabelsView = () => {
  const { connection: connectionName, schema: schemaName, table: tableName }: { connection: string, schema: string, table: string } = useParams();
  const { labels, isUpdating, isUpdatedLabels, tableBasic } = useSelector(
    (state: IRootState) => state.table
  );
  const dispatch = useActionDispatch();

  useEffect(() => {
    if (
      !labels ||
      tableBasic?.connection_name !== connectionName ||
      tableBasic?.target?.schema_name !== schemaName ||
      tableBasic?.target?.table_name !== tableName
    ) {
      dispatch(getTableLabels(connectionName, schemaName, tableName));
    }
  }, [connectionName, schemaName, tableName, tableBasic]);

  const onUpdate = async () => {
    await dispatch(
      updateTableLabels(connectionName, schemaName, tableName, labels)
    );
    await dispatch(getTableLabels(connectionName, schemaName, tableName));
  };

  const handleChange = (value: string[]) => {
    dispatch(setUpdatedLabels(value));
  };

  return (
    <div>
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
