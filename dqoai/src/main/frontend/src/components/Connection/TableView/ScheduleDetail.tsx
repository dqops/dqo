import React, { useEffect, useState } from 'react';
import ActionGroup from './TableActionGroup';
import { useSelector } from 'react-redux';
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
import { getFirstLevelActiveTab, getFirstLevelState } from "../../../redux/selectors";
import { CheckTypes } from "../../../shared/routes";

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
  const { checkTypes, connection: connectionName, schema: schemaName, table: tableName }: { checkTypes: CheckTypes, connection: string, schema: string, table: string } = useParams();
  const [tabs, setTabs] = useState(pageTabs);
  const [activeTab, setActiveTab] = useState<CheckRunRecurringScheduleGroup>(CheckRunRecurringScheduleGroup.profiling);

  const { isUpdating, scheduleGroups } = useSelector(getFirstLevelState(checkTypes));
  const updatedSchedule = scheduleGroups?.[activeTab]?.updatedSchedule;
  const isUpdatedSchedule = scheduleGroups?.[activeTab]?.isUpdatedSchedule;
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const dispatch = useActionDispatch();
  const onChangeTab = (tab: CheckRunRecurringScheduleGroup) => {
    setActiveTab(tab);
  }
  useEffect(() => {
    if (updatedSchedule === null || updatedSchedule === undefined) {
      dispatch(getTableSchedulingGroup(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, activeTab));
    }
  }, [connectionName, schemaName, tableName, activeTab, updatedSchedule]);

  const handleChange = (obj: any) => {
    dispatch(setIsUpdatedSchedulingGroup(checkTypes, firstLevelActiveTab, activeTab, true));
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
      updateTableSchedulingGroup(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, activeTab, updatedSchedule)
    );
    await dispatch(getTableSchedulingGroup(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, activeTab));
    dispatch(setIsUpdatedSchedulingGroup(checkTypes, firstLevelActiveTab, activeTab, false));
  };

  useEffect(() => {
    setTabs(prev => prev.map(tab => tab.value === activeTab ? ({ ...tab, isUpdated: isUpdatedSchedule }) : tab))
  }, [isUpdatedSchedule, activeTab])

  useEffect(() => {
    setTabs(prev => prev.map(tab => ({ ...tab, isUpdate: false })))
    dispatch(resetTableSchedulingGroup(checkTypes, firstLevelActiveTab));
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
