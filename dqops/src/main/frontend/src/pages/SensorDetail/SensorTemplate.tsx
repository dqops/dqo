import React from 'react';
import { ProviderSensorModel } from '../../api';
import Tabs from '../../components/Tabs';
import Jinja2Code from './Jinja2';
type SensorTemplateProps = {
  providerSensor?: ProviderSensorModel;
  onChange: (obj: Partial<ProviderSensorModel>) => void;
};

const tabs = [
  {
    label: 'Sensor template',
    value: 'sensor_template'
  },
  {
    label: 'Error sampling template',
    value: 'error_sampling_template'
  }
];

export default function SensorTemplate({
  providerSensor,
  onChange
}: SensorTemplateProps) {
  const [activeTab, setActiveTab] = React.useState('sensor_template');

  const onChangeSensor = (value: string) => {
    onChange({ sqlTemplate: value });
  };
  const onChangeError = (value: string) => {
    onChange({ errorSamplingTemplate: value });
  };

  return (
    <div>
      <div className="border-b border-gray-300 px-0">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
      </div>
      {activeTab === 'sensor_template' ? (
        <Jinja2Code
          value={providerSensor?.sqlTemplate}
          onChange={onChangeSensor}
        />
      ) : (
        <Jinja2Code
          value={providerSensor?.errorSamplingTemplate}
          onChange={onChangeError}
        />
      )}
    </div>
  );
}
