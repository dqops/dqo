import React from 'react';
import SnowflakePropertyItem from './SnowflakePropertyItem';
import { convertArrayToObject, convertObjectToArray } from "../../../../utils/object";

interface IProperties {
  [key: string]: string;
}

interface ISnowflakePropertiesViewProps {
  properties?: IProperties;
  onChange: (properties: IProperties) => void;
}

const SnowflakePropertiesView = ({ properties, onChange }: ISnowflakePropertiesViewProps) => {
  const entries: [string, string][] = convertObjectToArray(properties).concat([['', '']]);

  const onRemove = (key: number) => {
    onChange(convertArrayToObject(entries.filter((item, index) => index !== key)));
  };

  const onChangeProperty = (key: number, val: [string, string]) => {
    const obj = convertArrayToObject(entries.map((item, index) => index === key ? val : item));
    onChange(convertArrayToObject(entries.map((item, index) => index === key ? val : item)));
  };

  return (
    <div className="p-4">
      <table className="my-3 w-full">
        <thead>
          <th className="text-left min-w-40 w-full pr-4 py-2">JDBC connection property</th>
          <th className="text-left min-w-40 w-full pr-4 py-2">Value</th>
          <th className="px-8 min-w-40 py-2">Action</th>
        </thead>
        <tbody>
          {entries.map(([key, value], index) => (
            <SnowflakePropertyItem
              key={index}
              idx={index}
              name={key}
              value={value}
              isLast={index === entries.length - 1}
              onRemove={onRemove}
              onChange={onChangeProperty}
            />
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default SnowflakePropertiesView;
