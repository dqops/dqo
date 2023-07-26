import React from 'react';
import ResultBox from './ResultBox';

interface dataInterface {
  minBool?: boolean;
  maxBool?: boolean;
  sumBool?: boolean;
  meanBool?: boolean;
  nullCount?: boolean;
  notNullCount?: boolean;
}

export default function ResultPanel({
  minBool,
  maxBool,
  sumBool,
  meanBool,
  nullCount,
  notNullCount
}: dataInterface) {
  return (
    <tr className=" h-50 bg-blue-200">
      <th className="text-left pr-4 py-1.5"></th>
      <th className="text-left px-4 py-1.5"></th>
      <th className="text-center px-4 py-1.5 pr-1 w-1/12">
        {minBool && <ResultBox />}
      </th>
      <th className="text-center px-4 py-1.5 pr-1 w-1/12">
        {maxBool && <ResultBox />}
      </th>
      <th className="text-center px-4 py-1.5 pr-1 w-1/12">
        {sumBool && <ResultBox />}
      </th>
      <th className="text-center px-4 py-1.5 pr-1 w-1/12">
        {meanBool && <ResultBox />}
      </th>
      <th className="text-center px-4 py-1.5 pr-1 w-1/12">
        {nullCount && <ResultBox />}
      </th>
      <th className="text-center px-4 py-1.5 pr-1 w-1/12">
        {notNullCount && <ResultBox />}
      </th>
    </tr>
  );
}
