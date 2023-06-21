import React, { useEffect } from 'react';
import DataGroupingConfigurationView from '../DataGroupingConfigurationView';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getConnectionBasic,
  getConnectionDefaultGroupingConfiguration,
  setIsUpdatedDataGroupingConfiguration,
  setUpdatedDataGroupingConfiguration,
  updateConnectionDefaultGroupingConfiguration
} from '../../../redux/actions/connection.actions';
import { DataGroupingConfigurationSpec } from '../../../api';
import { useSelector } from 'react-redux';
import ConnectionActionGroup from './ConnectionActionGroup';
import { useParams } from "react-router-dom";
import { CheckTypes } from "../../../shared/routes";
import { getFirstLevelActiveTab, getFirstLevelState } from "../../../redux/selectors";

const ConnectionDefaultGroupingConfiguration = () => {
  const { connection, checkTypes }: { connection: string, checkTypes: CheckTypes } = useParams();
  const dispatch = useActionDispatch();
  const { isUpdating, updatedDataGroupingConfiguration, isUpdatedDataGroupingConfiguration } =
    useSelector(getFirstLevelState(checkTypes));

  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  useEffect(() => {
    dispatch(getConnectionBasic(checkTypes, firstLevelActiveTab, connection));
    dispatch(getConnectionDefaultGroupingConfiguration(checkTypes, firstLevelActiveTab, connection));
  }, [connection, checkTypes, firstLevelActiveTab]);

  useEffect(() => {
    if (!updatedDataGroupingConfiguration) {
      dispatch(getConnectionDefaultGroupingConfiguration(checkTypes, firstLevelActiveTab, connection));
    }
  }, [connection, checkTypes, firstLevelActiveTab]);

  const onUpdate = async () => {
    if (!updatedDataGroupingConfiguration) {
      return;
    }
    await dispatch(
      updateConnectionDefaultGroupingConfiguration(
        checkTypes,
        firstLevelActiveTab,
        connection,
        updatedDataGroupingConfiguration
      )
    );
    await dispatch(getConnectionDefaultGroupingConfiguration(checkTypes, firstLevelActiveTab, connection, false));
    dispatch(setIsUpdatedDataGroupingConfiguration(checkTypes, firstLevelActiveTab,false));
  };

  const handleChange = (value: DataGroupingConfigurationSpec) => {
    dispatch(setUpdatedDataGroupingConfiguration(checkTypes, firstLevelActiveTab, value));
    dispatch(setIsUpdatedDataGroupingConfiguration(checkTypes, firstLevelActiveTab,true));
  };

  return (
    <div className="px-4">
      <div className="pt-6 px-4">
        <p className="text-gray-700 italic text-base">
          The following data grouping configuration will be copied to the data grouping configuration of tables that will be imported in the future.
          This configuration does not affect tables that have already been imported.      </p>
      </div>
      <ConnectionActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedDataGroupingConfiguration}
        isUpdating={isUpdating}
      />
      <DataGroupingConfigurationView
        dataGroupingConfiguration={updatedDataGroupingConfiguration}
        onChange={handleChange}
      />
    </div>
  );
};

export default ConnectionDefaultGroupingConfiguration;
