import React, { useEffect, useState } from 'react';
import ActionGroup from './TableActionGroup';
import DataStreamsMappingView from '../DataStreamsMappingView';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableDefaultDataStreamMapping,
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
  const [isUpdated, setIsUpdated] = useState(false);
  const dispatch = useActionDispatch();
  const [updatedDataStreamMapping, setUpdatedDataStreamMapping] =
    useState<DataStreamMappingSpec>();
  const { isUpdating, dataStreamsMapping } = useSelector(
    (state: IRootState) => state.table
  );

  useEffect(() => {
    setUpdatedDataStreamMapping(dataStreamsMapping);
  }, [dataStreamsMapping]);

  useEffect(() => {
    dispatch(
      getTableDefaultDataStreamMapping(connectionName, schemaName, tableName)
    );
  }, []);

  const onUpdate = async () => {
    if (!updatedDataStreamMapping) {
      return;
    }
    await dispatch(
      updateTableDefaultDataStreamMapping(
        connectionName,
        schemaName,
        tableName,
        updatedDataStreamMapping
      )
    );
    await dispatch(
      getTableDefaultDataStreamMapping(connectionName, schemaName, tableName)
    );
    setIsUpdated(false);
  };

  const handleChange = (value: DataStreamMappingSpec) => {
    setUpdatedDataStreamMapping(value);
    setIsUpdated(true);
  };

  return (
    <div>
      <ActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdated}
        isUpdating={isUpdating}
      />
      <DataStreamsMappingView
        dataStreamsMapping={updatedDataStreamMapping}
        onChange={handleChange}
      />
    </div>
  );
};

export default TableDataStream;
