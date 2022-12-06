import React, { useEffect, useState } from 'react';
import Tabs from '../../Tabs';
import { UIAllChecksModel } from '../../../api';
import DataQualityChecks from '../../DataQualityChecks';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import {
  getTableDailyCheckpoints,
  getTableMonthlyCheckpoints,
  updateTableDailyCheckpoints,
  updateTableMonthlyCheckpoints
} from '../../../redux/actions/table.actions';
import TableActionGroup from './TableActionGroup';

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

interface ICheckpointsViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
}

const CheckpointsView = ({
  connectionName,
  schemaName,
  tableName
}: ICheckpointsViewProps) => {
  const [activeTab, setActiveTab] = useState('daily');
  const [updatedDailyCheckpoints, setUpdatedDailyCheckpoints] =
    useState<UIAllChecksModel>();
  const [updatedMonthlyCheckpoints, setUpdatedMonthlyCheckpoints] =
    useState<UIAllChecksModel>();
  const [isUpdated, setIsUpdated] = useState(false);

  const dispatch = useActionDispatch();

  const { dailyCheckpoints, monthlyCheckpoints, isUpdating } = useSelector(
    (state: IRootState) => state.table
  );

  useEffect(() => {
    setUpdatedDailyCheckpoints(dailyCheckpoints);
  }, [dailyCheckpoints]);

  useEffect(() => {
    setUpdatedMonthlyCheckpoints(monthlyCheckpoints);
  }, [monthlyCheckpoints]);

  useEffect(() => {
    dispatch(getTableDailyCheckpoints(connectionName, schemaName, tableName));
    dispatch(getTableMonthlyCheckpoints(connectionName, schemaName, tableName));
  }, [connectionName, schemaName, tableName, connectionName]);

  const onUpdate = async () => {
    if (activeTab === 'daily') {
      if (!updatedDailyCheckpoints) return;

      await dispatch(
        updateTableDailyCheckpoints(
          connectionName,
          schemaName,
          tableName,
          updatedDailyCheckpoints
        )
      );
      await dispatch(
        getTableDailyCheckpoints(connectionName, schemaName, tableName)
      );
    } else {
      if (!updatedMonthlyCheckpoints) return;

      await dispatch(
        updateTableMonthlyCheckpoints(
          connectionName,
          schemaName,
          tableName,
          updatedMonthlyCheckpoints
        )
      );
      await dispatch(
        getTableMonthlyCheckpoints(connectionName, schemaName, tableName)
      );
    }
    setIsUpdated(false);
  };

  const onDailyCheckpointsChange = (ui: UIAllChecksModel) => {
    setUpdatedDailyCheckpoints(ui);
    setIsUpdated(true);
  };

  const onMonthlyCheckpointsChange = (ui: UIAllChecksModel) => {
    setUpdatedMonthlyCheckpoints(ui);
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
            checksUI={updatedDailyCheckpoints}
            onChange={onDailyCheckpointsChange}
          />
        )}
        {activeTab === 'monthly' && (
          <DataQualityChecks
            checksUI={updatedMonthlyCheckpoints}
            onChange={onMonthlyCheckpointsChange}
          />
        )}
      </div>
    </div>
  );
};

export default CheckpointsView;
