import React from 'react';
import Checkbox from '../Checkbox';
import Input from '../Input';
import { RecurringScheduleSpec } from '../../api';

interface IScheduleTabProps {
  schedule?: RecurringScheduleSpec;
  onChange: () => void;
}

const ScheduleTab = ({ schedule, onChange }: IScheduleTabProps) => {
  return (
    <div className="">
      <div className="mb-3">
        <Checkbox
          checked={schedule?.disabled}
          onChange={onChange}
          label="Disabled"
        />
      </div>
      <Input label="Cron Expression" value={schedule?.cron_expression} />
    </div>
  );
};

export default ScheduleTab;