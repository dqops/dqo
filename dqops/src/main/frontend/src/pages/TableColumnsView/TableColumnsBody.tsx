import { IconButton, Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import moment from 'moment';
import React, { useEffect, useMemo, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import {
  DqoJobHistoryEntryModelStatusEnum,
  TableColumnsStatisticsModel
} from '../../api';
import Checkbox from '../../components/Checkbox';
import { getColor } from '../../components/Connection/TableView/TableQualityStatus/TableQualityStatusUtils';
import SvgIcon from '../../components/SvgIcon';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import { IRootState } from '../../redux/reducers';
import { JobApiClient } from '../../services/apiClient';
import { dateToString, formatNumber } from '../../shared/constants';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { getDetectedDatatype, useDecodedParams } from '../../utils';
import { MyData } from './TableColumnsConstans';
import {
  calculate_color,
  cutString,
  max_unique_value,
  renderValue
} from './TableColumnsUtils';

interface ITableColumnsBodyProps {
  columns: MyData[];
  objectStates: any;
  statistics: any;
  rewriteData: any;
  handleButtonClick: any;
  showDataStreamButtonFunc: any;
  refreshListFunc: () => void;
}

export default function TableColumnsBody({
  columns,
  objectStates,
  statistics,
  rewriteData,
  handleButtonClick,
  showDataStreamButtonFunc,
  refreshListFunc
}: ITableColumnsBodyProps) {
  const history = useHistory();
  const {
    connection,
    schema,
    table,
    checkTypes
  }: {
    connection: string;
    schema: string;
    table: string;
    checkTypes: CheckTypes;
  } = useDecodedParams();
  const { userProfile, job_dictionary_state } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const dispatch = useDispatch();
  const [jobId, setJobId] = useState<number>();
  const [lastRefreshedJobId, setLastRefreshedJobId] = useState<number>();

  const job = jobId ? job_dictionary_state[jobId] : undefined;

  useEffect(() => {
    if (
      job &&
      job?.status === DqoJobHistoryEntryModelStatusEnum.finished &&
      jobId != lastRefreshedJobId
    ) {
      setLastRefreshedJobId(jobId);
      refreshListFunc();
    }
  }, [job]);

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
            ).then((res) => setJobId(res.data.jobId?.jobId))
          : ''
      );
  };

  const navigate = (column: string) => {
    const url = ROUTES.COLUMN_LEVEL_PAGE(
      checkTypes,
      connection,
      schema,
      table,
      column,
      checkTypes === CheckTypes.PROFILING ? 'statistics' : 'daily'
    );
    const value = ROUTES.COLUMN_LEVEL_VALUE(
      checkTypes,
      connection,
      schema,
      table,
      column
    );
    dispatch(
      addFirstLevelTab(checkTypes, {
        url,
        value,
        state: {},
        label: column
      })
    );
    history.push(url);
  };

  const filteredColumns = useMemo(() => {
    return job &&
      (job.status === DqoJobHistoryEntryModelStatusEnum.running ||
        job.status === DqoJobHistoryEntryModelStatusEnum.queued ||
        job.status === DqoJobHistoryEntryModelStatusEnum.waiting)
      ? job.parameters?.collectStatisticsParameters
          ?.statistics_collector_search_filters?.columnNames
      : ([] as string[]);
  }, [job]);

const renderSecondLevelTooltip = (data: any) => {
  return (data.empty ?
  <div>
      <div className="flex gap-x-2">
        <div className="w-42">Quality Dimension:</div>
        <div>{data.quality_dimension}</div>
      </div>
    <div className="w-full">No data quality checks configured</div>
  </div> :
    <div>
      <div className="flex gap-x-2">
        <div className="w-42">Data quality check:</div>
        <div>{data.check_name}</div>
      </div>
      <div className="flex gap-x-2">
        <div className="w-42">Last executed at:</div>
        <div>{moment(data.last_executed_at).format('YYYY-MM-DD HH:mm:ss')}</div>
      </div>
      <div className="flex gap-x-2">
        <div className="w-42">Current severity level:</div>
        <div>{data.current_severity}</div>
      </div>
      <div className="flex gap-x-2">
        <div className="w-42">Highest historical severity level:</div>
        <div>{data.highest_severity}</div>
      </div>
      <div className="flex gap-x-2">
        <div className="w-42">Category:</div>
        <div>{data.category}</div>
      </div>
      <div className="flex gap-x-2">
        <div className="w-42">Quality Dimension:</div>
        <div>{data.quality_dimension}</div>
      </div>
    </div>
  );
};

const getBasicDimmensions = (column: MyData, type: string) : any[] => {
  const basicDimensions = column.dimentions?.filter((x) => x.quality_dimension === type);
  return basicDimensions?.length ? basicDimensions : [{empty: true, quality_dimension : type}]
};  
const basicDimensionTypes = ['Completeness', 'Validity', 'Consistency'];

const getAdditionalDimentions = (column: MyData) : any[] => {
  return column.dimentions?.filter((x) => !basicDimensionTypes.includes(x.quality_dimension)) ?? [];
}

  return (
    <tbody className="text-sm">
      {columns.map((column, index) => (
        <tr key={index}>
          <td className="border-b border-gray-100 text-right px-4 py-2 text-sm">
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
          <td className='border-b border-gray-100 text-left px-4 py-2'>
            <div className='flex items-center gap-x-0.5'>
            {basicDimensionTypes.map((dimType) => {
              return getBasicDimmensions(column, dimType)?.map((dim, index) => (
                <Tooltip key={index} content={renderSecondLevelTooltip(dim)} >
                  <div className={clsx('w-3 h-3', dim.empty === true ? 'border border-gray-200 bg-white' : getColor(dim.current_severity))} style={{ borderRadius: "6px" }} />
                </Tooltip>
              ));
            })}
            {getAdditionalDimentions(column).map((dim)  =>
                <Tooltip key={index} content={renderSecondLevelTooltip(dim)}>
                  <div className={clsx('w-3 h-3', getColor(dim.current_severity))} style={{ borderRadius: "6px" }} />
                </Tooltip>
             )}
          </div>
          </td>
          <td
            className="border-b border-gray-100 text-left px-4 py-2 underline cursor-pointer"
            onClick={() => navigate(column.nameOfCol ? column.nameOfCol : '')}
          >
            {column.nameOfCol}
          </td>
          <td className="border-b border-gray-100 text-left px-4 py-2">
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
