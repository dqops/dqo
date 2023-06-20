import React, { useEffect, useState } from 'react';
import SvgIcon from '../../components/SvgIcon';
import TableColumns from './TableColumns';
import { useHistory, useParams } from 'react-router-dom';
import ConnectionLayout from '../../components/ConnectionLayout';
import Button from '../../components/Button';
import {
  ColumnApiClient,
  JobApiClient,
  DataStreamsApi
} from '../../services/apiClient';
import {
  DqoJobHistoryEntryModelStatusEnum,
  TableColumnsStatisticsModel
} from '../../api';
import { AxiosResponse } from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import { ROUTES, CheckTypes } from '../../shared/routes';
import { LocationState } from './TableColumnsFunctions';

const TableColumnsView = () => {
  const {
    connection: connectionName,
    schema: schemaName,
    table: tableName
  }: { connection: string; schema: string; table: string } = useParams();
  const { job_dictionary_state, dataStreamButton, dataStreamName, spec } =
    useSelector((state: IRootState) => state.job || {});
  const dispatch = useDispatch();
  const history = useHistory();
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
  console.log(dataStreamName);
  console.log(spec);
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
  const doNothing = (): void => {};
  const postDataStream = async () => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      'sources',
      connectionName,
      schemaName,
      tableName,
      'data-streams'
    );
    const value = ROUTES.TABLE_LEVEL_VALUE(
      'sources',
      connectionName,
      schemaName,
      tableName
    );
    const data: LocationState = {
      bool: true,
      data_stream_name: dataStreamName,
      spec: spec
    };

    try {
      const response = await DataStreamsApi.createDataStream(
        connectionName,
        schemaName,
        tableName,
        { data_stream_name: dataStreamName, spec: spec }
      );
      if (response.status === 409) {
        doNothing();
      }
    } catch (error: any) {
      if (error.response && error.response.status) {
        doNothing();
      }
    }
    dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url,
        value,
        state: data,
        label: tableName
      })
    );
    history.push(url);
  };

  return (
    <ConnectionLayout>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2 max-w-full">
          <SvgIcon name="column" className="w-5 h-5 shrink-0" />
          <div className="text-xl font-semibold truncate">{`${connectionName}.${schemaName}.${tableName} columns`}</div>
        </div>
        {dataStreamButton == 1 && (
          <Button
            label="create data stream"
            color="primary"
            onClick={postDataStream}
          />
        )}
        {dataStreamButton == 2 && (
          <div className="flex text-red-500 items-center gap-x-4 absolute top-0 right-4 px-2">
            (You can choose max 9 columns)
            <Button
              label="Create Data Stream"
              color="secondary"
              className="text-black "
            />
          </div>
        )}
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
