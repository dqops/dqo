import React from 'react';
import Input from '../../Input';
import Checkbox from '../../Checkbox';
import { TConfigurationItemRowBoolean } from './TConfigurationItemRowBoolean'

export default function ConfigurationItemRow({
  label,
  value,
  onChange,
  defaultValue
}: TConfigurationItemRowBoolean) {
  const handleChange = (e: boolean) => {
    onChange(e);
  };

  return (
    <div>
      <Checkbox
        label={label}
        checked={value ?? defaultValue}
        onChange={handleChange}
        labelPosition="right"
      />
    </div>
  );
}
