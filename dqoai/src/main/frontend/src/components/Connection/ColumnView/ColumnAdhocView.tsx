import React, { useEffect } from 'react';
import DataQualityChecks from '../../DataQualityChecks';
import ColumnActionGroup from './ColumnActionGroup';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { UIAllChecksModel } from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getColumnChecksUi,
  setUpdatedChecksUi,
  updateColumnCheckUI
} from '../../../redux/actions/column.actions';

interface IAdhocViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
  columnName: string;
}

const AdhocView = ({
  connectionName,
  schemaName,
  tableName,
  columnName
}: IAdhocViewProps) => {
  const { columnBasic, checksUI, isUpdating, isUpdatedChecksUi } = useSelector(
    (state: IRootState) => state.column
  );
  const dispatch = useActionDispatch();

  useEffect(() => {
    if (
      !checksUI ||
      columnBasic?.connection_name !== connectionName ||
      columnBasic?.table?.schemaName !== schemaName ||
      columnBasic?.table?.tableName !== tableName ||
      columnBasic.column_name !== columnName
    ) {
      dispatch(
        getColumnChecksUi(connectionName, schemaName, tableName, columnName)
      );
    }
  }, [connectionName, schemaName, columnName, tableName, columnBasic]);

  const onUpdate = async () => {
    if (!checksUI) {
      return;
    }
    await dispatch(
      updateColumnCheckUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        checksUI
      )
    );
    await dispatch(
      getColumnChecksUi(connectionName, schemaName, tableName, columnName)
    );
  };

  const handleChange = (value: UIAllChecksModel) => {
    dispatch(setUpdatedChecksUi(value));
  };

  return (
    <div>
      <ColumnActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedChecksUi}
        isUpdating={isUpdating}
      />
      <DataQualityChecks
        onUpdate={onUpdate}
        checksUI={checksUI}
        onChange={handleChange}
      />
    </div>
  );
};

export default AdhocView;
