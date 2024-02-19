import React, { FocusEvent, useRef } from 'react';

import clsx from 'clsx';
import SvgIcon from '../SvgIcon';
import { Tooltip } from '@material-tailwind/react';
import moment from 'moment';
import usePopup from '../../hooks/usePopup';
import BaseDatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

interface IFieldDatePickerProps {
  type?: string;
  className?: string;
  label?: string;
  placeholder?: string;
  error?: boolean;
  helperText?: string;
  name?: string;
  value?: string | number;
  onChange?: (e: string) => void;
  onBlur?: (e: FocusEvent) => void;
  onClear?: () => void;
  info?: boolean;
  dataTestId?: string;
  tooltipText?: string;
  disabled?: boolean;
  defaultValue?: string;
}

const FieldDatePicker = ({
  label,
  error,
  helperText,
  name,
  value,
  onChange,
  onClear,
  tooltipText,
  disabled
}: IFieldDatePickerProps) => {
  const ref = useRef(null);
  const { isOpen, toggleMenu } = usePopup(ref);

  const onClick = () => {
    if (disabled) return;

    toggleMenu();
  };

  return (
    <div>
      <div className="flex space-x-1">
        {label && (
          <label
            htmlFor={name}
            className={clsx(
              'font-regular text-gray-700 mb-1 text-sm flex space-x-1',
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
                    className="w-4 h-4 text-gray-700 cursor-pointer"
                  />
                </div>
              </Tooltip>
            )}
          </label>
        )}
      </div>
      <div className="relative ">
        <div ref={ref} className="relative">
          <input
            type="text"
            value={value}
            onChange={(e) => onChange && onChange(e.target.value)}
            placeholder="YYYY-MM-DD"
            className={clsx(
              'h-9 placeholder-gray-500 py-0.5 px-3 border text-gray-900 focus:text-gray-900 focus:outline-none text-sm rounded flex items-center w-30',
              disabled ? 'bg-gray-300 bg-opacity-20 cursor-not-allowed' : '',
              !value
                ?.toString()
                .match(
                  /^(?:\d{4})-(?:0[1-9]|1[0-2])-(?:0[1-9]|[1-2][0-9]|3[0-1])$/
                )
                ? 'border-red-500'
                : ''
            )}
            disabled={disabled}
          />
          {isOpen && (
            <div className="absolute z-10 bg-white">
              <BaseDatePicker
                placeholderText="YYYY-MM-DD"
                onChange={onChange}
                selected={value}
                disabled={disabled}
                inline
              />
            </div>
          )}
        </div>
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
};

export default FieldDatePicker;
