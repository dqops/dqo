import React, {
  ChangeEvent,
  useEffect,
  useMemo,
  useRef,
  useState
} from 'react';

import clsx from 'clsx';

import { Tooltip } from '@material-tailwind/react';
import usePopup from '../../hooks/usePopup';
import SvgIcon from '../SvgIcon';

interface Option {
  label: string;
  value: string;
}

interface SelectInputProps {
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
  limit?: number;
}

const SelectInput = ({
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
  limit
}: SelectInputProps) => {
  const ref = useRef<HTMLDivElement>(null);
  const { isOpen, toggleMenu, closeMenu } = usePopup(ref);
  const [isChanged, setIsChanged] = useState(false);

  const handleClick = (option: Option) => {
    if (onChange) {
      onChange(option.value);
    }
    setIsChanged(false);
    closeMenu();
  };

  const filteredOptions = useMemo(() => {
    if (!isChanged) return options;

    return options.filter((option) => {
      const optionLabel = option.label || '';
      const inputValue = value || '';
      return optionLabel.toLowerCase().indexOf(inputValue.toLowerCase()) > -1;
    });
  }, [value, options, isChanged]);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    if (onChange) {
      onChange(e?.target.value);
    }
    setIsChanged(true);
  };

  useEffect(() => {
    const handleClickOutside = (event: any) => {
      if (ref.current && !ref.current.contains(event.target)) {
        closeMenu();
      }
    };
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [ref]);

  const dataOptions = limit ? filteredOptions.slice(0, limit) : filteredOptions;

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
              className="max-w-80 py-2 px-2 bg-gray-800"
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
      <div className="relative w-full min-w-40" ref={ref}>
        <div
          className={clsx(
            'cursor-pointer text-gray-900 h-9 pr-8 rounded flex items-center text-sm border overflow-hidden',
            triggerClassName,
            disabled ? 'bg-gray-50 cursor-not-allowed' : '',
            error ? 'border-red-500' : 'border-gray-300'
          )}
          onClick={() => (!disabled ? toggleMenu() : {})}
        >
          <input
            className={clsx(
              'h-full pl-4 focus:outline-none w-full',
              disabled ? 'bg-gray-50 cursor-not-allowed' : ''
            )}
            value={value || ''}
            onChange={handleChange}
            placeholder={placeholder}
            disabled={disabled}
          />
          <SvgIcon
            name={isOpen ? 'chevron-up' : 'chevron-down'}
            className="absolute right-2 w-4"
          />
        </div>
        <div
          className={clsx(
            'absolute top-10 bg-gray-50 left-0 min-w-full z-10 shadow-lg border border-gray-300 text-left rounded transition-all duration-150 ease-in',
            isOpen
              ? 'max-h-80 overflow-auto py-3'
              : 'opacity-0 max-h-0 overflow-hidden py-0'
          )}
        >
          {dataOptions.map((option, index) => (
            <div
              data-testid="select-option"
              key={index}
              className="py-2 px-4 hover:bg-gray-300 cursor-pointer whitespace-nowrap text-gray-700 text-sm h-8"
              onClick={() => handleClick(option)}
            >
              {option.label}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default SelectInput;
