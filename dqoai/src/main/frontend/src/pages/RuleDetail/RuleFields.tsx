import React from "react";
import { ParameterDefinitionSpec } from "../../api";
import RuleFieldRow from "./RuleFieldRow";
import RuleFieldAdd from "./RuleFieldAdd";

type RuleFieldsProps = {
  fields: Array<ParameterDefinitionSpec>;
  onChange: (values: Array<ParameterDefinitionSpec>) => void;
}

const RuleFields = ({ fields, onChange }: RuleFieldsProps) => {
  const handleChange = (idx: number, values: Partial<ParameterDefinitionSpec>) => {
    onChange(fields.map((field, index) => index === idx ? ({
      ...field,
      ...values
    }) : field));
  };

  return (
    <div>
      <table className="w-full text-sm">
        <thead>
        <tr>
          <th className="pr-4 py-2 text-left">
            Field Name
          </th>
          <th className="px-4 py-2 text-left">
            Display name
          </th>
          <th className="px-4 py-2 text-left">
            Help text
          </th>
          <th className="px-4 py-2 text-left">
            Data type
          </th>
          <th className="px-4 py-2 text-left">
            Display hint
          </th>
          <th className="px-4 py-2 text-left">
            Required
          </th>
          <th className="px-4 py-2 text-left">
            Allowed Values
          </th>
          <th className="px-4 py-2 text-left">
            Action
          </th>
        </tr>
        </thead>
        <tbody>
          {fields.map((field, index) => (
            <RuleFieldRow
              key={index}
              field={field}
              onChange={(values) => handleChange(index, values)}
            />
          ))}
          <RuleFieldAdd onAdd={(field) => {}} />
        </tbody>
      </table>
    </div>
  );
};

export default RuleFields;