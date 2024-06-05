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
  DataGroupingDimensionSpec,
  DqoJobHistoryEntryModelStatusEnum,
  TableColumnsStatisticsModel,
  TableStatisticsModel
} from '../../../api';
import TableStatisticsView from '../../../pages/TableStatisticsView';

import { AxiosResponse } from 'axios';
import {
  addFirstLevelTab,
  setActiveFirstLevelUrl
} from '../../../redux/actions/source.actions';
import { IRootState } from '../../../redux/reducers';
import {
  ColumnApiClient,
  DataGroupingConfigurationsApi,
  JobApiClient,
  TableApiClient
} from '../../../services/apiClient';
import { TABLE_LEVEL_TABS } from '../../../shared/constants';
import { useDecodedParams } from '../../../utils';
import { TableReferenceComparisons } from './TableComparison/TableReferenceComparisons';
import TablePreview from './TablePreview';
import TableQualityStatus from './TableQualityStatus/TableQualityStatus';

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

  const [checkedColumns, setCheckedColumns] = useState<string[]>([]);
  const history = useHistory();
  const [statistics, setStatistics] = useState<TableColumnsStatisticsModel>();
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
        state: { levels },
        label: tableName
      })
    );
    history.push(url);
  };

  const onChangeTab = (tab: string) => {
    dispatch(
      setActiveFirstLevelUrl(
        checkTypes,
        firstLevelActiveTab,
        ROUTES.TABLE_LEVEL_PAGE(
          checkTypes,
          connectionName,
          schemaName,
          tableName,
          tab
        )
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
      columnNames: checkedColumns
    }).then((res) => setJobId(res.data.jobId?.jobId));
  };

  const filteredCollectStatisticsJobs = useMemo(() => {
    return (
      job &&
      (job.status === DqoJobHistoryEntryModelStatusEnum.running ||
        job.status === DqoJobHistoryEntryModelStatusEnum.queued ||
        job.status === DqoJobHistoryEntryModelStatusEnum.waiting)
    );
  }, [job]);

  useEffect(() => {
    if (job && job?.status === DqoJobHistoryEntryModelStatusEnum.finished) {
      fetchRows();
      fetchColumns();
    }
  }, [job]);

  return (
    <div className="flex-grow min-h-0 flex flex-col">
      <div className="border-b border-gray-300">
        <Tabs
          tabs={TABLE_LEVEL_TABS[CheckTypes.PROFILING]}
          activeTab={activeTab}
          onChange={onChangeTab}
        />
      </div>
      {activeTab === 'statistics' && (
        <TableActionGroup
          shouldDelete={false}
          onUpdate={onUpdate}
          isUpdated={isUpdatedChecksUi}
          isUpdating={isUpdating}
          addSaveButton={false}
          createDataStream={
            checkedColumns.length > 0 && checkedColumns.length <= 9 && true
          }
          createDataStreamFunc={postDataStream}
          maxToCreateDataStream={checkedColumns.length > 9 && true}
          collectStatistics={collectStatistics}
          selectedColumns={checkedColumns && checkedColumns?.length > 0}
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
          checkedColumns={checkedColumns}
          setCheckedColumns={setCheckedColumns}
          schemaName={schemaName}
          tableName={tableName}
          statistics={statistics}
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
