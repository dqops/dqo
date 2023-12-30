import React from 'react';
import clsx from 'clsx';

export interface SwitchProps {
  checked: boolean;
  onChange?: (value: boolean) => void;
  className?: string;
}

const Switch: React.FC<SwitchProps> = ({ checked, onChange, className }) => {
  return (
    <div
      data-testid="switch"
      className={clsx(
        'relative cursor-pointer h-4 w-7 rounded-full',
        checked ? 'bg-primary' : 'bg-gray-150',
        className
      )}
      onClick={() => onChange && onChange(!checked)}
    >
      <div
        className={clsx(
          'absolute transform -translate-y-1/2 top-1/2 h-3 w-3 rounded-full ease-in-input transition-all duration-200 shadow-md',
          checked ? 'bg-white right-0.5' : 'bg-white left-0.5'
        )}
      />
    </div>
  );
};

export default Switch;
