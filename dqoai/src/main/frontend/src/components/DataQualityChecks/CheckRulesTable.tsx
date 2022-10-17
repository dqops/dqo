import React from 'react';
import SvgIcon from '../SvgIcon';
import { UIRuleThresholdsModel } from '../../api';

interface ICheckRulesTableProps {
  rules: UIRuleThresholdsModel[];
  openCheckRule: (rule: UIRuleThresholdsModel) => void;
}

const CheckRulesTable = ({ rules, openCheckRule }: ICheckRulesTableProps) => {
  return (
    <table className="w-full text-sm text-gray-700">
      <thead>
        <tr>
          <th className="text-left text-gray-700 py-2 border-b">Field Name</th>
          <th className="text-left text-gray-700 py-2 border-b">Medium</th>
          <th className="text-left text-gray-700 py-2 border-b">High</th>
          <th className="text-left text-gray-700 py-2 border-b">Low</th>
        </tr>
      </thead>
      <tbody>
        {rules.map((rule, ruleIndex) => (
          <tr key={ruleIndex}>
            <td className="text-left text-gray-700 py-2 border-b">
              <div className="flex space-x-2 items-center">
                <div className="text-gray-700 text-sm leading-1.5">
                  {rule.field_name}
                </div>
                <SvgIcon
                  name="cog"
                  className="w-4 h-4 text-blue-700 cursor-pointer"
                  onClick={() => openCheckRule(rule)}
                />
              </div>
            </td>
            <td className="text-left text-gray-700 py-2 border-b w-20">
              {rule.medium?.rule_parameters
                ? rule.medium.rule_parameters[0]?.double_value
                : 0}
            </td>
            <td className="text-left text-gray-700 py-2 border-b w-20">
              {rule?.high?.rule_parameters
                ? rule.high.rule_parameters[0]?.double_value
                : 0}
            </td>
            <td className="text-left text-gray-700 py-2 border-b w-20">
              {rule?.low?.rule_parameters
                ? rule.low.rule_parameters[0]?.double_value
                : 0}
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default CheckRulesTable;
