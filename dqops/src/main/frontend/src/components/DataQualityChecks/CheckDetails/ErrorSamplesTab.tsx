import moment from 'moment/moment';
import React, { useMemo } from 'react';
import { ErrorSamplesListModel } from '../../../api';
import { useTree } from '../../../contexts/treeContext';
import { getLocalDateInUserTimeZone } from '../../../utils';
import Select from '../../Select';
import { Table } from '../../Table';
import ErrorText from './ErrorText';

interface ErrorSamplesTabProps {
  errorSamples: ErrorSamplesListModel[];
  dataGroup?: string;
  month?: string;
  onChangeMonth: (month: string) => void;
  onChangeDataGroup: (name: string) => void;
}

const ErrorSamplesTab = ({
  errorSamples,
  dataGroup,
  onChangeDataGroup,
  month,
  onChangeMonth
}: ErrorSamplesTabProps) => {
  const { sidebarWidth } = useTree();

  const columns = [
    {
      label: 'Executed At',
      value: 'executedAt',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700 w-60'
    },
    {
      label: 'Error Source',
      value: 'errorSource',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700 w-50'
    },
    {
      label: 'Error Message',
      value: 'errorMessage',
      className: 'text-sm !py-2 text-gray-700 w-120',
      render: (text: string) => <ErrorText text={text} />
    },
    {
      label: 'Readout Id',
      value: 'readoutId',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700 w-80 text-right'
    }
  ];

  const getRowId = (sample: ErrorSamplesListModel) => {
    if (!sample.errorSamplesEntries) return;
    const idColumns = [];
    for (let i = 1; i <= 5; i++) {
      if (`rowId${i}` in sample.errorSamplesEntries) {
        idColumns.push({
          label: `rowId${i}`,
          value: `rowId${i}`,
          className: 'text-sm !py-2 whitespace-nowrap text-gray-700 w-50'
        });
      }
    }
  };

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
          <Select
            value={dataGroup || errorSamples[0]?.dataGroup}
            options={
              (errorSamples[0]?.dataGroupsNames || []).map((item) => ({
                label: item,
                value: item
              })) || []
            }
            onChange={onChangeDataGroup}
          />
        </div>
        <div className="flex space-x-4 items-center">
          <div className="text-sm">Month</div>
          <Select
            value={month}
            options={monthOptions}
            onChange={onChangeMonth}
          />
        </div>
      </div>
      {errorSamples.length === 0 && (
        <div className="text-gray-700 mt-5 text-sm">No Data</div>
      )}
      {errorSamples.map((result, index) => (
        <div key={index} className="mb-4">
          <Table
            className="mt-4 w-full"
            columns={columns}
            data={(result.errorSamplesEntries || []).map((item) => ({
              ...item,
              checkName: result.checkName,
              executedAt: moment(
                getLocalDateInUserTimeZone(new Date(String(item.executedAt)))
              ).format('YYYY-MM-DD HH:mm:ss')
            }))}
            emptyMessage="No Data"
          />
        </div>
      ))}
    </div>
  );
};

export default ErrorSamplesTab;
