import { Tooltip } from '@material-tailwind/react';
import React, { useEffect, useState } from 'react';
import { SharedCredentialListModel } from '../../../api';
import SvgIcon from '../../SvgIcon';
import KeyValuePropertyItem from './KeyValuePropertyItem';

interface IKeyValueProperties {
  properties?: { [key: string]: string };
  onChange: (properties: { [key: string]: string }) => void;
  sharedCredentials?: SharedCredentialListModel[];
  refetchDirectoriesIndicator?: any;
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
  refetchDirectoriesIndicator
}: IKeyValueProperties) => {
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
  }, [refetchDirectoriesIndicator]);

  return (
    <table className="my-3 w-full">
      <thead>
        <tr>
          <th className="text-left min-w-40 pr-4">
            <div className="flex gap-2 items-center">
              Virtual schema name
              <Tooltip
                className="max-w-80 py-4 px-4 bg-gray-800 m-4"
                content="A name that will point to the prefix"
                placement="top-start"
              >
                <div>
                  <SvgIcon
                    name="info"
                    className="w-5 h-5 text-gray-700 cursor-pointer"
                  />
                </div>
              </Tooltip>
            </div>
          </th>
          <th className="text-left min-w-40 pr-4">
            <div className="flex gap-2 items-center">
              Path
              <Tooltip
                className="max-w-80 py-4 px-4 bg-gray-800 m-4"
                content="The absolute path to a folder containing either files or another folder with files. E.g.: /usr/share/data/ or s3://bucket_name/data/"
                placement="top-start"
              >
                <div>
                  <SvgIcon
                    name="info"
                    className="w-5 h-5 text-gray-700 cursor-pointer"
                  />
                </div>
              </Tooltip>
            </div>
          </th>
          <th className="px-8 min-w-40">Action</th>
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
  );
};

export default KeyValueProperties;
