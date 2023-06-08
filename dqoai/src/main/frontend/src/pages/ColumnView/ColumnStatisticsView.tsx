import React, { useEffect, useState } from 'react';
import { ColumnApiClient, TableApiClient } from '../../services/apiClient';
import { useParams } from 'react-router-dom';
import { ColumnStatisticsModel, TableStatisticsModel } from '../../api';
import { CheckTypes } from '../../shared/routes';
import { AxiosResponse } from 'axios';
import { formatNumber, dateToString } from '../../shared/constants';

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

  const datatype_detected = (numberForFile: any) => {
    if (Number(numberForFile) === 1) {
      return 'INTEGER';
    }
    if (Number(numberForFile) === 2) {
      return 'FLOAT';
    }
    if (Number(numberForFile) === 3) {
      return 'DATETIME';
    }
    if (Number(numberForFile) === 4) {
      return 'TIMESTAMP';
    }
    if (Number(numberForFile) === 5) {
      return 'BOOLEAN';
    }
    if (Number(numberForFile) === 6) {
      return 'STRING';
    }
    if (Number(numberForFile) === 7) {
      return 'Mixed data type';
    }
  };

  const renderValue = (value: any) => {
    if (typeof value === 'boolean') {
      return value ? 'Yes' : 'No';
    }
    if (typeof value === 'object') {
      return value.toString();
    }
    return value;
  };

  return (
    <div className="p-4">
      <div className="flex w-full h-15">
        <div className="w-1/5 flex font-light ml-5">
          Datatype{' '}
          <div className="font-bold ml-5">
            {statistics?.type_snapshot?.column_type}
          </div>
        </div>
        <div className="w-1/4 flex font-light">
          Detected Datatype
          <div className="font-bold ml-5">
            {statistics &&
              statistics?.statistics?.map((x, index) => (
                <div className="mr-2" key={index}>
                  {x.collector === 'string_datatype_detect'
                    ? datatype_detected(x.result)
                    : ''}
                </div>
              ))}
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
              dateToString(
                renderValue(statistics?.statistics?.at(0)?.collectedAt)
              )}
          </div>
        </div>
      </div>

      <div className="w-full flex gap-8 flex-wrap">
        <div className="text-sm bg-white rounded-lg p-4 border border-gray-200 h-50 w-100">
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-light">Null count</div>
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
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-light">Null percent</div>
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
          <div className="h-10 flex justify-between items-center gap-x-36">
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
          <div className="h-10 flex justify-between items-center gap-x-36">
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
        </div>
        <div className="text-sm bg-white rounded-lg p-4 border border-gray-200 h-50 w-100">
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-light">Distinct count</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2 font-bold" key={index}>
                    {x.collector === 'unique_count'
                      ? formatNumber(Number(renderValue(x.result)))
                      : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-light">Distinct percent</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2 font-bold" key={index}>
                    {x.collector === 'unique_percent'
                      ? (Number(renderValue(x.result)) ===
                        Math.floor(Number(renderValue(x.result)))
                          ? Number(renderValue(x.result))
                          : Number(renderValue(x.result)).toFixed(2)) + '%'
                      : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center gap-x-36">
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
          <div className="h-10 flex justify-between items-center gap-x-36">
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
        </div>
        <div className="text-sm bg-white rounded-lg p-4 border border-gray-200 h-50">
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-light font-bold">Minimum</div>
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
            <div className="ml-2 font-light font-bold">Median</div>
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
        </div>
        <div className="text-sm bg-white rounded-lg p-4 border border-gray-200 h-50 w-100">
          <div className="h-10 flex justify-between items-center">
            <div className="ml-2 font-light">Minimum string length</div>
            <div>
              {statistics &&
              statistics?.statistics?.filter(
                (x) => x.collector === 'string_min_length'
              ).length === 0 ? (
                <div className="mr-2 font-bold">No string data type</div>
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
                <div className="mr-2 font-bold">No string data type</div>
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
                <div className="mr-2 font-bold">No string data type</div>
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
        </div>
        <div className="text-sm bg-white rounded-lg p-4 border border-gray-200">
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
        </div>
      </div>
    </div>
  );
};
export default ColumnStatisticsView;
