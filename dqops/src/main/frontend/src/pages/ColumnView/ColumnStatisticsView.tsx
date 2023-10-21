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

type TStatistics = {
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
  const [columnStatistics, setColumnStatistics] = useState<Record<string, TStatistics[]>>({})
 
  const tableStatistics : TStatistics[] = []

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
      getTableStatisticsModel(res.data)
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

  const renderKey = (value: TStatistics) => {
    if (value.sampleCount) {
      return value.result
    }
      return value.type?.replace(/_/g, " ")
                        .replace(/^\w/g, c => c.toUpperCase())
  }

  const renderColumnStatisticsValue = (value : TStatistics) => {
    if (value.type?.toLowerCase().includes("percent")) {
      return Number(value.result).toFixed(2) + "%"
    } else if (value.sampleCount) {
      return value.sampleCount  
    }
    return value.result
  }

  
  const getColumnStatisticsModel = (fetchedColumnsStatistics: ColumnStatisticsModel) => {
    const column_statistics_dictionary: Record<string, TStatistics[]> = {}
    fetchedColumnsStatistics.statistics?.flatMap((item: StatisticsMetricModel) => {
      if (item.collector !== "string_datatype_detect") {
        if (Object.keys(column_statistics_dictionary).find((x) => x === String(item.category))) {
            column_statistics_dictionary[String(item.category)].push({type: item.collector, result: renderValue(item.result), sampleCount: item.sampleCount})
          } else {
            column_statistics_dictionary[String(item.category)] = [{type: item.collector, result: renderValue(item.result), sampleCount: item.sampleCount}]
          }
      } else {
        tableStatistics.push({type: "Detected Datatype", result: getDetectedDatatype(item.result)})
      }
    })
    setColumnStatistics(column_statistics_dictionary)
    
    tableStatistics.push({type: "Datatype", result: String(fetchedColumnsStatistics.type_snapshot)})
    tableStatistics.push({type: "Collected at", result: fetchedColumnsStatistics.statistics?.at(0)?.collectedAt})
  
  }

    const getTableStatisticsModel = (fetchedTableStatistics: TableStatisticsModel) => {
      tableStatistics.push({type: "Row count", result: String(fetchedTableStatistics.statistics?.find((item) => item.collector === "row_count")?.result)})
    }

    
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
    )
  }
  console.log(columnStatistics)
  console.log(tableStatistics)

  return (
    <div className="p-4" onClick={() => getColumnStatisticsModel(statistics ?? {})}>
      <div className="flex w-full h-15">
        {tableStatistics.map((x, index) => 
        <div className="w-1/4 flex font-light ml-5" key={index}>
          {x.type}
          <div className="font-bold ml-5">
            {x.result}
          </div>
        </div>
        )}
        {/* <div className="w-1/4 flex font-light ml-5" onClick={() => getColumnStatisticsModel(statistics ?? {})}>
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
        </div> */}
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
      </div>
    </div>
  );
};
export default ColumnStatisticsView;
