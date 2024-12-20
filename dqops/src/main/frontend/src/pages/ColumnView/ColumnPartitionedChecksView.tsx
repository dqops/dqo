import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { CheckContainerModel, CheckResultsOverviewDataModel } from '../../api';
import ObservabilityStatus from '../../components/Connection/TableView/ObservabilityStatus/ObservabilityStatus';
import DataQualityChecks from '../../components/DataQualityChecks';
import Tabs from '../../components/Tabs';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getColumnDailyPartitionedChecks,
  getColumnMonthlyPartitionedChecks,
  setUpdatedDailyPartitionedChecks,
  setUpdatedMonthlyPartitionedChecks,
  updateColumnDailyPartitionedChecks,
  updateColumnMonthlyPartitionedChecks
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

const ColumnPartitionedChecksView = () => {
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
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  const isPremiumAcount =
    userProfile &&
    userProfile.license_type &&
    userProfile.license_type?.toLowerCase() !== 'free' &&
    !userProfile.trial_period_expires_at;
  const [tabs, setTabs] = useState(constansTabs);
  const [secondTab, setSecondTab] = useState('daily');

  const {
    dailyPartitionedChecks,
    monthlyPartitionedChecks,
    isUpdatedDailyPartitionedChecks,
    isUpdatedMonthlyPartitionedChecks,
    loading,
    isUpdating
  } = useSelector(getFirstLevelState(checkTypes));

  const [checkResultsOverview, setCheckResultsOverview] = useState<
    CheckResultsOverviewDataModel[]
  >([]);

  const getCheckOverview = () => {
    CheckResultOverviewApi.getColumnPartitionedChecksOverview(
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
  }, [
    checkTypes,
    firstLevelActiveTab,
    connection,
    schema,
    column,
    table,
    secondTab
  ]);

  const onUpdate = async () => {
    if (secondTab === 'daily') {
      if (!dailyPartitionedChecks || !isUpdatedDailyPartitionedChecks) return;

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
      if (!monthlyPartitionedChecks || !isUpdatedMonthlyPartitionedChecks)
        return;

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
    dispatch(
      setUpdatedDailyPartitionedChecks(checkTypes, firstLevelActiveTab, ui)
    );
  };

  const onMonthlyPartitionedChecksChange = (ui: CheckContainerModel) => {
    dispatch(
      setUpdatedMonthlyPartitionedChecks(checkTypes, firstLevelActiveTab, ui)
    );
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
        isUpdated={
          isUpdatedDailyPartitionedChecks || isUpdatedMonthlyPartitionedChecks
        }
        isUpdating={isUpdating}
      />
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={tab} onChange={onChangeTab} />
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
          checksUI={dailyPartitionedChecks}
          onChange={onDailyPartitionedChecksChange}
          checkResultsOverview={checkResultsOverview}
          getCheckOverview={getCheckOverview}
          loading={loading}
        />
      )}
      {tab === 'check-editor' && secondTab === 'monthly' && (
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
  );
};

export default ColumnPartitionedChecksView;
