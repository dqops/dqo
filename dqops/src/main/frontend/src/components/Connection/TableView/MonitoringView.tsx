import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import {
  CheckContainerModel,
  CheckResultsOverviewDataModel
} from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { setActiveFirstLevelUrl } from '../../../redux/actions/source.actions';
import {
  getTableDailyMonitoringChecks,
  getTableMonthlyMonitoringChecks,
  setUpdatedDailyMonitoringChecks,
  setUpdatedMonthlyMonitoringChecks,
  updateTableDailyMonitoringChecks,
  updateTableMonthlyMonitoringChecks
} from '../../../redux/actions/table.actions';
import {
  getFirstLevelActiveTab,
  getFirstLevelState,
  getSecondLevelTab
} from '../../../redux/selectors';
import { CheckResultOverviewApi } from '../../../services/apiClient';
import { CheckTypes, ROUTES } from '../../../shared/routes';
import DataQualityChecks from '../../DataQualityChecks';
import Tabs from '../../Tabs';
import TableActionGroup from './TableActionGroup';
import { TableReferenceComparisons } from './TableComparison/TableReferenceComparisons';
import TableQualityStatus from './TableQualityStatus/TableQualityStatus';
import { useDecodedParams } from '../../../utils';

const initTabs = [
  {
    label: 'Table quality status (daily checks)',
    value: 'table-quality-status-daily'
  },
  {
    label: 'Daily checks',
    value: 'daily'
  },
  {
    label: 'Table quality status (monthly checks)',
    value: 'table-quality-status-monthly'
  },
  {
    label: 'Monthly checks',
    value: 'monthly'
  },
  {
    label: 'Daily comparisons',
    value: 'daily_comparisons'
  },
  {
    label: 'Monthly comparisons',
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
  } = useDecodedParams();
  const activeTab = getSecondLevelTab(checkTypes, tab);

  const dispatch = useActionDispatch();
  const [tabs, setTabs] = useState(initTabs);
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
    if (activeTab === 'daily' || activeTab === 'daily_comparisons') {
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

  const onChangeTab = (activeTab: string) => {
    dispatch(
      setActiveFirstLevelUrl(
        checkTypes,
        firstLevelActiveTab,
        ROUTES.TABLE_LEVEL_PAGE(checkTypes, connectionName, schemaName, tableName, activeTab)
      )
    );
    history.push(
      ROUTES.TABLE_LEVEL_PAGE(
        checkTypes,
        connectionName,
        schemaName,
        tableName,
        activeTab
      )
    );
  };

  return (
    <div className="flex-grow min-h-0 flex flex-col">
      {(activeTab === 'daily' || activeTab === 'monthly') && (
        <TableActionGroup
          shouldDelete={false}
          onUpdate={onUpdate}
          isUpdated={isUpdatedDailyMonitoring || isUpdatedMonthlyMonitoring}
          isUpdating={isUpdating}
        />
      )}
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
      </div>
      {activeTab === 'daily' && (
        <DataQualityChecks
          onUpdate={onUpdate}
          checksUI={dailyMonitoring}
          onChange={onDailyMonitoringChange}
          checkResultsOverview={dailyCheckResultsOverview}
          getCheckOverview={getDailyCheckOverview}
          loading={loading}
        />
      )}
      {activeTab === 'monthly' && (
        <DataQualityChecks
          onUpdate={onUpdate}
          checksUI={monthlyMonitoring}
          onChange={onMonthlyMonitoringChange}
          checkResultsOverview={monthlyCheckResultsOverview}
          getCheckOverview={getMonthlyCheckOverview}
          loading={loading}
        />
      )}
      {activeTab === 'table-quality-status-daily' && (
        <TableQualityStatus timeScale="daily" />
      )}
      {activeTab === 'table-quality-status-monthly' && (
        <TableQualityStatus timeScale="monthly" />
      )}
      {activeTab === 'daily_comparisons' && (
        <TableReferenceComparisons
          checkTypes={checkTypes}
          timePartitioned="daily"
          checksUI={dailyMonitoring}
          onUpdateChecks={onUpdate}
        />
      )}
      {activeTab === 'monthly_comparisons' && (
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
