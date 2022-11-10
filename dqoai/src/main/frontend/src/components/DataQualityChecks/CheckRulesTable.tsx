import React from 'react';
import { UIFieldModel, UIRuleThresholdsModel } from '../../api';
import FieldControl from './FieldControl';

interface ICheckRulesTableProps {
  rules: UIRuleThresholdsModel[];
  onChange: (rules: UIRuleThresholdsModel[]) => void;
  disabled: boolean;
}

const CheckRulesTable = ({ rules, onChange, disabled }: ICheckRulesTableProps) => {
  const handleChange = (
    key: 'medium' | 'high' | 'low',
    field: UIFieldModel,
    idx: number
  ) => {
    if (key === 'medium') {
      const newParameters = rules[0]?.medium?.rule_parameters?.map(
        (item, index) => (index === idx ? field : item)
      );
      onChange([
        {
          ...rules[0],
          medium: {
            ...rules[0].medium,
            rule_parameters: newParameters
          }
        }
      ]);
    }
    if (key === 'high') {
      const newParameters = rules[0]?.high?.rule_parameters?.map(
        (item, index) => (index === idx ? field : item)
      );
      onChange([
        {
          ...rules[0],
          high: {
            ...rules[0].high,
            rule_parameters: newParameters
          }
        }
      ]);
    }
    if (key === 'low') {
      const newParameters = rules[0]?.low?.rule_parameters?.map((item, index) =>
        index === idx ? field : item
      );
      onChange([
        {
          ...rules[0],
          low: {
            ...rules[0].low,
            rule_parameters: newParameters
          }
        }
      ]);
    }
  };

  return (
    <table className="w-full text-sm text-gray-700">
      <thead>
        <tr>
          <th className="text-left text-gray-700 py-2">
            <button className="bg-orange-400 px-4 py-2 whitespace-nowrap rounded-full text-white">
              Add Alert
            </button>
          </th>
          <th className="text-left text-gray-700 py-2 px-4">
            <button className="bg-red-400 px-4 py-2 whitespace-nowrap rounded-full text-white">
              Add Fatal
            </button>
          </th>
          <th className="text-left text-gray-700 py-2">
            <button className="bg-yellow-400 px-4 py-2 whitespace-nowrap rounded-full text-white">
              Add Warning
            </button>
          </th>
        </tr>
      </thead>
      <tbody>
        {rules.map((rule, ruleIndex) => (
          <tr key={ruleIndex}>
            <td className="text-left text-gray-700 py-2">
              <div className="flex space-x-2">
                {rule.medium?.rule_parameters?.map((item, index) => (
                  <div key={index}>
                    <FieldControl
                      field={item}
                      onChange={(field: UIFieldModel) =>
                        handleChange('medium', field, index)
                      }
                      disabled={disabled}
                    />
                  </div>
                ))}
              </div>
            </td>
            <td className="text-left text-gray-700 py-2 px-4">
              <div className="flex space-x-2">
                {rule.high?.rule_parameters?.map((item, index) => (
                  <div key={index}>
                    <FieldControl
                      field={item}
                      onChange={(field: UIFieldModel) =>
                        handleChange('high', field, index)
                      }
                      disabled={disabled}
                    />
                  </div>
                ))}
              </div>
            </td>
            <td className="text-left text-gray-700 py-2">
              <div className="flex space-x-2">
                {rule.low?.rule_parameters?.map((item, index) => (
                  <div key={index}>
                    <FieldControl
                      field={item}
                      onChange={(field: UIFieldModel) =>
                        handleChange('low', field, index)
                      }
                      disabled={disabled}
                    />
                  </div>
                ))}
              </div>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default CheckRulesTable;
