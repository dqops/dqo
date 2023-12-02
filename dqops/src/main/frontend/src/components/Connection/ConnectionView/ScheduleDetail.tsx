import React, { useEffect, useState } from 'react';
import {
  getConnectionSchedulingGroup, resetConnectionSchedulingGroup, setIsUpdatedSchedulingGroup, setUpdatedSchedulingGroup,
  updateConnectionSchedulingGroup

} from '../../../redux/actions/connection.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { useSelector } from 'react-redux';
import ConnectionActionGroup from './ConnectionActionGroup';
import { useHistory, useLocation, useParams } from "react-router-dom";
import ScheduleView from "../../ScheduleView";
import Tabs from "../../Tabs";
import { CheckRunMonitoringScheduleGroup } from "../../../shared/enums/scheduling.enum";
import { getFirstLevelActiveTab, getFirstLevelState } from "../../../redux/selectors";
import { CheckTypes } from "../../../shared/routes";
import qs from "query-string";
import { SettingsApi } from '../../../services/apiClient';
import { MonitoringScheduleSpec } from '../../../api';

const ScheduleDetail = ({ isDefault } : { isDefault ?: boolean }) => {
  const { connection, checkTypes }: { checkTypes: CheckTypes, connection: string } = useParams();

  const getPageTabs = () => {
    switch(checkTypes) {
      case CheckTypes.PROFILING : {
        return [{
          label: 'Profiling',
          value: CheckRunMonitoringScheduleGroup.profiling
        }]
      }
      case CheckTypes.SOURCES : {
        return [{
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
        }]
      }
      case CheckTypes.PARTITIONED : {
        return [{
          label: 'Partition Daily',
          value: CheckRunMonitoringScheduleGroup.partitioned_daily
        },
        {
          label: 'Partition Monthly',
          value: CheckRunMonitoringScheduleGroup.partitioned_monthly
        }]
      }
      case CheckTypes.MONITORING : {
        return [{
          label: 'Monitoring Daily',
          value: CheckRunMonitoringScheduleGroup.monitoring_daily
        },
        {
          label: 'Monitoring Monthly',
          value: CheckRunMonitoringScheduleGroup.monitoring_monthly
        }]
      }
    }    
  }
  const [tabs, setTabs] = useState(getPageTabs());
  const [updatedSchedule, setUpdatedSchedule] = useState<MonitoringScheduleSpec | undefined>()
  const [isDefaultUpdated, setIsDefaultUpdated] = useState(false)
  const dispatch = useActionDispatch();
  const location = useLocation() as any;
  const { activeTab } = qs.parse(location.search) as any;
  const history = useHistory();

  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const { scheduleGroups, isUpdating }: {
    scheduleGroups?: any;
    isUpdating?: boolean;
  } = useSelector(getFirstLevelState(checkTypes));




  const isUpdatedSchedule = scheduleGroups?.[activeTab]?.isUpdatedSchedule;
  const fetchDefaultSchedule = async () => {
    try {
        await SettingsApi.getDefaultSchedule(String(activeTab).replace(/\s/g, "_").toLowerCase() as 'profiling' | 'monitoring_daily' | 'monitoring_monthly' | 'partitioned_daily' | 'partitioned_monthly' ?? 'profiling') 
          .then((res) => setUpdatedSchedule(res.data));

    } catch (error) {
      console.error(error)

    }
  }
  useEffect(() => {
      fetchDefaultSchedule()
  } , [activeTab])

  const onChangeTab = (tab: CheckRunMonitoringScheduleGroup) => {
    history.push(`${location.pathname}?activeTab=${tab}`)
  }
  const updateDefaultSchedules = async (obj: MonitoringScheduleSpec | undefined) =>{
    await SettingsApi.updateDefaultSchedules(String(activeTab).replace(/\s/g, "_").toLowerCase() as 'profiling' | 'monitoring_daily' | 'monitoring_monthly' | 'partitioned_daily' | 'partitioned_monthly' ?? 'profiling', obj)
      .then(() => setIsDefaultUpdated(false))
      .catch((err) => console.error(err))

  }

  const handleChange = (obj: MonitoringScheduleSpec) => {

      setUpdatedSchedule((prevState) => ({
        ...prevState,
        cron_expression: obj.cron_expression,
        disabled: obj.disabled
      }));
      setIsDefaultUpdated(true)
    
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

      updateDefaultSchedules(updatedSchedule)

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
    setTabs(getPageTabs())
  }, [checkTypes]);

  return (
    <div className="py-4 px-8">
      <ConnectionActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedSchedule || isDefaultUpdated}
        isUpdating={isUpdating}
      />
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab && activeTab?.length > 0 ? activeTab : getPageTabs()[0].value} onChange={onChangeTab} />
      </div>
      <ScheduleView handleChange={handleChange} schedule={updatedSchedule} isDefault={isDefault}/>
    </div>
  );
};

export default ScheduleDetail;
