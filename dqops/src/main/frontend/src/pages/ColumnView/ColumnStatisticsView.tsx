import { isDate, isInteger } from 'lodash';
import moment from 'moment';
import React, { useEffect, useState } from 'react';
import {
  ColumnStatisticsModel,
  StatisticsMetricModel,
  TableStatisticsModel
} from '../../api';
import SectionWrapper from '../../components/Dashboard/SectionWrapper';
import { formatNumber } from '../../shared/constants';
import { getDetectedDatatype } from '../../utils';

type TStatistics = {
  type?: string;
  result?: string;
  sampleCount?: number;
};

type TColumnStatisticsProps = {
  columnStatisticsProp?: ColumnStatisticsModel;
  tableStatisticsProp?: TableStatisticsModel;
};

const defaultColumnStatistics: Record<string, TStatistics[]> = {
  Nulls: [
    { type: 'Nulls count' },
    { type: 'Nulls percent' },
    { type: 'Not nulls count' },
    { type: 'Not nulls percent' }
  ],
  Uniqueness: [
    { type: 'Distinct count' },
    { type: 'Distinct percent' },
    { type: 'Duplicate count' },
    { type: 'Duplicate percent' }
  ],
  Range: [
    { type: 'Min value' },
    { type: 'Max value' },
    { type: 'Median value' },
    { type: 'Sum value' },
    { type: 'Mean value' }
  ],
  Text: [
    { type: 'Text min length' },
    { type: 'Text max length' },
    { type: 'Text mean length' },
    { type: 'Text min word count' },
    { type: 'Text max word count' }
  ],
  'Top most common values': Array(0)
};

