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
import { IRootState } from '../../../redux/reducers';
import { CheckResultsOverviewDataModel, UIAllChecksModel } from '../../../api';
import TableActionGroup from './TableActionGroup';
import { CheckResultOverviewApi } from '../../../services/apiClient';
import { useHistory, useParams } from "react-router-dom";
import { ROUTES } from "../../../shared/routes";

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
  const { connection: connectionName, schema: schemaName, table: tableName, tab, checkTypes }: { checkTypes: string, connection: string, schema: string, table: string, tab: string } = useParams();
  const [tabs, setTabs] = useState(initTabs);
  const dispatch = useActionDispatch();
  const [dailyCheckResultsOverview, setDailyCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);
  const [monthlyCheckResultsOverview, setMonthlyCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);
  const history = useHistory();

  const {
    tableBasic,
    dailyRecurring,
    monthlyRecurring,
    isUpdatedDailyRecurring,
    isUpdatedMonthlyRecurring,
    isUpdating,
    loading,
  } = useSelector((state: IRootState) => state.table);

  useEffect(() => {
    if (
      !dailyRecurring ||
      tableBasic?.connection_name !== connectionName ||
      tableBasic?.target?.schema_name !== schemaName ||
      tableBasic?.target?.table_name !== tableName
    ) {
      dispatch(getTableDailyRecurring(connectionName, schemaName, tableName));
    }
    if (
      !monthlyRecurring ||
      tableBasic?.connection_name !== connectionName ||
      tableBasic?.target?.schema_name !== schemaName ||
      tableBasic?.target?.table_name !== tableName
    ) {
      dispatch(
        getTableMonthlyRecurring(connectionName, schemaName, tableName)
      );
    }
  }, [connectionName, schemaName, tableName, tableBasic]);

  const onUpdate = async () => {
    if (tab === 'daily') {
      if (!dailyRecurring) return;

      await dispatch(
        updateTableDailyRecurring(
          connectionName,
          schemaName,
          tableName,
          dailyRecurring
        )
      );
      await dispatch(
        getTableDailyRecurring(connectionName, schemaName, tableName)
      );
    } else {
      if (!monthlyRecurring) return;

      await dispatch(
        updateTableMonthlyRecurring(
          connectionName,
          schemaName,
          tableName,
          monthlyRecurring
        )
      );
      await dispatch(
        getTableMonthlyRecurring(connectionName, schemaName, tableName)
      );
    }
  };

  const onDailyRecurringChange = (ui: UIAllChecksModel) => {
    dispatch(setUpdatedDailyRecurring(ui));
  };

  const onMonthlyRecurringChange = (ui: UIAllChecksModel) => {
    dispatch(setUpdatedMonthlyRecurring(ui));
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
