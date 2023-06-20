import React, { useEffect, useState } from 'react';
import { DataStreamBasicModel } from '../../../api';
import DataStreamListView from "./DataStreamListView";
import DataStreamEditView from "./DataStreamEditView";
import { DataStreamsApi } from "../../../services/apiClient";
import { useParams} from "react-router-dom";
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { setCreatedDataStream } from '../../../redux/actions/rule.actions';

const TableDataStream = () => {
  const { connection: connectionName, schema: schemaName, table: tableName }: { connection: string, schema: string, table: string } = useParams();
  const [isEditing, setIsEditing] = useState(false);
  const [dataStreams, setDataStreams] = useState<DataStreamBasicModel[]>([]);
  const [selectedDataStream, setSelectedDataStream] = useState<DataStreamBasicModel>();

  const {dataStreamName, bool} = useSelector((state: IRootState) => state.job || {})

  const actionDispatch = useActionDispatch();
  const setBackData = () =>{
    actionDispatch(setCreatedDataStream(false, "", {}))
  }
  useEffect(() => {
    getDataStreams()
      .catch((error) => {
        console.error(error);
      });
  }, [connectionName, schemaName, tableName]);
  
  const getDataStreams = () => {
    return new Promise<void>((resolve, reject) => {
      DataStreamsApi.getDataStreams(connectionName, schemaName, tableName)
        .then((res) => {
          setDataStreams(res.data);
          resolve();
        })
        .catch((error) => {
          reject(error);
        });
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
      
        {isEditing || bool ? (
          <DataStreamEditView
            onBack={() => {setIsEditing(false), setBackData()}}
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
