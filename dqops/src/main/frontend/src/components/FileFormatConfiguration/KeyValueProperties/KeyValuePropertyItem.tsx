import React from 'react';
import Input from '../../Input';
import FieldTypeInput from '../../Connection/ConnectionView/FieldTypeInput';
import { IconButton } from '@material-tailwind/react';
import SvgIcon from '../../SvgIcon';
import { SharedCredentialListModel } from '../../../api';

interface IKeyValuePropertyItemProps {
  idx: number;
  propertyKey: string;
  properties: { [key: string]: string };
  onChange: (properties: { [key: string]: string }) => void;
  sharedCredentials?: SharedCredentialListModel[];
}

const KeyValuePropertyItem = ({
  propertyKey,
  properties,
  idx,
  onChange,
  sharedCredentials
}: IKeyValuePropertyItemProps) => {
  const value = properties[propertyKey];
  // const isLast = idx === Object.keys(properties).length - 1;

  // const onAdd = (key: string, value: string) => {
  //   const elem = { [key]: value };
  //   onChange({ ...properties, ...elem });
  // };

  const onRemove = (key: string) => {
    const copiedProperties = { ...properties };
    delete copiedProperties[key];

    onChange(copiedProperties);
  };

  const onChangeValue = (key: string, value: string) => {
    const copiedProperties = { ...properties };
    copiedProperties[key] = value;
    onChange(copiedProperties);
  };
  return (
    <tr>
      <td className="pr-4 min-w-40 py-2 w-1/2">
        {/* <Input
          value={propertyKey}
          // onChange={(e) => onChange(idx, [e.target.value, value])}
        /> */}
        {propertyKey}
      </td>
      <td className="pr-4 min-w-40 py-2 w-1/2">
        <FieldTypeInput
          value={value}
          onChange={(val) => onChangeValue(propertyKey, val)}
          credential={true}
          data={sharedCredentials}
        />
      </td>
      <td className="px-8 min-w-20 py-2 text-center">
        <IconButton
          className="bg-teal-500"
          size="sm"
          onClick={() => onRemove(propertyKey)}
        >
          <SvgIcon name="delete" className="w-4" />
        </IconButton>
      </td>
    </tr>
  );
};

export default KeyValuePropertyItem;
