import React, { ChangeEvent, FocusEvent, useState } from 'react';

import clsx from 'clsx';

import { Tooltip } from '@material-tailwind/react';
import SvgIcon from '../SvgIcon';

interface INumberInputProps {
  className?: string;
  label?: string;
  placeholder?: string;
  name?: string;
  value?: string;
  onChange: (value: string) => void;
  onBlur?: (e: FocusEvent) => void;
  dataTestId?: string;
  min?: number;
  max?: number;
  tooltipText?: string;
  disabled?: boolean;
  error?: boolean;
  step?: number;
}

const FloatingPointInput = ({
  label,
  className,
  placeholder,
  name,
  value,
  onChange,
  onBlur,
  dataTestId,
  min,
  max,
  tooltipText,
  disabled,
  error,
  step
}: INumberInputProps) => {
  const [isInvalid, setIsInvalid] = useState(false);
  const [copiedValue, setCopiedValue] = useState(value);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const inputValue = e.target.value;
    setCopiedValue(inputValue);

    if (inputValue.match(/^[0-9]*[.]?[0-9]*$/)) {
      setIsInvalid(false);

      if (!inputValue.endsWith('.')) {
        onChange(inputValue);
      }
    } else {
      setIsInvalid(true);
    }
  };

  return (
    <div>
      <div className="flex space-x-1">
        {label && (
          <label
            htmlFor={name}
            className={clsx(
              ' font-regular text-gray-700 mb-1 text-sm flex space-x-1',
              error ? 'text-red-500' : ''
            )}
          >
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
      <div className="relative inline-flex">
        <input
          name={name}
          type="text"
          placeholder={placeholder}
          className={clsx(
            error || isInvalid
              ? 'border border-red-500'
              : 'focus:ring-1 focus:ring-teal-500 focus:ring-opacity-80 focus:border-none border-gray-300',
            'h-9 placeholder-gray-500 py-0.5 px-3 border text-gray-900 focus:text-gray-900 focus:outline-none block text-sm rounded',
            className
          )}
          value={copiedValue}
          onChange={handleChange}
          onBlur={onBlur}
          data-testid={dataTestId}
          min={min}
          max={max}
          disabled={disabled}
          step={step}
        />
      </div>
    </div>
  );
};

export default FloatingPointInput;
