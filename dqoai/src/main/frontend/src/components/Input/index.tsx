import React, { ChangeEvent, FocusEvent } from 'react';

import clsx from 'clsx';

import SvgIcon from '../SvgIcon';

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
  dataTestId
}: IInputProps) => (
  <div>
    <div className="flex space-x-1">
      {label && (
        <label
          htmlFor={name}
          className="block text-base font-regular text-gray-700 mb-2 capitalize text-sm"
        >
          {label}
        </label>
      )}
    </div>
    <div className="relative">
      <input
        name={name}
        type={type || 'text'}
        placeholder={placeholder}
        className={clsx(
          error
            ? 'border border-red-500'
            : 'focus:ring-2 focus:ring-blue-400 focus:ring-opacity-80 focus:border-none border-gray-300',
          'h-9 placeholder-gray-500 py-0.5 px-3 border text-gray-900 focus:text-gray-900 focus:outline-none block min-w-40 w-full sm:text-base rounded',
          className
        )}
        value={value || ''}
        onChange={onChange}
        onBlur={onBlur}
        data-testid={dataTestId}
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
