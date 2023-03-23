import React, { useEffect, useState } from 'react';
import ActionGroup from './TableActionGroup';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableSchedulingGroup, resetTableSchedulingGroup, setIsUpdatedSchedulingGroup,
  setUpdatedSchedulingGroup,
  updateTableSchedulingGroup
} from '../../../redux/actions/table.actions';
import { useParams } from "react-router-dom";
import ScheduleView from "../../ScheduleView";
import { CheckRunRecurringScheduleGroup } from "../../../shared/enums/scheduling.enum";
import Tabs from "../../Tabs";

const pageTabs = [
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
]

const ScheduleDetail = () => {
  const { connection: connectionName, schema: schemaName, table: tableName }: { connection: string, schema: string, table: string } = useParams();
  const [tabs, setTabs] = useState(pageTabs);
  const [activeTab, setActiveTab] = useState<CheckRunRecurringScheduleGroup>(CheckRunRecurringScheduleGroup.profiling);

  const { isUpdating, scheduleGroups } = useSelector(
    (state: IRootState) => state.table
  );
  const updatedSchedule = scheduleGroups?.[activeTab]?.updatedSchedule;
  const isUpdatedSchedule = scheduleGroups?.[activeTab]?.isUpdatedSchedule;

  const dispatch = useActionDispatch();
  const onChangeTab = (tab: CheckRunRecurringScheduleGroup) => {
    setActiveTab(tab);
  }
  useEffect(() => {
    if (updatedSchedule === null || updatedSchedule === undefined) {
      dispatch(getTableSchedulingGroup(connectionName, schemaName, tableName, activeTab));
    }
  }, [connectionName, schemaName, tableName, activeTab, updatedSchedule]);

  const handleChange = (obj: any) => {
    dispatch(setIsUpdatedSchedulingGroup(activeTab, true));
    dispatch(
      setUpdatedSchedulingGroup(activeTab, {
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
      updateTableSchedulingGroup(connectionName, schemaName, tableName, activeTab, updatedSchedule)
    );
    await dispatch(getTableSchedulingGroup(connectionName, schemaName, tableName, activeTab));
    dispatch(setIsUpdatedSchedulingGroup(activeTab, false));
  };

  useEffect(() => {
    setTabs(prev => prev.map(tab => tab.value === activeTab ? ({ ...tab, isUpdated: isUpdatedSchedule }) : tab))
  }, [isUpdatedSchedule, activeTab])

  useEffect(() => {
    setTabs(prev => prev.map(tab => ({ ...tab, isUpdate: false })))
    dispatch(resetTableSchedulingGroup());
  }, [connectionName, schemaName, tableName])

  return (
    <div className="py-4 px-8">
      <ActionGroup
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
