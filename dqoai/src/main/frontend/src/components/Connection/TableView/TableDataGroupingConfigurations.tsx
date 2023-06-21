import React, { useEffect, useState } from 'react';
import { DataGroupingConfigurationBasicModel } from '../../../api';
import DataGroupingConfigurationListView from "./DataGroupingConfigurationListView";
import DataGroupingConfigurationEditView from "./DataGroupingConfigurationEditView";
import { DataGroupingConfigurationsApi } from "../../../services/apiClient";
import { useParams } from "react-router-dom";

const TableDataGroupingConfiguration = () => {
  const { connection: connectionName, schema: schemaName, table: tableName }: { connection: string, schema: string, table: string } = useParams();
  const [isEditing, setIsEditing] = useState(false);

  const [dataGroupingConfigurations, setDataGroupingConfigurations] = useState<DataGroupingConfigurationBasicModel[]>([]);
  const [selectedDataGroupingConfiguration, setSelectedDataGroupingConfiguration] = useState<DataGroupingConfigurationBasicModel>();

  useEffect(() => {
    getDataGroupingConfigurations();
  }, [connectionName, schemaName, tableName]);

  const getDataGroupingConfigurations = () => {
    DataGroupingConfigurationsApi.getTableGroupingConfigurations(connectionName, schemaName, tableName).then((res) => {
      setDataGroupingConfigurations(res.data);
    });
  };

  const onEdit = (groupingConfiguration: DataGroupingConfigurationBasicModel) => {
    setSelectedDataGroupingConfiguration(groupingConfiguration);
    setIsEditing(true);
  };

  const onCreate = () => {
    setSelectedDataGroupingConfiguration(undefined);
    setIsEditing(true);
  };

  return (
    <div className="my-1">
      {
        isEditing ? (
          <DataGroupingConfigurationEditView
            onBack={() => setIsEditing(false)}
            selectedGroupingConfiguration={selectedDataGroupingConfiguration}
            connection={connectionName}
            schema={schemaName}
            table={tableName}
            getGroupingConfigurations={getDataGroupingConfigurations}
          />
        ) : (
          <DataGroupingConfigurationListView
            dataGroupingConfigurations={dataGroupingConfigurations}
            getDataGroupingConfigurations={getDataGroupingConfigurations}
            onCreate={onCreate}
            onEdit={onEdit}
          />
        )
      }
    </div>
  );
};

export default TableDataGroupingConfiguration;
