import React, { ChangeEvent, FocusEvent } from 'react';

import clsx from 'clsx';

import { Tooltip } from '@material-tailwind/react';
import SvgIcon from '../SvgIcon';

interface ITextAreaProps {
  className?: string;
  label?: string;
  placeholder?: string;
  error?: boolean;
  helperText?: string;
  name?: string;
  value?: string | number;
  onChange?: (e: ChangeEvent<HTMLTextAreaElement>) => void;
  onBlur?: (e: FocusEvent) => void;
  onClear?: () => void;
  rows?: number;
  dataTestId?: string;
  tooltipText?: string;
  disabled?: boolean;
  onClick?: (e: any) => void;
}

const TextArea = ({
  label,
  className,
  placeholder,
  error,
  helperText,
  name,
  value,
  onChange,
  onBlur,
  onClear,
  rows,
  dataTestId,
  tooltipText,
  disabled,
  onClick
}: ITextAreaProps) => (
  <div>
    {label && (
      <label
        htmlFor={name}
        className="block text-sm font-regular text-blue-dark-700 mb-1 flex space-x-1 items-center"
      >
        <span className="text-sm !text-black">{label}</span>
        {!!tooltipText && (
          <Tooltip
            content={tooltipText}
            className="max-w-80 py-2 px-2 bg-gray-800 !z-[9999]"
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
    <div className="relative">
      <textarea
        data-testid={dataTestId || 'text-area'}
        name={name}
        placeholder={placeholder}
        className={clsx(
          error ? 'border border-red-500' : 'focus:border-teal-500',
          'placeholder-gray-500 py-1 px-1 bg-white text-gray-700 border border-gray-300 focus:outline-none block min-w-40 w-full shadow-sm text-sm rounded-md',
          className
        )}
        value={value}
        onChange={onChange}
        onBlur={onBlur}
        rows={rows}
        disabled={disabled}
        onClick={onClick}
      />
      {value && (
        <SvgIcon
          name="subtract"
          className="cursor-pointer absolute top-1/2 right-4 transform -translate-y-1/2 w-2 h-2"
          onClick={onClear}
        />
      )}
    </div>
    {error && <div className="text-red-500 text-sm ml-2">{helperText}</div>}
  </div>
);

export default TextArea;
