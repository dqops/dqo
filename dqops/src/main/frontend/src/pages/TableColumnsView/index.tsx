import React, { useEffect, useState } from 'react';
import SvgIcon from '../../components/SvgIcon';
import TableColumns from './TableColumns';
import { useHistory, useParams } from 'react-router-dom';
import ConnectionLayout from '../../components/ConnectionLayout';
import Button from '../../components/Button';
import {
  ColumnApiClient,
  JobApiClient,
  DataGroupingConfigurationsApi
} from '../../services/apiClient';
import {
  DataGroupingConfigurationSpec,
  DqoJobHistoryEntryModelStatusEnum,
  TableColumnsStatisticsModel
} from '../../api';
import { AxiosResponse } from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import { ROUTES, CheckTypes } from '../../shared/routes';
import { setCreatedDataStream } from '../../redux/actions/definition.actions';

interface LocationState {
  bool: boolean;
  data_stream_name: string;
  spec: DataGroupingConfigurationSpec;
}

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
  const [levels, setLevels] = useState<DataGroupingConfigurationSpec>({});
  const [selected, setSelected] = useState<number>(0);
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
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

  const setLevelsData = (levelsToSet: DataGroupingConfigurationSpec): void => {
    setLevels(levelsToSet);
  };

  const setNumberOfSelected = (param: number): void => {
    setSelected(param);
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
      const response =
        await DataGroupingConfigurationsApi.createTableGroupingConfiguration(
          connectionName,
          schemaName,
          tableName,
          { data_grouping_configuration_name: nameOfDataStream, spec: levels }
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
              label="Create Data Grouping"
              color="primary"
              onClick={postDataStream}
              disabled={userProfile.can_manage_data_sources !== true}
            />
          )}
          {selected > 9 && (
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
            disabled={userProfile.can_collect_statistics  !== true}
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
          statistics={statistics}
        />
      </div>
    </ConnectionLayout>
  );
};

export default TableColumnsView;
