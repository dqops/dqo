import React from 'react';
import Input from '../../Input';
import FieldTypeInput from "../../Connection/ConnectionView/FieldTypeInput";
import { IconButton } from "@material-tailwind/react";
import SvgIcon from "../../SvgIcon";
import { SharedCredentialListModel } from '../../../api';

interface IKeyValuePropertyItemProps {
  idx: number;
  name: string;
  value: string;
  onRemove: (key: number) => void;
  isLast?: boolean;
  onChange: (key: number, value: [string, string]) => void;
  sharedCredentials ?: SharedCredentialListModel[];
}

const KeyValuePropertyItem = ({
  name,
  value,
  isLast,
  idx,
  onRemove,
  onChange,
  sharedCredentials
}: IKeyValuePropertyItemProps) => {

  return (
    <tr>
      <td className="pr-4 min-w-40 py-2 w-1/2">
        <Input
          value={name}
          onChange={(e) => onChange(idx, [e.target.value, value])}
        />
      </td>
      <td className="pr-4 min-w-40 py-2 w-1/2">
        <FieldTypeInput
          value={value}
          onChange={(val) => onChange(idx, [name, val])}
          credential={true}
          data={sharedCredentials}
        />
      </td>
      <td className="px-8 min-w-20 py-2 text-center">
        {isLast ? (
          <IconButton className="bg-teal-500" size="sm">
            <SvgIcon name="add" className="w-4" />
          </IconButton>
        ) : (
          <IconButton
            className="bg-teal-500"
            size="sm"
            onClick={() => onRemove(idx)}
          >
            <SvgIcon name="delete" className="w-4" />
          </IconButton>
        )}
      </td>
    </tr>
  );
};

export default KeyValuePropertyItem;
