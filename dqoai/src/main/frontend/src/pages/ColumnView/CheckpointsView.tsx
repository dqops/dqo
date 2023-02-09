import React, { useEffect, useState } from 'react';
import Tabs from '../../components/Tabs';
import DataQualityChecks from '../../components/DataQualityChecks';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getColumnDailyCheckpoints,
  getColumnMonthlyCheckpoints,
  setUpdatedDailyCheckPoints,
  setUpdatedMonthlyCheckPoints,
  updateColumnDailyCheckpoints,
  updateColumnMonthlyCheckpoints
} from '../../redux/actions/column.actions';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { CheckResultsOverviewDataModel, UIAllChecksModel } from '../../api';
import ColumnActionGroup from './ColumnActionGroup';
import { CheckResultOverviewApi } from "../../services/apiClient";

interface ICheckpointsViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
  columnName: string;
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
  tableName,
  columnName
}: ICheckpointsViewProps) => {
  const [activeTab, setActiveTab] = useState('daily');
  const [tabs, setTabs] = useState(initTabs);
  const dispatch = useActionDispatch();

  const {
    columnBasic,
    dailyCheckpoints,
    monthlyCheckpoints,
    isUpdatedDailyCheckpoints,
    isUpdatedMonthlyCheckpoints,
    isUpdating
  } = useSelector((state: IRootState) => state.column);

  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);

  const getCheckOverview = () => {
    CheckResultOverviewApi.getColumnCheckpointsOverview(connectionName, schemaName, tableName, columnName, activeTab as any).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    if (
      !dailyCheckpoints ||
      columnBasic?.connection_name !== connectionName ||
      columnBasic?.table?.schema_name !== schemaName ||
      columnBasic?.table?.table_name !== tableName ||
      columnBasic.column_name !== columnName
    ) {
      dispatch(
        getColumnDailyCheckpoints(
          connectionName,
          schemaName,
          tableName,
          columnName
        )
      );
    }
    if (
      !monthlyCheckpoints ||
      columnBasic?.connection_name !== connectionName ||
      columnBasic?.table?.schema_name !== schemaName ||
      columnBasic?.table?.table_name !== tableName ||
      columnBasic.column_name !== columnName
    ) {
      dispatch(
        getColumnMonthlyCheckpoints(
          connectionName,
          schemaName,
          tableName,
          columnName
        )
      );
    }
  }, [connectionName, schemaName, tableName, columnName, columnBasic]);

  const onUpdate = async () => {
    if (activeTab === 'daily') {
      if (!dailyCheckpoints) return;

      await dispatch(
        updateColumnDailyCheckpoints(
          connectionName,
          schemaName,
          tableName,
          columnName,
          dailyCheckpoints
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
      if (!monthlyCheckpoints) return;

      await dispatch(
        updateColumnMonthlyCheckpoints(
          connectionName,
          schemaName,
          tableName,
          columnName,
          monthlyCheckpoints
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
      <ColumnActionGroup
        shouldDelete={false}
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
            onUpdate={onUpdate}
            checksUI={dailyCheckpoints}
            onChange={onDailyCheckpointsChange}
            className="max-h-checks"
            checkResultsOverview={checkResultsOverview}
            getCheckOverview={getCheckOverview}
          />
        )}
        {activeTab === 'monthly' && (
          <DataQualityChecks
            onUpdate={onUpdate}
            checksUI={monthlyCheckpoints}
            onChange={onMonthlyCheckpointsChange}
            className="max-h-checks"
            checkResultsOverview={checkResultsOverview}
            getCheckOverview={getCheckOverview}
          />
        )}
      </div>
    </div>
  );
};

export default CheckpointsView;
