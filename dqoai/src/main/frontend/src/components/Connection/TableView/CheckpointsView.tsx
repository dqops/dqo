import React, { useEffect, useState } from 'react';
import Tabs from '../../Tabs';
import DataQualityChecks from '../../DataQualityChecks';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableDailyCheckpoints,
  getTableMonthlyCheckpoints,
  setUpdatedDailyCheckPoints,
  setUpdatedMonthlyCheckPoints,
  updateTableDailyCheckpoints,
  updateTableMonthlyCheckpoints
} from '../../../redux/actions/table.actions';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { CheckResultsOverviewDataModel, UIAllChecksModel } from '../../../api';
import TableActionGroup from './TableActionGroup';
import { CheckResultOverviewApi } from '../../../services/apiClient';
import { useParams } from "react-router-dom";

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

const CheckpointsView = () => {
  const { connection: connectionName, schema: schemaName, table: tableName }: { connection: string, schema: string, table: string } = useParams();
  const [activeTab, setActiveTab] = useState('daily');
  const [tabs, setTabs] = useState(initTabs);
  const dispatch = useActionDispatch();
  const [dailyCheckResultsOverview, setDailyCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);
  const [monthlyCheckResultsOverview, setMonthlyCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);

  const {
    tableBasic,
    dailyCheckpoints,
    monthlyCheckpoints,
    isUpdatedDailyCheckpoints,
    isUpdatedMonthlyCheckpoints,
    isUpdating
  } = useSelector((state: IRootState) => state.table);

  useEffect(() => {
    if (
      !dailyCheckpoints ||
      tableBasic?.connection_name !== connectionName ||
      tableBasic?.target?.schema_name !== schemaName ||
      tableBasic?.target?.table_name !== tableName
    ) {
      dispatch(getTableDailyCheckpoints(connectionName, schemaName, tableName));
    }
    if (
      !monthlyCheckpoints ||
      tableBasic?.connection_name !== connectionName ||
      tableBasic?.target?.schema_name !== schemaName ||
      tableBasic?.target?.table_name !== tableName
    ) {
      dispatch(
        getTableMonthlyCheckpoints(connectionName, schemaName, tableName)
      );
    }
  }, [connectionName, schemaName, tableName, tableBasic]);

  const onUpdate = async () => {
    if (activeTab === 'daily') {
      if (!dailyCheckpoints) return;

      await dispatch(
        updateTableDailyCheckpoints(
          connectionName,
          schemaName,
          tableName,
          dailyCheckpoints
        )
      );
      await dispatch(
        getTableDailyCheckpoints(connectionName, schemaName, tableName)
      );
    } else {
      if (!monthlyCheckpoints) return;

      await dispatch(
        updateTableMonthlyCheckpoints(
          connectionName,
          schemaName,
          tableName,
          monthlyCheckpoints
        )
      );
      await dispatch(
        getTableMonthlyCheckpoints(connectionName, schemaName, tableName)
      );
    }
  };

  const onDailyCheckpointsChange = (ui: UIAllChecksModel) => {
    dispatch(setUpdatedDailyCheckPoints(ui));
  };

  const onMonthlyCheckpointsChange = (ui: UIAllChecksModel) => {
    dispatch(setUpdatedMonthlyCheckPoints(ui));
  };

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'daily'
          ? { ...item, isUpdated: isUpdatedDailyCheckpoints }
          : item
      )
    );
  }, [isUpdatedDailyCheckpoints]);

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'monthly'
          ? { ...item, isUpdated: isUpdatedMonthlyCheckpoints }
          : item
      )
    );
  }, [isUpdatedMonthlyCheckpoints]);
  
  const getDailyCheckOverview = () => {
    CheckResultOverviewApi.getTableCheckpointsOverview(connectionName, schemaName, tableName, 'daily').then((res) => {
      setDailyCheckResultsOverview(res.data);
    });
  };
  
  const getMonthlyCheckOverview = () => {
    CheckResultOverviewApi.getTableCheckpointsOverview(connectionName, schemaName, tableName, 'monthly').then((res) => {
      setMonthlyCheckResultsOverview(res.data);
    });
  };
  
  return (
    <div className="flex-grow min-h-0 flex flex-col">
      <TableActionGroup
        shouldDelete={false}
        onUpdate={onUpdate}
        isUpdated={isUpdatedDailyCheckpoints || isUpdatedMonthlyCheckpoints}
        isUpdating={isUpdating}
      />
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
      </div>
      {activeTab === 'daily' && (
        <DataQualityChecks
          onUpdate={onUpdate}
          checksUI={dailyCheckpoints}
          onChange={onDailyCheckpointsChange}
          checkResultsOverview={dailyCheckResultsOverview}
          getCheckOverview={getDailyCheckOverview}
        />
      )}
      {activeTab === 'monthly' && (
        <DataQualityChecks
          onUpdate={onUpdate}
          checksUI={monthlyCheckpoints}
          onChange={onMonthlyCheckpointsChange}
          checkResultsOverview={monthlyCheckResultsOverview}
          getCheckOverview={getMonthlyCheckOverview}
        />
      )}
    </div>
  );
};

export default CheckpointsView;
