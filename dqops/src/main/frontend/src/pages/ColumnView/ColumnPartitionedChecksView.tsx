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
import { CheckResultsOverviewDataModel, CheckContainerModel } from '../../api';
import ColumnActionGroup from './ColumnActionGroup';
import { CheckResultOverviewApi } from "../../services/apiClient";
import { useHistory, useParams } from "react-router-dom";
import { CheckTypes, ROUTES } from "../../shared/routes";
import { getFirstLevelActiveTab, getFirstLevelState } from "../../redux/selectors";

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
  const { connection, schema, table, column, tab, checkTypes }: { checkTypes: CheckTypes, connection: string, schema: string, table: string, column: string, tab: 'daily' | 'monthly' } = useParams();
  const [tabs, setTabs] = useState(initTabs);

  const dispatch = useActionDispatch();
  const history = useHistory();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const {
    dailyPartitionedChecks,
    monthlyPartitionedChecks,
    isUpdatedDailyPartitionedChecks,
    isUpdatedMonthlyPartitionedChecks,
    loading,
    isUpdating
  } = useSelector(getFirstLevelState(checkTypes));

  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);

  const getCheckOverview = () => {
    CheckResultOverviewApi.getColumnPartitionedChecksOverview(connection, schema, table, column, tab).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    dispatch(
      getColumnDailyPartitionedChecks(
        checkTypes,
        firstLevelActiveTab,
        connection,
        schema,
        table,
        column
      )
    );
    dispatch(
      getColumnMonthlyPartitionedChecks(
        checkTypes,
        firstLevelActiveTab,
        connection,
        schema,
        table,
        column
      )
    );
  }, [checkTypes, firstLevelActiveTab, connection, schema, column, table]);

  const onUpdate = async () => {
    if (tab === 'daily') {
      if (!dailyPartitionedChecks) return;

      await dispatch(
        updateColumnDailyPartitionedChecks(
          checkTypes,
          firstLevelActiveTab,
          connection,
          schema,
          table,
          column,
          dailyPartitionedChecks
        )
      );
      await dispatch(
        getColumnDailyPartitionedChecks(
          checkTypes,
          firstLevelActiveTab,
          connection,
          schema,
          table,
          column,
          false
        )
      );
    } else {
      if (!monthlyPartitionedChecks) return;

      await dispatch(
        updateColumnMonthlyPartitionedChecks(
          checkTypes,
          firstLevelActiveTab,
          connection,
          schema,
          table,
          column,
          monthlyPartitionedChecks
        )
      );
      await dispatch(
        getColumnMonthlyPartitionedChecks(
          checkTypes,
          firstLevelActiveTab,
          connection,
          schema,
          table,
          column,
          false
        )
      );
    }
  };

  const onDailyPartitionedChecksChange = (ui: CheckContainerModel) => {
    dispatch(setUpdatedDailyPartitionedChecks(checkTypes, firstLevelActiveTab, ui));
  };

  const onMonthlyPartitionedChecksChange = (ui: CheckContainerModel) => {
    dispatch(setUpdatedMonthlyPartitionedChecks(checkTypes, firstLevelActiveTab, ui));
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

  useEffect(() => {
    if (tab !== 'daily' && tab !== 'monthly') {
      history.push(ROUTES.COLUMN_LEVEL_PAGE(checkTypes, connection, schema, table, column, 'daily'));
    }
  }, [tab]);

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
