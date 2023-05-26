import { CheckResultDetailedSingleModel } from "../../api";
import React from "react";
import { Table } from "../../components/Table";

type IncidentIssueListProps = {
  issues: CheckResultDetailedSingleModel[];
};

export const IncidentIssueList = ({ issues }: IncidentIssueListProps) => {
  const columns = [
    {
      label: 'Id',
      value: 'id',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700',
    },
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
      header: () => <span>Warning<br/>Lower Threshold</span>,
      value: 'warningLowerBound',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => <div>{typeof value === 'number' ? value : ''}</div>,
    },
    {
      header: () => <span>Warning<br/>Upper Threshold</span>,
      value: 'warningUpperBound',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => <div>{typeof value === 'number' ? value : ''}</div>,
    },
    {
      header: () => <span>Error<br/>Lower Threshold</span>,
      value: 'errorLowerBound',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => <div>{typeof value === 'number' ? value : ''}</div>,
    },
    {
      header: () => <span>Error<br/>Upper Threshold</span>,
      value: 'errorUpperBound',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => <div>{typeof value === 'number' ? value : ''}</div>,
    },
    {
      header: () => <span>Fatal<br/>Lower Threshold</span>,
      value: 'fatalLowerBound',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => <div>{typeof value === 'number' ? value : ''}</div>,
    },
    {
      header: () => <span>Fatal<br/>Upper Threshold</span>,
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
  ];

  const getSeverityClass = (row: CheckResultDetailedSingleModel) => {
    if (row.severity === 1) return 'bg-yellow-100';
    if (row.severity === 2) return 'bg-orange-100';
    if (row.severity === 3) return 'bg-red-100';

    return '';
  };

  return (
    <Table
      className="mt-4 w-full"
      columns={columns}
      data={issues}
      emptyMessage="No Data"
      getRowClass={getSeverityClass}
    />
  );
};

