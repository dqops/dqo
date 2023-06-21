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
  DataStreamMappingSpec,
  DqoJobHistoryEntryModelStatusEnum,
  TableColumnsStatisticsModel
} from '../../api';
import { AxiosResponse } from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import { ROUTES, CheckTypes } from '../../shared/routes';
import { LocationState } from './TableColumnsFunctions';
import { setCreatedDataStream } from '../../redux/actions/rule.actions';

const TableColumnsView = () => {
  const {
    connection: connectionName,
    schema: schemaName,
    table: tableName
  }: { connection: string; schema: string; table: string } = useParams();
  const { job_dictionary_state } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const dispatch = useDispatch();
  const history = useHistory();
  const [loadingJob, setLoadingJob] = useState(false);
  const [statistics, setStatistics] = useState<TableColumnsStatisticsModel>();
  const [nameOfDataStream, setNameOfDataStream] = useState<string>('');
  const [levels, setLevels] = useState<DataStreamMappingSpec>({});
  const [selected, setSelected] = useState<number>(0);
  const [stringCount, setStringCount] = useState(0);

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

  const updateData = (nameOfDS: string): void => {
    setNameOfDataStream(nameOfDS);
  };

  const setLevelsData = (levelsToSet: DataStreamMappingSpec): void => {
    setLevels(levelsToSet);
  };

  const setNumberOfSelected = (param: number): void => {
    setSelected(param);
  };

  useEffect(() => {
    fetchColumns();
    implementDataStreamName();
  }, [connectionName, schemaName, tableName, levels]);

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
      data_stream_name: nameOfDataStream,
      spec: levels
    };

    try {
      const response = await DataStreamsApi.createDataStream(
        connectionName,
        schemaName,
        tableName,
        { data_stream_name: nameOfDataStream, spec: levels }
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
    setCreatedDataStream(false, '', {});
  };

  const implementDataStreamName = () => {
    let count = 0;
    const columnValues = Object.values(levels)
      .map((level) => level.column)
      .filter((column) => column !== undefined);
    const joinedValues = columnValues.join(',');
    count = columnValues.length;

    setStringCount(count);

    return joinedValues;
  };
  console.log(selected);
  return (
    <ConnectionLayout>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2 max-w-full">
          <SvgIcon name="column" className="w-5 h-5 shrink-0" />
          <div className="text-xl font-semibold truncate">{`${connectionName}.${schemaName}.${tableName} columns`}</div>
        </div>
        <div className="flex items-center gap-x-2 justify-center">
          {selected !== 0 && selected <= 9 && (
            <Button
              label="Create Data Stream"
              color="primary"
              onClick={postDataStream}
            />
          )}
          {selected > 9 && (
            <div className="flex items-center gap-x-2 justify-center text-red-500">
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
      </div>
      <div>
        <TableColumns
          connectionName={connectionName}
          schemaName={schemaName}
          tableName={tableName}
          updateData={updateData}
          setLevelsData={setLevelsData}
          setNumberOfSelected={setNumberOfSelected}
        />
      </div>
    </ConnectionLayout>
  );
};

export default TableColumnsView;
