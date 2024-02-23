import { IconButton } from '@material-tailwind/react';
import React, { useEffect, useState } from 'react';
import SvgIcon from '../../SvgIcon';
import { SharedCredentialListModel } from '../../../api';
import Input from '../../Input';
import FieldTypeTextarea from '../../Connection/ConnectionView/FieldTypeTextarea';
import FieldTypeInput from '../../Connection/ConnectionView/FieldTypeInput';

interface IKeyValueProperties {
  properties: { [key: string]: string }[];
  onChange: (properties: { [key: string]: string }[]) => void;
  sharedCredentials?: SharedCredentialListModel[];
}

export default function KeyValuePropertyAddItem({
  properties,
  onChange,
  sharedCredentials
}: IKeyValueProperties) {
  const index = properties?.length ?? 0;

  const value = Object.values(properties[index] ?? {})[0] ?? '';
  const key = Object.keys(properties[index] ?? {})[0] ?? '';

  const onAdd = () => {
    onChange([...(properties ?? []), { [key]: value }]);
  };

  const isKeyRed = key.length === 0 && value.length > 0;
  const isValueRed = key.length > 0 && value.length === 0;
  const areBothEmpty = key.length === 0 && value.length === 0;

  // useEffect(() => {
  //   if (isKeyRed || isValueRed) {
  //     onAdd();
  //   }
  // }, [isKeyRed, isValueRed]);

  const onChangeKey = (newKey: string) => {
    const updatedProperties = [...properties];
    updatedProperties[index] = { [newKey]: value };
    onChange(updatedProperties);
  };

  const onChangeValue = (newValue: string) => {
    const updatedProperties = [...properties];

    updatedProperties[index] = { key: newValue };
    onChange(updatedProperties);
  };
  console.log(properties);
  return (
    <tr>
      <td className="pr-4 min-w-40 py-2 w-1/4">
        <Input
          value={key}
          onChange={(e) => onChangeKey(e.target.value)}
          className={isKeyRed ? 'border border-red-500' : ''}
        />
      </td>
      <td className="pr-4 min-w-40 py-2 w-3/4">
        <FieldTypeInput
          value={value}
          onChange={(val) => onChangeValue(val)}
          credential={true}
          data={sharedCredentials}
          inputClassName={isValueRed ? 'border border-red-500' : ''}
        />
      </td>
      <td className="px-8 min-w-20 py-2 text-center">
        <IconButton
          className="bg-teal-500"
          size="sm"
          onClick={onAdd}
          disabled={isKeyRed || isValueRed || areBothEmpty}
        >
          <SvgIcon name="add" className="w-4" />
        </IconButton>
      </td>
    </tr>
  );
}
