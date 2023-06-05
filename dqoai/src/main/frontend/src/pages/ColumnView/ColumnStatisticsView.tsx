import React, { useEffect, useState } from 'react';
import { ColumnApiClient, TableApiClient } from '../../services/apiClient';
import { useParams } from 'react-router-dom';
import { ColumnStatisticsModel, TableStatisticsModel } from '../../api';
import { CheckTypes } from '../../shared/routes';
import { AxiosResponse } from 'axios';

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
  const formatNumber = (k: number) => {
    if (k < 10 && k / Math.floor(k) !== 1 && k !== 0) {
      return k.toFixed(3);
    } else if (k > 10 && k / Math.floor(k) !== 1 && k < Math.pow(10, 2)) {
      return k.toFixed(2);
    } else if (
      k > Math.pow(10, 2) &&
      k / Math.floor(k) !== 1 &&
      k < Math.pow(10, 3)
    ) {
      return k.toFixed(1);
    } else if (
      k > Math.pow(10, 3) &&
      k / Math.floor(k) !== 1 &&
      k < Math.pow(10, 6)
    ) {
      return k.toFixed(0);
    } else if (k > Math.pow(10, 6) && k < Math.pow(10, 9)) {
      if (k > Math.pow(10, 6) && k < Math.pow(10, 7)) {
        return (k / Math.pow(10, 6)).toFixed(3) + 'M';
      } else if (k > Math.pow(10, 7) && k < Math.pow(10, 8)) {
        return (k / Math.pow(10, 6)).toFixed(2) + 'M';
      } else {
        return (k / Math.pow(10, 6)).toFixed(1) + 'M';
      }
    } else if (k > Math.pow(10, 9) && k < Math.pow(10, 12)) {
      if (k > Math.pow(10, 9) && k < Math.pow(10, 10)) {
        return (k / Math.pow(10, 9)).toFixed(3) + 'G';
      } else if (k > Math.pow(10, 10) && k < Math.pow(10, 11)) {
        return (k / Math.pow(10, 9)).toFixed(2) + 'G';
      } else {
        return (k / Math.pow(10, 9)).toFixed(1) + 'G';
      }
    } else if (k > Math.pow(10, 12) && k < Math.pow(10, 15)) {
      if (k > Math.pow(10, 12) && k < Math.pow(10, 13)) {
        return (k / Math.pow(10, 12)).toFixed(3) + 'T';
      } else if (k > Math.pow(10, 13) && k < Math.pow(10, 14)) {
        return (k / Math.pow(10, 12)).toFixed(2) + 'T';
      } else {
        return (k / Math.pow(10, 12)).toFixed(1) + 'T';
      }
    } else {
      return k;
    }
  };

  const dateToString = (k: string) => {
    const a = k.replace(/T/g, ' ');
    return a;
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
        <div className="text-sm bg-white rounded-lg p-4 border border-gray-200 inline-block w-100">
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
        <div className="text-sm bg-white rounded-lg p-4 border border-gray-200 inline-block w-100">
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-light">Unique count</div>
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
            <div className="ml-2 font-light">Unique percent</div>
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
        <div className="text-sm bg-white rounded-lg p-4 border border-gray-200 inline-block">
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
        <div className="text-sm bg-white rounded-lg p-4 border border-gray-200 inline-block w-100">
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-light">Minimum string length</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2 font-bold" key={index}>
                    {x.collector === 'string_min_length'
                      ? renderValue(x.result)
                      : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-light">Mean string length</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2 font-bold" key={index}>
                    {x.collector === 'string_mean_length'
                      ? Number(renderValue(x.result)).toFixed(2)
                      : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-light">Maximum string length</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2 font-bold" key={index}>
                    {x.collector === 'string_max_length'
                      ? renderValue(x.result)
                      : ''}
                  </div>
                ))}
            </div>
          </div>
        </div>
        <div className="text-sm bg-white rounded-lg p-4 border border-gray-200 inline-block w-100">
          {statistics &&
            statistics.statistics?.map((x, index) =>
              x.category === 'sampling' ? (
                <div
                  key={index}
                  className="h-10 flex justify-between items-center gap-x-36"
                >
                  <div className="ml-2 font-light">
                    Result{' '}
                    {renderValue(x.result) !== ''
                      ? renderValue(x.result)
                      : `""`}
                  </div>
                  <div>Sample Count {x.sampleCount}</div>
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
