import React from 'react';
import {
  TimeSeriesConfigurationSpec,
  TimeSeriesConfigurationSpecModeEnum,
  TimeSeriesConfigurationSpecTimeGradientEnum
} from '../../../api';
import Select from '../../Select';
import Input from '../../Input';

interface IConnectionDetailProps {
  timeSeries?: TimeSeriesConfigurationSpec;
  setTimeSeries: (val: TimeSeriesConfigurationSpec) => void;
}

const TimeSeriesView: React.FC<IConnectionDetailProps> = ({
  timeSeries,
  setTimeSeries
}) => {
  const onChange = (obj: any) => {
    setTimeSeries({
      ...timeSeries,
      ...obj
    });
  };

  const modeOptions = Object.values(TimeSeriesConfigurationSpecModeEnum).map(
    (item) => ({ label: item.toUpperCase(), value: item })
  );
  const gradientOptions = Object.values(
    TimeSeriesConfigurationSpecTimeGradientEnum
  ).map((item) => ({ label: item.toUpperCase(), value: item }));

  return (
    <table className="w-160">
      <tr>
        <td className="px-4 py-2">
          <div>Mode:</div>
        </td>
        <td className="px-4 py-2">
          <Select
            options={modeOptions}
            value={timeSeries?.mode}
            onChange={(mode) => onChange({ mode })}
          />
        </td>
      </tr>
      <tr>
        <td className="px-4 py-2">
          <div>Time Gradient:</div>
        </td>
        <td className="px-4 py-2">
          <Select
            options={gradientOptions}
            value={timeSeries?.time_gradient}
            onChange={(value) => onChange({ time_gradient: value })}
          />
        </td>
      </tr>
      <tr>
        <td className="px-4 py-2">
          <div>Timestamp Column:</div>
        </td>
        <td className="px-4 py-2">
          <Input
            value={timeSeries?.timestamp_column}
            onChange={(e) => onChange({ timestamp_column: e.target.value })}
          />
        </td>
      </tr>
    </table>
  );
};

export default TimeSeriesView;
