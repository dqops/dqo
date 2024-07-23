import qs from 'query-string';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableSchedulingGroup,
  resetTableSchedulingGroup,
  setIsUpdatedSchedulingGroup,
  setUpdatedSchedulingGroup,
  updateTableSchedulingGroup
} from '../../../redux/actions/table.actions';
import { IRootState } from '../../../redux/reducers';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../../redux/selectors';
import { CheckRunMonitoringScheduleGroup } from '../../../shared/enums/scheduling.enum';
import { CheckTypes } from '../../../shared/routes';
import { useDecodedParams } from '../../../utils';
import ScheduleView from '../../ScheduleView';
import Tabs from '../../Tabs';
import ActionGroup from './TableActionGroup';

const ScheduleDetail = () => {
  const {
    checkTypes,
    connection: connectionName,
    schema: schemaName,
    table: tableName
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
  } = useDecodedParams();
  const { userProfile } = useSelector((state: IRootState) => state.job);
  const getPageTabs = () => {
    switch (checkTypes) {
      case CheckTypes.PROFILING: {
        return [
          {
            label: 'Profiling',
            value: CheckRunMonitoringScheduleGroup.profiling
          }
        ];
      }
      case CheckTypes.PARTITIONED: {
        return userProfile &&
          userProfile.license_type &&
          userProfile.license_type?.toLowerCase() !== 'free' &&
          !userProfile.trial_period_expires_at
          ? [
              {
                label: 'Partition Daily',
                value: CheckRunMonitoringScheduleGroup.partitioned_daily
              },
              {
                label: 'Partition Monthly',
                value: CheckRunMonitoringScheduleGroup.partitioned_monthly
              }
            ]
          : [
              {
                label: 'Partition',
                value: CheckRunMonitoringScheduleGroup.partitioned_daily
              }
            ];
      }
      case CheckTypes.MONITORING: {
        return userProfile &&
          userProfile.license_type &&
          userProfile.license_type?.toLowerCase() !== 'free' &&
          !userProfile.trial_period_expires_at
          ? [
              {
                label: 'Monitoring Daily',
                value: CheckRunMonitoringScheduleGroup.monitoring_daily
              },
              {
                label: 'Monitoring Monthly',
                value: CheckRunMonitoringScheduleGroup.monitoring_monthly
              }
            ]
          : [
              {
                label: 'Monitoring',
                value: CheckRunMonitoringScheduleGroup.monitoring_daily
              }
            ];
      }
      default: {
        return userProfile &&
          userProfile.license_type &&
          userProfile.license_type?.toLowerCase() !== 'free' &&
          !userProfile.trial_period_expires_at
          ? [
              {
                label: 'Profiling',
                value: CheckRunMonitoringScheduleGroup.profiling
              },
              {
                label: 'Monitoring Daily',
                value: CheckRunMonitoringScheduleGroup.monitoring_daily
              },
              {
                label: 'Monitoring Monthly',
                value: CheckRunMonitoringScheduleGroup.monitoring_monthly
              },
              {
                label: 'Partition Daily',
                value: CheckRunMonitoringScheduleGroup.partitioned_daily
              },
              {
                label: 'Partition Monthly',
                value: CheckRunMonitoringScheduleGroup.partitioned_monthly
              }
            ]
          : [
              {
                label: 'Profiling',
                value: CheckRunMonitoringScheduleGroup.profiling
              },
              {
                label: 'Monitoring',
                value: CheckRunMonitoringScheduleGroup.monitoring_daily
              },

              {
                label: 'Partition',
                value: CheckRunMonitoringScheduleGroup.partitioned_daily
              }
            ];
      }
    }
  };
  const [tabs, setTabs] = useState(getPageTabs());
  const { activeTab = CheckRunMonitoringScheduleGroup.profiling } = qs.parse(
    location.search
  ) as any;

  const { isUpdating, scheduleGroups } = useSelector(
    getFirstLevelState(checkTypes)
  );
  const updatedSchedule = scheduleGroups?.[activeTab]?.updatedSchedule;
  const isUpdatedSchedule = scheduleGroups?.[activeTab]?.isUpdatedSchedule;
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const history = useHistory();

  const dispatch = useActionDispatch();
  const onChangeTab = (tab: CheckRunMonitoringScheduleGroup) => {
    history.push(`${location.pathname}?activeTab=${tab}`);
  };

  useEffect(() => {
    if (updatedSchedule === null || updatedSchedule === undefined) {
      dispatch(
        getTableSchedulingGroup(
          checkTypes,
          firstLevelActiveTab,
          connectionName,
          schemaName,
          tableName,
          activeTab
        )
      );
    }
  }, [connectionName, schemaName, tableName, activeTab, updatedSchedule]);

  const handleChange = (obj: any) => {
    dispatch(
      setIsUpdatedSchedulingGroup(
        checkTypes,
        firstLevelActiveTab,
        activeTab,
        true
      )
    );
    dispatch(
      setUpdatedSchedulingGroup(checkTypes, firstLevelActiveTab, activeTab, {
        ...updatedSchedule,
        ...obj
      })
    );
  };

  const onUpdate = async () => {
    if (updatedSchedule === null || updatedSchedule === undefined) {
      return;
    }
    await dispatch(
      updateTableSchedulingGroup(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        activeTab,
        updatedSchedule
      )
    );
    await dispatch(
      getTableSchedulingGroup(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        activeTab
      )
    );
    dispatch(
      setIsUpdatedSchedulingGroup(
        checkTypes,
        firstLevelActiveTab,
        activeTab,
        false
      )
    );
  };

  useEffect(() => {
    setTabs((prev) =>
      prev.map((tab) =>
        tab.value === activeTab ? { ...tab, isUpdated: isUpdatedSchedule } : tab
      )
    );
  }, [isUpdatedSchedule, activeTab]);

  useEffect(() => {
    setTabs((prev) => prev.map((tab) => ({ ...tab, isUpdate: false })));
    dispatch(resetTableSchedulingGroup(checkTypes, firstLevelActiveTab));
  }, [checkTypes, firstLevelActiveTab]);
  return (
    <div className="py-2">
      <ActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedSchedule}
        isUpdating={isUpdating}
      />
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
      </div>
      <div className="px-8">
        <ScheduleView handleChange={handleChange} schedule={updatedSchedule} />
      </div>
    </div>
  );
};

export default ScheduleDetail;
