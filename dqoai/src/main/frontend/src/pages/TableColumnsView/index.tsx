import React, { useEffect, useState } from 'react';
import SvgIcon from '../../components/SvgIcon';
import TableColumns from './TableColumns';
import { useParams } from 'react-router-dom';
import ConnectionLayout from '../../components/ConnectionLayout';
import Button from '../../components/Button';
import { ColumnApiClient, JobApiClient } from '../../services/apiClient';
import {
  DqoJobHistoryEntryModelStatusEnum,
  TableColumnsStatisticsModel
} from '../../api';
import { AxiosResponse } from 'axios';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { getCollectingJobs } from '../../redux/actions/job.actions';
import { useActionDispatch } from '../../hooks/useActionDispatch';

const TableColumnsView = () => {
  const {
    connection: connectionName,
    schema: schemaName,
    table: tableName
  }: { connection: string; schema: string; table: string } = useParams();
  const { jobs, isCollecting } = useSelector(
    (state: IRootState) => state.job || {}
  );

  const dispatch = useActionDispatch();
  const [loadingJob, setLoadingJob] = useState(false);
  const [statistics, setStatistics] = useState<TableColumnsStatisticsModel>();

  const setCollecting = (bool: boolean) => {
    dispatch(getCollectingJobs(bool));
  };

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
    fetchColumns();
    filterJobs();
  }, [connectionName, schemaName, tableName, jobs?.jobs]);

  const collectStatistics = async () => {
    try {
      setLoadingJob(true);
      setCollecting(true);
      await JobApiClient.collectStatisticsOnTable(
        statistics?.collect_column_statistics_job_template
      );
    } finally {
      setLoadingJob(false);
    }
  };

  const filterJobs = () => {
    const filteredJobs = jobs?.jobs?.filter(
      (x) =>
        x.jobType === 'collect statistics' &&
        x.parameters?.collectStatisticsParameters
          ?.statisticsCollectorSearchFilters?.schemaTableName ===
          schemaName + '.' + tableName &&
        (x.status === DqoJobHistoryEntryModelStatusEnum.running ||
          x.status === DqoJobHistoryEntryModelStatusEnum.queued ||
          x.status === DqoJobHistoryEntryModelStatusEnum.waiting)
    );

    if (filteredJobs && filteredJobs.length > 0) {
      console.log('in');
      setCollecting(true);
      console.log(isCollecting);
    } else {
      console.log('out');
      setCollecting(false);
    }
  };

  return (
    <ConnectionLayout>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2 max-w-full">
          <SvgIcon name="column" className="w-5 h-5 shrink-0" />
          <div className="text-xl font-semibold truncate">{`${connectionName}.${schemaName}.${tableName} columns`}</div>
        </div>
        <Button
          className="flex items-center gap-x-2 justify-center"
          label={isCollecting ? 'Collecting...' : 'Collect Statistic'}
          color={isCollecting ? 'secondary' : 'primary'}
          leftIcon={
            isCollecting ? <SvgIcon name="sync" className="w-4 h-4" /> : ''
          }
          onClick={() => {
            collectStatistics();
          }}
          loading={loadingJob}
        />
      </div>
      <div>
        <TableColumns
          connectionName={connectionName}
          schemaName={schemaName}
          tableName={tableName}
        />
      </div>
    </ConnectionLayout>
  );
};

export default TableColumnsView;
