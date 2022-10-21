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
  onChange: (dimension: DimensionMappingSpec) => void;
}

const DimensionItem = ({ idx, onChange, dimension }: IDimensionItemProps) => {
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
        <Select
          options={options}
          label="Source"
          value={dimension?.source}
          onChange={(value) => onChange({ ...dimension, source: value })}
        />
        <div className="flex-1">
          <Input
            label="Value"
            value={
              dimension?.source === DimensionMappingSpecSourceEnum.static_value
                ? dimension?.static_value
                : dimension?.column
            }
            onChange={(e) =>
              onChange({
                ...dimension,
                ...(dimension?.source ===
                DimensionMappingSpecSourceEnum.static_value
                  ? {
                      static_value: e.target.value
                    }
                  : {
                      column: e.target.value
                    })
              })
            }
          />
        </div>
      </div>
    </div>
  );
};

export default DimensionItem;
