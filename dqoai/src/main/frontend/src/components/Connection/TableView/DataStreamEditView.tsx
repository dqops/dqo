import React, { ChangeEvent, useEffect, useState } from "react";
import ActionGroup from "./TableActionGroup";
import DataStreamsMappingView from "../DataStreamsMappingView";
import { DataStreamBasicModel, DataStreamLevelSpecSourceEnum, DataStreamMappingSpec } from "../../../api";
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

export interface Errors {
  [key: string]: string;
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
  const [error, setError] = useState('');
  const [levelErrors, setLevelErrors] = useState<Errors>({});

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
        if (!dataStreamsMapping) {
          setError('Stream Mapping is Required');
          return;
        }
        const errors: Errors = {};

        Object.entries(dataStreamsMapping).forEach(([level, item]) => {
          if (item.source === DataStreamLevelSpecSourceEnum.tag && !item.tag) {
            errors[level] = 'Tag is Required';
          }
        });

        if (Object.values(errors).length) {
          setLevelErrors(errors);
          return;
        }

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

  const onClearError = (idx: number) => {
    setLevelErrors({
      ...levelErrors,
      [`level_${idx}`]: ''
    });
  };

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
      {error && (
        <div className="text-red-700 text-xs pt-4 px-6">{error}</div>
      )}
      <DataStreamsMappingView
        dataStreamsMapping={dataStreamsMapping}
        onChange={onChangeDataStreamsMapping}
        errors={levelErrors}
        onClearError={onClearError}
      />
    </div>
  );
};

export default DataStreamEditView;
