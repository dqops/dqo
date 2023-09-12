import React, { useMemo } from 'react';
import { SensorReadoutsListModel } from '../../../api';
import Select from '../../Select';
import { Table } from '../../Table';
import { useTree } from '../../../contexts/treeContext';
import moment from 'moment';
import { useParams } from 'react-router-dom';
import { CheckTypes } from '../../../shared/routes';

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
  } = useParams();
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
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700'
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
          <Select
            value={ dataGroup || sensorReadouts[0]?.dataGroup}
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
          <Select
            value={month}
            options={monthOptions}
            onChange={onChangeMonth}
          />
        </div>
      </div>
      {sensorReadouts.length === 0 && (
        <div className="text-gray-700 mt-5">No Data</div>
      )}
      {sensorReadouts.map((result, index) => (
        <div key={index}>
          <Table
            className="mt-4 w-full"
            columns={columns}
            data={result.sensorReadoutEntries || []}
          />
        </div>
      ))}
    </div>
  );
};

export default SensorReadoutsTab;
