import React from 'react';
import Input from '../../Input';
import FieldTypeInput from '../../Connection/ConnectionView/FieldTypeInput';
import { IconButton } from '@material-tailwind/react';
import SvgIcon from '../../SvgIcon';
import { SharedCredentialListModel } from '../../../api';
import FieldTypeTextarea from '../../Connection/ConnectionView/FieldTypeTextarea';

interface IKeyValuePropertyItemProps {
  properties: { [key: string]: string }[];
  onChange: (properties: { [key: string]: string }[]) => void;
  sharedCredentials?: SharedCredentialListModel[];
  index: number;
}

const KeyValuePropertyItem = ({
  properties,
  onChange,
  sharedCredentials,
  index
}: IKeyValuePropertyItemProps) => {
  const value = Object.values(properties[index])[0];
  const key = Object.keys(properties[index])[0];

  const onRemove = () => {
    const copiedProperties = [...properties].filter((_, idx) => idx !== index);
    onChange(copiedProperties);
  };

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
  return (
    <tr>
      <td className="pr-4 min-w-40 py-2 w-1/4">
        <Input value={key} onChange={(e) => onChangeKey(e.target.value)} />
        {/* {propertyKey} */}
      </td>
      <td className="pr-4 min-w-40 py-2 w-3/4">
        <FieldTypeInput
          value={value}
          onChange={(val) => onChangeValue(val)}
          data={sharedCredentials}
          credential={true}
        />
      </td>
      <td className="px-8 min-w-20 py-2 text-center">
        <IconButton className="bg-teal-500" size="sm" onClick={onRemove}>
          <SvgIcon name="delete" className="w-4" />
        </IconButton>
      </td>
    </tr>
  );
};

export default KeyValuePropertyItem;
