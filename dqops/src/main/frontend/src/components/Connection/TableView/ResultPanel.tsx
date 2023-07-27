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
    <tr className=" h-50 w-full">
      <th className="text-left pr-4 py-1.5  w-1/6 "></th>
      <th className="text-left px-4 py-1.5 w-1/6 "></th>
      <th className=" px-4 py-1.5 pr-1  relative">
        {minBool && <ResultBox item={prepareObj('min_match')} />}
      </th>
      <th className=" px-4 py-1.5 pr-1  relative">
        {maxBool && <ResultBox item={prepareObj('max_match')} />}
      </th>
      <th className=" px-4 py-1.5 pr-1 relative">
        {sumBool && <ResultBox item={prepareObj('sum_match')} />}
      </th>
      <th className="px-4 py-1.5 pr-1 relative">
        {meanBool && <ResultBox item={prepareObj('mean_match')} />}
      </th>
      <th className="text-center px-4 py-1.5 pr-1 relative">
        {nullCount && <ResultBox item={prepareObj('null_count_match')} />}
      </th>
      <th className="text-center px-4 py-1.5 pr-1 relative">
        {notNullCount && (
          <ResultBox item={prepareObj('not_null_count_match')} />
        )}
      </th>
    </tr>
  );
}
