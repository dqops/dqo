import { IconButton } from '@material-tailwind/react';
import React, { useState } from 'react';
import SvgIcon from '../../SvgIcon';
import { SharedCredentialListModel } from '../../../api';
import Input from '../../Input';
import FieldTypeTextarea from '../../Connection/ConnectionView/FieldTypeTextarea';
import FieldTypeInput from '../../Connection/ConnectionView/FieldTypeInput';

interface IKeyValueProperties {
  properties?: { [key: string]: string }[];
  onChange: (properties: { [key: string]: string }[]) => void;
  sharedCredentials?: SharedCredentialListModel[];
}

export default function KeyValuePropertyAddItem({
  properties,
  onChange,
  sharedCredentials
}: IKeyValueProperties) {
  const [key, setKey] = useState('');
  const [value, setValue] = useState('');

  const onAdd = () => {
    onChange([...(properties ?? []), { [key]: value }]);
    setKey('');
    setValue('');
  };

  const isKeyRed = key.length === 0 && value.length > 0;
  const isValueRed = key.length > 0 && value.length === 0;

  return (
    <tr>
      <td className="pr-4 min-w-40 py-2 w-1/4">
        <Input
          value={key}
          onChange={(e) => setKey(e.target.value)}
          className={isKeyRed ? 'border border-red-500' : ''}
        />
      </td>
      <td className="pr-4 min-w-40 py-2 w-3/4">
        <FieldTypeInput
          value={value}
          onChange={(val) => setValue(val)}
          credential={true}
          data={sharedCredentials}
          inputClassName={isValueRed ? 'border border-red-500' : ''}
        />
      </td>
      <td className="px-8 min-w-20 py-2 text-center">
        <IconButton className="bg-teal-500" size="sm" onClick={onAdd}>
          <SvgIcon name="add" className="w-4" />
        </IconButton>
      </td>
    </tr>
  );
}
