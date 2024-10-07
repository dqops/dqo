import { IconButton, Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import moment from 'moment';
import React, { useEffect, useMemo, useState } from 'react';
import {
  CheckResultEntryModel,
  CheckResultsListModel,
  CheckResultsOverviewDataModel,
  IssueHistogramModel
} from '../../../../api';
import { BarChart } from '../../../../pages/IncidentDetail/BarChart';
import {
  CheckResultApi,
  CheckResultOverviewApi
} from '../../../../services/apiClient';
import { CheckTypes } from '../../../../shared/routes';
import {
  getLocalDateInUserTimeZone,
  useDecodedParams
} from '../../../../utils';
import SectionWrapper from '../../../Dashboard/SectionWrapper';
import { ChartView } from '../../../DataQualityChecks/CheckDetails/ChartView';
import SelectTailwind from '../../../Select/SelectTailwind';
import SvgIcon from '../../../SvgIcon';
import { Table } from '../../../Table';
import {
  calculateDateRange,
  getColor,
  getDisplayCheckNameFromDictionary
} from './ObservabilityStatus.utils';

export default function ObservabilityStatus() {
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
  const [checkResultsEntry, setCheckResultsEntry] = useState<
    Array<CheckResultEntryModel[]>
  >([]);
  const [checkNamesCharts, setCheckNamesCharts] = useState<Array<string>>([]);
  const [checkResultsOverview, setCheckResultsOverview] = useState<
    Array<CheckResultsOverviewDataModel>
  >([]);
  const [histograms, setHistograms] = useState<IssueHistogramModel>({});
  const [histogramFilter, setHistogramFilter] = useState<{
    checkTypes: CheckTypes;
    column?: string;
    check?: string;
  }>({ checkTypes, column });
  const [groupingOptions, setGroupingOptions] = useState<string[]>([]);
  const [dataGroup, setDataGroup] = useState<string | undefined>('');
  const [month, setMonth] = useState<string>('Last 3 months');
  const [mode, setMode] = useState<'chart' | 'table'>('chart');

  const onChangeFilter = (obj: Partial<{ column: string; check: string }>) => {
    setHistogramFilter({ ...histogramFilter, ...obj });
  };

  useEffect(() => {
    const { startDate, endDate } = calculateDateRange(month);
    const getResultsForCharts = (results: Array<CheckResultsListModel>) => {
      const newResults: Array<CheckResultEntryModel[]> = [];
      const newCheckNamesForCharts: Array<string> = [];
      results.forEach((result) => {
        if (!column) {
          if (result.checkName === 'daily_row_count_anomaly') {
            newCheckNamesForCharts.push('daily_row_count_anomaly');
            newResults.push(result.checkResultEntries ?? []);
          }
          if (result.checkName === 'daily_partition_row_count_anomaly') {
            newCheckNamesForCharts.push('daily_partition_row_count_anomaly');
            newResults.push(result.checkResultEntries ?? []);
          }
          if (
            !results.find((x) => x.checkName === 'daily_row_count_anomaly') &&
            result.checkName === 'daily_row_count'
          ) {
            newCheckNamesForCharts.push('daily_row_count');
            newResults.push(result.checkResultEntries ?? []);
          }
          if (
            !results.find((x) => x.checkName === 'daily_partition_row_count_anomaly') &&
            result.checkName === 'daily_partition_row_count'
          ) {
            newCheckNamesForCharts.push('daily_partition_row_count');
            newResults.push(result.checkResultEntries ?? []);
          }
          if (result.checkName?.includes('daily_data_freshness_anomaly')) {
            newCheckNamesForCharts.push('daily_data_freshness_anomaly');
            newResults.push(result.checkResultEntries ?? []);
          }
          if (
            !results.find(
              (x) => x.checkName === 'daily_data_freshness_anomaly'
            ) &&
            result.checkName?.includes('daily_data_freshness')
          ) {
            newCheckNamesForCharts.push('daily_data_freshness');
            newResults.push(result.checkResultEntries ?? []);
          }
        } else {
          if (result.checkName?.includes('daily_nulls_percent_anomaly')) {
            newCheckNamesForCharts.push('daily_nulls_percent_anomaly');
            newResults.push(result.checkResultEntries ?? []);
          }
          if (result.checkName?.includes('daily_partition_nulls_percent_anomaly')) {
            newCheckNamesForCharts.push('daily_partition_nulls_percent_anomaly');
            newResults.push(result.checkResultEntries ?? []);
          }
          if (
            !results.find(
              (x) => x.checkName === 'daily_nulls_percent_anomaly'
            ) &&
            result.checkName?.includes('daily_nulls_percent')
          ) {
            newCheckNamesForCharts.push('daily_nulls_percent');
            newResults.push(result.checkResultEntries ?? []);
          }
          if (
            !results.find(
              (x) => x.checkName === 'daily_partition_nulls_percent_anomaly'
            ) &&
            result.checkName?.includes('daily_partition_nulls_percent')
          ) {
            newCheckNamesForCharts.push('daily_partition_nulls_percent');
            newResults.push(result.checkResultEntries ?? []);
          }
          if (result.checkName?.includes('daily_distinct_count_anomaly')) {
            newCheckNamesForCharts.push('daily_distinct_count_anomaly');
            newResults.push(result.checkResultEntries ?? []);
          }
          if (result.checkName?.includes('daily_partition_distinct_count_anomaly')) {
            newCheckNamesForCharts.push('daily_partition_distinct_count_anomaly');
            newResults.push(result.checkResultEntries ?? []);
          }
          if (
            !results.find(
              (x) => x.checkName === 'daily_distinct_count_anomaly'
            ) &&
            result.checkName?.includes('daily_distinct_count')
          ) {
            newCheckNamesForCharts.push('daily_distinct_count');
            newResults.push(result.checkResultEntries ?? []);
          }
          if (
            !results.find(
              (x) => x.checkName === 'daily_partition_distinct_count_anomaly'
            ) &&
            result.checkName?.includes('daily_partition_distinct_count')
          ) {
            newCheckNamesForCharts.push('daily_partition_distinct_count');
            newResults.push(result.checkResultEntries ?? []);
          }
        }
      });

      setCheckNamesCharts(newCheckNamesForCharts);
      return newResults;
    };

    const getCheckListData = (data: CheckResultsOverviewDataModel[]) => {
      const isAnomalyRowCount = data.find((x) =>
        x.checkName?.includes('row_count_anomaly')
      );
      const isRowCount = data.find((x) => x.checkName?.includes('row_count'));
      if (!isAnomalyRowCount && !isRowCount) {
        return;
      }
      switch (checkTypes) {
        case CheckTypes.MONITORING: {
          if (column) {
            CheckResultApi.getColumnMonitoringChecksResults(
              connection,
              schema,
              table,
              column,
              'daily',
              dataGroup,
              startDate,
              endDate,
              undefined,
              undefined,
              undefined,
              undefined,
              15
            ).then((res) => {
              console.log(res.data);
              setGroupingOptions(
                res.data.map((item) => item.dataGroup ?? '') ?? []
              );
              setCheckResultsEntry(getResultsForCharts(res.data ?? []) ?? []);
            });
            break;
          } else {
            CheckResultApi.getTableMonitoringChecksResults(
              connection,
              schema,
              table,
              'daily',
              dataGroup,
              startDate,
              endDate,
              undefined,
              undefined,
              undefined,
              undefined,
              15
            ).then((res) => {
              console.log(res.data);
              setCheckResultsEntry(getResultsForCharts(res.data ?? []) ?? []);
            });
            break;
          }
        }
        case CheckTypes.PARTITIONED: {
          if (column) {
            CheckResultApi.getColumnPartitionedChecksResults(
              connection,
              schema,
              table,
              column,
              'daily',
              dataGroup,
              startDate,
              endDate,
              undefined,
              undefined,
              undefined,
              undefined,
              15
            ).then((res) => {
              setCheckResultsEntry(getResultsForCharts(res.data ?? []) ?? []);
            });
            break;
          } else {
            CheckResultApi.getTablePartitionedChecksResults(
              connection,
              schema,
              table,
              'daily',
              dataGroup,
              startDate,
              endDate,
              undefined,
              undefined,
              undefined,
              undefined,
              15
            ).then((res) => {
              setCheckResultsEntry(getResultsForCharts(res.data ?? []) ?? []);
            });
            break;
          }
        }
      }
    };

    const filterColumnChecks = (data: CheckResultsOverviewDataModel[]) => {
      return data.filter((x) => x.checkCategory === 'schema');
    };

    const filterTableChecks = (data: CheckResultsOverviewDataModel[]) => {
      return data.filter((x) => x.checkCategory === 'schema');
    };
    const getOverviewData = () => {
      switch (checkTypes) {
        case CheckTypes.MONITORING: {
          if (column) {
            CheckResultOverviewApi.getColumnMonitoringChecksOverview(
              connection,
              schema,
              table,
              column,
              'daily',
              undefined,
              undefined
            ).then((res) => {
              setCheckResultsOverview(filterColumnChecks(res.data ?? []) ?? []);
              getCheckListData(res.data);
              //setResults(getRowCountResults(res.data ?? []) ?? []);
            });
            break;
          } else {
            CheckResultOverviewApi.getTableMonitoringChecksOverview(
              connection,
              schema,
              table,
              'daily',
              undefined,
              undefined,
              15
            ).then((res) => {
              getCheckListData(res.data);
              setCheckResultsOverview(filterTableChecks(res.data ?? []) ?? []);

              // setResults(getRowCountResults(res.data ?? []) ?? []);
            });
            break;
          }
        }
        case CheckTypes.PARTITIONED: {
          if (column) {
            CheckResultOverviewApi.getColumnPartitionedChecksOverview(
              connection,
              schema,
              table,
              column,
              'daily',
              undefined,
              undefined,
              15
            ).then((res) => {
              getCheckListData(res.data);
              setCheckResultsOverview(filterColumnChecks(res.data ?? []) ?? []);
              //  setResults(getRowCountResults(res.data ?? []) ?? []);
            });
            break;
          } else {
            CheckResultOverviewApi.getTablePartitionedChecksOverview(
              connection,
              schema,
              table,
              'daily',

              undefined,
              undefined,
              15
            ).then((res) => {
              getCheckListData(res.data);
              setCheckResultsOverview(filterTableChecks(res.data ?? []) ?? []);
              //  setResults(getRowCountResults(res.data ?? []) ?? []);
            });
            break;
          }
        }
      }
    };
    getOverviewData();
  }, [checkTypes, connection, schema, table, column, month]);

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
    CheckResultApi.getTableIssuesHistogram(
      connection,
      schema,
      table,
      undefined,
      undefined,
      undefined,
      undefined,
      column ? column : histogramFilter.column,
      histogramFilter.check,
      checkTypes as 'monitoring' | 'partitioned'
    ).then((res) => {
      setHistograms(res.data);
    });
  }, [connection, schema, table, column, histogramFilter]);
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
      className: 'text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left'
    }
  ];

  return (
    <div className="p-4 mt-2">
      <div className="flex flex-wrap items-center gap-x-4 mt-4">
        {checkResultsOverview.map((result, index) => (
          <SectionWrapper
            key={index}
            title={getDisplayCheckNameFromDictionary(result.checkName ?? '')}
            className=" mb-4"
          >
            <div className="flex items-center gap-x-1 min-w-60">
              {result?.statuses?.map((status, index) => (
                <Tooltip
                  key={index}
                  content={
                    <div className="text-white">
                      <div>Sensor value: {result?.results?.[index]}</div>
                      <div>
                        Executed at:{' '}
                        {result?.executedAtTimestamps
                          ? moment(
                              getLocalDateInUserTimeZone(
                                new Date(result.executedAtTimestamps[index])
                              )
                            ).format('YYYY-MM-DD HH:mm:ss')
                          : ''}
                      </div>
                      <div>
                        Time period:{' '}
                        {result?.timePeriodDisplayTexts
                          ? result.timePeriodDisplayTexts[index]
                          : ''}
                      </div>
                      <div>
                        Data group:{' '}
                        {result?.dataGroups ? result.dataGroups[index] : ''}
                      </div>
                    </div>
                  }
                  className="max-w-80 py-2 px-2 bg-gray-800"
                >
                  <div
                    key={index}
                    className={clsx(
                      'w-3 h-3',
                      getColor(result.statuses?.[index])
                    )}
                  />
                </Tooltip>
              ))}
            </div>
          </SectionWrapper>
        ))}
      </div>
      {(checkResultsEntry.length > 0 ? checkResultsEntry : [[]]).map(
        (result, index) => (
          <SectionWrapper title={getDisplayCheckNameFromDictionary(checkNamesCharts[index])} className="mb-6 max-w-200" key={index}>
            <div className="flex space-x-8 items-center">
              <div className="flex space-x-4 items-center">
                <div className="text-sm">Data group (time series)</div>
                <SelectTailwind
                  value={dataGroup || result[0]?.dataGroup}
                  options={
                    (Array.from(new Set(groupingOptions)) ?? []).map(
                      (item) => ({
                        label: item ?? '',
                        value: item
                      })
                    ) || []
                  }
                  onChange={setDataGroup}
                />
              </div>
              <div className="flex space-x-4 items-center">
                <div className="text-sm">Month</div>
                <SelectTailwind
                  value={month}
                  options={monthOptions}
                  onChange={setMonth}
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
                          mode === 'table'
                            ? 'font-bold text-white'
                            : 'text-teal-500'
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
                          mode === 'chart'
                            ? 'font-bold text-white'
                            : 'text-teal-500'
                        )}
                      />
                    </div>
                  </Tooltip>
                </IconButton>
              </div>
            </div>
            {mode === 'chart' ? (
              <ChartView data={result} />
            ) : (
              <div className="w-full overflow-x-auto">
                <Table
                  className="mt-1 w-full"
                  columns={columns}
                  data={(result ?? []).map((item) => ({
                    ...item,
                    checkName: result[0].checkName,
                    executedAt: moment(
                      getLocalDateInUserTimeZone(new Date(String()))
                    ).format('YYYY-MM-DD HH:mm:ss'),
                    timePeriod: item.timePeriod?.replace(/T/g, ' ')
                  }))}
                  emptyMessage="No data"
                  getRowClass={getColor}
                />
              </div>
            )}
          </SectionWrapper>
        )
      )}
      <SectionWrapper title="Data quality issues" className="mt-2 mb-4">
        <div className="grid grid-cols-4 px-4 gap-4 my-6">
          <div className="col-span-2">
            <BarChart histograms={histograms} setHistograms={setHistograms} />
          </div>
          {!column && (
            <SectionWrapper title="Filter by columns" className="text-sm">
              {Object.keys(histograms?.columns || {}).map((column, index) => (
                <div
                  className={clsx(
                    'flex gap-2 mb-2 cursor-pointer whitespace-normal break-all',
                    {
                      'font-bold text-gray-700':
                        histogramFilter?.column === column,
                      'text-gray-500':
                        histogramFilter?.column &&
                        histogramFilter?.column !== column
                    }
                  )}
                  key={index}
                  onClick={() =>
                    onChangeFilter({
                      column:
                        histogramFilter?.column === column ? undefined : column
                    })
                  }
                >
                  <span>
                    {column.length === 0 ? '(no column name)' : ''}
                    {column}
                  </span>
                  ({histograms?.columns?.[column]})
                </div>
              ))}
            </SectionWrapper>
          )}
          <SectionWrapper
            title="Filter by check name"
            className={clsx('text-sm', column && 'w-80')}
          >
            {Object.keys(histograms?.checks || {}).map((check, index) => (
              <div
                className={clsx(
                  'flex gap-2 mb-2 cursor-pointer whitespace-normal break-all',
                  {
                    'font-bold text-gray-700': histogramFilter?.check === check,
                    'text-gray-500':
                      histogramFilter?.check && histogramFilter?.check !== check
                  }
                )}
                key={index}
                onClick={() =>
                  onChangeFilter({
                    check: histogramFilter?.check === check ? '' : check
                  })
                }
              >
                <span>{check}</span>({histograms?.checks?.[check]})
              </div>
            ))}
          </SectionWrapper>
        </div>
      </SectionWrapper>
    </div>
  );
}
