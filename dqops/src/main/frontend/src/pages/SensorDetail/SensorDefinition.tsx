import React from 'react';
import { ParameterDefinitionSpec, SensorModel } from '../../api';
import Checkbox from '../../components/Checkbox';
import { setUpdatedSensor } from '../../redux/actions/definition.actions';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import SectionWrapper from '../../components/Dashboard/SectionWrapper';
import RuleFields from '../../components/Sensors/RuleFields';
import RuleParameters from '../../components/Sensors/RuleParameters';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';

type SensorDefinitionProps = {
  sensor: SensorModel;
};

export const SensorDefinition = ({ sensor }: SensorDefinitionProps) => {
  const dispatch = useActionDispatch();
  const {  userProfile } = useSelector(
    (state: IRootState) => state.job || {}
  );

  const onChange = (obj: Partial<SensorModel>) => {
    dispatch(
      setUpdatedSensor({
        ...(sensor || {}),
        ...obj
      })
    );
  };

  const onAdd = (field: ParameterDefinitionSpec) => {
    dispatch(
      setUpdatedSensor({
        ...(sensor || {}),
        sensor_definition_spec: {
          ...(sensor?.sensor_definition_spec || {}),
          fields: [...(sensor?.sensor_definition_spec?.fields || []), field]
        }
      })
    );
  };

  return (
    <div className="p-4">
      <div className="mb-8">
        <div className="flex gap-4 text-sm items-center mb-4">
          <p className="w-60">Requires event timestamp column:</p>
          <Checkbox
            checked={sensor?.sensor_definition_spec?.requires_event_timestamp}
            onChange={(requires_event_timestamp) =>
              onChange({
                sensor_definition_spec: {
                  ...(sensor?.sensor_definition_spec || {}),
                  requires_event_timestamp
                }
              })
            }
            disabled={userProfile.can_manage_definitions === false}
          />
        </div>
        <div className="flex gap-4 text-sm items-center mb-4">
          <p className="w-60">Requires ingetion timestamp column:</p>
          <Checkbox
            checked={
              sensor?.sensor_definition_spec?.requires_ingestion_timestamp
            }
            onChange={(requires_ingestion_timestamp) =>
              onChange({
                sensor_definition_spec: {
                  ...(sensor?.sensor_definition_spec || {}),
                  requires_ingestion_timestamp
                }
              })
            }
            disabled={userProfile.can_manage_definitions === false}
          />
        </div>
      </div>

      <SectionWrapper title="Sensor Fields">
        <RuleFields
          isReadOnly={sensor?.built_in || userProfile.can_manage_definitions === false}
          fields={sensor?.sensor_definition_spec?.fields || []}
          onChange={(fields) =>
            onChange({
              sensor_definition_spec: {
                ...(sensor?.sensor_definition_spec || {}),
                fields
              }
            })
          }
          onAdd={onAdd}
        />
      </SectionWrapper>
      <SectionWrapper className="mt-8" title="Sensor Parameters">
        <RuleParameters
          parameters={sensor?.sensor_definition_spec?.parameters}
          onChange={(parameters) =>
            onChange({
              sensor_definition_spec: {
                ...(sensor?.sensor_definition_spec || {}),
                parameters
              }
            })
          }
          canUserEdit={userProfile.can_manage_definitions}
        />
      </SectionWrapper>
    </div>
  );
};

export default SensorDefinition;
