import React from 'react';
import Select from '../../components/Select';
import Input from '../../components/Input';
import {
  ParameterDefinitionSpec,
  RuleModel,
  RuleModelModeEnum,
  RuleModelTypeEnum
} from '../../api';
import NumberInput from '../../components/NumberInput';
import SectionWrapper from '../../components/Dashboard/SectionWrapper';
import RuleFields from '../../components/Sensors/RuleFields';
import RuleParameters from '../../components/Sensors/RuleParameters';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setUpdatedRule } from '../../redux/actions/definition.actions';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';

type RuleDefinitionProps = {
  rule: RuleModel;
};

const typeOptions = Object.values(RuleModelTypeEnum).map((item) => ({
  label: item,
  value: item
}));
const modeOptions = Object.values(RuleModelModeEnum).map((item) => ({
  label: item,
  value: item
}));

export const RuleDefinition = ({ rule }: RuleDefinitionProps) => {
  const { userProfile } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const dispatch = useActionDispatch();

  const onChange = (obj: Partial<RuleModel>) => {
    dispatch(
      setUpdatedRule({
        ...rule,
        ...obj
      })
    );
  };

  const onAdd = (field: ParameterDefinitionSpec) => {
    dispatch(
      setUpdatedRule({
        ...rule,
        fields: [...(rule.fields || []), field]
      })
    );
  };

  return (
    <div className="p-4">
      {rule && (
        <>
          <div className="mb-8">
            <div className="flex gap-4 text-sm items-center mb-4">
              <p className="w-60">Rule runner type:</p>
              <Select
                value={rule.type}
                onChange={(type) => onChange({ type })}
                options={typeOptions}
                disabled={userProfile.can_manage_definitions === false}
              />
            </div>
            <div className="flex gap-4 text-sm items-center mb-4">
              <p className="w-60">Java rule class:</p>
              <div className="grow">
                <Input
                  value={rule.java_class_name}
                  onChange={(e) =>
                    onChange({ java_class_name: e.target.value })
                  }
                  disabled={userProfile.can_manage_definitions === false}
                />
              </div>
            </div>
            <div className="flex gap-4 text-sm items-center mb-4">
              <p className="w-60">Time window mode:</p>
              <Select
                value={rule.mode}
                onChange={(mode) => onChange({ mode })}
                options={modeOptions}
                disabled={userProfile.can_manage_definitions === false}
              />
            </div>
            <div className="flex gap-4 text-sm items-center mb-4">
              <p className="w-60">Historic data time periods:</p>
              <div className="grow">
                <NumberInput
                  value={rule.time_window?.prediction_time_window}
                  onChange={(prediction_time_window) =>
                    onChange({
                      time_window: {
                        ...rule.time_window,
                        prediction_time_window
                      }
                    })
                  }
                  disabled={userProfile.can_manage_definitions === false}
                  step={1}
                />
              </div>
            </div>
            <div className="flex gap-4 text-sm items-center mb-4">
              <p className="w-60">Minimum periods with readouts:</p>
              <div className="grow">
                <NumberInput
                  value={rule.time_window?.min_periods_with_readouts}
                  onChange={(min_periods_with_readouts) =>
                    onChange({
                      time_window: {
                        ...rule.time_window,
                        min_periods_with_readouts
                      }
                    })
                  }
                  disabled={userProfile.can_manage_definitions === false}
                  step={1}
                />
              </div>
            </div>
          </div>

          <SectionWrapper title="Rule Fields">
            <RuleFields
              fields={rule.fields || []}
              onChange={(fields) => onChange({ fields })}
              onAdd={onAdd}
              isReadOnly={rule?.built_in || userProfile.can_manage_definitions === false}
            />
          </SectionWrapper>
          <SectionWrapper className="mt-8" title="Rule Parameters">
            <RuleParameters
              parameters={rule.parameters}
              onChange={(parameters) => onChange({ parameters })}
              canUserEdit = {userProfile.can_manage_definitions}
            />
          </SectionWrapper>
        </>
      )}
    </div>
  );
};

export default RuleDefinition;
