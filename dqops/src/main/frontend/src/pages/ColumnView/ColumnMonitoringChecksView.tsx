import React, { useEffect, useState } from 'react';
import Tabs from '../../components/Tabs';
import DataQualityChecks from '../../components/DataQualityChecks';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getColumnDailyMonitoringChecks,
  getColumnMonthlyMonitoringChecks,
  setUpdatedDailyMonitoringChecks,
  setUpdatedMonthlyMonitoringChecks,
  updateColumnDailyMonitoringChecks,
  updateColumnMonthlyMonitoringChecks
} from '../../redux/actions/column.actions';
import { useSelector } from 'react-redux';
import { CheckResultsOverviewDataModel, CheckContainerModel } from '../../api';
import ColumnActionGroup from './ColumnActionGroup';
import { CheckResultOverviewApi } from "../../services/apiClient";
import { useHistory, useParams } from "react-router-dom";
import { CheckTypes, ROUTES } from "../../shared/routes";
import { getFirstLevelActiveTab, getFirstLevelState } from "../../redux/selectors";

const initTabs = [
  {
    label: 'Daily checks',
    value: 'daily'
  },
  {
    label: 'Monthly checks',
    value: 'monthly'
  }
];

const ColumnMonitoringChecksView = () => {
  const { connection, schema, table, column, tab, checkTypes }: { checkTypes: CheckTypes, connection: string, schema: string, table: string, column: string, tab: 'daily' | 'monthly' } = useParams();
  const [tabs, setTabs] = useState(initTabs);
  const dispatch = useActionDispatch();
  const history = useHistory();

  const {
    dailyMonitoring,
    monthlyMonitoring,
    isUpdatedDailyMonitoring,
    isUpdatedMonthlyMonitoring,
    isUpdating,
    loading,
  } = useSelector(getFirstLevelState(checkTypes));
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);

  const getCheckOverview = () => {
    CheckResultOverviewApi.getColumnMonitoringChecksOverview(connection, schema, table, column, tab).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    dispatch(
      getColumnDailyMonitoringChecks(
        checkTypes,
        firstLevelActiveTab,
        connection,
        schema,
        table,
        column
      )
    );
    dispatch(
      getColumnMonthlyMonitoringChecks(
        checkTypes,
        firstLevelActiveTab,
        connection,
        schema,
        table,
        column
      )
    );
  }, [checkTypes, firstLevelActiveTab, connection, schema, table, column]);

  const onUpdate = async () => {
    if (tab === 'daily') {
      if (!dailyMonitoring) return;

      await dispatch(
        updateColumnDailyMonitoringChecks(
          checkTypes,
          firstLevelActiveTab,
          connection,
          schema,
          table,
          column,
          dailyMonitoring
        )
      );
      await dispatch(
        getColumnDailyMonitoringChecks(
          checkTypes,
          firstLevelActiveTab,
          connection,
          schema,
          table,
          column,
          false
        )
      );
    } else {
      if (!monthlyMonitoring) return;

      await dispatch(
        updateColumnMonthlyMonitoringChecks(
          checkTypes,
          firstLevelActiveTab,
          connection,
          schema,
          table,
          column,
          monthlyMonitoring
        )
      );
      await dispatch(
        getColumnMonthlyMonitoringChecks(
          checkTypes,
          firstLevelActiveTab,
          connection,
          schema,
          table,
          column,
          false
        )
      );
    }
  };

  const onDailyMonitoringChange = (ui: CheckContainerModel) => {
    dispatch(setUpdatedDailyMonitoringChecks(checkTypes, firstLevelActiveTab, ui));
  };

  const onMonthlyMonitoringChange = (ui: CheckContainerModel) => {
    dispatch(setUpdatedMonthlyMonitoringChecks(checkTypes, firstLevelActiveTab, ui));
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

  useEffect(() => {
    if (tab !== 'daily' && tab !== 'monthly') {
      history.push(ROUTES.COLUMN_LEVEL_PAGE(checkTypes, connection, schema, table, column, 'daily'));
    }
  }, [tab]);

  const onChangeTab = (tab: string) => {
    history.push(ROUTES.COLUMN_LEVEL_PAGE(checkTypes, connection, schema, table, column, tab));
  };

  return (
    <div className="py-2">
      <ColumnActionGroup
        shouldDelete={false}
        onUpdate={onUpdate}
        isUpdated={isUpdatedDailyMonitoring || isUpdatedMonthlyMonitoring}
        isUpdating={isUpdating}
      />
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={tab} onChange={onChangeTab} />
      </div>
      <div>
        {tab === 'daily' && (
          <DataQualityChecks
            onUpdate={onUpdate}
            checksUI={dailyMonitoring}
            onChange={onDailyMonitoringChange}
            checkResultsOverview={checkResultsOverview}
            getCheckOverview={getCheckOverview}
            loading={loading}
          />
        )}
        {tab === 'monthly' && (
          <DataQualityChecks
            onUpdate={onUpdate}
            checksUI={monthlyMonitoring}
            onChange={onMonthlyMonitoringChange}
            checkResultsOverview={checkResultsOverview}
            getCheckOverview={getCheckOverview}
            loading={loading}
          />
        )}
      </div>
    </div>
  );
};

export default ColumnMonitoringChecksView;
