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
import { IRootState } from '../../redux/reducers';
import { CheckResultsOverviewDataModel, UICheckContainerModel } from '../../api';
import ColumnActionGroup from './ColumnActionGroup';
import { CheckResultOverviewApi } from "../../services/apiClient";
import { useHistory, useParams } from "react-router-dom";
import { ROUTES } from "../../shared/routes";

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
  const { connection, schema, table, column, tab, checkTypes }: { checkTypes: string, connection: string, schema: string, table: string, column: string, tab: 'daily' | 'monthly' } = useParams();
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
  } = useSelector((state: IRootState) => state.column);

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
          connection,
          schema,
          table,
          column,
          dailyRecurring
        )
      );
      await dispatch(
        getColumnDailyRecurring(
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
          connection,
          schema,
          table,
          column,
          monthlyRecurring
        )
      );
      await dispatch(
        getColumnMonthlyRecurring(
          connection,
          schema,
          table,
          column
        )
      );
    }
  };

  const onDailyRecurringChange = (ui: UICheckContainerModel) => {
    dispatch(setUpdatedDailyRecurring(ui));
  };

  const onMonthlyRecurringChange = (ui: UICheckContainerModel) => {
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
