import React from "react";
import {
  ProviderSensorDefinitionSpec,
  ProviderSensorDefinitionSpecTypeEnum,
  ProviderSensorModel,
} from "../../api";
import Select from "../../components/Select";
import Input from "../../components/Input";
import Checkbox from "../../components/Checkbox";
import SectionWrapper from "../../components/Dashboard/SectionWrapper";
import RuleParameters from "../../components/Sensors/RuleParameters";
import Jinja2Code from "./Jinja2";

type ProvideSensorProps = {
  providerSensor?: ProviderSensorModel;
  onChange?: (value: ProviderSensorModel) => void;
}

const runnerTypeOptions = Object.values(ProviderSensorDefinitionSpecTypeEnum).map((item) => ({
  label: item,
  value: item
}));

const ProvideSensor = ({
  providerSensor,
  onChange
}: ProvideSensorProps) => {

  const handleChange = (obj: Partial<ProviderSensorDefinitionSpec>) => {
    if (!onChange) {
      return;
    }

    onChange({
      ...providerSensor || {},
      providerSensorDefinitionSpec: {
        ...providerSensor?.providerSensorDefinitionSpec || {},
        ...obj
      },
    })
  };

  return (
    <div className="p-4">
      <div className="flex gap-4 text-sm items-center mb-4">
        <p className="w-60">Sensor runner type:</p>
        <Select
          options={runnerTypeOptions}
          value={providerSensor?.providerSensorDefinitionSpec?.type}
          onChange={(type) => handleChange({ type })}
        />
      </div>
      {providerSensor?.providerSensorDefinitionSpec?.type === ProviderSensorDefinitionSpecTypeEnum.java_class && (
        <div className="flex gap-4 text-sm items-center mb-4">
          <p className="w-60">Java sensor class:</p>
          <Input
            value={providerSensor?.providerSensorDefinitionSpec?.java_class_name}
            onChange={(e) => handleChange({ java_class_name: e.target.value })}
          />
        </div>
      )}

      <div className="flex gap-4 text-sm items-center mb-4">
        <p className="w-60">Supports grouping:</p>
        <Checkbox
          checked={providerSensor?.providerSensorDefinitionSpec?.supports_grouping}
          onChange={(checked) => handleChange({
            supports_grouping: checked
          })}
        />
      </div>


      <SectionWrapper className="mt-8 mb-4" title="Sensor Parameters">
        <RuleParameters
          parameters={providerSensor?.providerSensorDefinitionSpec?.parameters}
          onChange={(parameters) => handleChange({
            parameters
          })}
        />
      </SectionWrapper>


      <Jinja2Code providerSensor={providerSensor} />
    </div>
  );
};

export default ProvideSensor;