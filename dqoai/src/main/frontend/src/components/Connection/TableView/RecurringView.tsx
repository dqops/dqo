import React, { useEffect, useState } from 'react';
import Tabs from '../../Tabs';
import DataQualityChecks from '../../DataQualityChecks';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableDailyRecurring,
  getTableMonthlyRecurring,
  setUpdatedDailyRecurring,
  setUpdatedMonthlyRecurring,
  updateTableDailyRecurring,
  updateTableMonthlyRecurring
} from '../../../redux/actions/table.actions';
import { useSelector } from 'react-redux';
import { CheckResultsOverviewDataModel, UICheckContainerModel } from '../../../api';
import TableActionGroup from './TableActionGroup';
import { CheckResultOverviewApi } from '../../../services/apiClient';
import { useHistory, useParams } from "react-router-dom";
import { CheckTypes, ROUTES } from "../../../shared/routes";
import { getFirstLevelActiveTab, getFirstLevelState } from "../../../redux/selectors";

const initTabs = [
  {
    label: 'Daily',
    value: 'daily'
  },
  {
    label: 'Monthly',
    value: 'monthly'
  }
];

const RecurringView = () => {
  const { connection: connectionName, schema: schemaName, table: tableName, tab, checkTypes }: { checkTypes: CheckTypes, connection: string, schema: string, table: string, tab: string } = useParams();
  const [tabs, setTabs] = useState(initTabs);
  const dispatch = useActionDispatch();
  const [dailyCheckResultsOverview, setDailyCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);
  const [monthlyCheckResultsOverview, setMonthlyCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);
  const history = useHistory();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const {
    tableBasic,
    dailyRecurring,
    monthlyRecurring,
    isUpdatedDailyRecurring,
    isUpdatedMonthlyRecurring,
    isUpdating,
    loading,
  } = useSelector(getFirstLevelState(checkTypes));

  useEffect(() => {
    if (
      !dailyRecurring ||
      tableBasic?.connection_name !== connectionName ||
      tableBasic?.target?.schema_name !== schemaName ||
      tableBasic?.target?.table_name !== tableName
    ) {
      dispatch(getTableDailyRecurring(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName));
    }
    if (
      !monthlyRecurring ||
      tableBasic?.connection_name !== connectionName ||
      tableBasic?.target?.schema_name !== schemaName ||
      tableBasic?.target?.table_name !== tableName
    ) {
      dispatch(
        getTableMonthlyRecurring(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName)
      );
    }
  }, [connectionName, schemaName, tableName, tableBasic]);

  const onUpdate = async () => {
    if (tab === 'daily') {
      if (!dailyRecurring) return;

      await dispatch(
        updateTableDailyRecurring(
          checkTypes,
          firstLevelActiveTab,
          connectionName,
          schemaName,
          tableName,
          dailyRecurring
        )
      );
      await dispatch(
        getTableDailyRecurring(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName)
      );
    } else {
      if (!monthlyRecurring) return;

      await dispatch(
        updateTableMonthlyRecurring(
          checkTypes,
          firstLevelActiveTab,
          connectionName,
          schemaName,
          tableName,
          monthlyRecurring
        )
      );
      await dispatch(
        getTableMonthlyRecurring(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName)
      );
    }
  };

  const onDailyRecurringChange = (ui: UICheckContainerModel) => {
    dispatch(setUpdatedDailyRecurring(checkTypes, firstLevelActiveTab, ui));
  };

  const onMonthlyRecurringChange = (ui: UICheckContainerModel) => {
    dispatch(setUpdatedMonthlyRecurring(checkTypes, firstLevelActiveTab, ui));
  };

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'daily'
          ? { ...item, isUpdated: isUpdatedDailyRecurring }
          : item
      )
    );
  }, [isUpdatedDailyRecurring]);

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'monthly'
          ? { ...item, isUpdated: isUpdatedMonthlyRecurring }
          : item
      )
    );
  }, [isUpdatedMonthlyRecurring]);

  useEffect(() => {
    if (tab !== 'daily' && tab !== 'monthly') {
      history.push(ROUTES.TABLE_LEVEL_PAGE(checkTypes, connectionName, schemaName, tableName, 'daily'));
    }
  }, [tab]);

  const getDailyCheckOverview = () => {
    CheckResultOverviewApi.getTableRecurringOverview(connectionName, schemaName, tableName, 'daily').then((res) => {
      setDailyCheckResultsOverview(res.data);
    });
  };
  
  const getMonthlyCheckOverview = () => {
    CheckResultOverviewApi.getTableRecurringOverview(connectionName, schemaName, tableName, 'monthly').then((res) => {
      setMonthlyCheckResultsOverview(res.data);
    });
  };

  const onChangeTab = (tab: string) => {
    history.push(ROUTES.TABLE_LEVEL_PAGE(checkTypes, connectionName, schemaName, tableName, tab));
  };

  return (
    <div className="flex-grow min-h-0 flex flex-col">
      <TableActionGroup
        shouldDelete={false}
        onUpdate={onUpdate}
        isUpdated={isUpdatedDailyRecurring || isUpdatedMonthlyRecurring}
        isUpdating={isUpdating}
      />
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={tab} onChange={onChangeTab} />
      </div>
      {tab === 'daily' && (
        <DataQualityChecks
          onUpdate={onUpdate}
          checksUI={dailyRecurring}
          onChange={onDailyRecurringChange}
          checkResultsOverview={dailyCheckResultsOverview}
          getCheckOverview={getDailyCheckOverview}
          loading={loading}
        />
      )}
      {tab === 'monthly' && (
        <DataQualityChecks
          onUpdate={onUpdate}
          checksUI={monthlyRecurring}
          onChange={onMonthlyRecurringChange}
          checkResultsOverview={monthlyCheckResultsOverview}
          getCheckOverview={getMonthlyCheckOverview}
          loading={loading}
        />
      )}
    </div>
  );
};

export default RecurringView;
