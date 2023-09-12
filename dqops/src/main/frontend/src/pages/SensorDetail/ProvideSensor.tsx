import React from "react";
import {
  ProviderSensorDefinitionSpec,
  ProviderSensorDefinitionSpecTypeEnum,
  ProviderSensorModel, ProviderSensorModelProviderTypeEnum,
} from "../../api";
import Select from "../../components/Select";
import Input from "../../components/Input";
import Checkbox from "../../components/Checkbox";
import SectionWrapper from "../../components/Dashboard/SectionWrapper";
import RuleParameters from "../../components/Sensors/RuleParameters";
import Jinja2Code from "./Jinja2";
import { useSelector } from "react-redux";
import { IRootState } from "../../redux/reducers";

type ProvideSensorProps = {
  providerSensor?: ProviderSensorModel;
  onChange?: (value: ProviderSensorModel) => void;
  providerType?: ProviderSensorModelProviderTypeEnum;
}

const runnerTypeOptions = Object.values(ProviderSensorDefinitionSpecTypeEnum).map((item) => ({
  label: item,
  value: item
}));

const ProvideSensor = ({
  providerSensor,
  onChange,
  providerType
}: ProvideSensorProps) => {
  const {  userProfile } = useSelector(
    (state: IRootState) => state.job || {}
  );

  const handleChange = (obj: Partial<ProviderSensorDefinitionSpec>) => {
    if (!onChange) {
      return;
    }

    onChange({
      ...providerSensor || { providerType },
      providerSensorDefinitionSpec: {
        ...providerSensor?.providerSensorDefinitionSpec || {},
        ...obj
      },
    })
  };

  const handleChangeTemplate = (obj: Partial<ProviderSensorModel>) => {
    if (!onChange) {
      return;
    }

    onChange({
      ...providerSensor || {},
      ...obj,
    })
  }

  return (
    <div className="p-4">
      <div className="flex gap-4 text-sm items-center mb-4">
        <p className="w-60">Sensor runner type:</p>
        <Select
          options={runnerTypeOptions}
          value={providerSensor?.providerSensorDefinitionSpec?.type}
          onChange={(type) => handleChange({ type })}
          disabled={userProfile.can_manage_definitions !== true}

        />
      </div>
      {providerSensor?.providerSensorDefinitionSpec?.type === ProviderSensorDefinitionSpecTypeEnum.java_class && (
        <div className="flex gap-4 text-sm items-center mb-4">
          <p className="w-60">Java sensor class:</p>
          <Input
            value={providerSensor?.providerSensorDefinitionSpec?.java_class_name}
            onChange={(e) => handleChange({ java_class_name: e.target.value })}
            disabled={userProfile.can_manage_definitions !== true}

          />
        </div>
      )}

      <div className="flex gap-4 text-sm items-center mb-4">
        <p className="w-60">Supports grouping by GROUP BY:</p>
        <Checkbox
          checked={providerSensor?.providerSensorDefinitionSpec?.supports_grouping}
          onChange={(checked) => handleChange({
            supports_grouping: checked
          })}
          disabled={userProfile.can_manage_definitions !== true}

        />
      </div>

      <div className="flex gap-4 text-sm items-center mb-4">
        <p className="w-60">Supports partitioned checks by grouping by the partition date:</p>
        <Checkbox
          checked={providerSensor?.providerSensorDefinitionSpec?.supports_partitioned_checks}
          onChange={(checked) => handleChange({
            supports_partitioned_checks: checked
          })}
          disabled={userProfile.can_manage_definitions !== true}

        />
      </div>


      <SectionWrapper className="mt-8 mb-4" title="Sensor Parameters">
        <RuleParameters
          parameters={providerSensor?.providerSensorDefinitionSpec?.parameters}
          onChange={(parameters) => handleChange({
            parameters
          })}
          canUserEdit={userProfile.can_manage_definitions}
        />
      </SectionWrapper>

          {providerSensor?.providerSensorDefinitionSpec?.type !== ProviderSensorDefinitionSpecTypeEnum.java_class &&

            <Jinja2Code
            providerSensor={providerSensor}
            onChange={handleChangeTemplate}
            
            />
          }
    </div>
  );
};

export default ProvideSensor;