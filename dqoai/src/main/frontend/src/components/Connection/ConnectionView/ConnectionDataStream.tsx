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
import ConnectionActionGroup from './ConnectionActionGroup';
import { useParams } from "react-router-dom";
import { CheckTypes } from "../../../shared/routes";
import { getFirstLevelActiveTab, getFirstLevelState } from "../../../redux/selectors";

const ConnectionDataStream = () => {
  const { connection, checkTypes }: { connection: string, checkTypes: CheckTypes } = useParams();
  const dispatch = useActionDispatch();
  const { connectionBasic, isUpdating, updatedDataStreamsMapping, isUpdatedDataStreamsMapping } =
    useSelector(getFirstLevelState(checkTypes));

  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  useEffect(() => {
    if (connectionBasic?.connection_name !== connection) {
      dispatch(getConnectionBasic(checkTypes, firstLevelActiveTab, connection));
      dispatch(getConnectionDefaultDataStreamsMapping(checkTypes, firstLevelActiveTab, connection));
    }
  }, [connection]);

  useEffect(() => {
    if (!updatedDataStreamsMapping || (connectionBasic && connectionBasic?.connection_name !== connection)) {
      dispatch(getConnectionDefaultDataStreamsMapping(checkTypes, firstLevelActiveTab, connection));
    }
  }, [connection, connectionBasic]);

  const onUpdate = async () => {
    if (!updatedDataStreamsMapping) {
      return;
    }
    await dispatch(
      updateConnectionDefaultDataStreamsMapping(
        checkTypes,
        firstLevelActiveTab,
        connection,
        updatedDataStreamsMapping
      )
    );
    await dispatch(getConnectionDefaultDataStreamsMapping(checkTypes, firstLevelActiveTab, connection));
    dispatch(setIsUpdatedDataStreamsMapping(checkTypes, firstLevelActiveTab,false));
  };

  const handleChange = (value: DataStreamMappingSpec) => {
    dispatch(setUpdatedDataStreamsMapping(checkTypes, firstLevelActiveTab, value));
    dispatch(setIsUpdatedDataStreamsMapping(checkTypes, firstLevelActiveTab,true));
  };

  return (
    <div className="px-4">
      <div className="pt-6 px-4">
        <p className="text-gray-700 italic text-base">
          The following data stream configuration will be copied to the data stream configuration of tables that will be imported in the future.
          This configuration does not affect tables that are already imported.      </p>
      </div>
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
