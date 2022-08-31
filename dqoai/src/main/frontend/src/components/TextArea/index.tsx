import React, { ChangeEvent, FocusEvent } from 'react';
import clsx from 'clsx';
import SvgIcon from "../SvgIcon";

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
}

const TextArea: React.FC<ITextAreaProps> = ({
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
}) => (
  <div>
    <label htmlFor={name} className="block text-base font-regular text-blue-dark-700 mb-1">
      {label}
    </label>
    <div className="relative">
      <textarea
        data-testid={dataTestId || "text-area"}
        name={name}
        placeholder={placeholder}
        className={clsx(
          error ? 'border border-red-500' : 'focus:border-green-400 border-transparent',
          'placeholder-gray-500 py-2.5 px-3 bg-gray-50 text-gray-500 focus:text-purple-700 border border-transparent focus:outline-none block min-w-40 w-full shadow-sm sm:text-base rounded-md',
          className
        )}
        value={value}
        onChange={onChange}
        onBlur={onBlur}
        rows={rows}
      />
      {
        value && (
          <SvgIcon
            name="subtract"
            className="cursor-pointer absolute top-1/2 right-4 transform -translate-y-1/2 w-2 h-2"
            onClick={onClear}
          />
        )
      }
    </div>
    {error && <div className="text-red-500 text-sm ml-2">{helperText}</div>}
  </div>
);

export default TextArea;
