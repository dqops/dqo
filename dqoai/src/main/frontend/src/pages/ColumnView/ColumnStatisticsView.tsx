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

  return (
    <div className="p-4">
      <div className="flex w-full text-xl h-15">
        <div className="w-1/5 flex font-bold ml-5">
          Datatype{' '}
          <div className="font-light ml-5">
            {statistics?.type_snapshot?.column_type}
          </div>
        </div>
        <div className="w-1/4 flex font-bold">
          Detected Datatype
          <div className="font-light ml-5">
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
        <div className="w-1/5 flex font-bold">
          Total Rows
          <div className="font-light ml-5">
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
        <div className="w-1/4 flex font-bold">
          Collected at
          <div className="font-light ml-5">
            {statistics?.statistics?.at(0)?.collectedAt}
          </div>
        </div>
      </div>

      <div className="w-full flex gap-8 flex-wrap">
        <div className="border border-gray-400 inline-block">
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-bold">Null count</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2" key={index}>
                    {x.collector === 'nulls_count' ? renderValue(x.result) : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-bold">Null percent</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2" key={index}>
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
            <div className="ml-2 font-bold">Not-null</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2" key={index}>
                    {x.collector === 'not_nulls_count'
                      ? renderValue(x.result)
                      : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-bold">Not-null percent</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2" key={index}>
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
        <div className="border border-gray-400 inline-block">
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-bold">Unique count</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2" key={index}>
                    {x.collector === 'unique_count'
                      ? renderValue(x.result)
                      : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-bold">Unique percent</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2" key={index}>
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
            <div className="ml-2 font-bold">Duplicate count</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2" key={index}>
                    {x.collector === 'duplicate_count'
                      ? renderValue(x.result)
                      : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-bold">Duplicate percent</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2" key={index}>
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
        <div className="border border-gray-400 inline-block ">
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-bold">Minimum</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2" key={index}>
                    {x.collector === 'min_value' ? renderValue(x.result) : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-bold">Median</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2" key={index}>
                    {x.collector === 'median_value'
                      ? renderValue(x.result)
                      : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-bold">Maximum</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2" key={index}>
                    {x.collector === 'max_value' ? renderValue(x.result) : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-bold">Sum</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2" key={index}>
                    {x.collector === 'sum_value' ? renderValue(x.result) : ''}
                  </div>
                ))}
            </div>
          </div>
        </div>
        <div className="border border-gray-400 inline-block">
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-bold">Maximum string length</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2" key={index}>
                    {x.collector === 'string_max_length'
                      ? renderValue(x.result)
                      : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-bold">Mean string length</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2" key={index}>
                    {x.collector === 'string_mean_length'
                      ? renderValue(x.result)
                      : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center gap-x-36">
            <div className="ml-2 font-bold">Minimum string length</div>
            <div>
              {statistics &&
                statistics?.statistics?.map((x, index) => (
                  <div className="mr-2" key={index}>
                    {x.collector === 'string_min_length'
                      ? renderValue(x.result)
                      : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="h-10 flex justify-between items-center">
            <div className="ml-2"></div>
            <div className="mr-2"></div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default ColumnStatisticsView;
