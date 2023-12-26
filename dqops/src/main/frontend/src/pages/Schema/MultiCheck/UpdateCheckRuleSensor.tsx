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
    <div className=" my-4">
      <div className="flex">
        {updatedCheck?.sensor_parameters &&
        updatedCheck.sensor_parameters.length > 0 ? (
          <div className="text-center whitespace-nowrap text-gray-700 py-1.5 px-4 font-semibold bg-gray-400 w-50">
            Sensor parameters
          </div>
        ) : (
          <div className="w-50 py-1.5 px-12"></div>
        )}
        <div className="text-center whitespace-nowrap text-gray-700 py-1.5 px-4 font-semibold bg-gray-400 relative w-1/3">
          Passing rule
          <div className="w-5 bg-white absolute h-full right-0 top-0"></div>
        </div>
        <div className="text-center whitespace-nowrap text-gray-700 py-1.5 px-4 font-semibold bg-gray-400 pl-10 w-2/3">
          Failing rule
        </div>
      </div>
      <div className="flex">
        <div className="w-45 mr-[33px] flex justify-center items-center mt-11">
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
        <div className="relative bg-yellow-100 py-2 w-1/3">
          <div className="w-5 bg-white absolute h-full right-0 top-0"></div>
          <div className="text-center whitespace-nowrap border-b border-gray-300 text-gray-700 my-2 px-4 font-semibold">
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
        <div className="bg-orange-100  py-2 w-1/3">
          <div className="text-center whitespace-nowrap border-b border-gray-300 text-gray-700 my-2 px-4 font-semibold">
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
        <div className="bg-red-100 py-2 w-1/3">
          <div className="text-center whitespace-nowrap border-b my-2 border-gray-300 text-gray-700  px-4 font-semibold">
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
  );
}
