import React from 'react';

import SvgIcon from '../SvgIcon';
import { Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';

export interface CheckboxProps {
  type?: string;
  label?: any;
  className?: string;
  checked?: boolean;
  onChange: (value: boolean) => void;
  tooltipText?: string;
  disabled?: boolean;
  error?: boolean;
}

const Checkbox = ({
  label,
  className,
  checked,
  onChange,
  tooltipText,
  disabled,
  error
}: CheckboxProps) => {
  return (
    <label className={`relative cursor-pointer rounded-sm ${className}`}>
      <input
        disabled={disabled}
        className="absolute opacity-0"
        type="checkbox"
        checked={checked}
        onChange={(e) => onChange(e.target.checked)}
      />
      <span
        className={clsx(
          'absolute top-1/2 rounded-sm -translate-y-1/2 left-0 h-4 w-4 flex items-center justify-center text-sm',
          checked ? 'bg-blue-600 border-0' : 'border',
          error ? 'border-red-500' : 'border-gray-150'
        )}
      >
        {checked && (
          <SvgIcon
            name="check"
            className="absolute w-3 top-1/2 transform -translate-y-1/2 text-white"
          />
        )}
      </span>
      {label && (
        <div className="flex space-x-1 items-center">
          <span className="text-sm pl-5">{label}</span>
          {!!tooltipText && (
            <Tooltip
              content={tooltipText}
              className="max-w-80 py-4 px-4 bg-gray-800"
            >
              <div>
                <SvgIcon
                  name="info"
                  className="w-4 h-4 text-blue-700 cursor-pointer"
                />
              </div>
            </Tooltip>
          )}
        </div>
      )}
    </label>
  );
};

export default Checkbox;
