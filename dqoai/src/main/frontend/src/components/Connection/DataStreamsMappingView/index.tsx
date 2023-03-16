import React from 'react';
import { DataStreamMappingSpec, DataStreamLevelSpec } from '../../../api';
import DataStreamLevelItem from '../../DataQualityChecks/DataStreamLevelItem';
import { Errors } from "../TableView/DataStreamEditView";
import { useParams } from "react-router-dom";

interface IDataStreamsMappingViewProps {
  dataStreamsMapping?: DataStreamMappingSpec;
  onChange: (value: DataStreamMappingSpec) => void;
  errors?: Errors;
  onClearError?: (idx: number) => void;
}

const DataStreamsMappingView = ({
  dataStreamsMapping,
  onChange,
  errors,
  onClearError
}: IDataStreamsMappingViewProps) => {
  const { table }: { table: string } = useParams();

  const getDataStreamLevel = (index: number) => {
    if (index === 0) return dataStreamsMapping?.level_1;
    if (index === 1) return dataStreamsMapping?.level_2;
    if (index === 2) return dataStreamsMapping?.level_3;
    if (index === 3) return dataStreamsMapping?.level_4;
    if (index === 4) return dataStreamsMapping?.level_5;
    if (index === 5) return dataStreamsMapping?.level_6;
    if (index === 6) return dataStreamsMapping?.level_7;
    if (index === 7) return dataStreamsMapping?.level_8;
    if (index === 8) return dataStreamsMapping?.level_9;
  };

  const onChangeDataStreamsLevel = (
    dataStreamsLevel: DataStreamLevelSpec,
    index: number
  ) => {
    onChange({
      ...(dataStreamsMapping || {}),
      [`level_${index + 1}`]: dataStreamsLevel
    });
  };

  return (
    <div className="py-4 px-6">
      {Array(9)
        .fill(0)
        .map((item, index) => (
          <DataStreamLevelItem
            idx={index}
            key={index}
            dataStreamLevel={getDataStreamLevel(index)}
            onChange={(dataStreamsLevel) =>
              onChangeDataStreamsLevel(dataStreamsLevel, index)
            }
            scope={table ? "table" : "connection"}
            error={errors ? errors[`level_${index + 1}`] : ''}
            onClearError={onClearError}
          />
        ))}
    </div>
  );
};

export default DataStreamsMappingView;
