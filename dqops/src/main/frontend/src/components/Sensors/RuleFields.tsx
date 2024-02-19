import React from "react";
import { ParameterDefinitionSpec } from "../../api";
import RuleFieldRow from "./RuleFieldRow";
import RuleFieldAdd from "./RuleFieldAdd";

type RuleFieldsProps = {
  fields: Array<ParameterDefinitionSpec>;
  onChange: (values: Array<ParameterDefinitionSpec>) => void;
  onAdd: (value: ParameterDefinitionSpec) => void;
  isReadOnly?: boolean;
}

const RuleFields = ({ fields, onChange, onAdd, isReadOnly }: RuleFieldsProps) => {
  const handleChange = (idx: number, values: Partial<ParameterDefinitionSpec>) => {
    onChange(fields.map((field, index) => index === idx ? ({
      ...field,
      ...values
    }) : field));
  };

  const handleDelete = (idx: number) => {
    onChange(fields.filter((field, index) => index !== idx));
  };

  return (
    <div>
      <table className="w-full text-sm">
        <thead>
        <tr>
          <th className="pr-4 py-2 text-left">
            Field name
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
            Allowed values
          </th>
          {!isReadOnly && (
            <th className="px-4 py-2 text-left">
              Action
            </th>
          )}
        </tr>
        </thead>
        <tbody>
          {fields.map((field, index) => (
            <RuleFieldRow
              isReadOnly={isReadOnly}
              key={index}
              field={field}
              onChange={(values) => handleChange(index, values)}
              onDelete={() => handleDelete(index)}
            />
          ))}
          {!isReadOnly && (
            <RuleFieldAdd onAdd={onAdd} />
          )}
        </tbody>
      </table>
    </div>
  );
};

export default RuleFields;