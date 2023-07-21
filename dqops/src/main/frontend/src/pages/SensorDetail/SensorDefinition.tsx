import React from "react";
import { ParameterDefinitionSpec, SensorModel } from "../../api";
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
      ...sensor || {},
      ...obj
    }));
  }

  const onAdd = (field: ParameterDefinitionSpec) => {
    dispatch(setUpdatedSensor({
      ...sensor || {},
      sensor_definition_spec: {
        ...sensor?.sensor_definition_spec || {},
        fields: [
          ...sensor?.sensor_definition_spec?.fields || [],
          field
        ]
      }
    }))
  };

  return (
    <div className="p-4">
      <div className="mb-8">
        <div className="flex gap-4 text-sm items-center mb-4">
          <p className="w-60">Requires event timestamp column:</p>
          <Checkbox
            checked={sensor?.sensor_definition_spec?.requires_event_timestamp}
            onChange={(requires_event_timestamp) => onChange({
              sensor_definition_spec: {
                ...sensor?.sensor_definition_spec || {},
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
                ...sensor?.sensor_definition_spec || {},
                requires_ingestion_timestamp
              }
            })}
          />
        </div>
      </div>

      <SectionWrapper title="Sensor Fields">
        <RuleFields
          isReadOnly={sensor?.built_in}
          fields={sensor?.sensor_definition_spec?.fields || []}
          onChange={(fields) => onChange({
            sensor_definition_spec: {
              ...sensor?.sensor_definition_spec || {},
              fields
            }
          })}
          onAdd={onAdd}
        />
      </SectionWrapper>
      <SectionWrapper className="mt-8" title="Sensor Parameters">
        <RuleParameters
          parameters={sensor?.sensor_definition_spec?.parameters}
          onChange={(parameters) => onChange({
            sensor_definition_spec: {
              ...sensor?.sensor_definition_spec || {},
              parameters
            }
          })}
        />
      </SectionWrapper>
    </div>
  );
};

export default SensorDefinition;

