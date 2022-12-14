import React, { useEffect, useState } from 'react';
import Tabs from '../../Tabs';
import DataQualityChecks from '../../DataQualityChecks';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getColumnDailyCheckpoints,
  getColumnMonthlyCheckpoints,
  updateColumnDailyCheckpoints,
  updateColumnMonthlyCheckpoints
} from '../../../redux/actions/column.actions';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { UIAllChecksModel } from '../../../api';
import ColumnActionGroup from './ColumnActionGroup';

interface ICheckpointsViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
  columnName: string;
}

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

const CheckpointsView = ({
  connectionName,
  schemaName,
  tableName,
  columnName
}: ICheckpointsViewProps) => {
  const [activeTab, setActiveTab] = useState('daily');
  const [updatedDailyCheckpoints, setUpdatedDailyCheckpoints] =
    useState<UIAllChecksModel>();
  const [updatedMonthlyCheckpoints, setUpdatedMonthlyCheckpoints] =
    useState<UIAllChecksModel>();
  const [isUpdated, setIsUpdated] = useState(false);

  const dispatch = useActionDispatch();

  const { dailyCheckpoints, monthlyCheckpoints, isUpdating } = useSelector(
    (state: IRootState) => state.column
  );

  useEffect(() => {
    setUpdatedDailyCheckpoints(dailyCheckpoints);
  }, [dailyCheckpoints]);

  useEffect(() => {
    setUpdatedMonthlyCheckpoints(monthlyCheckpoints);
  }, [monthlyCheckpoints]);

  useEffect(() => {
    dispatch(
      getColumnDailyCheckpoints(
        connectionName,
        schemaName,
        tableName,
        columnName
      )
    );
    dispatch(
      getColumnMonthlyCheckpoints(
        connectionName,
        schemaName,
        tableName,
        columnName
      )
    );
  }, [connectionName, schemaName, tableName, connectionName]);

  const onUpdate = async () => {
    if (activeTab === 'daily') {
      if (!updatedDailyCheckpoints) return;

      await dispatch(
        updateColumnDailyCheckpoints(
          connectionName,
          schemaName,
          tableName,
          columnName,
          updatedDailyCheckpoints
        )
      );
      await dispatch(
        getColumnDailyCheckpoints(
          connectionName,
          schemaName,
          tableName,
          columnName
        )
      );
    } else {
      if (!updatedMonthlyCheckpoints) return;

      await dispatch(
        updateColumnMonthlyCheckpoints(
          connectionName,
          schemaName,
          tableName,
          columnName,
          updatedMonthlyCheckpoints
        )
      );
      await dispatch(
        getColumnMonthlyCheckpoints(
          connectionName,
          schemaName,
          tableName,
          columnName
        )
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
      <ColumnActionGroup
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
            className="max-h-checks"
          />
        )}
        {activeTab === 'monthly' && (
          <DataQualityChecks
            checksUI={updatedMonthlyCheckpoints}
            onChange={onMonthlyCheckpointsChange}
            className="max-h-checks"
          />
        )}
      </div>
    </div>
  );
};

export default CheckpointsView;
