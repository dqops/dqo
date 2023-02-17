import React from "react";
import { CheckResultsDetailedDataModel } from "../../../api";
import Select from "../../Select";
import { Table } from "../../Table";
import { useTree } from "../../../contexts/treeContext";

interface CheckResultsTabProps {
  results: CheckResultsDetailedDataModel[];
}

const CheckResultsTab = ({ results }: CheckResultsTabProps) => {
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
      label: 'Warning Lower Bound',
      value: 'warningLowerBound',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700',
      render: (value: number | string) => <div>{typeof value === 'number' ? value : ''}</div>,
    },
    {
      label: 'Warning Upper Bound',
      value: 'warningUpperBound',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700',
      render: (value: number | string) => <div>{typeof value === 'number' ? value : ''}</div>,
    },
    {
      label: 'Error Lower Bound',
      value: 'errorLowerBound',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700',
      render: (value: number | string) => <div>{typeof value === 'number' ? value : ''}</div>,
    },
    {
      label: 'Fatal Lower Bound',
      value: 'fatalLowerBound',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700',
      render: (value: number | string) => <div>{typeof value === 'number' ? value : ''}</div>,
    },
    {
      label: 'Fatal Upper Bound',
      value: 'fatalUpperBound',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700',
      render: (value: number | string) => <div>{typeof value === 'number' ? value : ''}</div>,
    },
    {
      label: 'Severity',
      value: 'severity',
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
      label: 'Include In Kpi',
      value: 'includeInKpi',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700',
    },
    {
      label: 'Include In Sla',
      value: 'includeInSla',
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
  ];

  return (
    <div className="py-3 overflow-auto" style={{ maxWidth: `calc(100vw - ${sidebarWidth + 100}px` }}>
      {results.map((result, index) => (
        <div key={index}>
          <div className="flex space-x-4 items-center">
            <div className="text-sm">Data stream</div>
            <Select
              value={result.dataStream}
              options={result.dataStreamNames?.map((item) => ({ label: item, value: item })) || []}
            />
          </div>
          <Table
            className="mt-4 w-full"
            columns={columns}
            data={result.singleCheckResults || []}
          />
        </div>
      ))}
    </div>
  );
};

export default CheckResultsTab;
