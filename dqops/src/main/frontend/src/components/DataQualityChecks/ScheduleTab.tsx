import React from 'react';
import { CronScheduleSpec } from '../../api';
import ScheduleView from "../ScheduleView";

interface IScheduleTabProps {
  schedule?: CronScheduleSpec;
  onChange: (item?: CronScheduleSpec) => void;
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
