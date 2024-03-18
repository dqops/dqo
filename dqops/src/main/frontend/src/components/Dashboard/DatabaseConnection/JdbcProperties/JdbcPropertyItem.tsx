import { IconButton } from '@material-tailwind/react';
import React from 'react';
import { SharedCredentialListModel } from '../../../../api';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';
import Input from '../../../Input';
import SvgIcon from '../../../SvgIcon';

interface IJdbcPropertyItemProps {
  properties: { [key: string]: string }[];
  onChange: (properties: { [key: string]: string }[]) => void;
  sharedCredentials?: SharedCredentialListModel[];
  index: number;
}

const JdbcPropertyItem = ({
  properties,
  onChange,
  sharedCredentials,
  index
}: IJdbcPropertyItemProps) => {
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
      <td className="pr-4 min-w-40 py-2 w-1/2">
        <Input
          value={key}
          onChange={(e) => onChangeKey(e.target.value, value)}
          className={isKeyRed ? 'border border-red-500' : ''}
        />
      </td>
      <td className="pr-4 min-w-40 py-2 w-1/2">
        <FieldTypeInput
          value={value}
          onChange={(val) => onChangeValue(key, val)}
          data={sharedCredentials}
          credential={true}
          inputClassName={isValueRed ? 'border border-red-500' : ''}
        />
      </td>
      <td className="px-8 min-w-20 py-2 text-center">
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

export default JdbcPropertyItem;
