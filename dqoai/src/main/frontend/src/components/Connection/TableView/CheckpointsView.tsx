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
import { UIAllChecksModel } from '../../../api';
import TableActionGroup from './TableActionGroup';

interface ICheckpointsViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
}

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

const CheckpointsView = ({
  connectionName,
  schemaName,
  tableName
}: ICheckpointsViewProps) => {
  const [activeTab, setActiveTab] = useState('daily');
  const [tabs, setTabs] = useState(initTabs);
  const dispatch = useActionDispatch();

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

  return (
    <div className="py-2">
      <TableActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedDailyCheckpoints || isUpdatedMonthlyCheckpoints}
        isUpdating={isUpdating}
      />
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
      </div>
      <div>
        {activeTab === 'daily' && (
          <DataQualityChecks
            checksUI={dailyCheckpoints}
            onChange={onDailyCheckpointsChange}
            className="max-h-checks"
          />
        )}
        {activeTab === 'monthly' && (
          <DataQualityChecks
            checksUI={monthlyCheckpoints}
            onChange={onMonthlyCheckpointsChange}
            className="max-h-checks"
          />
        )}
      </div>
    </div>
  );
};

export default CheckpointsView;
