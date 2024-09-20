import { Tooltip } from '@material-tailwind/react';
import { AxiosResponse } from 'axios';
import React, { useEffect, useMemo, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import {
  DataGroupingConfigurationSpec,
  DataGroupingDimensionSpec,
  DqoJobHistoryEntryModelStatusEnum,
  TableColumnsStatisticsModel
} from '../../api';
import Button from '../../components/Button';
import SvgIcon from '../../components/SvgIcon';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setJobAllert } from '../../redux/actions/job.actions';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import { IRootState } from '../../redux/reducers';
import { getFirstLevelActiveTab } from '../../redux/selectors';
import {
  ColumnApiClient,
  DataGroupingConfigurationsApi,
  JobApiClient
} from '../../services/apiClient';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import TableColumns from './TableColumns';

const TableColumnsView = () => {
  const {
    checkTypes,
    connection: connectionName,
    schema: schemaName,
    table: tableName
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
  } = useDecodedParams();
  const { job_dictionary_state, userProfile, job_allert } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const dispatch = useActionDispatch();
  const history = useHistory();
  const [statistics, setStatistics] = useState<TableColumnsStatisticsModel>();
  const [isCollected, setIsCollected] = useState<boolean>(false);
  const [checkedColumns, setCheckedColumns] = useState<string[]>([]);
  const [jobId, setJobId] = useState<number>();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const job = jobId ? job_dictionary_state[jobId] : undefined;

  const fetchColumns = async () => {
    try {
      const res: AxiosResponse<TableColumnsStatisticsModel> =
        await ColumnApiClient.getColumnsStatistics(
          connectionName,
          schemaName,
          tableName
        );
      if (
        !res.data.column_statistics?.find(
          (column) => column.statistics && column.statistics.length > 0
        ) &&
        !isCollected
      ) {
        dispatch(
          setJobAllert({
            activeTab: firstLevelActiveTab,
            action: 'collect_statistics',
            tooltipMessage:
              'The table has no results of basic statistics, please collect the statistics'
          })
        );
      }
      setStatistics(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const collectStatistics = async () => {
    dispatch(setJobAllert({}));
    try {
      await JobApiClient.collectStatisticsOnTable(undefined, false, undefined, false, undefined, {
        connection: connectionName,
        fullTableName: schemaName + '.' + tableName,
        enabled: true,
        columnNames: checkedColumns
      }).then((res) => {
        setIsCollected(true);
        dispatch(setJobAllert({}));
        setJobId(res.data.jobId?.jobId);
      });
    } catch (err) {
      console.error(err);
    }
  };

  const isCollectingStatistics = useMemo(() => {
    return (
      job &&
      job.parameters?.collectStatisticsParameters
        ?.statistics_collector_search_filters?.connection === connectionName &&
      job.parameters?.collectStatisticsParameters
        ?.statistics_collector_search_filters?.fullTableName ===
        schemaName + '.' + tableName &&
      (job.status === DqoJobHistoryEntryModelStatusEnum.running ||
        job.status === DqoJobHistoryEntryModelStatusEnum.queued ||
        job.status === DqoJobHistoryEntryModelStatusEnum.waiting)
    );
  }, [job, checkTypes, connectionName, schemaName, tableName]);

  const postDataStream = async () => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      CheckTypes.SOURCES,
      connectionName,
      schemaName,
      tableName,
      'data-groupings'
    );
    const value = ROUTES.TABLE_LEVEL_VALUE(
      CheckTypes.SOURCES,
      connectionName,
      schemaName,
      tableName
    );
    const data_grouping_configuration_name = checkedColumns.join(',');
    const levels: DataGroupingConfigurationSpec = {};
    checkedColumns.forEach((column, index) => {
      const key: keyof DataGroupingConfigurationSpec = `level_${
        index + 1
      }` as keyof DataGroupingConfigurationSpec;
      const value: DataGroupingDimensionSpec = {
        source: 'column_value',
        column: column
      };
      levels[key] = value;
      return;
    });
    try {
      await DataGroupingConfigurationsApi.createTableGroupingConfiguration(
        connectionName,
        schemaName,
        tableName,
        { data_grouping_configuration_name, spec: levels }
      );
    } catch (err) {
      console.error(err);
    }
    dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url,
        value,
        state: {
          levels
        },
        label: tableName
      })
    );
    history.push(url);
  };

  useEffect(() => {
    if (job && job?.status === DqoJobHistoryEntryModelStatusEnum.finished) {
      fetchColumns();
    }
  }, [job]);

  useEffect(() => {
    fetchColumns();
  }, [checkTypes, connectionName, schemaName, tableName]);

  return (
    <>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2 max-w-full">
          <SvgIcon name="column" className="w-5 h-5 shrink-0" />
          <div className="text-lg font-semibold truncate">{`${connectionName}.${schemaName}.${tableName} columns`}</div>
        </div>
        <div className="flex items-center gap-x-2 justify-center">
          {checkedColumns.length !== 0 && checkedColumns.length <= 9 && (
            <Button
              label="Create data grouping"
              color="primary"
              onClick={postDataStream}
              disabled={userProfile.can_manage_data_sources !== true}
            />
          )}
          {checkedColumns.length > 9 && (
            <div className="flex items-center gap-x-2 justify-center text-red-500">
              (You can choose max 9 columns)
              <Button
                label="Create data grouping"
                color="secondary"
                className="text-black "
              />
            </div>
          )}
          <Tooltip
            content={job_allert.tooltipMessage}
            className={
              job_allert.tooltipMessage
                ? 'max-w-60 py-2 px-2 bg-gray-800'
                : 'hidden'
            }
          >
            <div>
              <Button
                className="flex items-center gap-x-2 justify-center !h-[37px]"
                label={
                  isCollectingStatistics
                    ? 'Collecting...'
                    : checkedColumns.length !== 0
                    ? 'Collect statistics on selected'
                    : 'Collect statistics'
                }
                color={isCollectingStatistics ? 'secondary' : 'primary'}
                leftIcon={
                  isCollectingStatistics ? (
                    <SvgIcon name="sync" className="w-4 h-4 animate-spin" />
                  ) : (
                    ''
                  )
                }
                onClick={collectStatistics}
                disabled={userProfile.can_collect_statistics !== true}
                flashRedBorder={
                  firstLevelActiveTab === job_allert.activeTab &&
                  job_allert.action === 'collect_statistics'
                    ? true
                    : false
                }
              />
            </div>
          </Tooltip>
        </div>
      </div>
      <div>
        <TableColumns
          connectionName={connectionName}
          schemaName={schemaName}
          tableName={tableName}
          checkedColumns={checkedColumns}
          setCheckedColumns={setCheckedColumns}
          statistics={statistics}
          refreshListFunc={fetchColumns}
        />
      </div>
    </>
  );
};

export default TableColumnsView;
