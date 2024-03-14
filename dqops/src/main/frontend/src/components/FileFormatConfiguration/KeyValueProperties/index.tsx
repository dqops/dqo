import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import { SharedCredentialListModel } from '../../../api';
import { getFirstLevelActiveTab } from '../../../redux/selectors';
import { CheckTypes } from '../../../shared/routes';
import KeyValuePropertyItem from './KeyValuePropertyItem';

interface IKeyValueProperties {
  properties?: { [key: string]: string };
  onChange: (properties: { [key: string]: string }) => void;
  sharedCredentials?: SharedCredentialListModel[];
  storageType?: any;
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

const KeyValueProperties = ({
  properties,
  onChange,
  sharedCredentials,
  storageType
}: IKeyValueProperties) => {
  const { checkTypes }: { checkTypes: CheckTypes } = useParams();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [arr, setArr] = useState(convertObjectToArray(properties ?? {}));

  const onChangeArr = (
    array: {
      [key: string]: string;
    }[]
  ) => {
    setArr(array);
    onChange(convertArrayToObject(array));
  };

  useEffect(() => {
    if (properties) {
      setArr(convertObjectToArray(properties ?? {}));
    }
  }, [firstLevelActiveTab, storageType]);

  return (
    <div>
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
          {arr.map((_, index) => (
            <KeyValuePropertyItem
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

export default KeyValueProperties;
