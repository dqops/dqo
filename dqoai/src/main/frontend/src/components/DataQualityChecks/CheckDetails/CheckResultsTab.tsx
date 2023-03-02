import React, { useMemo } from "react";
import { CheckResultDetailedSingleModel, CheckResultsDetailedDataModel } from "../../../api";
import Select from "../../Select";
import { Table } from "../../Table";
import { useTree } from "../../../contexts/treeContext";
import moment from "moment";

interface CheckResultsTabProps {
  results: CheckResultsDetailedDataModel[];
  dataStreamName?: string;
  month?: string;
  onChangeMonth: (month: string) => void;
  onChangeDataStream: (name: string) => void;
}

const CheckResultsTab = ({ results, dataStreamName, month, onChangeMonth, onChangeDataStream }: CheckResultsTabProps) => {
  const { sidebarWidth } = useTree();

  const getSeverityClass = (row: CheckResultDetailedSingleModel) => {
    if (row.severity === 1) return 'bg-yellow-100';
    if (row.severity === 2) return 'bg-orange-100';
    if (row.severity === 3) return 'bg-red-100';

    return '';
  };

  const columns = [
    {
      label: 'Check Name',
      value: 'checkName',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700',
    },
    {
      label: 'Executed At',
      value: 'executedAt',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700',
    },
    {
      label: 'Time Scale',
      value: 'timeGradient',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700',
    },
    {
      label: 'Time Period',
      value: 'timePeriod',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700',
    },
    {
      label: 'Actual Value',
      value: 'actualValue',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => <div>{typeof value === 'number' ? value : ''}</div>,
    },
    {
      label: 'Expected Value',
      value: 'expectedValue',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => <div>{typeof value === 'number' ? value : ''}</div>,
    },
    {
      label: 'Issue Severity Level',
      value: 'severity',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number) => {
        let name = '';
        switch (value) {
          case 0:
            name = 'Success';
            break;
          case 1:
            name = 'Warning';
            break;
          case 2:
            name = 'Error';
            break;
          case 3:
            name = 'Fatal';
            break;
          default:
            break;
        }

        return (
          <div>{name}</div>
        )
      },
    },
    {
      label: 'Warning - Lower Threshold',
      value: 'warningLowerBound',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => <div>{typeof value === 'number' ? value : ''}</div>,
    },
    {
      label: 'Warning - Upper Threshold',
      value: 'warningUpperBound',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => <div>{typeof value === 'number' ? value : ''}</div>,
    },
    {
      label: 'Error - Lower Threshold',
      value: 'errorLowerBound',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => <div>{typeof value === 'number' ? value : ''}</div>,
    },
    {
      label: 'Error - Upper Threshold',
      value: 'errorUpperBound',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => <div>{typeof value === 'number' ? value : ''}</div>,
    },
    {
      label: 'Fatal - Lower Threshold',
      value: 'fatalLowerBound',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => <div>{typeof value === 'number' ? value : ''}</div>,
    },
    {
      label: 'Fatal - Upper Threshold',
      value: 'fatalUpperBound',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => <div>{typeof value === 'number' ? value : ''}</div>,
    },
    {
      label: 'Include In Kpi',
      value: 'includeInKpi',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700',
    },
    {
      label: 'Include In Sla',
      value: 'includeInSla',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700',
    },
    {
      label: 'Duration Ms',
      value: 'durationMs',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
    },
    {
      label: 'Data Stream',
      value: 'dataStream',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
    },
    {
      label: 'Readout Id',
      value: 'readoutId',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
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
      <div className="flex space-x-8 items-center">
        <div className="flex space-x-4 items-center">
          <div className="text-sm">Data stream</div>
          <Select
            value={dataStreamName}
            options={(results[0]?.dataStreamNames || []).map((item) => ({ label: item, value: item })) || []}
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
      {results.length === 0 && (
        <div className="text-gray-700 mt-5">No Data</div>
      )}

      {results.map((result, index) => (
        <div key={index}>
          <Table
            className="mt-4 w-full"
            columns={columns}
            data={(result.singleCheckResults || []).map((item) => ({ ...item, checkName: result.checkName }))}
            emptyMessage="No Data"
            getRowClass={getSeverityClass}
          />
        </div>
      ))}
    </div>
  );
};

export default CheckResultsTab;
