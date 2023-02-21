import React, { useMemo } from "react";
import { SensorReadoutsDetailedDataModel } from "../../../api";
import Select from "../../Select";
import { Table } from "../../Table";
import { useTree } from "../../../contexts/treeContext";
import moment from "moment";

interface SensorReadoutsTabProps {
  sensorReadouts: SensorReadoutsDetailedDataModel[];
  dataStreamName?: string;
  onChangeDataStream: (name: string) => void;
  month?: string;
  onChangeMonth: (month: string) => void;
}

const SensorReadoutsTab = ({ sensorReadouts, dataStreamName, onChangeDataStream, month, onChangeMonth }: SensorReadoutsTabProps) => {
  const { sidebarWidth } = useTree();

  const columns = [
    {
      label: 'Check Name',
      value: 'checkName',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700',
    },
    {
      label: 'Check Display Name',
      value: 'checkDisplayName',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700',
    },
    {
      label: 'Check Type',
      value: 'checkType',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700',
    },
    {
      label: 'Actual Value',
      value: 'actualValue',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700',
      render: (value: number | string) => <div>{typeof value === 'number' ? value : ''}</div>,
    },
    {
      label: 'Expected Value',
      value: 'expectedValue',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700',
      render: (value: number | string) => <div>{typeof value === 'number' ? value : ''}</div>,
    },
    {
      label: 'Column Name',
      value: 'columnName',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700',
    },
    {
      label: 'Data Stream',
      value: 'dataStream',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700',
    },
    {
      label: 'Duration Ms',
      value: 'durationMs',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700',
    },
    {
      label: 'Executed At',
      value: 'executedAt',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700',
    },
    {
      label: 'Time Gradient',
      value: 'timeGradient',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700',
    },
    {
      label: 'Time Period',
      value: 'timePeriod',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700',
    },
    {
      label: 'Provider',
      value: 'provider',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700',
    },
    {
      label: 'Quality Dimension',
      value: 'qualityDimension',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700',
    },
  ];

  const monthOptions = useMemo(() => {
    return Array(24).fill('').map((item, index) => ({
      label: moment().subtract(index, 'months').format('MMMM YYYY'),
      value: moment().subtract(index, 'months').format('MMMM YYYY')
    }))
  }, []);

  return (
    <div className="py-3 overflow-auto" style={{ maxWidth: `calc(100vw - ${sidebarWidth + 100}px` }}>
      {sensorReadouts.map((result, index) => (
        <div key={index}>
          <div className="flex space-x-8 items-center">
            <div className="flex space-x-4 items-center">
              <div className="text-sm">Data stream</div>
              <Select
                value={dataStreamName}
                options={result.dataStreamNames?.map((item) => ({ label: item, value: item })) || []}
                onChange={onChangeDataStream}
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
          <Table
            className="mt-4 w-full"
            columns={columns}
            data={result.singleSensorReadouts || []}
          />
        </div>
      ))}
    </div>
  );
};

export default SensorReadoutsTab;
