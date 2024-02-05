import React, { useEffect, useState } from 'react';
import {
  getConnectionSchedulingGroup,
  resetConnectionSchedulingGroup,
  setIsUpdatedSchedulingGroup,
  setUpdatedSchedulingGroup,
  updateConnectionSchedulingGroup
} from '../../../redux/actions/connection.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { useSelector } from 'react-redux';
import ConnectionActionGroup from './ConnectionActionGroup';
import { useHistory, useLocation, useParams } from 'react-router-dom';
import ScheduleView from '../../ScheduleView';
import Tabs from '../../Tabs';
import { CheckRunMonitoringScheduleGroup } from '../../../shared/enums/scheduling.enum';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../../redux/selectors';
import { CheckTypes } from '../../../shared/routes';
import qs from 'query-string';

const ScheduleDetail = () => {
  const {
    connection,
    checkTypes
  }: { checkTypes: CheckTypes; connection: string } = useParams();

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
        return [
          {
            label: 'Partition Daily',
            value: CheckRunMonitoringScheduleGroup.partitioned_daily
          },
          {
            label: 'Partition Monthly',
            value: CheckRunMonitoringScheduleGroup.partitioned_monthly
          }
        ];
      }
      case CheckTypes.MONITORING: {
        return [
          {
            label: 'Monitoring Daily',
            value: CheckRunMonitoringScheduleGroup.monitoring_daily
          },
          {
            label: 'Monitoring Monthly',
            value: CheckRunMonitoringScheduleGroup.monitoring_monthly
          }
        ];
      }
      default: {
        return [
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
        ];
      }
    }
  };

  const [tabs, setTabs] = useState(getPageTabs());
  const dispatch = useActionDispatch();
  const location = useLocation() as any;
  const { activeTab = CheckRunMonitoringScheduleGroup.profiling } = qs.parse(
    location.search
  ) as any;
  const history = useHistory();

  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const {
    scheduleGroups,
    isUpdating
  }: {
    scheduleGroups?: any;
    isUpdating?: boolean;
  } = useSelector(getFirstLevelState(checkTypes));

  const updatedSchedule = scheduleGroups?.[activeTab]?.updatedSchedule;
  const isUpdatedSchedule = scheduleGroups?.[activeTab]?.isUpdatedSchedule;

  const onChangeTab = (tab: CheckRunMonitoringScheduleGroup) => {
    history.push(`${location.pathname}?activeTab=${tab}`);
  };

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

  useEffect(() => {
    if (updatedSchedule === null || updatedSchedule === undefined) {
      dispatch(
        getConnectionSchedulingGroup(
          checkTypes,
          firstLevelActiveTab,
          connection,
          activeTab
        )
      );
    }
  }, [connection, activeTab, updatedSchedule]);

  const onUpdate = async () => {
    if (updatedSchedule === null || updatedSchedule === undefined) {
      return;
    }
    await dispatch(
      updateConnectionSchedulingGroup(
        checkTypes,
        firstLevelActiveTab,
        connection,
        activeTab,
        updatedSchedule
      )
    );
    await dispatch(
      getConnectionSchedulingGroup(
        checkTypes,
        firstLevelActiveTab,
        connection,
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
    dispatch(resetConnectionSchedulingGroup(checkTypes, firstLevelActiveTab));
  }, [connection]);

  return (
    <div className="py-2">
      <ConnectionActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedSchedule}
        isUpdating={isUpdating}
      />
      <div className="border-b border-gray-300 px-0">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
      </div>
      <div className="px-8">
        <ScheduleView handleChange={handleChange} schedule={updatedSchedule} />
      </div>
    </div>
  );
};

export default ScheduleDetail;
