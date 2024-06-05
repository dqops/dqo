import { Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import React, { ReactNode } from 'react';
import Checkbox from '../../../Checkbox';
import SvgIcon from '../../../SvgIcon';
import { TSeverityValues } from './TableComparisonConstans';
import { calculateColor } from './TableComparisonUtils';

interface TableRowProps {
  table: string;
  tableLevelComparisonExtended: boolean;
  settableLevelComparisonExtended: React.Dispatch<
    React.SetStateAction<boolean>
  >;
  showRowCount: boolean;
  onUpdateChecksUI: (
    checksUI: any,
    type: 'row' | 'column',
    disabled?: boolean,
    severity?: TSeverityValues
  ) => void;
  checksUI: any;
  setIsUpdated: React.Dispatch<React.SetStateAction<boolean>>;
  tableComparisonResults: any;
  showColumnCount: boolean;
  reference: any;
  checkTypes: any;
}

const TableRow: React.FC<TableRowProps> = ({
  table,
  tableLevelComparisonExtended,
  settableLevelComparisonExtended,
  showRowCount,
  onUpdateChecksUI,
  checksUI,
  setIsUpdated,
  tableComparisonResults,
  showColumnCount,
  reference,
  checkTypes
}) => {
  const renderWarningTooltip = (): ReactNode => {
    return (
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
    );
  };

  return (
    <tr className="mt-10 mb-10">
      <th
        className="text-left pr-4 py-1.5 flex items-center gap-x-2 font-normal"
        onClick={() =>
          settableLevelComparisonExtended((prevState) => !prevState)
        }
      >
        {tableLevelComparisonExtended ? (
          <SvgIcon name="chevron-down" className="w-5 h-5" />
        ) : (
          <SvgIcon name="chevron-right" className="w-5 h-5" />
        )}{' '}
        {table}
      </th>
      <th></th>
      <th
        className={clsx(
          'text-center px-0 py-4 pr-[11px] relative !w-30 !max-w-30 flex justify-center items-center',
          calculateColor(
            '',
            '',
            'row_count',
            checkTypes,
            tableComparisonResults
          )
        )}
      >
        <Checkbox
          checked={showRowCount}
          onChange={(checked) => {
            onUpdateChecksUI(checksUI, 'row', checked);
            setIsUpdated(true);
          }}
        />{' '}
        {calculateColor('', '', 'row_count', checkTypes, tableComparisonResults)
          .length !== 0 &&
          !showRowCount && <>{renderWarningTooltip()}</>}
      </th>
      <th
        className={clsx(
          'text-center px-0 py-4 pr-[11px] relative !w-30 !max-w-30',
          reference.supports_compare_column_count === true
            ? calculateColor(
                '',
                '',
                'column_count',
                checkTypes,
                tableComparisonResults
              )
            : ''
        )}
      >
        {reference.supports_compare_column_count === true ? (
          <Checkbox
            checked={showColumnCount}
            onChange={(checked) => {
              onUpdateChecksUI(checksUI, 'column', checked);
              setIsUpdated(true);
            }}
          />
        ) : null}
        {calculateColor(
          '',
          '',
          'column_count',
          checkTypes,
          tableComparisonResults
        ).length !== 0 &&
          !showColumnCount && <>{renderWarningTooltip()}</>}
      </th>
    </tr>
  );
};

export default TableRow;
