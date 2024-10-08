import { IconButton } from '@material-tailwind/react';
import React, { useEffect, useMemo, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import {
  DqoJobHistoryEntryModelStatusEnum,
  TableColumnsStatisticsModel
} from '../../api';
import Checkbox from '../../components/Checkbox';
import QualityDimensionStatuses from '../../components/DataQualityChecks/QualityDimension/QualityDimensionStatuses';
import SvgIcon from '../../components/SvgIcon';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import { IRootState } from '../../redux/reducers';
import { JobApiClient } from '../../services/apiClient';
import { dateToString, formatNumber } from '../../shared/constants';
import { CheckTypes, ROUTES } from '../../shared/routes';
import {
  getDetectedDatatype,
  getFirstLevelColumnTab,
  limitTextLength,
  useDecodedParams
} from '../../utils';
import { MyData } from './TableColumnsConstans';
import {
  calculate_color,
  cutString,
  max_unique_value,
  renderValue
} from './TableColumnsUtils';

interface ITableColumnsBodyProps {
  columns: MyData[];
  statistics: any;
  rewriteData: any;
  checkedColumns: string[];
  handleChangeCheckedColumns: (column: string) => void;
  refreshListFunc: () => void;
}

export default function TableColumnsBody({
  columns,
  checkedColumns,
  statistics,
  rewriteData,
  handleChangeCheckedColumns,
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
  const [items, setItems] = useState<Array<string>>([]);
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
      getFirstLevelColumnTab(checkTypes)
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

  const getMidSectionItemsBasedOnWidth = () => {
    const width = window.innerWidth;
    const excludedItems: string[] = [];

    if (width < 1700) {
      excludedItems.push(
        'Nulls count',
        'Max value',
        'Min value',
        'Scale',
        'Length'
      );
    } else if (width < 1800) {
      excludedItems.push('Nulls count', 'Max value', 'Min value', 'Scale');
    } else if (width < 1900) {
      excludedItems.push('Nulls count', 'Max value', 'Min value');
    } else if (width < 2000) {
      excludedItems.push('Nulls count', 'Max value');
    } else if (width < 2200) {
      excludedItems.push('Nulls count');
    }

    setItems(excludedItems);
  };

  useEffect(() => {
    getMidSectionItemsBasedOnWidth();
    window.addEventListener('resize', getMidSectionItemsBasedOnWidth);
    return () => {
      window.removeEventListener('resize', getMidSectionItemsBasedOnWidth);
    };
  }, []);

  return (
    <tbody className="text-sm">
      {columns.map((column, index) => (
        <tr key={index}>
          <td className="border-b border-gray-100 text-right px-2 py-2 text-sm">
            <div className="flex items-center gap-x-2">
              {' '}
              {column.id ? (
                <SvgIcon name="key" className="w-4 h-4 cursor-default" />
              ) : (
                <div className="w-4 h-4" />
              )}
              <Checkbox
                checked={!!checkedColumns?.find((x) => x === column.nameOfCol)}
                onChange={() => {
                  handleChangeCheckedColumns(column.nameOfCol ?? '');
                }}
                className="py-4"
              />
            </div>
          </td>
          <td className="border-b border-gray-100 text-left px-4 py-2">
            <QualityDimensionStatuses dimensions={column.dimentions} />
          </td>
          <td
            className="border-b border-gray-100 text-left px-4 py-2 underline cursor-pointer"
            onClick={() => navigate(column.nameOfCol ? column.nameOfCol : '')}
          >
            {column.nameOfCol}
          </td>
          <td className="border-b border-gray-100 text-left px-4 py-2">
            {column.labels}
          </td>
          <td className="border-b border-gray-100 text-left px-4 py-2">
            <div key={index} className="truncate">
              {getDetectedDatatype(column.detectedDatatypeVar)}
            </div>
          </td>
          <td className="border-b border-gray-100 text-left px-4 py-2">
            {limitTextLength(column.importedDatatype, 15)}
          </td>
          {!items.includes('Length') && (
            <td className="border-b border-gray-100 text-right px-4 py-2">
              <span className="float-right">{column.length}</span>
            </td>
          )}
          {!items.includes('Scale') && (
            <td className="border-b border-gray-100 text-left px-4 py-2">
              <span className="float-right">{column.scale}</span>
            </td>
          )}
          {!items.includes('Min value') && (
            <td className="border-b border-gray-100 text-left px-4 py-2">
              <div key={index} className="text-right float-right">
                {column.minimalValue
                  ? dateToString(String(column.minimalValue))
                    ? dateToString(String(column.minimalValue))
                    : cutString(String(column.minimalValue))
                  : ''}
              </div>
            </td>
          )}
          {!items.includes('Max value') && (
            <td className="border-b border-gray-100 text-left px-4 py-2">
              <div key={index} className="text-right float-right">
                {column.maximumValue
                  ? dateToString(String(column.maximumValue))
                    ? dateToString(String(column.maximumValue))
                    : cutString(String(column.maximumValue))
                  : ''}
              </div>
            </td>
          )}
          {!items.includes('Nulls count') && (
            <td className="border-b border-gray-100 text-left px-4 py-2">
              <div key={index} className="text-right float-right">
                {isNaN(Number(column.null_count)) ? '' : column.null_count}
              </div>
            </td>
          )}
          <td className="border-b border-gray-100 text-right px-4 py-2">
            <div className="flex justify-center items-center">
              <div className="flex items-center w-12">
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
                  ripple={false}
                  size="sm"
                  disabled={userProfile.can_collect_statistics !== true}
                  className={
                    filteredColumns?.find((x) => x === column.nameOfCol)
                      ? 'group bg-gray-400 ml-1.5 !shadow-none hover:!shadow-none '
                      : 'group bg-teal-500 ml-1.5 !shadow-none hover:!shadow-none hover:bg-[#028770]'
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
                  ripple={false}
                  size="sm"
                  className="group bg-teal-500 ml-3 !shadow-none hover:!shadow-none hover:bg-[#028770]"
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
