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
import { IRootState } from '../../../redux/reducers';
import { CheckResultsOverviewDataModel, UICheckContainerModel } from '../../../api';
import TableActionGroup from './TableActionGroup';
import { CheckResultOverviewApi } from '../../../services/apiClient';
import { useHistory, useParams } from "react-router-dom";
import { ROUTES } from "../../../shared/routes";

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
  const { connection: connectionName, schema: schemaName, table: tableName, tab, checkTypes }: { checkTypes: string, connection: string, schema: string, table: string, tab: string } = useParams();
  const [tabs, setTabs] = useState(initTabs);
  const [dailyCheckResultsOverview, setDailyCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);
  const [monthlyCheckResultsOverview, setMonthlyCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);
  const history = useHistory();
  const dispatch = useActionDispatch();

  const {
    tableBasic,
    dailyPartitionedChecks,
    monthlyPartitionedChecks,
    isUpdatedDailyPartitionedChecks,
    isUpdatedMonthlyPartitionedChecks,
    isUpdating,
    loading,
  } = useSelector((state: IRootState) => state.table);

  useEffect(() => {
    if (
      !dailyPartitionedChecks ||
      tableBasic?.connection_name !== connectionName ||
      tableBasic?.target?.schema_name !== schemaName ||
      tableBasic?.target?.table_name !== tableName
    ) {
      dispatch(
        getTableDailyPartitionedChecks(connectionName, schemaName, tableName)
      );
    }
    if (
      !monthlyPartitionedChecks ||
      tableBasic?.connection_name !== connectionName ||
      tableBasic?.target?.schema_name !== schemaName ||
      tableBasic?.target?.table_name !== tableName
    ) {
      dispatch(
        getTableMonthlyPartitionedChecks(connectionName, schemaName, tableName)
      );
    }
  }, [connectionName, schemaName, tableName, tableName, tableBasic]);

  const onUpdate = async () => {
    if (tab === 'daily') {
      if (!dailyPartitionedChecks) return;

      await dispatch(
        updateTableDailyPartitionedChecks(
          connectionName,
          schemaName,
          tableName,
          dailyPartitionedChecks
        )
      );
      await dispatch(
        getTableDailyPartitionedChecks(connectionName, schemaName, tableName)
      );
    } else {
      if (!monthlyPartitionedChecks) return;

      await dispatch(
        updateTableMonthlyPartitionedChecks(
          connectionName,
          schemaName,
          tableName,
          monthlyPartitionedChecks
        )
      );
      await dispatch(
        getTableMonthlyPartitionedChecks(connectionName, schemaName, tableName)
      );
    }
  };

  const onDailyPartitionedChecksChange = (ui: UICheckContainerModel) => {
    dispatch(setUpdatedDailyPartitionedChecks(ui));
  };

  const onMonthlyPartitionedChecksChange = (ui: UICheckContainerModel) => {
    dispatch(setUpdatedMonthlyPartitionedChecks(ui));
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
