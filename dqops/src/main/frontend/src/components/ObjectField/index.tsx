import React from 'react';

import { Tooltip } from '@material-tailwind/react';
import SvgIcon from '../SvgIcon';

interface IObjectFieldProps {
  className?: string;
  label?: string;
  value?: object;
  tooltipText?: string;
}

const ObjectField = ({ label, value, tooltipText }: IObjectFieldProps) => (
  <div>
    <div className="flex space-x-1">
      {label && (
        <label className="block font-regular text-gray-700 mb-1 text-sm flex space-x-1">
          <span>{label}</span>
          {!!tooltipText && (
            <Tooltip
              content={tooltipText}
              className="max-w-80 py-2 px-2 bg-gray-800"
            >
              <div>
                <SvgIcon
                  name="info"
                  className="w-4 h-4 text-gray-700 cursor-pointer"
                />
              </div>
            </Tooltip>
          )}
        </label>
      )}
    </div>
    <div className="relative">{JSON.stringify(value)}</div>
  </div>
);

export default ObjectField;
