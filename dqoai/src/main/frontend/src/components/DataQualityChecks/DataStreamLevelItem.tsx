import React from 'react';
import { DataStreamLevelSpec, DataStreamLevelSpecSourceEnum } from '../../api';
import Input from '../Input';
import ColumnSelect from './ColumnSelect';
import RadioButton from '../RadioButton';

interface IDataStreamLevelItemProps {
  dataStreamLevel?: DataStreamLevelSpec;
  idx: number;
  onChange: (dataStreamLevel: DataStreamLevelSpec) => void;
  scope?: string;
}

const DataStreamLevelItem = ({
  idx,
  onChange,
  dataStreamLevel,
  scope
}: IDataStreamLevelItemProps) => {
  return (
    <div className="mb-4 last:mb-0">
      <div className="flex justify-between items-center space-x-6">
        <div className="text-sm font-semibold flex-1">{`Data stream level ${
          idx + 1
        }`}</div>
        <div className="flex-1">
          <RadioButton
            checked={dataStreamLevel?.source === undefined}
            label="None"
            onClick={() => onChange({ ...dataStreamLevel, source: undefined })}
          />
        </div>
        <div className="">
          <RadioButton
            checked={
              dataStreamLevel?.source === DataStreamLevelSpecSourceEnum.tag
            }
            label="Tag"
            onClick={() =>
              onChange({
                ...dataStreamLevel,
                source: DataStreamLevelSpecSourceEnum.tag
              })
            }
          />
        </div>
        <div className="flex-1">
          <Input
            className="h-8"
            value={dataStreamLevel?.tag}
            onChange={(e) =>
              onChange({
                ...dataStreamLevel,
                tag: e.target.value
              })
            }
            disabled={
              dataStreamLevel?.source !== DataStreamLevelSpecSourceEnum.tag
            }
          />
        </div>
        <RadioButton
          checked={
            dataStreamLevel?.source ===
            DataStreamLevelSpecSourceEnum.column_value
          }
          label="Group by column"
          onClick={() =>
            onChange({
              ...dataStreamLevel,
              source: DataStreamLevelSpecSourceEnum.column_value
            })
          }
        />
        <div className="flex-1">
          <ColumnSelect
            triggerClassName="!h-8"
            disabled={
              dataStreamLevel?.source !==
              DataStreamLevelSpecSourceEnum.column_value
            }
            value={dataStreamLevel?.column}
            onChange={(value) =>
              onChange({
                ...dataStreamLevel,
                column: value
              })
            }
            scope={scope}
          />
        </div>
      </div>
    </div>
  );
};

export default DataStreamLevelItem;
