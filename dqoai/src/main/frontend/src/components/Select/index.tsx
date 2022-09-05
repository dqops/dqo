import React, { useMemo, useRef } from 'react';
import clsx from 'clsx';
import SvgIcon from '../SvgIcon';
import usePopup from '../../hooks/usePopup';

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
}

const Select = ({
  label,
  options = [],
  placeholder = 'Select Option',
  value,
  onChange,
  className,
  info,
}: SelectProps) => {
  const ref = useRef(null);
  const { isOpen, toggleMenu, closeMenu } = usePopup(ref);

  const selectedOption = useMemo(() => {
    return options.find((item) => item.value === value);
  }, [options, value]);

  const handleClick = (option: Option) => {
    onChange && onChange(option.value);
    closeMenu();
  }

  return (
    <div className={clsx('', className)}>
      {
        label && (
          <div className="flex space-x-1">
            <div className="block text-sm font-regular text-gray-700 mb-1">
              {label}
            </div>
            {
              info && (
                <SvgIcon name="info" />
              )
            }
          </div>
        )
      }
      <div className="relative w-full min-w-40" ref={ref}>
        <div className="cursor-pointer text-gray-900 h-9 py-2 px-4 pr-10 text-black rounded flex items-center text-sm border border-gray-300" onClick={toggleMenu}>
          {
            selectedOption ? selectedOption.label : placeholder
          }
          <SvgIcon
            name="chevron-down"
            className={clsx('absolute transform top-1/2 -translate-y-2/4 right-2 text-black w-4', isOpen ? 'transform rotate-180' : '')}
          />
        </div>
        <div className={clsx('absolute top-10 bg-gray-50 left-0 min-w-full z-10 shadow-lg border border-gray-300 text-left rounded transition-all duration-150 ease-in', isOpen ? 'max-h-80 overflow-auto py-3' : 'opacity-0 max-h-0 overflow-hidden py-0')}>
          {
            options.map((option, index) => (
              <div
                data-testid="select-option"
                key={index}
                className="py-2 px-4 hover:bg-gray-300 cursor-pointer whitespace-nowrap text-gray-700 text-sm"
                onClick={() => handleClick(option)}
              >
                {option.label}
              </div>
            ))
          }
        </div>
      </div>
    </div>
  );
};

export default Select;
