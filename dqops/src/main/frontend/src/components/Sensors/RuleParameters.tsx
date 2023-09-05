import React, { useState } from "react";
import Input from "../Input";
import { IconButton } from "@material-tailwind/react";
import SvgIcon from "../SvgIcon";

type RuleParametersProps = {
  parameters?: Record<string, string>;
  onChange: (parameters: Record<string, string>) => void;
  canUserEdit ?: boolean
}

const RuleParameters = ({ parameters, onChange, canUserEdit }: RuleParametersProps) => {
  const [name, setName] = useState('');
  const [value, setValue] = useState('');

  const onAdd = () => {
    if (!name) return;

    onChange({
      ...parameters || {},
      [name]: value
    });
    setName('');
    setValue('');
  };

  const onDelete = (key: string) => {
    if (parameters) {
      const newParameters = structuredClone(parameters);
      delete newParameters[key];

      onChange(newParameters);
    }
  };

  return (
    <div>
      <table className="w-full text-sm">
        <thead>
        <tr>
          <th className="pr-4 py-2 text-left">
            Parameter name
          </th>
          <th className="px-4 py-2 text-left">
            Value
          </th>
          <th className="px-4 py-2 text-left">
            Action
          </th>
        </tr>
        </thead>
        <tbody>
          {parameters ? Object.keys(parameters).map((key, index) => (
            <tr key={index}>
              <td className="pr-4 py-2">
                <Input
                  value={key}
                  onChange={(e) => {}}
                  disabled={canUserEdit !== true}
                />
              </td>
              <td className="px-4 py-2">
                <Input
                  value={parameters[key]}
                  onChange={(e) => {}}
                  disabled={canUserEdit !== true}
                />
              </td>
              <td className="px-4 py-2  align-top w-20">
                <IconButton
                  color="teal"
                  size="sm"
                  onClick={() => onDelete(key)}
                  className="!shadow-none"
                  disabled={canUserEdit !== true}
                >
                  <SvgIcon name="delete" className="w-4" />
                </IconButton>
              </td>
            </tr>
          )) : null}
          <tr>
            <td className="pr-4 py-2">
              <Input
                value={name}
                onChange={(e) => setName(e.target.value)}
                disabled={canUserEdit !== true}
              />
            </td>
            <td className="px-4 py-2">
              <Input
                value={value}
                onChange={(e) => setValue(e.target.value)}
                disabled={canUserEdit !== true}
              />
            </td>
            <td className="px-4 py-2  align-top w-20">
              <IconButton
                color="teal"
                size="sm"
                onClick={onAdd}
                className="!shadow-none"
                disabled={canUserEdit !== true}
              >
                <SvgIcon name="add" className="w-4" />
              </IconButton>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  );
};

export default RuleParameters;