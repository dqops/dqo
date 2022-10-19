import React from 'react';
import { UIFieldModel } from '../../api';
import FieldControl from './FieldControl';

interface ISensorParametersProps {
  parameters: UIFieldModel[];
  openCheckSensorParameter: (field: UIFieldModel) => void;
}

const SensorParameters = ({
  parameters,
  openCheckSensorParameter
}: ISensorParametersProps) => {
  return (
    <div className="w-full pr-8 py-2">
      {parameters.length ? (
        <div>
          {parameters.map((item, index) => (
            <div key={index} className="mb-3">
              <FieldControl field={item} />
            </div>
          ))}
          <FieldControl
            field={{
              definition: {
                field_name: 'string_list',
                display_name: 'string_list',
                help_hext: 'Select labels for this field',
                data_type: 'string_list'
              },
              string_list_value: ['ACB', 'DEF']
            }}
          />
        </div>
      ) : (
        <div>No sensor parameters</div>
      )}
    </div>
  );
};

export default SensorParameters;
