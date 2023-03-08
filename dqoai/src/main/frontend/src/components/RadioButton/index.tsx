import React from 'react';
import clsx from 'clsx';

interface RadioButtonProps {
  label?: string;
  onClick: () => void;
  checked?: boolean;
}

const RadioButton = ({ label, onClick, checked }: RadioButtonProps) => {
  return (
    <div
      className="flex items-center space-x-2 cursor-pointer"
      onClick={onClick}
    >
      <div className="w-5 h-5 border border-primary rounded-full flex items-center justify-center">
        <div
          className={clsx('w-3 h-3 rounded-full', checked ? 'bg-primary' : '')}
        />
      </div>
      {label && <div className="text-sm">{label}</div>}
    </div>
  );
};

export default RadioButton;
