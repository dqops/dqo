import React, { useEffect, useState } from 'react';
import Tabs from '../../components/Tabs';
import DataQualityChecks from '../../components/DataQualityChecks';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getColumnDailyPartitionedChecks,
  getColumnMonthlyPartitionedChecks,
  setUpdatedDailyPartitionedChecks,
  setUpdatedMonthlyPartitionedChecks,
  updateColumnDailyPartitionedChecks,
  updateColumnMonthlyPartitionedChecks
} from '../../redux/actions/column.actions';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { CheckResultsOverviewDataModel, UIAllChecksModel } from '../../api';
import ColumnActionGroup from './ColumnActionGroup';
import { CheckResultOverviewApi } from "../../services/apiClient";

interface IColumnPartitionedChecksViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
  columnName: string;
}

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

const ColumnPartitionedChecksView = ({
  connectionName,
  schemaName,
  tableName,
  columnName
}: IColumnPartitionedChecksViewProps) => {
  const [activeTab, setActiveTab] = useState('daily');
  const [tabs, setTabs] = useState(initTabs);

  const dispatch = useActionDispatch();

  const {
    columnBasic,
    dailyPartitionedChecks,
    monthlyPartitionedChecks,
    isUpdatedDailyPartitionedChecks,
    isUpdatedMonthlyPartitionedChecks,
    isUpdating
  } = useSelector((state: IRootState) => state.column);

  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);

  const getCheckOverview = () => {
    CheckResultOverviewApi.getColumnPartitionedChecksOverview(connectionName, schemaName, tableName, columnName, activeTab as any).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    if (
      !dailyPartitionedChecks ||
      columnBasic?.connection_name !== connectionName ||
      columnBasic?.table?.schema_name !== schemaName ||
      columnBasic?.table?.table_name !== tableName ||
      columnBasic.column_name !== columnName
    ) {
      dispatch(
        getColumnDailyPartitionedChecks(
          connectionName,
          schemaName,
          tableName,
          columnName
        )
      );
    }
    if (
      !monthlyPartitionedChecks ||
      columnBasic?.connection_name !== connectionName ||
      columnBasic?.table?.schema_name !== schemaName ||
      columnBasic?.table?.table_name !== tableName ||
      columnBasic.column_name !== columnName
    ) {
      dispatch(
        getColumnMonthlyPartitionedChecks(
          connectionName,
          schemaName,
          tableName,
          columnName
        )
      );
    }
  }, [connectionName, schemaName, columnName, tableName, columnBasic]);

  const onUpdate = async () => {
    if (activeTab === 'daily') {
      if (!dailyPartitionedChecks) return;

      await dispatch(
        updateColumnDailyPartitionedChecks(
          connectionName,
          schemaName,
          tableName,
          columnName,
          dailyPartitionedChecks
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
      if (!monthlyPartitionedChecks) return;

      await dispatch(
        updateColumnMonthlyPartitionedChecks(
          connectionName,
          schemaName,
          tableName,
          columnName,
          monthlyPartitionedChecks
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
      <ColumnActionGroup
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
      <div>
        {activeTab === 'daily' && (
          <DataQualityChecks
            onUpdate={onUpdate}
            checksUI={dailyPartitionedChecks}
            onChange={onDailyPartitionedChecksChange}
            className="max-h-checks"
            checkResultsOverview={checkResultsOverview}
            getCheckOverview={getCheckOverview}
          />
        )}
        {activeTab === 'monthly' && (
          <DataQualityChecks
            onUpdate={onUpdate}
            checksUI={monthlyPartitionedChecks}
            onChange={onMonthlyPartitionedChecksChange}
            className="max-h-checks"
            checkResultsOverview={checkResultsOverview}
            getCheckOverview={getCheckOverview}
          />
        )}
      </div>
    </div>
  );
};

export default ColumnPartitionedChecksView;
