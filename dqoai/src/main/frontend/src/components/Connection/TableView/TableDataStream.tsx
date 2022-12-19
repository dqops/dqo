import React, { useEffect } from 'react';
import ActionGroup from './TableActionGroup';
import DataStreamsMappingView from '../DataStreamsMappingView';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableDefaultDataStreamMapping,
  setUpdatedTableDataStreamsMapping,
  updateTableDefaultDataStreamMapping
} from '../../../redux/actions/table.actions';
import { DataStreamMappingSpec } from '../../../api';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';

interface ITableDataStreamProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
}

const TableDataStream = ({
  connectionName,
  schemaName,
  tableName
}: ITableDataStreamProps) => {
  const dispatch = useActionDispatch();
  const {
    isUpdating,
    dataStreamsMapping,
    isUpdatedDataStreamsMapping,
    tableBasic
  } = useSelector((state: IRootState) => state.table);

  useEffect(() => {
    if (
      !dataStreamsMapping ||
      tableBasic?.connection_name !== connectionName ||
      tableBasic?.target?.schema_name !== schemaName ||
      tableBasic?.target?.table_name !== tableName
    ) {
      dispatch(
        getTableDefaultDataStreamMapping(connectionName, schemaName, tableName)
      );
    }
  }, [connectionName, schemaName, tableName, tableBasic]);

  const onUpdate = async () => {
    if (!dataStreamsMapping) {
      return;
    }
    await dispatch(
      updateTableDefaultDataStreamMapping(
        connectionName,
        schemaName,
        tableName,
        dataStreamsMapping
      )
    );
    await dispatch(
      getTableDefaultDataStreamMapping(connectionName, schemaName, tableName)
    );
  };

  const handleChange = (value: DataStreamMappingSpec) => {
    dispatch(setUpdatedTableDataStreamsMapping(value));
  };

  return (
    <div>
      <ActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedDataStreamsMapping}
        isUpdating={isUpdating}
      />
      <DataStreamsMappingView
        dataStreamsMapping={dataStreamsMapping}
        onChange={handleChange}
      />
    </div>
  );
};

export default TableDataStream;
