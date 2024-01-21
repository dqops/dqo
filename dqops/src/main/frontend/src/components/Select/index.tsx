import React, { ReactNode, useEffect, useMemo, useRef, useState } from 'react';

import clsx from 'clsx';

import SvgIcon from '../SvgIcon';
import {
  Menu,
  MenuList,
  Tooltip,
  MenuItem,
  MenuHandler
} from '@material-tailwind/react';

export interface Option {
  label: string | number;
  value?: string | number;
  icon?: ReactNode;
}

export interface SelectProps {
  label?: string;
  options: Option[];
  placeholder?: string;
  value?: string | number;
  onChange?: (val: any) => void;
  className?: string;
  info?: boolean;
  triggerClassName?: string;
  tooltipText?: string;
  disabled?: boolean;
  error?: boolean;
  menuClassName?: string;
  onAdd?: () => void;
  addLabel?: string;
  prefix?: string;
  empty?: boolean;
  disableIcon?: boolean
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
  error,
  menuClassName,
  onAdd,
  addLabel,
  prefix,
  empty,
  disableIcon
}: SelectProps) => {
  const [menuWidth, setMenuWidth] = useState(0);
  const ref = useRef<HTMLButtonElement>(null);
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
    <div className={clsx('relative', className)}>
      {label && (
        <div className="flex space-x-1">
          <div
            className={clsx(
              'block font-regular text-gray-700 mb-1',
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
                  className="w-4 h-4 text-gray-700 cursor-pointer"
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
              'cursor-pointer text-gray-900 h-9 py-2 px-4 pr-10 rounded flex items-center text-sm border whitespace-nowrap relative',
              triggerClassName,
              disabled ? 'bg-gray-300 bg-opacity-20 cursor-not-allowed' : '',
              error ? 'border-red-500' : 'border-gray-300'
            )}
          >
            {selectedOption ? (
              <div className="flex items-center gap-2">
                {selectedOption.icon || ''}
                {(prefix ? prefix + ' ' : '') + selectedOption.label}
              </div>
            ) : (
              placeholder
            )}
            {disableIcon === true ? 
            <></> : 
            <SvgIcon
              name="chevron-down"
              className="absolute right-2 w-4"
            />
            }   
          </div>
        </MenuHandler>
        {!disabled && (
          <MenuList
            className={clsx(
              'z-50 min-w-40 bg-gray-50 !p-0 max-h-81 overflow-auto',
              menuClassName
            )}
            style={{zIndex: "200"}}
          >
            {options.map((option, index) => (
              <MenuItem
                data-testid="select-option"
                key={index}
                className="py-2 px-4 z-50 hover:bg-gray-300 cursor-pointer whitespace-nowrap text-gray-700 text-sm"
                onClick={() => handleClick(option)}
                style={{ minWidth: menuWidth, zIndex: "100" }}
              >
                <div
                  className={clsx(
                    'flex gap-2 items-center z-50',
                    empty ? 'h-5' : ''
                  )}
                >
                  {option.icon || ''}
                  {option.label}
                </div>
              </MenuItem>
            ))}
            {onAdd && (
              <MenuItem
                data-testid="select-option"
                className="py-2 px-4 hover:bg-gray-300 cursor-pointer whitespace-nowrap text-gray-700 text-sm !rounded-none"
                onClick={onAdd}
                style={{ minWidth: menuWidth }}
              >
                <div className="flex space-x-1">
                  <SvgIcon name="add" className="w-4 h-4" />
                  <div>{addLabel}</div>
                </div>
              </MenuItem>
            )}
          </MenuList>
        )}
      </Menu>
    </div>
  );
};

export default Select;
