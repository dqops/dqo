import React from "react";
import { ErrorsDetailedDataModel } from "../../../api";
import Select from "../../Select";
import { Table } from "../../Table";
import { useTree } from "../../../contexts/treeContext";

interface CheckErrorsTabProps {
  errors: ErrorsDetailedDataModel[];
  dataStreamName?: string;
  onChangeDataStream: (name: string) => void;
}

const CheckErrorsTab = ({ errors, dataStreamName, onChangeDataStream }: CheckErrorsTabProps) => {
  const { sidebarWidth } = useTree();

  const columns = [
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
    {
      label: 'Sensor Name',
      value: 'sensorName',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700',
    },
    {
      label: 'Readout Id',
      value: 'readoutId',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700',
    },
    {
      label: 'Error Message',
      value: 'errorMessage',
      className: 'text-sm !py-2 text-gray-700',
      render: (text: string) => <div className="line-clamp-3">{text}</div>
    },
    {
      label: 'Error Source',
      value: 'errorSource',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700',
    },
    {
      label: 'Error Timestamp',
      value: 'errorTimestamp',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700',
    },
  ];

  return (
    <div className="py-3 overflow-auto" style={{ maxWidth: `calc(100vw - ${sidebarWidth + 100}px` }}>
      {errors.map((result, index) => (
        <div key={index} className="mb-4">
          <div className="flex space-x-4 items-center">
            <div className="text-sm">Data stream</div>
            <Select
              value={dataStreamName}
              options={result.dataStreamNames?.map((item) => ({ label: item, value: item })) || []}
              onChange={onChangeDataStream}
            />
          </div>
          <Table
            className="mt-4 w-full"
            columns={columns}
            data={result.singleErrors || []}
          />
        </div>
      ))}
    </div>
  );
};

export default CheckErrorsTab;
