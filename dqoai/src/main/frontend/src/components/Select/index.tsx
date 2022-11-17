import React, { useEffect, useMemo, useRef, useState } from 'react';

import clsx from 'clsx';

import SvgIcon from '../SvgIcon';
import {
  Menu,
  MenuList,
  Tooltip,
  MenuItem,
  MenuHandler
} from '@material-tailwind/react';

interface Option {
  label: string;
  value: string;
}

interface SelectProps {
  label?: string;
  options: Option[];
  placeholder?: string;
  value?: string;
  onChange?: (val: any) => void;
  className?: string;
  info?: boolean;
  triggerClassName?: string;
  tooltipText?: string;
  disabled?: boolean;
  error?: boolean;
}

const Select = ({
  label,
  options = [],
  placeholder = 'Select Option',
  value,
  onChange,
  className,
  triggerClassName,
  tooltipText,
  disabled,
  error
}: SelectProps) => {
  const [menuWidth, setMenuWidth] = useState(0);
  const ref = useRef<HTMLDivElement>(null);
  const selectedOption = useMemo(() => {
    return options.find((item) => item.value === value);
  }, [options, value]);

  const handleClick = (option: Option) => {
    if (onChange) {
      onChange(option.value);
    }
  };

  useEffect(() => {
    setMenuWidth(ref.current?.offsetWidth || 0);
  }, []);

  return (
    <div className={clsx('', className)}>
      {label && (
        <div className="flex space-x-1">
          <div
            className={clsx(
              'block text-sm font-regular text-gray-700 mb-1',
              error ? 'text-red-500' : ''
            )}
          >
            {label}
          </div>
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
        </div>
      )}
      <Menu placement="bottom-end">
        <MenuHandler ref={ref}>
          <div
            className={clsx(
              'cursor-pointer text-gray-900 h-9 py-2 px-4 pr-10 text-black rounded flex items-center text-sm border whitespace-nowrap',
              triggerClassName,
              disabled ? 'bg-gray-300 cursor-not-allowed' : '',
              error ? 'border-red-500' : 'border-gray-300'
            )}
          >
            {selectedOption ? selectedOption.label : placeholder}
            <SvgIcon
              name="chevron-down"
              className="absolute transform top-1/2 -translate-y-2/4 right-2 text-black w-4"
            />
          </div>
        </MenuHandler>
        {!disabled && (
          <MenuList className="z-50 min-w-40 bg-gray-50 !p-0 max-h-80 overflow-auto">
            {options.map((option, index) => (
              <MenuItem
                data-testid="select-option"
                key={index}
                className="py-2 px-4 hover:bg-gray-300 cursor-pointer whitespace-nowrap text-gray-700 text-sm"
                onClick={() => handleClick(option)}
                style={{ minWidth: menuWidth }}
              >
                {option.label}
              </MenuItem>
            ))}
          </MenuList>
        )}
      </Menu>
    </div>
  );
};

export default Select;
