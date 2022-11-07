import React, { FocusEvent, useCallback } from 'react';

import clsx from 'clsx';

import SvgIcon from '../SvgIcon';
import { Tooltip } from '@material-tailwind/react';

interface INumberInputProps {
  className?: string;
  label?: string;
  placeholder?: string;
  name?: string;
  value?: string | number;
  onChange: (value: number) => void;
  onBlur?: (e: FocusEvent) => void;
  dataTestId?: string;
  min?: number;
  max?: number;
  tooltipText?: string;
}

const NumberInput = ({
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
  tooltipText
}: INumberInputProps) => {
  const handleChange = (e: any) => {
    if (onChange) {
      onChange(Number(e.target.value));
    }
  };

  const increase = useCallback(() => {
    if (!value) {
      onChange(1);
    } else {
      onChange(Number(value) + 1);
    }
  }, [onChange, value]);

  const decrease = useCallback(() => {
    if (!value) {
      if (min === undefined || min <= -1) {
        onChange(-1);
      }
    } else if (min === undefined || min <= Number(value) - 1) {
      onChange(Number(value) - 1);
    }
  }, [onChange, value]);

  return (
    <div>
      <div className="flex space-x-1">
        {label && (
          <label
            htmlFor={name}
            className="block font-regular text-gray-700 mb-1 text-sm flex space-x-1"
          >
            <span>{label}</span>
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
          </label>
        )}
      </div>
      <div className="relative">
        <input
          name={name}
          type="number"
          placeholder={placeholder}
          className={clsx(
            'focus:ring-2 focus:ring-blue-400 focus:ring-opacity-80 focus:border-none border-gray-300',
            'h-9 placeholder-gray-500 py-0.5 px-3 border text-gray-900 focus:text-gray-900 focus:outline-none block w-full sm:text-base rounded',
            className
          )}
          value={value}
          onChange={handleChange}
          onBlur={onBlur}
          data-testid={dataTestId}
          min={min}
          max={max}
        />
        <div className="flex flex-col absolute top-1/2 transform -translate-y-1/2 right-2">
          <SvgIcon
            className="cursor-pointer w-4"
            name="chevron-up"
            onClick={increase}
          />
          <SvgIcon
            className="cursor-pointer w-4"
            name="chevron-down"
            onClick={decrease}
          />
        </div>
      </div>
    </div>
  );
};

export default NumberInput;
