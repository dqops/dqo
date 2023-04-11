import React, { useEffect, useState } from 'react';
import Tabs from '../../components/Tabs';
import DataQualityChecks from '../../components/DataQualityChecks';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getColumnDailyRecurring,
  getColumnMonthlyRecurring,
  setUpdatedDailyRecurring,
  setUpdatedMonthlyRecurring,
  updateColumnDailyRecurring,
  updateColumnMonthlyRecurring
} from '../../redux/actions/column.actions';
import { useSelector } from 'react-redux';
import { CheckResultsOverviewDataModel, UICheckContainerModel } from '../../api';
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

const RecurringView = () => {
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
    CheckResultOverviewApi.getColumnRecurringOverview(connection, schema, table, column, tab).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    if (
      !dailyRecurring ||
      columnBasic?.connection_name !== connection ||
      columnBasic?.table?.schema_name !== schema ||
      columnBasic?.table?.table_name !== table ||
      columnBasic.column_name !== column
    ) {
      dispatch(
        getColumnDailyRecurring(
          checkTypes,
          firstLevelActiveTab,
          connection,
          schema,
          table,
          column
        )
      );
    }
    if (
      !monthlyRecurring ||
      columnBasic?.connection_name !== connection ||
      columnBasic?.table?.schema_name !== schema ||
      columnBasic?.table?.table_name !== table ||
      columnBasic.column_name !== column
    ) {
      dispatch(
        getColumnMonthlyRecurring(
          checkTypes,
          firstLevelActiveTab,
          connection,
          schema,
          table,
          column
        )
      );
    }
  }, [connection, schema, table, column, columnBasic]);

  const onUpdate = async () => {
    if (tab === 'daily') {
      if (!dailyRecurring) return;

      await dispatch(
        updateColumnDailyRecurring(
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
        getColumnDailyRecurring(
          checkTypes,
          firstLevelActiveTab,
          connection,
          schema,
          table,
          column
        )
      );
    } else {
      if (!monthlyRecurring) return;

      await dispatch(
        updateColumnMonthlyRecurring(
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
        getColumnMonthlyRecurring(
          checkTypes,
          firstLevelActiveTab,
          connection,
          schema,
          table,
          column
        )
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

export default RecurringView;
