import moment from 'moment';
import React, { useMemo } from 'react';
import { SensorReadoutsListModel } from '../../../api';
import { useTree } from '../../../contexts/treeContext';
import { CheckTypes } from '../../../shared/routes';
import { getLocalDateInUserTimeZone, useDecodedParams } from '../../../utils';
import SelectTailwind from '../../Select/SelectTailwind';
import { Table } from '../../Table';

interface SensorReadoutsTabProps {
  sensorReadouts: SensorReadoutsListModel[];
  dataGroup?: string;
  onChangeDataGroup: (name: string) => void;
  month?: string;
  onChangeMonth: (month: string) => void;
}

const SensorReadoutsTab = ({
  sensorReadouts,
  dataGroup,
  onChangeDataGroup,
  month,
  onChangeMonth
}: SensorReadoutsTabProps) => {
  const {
    checkTypes
  }: {
    checkTypes: CheckTypes;
  } = useDecodedParams();
  const { sidebarWidth } = useTree();

  const columns = [
    {
      label:
        checkTypes === 'profiling'
          ? 'Profile date (local time)'
          : checkTypes === 'partitioned'
          ? 'Partition Date'
          : 'Checkpoint date',
      value: 'timePeriod',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700'
    },
    {
      label: 'Time Scale',
      value: 'timeGradient',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700'
    },
    {
      label: 'Time Period',
      value: 'timePeriod',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700'
    },
    {
      label: 'Executed At',
      value: 'executedAt',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700'
    },
    {
      label: 'Actual Value',
      value: 'actualValue',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => (
        <div>{typeof value === 'number' ? value : ''}</div>
      )
    },
    {
      label: 'Duration Ms',
      value: 'durationMs',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700 text-right'
    },
    {
      label: 'Data Grouping',
      value: 'dataGroup',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700'
    },
    {
      label: 'Id',
      value: 'id',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700 text-right'
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
            value={dataGroup || sensorReadouts[0]?.dataGroup}
            options={
              (sensorReadouts[0]?.dataGroupNames || []).map((item) => ({
                label: item,
                value: item
              })) || []
            }
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
      </div>
      {sensorReadouts.length === 0 && (
        <div className="text-gray-700 mt-5 text-sm">No Data</div>
      )}
      {sensorReadouts.map((result, index) => (
        <div key={index}>
          <Table
            className="mt-4 w-full"
            columns={columns}
            data={
              result.sensorReadoutEntries?.map((item) => ({
                ...item,
                executedAt: moment(
                  getLocalDateInUserTimeZone(new Date(String(item.executedAt)))
                ).format('YYYY-MM-DD HH:mm:ss')
              })) || []
            }
          />
        </div>
      ))}
    </div>
  );
};

export default SensorReadoutsTab;
