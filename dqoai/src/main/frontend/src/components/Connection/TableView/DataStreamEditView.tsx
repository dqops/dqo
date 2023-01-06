import React, { ChangeEvent, useEffect, useState } from "react";
import ActionGroup from "./TableActionGroup";
import DataStreamsMappingView from "../DataStreamsMappingView";
import { DataStreamBasicModel, DataStreamMappingSpec } from "../../../api";
import Input from "../../Input";
import Button from "../../Button";
import SvgIcon from "../../SvgIcon";
import { DataStreamsApi } from "../../../services/apiClient";

interface IDataStreamEditViewProps {
  onBack: () => void;
  selectedDataStream?: DataStreamBasicModel;
  connection: string;
  schema: string;
  table: string;
  getDataStreams: () => void;
}

const DataStreamEditView = ({
  onBack,
  selectedDataStream,
  connection,
  schema,
  table,
  getDataStreams
}: IDataStreamEditViewProps) => {
  const [isUpdated, setIsUpdated] = useState(false);
  const [isUpdating, setIsUpdating] = useState(false);
  const [dataStreamsMapping, setDataStreamMapping] = useState<DataStreamMappingSpec>();
  const [name, setName] = useState('');

  useEffect(() => {
    if (selectedDataStream) {
      DataStreamsApi.getDataStream(
        connection,
        schema,
        table,
        selectedDataStream.data_stream_name || ''
      ).then(res => {
        setDataStreamMapping(res.data.spec);
      });
    }
  }, [selectedDataStream]);

  const onUpdate = async () => {
    try {
      setIsUpdating(true);
      if (selectedDataStream) {
        await DataStreamsApi.updateDataStream(
          connection,
          schema,
          table,
          selectedDataStream.data_stream_name || '',
          {
            data_stream_name: name,
            spec: dataStreamsMapping
          }
        )
      } else {
        await DataStreamsApi.createDataStream(connection, schema, table, {
          data_stream_name: name,
          spec: dataStreamsMapping
        });
      }
      setIsUpdated(false);
      getDataStreams();
      onBack();
    } finally {
      setIsUpdating(false);
    }
  };

  const onChangeName = (e: ChangeEvent<HTMLInputElement>) => {
    setName(e.target.value);
    setIsUpdated(true);
  }

  const onChangeDataStreamsMapping = (spec: DataStreamMappingSpec) => {
    if (name || selectedDataStream) {
      setIsUpdated(true);
    }
    setDataStreamMapping(spec);
  }

  return (
    <div>
      <ActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdated}
        isUpdating={isUpdating}
      />
      <div className="flex py-4 border-b border-gray-300 px-6 justify-between items-center">
        {selectedDataStream ? (
          <div>{selectedDataStream?.data_stream_name}</div>
        ) : (
          <div className="flex space-x-4 items-center">
            <div>Data stream name</div>
            <Input className="w-80" value={name} onChange={onChangeName} />
          </div>
        )}
        <Button
          label="Back"
          color="primary"
          variant="text"
          leftIcon={<SvgIcon name="chevron-left" className="w-4 h-4 mr-2" />}
          onClick={onBack}
        />
      </div>
      <DataStreamsMappingView
        dataStreamsMapping={dataStreamsMapping}
        onChange={onChangeDataStreamsMapping}
      />
    </div>
  );
};

export default DataStreamEditView;
