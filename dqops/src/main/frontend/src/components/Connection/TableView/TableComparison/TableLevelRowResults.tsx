import React, { ReactNode } from 'react';
import clsx from 'clsx';
import SvgIcon from '../../../SvgIcon';
import { calculateColor } from './TableComparisonUtils';
import Checkbox from '../../../Checkbox';
import { Tooltip } from '@material-tailwind/react';
import { TSeverityValues } from './TableComparisonConstans';

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
  checksUI: any; // Replace any with your actual data type
  setIsUpdated: React.Dispatch<React.SetStateAction<boolean>>;
  tableComparisonResults: any; // Replace any with your actual data type
  showColumnCount: boolean;
  reference: any; // Replace any with your actual data type
  checkTypes: any; // Replace any with your actual data type
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
        className="pr-6 max-w-80 py-4 px-4 bg-gray-800"
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
      <th className="text-left px-4 py-1.5"></th>
      <th
        className={clsx(
          'text-center px-0 py-4 pr-2 w-1/12 relative',
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
          'text-center px-0 py-4 pr-2 w-1/12 relative',
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
