import React, { useEffect } from 'react';
import DataStreamsMappingView from '../DataStreamsMappingView';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getConnectionDefaultDataStreamsMapping,
  setIsUpdatedDataStreamsMapping,
  setUpdatedDataStreamsMapping,
  updateConnectionDefaultDataStreamsMapping
} from '../../../redux/actions/connection.actions';
import { DataStreamMappingSpec } from '../../../api';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import ConnectionActionGroup from './ConnectionActionGroup';

interface IConnectionDataStreamProps {
  connectionName: string;
}

const ConnectionDataStream = ({
  connectionName
}: IConnectionDataStreamProps) => {
  const dispatch = useActionDispatch();
  const { isUpdating, updatedDataStreamsMapping, isUpdatedDataStreamsMapping } =
    useSelector((state: IRootState) => state.connection);

  useEffect(() => {
    if (!updatedDataStreamsMapping) {
      dispatch(getConnectionDefaultDataStreamsMapping(connectionName));
    }
  }, [connectionName]);

  const onUpdate = async () => {
    if (!updatedDataStreamsMapping) {
      return;
    }
    await dispatch(
      updateConnectionDefaultDataStreamsMapping(
        connectionName,
        updatedDataStreamsMapping
      )
    );
    await dispatch(getConnectionDefaultDataStreamsMapping(connectionName));
    dispatch(setIsUpdatedDataStreamsMapping(false));
  };

  const handleChange = (value: DataStreamMappingSpec) => {
    dispatch(setUpdatedDataStreamsMapping(value));
    dispatch(setIsUpdatedDataStreamsMapping(true));
  };

  return (
    <div>
      <ConnectionActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedDataStreamsMapping}
        isUpdating={isUpdating}
      />
      <DataStreamsMappingView
        dataStreamsMapping={updatedDataStreamsMapping}
        onChange={handleChange}
      />
    </div>
  );
};

export default ConnectionDataStream;
