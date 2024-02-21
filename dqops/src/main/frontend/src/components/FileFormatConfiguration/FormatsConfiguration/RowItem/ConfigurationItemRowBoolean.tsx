import React from 'react';
import Input from '../../../Input';
import Checkbox from '../../../Checkbox';
import { TConfigurationItemRowBoolean } from './TConfigurationItemRowBoolean';
import CheckboxThreeSteps from '../../../CheckBoxThreeSteps';

export default function ConfigurationItemRow({
  label,
  value,
  onChange,
  defaultValue,
  className
}: TConfigurationItemRowBoolean) {
  const handleChange = (e: boolean | undefined) => {
    onChange(e);
  };
  //debug why is not gray
  return (
    <CheckboxThreeSteps
      label={label}
      checked={value ?? defaultValue}
      onChange={handleChange}
      labelPosition="right"
      className={className}
    />
  );
}
