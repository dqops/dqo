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
    <tr className=" h-70 w-full">
      <th className="text-left pr-4 py-1.5  w-1/6 "></th>
      <th className="text-left px-4 py-1.5 w-1/6 "></th>
      <th className=" px-4 py-1.5 pr-1  relative">
        {obj['min_match'] && (
          <ResultBox item={prepareObj('min_match')} bool={minBool} />
        )}
      </th>
      <th className=" px-4 py-1.5 pr-1  relative">
        {obj['max_match'] && (
          <ResultBox item={prepareObj('max_match')} bool={maxBool} />
        )}
      </th>
      <th className=" px-4 py-1.5 pr-1 relative">
        {obj['sum_match'] && (
          <ResultBox item={prepareObj('sum_match')} bool={sumBool} />
        )}
      </th>
      <th className="px-4 py-1.5 pr-1 relative">
        {obj['mean_match'] && (
          <ResultBox item={prepareObj('mean_match')} bool={meanBool} />
        )}
      </th>
      <th className="text-center px-4 py-1.5 pr-1 relative">
        {obj['null_count_match'] && (
          <ResultBox item={prepareObj('null_count_match')} bool={nullCount} />
        )}
      </th>
      <th className="text-center px-4 py-1.5 pr-1 relative">
        {obj['not_null_count_match'] && (
          <ResultBox
            item={prepareObj('not_null_count_match')}
            bool={notNullCount}
          />
        )}
      </th>
    </tr>
  );
}
