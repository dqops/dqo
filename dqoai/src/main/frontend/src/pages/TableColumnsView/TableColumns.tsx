import React, { useEffect, useState } from 'react';
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

const TableColumns = ({
  connectionName,
  schemaName,
  tableName
}: ITableColumnsProps) => {
  const [statistics, setStatistics] = useState<TableColumnsStatisticsModel>();
  const [sortedStatistics, setSortedStatistics] =
    useState<ColumnStatisticsModel[]>();
  const [isOpen, setIsOpen] = useState(false);
  const [selectedColumn, setSelectedColumn] = useState<ColumnStatisticsModel>();
  const [loadingJob, setLoadingJob] = useState(false);

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
    setSortedStatistics(statistics?.column_statistics);
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
    try {
      setLoadingJob(true);
      await JobApiClient.collectStatisticsOnDataStreams(
        statistics?.column_statistics?.at(index)
          ?.collect_column_statistics_job_template
      );
    } finally {
      setLoadingJob(false);
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
      return 'rgba(2, 154, 128, 255)';
    }

    const logarithm = Math.log2(uniqueCount);
    const alpha = 1 - (logarithm / Math.log2(maxUniqueCount)) * 0.9;

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

  return (
    <div className="p-4">
      <table className="mb-6 mt-4 w-full">
        <thead>
          <tr>
            <th className="border-b border-gray-100 text-left px-4 py-2">
              Name
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
              First value sample
            </th>
            <th className="border-b border-gray-100 text-right px-4 py-2">
              Null count
            </th>
            <th className="border-b border-gray-100 text-right px-4 py-2 ">
              Null percent
            </th>
            <th
              className="border-b border-gray-100 text-right px-4 py-2"
              onClick={() => max_unique_value()}
            >
              Unique count
            </th>
            <th className="border-b border-gray-100 text-right px-7.5 py-2">
              Action
            </th>
          </tr>
        </thead>
        <tbody>
          {statistics &&
            statistics?.column_statistics?.map((column, index) => (
              <tr key={index}>
                <td
                  className="border-b border-gray-100 text-left px-4 py-2 underline cursor-pointer"
                  onClick={() =>
                    navigate(column.column_name ? column.column_name : '')
                  }
                >
                  {column.column_name}
                </td>
                <td className="border-b border-gray-100 px-4 py-2">
                  {column?.statistics?.map((metric, index) =>
                    metric.collector === 'string_datatype_detect' ? (
                      <div key={index} className="truncate">
                        {metric.result ? datatype_detected(metric.result) : ''}
                      </div>
                    ) : (
                      ''
                    )
                  )}
                </td>
                <td className="border-b border-gray-100 text-left px-4 py-2">
                  {column.type_snapshot?.column_type}
                </td>
                <td className="border-b border-gray-100 text-right px-4 py-2">
                  <span className="float-right">
                    {column.type_snapshot?.length}
                  </span>
                </td>
                <td className="border-b border-gray-100 text-left px-4 py-2">
                  <span className="float-right">
                    {column.type_snapshot?.scale}
                  </span>
                </td>
                <td className="border-b border-gray-100 text-left px-4 py-2">
                  {column?.statistics?.map((metric, index) =>
                    metric.collector === 'min_value' ? (
                      <div key={index} className="text-right float-right">
                        {metric.result
                          ? cutString(renderValue(metric.result))
                          : '0'}
                      </div>
                    ) : (
                      ''
                    )
                  )}
                </td>
                <td className="border-b border-gray-100 text-left px-4 py-2">
                  {column?.statistics?.map((metric, index) =>
                    metric.collector === 'nulls_count' ? (
                      <div key={index} className="text-right float-right">
                        {metric.result
                          ? formatNumber(Number(renderValue(metric.result)))
                          : '0'}
                      </div>
                    ) : (
                      ''
                    )
                  )}
                </td>
                <td className="border-b border-gray-100 text-right px-4 py-2">
                  {column?.statistics?.map((metric, index) =>
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
                          style={{ width: '100px' }}
                        >
                          <div
                            className="h-3 bg-amber-700"
                            style={{
                              width: metric.result
                                ? `${Number(renderValue(metric.result))}px`
                                : ''
                            }}
                          ></div>
                        </div>
                      </div>
                    ) : (
                      ''
                    )
                  )}
                </td>
                <td className="border-b border-gray-100 text-right px-4 my-0 py-0">
                  {column?.statistics?.map((metric, index) =>
                    metric.collector === 'unique_count' ? (
                      <div
                        key={index}
                        className="truncate float-right pr-2 my-0 py-0"
                        style={{
                          width: '100%',

                          backgroundColor: calculate_color(
                            Number(metric.result),
                            max_unique_value()
                          )
                        }}
                      >
                        {metric.result
                          ? formatNumber(Number(renderValue(metric.result)))
                          : ''}
                      </div>
                    ) : (
                      ''
                    )
                  )}
                </td>

                <td className="border-b border-gray-100 text-right px-4 py-2 ">
                  <IconButton
                    size="sm"
                    className="group bg-teal-500 ml-1.5"
                    onClick={() => collectStatistics(index)}
                  >
                    <SvgIcon name="boxplot" className="w-4 white" />
                    <span className="hidden absolute right-3 bottom-3 p-2 bg-black text-white normal-case rounded-md group-hover:block whitespace-nowrap">
                      Collect statistic
                    </span>
                  </IconButton>

                  <IconButton
                    size="sm"
                    className="group bg-teal-500 ml-3"
                    onClick={() => onRemoveColumn(column)}
                  >
                    <SvgIcon name="delete" className="w-4" />

                    <span className="hidden absolute right-3 bottom-3 p-2 normal-case bg-black text-white rounded-md group-hover:block whitespace-nowrap">
                      Click to delete
                    </span>
                  </IconButton>
                </td>
              </tr>
            ))}
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
