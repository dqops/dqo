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
import { UIAllChecksModel } from '../../../api';
import TableActionGroup from './TableActionGroup';

interface ITablePartitionedChecksViewProps {
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

const TablePartitionedChecksView = ({
  connectionName,
  schemaName,
  tableName
}: ITablePartitionedChecksViewProps) => {
  const [activeTab, setActiveTab] = useState('daily');
  const [tabs, setTabs] = useState(initTabs);

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

  return (
    <div className="py-2">
      <TableActionGroup
        onUpdate={onUpdate}
        isUpdated={
          isUpdatedDailyPartitionedChecks || isUpdatedMonthlyPartitionedChecks
        }
        isUpdating={isUpdating}
      />
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
      </div>
      <div>
        {activeTab === 'daily' && (
          <DataQualityChecks
            checksUI={dailyPartitionedChecks}
            onChange={onDailyPartitionedChecksChange}
            className="max-h-checks"
          />
        )}
        {activeTab === 'monthly' && (
          <DataQualityChecks
            checksUI={monthlyPartitionedChecks}
            onChange={onMonthlyPartitionedChecksChange}
            className="max-h-checks"
          />
        )}
      </div>
    </div>
  );
};

export default TablePartitionedChecksView;
