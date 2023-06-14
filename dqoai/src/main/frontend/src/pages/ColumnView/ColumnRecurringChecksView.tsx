import React, { useEffect, useState } from 'react';
import Tabs from '../../components/Tabs';
import DataQualityChecks from '../../components/DataQualityChecks';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getColumnDailyRecurringChecks,
  getColumnMonthlyRecurringChecks,
  setUpdatedDailyRecurringChecks,
  setUpdatedMonthlyRecurringChecks,
  updateColumnDailyRecurringChecks,
  updateColumnMonthlyRecurringChecks
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
    label: 'Daily',
    value: 'daily'
  },
  {
    label: 'Monthly',
    value: 'monthly'
  }
];

const ColumnRecurringChecksView = () => {
  const { connection, schema, table, column, tab, checkTypes }: { checkTypes: CheckTypes, connection: string, schema: string, table: string, column: string, tab: 'daily' | 'monthly' } = useParams();
  const [tabs, setTabs] = useState(initTabs);
  const dispatch = useActionDispatch();
  const history = useHistory();

  const {
    columnBasic,
    dailyRecurring,
    monthlyRecurring,
    isUpdatedDailyRecurring,
    isUpdatedMonthlyRecurring,
    isUpdating,
    loading,
  } = useSelector(getFirstLevelState(checkTypes));
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);

  const getCheckOverview = () => {
    CheckResultOverviewApi.getColumnRecurringChecksOverview(connection, schema, table, column, tab).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    dispatch(
      getColumnDailyRecurringChecks(
        checkTypes,
        firstLevelActiveTab,
        connection,
        schema,
        table,
        column
      )
    );
    dispatch(
      getColumnMonthlyRecurringChecks(
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
      if (!dailyRecurring) return;

      await dispatch(
        updateColumnDailyRecurringChecks(
          checkTypes,
          firstLevelActiveTab,
          connection,
          schema,
          table,
          column,
          dailyRecurring
        )
      );
      await dispatch(
        getColumnDailyRecurringChecks(
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
      if (!monthlyRecurring) return;

      await dispatch(
        updateColumnMonthlyRecurringChecks(
          checkTypes,
          firstLevelActiveTab,
          connection,
          schema,
          table,
          column,
          monthlyRecurring
        )
      );
      await dispatch(
        getColumnMonthlyRecurringChecks(
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

  const onDailyRecurringChange = (ui: CheckContainerModel) => {
    dispatch(setUpdatedDailyRecurringChecks(checkTypes, firstLevelActiveTab, ui));
  };

  const onMonthlyRecurringChange = (ui: CheckContainerModel) => {
    dispatch(setUpdatedMonthlyRecurringChecks(checkTypes, firstLevelActiveTab, ui));
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
        isUpdated={isUpdatedDailyRecurring || isUpdatedMonthlyRecurring}
        isUpdating={isUpdating}
      />
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={tab} onChange={onChangeTab} />
      </div>
      <div>
        {tab === 'daily' && (
          <DataQualityChecks
            onUpdate={onUpdate}
            checksUI={dailyRecurring}
            onChange={onDailyRecurringChange}
            className="max-h-table"
            checkResultsOverview={checkResultsOverview}
            getCheckOverview={getCheckOverview}
            loading={loading}
          />
        )}
        {tab === 'monthly' && (
          <DataQualityChecks
            onUpdate={onUpdate}
            checksUI={monthlyRecurring}
            onChange={onMonthlyRecurringChange}
            className="max-h-table"
            checkResultsOverview={checkResultsOverview}
            getCheckOverview={getCheckOverview}
            loading={loading}
          />
        )}
      </div>
    </div>
  );
};

export default ColumnRecurringChecksView;
