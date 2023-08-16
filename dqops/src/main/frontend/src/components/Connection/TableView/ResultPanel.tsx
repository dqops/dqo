import React from 'react';
import ResultBox from './ResultBox';
import { ComparisonCheckResultModel, TableComparisonModel } from '../../../api';
import { CheckTypes } from '../../../shared/routes';
import { useParams } from 'react-router-dom';

interface dataInterface {
  minBool?: boolean;
  maxBool?: boolean;
  sumBool?: boolean;
  meanBool?: boolean;
  nullCount?: boolean;
  notNullCount?: boolean;
  obj: { [key: string]: ComparisonCheckResultModel };
  onChange: (obj: Partial<TableComparisonModel>) => void;
  reference: TableComparisonModel;

  index: number;
}

export default function ResultPanel({
  minBool,
  maxBool,
  sumBool,
  meanBool,
  nullCount,
  notNullCount,
  obj,
  onChange,
  reference,

  index
}: dataInterface) {
  const {checkTypes} : {checkTypes : CheckTypes} = useParams()
  const prepareObj = (key: string): ComparisonCheckResultModel => {
    let newKey = ''

    if(checkTypes == CheckTypes.PROFILING){
      newKey = "profile_" + key
    }else{
      newKey = key;
    }

    if (Object.keys(obj).find((x) => x === newKey)) {
      return obj[newKey];
    } else {
      return {};
    }
  };

  return (
    <tr className=" h-70 w-full">
      <th className="text-left pr-4 py-1.5  w-1/6 "></th>
      <th className="text-left px-4 py-1.5 w-1/6 "></th>
      <th className=" px-4 py-1.5 pr-1  relative">
        <ResultBox
          item={prepareObj('min_match')}
          bool={minBool}
          secondBool={obj[ checkTypes === CheckTypes.PROFILING ?  'profile_min_match' : "min_match"] ? true : false}
          reference={reference}
          onChange={onChange}
          checkName="compare_min"
          index={index}
        />
      </th>
      <th className=" px-4 py-1.5 pr-1  relative">
        <ResultBox
          item={prepareObj('max_match')}
          bool={maxBool}
          secondBool={obj[checkTypes === CheckTypes.PROFILING ?  'profile_max_match' : "max_match"] ? true : false}
          onChange={onChange}
          index={index}
          checkName="compare_max"
          reference={reference}
        />
      </th>
      <th className=" px-4 py-1.5 pr-1 relative">
        <ResultBox
          item={prepareObj('sum_match')}
          bool={sumBool}
          secondBool={obj[checkTypes === CheckTypes.PROFILING ?  'profile_sum_match' : "max_match"] ? true : false}
          onChange={onChange}
          index={index}
          checkName="compare_sum"
          reference={reference}
        />
      </th>
      <th className="px-4 py-1.5 pr-1 relative">
        <ResultBox
          item={prepareObj('mean_match')}
          bool={meanBool}
          secondBool={obj[checkTypes === CheckTypes.PROFILING ?  'profile_mean_match' : "mean_match"] ? true : false}
          onChange={onChange}
          index={index}
          checkName="compare_mean"
          reference={reference}
        />
      </th>
      <th className="text-center px-4 py-1.5 pr-1 relative">
        <ResultBox
          item={prepareObj('null_count_match')}
          bool={nullCount}
          secondBool={obj[checkTypes === CheckTypes.PROFILING ?  'profile_null_count_match' : "null_count_match"] ? true : false}
          onChange={onChange}
          index={index}
          checkName="compare_null_count"
          reference={reference}
        />
      </th>
      <th className="text-center px-4 py-1.5 pr-1 relative">
        <ResultBox
          item={prepareObj('not_null_count_match')}
          bool={notNullCount}
          secondBool={obj[ checkTypes === CheckTypes.PROFILING ?'profile_not_null_count_match' :  'not_null_count_match'] ? true : false}
          onChange={onChange}
          index={index}
          checkName="compare_not_null_count"
          reference={reference}
        />
      </th>
    </tr>
  );
}
