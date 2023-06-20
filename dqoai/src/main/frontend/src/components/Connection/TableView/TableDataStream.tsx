import React, { useEffect, useState } from 'react';
import { DataStreamBasicModel } from '../../../api';
import DataStreamListView from "./DataStreamListView";
import DataStreamEditView from "./DataStreamEditView";
import { DataStreamsApi } from "../../../services/apiClient";
import { useParams} from "react-router-dom";
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';

const TableDataStream = () => {
  const { connection: connectionName, schema: schemaName, table: tableName }: { connection: string, schema: string, table: string } = useParams();
  const [isEditing, setIsEditing] = useState(false);
  const [dataStreams, setDataStreams] = useState<DataStreamBasicModel[]>([]);
  const [selectedDataStream, setSelectedDataStream] = useState<DataStreamBasicModel>();

  const {bool, dataStreamName, spec} = useSelector((state: IRootState) => state.job || {})

  console.log(dataStreamName)

  const checkingFunc = () =>{
    dataStreams.map((x) => x.data_stream_name===dataStreamName ? setIsEditing(true) : "")
  }
console.log(isEditing)
  useEffect(() => {
    getDataStreams()
    checkingFunc()
    
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

  const myObj : DataStreamBasicModel = {
    connection_name: connectionName,
    schema_name: schemaName,
    table_name: tableName, 
    data_stream_name: dataStreamName
  }

  return (
    <div className="my-1">
    
        {isEditing ? (
          <DataStreamEditView
            onBack={() => setIsEditing(false)}
            selectedDataStream={myObj || selectedDataStream}
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
