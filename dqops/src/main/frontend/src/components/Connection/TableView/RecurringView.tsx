import React, { useEffect, useState } from 'react';
import Tabs from '../../Tabs';
import DataQualityChecks from '../../DataQualityChecks';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableDailyRecurringChecks,
  getTableMonthlyRecurringChecks,
  setUpdatedDailyRecurringChecks,
  setUpdatedMonthlyRecurringChecks,
  updateTableDailyRecurringChecks,
  updateTableMonthlyRecurringChecks
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
    label: 'Daily Comparisons',
    value: 'daily_comparisons'
  },
  {
    label: 'Monthly Comparisons',
    value: 'monthly_comparisons'
  }
];

const RecurringView = () => {
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
    dailyRecurring,
    monthlyRecurring,
    isUpdatedDailyRecurring,
    isUpdatedMonthlyRecurring,
    isUpdating,
    loading
  } = useSelector(getFirstLevelState(checkTypes));

  useEffect(() => {
    dispatch(
      getTableDailyRecurringChecks(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName
      )
    );
    dispatch(
      getTableMonthlyRecurringChecks(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName
      )
    );
  }, [checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName]);

  const onUpdate = async () => {
    if (tab === 'daily') {
      if (!dailyRecurring) return;

      await dispatch(
        updateTableDailyRecurringChecks(
          checkTypes,
          firstLevelActiveTab,
          connectionName,
          schemaName,
          tableName,
          dailyRecurring
        )
      );
      await dispatch(
        getTableDailyRecurringChecks(
          checkTypes,
          firstLevelActiveTab,
          connectionName,
          schemaName,
          tableName,
          false
        )
      );
    } else {
      if (!monthlyRecurring) return;

      await dispatch(
        updateTableMonthlyRecurringChecks(
          checkTypes,
          firstLevelActiveTab,
          connectionName,
          schemaName,
          tableName,
          monthlyRecurring
        )
      );
      await dispatch(
        getTableMonthlyRecurringChecks(
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

  const onDailyRecurringChange = (ui: CheckContainerModel) => {
    dispatch(
      setUpdatedDailyRecurringChecks(checkTypes, firstLevelActiveTab, ui)
    );
  };

  const onMonthlyRecurringChange = (ui: CheckContainerModel) => {
    dispatch(
      setUpdatedMonthlyRecurringChecks(checkTypes, firstLevelActiveTab, ui)
    );
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
    if (
      tab !== 'daily' &&
      tab !== 'monthly' &&
      tab !== 'daily_comparisons' &&
      tab !== 'monthly_comparisons'
    ) {
      history.push(
        ROUTES.TABLE_LEVEL_PAGE(
          checkTypes,
          connectionName,
          schemaName,
          tableName,
          'daily'
        )
      );
    }
  }, [tab]);

  const getDailyCheckOverview = () => {
    CheckResultOverviewApi.getTableRecurringChecksOverview(
      connectionName,
      schemaName,
      tableName,
      'daily'
    ).then((res) => {
      setDailyCheckResultsOverview(res.data);
    });
  };

  const getMonthlyCheckOverview = () => {
    CheckResultOverviewApi.getTableRecurringChecksOverview(
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

  console.log(dailyRecurring);

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
      {tab === 'daily_comparisons' && (
        <TableReferenceComparisons
          checkTypes={checkTypes}
          timePartitioned="daily"
          checksUI={dailyRecurring}
        />
      )}
      {tab === 'monthly_comparisons' && (
        <TableReferenceComparisons
          checkTypes={checkTypes}
          timePartitioned="monthly"
          checksUI={monthlyRecurring}
        />
      )}
    </div>
  );
};

export default RecurringView;
