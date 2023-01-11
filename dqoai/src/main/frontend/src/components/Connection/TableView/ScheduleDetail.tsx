import React, { useEffect, useState } from 'react';
import Input from '../../Input';
import Checkbox from '../../Checkbox';
import { Radio } from '@material-tailwind/react';
import NumberInput from '../../NumberInput';
import ActionGroup from './TableActionGroup';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableSchedule,
  setUpdatedSchedule,
  updateTableSchedule
} from '../../../redux/actions/table.actions';
import { useParams } from "react-router-dom";

const ScheduleDetail = () => {
  const { connection: connectionName, schema: schemaName, table: tableName }: { connection: string, schema: string, table: string } = useParams();
  const [mode, setMode] = useState('');
  const [minutes, setMinutes] = useState(15);
  const [hour, setHour] = useState(15);

  const { schedule, isUpdating, isUpdatedSchedule, tableBasic } = useSelector(
    (state: IRootState) => state.table
  );
  const dispatch = useActionDispatch();

  useEffect(() => {
    if (
      !schedule ||
      tableBasic?.connection_name !== connectionName ||
      tableBasic?.target?.schema_name !== schemaName ||
      tableBasic?.target?.table_name !== tableName
    ) {
      dispatch(getTableSchedule(connectionName, schemaName, tableName));
    }
  }, [connectionName, schemaName, tableName, tableBasic]);

  const handleChange = (obj: any) => {
    dispatch(
      setUpdatedSchedule({
        ...schedule,
        ...obj
      })
    );
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

  const onUpdate = async () => {
    if (!schedule) {
      return;
    }
    await dispatch(
      updateTableSchedule(connectionName, schemaName, tableName, schedule)
    );
    await dispatch(getTableSchedule(connectionName, schemaName, tableName));
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
  
  return (
    <div className="p-4">
      <ActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedSchedule}
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
          id="unconfigured"
          name="mode"
          value=""
          label="Scheduled check execution not configured on a table level"
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
