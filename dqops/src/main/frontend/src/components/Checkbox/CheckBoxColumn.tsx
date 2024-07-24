import { Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import React from 'react';
import SvgIcon from '../SvgIcon';

export interface CheckboxProps {
  type?: string;
  label?: any;
  className?: string;
  checked?: boolean;
  onChange: (value: boolean) => void;
  tooltipText?: string;
  disabled?: boolean;
  error?: boolean;
  labelPosition?: 'left' | 'right';
  checkClassName?: string;
  isDisabled?: boolean;
}

const CheckboxColumn = ({
  label,
  className,
  checked = false,
  onChange,
  tooltipText,
  disabled,
  error,
  checkClassName,
  isDisabled
}: CheckboxProps) => {
  return (
    <label
      className={clsx(
        'block relative cursor-pointer rounded-sm inline-flex flex-col items-start select-none',
        className
      )}
    >
      {label && (
        <div className="mb-2 inline-flex space-x-1 items-center">
          <span className="text-sm">{label}</span>
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
        </div>
      )}
      <div className="inline-flex items-center space-x-2">
        <input
          disabled={disabled}
          className="h-4 w-4 cursor-pointer opacity-0"
          type="checkbox"
          checked={checked}
          onChange={(e) => onChange(e.target.checked)}
        />
        <span
          className={clsx(
            'h-4 w-4 flex items-center justify-center text-sm rounded-sm',
            checked ? checkClassName + ' bg-teal-500 border-0' : 'border',
            error ? 'border-red-500' : 'border-gray-150',
            isDisabled ? 'bg-gray-200' : ''
          )}
        >
          {checked && <SvgIcon name="check" className="w-3 text-white" />}
        </span>
      </div>
    </label>
  );
};

export default CheckboxColumn;
