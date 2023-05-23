import React from 'react';
import { TableVolumeRowCountSensorParametersSpec } from '../../api';
import Checkbox from '../Checkbox';
import TextArea from '../TextArea';

interface ISensorParametersSettingsProps {
  parameters?: TableVolumeRowCountSensorParametersSpec;
}

const SensorParametersSettings = ({
  parameters
}: ISensorParametersSettingsProps) => {
  
  const onChangeDisabled = () => {};

  return (
    <div>
      <div className="">
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
