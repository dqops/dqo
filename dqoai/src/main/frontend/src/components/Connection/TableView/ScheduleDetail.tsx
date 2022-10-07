import React, { useEffect, useState } from 'react';
import { RecurringScheduleSpec } from '../../../api';
import Input from '../../Input';
import Checkbox from '../../Checkbox';
import { Radio } from '@material-tailwind/react';
import NumberInput from '../../NumberInput';
import { TableApiClient } from '../../../services/apiClient';
import { AxiosResponse } from 'axios';
import Tabs from '../../Tabs';

interface IScheduleDetailProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
}

const tabs = [
  {
    label: 'Schedule override',
    value: 'schedule'
  },
  {
    label: 'Effective schedule',
    value: 'effective'
  }
];

const ScheduleDetail = ({
  connectionName,
  schemaName,
  tableName
}: IScheduleDetailProps) => {
  const [mode, setMode] = useState('minutes');
  const [minutes, setMinutes] = useState(15);
  const [hour, setHour] = useState(15);
  const [schedule, setSchedule] = useState<RecurringScheduleSpec>();
  const [activeTab, setActiveTab] = useState('schedule');

  const fetchSchedule = async () => {
    try {
      const res: AxiosResponse<RecurringScheduleSpec> =
        await TableApiClient.getTableScheduleOverride(
          connectionName,
          schemaName,
          tableName
        );
      setSchedule(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchSchedule().then();
  }, []);

  const handleChange = (obj: any) => {
    setSchedule({
      ...schedule,
      ...obj
    });
  };

  useEffect(() => {
    if (mode === 'minutes') {
      handleChange({ cron_expression: `*/${minutes} * * * *` });
    }
    if (mode === 'hour') {
      handleChange({ cron_expression: `${minutes} * * * *` });
    }
    if (mode === 'day') {
      handleChange({ cron_expression: `${hour} ${minutes} * * *` });
    }
  }, [minutes, hour, mode]);
  return (
    <div className="p-4">
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
      </div>

      <table className="mb-6 mt-8">
        {activeTab === 'effective' && (
          <tr>
            <td className="px-4 py-2">Time zone</td>
            <td className="px-4 py-2">
              <Input />
            </td>
          </tr>
        )}
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
          onChange={(e) => setMode(e.target.value)}
        />
        {mode === 'minutes' && (
          <div className="flex px-4 my-4 items-center space-x-3 text-gray-700">
            <div>Run every</div>
            <NumberInput
              min={0}
              max={60}
              value={minutes}
              onChange={setMinutes}
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
          onChange={(e) => setMode(e.target.value)}
        />
        {mode === 'hour' && (
          <div className="flex px-4 my-4 items-center space-x-3 text-gray-700">
            <div>At</div>
            <NumberInput
              min={0}
              max={60}
              value={minutes}
              onChange={setMinutes}
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
          onChange={(e) => setMode(e.target.value)}
        />
        {mode === 'day' && (
          <div className="flex px-4 my-4 items-center space-x-3 text-gray-700">
            <div>At</div>
            <NumberInput min={0} max={60} value={hour} onChange={setHour} />
            <div>:</div>
            <NumberInput
              min={0}
              max={60}
              value={minutes}
              onChange={setMinutes}
            />
          </div>
        )}
      </div>
    </div>
  );
};

export default ScheduleDetail;
