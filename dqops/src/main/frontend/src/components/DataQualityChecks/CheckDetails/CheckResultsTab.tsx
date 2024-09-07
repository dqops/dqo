import { IconButton, Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import moment from 'moment';
import React, { useEffect, useMemo, useState } from 'react';
import { useSelector } from 'react-redux';
import { CheckResultEntryModel, CheckResultsListModel } from '../../../api';
import { useTree } from '../../../contexts/treeContext';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { getCheckResults } from '../../../redux/actions/source.actions';
import { getFirstLevelActiveTab } from '../../../redux/selectors';
import { CheckTypes } from '../../../shared/routes';
import { getLocalDateInUserTimeZone, useDecodedParams } from '../../../utils';
import SelectTailwind from '../../Select/SelectTailwind';
import SvgIcon from '../../SvgIcon';
import { Table } from '../../Table';
import { ChartView } from './ChartView';

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
  } = useDecodedParams();
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
          ? 'Partition date'
          : 'Checkpoint date',
      value: 'timePeriod',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700'
    },
    {
      label: 'Time scale',
      value: 'timeGradient',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700'
    },
    {
      label: 'Executed at',
      value: 'executedAt',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700'
    },
    {
      label: 'Actual value',
      value: 'actualValue',
      className:
        'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => (
        <div>{typeof value === 'number' ? value : ''}</div>
      )
    },
    {
      label: 'Expected value',
      value: 'expectedValue',
      className:
        'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right',
      render: (value: number | string) => (
        <div>{typeof value === 'number' ? value : ''}</div>
      )
    },
    {
      header: () => (
        <span>
          Issue
          <br />
          severity level
        </span>
      ),
      value: 'severity',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left',
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
          lower threshold
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
          upper threshold
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
          lower threshold
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
          upper threshold
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
          lower threshold
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
          upper threshold
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
      label: 'Duration ms',
      value: 'durationMs',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right'
    },
    {
      label: 'Data group',
      value: 'dataGroup',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left'
    },
    {
      label: 'Id',
      value: 'id',
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right'
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
        checkName: results[0].checkName,
        executedAt: Number(
          moment(
            getLocalDateInUserTimeZone(new Date(String(item.executedAt)))
          ).format('YYYY-MM-DD HH:mm:ss')
        ),
        timePeriod: item.timePeriod?.replace(/T/g, ' ')
      }))
    )
    .reduce((arr, el) => [...arr, ...el], []);

  return (
    <div
      className="pt-3 overflow-auto"
      style={{ maxWidth: `calc(100vw - ${sidebarWidth + 100}px` }}
    >
      <div className="flex space-x-8 items-center">
        <div className="flex space-x-4 items-center">
          <div className="text-sm">Data group (time series)</div>
          <SelectTailwind
            value={dataGroup || results[0]?.dataGroup}
            options={
              (results[0]?.dataGroups || []).map((item) => ({
                label: item,
                value: item
              })) || []
            }
            onChange={onChangeDataGroup}
            disabled={(results[0]?.dataGroups ?? []).length === 0}
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
        <div className="flex space-x-4 items-center">
          <IconButton
            ripple={false}
            size="sm"
            className={
              mode === 'chart'
                ? 'bg-white border border-teal-500 !shadow-none hover:!shadow-none hover:bg-[#DDF2EF] '
                : 'bg-teal-500 !shadow-none hover:!shadow-none hover:bg-[#028770]'
            }
            onClick={() => {
              setMode('table');
            }}
          >
            <Tooltip
              content="View results in a table"
              className="max-w-80 py-2 px-2 !mb-6 bg-gray-800 !absolute"
            >
              <div>
                <SvgIcon
                  name="table"
                  className={clsx(
                    'w-4 h-4 cursor-pointer ',
                    mode === 'table' ? 'font-bold text-white' : 'text-teal-500'
                  )}
                />
              </div>
            </Tooltip>
          </IconButton>
          <IconButton
            size="sm"
            ripple={false}
            className={
              mode === 'table'
                ? 'bg-white border border-teal-500 !shadow-none hover:!shadow-none hover:bg-[#DDF2EF] '
                : 'bg-teal-500 !shadow-none hover:!shadow-none hover:bg-[#028770]'
            }
            onClick={() => {
              setMode('chart');
            }}
          >
            <Tooltip
              content="View results in a graph"
              className="max-w-80 py-2 px-2 !mb-6 bg-gray-800 !absolute "
            >
              <div>
                <SvgIcon
                  name="chart-line"
                  className={clsx(
                    'w-4 h-4 cursor-pointer',
                    mode === 'chart' ? 'font-bold text-white' : 'text-teal-500'
                  )}
                />
              </div>
            </Tooltip>
          </IconButton>
        </div>
      </div>
      {results.length === 0 && (
        <div className="text-gray-700 mt-5 text-sm">No Data</div>
      )}
      {mode === 'table' && results.length !== 0 && (
        <>
          {results[0] && (
            <Table
              className="mt-1 w-full"
              columns={columns}
              data={(results[0].checkResultEntries || []).map((item) => ({
                ...item,
                checkName: results[0].checkName,
                executedAt: moment(
                  getLocalDateInUserTimeZone(new Date(String(item.executedAt)))
                ).format('YYYY-MM-DD HH:mm:ss'),
                timePeriod: item.timePeriod?.replace(/T/g, ' ')
              }))}
              emptyMessage="No data"
              getRowClass={getSeverityClass}
            />
          )}
        </>
      )}
      {mode === 'chart' && results.length !== 0 && (
        <>
          <ChartView data={allResults} />
        </>
      )}
    </div>
  );
};

export default CheckResultsTab;
