import React, { ChangeEvent, useEffect, useRef, useState } from 'react';
import Input from '../Input';
import Checkbox from '../Checkbox';
import NumberInput from '../NumberInput';
import { RecurringScheduleSpec } from '../../api';
import clsx from 'clsx';
import RadioButton from '../RadioButton';
import { useParams } from 'react-router-dom';
import { JobApiClient } from '../../services/apiClient';
import Switch from '../Switch';

interface IScheduleViewProps {
  schedule?: RecurringScheduleSpec;
  handleChange: (obj: any) => void;
}

const ScheduleView = ({ schedule, handleChange }: IScheduleViewProps) => {
  const [mode, setMode] = useState('');
  const [minutes, setMinutes] = useState(15);
  const [hour, setHour] = useState(15);
  const { table, column }: { table: string; column: string } = useParams();
  const [cronBoolean, setCronBoolean] = useState<boolean>();
  const getData = async () => {
    const res = await JobApiClient.isCronSchedulerRunning();
    setCronBoolean(res.data);
  };

  const startCroner = async () => {
    await JobApiClient.startCronScheduler();
    setCronBoolean(true);
  };

  const changeStatus = () => {
    if (cronBoolean === false) {
      startCroner();
    }
  };

  const onChangeMode = (value: string) => {
    setMode(value);

    if (value === 'minutes') {
      handleChange({ cron_expression: `*/${minutes} * * * *` });
      return;
    }
    if (value === 'hour') {
      handleChange({ cron_expression: `${minutes} * * * *` });
      return;
    }
    if (value === 'day') {
      handleChange({ cron_expression: `${minutes} ${hour} * * *` });
      return;
    }
    if (!value) {
      handleChange({ cron_expression: '' });
    }
  };

  const onChangeMinutes = (val: number) => {
    if (Number(val) < 0 || Number(val) > 59) return;

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
    if (Number(val) < 0 || Number(val) > 23) return;
    if (mode === 'day') {
      handleChange({ cron_expression: `${minutes} ${val} * * *` });
    }
    setHour(val);
  };

  useEffect(() => {
    getData();
  }, []);

  // useEffect(() => {
  //   let isMounted = true;

  //   const fetchDataRecursive = async () => {
  //     await getData();

  //     if (isMounted) {
  //       fetchDataRecursive();
  //     }
  //   };

  //   fetchDataRecursive();

  //   return () => {
  //     isMounted = false;
  //   };
  // }, []);

  useEffect(() => {
    const interval = setInterval(() => {
      getData();
    }, 1000);
    return () => {
      clearInterval(interval);
    };
  }, []);

  console.log(cronBoolean);

  useEffect(() => {
    const cron_expression = schedule?.cron_expression ?? '';
    if (!cron_expression) {
      setMode('');
      return;
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
    if (
      /^\*\/\d\d? \* \* \* \*$/.test(e.target.value) ||
      /^\d\d? \* \* \* \*$/.test(e.target.value) ||
      /^\d\d? \d\d? \* \* \*$/.test(e.target.value)
    ) {
      handleChange({ cron_expression: e.target.value });
    }
  };

  const getLabel = () => {
    if (table && !column) {
      return 'Use scheduling configuration from the connection levels';
    }
    if (table && column) {
      return 'Use scheduling configuration from the connection or table levels';
    }
    return 'Scheduled check execution not configured for all tables from this connection';
  };

  return (
    <div>
      {cronBoolean === false ? (
        <div className="w-full h-12 flex items-center gap-x-4 text-red-500 border-b border-gray-300">
          Warning: the job scheduler is disabled and no scheduled jobs will be
          executed, enable the job scheduler?{' '}
          <Switch
            checked={cronBoolean ? cronBoolean : false}
            onChange={() => changeStatus()}
          />
        </div>
      ) : (
        <></>
      )}
      <table className="mb-6">
        <tbody>
          <tr>
            <td className="pr-4 py-4 text-sm">
              <div>Unix cron expression:</div>
            </td>
            <td className="px-4 py-4 text-sm">
              <Input
                className="!text-sm"
                value={schedule?.cron_expression}
                onChange={onChangeCronExpression}
              />
            </td>
          </tr>
          <tr>
            <td className="pr-4 py-2 text-sm">
              <div>Disable schedule:</div>
            </td>
            <td className="px-4 py-2 text-sm">
              <div className="flex">
                <Checkbox
                  checked={schedule?.disabled}
                  onChange={(value) => handleChange({ disabled: value })}
                />
              </div>
            </td>
          </tr>
        </tbody>
      </table>

      <div className="flex flex-col text-sm">
        <div
          className={clsx(
            'flex items-center text-sm',
            mode !== '' && 'opacity-60'
          )}
        >
          <RadioButton
            label={getLabel()}
            checked={mode === ''}
            onClick={() => onChangeMode('')}
            className="mb-4"
          />
        </div>
        <div
          className={clsx(
            'flex items-center text-sm',
            mode !== 'minutes' && 'opacity-60'
          )}
        >
          <RadioButton
            label="Run every"
            checked={mode === 'minutes'}
            onClick={() => onChangeMode('minutes')}
          />
          <div className="flex px-4 my-4 items-center space-x-3 text-gray-700">
            <NumberInput
              className="!text-sm"
              min={0}
              max={60}
              value={minutes}
              onChange={onChangeMinutes}
            />
            <div>minutes</div>
          </div>
        </div>
        <div
          className={clsx(
            'flex items-center text-sm',
            mode !== 'hour' && 'opacity-60'
          )}
        >
          <RadioButton
            label="Run"
            checked={mode === 'hour'}
            onClick={() => onChangeMode('hour')}
          />
          <div className="flex px-4 my-4 items-center space-x-3 text-gray-700">
            <NumberInput
              className="!text-sm"
              min={0}
              max={60}
              value={minutes}
              onChange={onChangeMinutes}
            />
            <div>minutes past every hour</div>
          </div>
        </div>
        <div
          className={clsx(
            'flex items-center text-sm',
            mode !== 'day' && 'opacity-60'
          )}
        >
          <RadioButton
            label="Run every day at"
            checked={mode === 'day'}
            onClick={() => onChangeMode('day')}
          />
          <div className="flex px-4 my-4 items-center space-x-3 text-gray-700">
            <NumberInput
              className="!text-sm"
              min={0}
              max={60}
              value={hour}
              onChange={onChangeHour}
            />
            <div>:</div>
            <NumberInput
              className="!text-sm"
              min={0}
              max={60}
              value={minutes}
              onChange={onChangeMinutes}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default ScheduleView;
