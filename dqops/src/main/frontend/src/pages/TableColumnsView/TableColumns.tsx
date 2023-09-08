import React, { ReactNode, useEffect, useState, useLayoutEffect } from 'react';
import { ColumnApiClient, JobApiClient } from '../../services/apiClient';
import {
  ColumnStatisticsModel,
  DataGroupingConfigurationSpec,
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
import { setCreatedDataStream } from '../../redux/actions/definition.actions';
import { useSelector } from 'react-redux';
import { getFirstLevelState } from '../../redux/selectors';
import Loader from '../../components/Loader';
import { formatNumber, dateToString } from '../../shared/constants';
import { IRootState } from '../../redux/reducers';
import Checkbox from '../../components/Checkbox';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { datatype_detected } from '../../shared/constants';

const spec: DataGroupingConfigurationSpec = {
  level_1: {
    column: undefined
  },
  level_2: {
    column: undefined
  },
  level_3: {
    column: undefined
  },
  level_4: {
    column: undefined
  },
  level_5: {
    column: undefined
  },
  level_6: {
    column: undefined
  },
  level_7: {
    column: undefined
  },
  level_8: {
    column: undefined
  },
  level_9: {
    column: undefined
  }
};

interface MyData {
  null_percent: number | undefined;
  unique_value: number | undefined;
  null_count?: number | undefined;
  nameOfCol?: string | undefined;
  minimalValue?: string | undefined;
  maximumValue?: string | undefined;
  detectedDatatypeVar: number | undefined;
  length?: number | undefined;
  scale?: number | undefined;
  importedDatatype?: string | undefined;
  columnHash: number;
  isColumnSelected: boolean;
}

interface ITableColumnsProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
  updateData: (arg: string) => void;
  setLevelsData: (arg: DataGroupingConfigurationSpec) => void;
  setNumberOfSelected: (arg: number) => void;
  statistics?: TableColumnsStatisticsModel;
  onChangeSelectedColumns?: (columns: Array<string>) => void
}

const labels = [
  'Column name',
  'Detected data type',
  'Imported data type',
  'Length',
  'Scale',
  'Min value',
  'Max value',
  'Null count'
];

