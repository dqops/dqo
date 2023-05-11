import React, { useEffect, useState } from 'react';
import Tabs from '../../Tabs';
import DataQualityChecks from '../../DataQualityChecks';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableDailyPartitionedChecks,
  getTableMonthlyPartitionedChecks,
  setUpdatedDailyPartitionedChecks,
  setUpdatedMonthlyPartitionedChecks,
  updateTableDailyPartitionedChecks,
  updateTableMonthlyPartitionedChecks
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
    label: 'Days',
    value: 'daily'
  },
  {
    label: 'Months',
    value: 'monthly'
  }
];

const TablePartitionedChecksView = () => {
  const { connection: connectionName, schema: schemaName, table: tableName, tab, checkTypes }: { checkTypes: CheckTypes, connection: string, schema: string, table: string, tab: string } = useParams();
  const [tabs, setTabs] = useState(initTabs);
  const [dailyCheckResultsOverview, setDailyCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);
  const [monthlyCheckResultsOverview, setMonthlyCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);
  const history = useHistory();
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const {
    dailyPartitionedChecks,
    monthlyPartitionedChecks,
    isUpdatedDailyPartitionedChecks,
    isUpdatedMonthlyPartitionedChecks,
    isUpdating,
    loading,
  } = useSelector(getFirstLevelState(checkTypes));

  useEffect(() => {
    dispatch(
      getTableDailyPartitionedChecks(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName)
    );
    dispatch(
      getTableMonthlyPartitionedChecks(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName)
    );
  }, [checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName]);

  const onUpdate = async () => {
    if (tab === 'daily') {
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
        getTableDailyPartitionedChecks(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, false)
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
        getTableMonthlyPartitionedChecks(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, false)
      );
    }
  };

  const onDailyPartitionedChecksChange = (ui: UICheckContainerModel) => {
    dispatch(setUpdatedDailyPartitionedChecks(checkTypes, firstLevelActiveTab, ui));
  };

  const onMonthlyPartitionedChecksChange = (ui: UICheckContainerModel) => {
    dispatch(setUpdatedMonthlyPartitionedChecks(checkTypes, firstLevelActiveTab, ui));
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

  useEffect(() => {
    if (tab !== 'daily' && tab !== 'monthly') {
      history.push(ROUTES.TABLE_LEVEL_PAGE(checkTypes, connectionName, schemaName, tableName, 'daily'));
    }
  }, [tab]);

  const getDailyCheckOverview = () => {
    CheckResultOverviewApi.getTablePartitionedChecksOverview(connectionName, schemaName, tableName, 'daily').then((res) => {
      setDailyCheckResultsOverview(res.data);
    });
  };
  
  const getMonthlyCheckOverview = () => {
    CheckResultOverviewApi.getTablePartitionedChecksOverview(connectionName, schemaName, tableName, 'monthly').then((res) => {
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
        isUpdated={
          isUpdatedDailyPartitionedChecks || isUpdatedMonthlyPartitionedChecks
        }
        isUpdating={isUpdating}
      />
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={tab} onChange={onChangeTab} />
      </div>
      {tab === 'daily' && (
        <DataQualityChecks
          onUpdate={onUpdate}
          checksUI={dailyPartitionedChecks}
          onChange={onDailyPartitionedChecksChange}
          checkResultsOverview={dailyCheckResultsOverview}
          getCheckOverview={getDailyCheckOverview}
          loading={loading}
        />
      )}
      {tab === 'monthly' && (
        <DataQualityChecks
          onUpdate={onUpdate}
          checksUI={monthlyPartitionedChecks}
          onChange={onMonthlyPartitionedChecksChange}
          checkResultsOverview={monthlyCheckResultsOverview}
          getCheckOverview={getMonthlyCheckOverview}
          loading={loading}
        />
      )}
    </div>
  );
};

export default TablePartitionedChecksView;
