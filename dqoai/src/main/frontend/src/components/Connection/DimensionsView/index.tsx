import React from 'react';
import {
  DimensionMappingSpec,
  DimensionsConfigurationSpec
} from '../../../api';
import DimensionItem from '../../DataQualityChecks/DimensionItem';

interface IDimensionsViewProps {
  dimensions?: DimensionsConfigurationSpec;
  onChange: (value: DimensionsConfigurationSpec) => void;
}

const DimensionsView = ({ dimensions, onChange }: IDimensionsViewProps) => {
  const getDimension = (index: number) => {
    if (index === 0) return dimensions?.dimension_1;
    if (index === 1) return dimensions?.dimension_2;
    if (index === 2) return dimensions?.dimension_3;
    if (index === 3) return dimensions?.dimension_4;
    if (index === 4) return dimensions?.dimension_5;
    if (index === 5) return dimensions?.dimension_6;
    if (index === 6) return dimensions?.dimension_7;
    if (index === 7) return dimensions?.dimension_8;
    if (index === 8) return dimensions?.dimension_9;
  };

  const onChangeDimensions = (
    dimension: DimensionMappingSpec,
    index: number
  ) => {
    onChange({
      ...(dimensions || {}),
      [`dimension_${index + 1}`]: dimension
    });
  };

  return (
    <div className="py-4 px-6">
      {Array(9)
        .fill(0)
        .map((item, index) => (
          <DimensionItem
            idx={index}
            key={index}
            dimension={getDimension(index)}
            onChange={(dimension) => onChangeDimensions(dimension, index)}
            scope="connection"
          />
        ))}
    </div>
  );
};

export default DimensionsView;
