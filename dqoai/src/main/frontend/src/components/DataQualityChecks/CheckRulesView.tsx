import React from 'react';
import { UIFieldModel, UIRuleThresholdsModel } from '../../api';
import FieldControl from './FieldControl';

interface ICheckRulesTableProps {
  rule?: UIRuleThresholdsModel;
  onChange: (rule: UIRuleThresholdsModel) => void;
  disabled: boolean;
}

const CheckRulesView = ({
  rule,
  onChange,
  disabled
}: ICheckRulesTableProps) => {
  const handleChange = (
    key: 'error' | 'fatal' | 'warning',
    field: UIFieldModel,
    idx: number
  ) => {
    if (key === 'error') {
      const newParameters = rule?.error?.rule_parameters?.map((item, index) =>
        index === idx ? field : item
      );
      onChange({
        ...rule,
        error: {
          ...rule?.error,
          rule_parameters: newParameters
        }
      });
    }
    if (key === 'fatal') {
      const newParameters = rule?.fatal?.rule_parameters?.map((item, index) =>
        index === idx ? field : item
      );
      onChange({
        ...rule,
        fatal: {
          ...rule?.fatal,
          rule_parameters: newParameters
        }
      });
    }
    if (key === 'warning') {
      const newParameters = rule?.warning?.rule_parameters?.map((item, index) =>
        index === idx ? field : item
      );
      onChange({
        ...rule,
        warning: {
          ...rule?.warning,
          rule_parameters: newParameters
        }
      });
    }
  };

  return (
    <table className="w-full text-sm text-gray-700">
      <tbody>
        <tr>
          <td className="text-left text-gray-700 py-2">
            <div className="flex space-x-2 items-end">
              <button
                className="bg-orange-400 px-4 py-2 whitespace-nowrap rounded-full text-white"
                onClick={() =>
                  onChange({
                    ...rule,
                    error: {
                      ...rule?.error,
                      configured: true
                    }
                  })
                }
              >
                Add Alert
              </button>
              {rule?.error?.rule_parameters?.map((item, index) => (
                <div key={index}>
                  <FieldControl
                    field={item}
                    onChange={(field: UIFieldModel) =>
                      handleChange('error', field, index)
                    }
                    disabled={disabled}
                  />
                </div>
              ))}
            </div>
          </td>
          <td className="text-left text-gray-700 py-2 px-4">
            <div className="flex space-x-2 items-end">
              <button
                className="bg-red-400 px-4 py-2 whitespace-nowrap rounded-full text-white"
                onClick={() =>
                  onChange({
                    ...rule,
                    fatal: {
                      ...rule?.fatal,
                      configured: true
                    }
                  })
                }
              >
                Add Fatal
              </button>
              {rule?.fatal?.rule_parameters?.map((item, index) => (
                <div key={index}>
                  <FieldControl
                    field={item}
                    onChange={(field: UIFieldModel) =>
                      handleChange('fatal', field, index)
                    }
                    disabled={disabled}
                  />
                </div>
              ))}
            </div>
          </td>
          <td className="text-left text-gray-700 py-2">
            <div className="flex space-x-2 items-end">
              <button
                className="bg-yellow-400 px-4 py-2 whitespace-nowrap rounded-full text-white"
                onClick={() =>
                  onChange({
                    ...rule,
                    warning: {
                      ...rule?.warning,
                      configured: true
                    }
                  })
                }
              >
                Add Warning
              </button>
              {rule?.warning?.rule_parameters?.map((item, index) => (
                <div key={index}>
                  <FieldControl
                    field={item}
                    onChange={(field: UIFieldModel) =>
                      handleChange('warning', field, index)
                    }
                    disabled={disabled}
                  />
                </div>
              ))}
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  );
};

export default CheckRulesView;
