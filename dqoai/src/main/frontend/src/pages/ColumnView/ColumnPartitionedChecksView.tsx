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
import { CheckResultsOverviewDataModel, UICheckContainerModel } from '../../api';
import ColumnActionGroup from './ColumnActionGroup';
import { CheckResultOverviewApi } from "../../services/apiClient";
import { useHistory, useParams } from "react-router-dom";
import { ROUTES } from "../../shared/routes";

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

const ColumnPartitionedChecksView = () => {
  const { connection, schema, table, column, tab, checkTypes }: { checkTypes: string, connection: string, schema: string, table: string, column: string, tab: 'daily' | 'monthly' } = useParams();
  const [tabs, setTabs] = useState(initTabs);

  const dispatch = useActionDispatch();
  const history = useHistory();

  const {
    columnBasic,
    dailyPartitionedChecks,
    monthlyPartitionedChecks,
    isUpdatedDailyPartitionedChecks,
    isUpdatedMonthlyPartitionedChecks,
    loading,
    isUpdating
  } = useSelector((state: IRootState) => state.column);

  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);

  const getCheckOverview = () => {
    CheckResultOverviewApi.getColumnPartitionedChecksOverview(connection, schema, table, column, tab).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    if (
      !dailyPartitionedChecks ||
      columnBasic?.connection_name !== connection ||
      columnBasic?.table?.schema_name !== schema ||
      columnBasic?.table?.table_name !== table ||
      columnBasic.column_name !== column
    ) {
      dispatch(
        getColumnDailyPartitionedChecks(
          connection,
          schema,
          table,
          column
        )
      );
    }
    if (
      !monthlyPartitionedChecks ||
      columnBasic?.connection_name !== connection ||
      columnBasic?.table?.schema_name !== schema ||
      columnBasic?.table?.table_name !== table ||
      columnBasic.column_name !== column
    ) {
      dispatch(
        getColumnMonthlyPartitionedChecks(
          connection,
          schema,
          table,
          column
        )
      );
    }
  }, [connection, schema, column, table, columnBasic]);

  const onUpdate = async () => {
    if (tab === 'daily') {
      if (!dailyPartitionedChecks) return;

      await dispatch(
        updateColumnDailyPartitionedChecks(
          connection,
          schema,
          table,
          column,
          dailyPartitionedChecks
        )
      );
      await dispatch(
        getColumnDailyPartitionedChecks(
          connection,
          schema,
          table,
          column
        )
      );
    } else {
      if (!monthlyPartitionedChecks) return;

      await dispatch(
        updateColumnMonthlyPartitionedChecks(
          connection,
          schema,
          table,
          column,
          monthlyPartitionedChecks
        )
      );
      await dispatch(
        getColumnMonthlyPartitionedChecks(
          connection,
          schema,
          table,
          column
        )
      );
    }
  };

  const onDailyPartitionedChecksChange = (ui: UICheckContainerModel) => {
    dispatch(setUpdatedDailyPartitionedChecks(ui));
  };

  const onMonthlyPartitionedChecksChange = (ui: UICheckContainerModel) => {
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

  const onChangeTab = (tab: string) => {
    history.push(ROUTES.COLUMN_LEVEL_PAGE(checkTypes, connection, schema, table, column, tab));
  };

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
        <Tabs tabs={tabs} activeTab={tab} onChange={onChangeTab} />
      </div>
      <div>
        {tab === 'daily' && (
          <DataQualityChecks
            onUpdate={onUpdate}
            checksUI={dailyPartitionedChecks}
            onChange={onDailyPartitionedChecksChange}
            className="max-h-checks"
            checkResultsOverview={checkResultsOverview}
            getCheckOverview={getCheckOverview}
            loading={loading}
          />
        )}
        {tab === 'monthly' && (
          <DataQualityChecks
            onUpdate={onUpdate}
            checksUI={monthlyPartitionedChecks}
            onChange={onMonthlyPartitionedChecksChange}
            className="max-h-checks"
            checkResultsOverview={checkResultsOverview}
            getCheckOverview={getCheckOverview}
            loading={loading}
          />
        )}
      </div>
    </div>
  );
};

export default ColumnPartitionedChecksView;
