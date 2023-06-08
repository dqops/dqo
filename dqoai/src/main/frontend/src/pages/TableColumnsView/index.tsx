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

const TableColumnsView = () => {
  const {
    connection: connectionName,
    schema: schemaName,
    table: tableName
  }: { connection: string; schema: string; table: string } = useParams();
  const { jobs, isCollecting } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const [loadingJob, setLoadingJob] = useState(false);
  const [statistics, setStatistics] = useState<TableColumnsStatisticsModel>();
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
    workInProgress2();
  }, [connectionName, schemaName, tableName]);

  const collectStatistics = async () => {
    try {
      setLoadingJob(true);
      await JobApiClient.collectStatisticsOnTable(
        statistics?.collect_column_statistics_job_template
      );
    } finally {
      setLoadingJob(false);
    }
  };
  // console.log(jobs);
  // console.log(tableName);
  // console.log();

  const workInProgress = () => {
    jobs?.jobs &&
      jobs?.jobs.map((x) =>
        x.jobType === 'collect statistics' &&
        x.parameters?.collectStatisticsParameters
          ?.statisticsCollectorSearchFilters?.schemaTableName ===
          schemaName + '.' + tableName &&
        (x.status === DqoJobHistoryEntryModelStatusEnum.running ||
          x.status === DqoJobHistoryEntryModelStatusEnum.queued ||
          x.status === DqoJobHistoryEntryModelStatusEnum.waiting)
          ? console.log('in')
          : console.log('out')
      );
  };

  const workInProgress2 = () => {
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
      getCollectingJobs(true);
      console.log(isCollecting);
    } else {
      console.log('out');
    }
  };
  console.log(isCollecting);

  return (
    <ConnectionLayout>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2 max-w-full">
          <SvgIcon name="column" className="w-5 h-5 shrink-0" />
          <div
            className="text-xl font-semibold truncate"
            onClick={() => workInProgress2()}
          >{`${connectionName}.${schemaName}.${tableName} columns`}</div>
        </div>
        <Button
          label="Collect Statistic"
          color="primary"
          onClick={collectStatistics}
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
