import React from 'react';
import KeyValuePropertyItem from './KeyValuePropertyItem';
import { SharedCredentialListModel } from '../../../api';
import KeyValuePropertyAddItem from './KeyValuePropertyAddItem';

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
              propertyKey={propertyKey}
              properties={properties ?? {}}
              onChange={onChange}
              sharedCredentials={sharedCredentials}
            />
          ))}
          <KeyValuePropertyAddItem
            properties={properties ?? {}}
            onChange={onChange}
            sharedCredentials={sharedCredentials}
          />
        </tbody>
      </table>
    </div>
  );
};

export default KeyValueProperties;
