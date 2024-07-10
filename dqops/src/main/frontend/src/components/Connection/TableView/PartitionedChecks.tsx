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
  getTableDailyPartitionedChecks,
  getTableMonthlyPartitionedChecks,
  setUpdatedDailyPartitionedChecks,
  setUpdatedMonthlyPartitionedChecks,
  updateTableDailyPartitionedChecks,
  updateTableMonthlyPartitionedChecks
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
    label: 'Table quality status (daily partitions)',
    value: 'table-quality-status-daily'
  },
  {
    label: 'Daily partitions',
    value: 'daily'
  },
  {
    label: 'Table quality status (monthly partitions)',
    value: 'table-quality-status-monthly'
  },
  {
    label: 'Monthly partitions',
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

const TablePartitionedChecksView = () => {
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
  const [tabs, setTabs] = useState(initTabs);
  const [dailyCheckResultsOverview, setDailyCheckResultsOverview] = useState<
    CheckResultsOverviewDataModel[]
  >([]);
  const [monthlyCheckResultsOverview, setMonthlyCheckResultsOverview] =
    useState<CheckResultsOverviewDataModel[]>([]);
  const history = useHistory();
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const activeTab = getSecondLevelTab(checkTypes, tab);

  const {
    dailyPartitionedChecks,
    monthlyPartitionedChecks,
    isUpdatedDailyPartitionedChecks,
    isUpdatedMonthlyPartitionedChecks,
    isUpdating,
    loading
  } = useSelector(getFirstLevelState(checkTypes));

  useEffect(() => {
    dispatch(
      getTableDailyPartitionedChecks(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName
      )
    );
    dispatch(
      getTableMonthlyPartitionedChecks(
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
      if (!dailyPartitionedChecks) return;

      await dispatch(
        updateTableDailyPartitionedChecks(
          checkTypes,
          firstLevelActiveTab,
          connectionName,
          schemaName,
          tableName,
          dailyPartitionedChecks
        )
      );
      await dispatch(
        getTableDailyPartitionedChecks(
          checkTypes,
          firstLevelActiveTab,
          connectionName,
          schemaName,
          tableName,
          false
        )
      );
    } else {
      if (!monthlyPartitionedChecks) return;

      await dispatch(
        updateTableMonthlyPartitionedChecks(
          checkTypes,
          firstLevelActiveTab,
          connectionName,
          schemaName,
          tableName,
          monthlyPartitionedChecks
        )
      );
      await dispatch(
        getTableMonthlyPartitionedChecks(
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

  const onDailyPartitionedChecksChange = (ui: CheckContainerModel) => {
    dispatch(
      setUpdatedDailyPartitionedChecks(checkTypes, firstLevelActiveTab, ui)
    );
  };

  const onMonthlyPartitionedChecksChange = (ui: CheckContainerModel) => {
    dispatch(
      setUpdatedMonthlyPartitionedChecks(checkTypes, firstLevelActiveTab, ui)
    );
  };

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'daily'
          ? { ...item, isUpdated: isUpdatedDailyPartitionedChecks }
          : item
      )
    );
  }, [isUpdatedDailyPartitionedChecks]);

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'monthly'
          ? { ...item, isUpdated: isUpdatedMonthlyPartitionedChecks }
          : item
      )
    );
  }, [isUpdatedMonthlyPartitionedChecks]);

  const getDailyCheckOverview = () => {
    CheckResultOverviewApi.getTablePartitionedChecksOverview(
      connectionName,
      schemaName,
      tableName,
      'daily'
    ).then((res) => {
      setDailyCheckResultsOverview(res.data);
    });
  };

  const getMonthlyCheckOverview = () => {
    CheckResultOverviewApi.getTablePartitionedChecksOverview(
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
      {(activeTab === 'monthly' || activeTab === 'daily') &&
        <TableActionGroup
          shouldDelete={false}
          onUpdate={onUpdate}
          isUpdated={
            isUpdatedDailyPartitionedChecks || isUpdatedMonthlyPartitionedChecks
          }
          isUpdating={isUpdating}
        />
      }
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
      </div>
      {activeTab === 'daily' && (
        <DataQualityChecks
          onUpdate={onUpdate}
          checksUI={dailyPartitionedChecks}
          onChange={onDailyPartitionedChecksChange}
          checkResultsOverview={dailyCheckResultsOverview}
          getCheckOverview={getDailyCheckOverview}
          loading={loading}
        />
      )}
      {activeTab === 'monthly' && (
        <DataQualityChecks
          onUpdate={onUpdate}
          checksUI={monthlyPartitionedChecks}
          onChange={onMonthlyPartitionedChecksChange}
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
          checksUI={dailyPartitionedChecks}
          onUpdateChecks={onUpdate}
        />
      )}
      {activeTab === 'monthly_comparisons' && (
        <TableReferenceComparisons
          checkTypes={checkTypes}
          timePartitioned="monthly"
          checksUI={monthlyPartitionedChecks}
          onUpdateChecks={onUpdate}
        />
      )}
    </div>
  );
};

export default TablePartitionedChecksView;
