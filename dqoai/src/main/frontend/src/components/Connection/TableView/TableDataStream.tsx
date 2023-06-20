import React, { useEffect, useState } from 'react';
import { DataStreamBasicModel, DataStreamMappingSpec } from '../../../api';
import DataStreamListView from "./DataStreamListView";
import DataStreamEditView from "./DataStreamEditView";
import { DataStreamsApi } from "../../../services/apiClient";
import { useParams, useLocation} from "react-router-dom";

interface MyLocationState {
  bool: boolean;
  data_stream_name: string;
  spec: DataStreamMappingSpec;
}
const TableDataStream = () => {
  const { connection: connectionName, schema: schemaName, table: tableName }: { connection: string, schema: string, table: string } = useParams();
  const [isEditing, setIsEditing] = useState(false);
  const [myState, setState] = useState<MyLocationState | undefined>();
  const [dataStreams, setDataStreams] = useState<DataStreamBasicModel[]>([]);
  const [selectedDataStream, setSelectedDataStream] = useState<DataStreamBasicModel>();

  const location = useLocation();

  const state = location.state as MyLocationState | undefined;
  console.log(state)

  const dSName = state?.data_stream_name

  useEffect(() => {
    getDataStreams();
    setState(location.state as MyLocationState | undefined)
  }, [connectionName, schemaName, tableName, myState]);
  
  const getDataStreams = () => {
    DataStreamsApi.getDataStreams(connectionName, schemaName, tableName).then((res) => {
      setDataStreams(res.data);
    });
  };

  console.log(myState)

  const onEdit = (stream: DataStreamBasicModel) => {
    setSelectedDataStream(stream);
    setIsEditing(true);
  };

  const onCreate = () => {
    setSelectedDataStream(undefined);
    setIsEditing(true);
  };

  const myObj : DataStreamBasicModel = {
    connection_name: connectionName,
    schema_name: schemaName,
    table_name: tableName, 
    data_stream_name: dSName
  }

  return (
    <div className="my-1">
    
      {location.state  ? <DataStreamEditView
            onBack={() => setIsEditing(false)}
            selectedDataStream={myObj}
            connection={connectionName}
            schema={schemaName}
            table={tableName}
            getDataStreams={getDataStreams}
          /> 
        :
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
