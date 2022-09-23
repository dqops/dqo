import React from 'react';

import SvgIcon from '../SvgIcon';

export interface CheckboxProps {
  type?: string;
  label?: any;
  className?: string;
  checked?: boolean;
  onChange: (value: boolean) => void;
}

const Checkbox = ({ label, className, checked, onChange }: CheckboxProps) => {
  return (
    <label className={`relative cursor-pointer rounded-sm ${className}`}>
      <input
        className="absolute opacity-0"
        type="checkbox"
        checked={checked}
        onChange={(e) => onChange(e.target.checked)}
      />
      <span
        className={`absolute top-1/2 rounded-sm -translate-y-1/2 left-0 h-4 w-4 flex items-center justify-center border-gray-150 ${
          checked ? 'bg-blue-600 border-0' : 'border'
        }`}
      >
        {checked && (
          <SvgIcon
            name="check"
            className="absolute w-3 top-1/2 transform -translate-y-1/2 text-white"
          />
        )}
      </span>
      {label && <span className="text-sm pl-5">{label}</span>}
    </label>
  );
};

export default Checkbox;
