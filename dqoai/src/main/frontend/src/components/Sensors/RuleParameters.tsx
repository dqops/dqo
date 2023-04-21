import React, { useState } from "react";
import { ParameterDefinitionSpec } from "../../api";
import Input from "../Input";
import { IconButton } from "@material-tailwind/react";
import SvgIcon from "../SvgIcon";

type RuleParametersProps = {
  fields: Array<ParameterDefinitionSpec>;
  onChange: (values: Array<ParameterDefinitionSpec>) => void;
}

const RuleParameters = () => {
  const [name, setName] = useState('');
  const [value, setValue] = useState('');

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
          <tr>
            <td className="pr-4 py-2">
              <Input
                value={name}
                onChange={(e) => setName(e.target.value)}
              />
            </td>
            <td className="px-4 py-2">
              <Input
                value={value}
                onChange={(e) => setValue(e.target.value)}
              />
            </td>
            <td className="px-4 py-2  align-top w-20">
              <IconButton
                color="teal"
                size="sm"
                onClick={() => {}}
                className="!shadow-none"
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