import React from 'react';
import {
  DimensionMappingSpec,
  DimensionMappingSpecSourceEnum
} from '../../api';
import Select from '../Select';
import Input from '../Input';

interface IDimensionItemProps {
  dimension?: DimensionMappingSpec;
  idx: number;
}

const DimensionItem = ({ idx }: IDimensionItemProps) => {
  const options = [
    {
      value: DimensionMappingSpecSourceEnum.static_value,
      label: 'Static Value'
    },
    {
      value: DimensionMappingSpecSourceEnum.dynamic_from_group_by_column,
      label: 'Column'
    }
  ];

  return (
    <div className="mb-4 last:mb-0">
      <div className="text-sm font-semibold mb-2">{`Dimension ${idx + 1}`}</div>
      <div className="flex justify-between items-center space-x-6">
        <Select options={options} label="Source" />
        <div className="flex-1">
          <Input label="Value" />
        </div>
      </div>
    </div>
  );
};

export default DimensionItem;
