import { Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import React, { useState } from 'react';
import { ColumnComparisonModel, TableComparisonModel } from '../../../../api';
import { CheckTypes } from '../../../../shared/routes';
import Checkbox from '../../../Checkbox';
import Select, { Option } from '../../../Select';
import SvgIcon from '../../../SvgIcon';
import ResultPanel from '../ResultPanel';
import { itemsToRender } from './TableComparisonConstans';
import { calculateColor, getComparisonResults } from './TableComparisonUtils';

interface ITableComparisonBody {
  item: ColumnComparisonModel;
  index: number;
  checkTypes: CheckTypes;
  columnOptions: Option[];
  tableComparisonResults: any;
  reference: TableComparisonModel;
  onChange: (obj: Partial<TableComparisonModel>) => void;
}

export default function TableComparisonOverwiewBody({
  item,
  index,
  columnOptions,
  checkTypes,
  tableComparisonResults,
  reference,
  onChange
}: ITableComparisonBody) {
  const [isElemExtended, setIsElemExtended] = useState<Array<boolean>>([]);

  const handleExtend = (index: number) => {
    const newArr = [...isElemExtended];
    newArr[index] = !isElemExtended[index];
    setIsElemExtended(newArr);
  };

  const onChangeColumn = (
    obj: Partial<ColumnComparisonModel>,
    columnIndex: number
  ) => {
    const newColumns = reference?.columns?.map((item, index) =>
      index === columnIndex
        ? {
            ...item,
            ...obj
          }
        : item
    );
    onChange({
      columns: newColumns
    });
  };

  return (
    <tbody key={index}>
      <tr key={index}>
        <td
          className="text-left py-1.5 flex items-center gap-x-2 cursor-pointer"
          onClick={() => {
            handleExtend(index);
          }}
        >
          {isElemExtended?.at(index) ? (
            <SvgIcon name="chevron-down" className="w-5 h-5" />
          ) : (
            <SvgIcon name="chevron-right" className="w-5 h-5" />
          )}
          {item.compared_column_name}
        </td>
        <td>
          <Select
            value={
              item.reference_column_name !== undefined
                ? item.reference_column_name
                : ''
            }
            options={[{ value: '', label: '' }, ...columnOptions]}
            onChange={(e) =>
              onChangeColumn({ reference_column_name: e }, index)
            }
            empty={true}
            placeholder=""
          />
        </td>
        {itemsToRender.map((itemData, jIndex) => (
          <td
            key={jIndex}
            className={clsx(
              'text-center px-0 py-4 pr-[11px] relative !w-30 !max-w-30 !h-10 !max-h-10',
              calculateColor(
                item.compared_column_name ?? '',
                itemData.key,
                undefined,
                checkTypes,
                tableComparisonResults
              )
            )}
          >
            <div className="flex justify-center items-center h-full">
              <Checkbox
                checked={
                  !!item[itemData.prop as keyof ColumnComparisonModel] &&
                  !(
                    item.reference_column_name === undefined ||
                    item.reference_column_name.length === 0 ||
                    !columnOptions.find(
                      (x) => x.label === item.reference_column_name
                    )
                  )
                }
                onChange={(checked) => {
                  onChangeColumn(
                    {
                      [itemData.prop as keyof ColumnComparisonModel]: checked
                        ? reference?.default_compare_thresholds
                        : undefined
                    },
                    index
                  );
                }}
                isDisabled={
                  item.reference_column_name === undefined ||
                  item.reference_column_name.length === 0 ||
                  !columnOptions.find(
                    (x) => x.label === item.reference_column_name
                  )
                }
                disabled={
                  item.reference_column_name === undefined ||
                  item.reference_column_name.length === 0 ||
                  !columnOptions.find(
                    (x) => x.label === item.reference_column_name
                  )
                }
              />
            </div>
            {calculateColor(
              item.compared_column_name ?? '',
              itemData.key,
              undefined,
              checkTypes,
              tableComparisonResults
            ).length !== 0 &&
              !(
                !!item[itemData.prop as keyof ColumnComparisonModel] &&
                !(
                  item.reference_column_name === undefined ||
                  item.reference_column_name.length === 0 ||
                  !columnOptions.find(
                    (x) => x.label === item.reference_column_name
                  )
                )
              ) && (
                <Tooltip
                  content="Previous comparison results are present, delete the results before comparing the tables again"
                  className="pr-6 max-w-80 py-2 px-2 bg-gray-800"
                >
                  <div>
                    <SvgIcon
                      name="warning"
                      className="w-5 h-5 absolute bottom-[10px] left-[6px]"
                    />
                  </div>
                </Tooltip>
              )}
          </td>
        ))}
      </tr>
      {isElemExtended.at(index) && (
        <ResultPanel
          obj={getComparisonResults(
            item.compared_column_name ?? '',
            tableComparisonResults
          )}
          onChange={onChange}
          bools={[
            !!item.compare_min,
            !!item.compare_max,
            !!item.compare_sum,
            !!item.compare_mean,
            !!item.compare_null_count,
            !!item.compare_not_null_count
          ]}
          reference={reference}
          index={index}
        />
      )}
    </tbody>
  );
}
