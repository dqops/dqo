import React, { useEffect, useState } from 'react';
import DataQualityChecks from '../../components/DataQualityChecks';
import ColumnActionGroup from './ColumnActionGroup';
import { useSelector } from 'react-redux';
import {
  CheckResultsOverviewDataModel,
  ColumnStatisticsModel,
  CheckContainerModel
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
  JobApiClient
} from '../../services/apiClient';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../redux/selectors';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { useParams } from 'react-router-dom';
import Tabs from '../../components/Tabs';
import ColumnStatisticsView from './ColumnStatisticsView';
import { useHistory } from 'react-router-dom';
import { IRootState } from '../../redux/reducers';

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
  const { checksUI, isUpdating, isUpdatedChecksUi, loading } = useSelector(
    getFirstLevelState(checkTypes)
  );
  const { job_dictionary_state } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const dispatch = useActionDispatch();
  const [checkResultsOverview, setCheckResultsOverview] = useState<
    CheckResultsOverviewDataModel[]
  >([]);
  const [activeTab, setActiveTab] = useState(tab === 'statistics' || tab === 'advanced' ? tab : 'statistics');
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [loadingJob, setLoadingJob] = useState(false);
  const [statistics, setStatistics] = useState<ColumnStatisticsModel>();
  const [jobId, setJobId] = useState<number>();
  const job = jobId ? job_dictionary_state[jobId] : undefined;
  const [collectedStatisticsIndicator, setCollectedSatisticsIndicator] = useState(false)

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
      setStatistics(res.data);
    });
  };

  useEffect(() => {
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
    try {
      setLoadingJob(true);
      const res = await JobApiClient.collectStatisticsOnTable(
        undefined,
        false,
        undefined,
        statistics?.collect_column_statistics_job_template
      );
      setJobId(res.data.jobId?.jobId)
    } finally {
      setLoadingJob(false);
    }
  };

  useEffect(() => {
    if(job && job.status === "succeeded") {
      setCollectedSatisticsIndicator((prevState) => !prevState)
    }
  },[job_dictionary_state])

  const onChangeTab = (tab: string) => {
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
    setActiveTab(tab)
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
        runningStatistics={loadingJob}
      />
      <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} className='w-full h-12 overflow-hidden max-w-full'/>
      {activeTab === 'statistics' && <ColumnStatisticsView statisticsCollectedIndicator={collectedStatisticsIndicator}/>}
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
