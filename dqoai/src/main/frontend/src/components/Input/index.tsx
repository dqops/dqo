import React, { ChangeEvent, FocusEvent } from 'react';
import clsx from 'clsx';
import SvgIcon from "../SvgIcon";

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

const Input: React.FC<IInputProps> = ({
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
}) => (
  <div>
    <div className="flex space-x-1">
      <label htmlFor={name} className="block text-base font-regular text-blue-dark-700 mb-1 capitalize">
        {label}
      </label>
    </div>
    <div className="relative">
      <input
        name={name}
        type={type || 'text'}
        placeholder={placeholder}
        className={clsx(
          error ? 'border border-red-500' : 'focus:border-green-400 border-transparent',
          'h-11 placeholder-gray-500 py-2.5 px-3 bg-gray-50 text-gray-500 focus:text-purple-700 border border-transparent focus:outline-none block min-w-40 w-full shadow-sm sm:text-base rounded-md',
          className
        )}
        value={value}
        onChange={onChange}
        onBlur={onBlur}
        data-testid={dataTestId}
      />
      {
        value && (
          <div className="absolute top-1/2 right-4 bg-gray-50 transform -translate-y-1/2 w-4 h-4 flex justify-center items-center">
            <SvgIcon
              name="subtract"
              className="cursor-pointer w-2 h-2"
              onClick={onClear}
            />
          </div>
        )
      }
    </div>
    {error && <div className="text-red-500 text-sm ml-2">{helperText}</div>}
  </div>
);

export default Input;
