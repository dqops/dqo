import { Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import moment from 'moment';
import React, { useEffect, useMemo, useState } from 'react';
import {
  CheckResultEntryModel,
  CheckResultsListModel,
  IssueHistogramModel
} from '../../../../api';
import { BarChart } from '../../../../pages/IncidentDetail/BarChart';
import { CheckResultApi } from '../../../../services/apiClient';
import { CheckTypes } from '../../../../shared/routes';
import {
  getLocalDateInUserTimeZone,
  useDecodedParams
} from '../../../../utils';
import SectionWrapper from '../../../Dashboard/SectionWrapper';
import { ChartView } from '../../../DataQualityChecks/CheckDetails/ChartView';
import SelectTailwind from '../../../Select/SelectTailwind';

export default function ObservabilityStatus() {
  const {
    checkTypes,
    connection,
    schema,
    table
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
  } = useDecodedParams();
  const [results, setResults] = useState<CheckResultEntryModel[]>([]);
  const [allResults, setAllResults] = useState<CheckResultsListModel[]>([]);
  const [histograms, setHistograms] = useState<IssueHistogramModel>({});
  const [histogramFilter, setHistogramFilter] = useState<{
    checkTypes: CheckTypes;
    column?: string;
    check?: string;
  }>({ checkTypes });
  const [isAnomalyRowCount, setIsAnomalyRowCount] = useState<boolean>(false);
  const [dataGroup, setDataGroup] = useState<string | undefined>('');
  const [month, setMonth] = useState<string>('Last 3 months');

  const onChangeFilter = (obj: Partial<{ column: string; check: string }>) => {
    setHistogramFilter({ ...histogramFilter, ...obj });
  };

  useEffect(() => {
    const { startDate, endDate } = calculateDateRange(month);
    const getRowCountResults = (results: Array<CheckResultsListModel>) => {
      if (
        results.find((result) =>
          result.checkName?.includes('row_count_anomaly')
        )
      ) {
        setIsAnomalyRowCount(true);
        return results.filter((result) =>
          result.checkName?.includes('row_count_anomaly')
        )[0]?.checkResultEntries;
      }
      return results.filter((result) =>
        result.checkName?.includes('row_count')
      )[0]?.checkResultEntries;
    };
    switch (checkTypes) {
      case CheckTypes.MONITORING: {
        CheckResultApi.getTableMonitoringChecksResults(
          connection,
          schema,
          table,
          'daily',
          undefined,
          startDate,
          endDate,
          undefined,
          undefined,
          undefined,
          undefined,
          15
        ).then((res) => {
          setAllResults(
            res.data.filter((x) => x.checkCategory === 'schema') ?? []
          );
          setResults(getRowCountResults(res.data ?? []) ?? []);
        });
        break;
      }
      case CheckTypes.PARTITIONED: {
        CheckResultApi.getTablePartitionedChecksResults(
          connection,
          schema,
          table,
          'daily',
          undefined,
          startDate,
          endDate,
          undefined,
          undefined,
          undefined,
          undefined,
          15
        ).then((res) => {
          setAllResults(
            res.data.filter((x) => x.checkCategory === 'schema') ?? []
          );
          setResults(getRowCountResults(res.data ?? []) ?? []);
        });
        break;
      }
    }
  }, [checkTypes, connection, schema, table, month]);

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
      histogramFilter.column,
      histogramFilter.check
    ).then((res) => {
      setHistograms(res.data);
    });
  }, [connection, schema, table, histogramFilter]);

  console.log(allResults);

  return (
    <div className="p-4 mt-2">
      <SectionWrapper
        title={isAnomalyRowCount ? 'Anomaly row count' : 'Row count'}
      >
        <div className="flex space-x-8 items-center">
          <div className="flex space-x-4 items-center">
            <div className="text-sm">Data group (time series)</div>
            <SelectTailwind
              value={dataGroup || results[0]?.dataGroup}
              options={
                (results ?? []).map((item) => ({
                  label: item.dataGroup ?? '',
                  value: item.dataGroup
                })) || []
              }
              onChange={setDataGroup}
              disabled={true}
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
        </div>
        {results.length === 0 && (
          <div className="text-gray-700 mt-5 text-sm">No Data</div>
        )}
        <ChartView data={results} />
      </SectionWrapper>
      <SectionWrapper title="Data quality issue severity" className="mt-8 mb-4">
        <div className="grid grid-cols-4 px-4 gap-4 my-6">
          <div className="col-span-2">
            <BarChart histograms={histograms} />
          </div>

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
          <SectionWrapper title="Filter by check name" className="text-sm">
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
      <div className="flex items-center gap-x-4">
        {allResults.map((result, index) => (
          <SectionWrapper
            key={index}
            title={result.checkName ?? ''}
            className="mt-8 mb-4"
          >
            <div className="flex items-center gap-x-1">
              {result.checkResultEntries?.map((entry, index) => (
                <Tooltip
                  key={index}
                  content={
                    <div className="text-white">
                      <div>Sensor value: {entry.sensorName}</div>
                      {/* <div>
                        Most severe status:{' '}
                        <span className="capitalize">
                          {getStatusLabel(status)}
                        </span>
                      </div> */}
                      <div>
                        Executed at:{' '}
                        {entry.executedAt
                          ? moment(
                              getLocalDateInUserTimeZone(
                                new Date(entry.executedAt)
                              )
                            ).format('YYYY-MM-DD HH:mm:ss')
                          : ''}
                      </div>
                      <div>
                        Time period:{' '}
                        {entry.timePeriod
                          ? moment(
                              getLocalDateInUserTimeZone(
                                new Date(entry.timePeriod)
                              )
                            ).format('YYYY-MM-DD HH:mm:ss')
                          : ''}
                      </div>
                      <div>Data group: {entry.dataGroup}</div>
                    </div>
                  }
                  className="max-w-80 py-2 px-2 bg-gray-800"
                >
                  <div
                    key={index}
                    className={clsx('w-3 h-3', getColor(entry.severity))}
                  />
                </Tooltip>
              ))}
            </div>
          </SectionWrapper>
        ))}
      </div>
    </div>
  );
}

const calculateDateRange = (month: string) => {
  if (!month) return { startDate: '', endDate: '' };

  if (month === 'Last 3 months') {
    return {
      startDate: moment().add(-3, 'month').format('YYYY-MM-DD'),
      endDate: moment().format('YYYY-MM-DD')
    };
  }

  return {
    startDate: moment(month, 'MMMM YYYY').format('YYYY-MM-DD'),
    endDate: moment(month, 'MMMM YYYY').endOf('month').format('YYYY-MM-DD')
  };
};

const getColor = (status: number | undefined) => {
  switch (status) {
    case 0:
      return 'bg-teal-500';
    case 1:
      return 'bg-yellow-900';
    case 2:
      return 'bg-orange-900';
    case 3:
      return 'bg-red-900';
    case 4:
      return 'bg-black';
    default:
      return 'bg-black';
  }
};
