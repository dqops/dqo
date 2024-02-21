import React, { useState } from 'react';
import KeyValuePropertyItem from './KeyValuePropertyItem';
import { SharedCredentialListModel } from '../../../api';
import { IconButton } from '@material-tailwind/react';
import FieldTypeInput from '../../Connection/ConnectionView/FieldTypeInput';
import SvgIcon from '../../SvgIcon';
import Input from '../../Input';

interface IKeyValueProperties {
  properties?: { [key: string]: string };
  onChange: (properties: { [key: string]: string }) => void;
  sharedCredentials?: SharedCredentialListModel[];
}

const KeyValueProperties = ({
  properties,
  onChange,
  sharedCredentials
}: IKeyValueProperties) => {
  const [key, setKey] = useState('');
  const [value, setValue] = useState('');
  const onAdd = () => {
    const elem = { [key]: value };
    setKey('');
    setValue('');
    onChange({ ...properties, ...elem });
  };
  return (
    <div className="py-4">
      <table className="my-3 w-full">
        <thead>
          <tr>
            <th className="text-left min-w-40 pr-4 py-2">
              Virtual schema name
            </th>
            <th className="text-left min-w-40 pr-4 py-2">Path</th>
            <th className="px-8 min-w-40 py-2">Action</th>
          </tr>
        </thead>
        <tbody>
          {Object.keys(properties ?? {}).map((propertyKey, index) => (
            <KeyValuePropertyItem
              key={index}
              idx={index}
              propertyKey={propertyKey}
              properties={properties ?? {}}
              onChange={onChange}
              sharedCredentials={sharedCredentials}
            />
          ))}
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
        </tbody>
      </table>
    </div>
  );
};

export default KeyValueProperties;
