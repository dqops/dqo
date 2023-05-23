import React, { useEffect, useState } from 'react';
import SvgIcon from '../../components/SvgIcon';
import TableColumns from './TableColumns';
import { useParams } from "react-router-dom";
import ConnectionLayout from "../../components/ConnectionLayout";
import Button from '../../components/Button';
import { ColumnApiClient, JobApiClient } from '../../services/apiClient';
import { TableColumnsStatisticsModel } from '../../api';
import { AxiosResponse } from 'axios';

const TableColumnsView = () => {
  const { connection: connectionName, schema: schemaName, table: tableName }: { connection: string, schema: string, table: string } = useParams();
  const [loadingJob, setLoadingJob] = useState(false)
  const [statistics, setStatistics] = useState<TableColumnsStatisticsModel>();
  const fetchColumns = async () => {
    try {
      const res: AxiosResponse<TableColumnsStatisticsModel> =
        await ColumnApiClient.getColumnsStatistics(connectionName, schemaName, tableName);
      setStatistics(res.data);
    } catch (err) {
      console.error(err);
    }
  };
  
  useEffect(() => {
    fetchColumns().then();
  }, [connectionName, schemaName, tableName]);

  const collectStatistics = async () => {
    try {
      setLoadingJob(true);
      await JobApiClient.collectStatisticsOnTable(statistics?.collect_column_statistics_job_template);
    } finally {
      setLoadingJob(false);
    }
  };

  return (
    <ConnectionLayout>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2 max-w-full" >
          <SvgIcon name="column" className="w-5 h-5 shrink-0" />
          <div className="text-xl font-semibold truncate">{`${connectionName}.${schemaName}.${tableName} columns`}</div>
        </div>
        <Button label='Collect Statistic' color='primary' onClick={() => collectStatistics()}/>
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
