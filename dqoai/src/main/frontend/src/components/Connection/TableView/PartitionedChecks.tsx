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
import { CheckResultsOverviewDataModel, UIAllChecksModel } from '../../../api';
import TableActionGroup from './TableActionGroup';
import { CheckResultOverviewApi } from '../../../services/apiClient';
import { useParams } from "react-router-dom";

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
  const { connection: connectionName, schema: schemaName, table: tableName }: { connection: string, schema: string, table: string } = useParams();
  const [activeTab, setActiveTab] = useState('daily');
  const [tabs, setTabs] = useState(initTabs);
  const [dailyCheckResultsOverview, setDailyCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);
  const [monthlyCheckResultsOverview, setMonthlyCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);

  const dispatch = useActionDispatch();

  const {
    tableBasic,
    dailyPartitionedChecks,
    monthlyPartitionedChecks,
    isUpdatedDailyPartitionedChecks,
    isUpdatedMonthlyPartitionedChecks,
    isUpdating
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
    if (activeTab === 'daily') {
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

  const onDailyPartitionedChecksChange = (ui: UIAllChecksModel) => {
    dispatch(setUpdatedDailyPartitionedChecks(ui));
  };

  const onMonthlyPartitionedChecksChange = (ui: UIAllChecksModel) => {
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
        <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
      </div>
      {activeTab === 'daily' && (
        <DataQualityChecks
          onUpdate={onUpdate}
          checksUI={dailyPartitionedChecks}
          onChange={onDailyPartitionedChecksChange}
          checkResultsOverview={dailyCheckResultsOverview}
          getCheckOverview={getDailyCheckOverview}
        />
      )}
      {activeTab === 'monthly' && (
        <DataQualityChecks
          onUpdate={onUpdate}
          checksUI={monthlyPartitionedChecks}
          onChange={onMonthlyPartitionedChecksChange}
          checkResultsOverview={monthlyCheckResultsOverview}
          getCheckOverview={getMonthlyCheckOverview}
        />
      )}
    </div>
  );
};

export default TablePartitionedChecksView;
