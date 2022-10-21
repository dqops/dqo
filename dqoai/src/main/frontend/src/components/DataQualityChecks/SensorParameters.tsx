import React from 'react';
import { UIFieldModel } from '../../api';
import FieldControl from './FieldControl';

interface ISensorParametersProps {
  parameters: UIFieldModel[];
  openCheckSensorParameter: (field: UIFieldModel) => void;
  onChange: (parameters: UIFieldModel[]) => void;
}

const SensorParameters = ({
  parameters,
  openCheckSensorParameter,
  onChange
}: ISensorParametersProps) => {
  const handleChange = (field: UIFieldModel, idx: number) => {
    const newParameters = parameters.map((item, index) =>
      index === idx ? field : item
    );
    onChange(newParameters);
  };

  return (
    <div className="w-full pr-8 py-2">
      {parameters.length ? (
        <div>
          {parameters.map((item, index) => (
            <div key={index} className="mb-3">
              <FieldControl
                field={item}
                onChange={(field: UIFieldModel) => handleChange(field, index)}
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
