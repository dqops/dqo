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

const TableColumnsView = () => {
  const {
    connection: connectionName,
    schema: schemaName,
    table: tableName
  }: { connection: string; schema: string; table: string } = useParams();
  const { job_dictionary_state } = useSelector(
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

  return (
    <ConnectionLayout>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2 max-w-full">
          <SvgIcon name="column" className="w-5 h-5 shrink-0" />
          <div className="text-xl font-semibold truncate">{`${connectionName}.${schemaName}.${tableName} columns`}</div>
        </div>

        <Button
          className="flex items-center gap-x-2 justify-center"
          label={
            filteredJobs?.find(
              (x) =>
                x.parameters?.collectStatisticsParameters
                  ?.statisticsCollectorSearchFilters?.schemaTableName ===
                schemaName + '.' + tableName
            )
              ? 'Collecting...'
              : 'Collect Statistic'
          }
          color={
            filteredJobs?.find(
              (x) =>
                x.parameters?.collectStatisticsParameters
                  ?.statisticsCollectorSearchFilters?.schemaTableName ===
                schemaName + '.' + tableName
            )
              ? 'secondary'
              : 'primary'
          }
          leftIcon={
            filteredJobs?.find(
              (x) =>
                x.parameters?.collectStatisticsParameters
                  ?.statisticsCollectorSearchFilters?.schemaTableName ===
                schemaName + '.' + tableName
            ) ? (
              <SvgIcon name="sync" className="w-4 h-4" />
            ) : (
              ''
            )
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
