import React, { ChangeEvent, useEffect, useState } from "react";
import Input from "../Input";
import Checkbox from "../Checkbox";
import NumberInput from "../NumberInput";
import { RecurringScheduleSpec } from "../../api";
import clsx from "clsx";
import RadioButton from "../RadioButton";

interface IScheduleViewProps {
  schedule?: RecurringScheduleSpec;
  handleChange: (obj: any) => void;
}

const ScheduleView = ({ schedule, handleChange }: IScheduleViewProps) => {
  const [mode, setMode] = useState('');
  const [minutes, setMinutes] = useState(15);
  const [hour, setHour] = useState(15);
  const onChangeMode = (value: string) => {
    setMode(value);

    if (value === 'minutes') {
      handleChange({ cron_expression: `*/${minutes} * * * *` });
    }
    if (value === 'hour') {
      handleChange({ cron_expression: `${minutes} * * * *` });
    }
    if (value === 'day') {
      handleChange({ cron_expression: `${minutes} ${hour} * * *` });
    }
    if (value) {
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
    if (!schedule?.cron_expression) return;
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
        <RadioButton
          label="Scheduled check execution not configured for all tables from this connection"
          checked={mode === ''}
          onClick={() => onChangeMode('')}
          className="mb-4"
        />
        <RadioButton
          label="Run every X minutes"
          checked={mode === 'minutes'}
          onClick={() => onChangeMode('minutes')}
        />
        <div className={clsx("flex px-4 my-4 items-center space-x-3 text-gray-700", mode !== "minutes" && "opacity-60")}>
          <div>Run every</div>
          <NumberInput
            min={0}
            max={60}
            value={minutes}
            onChange={onChangeMinutes}
          />
          <div>minutes</div>
        </div>
        <RadioButton
          label="Run every hour"
          checked={mode === 'hour'}
          onClick={() => onChangeMode('hour')}
        />
        <div className={clsx("flex px-4 my-4 items-center space-x-3 text-gray-700", mode !== "hour" && "opacity-60")}>
          <div>At</div>
          <NumberInput
            min={0}
            max={60}
            value={minutes}
            onChange={onChangeMinutes}
          />
          <div>minutes past hour</div>
        </div>
        <RadioButton
          label="Run every day"
          checked={mode === 'day'}
          onClick={() => onChangeMode('day')}
        />
        <div className={clsx("flex px-4 my-4 items-center space-x-3 text-gray-700", mode !== "day" && "opacity-60")}>
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
      </div>
    </div>
  );
};

export default ScheduleView;
