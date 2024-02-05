import React from 'react';
import {
  CompareThresholdsModel,
  ComparisonCheckResultModel,
  TableComparisonModel
} from '../../../api';
import clsx from 'clsx';
import Input from '../../Input';
import { CheckName } from './ResultPanel';

interface data {
  item: ComparisonCheckResultModel;
  bool?: boolean;
  secondBool?: boolean;
  type?: string;
  onChange: (obj: Partial<TableComparisonModel>) => void;
  reference?: TableComparisonModel;
  checkName?: CheckName;
  index: number;
}

const ResultBox = ({
  item,
  bool,
  onChange,
  reference,
  secondBool,
  checkName,
  index
}: data) => {
  console.log(item);
  const onChangeCompare = (
    obj: Partial<CompareThresholdsModel>,
    checkName?: CheckName
  ) => {
    const updatedColumns = reference?.columns?.map((column, i) =>
      i === index
        ? {
            ...column,
            [checkName!]: {
              ...(column[checkName!] || {}),
              ...obj
            }
          }
        : column
    );

    onChange &&
      onChange({
        columns: updatedColumns
      });
  };

  return (
    <tr className="flex flex-col text-xs font-light justify-start items-start  absolute top-0">
      <td className="h-42">
        {' '}
        <div className="block mb-5 mt-3 h-20">
          <div
            className={clsx(
              ' px-4 py-2 flex items-center gap-2',
              bool ? 'bg-yellow-100' : 'bg-gray-400'
            )}
          >
            <Input
              className="max-w-30 !min-w-initial"
              type="number"
              value={
                reference?.columns?.at(index)?.[checkName!]
                  ?.warning_difference_percent
              }
              onChange={(e) => {
                onChangeCompare(
                  {
                    warning_difference_percent:
                      String(e.target.value).length === 0
                        ? undefined
                        : Number(e.target.value)
                  },
                  checkName
                );
              }}
              disabled={!bool}
            />
            %
          </div>
          <div
            className={clsx(
              ' px-4 py-2 flex items-center gap-2',
              bool ? 'bg-orange-100' : 'bg-gray-50'
            )}
          >
            <Input
              className="max-w-30 !min-w-initial"
              type="number"
              value={
                reference?.columns?.at(index)?.[checkName!]
                  ?.error_difference_percent
              }
              onChange={(e) => {
                onChangeCompare(
                  {
                    error_difference_percent:
                      String(e.target.value).length === 0
                        ? undefined
                        : Number(e.target.value)
                  },
                  checkName
                );
              }}
              disabled={!bool}
            />
            %
          </div>
          <div
            className={clsx(
              ' px-4 py-2 flex items-center gap-2',
              bool ? 'bg-red-100' : 'bg-gray-300'
            )}
          >
            <Input
              className="max-w-30 !min-w-initial"
              type="number"
              value={
                reference?.columns?.at(index)?.[checkName!]
                  ?.fatal_difference_percent
              }
              onChange={(e) => {
                onChangeCompare(
                  {
                    fatal_difference_percent:
                      String(e.target.value).length === 0
                        ? undefined
                        : Number(e.target.value)
                  },
                  checkName
                );
              }}
              disabled={!bool}
            />
            %
          </div>
        </div>
      </td>
      {secondBool && (
        <>
          <td className="flex justify-between w-2/3 ">
            <th className="text-xs font-light">Valid:</th>
            {item.valid_results}
          </td>
          <td className="flex justify-between w-2/3 ">
            <th className="text-xs font-light">Errors:</th>
            {item.errors}
          </td>
          <td className="flex justify-between w-2/3 ">
            <th className="text-xs font-light">Fatal:</th>
            {item.fatals}
          </td>
          <td className="flex justify-between w-2/3 ">
            <th className="text-xs font-light">Warning:</th>
            {item.warnings}
          </td>
          <td>
            <a className="group relative text-blue-300 underline whitespace-nowrap cursor-pointer">
              Show mismatches
              <section
                className={clsx(
                  'hidden group-hover:grid grid-cols-2 absolute top-4 right-0 px-1 gap-y-1 rounded-md border border-gray-400 z-50 bg-white text-black no-underline',
                  item.not_matching_data_groups ? 'w-60 h-60 ' : 'w-40 h-10'
                )}
                style={{ right: '-8px' }}
              >
                {item.not_matching_data_groups ? (
                  item.not_matching_data_groups.map((x, index) => (
                    <span key={index}>
                      {x.replace(/./, (c) => c.toUpperCase())}
                    </span>
                  ))
                ) : (
                  <span>Whole table</span>
                )}
              </section>
            </a>
          </td>
        </>
      )}
    </tr>
  );
};
export default ResultBox;
