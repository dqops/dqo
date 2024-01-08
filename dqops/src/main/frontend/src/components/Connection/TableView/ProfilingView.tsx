import React, { useEffect, useState } from 'react';

import TableActionGroup from './TableActionGroup';
import { useSelector } from 'react-redux';

import {
  getTableProfilingChecksModel,
  updateTableProfilingChecksModel
} from '../../../redux/actions/table.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { useHistory, useParams } from 'react-router-dom';
import {
  getFirstLevelActiveTab,
  getFirstLevelState,
  getSecondLevelTab
} from '../../../redux/selectors';
import { CheckTypes, ROUTES } from '../../../shared/routes';
import TableProfilingChecks from '../../../pages/TableProfilingChecks';

import Tabs from '../../Tabs';

import TableStatisticsView from '../../../pages/TableStatisticsView';
import {
  DataGroupingConfigurationSpec,
  DqoJobHistoryEntryModelJobTypeEnum,
  DqoJobHistoryEntryModelStatusEnum,
  TableColumnsStatisticsModel
} from '../../../api';

import { setCreatedDataStream } from '../../../redux/actions/definition.actions';
import { addFirstLevelTab, setActiveFirstLevelUrl } from '../../../redux/actions/source.actions';
import {
  ColumnApiClient,
  DataGroupingConfigurationsApi
} from '../../../services/apiClient';
import { TableReferenceComparisons } from './TableComparison/TableReferenceComparisons';
import { IRootState } from '../../../redux/reducers';
import TablePreview from './TablePreview';
import TableQualityStatus from './TableQualityStatus/TableQualityStatus';
import { TABLE_LEVEL_TABS } from '../../../shared/constants';
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
  } = useParams();

  const { checksUI, isUpdating, isUpdatedChecksUi, tableBasic } = useSelector(
    getFirstLevelState(checkTypes)
  );
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const activeTab = getSecondLevelTab(checkTypes, tab);

  const [nameOfDataStream, setNameOfDataStream] = useState<string>('');
  const [levels, setLevels] = useState<DataGroupingConfigurationSpec>({});
  const [selected, setSelected] = useState<number>(0);
  const history = useHistory();
  const [statistics, setStatistics] = useState<TableColumnsStatisticsModel>();
  const [selectedColumns, setSelectedColumns] = useState<Array<string>>();
  const [filteredJob, setFilteredJob] = useState<number>();

  const { job_dictionary_state } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const fetchColumns = async () => {
    try {
      await ColumnApiClient.getColumnsStatistics(
        connectionName,
        schemaName,
        tableName
      ).then((res) => setStatistics(res.data));
      setFilteredJob(undefined);
    } catch (err) {
      console.error(err);
    }
  };

  const onChangeSelectedColumns = (columns: string[]): void => {
    setSelectedColumns(columns);
  };

  useEffect(() => {
    if (activeTab === 'statistics' || activeTab === 'preview') {
      fetchColumns();
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

  useEffect(() => {
    setFilteredJob(
      Object.values(job_dictionary_state)?.find(
        (x) =>
          x.jobType === DqoJobHistoryEntryModelJobTypeEnum.collect_statistics &&
          x.parameters?.collectStatisticsParameters
            ?.statistics_collector_search_filters?.fullTableName ===
            schemaName + '.' + tableName &&
          (x.status === DqoJobHistoryEntryModelStatusEnum.running ||
            x.status === DqoJobHistoryEntryModelStatusEnum.queued ||
            x.status === DqoJobHistoryEntryModelStatusEnum.waiting)
      )?.jobId?.jobId
    );
    if (filteredJob) {
      if (
        job_dictionary_state[filteredJob ?? '']?.status ===
        DqoJobHistoryEntryModelStatusEnum.succeeded
      ) {
        fetchColumns();
      }
    }
  }, [job_dictionary_state]);

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
          collectStatistic={true}
          addSaveButton={false}
          createDataStream={selected > 0 && selected <= 9 && true}
          createDataStreamFunc={postDataStream}
          maxToCreateDataStream={selected > 9 && true}
          statistics={statistics}
          selectedColumns={selectedColumns}
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
