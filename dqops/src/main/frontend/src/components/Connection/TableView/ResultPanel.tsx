import React from 'react';
import { ComparisonCheckResultModel, TableComparisonModel } from '../../../api';
import { CheckTypes } from '../../../shared/routes';
import ResultBox from './ResultBox';
import { useDecodedParams } from '../../../utils';

interface dataInterface {
  bools: boolean[];
  obj: { [key: string]: ComparisonCheckResultModel };
  onChange: (obj: Partial<TableComparisonModel>) => void;
  reference: TableComparisonModel;

  index: number;
}

const checkArray = [
  'min',
  'max',
  'sum',
  'mean',
  'null_count',
  'not_null_count'
];

export type CheckName =
  | 'compare_max'
  | 'compare_min'
  | 'compare_sum'
  | 'compare_mean'
  | 'compare_null_count'
  | 'compare_not_null_count';

export default function ResultPanel({
  obj,
  onChange,
  reference,
  index,
  bools
}: dataInterface) {
  const { checkTypes }: { checkTypes: CheckTypes } = useDecodedParams();
  const prepareObj = (key: string): ComparisonCheckResultModel => {
    let newKey = '';

    if (checkTypes == CheckTypes.PROFILING) {
      newKey = 'profile_' + key;
    } else {
      newKey = key;
    }
    if (Object.keys(obj).find((x) => x === newKey)) {
      return obj[newKey];
    } else {
      return {};
    }
  };

  return (
    <tr className=" h-80 w-full">
      <th></th>
      <th></th>
      {checkArray.map((x, jIndex) => (
        <th className="py-1.5 relative" key={jIndex}>
          <ResultBox
            item={prepareObj(x + '_match')}
            bool={bools[jIndex]}
            secondBool={
              obj[
                checkTypes === CheckTypes.PROFILING
                  ? 'profile_' + x + '_match'
                  : x + '_match'
              ]
                ? true
                : false
            }
            reference={reference}
            onChange={onChange}
            checkName={('compare_' + x) as CheckName}
            index={index}
          />
        </th>
      ))}
    </tr>
  );
}
