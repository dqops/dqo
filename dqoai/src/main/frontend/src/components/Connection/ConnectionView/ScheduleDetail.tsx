import React from 'react';
import { ConnectionModel, RecurringScheduleSpec } from '../../../api';
import Input from '../../Input';
import Checkbox from '../../Checkbox';

interface IScheduleDetailProps {
  schedule?: RecurringScheduleSpec;
  onChange: any;
}

const ScheduleDetail: React.FC<IScheduleDetailProps> = ({
  schedule,
  onChange
}) => {
  const handleChange = (obj: any) => {
    onChange({
      spec: {
        schedule: {
          ...obj
        }
      }
    });
  };

  return (
    <div className="p-4">
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
              checked={schedule?.disable}
              onChange={(value) => handleChange({ disable: value })}
            />
          </td>
        </tr>
      </table>
    </div>
  );
};

export default ScheduleDetail;
