import React from "react";
import { RuleActionGroup } from "../../components/Sensors/RuleActionGroup";
import { SensorModel } from "../../api";
import Checkbox from "../../components/Checkbox";
import { setUpdatedSensor } from "../../redux/actions/sensor.actions";
import { useActionDispatch } from "../../hooks/useActionDispatch";
import SectionWrapper from "../../components/Dashboard/SectionWrapper";
import RuleFields from "../../components/Sensors/RuleFields";
import RuleParameters from "../../components/Sensors/RuleParameters";

type SensorDefinitionProps = {
  sensor: SensorModel;
};

export const SensorDefinition = ({ sensor }: SensorDefinitionProps) => {
  const dispatch = useActionDispatch();

  const onChange = (obj: Partial<SensorModel>) => {
    dispatch(setUpdatedSensor({
      ...sensor,
      ...obj
    }));
  }

  return (
    <div className="p-4">
      <RuleActionGroup />
      {sensor && (
        <>
          <div className="mb-8">
            <div className="flex gap-4 text-sm items-center mb-4">
              <p className="w-60">Requires event timestamp column:</p>
              <Checkbox
                checked={sensor?.sensor_definition_spec?.requires_event_timestamp}
                onChange={(requires_event_timestamp) => onChange({
                  sensor_definition_spec: {
                    ...sensor?.sensor_definition_spec,
                    requires_event_timestamp
                  }
                })}
              />
            </div>
            <div className="flex gap-4 text-sm items-center mb-4">
              <p className="w-60">Requires ingetion timestamp column:</p>
              <Checkbox
                checked={sensor?.sensor_definition_spec?.requires_ingestion_timestamp}
                onChange={(requires_ingestion_timestamp) => onChange({
                  sensor_definition_spec: {
                    ...sensor?.sensor_definition_spec,
                    requires_ingestion_timestamp
                  }
                })}
              />
            </div>
          </div>

          <SectionWrapper title="Sensor Fields">
            <RuleFields
              fields={sensor.sensor_definition_spec?.fields || []}
              onChange={(fields) => onChange({
                sensor_definition_spec: {
                  ...sensor.sensor_definition_spec,
                  fields
                }
              })}
            />
          </SectionWrapper>
          <SectionWrapper className="mt-8" title="Sensor Parameters">
            <RuleParameters />
          </SectionWrapper>
        </>
      )}
    </div>
  );
};

export default SensorDefinition;

