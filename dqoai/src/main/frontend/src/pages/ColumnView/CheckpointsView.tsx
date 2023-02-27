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
import { useHistory, useParams } from "react-router-dom";
import { ROUTES } from "../../shared/routes";

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
  const { connection, schema, table, column, tab, checkTypes }: { checkTypes: string, connection: string, schema: string, table: string, column: string, tab: 'daily' | 'monthly' } = useParams();
  const [tabs, setTabs] = useState(initTabs);
  const dispatch = useActionDispatch();
  const history = useHistory();

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
    CheckResultOverviewApi.getColumnCheckpointsOverview(connection, schema, table, column, tab).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    if (
      !dailyCheckpoints ||
      columnBasic?.connection_name !== connection ||
      columnBasic?.table?.schema_name !== schema ||
      columnBasic?.table?.table_name !== table ||
      columnBasic.column_name !== column
    ) {
      dispatch(
        getColumnDailyCheckpoints(
          connection,
          schema,
          table,
          column
        )
      );
    }
    if (
      !monthlyCheckpoints ||
      columnBasic?.connection_name !== connection ||
      columnBasic?.table?.schema_name !== schema ||
      columnBasic?.table?.table_name !== table ||
      columnBasic.column_name !== column
    ) {
      dispatch(
        getColumnMonthlyCheckpoints(
          connection,
          schema,
          table,
          column
        )
      );
    }
  }, [connection, schema, table, column, columnBasic]);

  const onUpdate = async () => {
    if (tab === 'daily') {
      if (!dailyCheckpoints) return;

      await dispatch(
        updateColumnDailyCheckpoints(
          connection,
          schema,
          table,
          column,
          dailyCheckpoints
        )
      );
      await dispatch(
        getColumnDailyCheckpoints(
          connection,
          schema,
          table,
          column
        )
      );
    } else {
      if (!monthlyCheckpoints) return;

      await dispatch(
        updateColumnMonthlyCheckpoints(
          connection,
          schema,
          table,
          column,
          monthlyCheckpoints
        )
      );
      await dispatch(
        getColumnMonthlyCheckpoints(
          connection,
          schema,
          table,
          column
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

  const onChangeTab = (tab: string) => {
    history.push(ROUTES.COLUMN_LEVEL_PAGE(checkTypes, connection, schema, table, column, tab));
  };

  return (
    <div className="py-2">
      <ColumnActionGroup
        shouldDelete={false}
        onUpdate={onUpdate}
        isUpdated={isUpdatedDailyCheckpoints || isUpdatedMonthlyCheckpoints}
        isUpdating={isUpdating}
      />
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={tab} onChange={onChangeTab} />
      </div>
      <div>
        {tab === 'daily' && (
          <DataQualityChecks
            onUpdate={onUpdate}
            checksUI={dailyCheckpoints}
            onChange={onDailyCheckpointsChange}
            className="max-h-checks"
            checkResultsOverview={checkResultsOverview}
            getCheckOverview={getCheckOverview}
          />
        )}
        {tab === 'monthly' && (
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
