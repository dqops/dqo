import React, { useEffect, useMemo, useState } from 'react';

import { useSelector } from 'react-redux';
import TableActionGroup from './TableActionGroup';

import { useHistory } from 'react-router-dom';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import TableProfilingChecks from '../../../pages/TableProfilingChecks';
import {
  getTableProfilingChecksModel,
  updateTableProfilingChecksModel
} from '../../../redux/actions/table.actions';
import {
  getFirstLevelActiveTab,
  getFirstLevelState,
  getSecondLevelTab
} from '../../../redux/selectors';
import { CheckTypes, ROUTES } from '../../../shared/routes';

import Tabs from '../../Tabs';

import {
  DataGroupingConfigurationSpec,
  DqoJobHistoryEntryModelStatusEnum,
  TableColumnsStatisticsModel,
  TableStatisticsModel
} from '../../../api';
import TableStatisticsView from '../../../pages/TableStatisticsView';

import { AxiosResponse } from 'axios';
import { setCreatedDataStream } from '../../../redux/actions/definition.actions';
import { addFirstLevelTab, setActiveFirstLevelUrl } from '../../../redux/actions/source.actions';
import { IRootState } from '../../../redux/reducers';
import {
  ColumnApiClient,
  DataGroupingConfigurationsApi,
  JobApiClient,
  TableApiClient
} from '../../../services/apiClient';
import { TABLE_LEVEL_TABS } from '../../../shared/constants';
import { TableReferenceComparisons } from './TableComparison/TableReferenceComparisons';
import TablePreview from './TablePreview';
import TableQualityStatus from './TableQualityStatus/TableQualityStatus';
import { useDecodedParams } from '../../../utils';
interface LocationState {
  bool: boolean;
  data_stream_name: string;
  spec: DataGroupingConfigurationSpec;
}
const ProfilingView = () => {
  const {
    checkTypes,
    connection: connectionName,
    schema: schemaName,
    table: tableName,
    tab
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
    tab: string;
  } = useDecodedParams();

  const { checksUI, isUpdating, isUpdatedChecksUi, tableBasic } = useSelector(
    getFirstLevelState(checkTypes)
  );
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const activeTab = getSecondLevelTab(checkTypes, tab);
  const { job_dictionary_state } = useSelector(
    (state: IRootState) => state.job || {}
  );

  const [nameOfDataStream, setNameOfDataStream] = useState<string>('');
  const [levels, setLevels] = useState<DataGroupingConfigurationSpec>({});
  const [selected, setSelected] = useState<number>(0);
  const history = useHistory();
  const [statistics, setStatistics] = useState<TableColumnsStatisticsModel>();
  const [selectedColumns, setSelectedColumns] = useState<Array<string>>();
  const [jobId, setJobId] = useState<number>();
  const [rowCount, setRowCount] = useState<TableStatisticsModel>();

  const job = jobId ? job_dictionary_state[jobId] : undefined;

  const fetchColumns = async () => {
    try {
      await ColumnApiClient.getColumnsStatistics(
        connectionName,
        schemaName,
        tableName
      ).then((res) => setStatistics(res.data));
    } catch (err) {
      console.error(err);
    }
  };

  const onChangeSelectedColumns = (columns: string[]): void => {
    setSelectedColumns(columns);
  };

  const fetchRows = async () => {
    try {
      const res: AxiosResponse<TableStatisticsModel> =
        await TableApiClient.getTableStatistics(
          connectionName,
          schemaName,
          tableName
        );
      setRowCount(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    if (activeTab === 'statistics' || activeTab === 'preview') {
      fetchColumns();
      fetchRows();
    }
  }, [connectionName, schemaName, tableName, activeTab]);

  useEffect(() => {
    dispatch(
      getTableProfilingChecksModel(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName
      )
    );
  }, [
    checkTypes,
    firstLevelActiveTab,
    connectionName,
    schemaName,
    tableName,
    tableBasic
  ]);

  const onUpdate = async () => {
    if (!checksUI) {
      return;
    }
    await dispatch(
      updateTableProfilingChecksModel(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        checksUI
      )
    );
    await dispatch(
      getTableProfilingChecksModel(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        false
      )
    );
  };

  const updateData2 = (nameOfDS: string): void => {
    setNameOfDataStream(nameOfDS);
  };

  const setLevelsData2 = (levelsToSet: DataGroupingConfigurationSpec): void => {
    setLevels(levelsToSet);
  };

  const setNumberOfSelected2 = (param: number): void => {
    setSelected(param);
  };

  const doNothing = (): void => {};

  const postDataStream = async () => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      'sources',
      connectionName,
      schemaName,
      tableName,
      'data-groupings'
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

  const onChangeTab = (tab: string) => {
    dispatch(
      setActiveFirstLevelUrl(
        checkTypes,
        firstLevelActiveTab,
        ROUTES.TABLE_LEVEL_PAGE(checkTypes, connectionName, schemaName, tableName, tab)
      )
    );
    history.push(
      ROUTES.TABLE_LEVEL_PAGE(
        checkTypes,
        connectionName,
        schemaName,
        tableName,
        tab
      )
    );
  };

  const collectStatistics = async () => {
      await JobApiClient.collectStatisticsOnTable(undefined, false, undefined, {
        ...statistics?.collect_column_statistics_job_template,
        columnNames: selectedColumns
      }).then((res) => setJobId(res.data.jobId?.jobId));
  };
 
  const filteredCollectStatisticsJobs = useMemo(() => {
    return (job && (
      job.status === DqoJobHistoryEntryModelStatusEnum.running ||
      job.status === DqoJobHistoryEntryModelStatusEnum.queued ||
      job.status === DqoJobHistoryEntryModelStatusEnum.waiting ))
    }, [job])

    
  useEffect(() => {
    if (job && job?.status === DqoJobHistoryEntryModelStatusEnum.finished) {
      fetchRows();
      fetchColumns();
    }
  }, [job]);

  return (
    <div className="flex-grow min-h-0 flex flex-col">
      <div className="border-b border-gray-300">
        <Tabs tabs={TABLE_LEVEL_TABS[CheckTypes.PROFILING]} activeTab={activeTab} onChange={onChangeTab} />
      </div>
      {activeTab === 'statistics' && (
        <TableActionGroup
          shouldDelete={false}
          onUpdate={onUpdate}
          isUpdated={isUpdatedChecksUi}
          isUpdating={isUpdating}
          addSaveButton={false}
          createDataStream={selected > 0 && selected <= 9 && true}
          createDataStreamFunc={postDataStream}
          maxToCreateDataStream={selected > 9 && true}
          collectStatistics = {collectStatistics}
          selectedColumns={selectedColumns && selectedColumns?.length > 0}
          collectStatisticsSpinner={filteredCollectStatisticsJobs}
        />
      )}
      {activeTab === 'advanced' && (
        <TableActionGroup
          shouldDelete={false}
          onUpdate={onUpdate}
          isUpdated={isUpdatedChecksUi}
          isUpdating={isUpdating}
        />
      )}
      {activeTab === 'statistics' && (
        <TableStatisticsView
          connectionName={connectionName}
          schemaName={schemaName}
          tableName={tableName}
          updateData2={updateData2}
          setLevelsData2={setLevelsData2}
          setNumberOfSelected2={setNumberOfSelected2}
          statistics={statistics}
          onChangeSelectedColumns={onChangeSelectedColumns}
          refreshListFunc={fetchColumns}
          rowCount={rowCount ?? {}}
        />
      )}
      {activeTab === 'preview' && (
        <TablePreview statistics={statistics ?? {}} />
      )}
      {activeTab === 'table-quality-status' && <TableQualityStatus />}
      {activeTab === 'advanced' && <TableProfilingChecks />}
      {activeTab === 'table-comparisons' && (
        <TableReferenceComparisons
          checkTypes={checkTypes}
          checksUI={checksUI}
          onUpdateChecks={onUpdate}
        />
      )}
    </div>
  );
};

export default ProfilingView;
