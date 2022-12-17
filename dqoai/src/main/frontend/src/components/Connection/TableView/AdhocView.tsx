import React, { useEffect } from 'react';
import DataQualityChecks from '../../DataQualityChecks';
import TableActionGroup from './TableActionGroup';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { UIAllChecksModel } from '../../../api';
import {
  getTableAdHocChecksUI,
  setUpdatedChecksUi,
  updateTableAdHocChecksUI
} from '../../../redux/actions/table.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';

interface IAdhocViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
}

const AdhocView = ({
  connectionName,
  schemaName,
  tableName
}: IAdhocViewProps) => {
  const { checksUI, isUpdating, isUpdatedChecksUi, tableBasic } = useSelector(
    (state: IRootState) => state.table
  );
  const dispatch = useActionDispatch();

  useEffect(() => {
    if (
      !checksUI ||
      tableBasic?.connection_name !== connectionName ||
      tableBasic?.target?.schema_name !== schemaName ||
      tableBasic?.target?.table_name !== tableName
    ) {
      dispatch(getTableAdHocChecksUI(connectionName, schemaName, tableName));
    }
  }, [connectionName, schemaName, tableName, tableBasic]);

  const onUpdate = async () => {
    if (!checksUI) {
      return;
    }
    await dispatch(
      updateTableAdHocChecksUI(connectionName, schemaName, tableName, checksUI)
    );
    await dispatch(
      getTableAdHocChecksUI(connectionName, schemaName, tableName)
    );
  };

  const handleChange = (value: UIAllChecksModel) => {
    dispatch(setUpdatedChecksUi(value));
  };

  return (
    <div>
      <TableActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedChecksUi}
        isUpdating={isUpdating}
      />
      <DataQualityChecks checksUI={checksUI} onChange={handleChange} />
    </div>
  );
};

export default AdhocView;
