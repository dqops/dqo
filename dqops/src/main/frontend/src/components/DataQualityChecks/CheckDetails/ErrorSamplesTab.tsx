import { IconButton, Tooltip } from '@material-tailwind/react';
import moment from 'moment/moment';
import React, { useMemo } from 'react';
import { ErrorSamplesListModel } from '../../../api';
import { useTree } from '../../../contexts/treeContext';
import { getLocalDateInUserTimeZone } from '../../../utils';
import SelectTailwind from '../../Select/SelectTailwind';
import SvgIcon from '../../SvgIcon';
import { Table } from '../../Table';

interface ErrorSamplesTabProps {
  errorSamples: ErrorSamplesListModel[];
  dataGroup?: string;
  month?: string;
  onChangeMonth: (month: string) => void;
  onChangeDataGroup: (name: string) => void;
  downloadLink: string;
}
const getRowIds = () => {
  const idColumns = [];
  for (let i = 1; i <= 5; i++) {
    idColumns.push({
      label: `ID Column ${i}`,
      value: `rowId${i}`,
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700 w-50 text-right'
    });
  }
  return idColumns;
};

const ErrorSamplesTab = ({
  errorSamples,
  dataGroup,
  onChangeDataGroup,
  month,
  onChangeMonth,
  downloadLink
}: ErrorSamplesTabProps) => {
  const { sidebarWidth } = useTree();

  const columns = [
    {
      header: () => (
        <span>
          Sample
          <br />
          index
        </span>
      ),
      value: 'sampleIndex',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700 w-20 text-right'
    },
    {
      label: 'Collected at',
      value: 'collectedAt',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700 w-60'
    },

    {
      header: () => (
        <span>
          Result
          <br />
          data type
        </span>
      ),
      value: 'resultDataType',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700 w-50'
    },
    {
      label: 'Result',
      value: 'result',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700 w-50 text-right'
    },
    ...getRowIds(),
    {
      label: 'Id',
      value: 'id',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700 text-left'
    }
  ];

  const monthOptions = useMemo(() => {
    return [
      {
        label: 'Last 3 months',
        value: 'Last 3 months'
      },
      ...Array(24)
        .fill('')
        .map((item, index) => ({
          label: moment().subtract(index, 'months').format('MMMM YYYY'),
          value: moment().subtract(index, 'months').format('MMMM YYYY')
        }))
    ];
  }, []);

  return (
    <div
      className="py-3 overflow-auto"
      style={{ maxWidth: `calc(100vw - ${sidebarWidth + 100}px` }}
    >
      <div className="flex space-x-8 items-center">
        <div className="flex space-x-4 items-center">
          <div className="text-sm">Data group (time series)</div>
          <SelectTailwind
            value={dataGroup || errorSamples[0]?.dataGroup}
            options={
              (errorSamples[0]?.dataGroupsNames || []).map((item) => ({
                label: item,
                value: item
              })) || []
            }
            disabled={true}
            onChange={onChangeDataGroup}
          />
        </div>
        <div className="flex space-x-4 items-center">
          <div className="text-sm">Month</div>
          <SelectTailwind
            value={month}
            options={monthOptions}
            onChange={onChangeMonth}
          />
        </div>
        <IconButton
          ripple={false}
          size="sm"
          disabled={errorSamples.length === 0 ? true : false}
          className={
            'bg-white border border-teal-500 !shadow-none hover:!shadow-none hover:bg-[#DDF2EF]'
          }
        >
          <Tooltip
            content="Download error samples as a CSV file"
            className="max-w-60 py-2 px-2 bg-gray-800"
          >
            <a
              href={downloadLink}
              rel="noreferrer"
              target="_blank"
              className="text-teal-500"
            >
              <SvgIcon
                name="download"
                className={'w-4 h-4 cursor-pointer font-bold text-teal-500'}
              />
            </a>
          </Tooltip>
        </IconButton>
      </div>
      {errorSamples.length === 0 && (
        <div className="text-gray-700 mt-5 text-sm">No Data</div>
      )}
      {errorSamples.map((result, index) => (
        <div key={index}>
          <Table
            className="mt-1 w-full"
            columns={columns}
            data={(result.errorSamplesEntries || []).map((item) => ({
              ...item,
              checkName: result.checkName,
              collectedAt: moment(
                getLocalDateInUserTimeZone(new Date(String(item.collectedAt)))
              ).format('YYYY-MM-DD HH:mm:ss')
            }))}
            emptyMessage="No data"
          />
        </div>
      ))}
    </div>
  );
};

export default ErrorSamplesTab;
