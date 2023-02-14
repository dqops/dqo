import React, { useEffect, useState } from 'react';
import {
  getConnectionSchedulingGroup, resetConnectionSchedulingGroup, setIsUpdatedSchedulingGroup, setUpdatedSchedulingGroup,
  updateConnectionSchedulingGroup

} from '../../../redux/actions/connection.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import ConnectionActionGroup from './ConnectionActionGroup';
import { useParams } from "react-router-dom";
import ScheduleView from "../../ScheduleView";
import Tabs from "../../Tabs";
import { CheckRunRecurringScheduleGroup } from "../../../shared/enums/scheduling.enum";

const pageTabs = [
  {
    label: 'Profiling',
    value: CheckRunRecurringScheduleGroup.profiling
  },
  {
    label: 'Daily',
    value: CheckRunRecurringScheduleGroup.daily
  },
  {
    label: 'Monthly',
    value: CheckRunRecurringScheduleGroup.monthly
  },
]

const ScheduleDetail = () => {
  const { connection }: { connection: string } = useParams();
  const [tabs, setTabs] = useState(pageTabs);
  const [activeTab, setActiveTab] = useState<CheckRunRecurringScheduleGroup>(CheckRunRecurringScheduleGroup.profiling);
  const dispatch = useActionDispatch();
  const { scheduleGroups } = useSelector(
    (state: IRootState) => state.connection
  );
  const updatedSchedule = scheduleGroups?.[activeTab]?.updatedSchedule;
  const isUpdatedSchedule = scheduleGroups?.[activeTab]?.isUpdatedSchedule;
  const { isUpdating } = useSelector((state: IRootState) => state.connection);

  const onChangeTab = (tab: CheckRunRecurringScheduleGroup) => {
    setActiveTab(tab);
  }

  const handleChange = (obj: any) => {
    dispatch(setIsUpdatedSchedulingGroup(activeTab, true));
    dispatch(
      setUpdatedSchedulingGroup(activeTab, {
        ...updatedSchedule,
        ...obj
      })
    );
  };

  useEffect(() => {
    if (updatedSchedule === null || updatedSchedule === undefined) {
      dispatch(getConnectionSchedulingGroup(connection, activeTab));
    }
  }, [connection, activeTab, updatedSchedule]);

  const onUpdate = async () => {
    if (updatedSchedule === null || updatedSchedule === undefined) {
      return;
    }
    await dispatch(updateConnectionSchedulingGroup(connection, activeTab, updatedSchedule));
    await dispatch(getConnectionSchedulingGroup(connection, activeTab));
    dispatch(setIsUpdatedSchedulingGroup(activeTab, false));
  };

  useEffect(() => {
    setTabs(prev => prev.map(tab => tab.value === activeTab ? ({ ...tab, isUpdated: isUpdatedSchedule }) : tab))
  }, [isUpdatedSchedule, activeTab]);

  useEffect(() => {
    setTabs(prev => prev.map(tab => ({ ...tab, isUpdate: false })))
    dispatch(resetConnectionSchedulingGroup());
  }, [connection]);
  return (
    <div className="p-4">
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
