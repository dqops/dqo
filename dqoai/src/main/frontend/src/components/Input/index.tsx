import React, { ChangeEvent, FocusEvent, KeyboardEvent } from 'react';

import clsx from 'clsx';

import SvgIcon from '../SvgIcon';
import { Tooltip } from '@material-tailwind/react';

interface IInputProps {
  type?: string;
  className?: string;
  label?: string;
  placeholder?: string;
  error?: boolean;
  helperText?: string;
  name?: string;
  value?: string | number;
  onChange?: (e: ChangeEvent<HTMLInputElement>) => void;
  onBlur?: (e: FocusEvent) => void;
  onClear?: () => void;
  info?: boolean;
  dataTestId?: string;
  tooltipText?: string;
  disabled?: boolean;
  defaultValue?: string;
  onKeyDown?: (e: KeyboardEvent<HTMLInputElement>) => void;
}

const Input = ({
  label,
  type,
  className,
  placeholder,
  error,
  helperText,
  name,
  value,
  onChange,
  onBlur,
  onClear,
  dataTestId,
  tooltipText,
  disabled,
  defaultValue,
  onKeyDown,
}: IInputProps) => (
  <div>
    <div className="flex space-x-1">
      {label && (
        <label
          htmlFor={name}
          className={clsx(
            'block font-regular text-gray-700 mb-1 text-sm flex space-x-1',
            error ? 'text-red-500' : ''
          )}
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
        type={type || 'text'}
        placeholder={placeholder}
        disabled={disabled}
        className={clsx(
          error
            ? 'border border-red-500'
            : 'focus:ring-1 focus:ring-teal-500 focus:ring-opacity-80 focus:border-0 border-gray-300',
          'h-9 placeholder-gray-500 py-0.5 px-3 border text-gray-900 focus:text-gray-900 focus:outline-none block min-w-40 w-full sm:text-base rounded',
          className
        )}
        value={value || ''}
        onChange={onChange}
        onBlur={onBlur}
        data-testid={dataTestId}
        defaultValue={defaultValue}
        onKeyDown={onKeyDown}
      />
      {value && (
        <div className="absolute top-1/2 right-4 transform -translate-y-1/2 w-4 h-4 flex justify-center items-center">
          <SvgIcon
            name="subtract"
            className="cursor-pointer w-2 h-2"
            onClick={onClear}
          />
        </div>
      )}
    </div>
    {error && <div className="text-red-500 text-sm ml-2">{helperText}</div>}
  </div>
);

export default Input;
