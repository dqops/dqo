import React, { ReactNode, useEffect, useState } from 'react';
import {
  ColumnApiClient,
  DataStreamsApi,
  JobApiClient
} from '../../services/apiClient';
import { AxiosResponse } from 'axios';
import {
  ColumnStatisticsModel,
  DataStreamMappingSpec,
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
import { setCreatedDataStream } from '../../redux/actions/rule.actions';
import { useSelector } from 'react-redux';
import { getFirstLevelState } from '../../redux/selectors';
import Loader from '../../components/Loader';
import { formatNumber, dateToString } from '../../shared/constants';
import { IRootState } from '../../redux/reducers';
import Checkbox from '../../components/Checkbox';
import Button from '../../components/Button';
import { showDataStreamButton } from '../../redux/actions/job.actions';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { datatype_detected } from '../../shared/constants';
import { cutString } from './TableColumnsFunctions';
import {
  renderValue,
  MyData,
  LocationState,
  labels,
  calculate_color,
  spec
} from './TableColumnsFunctions';

interface ITableColumnsProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
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
  const [objectStates, setObjectStates] = useState<{ [key: string]: boolean }>(
    {}
  );

  const handleButtonClick = (name: string) => {
    setObjectStates((prevStates) => ({
      ...prevStates,
      [name]: !prevStates[name]
    }));
  };
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
  const actionDispatch = useActionDispatch();
  const { loading } = useSelector(getFirstLevelState(CheckTypes.SOURCES));
  const { dataStreamButton } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const setData = () => {
    actionDispatch(setCreatedDataStream(true, setDataStream(), setSpec2()));
  };

  console.log(dataStreamButton);
  const { job_dictionary_state } = useSelector(
    (state: IRootState) => state.job || {}
  );

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
  if (columnNameData && hashData) {
    const maxLength = Math.max(columnNameData.length, hashData.length);

    for (let i = 0; i < maxLength; i++) {
      const newData: MyData = {
        null_percent: Number(renderValue(nullPercentData?.[i])),
        unique_value: Number(renderValue(uniqueCountData?.[i])),
        null_count: Number(renderValue(nullCountData?.[i])),
        detectedDatatypeVar: Number(detectedDatatypeVar?.[i]),
        nameOfCol: columnNameData?.[i],
        minimalValue: renderValue(minimalValueData?.[i]),
        length: renderValue(lengthData?.[i]),
        scale: renderValue(scaleData?.[i]),
        importedDatatype: renderValue(typeData?.[i]),
        columnHash: Number(hashData?.[i]),
        isColumnSelected: false
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

  const sortData = <T extends { [key: string]: any }>(key: keyof T) => {
    const sortedArray = [...dataArray];
    sortedArray.sort((a, b) => {
      const valueA = String(a[key as keyof typeof a]);
      const valueB = String(b[key as keyof typeof b]);

      if (valueA && valueB) {
        return sortDirection === 'asc'
          ? parseFloat(valueA) - parseFloat(valueB)
          : parseFloat(valueB) - parseFloat(valueA);
      } else if (valueA) {
        return sortDirection === 'asc' ? -1 : 1;
      } else if (valueB) {
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
      case 'Column name':
        sortAlphabetictly();
        break;
      case 'Detected data type':
        sortData<MyData>('detectedDatatypeVar');
        break;
      case 'Imported data type':
        sortData<MyData>('importedDatatype');
        break;
      case 'Length':
        sortData<MyData>('length');
        break;
      case 'Scale':
        sortData<MyData>('scale');
        break;
      case 'Null count':
        sortData<MyData>('null_count');
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

  const countTrueValues = (obj: Record<string, boolean>): number => {
    let count = 0;
    for (const key in obj) {
      if (obj[key] === true) {
        count++;
      }
    }
    return count;
  };
  const selectStrings = (obj: Record<string, boolean>) => {
    return Object.keys(obj).filter((key) => obj[key] === true);
  };
  const listOfStr = selectStrings(objectStates);
  const trueValuesCount = countTrueValues(objectStates);

  const setSpec2 = () => {
    for (let i = 1; i <= trueValuesCount; i++) {
      const levelKey = `level_${i}` as keyof DataStreamMappingSpec;
      const level = spec[levelKey];

      if (level) {
        level.column = listOfStr.at(i - 1);
        level.source = 'column_value';
      }
    }
    return spec;
  };

  const setDataStream = () => {
    let dataStream = '';
    for (let i = 1; i <= trueValuesCount; i++) {
      const levelKey = `level_${i}` as keyof DataStreamMappingSpec;
      const level = spec[levelKey];

      if (level && i !== trueValuesCount) {
        dataStream += listOfStr.at(i - 1) + ',';
      } else {
        dataStream += listOfStr.at(i - 1);
      }
    }
    return dataStream;
  };

  const doNothing = (): void => {};

  setCreatedDataStream(true, setDataStream(), setSpec2());

  const postDataStream = async () => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      'sources',
      connectionName,
      schemaName,
      tableName,
      'data-streams'
    );
    const value = ROUTES.TABLE_LEVEL_VALUE(
      'sources',
      connection,
      schema,
      table
    );
    const data: LocationState = {
      bool: true,
      data_stream_name: setDataStream(),
      spec: setSpec2()
    };

    try {
      const response = await DataStreamsApi.createDataStream(
        connectionName,
        schemaName,
        tableName,
        data
      );
      if (response.status === 409) {
        doNothing();
      }
    } catch (error: any) {
      if (error.response && error.response.status) {
        doNothing();
      }
    }
    setData();
    dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url,
        value,
        state: data,
        label: table
      })
    );
    history.push(url);
  };

  const showDataStreamButtonFunc = () => {
    if (trueValuesCount === 0) {
      actionDispatch(showDataStreamButton(0));
    } else if (trueValuesCount <= 9) {
      actionDispatch(showDataStreamButton(1));
    } else if (trueValuesCount > 9) {
      actionDispatch(showDataStreamButton(2));
    }
  };

  const mapFunc = (column: MyData, index: number): ReactNode => {
    return (
      <tr key={index}>
        <td className="border-b border-gray-100 text-right px-4 py-2">
          <div>
            <Checkbox
              checked={
                objectStates[column.nameOfCol ? column.nameOfCol : '']
                  ? true
                  : false
              }
              onChange={() => {
                handleButtonClick(column.nameOfCol ? column.nameOfCol : ''),
                  showDataStreamButtonFunc();
              }}
              className="py-4"
            />
          </div>
        </td>
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
    <div className="p-4 py-6 relative">
      {trueValuesCount !== 0 && trueValuesCount <= 9 && (
        <Button
          label="Create Data Stream"
          color="primary"
          onClick={postDataStream}
          className="absolute top-0 right-4 px-2"
        />
      )}
      {trueValuesCount !== 0 && trueValuesCount > 9 && (
        <div className="flex text-red-500 items-center gap-x-4 absolute top-0 right-4 px-2">
          (You can choose max 9 columns)
          <Button
            label="Create Data Stream"
            color="secondary"
            className="text-black "
          />
        </div>
      )}
      <table className="mb-6 mt-4 w-full">
        <thead>
          <tr>
            <th
              className="border-b border-gray-100 "
              style={{ width: '6px' }}
            ></th>
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
                onClick={() => sortData<MyData>('null_percent')}
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
                onClick={() => sortData<MyData>('unique_value')}
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
