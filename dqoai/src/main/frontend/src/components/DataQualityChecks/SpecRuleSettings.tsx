import React from 'react';
import { MaxValueRuleThresholdsSpec } from '../../api';
import Checkbox from '../Checkbox';
import Input from '../Input';

interface ISpecRuleSettingsProps {
  rule?: MaxValueRuleThresholdsSpec;
}

const SpecRuleSettings = ({ rule }: ISpecRuleSettingsProps) => {
  return (
    <div>
      <div className="text-gray-700">
        <div className="mb-3">
          <Checkbox
            checked={rule?.disabled}
            onChange={() => {}}
            label="Disabled"
          />
        </div>
        <div className="mb-3">
          <div className="font-semibold mb-3">Time Window</div>
          <div className="mb-2">
            <Input
              label="prediction_time_window"
              value={rule?.time_window?.prediction_time_window}
            />
          </div>
          <div className="mb-2">
            <Input
              label="min_periods_with_reading"
              value={rule?.time_window?.min_periods_with_reading}
            />
          </div>
        </div>
        <div className="mb-3">
          <div className="font-semibold">Medium</div>
          <div className="flex items-center space-x-4">
            <Checkbox
              checked={rule?.medium?.disabled}
              onChange={() => {}}
              label="Disabled"
            />
            <div>
              <Input value={rule?.medium?.max_value} />
            </div>
          </div>
        </div>
        <div className="mb-3">
          <div className="font-semibold">High</div>
          <div className="flex items-center space-x-4">
            <Checkbox
              checked={rule?.high?.disabled}
              onChange={() => {}}
              label="Disabled"
            />
            <div>
              <Input value={rule?.high?.max_value} />
            </div>
          </div>
        </div>
        <div className="mb-3">
          <div className="font-semibold">Low</div>
          <div className="flex items-center space-x-4">
            <Checkbox
              checked={rule?.low?.disabled}
              onChange={() => {}}
              label="Disabled"
            />
            <div>
              <Input value={rule?.low?.max_value} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SpecRuleSettings;
