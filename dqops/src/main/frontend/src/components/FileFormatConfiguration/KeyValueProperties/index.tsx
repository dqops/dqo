import React from 'react';
import KeyValuePropertyItem from './KeyValuePropertyItem';
import {
  convertArrayToObject,
  convertObjectToArray
} from '../../../utils/object';
import { SharedCredentialListModel } from '../../../api';

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
  const entries: [string, string][] = properties
    ? convertObjectToArray(properties)
    : [['', '']];

  const onRemove = (key: number) => {
    onChange(
      convertArrayToObject(entries.filter((item, index) => index !== key))
    );
  };

  const onChangeDict = (key: number, val: [string, string]) => {
    onChange(
      convertArrayToObject(
        entries.map((item, index) => (index === key ? val : item))
      )
    );
  };

  const onAdd = () => {
    const newEntry: [string, string] = ['', ''];

    onChange({
      ...convertArrayToObject([...entries, newEntry]),
      ...{ ['']: '' }
    });
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
          {entries.map(([key, value], index) => (
            <KeyValuePropertyItem
              key={index}
              idx={index}
              name={key}
              value={value}
              isLast={index === entries.length - 1}
              onRemove={onRemove}
              onChange={onChangeDict}
              onAdd={onAdd}
              sharedCredentials={sharedCredentials}
            />
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default KeyValueProperties;
