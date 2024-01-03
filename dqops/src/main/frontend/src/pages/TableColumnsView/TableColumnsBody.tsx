import React from 'react';
import { MyData } from './TableColumnsConstans';
import SvgIcon from '../../components/SvgIcon';
import { dateToString, formatNumber } from '../../shared/constants';
import { getDetectedDatatype } from '../../utils';
import {
  cutString,
  renderValue,
  calculate_color,
  max_unique_value
} from './TableColumnsUtils';
import {
  DqoJobHistoryEntryModelJobTypeEnum,
  DqoJobHistoryEntryModelStatusEnum,
  TableColumnsStatisticsModel
} from '../../api';
import { JobApiClient } from '../../services/apiClient';
import { useDispatch, useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { useHistory } from 'react-router-dom';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import Checkbox from '../../components/Checkbox';
import { IconButton } from '@material-tailwind/react';

interface ITableColumnsBodyProps {
  connection: string;
  schema: string;
  table: string;
  columns: MyData[];
  objectStates: any;
  statistics: any;
  rewriteData: any;
  handleButtonClick: any;
  showDataStreamButtonFunc: any;
}

export default function TableColumnsBody({
  connection,
  schema,
  table,
  columns,
  objectStates,
  statistics,
  rewriteData,
  handleButtonClick,
  showDataStreamButtonFunc
}: ITableColumnsBodyProps) {
  const history = useHistory();
  const { userProfile, job_dictionary_state } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const dispatch = useDispatch();
  const collectStatistics = async (
    statistics: TableColumnsStatisticsModel,
    hashValue?: number
  ) => {
    statistics?.column_statistics &&
      statistics?.column_statistics.map(async (x, index) =>
        x.column_hash === hashValue
          ? await JobApiClient.collectStatisticsOnTable(
              undefined,
              false,
              undefined,
              statistics?.column_statistics?.at(index)
                ?.collect_column_statistics_job_template
            )
          : ''
      );
  };

  const navigate = (column: string) => {
    const url = ROUTES.COLUMN_LEVEL_PAGE(
      CheckTypes.PROFILING,
      connection,
      schema,
      table,
      column,
      'statistics'
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

  const filteredJobs = Object.values(job_dictionary_state)?.filter(
    (x) =>
      x.jobType === DqoJobHistoryEntryModelJobTypeEnum.collect_statistics &&
      x.parameters?.collectStatisticsParameters
        ?.statistics_collector_search_filters?.fullTableName ===
        schema + '.' + table &&
      (x.status === DqoJobHistoryEntryModelStatusEnum.running ||
        x.status === DqoJobHistoryEntryModelStatusEnum.queued ||
        x.status === DqoJobHistoryEntryModelStatusEnum.waiting)
  );
  const filteredColumns = filteredJobs?.flatMap(
    (x) =>
      x.parameters?.collectStatisticsParameters
        ?.statistics_collector_search_filters?.columnNames
  );

  return (
    <tbody>
      {columns.map((column, index) => (
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
              {getDetectedDatatype(column.detectedDatatypeVar)}
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
                  max_unique_value(statistics)
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
                  disabled={userProfile.can_collect_statistics !== true}
                  className={
                    filteredColumns?.find((x) => x === column.nameOfCol)
                      ? 'group bg-gray-400 ml-1.5'
                      : 'group bg-teal-500 ml-1.5'
                  }
                  onClick={() =>
                    collectStatistics(statistics, column.columnHash)
                  }
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
      ))}
    </tbody>
  );
}
