import React from 'react';
import JdbcPropertyItem from './JdbcPropertyItem';
import { convertArrayToObject, convertObjectToArray } from "../../../../utils/object";
import { SharedCredentialListModel } from '../../../../api';

interface IProperties {
  [key: string]: string;
}

interface IJdbcPropertiesViewProps {
  properties?: IProperties;
  onChange: (properties: IProperties) => void;
  sharedCredentials ?: SharedCredentialListModel[];
}

const JdbcPropertiesView = ({ properties, onChange, sharedCredentials }: IJdbcPropertiesViewProps) => {
  const entries: [string, string][] = convertObjectToArray(properties).concat([['', '']]);

  const onRemove = (key: number) => {
    onChange(convertArrayToObject(entries.filter((item, index) => index !== key)));
  };

  const onChangeProperty = (key: number, val: [string, string]) => {
    onChange(convertArrayToObject(entries.map((item, index) => index === key ? val : item)));
  };

  return (
    <div className="py-4">
      <table className="my-3 w-full">
        <thead>
          <tr>
            <th className="text-left min-w-40 pr-4 py-2">JDBC connection property</th>
            <th className="text-left min-w-40 pr-4 py-2">Value</th>
            <th className="px-8 min-w-40 py-2">Action</th>
          </tr>
        </thead>
        <tbody>
          {entries.map(([key, value], index) => (
            <JdbcPropertyItem
              key={index}
              idx={index}
              name={key}
              value={value}
              isLast={index === entries.length - 1}
              onRemove={onRemove}
              onChange={onChangeProperty}
              sharedCredentials={sharedCredentials}
            />
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default JdbcPropertiesView;
