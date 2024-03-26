import clsx from 'clsx';
import React, { ChangeEvent, useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { MonitoringScheduleSpec } from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setCronScheduler } from '../../redux/actions/job.actions';
import { IRootState } from '../../redux/reducers';
import { JobApiClient } from '../../services/apiClient';
import { useDecodedParams } from '../../utils';
import Checkbox from '../Checkbox';
import Input from '../Input';
import NumberInput from '../NumberInput';
import RadioButton from '../RadioButton';
import Switch from '../Switch';

interface IScheduleViewProps {
  schedule?: MonitoringScheduleSpec;
  handleChange: (obj: any) => void;
  isDefault?: boolean;
}
type TMinutes = { minutes: number; day: number; hour: number };

const defaultMinutes = { minutes: 15, day: 0, hour: 0 };
const defaultHourForDaily = 8;

const ScheduleView = ({
  schedule,
  handleChange,
  isDefault
}: IScheduleViewProps) => {
  const [mode, setMode] = useState('');
  const [minutes, setMinutes] = useState<TMinutes>(defaultMinutes);
  const [hour, setHour] = useState(defaultHourForDaily);
  const { table, column }: { table: string; column: string } = useDecodedParams();

  const { isCronScheduled, userProfile } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const dispatch = useActionDispatch();

  const scheduleCron = (bool: boolean) => {
    dispatch(setCronScheduler(bool));
  };

  const startCroner = async () => {
    await JobApiClient.startCronScheduler();
  };

  const changeStatus = () => {
    startCroner();
    scheduleCron(true);
  };

  const onChangeMode = (value: string) => {
    setMode(value);

    if (value === 'minutes') {
      handleChange({ cron_expression: `*/${minutes.minutes} * * * *` });
      return;
    }
    if (value === 'hour') {
      handleChange({ cron_expression: `${minutes.minutes} * * * *` });
      return;
    }
    if (value === 'day') {
      handleChange({ cron_expression: `${minutes.day} ${hour} * * *` });
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
    setMinutes((prevState) => ({
      ...prevState,
      [mode as keyof TMinutes]: val
    }));
  };

  const onChangeHour = (val: number) => {
    if (Number(val) < 0 || Number(val) > 23) return;
    if (mode === 'day') {
      handleChange({ cron_expression: `${minutes.day} ${val} * * *` });
    }
    setHour(val);
  };

  useEffect(() => {
    const checkCronExpresion = () => {
      const cron_expression = schedule?.cron_expression ?? '';
      if (cron_expression?.length === 0) {
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
          setMinutes((prevState) => ({
            ...prevState,
            minutes: Number(matches[1])
          }));
        }
        return;
      } else if (/^\d\d? \* \* \* \*$/.test(cron_expression)) {
        setMode('hour');
        const matches = cron_expression.match(/^(\d\d?) \* \* \* \*$/);

        if (!matches) return;
        if (Number(matches[1]) < 0) {
          onChangeMinutes(0);
        } else if (Number(matches[1]) > 59) {
          onChangeMinutes(59);
        } else {
          setMinutes((prevState) => ({
            ...prevState,
            hour: Number(matches[1])
          }));
        }
        return;
      } else if (/^\d\d? \d\d? \* \* \*$/.test(cron_expression)) {
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
          setMinutes((prevState) => ({
            ...prevState,
            day: Number(matches[1])
          }));
        }
        return;
      } else {
        setMode('custom');
        return;
      }
    };

    setMinutes(defaultMinutes);
    setHour(defaultHourForDaily);
    checkCronExpresion();
  }, [schedule]);

  useEffect(() => {
    const getIsSchedulerRunning = async () => {
      const res = await JobApiClient.isCronSchedulerRunning();
      scheduleCron(res.data);
    };

    getIsSchedulerRunning();
  }, []);

  const onChangeCronExpression = (e: ChangeEvent<HTMLInputElement>) => {
    if (mode === 'custom') {
      const cronExpression = e.target.value;
      handleChange({ cron_expression: cronExpression });
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
      {isCronScheduled === false ? (
        <div
          className={clsx(
            'w-full h-12 flex items-center gap-x-4 text-red-500 border-b border-gray-300 text-sm',
            userProfile.can_manage_scheduler !== true ||
              (isDefault === true &&
                userProfile.can_manage_data_sources !== true)
              ? 'pointer-events-none cursor-not-allowed'
              : ''
          )}
        >
          Warning: the job scheduler is disabled and no scheduled jobs will be
          executed, enable the job scheduler?{' '}
          <Switch
            checked={isCronScheduled ? isCronScheduled : false}
            onChange={() => changeStatus()}
          />
        </div>
      ) : (
        <></>
      )}
      <table className="mb-6 text-sm">
        <tbody>
          <tr>
            <td
              className={clsx(
                'pr-4 py-4 text-sm',
                mode !== 'custom' && 'opacity-60'
              )}
            >
              <div>Unix cron expression:</div>
            </td>
            <td className="px-4 py-4 text-sm">
              <Input
                className="!text-sm"
                value={schedule?.cron_expression}
                onChange={onChangeCronExpression}
                disabled={
                  userProfile.can_manage_data_sources !== true ||
                  mode !== 'custom'
                }
              />
            </td>
            <td className="text-xs underline text-teal-500">
              <a
                href="https://dqops.com/docs/working-with-dqo/configure-scheduling-of-data-quality-checks/cron-formatting/"
                target="_blank"
                rel="noreferrer"
              >
                Unix cron expression documentation
              </a>
            </td>
          </tr>
          <tr>
            <td className="pr-4 py-2 text-sm">
              <div>Disable schedule:</div>
            </td>
            <td
              className={clsx(
                'px-4 py-2 text-sm',
                userProfile.can_manage_scheduler ||
                  (isDefault === true &&
                    userProfile.can_manage_data_sources !== true)
                  ? ''
                  : 'cursor-not-allowed pointer-events-none'
              )}
            >
              <div className="flex">
                <Checkbox
                  checked={schedule?.disabled}
                  onChange={(value) => handleChange({ disabled: value })}
                  disabled={userProfile.can_manage_scheduler !== true}
                />
              </div>
            </td>
          </tr>
        </tbody>
      </table>

      <div
        className={clsx(
          'flex flex-col text-sm',
          userProfile.can_manage_data_sources === true
            ? ''
            : 'cursor-not-allowed pointer-events-none'
        )}
      >
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
              value={minutes.minutes}
              onChange={onChangeMinutes}
              disabled={
                userProfile.can_manage_scheduler !== true ||
                (isDefault === true &&
                  userProfile.can_manage_data_sources !== true)
              }
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
              value={minutes.hour}
              onChange={onChangeMinutes}
              disabled={
                userProfile.can_manage_scheduler !== true ||
                (isDefault === true &&
                  userProfile.can_manage_data_sources !== true)
              }
            />
            <div>minutes past every hour</div>
          </div>
        </div>
        <div
          className={clsx(
            'flex items-center text-sm mb-4',
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
              disabled={
                userProfile.can_manage_scheduler !== true ||
                (isDefault === true &&
                  userProfile.can_manage_data_sources !== true)
              }
            />
            <div>:</div>
            <NumberInput
              className="!text-sm"
              min={0}
              max={60}
              value={minutes.day}
              onChange={onChangeMinutes}
              disabled={
                userProfile.can_manage_scheduler !== true ||
                (isDefault === true &&
                  userProfile.can_manage_data_sources !== true)
              }
            />
          </div>
        </div>
        <div
          className={clsx(
            'flex items-center text-sm',
            mode !== 'custom' && 'opacity-60'
          )}
        >
          <RadioButton
            label="Use custom cron expression"
            checked={mode === 'custom'}
            onClick={() => onChangeMode('custom')}
            className="mb-4"
          />
        </div>
      </div>
    </div>
  );
};

export default ScheduleView;
