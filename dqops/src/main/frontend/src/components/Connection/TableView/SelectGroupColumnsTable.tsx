import { Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import React from 'react';
import SectionWrapper from '../../Dashboard/SectionWrapper';
import ColumnSelect from '../../DataQualityChecks/ColumnSelect';
import TextArea from '../../TextArea';

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
  onChangeParameters: (data: any) => void;
  filter?: string;
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
  columnOptions,
  onChangeParameters,
  filter
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
                        : 'my-0.5'
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
                      className=" block mx-5 mt-3"
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
          <tr className="my-2">
            <TextArea
              className="w-full my-2 py-2"
              value={filter}
              onChange={(e) =>
                refTable
                  ? onChangeParameters({
                      reference_table_filter: e.target.value
                    })
                  : onChangeParameters({
                      compared_table_filter: e.target.value
                    })
              }
            />
          </tr>
        </tbody>
      </table>
    </SectionWrapper>
  );
};
