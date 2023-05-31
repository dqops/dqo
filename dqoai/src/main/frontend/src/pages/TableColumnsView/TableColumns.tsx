import React, { ReactNode, useEffect, useState } from 'react';
import { ColumnApiClient, JobApiClient } from '../../services/apiClient';
import { AxiosResponse } from 'axios';
import { ColumnStatisticsModel, TableColumnsStatisticsModel } from '../../api';
import { IconButton } from '@material-tailwind/react';
import SvgIcon from '../../components/SvgIcon';
import ConfirmDialog from './ConfirmDialog';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { useDispatch } from 'react-redux/es/hooks/useDispatch';
import { useParams, useHistory } from 'react-router-dom';
import { addFirstLevelTab } from '../../redux/actions/source.actions';

interface ITableColumnsProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
}

interface MyData {
  null_percent: number | undefined;
  unique_value: number | undefined;
  null_count?: number | undefined;
  nameOfCol?: string | undefined;
  minimalValue?: string | undefined;
  detectedDatatypeVar: number | undefined;
  length?: number | undefined;
  scale?: number | undefined;
  importedDatatype?: string | undefined;
}

const TableColumns = ({
  connectionName,
  schemaName,
  tableName
}: ITableColumnsProps) => {
  const [statistics, setStatistics] = useState<TableColumnsStatisticsModel>();
  const [sortedStatistics, setSortedStatistics] =
    useState<Array<ColumnStatisticsModel>>();
  const [isOpen, setIsOpen] = useState(false);
  const [selectedColumn, setSelectedColumn] = useState<ColumnStatisticsModel>();
  const [loadingJob, setLoadingJob] = useState(false);
  const [workingArr, setWorkingArr] = useState<any>([]);
  const [reverse, setReverse] = useState<boolean>(true);
  const [dataArray1, setDataArray1] = useState<MyData[]>();
  const [sortDirection, setSortDirection] = useState<'asc' | 'desc'>('asc');

  const dispatch = useDispatch();
  const {
    connection,
    schema,
    table
  }: {
    connection: string;
    schema: string;
    table: string;
    tab: string;
    checkTypes: CheckTypes;
  } = useParams();
  const history = useHistory();

  const fetchColumns = async () => {
    try {
      const res: AxiosResponse<TableColumnsStatisticsModel> =
        await ColumnApiClient.getColumnsStatistics(
          connectionName,
          schemaName,
          tableName
        );
      setStatistics(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchColumns().then();
  }, [connectionName, schemaName, tableName]);

  const onRemoveColumn = (column: ColumnStatisticsModel) => {
    setIsOpen(true);
    setSelectedColumn(column);
  };

  const navigate = (column: string) => {
    const url = ROUTES.COLUMN_LEVEL_PAGE(
      'profiling',
      connectionName,
      schemaName,
      tableName,
      column,
      'detail'
    );
    const value = ROUTES.COLUMN_LEVEL_VALUE(
      'profiling',
      connection,
      schema,
      table,
      column
    );
    dispatch(
      addFirstLevelTab(CheckTypes.PROFILING, {
        url,
        value,
        state: {},
        label: table
      })
    );
    history.push(url);
  };

  const removeColumn = async () => {
    if (selectedColumn?.column_name) {
      await ColumnApiClient.deleteColumn(
        connectionName,
        schemaName,
        tableName,
        selectedColumn?.column_name
      );
      await fetchColumns();
    }
  };

  const collectStatistics = async (index: number) => {
    await JobApiClient.collectStatisticsOnDataStreams(
      statistics?.column_statistics?.at(index)
        ?.collect_column_statistics_job_template
    );
  };

  useEffect(() => {
    fetchColumns();
  }, [connectionName, schemaName, tableName]);

  const renderValue = (value: any) => {
    if (typeof value === 'boolean') {
      return value ? 'Yes' : 'No';
    }
    if (typeof value === 'object') {
      return value.toString();
    }
    return value;
  };

  const cutString = (text: string) => {
    if (text.length > 22 && isNaN(Number(text))) {
      return text.slice(0, 22) + '...';
    } else {
      return text;
    }
  };

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

  const max_unique_value = () => {
    const arr: number[] = [];
    statistics?.column_statistics?.map((x) => {
      x.statistics?.map((y) => {
        if (y.collector == 'unique_count') {
          arr.push(Number(y.result));
        }
      });
    });
    let max = 0;

    for (let i = 0; i < arr.length; i++) {
      if (Number(arr.at(i)) > max) {
        max = Number(arr.at(i));
      }
    }

    return max;
  };

  const calculate_color = (uniqueCount: number, maxUniqueCount: number) => {
    if (uniqueCount === 0) {
      return 'rgba(255, 255, 255, 1)';
    }

    if (uniqueCount === maxUniqueCount) {
      return 'rgba(2, 154, 128, 0.1)';
    }

    if (uniqueCount === 1) {
      return 'rgba(2, 154, 128, 0.8)';
    }

    const logarithm = Math.log2(uniqueCount);
    const alpha = (1 - (logarithm / Math.log2(maxUniqueCount)) * 0.9) / 1.3;
    const color = `rgba(2, 154, 128, ${alpha})`;

    return color;
  };

  const formatNumber = (k: number) => {
    if (k > 1000 && k < 1000000) {
      if (k > Math.pow(10, 3) && k < Math.pow(10, 4)) {
        return (k / Math.pow(10, 3)).toFixed(3) + 'k';
      } else if (k > Math.pow(10, 4) && k < Math.pow(10, 5)) {
        return (k / Math.pow(10, 3)).toFixed(2) + 'k';
      } else {
        return (k / Math.pow(10, 3)).toFixed(1) + 'k';
      }
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
  const handleSortChange = (sortBy: string) => {
    switch (sortBy) {
      case 'nulls_percent':
        // setSortedStatistics(sortedStatistics?.forEach((x) => x.statistics))
        break;
      case 'name':
        break;
      case 'unique_count':
        break;
      case 'nulls_count':
      default:
    }
  };

  const sortAlphabetictly = async (reverse?: boolean) => {
    const sorted = dataArray.sort((a, b) =>
      a.nameOfCol && b.nameOfCol
        ? reverse === true
          ? a.nameOfCol.localeCompare(b.nameOfCol)
          : b.nameOfCol.localeCompare(a.nameOfCol)
        : 0
    );
    setDataArray1(sorted);
    console.log(dataArray1);
  };

  const workInProgress = () => {
    statistics?.column_statistics?.forEach((x) =>
      x.statistics?.forEach((a) =>
        a.collector === 'nulls_percent' ? workingArr.push(a.result) : ''
      )
    );
    workingArr.sort((y: any, z: any) => y - z);
    console.log(workingArr);
  };

  const setSortedStatsFunc = () => {
    const sortingArray: (ColumnStatisticsModel[] | undefined)[] = [];
    if (statistics?.column_statistics) {
      for (let i = 0; i < statistics?.column_statistics?.length; i++) {
        statistics.column_statistics.map((x) => {
          x.statistics?.map((y) => {
            if (
              y.collector === 'nulls_percent' &&
              y.result === workingArr.at(i)
            ) {
              sortingArray.push(statistics.column_statistics);
            }
          });
        });
      }
      console.log(sortingArray);
    }
  };

  const nullPercentData = statistics?.column_statistics?.map((x) =>
    x.statistics
      ?.filter((item) => item.collector === 'nulls_percent')
      .map((item) => item.result)
  );
  const uniqueCountData = statistics?.column_statistics?.map((x) =>
    x.statistics
      ?.filter((item) => item.collector === 'unique_count')
      .map((item) => item.result)
  );
  const nullCountData = statistics?.column_statistics?.map((x) =>
    x.statistics
      ?.filter((item) => item.collector === 'nulls_count')
      .map((item) => item.result)
  );
  const detectedDatatypeVar = statistics?.column_statistics?.map((x) =>
    x.statistics
      ?.filter((item) => item.collector === 'string_datatype_detect')
      .map((item) => item.result)
  );

  const columnNameData = statistics?.column_statistics?.map(
    (x) => x.column_name
  );
  const minimalValueData = statistics?.column_statistics?.map((x) =>
    x.statistics
      ?.filter((item) => item.collector === 'min_value')
      .map((item) => item.result)
  );
  const lengthData = statistics?.column_statistics?.map(
    (x) => x.type_snapshot?.length
  );

  const scaleData = statistics?.column_statistics?.map(
    (x) => x.type_snapshot?.scale
  );

  const typeData = statistics?.column_statistics?.map(
    (x) => x.type_snapshot?.column_type
  );

  const dataArray: MyData[] = [];

  if (
    nullPercentData &&
    uniqueCountData &&
    nullCountData &&
    detectedDatatypeVar &&
    columnNameData &&
    minimalValueData &&
    lengthData &&
    scaleData &&
    typeData
  ) {
    const maxLength = Math.max(
      nullPercentData.length,
      uniqueCountData.length,
      nullCountData.length,
      detectedDatatypeVar.length,
      columnNameData.length,
      minimalValueData.length,
      lengthData.length,
      scaleData.length,
      typeData.length
    );

    for (let i = 0; i < maxLength; i++) {
      const nullPercent = nullPercentData[i];
      const uniqueValue = uniqueCountData[i];
      const nullCount = nullCountData[i];
      const detectedDatatype = detectedDatatypeVar[i];
      const columnname = columnNameData[i];
      const minimalValue = minimalValueData[i];
      const lengthValue = lengthData[i];
      const scaleValue = scaleData[i];
      const typeValue = typeData[i];

      const newData: MyData = {
        null_percent: Number(renderValue(nullPercent)),
        unique_value: Number(renderValue(uniqueValue)),
        null_count: Number(renderValue(nullCount)),
        detectedDatatypeVar: Number(detectedDatatype),
        nameOfCol: columnname,
        minimalValue: minimalValue?.toString(),
        length: renderValue(lengthValue),
        scale: renderValue(scaleValue),
        importedDatatype: renderValue(typeValue)
      };

      dataArray.push(newData);
    }
  }

  const sortDataByNullPercent = () => {
    const sortedArray = [...dataArray];
    sortedArray.sort((a, b) => {
      const nullsPercentA = a.null_percent;
      const nullsPercentB = b.null_percent;

      if (nullsPercentA && nullsPercentB) {
        return sortDirection === 'asc'
          ? nullsPercentA - nullsPercentB
          : nullsPercentB - nullsPercentA;
      } else if (nullsPercentA) {
        return sortDirection === 'asc' ? -1 : 1;
      } else if (nullsPercentB) {
        return sortDirection === 'asc' ? 1 : -1;
      } else {
        return 0;
      }
    });
    setDataArray1(sortedArray);
    setSortDirection(sortDirection === 'asc' ? 'desc' : 'asc');
    console.log(dataArray);
    console.log(dataArray1);
  };

  const sortDataByNullCount = () => {
    const sortedArray = [...dataArray];
    sortedArray.sort((a, b) => {
      const nullsCountA = a.null_count;
      const nullsCountB = b.null_count;

      if (nullsCountA && nullsCountB) {
        return sortDirection === 'asc'
          ? nullsCountA - nullsCountB
          : nullsCountB - nullsCountA;
      } else if (nullsCountA) {
        return sortDirection === 'asc' ? -1 : 1;
      } else if (nullsCountB) {
        return sortDirection === 'asc' ? 1 : -1;
      } else {
        return 0;
      }
    });
    setDataArray1(sortedArray);
    setSortDirection(sortDirection === 'asc' ? 'desc' : 'asc');
    console.log(dataArray);
    console.log(dataArray1);
  };

  const sortDataByUniqueValue = () => {
    const sortedArray = [...dataArray];
    sortedArray.sort((a, b) => {
      const uniqueValueA = a.unique_value;
      const uniqueValueB = b.unique_value;

      if (uniqueValueA && uniqueValueB) {
        return sortDirection === 'asc'
          ? uniqueValueA - uniqueValueB
          : uniqueValueB - uniqueValueA;
      } else if (uniqueValueA) {
        return sortDirection === 'asc' ? -1 : 1;
      } else if (uniqueValueB) {
        return sortDirection === 'asc' ? 1 : -1;
      } else {
        return 0;
      }
    });
    setDataArray1(sortedArray);
    setSortDirection(sortDirection === 'asc' ? 'desc' : 'asc');
    console.log(dataArray);
    console.log(dataArray1);
  };

  const mapFunc = (column: MyData, index: number): ReactNode => {
    return (
      <tr key={index}>
        <td className="border-b border-gray-100 text-left px-4 py-2 underline cursor-pointer">
          {column.nameOfCol}
        </td>
        <td className="border-b border-gray-100 px-4 py-2">
          {/* {column?.statistics?.map((metric, index) =>
      metric.collector === 'string_datatype_detect' ? ( */}
          <div key={index} className="truncate">
            {/* {metric.result ? datatype_detected(metric.result) : ''} */}
            {datatype_detected(column.detectedDatatypeVar)}
          </div>
          {/* //   ) : (
    //     ''
    //   )
    // )} */}
        </td>
        <td className="border-b border-gray-100 text-left px-4 py-2">
          {/* {column.type_snapshot?.column_type} */}
          {column.importedDatatype}
        </td>
        <td className="border-b border-gray-100 text-right px-4 py-2">
          <span className="float-right">
            {/* {column.type_snapshot?.length} */}
            {column.length}
          </span>
        </td>
        <td className="border-b border-gray-100 text-left px-4 py-2">
          <span className="float-right">
            {/* {column.type_snapshot?.scale} */}
            {column.scale}
          </span>
        </td>
        <td className="border-b border-gray-100 text-left px-4 py-2">
          {/* {column?.statistics?.map((metric, index) =>
      metric.collector === 'min_value' ? ( */}
          <div key={index} className="text-right float-right">
            {/* {metric.result
          ? cutString(renderValue(metric.result))
          : '0'} */}
            {column.minimalValue}
          </div>
          {/* ) : (
        ''
      )
    )} */}
        </td>
        <td className="border-b border-gray-100 text-left px-4 py-2">
          {/* {column?.statistics?.map((metric, index) =>
      metric.collector === 'nulls_count' ? ( */}
          <div key={index} className="text-right float-right">
            {/* {metric.result
          ? formatNumber(Number(renderValue(metric.result)))
          : '0'} */}
            {column.null_count}
          </div>
          {/* ) : (
        ''
      )
    )} */}
        </td>
        <td className="border-b border-gray-100 text-right px-4 py-2">
          <div className="flex justify-center items-center">
            <div>{Number(column.null_percent).toFixed(2)}%</div>
            <div
              className=" h-3 border border-gray-100 flex ml-5"
              style={{ width: '66.66px' }}
            >
              <div
                className="h-3 bg-amber-700"
                style={{
                  width: column.null_percent
                    ? `${(Number(renderValue(column.null_percent)) * 2) / 3}px`
                    : ''
                }}
              ></div>
            </div>
          </div>
          {/* {column?.statistics?.map((metric, index) =>
      metric.collector === 'nulls_percent' ? (
        <div
          key={index}
          className="truncate float-right flex justify-center items-center"
        >
          <div className="w-20">
            {metric.result
              ? !isNaN(Number(renderValue(metric.result)))
                ? Number(renderValue(metric.result)).toFixed(2) +
                  '%'
                : renderValue(metric.result)
              : '0.00%'}
          </div>
          <div
            className=" h-3 border border-gray-100 flex ml-5"
            style={{ width: '66.66px' }}
          >
            <div
              className="h-3 bg-amber-700"
              style={{
                width: metric.result
                  ? `${
                      (Number(renderValue(metric.result)) * 2) / 3
                    }px`
                  : ''
              }}
            ></div>
          </div>
        </div>
      ) : (
        ''
      )
    )} */}
        </td>
        <td className="border-b border-gray-100 text-right px-4 my-0 py-0">
          <div
            key={index}
            className="truncate float-right pr-2 my-0 py-0"
            style={{
              width: '100%',

              backgroundColor: calculate_color(
                Number(column.unique_value),
                max_unique_value()
              )
            }}
          >
            {column.unique_value
              ? formatNumber(Number(renderValue(column.unique_value)))
              : ''}
          </div>
        </td>

        <td className="border-b border-gray-100 text-right px-4 py-2 flex flex-nowrap justify-end items-end">
          <IconButton
            size="sm"
            className="group bg-teal-500 ml-1.5"
            onClick={() => collectStatistics(index)}
          >
            <SvgIcon name="boxplot" className="w-4 white" />
            <div className="hidden absolute right-0 bottom-6 p-1 bg-black text-white normal-case rounded-md group-hover:block whitespace-nowrap">
              Collect statistic
            </div>
          </IconButton>

          <IconButton size="sm" className="group bg-teal-500 ml-3">
            <SvgIcon name="delete" className="w-4" />

            <span className="hidden absolute right-0 bottom-6 p-1 normal-case bg-black text-white rounded-md group-hover:block whitespace-nowrap">
              Click to delete
            </span>
          </IconButton>
        </td>
      </tr>
    );
  };

  return (
    <div className="p-4">
      <table className="mb-6 mt-4 w-full">
        <thead>
          <tr>
            <th className="border-b border-gray-100 text-left px-4 py-2 cursor-pointer">
              <div
                className="flex"
                onClick={() => {
                  setReverse(!reverse), sortAlphabetictly(reverse);
                }}
              >
                <div>Name</div>

                <div>
                  <SvgIcon name="chevron-up" className="w-3 h-3" />

                  <SvgIcon name="chevron-down" className="w-3 h-3" />
                </div>
              </div>
            </th>
            <th className="border-b border-gray-100 text-left px-4 py-2">
              Detected datatype
            </th>
            <th className="border-b border-gray-100 text-left px-4 py-2">
              Imported type
            </th>
            <th className="border-b border-gray-100 text-right px-4 py-2">
              Length
            </th>
            <th className="border-b border-gray-100 text-right px-4 py-2">
              Scale
            </th>
            <th className="border-b border-gray-100 text-right px-4 py-2">
              Minimal value
            </th>
            <th className="border-b border-gray-100 text-right px-4 py-2 flex items-center">
              <div className="text-right" onClick={() => sortDataByNullCount()}>
                Null count
              </div>
              <div className="cursor-pointer">
                <div className="w-3 h-3">
                  <SvgIcon name="chevron-up" className="w-3 h-3" />
                </div>
                <div className="w-3 h-3">
                  <SvgIcon name="chevron-down" className="w-3 h-3" />
                </div>
              </div>
            </th>
            <th
              className="border-b border-gray-100 text-right px-10 py-2 "
              onClick={() => sortDataByNullPercent()}
            >
              Null percent
            </th>
            <th
              className="border-b border-gray-100 text-right px-4 py-2"
              onClick={() => sortDataByUniqueValue()}
            >
              Unique count
            </th>
            <th className="border-b border-gray-100 text-right px-7.5 py-2">
              Action
            </th>
          </tr>
        </thead>
        <tbody>
          {dataArray1 !== undefined
            ? dataArray1.map(mapFunc)
            : dataArray.map(mapFunc)}
        </tbody>
      </table>
      <ConfirmDialog
        open={isOpen}
        onClose={() => setIsOpen(false)}
        column={selectedColumn}
        onConfirm={removeColumn}
      />
    </div>
  );
};

export default TableColumns;
