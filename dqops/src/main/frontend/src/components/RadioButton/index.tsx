import clsx from 'clsx';
import React from 'react';

interface RadioButtonProps {
  label?: string;
  onClick: () => void;
  checked?: boolean;
  className?: string;
  fontClassName?: string;
}

const RadioButton = ({ label, onClick, checked, className, fontClassName }: RadioButtonProps) => {
  return (
    <div
      className={clsx("flex items-center space-x-2 cursor-pointer", className)}
      onClick={onClick}
    >
      <div className="w-5 h-5 border border-primary rounded-full flex items-center justify-center">
        <div
          className={clsx('w-3 h-3 rounded-full', checked ? 'bg-primary' : '')}
        />
      </div>
      {label && <div className={clsx("text-sm", fontClassName)}>{label}</div>}
    </div>
  );
};

export default RadioButton;
