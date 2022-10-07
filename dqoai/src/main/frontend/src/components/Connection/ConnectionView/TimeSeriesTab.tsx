import React from 'react';
import {
  TimeSeriesConfigurationSpec,
  TimeSeriesConfigurationSpecModeEnum,
  TimeSeriesConfigurationSpecTimeGradientEnum
} from '../../../api';
import Select from '../../Select';
import Input from '../../Input';
import NumberInput from '../../NumberInput';

interface IConnectionDetailProps {
  timeSeries?: TimeSeriesConfigurationSpec;
  setTimeSeries: (val: TimeSeriesConfigurationSpec) => void;
}

const TimeSeriesTab: React.FC<IConnectionDetailProps> = ({
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
    <div className="p-4">
      <table className="mb-6 w-160">
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
        <tr>
          <td className="px-4 py-2">
            <div>Time Window Periods:</div>
          </td>
          <td className="px-4 py-2">
            <NumberInput
              value={timeSeries?.time_window_periods}
              onChange={(value) => onChange({ time_window_periods: value })}
            />
          </td>
        </tr>
        <tr>
          <td className="px-4 py-2">
            <div>Incremental time window periods:</div>
          </td>
          <td className="px-4 py-2">
            <NumberInput
              value={timeSeries?.incremental_time_window_periods}
              onChange={(value) =>
                onChange({ incremental_time_window_periods: value })
              }
            />
          </td>
        </tr>
        <tr>
          <td className="px-4 py-2">
            <div>Excluded recent periods:</div>
          </td>
          <td className="px-4 py-2">
            <NumberInput
              value={timeSeries?.excluded_recent_periods}
              onChange={(value) => onChange({ excluded_recent_periods: value })}
            />
          </td>
        </tr>
      </table>
    </div>
  );
};

export default TimeSeriesTab;
