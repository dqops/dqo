import React from 'react';
import {
  CheckModel,
  FieldModel,
  RuleParametersModel,
  RuleThresholdsModel
} from '../../../api';
import CheckRuleItem from '../../../components/DataQualityChecks/CheckRuleItem';
import SensorParameters from '../../../components/DataQualityChecks/SensorParameters';

type TUpdateCheckRuleSensor = {
  updatedCheck: CheckModel;
  handleChange: (obj: Partial<CheckModel>) => void;
};
export default function UpdateCheckRuleSensor({
  updatedCheck,
  handleChange
}: TUpdateCheckRuleSensor) {
  const getCheckModelWithoutHelpText = (
    severity: keyof RuleThresholdsModel
  ): RuleParametersModel | undefined => {
    const updatedCheckCopy = { ...updatedCheck };

    updatedCheckCopy?.rule?.[severity]?.rule_parameters?.forEach((x) => {
      if (x.definition) {
        x.definition.help_text = undefined;
      }
    });

    return updatedCheckCopy.rule?.[severity];
  };

  return (
    <div className="flex p-4">
      {updatedCheck?.sensor_parameters &&
      updatedCheck.sensor_parameters.length > 0 ? (
        <div className="text-center whitespace-nowrap text-gray-700  bg-gray-400 px-4">
          <div className="py-2 font-semibold">Sensor parameters</div>
          <div className="flex justify-end items-center">
            <div className=" mt-10">
              <SensorParameters
                parameters={
                  updatedCheck?.sensor_parameters?.map((x) => ({
                    ...x,
                    definition: { ...x.definition, help_text: undefined }
                  })) || []
                }
                onChange={(parameters: FieldModel[]) =>
                  handleChange({ sensor_parameters: parameters })
                }
                onUpdate={() => undefined}
              />
            </div>
          </div>
        </div>
      ) : (
        <div></div>
      )}
      <div className="text-center whitespace-nowrap text-gray-700 bg-gray-400 relative w-70">
        <div className="py-2  font-semibold">Passing rule (KPI met)</div>
        <div className="relative bg-yellow-100 w-70 pb-2">
          <div className="w-5 bg-white absolute h-full right-0 top-0"></div>
          <div className="text-center whitespace-nowrap border-b border-gray-300 text-gray-700 py-2 font-semibold">
            Warning threshold
          </div>
          <CheckRuleItem
            parameters={getCheckModelWithoutHelpText('warning')}
            onChange={(warning) =>
              handleChange({
                rule: {
                  ...updatedCheck?.rule,
                  warning: warning
                }
              })
            }
            type="warning"
            onUpdate={() => {}}
          />
        </div>
        <div className="w-5 bg-white absolute h-full right-0 top-0"></div>
      </div>
      <div className="text-center whitespace-nowrap text-gray-700  bg-gray-400 w-140">
        <div className="py-2 font-semibold">Failing rule (KPI not met)</div>
        <div className="flex">
          <div className="bg-orange-100 w-70 pb-2">
            <div className="text-center whitespace-nowrap border-b border-gray-300 text-gray-700 py-2 font-semibold">
              Error threshold
            </div>
            <CheckRuleItem
              parameters={getCheckModelWithoutHelpText('error')}
              onChange={(error) =>
                handleChange({
                  rule: {
                    ...updatedCheck?.rule,
                    error: error
                  }
                })
              }
              type="error"
              onUpdate={() => {}}
            />
          </div>
          <div className="bg-red-100 w-70 pb-2">
            <div className="text-center whitespace-nowrap border-b border-gray-300 text-gray-700 px-4 py-2 font-semibold">
              Fatal threshold
            </div>
            <CheckRuleItem
              parameters={getCheckModelWithoutHelpText('fatal')}
              onChange={(fatal) =>
                handleChange({
                  rule: {
                    ...updatedCheck?.rule,
                    fatal
                  }
                })
              }
              type="fatal"
              onUpdate={() => {}}
            />
          </div>
        </div>
      </div>
    </div>
  );
}
