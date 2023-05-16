import React, { useEffect, useState } from 'react';
import DataQualityChecks from '../../components/DataQualityChecks';
import ColumnActionGroup from './ColumnActionGroup';
import { useSelector } from 'react-redux';
import { CheckResultsOverviewDataModel, ColumnStatisticsModel, UICheckContainerModel } from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getColumnChecksUi,
  setUpdatedChecksUi,
  updateColumnCheckUI
} from '../../redux/actions/column.actions';
import { CheckResultOverviewApi, ColumnApiClient, JobApiClient } from "../../services/apiClient";
import { getFirstLevelActiveTab, getFirstLevelState } from "../../redux/selectors";
import { CheckTypes } from "../../shared/routes";
import { useParams } from "react-router-dom";
import Tabs from "../../components/Tabs";
import ColumnStatisticsView from "./ColumnStatisticsView";

const tabs = [
  {
    label: 'Statistics',
    value: 'statistics'
  },
  {
    label: 'Advanced Profiling',
    value: 'advanced'
  },
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
  const { checkTypes }: { checkTypes: CheckTypes } = useParams();
  const { checksUI, isUpdating, isUpdatedChecksUi, loading } = useSelector(getFirstLevelState(checkTypes));
  const dispatch = useActionDispatch();
  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [activeTab, setActiveTab] = useState("statistics");
  const [loadingJob, setLoadingJob] = useState(false);
  const [statistics, setStatistics] = useState<ColumnStatisticsModel>();

  const getCheckOverview = () => {
    CheckResultOverviewApi.getColumnProfilingChecksOverview(connectionName, schemaName, tableName, columnName).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  const getColumnStatistics = () => {
    ColumnApiClient.getColumnStatistics(connectionName, schemaName, tableName, columnName).then((res) => {
      setStatistics(res.data);
    });
  }

  useEffect(() => {
    getColumnStatistics();
  }, [checkTypes, firstLevelActiveTab, connectionName, schemaName, columnName, tableName]);

  useEffect(() => {
    dispatch(
      getColumnChecksUi(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, columnName)
    );
  }, [checkTypes, firstLevelActiveTab, connectionName, schemaName, columnName, tableName]);

  const onUpdate = async () => {
    if (!checksUI) {
      return;
    }
    await dispatch(
      updateColumnCheckUI(
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
      getColumnChecksUi(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, columnName, false)
    );
  };

  const handleChange = (value: UICheckContainerModel) => {
    dispatch(setUpdatedChecksUi(checkTypes, firstLevelActiveTab, value));
  };

  const onCollectStatistics = async () => {
    try {
      setLoadingJob(true);
      await JobApiClient.collectStatisticsOnTable(statistics?.collect_column_statistics_job_template);
    } finally {
      setLoadingJob(false);
    }
  };

  return (
    <div>
      <ColumnActionGroup
        shouldDelete={false}
        onUpdate={onUpdate}
        isUpdated={isUpdatedChecksUi}
        isUpdating={isUpdating}
        isStatistics={activeTab === "statistics"}
        onCollectStatistics={onCollectStatistics}
        runningStatistics={loadingJob}
      />
      <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
      {activeTab === "statistics" && (
        <ColumnStatisticsView />
      )}
      {activeTab === "advanced" && (
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
