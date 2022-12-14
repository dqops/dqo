import React, { useEffect, useState } from 'react';
import Tabs from '../../Tabs';
import DataQualityChecks from '../../DataQualityChecks';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getColumnDailyPartitionedChecks,
  getColumnMonthlyPartitionedChecks,
  updateColumnDailyPartitionedChecks,
  updateColumnMonthlyPartitionedChecks
} from '../../../redux/actions/column.actions';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { UIAllChecksModel } from '../../../api';
import ColumnActionGroup from './ColumnActionGroup';

interface IColumnPartitionedChecksViewProps {
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

const ColumnPartitionedChecksView = ({
  connectionName,
  schemaName,
  tableName,
  columnName
}: IColumnPartitionedChecksViewProps) => {
  const [activeTab, setActiveTab] = useState('daily');
  const [updatedDailyPartitionedChecks, setUpdatedDailyPartitionedChecks] =
    useState<UIAllChecksModel>();
  const [updatedMonthlyPartitionedChecks, setUpdatedMonthlyPartitionedChecks] =
    useState<UIAllChecksModel>();
  const [isUpdated, setIsUpdated] = useState(false);

  const dispatch = useActionDispatch();

  const { dailyPartitionedChecks, monthlyPartitionedChecks, isUpdating } =
    useSelector((state: IRootState) => state.column);

  useEffect(() => {
    setUpdatedDailyPartitionedChecks(dailyPartitionedChecks);
  }, [dailyPartitionedChecks]);

  useEffect(() => {
    setUpdatedMonthlyPartitionedChecks(monthlyPartitionedChecks);
  }, [monthlyPartitionedChecks]);

  useEffect(() => {
    dispatch(
      getColumnDailyPartitionedChecks(
        connectionName,
        schemaName,
        tableName,
        columnName
      )
    );
    dispatch(
      getColumnMonthlyPartitionedChecks(
        connectionName,
        schemaName,
        tableName,
        columnName
      )
    );
  }, [connectionName, schemaName, tableName, connectionName]);

  const onUpdate = async () => {
    if (activeTab === 'daily') {
      if (!updatedDailyPartitionedChecks) return;

      await dispatch(
        updateColumnDailyPartitionedChecks(
          connectionName,
          schemaName,
          tableName,
          columnName,
          updatedDailyPartitionedChecks
        )
      );
      await dispatch(
        getColumnDailyPartitionedChecks(
          connectionName,
          schemaName,
          tableName,
          columnName
        )
      );
    } else {
      if (!updatedMonthlyPartitionedChecks) return;

      await dispatch(
        updateColumnMonthlyPartitionedChecks(
          connectionName,
          schemaName,
          tableName,
          columnName,
          updatedMonthlyPartitionedChecks
        )
      );
      await dispatch(
        getColumnMonthlyPartitionedChecks(
          connectionName,
          schemaName,
          tableName,
          columnName
        )
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
            checksUI={updatedDailyPartitionedChecks}
            onChange={onDailyPartitionedChecksChange}
            className="max-h-checks"
          />
        )}
        {activeTab === 'monthly' && (
          <DataQualityChecks
            checksUI={updatedMonthlyPartitionedChecks}
            onChange={onMonthlyPartitionedChecksChange}
            className="max-h-checks"
          />
        )}
      </div>
    </div>
  );
};

export default ColumnPartitionedChecksView;
