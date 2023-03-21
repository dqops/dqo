import React, { useEffect } from 'react';
import DataStreamsMappingView from '../DataStreamsMappingView';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getConnectionBasic,
  getConnectionDefaultDataStreamsMapping,
  setIsUpdatedDataStreamsMapping,
  setUpdatedDataStreamsMapping,
  updateConnectionDefaultDataStreamsMapping
} from '../../../redux/actions/connection.actions';
import { DataStreamMappingSpec } from '../../../api';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import ConnectionActionGroup from './ConnectionActionGroup';
import { useParams } from "react-router-dom";

const ConnectionDataStream = () => {
  const { connection }: { connection: string } = useParams();
  const dispatch = useActionDispatch();
  const { connectionBasic, isUpdating, updatedDataStreamsMapping, isUpdatedDataStreamsMapping } =
    useSelector((state: IRootState) => state.connection);

  useEffect(() => {
    if (connectionBasic?.connection_name !== connection) {
      dispatch(getConnectionBasic(connection));
      dispatch(getConnectionDefaultDataStreamsMapping(connection));
    }
  }, [connection]);

  useEffect(() => {
    if (!updatedDataStreamsMapping || (connectionBasic && connectionBasic?.connection_name !== connection)) {
      dispatch(getConnectionDefaultDataStreamsMapping(connection));
    }
  }, [connection, connectionBasic]);

  const onUpdate = async () => {
    if (!updatedDataStreamsMapping) {
      return;
    }
    await dispatch(
      updateConnectionDefaultDataStreamsMapping(
        connection,
        updatedDataStreamsMapping
      )
    );
    await dispatch(getConnectionDefaultDataStreamsMapping(connection));
    dispatch(setIsUpdatedDataStreamsMapping(false));
  };

  const handleChange = (value: DataStreamMappingSpec) => {
    dispatch(setUpdatedDataStreamsMapping(value));
    dispatch(setIsUpdatedDataStreamsMapping(true));
  };

  return (
    <div className="px-4">
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
