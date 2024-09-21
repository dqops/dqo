import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory, useLocation } from 'react-router-dom';
import { MonitoringScheduleSpec } from '../../api';
import Button from '../../components/Button';
import ScheduleView from '../../components/ScheduleView';
import Tabs from '../../components/Tabs';
import { IRootState } from '../../redux/reducers';
import { SettingsApi } from '../../services/apiClient';
import { CheckRunMonitoringScheduleGroup } from '../../shared/enums/scheduling.enum';
import { useDecodedParams } from '../../utils';

const tabs = [
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

const freeTrialTabs = [
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

const DefaultSchedulesDetail = () => {
  const { userProfile } = useSelector((state: IRootState) => state.job);

  const { tab }: { tab: CheckRunMonitoringScheduleGroup } = useDecodedParams();
  const [updatedSchedule, setUpdatedSchedule] = useState<
    MonitoringScheduleSpec | undefined
  >();
  const [isDefaultUpdated, setIsDefaultUpdated] = useState(false);
  const location = useLocation() as any;
  const [activeTab, setActiveTab] = useState(tab ?? tabs[0].value);
  const history = useHistory();

  const fetchDefaultSchedule = async () => {
    try {
      await SettingsApi.getDefaultSchedule(activeTab).then((res) =>
        setUpdatedSchedule(res.data)
      );
    } catch (error) {
      console.error(error);
    }
  };
  useEffect(() => {
    fetchDefaultSchedule();
  }, [activeTab]);

  const onChangeTab = (tab: CheckRunMonitoringScheduleGroup) => {
    history.push(`${location.pathname}?activeTab=${tab}`);
    setActiveTab(tab);
  };

  const updateDefaultSchedules = async () => {
    await SettingsApi.updateDefaultSchedules(activeTab, updatedSchedule)
      .then(() => setIsDefaultUpdated(false))
      .catch((err) => console.error(err));
  };

  const handleChange = (obj: MonitoringScheduleSpec) => {
    setUpdatedSchedule((prevState) => ({
      ...prevState,
      cron_expression: obj.cron_expression,
      disabled: obj.disabled
    }));
    setIsDefaultUpdated(true);
  };

  return (
    <>
      <div className="flex justify-between px-4 py-3 border-b border-gray-300 mb-2 h-14">
        <div className="flex items-center space-x-2 max-w-full">
          <div className="text-lg font-semibold truncate">
            Default schedule editor
          </div>
        </div>
        <Button
          label="Save"
          color={isDefaultUpdated ? 'primary' : 'secondary'}
          className="w-45 !h-[37px]"
          onClick={updateDefaultSchedules}
        />
      </div>
      <div className="border-b border-gray-300">
        <Tabs
          tabs={
            userProfile &&
            userProfile.license_type &&
            userProfile.license_type?.toLowerCase() !== 'free' &&
            !userProfile.trial_period_expires_at
              ? tabs
              : freeTrialTabs
          }
          activeTab={activeTab}
          onChange={onChangeTab}
        />
      </div>
      <div className="px-4">
        <ScheduleView
          handleChange={handleChange}
          schedule={updatedSchedule}
          isDefault={true}
        />
      </div>
    </>
  );
};

export default DefaultSchedulesDetail;
