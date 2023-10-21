import React, { useEffect, useState } from 'react';
import { ColumnApiClient, TableApiClient } from '../../services/apiClient';
import { useParams } from 'react-router-dom';
import { ColumnStatisticsModel, StatisticsMetricModel, TableStatisticsModel } from '../../api';
import { CheckTypes } from '../../shared/routes';
import { AxiosResponse } from 'axios';
import { formatNumber } from '../../shared/constants';
import moment from 'moment';
import SectionWrapper from '../../components/Dashboard/SectionWrapper';
import { getDetectedDatatype } from '../../utils';

type TColumnStatistics = {
    type?: string,
    result?: string,
    sampleCount?: number
}   

const ColumnStatisticsView = () => {
  const {
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
  const [statistics, setStatistics] = useState<ColumnStatisticsModel>();
  const [rowCount, setRowCount] = useState<TableStatisticsModel>();
  const [columnStatistics, setColumnStatistics] = useState<Record<string, TColumnStatistics[]>>({})

  const fetchColumns = async () => {
    try {
      const res: AxiosResponse<ColumnStatisticsModel> =
        await ColumnApiClient.getColumnStatistics(
          connection,
          schema,
          table,
          column
        );
      setStatistics(res.data);
    } catch (err) {
      console.error(err);
    }
  };
  const fetchRows = async () => {
    try {
      const res: AxiosResponse<TableStatisticsModel> =
        await TableApiClient.getTableStatistics(connection, schema, table);
      setRowCount(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchColumns();
    fetchRows();
  }, [connection, schema, table, column]);

  const renderValue = (value: any) => {
    if (typeof value === 'boolean') {
      return value ? 'Yes' : 'No';
    }
    if (typeof value === 'object') {
      return value.toString();
    }
    return value;
  };

  const renderCategory = (value: string) => {
    if (value.toLowerCase() === 'sampling') {
      return "Top most common values"
    } else if (value.toLowerCase() === 'strings') {
      return "String length"
    } 
    return value.replace(/_/g, " ")
                .replace(/^\w/g, c => c.toUpperCase())
  }

  const renderKey = (value: TColumnStatistics) => {
    if (value.sampleCount) {
      return value.result
    }
      return value.type?.replace(/_/g, " ")
                        .replace(/^\w/g, c => c.toUpperCase())
  }

  const renderColumnStatisticsValue = (value : TColumnStatistics) => {
    if (value.type?.toLowerCase().includes("percent")) {
      return Number(value.result).toFixed(2) + "%"
    } else if (value.sampleCount) {
      return value.sampleCount  
    }
    return value.result
  }

  
  const getColumnStatisticsModel = (fetchedColumnsStatistics: ColumnStatisticsModel) => {
    const column_statistics_dictionary: Record<string, TColumnStatistics[]> = {}
    fetchedColumnsStatistics.statistics?.flatMap((item: StatisticsMetricModel) => {
      if (Object.keys(column_statistics_dictionary).find((x) => x === String(item.category))) {
        column_statistics_dictionary[String(item.category)].push({type: item.collector, result: renderValue(item.result), sampleCount: item.sampleCount})
      } else {
        column_statistics_dictionary[String(item.category)] = [{type: item.collector, result: renderValue(item.result), sampleCount: item.sampleCount}]
      }
    })
    setColumnStatistics(column_statistics_dictionary)
  }
  console.log(columnStatistics)
  const renderSampleIndicator = (value: number) : React.JSX.Element => {
    const nullCount = columnStatistics["nulls"].find((x) => x.type === 'not_nulls_count')?.result
    return (
      <div className=" h-3 border border-gray-100 flex ml-5 w-[100px]">
          <div
            className="h-3 bg-green-700 gap-x-5"
            style={{
              width: `${
                   (value * 100) /
                    Number(renderValue(nullCount))
              }px`
            }}
          ></div>  
      </div>
    )}

  return (
    <div className="p-4">
      <div className="flex w-full h-15">
        <div className="w-1/4 flex font-light ml-5" onClick={() => getColumnStatisticsModel(statistics ?? {})}>
          Datatype{' '}
          <div className="font-bold ml-5">
            {statistics?.type_snapshot?.column_type}
          </div>
        </div>
        <div className="w-1/3 flex font-light">
          Detected Datatype
          <div className="font-bold ml-5">
            {statistics &&
            statistics?.statistics?.filter(
              (x) => x.collector === 'string_datatype_detect'
            ).length === 0 ? (
              <div className="mr-2 font-bold ">No datatype detected</div>
            ) : (
              statistics?.statistics?.map((x, index) => (
                <div className="mr-2 font-bold" key={index}>
                  {x.collector === 'string_datatype_detect'
                    ? getDetectedDatatype(x.result)
                    : ''}
                </div>
              ))
            )}
          </div>
        </div>
        <div className="w-1/5 flex font-light">
          Total Rows
          <div className="font-bold ml-5">
            {rowCount &&
              rowCount.statistics?.map((x, index) => (
                <div key={index}>
                  {x.collector === 'row_count' && x.category === 'volume'
                    ? renderValue(x.result)
                    : ''}
                </div>
              ))}
          </div>
        </div>
        <div className="w-1/4 flex font-light">
          Collected at
          <div className="font-bold ml-5">
            {statistics?.statistics?.at(0)?.collectedAt &&
              moment(statistics?.statistics?.at(0)?.collectedAt).format(
                'YYYY-MM-DD HH:mm:ss'
              )}
          </div>
        </div>
      </div>

      <div className="w-full flex gap-8 flex-wrap">
        {Object.keys(columnStatistics).map((column, index) => 
        <SectionWrapper key={index} title={renderCategory(column)}
        className="text-sm bg-white rounded-lg p-4 border border-gray-200 min-w-100">
          {columnStatistics[column].map((item, jIndex) => 
          <div key={jIndex} className="h-10 flex justify-between items-center">
            <div className="ml-2 font-light">{renderKey(item)}</div>
            <div className="mr-2 font-bold flex items-center">{renderColumnStatisticsValue(item)} 
            {item.sampleCount ? renderSampleIndicator(item.sampleCount): null}
            </div>
          </div>)}
        </SectionWrapper>)}
        {/* <SectionWrapper title='Nulls' className="text-sm bg-white rounded-lg p-4 border border-gray-200 h-50 w-100">
          <div className="h-10 flex justify-between items-center">
            <div className="ml-2 font-light">Nulls count</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2 font-bold" key={index}>
                    {x.collector === 'nulls_count'
                      ? formatNumber(Number(renderValue(x.result)))
                      : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center ">
            <div className="ml-2 font-light">Nulls percent</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2 font-bold" key={index}>
                    {x.collector === 'nulls_percent'
                      ? (Number(renderValue(x.result)) ===
                        Math.floor(Number(renderValue(x.result)))
                          ? Number(renderValue(x.result))
                          : Number(renderValue(x.result)).toFixed(2)) + '%'
                      : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center">
            <div className="ml-2 font-light">Not-null</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2 font-bold" key={index}>
                    {x.collector === 'not_nulls_count'
                      ? formatNumber(Number(renderValue(x.result)))
                      : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center">
            <div className="ml-2 font-light">Not-null percent</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2 font-bold" key={index}>
                    {x.collector === 'not_nulls_percent'
                      ? (Number(renderValue(x.result)) ===
                        Math.floor(Number(renderValue(x.result)))
                          ? Number(renderValue(x.result))
                          : Number(renderValue(x.result)).toFixed(2)) + '%'
                      : ''}
                  </div>
                ))}
            </div>
          </div>
        </SectionWrapper>
        <SectionWrapper title='Uniqueness' className="text-sm bg-white rounded-lg p-4 border border-gray-200 h-50 w-100">
          <div className="h-10 flex justify-between items-center">
            <div className="ml-2 font-light">Distinct count</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2 font-bold" key={index}>
                    {x.collector === 'distinct_count'
                      ? formatNumber(Number(renderValue(x.result)))
                      : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center ">
            <div className="ml-2 font-light">Distinct percent</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2 font-bold" key={index}>
                    {x.collector === 'distinct_percent'
                      ? (Number(renderValue(x.result)) ===
                        Math.floor(Number(renderValue(x.result)))
                          ? Number(renderValue(x.result))
                          : Number(renderValue(x.result)).toFixed(2)) + '%'
                      : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center">
            <div className="ml-2 font-light">Duplicate count</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2 font-bold" key={index}>
                    {x.collector === 'duplicate_count'
                      ? formatNumber(Number(renderValue(x.result)))
                      : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center ">
            <div className="ml-2 font-light">Duplicate percent</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2 font-bold" key={index}>
                    {x.collector === 'duplicate_percent'
                      ? (Number(renderValue(x.result)) ===
                        Math.floor(Number(renderValue(x.result)))
                          ? Number(renderValue(x.result))
                          : Number(renderValue(x.result)).toFixed(2)) + '%'
                      : ''}
                  </div>
                ))}
            </div>
          </div>
        </SectionWrapper>
        <SectionWrapper title='Range' className="text-sm bg-white rounded-lg p-4 border border-gray-200 h-50">
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-light">Minimum</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2 font-bold" key={index}>
                    {x.collector === 'min_value' ? renderValue(x.result) : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-light ">Median</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2 font-bold" key={index}>
                    {x.collector === 'median_value'
                      ? !isNaN(Number(renderValue(x.result)))
                        ? formatNumber(renderValue(x.result))
                        : renderValue(x.result)
                      : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-light ">Maximum</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2 font-bold" key={index}>
                    {x.collector === 'max_value'
                      ? !isNaN(Number(renderValue(x.result)))
                        ? formatNumber(renderValue(x.result))
                        : renderValue(x.result)
                      : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-light">Sum</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2 font-bold" key={index}>
                    {x.collector === 'sum_value'
                      ? !isNaN(Number(renderValue(x.result)))
                        ? formatNumber(renderValue(x.result))
                        : renderValue(x.result)
                      : ''}
                  </div>
                ))}
            </div>
          </div>
        </SectionWrapper>
        <SectionWrapper title='String length' className="text-sm bg-white rounded-lg p-4 border border-gray-200 h-50 w-100">
          <div className="h-10 flex justify-between items-center">
            <div className="ml-2 font-light">Minimum string length</div>
            <div>
              {statistics &&
              statistics?.statistics?.filter(
                (x) => x.collector === 'string_min_length'
              ).length === 0 ? (
                <div className="mr-2 font-bold">Not a string data type</div>
              ) : (
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2 font-bold" key={index}>
                    {x.collector === 'string_min_length'
                      ? renderValue(x.result)
                      : ''}
                  </div>
                ))
              )}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center">
            <div className="ml-2 font-light">Mean string length</div>
            <div>
              {statistics &&
              statistics?.statistics?.filter(
                (x) => x.collector === 'string_mean_length'
              ).length === 0 ? (
                <div className="mr-2 font-bold">Not a string data type</div>
              ) : (
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2 font-bold" key={index}>
                    {x.collector === 'string_mean_length'
                      ? Number(renderValue(x.result)).toFixed(2)
                      : ''}
                  </div>
                ))
              )}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center">
            <div className="ml-2 font-light">Maximum string length</div>
            <div>
              {statistics &&
              statistics?.statistics?.filter(
                (x) => x.collector === 'string_max_length'
              ).length === 0 ? (
                <div className="mr-2 font-bold">Not a string data type</div>
              ) : (
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2 font-bold" key={index}>
                    {x.collector === 'string_max_length'
                      ? renderValue(x.result)
                      : ''}
                  </div>
                ))
              )}
            </div>
          </div>
        </SectionWrapper>
        {statistics?.statistics?.find((x) => x.category === 'sampling') ? (
          <SectionWrapper title='Top 10 most common values' className="text-sm bg-white rounded-lg p-4 border border-gray-200">
            {statistics &&
              statistics.statistics?.map((x, index) =>
                x.category === 'sampling' ? (
                  <div key={index} className="h-10 flex items-center gap-x-5">
                    <div className="flex gap-x-5 w-50">
                      <div className="ml-2 font-light overflow-hidden whitespace-nowrap overflow-ellipsis">
                        {renderValue(x.result) !== ''
                          ? renderValue(x.result)
                          : `""`}
                      </div>
                    </div>
                    <div className="w-8">
                      {formatNumber(Number(x.sampleCount))}
                    </div>
                    <div
                      className=" h-3 border border-gray-100 flex ml-5"
                      style={{ width: '100px' }}
                    >
                      {statistics.statistics?.map((y) =>
                        y.collector === 'not_nulls_count' ? (
                          <div
                            key={index}
                            className="h-3 bg-green-700 gap-x-5"
                            style={{
                              width: `${
                                x.sampleCount !== null
                                  ? (Number(renderValue(x.sampleCount)) * 100) /
                                    Number(renderValue(y.result))
                                  : 0
                              }px`
                            }}
                          ></div>
                        ) : (
                          ''
                        )
                      )}
                    </div>
                  </div>
                ) : (
                  <></>
                )
              )}
          </SectionWrapper>
        ) : (
          <></>
        )} */}
      </div>
    </div>
  );
};
export default ColumnStatisticsView;
