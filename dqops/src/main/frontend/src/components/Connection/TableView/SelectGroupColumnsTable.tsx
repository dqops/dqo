import React from 'react';
import SectionWrapper from '../../Dashboard/SectionWrapper';
import clsx from 'clsx';
import ColumnSelect from '../../DataQualityChecks/ColumnSelect';
import { Tooltip } from '@material-tailwind/react';

type SelectDataGroupingForTableProps = {
  title: string;
  className?: string;
  placeholder?: string;
  refTable?: string;
  onChangeDataGroupingArray: (
    reference: boolean,
    index: number,
    columnName: string
  ) => void;
  warningMessageList?: Array<boolean>;
  requiredColumnsIndexes: number[];
  responseList: Array<string>;
  checkIfDistinctCountIsBiggerThanLimit?: (
    columnName: string,
    index: number,
    reference: boolean
  ) => void;
  dqoLimit?: number;
  columnOptions: Option[];
};
interface Option {
  label: string | number;
  value?: string | number;
}

export const SelectGroupColumnsTable = ({
  title,
  className,
  placeholder,
  refTable,
  onChangeDataGroupingArray,
  responseList,
  requiredColumnsIndexes,
  warningMessageList,
  checkIfDistinctCountIsBiggerThanLimit,
  dqoLimit,
  columnOptions
}: SelectDataGroupingForTableProps) => {
  const handleColumnSelectChange = (value: string, index: number) => {
    if (refTable) {
      onChangeDataGroupingArray(true, index, value);
    } else {
      onChangeDataGroupingArray(false, index, value);
    }
  };

  const message = `The last known distinct count statistics for this column detected more than ${dqoLimit} rows or the statistics were not collected for this table yet`;

  return (
    <SectionWrapper className={clsx(className, 'text-sm')} title={title}>
      <table className="w-full">
        <tbody>
          {[1, 2, 3, 4, 5, 6, 7, 8, 9].map((item, index) => {
            return (
              <tr key={index} className="">
                <td className="my-1.5 w-11/12">
                  <ColumnSelect
                    triggerClassName={clsx(
                      requiredColumnsIndexes.includes(index)
                        ? 'my-0.5 border border-red-500'
                        : columnOptions.find(
                            (x) =>
                              x.label === responseList[index] ||
                              responseList[index]?.length === 0
                          )
                        ? 'my-0.5'
                        : 'my-0.5 text-red-500'
                    )}
                    value={responseList[index] ?? ''}
                    onChange={(value: string) => {
                      handleColumnSelectChange(value, index),
                        checkIfDistinctCountIsBiggerThanLimit &&
                          checkIfDistinctCountIsBiggerThanLimit(
                            value,
                            index,
                            refTable ? true : false
                          );
                    }}
                    placeholder={placeholder}
                    passedOptions={columnOptions.map((option) => ({
                      label: String(option.label),
                      value: option.value ? String(option.value) : ''
                    }))}
                  />
                </td>
                {warningMessageList?.[index] === true ? (
                  <Tooltip content={message}>
                    <td
                      className="bg-red-500 block mx-5 mt-3"
                      style={{
                        height: '20px',
                        width: '20px',
                        borderRadius: '10px'
                      }}
                    >
                      {' '}
                    </td>
                  </Tooltip>
                ) : null}
              </tr>
            );
          })}
        </tbody>
      </table>
    </SectionWrapper>
  );
};
