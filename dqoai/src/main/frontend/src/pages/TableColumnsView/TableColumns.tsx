import React, { ReactNode, useEffect, useState } from 'react';
import { ColumnApiClient, JobApiClient } from '../../services/apiClient';
import { AxiosResponse } from 'axios';
import {
  ColumnStatisticsModel,
  DqoJobHistoryEntryModelStatusEnum,
  TableColumnsStatisticsModel
} from '../../api';
import { IconButton } from '@material-tailwind/react';
import SvgIcon from '../../components/SvgIcon';
import ConfirmDialog from './ConfirmDialog';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { useDispatch } from 'react-redux/es/hooks/useDispatch';
import { useParams, useHistory } from 'react-router-dom';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import { useSelector } from 'react-redux';
import { getFirstLevelState } from '../../redux/selectors';
import Loader from '../../components/Loader';
import { formatNumber, dateToString } from '../../shared/constants';
import { IRootState } from '../../redux/reducers';

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
  columnHash: number;
}

const TableColumns = ({
  connectionName,
  schemaName,
  tableName
}: ITableColumnsProps) => {
  const [statistics, setStatistics] = useState<TableColumnsStatisticsModel>();
  const [isOpen, setIsOpen] = useState(false);
  const [selectedColumn, setSelectedColumn] = useState<ColumnStatisticsModel>();
  const [columnToDelete, setColumnToDelete] = useState<ColumnStatisticsModel>();
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
  const { loading } = useSelector(getFirstLevelState(CheckTypes.SOURCES));

  const { job_dictionary_state } = useSelector(
    (state: IRootState) => state.job || {}
  );

  const labels = [
    'Column name',
    'Detected data type',
    'Imported data type',
    'Length',
    'Scale',
    'Minimal value',
    'Null count'
  ];

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

  const collectStatistics = async (hashValue?: number) => {
    statistics?.column_statistics &&
      statistics?.column_statistics.map(async (x, index) =>
        x.column_hash === hashValue
          ? await JobApiClient.collectStatisticsOnDataStreams(
              statistics?.column_statistics?.at(index)
                ?.collect_column_statistics_job_template
            )
          : ''
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
  const filteredJobs = Object.values(job_dictionary_state)?.filter(
    (x) =>
      x.jobType === 'collect statistics' &&
      x.parameters?.collectStatisticsParameters
        ?.statisticsCollectorSearchFilters?.schemaTableName ===
        schemaName + '.' + tableName &&
      (x.status === DqoJobHistoryEntryModelStatusEnum.running ||
        x.status === DqoJobHistoryEntryModelStatusEnum.queued ||
        x.status === DqoJobHistoryEntryModelStatusEnum.waiting)
  );
  const filteredColumns = filteredJobs?.map(
    (x) =>
      x.parameters?.collectStatisticsParameters
        ?.statisticsCollectorSearchFilters?.columnName
  );

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

  const nullPercentData = statistics?.column_statistics?.map((x) =>
    x.statistics
      ?.filter((item) => item.collector === 'nulls_percent')
      .map((item) => Number(renderValue(item.result)))
  );
  const uniqueCountData = statistics?.column_statistics?.map((x) =>
    x.statistics
      ?.filter((item) => item.collector === 'unique_count')
      .map((item) => item.result)
  );
  const nullCountData = statistics?.column_statistics?.map((x) =>
    x.statistics
      ?.filter((item) => item.collector === 'nulls_count')
      .map((item) => renderValue(item.result))
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

  const hashData = statistics?.column_statistics?.map((x) => x.column_hash);

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
    typeData &&
    hashData
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
      typeData.length,
      hashData.length
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
      const hashValue = hashData[i];

      const newData: MyData = {
        null_percent: Number(renderValue(nullPercent)),
        unique_value: Number(renderValue(uniqueValue)),
        null_count: Number(renderValue(nullCount)),
        detectedDatatypeVar: Number(detectedDatatype),
        nameOfCol: columnname,
        minimalValue: renderValue(minimalValue),
        length: renderValue(lengthValue),
        scale: renderValue(scaleValue),
        importedDatatype: renderValue(typeValue),
        columnHash: Number(hashValue)
      };

      dataArray.push(newData);
    }
  }

  const sortAlphabetictly = () => {
    const sortedArray = [...dataArray];
    sortedArray.sort((a, b) => {
      const nullsCountA = String(a.nameOfCol);
      const nullsCountB = String(b.nameOfCol);

      if (nullsCountA && nullsCountB) {
        return sortDirection === 'asc'
          ? nullsCountA.localeCompare(nullsCountB)
          : nullsCountB.localeCompare(nullsCountA);
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
  };

  const sortDataByMinimalValue = () => {
    const sortedArray = [...dataArray];
    const BoolArray = [];
    const StringArray = [];
    const NumberArray = [];

    for (let i = 0; i < sortedArray.length; i++) {
      const object = sortedArray[i];
      const minimalValue = object?.minimalValue;

      if (
        String(minimalValue)?.charAt(0) === '0' &&
        String(minimalValue)?.length !== 1 &&
        String(minimalValue)?.charAt(1) !== '.'
      ) {
        StringArray.push(object);
      } else if (minimalValue === 'Yes' || minimalValue === 'No') {
        BoolArray.push(object);
      } else if (
        typeof minimalValue === 'string' &&
        isNaN(Number(minimalValue))
      ) {
        StringArray.push(object);
      } else if (
        typeof minimalValue === 'string' &&
        !isNaN(Number(minimalValue))
      ) {
        NumberArray.push(object);
      }
    }
    BoolArray.sort((a, b) => {
      const nullsCountA = String(a.minimalValue);
      const nullsCountB = String(b.minimalValue);

      if (nullsCountA && nullsCountB) {
        return sortDirection === 'asc'
          ? nullsCountA.localeCompare(nullsCountB)
          : nullsCountB.localeCompare(nullsCountA);
      } else if (nullsCountA) {
        return sortDirection === 'asc' ? -1 : 1;
      } else if (nullsCountB) {
        return sortDirection === 'asc' ? 1 : -1;
      } else {
        return 0;
      }
    });

    StringArray.sort((a, b) => {
      const nullsCountA = String(a.minimalValue);
      const nullsCountB = String(b.minimalValue);

      if (nullsCountA && nullsCountB) {
        return sortDirection === 'asc'
          ? nullsCountA.localeCompare(nullsCountB)
          : nullsCountB.localeCompare(nullsCountA);
      } else if (nullsCountA) {
        return sortDirection === 'asc' ? -1 : 1;
      } else if (nullsCountB) {
        return sortDirection === 'asc' ? 1 : -1;
      } else {
        return 0;
      }
    });

    NumberArray.sort((a, b) => {
      const nullsPercentA = String(a.minimalValue);
      const nullsPercentB = String(b.minimalValue);

      if (nullsPercentA && nullsPercentB) {
        return sortDirection === 'asc'
          ? parseFloat(nullsPercentA) - parseFloat(nullsPercentB)
          : parseFloat(nullsPercentB) - parseFloat(nullsPercentA);
      } else if (nullsPercentA) {
        return sortDirection === 'asc' ? -1 : 1;
      } else if (nullsPercentB) {
        return sortDirection === 'asc' ? 1 : -1;
      } else {
        return 0;
      }
    });

    setSortDirection(sortDirection === 'asc' ? 'desc' : 'asc');
    const sortedResult = [...BoolArray, ...StringArray, ...NumberArray];
    setDataArray1(sortedResult);
  };

  const sortDataByLength = () => {
    const sortedArray = [...dataArray];
    sortedArray.sort((a, b) => {
      const nullsPercentA = String(a.length);
      const nullsPercentB = String(b.length);

      if (nullsPercentA && nullsPercentB) {
        return sortDirection === 'asc'
          ? parseFloat(nullsPercentA) - parseFloat(nullsPercentB)
          : parseFloat(nullsPercentB) - parseFloat(nullsPercentA);
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
  };

  const sortDataByScale = () => {
    const sortedArray = [...dataArray];
    sortedArray.sort((a, b) => {
      const nullsPercentA = String(a.scale);
      const nullsPercentB = String(b.scale);

      if (nullsPercentA && nullsPercentB) {
        return sortDirection === 'asc'
          ? parseFloat(nullsPercentA) - parseFloat(nullsPercentB)
          : parseFloat(nullsPercentB) - parseFloat(nullsPercentA);
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
  };

  const sortDataByNullPercent = () => {
    const sortedArray = [...dataArray];
    sortedArray.sort((a, b) => {
      const nullsPercentA = String(a.null_percent);
      const nullsPercentB = String(b.null_percent);

      if (nullsPercentA && nullsPercentB) {
        return sortDirection === 'asc'
          ? parseFloat(nullsPercentA) - parseFloat(nullsPercentB)
          : parseFloat(nullsPercentB) - parseFloat(nullsPercentA);
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
  };

  const sortDataByNullCount = () => {
    const sortedArray = [...dataArray];
    sortedArray.sort((a, b) => {
      const nullsCountA = String(a.null_count);
      const nullsCountB = String(b.null_count);

      if (nullsCountA && nullsCountB) {
        return sortDirection === 'asc'
          ? Number(nullsCountA) - Number(nullsCountB)
          : Number(nullsCountB) - Number(nullsCountA);
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
  };

  const sortDataByDetectedtype = () => {
    const sortedArray = [...dataArray];
    sortedArray.sort((a, b) => {
      const nullsCountA = String(a.detectedDatatypeVar);
      const nullsCountB = String(b.detectedDatatypeVar);

      if (nullsCountA && nullsCountB) {
        return sortDirection === 'asc'
          ? Number(nullsCountA) - Number(nullsCountB)
          : Number(nullsCountB) - Number(nullsCountA);
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
  };

  const sortDataByImportedtype = () => {
    const sortedArray = [...dataArray];
    sortedArray.sort((a, b) => {
      const nullsCountA = String(a.importedDatatype);
      const nullsCountB = String(b.importedDatatype);

      if (nullsCountA && nullsCountB) {
        return sortDirection === 'asc'
          ? nullsCountA.localeCompare(nullsCountB)
          : nullsCountB.localeCompare(nullsCountA);
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
  };

  const handleSorting = (param: string) => {
    switch (param) {
      case 'Name':
        sortAlphabetictly();
        break;
      case 'Detected data type':
        sortDataByDetectedtype();
        break;
      case 'Imported type':
        sortDataByImportedtype();
        break;
      case 'Length':
        sortDataByLength();
        break;
      case 'Scale':
        sortDataByScale();
        break;
      case 'Null count':
        sortDataByNullCount();
        break;
      case 'Minimal value':
        sortDataByMinimalValue();
        break;
    }
  };

  const rewriteData = (hashValue: number) => {
    const columnToDelete = statistics?.column_statistics?.find(
      (x) => x.column_hash === hashValue
    );

    if (columnToDelete) {
      Promise.resolve()
        .then(() => setColumnToDelete(columnToDelete))
        .then(() => onRemoveColumn(columnToDelete))
        .catch((error) => console.error(error));
    }
  };

  const mapFunc = (column: MyData, index: number): ReactNode => {
    return (
      <tr key={index}>
        <td
          className="border-b border-gray-100 text-left px-4 py-2 underline cursor-pointer"
          onClick={() => navigate(column.nameOfCol ? column.nameOfCol : '')}
        >
          {column.nameOfCol}
        </td>
        <td className="border-b border-gray-100 px-4 py-2">
          <div key={index} className="truncate">
            {datatype_detected(column.detectedDatatypeVar)}
          </div>
        </td>
        <td className="border-b border-gray-100 text-left px-4 py-2">
          {column.importedDatatype}
        </td>
        <td className="border-b border-gray-100 text-right px-4 py-2">
          <span className="float-right">{column.length}</span>
        </td>
        <td className="border-b border-gray-100 text-left px-4 py-2">
          <span className="float-right">{column.scale}</span>
        </td>
        <td className="border-b border-gray-100 text-left px-4 py-2">
          <div key={index} className="text-right float-right">
            {dateToString(String(column.minimalValue))
              ? dateToString(String(column.minimalValue))
              : cutString(String(column.minimalValue))}
          </div>
        </td>
        <td className="border-b border-gray-100 text-left px-4 py-2">
          <div key={index} className="text-right float-right">
            {column.null_count}
          </div>
        </td>
        <td className="border-b border-gray-100 text-right px-4 py-2">
          <div className="flex justify-center items-center">
            <div className="flex justify-center items-center">
              <div>{Number(column.null_percent).toFixed(2)}</div>
              <div>{isNaN(Number(column.null_percent)) ? '' : '%'}</div>
            </div>
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

        <td className="border-b border-gray-100 text-right px-4 py-2">
          <div className="flex" style={{ justifyContent: 'flex-end' }}>
            <div>
              <IconButton
                size="sm"
                className={
                  filteredColumns?.find((x) => x === column.nameOfCol)
                    ? 'group bg-gray-400 ml-1.5'
                    : 'group bg-teal-500 ml-1.5'
                }
                onClick={() => collectStatistics(column.columnHash)}
              >
                <SvgIcon name="boxplot" className="w-4 white" />
                <div className="hidden absolute right-0 bottom-6 p-1 bg-black text-white normal-case rounded-md group-hover:block whitespace-nowrap">
                  Collect statistics
                </div>
              </IconButton>
            </div>
            <div>
              <IconButton
                size="sm"
                className="group bg-teal-500 ml-3"
                onClick={() => {
                  rewriteData(column.columnHash);
                }}
              >
                <SvgIcon name="delete" className="w-4" />

                <span className="hidden absolute right-0 bottom-6 p-1 normal-case bg-black text-white rounded-md group-hover:block whitespace-nowrap">
                  Delete column
                </span>
              </IconButton>
            </div>
          </div>
        </td>
      </tr>
    );
  };

  if (loading) {
    return (
      <div className="flex justify-center min-h-80">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }

  return (
    <div className="p-4">
      <table className="mb-6 mt-4 w-full">
        <thead>
          <tr>
            {labels.map((x, index) => (
              <th
                className="border-b border-gray-100 text-left px-4 py-2 cursor-pointer"
                key={index}
              >
                <div
                  className="flex"
                  style={{
                    justifyContent:
                      x === 'Minimal Value' || x === 'Null count'
                        ? 'flex-end'
                        : 'flex-start'
                  }}
                  onClick={() => {
                    handleSorting(x);
                  }}
                >
                  <div>{x}</div>
                  <div>
                    <SvgIcon name="chevron-up" className="w-3 h-3" />
                    <SvgIcon name="chevron-down" className="w-3 h-3" />
                  </div>
                </div>
              </th>
            ))}

            <th className="border-b border-gray-100 text-right px-10 py-2 ">
              <div
                className="flex justify-center cursor-pointer"
                onClick={() => sortDataByNullPercent()}
              >
                <div>Null percent</div>
                <div>
                  <SvgIcon name="chevron-up" className="w-3 h-3" />
                  <SvgIcon name="chevron-down" className="w-3 h-3" />
                </div>
              </div>
            </th>
            <th className="border-b border-gray-100 text-right px-4 py-2">
              <div
                className="flex justify-end cursor-pointer"
                onClick={() => sortDataByUniqueValue()}
              >
                <div>Unique Count</div>
                <div>
                  <SvgIcon name="chevron-up" className="w-3 h-3" />
                  <SvgIcon name="chevron-down" className="w-3 h-3" />
                </div>
              </div>
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
