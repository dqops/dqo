import React, { useEffect, useMemo, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import {
  CheckContainerModel,
  CheckResultsOverviewDataModel,
  ColumnStatisticsModel,
  DqoJobHistoryEntryModelStatusEnum,
  TableStatisticsModel
} from '../../api';
import DataQualityChecks from '../../components/DataQualityChecks';
import Tabs from '../../components/Tabs';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getColumnProfilingChecksModel,
  setUpdatedChecksModel,
  updateColumnProfilingChecksModel
} from '../../redux/actions/column.actions';
import { setActiveFirstLevelUrl } from '../../redux/actions/source.actions';
import { IRootState } from '../../redux/reducers';
import {
  getFirstLevelActiveTab,
  getFirstLevelState,
  getSecondLevelTab
} from '../../redux/selectors';
import {
  CheckResultOverviewApi,
  ColumnApiClient,
  JobApiClient,
  TableApiClient
} from '../../services/apiClient';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import ColumnActionGroup from './ColumnActionGroup';
import ColumnStatisticsView from './ColumnStatisticsView';

const tabs = [
  {
    label: 'Basic data statistics',
    value: 'statistics'
  },
  {
    label: 'Profiling checks',
    value: 'advanced'
  }
];

interface IProfilingViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
  columnName: string;
}

const ColumnProfilingChecksView = ({
  connectionName,
  schemaName,
  tableName,
  columnName
}: IProfilingViewProps) => {
  const { checkTypes, tab }: { checkTypes: CheckTypes; tab: string } =
    useDecodedParams();
  const { checksUI, isUpdating, isUpdatedChecksUi, loading } = useSelector(
    getFirstLevelState(checkTypes)
  );
  const activeTab = getSecondLevelTab(checkTypes, tab);
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const { job_dictionary_state } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const dispatch = useActionDispatch();
  const [checkResultsOverview, setCheckResultsOverview] = useState<
    CheckResultsOverviewDataModel[]
  >([]);
  const [columnStatistics, setColumnStatistics] =
    useState<ColumnStatisticsModel>();
  const [tableStatistics, setTableStatistics] =
    useState<TableStatisticsModel>();
  const [jobId, setJobId] = useState<number>();

  const job = jobId ? job_dictionary_state[jobId] : undefined;

  const history = useHistory();

  const getCheckOverview = () => {
    CheckResultOverviewApi.getColumnProfilingChecksOverview(
      connectionName,
      schemaName,
      tableName,
      columnName
    ).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  const getColumnStatistics = () => {
    ColumnApiClient.getColumnStatistics(
      connectionName,
      schemaName,
      tableName,
      columnName
    ).then((res) => {
      setColumnStatistics(res.data);
    });
  };

  const getTableStatistics = async () => {
    try {
      await TableApiClient.getTableStatistics(
        connectionName,
        schemaName,
        tableName
      ).then((res) => setTableStatistics(res.data));
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    getTableStatistics();
    getColumnStatistics();
  }, [
    checkTypes,
    firstLevelActiveTab,
    connectionName,
    schemaName,
    columnName,
    tableName
  ]);

  useEffect(() => {
    dispatch(
      getColumnProfilingChecksModel(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        columnName
      )
    );
  }, [
    checkTypes,
    firstLevelActiveTab,
    connectionName,
    schemaName,
    columnName,
    tableName
  ]);

  const onUpdate = async () => {
    if (!checksUI) {
      return;
    }
    await dispatch(
      updateColumnProfilingChecksModel(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        columnName,
        checksUI
      )
    );
    await dispatch(
      getColumnProfilingChecksModel(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        columnName,
        false
      )
    );
  };

  const handleChange = (value: CheckContainerModel) => {
    dispatch(setUpdatedChecksModel(checkTypes, firstLevelActiveTab, value));
  };

  const onCollectStatistics = async () => {
    await JobApiClient.collectStatisticsOnTable(
      undefined,
      false,
      undefined,
      columnStatistics?.collect_column_statistics_job_template
    ).then((res) => setJobId(res.data.jobId?.jobId));
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
      getColumnStatistics();
      getTableStatistics();
    }
  }, [job]);

  const onChangeTab = (tab: string) => {
    dispatch(
      setActiveFirstLevelUrl(
        checkTypes,
        firstLevelActiveTab,
        ROUTES.COLUMN_LEVEL_PAGE(
          checkTypes,
          connectionName,
          schemaName,
          tableName,
          columnName,
          tab
        )
      )
    );
    history.push(
      ROUTES.COLUMN_LEVEL_PAGE(
        checkTypes,
        connectionName,
        schemaName,
        tableName,
        columnName,
        tab
      )
    );
  };

  return (
    <div className="flex flex-col overflow-x-auto overflow-y-hidden">
      <ColumnActionGroup
        shouldDelete={false}
        onUpdate={onUpdate}
        isUpdated={isUpdatedChecksUi}
        isUpdating={isUpdating}
        isStatistics={activeTab === 'statistics'}
        onCollectStatistics={onCollectStatistics}
        collectStatisticsSpinner={filteredCollectStatisticsJobs}
      />
      <div className="border-b border-gray-300">  
        <Tabs
          tabs={tabs}
          activeTab={activeTab}
          onChange={onChangeTab}
          className="w-full overflow-hidden max-w-full"
        />
      </div>
      {activeTab === 'statistics' && (
        <ColumnStatisticsView
          columnStatisticsProp={columnStatistics}
          tableStatisticsProp={tableStatistics}
        />
      )}
      {activeTab === 'advanced' && (
        <DataQualityChecks
          onUpdate={onUpdate}
          checksUI={checksUI}
          onChange={handleChange}
          checkResultsOverview={checkResultsOverview}
          getCheckOverview={getCheckOverview}
          loading={loading}
        />
      )}
    </div>
  );
};

export default ColumnProfilingChecksView;
