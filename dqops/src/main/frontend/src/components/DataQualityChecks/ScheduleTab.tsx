import React from 'react';
import { MonitoringScheduleSpec } from '../../api';
import ScheduleView from "../ScheduleView";

interface IScheduleTabProps {
  schedule?: MonitoringScheduleSpec;
  onChange: (item?: MonitoringScheduleSpec) => void;
}

const ScheduleTab = ({ schedule, onChange }: IScheduleTabProps) => {
  const handleChange = (obj: any) => {
    onChange({
      ...schedule,
      ...obj
    });
  };
  const isDisabled = schedule?.disabled;

  return (
    <div className={isDisabled ? 'text-gray-700' : ''}>
      <ScheduleView handleChange={handleChange} schedule={schedule}/>
    </div>
  );
};

export default ScheduleTab;
