import React from 'react';
import Checkbox from '../Checkbox';
import Input from '../Input';
import { RecurringScheduleSpec } from '../../api';

interface IScheduleTabProps {
  schedule?: RecurringScheduleSpec;
  onChange: (item: RecurringScheduleSpec) => void;
}

const ScheduleTab = ({ schedule, onChange }: IScheduleTabProps) => {
  const handleChange = (obj: any) => {
    onChange({
      ...schedule,
      ...obj
    });
  };

  return (
    <div className="">
      <div className="mb-3">
        <Checkbox
          checked={schedule?.disabled}
          onChange={(value) => handleChange({ disabled: value })}
          label="Disabled"
        />
      </div>
      <Input
        label="Cron Expression"
        value={schedule?.cron_expression}
        onChange={(e) => handleChange({ cron_expression: e.target.value })}
      />
    </div>
  );
};

export default ScheduleTab;
