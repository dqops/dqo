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
  valuePlaceholder?: string;
}

const KeyValuePropertyItem = ({
  properties,
  onChange,
  sharedCredentials,
  index,
  valuePlaceholder
}: IKeyValuePropertyItemProps) => {
  const value = Object.values(properties[index])[0];
  const key = Object.keys(properties[index])[0];

  const onRemove = () => {
    const copiedProperties = [...properties].filter((_, idx) => idx !== index);
    onChange(copiedProperties);
  };

  const onChangeKey = (newKey: string, value: string) => {
    const updatedProperties = [...properties];

    updatedProperties[index] = { [newKey]: value };
    onChange(updatedProperties);
  };

  const onChangeValue = (key: string, newValue: string) => {
    const updatedProperties = [...properties];

    updatedProperties[index] = { [key]: newValue };
    onChange(updatedProperties);
  };
  const onAdd = () => {
    onChange([...(properties ?? []), { ['']: '' }]);
  };

  const isKeyRed = key.length === 0 && value.length > 0;
  const isValueRed = key.length > 0 && value.length === 0;
  const areBothEmpty = key.length === 0 && value.length === 0;

  return (
    <tr>
      <td className="pr-4 min-w-40 py-2 w-1/4">
        <Input
          value={key}
          onChange={(e) => onChangeKey(e.target.value, value)}
          className={isKeyRed ? 'border border-red-500' : ''}
        />
        {/* {propertyKey} */}
      </td>
      <td className="pr-4 min-w-40 py-2 w-3/4">
        <FieldTypeInput
          value={value}
          onChange={(val) => onChangeValue(key, val)}
          data={sharedCredentials}
          placeholder={valuePlaceholder}
          credential={true}
          inputClassName={isValueRed ? 'border border-red-500' : ''}
        />
      </td>
      <td className="px-8 min-w-20 py-2 text-center gap-x-4">
        {!(index === 0 && properties.length === 1) && (
          <IconButton className="bg-teal-500 mx-1" size="sm" onClick={onRemove}>
            <SvgIcon name="delete" className="w-4" />
          </IconButton>
        )}
        {index === properties.length - 1 && (
          <IconButton
            className="bg-teal-500 mx-1"
            size="sm"
            onClick={onAdd}
            disabled={isKeyRed || isValueRed || areBothEmpty}
          >
            <SvgIcon name="add" className="w-4" />
          </IconButton>
        )}
      </td>
    </tr>
  );
};

export default KeyValuePropertyItem;