const ColumnStatisticsView = ({
  columnStatisticsProp,
  tableStatisticsProp
}: TColumnStatisticsProps) => {
  const [columnStatistics, setColumnStatistics] = useState<
    Record<string, TStatistics[]>
  >(defaultColumnStatistics);
  const [tableStatistics, setTableStatistics] = useState<TStatistics[]>([]);
  const [rowCount, setRowCount] = useState<number>();

  useEffect(() => {
    if (columnStatisticsProp) {
      getColumnStatisticsModel(columnStatisticsProp);
    }
    if (tableStatisticsProp) {
      getTableStatisticsModel(tableStatisticsProp);
    }
  }, [columnStatisticsProp, tableStatisticsProp]);

  const renderCategory = (value: string) => {
    if (value.toLowerCase() === 'sampling') {
      return 'Top most common values';
    } else if (value.toLowerCase() === 'text') {
      return 'Text length';
    }
    return value.replace(/_/g, ' ').replace(/^\w/g, (c) => c.toUpperCase());
  };

  const renderKey = (value: TStatistics) => {
    if (value.sampleCount) {
      return value.result;
    }
    return value.type
      ?.replace(/_/g, ' ')
      .replace(/^\w/g, (c) => c.toUpperCase());
  };

  const renderColumnStatisticsValue = (value: TStatistics) => {
    if (value.sampleCount) {
      return value.sampleCount;
    }
    if (!isNaN(Number(value.result))) {
      if (value.type?.toLowerCase().includes('percent')) {
        if (isInteger(Number(value.result))) {
          return value.result + '%';
        } else {
          return Number(value.result).toFixed(2) + '%';
        }
      } else if (!isNaN(Number(value.result))) {
        if (isInteger(Number(value.result))) {
          return formatNumber(Number(value.result));
        } else {
          return formatNumber(Number(Number(value.result).toFixed(2)));
        }
      }
    } else if (isDate(value.result)) {
      moment(value.result).format('YYYY-MM-DD HH:mm:ss');
    }
    return value.result;
  };

  const getColumnStatisticsModel = (
    fetchedColumnsStatistics: ColumnStatisticsModel
  ) => {
    const column_statistics_dictionary: Record<string, TStatistics[]> = {
      Nulls: [
        { type: 'Nulls count' },
        { type: 'Nulls percent' },
        { type: 'Not nulls count' },
        { type: 'Not nulls percent' }
      ],
      Uniqueness: [
        { type: 'Distinct count' },
        { type: 'Distinct percent' },
        { type: 'Duplicate count' },
        { type: 'Duplicate percent' }
      ],
      Range: [
        { type: 'Min value' },
        { type: 'Max value' },
        { type: 'Median value' },
        { type: 'Sum value' },
        { type: 'Mean value' }
      ],
      Text: [
        { type: 'Text min length' },
        { type: 'Text max length' },
        { type: 'Text mean length' },
        { type: 'Text min word count' },
        { type: 'Text max word count' }
      ],
      'Top most common values': Array(0)
    };
    const table_statistics_array: TStatistics[] = [];
    if (fetchedColumnsStatistics.statistics) {
      fetchedColumnsStatistics.statistics?.flatMap(
        (item: StatisticsMetricModel) => {
          if (item.collector !== 'text_datatype_detect') {
            const key = Object.keys(column_statistics_dictionary).find(
              (x) => x.toLowerCase() === String(item.category)
            );
            if (key) {
              if (
                column_statistics_dictionary[key].find(
                  (y) =>
                    y.type?.toLowerCase().replace(/\s/g, '_') === item.collector
                )
              ) {
                const foundObject = column_statistics_dictionary[key].find(
                  (y) =>
                    y.type?.toLowerCase().replace(/\s/g, '_') === item.collector
                );
                if (foundObject) {
                  foundObject.result = String(item.result);
                  foundObject.sampleCount = item.sampleCount;
                }
              }
            }
            if (item.category === 'sampling') {
              column_statistics_dictionary['Top most common values'].push({
                type: item.collector,
                result: String(item.result),
                sampleCount: item.sampleCount
              });
            }
          } else {
            table_statistics_array.push({
              type: 'Detected data type',
              result: getDetectedDatatype(item.result)
            });
          }
        }
      );
      table_statistics_array.push({
        type: 'Data type',
        result: String(fetchedColumnsStatistics.type_snapshot?.column_type)
      });

      table_statistics_array.push({
        type: 'Collected at',
        result: moment(
          fetchedColumnsStatistics.statistics?.at(0)?.collectedAt
        ).format('YYYY-MM-DD HH:mm:ss')
      });
      setColumnStatistics(column_statistics_dictionary);
      setTableStatistics(table_statistics_array);
    }
  };

  const getTableStatisticsModel = (
    fetchedTableStatistics: TableStatisticsModel
  ) => {
    if (
      !isNaN(
        Number(
          fetchedTableStatistics.statistics?.find(
            (item) => item.collector === 'row_count'
          )?.result
        )
      )
    ) {
      setRowCount(
        Number(
          fetchedTableStatistics.statistics?.find(
            (item) => item.collector === 'row_count'
          )?.result
        )
      );
    }
  };

  const renderSampleIndicator = (value: number): React.JSX.Element => {
    const nullCount = columnStatistics['Nulls'].find(
      (x) => x.type === 'Not nulls count'
    )?.result;
    return (
      <div className=" h-3 border border-gray-100 flex ml-5 w-[100px]">
        <div
          className="h-3 bg-green-700 gap-x-5"
          style={{
            width: `${(value * 100) / Number(nullCount)}px`
          }}
        ></div>
      </div>
    );
  };

  return (
    <div className="p-4 text-sm overflow-auto">
      <div className="flex w-full h-15">
        {[
          ...tableStatistics,
          rowCount ? { type: 'Row count', result: rowCount } : {}
        ].map((x, index) => (
          <div className="flex font-light ml-5 mr-20" key={index}>
            {x.type}
            <div className="font-bold ml-5">{x.result}</div>
          </div>
        ))}
      </div>
      <div className="w-full flex gap-8 flex-wrap text-sm">
        {Object.keys(columnStatistics).map((column, index) => (
          <SectionWrapper
            key={index}
            title={renderCategory(column)}
            className="text-sm bg-white rounded-lg p-4 border border-gray-200 min-w-100"
            titleClassName="!z-[1]"
          >
            <div className="!max-h-120 overflow-auto">
              {columnStatistics[column].map((item, jIndex) => (
                <div
                  key={jIndex}
                  className="h-10 flex justify-between items-center"
                >
                  <div className="ml-2 font-light">{renderKey(item)}</div>
                  <div className="mr-2 font-bold flex items-center">
                    {renderColumnStatisticsValue(item)}
                    {item.sampleCount
                      ? renderSampleIndicator(item.sampleCount)
                      : null}
                  </div>
                </div>
              ))}
            </div>
          </SectionWrapper>
        ))}
      </div>
    </div>
  );
};
export default ColumnStatisticsView;
