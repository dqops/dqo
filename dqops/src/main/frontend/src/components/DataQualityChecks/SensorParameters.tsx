import React from 'react';
import { FieldModel } from '../../api';
import FieldControl from './FieldControl';

interface ISensorParametersProps {
  parameters: FieldModel[];
  onChange: (parameters: FieldModel[]) => void;
  disabled?: boolean;
  onUpdate: () => void;
}

const SensorParameters = ({
  parameters,
  onChange,
  disabled,
  onUpdate,
}: ISensorParametersProps) => {
  const handleChange = (field: FieldModel, idx: number) => {
    const newParameters = parameters.map((item, index) =>
      index === idx ? field : item
    );
    onChange(newParameters);
  };

  return (
    <div className="w-full pr-8">
      {parameters.length ? (
        <div className="flex space-x-2">
          {parameters.map((item, index) => (
            <div key={index} className="">
              <FieldControl
                field={item}
                onChange={(field: FieldModel) => handleChange(field, index)}
                disabled={disabled}
                onSave={onUpdate}
              />
            </div>
          ))}
        </div>
      ) : (
        <div>No sensor parameters</div>
      )}
    </div>
  );
};

export default SensorParameters;
