import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { CheckContainerModel, CheckResultsOverviewDataModel } from '../../api';
import ObservabilityStatus from '../../components/Connection/TableView/ObservabilityStatus/ObservabilityStatus';
import DataQualityChecks from '../../components/DataQualityChecks';
import Tabs from '../../components/Tabs';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getColumnDailyMonitoringChecks,
  getColumnMonthlyMonitoringChecks,
  setUpdatedDailyMonitoringChecks,
  setUpdatedMonthlyMonitoringChecks,
  updateColumnDailyMonitoringChecks,
  updateColumnMonthlyMonitoringChecks
} from '../../redux/actions/column.actions';
import { setActiveFirstLevelUrl } from '../../redux/actions/source.actions';
import { IRootState } from '../../redux/reducers';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../redux/selectors';
import { CheckResultOverviewApi } from '../../services/apiClient';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import ColumnActionGroup from './ColumnActionGroup';

const premiumTabs = [
  {
    label: 'Daily partitions',
    value: 'daily'
  },
  {
    label: 'Monthly partitions',
    value: 'monthly'
  }
];

const constansTabs = [
  {
    label: 'Observability status',
    value: 'observability-status'
  },
  {
    label: 'Data quality check editor',
    value: 'check-editor'
  }
];

const ColumnMonitoringChecksView = () => {
  const { userProfile } = useSelector((state: IRootState) => state.job || {});

  const {
    connection,
    schema,
    table,
    column,
    tab,
    checkTypes
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
    column: string;
    tab: 'observability-status' | 'check-editor';
  } = useDecodedParams();
  const dispatch = useActionDispatch();
  const history = useHistory();

  const {
    dailyMonitoring,
    monthlyMonitoring,
    isUpdatedDailyMonitoring,
    isUpdatedMonthlyMonitoring,
    isUpdating,
    loading
  } = useSelector(getFirstLevelState(checkTypes));
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const isPremiumAcount =
    userProfile &&
    userProfile.license_type &&
    userProfile.license_type?.toLowerCase() !== 'free' &&
    !userProfile.trial_period_expires_at;
  const [checkResultsOverview, setCheckResultsOverview] = useState<
    CheckResultsOverviewDataModel[]
  >([]);
  const [tabs, setTabs] = useState(constansTabs);
  const [secondTab, setSecondTab] = useState('daily');
  const getCheckOverview = () => {
    CheckResultOverviewApi.getColumnMonitoringChecksOverview(
      connection,
      schema,
      table,
      column,
      secondTab === 'daily' ? 'daily' : 'monthly'
    ).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    dispatch(
      getColumnDailyMonitoringChecks(
        checkTypes,
        firstLevelActiveTab,
        connection,
        schema,
        table,
        column
      )
    );
    dispatch(
      getColumnMonthlyMonitoringChecks(
        checkTypes,
        firstLevelActiveTab,
        connection,
        schema,
        table,
        column
      )
    );
  }, [checkTypes, firstLevelActiveTab, connection, schema, table, column]);

  const onUpdate = async () => {
    if (secondTab === 'daily') {
      if (!dailyMonitoring || !isUpdatedDailyMonitoring) return;

      await dispatch(
        updateColumnDailyMonitoringChecks(
          checkTypes,
          firstLevelActiveTab,
          connection,
          schema,
          table,
          column,
          dailyMonitoring
        )
      );
      await dispatch(
        getColumnDailyMonitoringChecks(
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
      if (!monthlyMonitoring || !isUpdatedMonthlyMonitoring) return;

      await dispatch(
        updateColumnMonthlyMonitoringChecks(
          checkTypes,
          firstLevelActiveTab,
          connection,
          schema,
          table,
          column,
          monthlyMonitoring
        )
      );
      await dispatch(
        getColumnMonthlyMonitoringChecks(
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

  const onDailyMonitoringChange = (ui: CheckContainerModel) => {
    dispatch(
      setUpdatedDailyMonitoringChecks(checkTypes, firstLevelActiveTab, ui)
    );
  };

  const onMonthlyMonitoringChange = (ui: CheckContainerModel) => {
    dispatch(
      setUpdatedMonthlyMonitoringChecks(checkTypes, firstLevelActiveTab, ui)
    );
  };

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'daily'
          ? { ...item, isUpdated: isUpdatedDailyMonitoring }
          : item
      )
    );
  }, [isUpdatedDailyMonitoring]);

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'monthly'
          ? { ...item, isUpdated: isUpdatedMonthlyMonitoring }
          : item
      )
    );
  }, [isUpdatedMonthlyMonitoring]);

  const onChangeTab = (tab: string) => {
    dispatch(
      setActiveFirstLevelUrl(
        checkTypes,
        firstLevelActiveTab,
        ROUTES.COLUMN_LEVEL_PAGE(
          checkTypes,
          connection,
          schema,
          table,
          column,
          tab
        )
      )
    );
    history.push(
      ROUTES.COLUMN_LEVEL_PAGE(
        checkTypes,
        connection,
        schema,
        table,
        column,
        tab
      )
    );
  };

  return (
    <div className="flex flex-col overflow-x-auto overflow-y-auto">
      <ColumnActionGroup
        shouldDelete={false}
        onUpdate={onUpdate}
        isUpdated={isUpdatedDailyMonitoring || isUpdatedMonthlyMonitoring}
        isUpdating={isUpdating}
      />
      <div className="border-b border-gray-300">
        <Tabs
          tabs={tabs}
          activeTab={tab}
          onChange={onChangeTab}
          className="w-full overflow-hidden max-w-full"
        />
      </div>
      {isPremiumAcount && tab === 'check-editor' && (
        <div className="border-b border-gray-300 pt-2">
          <Tabs
            tabs={premiumTabs}
            activeTab={secondTab}
            onChange={setSecondTab}
          />
        </div>
      )}
      {tab === 'observability-status' && <ObservabilityStatus />}
      {tab === 'check-editor' && secondTab === 'daily' && (
        <DataQualityChecks
          onUpdate={onUpdate}
          checksUI={dailyMonitoring}
          onChange={onDailyMonitoringChange}
          checkResultsOverview={checkResultsOverview}
          getCheckOverview={getCheckOverview}
          loading={loading}
        />
      )}
      {tab === 'check-editor' && secondTab === 'monthly' && (
        <DataQualityChecks
          onUpdate={onUpdate}
          checksUI={monthlyMonitoring}
          onChange={onMonthlyMonitoringChange}
          checkResultsOverview={checkResultsOverview}
          getCheckOverview={getCheckOverview}
          loading={loading}
        />
      )}
    </div>
  );
};

export default ColumnMonitoringChecksView;
