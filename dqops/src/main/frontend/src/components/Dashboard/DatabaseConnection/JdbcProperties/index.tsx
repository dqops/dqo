import React, { useEffect, useState } from 'react';
import { SharedCredentialListModel } from '../../../../api';
import JdbcPropertyItem from './JdbcPropertyItem';

interface IProperties {
  [key: string]: string;
}

interface IJdbcPropertiesViewProps {
  properties?: IProperties;
  onChange: (properties: IProperties) => void;
  sharedCredentials?: SharedCredentialListModel[];
  title?: string;
}

function convertObjectToArray(obj: {
  [key: string]: string;
}): { [key: string]: string }[] {
  return Object.entries(obj).map(([key, value]) => ({ [key]: value }));
}

function convertArrayToObject(array: { [key: string]: string }[]): {
  [key: string]: string;
} {
  return array.reduce((result, currentObject) => {
    for (const key in currentObject) {
      if (Object.prototype.hasOwnProperty.call(currentObject, key)) {
        result[key] = currentObject[key];
      }
    }
    return result;
  }, {});
}

function arePropertiesEqual(obj1: IProperties, obj2: IProperties): boolean {
  const keys1 = Object.keys(obj1);
  const keys2 = Object.keys(obj2);

  if (keys1.length !== keys2.length) return false;

  for (const key of keys1) {
    if (obj1[key] !== obj2[key]) return false;
  }

  return true;
}

const JdbcPropertiesView = ({
  properties = { ['']: '' },
  onChange,
  sharedCredentials,
  title
}: IJdbcPropertiesViewProps) => {
  const [arr, setArr] = useState(
    convertObjectToArray(properties ?? { ['']: '' })
  );

  const onChangeArr = (array: { [key: string]: string }[]) => {
    setArr(array);
    onChange(convertArrayToObject(array));
  };

  useEffect(() => {
    if (!arePropertiesEqual(convertArrayToObject(arr), properties)) {
      setArr(convertObjectToArray(properties ?? { ['']: '' }));
    }
    if (Object.keys(properties).length === 0 || properties === undefined) {
      setArr([{ ['']: '' }]);
    }
  }, [properties]);

  return (
    <div className="py-4">
      <table className="my-3 w-full">
        <thead>
          <tr>
            <th className="text-left min-w-40 pr-4 py-2">
              {title ?? 'JDBC connection property'}
            </th>
            <th className="text-left min-w-40 pr-4 py-2">Value</th>
            <th className="px-8 min-w-40 py-2">Action</th>
          </tr>
        </thead>
        <tbody>
          {arr.map((_, index) => (
            <JdbcPropertyItem
              key={index}
              index={index}
              properties={arr}
              onChange={onChangeArr}
              sharedCredentials={sharedCredentials}
            />
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default JdbcPropertiesView;
