import { IconButton } from '@material-tailwind/react';
import React, { useState } from 'react';
import FieldTypeInput from '../../Connection/ConnectionView/FieldTypeInput';
import SvgIcon from '../../SvgIcon';
import { SharedCredentialListModel } from '../../../api';
import Input from '../../Input';

interface IKeyValueProperties {
  properties?: { [key: string]: string };
  onChange: (properties: { [key: string]: string }) => void;
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
    setKey('');
    setValue('');
    onChange({ ...properties, [key]: value });
  };

  return (
    <tr>
      <td className="pr-4 min-w-40 py-2 w-1/2">
        <Input value={key} onChange={(e) => setKey(e.target.value)} />
      </td>
      <td className="pr-4 min-w-40 py-2 w-1/2">
        <FieldTypeInput
          value={value}
          onChange={(val) => setValue(val)}
          credential={true}
          data={sharedCredentials}
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
