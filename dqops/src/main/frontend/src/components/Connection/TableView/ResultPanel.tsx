import React from 'react';
import ResultBox from './ResultBox';
import { ComparisonCheckResultModel } from '../../../api';

interface dataInterface {
  minBool?: boolean;
  maxBool?: boolean;
  sumBool?: boolean;
  meanBool?: boolean;
  nullCount?: boolean;
  notNullCount?: boolean;
  obj: { [key: string]: ComparisonCheckResultModel };
}

export default function ResultPanel({
  minBool,
  maxBool,
  sumBool,
  meanBool,
  nullCount,
  notNullCount,
  obj
}: dataInterface) {
  const prepareObj = (key: string): ComparisonCheckResultModel => {
    if (Object.keys(obj).find((x) => x === key)) {
      return obj[key];
    } else {
      return {};
    }
  };

  return (
    <tr className=" h-50 bg-blue-200 w-full">
      <th className="text-left pr-4 py-1.5 bg-red-500 "></th>
      <th className="text-left px-4 py-1.5 bg-green-500 "></th>
      <th className="text-center px-4 py-1.5 pr-1 w-1/12">
        {minBool && <ResultBox item={prepareObj('min_match')} />}
      </th>
      <th className=" px-4 py-1.5 pr-1 w-1/12">
        {maxBool && <ResultBox item={prepareObj('max_match')} />}
      </th>
      <th className=" px-4 py-1.5 pr-1 w-1/12 flex justify-start">
        {sumBool && <ResultBox item={prepareObj('sum_match')} />}
      </th>
      <th className="px-4 py-1.5 pr-1 w-1/12 ">
        {meanBool && <ResultBox item={prepareObj('mean_match')} />}
      </th>
      <th className="text-center px-4 py-1.5 pr-1 w-1/12">
        {nullCount && <ResultBox item={prepareObj('null_count_match')} />}
      </th>
      <th className="text-center px-4 py-1.5 pr-1 w-1/12">
        {notNullCount && (
          <ResultBox item={prepareObj('not_null_count_match')} />
        )}
      </th>
    </tr>
  );
}
