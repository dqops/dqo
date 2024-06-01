import React, { useRef } from 'react';

import { Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import SvgIcon from '../SvgIcon';

export interface CheckboxProps {
  type?: string;
  label?: any;
  className?: string;
  checked?: boolean;
  onChange: (value: boolean | undefined) => void;
  tooltipText?: string;
  disabled?: boolean;
  error?: boolean;
  labelPosition?: 'left' | 'right';
  checkClassName?: string;
  isDisabled?: boolean;
}

const getStep = (checked: boolean | undefined) => {
  if (typeof checked === 'undefined') {
    return 3;
  }
  if (checked === true) {
    return 1;
  }
  if (checked === false) {
    return 2;
  }
  return 3;
};

const CheckboxThreeSteps = ({
  label,
  className,
  checked,
  onChange,
  tooltipText,
  disabled,
  error,
  labelPosition = 'right',
  checkClassName,
  isDisabled
}: CheckboxProps) => {
  const ref = useRef(getStep(checked));
  const handleOnChange = () => {
    if (ref.current < 3) {
      ref.current = ref.current + 1;
    } else {
      ref.current = 1;
    }
    if (ref.current === 1) {
      onChange(true);
    } else if (ref.current === 2) {
      onChange(false);
    } else if (ref.current === 3) {
      onChange(undefined);
    }
  };
  return (
    <label
      className={clsx(
        'block relative cursor-pointer rounded-sm inline-flex items-center select-none',
        className,
        labelPosition === 'left' ? 'flex-row-reverse' : ''
      )}
    >
      <div className="relative">
        <input
          disabled={disabled}
          className="absolute opacity-0"
          type="checkbox"
          checked={checked}
          onChange={handleOnChange}
        />
        <span
          className={clsx(
            'absolute top-1/2 rounded-sm -translate-y-1/2 left-0 h-4 w-4 flex items-center justify-center text-sm',
            checked ? checkClassName + ' bg-teal-500 border-0' : 'border',
            error ? 'border-red-500' : 'border-gray-150',
            isDisabled || checked === undefined ? 'bg-gray-200' : ''
          )}
        >
          {checked && (
            <SvgIcon
              name="check"
              className="absolute w-3 top-1/2 transform -translate-y-1/2 text-white"
            />
          )}
        </span>
      </div>
      {label && (
        <div
          className={clsx(
            'inline-flex space-x-1 items-center',
            labelPosition === 'left' ? 'mr-2' : 'ml-5'
          )}
        >
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
    </label>
  );
};

export default CheckboxThreeSteps;
