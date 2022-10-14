import React from 'react';
import {
  Popover,
  PopoverHandler,
  PopoverContent
} from '@material-tailwind/react';
import { MinValueRuleThresholdsSpec } from '../../../../api';
import Checkbox from '../../../Checkbox';
import Input from '../../../Input';

interface IMinValueRuleSettingsProps {
  rule?: MinValueRuleThresholdsSpec;
}

const MinValueRuleSettings = ({ rule }: IMinValueRuleSettingsProps) => {
  return (
    <div>
      <Popover placement="bottom-start">
        <PopoverHandler>
          <div className="text-sm cursor-pointer text-blue-700 font-semibold inline-block">
            More Settings
          </div>
        </PopoverHandler>
        <PopoverContent className="border-gray-400 max-h-80 overflow-auto">
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
                  <Input value={rule?.medium?.min_value} />
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
                  <Input value={rule?.high?.min_value} />
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
                  <Input value={rule?.low?.min_value} />
                </div>
              </div>
            </div>
          </div>
        </PopoverContent>
      </Popover>
    </div>
  );
};

export default MinValueRuleSettings;
