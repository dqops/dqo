import React from "react";
import Select from "../../components/Select";
import { SensorActionGroup } from "../../components/Sensors/SensorActionGroup";
import Input from "../../components/Input";
import { RuleModel, RuleModelModeEnum, RuleModelTypeEnum } from "../../api";
import NumberInput from "../../components/NumberInput";
import SectionWrapper from "../../components/Dashboard/SectionWrapper";
import RuleFields from "./RuleFields";
import RuleParameters from "./RuleParameters";
import { useActionDispatch } from "../../hooks/useActionDispatch";
import { setUpdatedRule } from "../../redux/actions/sensor.actions";

type RuleDefinitionProps = {
  rule: RuleModel;
}

const typeOptions = Object.values(RuleModelTypeEnum).map((item) => ({ label: item, value: item }));
const modeOptions = Object.values(RuleModelModeEnum).map((item) => ({ label: item, value: item }));

export const RuleDefinition = ({ rule }: RuleDefinitionProps) => {
  const dispatch = useActionDispatch();

  const onChange = (obj: Partial<RuleModel>) => {
    dispatch(setUpdatedRule({
      ...rule,
      ...obj
    }));
  }

  return (
    <div className="p-4">
      <SensorActionGroup />

      {rule && (
        <>
          <div className="mb-8">
            <div className="flex gap-4 text-sm items-center mb-4">
              <p className="w-60">Rule runner type:</p>
              <Select
                value={rule.type}
                onChange={(type) => onChange({ type })}
                options={typeOptions}
              />
            </div>
            <div className="flex gap-4 text-sm items-center mb-4">
              <p className="w-60">Java rule class:</p>
              <div className="grow">
                <Input
                  value={rule.java_class_name}
                  onChange={(e) => onChange({ java_class_name: e.target.value })}
                />
              </div>
            </div>
            <div className="flex gap-4 text-sm items-center mb-4">
              <p className="w-60">Time window mode:</p>
              <Select
                value={rule.mode}
                onChange={(mode) => onChange({ mode })}
                options={modeOptions}
              />
            </div>
            <div className="flex gap-4 text-sm items-center mb-4">
              <p className="w-60">Historic data time periods:</p>
              <div className="grow">
                <NumberInput
                  value={rule.time_window?.prediction_time_window}
                  onChange={(prediction_time_window) => onChange({
                    time_window: {
                      ...rule.time_window,
                      prediction_time_window,
                    }
                  })}
                />
              </div>
            </div>
            <div className="flex gap-4 text-sm items-center mb-4">
              <p className="w-60">Minimum periods with readouts:</p>
              <div className="grow">
                <NumberInput
                  value={rule.time_window?.min_periods_with_readouts}
                  onChange={(min_periods_with_readouts) => onChange({
                    time_window: {
                      ...rule.time_window,
                      min_periods_with_readouts,
                    }
                  })}
                />
              </div>
            </div>
          </div>

          <SectionWrapper title="Rule Fields">
            <RuleFields
              fields={rule.fields || []}
              onChange={(fields) => onChange({ fields })}
            />
          </SectionWrapper>
          <SectionWrapper className="mt-8" title="Rule Parameters">
            <RuleParameters />
          </SectionWrapper>
        </>
      )}
    </div>
  );
};

export default RuleDefinition;

