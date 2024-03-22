import React, { useEffect, useState } from "react";
import { useHistory, useLocation } from "react-router-dom";
import { MonitoringScheduleSpec } from "../../api";
import Button from "../../components/Button";
import ScheduleView from "../../components/ScheduleView";
import SvgIcon from "../../components/SvgIcon";
import Tabs from "../../components/Tabs";
import { SettingsApi } from "../../services/apiClient";
import { CheckRunMonitoringScheduleGroup } from "../../shared/enums/scheduling.enum";
import { useDecodedParams } from "../../utils";

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

const DefaultSchedulesDetail = () => {
  const {tab} : {tab : CheckRunMonitoringScheduleGroup} = useDecodedParams()
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
      <div className="px-4">
        <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14">
          <div className="flex items-center space-x-2 max-w-full">
            <SvgIcon name="grid" className="w-5 h-5 shrink-0" />
            <div className="text-lg font-semibold truncate">
              Default schedule editor
            </div>
          </div>
          <Button
            label="Save"
            color="primary"
            className="pl-14 pr-14"
            onClick={updateDefaultSchedules}
            disabled={!isDefaultUpdated}
          />
        </div>
        <div className="border-b border-gray-300">
          <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
        </div>
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
