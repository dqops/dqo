import React from 'react';
import { TableConsistencyRowCountSensorParametersSpec } from '../../api';
import Checkbox from '../Checkbox';
import TextArea from '../TextArea';

interface ISensorParametersSettingsProps {
  parameters?: TableConsistencyRowCountSensorParametersSpec;
}

const SensorParametersSettings = ({
  parameters
}: ISensorParametersSettingsProps) => {
  return (
    <div>
      <div className="">
        <div className="mb-3">
          <Checkbox
            checked={parameters?.disabled}
            onChange={() => {}}
            label="Disabled"
          />
        </div>
        <TextArea
          className="!bg-white !text-gray-700 !border-gray-300"
          label="Filter"
          value={parameters?.filter}
        />
      </div>
    </div>
  );
};

export default SensorParametersSettings;
