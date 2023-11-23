import React, { useEffect, useState } from 'react';
import Tabs from '../../Tabs';
import DataQualityChecks from '../../DataQualityChecks';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableDailyMonitoringChecks,
  getTableMonthlyMonitoringChecks,
  setUpdatedDailyMonitoringChecks,
  setUpdatedMonthlyMonitoringChecks,
  updateTableDailyMonitoringChecks,
  updateTableMonthlyMonitoringChecks
} from '../../../redux/actions/table.actions';
import { useSelector } from 'react-redux';
import {
  CheckResultsOverviewDataModel,
  CheckContainerModel
} from '../../../api';
import TableActionGroup from './TableActionGroup';
import { CheckResultOverviewApi } from '../../../services/apiClient';
import { useHistory, useParams } from 'react-router-dom';
import { CheckTypes, ROUTES } from '../../../shared/routes';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../../redux/selectors';
import { TableReferenceComparisons } from './TableReferenceComparisons';
import TableQualityStatus from './TableQualityStatus';

const initTabs = [
  {
    label: 'Daily',
    value: 'daily'
  },
  {
    label: 'Monthly',
    value: 'monthly'
  },
  {
    label: 'Table quality status daily',
    value: 'table-quality-status-daily'
  },
  {
    label: 'Table quality status monthly',
    value: 'table-quality-status-monthly'
  },
  {
    label: 'Daily Comparisons',
    value: 'daily_comparisons'
  },
  {
    label: 'Monthly Comparisons',
    value: 'monthly_comparisons'
  }
];

const MonitoringView = () => {
  const {
    connection: connectionName,
    schema: schemaName,
    table: tableName,
    tab,
    checkTypes
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
    tab: string;
  } = useParams();
  const [tabs, setTabs] = useState(initTabs);
  const dispatch = useActionDispatch();
  const [dailyCheckResultsOverview, setDailyCheckResultsOverview] = useState<
    CheckResultsOverviewDataModel[]
  >([]);
  const [monthlyCheckResultsOverview, setMonthlyCheckResultsOverview] =
    useState<CheckResultsOverviewDataModel[]>([]);
  const history = useHistory();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const {
    dailyMonitoring,
    monthlyMonitoring,
    isUpdatedDailyMonitoring,
    isUpdatedMonthlyMonitoring,
    isUpdating,
    loading
  } = useSelector(getFirstLevelState(checkTypes));

  useEffect(() => {
    dispatch(
      getTableDailyMonitoringChecks(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName
      )
    );
    dispatch(
      getTableMonthlyMonitoringChecks(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName
      )
    );
  }, [checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName]);

  const onUpdate = async () => {
    if (tab === 'daily' || tab === 'daily_comparisons') {
      if (!dailyMonitoring) return;

      await dispatch(
        updateTableDailyMonitoringChecks(
          checkTypes,
          firstLevelActiveTab,
          connectionName,
          schemaName,
          tableName,
          dailyMonitoring
        )
      );
      await dispatch(
        getTableDailyMonitoringChecks(
          checkTypes,
          firstLevelActiveTab,
          connectionName,
          schemaName,
          tableName,
          false
        )
      );
    } else {
      if (!monthlyMonitoring) return;

      await dispatch(
        updateTableMonthlyMonitoringChecks(
          checkTypes,
          firstLevelActiveTab,
          connectionName,
          schemaName,
          tableName,
          monthlyMonitoring
        )
      );
      await dispatch(
        getTableMonthlyMonitoringChecks(
          checkTypes,
          firstLevelActiveTab,
          connectionName,
          schemaName,
          tableName,
          false
        )
      );
    }
  };

  const onDailyMonitoringChange = (ui: CheckContainerModel) => {
    dispatch(
      setUpdatedDailyMonitoringChecks(checkTypes, firstLevelActiveTab, ui)
    );
  };

  const onMonthlyMonitoringChange = (ui: CheckContainerModel) => {
    dispatch(
      setUpdatedMonthlyMonitoringChecks(checkTypes, firstLevelActiveTab, ui)
    );
  };

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'daily'
          ? { ...item, isUpdated: isUpdatedDailyMonitoring }
          : item
      )
    );
  }, [isUpdatedDailyMonitoring]);

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'monthly'
          ? { ...item, isUpdated: isUpdatedMonthlyMonitoring }
          : item
      )
    );
  }, [isUpdatedMonthlyMonitoring]);

  const getDailyCheckOverview = () => {
    CheckResultOverviewApi.getTableMonitoringChecksOverview(
      connectionName,
      schemaName,
      tableName,
      'daily'
    ).then((res) => {
      setDailyCheckResultsOverview(res.data);
    });
  };

  const getMonthlyCheckOverview = () => {
    CheckResultOverviewApi.getTableMonitoringChecksOverview(
      connectionName,
      schemaName,
      tableName,
      'monthly'
    ).then((res) => {
      setMonthlyCheckResultsOverview(res.data);
    });
  };

  const onChangeTab = (tab: string) => {
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

  return (
    <div className="flex-grow min-h-0 flex flex-col">
      <TableActionGroup
        shouldDelete={false}
        onUpdate={onUpdate}
        isUpdated={isUpdatedDailyMonitoring || isUpdatedMonthlyMonitoring}
        isUpdating={isUpdating}
      />
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={tab} onChange={onChangeTab} />
      </div>
      {tab === 'daily' && (
        <DataQualityChecks
          onUpdate={onUpdate}
          checksUI={dailyMonitoring}
          onChange={onDailyMonitoringChange}
          checkResultsOverview={dailyCheckResultsOverview}
          getCheckOverview={getDailyCheckOverview}
          loading={loading}
        />
      )}
      {tab === 'monthly' && (
        <DataQualityChecks
          onUpdate={onUpdate}
          checksUI={monthlyMonitoring}
          onChange={onMonthlyMonitoringChange}
          checkResultsOverview={monthlyCheckResultsOverview}
          getCheckOverview={getMonthlyCheckOverview}
          loading={loading}
        />
      )}
      {tab === 'table-quality-status-daily' && (
        <TableQualityStatus timeScale="daily" />
      )}
      {tab === 'table-quality-status-monthly' && (
        <TableQualityStatus timeScale="monthly" />
      )}
      {tab === 'daily_comparisons' && (
        <TableReferenceComparisons
          checkTypes={checkTypes}
          timePartitioned="daily"
          checksUI={dailyMonitoring}
          onUpdateChecks={onUpdate}
        />
      )}
      {tab === 'monthly_comparisons' && (
        <TableReferenceComparisons
          checkTypes={checkTypes}
          timePartitioned="monthly"
          checksUI={monthlyMonitoring}
          onUpdateChecks={onUpdate}
        />
      )}
    </div>
  );
};

export default MonitoringView;
