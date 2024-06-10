import qs from 'query-string';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory, useLocation } from 'react-router-dom';
import { DataGroupingConfigurationListModel } from '../../../api';
import { getFirstLevelState } from '../../../redux/selectors';
import { DataGroupingConfigurationsApi } from '../../../services/apiClient';
import { CheckTypes } from '../../../shared/routes';
import { useDecodedParams } from '../../../utils';
import DataGroupingConfigurationEditView from './DataGroupingConfigurationEditView';
import DataGroupingConfigurationListView from './DataGroupingConfigurationListView';

const TableDataGroupingConfiguration = () => {
  const {
    connection: connectionName,
    schema: schemaName,
    table: tableName
  }: { connection: string; schema: string; table: string } = useDecodedParams();
  const { levels } = useSelector(getFirstLevelState(CheckTypes.SOURCES));
  const location = useLocation();
  const [isEditing, setIsEditing] = useState(false);

  const history = useHistory();
  const [dataGroupingConfigurations, setDataGroupingConfigurations] = useState<
    DataGroupingConfigurationListModel[]
  >([]);

  const [
    selectedDataGroupingConfiguration,
    setSelectedDataGroupingConfiguration
  ] = useState<DataGroupingConfigurationListModel>();

  useEffect(() => {
    getDataGroupingConfigurations();
  }, [connectionName, schemaName, tableName]);

  useEffect(() => {
    const { isEditing: editing } = qs.parse(location.search);
    setIsEditing(editing === 'true');
  }, [location]);

  const getDataGroupingConfigurations = () => {
    DataGroupingConfigurationsApi.getTableGroupingConfigurations(
      connectionName,
      schemaName,
      tableName
    ).then((res) => {
      setDataGroupingConfigurations(res.data);
    });
  };

  const onEdit = (
    groupingConfiguration: DataGroupingConfigurationListModel
  ) => {
    setSelectedDataGroupingConfiguration(groupingConfiguration);
    history.replace(`${location.pathname}?isEditing=true`);
  };

  const onCreate = () => {
    setSelectedDataGroupingConfiguration(undefined);
    history.replace(`${location.pathname}?isEditing=true`);
  };

  const onBack = () => {
    history.replace(`${location.pathname}?isEditing=false`);
  };

  const sortObjects = (
    array: DataGroupingConfigurationListModel[]
  ): DataGroupingConfigurationListModel[] => {
    const sortedArray = array.sort((a, b) =>
      (a.data_grouping_configuration_name ?? '').localeCompare(
        b.data_grouping_configuration_name ?? ''
      )
    );
    return sortedArray;
  };

  useEffect(() => {
    if (levels) {
      setSelectedDataGroupingConfiguration({
        connection_name: connectionName,
        schema_name: schemaName,
        table_name: tableName,
        data_grouping_configuration_name: Object.values(levels)
          .map((x) => (x as any)?.column)
          .join(','),
        default_data_grouping_configuration: true
      });
      history.replace(`${location.pathname}?isEditing=true`);
    }
  }, [levels, connectionName, schemaName, tableName]);

  return (
    <div className="my-1">
      {isEditing ? (
        <DataGroupingConfigurationEditView
          onBack={onBack}
          selectedGroupingConfiguration={selectedDataGroupingConfiguration}
          connection={connectionName}
          schema={schemaName}
          table={tableName}
          getGroupingConfigurations={getDataGroupingConfigurations}
        />
      ) : (
        <DataGroupingConfigurationListView
          dataGroupingConfigurations={sortObjects(dataGroupingConfigurations)}
          getDataGroupingConfigurations={getDataGroupingConfigurations}
          onCreate={onCreate}
          onEdit={onEdit}
        />
      )}
    </div>
  );
};

export default TableDataGroupingConfiguration;
