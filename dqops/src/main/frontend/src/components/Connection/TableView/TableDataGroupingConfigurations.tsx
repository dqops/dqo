import React, { useEffect, useState } from 'react';
import { DataGroupingConfigurationListModel } from '../../../api';
import DataGroupingConfigurationListView from './DataGroupingConfigurationListView';
import DataGroupingConfigurationEditView from './DataGroupingConfigurationEditView';
import { DataGroupingConfigurationsApi } from '../../../services/apiClient';
import { useHistory, useLocation, useParams } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { setCreatedDataStream } from '../../../redux/actions/definition.actions';
import { IRootState } from '../../../redux/reducers';
import qs from 'query-string';

const TableDataGroupingConfiguration = () => {
  const {
    connection: connectionName,
    schema: schemaName,
    table: tableName
  }: { connection: string; schema: string; table: string } = useParams();
  const location = useLocation();
  const [isEditing, setIsEditing] = useState(false);
  const { dataGrouping, bool } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const history = useHistory();

  const actionDispatch = useActionDispatch();
  const setBackData = () => {
    actionDispatch(setCreatedDataStream(false, '', {}));
  };
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

  const myObj: DataGroupingConfigurationListModel = {
    connection_name: connectionName,
    schema_name: schemaName,
    table_name: tableName,
    data_grouping_configuration_name: dataGrouping
  };

  const onBack = () => {
    history.replace(`${location.pathname}?isEditing=false`);
    setBackData();
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

  return (
    <div className="my-1">
      {isEditing || bool ? (
        <DataGroupingConfigurationEditView
          onBack={onBack}
          selectedGroupingConfiguration={
            dataGrouping.length !== 0
              ? myObj
              : selectedDataGroupingConfiguration
          }
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
