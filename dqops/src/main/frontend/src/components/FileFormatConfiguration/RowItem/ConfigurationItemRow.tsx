import React from 'react';
import Input from '../../Input';
import Checkbox from '../../Checkbox';
import { TConfigurationItemRow } from './TConfigurationItemRow'

export default function ConfigurationItemRow({
  label,
  value,
  onChange,
  defaultValue
}: TConfigurationItemRow) {
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    onChange(e.target.value);
  };

  return (
    <div>
      <div className="py-1">{label}</div>
      <Input 
        value={value ?? defaultValue}
        onChange={handleChange} />
    </div>
  );
}
