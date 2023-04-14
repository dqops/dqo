import React, { useEffect, useState } from 'react';
import {
  getConnectionSchedulingGroup, resetConnectionSchedulingGroup, setIsUpdatedSchedulingGroup, setUpdatedSchedulingGroup,
  updateConnectionSchedulingGroup

} from '../../../redux/actions/connection.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { useSelector } from 'react-redux';
import ConnectionActionGroup from './ConnectionActionGroup';
import { useParams } from "react-router-dom";
import ScheduleView from "../../ScheduleView";
import Tabs from "../../Tabs";
import { CheckRunRecurringScheduleGroup } from "../../../shared/enums/scheduling.enum";
import { getFirstLevelActiveTab, getFirstLevelState } from "../../../redux/selectors";
import { CheckTypes } from "../../../shared/routes";

const pageTabs = [
  {
    label: 'Profiling',
    value: CheckRunRecurringScheduleGroup.profiling
  },
  {
    label: 'Recurring daily',
    value: CheckRunRecurringScheduleGroup.recurring_daily
  },
  {
    label: 'Recurring monthly',
    value: CheckRunRecurringScheduleGroup.recurring_monthly
  },
  {
    label: 'Partitioned daily',
    value: CheckRunRecurringScheduleGroup.partitioned_daily
  },
  {
    label: 'Partitioned monthly',
    value: CheckRunRecurringScheduleGroup.partitioned_monthly
  },
]

const ScheduleDetail = () => {
  const { connection, checkTypes }: { checkTypes: CheckTypes, connection: string } = useParams();
  const [tabs, setTabs] = useState(pageTabs);
  const [activeTab, setActiveTab] = useState<CheckRunRecurringScheduleGroup>(CheckRunRecurringScheduleGroup.profiling);
  const dispatch = useActionDispatch();

  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const { scheduleGroups, isUpdating }: {
    scheduleGroups?: any;
    isUpdating?: boolean;
  } = useSelector(getFirstLevelState(checkTypes));


  const updatedSchedule = scheduleGroups?.[activeTab]?.updatedSchedule;
  const isUpdatedSchedule = scheduleGroups?.[activeTab]?.isUpdatedSchedule;

  const onChangeTab = (tab: CheckRunRecurringScheduleGroup) => {
    setActiveTab(tab);
  }

  const handleChange = (obj: any) => {
    dispatch(setIsUpdatedSchedulingGroup(checkTypes, firstLevelActiveTab, activeTab, true));
    dispatch(
      setUpdatedSchedulingGroup(checkTypes, firstLevelActiveTab, activeTab, {
        ...updatedSchedule,
        ...obj
      })
    );
  };

  useEffect(() => {
    if (updatedSchedule === null || updatedSchedule === undefined) {
      dispatch(getConnectionSchedulingGroup(checkTypes, firstLevelActiveTab, connection, activeTab));
    }
  }, [connection, activeTab, updatedSchedule]);

  const onUpdate = async () => {
    if (updatedSchedule === null || updatedSchedule === undefined) {
      return;
    }
    await dispatch(updateConnectionSchedulingGroup(checkTypes, firstLevelActiveTab, connection, activeTab, updatedSchedule));
    await dispatch(getConnectionSchedulingGroup(checkTypes, firstLevelActiveTab, connection, activeTab));
    dispatch(setIsUpdatedSchedulingGroup(checkTypes, firstLevelActiveTab, activeTab, false));
  };

  useEffect(() => {
    setTabs(prev => prev.map(tab => tab.value === activeTab ? ({ ...tab, isUpdated: isUpdatedSchedule }) : tab))
  }, [isUpdatedSchedule, activeTab]);

  useEffect(() => {
    setTabs(prev => prev.map(tab => ({ ...tab, isUpdate: false })))
    dispatch(resetConnectionSchedulingGroup(checkTypes, firstLevelActiveTab));
  }, [connection]);

  useEffect(() => {
    if (checkTypes === 'profiling') {
      setTabs([
        {
          label: 'Profiling',
          value: CheckRunRecurringScheduleGroup.profiling
        },
      ]);
      setActiveTab(CheckRunRecurringScheduleGroup.profiling);
    } else if (checkTypes === 'recurring') {
      setTabs([
        {
          label: 'Recurring Daily',
          value: CheckRunRecurringScheduleGroup.recurring_daily
        },
        {
          label: 'Recurring Monthly',
          value: CheckRunRecurringScheduleGroup.recurring_monthly
        },
      ]);
      setActiveTab(CheckRunRecurringScheduleGroup.recurring_daily);
    } else if (checkTypes === 'partitioned') {
      setTabs([
        {
          label: 'Partitioned Daily',
          value: CheckRunRecurringScheduleGroup.partitioned_daily
        },
        {
          label: 'Partitioned Monthly',
          value: CheckRunRecurringScheduleGroup.partitioned_monthly
        },
      ]);
      setActiveTab(CheckRunRecurringScheduleGroup.partitioned_daily);
    } else {
      setTabs([
        {
          label: 'Profiling',
          value: CheckRunRecurringScheduleGroup.profiling
        },
        {
          label: 'Recurring Daily',
          value: CheckRunRecurringScheduleGroup.recurring_daily
        },
        {
          label: 'Recurring Monthly',
          value: CheckRunRecurringScheduleGroup.recurring_monthly
        },
        {
          label: 'Partitioned Daily',
          value: CheckRunRecurringScheduleGroup.partitioned_daily
        },
        {
          label: 'Partitioned Monthly',
          value: CheckRunRecurringScheduleGroup.partitioned_monthly
        },
      ]);
      setActiveTab(CheckRunRecurringScheduleGroup.profiling);
    }
  }, [checkTypes]);

  return (
    <div className="py-4 px-8">
      <ConnectionActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedSchedule}
        isUpdating={isUpdating}
      />
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
      </div>
      <ScheduleView handleChange={handleChange} schedule={updatedSchedule} />
    </div>
  );
};

export default ScheduleDetail;
