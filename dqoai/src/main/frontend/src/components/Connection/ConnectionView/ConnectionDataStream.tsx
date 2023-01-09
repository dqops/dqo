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
import { useParams } from "react-router-dom";

const ConnectionDataStream = () => {
  const { connection }: { connection: string } = useParams();
  const dispatch = useActionDispatch();
  const { isUpdating, updatedDataStreamsMapping, isUpdatedDataStreamsMapping } =
    useSelector((state: IRootState) => state.connection);

  useEffect(() => {
    if (!updatedDataStreamsMapping) {
      dispatch(getConnectionDefaultDataStreamsMapping(connection));
    }
  }, [connection]);

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
