import React, { useEffect, useState } from 'react';
import DataDomainItem from './DataDomainItem';

interface IDataDomainRoles {
  [key: string]: string;
}

interface IDataDomainsProps {
  dataDomainRoles?: IDataDomainRoles;
  onChange: (dataDomainRoles: IDataDomainRoles) => void;
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

const DataDomains = ({ dataDomainRoles = {}, onChange }: IDataDomainsProps) => {
  const [arr, setArr] = useState(convertObjectToArray(dataDomainRoles ?? {}));
  useEffect(() => {
    setArr(convertObjectToArray(dataDomainRoles ?? {}));
  }, [dataDomainRoles]);
  const onChangeArr = (array: { [key: string]: string }[]) => {
    setArr(array);
    onChange(convertArrayToObject(array));
  };

  return (
    <div className="p-4 text-sm border-t border-gray-100">
      <table className="w-full">
        <thead>
          <tr>
            <th className="text-left min-w-40 pr-4 py-2">Data domain</th>
            <th className="text-left min-w-40 pr-4 py-2">Domain role</th>
            {/* <th className="px-8 min-w-40 py-2">Action</th> */}
          </tr>
        </thead>
        <tbody>
          {arr.map((_, index) => (
            <DataDomainItem
              key={index}
              index={index}
              dataDomains={arr}
              onChange={onChangeArr}
            />
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default DataDomains;
