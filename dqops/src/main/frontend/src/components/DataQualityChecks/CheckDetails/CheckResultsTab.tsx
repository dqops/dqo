import React, { useEffect, useMemo, useState } from 'react';
import {
  CheckResultEntryModel,
  CheckResultsListModel
} from '../../../api';
import Select from '../../Select';
import { Table } from '../../Table';
import { useTree } from '../../../contexts/treeContext';
import moment from 'moment';
import SvgIcon from '../../SvgIcon';
import clsx from 'clsx';
import { ChartView } from './ChartView';
import { getCheckResults } from '../../../redux/actions/source.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { CheckTypes } from '../../../shared/routes';
import { useParams } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { getFirstLevelActiveTab } from '../../../redux/selectors';
import { IconButton } from '@material-tailwind/react';

interface CheckResultsTabProps {
  results: CheckResultsListModel[];
  dataGroup?: string;
  month?: string;
  onChangeMonth: (month: string) => void;
  onChangeDataGroup: (name: string) => void;
  runCheckType: string;
  timeScale?: 'daily' | 'monthly';
  checkName: string;
  category?: string;
  comparisonName?: string;
}

const CheckResultsTab = ({
  results,
  dataGroup,
  month,
  onChangeMonth,
  onChangeDataGroup,
  runCheckType,
  timeScale,
  checkName,
  category,
  comparisonName
}: CheckResultsTabProps) => {
  const { sidebarWidth } = useTree();
  const [mode, setMode] = useState('table');
  const dispatch = useActionDispatch();
  const {
    checkTypes,
    connection,
    schema,
    table,
    column
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
    column: string;
  } = useParams();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const getSeverityClass = (row: CheckResultEntryModel) => {
    if (row.severity === 1) return 'bg-yellow-100';
    if (row.severity === 2) return 'bg-orange-100';
    if (row.severity === 3) return 'bg-red-100';

    return '';
  };

  //delete dataGroup from here

  const columns = [
    {
      label:
        checkTypes === 'profiling'
          ? 'Profile date (local time)'
          : checkTypes === 'partitioned'
          ? 'Partition Date'
          : 'Checkpoint date',
      value: 'timePeriod',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700'
    },
    {
      label: 'Time Scale',
      value: 'timeGradient',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700'
    },
    {
      label: 'Executed At',
      value: 'executedAt',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700'
    },
    {
      label: 'Actual Value',
      value: 'actualValue',
      className:
        'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => (
        <div>{typeof value === 'number' ? value : ''}</div>
      )
    },
    {
      label: 'Expected Value',
      value: 'expectedValue',
      className:
        'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => (
        <div>{typeof value === 'number' ? value : ''}</div>
      )
    },
    {
      label: 'Issue Severity Level',
      value: 'severity',
      className:
        'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
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

        return <div>{name}</div>;
      }
    },
    {
      header: () => (
        <span>
          Warning
          <br />
          Lower Threshold
        </span>
      ),
      value: 'warningLowerBound',
      className:
        'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => (
        <div>{typeof value === 'number' ? value : ''}</div>
      )
    },
    {
      header: () => (
        <span>
          Warning
          <br />
          Upper Threshold
        </span>
      ),
      value: 'warningUpperBound',
      className:
        'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => (
        <div>{typeof value === 'number' ? value : ''}</div>
      )
    },
    {
      header: () => (
        <span>
          Error
          <br />
          Lower Threshold
        </span>
      ),
      value: 'errorLowerBound',
      className:
        'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => (
        <div>{typeof value === 'number' ? value : ''}</div>
      )
    },
    {
      header: () => (
        <span>
          Error
          <br />
          Upper Threshold
        </span>
      ),
      value: 'errorUpperBound',
      className:
        'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => (
        <div>{typeof value === 'number' ? value : ''}</div>
      )
    },
    {
      header: () => (
        <span>
          Fatal
          <br />
          Lower Threshold
        </span>
      ),
      value: 'fatalLowerBound',
      className:
        'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => (
        <div>{typeof value === 'number' ? value : ''}</div>
      )
    },
    {
      header: () => (
        <span>
          Fatal
          <br />
          Upper Threshold
        </span>
      ),
      value: 'fatalUpperBound',
      className:
        'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => (
        <div>{typeof value === 'number' ? value : ''}</div>
      )
    },
    {
      label: 'Include In Kpi',
      value: 'includeInKpi',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700'
    },
    {
      label: 'Include In Sla',
      value: 'includeInSla',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700'
    },
    {
      label: 'Duration Ms',
      value: 'durationMs',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right'
    },
    {
      label: 'Data Group',
      value: 'dataGroup',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right'
    },
    {
      label: 'Id',
      value: 'id',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700'
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

  useEffect(() => {
    const startDate = month
      ? moment(month, 'MMMM YYYY').startOf('month').format('YYYY-MM-DD')
      : '';
    const endDate = month
      ? moment(month, 'MMMM YYYY').endOf('month').format('YYYY-MM-DD')
      : '';

    dispatch(
      getCheckResults(checkTypes, firstLevelActiveTab, {
        connection,
        schema,
        table,
        column,
        runCheckType,
        checkName,
        timeScale,
        startDate,
        endDate,
        category,
        comparisonName
      })
    );
  }, [mode]);

  const allResults = results
    .map((result) =>
      (result.checkResultEntries || []).map((item) => ({
        ...item,
        checkName: result.checkName
      }))
    )
    .reduce((arr, el) => [...arr, ...el], []);

  return (
    <div
      className="py-3 overflow-auto"
      style={{ maxWidth: `calc(100vw - ${sidebarWidth + 100}px` }}
    >
      <div className="flex space-x-8 items-center">
        <div className="flex space-x-4 items-center">
          <div className="text-sm">Data group (time series)</div>
          <Select
            value={ dataGroup || results[0]?.dataGroup }
            options={
              (results[0]?.dataGroups || []).map((item) => ({
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
        <div className="flex space-x-4 items-center">
          <IconButton
            size="sm"
            className={
              mode === 'chart'
                ? 'bg-white border border-teal-500'
                : 'bg-teal-500'
            }
            onClick={() => {
               setMode('table');
            }}
          >
            <SvgIcon
              name="table"
              className={clsx(
                'w-4 h-4 cursor-pointer ',
                mode === 'table' ? 'font-bold text-white' : 'text-teal-500'
              )}
            />
          </IconButton>
          <IconButton
            size="sm"
            className={
              mode === 'table'
                ? 'bg-white border border-teal-500 '
                : 'bg-teal-500'
            }
            onClick={() => {
             setMode('chart');
            }}
          >
            <SvgIcon
              name="chart-line"
              className={clsx(
                'w-4 h-4 cursor-pointer',
                mode === 'chart' ? 'font-bold text-white' : 'text-teal-500'
              )}
            />
          </IconButton>
        </div>
      </div>
      {results.length === 0 && (
        <div className="text-gray-700 mt-5">No Data</div>
      )}

      {mode === 'table' && (
        <>
          {results[0] && (
            <Table
              className="mt-4 w-full"
              columns={columns}
              data={(results[0].checkResultEntries || []).map((item) => ({
                ...item,
                checkName: results[0].checkName
              }))}
              emptyMessage="No Data"
              getRowClass={getSeverityClass}
            />
          )}
        </>
      )}
      {mode === 'chart' && (
        <>
          <ChartView data={allResults} />
        </>
      )}
    </div>
  );
};

export default CheckResultsTab;
