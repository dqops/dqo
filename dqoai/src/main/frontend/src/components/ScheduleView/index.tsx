import React, { ChangeEvent, useEffect, useState } from "react";
import Input from "../Input";
import Checkbox from "../Checkbox";
import { Radio } from "@material-tailwind/react";
import NumberInput from "../NumberInput";
import { RecurringScheduleSpec } from "../../api";

interface IScheduleViewProps {
  schedule?: RecurringScheduleSpec;
  handleChange: (obj: any) => void;
}

const ScheduleView = ({ schedule, handleChange }: IScheduleViewProps) => {
  const [mode, setMode] = useState('');
  const [minutes, setMinutes] = useState(15);
  const [hour, setHour] = useState(15);
  const onChangeMode = (e: any) => {
    setMode(e.target.value);

    if (e.target.value === 'minutes') {
      handleChange({ cron_expression: `*/${minutes} * * * *` });
    }
    if (e.target.value === 'hour') {
      handleChange({ cron_expression: `${minutes} * * * *` });
    }
    if (e.target.value === 'day') {
      handleChange({ cron_expression: `${minutes} ${hour} * * *` });
    }
    if (!e.target.value) {
      handleChange({ cron_expression: '' });
    }
  };

  const onChangeMinutes = (val: number) => {
    if (Number(val) < 0 || Number(val) >  59) return;

    if (mode === 'minutes') {
      handleChange({ cron_expression: `*/${val} * * * *` });
    }
    if (mode === 'hour') {
      handleChange({ cron_expression: `${val} * * * *` });
    }
    if (mode === 'day') {
      handleChange({ cron_expression: `${val} ${hour} * * *` });
    }
    setMinutes(val);
  };

  const onChangeHour = (val: number) => {
    if (Number(val) < 0 || Number(val) >  23) return;
    if (mode === 'day') {
      handleChange({ cron_expression: `${minutes} ${val} * * *` });
    }
    setHour(val);
  };

  useEffect(() => {
    // if (!schedule?.cron_expression) return;
    const cron_expression = schedule?.cron_expression ?? "";
    if (!cron_expression) {
      setMode("");
    }
    if (/^\*\/\d\d? \* \* \* \*$/.test(cron_expression)) {
      setMode('minutes');
      const matches = cron_expression.match(/^\*\/(\d\d?) \* \* \* \*$/);
      if (!matches) return;
      if (Number(matches[1]) < 0) {
        onChangeMinutes(0);
      } else if (Number(matches[1]) > 59) {
        onChangeMinutes(59);
      } else {
        setMinutes(Number(matches[1]));
      }
    }
    if (/^\d\d? \* \* \* \*$/.test(cron_expression)) {
      setMode('hour');
      const matches = cron_expression.match(/^(\d\d?) \* \* \* \*$/);
      if (!matches) return;
      if (Number(matches[1]) < 0) {
        onChangeMinutes(0);
      } else if (Number(matches[1]) > 59) {
        onChangeMinutes(59);
      } else {
        setMinutes(Number(matches[1]));
      }
    }

    if (/^\d\d? \d\d? \* \* \*$/.test(cron_expression)) {
      setMode('day');
      const matches = cron_expression.match(/^(\d\d?) (\d\d?) \* \* \*$/);
      if (!matches) return;

      if (Number(matches[2]) < 0) {
        onChangeHour(0);
      } else if (Number(matches[2]) > 23) {
        onChangeHour(23);
      } else {
        setHour(Number(matches[2]));
      }
      if (Number(matches[1]) < 0) {
        onChangeMinutes(0);
      } else if (Number(matches[1]) > 59) {
        onChangeMinutes(59);
      } else {
        setMinutes(Number(matches[1]));
      }
    }
  }, [schedule?.cron_expression]);

  const onChangeCronExpression = (e: ChangeEvent<HTMLInputElement>) => {
    if (/^\*\/\d\d? \* \* \* \*$/.test(e.target.value) ||
      /^\d\d? \* \* \* \*$/.test(e.target.value) ||
      /^\d\d? \d\d? \* \* \*$/.test(e.target.value)
    ) {
      handleChange({ cron_expression: e.target.value });
    }
  };

  return (
    <div>
      <table className="mb-6">
        <tbody>
          <tr>
            <td className="px-4 py-2">
              <div>Unix cron expression:</div>
            </td>
            <td className="px-4 py-2">
              <Input
                value={schedule?.cron_expression}
                onChange={onChangeCronExpression}
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
        </tbody>
      </table>
      <div className="flex flex-col">
        <Radio
          id="unconfigured"
          name="mode"
          value=""
          label="Scheduled check execution not configured for all tables from this connection"
          checked={mode === ''}
          onChange={onChangeMode}
          color="teal"
        />
        <Radio
          id="minutes"
          name="mode"
          value="minutes"
          label="Run every X minutes"
          checked={mode === 'minutes'}
          onChange={onChangeMode}
          color="teal"
        />
        {mode === 'minutes' && (
          <div className="flex px-4 my-4 items-center space-x-3 text-gray-700">
            <div>Run every</div>
            <NumberInput
              min={0}
              max={60}
              value={minutes}
              onChange={onChangeMinutes}
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
          color="teal"
        />
        {mode === 'hour' && (
          <div className="flex px-4 my-4 items-center space-x-3 text-gray-700">
            <div>At</div>
            <NumberInput
              min={0}
              max={60}
              value={minutes}
              onChange={onChangeMinutes}
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
          color="teal"
        />
        {mode === 'day' && (
          <div className="flex px-4 my-4 items-center space-x-3 text-gray-700">
            <div>At</div>
            <NumberInput
              min={0}
              max={60}
              value={hour}
              onChange={onChangeHour}
            />
            <div>:</div>
            <NumberInput
              min={0}
              max={60}
              value={minutes}
              onChange={onChangeMinutes}
            />
          </div>
        )}
      </div>
    </div>
  );
};

export default ScheduleView;
