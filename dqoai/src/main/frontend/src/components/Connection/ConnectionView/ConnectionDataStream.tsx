import React, { useEffect, useState } from 'react';
import DataStreamsMappingView from '../DataStreamsMappingView';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getConnectionDefaultDataStreamsMapping,
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
  const [isUpdated, setIsUpdated] = useState(false);
  const dispatch = useActionDispatch();
  const [updatedDataStreamsMapping, setUpdatedDataStreamsMapping] =
    useState<DataStreamMappingSpec>();
  const { isUpdating, defaultDataStreams } = useSelector(
    (state: IRootState) => state.connection
  );

  useEffect(() => {
    setUpdatedDataStreamsMapping(defaultDataStreams);
  }, [defaultDataStreams]);

  useEffect(() => {
    dispatch(getConnectionDefaultDataStreamsMapping(connectionName));
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
    setIsUpdated(false);
  };

  const handleChange = (value: DataStreamMappingSpec) => {
    setUpdatedDataStreamsMapping(value);
    setIsUpdated(true);
  };

  return (
    <div>
      <ConnectionActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdated}
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
