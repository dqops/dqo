import React, { useState } from 'react';
import { SharedCredentialListModel } from '../../../../api';
import JdbcPropertyItem from './JdbcPropertyItem';

interface IProperties {
  [key: string]: string;
}

interface IJdbcPropertiesViewProps {
  properties?: IProperties;
  onChange: (properties: IProperties) => void;
  sharedCredentials?: SharedCredentialListModel[];
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

const JdbcPropertiesView = ({
  properties = {[''] : ''},
  onChange,
  sharedCredentials
}: IJdbcPropertiesViewProps) => {
  const [arr, setArr] = useState(convertObjectToArray(properties ?? {}));

  const onChangeArr = (
    array: {
      [key: string]: string;
    }[]
  ) => {
    setArr(array);
    onChange(convertArrayToObject(array));
  };

  // useEffect(() => {
  //   if (properties) {
  //     setArr(convertObjectToArray(properties ?? {}));
  //   }
  // }, [firstLevelActiveTab, storageType]);

  return (
    <div className="py-4">
      <table className="my-3 w-full">
        <thead>
          <tr>
            <th className="text-left min-w-40 pr-4 py-2">
              JDBC connection property
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
