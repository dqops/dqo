import React, { useEffect, useState } from 'react';
import ActionGroup from './ActionGroup';
import DataStreamsMappingView from '../DataStreamsMappingView';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableDataStreamMapping,
  updateTableDataStreamMapping
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
    dispatch(getTableDataStreamMapping(connectionName, schemaName, tableName));
  }, []);

  const onUpdate = async () => {
    await dispatch(
      updateTableDataStreamMapping(
        connectionName,
        schemaName,
        tableName,
        updatedDataStreamMapping
      )
    );
    await dispatch(
      getTableDataStreamMapping(connectionName, schemaName, tableName)
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
