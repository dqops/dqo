import React from 'react';
import {
  DimensionMappingSpec,
  DimensionMappingSpecSourceEnum
} from '../../api';
import Input from '../Input';
import ColumnSelect from './ColumnSelect';
import RadioButton from '../RadioButton';

interface IDimensionItemProps {
  dimension?: DimensionMappingSpec;
  idx: number;
  onChange: (dimension: DimensionMappingSpec) => void;
  scope?: string;
}

const DimensionItem = ({
  idx,
  onChange,
  dimension,
  scope
}: IDimensionItemProps) => {
  return (
    <div className="mb-4 last:mb-0">
      <div className="flex justify-between items-center space-x-6">
        <div className="text-sm font-semibold flex-1">{`Dimension ${
          idx + 1
        }`}</div>
        <div className="flex-1">
          <RadioButton
            checked={dimension?.source === undefined}
            label="None"
            onClick={() => onChange({ ...dimension, source: undefined })}
          />
        </div>
        <div className="">
          <RadioButton
            checked={
              dimension?.source === DimensionMappingSpecSourceEnum.static_value
            }
            label="Static Value"
            onClick={() =>
              onChange({
                ...dimension,
                source: DimensionMappingSpecSourceEnum.static_value
              })
            }
          />
        </div>
        <div className="flex-1">
          <Input
            className="h-8"
            value={dimension?.static_value}
            onChange={(e) =>
              onChange({
                ...dimension,
                static_value: e.target.value
              })
            }
            disabled={
              dimension?.source !== DimensionMappingSpecSourceEnum.static_value
            }
          />
        </div>
        <RadioButton
          checked={
            dimension?.source ===
            DimensionMappingSpecSourceEnum.dynamic_from_group_by_column
          }
          label="Group by column"
          onClick={() =>
            onChange({
              ...dimension,
              source:
                DimensionMappingSpecSourceEnum.dynamic_from_group_by_column
            })
          }
        />
        <div className="flex-1">
          <ColumnSelect
            disabled={
              dimension?.source !==
              DimensionMappingSpecSourceEnum.dynamic_from_group_by_column
            }
            value={dimension?.column}
            onChange={(value) =>
              onChange({
                ...dimension,
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

export default DimensionItem;
