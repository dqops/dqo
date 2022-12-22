import React, { useEffect, useState } from 'react';
import Checkbox from '../Checkbox';
import Input from '../Input';
import { RecurringScheduleSpec } from '../../api';
import { Radio } from '@material-tailwind/react';
import NumberInput from '../NumberInput';

interface IScheduleTabProps {
  schedule?: RecurringScheduleSpec;
  onChange: (item?: RecurringScheduleSpec) => void;
}

const ScheduleTab = ({ schedule, onChange }: IScheduleTabProps) => {
  const [mode, setMode] = useState('');
  const [minutes, setMinutes] = useState(15);
  const [hour, setHour] = useState(15);

  const handleChange = (obj: any) => {
    onChange({
      ...schedule,
      ...obj
    });
  };

  const onChangeMode = (e: any) => {
    setMode(e.target.value);

    if (e.target.value === 'minutes') {
      handleChange({ cron_expression: `*/${minutes} * * * *` });
    }
    if (e.target.value === 'hour') {
      handleChange({ cron_expression: `${minutes} * * * *` });
    }
    if (e.target.value === 'day') {
      handleChange({ cron_expression: `${hour} ${minutes} * * *` });
    }
    if (!e.target.value) {
      handleChange({ cron_expression: '' });
    }
  };

  const onChangeMinutes = (val: number) => {
    if (mode === 'minutes') {
      handleChange({ cron_expression: `*/${val} * * * *` });
    }
    if (mode === 'hour') {
      handleChange({ cron_expression: `${val} * * * *` });
    }
    if (mode === 'day') {
      handleChange({ cron_expression: `${hour} ${val} * * *` });
    }
    setMinutes(val);
  };

  const onChangeHour = (val: number) => {
    if (mode === 'day') {
      handleChange({ cron_expression: `${val} ${minutes} * * *` });
    }
    setHour(val);
  };
  
  
  useEffect(() => {
    if (!schedule?.cron_expression) return;
    
    if (/^\*\/\d\d? \* \* \* \*$/.test(schedule?.cron_expression)) {
      setMode('minutes');
      const matches = schedule?.cron_expression.match(/^\*\/(\d\d?) \* \* \* \*$/);
      if (!matches) return;
      setMinutes(Number(matches[1]));
    }
    if (/^\d\d? \* \* \* \*$/.test(schedule?.cron_expression)) {
      setMode('hour');
      const matches = schedule?.cron_expression.match(/^(\d\d?) \* \* \* \*$/);
      if (!matches) return;
      setMinutes(Number(matches[1]));
    }
    
    if (/^\d\d? \d\d? \* \* \*$/.test(schedule?.cron_expression)) {
      setMode('day');
      const matches = schedule?.cron_expression.match(/^(\d\d?) (\d\d?) \* \* \*$/);
      if (!matches) return;
      setHour(Number(matches[1]));
      setMinutes(Number(matches[2]));
    }
  }, []);
  
  const isDisabled = schedule?.disabled;

  return (
    <div className={isDisabled ? 'text-gray-700' : ''}>
      <table className="mb-6">
        <tr>
          <td className="px-4 py-2">
            <div>Unix cron expression:</div>
          </td>
          <td className="px-4 py-2">
            <Input
              value={schedule?.cron_expression}
              onChange={(e) =>
                handleChange({ cron_expression: e.target.value })
              }
              disabled={isDisabled}
            />
          </td>
        </tr>
        <tr>
          <td className="px-4 py-2">
            <div>Disable schedule:</div>
          </td>
          <td className="px-4 py-2">
            <Checkbox
              checked={schedule?.disabled}
              onChange={(value) => handleChange({ disabled: value })}
            />
          </td>
        </tr>
      </table>
      <div className="flex flex-col">
        <Radio
          id="unconfigured"
          name="mode"
          value=""
          label="Custom check execution schedule not configured for this check"
          checked={mode === ''}
          onChange={onChangeMode}
        />
        <Radio
          id="minutes"
          name="mode"
          value="minutes"
          label="Run every X minutes"
          checked={mode === 'minutes'}
          onChange={onChangeMode}
          disabled={isDisabled}
        />
        {mode === 'minutes' && (
          <div className="flex px-4 my-4 items-center space-x-3 text-gray-700">
            <div>Run every</div>
            <NumberInput
              min={0}
              max={60}
              value={minutes}
              onChange={onChangeMinutes}
              disabled={isDisabled}
            />
            <div>minutes</div>
          </div>
        )}
        <Radio
          id="hour"
          name="mode"
          label="Run every hour"
          value="hour"
          checked={mode === 'hour'}
          onChange={onChangeMode}
          disabled={isDisabled}
        />
        {mode === 'hour' && (
          <div className="flex px-4 my-4 items-center space-x-3 text-gray-700">
            <div>At</div>
            <NumberInput
              min={0}
              max={60}
              value={minutes}
              onChange={onChangeMinutes}
              disabled={isDisabled}
            />
            <div>minutes past hour</div>
          </div>
        )}
        <Radio
          id="day"
          name="mode"
          label="Run every day"
          value="day"
          checked={mode === 'day'}
          onChange={onChangeMode}
          disabled={isDisabled}
        />
        {mode === 'day' && (
          <div className="flex px-4 my-4 items-center space-x-3 text-gray-700">
            <div>At</div>
            <NumberInput
              min={0}
              max={60}
              value={hour}
              onChange={onChangeHour}
              disabled={isDisabled}
            />
            <div>:</div>
            <NumberInput
              min={0}
              max={60}
              value={minutes}
              onChange={onChangeMinutes}
              disabled={isDisabled}
            />
          </div>
        )}
      </div>
    </div>
  );
};

export default ScheduleTab;
