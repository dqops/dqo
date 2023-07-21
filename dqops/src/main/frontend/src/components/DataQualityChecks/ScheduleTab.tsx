import React from 'react';
import { RecurringScheduleSpec } from '../../api';
import ScheduleView from "../ScheduleView";

interface IScheduleTabProps {
  schedule?: RecurringScheduleSpec;
  onChange: (item?: RecurringScheduleSpec) => void;
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
      <ScheduleView handleChange={handleChange} schedule={schedule} />
    </div>
  );
};

export default ScheduleTab;
