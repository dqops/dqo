import React, { ReactNode, useEffect, useMemo, useRef, useState } from 'react';

import clsx from 'clsx';

import { Menu, Tooltip } from '@material-tailwind/react';
import usePopup from '../../hooks/usePopup';
import SvgIcon from '../SvgIcon';

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
  disableIcon?: boolean;
  selectedMenu?: string | number;
  onClickValue?: (value: string | number) => void;
}

const Select = ({
  label,
  options = [],
  placeholder = 'Select option',
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
  disableIcon,
  selectedMenu = label,
  onClickValue
}: SelectProps) => {
  const [menuWidth, setMenuWidth] = useState(0);
  const ref = useRef<HTMLButtonElement>(null);
  const { isOpen, toggleMenu, closeMenu } = usePopup(ref);
  const selectedOption = useMemo(() => {
    return options.find(
      (item) =>
        item.value?.toString().toLowerCase() ===
          value?.toString().toLowerCase() || item.value === value
    );
  }, [options, value]);

  const handleClick = (option: Option) => {
    if (onChange) {
      onChange(option.value);
    }
    closeMenu();
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
        <div>
          <div
            className={clsx(
              'cursor-pointer text-gray-900 h-9 py-2 px-4 pr-10 rounded flex items-center text-sm border whitespace-nowrap relative',
              triggerClassName,
              disabled ? 'bg-gray-300 bg-opacity-20 cursor-not-allowed' : '',
              error ? 'border-red-500' : 'border-gray-300'
            )}
            onClick={() =>
              !disabled
                ? (toggleMenu(), onClickValue && onClickValue(label ?? ''))
                : {}
            }
          >
            {selectedOption ? (
              <div className="flex items-center gap-2">
                {selectedOption.icon || ''}
                {(prefix ? prefix + ' ' : '') + selectedOption.label}
              </div>
            ) : (
              placeholder
            )}
            {disableIcon === true ? (
              <></>
            ) : (
              <SvgIcon name="chevron-down" className="absolute right-2 w-4" />
            )}
          </div>
        </div>
        {!disabled && label === selectedMenu && (
          <div
            className={clsx(
              'absolute top-10 bg-gray-50 left-0 min-w-full z-10 shadow-lg border border-gray-300 text-left rounded transition-all duration-150 ease-in',
              isOpen
                ? 'max-h-80 overflow-auto py-3'
                : 'opacity-0 max-h-0 overflow-hidden py-0',
              menuClassName
            )}
          >
            {options.map((option, index) => (
              <div
                data-testid="select-option"
                key={index}
                className={clsx(
                  'py-2 px-4 hover:bg-gray-300 cursor-pointer whitespace-nowrap text-gray-700 text-sm h-8',
                  empty ? 'h-5' : '',
                  option.icon ? 'flex items-center gap-x-2' : ''
                )}
                onClick={() => handleClick(option)}
              >
                {option.icon || ''}
                {option.label}
              </div>
            ))}
            {onAdd && (
              <div
                data-testid="select-option"
                className="py-2 px-4 hover:bg-gray-300 cursor-pointer whitespace-nowrap text-gray-700 text-sm !rounded-none"
                onClick={onAdd}
                style={{ minWidth: menuWidth }}
              >
                <div className="flex space-x-1">
                  <SvgIcon name="add" className="w-4 h-4" />
                  <div>{addLabel}</div>
                </div>
              </div>
            )}
          </div>
        )}
      </Menu>
    </div>
  );
};

export default Select;
