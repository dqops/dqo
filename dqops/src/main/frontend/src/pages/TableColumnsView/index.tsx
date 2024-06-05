import { AxiosResponse } from 'axios';
import React, { useEffect, useMemo, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import {
  DataGroupingConfigurationSpec,
  DataGroupingDimensionSpec,
  DqoJobHistoryEntryModelStatusEnum,
  TableColumnsStatisticsModel
} from '../../api';
import Button from '../../components/Button';
import SvgIcon from '../../components/SvgIcon';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import { IRootState } from '../../redux/reducers';
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
    connection: connectionName,
    schema: schemaName,
    table: tableName
  }: { connection: string; schema: string; table: string } = useDecodedParams();
  const { job_dictionary_state, userProfile } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const dispatch = useDispatch();
  const history = useHistory();
  const [statistics, setStatistics] = useState<TableColumnsStatisticsModel>();
  const [checkedColumns, setCheckedColumns] = useState<string[]>([]);
  const [jobId, setJobId] = useState<number>();

  const job = jobId ? job_dictionary_state[jobId] : undefined;

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

  const collectStatistics = async () => {
    try {
      await JobApiClient.collectStatisticsOnTable(undefined, false, undefined, {
        connection: connectionName,
        fullTableName: schemaName + '.' + tableName,
        enabled: true,
        columnNames: checkedColumns
      }).then((res) => setJobId(res.data.jobId?.jobId));
    } catch (err) {
      console.error(err);
    }
  };

  const filteredJobs = useMemo(() => {
    return (
      job &&
      (job.status === DqoJobHistoryEntryModelStatusEnum.running ||
        job.status === DqoJobHistoryEntryModelStatusEnum.queued ||
        job.status === DqoJobHistoryEntryModelStatusEnum.waiting)
    );
  }, [job]);

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
  }, [connectionName, schemaName, tableName]);
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
              label="Create Data Grouping"
              color="primary"
              onClick={postDataStream}
              disabled={userProfile.can_manage_data_sources !== true}
            />
          )}
          {checkedColumns.length > 9 && (
            <div className="flex items-center gap-x-2 justify-center text-red-500">
              (You can choose max 9 columns)
              <Button
                label="Create Data Grouping"
                color="secondary"
                className="text-black "
              />
            </div>
          )}
          <Button
            className="flex items-center gap-x-2 justify-center"
            label={
              filteredJobs
                ? 'Collecting...'
                : checkedColumns.length !== 0
                ? 'Collect statistics on selected'
                : 'Collect statistics'
            }
            color={filteredJobs ? 'secondary' : 'primary'}
            leftIcon={
              filteredJobs ? <SvgIcon name="sync" className="w-4 h-4" /> : ''
            }
            onClick={collectStatistics}
            disabled={userProfile.can_collect_statistics !== true}
          />
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
