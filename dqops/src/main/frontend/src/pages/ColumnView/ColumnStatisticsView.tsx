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
import { isDate, isInteger } from 'lodash';

type TStatistics = {
    type?: string,
    result?: string,
    sampleCount?: number
}   

const initColumnStatisticsObject : Record<string,  TStatistics[]> = {
  "Nulls" : [{type: "Nulls count"}, {type: "Nulls percent"}, {type: "Not nulls count"}, {type: "Not nulls percent"}],
  "Uniqueness" : [{type: "Distinct count"}, {type: "Distinct percent"}, {type: "Duplicate count"}, {type: "Duplicate percent"}],
  "Range" : [{type: "Min value"}, {type: "Max value"}, {type: "Median value"}, {type: "Sum value"}],
  "strings" : [{type: "String min length"}, {type: "String max length"}, {type: "String mean length"}],
  "Top most common values" : [],
}

const ColumnStatisticsView = ({statisticsCollectedIndicator} : {statisticsCollectedIndicator?: boolean}) => {
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
  const [columnStatistics, setColumnStatistics] = useState<Record<string, TStatistics[]>>(initColumnStatisticsObject);
  const [tableStatistics, setTableStatistics] = useState<TStatistics[]>([]);
  const [rowCount, setRowCount] = useState<number>()

  useEffect(() => {
    const fetchStatistics = async () => {
      try {
        const res: AxiosResponse<ColumnStatisticsModel> = await ColumnApiClient.getColumnStatistics(
          connection,
          schema,
          table,
          column
        );
        if (res.data.statistics && res.data.statistics?.length > 0) {
          getColumnStatisticsModel(res.data);
        } else { 
          setColumnStatistics({
            "Nulls" : [{type: "Nulls count"}, {type: "Nulls percent"}, {type: "Not nulls count"}, {type: "Not nulls percent"}],
            "Uniqueness" : [{type: "Distinct count"}, {type: "Distinct percent"}, {type: "Duplicate count"}, {type: "Duplicate percent"}],
            "Range" : [{type: "Min value"}, {type: "Max value"}, {type: "Median value"}, {type: "Sum value"}],
            "strings" : [{type: "String min length"}, {type: "String max length"}, {type: "String mean length"}],
            "Top most common values" : [],
          });
        }
      } catch (err) {
        console.error(err);
      }
    };

    const fetchRowStatistics = async () => {
      try {
        const res: AxiosResponse<TableStatisticsModel> =
          await TableApiClient.getTableStatistics(connection, schema, table);
        getTableStatisticsModel(res.data)
      } catch (err) {
        console.error(err);
      }
    };

    fetchStatistics();
    fetchRowStatistics();
  }, [connection, schema, table, column, statisticsCollectedIndicator, columnStatistics]);


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
    if (value.sampleCount) {
      return value.sampleCount  
    } 
      if(!isNaN(Number(value.result))){
        if (value.type?.toLowerCase().includes("percent")) {
          if (isInteger(Number(value.result))) {
            return value.result + "%"
          } else {
            return Number(value.result).toFixed(2) + "%"
          }
        } else if (!isNaN(Number(value.result))) {
          if (isInteger(Number(value.result))) {
            return formatNumber(Number(value.result))
          } else {
            return formatNumber(Number(Number(value.result).toFixed(2)))
          } 
        }
    } else if (isDate(value.result)) {
      moment(value.result).format('YYYY-MM-DD HH:mm:ss')
    }
    return value.result
  }

  const getColumnStatisticsModel = (fetchedColumnsStatistics: ColumnStatisticsModel) => {
    const column_statistics_dictionary: Record<string, TStatistics[]> = initColumnStatisticsObject
    const table_statistics_array : TStatistics[] = []
    if(fetchedColumnsStatistics.statistics && fetchedColumnsStatistics?.statistics.length > 0) {
      fetchedColumnsStatistics.statistics?.flatMap((item: StatisticsMetricModel) => {
        if (item.collector !== "string_datatype_detect") {
          const key = Object.keys(column_statistics_dictionary).find((x) => x.toLowerCase() === String(item.category))
          if (key) {
              if(column_statistics_dictionary[key].find((y) => y.type?.toLowerCase().replace(/\s/g, "_") === item.collector)){
                const foundObject = column_statistics_dictionary[key].find((y) => y.type?.toLowerCase().replace(/\s/g, "_") === item.collector);
                if (foundObject) {
                  foundObject.result = String(item.result);
                  foundObject.sampleCount = item.sampleCount;
                }
              } 
          }   
          if(item.category === 'sampling'){
            column_statistics_dictionary["Top most common values"].push({type: item.collector, result: String(item.result), sampleCount: item.sampleCount})
          }     
        } else {
          table_statistics_array.push({type: "Detected Datatype:", result: getDetectedDatatype(item.result)})
        }
      })
      table_statistics_array.push({type: "Datatype:", 
      result: String(fetchedColumnsStatistics.type_snapshot?.column_type)})
      
      table_statistics_array.push({type: "Collected at:", 
      result: moment(fetchedColumnsStatistics.statistics?.at(0)?.collectedAt).format('YYYY-MM-DD HH:mm:ss')})
      setColumnStatistics(column_statistics_dictionary)  
      setTableStatistics(table_statistics_array)
    }
  }

  const getTableStatisticsModel = (fetchedTableStatistics: TableStatisticsModel) => {
    if (!isNaN(Number(fetchedTableStatistics.statistics?.find((item) => item.collector === "row_count")?.result))) {
      setRowCount(Number(fetchedTableStatistics.statistics?.find((item) => item.collector === "row_count")?.result))
    }
  }
 
  const renderSampleIndicator = (value: number) : React.JSX.Element => {
    const nullCount = columnStatistics["Nulls"].find((x) => x.type === 'Not nulls count')?.result
    return (
      <div className=" h-3 border border-gray-100 flex ml-5 w-[100px]">
          <div
            className="h-3 bg-green-700 gap-x-5"
            style={{
              width: `${
                (value * 100) /
                    Number(nullCount)
              }px`
            }}
            ></div>  
      </div>
    )
  }

  return (
    <div className="p-4">
      <div className="flex w-full h-15">
        {[...tableStatistics, rowCount ? { type: "Row count", result: rowCount} : {}].map((x, index) => 
        <div className="flex font-light ml-5 mr-20" key={index}>
          {x.type}
          <div className="font-bold ml-5">
            {x.result}
          </div>
        </div>
        )}
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
