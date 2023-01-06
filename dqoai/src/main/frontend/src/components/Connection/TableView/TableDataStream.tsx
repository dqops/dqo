import React, { useEffect, useState } from 'react';
import { DataStreamBasicModel } from '../../../api';
import DataStreamListView from "./DataStreamListView";
import DataStreamEditView from "./DataStreamEditView";
import { DataStreamsApi } from "../../../services/apiClient";

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
  const [isEditing, setIsEditing] = useState(false);

  const [dataStreams, setDataStreams] = useState<DataStreamBasicModel[]>([]);
  const [selectedDataStream, setSelectedDataStream] = useState<DataStreamBasicModel>();

  useEffect(() => {
    getDataStreams();
  }, [connectionName, schemaName, tableName]);

  const getDataStreams = () => {
    DataStreamsApi.getDataStreams(connectionName, schemaName, tableName).then((res) => {
      setDataStreams(res.data);
    });
  };

  const onEdit = (stream: DataStreamBasicModel) => {
    setSelectedDataStream(stream);
    setIsEditing(true);
  };

  const onCreate = () => {
    setSelectedDataStream(undefined);
    setIsEditing(true);
  };

  return (
    <div>
      {
        isEditing ? (
          <DataStreamEditView
            onBack={() => setIsEditing(false)}
            selectedDataStream={selectedDataStream}
            connection={connectionName}
            schema={schemaName}
            table={tableName}
            getDataStreams={getDataStreams}
          />
        ) : (
          <DataStreamListView
            dataStreams={dataStreams}
            getDataStreams={getDataStreams}
            onCreate={onCreate}
            onEdit={onEdit}
          />
        )
      }
    </div>
  );
};

export default TableDataStream;
