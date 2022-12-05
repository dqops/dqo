import React, { useEffect, useState } from 'react';
import Tabs from '../../Tabs';
import { UIAllChecksModel } from '../../../api';
import DataQualityChecks from '../../DataQualityChecks';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import TableActionGroup from './TableActionGroup';
import {
  getTableDailyPartitionedChecks,
  getTableMonthlyPartitionedChecks,
  updateTableDailyPartitionedChecks,
  updateTableMonthlyPartitionedChecks
} from '../../../redux/actions/table.actions';

const tabs = [
  {
    label: 'Daily',
    value: 'daily'
  },
  {
    label: 'Monthly',
    value: 'monthly'
  }
];

interface IPartitionedChecksProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
}

const PartitionedChecks = ({
  connectionName,
  schemaName,
  tableName
}: IPartitionedChecksProps) => {
  const [activeTab, setActiveTab] = useState('daily');

  const [updatedDailyPartitionedChecks, setUpdatedDailyPartitionedChecks] =
    useState<UIAllChecksModel>();
  const [updatedMonthlyPartitionedChecks, setUpdatedMonthlyPartitionedChecks] =
    useState<UIAllChecksModel>();
  const [isUpdated, setIsUpdated] = useState(false);

  const dispatch = useActionDispatch();

  const { dailyPartitionedChecks, monthlyPartitionedChecks, isUpdating } =
    useSelector((state: IRootState) => state.table);

  useEffect(() => {
    setUpdatedDailyPartitionedChecks(dailyPartitionedChecks);
  }, [dailyPartitionedChecks]);

  useEffect(() => {
    setUpdatedMonthlyPartitionedChecks(monthlyPartitionedChecks);
  }, [monthlyPartitionedChecks]);

  useEffect(() => {
    dispatch(
      getTableDailyPartitionedChecks(connectionName, schemaName, tableName)
    );
    dispatch(
      getTableMonthlyPartitionedChecks(connectionName, schemaName, tableName)
    );
  }, [connectionName, schemaName, tableName, connectionName]);

  const onUpdate = async () => {
    if (activeTab === 'daily') {
      if (!updatedDailyPartitionedChecks) return;

      await dispatch(
        updateTableDailyPartitionedChecks(
          connectionName,
          schemaName,
          tableName,
          updatedDailyPartitionedChecks
        )
      );
      await dispatch(
        getTableDailyPartitionedChecks(connectionName, schemaName, tableName)
      );
    } else {
      if (!updatedMonthlyPartitionedChecks) return;

      await dispatch(
        updateTableMonthlyPartitionedChecks(
          connectionName,
          schemaName,
          tableName,
          updatedMonthlyPartitionedChecks
        )
      );
      await dispatch(
        getTableMonthlyPartitionedChecks(connectionName, schemaName, tableName)
      );
    }
    setIsUpdated(false);
  };

  const onDailyPartitionedChecksChange = (ui: UIAllChecksModel) => {
    setUpdatedDailyPartitionedChecks(ui);
    setIsUpdated(true);
  };

  const onMonthlyPartitionedChecksChange = (ui: UIAllChecksModel) => {
    setUpdatedMonthlyPartitionedChecks(ui);
    setIsUpdated(true);
  };

  return (
    <div className="py-2">
      <TableActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdated}
        isUpdating={isUpdating}
      />
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
      </div>
      <div>
        {activeTab === 'daily' && (
          <DataQualityChecks
            checksUI={updatedDailyPartitionedChecks}
            onChange={onDailyPartitionedChecksChange}
          />
        )}
        {activeTab === 'monthly' && (
          <DataQualityChecks
            checksUI={updatedMonthlyPartitionedChecks}
            onChange={onMonthlyPartitionedChecksChange}
          />
        )}
      </div>
    </div>
  );
};

export default PartitionedChecks;
