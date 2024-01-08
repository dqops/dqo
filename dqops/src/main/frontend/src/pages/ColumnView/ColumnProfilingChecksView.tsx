import React, { useEffect, useMemo, useState } from 'react';
import DataQualityChecks from '../../components/DataQualityChecks';
import ColumnActionGroup from './ColumnActionGroup';
import { useSelector } from 'react-redux';
import {
  CheckResultsOverviewDataModel,
  ColumnStatisticsModel,
  CheckContainerModel,
  DqoJobHistoryEntryModelStatusEnum,
  TableStatisticsModel
} from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getColumnProfilingChecksModel,
  setUpdatedChecksModel,
  updateColumnProfilingChecksModel
} from '../../redux/actions/column.actions';
import {
  CheckResultOverviewApi,
  ColumnApiClient,
  JobApiClient,
  TableApiClient
} from '../../services/apiClient';
import {
  getFirstLevelActiveTab,
  getFirstLevelState,
  getSecondLevelTab
} from '../../redux/selectors';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { useParams } from 'react-router-dom';
import Tabs from '../../components/Tabs';
import ColumnStatisticsView from './ColumnStatisticsView';
import { useHistory } from 'react-router-dom';
import { IRootState } from '../../redux/reducers';
import { setActiveFirstLevelUrl } from '../../redux/actions/source.actions';

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
  const { checkTypes, tab }: { checkTypes: CheckTypes, tab: string } = useParams();
  const { checksUI, isUpdating, isUpdatedChecksUi, loading } = useSelector(getFirstLevelState(checkTypes));
  const activeTab = getSecondLevelTab(checkTypes, tab);
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const { job_dictionary_state } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const dispatch = useActionDispatch();
  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);
  const [columnStatistics, setColumnStatistics] = useState<ColumnStatisticsModel>();
  const [tableStatistics, setTableStatistics] = useState<TableStatisticsModel>();
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
      await TableApiClient.getTableStatistics(connectionName, schemaName, tableName)
      .then((res) => setTableStatistics(res.data))
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
    ).then((res) => setJobId(res.data.jobId?.jobId))
  };

  const filteredCollectStatisticsJobs = useMemo(() => {
    return (job && (
      job.status === DqoJobHistoryEntryModelStatusEnum.running ||
      job.status === DqoJobHistoryEntryModelStatusEnum.queued ||
      job.status === DqoJobHistoryEntryModelStatusEnum.waiting ))
    }, [job])

  useEffect(() => {
    if (job && job?.status === DqoJobHistoryEntryModelStatusEnum.succeeded) {
      getColumnStatistics()
      getTableStatistics()
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
    <div className="flex flex-col overflow-x-auto overflow-y-hidden"
    >
      <ColumnActionGroup
        shouldDelete={false}
        onUpdate={onUpdate}
        isUpdated={isUpdatedChecksUi}
        isUpdating={isUpdating}
        isStatistics={activeTab === 'statistics'}
        onCollectStatistics={onCollectStatistics}
        collectStatisticsSpinner = {filteredCollectStatisticsJobs}
      />
      <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} className='w-full h-12 overflow-hidden max-w-full'/>
      {activeTab === 'statistics' && <ColumnStatisticsView columnStatisticsProp = {columnStatistics} tableStatisticsProp = {tableStatistics} />}
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
