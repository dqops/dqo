import React, { useEffect, useState } from 'react';
import { RecurringScheduleSpec } from '../../../api';
import Input from '../../Input';
import Checkbox from '../../Checkbox';
import { Radio } from '@material-tailwind/react';
import NumberInput from '../../NumberInput';
import {
  getConnectionSchedule,
  updateConnectionSchedule
} from '../../../redux/actions/connection.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import ConnectionActionGroup from './ConnectionActionGroup';

interface IScheduleDetailProps {
  connectionName: string;
}

const ScheduleDetail: React.FC<IScheduleDetailProps> = ({ connectionName }) => {
  const [mode, setMode] = useState('minutes');
  const [minutes, setMinutes] = useState(15);
  const [hour, setHour] = useState(15);
  const [isUpdated, setIsUpdated] = useState(false);
  const dispatch = useActionDispatch();
  const [updatedSchedule, setUpdatedSchedule] =
    useState<RecurringScheduleSpec>();

  const { schedule, isUpdating } = useSelector(
    (state: IRootState) => state.connection
  );

  const handleChange = (obj: any) => {
    setIsUpdated(true);
    setUpdatedSchedule({
      ...updatedSchedule,
      ...obj
    });
  };

  useEffect(() => {
    setUpdatedSchedule(schedule);
  }, [schedule]);

  useEffect(() => {
    dispatch(getConnectionSchedule(connectionName));
  }, [connectionName]);

  const onUpdate = async () => {
    await dispatch(updateConnectionSchedule(connectionName, updatedSchedule));
    await dispatch(getConnectionSchedule(connectionName));
    setIsUpdated(false);
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

  return (
    <div className="p-4">
      <ConnectionActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdated}
        isUpdating={isUpdating}
      />
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
          id="minutes"
          name="mode"
          value="minutes"
          label="Run every X minutes"
          checked={mode === 'minutes'}
          onChange={onChangeMode}
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

export default ScheduleDetail;