const TableColumns = ({
  connectionName,
  schemaName,
  tableName,
  updateData,
  setLevelsData,
  setNumberOfSelected,
  statistics,
  onChangeSelectedColumns
}: ITableColumnsProps) => {
  const [isOpen, setIsOpen] = useState(false);
  const [selectedColumn, setSelectedColumn] = useState<ColumnStatisticsModel>();
  const [dataArray1, setDataArray1] = useState<MyData[]>();
  const [sortDirection, setSortDirection] = useState<'asc' | 'desc'>('asc');
  const [objectStates, setObjectStates] = useState<{ [key: string]: boolean }>(
    {}
  );
  const [shouldResetCheckboxes, setShouldResetCheckboxes] = useState(false);
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
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

  const { job_dictionary_state } = useSelector(
    (state: IRootState) => state.job || {}
  );

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
    }
  };

  const collectStatistics = async (hashValue?: number) => {
    statistics?.column_statistics &&
      statistics?.column_statistics.map(async (x, index) =>
        x.column_hash === hashValue
          ? await JobApiClient.collectStatisticsOnDataGroups(
              statistics?.column_statistics?.at(index)
                ?.collect_column_statistics_job_template
            )
          : ''
      );
  };

  useEffect(() => {
    setShouldResetCheckboxes(true);
  }, [connectionName, schemaName, tableName]);

  useLayoutEffect(() => {
    if (shouldResetCheckboxes) {
      setObjectStates({});
      setShouldResetCheckboxes(false);
    }
  }, [shouldResetCheckboxes]);

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

  const max_unique_value = () => {
    const arr: number[] = [];
    statistics?.column_statistics?.map((x) => {
      x.statistics?.map((y) => {
        if (y.collector == 'distinct_count') {
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
  const filteredColumns = filteredJobs?.flatMap(
    (x) =>
      x.parameters?.collectStatisticsParameters
        ?.statisticsCollectorSearchFilters?.columnNames
  );

  const nullPercentData = statistics?.column_statistics?.map((x) =>
    x.statistics
      ?.filter((item) => item.collector === 'nulls_percent')
      .map((item) => Number(renderValue(item.result)))
  );
  const uniqueCountData = statistics?.column_statistics?.map((x) =>
    x.statistics
      ?.filter((item) => item.collector === 'distinct_count')
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

  const maximumValueData = statistics?.column_statistics?.map((x) =>
    x.statistics
      ?.filter((item) => item.collector === 'max_value')
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
        maximumValue: renderValue(maximumValueData?.[i]),
        length: renderValue(lengthData?.[i]),
        scale: renderValue(scaleData?.[i]),
        importedDatatype: String(renderValue(typeData?.[i])),
        columnHash: Number(hashData?.[i]),
        isColumnSelected: false
      };

      dataArray.push(newData);
    }
  }

  const sortAlphabetictly = (typ: keyof MyData) => {
    const sortedArray = [...dataArray];
    sortedArray.sort((a, b) => {
      const nullsCountA = String(a[typ]);
      const nullsCountB = String(b[typ]);

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

  const sortDataByMinimalValue = (typ: keyof MyData) => {
    const sortedArray = [...dataArray];
    const BoolArray = [];
    const StringArray = [];
    const NumberArray = [];

    for (let i = 0; i < sortedArray.length; i++) {
      const object = sortedArray[i];
      const minimalValue = object[typ];

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
      const nullsCountA = String(a[typ]);
      const nullsCountB = String(b[typ]);

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
      const nullsCountA = String(a[typ]);
      const nullsCountB = String(b[typ]);

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
      const nullsPercentA = String(a[typ]);
      const nullsPercentB = String(b[typ]);

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
        sortAlphabetictly('nameOfCol');
        break;
      case 'Detected data type':
        sortData<MyData>('detectedDatatypeVar');
        break;
      case 'Imported data type':
        sortAlphabetictly('importedDatatype');
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
      case 'Min value':
        sortDataByMinimalValue('minimalValue');
        break;
      case 'Max value':
        sortDataByMinimalValue('maximumValue');
        break;
    }
  };

  const rewriteData = (hashValue: number) => {
    const columnToDelete = statistics?.column_statistics?.find(
      (x) => x.column_hash === hashValue
    );

    if (columnToDelete) {
      Promise.resolve()
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
    setNumberOfSelected(count);
    return count;
  };
  const selectStrings = (obj: Record<string, boolean>) => {
    return Object.keys(obj).filter((key) => obj[key] === true);
  };
  const listOfStr = selectStrings(objectStates);

  const setSpec2 = () => {
    const newSpec: DataGroupingConfigurationSpec = {};

    for (let i = 1; i <= 9; i++) {
      const levelKey = `level_${i}` as keyof DataGroupingConfigurationSpec;
      const level = spec[levelKey];

      if (level) {
        level.column = listOfStr.at(i - 1);

        if (level.column !== undefined) {
          level.source = 'column_value';
          newSpec[levelKey] = level;
        }
      }
    }

    return newSpec;
  };

  const fixString = () => {
    const columnValues = Object.values(spec)
      .map((level) => level.column)
      .filter((column) => column !== undefined);

    const joinedValues = columnValues.join(',');

    return joinedValues;
  };

  const showDataStreamButtonFunc = async () => {
    await actionDispatch(setCreatedDataStream(true, fixString(), setSpec2()));
  };

  const setAllSelectedColumns = () => {
    const keysWithTrueValues = [];
    for (const key in objectStates) {
      if (objectStates[key] === true) {
        keysWithTrueValues.push(key);
      }
    }
    onChangeSelectedColumns && onChangeSelectedColumns(keysWithTrueValues)
  }


  useEffect(() => {
    const joinedValues = fixString();
    setLevelsData(setSpec2());
    countTrueValues(objectStates);
    updateData(joinedValues);
    setCreatedDataStream(true, fixString(), setSpec2());
    setAllSelectedColumns()
  }, [spec, objectStates]);

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
                showDataStreamButtonFunc();
                handleButtonClick(column.nameOfCol ? column.nameOfCol : '');
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
            {column.minimalValue
              ? dateToString(String(column.minimalValue))
                ? dateToString(String(column.minimalValue))
                : cutString(String(column.minimalValue))
              : ''}
          </div>
        </td>
        <td className="border-b border-gray-100 text-left px-4 py-2">
          <div key={index} className="text-right float-right">
            {column.maximumValue
              ? dateToString(String(column.maximumValue))
                ? dateToString(String(column.maximumValue))
                : cutString(String(column.maximumValue))
              : ''}
          </div>
        </td>
        <td className="border-b border-gray-100 text-left px-4 py-2">
          <div key={index} className="text-right float-right">
            {isNaN(Number(column.null_count)) ? '' : column.null_count}
          </div>
        </td>
        <td className="border-b border-gray-100 text-right px-4 py-2">
          <div className="flex justify-center items-center">
            <div className="flex justify-center items-center">
              <div>
                {isNaN(Number(column.null_percent))
                  ? ''
                  : Number(column.null_percent).toFixed(2)}
              </div>
              <div>{isNaN(Number(column.null_percent)) ? '' : '%'}</div>
            </div>
            {isNaN(Number(column.null_percent)) ? (
              ''
            ) : (
              <div
                className=" h-3 border border-gray-100 flex ml-5"
                style={{ width: '66.66px' }}
              >
                <div
                  className="h-3 bg-amber-700"
                  style={{
                    width: column.null_percent
                      ? `${
                          (Number(renderValue(column.null_percent)) * 2) / 3
                        }px`
                      : ''
                  }}
                ></div>
              </div>
            )}
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
                disabled={userProfile.can_collect_statistics  !== true}
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
                disabled={userProfile.can_manage_data_sources !== true}
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
                      x === 'Min value' ||
                      x === 'Null count' ||
                      x === 'Max value'
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
                <div onClick={() => updateData('updated data')}>
                  Null percent
                </div>
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
                <div>Distinct Count</div>
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
